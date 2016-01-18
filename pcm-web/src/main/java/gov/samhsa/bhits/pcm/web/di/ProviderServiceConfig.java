package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.patient.PatientRepository;
import gov.samhsa.bhits.pcm.domain.provider.IndividualProviderRepository;
import gov.samhsa.bhits.pcm.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.bhits.pcm.service.provider.IndividualProviderService;
import gov.samhsa.bhits.pcm.service.provider.IndividualProviderServiceImpl;
import gov.samhsa.bhits.pcm.service.provider.OrganizationalProviderService;
import gov.samhsa.bhits.pcm.service.provider.OrganizationalProviderServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderServiceConfig {

    @Bean
    public IndividualProviderService individualProviderService(IndividualProviderRepository individualProviderRepository,
                                                               ModelMapper modelMapper, PatientRepository patientRepository) {
        return new IndividualProviderServiceImpl(individualProviderRepository,
                modelMapper, patientRepository);
    }

    @Bean
    public OrganizationalProviderService organizationalProviderService(PatientRepository patientRepository, ModelMapper modelMapper,
                                                                       OrganizationalProviderRepository organizationalProviderRepository) {
        return new OrganizationalProviderServiceImpl(patientRepository, modelMapper,
                organizationalProviderRepository);
    }
}
