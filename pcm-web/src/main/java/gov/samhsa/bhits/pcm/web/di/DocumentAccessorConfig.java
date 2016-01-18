package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.common.tool.DocumentAccessor;
import gov.samhsa.bhits.common.tool.DocumentAccessorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentAccessorConfig {

    @Bean
    public DocumentAccessor docmentAccessor(){
        return new DocumentAccessorImpl();
    }
}
