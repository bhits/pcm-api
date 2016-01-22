package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.pcm.infrastructure.AbstractConsentRevokationPdfGenerator;
import gov.samhsa.mhc.pcm.infrastructure.pg.ConsentRevokationPdfGeneratorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentRevokationPdfGeneratorConfig {

    @Bean
    public AbstractConsentRevokationPdfGenerator consentRevokationPdfGenerator(){
        return new ConsentRevokationPdfGeneratorImpl();
    }
}
