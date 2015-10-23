package gov.samhsa.pcm.web.config.di.root;

import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;
import gov.samhsa.consent.ConsentBuilderImpl;
import gov.samhsa.consent.pg.XacmlXslUrlProviderImpl;
import gov.samhsa.pcm.common.SpringContext;
import gov.samhsa.pcm.domain.DomainEventServiceImpl;
import gov.samhsa.pcm.domain.account.EmailTokenRepository;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.audit.ModifiedEntityTypeEntityRepository;
import gov.samhsa.pcm.domain.audit.RevisionInfoEntityRepository;
import gov.samhsa.pcm.domain.audit.RevisionListenerImpl;
import gov.samhsa.pcm.domain.clinicaldata.AllergyRepository;
import gov.samhsa.pcm.domain.clinicaldata.ClinicalDocumentRepository;
import gov.samhsa.pcm.domain.consent.ConsentFactory;
import gov.samhsa.pcm.domain.consent.ConsentRepository;
import gov.samhsa.pcm.domain.educationmaterial.EducationMaterialRepository;
import gov.samhsa.pcm.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.provider.IndividualProviderRepository;
import gov.samhsa.pcm.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.pcm.domain.provider.StaffIndividualProviderRepository;
import gov.samhsa.pcm.domain.provider.StaffOrganizationalProviderRepository;
import gov.samhsa.pcm.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.pcm.domain.reference.ClinicalDocumentSectionTypeCodeRepository;
import gov.samhsa.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.pcm.domain.reference.CountryCodeRepository;
import gov.samhsa.pcm.domain.reference.LanguageCodeRepository;
import gov.samhsa.pcm.domain.reference.LegalRepresentativeTypeCodeRepository;
import gov.samhsa.pcm.domain.reference.MaritalStatusCodeRepository;
import gov.samhsa.pcm.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.pcm.domain.reference.RaceCodeRepository;
import gov.samhsa.pcm.domain.reference.ReligiousAffiliationCodeRepository;
import gov.samhsa.pcm.domain.reference.SensitivityPolicyCodeRepository;
import gov.samhsa.pcm.domain.reference.StateCodeRepository;
import gov.samhsa.pcm.domain.staff.StaffRepository;
import gov.samhsa.pcm.domain.systemnotification.SystemNotificationRepository;
import gov.samhsa.pcm.domain.valueset.CodeSystemRepository;
import gov.samhsa.pcm.domain.valueset.CodeSystemVersionRepository;
import gov.samhsa.pcm.domain.valueset.ConceptCodeRepository;
import gov.samhsa.pcm.domain.valueset.ConceptCodeValueSetRepository;
import gov.samhsa.pcm.domain.valueset.MedicalSectionRepository;
import gov.samhsa.pcm.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.pcm.domain.valueset.ValueSetRepository;
import gov.samhsa.pcm.esignaturepolling.EchoSignPollingService;
import gov.samhsa.pcm.hl7.Hl7v3TransformerImpl;
import gov.samhsa.pcm.infrastructure.ConsentPdfGeneratorImpl;
import gov.samhsa.pcm.infrastructure.EchoSignSignatureServiceImpl;
import gov.samhsa.pcm.infrastructure.EmailSenderImpl;
import gov.samhsa.pcm.infrastructure.HippaSpaceCodedConceptLookupServiceImpl;
import gov.samhsa.pcm.infrastructure.PixServiceImpl;
import gov.samhsa.pcm.infrastructure.QueryProxy;
import gov.samhsa.pcm.infrastructure.RestfulQueryProxy;
import gov.samhsa.pcm.infrastructure.TryPolicyServiceImpl;
import gov.samhsa.pcm.infrastructure.domain.ConsentRevokeSubmittedEventHandler;
import gov.samhsa.pcm.infrastructure.domain.ConsentRevokedEventHandler;
import gov.samhsa.pcm.infrastructure.domain.ConsentSignedEventHandler;
import gov.samhsa.pcm.infrastructure.domain.ConsentSubmittedEventHandler;
import gov.samhsa.pcm.infrastructure.domain.account.UsersRepositoryImpl;
import gov.samhsa.pcm.infrastructure.eventlistener.AuthenticationFailedEventListener;
import gov.samhsa.pcm.infrastructure.eventlistener.AuthorizationFailureEventListener;
import gov.samhsa.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.pcm.infrastructure.eventlistener.FileDownloadedEventListener;
import gov.samhsa.pcm.infrastructure.eventlistener.FileUploadedEventListener;
import gov.samhsa.pcm.infrastructure.eventlistener.LoginFailureEventListener;
import gov.samhsa.pcm.infrastructure.eventlistener.LoginSuccessEventListener;
import gov.samhsa.pcm.infrastructure.eventlistener.MaliciousFileDetectedEventListener;
import gov.samhsa.pcm.infrastructure.eventlistener.UnauthorizedAccessAttemptedEventListener;
import gov.samhsa.pcm.infrastructure.eventlistener.UserCreatedEventListener;
import gov.samhsa.pcm.infrastructure.pg.ConsentRevokationPdfGeneratorImpl;
import gov.samhsa.pcm.infrastructure.rabbit.MessageSenderImpl;
import gov.samhsa.pcm.infrastructure.security.AccessReferenceMapperImpl;
import gov.samhsa.pcm.infrastructure.security.ClamAVService;
import gov.samhsa.pcm.infrastructure.security.RecaptchaService;
import gov.samhsa.pcm.infrastructure.security.TokenGeneratorUuidImpl;
import gov.samhsa.pcm.infrastructure.security.UserContextImpl;
import gov.samhsa.pcm.pixclient.service.PixManagerServiceImpl;
import gov.samhsa.pcm.pixclient.util.PixManagerConstants;
import gov.samhsa.pcm.pixclient.util.PixManagerMessageHelper;
import gov.samhsa.pcm.pixclient.util.PixManagerRequestXMLToJava;
import gov.samhsa.pcm.service.account.AccountLinkToPatientServiceImpl;
import gov.samhsa.pcm.service.account.AccountVerificationServiceImpl;
import gov.samhsa.pcm.service.account.MrnServiceImpl;
import gov.samhsa.pcm.service.account.PasswordResetServiceImpl;
import gov.samhsa.pcm.service.account.pg.AccountServiceImpl;
import gov.samhsa.pcm.service.admin.AdminProfileDtoToAdministratorMapper;
import gov.samhsa.pcm.service.admin.pg.AdminServiceImpl;
import gov.samhsa.pcm.service.audit.AdminAuditServiceImpl;
import gov.samhsa.pcm.service.audit.pg.AuditServiceImpl;
import gov.samhsa.pcm.service.clinicaldata.AllergyServiceImpl;
import gov.samhsa.pcm.service.clinicaldata.ClinicalDocumentServiceImpl;
import gov.samhsa.pcm.service.consent.ConsentAssertion;
import gov.samhsa.pcm.service.consent.ConsentCheckService;
import gov.samhsa.pcm.service.consent.ConsentCheckServiceImpl;
import gov.samhsa.pcm.service.consent.ConsentEffectiveAndToBeExpiredPollingService;
import gov.samhsa.pcm.service.consent.ConsentHelper;
import gov.samhsa.pcm.service.consent.ConsentServiceImpl;
import gov.samhsa.pcm.service.consent.PolicyIdServiceImpl;
import gov.samhsa.pcm.service.consent.pg.ConsentOneToOneAssertion;
import gov.samhsa.pcm.service.consentexport.ConsentDtoFactoryImpl;
import gov.samhsa.pcm.service.consentexport.ConsentExportMapperImpl;
import gov.samhsa.pcm.service.consentexport.ConsentExportService;
import gov.samhsa.pcm.service.consentexport.ConsentExportServiceImpl;
import gov.samhsa.pcm.service.contact.ContactServiceImpl;
import gov.samhsa.pcm.service.educationmaterial.EducationMaterialServiceImpl;
import gov.samhsa.pcm.service.notification.NotificationServiceImpl;
import gov.samhsa.pcm.service.patient.PatientLegalRepresentativeAssociationServiceImpl;
import gov.samhsa.pcm.service.patient.PatientProfileDtoToPatientMapper;
import gov.samhsa.pcm.service.patient.pg.PatientDtoToPatientMapper;
import gov.samhsa.pcm.service.patient.pg.PatientServiceImpl;
import gov.samhsa.pcm.service.provider.FavoriteIndividualProviderServiceImpl;
import gov.samhsa.pcm.service.provider.FavoriteOrganizationalProviderServiceImpl;
import gov.samhsa.pcm.service.provider.HashMapResultToProviderDtoConverter;
import gov.samhsa.pcm.service.provider.IndividualProviderService;
import gov.samhsa.pcm.service.provider.IndividualProviderServiceImpl;
import gov.samhsa.pcm.service.provider.OrganizationalProviderService;
import gov.samhsa.pcm.service.provider.OrganizationalProviderServiceImpl;
import gov.samhsa.pcm.service.provider.ProviderSearchLookupService;
import gov.samhsa.pcm.service.provider.pg.ProviderSearchLookupServiceImpl;
import gov.samhsa.pcm.service.reference.AdministrativeGenderCodeServiceImpl;
import gov.samhsa.pcm.service.reference.ClinicalDocumentSectionTypeCodeServiceImpl;
import gov.samhsa.pcm.service.reference.ClinicalDocumentTypeCodeServiceImpl;
import gov.samhsa.pcm.service.reference.CountryCodeServiceImpl;
import gov.samhsa.pcm.service.reference.LanguageCodeServiceImpl;
import gov.samhsa.pcm.service.reference.LegalRepresentativeTypeCodeServiceImpl;
import gov.samhsa.pcm.service.reference.MaritalStatusCodeServiceImpl;
import gov.samhsa.pcm.service.reference.PurposeOfUseCodeServiceImpl;
import gov.samhsa.pcm.service.reference.RaceCodeServiceImpl;
import gov.samhsa.pcm.service.reference.ReligiousAffiliationCodeServiceImpl;
import gov.samhsa.pcm.service.reference.SensitivityPolicyCodeServiceImpl;
import gov.samhsa.pcm.service.reference.pg.StateCodeServicePg;
import gov.samhsa.pcm.service.reference.pg.StateCodeServicePgImpl;
import gov.samhsa.pcm.service.spirit.SpiritQueryServiceImpl;
import gov.samhsa.pcm.service.systemnotification.SystemNotificationServiceImpl;
import gov.samhsa.pcm.service.validator.FieldValidatorChangePassword;
import gov.samhsa.pcm.service.validator.FieldValidatorCreateNewAccountOnPatient;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroubleCreateNewPassword;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroublePassword;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroubleSelection;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroubleUsername;
import gov.samhsa.pcm.service.validator.FieldValidatorRequestMoreInformation;
import gov.samhsa.pcm.service.validator.pg.FieldValidator;
import gov.samhsa.pcm.service.valueset.CodeSystemServiceImpl;
import gov.samhsa.pcm.service.valueset.CodeSystemVersionServiceImpl;
import gov.samhsa.pcm.service.valueset.ConceptCodeServiceImpl;
import gov.samhsa.pcm.service.valueset.MedicalSectionServiceImpl;
import gov.samhsa.pcm.service.valueset.ValueSetCategoryServiceImpl;
import gov.samhsa.pcm.service.valueset.ValueSetLookupServiceImpl;
import gov.samhsa.pcm.service.valueset.ValueSetMgmtHelper;
import gov.samhsa.pcm.service.valueset.ValueSetServiceImpl;
import gov.samhsa.spirit.wsclient.adapter.SpiritAdapterImpl;
import gov.samhsa.spirit.wsclient.util.SpiritClientHelper;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import ch.qos.logback.audit.AuditException;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class ApplicationContextConfig {

	private String configPath = System.getProperty("C2S_PROPS");

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private DataAccessConfig dataAccessConfig;

	@Autowired
	private WebSecurityConfig webSecurityConfigurtion;

	@Autowired
	private RabbitMQConfig rabbitMQConfig;

	@Autowired
	private ConsentRepository consentRepository;

	@Autowired
	private EducationMaterialRepository educationMaterialRepository;

	@Autowired
	private AllergyRepository allergyRepository;

	@Autowired
	private LegalRepresentativeTypeCodeRepository legalRepresentativeTypeCodeRepository;

	@Autowired
	private ValueSetCategoryRepository valueSetCategoryRepository;

	@Autowired
	private ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

	@Autowired
	private ClinicalDocumentSectionTypeCodeRepository clinicalDocumentSectionTypeCodeRepository;

	@Autowired
	private MedicalSectionRepository medicalSectionRepository;

	@Autowired
	private SystemNotificationRepository systemNotificationRepository;

	@Autowired
	private SensitivityPolicyCodeRepository sensitivityPolicyCodeRepository;

	@Autowired
	private OrganizationalProviderRepository organizationalProviderRepository;

	@Autowired
	private StaffOrganizationalProviderRepository staffOrganizationalProviderRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	@Autowired
	private ConceptCodeRepository conceptCodeRepository;

	@Autowired
	private ValueSetRepository valueSetRepository;

	@Autowired
	private ConceptCodeValueSetRepository conceptCodeValueSetRepository;

	@Autowired
	private CodeSystemVersionRepository codeSystemVersionRepository;

	@Autowired
	private StateCodeRepository stateCodeRepository;

	@Autowired
	private CountryCodeRepository countryCodeRepository;

	@Autowired
	private LanguageCodeRepository languageCodeRepository;

	@Autowired
	private MaritalStatusCodeRepository maritalStatusCodeRepository;

	@Autowired
	private RaceCodeRepository raceCodeRepository;

	@Autowired
	private ReligiousAffiliationCodeRepository religiousAffiliationCodeRepository;

	@Autowired
	private ClinicalDocumentRepository clinicalDocumentRepository;

	@Autowired
	private IndividualProviderRepository individualProviderRepository;

	@Autowired
	private StaffIndividualProviderRepository staffIndividualProviderRepository;

	@Autowired
	private PurposeOfUseCodeRepository purposeOfUseCodeRepository;

	@Autowired
	private CodeSystemRepository codeSystemRepository;

	@Autowired
	protected EmailTokenRepository emailTokenRepository;

	@Autowired
	protected UsersRepository usersRepository;

	@Autowired
	private StaffRepository providerAdminRepository;

	@Autowired
	private RevisionInfoEntityRepository patientRevisionEntityRepository;

	@Autowired
	private ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository;

	@Autowired
	private PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

	@Value("${passwordResetTokenExpireInHours}")
	private String passwordResetTokenExpireInHours;

	@Value("${numberRecentPatientActivities}")
	private String numberRecentPatientActivities;

	@Value("${accountVerificationTokenExpireInHours}")
	private String accountVerificationTokenExpireInHours;

	@Value("${empi.endPointAddress}")
	private String empiEndPointAddress;

	@Value("${clamdHost}")
	private String clamdHost;

	@Value("${clamdPort}")
	private String clamdPort;

	@Value("${connTimeOut}")
	private String connTimeOut;

	@Value("${maximum_upload_file_size}")
	private String maximumUploadFileSize;

	@Value("${extensions_permitted_to_upload}")
	private String extensionsPermittedToUpload;

	@Value("${concept_code_list_page_size}")
	private int conceptCodeListPageSize;

	@Value("${c2s.org}")
	private String c2sOrg;

	@Value("${daysToBeExpiredToSendNotification}")
	private String daysToBeExpiredToSendNotification;

	@Value("${notification_consent_is_effective}")
	private String notification_consent_is_effective;

	@Value("${notification_consent_expires_30_days}")
	private String notification_consent_expires_30_days;

	@Value("${notification_consent_revoked}")
	private String notification_consent_revoked;

	@Value("${notification_consent_revoke_needs_signing}")
	private String notification_consent_revoke_needs_signing;

	@Value("${pid.domain.id}")
	private String pidDomainId;

	@Value("${pid.domain.type}")
	private String pidDomainType;

	@Value("${notification_consent_signed}")
	private String notification_consent_signed;

	@Value("${echoSignDocumentService15EndpointAddress}")
	private String echoSignDocumentService15EndpointAddress;

	@Value("${echoSignApiKey}")
	private String echoSignApiKey;

	@Value("${ProviderSearchURL}")
	private String providerSearchURL;

	@Value("${HIPAASpaceRestfulWebService}")
	private String hIPAASpaceRestfulWebService;

	@Value("${HIPAASpaceRestfulWebServiceToken}")
	private String hIPAASpaceRestfulWebServiceToken;

	@Value("${maximum_failed_attempts}")
	private String maximum_failed_attempts;

	@Value("${email.host}")
	private String emailHost;

	@Value("${email.port}")
	private String emailPort;

	@Value("${email.protocol}")
	private String emailProtocol;

	@Value("${email.username}")
	private String emailUsername;

	@Value("${email.password}")
	private String emailPassword;

	@Value("${pid.prefix}")
	private String pidPrefix;

	@Value("${empi.domain.id}")
	private String empiDomainId;

	@Value("${empi.domain.name}")
	private String empiDomainName;

	@Value("${c2s.trymypolicy.endPointAddress}")
	private String c2sTrymypolicyEndPointAddress;

	@Value("${recaptchaHttpsServer}")
	private String recaptchaHttpsServer;

	@Value("${recaptchaHttpServer}")
	private String recaptchaHttpServer;

	@Value("${recaptchaVerificationServer}")
	private String recaptchaVerificationServer;

	@Value("${recaptchaPrivateKey}")
	private String recaptchaPrivateKey;

	@Value("${spirit.client.endpointAddress}")
	private String spiritClientEndpointAddress;

	@Value("${spirit.client.org}")
	private String spiritClientOrg;

	@Value("${spirit.client.pwd}")
	private String spiritClientPwd;

	@Value("${spirit.client.rol}")
	private String spiritClientRol;

	@Value("${spirit.client.user}")
	private String spiritClientUser;

	@Value("${recaptchaPublicKey}")
	private String recaptchaPublicKey;

	@Bean
	public AccessReferenceMapperImpl accessReferenceMapperImpl() {
		AccessReferenceMapperImpl accessReferenceMapperImpl = new AccessReferenceMapperImpl();
		return accessReferenceMapperImpl;
	}

	@Bean
	public AccountLinkToPatientServiceImpl accountLinkToPatientServiceImpl() {
		AccountLinkToPatientServiceImpl accountLinkToPatientServiceImpl = new AccountLinkToPatientServiceImpl(
				Integer.parseInt(passwordResetTokenExpireInHours),
				usersRepository, patientRepository, tokenGeneratorUuidImpl(),
				emailTokenRepository, emailSenderImpl(),
				webSecurityConfigurtion.passwordEncoder());
		return accountLinkToPatientServiceImpl;
	}

	@Bean
	public AccountServiceImpl accountServiceImpl() {
		AccountServiceImpl accountServiceImpl = new AccountServiceImpl(
				Integer.parseInt(passwordResetTokenExpireInHours),
				patientRepository, administrativeGenderCodeRepository,
				webSecurityConfigurtion.passwordEncoder(), userContextImpl(),
				emailSenderImpl(), tokenGeneratorUuidImpl(),
				emailTokenRepository, usersRepository, spiritQueryServiceImpl(), patientDtoToPatientMapper(), mrnServiceImpl(),
				pixServiceImpl());
		return accountServiceImpl;
	}

	@Bean
	public AccountVerificationServiceImpl accountVerificationServiceImpl() {
		AccountVerificationServiceImpl accountVerificationServiceImpl = new AccountVerificationServiceImpl(
				usersRepository, patientRepository, emailTokenRepository,
				emailSenderImpl());
		return accountVerificationServiceImpl;
	}

	@Bean
	public AdminAuditServiceImpl adminAuditServiceImpl() {
		AdminAuditServiceImpl adminAuditServiceImpl = new AdminAuditServiceImpl(
				Integer.parseInt(numberRecentPatientActivities),
				entityManagerFactory, patientRepository,
				patientRevisionEntityRepository,
				modifiedEntityTypeEntityRepository,
				patientLegalRepresentativeAssociationRepository,
				providerAdminRepository);
		return adminAuditServiceImpl;
	}

	@Bean
	public AdministrativeGenderCodeServiceImpl administrativeGenderCodeServiceImpl() {
		AdministrativeGenderCodeServiceImpl administrativeGenderCodeServiceImpl = new AdministrativeGenderCodeServiceImpl(
				administrativeGenderCodeRepository, modelMapper());
		return administrativeGenderCodeServiceImpl;
	}

	@Bean
	public AdminProfileDtoToAdministratorMapper adminProfileDtoToAdministratorMapper() {
		AdminProfileDtoToAdministratorMapper adminProfileDtoToAdministratorMapper = new AdminProfileDtoToAdministratorMapper(
				providerAdminRepository, administrativeGenderCodeRepository);
		return adminProfileDtoToAdministratorMapper;
	}

	@Bean
	public AdminServiceImpl adminServiceImpl() {
		AdminServiceImpl adminServiceImpl = new AdminServiceImpl(
				Integer.parseInt(accountVerificationTokenExpireInHours),
				providerAdminRepository, patientRepository,
				patientProfileDtoToPatientMapper(),
				adminProfileDtoToAdministratorMapper(), modelMapper(),
				userContextImpl(), webSecurityConfigurtion.passwordEncoder(),
				emailSenderImpl(), tokenGeneratorUuidImpl(),
				emailTokenRepository, usersRepository,
				administrativeGenderCodeRepository, spiritQueryServiceImpl(), patientDtoToPatientMapper(), mrnServiceImpl(),
				pixServiceImpl());
		return adminServiceImpl;
	}

	@Bean
	public AllergyServiceImpl allergyServiceImpl() {
		AllergyServiceImpl allergyServiceImpl = new AllergyServiceImpl(
				allergyRepository);
		return allergyServiceImpl;
	}

	@Bean
	public AuditService auditServiceImpl() throws AuditException {
		AuditService auditServiceImpl = new gov.samhsa.acs.audit.AuditServiceImpl("Consent2share_PCM");
		return auditServiceImpl;
	}

	@Bean
	public gov.samhsa.pcm.service.audit.AuditService auditServiceImpl2() {
		gov.samhsa.pcm.service.audit.AuditService auditServiceImpl = new AuditServiceImpl(
				entityManagerFactory, patientRepository,
				patientRevisionEntityRepository,
				modifiedEntityTypeEntityRepository,
				patientLegalRepresentativeAssociationRepository,
				providerAdminRepository);
		return auditServiceImpl;
	}

	@Bean
	public AuthenticationFailedEventListener authenticationFailedEventListener()
			throws AuditException {
		AuthenticationFailedEventListener authenticationFailedEventListener = new AuthenticationFailedEventListener(eventService(), auditServiceImpl());
		return authenticationFailedEventListener;
	}

	@Bean
	public AuthorizationFailureEventListener authorizationFailureEventListener() {
		AuthorizationFailureEventListener authorizationFailureEventListener = new AuthorizationFailureEventListener(
				eventService(), userContextImpl());
		return authorizationFailureEventListener;
	}

	@Bean
	public ClamAVService clamAVService() {
		ClamAVService clamAVService = new ClamAVService(clamdHost, clamdPort,
				connTimeOut);
		return clamAVService;
	}

	@Bean
	public ClinicalDocumentSectionTypeCodeServiceImpl clinicalDocumentSectionTypeCodeServiceImpl() {
		ClinicalDocumentSectionTypeCodeServiceImpl clinicalDocumentSectionTypeCodeServiceImpl = new ClinicalDocumentSectionTypeCodeServiceImpl(
				clinicalDocumentSectionTypeCodeRepository, modelMapper());
		return clinicalDocumentSectionTypeCodeServiceImpl;
	}

	@Bean
	public ClinicalDocumentServiceImpl clinicalDocumentServiceImpl() {
		ClinicalDocumentServiceImpl clinicalDocumentServiceImpl = new ClinicalDocumentServiceImpl(
				maximumUploadFileSize, extensionsPermittedToUpload,
				clinicalDocumentRepository, clinicalDocumentTypeCodeRepository,
				modelMapper(), patientRepository, userContextImpl(),
				validator());
		return clinicalDocumentServiceImpl;
	}

	@Bean
	public ClinicalDocumentTypeCodeServiceImpl clinicalDocumentTypeCodeServiceImpl() {
		ClinicalDocumentTypeCodeServiceImpl clinicalDocumentTypeCodeServiceImpl = new ClinicalDocumentTypeCodeServiceImpl(
				clinicalDocumentTypeCodeRepository, modelMapper());
		return clinicalDocumentTypeCodeServiceImpl;
	}

	@Bean
	public CodeSystemServiceImpl codeSystemServiceImpl() {
		CodeSystemServiceImpl codeSystemServiceImpl = new CodeSystemServiceImpl(
				codeSystemRepository, valueSetMgmtHelper());
		return codeSystemServiceImpl;
	}

	@Bean
	public CodeSystemVersionServiceImpl codeSystemVersionServiceImpl() {
		CodeSystemVersionServiceImpl codeSystemVersionServiceImpl = new CodeSystemVersionServiceImpl(
				codeSystemVersionRepository, codeSystemRepository,
				valueSetMgmtHelper());
		return codeSystemVersionServiceImpl;
	}

	@Bean
	public ConceptCodeServiceImpl conceptCodeServiceImpl() {
		ConceptCodeServiceImpl conceptCodeServiceImpl = new ConceptCodeServiceImpl(
				conceptCodeListPageSize, conceptCodeRepository,
				valueSetRepository, codeSystemRepository,
				codeSystemVersionRepository, conceptCodeValueSetRepository,
				valueSetMgmtHelper());
		return conceptCodeServiceImpl;
	}

	@Bean(name = "consentAssertions")
	public Set<ConsentAssertion> consentAssertions() {
		Set<ConsentAssertion> consentAssertions = new HashSet<ConsentAssertion>();
		return consentAssertions;
	}

	@Bean
	public ConsentBuilderImpl consentBuilder() {
		ConsentBuilderImpl consentBuilderImpl = new ConsentBuilderImpl(c2sOrg,
				xacmlXslUrlProvider(), consentDtoFactoryImpl(),
				xmlTransformerImpl());
		return consentBuilderImpl;
	}

	@Bean
	public ConsentCheckService consentCheckServiceImpl() {
		ConsentCheckService consentCheckServiceImpl = new ConsentCheckServiceImpl(
				consentRepository, patientRepository, consentHelper());
		return consentCheckServiceImpl;
	}

	@Bean
	public ConsentDtoFactoryImpl consentDtoFactoryImpl() {
		ConsentDtoFactoryImpl consentDtoFactoryImpl = new ConsentDtoFactoryImpl(
				consentRepository, modelMapper(), consentExportMapperImpl());
		return consentDtoFactoryImpl;
	}

	@Bean(name = "consentEffectiveAndToBeExpiredPollingService")
	public ConsentEffectiveAndToBeExpiredPollingService consentEffectiveAndToBeExpiredPollingService() {
		ConsentEffectiveAndToBeExpiredPollingService consentEffectiveAndToBeExpiredPollingService = new ConsentEffectiveAndToBeExpiredPollingService(
				daysToBeExpiredToSendNotification,
				notification_consent_is_effective,
				notification_consent_expires_30_days, consentRepository,
				systemNotificationRepository);
		return consentEffectiveAndToBeExpiredPollingService;
	}

	@Bean
	public ConsentExportMapperImpl consentExportMapperImpl() {
		ConsentExportMapperImpl consentExportMapperImpl = new ConsentExportMapperImpl(
				modelMapper());
		return consentExportMapperImpl;
	}

	@Bean
	public ConsentExportService consentExportServiceImpl() {
		ConsentExportService consentExportServiceImpl = new ConsentExportServiceImpl(
				consentBuilder());
		return consentExportServiceImpl;
	}

	@Bean
	public ConsentFactory consentFactory() {
		ConsentFactory consentFactory = new ConsentFactory(consentRepository);
		return consentFactory;
	}

	@Bean
	public ConsentHelper consentHelper() {
		ConsentHelper consentHelper = new ConsentHelper();
		return consentHelper;
	}

	@Bean
	public ConsentOneToOneAssertion consentOneToOneAssertion() {
		ConsentOneToOneAssertion consentOneToOneAssertion = new ConsentOneToOneAssertion();
		return consentOneToOneAssertion;
	}

	@Bean
	public ConsentPdfGeneratorImpl consentPdfGeneratorImpl() {
		ConsentPdfGeneratorImpl consentPdfGeneratorImpl = new ConsentPdfGeneratorImpl(
				valueSetCategoryRepository, clinicalDocumentTypeCodeRepository,
				medicalSectionRepository);
		return consentPdfGeneratorImpl;
	}

	@Bean
	public ConsentRevokationPdfGeneratorImpl consentRevokationPdfGeneratorImpl() {
		ConsentRevokationPdfGeneratorImpl consentRevokationPdfGeneratorImpl = new ConsentRevokationPdfGeneratorImpl();
		return consentRevokationPdfGeneratorImpl;
	}

	@Bean
	public ConsentRevokedEventHandler consentRevokedEventHandler() {
		ConsentRevokedEventHandler consentRevokedEventHandler = new ConsentRevokedEventHandler(
				notification_consent_revoked, messageSenderImpl(),
				consentRepository, systemNotificationRepository);
		return consentRevokedEventHandler;
	}

	@Bean
	public ConsentRevokeSubmittedEventHandler consentRevokeSubmittedEventHandler() {
		ConsentRevokeSubmittedEventHandler consentRevokeSubmittedEventHandler = new ConsentRevokeSubmittedEventHandler(
				notification_consent_revoke_needs_signing, messageSenderImpl(),
				consentRepository, systemNotificationRepository);
		return consentRevokeSubmittedEventHandler;
	}

	@Bean
	public ConsentServiceImpl consentServiceImpl() {
		ConsentServiceImpl consentServiceImpl = new ConsentServiceImpl(
				pidDomainId, pidDomainType, consentRepository,
				consentPdfGeneratorImpl(), patientRepository,
				individualProviderRepository, organizationalProviderRepository,
				clinicalDocumentTypeCodeRepository, medicalSectionRepository,
				valueSetCategoryRepository, purposeOfUseCodeRepository,
				echoSignSignatureServiceImpl(), userContextImpl(),
				consentRevokationPdfGeneratorImpl(),
				consentExportServiceImpl(), tryPolicyServiceImpl(),
				consentBuilder(), consentFactory(), consentCheckServiceImpl(),
				consentAssertions(), policyIdServiceImpl(),
				xmlTransformerImpl(), documentXmlConverterImpl(),
				documentAccessorImpl());
		return consentServiceImpl;
	}

	@Bean
	public ConsentSignedEventHandler consentSignedEventHandler() {
		ConsentSignedEventHandler consentSignedEventHandler = new ConsentSignedEventHandler(
				notification_consent_signed, messageSenderImpl(),
				consentRepository, systemNotificationRepository);
		return consentSignedEventHandler;
	}

	@Bean
	public ConsentSubmittedEventHandler consentSubmittedEventHandler() {
		ConsentSubmittedEventHandler consentSubmittedEventHandler = new ConsentSubmittedEventHandler(
				notification_consent_signed, messageSenderImpl(),
				consentRepository, systemNotificationRepository);
		return consentSubmittedEventHandler;
	}

	@Bean
	public CountryCodeServiceImpl countryCodeServiceImpl() {
		CountryCodeServiceImpl countryCodeServiceImpl = new CountryCodeServiceImpl(
				countryCodeRepository, modelMapper());
		return countryCodeServiceImpl;
	}

	@Bean
	public DocumentAccessorImpl documentAccessorImpl() {
		DocumentAccessorImpl documentAccessorImpl = new DocumentAccessorImpl();
		return documentAccessorImpl;
	}

	@Bean
	public DocumentXmlConverterImpl documentXmlConverterImpl() {
		DocumentXmlConverterImpl documentXmlConverterImpl = new DocumentXmlConverterImpl();
		return documentXmlConverterImpl;
	}

	@Bean
	public DomainEventServiceImpl domainEventServiceImpl() {
		DomainEventServiceImpl domainEventServiceImpl = new DomainEventServiceImpl();
		return domainEventServiceImpl;
	}

	@Bean
	public EchoSignSignatureServiceImpl echoSignSignatureServiceImpl() {
		EchoSignSignatureServiceImpl echoSignSignatureServiceImpl = new EchoSignSignatureServiceImpl(
				echoSignDocumentService15EndpointAddress, echoSignApiKey);
		return echoSignSignatureServiceImpl;
	}

	@Bean
	public EducationMaterialServiceImpl educationMaterialServiceImpl() {
		EducationMaterialServiceImpl educationMaterialServiceImpl = new EducationMaterialServiceImpl(
				educationMaterialRepository);
		return educationMaterialServiceImpl;
	}

	@Bean
	public EmailSenderImpl emailSenderImpl() {
		EmailSenderImpl emailSenderImpl = new EmailSenderImpl(mailSender(),
				templateEngine(), messageSource());
		return emailSenderImpl;
	}

	@Bean
	public ClassLoaderTemplateResolver emailTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("META-INF/email/");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(1);
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public EchoSignPollingService esignaturePollingService() {
		EchoSignPollingService esignaturePollingService = new EchoSignPollingService(
				consentRepository, echoSignSignatureServiceImpl());
		return esignaturePollingService;
	}

	@Bean
	public EventService eventService() {
		EventService eventService = new EventService();
		return eventService;
	}

	@Bean
	public FavoriteIndividualProviderServiceImpl favoriteIndividualProviderServiceImpl() {
		FavoriteIndividualProviderServiceImpl favoriteIndividualProviderServiceImpl = new FavoriteIndividualProviderServiceImpl(
				individualProviderRepository, modelMapper(),
				staffIndividualProviderRepository);
		return favoriteIndividualProviderServiceImpl;
	}

	@Bean
	public FavoriteOrganizationalProviderServiceImpl favoriteOrganizationalProviderServiceImpl() {
		FavoriteOrganizationalProviderServiceImpl favoriteOrganizationalProviderServiceImpl = new FavoriteOrganizationalProviderServiceImpl(
				organizationalProviderRepository, modelMapper(),
				staffOrganizationalProviderRepository);
		return favoriteOrganizationalProviderServiceImpl;
	}

	@Bean
	public FieldValidator fieldValidator() {
		FieldValidator fieldValidator = new FieldValidator();
		return fieldValidator;
	}

	@Bean
	public FieldValidatorChangePassword fieldValidatorChangePassword() {
		FieldValidatorChangePassword fieldValidatorChangePassword = new FieldValidatorChangePassword();
		return fieldValidatorChangePassword;
	}

	@Bean
	public FieldValidatorCreateNewAccountOnPatient fieldValidatorCreateNewAccountOnPatient() {
		FieldValidatorCreateNewAccountOnPatient fieldValidatorCreateNewAccountOnPatient = new FieldValidatorCreateNewAccountOnPatient();
		return fieldValidatorCreateNewAccountOnPatient;
	}

	@Bean
	public FieldValidatorLoginTroubleCreateNewPassword fieldValidatorLoginTroubleCreateNewPassword() {
		FieldValidatorLoginTroubleCreateNewPassword fieldValidatorLoginTroubleCreateNewPassword = new FieldValidatorLoginTroubleCreateNewPassword();
		return fieldValidatorLoginTroubleCreateNewPassword;
	}

	@Bean
	public FieldValidatorLoginTroublePassword fieldValidatorLoginTroublePassword() {
		FieldValidatorLoginTroublePassword fieldValidatorLoginTroublePassword = new FieldValidatorLoginTroublePassword();
		return fieldValidatorLoginTroublePassword;
	}

	@Bean
	public FieldValidatorLoginTroubleSelection fieldValidatorLoginTroubleSelection() {
		FieldValidatorLoginTroubleSelection fieldValidatorLoginTroubleSelection = new FieldValidatorLoginTroubleSelection();
		return fieldValidatorLoginTroubleSelection;
	}

	@Bean
	public FieldValidatorLoginTroubleUsername fieldValidatorLoginTroubleUsername() {
		FieldValidatorLoginTroubleUsername fieldValidatorLoginTroubleUsername = new FieldValidatorLoginTroubleUsername();
		return fieldValidatorLoginTroubleUsername;
	}

	@Bean
	public FieldValidatorRequestMoreInformation fieldValidatorRequestMoreInformation() {
		FieldValidatorRequestMoreInformation fieldValidatorRequestMoreInformation = new FieldValidatorRequestMoreInformation();
		return fieldValidatorRequestMoreInformation;
	}

	@Bean
	public FileDownloadedEventListener fileDownloadedEventListener()
			throws AuditException {
		FileDownloadedEventListener fileDownloadedEventListener = new FileDownloadedEventListener(
				eventService(), auditServiceImpl());
		return fileDownloadedEventListener;
	}

	@Bean
	public FileUploadedEventListener fileUploadedEventListener()
			throws AuditException {
		FileUploadedEventListener fileUploadedEventListener = new FileUploadedEventListener(
				eventService(), auditServiceImpl());
		return fileUploadedEventListener;
	}

	@Bean
	public HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter() {
		HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter = new HashMapResultToProviderDtoConverter();
		return hashMapResultToProviderDtoConverter;
	}

	@Bean
	public HippaSpaceCodedConceptLookupServiceImpl hippaSpaceCodedConceptLookupServiceImpl() {
		HippaSpaceCodedConceptLookupServiceImpl hippaSpaceCodedConceptLookupServiceImpl = new HippaSpaceCodedConceptLookupServiceImpl(
				hIPAASpaceRestfulWebService, hIPAASpaceRestfulWebServiceToken,
				restfulQueryProxy());
		return hippaSpaceCodedConceptLookupServiceImpl;
	}

	@Bean
	@Scope("prototype")
	public Hl7v3TransformerImpl hl7v3TransformerImpl() {
		Hl7v3TransformerImpl hl7v3TransformerImpl = new Hl7v3TransformerImpl(
				xmlTransformerImpl());
		return hl7v3TransformerImpl;
	}

	@Bean
	public IndividualProviderService individualProviderServiceImpl() {
		IndividualProviderService individualProviderServiceImpl = new IndividualProviderServiceImpl(
				individualProviderRepository, modelMapper(), patientRepository);
		return individualProviderServiceImpl;
	}

	@Bean
	public LanguageCodeServiceImpl languageCodeServiceImpl() {
		LanguageCodeServiceImpl languageCodeServiceImpl = new LanguageCodeServiceImpl(
				languageCodeRepository, modelMapper());
		return languageCodeServiceImpl;
	}

	@Bean
	public LegalRepresentativeTypeCodeServiceImpl legalRepresentativeTypeCodeServiceImpl() {
		LegalRepresentativeTypeCodeServiceImpl legalRepresentativeTypeCodeServiceImpl = new LegalRepresentativeTypeCodeServiceImpl(
				legalRepresentativeTypeCodeRepository, modelMapper());
		return legalRepresentativeTypeCodeServiceImpl;
	}

	@Bean(initMethod = "init", destroyMethod = "destroy")
	public AuditService logbackAuditServiceImpl() throws AuditException {
		AuditService auditServiceImpl = new gov.samhsa.acs.audit.AuditServiceImpl(
				"Consent2share_PCM");
		return auditServiceImpl;
	}

	@Bean
	public LoginFailureEventListener loginFailureEventListener() {
		LoginFailureEventListener loginFailureEventListener = new LoginFailureEventListener(
				Short.parseShort(maximum_failed_attempts), eventService(),
				usersRepository);
		return loginFailureEventListener;
	}

	@Bean
	public LoginSuccessEventListener loginSuccessEventListener() {
		LoginSuccessEventListener loginSuccessEventListener = new LoginSuccessEventListener(
				eventService(), usersRepository);
		return loginSuccessEventListener;
	}

	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost(emailHost);
		javaMailSenderImpl.setPort(Integer.parseInt(emailPort));
		javaMailSenderImpl.setProtocol(emailProtocol);
		javaMailSenderImpl.setUsername(emailUsername);
		javaMailSenderImpl.setPassword(emailPassword);
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		javaMailSenderImpl.setJavaMailProperties(properties);
		return javaMailSenderImpl;
	}

	@Bean
	public MaliciousFileDetectedEventListener maliciousFileDetectedEventListener()
			throws AuditException {
		MaliciousFileDetectedEventListener maliciousFileDetectedEventListener = new MaliciousFileDetectedEventListener(
				eventService(), auditServiceImpl());
		return maliciousFileDetectedEventListener;
	}

	@Bean
	public MaritalStatusCodeServiceImpl maritalStatusCodeServiceImpl() {
		MaritalStatusCodeServiceImpl maritalStatusCodeServiceImpl = new MaritalStatusCodeServiceImpl(
				maritalStatusCodeRepository, modelMapper());
		return maritalStatusCodeServiceImpl;
	}

	@Bean
	public MedicalSectionServiceImpl medicalSectionServiceImpl() {
		MedicalSectionServiceImpl medicalSectionServiceImpl = new MedicalSectionServiceImpl(
				medicalSectionRepository, consentRepository,
				valueSetMgmtHelper());
		return medicalSectionServiceImpl;
	}

	@Bean
	public MessageSenderImpl messageSenderImpl() {
		MessageSenderImpl messageSenderImpl = new MessageSenderImpl(
				rabbitMQConfig.amqpTemplate(), rabbitMQConfig.rabbitAdmin(),
				rabbitMQConfig.queue(), rabbitMQConfig.fanoutExchange());
		return messageSenderImpl;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		String[] basenames = { "WEB-INF/i18n/application",
				"WEB-INF/i18n/validation", "WEB-INF/i18n/messages",
				"WEB-INF/i18n/list_consent_messages",
				"WEB-INF/i18n/add_consent_messages",
				"WEB-INF/i18n/sidebar_messages",
				"WEB-INF/i18n/header_messages",
				"file:"+configPath+"/pcm-pg-config" };
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(basenames);
		return messageSource;
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

	@Bean
	public MrnServiceImpl mrnServiceImpl() {
		MrnServiceImpl mrnServiceImpl = new MrnServiceImpl(pidPrefix,
				patientRepository);
		return mrnServiceImpl;
	}

	@Bean
	public NotificationServiceImpl notificationServiceImpl() {
		NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl(
				patientRepository);
		return notificationServiceImpl;
	}

	@Bean
	public OrganizationalProviderService organizationalProviderServiceImpl() {
		OrganizationalProviderService organizationalProviderServiceImpl = new OrganizationalProviderServiceImpl(
				patientRepository, modelMapper(),
				organizationalProviderRepository);
		return organizationalProviderServiceImpl;
	}

	@Bean
	public PasswordResetServiceImpl passwordResetServiceImpl() {
		PasswordResetServiceImpl passwordResetServiceImpl = new PasswordResetServiceImpl(
				Integer.parseInt(passwordResetTokenExpireInHours),
				usersRepository, patientRepository, providerAdminRepository,
				tokenGeneratorUuidImpl(), emailTokenRepository,
				emailSenderImpl(), webSecurityConfigurtion.passwordEncoder());
		return passwordResetServiceImpl;
	}

	@Bean
	public PatientDtoToPatientMapper patientDtoToPatientMapper(){
		PatientDtoToPatientMapper patientDtoToPatientMapper = new PatientDtoToPatientMapper(stateCodeRepository, countryCodeRepository, administrativeGenderCodeRepository, languageCodeRepository, maritalStatusCodeRepository, raceCodeRepository, religiousAffiliationCodeRepository);
		return patientDtoToPatientMapper;
	}

	@Bean
	public PatientLegalRepresentativeAssociationServiceImpl PatientLegalRepresentativeAssociationServiceImpl() {
		PatientLegalRepresentativeAssociationServiceImpl PatientLegalRepresentativeAssociationServiceImpl = new PatientLegalRepresentativeAssociationServiceImpl(
				patientLegalRepresentativeAssociationRepository,
				patientRepository, legalRepresentativeTypeCodeRepository,
				modelMapper(), userContextImpl());
		return PatientLegalRepresentativeAssociationServiceImpl;
	}

	@Bean
	public PatientProfileDtoToPatientMapper patientProfileDtoToPatientMapper() {
		PatientProfileDtoToPatientMapper patientProfileDtoToPatientMapper = new PatientProfileDtoToPatientMapper(
				patientRepository, stateCodeRepository, countryCodeRepository,
				administrativeGenderCodeRepository, languageCodeRepository,
				maritalStatusCodeRepository, raceCodeRepository,
				religiousAffiliationCodeRepository);
		return patientProfileDtoToPatientMapper;
	}

	@Bean
	public PatientServiceImpl patientServiceImpl() {
		PatientServiceImpl patientServiceImpl = new PatientServiceImpl(
				patientRepository,
				patientLegalRepresentativeAssociationRepository, modelMapper(),
				userContextImpl(), patientProfileDtoToPatientMapper(),
				usersRepository, webSecurityConfigurtion.passwordEncoder(),
				emailSenderImpl(), pixServiceImpl(), spiritQueryServiceImpl());
		return patientServiceImpl;
	}

	@Bean
	public PixManagerConstants pixManagerConstants() {
		PixManagerConstants pixManagerConstants = new PixManagerConstants(
				empiDomainId, empiDomainName);
		return pixManagerConstants;
	}

	@Bean
	public PixManagerMessageHelper pixManagerMessageHelper() {
		PixManagerMessageHelper pixManagerMessageHelper = new PixManagerMessageHelper();
		return pixManagerMessageHelper;
	}

	@Bean(autowire = Autowire.BY_TYPE)
	@Scope("prototype")
	public PixManagerRequestXMLToJava pixManagerRequestXMLToJava() {
		PixManagerRequestXMLToJava pixManagerRequestXMLToJava = new PixManagerRequestXMLToJava(
				simpleMarshallerImpl());
		return pixManagerRequestXMLToJava;
	}

	@Bean
	public PixManagerServiceImpl pixManagerServiceImpl() {
		PixManagerServiceImpl pixManagerServiceImpl = new PixManagerServiceImpl(
				empiEndPointAddress);
		return pixManagerServiceImpl;
	}

	@Bean
	public PixServiceImpl pixServiceImpl() {
		PixServiceImpl pixServiceImpl = new PixServiceImpl(pidDomainId,
				pidPrefix, pixManagerRequestXMLToJava(),
				pixManagerServiceImpl(), pixManagerMessageHelper(),
				hl7v3TransformerImpl(), simpleMarshallerImpl());
		return pixServiceImpl;
	}

	@Bean
	public PolicyIdServiceImpl policyIdServiceImpl() {
		PolicyIdServiceImpl policyIdServiceImpl = new PolicyIdServiceImpl(
				pidDomainId, pidDomainType, consentRepository);
		return policyIdServiceImpl;
	}

	@Bean
	public ProviderSearchLookupService providerSearchLookupServiceImpl() {
		ProviderSearchLookupService providerSearchLookupServiceImpl = new ProviderSearchLookupServiceImpl(
				providerSearchURL, stateCodeServiceImpl(), individualProviderServiceImpl(), organizationalProviderServiceImpl(),hashMapResultToProviderDtoConverter() );
		return providerSearchLookupServiceImpl;
	}

	@Bean
	public PurposeOfUseCodeServiceImpl purposeOfUseCodeServiceImpl() {
		PurposeOfUseCodeServiceImpl purposeOfUseCodeServiceImpl = new PurposeOfUseCodeServiceImpl(
				purposeOfUseCodeRepository);
		return purposeOfUseCodeServiceImpl;
	}

	@Bean
	public QueryProxy queryProxy() {
		QueryProxy queryProxy = new QueryProxy();
		return queryProxy;
	}

	@Bean
	public RaceCodeServiceImpl raceCodeServiceImpl() {
		RaceCodeServiceImpl raceCodeServiceImpl = new RaceCodeServiceImpl(
				raceCodeRepository, modelMapper());
		return raceCodeServiceImpl;
	}

	@Bean
	public RecaptchaService recaptchaService() {
		RecaptchaService recaptchaService = new RecaptchaService(
				recaptchaHttpsServer, recaptchaHttpServer,
				recaptchaVerificationServer, recaptchaPrivateKey,
				recaptchaPublicKey);
		return recaptchaService;
	}

	@Bean
	public ReligiousAffiliationCodeServiceImpl religiousAffiliationCodeServiceImpl() {
		ReligiousAffiliationCodeServiceImpl religiousAffiliationCodeServiceImpl = new ReligiousAffiliationCodeServiceImpl(
				religiousAffiliationCodeRepository, modelMapper());
		return religiousAffiliationCodeServiceImpl;
	}

	@Bean
	public RestfulQueryProxy restfulQueryProxy() {
		RestfulQueryProxy restfulQueryProxy = new RestfulQueryProxy();
		return restfulQueryProxy;
	}

	@Bean
	public RevisionListenerImpl revisionListenerImpl() {
		RevisionListenerImpl revisionListenerImpl = new RevisionListenerImpl();
		return revisionListenerImpl;
	}

	@Bean
	public SensitivityPolicyCodeServiceImpl sensitivityPolicyCodeServiceImpl() {
		SensitivityPolicyCodeServiceImpl sensitivityPolicyCodeServiceImpl = new SensitivityPolicyCodeServiceImpl(
				sensitivityPolicyCodeRepository);
		return sensitivityPolicyCodeServiceImpl;
	}

	@Bean
	@Scope("prototype")
	public SimpleMarshallerImpl simpleMarshallerImpl() {
		SimpleMarshallerImpl simpleMarshallerImpl = new SimpleMarshallerImpl();
		return simpleMarshallerImpl;
	}

	@Bean
	public SpiritAdapterImpl spiritAdapter() {
		SpiritAdapterImpl spiritAdapter = new SpiritAdapterImpl(spiritClientEndpointAddress,spiritClientOrg,spiritClientPwd,spiritClientRol,spiritClientUser,empiDomainId,pidDomainId,pidDomainType,pidPrefix);
		return spiritAdapter;
	}

	@Bean
	public SpiritClientHelper spiritClientHelper() {
		SpiritClientHelper spiritClientHelper = new SpiritClientHelper();
		return spiritClientHelper;
	}

	@Bean
	public SpiritQueryServiceImpl spiritQueryServiceImpl() {
		SpiritQueryServiceImpl spiritQueryServiceImpl = new SpiritQueryServiceImpl(spiritAdapter());
		return spiritQueryServiceImpl;
	}

	@Bean(autowire = Autowire.BY_TYPE)
	public SpringContext springContext() {
		SpringContext springContext = new SpringContext();
		return springContext;
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		SpringSecurityDialect springSecurityDialect = new SpringSecurityDialect();
		return springSecurityDialect;
	}

	@Bean
	public StateCodeServicePg stateCodeServiceImpl() {
		StateCodeServicePg stateCodeServiceImpl = new StateCodeServicePgImpl(
				stateCodeRepository, modelMapper());
		return stateCodeServiceImpl;
	}


	@Bean
	public SystemNotificationServiceImpl systemNotificationServiceImpl() {
		SystemNotificationServiceImpl systemNotificationServiceImpl = new SystemNotificationServiceImpl(
				patientRepository, systemNotificationRepository, modelMapper());
		return systemNotificationServiceImpl;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		Set<ITemplateResolver> templateResolvers = new HashSet<ITemplateResolver>();
		templateResolvers.add(templateResolver());
		templateResolvers.add(emailTemplateResolver());

		Set<IDialect> dialects = new HashSet<IDialect>();
		dialects.add(springSecurityDialect());

		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setTemplateResolvers(templateResolvers);
		springTemplateEngine.setAdditionalDialects(dialects);
		return springTemplateEngine;
	}

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/");
		templateResolver.setSuffix(".html");
		;
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setOrder(2);
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		return thymeleafViewResolver;
	}

	@Bean
	public TokenGeneratorUuidImpl tokenGeneratorUuidImpl() {
		TokenGeneratorUuidImpl tokenGeneratorUuidImpl = new TokenGeneratorUuidImpl();
		return tokenGeneratorUuidImpl;
	}

	@Bean
	public TryPolicyServiceImpl tryPolicyServiceImpl() {
		TryPolicyServiceImpl tryPolicyServiceImpl = new TryPolicyServiceImpl(
				c2sTrymypolicyEndPointAddress);
		return tryPolicyServiceImpl;
	}

	@Bean
	public UnauthorizedAccessAttemptedEventListener unauthorizedAccessAttemptedEventListener()
			throws AuditException {
		UnauthorizedAccessAttemptedEventListener unauthorizedAccessAttemptedEventListener = new UnauthorizedAccessAttemptedEventListener(
				eventService(), auditServiceImpl());
		return unauthorizedAccessAttemptedEventListener;
	}

	@Bean
	public UserContextImpl userContextImpl() {
		UserContextImpl userContextImpl = new UserContextImpl(usersRepository,
				patientRepository, providerAdminRepository,
				webSecurityConfigurtion.accountUserDetailsService());
		return userContextImpl;
	}

	@Bean
	public UserCreatedEventListener userCreatedEventListener()
			throws AuditException {
		UserCreatedEventListener userCreatedEventListener = new UserCreatedEventListener(
				eventService(), auditServiceImpl());
		return userCreatedEventListener;
	}

	@Bean
	public UsersRepositoryImpl usersRepositoryImpl() {
		UsersRepositoryImpl usersRepositoryImpl = new UsersRepositoryImpl(
				dataAccessConfig.dataSource());
		return usersRepositoryImpl;
	}

	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
		return lvfb;
	}

	@Bean
	public ValueSetCategoryServiceImpl valueSetCategoryServiceImpl() {
		ValueSetCategoryServiceImpl valueSetCategoryServiceImpl = new ValueSetCategoryServiceImpl(
				valueSetCategoryRepository, consentRepository,
				valueSetMgmtHelper());
		return valueSetCategoryServiceImpl;
	}

	@Bean
	public ValueSetLookupServiceImpl valueSetLookupServiceImpl() {
		ValueSetLookupServiceImpl valueSetLookupServiceImpl = new ValueSetLookupServiceImpl(
				conceptCodeRepository, valueSetRepository,
				codeSystemRepository, codeSystemVersionRepository,
				conceptCodeValueSetRepository, valueSetMgmtHelper());
		return valueSetLookupServiceImpl;
	}

	@Bean
	public ValueSetMgmtHelper valueSetMgmtHelper() {
		ValueSetMgmtHelper ValueSetMgmtHelper = new ValueSetMgmtHelper(
				conceptCodeListPageSize);
		return ValueSetMgmtHelper;
	}

	@Bean
	public ValueSetServiceImpl valueSetServiceImpl() {
		ValueSetServiceImpl valueSetServiceImpl = new ValueSetServiceImpl(
				conceptCodeListPageSize, valueSetRepository,
				valueSetCategoryRepository, conceptCodeValueSetRepository,
				valueSetMgmtHelper());
		return valueSetServiceImpl;
	}

	@Bean
	public XacmlXslUrlProviderImpl xacmlXslUrlProvider() {
		XacmlXslUrlProviderImpl xacmlXslUrlProviderImpl = new XacmlXslUrlProviderImpl();
		return xacmlXslUrlProviderImpl;
	}

	@Bean
	public XmlTransformerImpl xmlTransformerImpl() {
		XmlTransformerImpl xmlTransformerImpl = new XmlTransformerImpl(
				simpleMarshallerImpl());
		return xmlTransformerImpl;
	}


	@Bean
	public ContactServiceImpl contactServiceImpl() {
		ContactServiceImpl contactServiceImpl = new ContactServiceImpl(emailSenderImpl());
		return contactServiceImpl;
	}
}
