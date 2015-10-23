package gov.samhsa.pcm.service.provider;

import gov.samhsa.pcm.domain.provider.IndividualProvider;
import gov.samhsa.pcm.domain.provider.IndividualProviderRepository;
import gov.samhsa.pcm.domain.provider.StaffIndividualProvider;
import gov.samhsa.pcm.domain.provider.StaffIndividualProviderRepository;
import gov.samhsa.pcm.service.dto.StaffIndividualProviderDto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class FavoriteIndividualProviderServiceImpl.
 */
@Transactional
public class FavoriteIndividualProviderServiceImpl implements
		FavoriteIndividualProviderService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The individual provider repository. */
	IndividualProviderRepository individualProviderRepository;

	/** The model mapper. */
	ModelMapper modelMapper;

	/** The staff individual provider repository. */
	StaffIndividualProviderRepository staffIndividualProviderRepository;

	/**
	 * Instantiates a new favorite individual provider service impl.
	 *
	 * @param individualProviderRepository
	 *            the individual provider repository
	 * @param modelMapper
	 *            the model mapper
	 * @param staffIndividualProviderRepository
	 *            the staff individual provider repository
	 */
	public FavoriteIndividualProviderServiceImpl(
			IndividualProviderRepository individualProviderRepository,
			ModelMapper modelMapper,
			StaffIndividualProviderRepository staffIndividualProviderRepository) {
		super();
		this.individualProviderRepository = individualProviderRepository;
		this.modelMapper = modelMapper;
		this.staffIndividualProviderRepository = staffIndividualProviderRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.provider.FavoriteIndividualProviderService
	 * #findAllStaffIndividualProviders()
	 */
	@Override
	public List<StaffIndividualProvider> findAllStaffIndividualProviders() {
		return staffIndividualProviderRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.provider.FavoriteIndividualProviderService
	 * #findAllStaffIndividualProvidersDto()
	 */
	@Override
	public List<StaffIndividualProviderDto> findAllStaffIndividualProvidersDto() {
		List<StaffIndividualProviderDto> providers = new ArrayList<StaffIndividualProviderDto>();

		for (StaffIndividualProvider entity : staffIndividualProviderRepository
				.findAll()) {
			providers.add(modelMapper.map(entity,
					StaffIndividualProviderDto.class));
		}
		return providers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.provider.FavoriteIndividualProviderService
	 * #isFavoriteIndividualProvider(long)
	 */
	@Override
	public boolean isFavoriteIndividualProvider(long id)
			throws IllegalArgumentException {
		StaffIndividualProvider searchedProvider = null;
		IndividualProvider individualProvider = findIndividualProvider(id);
		searchedProvider = staffIndividualProviderRepository
				.findByIndividualProvider(individualProvider);

		if (searchedProvider == null) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.provider.FavoriteIndividualProviderService
	 * #addFavoriteIndividualProvider(long)
	 */
	@Override
	public boolean addFavoriteIndividualProvider(long id)
			throws IllegalArgumentException {
		boolean isAlreadyFavorite = false;
		isAlreadyFavorite = isFavoriteIndividualProvider(id);

		if (isAlreadyFavorite == true) {
			logger.info("Individual Provider with id = '" + id
					+ "' is already a favorite.");
			return false;
		} else {
			StaffIndividualProvider staffIndividualProvider = new StaffIndividualProvider();
			IndividualProvider individualProvider = findIndividualProvider(id);

			if (individualProvider == null) {
				logger.warn("Unable to find an individual provider with id = '"
						+ id + "'.");
				throw new NullPointerException("findIndividualProvider(" + id
						+ ") in IndividualProviderService returned null");
			}

			staffIndividualProvider.setIndividualProvider(individualProvider);
			staffIndividualProviderRepository.save(staffIndividualProvider);
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.provider.FavoriteIndividualProviderService
	 * #addFavouriteIndividualProvider(gov.samhsa.consent2share.domain.provider.
	 * IndividualProvider)
	 */
	@Override
	public void addFavouriteIndividualProvider(
			IndividualProvider individualProvider) {
		StaffIndividualProvider staffIndividualProvider = new StaffIndividualProvider();
		staffIndividualProvider.setIndividualProvider(individualProvider);
		staffIndividualProvider.setId(0);
		staffIndividualProviderRepository.save(staffIndividualProvider);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.provider.FavoriteIndividualProviderService
	 * #deleteFavoriteIndividualProvider(long)
	 */
	@Override
	public void deleteFavoriteIndividualProvider(long id)
			throws IllegalArgumentException {
		staffIndividualProviderRepository.delete(id);
	}

	/**
	 * Find individual provider.
	 *
	 * @param id
	 *            the id
	 * @return the individual provider
	 */
	private IndividualProvider findIndividualProvider(Long id) {
		return individualProviderRepository.findOne(id);
	}
}
