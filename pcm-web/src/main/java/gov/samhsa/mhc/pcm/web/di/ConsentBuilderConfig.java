package gov.samhsa.mhc.pcm.web.di;

import gov.samhsa.mhc.common.document.transformer.XmlTransformer;
import gov.samhsa.mhc.consentgen.ConsentBuilder;
import gov.samhsa.mhc.consentgen.ConsentBuilderImpl;
import gov.samhsa.mhc.consentgen.ConsentDtoFactory;
import gov.samhsa.mhc.consentgen.XacmlXslUrlProvider;
import gov.samhsa.mhc.consentgen.pg.XacmlXslUrlProviderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentBuilderConfig {

    @Value("${mhc.pcm.config.pid.org}")
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
