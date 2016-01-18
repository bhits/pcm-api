package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.common.tool.DocumentXmlConverter;
import gov.samhsa.bhits.common.tool.DocumentXmlConverterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentXmlConverterConfig {

    @Bean
    public DocumentXmlConverter documentXmlConverter(){
        return new DocumentXmlConverterImpl();
    }
}
