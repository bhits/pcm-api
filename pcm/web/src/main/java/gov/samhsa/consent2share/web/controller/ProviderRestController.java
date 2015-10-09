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
package gov.samhsa.consent2share.web.controller;

import flexjson.JSONDeserializer;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.OrganizationalProvider;
import gov.samhsa.consent2share.domain.reference.EntityType;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;
import gov.samhsa.consent2share.service.dto.OrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.ProviderDto;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.provider.HashMapResultToProviderDtoConverter;
import gov.samhsa.consent2share.service.provider.IndividualProviderService;
import gov.samhsa.consent2share.service.provider.OrganizationalProviderService;
import gov.samhsa.consent2share.service.provider.ProviderSearchLookupService;
import gov.samhsa.consent2share.web.AjaxException;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class ProviderController.
 */
@RestController
@RequestMapping("/patients")
public class ProviderRestController extends AbstractController {

	/** The patient service. */
	@Autowired
	PatientService patientService;

	/** The individual provider service. */
	@Autowired
	IndividualProviderService individualProviderService;

	/** The organizational provider service. */
	@Autowired
	OrganizationalProviderService organizationalProviderService;

	/** The provider search lookup service. */
	@Autowired
	ProviderSearchLookupService providerSearchLookupService;

	/** The hash map result to provider dto converter. */
	@Autowired
	HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Constant NPI_LENGTH. */
	public static final int NPI_LENGTH = 10;

	/**
	 * Connection main.
	 *
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "providers", method = RequestMethod.GET)
	public Set<ProviderDto> connectionMain() {
		Set<ProviderDto> providerDtos = patientService.findProvidersByUsername("albert.smith");
		return providerDtos;
	}

	/**
	 * Delete individual provider.
	 * 
	 * @param individualProviderid
	 *            the individual providerid
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "providers/{npi}", method = RequestMethod.DELETE)
	public String deleteProvider(@PathVariable("npi") String npi) {

		Set<ProviderDto> providerDtos = patientService.findProvidersByUsername("albert.smith");
		try {
			ProviderDto providerDto = providerDtos.stream().filter(t -> t.getNpi().equals(npi)).findAny().get();

			if (providerDto == null)
				throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR: Unable to delete this provider.");
			else {
				if (providerDto.isDeletable() == false)
					throw new AjaxException(HttpStatus.CONFLICT,
							"Unable to delete this provider because it is currently used in one or more of your consents.");
				if (providerDto.isDeletable() == true && providerDto.getEntityType().equals("Individual"))
					individualProviderService.deleteIndividualProviderByNpi(npi);
				else
					organizationalProviderService.deleteOrganizationalProviderByNpi(npi);
			}
			return "Success.";

		} catch (Exception e) {
			logger.error(
					"Unable to delete individual provider: PatientConnectionDto could not be found for specified patientId...");
			logger.error("...STACK TRACE: ", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR: Unable to delete this provider.");
		}

	}

	@RequestMapping(value = "providers/{npi}", method = RequestMethod.GET)
	public String checkProvider(@PathVariable("npi") String npi) {

		return npi;

	}

	@RequestMapping(value = "providers/{npi}", method = RequestMethod.POST)
	public String addProvider(@PathVariable("npi") String npi) {

		OrganizationalProvider organizationalProviderReturned = null;
		IndividualProvider individualProviderReturned = null;
		boolean isOrgProvider = false;

		if (npi.length() == NPI_LENGTH && npi.matches("[0-9]+")) {
			String providerDtoJSON = providerSearchLookupService.providerSearchByNpi(npi);

			HashMap<String, String> result = deserializeResult(providerDtoJSON);

			if ((EntityType.valueOf(result.get("entityType")) == EntityType.Organization)) {
				isOrgProvider = true;
				OrganizationalProviderDto providerDto = new OrganizationalProviderDto();
				hashMapResultToProviderDtoConverter.setProviderDto(providerDto, result);
				providerDto.setOrgName(result.get("providerOrganizationName"));
				providerDto.setAuthorizedOfficialLastName(result.get("authorizedOfficialLastName"));
				providerDto.setAuthorizedOfficialFirstName(result.get("authorizedOfficialFirstName"));
				providerDto.setAuthorizedOfficialTitle(result.get("authorizedOfficialTitleorPosition"));
				providerDto.setAuthorizedOfficialNamePrefix(result.get("authorizedOfficialNamePrefixText"));
				providerDto.setAuthorizedOfficialTelephoneNumber(result.get("authorizedOfficialTelephoneNumber"));
				// TODO: hardcoded the patient username
				providerDto.setUsername("albert.smith");

				organizationalProviderReturned = organizationalProviderService
						.addNewOrganizationalProvider(providerDto);
			} else {
				isOrgProvider = false;
				IndividualProviderDto providerDto = new IndividualProviderDto();
				hashMapResultToProviderDtoConverter.setProviderDto(providerDto, result);
				providerDto.setFirstName(result.get("providerFirstName"));
				providerDto.setMiddleName(result.get("providerMiddleName"));
				providerDto.setLastName(result.get("providerLastName"));
				providerDto.setNamePrefix(result.get("providerNamePrefixText"));
				providerDto.setNameSuffix(result.get("providerNameSuffixText"));
				providerDto.setCredential(result.get("providerCredentialText"));

				// TODO: hardcoded the patient username
				providerDto.setUsername("albert.smith");

				individualProviderReturned = individualProviderService.addNewIndividualProvider(providerDto);
			}

			if (isOrgProvider == true) {
				if (organizationalProviderReturned != null) {
					return "Success";
				} else {
					throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
							"Unable to add this new provider because this provider already exists.");
				}
			} else {
				if (individualProviderReturned != null) {
					return "Success";
				} else {
					throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
							"Unable to add this new provider because this provider already exists.");
				}
			}
		} else {
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Unable to add this new provider because this provider npi is not existing.");
		}

	}

	/**
	 * Deserialize result.
	 * 
	 * @param providerDtoJSON
	 *            the provider dto json
	 * @return the hash map
	 */
	public HashMap<String, String> deserializeResult(String providerDtoJSON) {
		return new JSONDeserializer<HashMap<String, String>>().deserialize(providerDtoJSON);
	}

}
