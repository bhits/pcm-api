package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.common.marshaller.SimpleMarshaller;
import gov.samhsa.bhits.common.document.transformer.XmlTransformer;
import gov.samhsa.bhits.common.document.transformer.XmlTransformerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlTransformerConfig {

    @Bean
    public XmlTransformer xmlTransformer(SimpleMarshaller marshaller){
        return new XmlTransformerImpl(marshaller);
    }
}
