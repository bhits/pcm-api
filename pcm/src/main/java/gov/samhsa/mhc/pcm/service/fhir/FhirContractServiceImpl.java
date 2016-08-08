package gov.samhsa.mhc.pcm.service.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.*;
import ca.uhn.fhir.model.dstu2.valueset.ContractTypeCodesEnum;
import ca.uhn.fhir.model.dstu2.valueset.ListModeEnum;
import ca.uhn.fhir.model.dstu2.valueset.ListStatusEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.rest.client.IGenericClient;
import gov.samhsa.mhc.pcm.domain.consent.*;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProvider;
import gov.samhsa.mhc.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.mhc.pcm.domain.reference.ClinicalConceptCode;
import gov.samhsa.mhc.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.mhc.pcm.infrastructure.dto.PatientDto;
import gov.samhsa.mhc.pcm.service.dto.SensitivePolicyCodeEnum;
import gov.samhsa.mhc.pcm.service.dto.SpecificMedicalInfoDto;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by sadhana.chandra on 8/2/2016.
 */
@Service
public class FhirContractServiceImpl implements FhirContractService {
    /**
     * The logger.
     */
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FhirContext fhirContext;

    @Autowired
    private IGenericClient fhirClient;

    @Autowired
    private FhirPatientService fhirPatientService;

    @Autowired
    private UniqueOidProvider uniqueOidProvider;

    @Value("${mhc.pcm.config.pid.domain.id}")
    private String pidSystem;

    @Value("${mhc.pcm.config.npi.system}")
    private String npiSystem;

    @Value("${mhc.pcm.config.npi.label}")
    private String npiLabel;

    @Value("${mhc.pcm.config.pou.system}")
    private String pouSystem;

    @Value("${logging.path}")
    private String logOutputPath;

    @Value("${mhc.pcm.config.hie-connection.fhir.keepExcludeList}")
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


    public void publishFhirContractToHie(Contract fhirContract) {
        //  invoke Contract service
        fhirClient.create().resource(fhirContract).execute();

    }

    public void publishFhirContractToHie(Consent consent, PatientDto patientDto) {
        // createFhirContract(consent, patientDto);
        publishFhirContractToHie(createFhirContract(consent, patientDto));
    }

    @Override
    public Contract createFhirContract(Consent consent, PatientDto patientDto) {
        Contract contract = consentDtoToContract.apply(consent, patientDto);

        return contract;
    }

    BiFunction<Consent, PatientDto, Contract> consentDtoToContract = new BiFunction<Consent, PatientDto, Contract>() {
        @Override
        public Contract apply(Consent consent, PatientDto patientDto) {
            Contract contract = createGranularConsent(consent, patientDto);
            return contract;
        }
    };

    private Contract createGranularConsent(Consent consent, PatientDto patientDto) {

        Contract fhirContract = createBasicConsent(consent, patientDto);
        // add granular preferences
        ListResource incudeListResource = getListResource(INCLUDE_SENSITIVE_CODES,"I",
                "List of included Sensitive policy codes");

        ListResource excludeListResource = getListResource(EXCLUDE_SENSITIVE_CODES,"E",
                "List of Excluded Sensitive policy codes");

        List<String> excludeCodes = getConsentObligations(consent);
/*        Set<String> allSensitiveCodes = Stream.of(SensitivePolicyCodeEnum.values())
                .map(SensitivePolicyCodeEnum::getCode)
                .collect(Collectors.toSet());
        Set<String> includeCodes = allSensitiveCodes.stream()
                .filter(
                        e -> (excludeCodes.stream()
                                .filter(d -> d.equalsIgnoreCase(e))
                                .count()) < 1)
                .collect(Collectors.toSet());*/

        // go over full list and add obligation as exclusions
        for (SensitivePolicyCodeEnum codesEnum : SensitivePolicyCodeEnum.values()) {
            if (excludeCodes.contains(codesEnum.getCode())) {
                // exclude it
                excludeListResource.addEntry(getResourceEntry(codesEnum.getCode(), codesEnum.getDisplayName(), fhirContract));
            } else {
                // include it
                incudeListResource.addEntry(getResourceEntry(codesEnum.getCode(), codesEnum.getDisplayName(), fhirContract));
            }
        }

        // add list to contract
        if(keepExcludeList.equalsIgnoreCase("true")) {
            fhirContract.getTerm().get(0).getSubject().setReference("#" + excludeListResource.getId());
            fhirContract.getContained().getContainedResources().add(excludeListResource);
        } else {
            fhirContract.getTerm().get(0).getSubject().setReference("#" + incudeListResource.getId());
            fhirContract.getContained().getContainedResources().add(incudeListResource);
        }
       //TODO : write log only in debug mode
        createContracttoLogMessage(fhirContract, "GranularConsent");
        return fhirContract;

    }

