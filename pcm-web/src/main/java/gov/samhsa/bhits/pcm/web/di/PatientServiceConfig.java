package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.bhits.pcm.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.bhits.pcm.domain.patient.PatientRepository;
import gov.samhsa.bhits.pcm.service.patient.PatientService;
import gov.samhsa.bhits.pcm.service.patient.PatientServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
public class PatientServiceConfig {

    @Bean
    public PatientService patientService(PatientRepository patientRepository,
                                         PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository,
                                         ModelMapper modelMapper,
                                         EmailSender emailSender) {
        return new PatientServiceImpl(patientRepository,
                patientLegalRepresentativeAssociationRepository,
                modelMapper,
                passwordEncoder(),
                emailSender);
    }

    // Uses SHA-256 with multiple iterations and a random salt value.
    @Bean
    public StandardPasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
}
