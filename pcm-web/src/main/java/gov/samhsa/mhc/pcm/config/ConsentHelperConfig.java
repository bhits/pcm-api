package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.pcm.service.consent.ConsentHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentHelperConfig {

    @Bean
    public ConsentHelper consentHelper(){
        return new ConsentHelper();
    }
}
