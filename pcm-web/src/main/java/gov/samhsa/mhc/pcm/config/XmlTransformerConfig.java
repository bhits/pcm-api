package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.common.marshaller.SimpleMarshaller;
import gov.samhsa.mhc.common.document.transformer.XmlTransformer;
import gov.samhsa.mhc.common.document.transformer.XmlTransformerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlTransformerConfig {

    @Bean
    public XmlTransformer xmlTransformer(SimpleMarshaller marshaller){
        return new XmlTransformerImpl(marshaller);
    }
}
