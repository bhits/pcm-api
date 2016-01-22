package gov.samhsa.mhc.pcm.web.di;

import gov.samhsa.mhc.pcm.domain.consent.ConsentPdfGenerator;
import gov.samhsa.mhc.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.mhc.pcm.domain.valueset.MedicalSectionRepository;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.mhc.pcm.infrastructure.ConsentPdfGeneratorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentPdfGeneratorConfig {

    @Bean
    public ConsentPdfGenerator consentPdfGenerator(ValueSetCategoryRepository valueSetCategoryRepository,
                                                   ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository,
                                                   MedicalSectionRepository medicalSectionRepository) {
        return new ConsentPdfGeneratorImpl(valueSetCategoryRepository,
                clinicalDocumentTypeCodeRepository,
                medicalSectionRepository);
    }
}
