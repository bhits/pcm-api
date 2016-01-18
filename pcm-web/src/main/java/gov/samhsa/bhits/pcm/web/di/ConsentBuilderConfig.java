package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.common.tool.XmlTransformer;
import gov.samhsa.bhits.consentgen.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentBuilderConfig {

    @Value("${bhits.pcm.config.pid.org}")
    private String pcmOrg;

    @Bean
    public ConsentBuilder consentBuilder(ConsentDtoFactory consentDtoFactory,
                                         XmlTransformer xmlTransformer) {
        return new ConsentBuilderImpl(pcmOrg,
                xacmlXslUrlProvider(),
                consentDtoFactory,
                xmlTransformer);
    }

    @Bean
    public XacmlXslUrlProvider xacmlXslUrlProvider() {
        return new XacmlXslUrlProviderImpl();
    }
}
