package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.common.document.accessor.DocumentAccessor;
import gov.samhsa.mhc.common.document.accessor.DocumentAccessorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentAccessorConfig {

    @Bean
    public DocumentAccessor docmentAccessor(){
        return new DocumentAccessorImpl();
    }
}
