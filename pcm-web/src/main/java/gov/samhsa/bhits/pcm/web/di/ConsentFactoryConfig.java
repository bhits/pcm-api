package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.consent.ConsentFactory;
import gov.samhsa.bhits.pcm.domain.consent.ConsentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentFactoryConfig {

    @Bean
    public ConsentFactory consentFactory(ConsentRepository consentRepository) {
        return new ConsentFactory(consentRepository);
    }
}
