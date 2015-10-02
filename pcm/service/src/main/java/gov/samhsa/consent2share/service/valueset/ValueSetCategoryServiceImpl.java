package gov.samhsa.consent2share.service.valueset;

import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategory;
import gov.samhsa.consent2share.domain.valueset.ValueSetCategoryRepository;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.ValueSetCategoryDto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class ValueSetCategoryServiceImpl.
 */
public class ValueSetCategoryServiceImpl implements ValueSetCategoryService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The value set category repository. */
	private ValueSetCategoryRepository valueSetCategoryRepository;

	/** The consent repository. */
	private ConsentRepository consentRepository;

	/** The value set mgmt helper. */
	ValueSetMgmtHelper valueSetMgmtHelper;

	/**
	 * Instantiates a new value set category service impl.
	 *
	 * @param valueSetCategoryRepository
	 *            the value set category repository
	 * @param consentRepository
	 *            the consent repository
	 * @param valueSetMgmtHelper
	 *            the value set mgmt helper
	 */
	public ValueSetCategoryServiceImpl(
			ValueSetCategoryRepository valueSetCategoryRepository,
			ConsentRepository consentRepository,
			ValueSetMgmtHelper valueSetMgmtHelper) {
		super();
		this.valueSetCategoryRepository = valueSetCategoryRepository;
		this.consentRepository = consentRepository;
		this.valueSetMgmtHelper = valueSetMgmtHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.ValueSetCategoryService#create
	 * (gov.samhsa.consent2share.service.dto.ValueSetCategoryDto)
	 */
	@Override
	@Transactional
	public ValueSetCategoryDto create(ValueSetCategoryDto created) {
		logger.debug("Creating a new ValueSetCategory with information: "
				+ created);
		String description = (created.getDescription() != null) ? created
				.getDescription() : "";
		ValueSetCategory valueSetCategory = ValueSetCategory
				.getBuilder(created.getCode(), created.getName(),
						created.getUserName()).description(description).build();
		valueSetCategory = valueSetCategoryRepository.save(valueSetCategory);
		return valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(
				valueSetCategory, consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.ValueSetCategoryService#delete
	 * (java.lang.Long)
	 */
	@Override
	@Transactional(rollbackFor = ValueSetCategoryNotFoundException.class)
	public ValueSetCategoryDto delete(Long valueSetCategoryId)
			throws ValueSetCategoryNotFoundException {
		logger.debug("Deleting ValueSetCategory with id: " + valueSetCategoryId);
		ValueSetCategory deleted = valueSetCategoryRepository
				.findOne(valueSetCategoryId);
		if (deleted == null) {
			logger.debug("No ValueSetCategory found with an id: "
					+ valueSetCategoryId);
			throw new ValueSetCategoryNotFoundException();
		}
		valueSetCategoryRepository.delete(deleted);
		return valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(deleted,
				consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.ValueSetCategoryService#findAll
	 * ()
	 */
	@Override
	public List<ValueSetCategoryDto> findAll() {
		logger.debug("Finding all valueSetCategories");
		List<ValueSetCategory> valueSetCategories = valueSetCategoryRepository
				.findAll();
		return valueSetMgmtHelper.convertValueSetCategoryEntitiesToDtos(
				valueSetCategories, consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.ValueSetCategoryService#findById
	 * (java.lang.Long)
	 */
	@Override
	public ValueSetCategoryDto findById(Long id) {
		logger.debug("Finding a ValueSetCategory with id: " + id);
		ValueSetCategory valueSetCategory = valueSetCategoryRepository
				.findOne(id);
		return valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(
				valueSetCategory, consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.valueset.ValueSetCategoryService#update
	 * (gov.samhsa.consent2share.service.dto.ValueSetCategoryDto)
	 */
	@Override
	@Transactional(rollbackFor = ValueSetCategoryNotFoundException.class)
	public ValueSetCategoryDto update(ValueSetCategoryDto updated)
			throws ValueSetCategoryNotFoundException {
		logger.debug("Updating ValueSetCategory with information" + updated);

		ValueSetCategory valueSetCategory = valueSetCategoryRepository
				.findOne(updated.getId());
		if (valueSetCategory == null) {
			logger.debug("No ValueSetCategory found with an id: "
					+ updated.getId());
			throw new ValueSetCategoryNotFoundException();
		}

		valueSetCategory.update(updated.getCode(), updated.getName(),
				updated.getDescription(), updated.getUserName());
		return valueSetMgmtHelper.createValuesetCategoryDtoFromEntity(
				valueSetCategory, consentRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.valueset.ValueSetCategoryService#
	 * findAllValueSetCategoriesAddConsentFieldsDto()
	 */
	@Override
	public List<AddConsentFieldsDto> findAllValueSetCategoriesAddConsentFieldsDto() {
		List<ValueSetCategory> valueSetCategoryList = valueSetCategoryRepository
				.findAll();
		List<AddConsentFieldsDto> sensitivityPolicyDto = new ArrayList<AddConsentFieldsDto>();
		for (ValueSetCategory valueSetCategory : valueSetCategoryList) {
			AddConsentFieldsDto sensitivityPolicyDtoItem = new AddConsentFieldsDto();
			sensitivityPolicyDtoItem.setCode(valueSetCategory.getCode());
			sensitivityPolicyDtoItem.setDisplayName(valueSetCategory.getName());
			sensitivityPolicyDtoItem.setDescription(valueSetCategory
					.getDescription());
			sensitivityPolicyDto.add(sensitivityPolicyDtoItem);
		}
		return sensitivityPolicyDto;
	}

	/**
	 * This setter method should be used only by unit tests.
	 *
	 * @param valueSetCategoryRepository
	 *            the new value set category repository
	 */
	protected void setValueSetCategoryRepository(
			ValueSetCategoryRepository valueSetCategoryRepository) {
		this.valueSetCategoryRepository = valueSetCategoryRepository;
	}
}
