/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.service.consent;

import echosign.api.clientv20.dto16.EmbeddedWidgetCreationResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.consent.ConsentBuilder;
import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent2share.common.UniqueValueGeneratorException;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareClinicalDocumentSectionTypeCode;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareClinicalDocumentTypeCode;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareSensitivityPolicyCode;
import gov.samhsa.consent2share.domain.consent.ConsentFactory;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentPdfGenerator;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.consent.ConsentShareForPurposeOfUseCode;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsent;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsentRevocation;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.AbstractProvider;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.IndividualProviderRepository;
import gov.samhsa.consent2share.domain.provider.OrganizationalProvider;
import gov.samhsa.consent2share.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.consent2share.domain.reference.ClinicalConceptCode;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCode;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.consent2share.domain.reference.PurposeOfUseCode;
import gov.samhsa.consent2share.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.consent2share.domain.valueset.MedicalSection;
import gov.samhsa.consent2share.domain.valueset.MedicalSectionRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategory;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.consent2share.infrastructure.AbstractConsentRevokationPdfGenerator;
import gov.samhsa.consent2share.infrastructure.EchoSignSignatureService;
import gov.samhsa.consent2share.infrastructure.TryPolicyService;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentValidationDto;
import gov.samhsa.consent2share.service.dto.SpecificMedicalInfoDto;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;

/**
 * The Class ConsentServiceImpl.
 */
