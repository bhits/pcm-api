package gov.samhsa.mhc.pcm.service.fhir;


import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.dstu2.valueset.ContactPointSystemEnum;
import ca.uhn.fhir.model.dstu2.valueset.IdentifierUseEnum;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.primitive.IdDt;
import gov.samhsa.mhc.pcm.service.dto.PatientProfileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FhirPatientServiceImpl implements FhirPatientService {

    @Value("${mhc.pcm.config.pid.domain.id}")
    private String pidSystem="1.3.6.1.4.1.21367.13.20.200";

    @Value("${mhc.pcm.config.ssn.system}")
    private String ssnSystem = "http://hl7.org/fhir/v2/0203";

    @Value("${mhc.pcm.config.ssn.label}")
    private String ssnLabel = "SSN";



    @Override
    public Patient createFhirPatient(PatientProfileDto patientProfileDto) {
        Patient patient = patientProfileDtoToPatient.apply(patientProfileDto);
        return patient;
    }

    Function<PatientProfileDto, Patient> patientProfileDtoToPatient = new Function<PatientProfileDto, Patient>() {
        @Override
        public Patient apply(PatientProfileDto patientProfileDto) {
            // set patient information
            Patient fhirPatient = new Patient();

            //setting mandatory fields
            fhirPatient.setId(new IdDt(patientProfileDto.getMedicalRecordNumber()));
            fhirPatient.addName().addFamily(patientProfileDto.getLastName()).addGiven(patientProfileDto.getFirstName());
            fhirPatient.addTelecom().setValue(patientProfileDto.getEmail()).setSystem(ContactPointSystemEnum.EMAIL);
            fhirPatient.setBirthDate(new DateDt(patientProfileDto.getBirthDate()));
            fhirPatient.setGender(getPatientGender.apply(patientProfileDto.getAdministrativeGenderCode()));
            fhirPatient.setActive(true);

            //Add an Identifier
            setIdentifiers(fhirPatient, patientProfileDto);

            //optional fields
            fhirPatient.addAddress().addLine(patientProfileDto.getAddressStreetAddressLine()).setCity(patientProfileDto.getAddressCity()).setState(patientProfileDto.getAddressStateCode()).setPostalCode(patientProfileDto.getAddressPostalCode());
            return fhirPatient;
        }
    };


    private void setIdentifiers(Patient patient, PatientProfileDto patientProfileDto) {

        //setting patient mrn
        patient.addIdentifier().setSystem(pidSystem)
                .setUse(IdentifierUseEnum.OFFICIAL).setValue(patientProfileDto.getMedicalRecordNumber());

        // setting ssn value
        if(null != patientProfileDto.getSocialSecurityNumber() && ! patientProfileDto.getSocialSecurityNumber().isEmpty())
            patient.addIdentifier().setSystem(ssnSystem)
                    .setValue(patientProfileDto.getSocialSecurityNumber()).setSystem(ssnLabel);

        if(null != patientProfileDto.getTelephoneTelephone() && ! patientProfileDto.getTelephoneTelephone().isEmpty())
            patient.addTelecom().setValue(patientProfileDto.getTelephoneTelephone()).setSystem(ContactPointSystemEnum.PHONE);



    }

    Function<String, AdministrativeGenderEnum> getPatientGender = new Function<String, AdministrativeGenderEnum>() {
        @Override
        public AdministrativeGenderEnum apply(String codeString) {
            if (codeString != null && !"".equals(codeString) || codeString != null && !"".equals(codeString)) {
                if ("male".equalsIgnoreCase(codeString) || "M".equalsIgnoreCase(codeString)) {
                    return AdministrativeGenderEnum.MALE;
                } else if ("female".equalsIgnoreCase(codeString) || "F".equalsIgnoreCase(codeString)) {
                    return AdministrativeGenderEnum.FEMALE;
                } else if ("other".equalsIgnoreCase(codeString) || "O".equalsIgnoreCase(codeString)) {
                    return AdministrativeGenderEnum.OTHER;
                } else if ("unknown".equalsIgnoreCase(codeString) || "UN".equalsIgnoreCase(codeString)) {
                    return AdministrativeGenderEnum.UNKNOWN;
                } else {
                    throw new IllegalArgumentException("Unknown AdministrativeGender code \'" + codeString + "\'");
                }
            } else {
                return null;
            }
        }
    };


}
