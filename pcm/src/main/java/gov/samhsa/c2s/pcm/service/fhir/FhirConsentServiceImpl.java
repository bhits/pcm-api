package gov.samhsa.c2s.pcm.service.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.IGenericClient;
import gov.samhsa.c2s.pcm.domain.consent.*;
import gov.samhsa.c2s.pcm.domain.provider.IndividualProvider;
import gov.samhsa.c2s.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.c2s.pcm.domain.reference.ClinicalConceptCode;
import gov.samhsa.c2s.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.c2s.pcm.infrastructure.dto.PatientDto;
import gov.samhsa.c2s.pcm.service.dto.SensitivePolicyCodeEnum;
import org.apache.commons.io.FileUtils;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Consent;
import org.hl7.fhir.dstu3.model.codesystems.V3ActReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * Created by Sadhana.chandra on 12/2/2016.
 * Implementatin for FHIR Consent Service
 */

@Service
public class FhirConsentServiceImpl implements FhirConsentService {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FhirContext fhirContext;

    @Autowired
    private IGenericClient fhirClient;

    @Autowired
    private FhirPatientService fhirPatientService;

    @Value("${c2s.pcm.config.pid.domain.id}")
    private String pidSystem;

    @Value("${c2s.pcm.config.npi.system}")
    private String npiSystem;

    @Value("${c2s.pcm.config.npi.label}")
    private String npiLabel;

    @Value("${c2s.pcm.config.pou.system}")
    private String pouSystem;

    @Value("${logging.path}")
    private String logOutputPath;

    @Value("${c2s.pcm.config.hie-connection.fhir.keepExcludeList}")
    private String keepExcludeList;

    // FHIR resource identifiers for inline/embedded objects
    private String CONFIDENTIALITY_CODE_CODE_SYSTEM = "urn:oid:2.16.840.1.113883.5.25";
    private String CODE_SYSTEM_SET_OPERATOR = "http://hl7.org/fhir/v3/SetOperator";

    @Override
    public void publishFhirConsentToHie(Consent fhirConsent) {
        //  invoke Consent service
       fhirClient.create().resource(fhirConsent).execute();

    }

    @Override
    public void publishFhirConsentToHie(gov.samhsa.c2s.pcm.domain.consent.Consent consent, PatientDto patientDto) {
        publishFhirConsentToHie(createFhirConsent(consent, patientDto));
    }

    @Override
    public Consent createFhirConsent(gov.samhsa.c2s.pcm.domain.consent.Consent consent, PatientDto patientDto) {
        return createGranularConsent(consent, patientDto);
    }

    // TODO :: Need to Retrieve Patient object from FHIR Server and add to consent object
/*    private Patient getFhirPatient(PatientDto patientDto){
        Patient foundPatient = null;
        Bundle results = fhirClient.search().forResource(Patient.class)
                //.where(Patient.IDENTIFIER.exactly().systemAndCode()systemAndIdentifier("system", pidSystem))
                .where(new TokenClientParam("email").exactly().code(patientDto.getEmail()))
                .and(new TokenClientParam("family").exactly().code(patientDto.getLastName()))
                .and(new TokenClientParam("given").exactly().code(patientDto.getFirstName()))

                //  .and(Patient.IDENTIFIER.exactly().systemAndValues("value",patientDto.getMedicalRecordNumber()))
                .returnBundle(Bundle.class)
                  .execute();
        if(results.getEntry().get(0) != null)
          foundPatient = (Patient) results.getEntry().get(0).getResource();
        return foundPatient;
    }*/

