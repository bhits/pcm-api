package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.clinicaldata.ClinicalDocumentRepository;
import gov.samhsa.bhits.pcm.domain.patient.PatientRepository;
import gov.samhsa.bhits.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.bhits.pcm.service.clinicaldata.ClinicalDocumentService;
import gov.samhsa.bhits.pcm.service.clinicaldata.ClinicalDocumentServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

@Configuration
public class ClinicalDocumentServiceConfig {

    @Value("${bhits.pcm.config.clinicaldata.maximumUploadFileSize}")
    private long maximumUploadFileSize;

    @Value("${bhits.pcm.config.clinicaldata.extensionsPermittedToUpload}")
    private String extensionsPermittedToUpload;

    @Bean
    public ClinicalDocumentService clinicalDocumentService(ClinicalDocumentRepository clinicalDocumentRepository,
                                                           ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository,
                                                           PatientRepository patientRepository,
                                                           ModelMapper modelMapper, Validator validator) {
        return new ClinicalDocumentServiceImpl(maximumUploadFileSize,
                extensionsPermittedToUpload,
                clinicalDocumentRepository,
                clinicalDocumentTypeCodeRepository,
                modelMapper, patientRepository, validator);
    }
}
