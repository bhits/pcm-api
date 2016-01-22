package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.mhc.pcm.service.reference.ClinicalDocumentTypeCodeService;
import gov.samhsa.mhc.pcm.service.reference.ClinicalDocumentTypeCodeServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClinicalDocumentTypeCodeServiceConfig {

    @Bean
    public ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService(ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository,
                                                                           ModelMapper modelMapper) {
        return new ClinicalDocumentTypeCodeServiceImpl(clinicalDocumentTypeCodeRepository,
                modelMapper);
    }
}
