package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.pcm.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.mhc.pcm.service.reference.PurposeOfUseCodeService;
import gov.samhsa.mhc.pcm.service.reference.PurposeOfUseCodeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurposeOfUseCodeServiceConfig {

    @Bean
    public PurposeOfUseCodeService purposeOfUseCodeService(PurposeOfUseCodeRepository purposeOfUseCodeRepository) {
        return new PurposeOfUseCodeServiceImpl(purposeOfUseCodeRepository);
    }
}
