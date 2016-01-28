package gov.samhsa.mhc.pcm.service.consent;


import java.util.List;

import gov.samhsa.mhc.pcm.domain.consent.Consent;
import gov.samhsa.mhc.pcm.domain.consent.ConsentRepository;
import gov.samhsa.mhc.pcm.domain.patient.PatientRepository;
import gov.samhsa.mhc.pcm.service.dto.ConsentDto;
import gov.samhsa.mhc.pcm.service.dto.ConsentValidationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ConsentCheckServiceImpl.
 */
@Service
public class ConsentCheckServiceImpl implements ConsentCheckService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The consent repository. */
	@Autowired
	private ConsentRepository consentRepository;

	/** The patient repository. */
	@Autowired
	private PatientRepository patientRepository;

	/** The consent helper. */
	@Autowired
	private ConsentHelper consentHelper;

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