@Transactional
public class ConsentServiceImpl implements ConsentService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The consent repository. */
	private final ConsentRepository consentRepository;

	/** The consent pdf generator. */
	private final ConsentPdfGenerator consentPdfGenerator;

	/** The patient repository. */
	private final PatientRepository patientRepository;

	/** The individual provider repository. */
	private final IndividualProviderRepository individualProviderRepository;

	/** The organizational provider repository. */
	private final OrganizationalProviderRepository organizationalProviderRepository;

	/** The clinical document type code repository. */
	private final ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

	/** The clinical document section type code repository. */
	private final MedicalSectionRepository medicalSectionRepository;

	/** The value set category repository. */
	private final ValueSetCategoryRepository valueSetCategoryRepository;

	/** The purpose of use code repository. */
	private final PurposeOfUseCodeRepository purposeOfUseCodeRepository;

	/** The echo sign signature service. */
	private final EchoSignSignatureService echoSignSignatureService;

	/** The user context. */
	private final UserContext userContext;

	/** The consent revokation pdf generator. */
	private final AbstractConsentRevokationPdfGenerator consentRevokationPdfGenerator;

	/** The consent export service. */
	private final ConsentExportService consentExportService;

	/** Try policy service. */
	private final TryPolicyService tryPolicyService;

	/** The consent builder. */
	private final ConsentBuilder consentBuilder;

	/** The consent factory. */
	private final ConsentFactory consentFactory;

	/** The consent check service. */
	ConsentCheckService consentCheckService;

	/** The consent assertions. */
	private final Set<ConsentAssertion> consentAssertions;

	/** The policy id service. */
	private final PolicyIdService policyIdService;

	/** The c2s domain id. */
	private final String c2sDomainId;

	/** The c2s domain type. */
	private final String c2sDomainType;

	/** The xml transformer. */
	private final XmlTransformer xmlTransformer;

	/** The document xml converter. */
	private final DocumentXmlConverter documentXmlConverter;

	/** The document accessor. */
	private final DocumentAccessor documentAccessor;

	/**
	 * Instantiates a new consent service impl.
	 *
	 * @param c2sDomainId
	 *            the c2s domain id
	 * @param c2sDomainType
	 *            the c2s domain type
	 * @param consentRepository
	 *            the consent repository
	 * @param consentPdfGenerator
	 *            the consent pdf generator
	 * @param patientRepository
	 *            the patient repository
	 * @param individualProviderRepository
	 *            the individual provider repository
	 * @param organizationalProviderRepository
	 *            the organizational provider repository
	 * @param clinicalDocumentTypeCodeRepository
	 *            the clinical document type code repository
	 * @param medicalSectionRepository
	 *            the medical section repository
	 * @param valueSetCategoryRepository
	 *            the value set category repository
	 * @param purposeOfUseCodeRepository
	 *            the purpose of use code repository
	 * @param echoSignSignatureService
	 *            the echo sign signature service
	 * @param userContext
	 *            the user context
	 * @param consentRevokationPdfGenerator
	 *            the consent revokation pdf generator
	 * @param consentExportService
	 *            the consent export service
	 * @param tryPolicyService
	 *            the try policy service
	 * @param consentBuilder
	 *            the consent builder
	 * @param consentFactory
	 *            the consent factory
	 * @param consentCheckService
	 *            the consent check service
	 * @param consentAssertions
	 *            the consent assertions
	 * @param policyIdService
	 *            the policy id service
	 * @param xmlTransformer
	 *            the xml transformer
	 * @param documentXmlConverter
	 *            the document xml converter
	 * @param documentAccessor
	 *            the document accessor
	 */
	public ConsentServiceImpl(
			String c2sDomainId,
			String c2sDomainType,
			ConsentRepository consentRepository,
			ConsentPdfGenerator consentPdfGenerator,
			PatientRepository patientRepository,
			IndividualProviderRepository individualProviderRepository,
			OrganizationalProviderRepository organizationalProviderRepository,
			ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository,
			MedicalSectionRepository medicalSectionRepository,
			ValueSetCategoryRepository valueSetCategoryRepository,
			PurposeOfUseCodeRepository purposeOfUseCodeRepository,
			EchoSignSignatureService echoSignSignatureService,
			UserContext userContext,
			AbstractConsentRevokationPdfGenerator consentRevokationPdfGenerator,
			ConsentExportService consentExportService,
			TryPolicyService tryPolicyService, ConsentBuilder consentBuilder,
			ConsentFactory consentFactory,
			ConsentCheckService consentCheckService,
			Set<ConsentAssertion> consentAssertions,
			PolicyIdService policyIdService, XmlTransformer xmlTransformer,
			DocumentXmlConverter documentXmlConverter,
			DocumentAccessor documentAccessor) {
		super();
		this.c2sDomainId = c2sDomainId;
		this.c2sDomainType = c2sDomainType;
		this.consentRepository = consentRepository;
		this.consentPdfGenerator = consentPdfGenerator;
		this.patientRepository = patientRepository;
		this.individualProviderRepository = individualProviderRepository;
		this.organizationalProviderRepository = organizationalProviderRepository;
		this.clinicalDocumentTypeCodeRepository = clinicalDocumentTypeCodeRepository;
		this.medicalSectionRepository = medicalSectionRepository;
		this.valueSetCategoryRepository = valueSetCategoryRepository;
		this.purposeOfUseCodeRepository = purposeOfUseCodeRepository;
		this.echoSignSignatureService = echoSignSignatureService;
		this.userContext = userContext;
		this.consentRevokationPdfGenerator = consentRevokationPdfGenerator;
		this.consentExportService = consentExportService;
		this.tryPolicyService = tryPolicyService;
		this.consentBuilder = consentBuilder;
		this.consentFactory = consentFactory;
		this.consentCheckService = consentCheckService;
		this.consentAssertions = consentAssertions;
		this.policyIdService = policyIdService;
		this.xmlTransformer = xmlTransformer;
		this.documentXmlConverter = documentXmlConverter;
		this.documentAccessor = documentAccessor;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.service.consent.ConsentService#
	 * addUnsignedConsentRevokationPdf(java.lang.Long, java.lang.String)
	 */
	@Override
	public void addUnsignedConsentRevokationPdf(Long consentId,
			String revokationType) {
		final Consent consent = consentRepository.findOne(consentId);
		if (revokationType.equals("EMERGENCY ONLY")) {
			consent.setConsentRevokationType("EMERGENCY ONLY");
		} else if (revokationType.equals("NO NEVER")) {
			consent.setConsentRevokationType("NO NEVER");
		}
		consent.setUnsignedPdfConsentRevoke(consentRevokationPdfGenerator
				.generateConsentRevokationPdf(consent));
		consentRepository.save(consent);
	}

	/**
	 * Are there duplicates.
	 *
	 * @param set1
	 *            the set1
	 * @param set2
	 *            the set2
	 * @return true, if successful
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean areThereDuplicatesInTwoSets(Set set1, Set set2) {
		final Set all = new HashSet();
		all.addAll(set1);
		all.addAll(set2);
		if (all.size() == set1.size() + set2.size()) {
			return false;
		}
		return true;
	}

	/**
	 * Consent list to consent list dtos converter.
	 *
	 * @param consents
	 *            the consents
	 * @return the list
	 */
	public List<ConsentListDto> consentListToConsentListDtosConverter(
			List<Consent> consents) {
		final List<ConsentListDto> consentListDtos = makeConsentListDtos();
		for (final Consent consent : consents) {
			final ConsentListDto consentListDto = new ConsentListDto();
			// Get fields
			final Set<String> isMadeToName = new HashSet<String>();
			for (final ConsentIndividualProviderDisclosureIsMadeTo item : consent
					.getProvidersDisclosureIsMadeTo()) {
				final String name = item.getIndividualProvider().getLastName()
						+ ", " + item.getIndividualProvider().getFirstName();
				isMadeToName.add(name);
			}
			final Set<String> isMadeToOrgName = new HashSet<String>();
			for (final ConsentOrganizationalProviderDisclosureIsMadeTo item : consent
					.getOrganizationalProvidersDisclosureIsMadeTo()) {
				isMadeToOrgName.add(item.getOrganizationalProvider()
						.getOrgName());
			}

			final Set<String> toDiscloseName = new HashSet<String>();
			for (final ConsentIndividualProviderPermittedToDisclose item : consent
					.getProvidersPermittedToDisclose()) {
				final String name = item.getIndividualProvider().getLastName()
						+ ", " + item.getIndividualProvider().getFirstName();
				toDiscloseName.add(name);
			}

			final Set<String> toDiscloseOrgName = new HashSet<String>();
			for (final ConsentOrganizationalProviderPermittedToDisclose item : consent
					.getOrganizationalProvidersPermittedToDisclose()) {
				toDiscloseOrgName.add(item.getOrganizationalProvider()
						.getOrgName());
			}

			final Set<String> consentDoNotShareClinicalDocumentTypeCode = new HashSet<String>();
			for (final ConsentDoNotShareClinicalDocumentTypeCode item : consent
					.getDoNotShareClinicalDocumentTypeCodes()) {
				consentDoNotShareClinicalDocumentTypeCode.add(item
						.getClinicalDocumentTypeCode().getDisplayName());
			}

			final Set<String> consentDoNotShareClinicalDocumentSectionTypeCode = new HashSet<String>();
			for (final ConsentDoNotShareClinicalDocumentSectionTypeCode item : consent
					.getDoNotShareClinicalDocumentSectionTypeCodes()) {
				consentDoNotShareClinicalDocumentSectionTypeCode.add(item
						.getMedicalSection().getName());
			}

			final Set<String> consentDoNotShareSensitivityPolicyCode = new HashSet<String>();
			for (final ConsentDoNotShareSensitivityPolicyCode item : consent
					.getDoNotShareSensitivityPolicyCodes()) {
				consentDoNotShareSensitivityPolicyCode.add(item
						.getValueSetCategory().getName());
			}

			final Set<String> consentShareForPurposeOfUseCode = new HashSet<String>();
			for (final ConsentShareForPurposeOfUseCode item : consent
					.getShareForPurposeOfUseCodes()) {
				consentShareForPurposeOfUseCode.add(item.getPurposeOfUseCode()
						.getDisplayName());
			}

			final Set<String> consentDoNotShareClinicalConceptCodes = new HashSet<String>();
			for (final ClinicalConceptCode item : consent
					.getDoNotShareClinicalConceptCodes()) {
				consentDoNotShareClinicalConceptCodes
				.add(item.getDisplayName());
			}

			if (consent.getSignedPdfConsent() != null) {
				if (consent.getSignedPdfConsent().getDocumentSignedStatus()
						.equals("SIGNED")) {
					consentListDto.setConsentStage("CONSENT_SIGNED");
				} else {
					consentListDto.setConsentStage("CONSENT_SAVED");
				}
			} else {
				consentListDto.setConsentStage("CONSENT_SAVED");
			}

			if (!consentListDto.getConsentStage().equals("CONSENT_SIGNED")) {
				consentListDto.setRevokeStage("NA");
			} else {
				if (consent.getSignedPdfConsentRevoke() != null) {
					if (consent.getSignedPdfConsentRevoke()
							.getDocumentSignedStatus().equals("SIGNED")) {
						consentListDto.setRevokeStage("REVOCATION_REVOKED");
					} else {
						consentListDto
						.setRevokeStage("REVOCATION_NOT_SUBMITTED");
					}
				} else {
					consentListDto.setRevokeStage("REVOCATION_NOT_SUBMITTED");
				}
			}

			// Set fields
			isMadeToName.addAll(isMadeToOrgName);
			toDiscloseName.addAll(toDiscloseOrgName);
			consentListDto
			.setDoNotShareClinicalConceptCodes(consentDoNotShareClinicalConceptCodes);
			consentListDto.setId(Long.toString(consent.getId()));
			consentListDto.setIsMadeToName(isMadeToName);
			consentListDto.setToDiscloseName(toDiscloseName);
			consentListDto
			.setDoNotShareClinicalDocumentSectionTypeCodes(consentDoNotShareClinicalDocumentSectionTypeCode);
			consentListDto
			.setDoNotShareClinicalDocumentTypeCodes(consentDoNotShareClinicalDocumentTypeCode);
			consentListDto
			.setShareForPurposeOfUseCodes(consentShareForPurposeOfUseCode);
			consentListDto
			.setDoNotShareSensitivityPolicyCodes(consentDoNotShareSensitivityPolicyCode);

			consentListDto.setConsentStart(consent.getStartDate());
			consentListDto.setConsentEnd(consent.getEndDate());

			// Merge all Dtos
			consentListDtos.add(consentListDto);
		}
		return consentListDtos;
	}

	/**
	 * Count all consents.
	 *
	 * @return the long
	 */
	@Override
	public long countAllConsents() {
		return consentRepository.count();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.service.consent.ConsentService#
	 * createConsentEmbeddedWidget
	 * (gov.samhsa.consent2share.service.dto.ConsentPdfDto)
	 */
	@Override
	public String createConsentEmbeddedWidget(ConsentPdfDto consentPdfDto) {
		final Consent consent = consentRepository
				.findOne(consentPdfDto.getId());
		// SignConsent
		final SignedPDFConsent signedPdfConsent = makeSignedPdfConsent();
		final Patient patient = consent.getPatient();
		final String patientEmail = patient.getEmail();

		final EmbeddedWidgetCreationResult result = echoSignSignatureService
				.createEmbeddedWidget(consentPdfDto.getContent(),
						consentPdfDto.getFilename(),
						consentPdfDto.getConsentName(), null, patientEmail);
		signedPdfConsent.setDocumentId(result.getDocumentKey());
		signedPdfConsent
		.setDocumentNameBySender(consentPdfDto.getConsentName());
		signedPdfConsent
		.setDocumentMessageBySender("This is a hard-coded greeting to be replaced. Hi.");
		signedPdfConsent.setSignerEmail(patientEmail);
		signedPdfConsent.setDocumentSignedStatus("Unsigned");
		consent.setSignedPdfConsent(signedPdfConsent);
		consentRepository.save(consent);
		return result.getJavascript();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.service.consent.ConsentService#
	 * createRevocationEmbeddedWidget
	 * (gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto)
	 */
	@Override
	public String createRevocationEmbeddedWidget(
			ConsentRevokationPdfDto consentRevokationPdfDto) {
		final Consent consent = consentRepository
				.findOne(consentRevokationPdfDto.getId());
		// SignConsentRevokation
		final SignedPDFConsentRevocation signedPDFConsentRevocation = makeSignedPDFConsentRevocation();
		final Patient patient = consent.getPatient();
		final String patientEmail = patient.getEmail();

		final EmbeddedWidgetCreationResult result = echoSignSignatureService
				.createEmbeddedWidget(consentRevokationPdfDto.getContent(),
						consentRevokationPdfDto.getFilename(),
						consentRevokationPdfDto.getConsentName()
						+ " Revocation", null, patientEmail);
		signedPDFConsentRevocation.setDocumentId(result.getDocumentKey());
		signedPDFConsentRevocation
		.setDocumentNameBySender(consentRevokationPdfDto
				.getConsentName());
		signedPDFConsentRevocation
		.setDocumentMessageBySender("This is a hard-coded greeting to be replaced. Hi.");
		signedPDFConsentRevocation.setSignerEmail("consent2share@gmail.com");
		signedPDFConsentRevocation.setDocumentSignedStatus("Unsigned");
		signedPDFConsentRevocation.setDocumentCreatedBy(consent.getPatient()
				.getLastName() + ", " + consent.getPatient().getFirstName());
		signedPDFConsentRevocation
		.setDocumentSentOutForSignatureDateTime(new Date());
		consent.setConsentRevoked(true);
		consent.setSignedPdfConsentRevoke(signedPDFConsentRevocation);

		if (consentRevokationPdfDto.getRevokationType()
				.equals("EMERGENCY ONLY")) {
			consent.setConsentRevokationType("EMERGENCY ONLY");
		}
		if (consentRevokationPdfDto.getRevokationType().equals("NO NEVER")) {
			consent.setConsentRevokationType("NO NEVER");
		}

		consentRepository.save(consent);
		return result.getJavascript();
	}

	/**
	 * Delete consent.
	 *
	 * @param consent
	 *            the consent
	 */
	@Override
	public void deleteConsent(Consent consent) {
		consentRepository.delete(consent);
	}

	/**
	 * Delete consent.
	 *
	 * @param consentId
	 *            the consent id
	 * @return true, if successful
	 */
	@Override
	public boolean deleteConsent(Long consentId) {
		Consent consent = null;

		try {
			consent = findConsent(consentId);
		} catch (final IllegalArgumentException e) {
			logger.warn("Attempted to call findConsent(consentId) with null or invalid consentId from deleteConsent(Long consentId) method in ConsentService.");
			logger.warn("The exception stack trace is: " + e);
			return false;
		}

		if (consent.getSignedPdfConsent() != null) {
			if (consent.getSignedPdfConsent().getSignedPdfConsentContent() != null) {
				return false;
			}
		}

		try {
			consentRepository.delete(consent);
		} catch (final IllegalArgumentException e) {
			logger.warn("Attempted to call consentRepository.delete(consent) with null or invalid consent from deleteConsent(Long consentId) method in ConsentService.");
			logger.warn("The exception stack trace is: " + e);
			return false;
		}

		return true;
	}

	/**
	 * Find all consents.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Consent> findAllConsents() {
		return consentRepository.findAll();
	}

	/**
	 * Find all consents dto by patient.
	 *
	 * @param patientId
	 *            the patient id
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ConsentListDto> findAllConsentsDtoByPatient(Long patientId) {
		final Patient patient = patientRepository.findOne(patientId);
		final List<Consent> consents = consentRepository.findByPatient(patient);
		List<ConsentListDto> consentListDtos = makeConsentListDtos();
		if (consents != null) {
			consentListDtos = consentListToConsentListDtosConverter(consents);
		}
		return consentListDtos;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.service.consent.ConsentService#
	 * findAllConsentsDtoByUserName(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ConsentListDto> findAllConsentsDtoByUserName(String userName) {
		final Patient patient = patientRepository.findByUsername(userName);
		final List<Consent> consents = consentRepository.findByPatient(patient);
		List<ConsentListDto> consentListDtos = makeConsentListDtos();
		if (consents != null) {
			consentListDtos = consentListToConsentListDtosConverter(consents);
		}
		return consentListDtos;
	}

	/**
	 * Find consent.
	 *
	 * @param id
	 *            the id
	 * @return the consent
	 */
	@Override
	@Transactional(readOnly = true)
	public Consent findConsent(Long id) {
		final Consent consent = consentRepository.findOne(id);
		return consent;
	}

	/**
	 * Returns consentDto based on consent id.
	 *
	 * @param consentId
	 *            the consent id
	 * @return ConsentDto
	 */
	@Override
	@Transactional(readOnly = true)
	public ConsentDto findConsentById(Long consentId) {
		Consent consent = null;
		ConsentDto consentDto = null;

		if (consentRepository.findOne(consentId) != null) {
			consent = consentRepository.findOne(consentId);

			consentDto = new ConsentDto();
			// Get fields
			final Set<String> isMadeToName = new HashSet<String>();
			final Set<String> isMadeToNpi = new HashSet<String>();
			for (final ConsentIndividualProviderDisclosureIsMadeTo item : consent
					.getProvidersDisclosureIsMadeTo()) {
				final String name = item.getIndividualProvider().getLastName()
						+ ", " + item.getIndividualProvider().getFirstName();
				isMadeToName.add(name);
				isMadeToNpi.add(item.getIndividualProvider().getNpi());
			}

			final Set<String> isMadeToOrgName = new HashSet<String>();
			final Set<String> isMadeToOrgNpi = new HashSet<String>();
			for (final ConsentOrganizationalProviderDisclosureIsMadeTo item : consent
					.getOrganizationalProvidersDisclosureIsMadeTo()) {
				isMadeToOrgName.add(item.getOrganizationalProvider()
						.getOrgName());
				isMadeToOrgNpi.add(item.getOrganizationalProvider().getNpi());
			}

			final Set<String> toDiscloseName = new HashSet<String>();
			final Set<String> toDiscloseNpi = new HashSet<String>();
			for (final ConsentIndividualProviderPermittedToDisclose item : consent
					.getProvidersPermittedToDisclose()) {
				final String name = item.getIndividualProvider().getLastName()
						+ ", " + item.getIndividualProvider().getFirstName();
				toDiscloseName.add(name);
				toDiscloseNpi.add(item.getIndividualProvider().getNpi());
			}

			final Set<String> toDiscloseOrgName = new HashSet<String>();
			final Set<String> toDiscloseOrgNpi = new HashSet<String>();
			for (final ConsentOrganizationalProviderPermittedToDisclose item : consent
					.getOrganizationalProvidersPermittedToDisclose()) {
				toDiscloseOrgName.add(item.getOrganizationalProvider()
						.getOrgName());
				toDiscloseOrgNpi.add(item.getOrganizationalProvider().getNpi());
			}

			final Set<String> consentDoNotShareClinicalDocumentTypeCode = new HashSet<String>();
			for (final ConsentDoNotShareClinicalDocumentTypeCode item : consent
					.getDoNotShareClinicalDocumentTypeCodes()) {
				consentDoNotShareClinicalDocumentTypeCode.add(item
						.getClinicalDocumentTypeCode().getDisplayName());
			}

			final Set<String> consentDoNotShareClinicalDocumentSectionTypeCode = new HashSet<String>();
			for (final ConsentDoNotShareClinicalDocumentSectionTypeCode item : consent
					.getDoNotShareClinicalDocumentSectionTypeCodes()) {
				consentDoNotShareClinicalDocumentSectionTypeCode.add(item
						.getMedicalSection().getName());
			}

			final Set<String> consentDoNotShareSensitivityPolicyCode = new HashSet<String>();
			for (final ConsentDoNotShareSensitivityPolicyCode item : consent
					.getDoNotShareSensitivityPolicyCodes()) {
				consentDoNotShareSensitivityPolicyCode.add(item
						.getValueSetCategory().getName());
			}

			final Set<String> consentShareForPurposeOfUseCode = new HashSet<String>();
			for (final ConsentShareForPurposeOfUseCode item : consent
					.getShareForPurposeOfUseCodes()) {
				consentShareForPurposeOfUseCode.add(item.getPurposeOfUseCode()
						.getDisplayName());
			}

			final Set<SpecificMedicalInfoDto> consentDoNotShareClinicalConceptCodes = new HashSet<SpecificMedicalInfoDto>();
			for (final ClinicalConceptCode item : consent
					.getDoNotShareClinicalConceptCodes()) {
				final SpecificMedicalInfoDto specificMedicalInfoDto = new SpecificMedicalInfoDto();
				specificMedicalInfoDto.setCode(item.getCode());
				specificMedicalInfoDto.setCodeSystem(item.getCodeSystem());
				specificMedicalInfoDto.setDisplayName(item.getDisplayName());
				consentDoNotShareClinicalConceptCodes
				.add(specificMedicalInfoDto);
			}

			// Set fields
			// isMadeToName.addAll(isMadeToOrgName);
			// toDiscloseName.addAll(toDiscloseOrgName);
			consentDto
			.setDoNotShareClinicalConceptCodes(consentDoNotShareClinicalConceptCodes);
			consentDto.setId(String.valueOf(consent.getId()));
			consentDto.setUsername(consent.getPatient().getUsername());

			//
			// Converting timestamp to Date to resolve
			// rendering the values in IE
			//
			final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String today = "";
			try {
				today = formatter.format(consent.getStartDate());
				consentDto.setConsentStart(formatter.parse(today));
			} catch (final ParseException e) {
				consentDto.setConsentStart(consent.getStartDate());
				e.printStackTrace();
			}
			try {
				today = formatter.format(consent.getEndDate());
				consentDto.setConsentEnd(formatter.parse(today));
			} catch (final ParseException e) {
				consentDto.setConsentEnd(consent.getEndDate());
				e.printStackTrace();
			}

			// TODO: Cleanup: combine name and npi into one object
			// populate consent dto with selected options
			consentDto
			.setOrganizationalProvidersDisclosureIsMadeTo(isMadeToOrgName);
			consentDto
			.setOrganizationalProvidersDisclosureIsMadeToNpi(isMadeToOrgNpi);

			consentDto
			.setOrganizationalProvidersPermittedToDisclose(toDiscloseOrgName);
			consentDto
			.setOrganizationalProvidersPermittedToDiscloseNpi(toDiscloseOrgNpi);

			consentDto.setProvidersDisclosureIsMadeTo(isMadeToName);
			consentDto.setProvidersDisclosureIsMadeToNpi(isMadeToNpi);

			consentDto.setProvidersPermittedToDisclose(toDiscloseName);
			consentDto.setProvidersPermittedToDiscloseNpi(toDiscloseNpi);

			consentDto
			.setDoNotShareClinicalDocumentSectionTypeCodes(consentDoNotShareClinicalDocumentSectionTypeCode);
			consentDto
			.setDoNotShareClinicalDocumentTypeCodes(consentDoNotShareClinicalDocumentTypeCode);
			consentDto
			.setShareForPurposeOfUseCodes(consentShareForPurposeOfUseCode);
			consentDto
			.setDoNotShareSensitivityPolicyCodes(consentDoNotShareSensitivityPolicyCode);

			final HashMap<String, String> purposeOfUseMap = new HashMap<String, String>();
			for (final ConsentShareForPurposeOfUseCode purposeOfUseCode : consent
					.getShareForPurposeOfUseCodes()) {
				purposeOfUseMap.put(purposeOfUseCode.getPurposeOfUseCode()
						.getCode(), purposeOfUseCode.getPurposeOfUseCode()
						.getDisplayName());
			}
			consentDto.setPurposeOfUseCodesAndValues(purposeOfUseMap);

		}
		return consentDto;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.service.consent.ConsentService#findConsentContentDto
	 * (java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public AbstractPdfDto findConsentContentDto(Long consentId) {
		final Consent consent = findConsent(consentId);
		AbstractPdfDto consentPdfDto;
		if (consent.getSignedPdfConsent() != null) {
			if (consent.getSignedPdfConsent().getSignedPdfConsentContent() != null) {
				consentPdfDto = findConsentPdfDto(consent.getId());
			} else {
				consentPdfDto = findConsentPdfDto(consent.getId());
			}
		} else {
			consentPdfDto = findConsentPdfDto(consent.getId());
		}

		if (consent.getSignedPdfConsentRevoke() != null) {
			if (consent.getSignedPdfConsentRevoke()
					.getSignedPdfConsentRevocationContent() != null) {
				consentPdfDto = findConsentRevokationPdfDto(consent.getId());
			} else {
				consentPdfDto = findConsentRevokationPdfDto(consent.getId());
			}

		}
		return consentPdfDto;

	}

	/**
	 * Find consent entries.
	 *
	 * @param firstResult
	 *            the first result
	 * @param maxResults
	 *            the max results
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Consent> findConsentEntries(int firstResult, int maxResults) {
		return consentRepository.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	/**
	 * Find consent pdf dto.
	 *
	 * @param consentId
	 *            the consent id
	 * @return the consent pdf dto
	 */
	@Override
	@Transactional(readOnly = true)
	public ConsentPdfDto findConsentPdfDto(Long consentId) {
		final Consent consent = consentRepository.findOne(consentId);
		final ConsentPdfDto consentPdfDto = makeConsentPdfDto();
		if (consent.getSignedPdfConsent() != null) {
			if (consent.getSignedPdfConsent().getSignedPdfConsentContent() != null) {
				consentPdfDto.setContent(consent.getSignedPdfConsent()
						.getSignedPdfConsentContent());
			} else {
				consentPdfDto.setContent(consent.getUnsignedPdfConsent());
			}
		} else {
			consentPdfDto.setContent(consent.getUnsignedPdfConsent());
		}
		consentPdfDto.setFilename(consent.getPatient().getFirstName() + "_"
				+ consent.getPatient().getLastName() + "_Consent"
				+ consent.getId() + ".pdf");
		consentPdfDto.setConsentName(consent.getName());
		consentPdfDto.setId(consentId);
		return consentPdfDto;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.service.consent.ConsentService#
	 * findConsentRevokationPdfDto(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public ConsentRevokationPdfDto findConsentRevokationPdfDto(Long consentId) {
		final Consent consent = consentRepository.findOne(consentId);
		final ConsentRevokationPdfDto consentRevokationPdfDto = makeConsentRevokationPdfDto();
		if (consent.getSignedPdfConsentRevoke() != null) {
			if (consent.getSignedPdfConsentRevoke()
					.getSignedPdfConsentRevocationContent() != null) {
				consentRevokationPdfDto.setContent(consent
						.getSignedPdfConsentRevoke()
						.getSignedPdfConsentRevocationContent());
			} else {
				consentRevokationPdfDto.setContent(consent
						.getUnsignedPdfConsentRevoke());
			}
		} else {
			consentRevokationPdfDto.setContent(consent
					.getUnsignedPdfConsentRevoke());
		}
		consentRevokationPdfDto.setFilename(consent.getPatient().getFirstName()
				+ "_" + consent.getPatient().getLastName()
				+ "_ConsentRevokation" + consent.getId() + ".pdf");
		consentRevokationPdfDto.setConsentName(consent.getName());
		consentRevokationPdfDto.setId(consent.getId());
		return consentRevokationPdfDto;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.service.consent.ConsentService#getConsentSignedStage
	 * (java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public String getConsentSignedStage(Long consentId) {
		String signStatus = "NA";
		final Consent consent = consentRepository.findOne(consentId);
		if (consent.getSignedPdfConsent() != null) {
			if (consent.getSignedPdfConsent().getDocumentSignedStatus()
					.equals("SIGNED")) {
				signStatus = "CONSENT_SIGNED";
			} else {
				signStatus = "CONSENT_SAVED";
			}
		} else {
			signStatus = "CONSENT_SAVED";
		}
		return signStatus;
	}

	/**
	 * Gets the ids.
	 *
	 * @param c32List
	 *            the c32 list
	 * @return the ids
	 */
	public List<String> getIds(NodeList c32List) {
		final List<String> listOfIdsInC32 = new ArrayList<String>();

		for (int i = 0; i < c32List.getLength(); i++) {

			final NodeList childNodes = c32List.item(i).getChildNodes();

			for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
				final Node childNode = childNodes.item(childIndex);

				if (childNode.getNodeType() == Node.ELEMENT_NODE) {

					final NodeList grandChildNodeList = childNode
							.getChildNodes();

					for (int grandChildIndex = 0; grandChildIndex < grandChildNodeList
							.getLength(); grandChildIndex++) {
						final Node grandChildNode = grandChildNodeList
								.item(grandChildIndex);

						if (grandChildNode.getNodeName().equalsIgnoreCase("ID")) {
							listOfIdsInC32.add(grandChildNode.getAttributes()
									.getNamedItem("root").getNodeValue());
						}

					}
				}
			}
		}
		return listOfIdsInC32;
	}

	/**
	 * Gets the ids to tag.
	 *
	 * @param originalC32Ids
	 *            the original c32 ids
	 * @param segmentedC32Ids
	 *            the segmented c32 ids
	 * @return the ids to tag
	 */
	public List<String> getIdsToTag(List<String> originalC32Ids,
			List<String> segmentedC32Ids) {
		final List<String> idsToTag = new ArrayList<String>();

		for (int i = 0; i < originalC32Ids.size(); i++) {
			if (!segmentedC32Ids.contains(originalC32Ids.get(i))) {
				idsToTag.add(originalC32Ids.get(i));
			}
		}

		return idsToTag;
	}

	/**
	 * Return tagged c32. Entry tags that have been removed in segmented c32
	 * were tagged and returned
	 *
	 * @param originalC32
	 *            the original c32
	 * @param consentId
	 *            the consent id
	 * @param purposeOfUse
	 *            the purpose of use
	 * @return the tagged c32
	 * @throws ConsentGenException
	 *             the consent gen exception
	 */
	@Override
	public String getTaggedC32(String originalC32, Long consentId,
			String purposeOfUse) throws ConsentGenException {

		final String xacml = findConsentXACMLById(consentId);

		// get segmented doc
		final String segmentedC32 = tryPolicyService.tryPolicy(originalC32,
				xacml, purposeOfUse);

		try {
			final Document taggedC32Doc = documentXmlConverter
					.loadDocument(segmentedC32);
			changeXslPath(taggedC32Doc);

			final NodeList taggedC32List = taggedC32Doc
					.getElementsByTagName("entry");

			final Document segmentedC32Doc = documentXmlConverter
					.loadDocument(segmentedC32);
			final NodeList segmentedC32List = segmentedC32Doc
					.getElementsByTagName("entry");

			logger.info("Original C32: " + originalC32);
			logger.info("Segmented C32: " + segmentedC32);

			logger.info("Tagged C32 Entry size: " + taggedC32List.getLength());
			logger.info("Segmented C32 Entry size: "
					+ segmentedC32List.getLength());

			// xslt transformation
			final String xslUrl = Thread.currentThread()
					.getContextClassLoader().getResource("CDA_flag_redact.xsl")
					.toString();

			final String output = xmlTransformer.transform(taggedC32Doc,
					xslUrl, Optional.empty(), Optional.empty());

			logger.info("Printing transformed xslt: " + output);
			return output;

		} catch (final Exception e) {
			throw new DS4PException(e.toString(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.service.consent.ConsentService#getXacmlCcd(java
	 * .lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public byte[] getXacmlCcd(Long consentId) {
		return consentRepository.findOne(consentId).getXacmlCcd();
	}

	/**
	 * Checks if is consent belong to this user.
	 *
	 * @param consentId
	 *            the consent id
	 * @param patientId
	 *            the patient id
	 * @return true, if is consent belong to this user
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean isConsentBelongToThisUser(Long consentId, Long patientId) {
		if (consentRepository.findOne(consentId) != null) {
			final Consent consent1 = consentRepository.findOne(consentId);
			final Patient patient = patientRepository.findOne(patientId);

			return consent1.getPatient().equals(patient);
		} else {
			return false;
		}
	}

	/**
	 * Make consent.
	 *
	 * @return the consent
	 */
	@Override
	public Consent makeConsent() {
		return new Consent();
	}

	/**
	 * Make consent dto.
	 *
	 * @return the consent dto
	 */
	@Override
	public ConsentDto makeConsentDto() {
		return new ConsentDto();
	}

	/**
	 * Make consent list dtos.
	 *
	 * @return the array list
	 */
	@Override
	public ArrayList<ConsentListDto> makeConsentListDtos() {
		return new ArrayList<ConsentListDto>();
	}

	/**
	 * Make consent pdf dto.
	 *
	 * @return the consent pdf dto
	 */
	@Override
	public ConsentPdfDto makeConsentPdfDto() {
		return new ConsentPdfDto();
	}

	/**
	 * Make consent revokation pdf dto.
	 *
	 * @return the consent revokation pdf dto
	 */
	@Override
	public ConsentRevokationPdfDto makeConsentRevokationPdfDto() {
		return new ConsentRevokationPdfDto();
	}

	/**
	 * Make signed pdf consent.
	 *
	 * @return the signed pdf consent
	 */
	@Override
	public SignedPDFConsent makeSignedPdfConsent() {
		return new SignedPDFConsent();
	}

	/**
	 * Make signed pdf consent revocation.
	 *
	 * @return the signed pdf consent revocation
	 */
	@Override
	public SignedPDFConsentRevocation makeSignedPDFConsentRevocation() {
		return new SignedPDFConsentRevocation();
	}

	/**
	 * Save consent.
	 *
	 * @param consent
	 *            the consent
	 */
	@Override
	public void saveConsent(Consent consent) {
		consentRepository.save(consent);
	}

	/**
	 * Save consent.
	 *
	 * @param consentDto
	 *            the consent dto
	 * @param patientId
	 *            the patient id
	 * @return the object
	 * @throws ConsentGenException
	 *             the consent gen exception
	 */
	@Override
	public Object saveConsent(ConsentDto consentDto, long patientId)
			throws ConsentGenException {
		// Assert consentDto
		consentAssertions.forEach(assertion -> assertion
				.assertConsentDto(consentDto));

		// check for duplicate policy
		consentDto.setPatientId(patientId);
		final ConsentValidationDto consentValidationDto = consentCheckService
				.getConflictConsent(consentDto);
		if (null != consentValidationDto) {
			logger.debug("duplicate policy found");
			return consentValidationDto;
		}
		Consent consent = makeConsent();
		Patient patient;
		if (patientId != 0) {
			patient = patientRepository.findOne(patientId);
		} else {
			patient = patientRepository
					.findByUsername(consentDto.getUsername());
		}
		final Map<String, AbstractProvider> providerMap = new HashMap<String, AbstractProvider>();
		for (final IndividualProvider o : patient.getIndividualProviders()) {
			providerMap.put(o.getNpi(), o);
		}
		for (final OrganizationalProvider o : patient
				.getOrganizationalProviders()) {
			providerMap.put(o.getNpi(), o);
		}
		if (consentDto.getId() != null
				&& Long.parseLong(consentDto.getId()) > 0) {
			consent = consentRepository.findOne(Long.parseLong(consentDto
					.getId()));
		}

		// Build the updated policy id and set it
		final String policyId = generatePolicyId(consentDto,
				patient.getMedicalRecordNumber());

		consent.setConsentReferenceId(policyId);

		// Set Providers
		if (consentDto.getProvidersDisclosureIsMadeTo() != null) {
			final Set<ConsentIndividualProviderDisclosureIsMadeTo> providersDisclosureIsMadeTo = new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>();
			for (final String item : consentDto
					.getProvidersDisclosureIsMadeTo()) {
				final IndividualProvider individualProvider = (IndividualProvider) providerMap
						.get(item);
				final ConsentIndividualProviderDisclosureIsMadeTo consentIndividualProviderPermittedToDisclose = new ConsentIndividualProviderDisclosureIsMadeTo(
						individualProvider);
				providersDisclosureIsMadeTo
				.add(consentIndividualProviderPermittedToDisclose);
			}
			consent.setProvidersDisclosureIsMadeTo(providersDisclosureIsMadeTo);
		} else {
			consent.setProvidersDisclosureIsMadeTo(new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>());
		}

		if (consentDto.getProvidersPermittedToDisclose() != null) {
			final Set<ConsentIndividualProviderPermittedToDisclose> providersPermittedToDisclose = new HashSet<ConsentIndividualProviderPermittedToDisclose>();
			for (final String item : consentDto
					.getProvidersPermittedToDisclose()) {
				final IndividualProvider individualProvider = (IndividualProvider) providerMap
						.get(item);
				final ConsentIndividualProviderPermittedToDisclose consentIndividualProviderPermittedToDisclose = new ConsentIndividualProviderPermittedToDisclose(
						individualProvider);
				providersPermittedToDisclose
				.add(consentIndividualProviderPermittedToDisclose);
			}
			consent.setProvidersPermittedToDisclose(providersPermittedToDisclose);
		} else {
			consent.setProvidersPermittedToDisclose(new HashSet<ConsentIndividualProviderPermittedToDisclose>());
		}

		if (consentDto.getOrganizationalProvidersDisclosureIsMadeTo() != null) {
			final Set<ConsentOrganizationalProviderDisclosureIsMadeTo> organizationalProvidersDisclosureIsMadeTo = new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>();
			for (final String item : consentDto
					.getOrganizationalProvidersDisclosureIsMadeTo()) {
				final OrganizationalProvider organizationalProvider = (OrganizationalProvider) providerMap
						.get(item);
				final ConsentOrganizationalProviderDisclosureIsMadeTo consentOrganizationalProviderPermittedToDisclose = new ConsentOrganizationalProviderDisclosureIsMadeTo(
						organizationalProvider);
				organizationalProvidersDisclosureIsMadeTo
				.add(consentOrganizationalProviderPermittedToDisclose);
			}
			consent.setOrganizationalProvidersDisclosureIsMadeTo(organizationalProvidersDisclosureIsMadeTo);
		} else {
			consent.setOrganizationalProvidersDisclosureIsMadeTo(new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>());
		}

		if (consentDto.getOrganizationalProvidersPermittedToDisclose() != null) {
			final Set<ConsentOrganizationalProviderPermittedToDisclose> organizationalProvidersPermittedToDisclose = new HashSet<ConsentOrganizationalProviderPermittedToDisclose>();
			for (final String item : consentDto
					.getOrganizationalProvidersPermittedToDisclose()) {
				final OrganizationalProvider organizationalProvider = (OrganizationalProvider) providerMap
						.get(item);
				final ConsentOrganizationalProviderPermittedToDisclose consentOrganizationalProviderPermittedToDisclose = new ConsentOrganizationalProviderPermittedToDisclose(
						organizationalProvider);
				organizationalProvidersPermittedToDisclose
				.add(consentOrganizationalProviderPermittedToDisclose);
			}
			consent.setOrganizationalProvidersPermittedToDisclose(organizationalProvidersPermittedToDisclose);
		} else {
			consent.setOrganizationalProvidersPermittedToDisclose(new HashSet<ConsentOrganizationalProviderPermittedToDisclose>());
		}

		// Set Do Not Shares
		if (consentDto.getDoNotShareClinicalDocumentTypeCodes() != null) {
			final Set<ConsentDoNotShareClinicalDocumentTypeCode> doNotShareClinicalDocumentTypeCodes = new HashSet<ConsentDoNotShareClinicalDocumentTypeCode>();
			for (final String item : consentDto
					.getDoNotShareClinicalDocumentTypeCodes()) {
				final ClinicalDocumentTypeCode clinicalDocumentTypeCode = clinicalDocumentTypeCodeRepository
						.findByCode(item);
				final ConsentDoNotShareClinicalDocumentTypeCode consentDoNotShareClinicalDocumentTypeCode = new ConsentDoNotShareClinicalDocumentTypeCode(
						clinicalDocumentTypeCode);
				doNotShareClinicalDocumentTypeCodes
				.add(consentDoNotShareClinicalDocumentTypeCode);
			}
			consent.setDoNotShareClinicalDocumentTypeCodes(doNotShareClinicalDocumentTypeCodes);
		} else {
			consent.setDoNotShareClinicalDocumentTypeCodes(new HashSet<ConsentDoNotShareClinicalDocumentTypeCode>());
		}

		if (consentDto.getDoNotShareClinicalDocumentSectionTypeCodes() != null) {
			final Set<ConsentDoNotShareClinicalDocumentSectionTypeCode> doNotShareClinicalDocumentSectionTypeCodes = new HashSet<ConsentDoNotShareClinicalDocumentSectionTypeCode>();
			for (final String item : consentDto
					.getDoNotShareClinicalDocumentSectionTypeCodes()) {
				final MedicalSection medicalSectionCode = medicalSectionRepository
						.findByCode(item);
				final ConsentDoNotShareClinicalDocumentSectionTypeCode consentDoNotShareClinicalDocumentSectionTypeCode = new ConsentDoNotShareClinicalDocumentSectionTypeCode(
						medicalSectionCode);
				doNotShareClinicalDocumentSectionTypeCodes
				.add(consentDoNotShareClinicalDocumentSectionTypeCode);
			}
			consent.setDoNotShareClinicalDocumentSectionTypeCodes(doNotShareClinicalDocumentSectionTypeCodes);
		} else {
			consent.setDoNotShareClinicalDocumentSectionTypeCodes(new HashSet<ConsentDoNotShareClinicalDocumentSectionTypeCode>());
		}

		if (consentDto.getDoNotShareSensitivityPolicyCodes() != null) {
			final Set<ConsentDoNotShareSensitivityPolicyCode> doNotShareSensitivityPolicyCodes = new HashSet<ConsentDoNotShareSensitivityPolicyCode>();
			for (final String item : consentDto
					.getDoNotShareSensitivityPolicyCodes()) {
				final ValueSetCategory valueSetCategory = valueSetCategoryRepository
						.findByCode(item);
				final ConsentDoNotShareSensitivityPolicyCode consentDoNotShareSensitivityPolicyCode = new ConsentDoNotShareSensitivityPolicyCode(
						valueSetCategory);
				doNotShareSensitivityPolicyCodes
				.add(consentDoNotShareSensitivityPolicyCode);
			}
			consent.setDoNotShareSensitivityPolicyCodes(doNotShareSensitivityPolicyCodes);
		} else {
			consent.setDoNotShareSensitivityPolicyCodes(new HashSet<ConsentDoNotShareSensitivityPolicyCode>());
		}

		if (consentDto.getShareForPurposeOfUseCodes() != null) {
			final Set<ConsentShareForPurposeOfUseCode> shareForPurposeOfUseCodes = new HashSet<ConsentShareForPurposeOfUseCode>();
			for (final String item : consentDto.getShareForPurposeOfUseCodes()) {
				final PurposeOfUseCode purposeOfUseCode = purposeOfUseCodeRepository
						.findByCode(item);
				final ConsentShareForPurposeOfUseCode consentShareForPurposeOfUseCode = new ConsentShareForPurposeOfUseCode(
						purposeOfUseCode);
				shareForPurposeOfUseCodes.add(consentShareForPurposeOfUseCode);
			}
			consent.setShareForPurposeOfUseCodes(shareForPurposeOfUseCodes);
		} else {
			consent.setShareForPurposeOfUseCodes(new HashSet<ConsentShareForPurposeOfUseCode>());
		}

		if (consentDto.getDoNotShareClinicalConceptCodes() != null) {
			final Set<ClinicalConceptCode> doNotShareClinicalConceptCodes = new HashSet<ClinicalConceptCode>();
			for (final SpecificMedicalInfoDto item : consentDto
					.getDoNotShareClinicalConceptCodes()) {
				final ClinicalConceptCode clinicalConceptCode = new ClinicalConceptCode();
				clinicalConceptCode.setCode(item.getCode());
				clinicalConceptCode.setCodeSystem(item.getCodeSystem());
				clinicalConceptCode.setCodeSystemName(item.getCodeSystem());
				clinicalConceptCode.setDisplayName(item.getDisplayName());
				doNotShareClinicalConceptCodes.add(clinicalConceptCode);
			}
			consent.setDoNotShareClinicalConceptCodes(doNotShareClinicalConceptCodes);
		} else {
			consent.setDoNotShareClinicalConceptCodes(new HashSet<ClinicalConceptCode>());
		}

		// Set Dates
		consent.setStartDate(consentDto.getConsentStart());
		consent.setEndDate(consentDto.getConsentEnd());

		consent.setPatient(patient);
		consent.setName("Consent");
		consent.setDescription("This is a consent made by "
				+ patient.getFirstName() + " " + patient.getLastName());
		consent.setUnsignedPdfConsent(consentPdfGenerator
				.generate42CfrPart2Pdf(consent));
		try {
			consent.setXacmlCcd(consentExportService.exportConsent2XACML(
					consent).getBytes());

			// set xacml for consentFrom provider to give access
			// to consent pdf
			consent.setXacmlPdfConsentFrom(consentExportService
					.exportConsent2XacmlPdfConsentFrom(consent).getBytes());

			consent.setXacmlPdfConsentTo(consentExportService
					.exportConsent2XacmlPdfConsentTo(consent).getBytes());

		} catch (final ConsentGenException e) {
			logger.error("Error in saving consent in xacml format", e);
			throw new ConsentGenException(e.getMessage());
		}

		if (consent.getId() != null) {
			consentRepository.save(consent);
		} else {
			consentFactory.createNewConsent(consent);
		}

		return consentDto;
	}

	/**
	 * Sign consent.
	 *
	 * @param consentPdfDto
	 *            the consent pdf dto
	 * @return true, if successful
	 */
	@Override
	public boolean signConsent(ConsentPdfDto consentPdfDto) {
		final Consent consent = consentRepository
				.findOne(consentPdfDto.getId());
		if (consent == null) {
			return false;
		}
		// SignConsent
		final SignedPDFConsent signedPdfConsent = makeSignedPdfConsent();
		final Patient patient = consent.getPatient();
		final String patientEmail = patient.getEmail();

		// Email hard-coded and to be changed
		signedPdfConsent.setDocumentId(echoSignSignatureService
				.sendDocumentToSign(consentPdfDto.getContent(),
						consentPdfDto.getFilename(),
						consentPdfDto.getConsentName(), patientEmail,
						"consent2share@gmail.com"));
		signedPdfConsent
		.setDocumentNameBySender(consentPdfDto.getConsentName());
		signedPdfConsent
		.setDocumentMessageBySender("This is a hard-coded greeting to be replaced. Hi.");
		signedPdfConsent.setSignerEmail(patientEmail);
		signedPdfConsent.setDocumentSignedStatus("Unsigned");
		consent.setSignedPdfConsent(signedPdfConsent);
		consentRepository.save(consent);
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.service.consent.ConsentService#signConsentRevokation
	 * (gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto)
	 */
	@Override
	public void signConsentRevokation(
			ConsentRevokationPdfDto consentRevokationPdfDto) {
		final Consent consent = consentRepository
				.findOne(consentRevokationPdfDto.getId());
		// SignConsentRevokation
		final SignedPDFConsentRevocation signedPDFConsentRevocation = makeSignedPDFConsentRevocation();
		final Patient patient = consent.getPatient();
		final String patientEmail = patient.getEmail();

		// TODO:Email and Email message hard-coded and to be changed
		signedPDFConsentRevocation.setDocumentId(echoSignSignatureService
				.sendDocumentToSign(consentRevokationPdfDto.getContent(),
						consentRevokationPdfDto.getFilename(),
						consentRevokationPdfDto.getConsentName()
						+ " Revocation", patientEmail,
						"consent2share@gmail.com"));
		signedPDFConsentRevocation
		.setDocumentNameBySender(consentRevokationPdfDto
				.getConsentName());
		signedPDFConsentRevocation
		.setDocumentMessageBySender("This is a hard-coded greeting to be replaced. Hi.");
		signedPDFConsentRevocation.setSignerEmail("consent2share@gmail.com");
		signedPDFConsentRevocation.setDocumentSignedStatus("Unsigned");
		signedPDFConsentRevocation.setDocumentCreatedBy(consent.getPatient()
				.getLastName() + ", " + consent.getPatient().getFirstName());
		signedPDFConsentRevocation
		.setDocumentSentOutForSignatureDateTime(new Date());
		consent.setConsentRevoked(true);
		consent.setSignedPdfConsentRevoke(signedPDFConsentRevocation);

		if (consentRevokationPdfDto.getRevokationType()
				.equals("EMERGENCY ONLY")) {
			consent.setConsentRevokationType("EMERGENCY ONLY");
		}
		if (consentRevokationPdfDto.getRevokationType().equals("NO NEVER")) {
			consent.setConsentRevokationType("NO NEVER");
		}

		consentRepository.save(consent);
	}

	/**
	 * Tag c32 document.
	 *
	 * @param taggedC32List
	 *            the tagged c32 list
	 * @param taggedC32Ids
	 *            the tagged c32 ids
	 */
	public void tagC32Document(NodeList taggedC32List, List<String> taggedC32Ids) {

		for (int i = 0; i < taggedC32List.getLength(); i++) {
			final Element elementToAddAttribute = (Element) taggedC32List
					.item(i);

			final NodeList childNodes = taggedC32List.item(i).getChildNodes();

			for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
				final Node childNode = childNodes.item(childIndex);

				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					final NodeList grandChildNodeList = childNode
							.getChildNodes();

					for (int grandChildIndex = 0; grandChildIndex < grandChildNodeList
							.getLength(); grandChildIndex++) {
						final Node grandChildNode = grandChildNodeList
								.item(grandChildIndex);

						if (grandChildNode.getNodeName().equalsIgnoreCase("ID")) {

							// tag c32 by adding redact attribute to ids that
							// were segmented
							if (taggedC32Ids.contains(grandChildNode
									.getAttributes().getNamedItem("root")
									.getNodeValue())) {
								logger.info("Match!");
								elementToAddAttribute.setAttribute("redact",
										"redact");
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Tag smart c32 document.
	 *
	 * @param taggedC32List
	 *            the tagged c32 list
	 * @param taggedC32Ids
	 *            the tagged c32 ids
	 */
	public void tagSmartC32Document(NodeList taggedC32List,
			List<String> taggedC32Ids) {

		for (int i = 0; i < taggedC32List.getLength(); i++) {
			final Element elementToAddAttribute = (Element) taggedC32List
					.item(i);

			final NodeList childNodes = taggedC32List.item(i).getChildNodes();

			if (!isRedactableSection(taggedC32List.item(i))) {
				continue;
			}

			for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
				final Node childNode = childNodes.item(childIndex);

				if (childNode.getNodeType() == Node.ELEMENT_NODE) {

					final NodeList grandChildNodeList = childNode
							.getChildNodes();

					for (int grandChildIndex = 0; grandChildIndex < grandChildNodeList
							.getLength(); grandChildIndex++) {
						final Node grandChildNode = grandChildNodeList
								.item(grandChildIndex);

						if (grandChildNode.getNodeName().equalsIgnoreCase(
								"entryRelationship")) {
							final Node observationNode = grandChildNode
									.getChildNodes().item(1);
							final NodeList observationChildNodes = observationNode
									.getChildNodes();

							for (int observationIndex = 0; observationIndex < observationChildNodes
									.getLength(); observationIndex++) {

								final Node observationChildNode = observationChildNodes
										.item(observationIndex);

								if (childNode.getNodeType() == Node.ELEMENT_NODE) {

									if (observationChildNode.getNodeName()
											.equalsIgnoreCase("text")) {

										final Node referenceNode = observationChildNode
												.getChildNodes().item(1);
										final Node referenceValue = referenceNode
												.getAttributes().getNamedItem(
														"value");
										logger.info("Reference value is: "
												+ referenceValue.getNodeValue());

										if (taggedC32Ids
												.contains(referenceValue
														.getNodeValue())) {
											logger.info("Match!");
											elementToAddAttribute.setAttribute(
													"redact", "redact");
										}
									}
								}
							}
						}

					}
				}
			}
		}
	}

	/**
	 * Update consent.
	 *
	 * @param consent
	 *            the consent
	 * @return the consent
	 */
	@Override
	public Consent updateConsent(Consent consent) {
		return consentRepository.save(consent);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.service.consent.ConsentService#validateConsentDate
	 * (java.util.Date, java.util.Date)
	 */
	@Override
	public boolean validateConsentDate(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			final Date today = new LocalDate().toDate();
			if (startDate.compareTo(endDate) <= 0
					&& startDate.compareTo(today) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Changes xsl path to local xsl.
	 *
	 * @param taggedC32Doc
	 *            the tagged c32 doc
	 */
	private void changeXslPath(Document taggedC32Doc) {
		final String expression = "/processing-instruction('xml-stylesheet')";
		Optional<ProcessingInstruction> pi = Optional.empty();
		try {
			pi = documentAccessor.getProcessingInstruction(taggedC32Doc,
					expression);
		} catch (final DocumentAccessorException e) {
			throw new DS4PException("Error processing xsl path");
		}

		// <?xml-stylesheet href="http://obhita.org/CDA.xsl" type="text/xsl"?>
		if (pi.isPresent()) {
			pi.get().setData("type='text/xsl' href='CDA_flag_redact.xsl'");
		} else {
			// Add xml style sheet at the second line of xml string
			final ProcessingInstruction p = taggedC32Doc
					.createProcessingInstruction("xml-stylesheet",
							"type=\"text/xsl\" href=\"CDA_flag_redact.xsl\"");
			final Element stylesheetEl = taggedC32Doc.getDocumentElement();
			taggedC32Doc.insertBefore(p, stylesheetEl);
		}
	}

	/**
	 * Returns xacmlFile based on consent id.
	 *
	 * @param consentId
	 *            the consent id
	 * @return xacmlFile
	 */
	@Transactional(readOnly = true)
	private String findConsentXACMLById(Long consentId) {
		String xacmlFile = "";

		if (consentRepository.findOne(consentId) != null) {
			final Consent consent = consentRepository.findOne(consentId);
			final byte[] xacmlByte = consent.getXacmlCcd();
			try {
				xacmlFile = new String(xacmlByte, "UTF-8");
			} catch (final UnsupportedEncodingException e) {
				logger.error("Error while converting xacml byte[] to string "
						+ e.getMessage());
				e.printStackTrace();
			}

		}
		return xacmlFile;
	}

	/**
	 * Generate policy id.
	 *
	 * @param consentDto
	 *            the consent dto
	 * @param mrn
	 *            the mrn
	 * @return the string
	 */
	private String generatePolicyId(ConsentDto consentDto, String mrn) {
		String policyId = null;
		final StringBuilder errorBuilder = new StringBuilder();
		try {
			policyId = policyIdService.generatePolicyId(consentDto, mrn);
		} catch (final UniqueValueGeneratorException e) {
			errorBuilder.append(". ");
			errorBuilder.append(e.getMessage());
			logger.error(e.getMessage(), e);
		} finally {
			Assert.hasText(
					policyId,
					errorBuilder.insert(0,
							"PolicyId cannot be generated by PCM!").toString());
		}
		return policyId;
	}

	/**
	 * Gets the ids for smart.
	 *
	 * @param c32List
	 *            the c32 list
	 * @return the ids for smart
	 */
	private List<String> getIdsForSmart(NodeList c32List) {
		final List<String> listOfIdsInC32 = new ArrayList<String>();

		for (int i = 0; i < c32List.getLength(); i++) {

			final NodeList childNodes = c32List.item(i).getChildNodes();

			if (!isRedactableSection(c32List.item(i))) {
				continue;
			}

			for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
				final Node childNode = childNodes.item(childIndex);

				if (childNode.getNodeType() == Node.ELEMENT_NODE) {

					final NodeList grandChildNodeList = childNode
							.getChildNodes();

					for (int grandChildIndex = 0; grandChildIndex < grandChildNodeList
							.getLength(); grandChildIndex++) {
						final Node grandChildNode = grandChildNodeList
								.item(grandChildIndex);

						if (grandChildNode.getNodeName().equalsIgnoreCase(
								"entryRelationship")) {
							final Node observationNode = grandChildNode
									.getChildNodes().item(1);
							final NodeList observationChildNodes = observationNode
									.getChildNodes();

							for (int observationIndex = 0; observationIndex < observationChildNodes
									.getLength(); observationIndex++) {

								final Node observationChildNode = observationChildNodes
										.item(observationIndex);

								if (childNode.getNodeType() == Node.ELEMENT_NODE) {

									if (observationChildNode.getNodeName()
											.equalsIgnoreCase("text")) {

										final Node referenceNode = observationChildNode
												.getChildNodes().item(1);
										final Node referenceValue = referenceNode
												.getAttributes().getNamedItem(
														"value");
										logger.info("Reference value is: "
												+ referenceValue.getNodeValue());

										listOfIdsInC32.add(referenceValue
												.getNodeValue());
									}
								}
							}
						}

					}
				}
			}
		}
		return listOfIdsInC32;
	}

	/**
	 * Checks if is redactable section.
	 *
	 * @param entryNode
	 *            the entry node
	 * @return true, if is redactable section
	 */
	private boolean isRedactableSection(Node entryNode) {
		boolean isRedactable = false;
		while (entryNode.getPreviousSibling() != null) {
			final Node previousSibling = entryNode.getPreviousSibling();
			if (previousSibling.getNodeType() == Node.ELEMENT_NODE) {
				if (previousSibling.getNodeName().equalsIgnoreCase("title")) {
					final NodeList c = previousSibling.getChildNodes();
					isRedactable = validationSection(previousSibling
							.getChildNodes().item(0).getNodeValue());
				}
			}
			entryNode = previousSibling;
		}
		return isRedactable;
	}

	/**
	 * Checks if is smart c32.
	 *
	 * @param originalC32
	 *            the original c32
	 * @return true, if is smart c32
	 */
	private boolean isSmartC32(String originalC32) {
		if (originalC32.contains("content")) {
			return true;
		}
		return false;
	}

	/**
	 * Validation section.
	 *
	 * @param nodeValue
	 *            the node value
	 * @return true, if successful
	 */
	private boolean validationSection(String nodeValue) {
		boolean redacted = false;
		if (nodeValue.toUpperCase().matches(
				"^.*?(PROBLEMS|MEDICATIONS|RESULTS|ALLERGIES).*$")) {
			redacted = true;
		}

		logger.info("Node value: " + nodeValue + "   Is redactable: "
				+ redacted);
		return redacted;
	}
}
