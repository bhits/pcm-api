package gov.samhsa.consent2share.service.provider;

import gov.samhsa.consent2share.domain.provider.OrganizationalProvider;
import gov.samhsa.consent2share.domain.provider.StaffOrganizationalProvider;
import gov.samhsa.consent2share.service.dto.StaffOrganizationalProviderDto;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

/**
 * The Interface FavoriteOrganizationalProviderService.
 */
@Secured({ "ROLE_USER", "ROLE_ADMIN" })
public interface FavoriteOrganizationalProviderService {

	/**
	 * Find all favorite organizational providers.
	 *
	 * @return the list
	 */
	public abstract List<StaffOrganizationalProvider> findAllFavoriteOrganizationalProviders();

	/**
	 * Find all staff organizational providers dto.
	 *
	 * @return the list
	 */
	public abstract List<StaffOrganizationalProviderDto> findAllStaffOrganizationalProvidersDto();

	/**
	 * Checks if is favorite organizational provider.
	 *
	 * @param id
	 *            the id
	 * @return true, if is favorite organizational provider
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public abstract boolean isFavoriteOrganizationalProvider(long id)
			throws IllegalArgumentException;

	/**
	 * Adds the favourite organizational provider.
	 *
	 * @param organizationalProvider
	 *            the organizational provider
	 */
	public void addFavouriteOrganizationalProvider(
			OrganizationalProvider organizationalProvider);

	/**
	 * Adds the favorite organizational provider.
	 *
	 * @param id
	 *            the id
	 * @return true, if successful
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public abstract boolean addFavoriteOrganizationalProvider(long id)
			throws IllegalArgumentException;

	/**
	 * Delete favorite organizational provider.
	 *
	 * @param id
	 *            the id
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public abstract void deleteFavoriteOrganizationalProvider(long id)
			throws IllegalArgumentException;
}
