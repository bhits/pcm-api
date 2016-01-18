package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.consent.ConsentPdfGenerator;
import gov.samhsa.bhits.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.bhits.pcm.domain.valueset.MedicalSectionRepository;
import gov.samhsa.bhits.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.bhits.pcm.infrastructure.ConsentPdfGeneratorImpl;
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
