package gov.samhsa.mhc.vss.web.di;

import gov.samhsa.mhc.pcm.domain.consent.ConsentRepository;
import gov.samhsa.mhc.pcm.domain.valueset.*;
import gov.samhsa.mhc.vss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValueSetServiceConfig {

    @Autowired
    private MedicalSectionRepository medicalSectionRepository;

    @Value("${mhc.vss.config.conceptCodeListPageSize}")
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

    @Bean
    public ValueSetLookupService valueSetLookupService(ConceptCodeRepository conceptCodeRepository,
                                                       ValueSetRepository valueSetRepository,
                                                       CodeSystemRepository codeSystemRepository,
                                                       CodeSystemVersionRepository codeSystemVersionRepository,
                                                       ConceptCodeValueSetRepository conceptCodeValueSetRepository) {
        return new ValueSetLookupServiceImpl(conceptCodeRepository,
                valueSetRepository,
                codeSystemRepository,
                codeSystemVersionRepository,
                conceptCodeValueSetRepository,
                valueSetMgmtHelper());
    }
}