    private ListResource getListResource(String sensitiveCodesId, String operator, String title) {
        ListResource incudeListResource = new ListResource();
        incudeListResource.setId(new IdDt(sensitiveCodesId));
        incudeListResource.setTitle(title);
        // specifies how the list items are to be used
        CodeableConceptDt includeCodeValue = new CodeableConceptDt(CODE_SYSTEM_SET_OPERATOR, operator);
        includeCodeValue.setText(sensitiveCodesId );
        incudeListResource.setCode(includeCodeValue);
        incudeListResource.setStatus(ListStatusEnum.CURRENT);
        incudeListResource.setMode(ListModeEnum.SNAPSHOT_LIST);
        return incudeListResource;
    }

    private ListResource.Entry getResourceEntry(String sensitivePolicyCode, String description, Contract fhirContract) {
        // add discharge summary document type
        ListResource.Entry resourceSummaryEntry = new ListResource.Entry();
        // use list item flag to specify a category and the item to specify an
        // instance (e.g. DocumentReference)
        CodeableConceptDt codeableConceptDt = new CodeableConceptDt(CONFIDENTIALITY_CODE_CODE_SYSTEM, sensitivePolicyCode);
        // dischargeSummaryCode
        codeableConceptDt.setText(description);
        resourceSummaryEntry.setDeleted(false);
        // dischargeSummaryEntry.setFlag(dischargeSummaryCode);
        Basic basicItem = new Basic();
        basicItem.setId(new IdDt(sensitivePolicyCode));
        basicItem.setCode(codeableConceptDt);

        // add items as Basic resources
        fhirContract.getContained().getContainedResources().add(basicItem);

        ResourceReferenceDt resourceReferenceDt = new ResourceReferenceDt("#" + basicItem.getId());
        resourceSummaryEntry.setItem(resourceReferenceDt);

        return resourceSummaryEntry;
    }


    private Contract createBasicConsent(Consent consent, PatientDto patientDto) {
        Contract contract = new Contract();

        // set the id as a concatenated "OID.consentId"
        contract.setId(new IdDt(consent.getConsentReferenceId()));

        // add local reference to patient
        Patient fhirPatient = fhirPatientService.createFhirPatient(patientDto);
        ResourceReferenceDt patientReference = new ResourceReferenceDt("#" + patientDto.getMedicalRecordNumber());
        contract.getSubject().add(patientReference);
        contract.getSignerFirstRep().setType(new CodingDt("http://hl7.org/fhir/contractsignertypecodes", "1.2.840.10065.1.12.1.7"));
        contract.getSignerFirstRep().setSignature(fhirPatient.getNameFirstRep().getNameAsSingleString());
        contract.getSignerFirstRep().setParty(patientReference);
        //add test patient as a contained resource rather than external reference
        contract.getContained().getContainedResources().add(fhirPatient);

        // Specify Authors
        // specify the authorized to disclose
        Set<OrganizationalProvider> sourceOrgPermittedTo = new HashSet<OrganizationalProvider>();
        for (ConsentOrganizationalProviderPermittedToDisclose orgPermittedTo : consent.getOrganizationalProvidersPermittedToDisclose()) {
            sourceOrgPermittedTo.add(orgPermittedTo.getOrganizationalProvider());
        }
        Organization sourceOrganizationResource = setOrganizationProvider(sourceOrgPermittedTo, SOURCE_ORG_ID);
        contract.getContained().getContainedResources().add(sourceOrganizationResource);
        contract.addAuthority().setReference("#" + sourceOrganizationResource.getId());

        if (null == sourceOrganizationResource) {
            Set<IndividualProvider> sourceindPermittedTo = new HashSet<IndividualProvider>();
            for (ConsentIndividualProviderPermittedToDisclose indPermittedTo : consent.getProvidersPermittedToDisclose()) {
                sourceindPermittedTo.add(indPermittedTo.getIndividualProvider());
            }
            Practitioner sourcePractitioner = setPractitionerProvider(sourceindPermittedTo, SOURCE_IND_ID);
            contract.getTermFirstRep().addActor().getEntity().setReference("#" + sourcePractitioner.getId());
            contract.getContained().getContainedResources().add(sourcePractitioner);
        }

        // specify the covered entity authorized to disclose
        // add source resource and authority reference
        Set<OrganizationalProvider> recipientOrgMadeTo = new HashSet<OrganizationalProvider>();
        for (ConsentOrganizationalProviderDisclosureIsMadeTo orgMadeTo : consent.getOrganizationalProvidersDisclosureIsMadeTo()) {
            recipientOrgMadeTo.add(orgMadeTo.getOrganizationalProvider());
        }
        Organization recipientOrganization = setOrganizationProvider(recipientOrgMadeTo, RECIPIENT_ORG_ID);
        contract.getContained().getContainedResources().add(recipientOrganization);
        contract.addAuthority().setReference("#" + recipientOrganization.getId());

        //This is required if the organization was not already added as a "contained" resource reference by the Patient
        //contract.getContained().getContainedResources().add(sourceOrganizationResource);
        // specify the provider who authored the data
        if (null == recipientOrganization) {
            Set<IndividualProvider> recipientindPermittedTo = new HashSet<IndividualProvider>();
            for (ConsentIndividualProviderDisclosureIsMadeTo indPermittedTo : consent.getProvidersDisclosureIsMadeTo()) {
                recipientindPermittedTo.add(indPermittedTo.getIndividualProvider());
            }
            Practitioner recipientPractitioner = setPractitionerProvider(recipientindPermittedTo, RECIPIENT_IND_ID);
            contract.addActor().getEntity().setReference("#" + recipientPractitioner.getId());
            contract.getContained().getContainedResources().add(recipientPractitioner);
        }


        // set POU
        for (ConsentShareForPurposeOfUseCode pou : consent.getShareForPurposeOfUseCodes())
            contract.getActionReason().add(new CodeableConceptDt(pouSystem, getPurposeOfUseCode.apply(pou.getPurposeOfUseCode())));

        // set terms of consent and intended recipient(s)
        contract.getTermFirstRep().setText("description of the consent terms");
        PeriodDt applicablePeriod = new PeriodDt();
        applicablePeriod.setEnd(new DateTimeDt(consent.getEndDate()));
        applicablePeriod.setStart(new DateTimeDt(consent.getStartDate()));
        contract.getTermFirstRep().setApplies(applicablePeriod);


        // contract.getIdentifier().setSystem(pidSystem).setValue(consent.getConsentReferenceId());
        final String xdsDocumentEntryUniqueId = uniqueOidProvider.getOid();

        contract.getIdentifier().setSystem(pidSystem).setValue(xdsDocumentEntryUniqueId);
        contract.getType().setValueAsEnum(ContractTypeCodesEnum.DISCLOSURE);

        DateTimeDt issuedDateTime = new DateTimeDt();
        issuedDateTime.setValue(Calendar.getInstance().getTime());
        contract.setIssued(issuedDateTime);
        //TODO : write log only in debug mode
        createContracttoLogMessage(contract, "BasicConsent");
        return contract;
    }


