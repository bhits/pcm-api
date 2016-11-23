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
package gov.samhsa.c2s.pcm.service.provider;


import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.domain.patient.PatientRepository;
import gov.samhsa.c2s.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.c2s.pcm.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.c2s.pcm.infrastructure.dto.Provider;
import gov.samhsa.c2s.pcm.service.dto.OrganizationalProviderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * The Class OrganizationalProviderServiceImpl.
 */
@Transactional
@Service
public class OrganizationalProviderServiceImpl implements
        OrganizationalProviderService {

    /**
     * The patient repository.
     */
    @Autowired
    private PatientRepository patientRepository;

    /**
     * The model mapper.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * The organizational provider repository.
     */
    @Autowired
    private OrganizationalProviderRepository organizationalProviderRepository;

    @Autowired
    private MapProviderResultToProviderDtoConverter mapProviderResultToProviderDtoConverter;

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #countAllOrganizationalProviders()
     */
    @Override
    public long countAllOrganizationalProviders() {
        return organizationalProviderRepository.count();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #deleteOrganizationalProvider
     * (gov.samhsa.consent2share.domain.provider.OrganizationalProvider)
     */
    @Override
    public void deleteOrganizationalProvider(
            OrganizationalProvider organizationalProvider) {
        organizationalProviderRepository.delete(organizationalProvider);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #deleteOrganizationalProviderDto
     * (gov.samhsa.consent2share.service.dto.OrganizationalProviderDto)
     */
    @Override
    public void deleteOrganizationalProviderDto(
            OrganizationalProviderDto organizationalProviderDto) {
        Patient patient = patientRepository
                .findByUsername(organizationalProviderDto.getUsername());
        Set<OrganizationalProvider> organizationalProviders = patient
                .getOrganizationalProviders();
        for (OrganizationalProvider o : organizationalProviders) {
            if (o.getNpi().equals(organizationalProviderDto.getNpi())) {
                organizationalProviders.remove(o);
                break;
            }
        }
        patient.setOrganizationalProviders(organizationalProviders);
        patientRepository.save(patient);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #deleteOrganizationalProviderDtoByPatientId
     * (gov.samhsa.consent2share.service.dto.OrganizationalProviderDto)
     */
    @Override
    public void deleteOrganizationalProviderDtoByPatientId(
            OrganizationalProviderDto organizationalProviderDto) {
        Patient patient = patientRepository.findOne(Long
                .parseLong(organizationalProviderDto.getPatientId()));
        Set<OrganizationalProvider> organizationalProviders = patient
                .getOrganizationalProviders();
        for (OrganizationalProvider o : organizationalProviders) {
            if (o.getNpi().equals(organizationalProviderDto.getNpi())) {
                organizationalProviders.remove(o);
                break;
            }
        }
        patient.setOrganizationalProviders(organizationalProviders);
        patientRepository.save(patient);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #findOrganizationalProvider(java.lang.Long)
     */
    @Override
    public OrganizationalProvider findOrganizationalProvider(Long id) {
        return organizationalProviderRepository.findOne(id);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #findOrganizationalProviderDto(java.lang.Long)
     */
    @Override
    public OrganizationalProviderDto findOrganizationalProviderDto(Long id) {
        OrganizationalProvider provider = organizationalProviderRepository
                .findOne(id);
        OrganizationalProviderDto providerDto = modelMapper.map(provider,
                OrganizationalProviderDto.class);

        return providerDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #findAllOrganizationalProviders()
     */
    @Override
    public List<OrganizationalProvider> findAllOrganizationalProviders() {
        return organizationalProviderRepository.findAll();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #findOrganizationalProviderEntries(int, int)
     */
    @Override
    public List<OrganizationalProvider> findOrganizationalProviderEntries(
            int firstResult, int maxResults) {
        return organizationalProviderRepository.findAll(
                new org.springframework.data.domain.PageRequest(firstResult
                        / maxResults, maxResults)).getContent();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #saveOrganizationalProvider
     * (gov.samhsa.consent2share.domain.provider.OrganizationalProvider)
     */
    @Override
    public void saveOrganizationalProvider(
            OrganizationalProvider organizationalProvider) {
        organizationalProviderRepository.save(organizationalProvider);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #updateOrganizationalProvider
     * (gov.samhsa.consent2share.domain.provider.OrganizationalProvider)
     */
    @Override
    public OrganizationalProvider updateOrganizationalProvider(
            OrganizationalProvider organizationalProvider) {
        return organizationalProviderRepository.save(organizationalProvider);
    }

    @Override
    public void deleteOrganizationalProviderByNpi(String username, String npi) {
        Patient patient = patientRepository.findByUsername(username);
        Set<OrganizationalProvider> organizationalProviders = patient.getOrganizationalProviders();
        for (OrganizationalProvider o : organizationalProviders) {
            if (o.getNpi().equals(npi)) {
                organizationalProviders.remove(o);
                break;
            }
        }
        patient.setOrganizationalProviders(organizationalProviders);
        patientRepository.save(patient);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #updateOrganizationalProvider
     * (gov.samhsa.consent2share.service.dto.OrganizationalProviderDto)
     */
    @Override
    public void updateOrganizationalProvider(
            OrganizationalProviderDto organizationalProviderDto) {
        Patient patient = patientRepository
                .findByUsername(organizationalProviderDto.getUsername());
        Set<OrganizationalProvider> organizationalProviders = patient
                .getOrganizationalProviders();
        OrganizationalProvider organizationalProvider = null;
        for (OrganizationalProvider o : organizationalProviders) {
            if (o.getNpi().equals(organizationalProviderDto.getNpi())) {
                organizationalProvider = o;
                break;
            }

        }
        if (organizationalProvider == null)
            organizationalProvider = new OrganizationalProvider();
        organizationalProvider.setOrgName(organizationalProviderDto
                .getOrgName());
        organizationalProvider
                .setAuthorizedOfficialFirstName(organizationalProviderDto
                        .getAuthorizedOfficialFirstName());
        organizationalProvider
                .setAuthorizedOfficialLastName(organizationalProviderDto
                        .getAuthorizedOfficialLastName());
        organizationalProvider
                .setAuthorizedOfficialTitle(organizationalProviderDto
                        .getAuthorizedOfficialTitle());
        organizationalProvider
                .setAuthorizedOfficialNamePrefix(organizationalProviderDto
                        .getAuthorizedOfficialNamePrefix());
        organizationalProvider
                .setAuthorizedOfficialTelephoneNumber(organizationalProviderDto
                        .getAuthorizedOfficialTelephoneNumber());
        organizationalProvider.setNpi(organizationalProviderDto.getNpi());
        organizationalProvider.setEntityType(organizationalProviderDto
                .getEntityType());
        organizationalProvider
                .setFirstLineMailingAddress(organizationalProviderDto
                        .getFirstLineMailingAddress());
        organizationalProvider
                .setSecondLineMailingAddress(organizationalProviderDto
                        .getSecondLineMailingAddress());
        organizationalProvider
                .setMailingAddressCityName(organizationalProviderDto
                        .getMailingAddressCityName());
        organizationalProvider
                .setMailingAddressStateName(organizationalProviderDto
                        .getMailingAddressStateName());
        organizationalProvider
                .setMailingAddressPostalCode(organizationalProviderDto
                        .getMailingAddressPostalCode());
        organizationalProvider
                .setMailingAddressCountryCode(organizationalProviderDto
                        .getMailingAddressCountryCode());
        organizationalProvider
                .setMailingAddressTelephoneNumber(organizationalProviderDto
                        .getMailingAddressTelephoneNumber());
        organizationalProvider
                .setMailingAddressFaxNumber(organizationalProviderDto
                        .getMailingAddressFaxNumber());
        organizationalProvider
                .setFirstLinePracticeLocationAddress(organizationalProviderDto
                        .getFirstLinePracticeLocationAddress());
        organizationalProvider
                .setSecondLinePracticeLocationAddress(organizationalProviderDto
                        .getSecondLinePracticeLocationAddress());
        organizationalProvider
                .setPracticeLocationAddressCityName(organizationalProviderDto
                        .getPracticeLocationAddressCityName());
        organizationalProvider
                .setPracticeLocationAddressStateName(organizationalProviderDto
                        .getPracticeLocationAddressStateName());
        organizationalProvider
                .setPracticeLocationAddressPostalCode(organizationalProviderDto
                        .getPracticeLocationAddressPostalCode());
        organizationalProvider
                .setPracticeLocationAddressCountryCode(organizationalProviderDto
                        .getPracticeLocationAddressCountryCode());
        organizationalProvider
                .setPracticeLocationAddressTelephoneNumber(organizationalProviderDto
                        .getPracticeLocationAddressTelephoneNumber());
        organizationalProvider
                .setPracticeLocationAddressFaxNumber(organizationalProviderDto
                        .getPracticeLocationAddressFaxNumber());
        organizationalProvider.setEnumerationDate(organizationalProviderDto
                .getEnumerationDate());
        organizationalProvider.setLastUpdateDate(organizationalProviderDto
                .getLastUpdateDate());
        organizationalProvider
                .setProviderTaxonomyCode(organizationalProviderDto
                        .getProviderTaxonomyCode());
        organizationalProvider
                .setProviderTaxonomyDescription(organizationalProviderDto
                        .getProviderTaxonomyDescription());
        organizationalProviders.add(organizationalProvider);
        patient.setOrganizationalProviders(organizationalProviders);

        patientRepository.save(patient);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.provider.OrganizationalProviderService
     * #addNewOrganizationalProvider
     * (gov.samhsa.consent2share.service.dto.OrganizationalProviderDto)
     */
    @Override
    public OrganizationalProvider addNewOrganizationalProvider(
            Provider provider, String username) {

        OrganizationalProviderDto organizationalProviderDto = new OrganizationalProviderDto();

        mapProviderResultToProviderDtoConverter.setProviderDto(organizationalProviderDto, provider);

        organizationalProviderDto.setOrgName(provider.getOrganizationName() == null ? "" : provider.getOrganizationName());
        organizationalProviderDto.setUsername(username);

        //TODO: Remove
        organizationalProviderDto.setAuthorizedOfficialLastName("");
        organizationalProviderDto.setAuthorizedOfficialFirstName("");
        organizationalProviderDto.setAuthorizedOfficialTitle("");
        organizationalProviderDto.setAuthorizedOfficialNamePrefix("");
        organizationalProviderDto.setAuthorizedOfficialTelephoneNumber("");

        Patient patient;
        String inNPI = organizationalProviderDto.getNpi();

        if (organizationalProviderDto.getUsername() == null
                && organizationalProviderDto.getPatientId() != null) {
            patient = patientRepository.findOne(Long.valueOf(
                    organizationalProviderDto.getPatientId()).longValue());
        } else {
            patient = patientRepository
                    .findByUsername(organizationalProviderDto.getUsername());
        }

        Set<OrganizationalProvider> organizationalProviders = patient
                .getOrganizationalProviders();

        OrganizationalProvider in_organizationalProvider = null;
        for (OrganizationalProvider o : organizationalProviders) {
            if (o.getNpi().equals(inNPI)) {
                in_organizationalProvider = o;
                break;
            }
        }

        if (in_organizationalProvider != null) {
            return null;
        } else {
            OrganizationalProvider organizationalProvider = new OrganizationalProvider();

            organizationalProvider.setOrgName(organizationalProviderDto
                    .getOrgName());
            organizationalProvider
                    .setAuthorizedOfficialFirstName(organizationalProviderDto
                            .getAuthorizedOfficialFirstName());
            organizationalProvider
                    .setAuthorizedOfficialLastName(organizationalProviderDto
                            .getAuthorizedOfficialLastName());
            organizationalProvider
                    .setAuthorizedOfficialTitle(organizationalProviderDto
                            .getAuthorizedOfficialTitle());
            organizationalProvider
                    .setAuthorizedOfficialNamePrefix(organizationalProviderDto
                            .getAuthorizedOfficialNamePrefix());
            organizationalProvider
                    .setAuthorizedOfficialTelephoneNumber(organizationalProviderDto
                            .getAuthorizedOfficialTelephoneNumber());
            organizationalProvider.setNpi(organizationalProviderDto.getNpi());
            organizationalProvider.setEntityType(organizationalProviderDto
                    .getEntityType());
            organizationalProvider
                    .setFirstLineMailingAddress(organizationalProviderDto
                            .getFirstLineMailingAddress());
            organizationalProvider
                    .setSecondLineMailingAddress(organizationalProviderDto
                            .getSecondLineMailingAddress());
            organizationalProvider
                    .setMailingAddressCityName(organizationalProviderDto
                            .getMailingAddressCityName());
            organizationalProvider
                    .setMailingAddressStateName(organizationalProviderDto
                            .getMailingAddressStateName());
            organizationalProvider
                    .setMailingAddressPostalCode(organizationalProviderDto
                            .getMailingAddressPostalCode());
            organizationalProvider
                    .setMailingAddressCountryCode(organizationalProviderDto
                            .getMailingAddressCountryCode());
            organizationalProvider
                    .setMailingAddressTelephoneNumber(organizationalProviderDto
                            .getMailingAddressTelephoneNumber());
            organizationalProvider
                    .setMailingAddressFaxNumber(organizationalProviderDto
                            .getMailingAddressFaxNumber());
            organizationalProvider
                    .setFirstLinePracticeLocationAddress(organizationalProviderDto
                            .getFirstLinePracticeLocationAddress());
            organizationalProvider
                    .setSecondLinePracticeLocationAddress(organizationalProviderDto
                            .getSecondLinePracticeLocationAddress());
            organizationalProvider
                    .setPracticeLocationAddressCityName(organizationalProviderDto
                            .getPracticeLocationAddressCityName());
            organizationalProvider
                    .setPracticeLocationAddressStateName(organizationalProviderDto
                            .getPracticeLocationAddressStateName());
            organizationalProvider
                    .setPracticeLocationAddressPostalCode(organizationalProviderDto
                            .getPracticeLocationAddressPostalCode());
            organizationalProvider
                    .setPracticeLocationAddressCountryCode(organizationalProviderDto
                            .getPracticeLocationAddressCountryCode());
            organizationalProvider
                    .setPracticeLocationAddressTelephoneNumber(organizationalProviderDto
                            .getPracticeLocationAddressTelephoneNumber());
            organizationalProvider
                    .setPracticeLocationAddressFaxNumber(organizationalProviderDto
                            .getPracticeLocationAddressFaxNumber());
            organizationalProvider.setEnumerationDate(organizationalProviderDto
                    .getEnumerationDate());
            organizationalProvider.setLastUpdateDate(organizationalProviderDto
                    .getLastUpdateDate());
            organizationalProvider
                    .setProviderTaxonomyCode(organizationalProviderDto
                            .getProviderTaxonomyCode());
            organizationalProvider
                    .setProviderTaxonomyDescription(organizationalProviderDto
                            .getProviderTaxonomyDescription());
            organizationalProviders.add(organizationalProvider);
            patient.setOrganizationalProviders(organizationalProviders);

            patient = patientRepository.save(patient);

            Set<OrganizationalProvider> organizationalProvidersReturned = patient
                    .getOrganizationalProviders();

            OrganizationalProvider in_organizationalProviderReturned = null;
            for (OrganizationalProvider o : organizationalProvidersReturned) {
                if (o.getNpi().equals(inNPI)) {
                    in_organizationalProviderReturned = o;
                    break;
                }
            }

            if (in_organizationalProviderReturned == null) {
                return null;
            } else {
                return in_organizationalProviderReturned;
            }

        }
    }

    /**
     * Find organizational provider by npi.
     *
     * @param npi the npi
     * @return the organizational provider
     */
    public OrganizationalProvider findOrganizationalProviderByNpi(String npi) {
        return organizationalProviderRepository.findByNpi(npi);
    }
}
