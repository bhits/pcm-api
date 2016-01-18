package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.bhits.pcm.service.reference.PurposeOfUseCodeService;
import gov.samhsa.bhits.pcm.service.reference.PurposeOfUseCodeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurposeOfUseCodeServiceConfig {

    @Bean
    public PurposeOfUseCodeService purposeOfUseCodeService(PurposeOfUseCodeRepository purposeOfUseCodeRepository) {
        return new PurposeOfUseCodeServiceImpl(purposeOfUseCodeRepository);
    }
}