    private Organization setOrganizationProvider(Set<OrganizationalProvider> orgProviders, String orgIdName) {
        Organization sourceOrganizationResource = new Organization();

        //Set<OrganizationalProvider> orgProvidersDisclosureIsMadeTo = consentAttestationDto.getOrgProvidersDisclosureIsMadeTo();

        orgProviders.forEach((OrganizationalProvider organizationalProvider) ->
        {
            sourceOrganizationResource.setId(new IdDt(orgIdName));
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

        // Set<IndividualProvider> indProvidersDisclosureIsMadeTo = consentAttestationDto.getIndProvidersDisclosureIsMadeTo();

        individualProviders.forEach((IndividualProvider individualProvider) ->
        {
            sourcePractitionerResource.setId(new IdDt(practIdName));
            sourcePractitionerResource.addIdentifier().setSystem(npiSystem).setValue(individualProvider.getNpi());
            // sourceOrganizationResource.setName(individualProvider.getn());
            sourcePractitionerResource.addAddress().addLine(individualProvider.getFirstLineMailingAddress())
                    .setCity(individualProvider.getMailingAddressCityName())
                    .setState(individualProvider.getMailingAddressStateName())
                    .setPostalCode(individualProvider.getMailingAddressPostalCode());

        });

        return sourcePractitionerResource;
    }


    Function<PurposeOfUseCode, String> getPurposeOfUseCode = new Function<PurposeOfUseCode, String>() {
        //TODO : Replace with FHIR ENUM class once FHIR version migrate to DSTU3.
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

    private void createContracttoLogMessage(Contract contract, String currentTest) {
        String xmlEncodedGranularConsent = fhirContext.newXmlParser().setPrettyPrint(true)
                .encodeResourceToString(contract);
        try {
            FileUtils.writeStringToFile(new File(logOutputPath + "/XML/" + currentTest + ".xml"), xmlEncodedGranularConsent);
            String jsonEncodedGranularConsent = fhirContext.newJsonParser().setPrettyPrint(true)
                    .encodeResourceToString(contract);
            FileUtils.writeStringToFile(new File(logOutputPath + "/JSON/" + currentTest + ".json"), jsonEncodedGranularConsent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getConsentObligations(Consent consent) {
        final Set<String> obligationCodes = new HashSet<String>();

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

        final Set<String> consentDoNotShareClinicalConceptCodes = new HashSet<String>();
        for (final ClinicalConceptCode item : consent
                .getDoNotShareClinicalConceptCodes()) {
            final SpecificMedicalInfoDto specificMedicalInfoDto = new SpecificMedicalInfoDto();
            consentDoNotShareClinicalConceptCodes
                    .add(item.getCode());
        }

        return new ArrayList<String>(obligationCodes);
    }

}
