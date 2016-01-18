package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.service.consent.ConsentHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentHelperConfig {

    @Bean
    public ConsentHelper consentHelper(){
        return new ConsentHelper();
    }
}
