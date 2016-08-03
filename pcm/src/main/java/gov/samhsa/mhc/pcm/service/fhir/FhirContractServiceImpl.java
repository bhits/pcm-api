package gov.samhsa.mhc.pcm.service.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Contract;
import ca.uhn.fhir.model.dstu2.resource.Organization;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.resource.Practitioner;
import ca.uhn.fhir.model.dstu2.valueset.ContractTypeCodesEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.IdDt;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProvider;
import gov.samhsa.mhc.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.mhc.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.mhc.pcm.service.dto.ConsentAttestationDto;
import gov.samhsa.mhc.pcm.service.dto.PatientProfileDto;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Created by sadhana.chandra on 8/2/2016.
 */
@Service
public class FhirContractServiceImpl implements  FhirContractService {
    /**
     * The logger.
     */
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FhirContext fhirContext;

    @Autowired
    private FhirPatientService fhirPatientService;

    @Value("${mhc.pcm.config.pid.domain.id}")
    private String pidSystem="1.3.6.1.4.1.21367.13.20.200";

    @Value("${mhc.pcm.config.npi.system}")
    private String npiSystem = "http://hl7.org/fhir/sid/us-npi";

    @Value("${mhc.pcm.config.npi.label}")
    private String npiLabel = "PRN";

    @Value("${mhc.pcm.config.pou.system}")
    private String pouSystem = "http://hl7.org/fhir/ValueSet/v3-PurposeOfUse";

    // FHIR resource identifiers for inline/embedded objects
    private static String consentId = "consentId";
    private static String sourceOrganizationId = "sourceOrgOID";
    private static String sourcePractitionerId = "sourcePractitionerNPI";
    private static String recipientPractitionerId = "recipientPractitionerNPI";
    private static String recipientOrganizationId = "recipientOrganizationId";

    @Override
    public Contract createFhirContract(ConsentAttestationDto consentAttestationDto,PatientProfileDto patientProfileDto) {
        Contract contract = consentDtoToContract.apply(consentAttestationDto,patientProfileDto);

        return contract;
    }

    BiFunction<ConsentAttestationDto, PatientProfileDto, Contract> consentDtoToContract = new BiFunction<ConsentAttestationDto, PatientProfileDto, Contract>() {
        @Override
        public Contract apply(ConsentAttestationDto consentAttestationDto, PatientProfileDto patientProfileDto) {
            Contract contract = createBasicConsent(consentAttestationDto, patientProfileDto);
            return contract;
        }
    };
    private Contract createBasicConsent(ConsentAttestationDto consentAttestationDto, PatientProfileDto patientProfileDto ){
        Contract contract = new Contract();

        // set the id as a concatenated "OID.consentId"
        contract.setId(new IdDt(consentAttestationDto.getConsentReferenceNumber()));

        // add local reference to patient
        Patient fhirPatient = fhirPatientService.createFhirPatient(patientProfileDto);
        ResourceReferenceDt patientReference = new ResourceReferenceDt("#" + patientProfileDto.getMedicalRecordNumber());
        contract.getSubject().add(patientReference);
        contract.getSignerFirstRep().setType(new CodingDt("http://hl7.org/fhir/contractsignertypecodes","1.2.840.10065.1.12.1.7"));
        contract.getSignerFirstRep().setSignature(fhirPatient.getNameFirstRep().getNameAsSingleString());
        contract.getSignerFirstRep().setParty(patientReference);
        //add test patient as a contained resource rather than external reference
        contract.getContained().getContainedResources().add(fhirPatient);


        // specify the covered entity authorized to disclose
        // add source resource and authority reference
        Organization sourceOrganizationResource = setOrganizationProvider(consentAttestationDto.getOrgProvidersDisclosureIsMadeTo());
        sourceOrganizationResource.addIdentifier().setValue("Source Organization Details");
        contract.getContained().getContainedResources().add(sourceOrganizationResource);
        contract.addAuthority().setReference("#" + sourceOrganizationResource.getId());

        //This is required if the organization was not already added as a "contained" resource reference by the Patient
        //contract.getContained().getContainedResources().add(sourceOrganizationResource);
        // specify the provider who authored the data
        if(null == sourceOrganizationResource) {
            Practitioner sourcePractitioner = setPractitionerProvider(consentAttestationDto.getIndProvidersDisclosureIsMadeTo());
            contract.addActor().getEntity().setReference("#" + sourcePractitioner.getId());
            contract.getContained().getContainedResources().add(sourcePractitioner);
        }


        // list all recipients
        Organization recipientOrganization = setOrganizationProvider(consentAttestationDto.getOrgProvidersPermittedToDisclosure());
        contract.getContained().getContainedResources().add(recipientOrganization);
        contract.addAuthority().setReference("#" + recipientOrganization.getId());

        if(null == recipientOrganization) {
            Practitioner recipientPractitioner = setPractitionerProvider(consentAttestationDto.getIndProvidersPermittedToDisclosure());
            contract.getTermFirstRep().addActor().getEntity().setReference("#" + recipientPractitioner.getId() );
            contract.getContained().getContainedResources().add(recipientPractitioner);
        }

        // set POU
       for(PurposeOfUseCode pou: consentAttestationDto.getPurposeOfUseCodes())
           contract.getActionReason().add(new CodeableConceptDt(pouSystem, pou.getCode()));

        // set terms of consent and intended recipient(s)
        contract.getTermFirstRep().setText("description of the consent terms");
        PeriodDt applicablePeriod = new PeriodDt();
        applicablePeriod.setEnd(new DateTimeDt(consentAttestationDto.getConsentEnd()));
        applicablePeriod.setStart(new DateTimeDt(consentAttestationDto.getConsentStart()));
        contract.getTermFirstRep().setApplies(applicablePeriod);


        contract.getIdentifier().setSystem(pidSystem).setValue(consentAttestationDto.getConsentReferenceNumber());
        contract.getType().setValueAsEnum(ContractTypeCodesEnum.DISCLOSURE);

        DateTimeDt issuedDateTime = new DateTimeDt();
        issuedDateTime.setValue(Calendar.getInstance().getTime());
        contract.setIssued(new DateTimeDt(consentAttestationDto.getConsentTermsVersions().getAddedDateTime()));


        //TODO : write log only in debug mode
        createContracttoLogMessage(contract);

        return contract;
    }



