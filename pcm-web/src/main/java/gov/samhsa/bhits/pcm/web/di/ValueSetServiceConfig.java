package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.consent.ConsentRepository;
import gov.samhsa.bhits.pcm.domain.valueset.MedicalSectionRepository;
import gov.samhsa.bhits.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.bhits.vss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValueSetServiceConfig {

    @Autowired
    private MedicalSectionRepository medicalSectionRepository;

    @Value("${bhits.vss.config.conceptCodeListPageSize}")
    private int conceptCodeListPageSize;

    @Autowired
    private ConsentRepository consentRepository;

    @Autowired
    private ValueSetCategoryRepository valueSetCategoryRepository;

    @Bean
    public MedicalSectionService medicalSectionService() {
        return new MedicalSectionServiceImpl(medicalSectionRepository,
                consentRepository,
                valueSetMgmtHelper());
    }

    @Bean
    public ValueSetMgmtHelper valueSetMgmtHelper() {
        return new ValueSetMgmtHelper(conceptCodeListPageSize);
    }

    @Bean
    public ValueSetCategoryService valueSetCategoryService() {
        return new ValueSetCategoryServiceImpl(valueSetCategoryRepository, consentRepository, valueSetMgmtHelper());
    }
}
