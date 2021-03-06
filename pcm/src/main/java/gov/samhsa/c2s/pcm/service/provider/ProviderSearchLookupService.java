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
package gov.samhsa.c2s.pcm.service.provider;

import gov.samhsa.c2s.pcm.infrastructure.dto.ProviderDto;
import gov.samhsa.c2s.pcm.service.dto.MultiProviderRequestDto;

import java.security.Principal;

/**
 * The Interface ProviderSearchLookupService.
 */
public interface ProviderSearchLookupService {

	/**
	 * Checks if is validated search.
	 *
	 * @param usstate
	 *            the usstate
	 * @param city
	 *            the city
	 * @param zipcode
	 *            the zipcode
	 * @param gender
	 *            the gender
	 * @param specialty
	 *            the specialty
	 * @param phone
	 *            the phone
	 * @param firstname
	 *            the firstname
	 * @param lastname
	 *            the lastname
	 * @param facilityName
	 *            the facility name
	 * @return true, if is validated search
	 */
	boolean isValidatedSearch(String usstate, String city, String zipcode,
							  String gender, String specialty, String phone, String firstname,
							  String lastname, String facilityName);

	/**
	 * Provider search by npi.
	 *
	 * @param npi
	 *            the npi
	 * @return the string
	 */
	public ProviderDto providerSearchByNpi(String npi);

	public void addProvider(String username, String npi);

	public void addMultipleProviders(String username,MultiProviderRequestDto npiList);

}