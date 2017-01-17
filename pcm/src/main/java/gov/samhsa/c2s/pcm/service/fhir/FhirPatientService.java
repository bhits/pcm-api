package gov.samhsa.c2s.pcm.service.fhir;


import gov.samhsa.c2s.pcm.infrastructure.dto.PatientDto;
import org.hl7.fhir.dstu3.model.Patient;

public interface FhirPatientService {

  /* converts patientdto to fhir patient object */
  public Patient createFhirPatient(PatientDto patientDto);

}