    private Consent createBasicConsent(gov.samhsa.c2s.pcm.domain.consent.Consent c2sConsent, PatientDto patientDto) {


        Consent fhirConsent = new Consent();

        // set the id as a concatenated "OID.consentId"
        fhirConsent.setId(new IdType(c2sConsent.getConsentReferenceId()));

        // Set patient reference and add patient as contained resource
        Patient fhirPatient = fhirPatientService.createFhirPatient(patientDto);
        fhirConsent.getPatient().setReference("#" + patientDto.getMedicalRecordNumber());
        fhirConsent.getContained().add(fhirPatient);

        // Consent signature details
        Reference consentSignature = new Reference();
        consentSignature.setDisplay(fhirPatient.getNameFirstRep().getNameAsSingleString());
        fhirConsent.getConsentor().add(consentSignature);

        // consent status
        fhirConsent.setStatus(Consent.ConsentStatus.ACTIVE);

        // Specify Authors, the providers authorizes to disclose
        // Author :: Organizational Provider
        Organization sourceOrganizationResource = null;
        for (ConsentOrganizationalProviderPermittedToDisclose orgPermittedTo : c2sConsent.getOrganizationalProvidersPermittedToDisclose()) {
            Set<OrganizationalProvider> sourceOrgPermittedTo = new HashSet<>();
            sourceOrgPermittedTo.add(orgPermittedTo.getOrganizationalProvider());
            sourceOrganizationResource = setOrganizationProvider(sourceOrgPermittedTo, orgPermittedTo.getOrganizationalProvider().getOrgName());
        }

        if (null != sourceOrganizationResource) {
            fhirConsent.getContained().add(sourceOrganizationResource);
            fhirConsent.getOrganization().setReference("#" + sourceOrganizationResource.getId());
        } else {
            //// Author :: Individual Provider
            Practitioner sourcePractitioner = null;
            for (ConsentIndividualProviderPermittedToDisclose indPermittedTo : c2sConsent.getProvidersPermittedToDisclose()) {
                Set<IndividualProvider> sourceindPermittedTo = new HashSet<>();
                sourceindPermittedTo.add(indPermittedTo.getIndividualProvider());
                sourcePractitioner = setPractitionerProvider(sourceindPermittedTo, indPermittedTo.getIndividualProvider().getNpi());
            }
            if (null != sourcePractitioner) {
                fhirConsent.getContained().add(sourcePractitioner);
                fhirConsent.getOrganization().setReference("#" + sourcePractitioner.getId());
            }
        }

        // Specify Recipients, the providers disclosure is made to
        // Recipient :: Organizational Provider
        Organization recipientOrganization = null;
        for (ConsentOrganizationalProviderDisclosureIsMadeTo orgMadeTo : c2sConsent.getOrganizationalProvidersDisclosureIsMadeTo()) {
            Set<OrganizationalProvider> recipientOrgMadeTo = new HashSet<>();
            recipientOrgMadeTo.add(orgMadeTo.getOrganizationalProvider());
            recipientOrganization = setOrganizationProvider(recipientOrgMadeTo, orgMadeTo.getOrganizationalProvider().getOrgName());
        }
        if (null != recipientOrganization) {
            fhirConsent.getContained().add(recipientOrganization);
            fhirConsent.getRecipient().add(new Reference().setReference("#" + recipientOrganization.getId()));
         } else {
            // Recipient :: Individual Provider
            Practitioner recipientPractitioner = null;
            for (ConsentIndividualProviderDisclosureIsMadeTo indPermittedTo : c2sConsent.getProvidersDisclosureIsMadeTo()) {
                Set<IndividualProvider> recipientIndPermittedTo = new HashSet<>();
                recipientIndPermittedTo.add(indPermittedTo.getIndividualProvider());
                recipientPractitioner = setPractitionerProvider(recipientIndPermittedTo, indPermittedTo.getIndividualProvider().getNpi());
            }
            if(null != recipientPractitioner) {
                fhirConsent.getContained().add(recipientPractitioner);
                fhirConsent.getRecipient().add(new Reference().setReference("#" + recipientPractitioner.getId()));
            }
        }


        // set POU
        for (ConsentShareForPurposeOfUseCode pou : c2sConsent.getShareForPurposeOfUseCodes()) {
            String fhirPou = getPurposeOfUseCode.apply(pou.getPurposeOfUseCode());
            Coding coding = new Coding(pouSystem, fhirPou, pou.getPurposeOfUseCode().getCode());
            fhirConsent.getPurpose().add(coding);
        }

        /* set terms of consent and intended recipient(s) */
        fhirConsent.getPeriod().setStart(c2sConsent.getStartDate());
        fhirConsent.getPeriod().setEnd(c2sConsent.getEndDate());
        // consent sign time
        fhirConsent.setDateTime(new Date());

        // final String xdsDocumentEntryUniqueId = uniqueOidProvider.getOid();
        // fhirConsent.getIdentifier().setSystem(pidSystem).setValue(xdsDocumentEntryUniqueId);
        // set identifier for this consent
        fhirConsent.getIdentifier().setSystem(pidSystem).setValue(c2sConsent.getConsentReferenceId());

        //set category
        //fhirConsent.getType().setValueAsEnum(ContractTypeCodesEnum.DISCLOSURE);


        //TODO (#20): write log only in debug mode
       // createConsentToLogMessage(fhirConsent, "BasicConsent" + fhirPatient.getId());
        return fhirConsent;
    }

    private Organization setOrganizationProvider(Set<OrganizationalProvider> orgProviders, String orgIdName) {
        Organization sourceOrganizationResource = new Organization();

        orgProviders.forEach((OrganizationalProvider organizationalProvider) ->
        {
            sourceOrganizationResource.setId(new IdType(orgIdName));
            sourceOrganizationResource.addIdentifier().setSystem(npiSystem).setValue(organizationalProvider.getNpi());
            sourceOrganizationResource.setName(organizationalProvider.getOrgName());
            sourceOrganizationResource.addAddress().addLine(organizationalProvider.getFirstLinePracticeLocationAddress())
                    .setCity(organizationalProvider.getMailingAddressCityName())
                    .setState(organizationalProvider.getMailingAddressStateName())
                    .setPostalCode(organizationalProvider.getMailingAddressPostalCode());
        });
        return sourceOrganizationResource;
    }

