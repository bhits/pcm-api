package gov.samhsa.mhc.pcm.service.fhir;

import ca.uhn.fhir.model.dstu2.resource.Patient;
import gov.samhsa.mhc.pcm.service.dto.PatientProfileDto;

/**
 * Created by sadhana.chandra on 8/2/2016.
 */
public interface FhirPatientService {

  public Patient createFhirPatient(PatientProfileDto patientProfileDto);

}
