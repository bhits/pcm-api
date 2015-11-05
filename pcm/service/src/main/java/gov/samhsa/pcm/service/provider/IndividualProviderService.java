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
package gov.samhsa.pcm.service.provider;

import gov.samhsa.pcm.domain.provider.IndividualProvider;
import gov.samhsa.pcm.service.dto.IndividualProviderDto;

import java.util.List;

/**
 * The Interface IndividualProviderService.
 */
public interface IndividualProviderService {

	/**
	 * Count all individual providers.
	 *
	 * @return the long
	 */
	public abstract long countAllIndividualProviders();

	/**
	 * Delete individual provider.
	 *
	 * @param individualProvider
	 *            the individual provider
	 */
	public abstract void deleteIndividualProvider(
			IndividualProvider individualProvider);

	/**
	 * Delete individual provider dto.
	 *
	 * @param individualProviderDto
	 *            the individual provider dto
	 */
	public abstract void deleteIndividualProviderDto(
			IndividualProviderDto individualProviderDto);

	/**
	 * Delete individual provider by patient id.
	 *
	 * @param individualProviderDto
	 *            the individual provider dto
	 */
	public abstract void deleteIndividualProviderDtoByPatientId(
			IndividualProviderDto individualProviderDto);
	
	public abstract void deleteIndividualProviderByNpi(String npi);

	/**
	 * Find individual provider.
	 *
	 * @param id
	 *            the id
	 * @return the individual provider
	 */
	public abstract IndividualProvider findIndividualProvider(Long id);

	/**
	 * Find individual provider dto.
	 *
	 * @param id
	 *            the id
	 * @return the individual provider dto
	 */
	public abstract IndividualProviderDto findIndividualProviderDto(Long id);

	/**
	 * Find all individual providers.
	 *
	 * @return the list
	 */
	public abstract List<IndividualProvider> findAllIndividualProviders();

	/**
	 * Find individual provider by npi.
	 *
	 * @param npi
	 *            the npi
	 * @return the individual provider
	 */
	public abstract IndividualProvider findIndividualProviderByNpi(String npi);

	/**
	 * Find all individual providers dto.
	 *
	 * @return the list
	 */
	public abstract List<IndividualProviderDto> findAllIndividualProvidersDto();

	/**
	 * Find individual provider entries.
	 *
	 * @param firstResult
	 *            the first result
	 * @param maxResults
	 *            the max results
	 * @return the list
	 */
	public abstract List<IndividualProvider> findIndividualProviderEntries(
			int firstResult, int maxResults);

	/**
	 * Save individual provider.
	 *
	 * @param individualProvider
	 *            the individual provider
	 */
	public abstract void saveIndividualProvider(
			IndividualProvider individualProvider);

	/**
	 * Update individual provider.
	 *
	 * @param individualProvider
	 *            the individual provider
	 * @return the individual provider
	 */
	public abstract IndividualProvider updateIndividualProvider(
			IndividualProvider individualProvider);

	/**
	 * Update individual provider.
	 *
	 * @param individualProviderDto
	 *            the individual provider dto
	 */
	public abstract void updateIndividualProvider(
			IndividualProviderDto individualProviderDto);

	/**
	 * Add new individual provider
	 * 
	 * Returns in_individualProviderReturned if added successfully; Returns null
	 * if add fails (e.g. if added provider already exists)
	 *
	 * @param individualProviderDto
	 *            the individual provider dto
	 * @return IndividualProvider in_individualProviderReturned
	 */
	public abstract IndividualProvider addNewIndividualProvider(
			IndividualProviderDto individualProviderDto);
}
