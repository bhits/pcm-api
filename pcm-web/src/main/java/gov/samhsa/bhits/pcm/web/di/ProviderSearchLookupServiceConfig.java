package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.reference.StateCodeRepository;
import gov.samhsa.bhits.pcm.service.provider.*;
import gov.samhsa.bhits.pcm.service.provider.pg.ProviderSearchLookupServiceImpl;
import gov.samhsa.bhits.pcm.service.reference.pg.StateCodeServicePg;
import gov.samhsa.bhits.pcm.service.reference.pg.StateCodeServicePgImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderSearchLookupServiceConfig {

    @Value("${bhits.pcm.config.pls.api}")
    private String providerSearchURL;

    @Autowired
    private StateCodeRepository stateCodeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Bean
    public ProviderSearchLookupService providerSearchLookupService(IndividualProviderService individualProviderService,
                                                                   OrganizationalProviderService organizationalProviderService) {
        return new ProviderSearchLookupServiceImpl(providerSearchURL,
                stateCodeService(),
                individualProviderService,
                organizationalProviderService,
                hashMapResultToProviderDtoConverter());
    }

    @Bean
    public StateCodeServicePg stateCodeService() {
        return new StateCodeServicePgImpl(stateCodeRepository, modelMapper);
    }

    @Bean
    public HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter() {
        return new HashMapResultToProviderDtoConverter();
    }
}
