package gov.samhsa.pcm.service.provider;

import gov.samhsa.pcm.domain.provider.IndividualProvider;
import gov.samhsa.pcm.domain.provider.StaffIndividualProvider;
import gov.samhsa.pcm.service.dto.StaffIndividualProviderDto;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

/**
 * The Interface FavoriteIndividualProviderService.
 */
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
public interface FavoriteIndividualProviderService {
	/**
	 * Find all staff favorite individual providers.
	 *
	 * @return the list
	 */
	public abstract List<StaffIndividualProvider> findAllStaffIndividualProviders();

	/**
	 * Find all staff favorite individual providers dto.
	 *
	 * @return the list
	 */
	public abstract List<StaffIndividualProviderDto> findAllStaffIndividualProvidersDto();

	/**
	 * Checks if is favorite individual provider.
	 *
	 * @param id
	 *            the id
	 * @return true, if is favorite individual provider
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public abstract boolean isFavoriteIndividualProvider(long id)
			throws IllegalArgumentException;

	/**
	 * Adds the favorite individual provider.
	 *
	 * @param id
	 *            the id
	 * @return true, if successful
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public boolean addFavoriteIndividualProvider(long id)
			throws IllegalArgumentException;

	/**
	 * Adds the favourite individual provider.
	 *
	 * @param individualProvider
	 *            the individual provider
	 */
	public void addFavouriteIndividualProvider(
			IndividualProvider individualProvider);

	/**
	 * Delete favorite individual provider.
	 *
	 * @param id
	 *            the id
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public abstract void deleteFavoriteIndividualProvider(long id)
			throws IllegalArgumentException;
}
