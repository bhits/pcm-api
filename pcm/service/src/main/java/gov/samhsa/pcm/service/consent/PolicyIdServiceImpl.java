package gov.samhsa.pcm.service.consent;

import gov.samhsa.pcm.common.UniqueValueGenerator;
import gov.samhsa.pcm.domain.consent.ConsentRepository;
import gov.samhsa.pcm.service.dto.ConsentDto;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.Assert;

/**
 * The Class PolicyIdServiceImpl.
 */
public class PolicyIdServiceImpl implements PolicyIdService {

	/** The Constant RANDOM_STRING_LENGTH. */
	private static final int RANDOM_STRING_LENGTH = 6;

	/** The pid domain id. */
	private String pidDomainId;

	/** The pid domain type. */
	private String pidDomainType;

	/** The consent repository. */
	private ConsentRepository consentRepository;

	/**
	 * Instantiates a new policy id service impl.
	 *
	 * @param pidDomainId
	 *            the pid domain id
	 * @param pidDomainType
	 *            the pid domain type
	 * @param consentRepository
	 *            the consent repository
	 */
	public PolicyIdServiceImpl(String pidDomainId, String pidDomainType,
			ConsentRepository consentRepository) {
		super();
		this.pidDomainId = pidDomainId;
		this.pidDomainType = pidDomainType;
		this.consentRepository = consentRepository;
		Assert.hasText(this.pidDomainId,
				"PolicyIdServiceImpl cannot be initialized without 'pidDomainId'!");
		Assert.hasText(this.pidDomainType,
				"PolicyIdServiceImpl cannot be initialized without 'pidDomainType'!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.consent.PolicyIdService#generatePolicyId
	 * (gov.samhsa.consent2share.service.dto.ConsentDto, java.lang.String)
	 */
	@Override
	public String generatePolicyId(ConsentDto consentDto, String mrn) {
		final short iterationLimit = 3;
		return UniqueValueGenerator
				.generateUniqueValue(() -> generateRandomPolicyId(consentDto, mrn),
						generatedValue -> consentRepository.findAllByConsentReferenceId(generatedValue)
								.size() == 0, iterationLimit);
	}

	/**
	 * Generate random policy id.
	 *
	 * @param consentDto
	 *            the consent dto
	 * @param mrn
	 *            the mrn
	 * @return the string
	 */
	private String generateRandomPolicyId(ConsentDto consentDto, String mrn) {
		Assert.hasText(mrn, "The patient must have an local c2s identifier.");
		StringBuilder consentReferenceIdBuilder = new StringBuilder();
		consentReferenceIdBuilder.append(mrn);
		consentReferenceIdBuilder.append(":&");
		consentReferenceIdBuilder.append(this.pidDomainId);
		consentReferenceIdBuilder.append("&");
		consentReferenceIdBuilder.append(this.pidDomainType);

		consentReferenceIdBuilder.append(":");
		if (consentDto.getOrganizationalProvidersDisclosureIsMadeTo() != null) {
			consentReferenceIdBuilder
					.append(consentDto
							.getOrganizationalProvidersDisclosureIsMadeTo()
							.toArray()[0]);
		} else {
			consentReferenceIdBuilder.append(consentDto
					.getProvidersDisclosureIsMadeTo().toArray()[0]);
		}
		consentReferenceIdBuilder.append(":");
		if (consentDto.getOrganizationalProvidersPermittedToDisclose() != null) {
			consentReferenceIdBuilder
					.append(consentDto
							.getOrganizationalProvidersPermittedToDisclose()
							.toArray()[0]);
		} else {
			consentReferenceIdBuilder.append(consentDto
					.getProvidersPermittedToDisclose().toArray()[0]);
		}
		consentReferenceIdBuilder.append(":");
		consentReferenceIdBuilder.append(RandomStringUtils
				.randomAlphanumeric((RANDOM_STRING_LENGTH)));
		return consentReferenceIdBuilder.toString().toUpperCase();
	}
}
