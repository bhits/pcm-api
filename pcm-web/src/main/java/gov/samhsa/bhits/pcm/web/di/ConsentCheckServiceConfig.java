package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.consent.ConsentRepository;
import gov.samhsa.bhits.pcm.domain.patient.PatientRepository;
import gov.samhsa.bhits.pcm.service.consent.ConsentCheckService;
import gov.samhsa.bhits.pcm.service.consent.ConsentCheckServiceImpl;
import gov.samhsa.bhits.pcm.service.consent.ConsentHelper;
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
