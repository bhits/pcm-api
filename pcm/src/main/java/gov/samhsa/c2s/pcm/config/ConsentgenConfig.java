package gov.samhsa.c2s.pcm.config;

import gov.samhsa.c2s.pcm.domain.consent.ConsentRepository;
import gov.samhsa.c2s.common.document.transformer.XmlTransformer;
import gov.samhsa.c2s.common.consentgen.ConsentBuilder;
import gov.samhsa.c2s.common.consentgen.ConsentBuilderImpl;
import gov.samhsa.c2s.common.consentgen.ConsentDtoFactory;
import gov.samhsa.c2s.common.consentgen.XacmlXslUrlProvider;
import gov.samhsa.c2s.common.consentgen.pg.XacmlXslUrlProviderImpl;
import gov.samhsa.c2s.pcm.service.consentexport.ConsentDtoFactoryImpl;
import gov.samhsa.c2s.pcm.service.consentexport.ConsentExportMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentgenConfig {

    @Autowired
    private PcmProperties pcmProperties;

    @Bean
    public ConsentBuilder consentBuilder(ConsentDtoFactory consentDtoFactory,
                                         XmlTransformer xmlTransformer) {
        return new ConsentBuilderImpl(pcmProperties.getPid().getOrg(),
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
