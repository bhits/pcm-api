package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.consentgen.ConsentDtoFactory;
import gov.samhsa.bhits.pcm.domain.consent.ConsentRepository;
import gov.samhsa.bhits.pcm.service.consentexport.ConsentDtoFactoryImpl;
import gov.samhsa.bhits.pcm.service.consentexport.ConsentExportMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentDtoFactoryConfig {

    @Bean
    public ConsentDtoFactory consentDtoFactory(ConsentRepository consentRepository,
                                               ModelMapper modelMapper,
                                               ConsentExportMapper consentExportMapper) {
        return new ConsentDtoFactoryImpl(consentRepository, modelMapper, consentExportMapper);
    }
}
