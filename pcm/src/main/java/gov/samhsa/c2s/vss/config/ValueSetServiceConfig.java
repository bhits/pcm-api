package gov.samhsa.c2s.vss.config;

import gov.samhsa.c2s.pcm.domain.consent.ConsentRepository;
import gov.samhsa.c2s.pcm.domain.valueset.*;
import gov.samhsa.c2s.vss.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValueSetServiceConfig {

    @Autowired
    private VssProperties vssProperties;

    @Autowired
    private ConsentRepository consentRepository;

    @Autowired
    private ValueSetCategoryRepository valueSetCategoryRepository;

    @Bean
    public ValueSetMgmtHelper valueSetMgmtHelper() {
        return new ValueSetMgmtHelper(vssProperties.getConceptCodeListPageSize());
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