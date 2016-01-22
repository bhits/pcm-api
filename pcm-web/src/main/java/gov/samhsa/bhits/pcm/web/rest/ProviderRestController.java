/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p/>
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
package gov.samhsa.bhits.pcm.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.samhsa.bhits.pcm.domain.provider.IndividualProvider;
import gov.samhsa.bhits.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.bhits.pcm.domain.reference.EntityType;
import gov.samhsa.bhits.pcm.service.dto.IndividualProviderDto;
import gov.samhsa.bhits.pcm.service.dto.OrganizationalProviderDto;
import gov.samhsa.bhits.pcm.service.dto.ProviderDto;
import gov.samhsa.bhits.pcm.service.exception.ProviderAlreadyInUseException;
import gov.samhsa.bhits.pcm.service.exception.CannotDeleteProviderException;
import gov.samhsa.bhits.pcm.service.exception.CannotDeserializeProviderResultException;
import gov.samhsa.bhits.pcm.service.exception.ProviderNotFoundException;
import gov.samhsa.bhits.pcm.service.patient.PatientService;
import gov.samhsa.bhits.pcm.service.provider.HashMapResultToProviderDtoConverter;
import gov.samhsa.bhits.pcm.service.provider.IndividualProviderService;
import gov.samhsa.bhits.pcm.service.provider.OrganizationalProviderService;
import gov.samhsa.bhits.pcm.service.provider.ProviderSearchLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * The Class ProviderController.
 */
@RestController
@RequestMapping("/patients")
public class ProviderRestController {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The Constant NPI_LENGTH.
     */
    public static final int NPI_LENGTH = 10;

    /**
     * The patient service.
     */
    @Autowired
    private PatientService patientService;

    /**
     * The individual provider service.
     */
    @Autowired
    private IndividualProviderService individualProviderService;

    /**
     * The organizational provider service.
     */
    @Autowired
    private OrganizationalProviderService organizationalProviderService;

    /**
     * The provider search lookup service.
     */
    @Autowired
    private ProviderSearchLookupService providerSearchLookupService;

    /**
     * The hash map result to provider dto converter.
     */
    @Autowired
    private HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * List providers.
     *
     * @return the sets the
     */
    @RequestMapping(value = "providers", method = RequestMethod.GET)
    public Set<ProviderDto> listProviders() {
        Set<ProviderDto> providerDtos = patientService.findProvidersByUsername("albert.smith");
        return providerDtos;
    }

    /**
     * Delete provider.
     *
     * @param npi the npi
     */
    @RequestMapping(value = "providers/{npi}", method = RequestMethod.DELETE)
    public void deleteProvider(@PathVariable("npi") String npi) {

        Set<ProviderDto> providerDtos = patientService.findProvidersByUsername("albert.smith");
        try {
            ProviderDto providerDto = providerDtos.stream().filter(t -> t.getNpi().equals(npi)).findAny().orElseThrow(() -> new ProviderNotFoundException("This patient doesn't have this provider"));

            if (providerDto == null)
                new CannotDeleteProviderException("ERROR: Unable to delete this provider.");
            else {
                if (providerDto.isDeletable() == false)
                    throw new ProviderAlreadyInUseException("Error: Unable to delete this provider because it is currently used in one or more of your consents.");
                if (providerDto.isDeletable() == true && providerDto.getEntityType().equals("Individual"))
                    individualProviderService.deleteIndividualProviderByNpi(npi);
                else
                    organizationalProviderService.deleteOrganizationalProviderByNpi(npi);
            }

        } catch (Exception e) {
            logger.error(
                    "Unable to delete individual provider: PatientConnectionDto could not be found for specified patientId...");
            logger.error("...STACK TRACE: ", e);
            throw e;
        }

    }

    /**
     * Adds the provider.
     *
     * @param npi the npi
     */
    @RequestMapping(value = "providers/{npi}", method = RequestMethod.POST)
    public void addProvider(@PathVariable("npi") String npi) {

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
                if (organizationalProviderReturned == null)
                    throw new ProviderAlreadyInUseException("Error: The provider could not be added because the provider already exists in the patient’s account.");
            } else {
                if (individualProviderReturned == null)
                    throw new ProviderAlreadyInUseException("Error: The provider could not be added because the provider already exists in the patient’s account.");
            }
        } else {
            throw new ProviderNotFoundException("Error:The provider could not be added because the specified NPI could not be found.");
        }

    }

    private HashMap<String, String> deserializeResult(String providerDtoJSON) {
        try {
            return objectMapper.readValue(providerDtoJSON, HashMap.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new CannotDeserializeProviderResultException(e.getMessage(), e);
        }
    }
}
