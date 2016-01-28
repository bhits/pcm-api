package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.common.document.accessor.DocumentAccessor;
import gov.samhsa.mhc.common.document.accessor.DocumentAccessorImpl;
import gov.samhsa.mhc.common.document.converter.DocumentXmlConverter;
import gov.samhsa.mhc.common.document.converter.DocumentXmlConverterImpl;
import gov.samhsa.mhc.common.document.transformer.XmlTransformer;
import gov.samhsa.mhc.common.document.transformer.XmlTransformerImpl;
import gov.samhsa.mhc.common.marshaller.SimpleMarshaller;
import gov.samhsa.mhc.common.marshaller.SimpleMarshallerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonLibraryConfig {

    @Bean
    public DocumentAccessor docmentAccessor(){
        return new DocumentAccessorImpl();
    }

    @Bean
    public DocumentXmlConverter documentXmlConverter(){
        return new DocumentXmlConverterImpl();
    }

    @Bean
    public SimpleMarshaller simpleMarshaller(){
        return new SimpleMarshallerImpl();
    }

    @Bean
    public XmlTransformer xmlTransformer(){
        return new XmlTransformerImpl(simpleMarshaller());
    }
}
