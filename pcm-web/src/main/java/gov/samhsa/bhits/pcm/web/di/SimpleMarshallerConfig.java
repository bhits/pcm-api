package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.common.tool.SimpleMarshaller;
import gov.samhsa.bhits.common.tool.SimpleMarshallerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleMarshallerConfig {

    @Bean
    public SimpleMarshaller simpleMarshaller(){
        return new SimpleMarshallerImpl();
    }
}
