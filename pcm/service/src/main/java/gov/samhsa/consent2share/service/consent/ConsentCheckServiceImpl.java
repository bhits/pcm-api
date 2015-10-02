package gov.samhsa.consent2share.service.consent;

import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentValidationDto;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ConsentCheckServiceImpl.
 */
public class ConsentCheckServiceImpl implements ConsentCheckService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The consent repository. */
	private ConsentRepository consentRepository;

	/** The patient repository. */
	private PatientRepository patientRepository;

	/** The consent helper. */
	ConsentHelper consentHelper;

	/**
	 * Instantiates a new consent check service impl.
	 *
	 * @param consentRepository
	 *            the consent repository
	 * @param patientRepository
	 *            the patient repository
	 * @param consentHelper
	 *            the consent helper
	 */
	public ConsentCheckServiceImpl(ConsentRepository consentRepository,
			PatientRepository patientRepository, ConsentHelper consentHelper) {
		super();
		this.consentRepository = consentRepository;
		this.patientRepository = patientRepository;
		this.consentHelper = consentHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.consent.ConsentCheckService#
	 * getConflictConsent(gov.samhsa.consent2share.service.dto.ConsentDto)
	 */
	@Override
	public ConsentValidationDto getConflictConsent(ConsentDto consentDto) {

		ConsentValidationDto consentValidationDto = null;
		List<Consent> consents;

		// 1. Get all consents for that user
		if (consentDto.getUsername() != null
				&& !consentDto.getUsername().isEmpty()) {
			consents = consentRepository.findAllByPatientUsername(consentDto
					.getUsername());
		} else {
			consents = consentRepository.findByPatient(patientRepository
					.findOne(consentDto.getPatientId()));
		}
		boolean isConflict = false;
		for (Consent consent : consents) {

			// editing the existing consent then skip that consent
			if (consent.getId().toString().equalsIgnoreCase(consentDto.getId()))
				continue;

			boolean isOverlap = false;
			boolean isPOUMatch = false;
			boolean isProviderMatch = false;
			boolean isConsentRevoked = false;
			// check if the selected consent terms overlaps with the existing
			// consent
			isOverlap = consentHelper.isConsentTermOverlap(consentDto,
					consent.getStartDate(), consent.getEndDate());
			if (isOverlap) {
				// if it overlaps
				// Check if the selected POU codes belongs to the existing
				// consent
				isPOUMatch = consentHelper.isPOUMatches(
						consentDto.getShareForPurposeOfUseCodes(),
						consent.getShareForPurposeOfUseCodes());
				if (isPOUMatch) {

					// check the provider combination match
					isProviderMatch = consentHelper.isProviderComboMatch(
							consent, consentDto);
					if (isProviderMatch) {

						// check if the existing consent is in revoked state
						// if its revoked then its not a duplicate consent
						isConsentRevoked = consentHelper
								.isConsentRevoked(consent);
						if (!isConsentRevoked) {
							isConflict = true;
							consentValidationDto = consentHelper
									.convertConsentToConsentListDto(consent,
											consentDto);
							break;
						}
					} else {
						continue;
					}
				} else {
					// no conflicts so check the next consent
					continue;
				}

			} else {
				// no conflicts so check the next consent
				continue;
			}
		}

		logger.debug("is conflict found: " + isConflict);
		return consentValidationDto;
	}
}
