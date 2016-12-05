package gov.samhsa.c2s.pcm.service.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.IGenericClient;
import gov.samhsa.c2s.pcm.domain.consent.*;
import gov.samhsa.c2s.pcm.domain.provider.IndividualProvider;
import gov.samhsa.c2s.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.c2s.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.c2s.pcm.infrastructure.dto.PatientDto;
import org.apache.commons.io.FileUtils;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Consent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by Sadhana.chandra on 12/2/2016.
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
    private static String CONFIDENTIALITY_CODE_CODE_SYSTEM = "urn:oid:2.16.840.1.113883.5.25";
    private static String CODE_SYSTEM_SET_OPERATOR = "http://hl7.org/fhir/v3/SetOperator";
    private static String SOURCE_ORG_ID = "SOURCE_ORG_ID";
    private static String SOURCE_IND_ID = "SOURCE_IND_ID";
    private static String RECIPIENT_ORG_ID = "RECIPIENT_ORG_ID";
    private static String RECIPIENT_IND_ID = "RECIPIENT_IND_ID";
    private static String INCLUDE_SENSITIVE_CODES = "INCLUDE_SENSITIVE_CODES";
    private static String EXCLUDE_SENSITIVE_CODES = "EXClUDE_SENSITIVE_CODES";

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
        return createBasicConsent(consent, patientDto);
    }


    private Consent createBasicConsent(gov.samhsa.c2s.pcm.domain.consent.Consent c2sConsent, PatientDto patientDto) {

        Consent fhirConsent = new Consent();

        // set the id as a concatenated "OID.consentId"
        fhirConsent.setId(new IdType(c2sConsent.getConsentReferenceId()));

        // Set patient reference and add patient as contained resource
        Patient fhirPatient = fhirPatientService.createFhirPatient(patientDto);
        fhirConsent.getPatient().setReference("#" + patientDto.getMedicalRecordNumber());
        fhirConsent.getContained().add(fhirPatient);

        // Consent signature details
        Reference consentor = fhirConsent.getConsentor().get(0);
        consentor.setDisplay(fhirPatient.getNameFirstRep().getNameAsSingleString());
        fhirConsent.getConsentor().add(consentor);

        // consent status
        fhirConsent.setStatus(Consent.ConsentStatus.ACTIVE);

        // Specify Authors, the providers authorizes to disclose
        // Author :: Organizational Provider
        Set<OrganizationalProvider> sourceOrgPermittedTo = new HashSet<>();
        for (ConsentOrganizationalProviderPermittedToDisclose orgPermittedTo : c2sConsent.getOrganizationalProvidersPermittedToDisclose()) {
            sourceOrgPermittedTo.add(orgPermittedTo.getOrganizationalProvider());
        }
        Organization sourceOrganizationResource = setOrganizationProvider(sourceOrgPermittedTo, SOURCE_ORG_ID);

        if (null != sourceOrganizationResource) {
            fhirConsent.getContained().add(sourceOrganizationResource);
            fhirConsent.getOrganization().setReference("#" + sourceOrganizationResource.getId());
        } else {
            //// Author :: Individual Provider
            Set<IndividualProvider> sourceindPermittedTo = new HashSet<>();
            for (ConsentIndividualProviderPermittedToDisclose indPermittedTo : c2sConsent.getProvidersPermittedToDisclose()) {
                sourceindPermittedTo.add(indPermittedTo.getIndividualProvider());
            }
            Practitioner sourcePractitioner = setPractitionerProvider(sourceindPermittedTo, SOURCE_IND_ID);

            fhirConsent.getContained().add(sourcePractitioner);
            fhirConsent.getOrganization().setReference("#" + sourcePractitioner.getId());
        }

        // Specify Recipients, the providers disclosure is made to
        // Recipient :: Organizational Provider
        Set<OrganizationalProvider> recipientOrgMadeTo = new HashSet<>();
        for (ConsentOrganizationalProviderDisclosureIsMadeTo orgMadeTo : c2sConsent.getOrganizationalProvidersDisclosureIsMadeTo()) {
            recipientOrgMadeTo.add(orgMadeTo.getOrganizationalProvider());
        }
        Organization recipientOrganization = setOrganizationProvider(recipientOrgMadeTo, RECIPIENT_ORG_ID);
        if (null != recipientOrganization) {
            fhirConsent.getContained().add(recipientOrganization);
            fhirConsent.getRecipient().get(0).setReference("#" + recipientOrganization.getId());
        } else {
            // Recipient :: Individual Provider
            Set<IndividualProvider> recipientindPermittedTo = new HashSet<>();
            for (ConsentIndividualProviderDisclosureIsMadeTo indPermittedTo : c2sConsent.getProvidersDisclosureIsMadeTo()) {
                recipientindPermittedTo.add(indPermittedTo.getIndividualProvider());
            }
            Practitioner recipientPractitioner = setPractitionerProvider(recipientindPermittedTo, RECIPIENT_IND_ID);

            fhirConsent.getContained().add(recipientPractitioner);
            fhirConsent.getRecipient().get(0).setReference("#" + recipientPractitioner.getId());
        }


        // set POU
        for (ConsentShareForPurposeOfUseCode pou : c2sConsent.getShareForPurposeOfUseCodes()) {
            String fhirPou = getPurposeOfUseCode.apply(pou.getPurposeOfUseCode());
            Coding coding = new Coding(pouSystem, fhirPou, fhirPou);
            fhirConsent.getPurpose().add(coding);
        }

        /* set terms of consent and intended recipient(s) */
        fhirConsent.getPeriod().setStart(c2sConsent.getStartDate());
        fhirConsent.getPeriod().setEnd(c2sConsent.getEndDate());
        // consent sign time
        fhirConsent.getDateTime().setTime(Calendar.getInstance().getTimeInMillis());

        // final String xdsDocumentEntryUniqueId = uniqueOidProvider.getOid();
        // fhirConsent.getIdentifier().setSystem(pidSystem).setValue(xdsDocumentEntryUniqueId);
        // set identifier for this consent
        fhirConsent.getIdentifier().setSystem(pidSystem).setValue(c2sConsent.getConsentReferenceId());

        //set category
        //fhirConsent.getType().setValueAsEnum(ContractTypeCodesEnum.DISCLOSURE);


        //TODO (#20): write log only in debug mode
        createConsentToLogMessage(fhirConsent, "BasicConsent" + fhirConsent.getId());
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
            sourcePractitionerResource.addAddress().addLine(individualProvider.getFirstLineMailingAddress())
                    .setCity(individualProvider.getMailingAddressCityName())
                    .setState(individualProvider.getMailingAddressStateName())
                    .setPostalCode(individualProvider.getMailingAddressPostalCode());

        });

        return sourcePractitionerResource;
    }

    private Function<PurposeOfUseCode, String> getPurposeOfUseCode = new Function<PurposeOfUseCode, String>() {
        //TODO (#21): Replace with FHIR ENUM class once FHIR version migrate to DSTU3.
        @Override
        public String apply(PurposeOfUseCode pou) {
            String codeString = pou.getCode();
            if (codeString != null && !"".equals(codeString) || codeString != null && !"".equals(codeString)) {
                if ("TREATMENT".equalsIgnoreCase(codeString)) {
                    return "TREAT";
                } else if ("PAYMENT".equalsIgnoreCase(codeString)) {
                    return "HPAYMT";
                } else if ("RESEARCH".equalsIgnoreCase(codeString)) {
                    return "HRESCH";
                } else {
                    throw new IllegalArgumentException("Unknown Purpose of Use code \'" + codeString + "\'");
                }
            } else {
                return "";
            }
        }
    };

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
