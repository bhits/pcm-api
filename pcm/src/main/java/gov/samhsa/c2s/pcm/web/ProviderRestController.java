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
package gov.samhsa.c2s.pcm.web;

import gov.samhsa.c2s.pcm.domain.provider.IndividualProvider;
import gov.samhsa.c2s.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.c2s.pcm.domain.reference.EntityType;
import gov.samhsa.c2s.pcm.infrastructure.dto.ProviderDto;
import gov.samhsa.c2s.pcm.service.exception.CannotDeleteProviderException;
import gov.samhsa.c2s.pcm.service.exception.ProviderAlreadyInUseException;
import gov.samhsa.c2s.pcm.service.exception.ProviderNotFoundException;
import gov.samhsa.c2s.pcm.service.patient.PatientService;
import gov.samhsa.c2s.pcm.service.provider.IndividualProviderService;
import gov.samhsa.c2s.pcm.service.provider.OrganizationalProviderService;
import gov.samhsa.c2s.pcm.service.provider.ProviderSearchLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Set;

/**
 * The Class ProviderController.
 */
@RestController
@RequestMapping("/patients")
public class ProviderRestController {

    /**
     * The Constant NPI_LENGTH.
     */
    public static final int NPI_LENGTH = 10;
    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
     * List providers.
     *
     * @return the sets the
     */
    @RequestMapping(value = "providers", method = RequestMethod.GET)
    public Set<gov.samhsa.c2s.pcm.service.dto.ProviderDto> listProviders() {
        // FIXME (#26): remove this line when patient creation concept in PCM is finalized
        final Long patientId = patientService.createNewPatientWithOAuth2AuthenticationIfNotExists();
        Set<gov.samhsa.c2s.pcm.service.dto.ProviderDto> providerDtos = patientService.findProvidersByPatientId(patientId);
        return providerDtos;
    }

    /**
     * Delete provider.
     *
     * @param npi the npi
     */
    @RequestMapping(value = "providers/{npi}", method = RequestMethod.DELETE)
    public void deleteProvider(Principal principal, @PathVariable("npi") String npi) {
      
        Set<gov.samhsa.c2s.pcm.service.dto.ProviderDto> providerDtos = patientService.findProvidersByUsername(principal.getName());
        ProviderDto providerDto = providerDtos.stream().filter(t -> t.getNpi().equals(npi)).findAny().orElseThrow(() -> new ProviderNotFoundException("This patient doesn't have this provider"));

        if (providerDto == null) {
            new CannotDeleteProviderException("ERROR: Unable to delete this provider.");
        } else {
            if (providerDto.isDeletable() == false) {
                throw new ProviderAlreadyInUseException("Error: Unable to delete this provider because it is currently used in one or more of your consents.");
            }
            if (providerDto.isDeletable() == true && providerDto.getEntityType().equals("Individual")) {
                individualProviderService.deleteIndividualProviderByNpi(principal.getName(), npi);
            } else {
                organizationalProviderService.deleteOrganizationalProviderByNpi(principal.getName(), npi);
            }
        }
    }

    /**
     * Adds the provider.
     *
     * @param npi the npi
     */
    @RequestMapping(value = "providers/{npi}", method = RequestMethod.POST)
    public void addProvider(Principal principal, @PathVariable("npi") String npi) {
        final String username = principal.getName();
        OrganizationalProvider organizationalProviderReturned = null;
        IndividualProvider individualProviderReturned = null;
        boolean isOrgProvider = false;

        if (npi.length() == NPI_LENGTH && npi.matches("[0-9]+")) {

            ProviderDto providerDto = providerSearchLookupService.providerSearchByNpi(npi);

            if ((EntityType.valueOf(providerDto.getEntityType().getDisplayName()) == EntityType.Organization)) {
                isOrgProvider = true;
                organizationalProviderReturned = organizationalProviderService
                        .addNewOrganizationalProvider(providerDto, username);
            } else {
                isOrgProvider = false;
                individualProviderReturned = individualProviderService.addNewIndividualProvider(providerDto, username);
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
}