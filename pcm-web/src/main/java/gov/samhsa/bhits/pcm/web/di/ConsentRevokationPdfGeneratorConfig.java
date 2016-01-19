package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.infrastructure.AbstractConsentRevokationPdfGenerator;
import gov.samhsa.bhits.pcm.infrastructure.pg.ConsentRevokationPdfGeneratorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentRevokationPdfGeneratorConfig {

    @Bean
    public AbstractConsentRevokationPdfGenerator consentRevokationPdfGenerator(){
        return new ConsentRevokationPdfGeneratorImpl();
    }
}
