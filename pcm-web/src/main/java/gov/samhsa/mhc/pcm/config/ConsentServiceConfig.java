package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.common.document.accessor.DocumentAccessor;
import gov.samhsa.mhc.common.document.converter.DocumentXmlConverter;
import gov.samhsa.mhc.common.document.transformer.XmlTransformer;
import gov.samhsa.mhc.consentgen.ConsentBuilder;
import gov.samhsa.mhc.pcm.domain.consent.ConsentFactory;
import gov.samhsa.mhc.pcm.domain.consent.ConsentPdfGenerator;
import gov.samhsa.mhc.pcm.domain.consent.ConsentRepository;
import gov.samhsa.mhc.pcm.domain.patient.PatientRepository;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProviderRepository;
import gov.samhsa.mhc.pcm.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.mhc.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.mhc.pcm.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.mhc.pcm.domain.valueset.MedicalSectionRepository;
import gov.samhsa.mhc.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.mhc.pcm.infrastructure.AbstractConsentRevokationPdfGenerator;
import gov.samhsa.mhc.pcm.infrastructure.EchoSignSignatureService;
import gov.samhsa.mhc.pcm.service.consent.*;
import gov.samhsa.mhc.pcm.service.consentexport.ConsentExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class ConsentServiceConfig {

    @Value("${mhc.pcm.config.pid.domain.id}")
    private String pidDomainId;

    @Value("${mhc.pcm.config.pid.domain.type}")
    private String pidDomainType;

    @Autowired
    private ConsentRepository consentRepository;

    @Bean
    public ConsentService consentService(ConsentRepository consentRepository,
                                         ConsentPdfGenerator consentPdfGenerator,
                                         PatientRepository patientRepository,
                                         IndividualProviderRepository individualProviderRepository,
                                         OrganizationalProviderRepository organizationalProviderRepository,
                                         ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository,
                                         MedicalSectionRepository medicalSectionRepository,
                                         ValueSetCategoryRepository valueSetCategoryRepository,
                                         PurposeOfUseCodeRepository purposeOfUseCodeRepository,
                                         EchoSignSignatureService echoSignSignatureService,
                                         AbstractConsentRevokationPdfGenerator consentRevokationPdfGenerator,
                                         ConsentExportService consentExportService, ConsentBuilder consentBuilder,
                                         ConsentFactory consentFactory,
                                         ConsentCheckService consentCheckService,
                                         Set<ConsentAssertion> consentAssertions,
                                         XmlTransformer xmlTransformer,
                                         DocumentXmlConverter documentXmlConverter,
                                         DocumentAccessor documentAccessor) {
        return new ConsentServiceImpl(pidDomainId,
                pidDomainType,
                consentRepository,
                consentPdfGenerator,
                patientRepository,
                individualProviderRepository,
                organizationalProviderRepository,
                clinicalDocumentTypeCodeRepository,
                medicalSectionRepository,
                valueSetCategoryRepository,
                purposeOfUseCodeRepository,
                echoSignSignatureService,
                consentRevokationPdfGenerator,
                consentExportService, consentBuilder,
                consentFactory,
                consentCheckService,
                consentAssertions,
                policyIdService(), xmlTransformer,
                documentXmlConverter,
                documentAccessor);
    }

    @Bean
    public PolicyIdService policyIdService() {
        return new PolicyIdServiceImpl(pidDomainId, pidDomainType, consentRepository);
    }
}
