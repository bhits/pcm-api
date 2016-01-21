package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.common.document.converter.DocumentXmlConverter;
import gov.samhsa.bhits.common.document.converter.DocumentXmlConverterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentXmlConverterConfig {

    @Bean
    public DocumentXmlConverter documentXmlConverter(){
        return new DocumentXmlConverterImpl();
    }
}
