package gov.samhsa.mhc.pcm.service.fhir;

import ca.uhn.fhir.model.dstu2.resource.Contract;
import gov.samhsa.mhc.pcm.service.dto.ConsentAttestationDto;
import gov.samhsa.mhc.pcm.service.dto.PatientProfileDto;

/**
 * Created by sadhana.chandra on 8/2/2016.
 */
public interface FhirContractService {
    public Contract createFhirContract(ConsentAttestationDto consentAttestationDto, PatientProfileDto patientProfileDto);
}
