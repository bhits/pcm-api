package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.valueset.MedicalSection;
import gov.samhsa.consent2share.domain.valueset.MedicalSectionRepository;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.MedicalSectionDto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class MedicalSectionServiceImpl.
 */
public class MedicalSectionServiceImpl implements MedicalSectionService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The medical section repository. */
	private MedicalSectionRepository medicalSectionRepository;

	/** The consent repository. */
	private ConsentRepository consentRepository;

	/** The value set mgmt helper. */
	ValueSetMgmtHelper valueSetMgmtHelper;

	/**
	 * Instantiates a new medical section service impl.
	 *
	 * @param medicalSectionRepository
	 *            the medical section repository
	 * @param consentRepository
	 *            the consent repository
	 * @param valueSetMgmtHelper
	 *            the value set mgmt helper
	 */
	public MedicalSectionServiceImpl(
			MedicalSectionRepository medicalSectionRepository,
			ConsentRepository consentRepository,
			ValueSetMgmtHelper valueSetMgmtHelper) {
		super();
		this.medicalSectionRepository = medicalSectionRepository;
		this.consentRepository = consentRepository;
		this.valueSetMgmtHelper = valueSetMgmtHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.MedicalSectionService#create
	 * (gov.samhsa.consent2share.service.dto.MedicalSectionDto)
	 */
	@Override
	@Transactional
	public MedicalSectionDto create(MedicalSectionDto created) {
		logger.debug("Creating a new MedicalSection with information: "
				+ created);
		String description = (created.getDescription() != null) ? created
				.getDescription() : "";
		MedicalSection medicalSection = MedicalSection
				.getBuilder(created.getCode(), created.getName(),
						created.getUserName()).description(description).build();
		medicalSection = medicalSectionRepository.save(medicalSection);
		return valueSetMgmtHelper.createMedicalSectionDtoFromEntity(
				medicalSection, consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.MedicalSectionService#delete
	 * (java.lang.Long)
	 */
	@Override
	@Transactional(rollbackFor = MedicalSectionNotFoundException.class)
	public MedicalSectionDto delete(Long medicalSectionId)
			throws MedicalSectionNotFoundException {
		logger.debug("Deleting MedicalSection with id: " + medicalSectionId);
		MedicalSection deleted = medicalSectionRepository
				.findOne(medicalSectionId);
		if (deleted == null) {
			logger.debug("No MedicalSection found with an id: "
					+ medicalSectionId);
			throw new MedicalSectionNotFoundException();
		}
		medicalSectionRepository.delete(deleted);
		return valueSetMgmtHelper.createMedicalSectionDtoFromEntity(deleted,
				consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.MedicalSectionService#findAll()
	 */
	@Override
	public List<MedicalSectionDto> findAll() {
		logger.debug("Finding all medicalSections");
		List<MedicalSection> medicalSections = medicalSectionRepository
				.findAll();
		return valueSetMgmtHelper.convertMedicalSectionEntitiesToDtos(
				medicalSections, consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.MedicalSectionService#findById
	 * (java.lang.Long)
	 */
	@Override
	public MedicalSectionDto findById(Long id) {
		logger.debug("Finding a MedicalSection with id: " + id);
		MedicalSection medicalSection = medicalSectionRepository.findOne(id);
		return valueSetMgmtHelper.createMedicalSectionDtoFromEntity(
				medicalSection, consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.MedicalSectionService#update
	 * (gov.samhsa.consent2share.service.dto.MedicalSectionDto)
	 */
	@Override
	@Transactional(rollbackFor = MedicalSectionNotFoundException.class)
	public MedicalSectionDto update(MedicalSectionDto updated)
			throws MedicalSectionNotFoundException {
		logger.debug("Updating MedicalSection with information" + updated);

		MedicalSection medicalSection = medicalSectionRepository
				.findOne(updated.getId());
		if (medicalSection == null) {
			logger.debug("No MedicalSection found with an id: "
					+ updated.getId());
			throw new MedicalSectionNotFoundException();
		}

		medicalSection.update(updated.getCode(), updated.getName(),
				updated.getDescription(), updated.getUserName());
		return valueSetMgmtHelper.createMedicalSectionDtoFromEntity(
				medicalSection, consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.valueset.MedicalSectionService#
	 * findAllMedicalSectionsAddConsentFieldsDto()
	 */
	@Override
	public List<AddConsentFieldsDto> findAllMedicalSectionsAddConsentFieldsDto() {
		List<MedicalSection> medicalSectionList = medicalSectionRepository
				.findAll();
		List<AddConsentFieldsDto> sensitivityPolicyDto = new ArrayList<AddConsentFieldsDto>();
		for (MedicalSection medicalSection : medicalSectionList) {
			AddConsentFieldsDto sensitivityPolicyDtoItem = new AddConsentFieldsDto();
			sensitivityPolicyDtoItem.setCode(medicalSection.getCode());
			sensitivityPolicyDtoItem.setDisplayName(medicalSection.getName());
			sensitivityPolicyDtoItem.setDescription(medicalSection
					.getDescription());
			sensitivityPolicyDto.add(sensitivityPolicyDtoItem);
		}
		return sensitivityPolicyDto;
	}

	/**
	 * This setter method should be used only by unit tests.
	 *
	 * @param medicalSectionRepository
	 *            the new medical section repository
	 */
	protected void setMedicalSectionRepository(
			MedicalSectionRepository medicalSectionRepository) {
		this.medicalSectionRepository = medicalSectionRepository;
	}
}