    private Practitioner setPractitionerProvider(Set<IndividualProvider> individualProviders, String practIdName) {
        Practitioner sourcePractitionerResource = new Practitioner();

        individualProviders.forEach((IndividualProvider individualProvider) ->
        {
            sourcePractitionerResource.setId(new IdType(practIdName));
            sourcePractitionerResource.addIdentifier().setSystem(npiSystem).setValue(individualProvider.getNpi());
            //setting the name element
            HumanName indName = new HumanName();
            indName.addFamily(individualProvider.getLastName());
            indName.addGiven(individualProvider.getFirstName());
            indName.addPrefix(individualProvider.getNamePrefix());
            indName.addSuffix(individualProvider.getNameSuffix());
            sourcePractitionerResource.addName(indName);
            //setting the address
            sourcePractitionerResource.addAddress().addLine(individualProvider.getFirstLineMailingAddress())
                    .setCity(individualProvider.getMailingAddressCityName())
                    .setState(individualProvider.getMailingAddressStateName())
                    .setPostalCode(individualProvider.getMailingAddressPostalCode());

        });

        return sourcePractitionerResource;
    }

    private Function<PurposeOfUseCode, String> getPurposeOfUseCode = new Function<PurposeOfUseCode, String>() {
         @Override
        public String apply(PurposeOfUseCode pou) {
            String codeString = pou.getCode();
            if (codeString != null && !"".equals(codeString) || codeString != null && !"".equals(codeString)) {
                if ("TREATMENT".equalsIgnoreCase(codeString)) {
                    return  V3ActReason.TREAT.toString();
                } else if ("PAYMENT".equalsIgnoreCase(codeString)) {
                    return  V3ActReason.HPAYMT.toString();
                } else if ("RESEARCH".equalsIgnoreCase(codeString)) {
                    return  V3ActReason.HRESCH.toString();
                } else {
                    throw new IllegalArgumentException("Unknown Purpose of Use code \'" + codeString + "\'");
                }
            } else {
                return "";
            }
        }
    };

    private Consent createGranularConsent(gov.samhsa.c2s.pcm.domain.consent.Consent c2sConsent, PatientDto patientDto) {

        Consent fhirConsent = createBasicConsent(c2sConsent, patientDto);
        // add granular preferences

        // get obligations from consent
        List<String> excludeCodes = getConsentObligations(c2sConsent);

        List<Coding> excludeCodingList = new ArrayList<>();
        List<Coding> includeCodingList = new ArrayList<>();
        // go over full list and add obligation as exclusions
        for (SensitivePolicyCodeEnum codesEnum : SensitivePolicyCodeEnum.values()) {
            if (excludeCodes.contains(codesEnum.getCode())) {
                // exclude it
                 excludeCodingList.add(new Coding(codesEnum.getCodeSystem(), codesEnum.getCode(), codesEnum.getDisplayName()));
              } else {
                // include it
                includeCodingList.add(new Coding(codesEnum.getCodeSystem(), codesEnum.getCode(), codesEnum.getDisplayName()));
             }
        }

        // add list to consent
        Consent.ExceptComponent exceptComponent = new Consent.ExceptComponent();

        if(keepExcludeList.equalsIgnoreCase("true")) {
            //List of Excluded Sensitive policy codes
            exceptComponent.setType(Consent.ConsentExceptType.DENY);
            exceptComponent.setSecurityLabel(excludeCodingList);
        } else {
            //List of included Sensitive policy codes
            exceptComponent.setSecurityLabel(includeCodingList);
            exceptComponent.setType(Consent.ConsentExceptType.PERMIT);
         }
        fhirConsent.setExcept(Arrays.asList(exceptComponent));

        createConsentToLogMessage(fhirConsent, "GranularConsent"+patientDto.getMedicalRecordNumber());
        return fhirConsent;

    }

    private List<String> getConsentObligations(gov.samhsa.c2s.pcm.domain.consent.Consent  consent) {
        final Set<String> obligationCodes = new HashSet<>();

        for (final ConsentDoNotShareClinicalDocumentTypeCode item : consent
                .getDoNotShareClinicalDocumentTypeCodes()) {
            obligationCodes.add(item
                    .getClinicalDocumentTypeCode().getCode());
        }

        for (final ConsentDoNotShareSensitivityPolicyCode item : consent
                .getDoNotShareSensitivityPolicyCodes()) {
            obligationCodes.add(item
                    .getValueSetCategory().getCode());
        }

        for (final ClinicalConceptCode item : consent
                .getDoNotShareClinicalConceptCodes()) {
            obligationCodes.add(item.getCode());
        }
        return new ArrayList<>(obligationCodes);
    }





    private void createConsentToLogMessage(Consent fhirConsent, String currentTest) {
        String xmlEncodedGranularConsent = fhirContext.newXmlParser().setPrettyPrint(true)
                .encodeResourceToString(fhirConsent);
        try {
            FileUtils.writeStringToFile(new File(logOutputPath + "/XML/" + currentTest + ".xml"), xmlEncodedGranularConsent);
            String jsonEncodedGranularConsent = fhirContext.newJsonParser().setPrettyPrint(true)
                    .encodeResourceToString(fhirConsent);
            FileUtils.writeStringToFile(new File(logOutputPath + "/JSON/" + currentTest + ".json"), jsonEncodedGranularConsent);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
