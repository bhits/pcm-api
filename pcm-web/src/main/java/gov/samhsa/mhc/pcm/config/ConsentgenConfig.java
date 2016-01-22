package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.common.document.transformer.XmlTransformer;
import gov.samhsa.mhc.consentgen.ConsentBuilder;
import gov.samhsa.mhc.consentgen.ConsentBuilderImpl;
import gov.samhsa.mhc.consentgen.ConsentDtoFactory;
import gov.samhsa.mhc.consentgen.XacmlXslUrlProvider;
import gov.samhsa.mhc.consentgen.pg.XacmlXslUrlProviderImpl;
import gov.samhsa.mhc.pcm.domain.consent.ConsentRepository;
import gov.samhsa.mhc.pcm.service.consentexport.ConsentDtoFactoryImpl;
import gov.samhsa.mhc.pcm.service.consentexport.ConsentExportMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentgenConfig {

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

    @Bean
    public ConsentDtoFactory consentDtoFactory(ConsentRepository consentRepository,
                                               ModelMapper modelMapper,
                                               ConsentExportMapper consentExportMapper) {
        return new ConsentDtoFactoryImpl(consentRepository, modelMapper, consentExportMapper);
    }
}
