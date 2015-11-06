package gov.samhsa.pcm.service.provider;

import gov.samhsa.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.pcm.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.pcm.domain.provider.StaffOrganizationalProvider;
import gov.samhsa.pcm.domain.provider.StaffOrganizationalProviderRepository;
import gov.samhsa.pcm.service.dto.StaffOrganizationalProviderDto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class FavoriteOrganizationalProviderServiceImpl.
 */
@Transactional
public class FavoriteOrganizationalProviderServiceImpl implements
		FavoriteOrganizationalProviderService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The organizational provider repository. */
	OrganizationalProviderRepository organizationalProviderRepository;

	/** The model mapper. */
	ModelMapper modelMapper;

	/** The staff organizational provider repository. */
	StaffOrganizationalProviderRepository staffOrganizationalProviderRepository;

	/**
	 * Instantiates a new favorite organizational provider service impl.
	 *
	 * @param organizationalProviderRepository
	 *            the organizational provider repository
	 * @param modelMapper
	 *            the model mapper
	 * @param staffOrganizationalProviderRepository
	 *            the staff organizational provider repository
	 */
	public FavoriteOrganizationalProviderServiceImpl(
			OrganizationalProviderRepository organizationalProviderRepository,
			ModelMapper modelMapper,
			StaffOrganizationalProviderRepository staffOrganizationalProviderRepository) {
		super();
		this.organizationalProviderRepository = organizationalProviderRepository;
		this.modelMapper = modelMapper;
		this.staffOrganizationalProviderRepository = staffOrganizationalProviderRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.
	 * FavoriteOrganizationalProviderService
	 * #findAllFavoriteOrganizationalProviders()
	 */
	@Override
	public List<StaffOrganizationalProvider> findAllFavoriteOrganizationalProviders() {
		return staffOrganizationalProviderRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.
	 * FavoriteOrganizationalProviderService
	 * #findAllStaffOrganizationalProvidersDto()
	 */
	@Override
	public List<StaffOrganizationalProviderDto> findAllStaffOrganizationalProvidersDto() {
		List<StaffOrganizationalProviderDto> providers = new ArrayList<StaffOrganizationalProviderDto>();

		for (StaffOrganizationalProvider entity : staffOrganizationalProviderRepository
				.findAll()) {
			providers.add(modelMapper.map(entity,
					StaffOrganizationalProviderDto.class));
		}
		return providers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.
	 * FavoriteOrganizationalProviderService
	 * #isFavoriteOrganizationalProvider(long)
	 */
	@Override
	public boolean isFavoriteOrganizationalProvider(long id)
			throws IllegalArgumentException {
		StaffOrganizationalProvider searchedProvider = null;
		OrganizationalProvider organizationalProvider = findOrganizationalProvider(id);
		searchedProvider = staffOrganizationalProviderRepository
				.findByOrganizationalProvider(organizationalProvider);

		if (searchedProvider == null) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.
	 * FavoriteOrganizationalProviderService
	 * #addFavouriteOrganizationalProvider(gov
	 * .samhsa.consent2share.domain.provider.OrganizationalProvider)
	 */
	@Override
	public void addFavouriteOrganizationalProvider(
			OrganizationalProvider organizationalProvider) {
		StaffOrganizationalProvider staffOrganizationalProvider = new StaffOrganizationalProvider();
		staffOrganizationalProvider
				.setOrganizationalProvider(organizationalProvider);
		staffOrganizationalProvider.setId(0);
		staffOrganizationalProviderRepository.save(staffOrganizationalProvider);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.
	 * FavoriteOrganizationalProviderService
	 * #addFavoriteOrganizationalProvider(long)
	 */
	@Override
	public boolean addFavoriteOrganizationalProvider(long id)
			throws IllegalArgumentException {
		boolean isAlreadyFavorite = false;
		isAlreadyFavorite = isFavoriteOrganizationalProvider(id);

		if (isAlreadyFavorite == true) {
			logger.info("Organizational Provider with id = '" + id
					+ "' is already a favorite.");
			return false;
		} else {
			StaffOrganizationalProvider staffOrganizationalProvider = new StaffOrganizationalProvider();
			OrganizationalProvider organizationalProvider = findOrganizationalProvider(id);

			if (organizationalProvider == null) {
				logger.warn("Unable to find an organizational provider with id = '"
						+ id + "'.");
				throw new NullPointerException("findOrganizationalProvider("
						+ id
						+ ") in OrganizationalProviderService returned null");
			}

			staffOrganizationalProvider
					.setOrganizationalProvider(organizationalProvider);
			staffOrganizationalProviderRepository
					.save(staffOrganizationalProvider);
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.provider.
	 * FavoriteOrganizationalProviderService
	 * #deleteFavoriteOrganizationalProvider(long)
	 */
	@Override
	public void deleteFavoriteOrganizationalProvider(long id)
			throws IllegalArgumentException {
		staffOrganizationalProviderRepository.delete(id);
	}

	/**
	 * Find organizational provider.
	 *
	 * @param id
	 *            the id
	 * @return the organizational provider
	 */
	private OrganizationalProvider findOrganizationalProvider(Long id) {
		return organizationalProviderRepository.findOne(id);

	}
}
