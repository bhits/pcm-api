package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.common.tool.DocumentAccessor;
import gov.samhsa.bhits.common.tool.DocumentXmlConverter;
import gov.samhsa.bhits.common.tool.XmlTransformer;
import gov.samhsa.bhits.consentgen.ConsentBuilder;
import gov.samhsa.bhits.pcm.domain.consent.ConsentFactory;
import gov.samhsa.bhits.pcm.domain.consent.ConsentPdfGenerator;
import gov.samhsa.bhits.pcm.domain.consent.ConsentRepository;
import gov.samhsa.bhits.pcm.domain.patient.PatientRepository;
import gov.samhsa.bhits.pcm.domain.provider.IndividualProviderRepository;
import gov.samhsa.bhits.pcm.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.bhits.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.bhits.pcm.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.bhits.pcm.domain.valueset.MedicalSectionRepository;
import gov.samhsa.bhits.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.bhits.pcm.infrastructure.AbstractConsentRevokationPdfGenerator;
import gov.samhsa.bhits.pcm.infrastructure.EchoSignSignatureService;
import gov.samhsa.bhits.pcm.service.consent.*;
import gov.samhsa.bhits.pcm.service.consentexport.ConsentExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class ConsentServiceConfig {

    @Value("${bhits.pcm.config.pid.domain.id}")
    private String pidDomainId;

    @Value("${bhits.pcm.config.pid.domain.type}")
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