    private Organization setOrganizationProvider(Set<OrganizationalProvider> orgProviders) {
        Organization sourceOrganizationResource = new Organization();

        //Set<OrganizationalProvider> orgProvidersDisclosureIsMadeTo = consentAttestationDto.getOrgProvidersDisclosureIsMadeTo();

        orgProviders.forEach((OrganizationalProvider organizationalProvider) ->
        {
            sourceOrganizationResource.setId(new IdDt(organizationalProvider.getId()));
            sourceOrganizationResource.addIdentifier().setSystem(npiSystem).setValue(organizationalProvider.getNpi());
            sourceOrganizationResource.setName(organizationalProvider.getOrgName());
            sourceOrganizationResource.addAddress().addLine(organizationalProvider.getFirstLineMailingAddress())
                    .setCity(organizationalProvider.getMailingAddressCityName())
                    .setState(organizationalProvider.getMailingAddressStateName())
                    .setPostalCode(organizationalProvider.getMailingAddressPostalCode());
        });
        return sourceOrganizationResource;
    }

    private Practitioner setPractitionerProvider( Set<IndividualProvider> individualProviders) {
        Practitioner sourcePractitionerResource = new Practitioner();

        // Set<IndividualProvider> indProvidersDisclosureIsMadeTo = consentAttestationDto.getIndProvidersDisclosureIsMadeTo();

        individualProviders.forEach((IndividualProvider individualProvider) ->
        {
            sourcePractitionerResource.setId(new IdDt(individualProvider.getId()));
            sourcePractitionerResource.addIdentifier().setSystem(npiSystem).setValue(individualProvider.getNpi());
            // sourceOrganizationResource.setName(individualProvider.getn());
            sourcePractitionerResource.addAddress().addLine(individualProvider.getFirstLineMailingAddress())
                    .setCity(individualProvider.getMailingAddressCityName())
                    .setState(individualProvider.getMailingAddressStateName())
                    .setPostalCode(individualProvider.getMailingAddressPostalCode());

        });

        return sourcePractitionerResource;
    }


    private void createContracttoLogMessage( Contract contract) {
        String testResourcesPath = "src/test/resources/";
        String currentTest = "BasicConsent";
        String xmlEncodedGranularConsent = fhirContext.newXmlParser().setPrettyPrint(true)
                .encodeResourceToString(contract);
        try {
            FileUtils.writeStringToFile(new File(testResourcesPath + "/XML/" + currentTest + ".xml"), xmlEncodedGranularConsent);
            String jsonEncodedGranularConsent = fhirContext.newJsonParser().setPrettyPrint(true)
                    .encodeResourceToString(contract);
            FileUtils.writeStringToFile(new File(testResourcesPath + "/JSON/" + currentTest + ".json"), jsonEncodedGranularConsent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
