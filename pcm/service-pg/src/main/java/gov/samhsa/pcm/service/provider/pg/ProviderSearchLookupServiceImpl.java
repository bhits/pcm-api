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
package gov.samhsa.pcm.service.provider.pg;

import gov.samhsa.pcm.service.dto.LookupDto;
import gov.samhsa.pcm.service.provider.HashMapResultToProviderDtoConverter;
import gov.samhsa.pcm.service.provider.IndividualProviderService;
import gov.samhsa.pcm.service.provider.OrganizationalProviderService;
import gov.samhsa.pcm.service.reference.StateCodeService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class ProviderSearchLookupServiceImpl.
 */
@Transactional
public class ProviderSearchLookupServiceImpl
		extends
		gov.samhsa.pcm.service.provider.ProviderSearchLookupServiceImpl {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The individual provider service. */
	IndividualProviderService individualProviderService;

	/** The organizational provider service. */
	OrganizationalProviderService organizationalProviderService;

	/** The hash map result to provider dto converter. */
	HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter;

	/**
	 * Instantiates a new provider search lookup service impl.
	 *
	 * @param providerSearchURL
	 *            the provider search url
	 * @param stateCodeService
	 *            the state code service
	 * @param individualProviderService
	 *            the individual provider service
	 * @param organizationalProviderService
	 *            the organizational provider service
	 * @param hashMapResultToProviderDtoConverter
	 *            the hash map result to provider dto converter
	 */
	public ProviderSearchLookupServiceImpl(
			String providerSearchURL,
			StateCodeService stateCodeService,
			IndividualProviderService individualProviderService,
			OrganizationalProviderService organizationalProviderService,
			HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter) {
		super(providerSearchURL, stateCodeService);
		this.individualProviderService = individualProviderService;
		this.organizationalProviderService = organizationalProviderService;
		this.hashMapResultToProviderDtoConverter = hashMapResultToProviderDtoConverter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.provider.ProviderSearchLookupServiceImpl
	 * #isValidatedSearch(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isValidatedSearch(String usstate, String city,
			String zipcode, String gender, String specialty, String phone,
			String firstname, String lastname, String facilityName) {
		boolean validateCall = true;
		if (usstate == null && city == null && zipcode == null && phone == null
				&& specialty == null && firstname == null && lastname == null
				&& facilityName == null && gender == null)
			validateCall = false;
		if (usstate != null && zipcode != null)
			validateCall = false;
		if (lastname == null && facilityName == null) {
			validateCall = false;
		}
		if (zipcode == null && city == null && usstate == null) {
			validateCall = false;
		}
		if (usstate != null) {
			if (usstate.matches("[a-zA-Z][a-zA-Z]") == true) {

				// Get a list of all valid US states from database via
				// stateCodeService service
				List<LookupDto> stateCodes = stateCodeService
						.findAllStateCodes();
				boolean isValidStateCode = false;

				// Loop through list of valid state codes and check if usstate
				// value
				// matches a valid state
				for (LookupDto stateCode : stateCodes) {
					if (usstate.compareToIgnoreCase(stateCode.getCode()) == 0) {
						isValidStateCode = true;
						break;
					}
				}

				// If no state in the list matched the input value for usstate,
				// then
				// set validateCall to false
				if (isValidStateCode != true) {
					validateCall = false;
				}
			} else
				validateCall = false;
		}

		if (city != null && city.length() < 2)
			validateCall = false;
		if (zipcode != null && zipcode.length() < 5)
			validateCall = false;
		if (specialty != null && specialty.length() < 3)
			validateCall = false;
		if (phone != null && phone.length() != 10)
			validateCall = false;
		if (firstname != null && firstname.length() < 2)
			validateCall = false;
		if (lastname != null && lastname.length() < 2)
			validateCall = false;
		if (facilityName != null && facilityName.length() < 2)
			validateCall = false;
		if (city != null && usstate == null)
			validateCall = false;

		return validateCall;
	}
}
