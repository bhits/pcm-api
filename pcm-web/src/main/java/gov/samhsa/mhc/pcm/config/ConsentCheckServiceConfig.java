package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.pcm.domain.consent.ConsentRepository;
import gov.samhsa.mhc.pcm.domain.patient.PatientRepository;
import gov.samhsa.mhc.pcm.service.consent.ConsentCheckService;
import gov.samhsa.mhc.pcm.service.consent.ConsentCheckServiceImpl;
import gov.samhsa.mhc.pcm.service.consent.ConsentHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentCheckServiceConfig {

    @Bean
    public ConsentCheckService consentCheckService(ConsentRepository consentRepository,
                                                   PatientRepository patientRepository,
                                                   ConsentHelper consentHelper) {
        return new ConsentCheckServiceImpl(
                consentRepository,
                patientRepository,
                consentHelper);
    }
}
