package gov.samhsa.mhc.pcm.web.di;

import gov.samhsa.mhc.pcm.domain.consent.ConsentFactory;
import gov.samhsa.mhc.pcm.domain.consent.ConsentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentFactoryConfig {

    @Bean
    public ConsentFactory consentFactory(ConsentRepository consentRepository) {
        return new ConsentFactory(consentRepository);
    }
}
