package gov.samhsa.pcm.service.consentexport;

import gov.samhsa.consent.ConsentDto;
import gov.samhsa.consent.ConsentDtoFactory;
import gov.samhsa.pcm.domain.consent.Consent;
import gov.samhsa.pcm.domain.consent.ConsentRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ConsentDtoFactoryImpl.
 */
public class ConsentDtoFactoryImpl implements ConsentDtoFactory {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The consent repository. */
	ConsentRepository consentRepository;

	/** The model mapper. */
	ModelMapper modelMapper;

	/** The consent export mapper. */
	private ConsentExportMapper consentExportMapper;

	/**
	 * Instantiates a new consent dto factory impl.
	 *
	 * @param consentRepository
	 *            the consent repository
	 * @param modelMapper
	 *            the model mapper
	 * @param consentExportMapper
	 *            the consent export mapper
	 */
	public ConsentDtoFactoryImpl(ConsentRepository consentRepository,
			ModelMapper modelMapper, ConsentExportMapper consentExportMapper) {
		super();
		this.consentRepository = consentRepository;
		this.modelMapper = modelMapper;
		this.consentExportMapper = consentExportMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent.ConsentDtoFactory#createConsentDto(long)
	 */
	@Override
	public ConsentDto createConsentDto(long consentId) {
		Consent consent = consentRepository.findOne(consentId);
		ConsentDto consentDto = consentExportMapper.map(consent);
		return consentDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent.ConsentDtoFactory#createConsentDto(java.lang.Object)
	 */
	@Override
	public ConsentDto createConsentDto(Object obj) {
		ConsentDto consentDto = null;

		if (obj != null) {
			if (obj instanceof Long) {
				long consentId = ((Long) obj).longValue();
				Consent consent = consentRepository.findOne(consentId);
				consentDto = consentExportMapper.map(consent);
			} else if (obj instanceof Consent) {
				consentDto = consentExportMapper.map((Consent) obj);
			}
		}
		return consentDto;
	}
}
