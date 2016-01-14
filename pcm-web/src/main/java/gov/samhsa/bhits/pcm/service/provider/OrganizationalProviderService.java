/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.bhits.pcm.service.provider;


import gov.samhsa.bhits.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.bhits.pcm.service.dto.OrganizationalProviderDto;

import java.util.List;

/**
 * The Interface OrganizationalProviderService.
 */
public interface OrganizationalProviderService {

	/**
	 * Count all organizational providers.
	 *
	 * @return the long
	 */
	public abstract long countAllOrganizationalProviders();

	/**
	 * Delete organizational provider.
	 *
	 * @param organizationalProvider
	 *            the organizational provider
	 */
	public abstract void deleteOrganizationalProvider(
			OrganizationalProvider organizationalProvider);

	/**
	 * Delete organizational provider dto.
	 *
	 * @param organizationalProviderDto
	 *            the organizational provider dto
	 */
	public abstract void deleteOrganizationalProviderDto(
			OrganizationalProviderDto organizationalProviderDto);

	/**
	 * Delete organizational provider by patient id.
	 *
	 * @param organizationalProviderDto
	 *            the organizational provider dto
	 */
	public abstract void deleteOrganizationalProviderDtoByPatientId(
			OrganizationalProviderDto organizationalProviderDto);

	/**
	 * Find organizational provider.
	 *
	 * @param id
	 *            the id
	 * @return the organizational provider
	 */
	public abstract OrganizationalProvider findOrganizationalProvider(Long id);

	/**
	 * Find organizational provider dto.
	 *
	 * @param id
	 *            the id
	 * @return the organizational provider dto
	 */
	public abstract OrganizationalProviderDto findOrganizationalProviderDto(
			Long id);

	/**
	 * Update organizational provider.
	 *
	 * @param organizationalProviderDto
	 *            the organizational provider dto
	 */
	public abstract void updateOrganizationalProvider(
			OrganizationalProviderDto organizationalProviderDto);
	
	
	public abstract void  deleteOrganizationalProviderByNpi(String npi);

	/**
	 * Add new organizational provider
	 * 
	 * Returns in_organizationalProviderReturned if added successfully; Returns
	 * null if add fails (e.g. if added provider already exists)
	 * 
	 * @param organizationalProviderDto
	 * @return OrganizationalProvider in_organizationalProviderReturned
	 */
	public abstract OrganizationalProvider addNewOrganizationalProvider(
			OrganizationalProviderDto organizationalProviderDto);

	/**
	 * Find all organizational providers.
	 *
	 * @return the list
	 */
	public abstract List<OrganizationalProvider> findAllOrganizationalProviders();

	/**
	 * Find organizational provider entries.
	 *
	 * @param firstResult
	 *            the first result
	 * @param maxResults
	 *            the max results
	 * @return the list
	 */
	public abstract List<OrganizationalProvider> findOrganizationalProviderEntries(
			int firstResult, int maxResults);

	/**
	 * Save organizational provider.
	 *
	 * @param organizationalProvider
	 *            the organizational provider
	 */
	public abstract void saveOrganizationalProvider(
			OrganizationalProvider organizationalProvider);

	/**
	 * Update organizational provider.
	 *
	 * @param organizationalProvider
	 *            the organizational provider
	 * @return the organizational provider
	 */
	public abstract OrganizationalProvider updateOrganizationalProvider(
			OrganizationalProvider organizationalProvider);
}
