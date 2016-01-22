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
package gov.samhsa.mhc.pcm.service.patient;

import gov.samhsa.mhc.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.mhc.pcm.domain.commondomainservices.EmailType;
import gov.samhsa.mhc.pcm.domain.consent.*;
import gov.samhsa.mhc.pcm.domain.patient.Patient;
import gov.samhsa.mhc.pcm.domain.patient.PatientLegalRepresentativeAssociation;
import gov.samhsa.mhc.pcm.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.mhc.pcm.domain.patient.PatientRepository;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProvider;
import gov.samhsa.mhc.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.mhc.pcm.service.dto.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;

/**
 * The Class PatientServiceImpl.
 */
@Transactional
@Service
public class PatientServiceImpl implements PatientService {

    /**
     * The Constant LAST_N_DIGITS_OF_SSN.
     */
    public static final Integer LAST_N_DIGITS_OF_SSN = 4;

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The patient repository.
     */
    @Autowired
    private PatientRepository patientRepository;

    /**
     * The patient legal representative association repository.
     */
    @Autowired
    private PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

    /**
     * The model mapper.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * The password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * The email sender.
     */
    @Autowired
    private EmailSender emailSender;

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.patient.PatientService#countAllPatients
     * ()
     */
    @Override
    @Transactional(readOnly = true)
    public long countAllPatients() {
        return patientRepository.count();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.patient.PatientService#findPatient(java
     * .lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public PatientProfileDto findPatient(Long id) {
        Patient patient = patientRepository.findOne(id);
        PatientProfileDto patientDto = modelMapper.map(patient,
                PatientProfileDto.class);
        return patientDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findAllPatientByFirstNameAndLastName(java.lang.String[])
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientAdminDto> findAllPatientByFirstNameAndLastName(
            String[] tokens) {
        List<Patient> patients;
        if (tokens.length == 1) {
            patients = patientRepository
                    .findAllByFirstNameLikesAndLastNameLikes("%" + tokens[0]
                            + "%");
        } else if (tokens.length >= 2) {
            patients = patientRepository
                    .findAllByFirstNameLikesAndLastNameLikes("%" + tokens[0]
                            + "%", "%" + tokens[1] + "%");
        } else {
            patients = new ArrayList<Patient>();
        }
        List<PatientAdminDto> PatientAdminDtoList = mapPatientListToPatientAdminDtoList(patients);
        return PatientAdminDtoList;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.patient.PatientService#findByUsername
     * (java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public PatientProfileDto findByUsername(String username) {
        Patient patient = patientRepository.findByUsername(username);
        PatientProfileDto patientDto = modelMapper.map(patient,
                PatientProfileDto.class);
        return patientDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.patient.PatientService#findIdByUsername
     * (java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public Long findIdByUsername(String username) {
        return patientRepository.findByUsername(username).getId();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.patient.PatientService#findUsernameById
     * (java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public String findUsernameById(long id) {
        return patientRepository.findOne(id).getUsername();
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findPatientEmailByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public String findPatientEmailByUsername(String username) {
        return patientRepository.findByUsername(username).getEmail();
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findPatientProfileByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public PatientProfileDto findPatientProfileByUsername(String username) {
        Patient patient = patientRepository.findByUsername(username);
        PatientProfileDto patientDto = modelMapper.map(patient,
                PatientProfileDto.class);

        return patientDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findPatientConnectionByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public PatientConnectionDto findPatientConnectionByUsername(String username) {
        Patient patient = patientRepository.findByUsername(username);
        return findPatientConnectionByPatient(patient);
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findPatientConnectionByPatient
     * (gov.samhsa.consent2share.domain.patient.Patient)
     */
    @Override
    @Transactional(readOnly = true)
    public PatientConnectionDto findPatientConnectionByPatient(Patient patient) {
        PatientConnectionDto patientConnectionDto = modelMapper.map(patient,
                PatientConnectionDto.class);

        Set<IndividualProvider> consentIndividualProviders = new HashSet<IndividualProvider>();
        Set<OrganizationalProvider> consentOrganizationalProviders = new HashSet<OrganizationalProvider>();

        if (patient.getConsents() != null) {
            Set<Consent> consents = patient.getConsents();
            for (Consent consent : consents) {
                Set<ConsentOrganizationalProviderDisclosureIsMadeTo> consentOrganizationalProviderDisclosureIsMadeTos = consent
                        .getOrganizationalProvidersDisclosureIsMadeTo();
                if (consentOrganizationalProviderDisclosureIsMadeTos.size() != 0)
                    for (ConsentOrganizationalProviderDisclosureIsMadeTo cop : consentOrganizationalProviderDisclosureIsMadeTos) {
                        consentOrganizationalProviders.add(cop
                                .getOrganizationalProvider());
                    }

                Set<ConsentOrganizationalProviderPermittedToDisclose> consentOrganizationalProviderPermittedToDiscloses = consent
                        .getOrganizationalProvidersPermittedToDisclose();
                if (consentOrganizationalProviderPermittedToDiscloses.size() != 0)
                    for (ConsentOrganizationalProviderPermittedToDisclose copp : consentOrganizationalProviderPermittedToDiscloses) {
                        consentOrganizationalProviders.add(copp
                                .getOrganizationalProvider());
                    }

                Set<ConsentIndividualProviderDisclosureIsMadeTo> consentIndividualProviderDisclosureIsMadeTos = consent
                        .getProvidersDisclosureIsMadeTo();
                if (consentIndividualProviderDisclosureIsMadeTos.size() != 0)
                    for (ConsentIndividualProviderDisclosureIsMadeTo cip : consentIndividualProviderDisclosureIsMadeTos) {
                        consentIndividualProviders.add(cip
                                .getIndividualProvider());
                    }

                Set<ConsentIndividualProviderPermittedToDisclose> consentIndividualProviderPermittedToDiscloses = consent
                        .getProvidersPermittedToDisclose();
                if (consentIndividualProviderPermittedToDiscloses.size() != 0)
                    for (ConsentIndividualProviderPermittedToDisclose cipp : consentIndividualProviderPermittedToDiscloses) {
                        consentIndividualProviders.add(cipp
                                .getIndividualProvider());
                    }
            }
        }

        // check whether the providers in the consent provider list

        Set<IndividualProviderDto> individualProviderDtos = patientConnectionDto
                .getIndividualProviders();
        Set<OrganizationalProviderDto> organizationalProviderDtos = patientConnectionDto
                .getOrganizationalProviders();
        if (individualProviderDtos.size() != 0)
            for (IndividualProviderDto individualProviderDto : individualProviderDtos) {
                individualProviderDto.setDeletable(true);
                for (IndividualProvider individualProvider : consentIndividualProviders) {
                    if (Long.parseLong(individualProviderDto.getId()) == individualProvider
                            .getId()
                            && individualProviderDto.getNpi().equals(
                            individualProvider.getNpi()))
                        individualProviderDto.setDeletable(false);
                }
            }

        if (organizationalProviderDtos.size() != 0)
            for (OrganizationalProviderDto organizationalProviderDto : organizationalProviderDtos) {
                organizationalProviderDto.setDeletable(true);
                for (OrganizationalProvider organizationalProvider : consentOrganizationalProviders) {
                    if (Long.parseLong(organizationalProviderDto.getId()) == organizationalProvider
                            .getId()
                            && organizationalProviderDto.getNpi().equals(
                            organizationalProvider.getNpi()))
                        organizationalProviderDto.setDeletable(false);
                }
            }

        return patientConnectionDto;
    }


    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findPatientConnectionByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public Set<ProviderDto> findProvidersByUsername(String username) {
        Patient patient = patientRepository.findByUsername(username);
        return findProvidersByPatient(patient);
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findPatientConnectionByPatient
     * (gov.samhsa.consent2share.domain.patient.Patient)
     */
    @Override
    @Transactional(readOnly = true)
    public Set<ProviderDto> findProvidersByPatient(Patient patient) {
        PatientConnectionDto patientConnectionDto = modelMapper.map(patient,
                PatientConnectionDto.class);

        Set<IndividualProvider> consentIndividualProviders = new HashSet<IndividualProvider>();
        Set<OrganizationalProvider> consentOrganizationalProviders = new HashSet<OrganizationalProvider>();

        if (patient.getConsents() != null) {
            Set<Consent> consents = patient.getConsents();
            for (Consent consent : consents) {
                Set<ConsentOrganizationalProviderDisclosureIsMadeTo> consentOrganizationalProviderDisclosureIsMadeTos = consent
                        .getOrganizationalProvidersDisclosureIsMadeTo();
                if (consentOrganizationalProviderDisclosureIsMadeTos.size() != 0)
                    for (ConsentOrganizationalProviderDisclosureIsMadeTo cop : consentOrganizationalProviderDisclosureIsMadeTos) {
                        consentOrganizationalProviders.add(cop
                                .getOrganizationalProvider());
                    }

                Set<ConsentOrganizationalProviderPermittedToDisclose> consentOrganizationalProviderPermittedToDiscloses = consent
                        .getOrganizationalProvidersPermittedToDisclose();
                if (consentOrganizationalProviderPermittedToDiscloses.size() != 0)
                    for (ConsentOrganizationalProviderPermittedToDisclose copp : consentOrganizationalProviderPermittedToDiscloses) {
                        consentOrganizationalProviders.add(copp
                                .getOrganizationalProvider());
                    }

                Set<ConsentIndividualProviderDisclosureIsMadeTo> consentIndividualProviderDisclosureIsMadeTos = consent
                        .getProvidersDisclosureIsMadeTo();
                if (consentIndividualProviderDisclosureIsMadeTos.size() != 0)
                    for (ConsentIndividualProviderDisclosureIsMadeTo cip : consentIndividualProviderDisclosureIsMadeTos) {
                        consentIndividualProviders.add(cip
                                .getIndividualProvider());
                    }

                Set<ConsentIndividualProviderPermittedToDisclose> consentIndividualProviderPermittedToDiscloses = consent
                        .getProvidersPermittedToDisclose();
                if (consentIndividualProviderPermittedToDiscloses.size() != 0)
                    for (ConsentIndividualProviderPermittedToDisclose cipp : consentIndividualProviderPermittedToDiscloses) {
                        consentIndividualProviders.add(cipp
                                .getIndividualProvider());
                    }
            }
        }

        // check whether the providers in the consent provider list

        Set<IndividualProviderDto> individualProviderDtos = patientConnectionDto
                .getIndividualProviders();
        Set<OrganizationalProviderDto> organizationalProviderDtos = patientConnectionDto
                .getOrganizationalProviders();

        Set<ProviderDto> providerDtos = new HashSet<ProviderDto>();
        if (individualProviderDtos.size() != 0)
            for (IndividualProviderDto individualProviderDto : individualProviderDtos) {
                individualProviderDto.setDeletable(true);
                for (IndividualProvider individualProvider : consentIndividualProviders) {
                    if (Long.parseLong(individualProviderDto.getId()) == individualProvider
                            .getId()
                            && individualProviderDto.getNpi().equals(
                            individualProvider.getNpi()))
                        individualProviderDto.setDeletable(false);
                }
                ProviderDto providerDto = modelMapper.map(individualProviderDto, ProviderDto.class);
                providerDto.setEntityType("Individual");
                providerDtos.add(providerDto);

            }

        if (organizationalProviderDtos.size() != 0)
            for (OrganizationalProviderDto organizationalProviderDto : organizationalProviderDtos) {
                organizationalProviderDto.setDeletable(true);
                for (OrganizationalProvider organizationalProvider : consentOrganizationalProviders) {
                    if (Long.parseLong(organizationalProviderDto.getId()) == organizationalProvider
                            .getId()
                            && organizationalProviderDto.getNpi().equals(
                            organizationalProvider.getNpi()))
                        organizationalProviderDto.setDeletable(false);
                }
                ProviderDto providerDto = modelMapper.map(organizationalProviderDto, ProviderDto.class);
                providerDto.setEntityType("Organization");
                providerDtos.add(providerDto);
            }

        return providerDtos;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findPatientConnectionById(long)
     */
    @Override
    @Transactional(readOnly = true)
    public PatientConnectionDto findPatientConnectionById(long id) {
        Patient patient = patientRepository.findOne(id);
        return findPatientConnectionByPatient(patient);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.patient.PatientService#findPatientEntries
     * (int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public List<PatientProfileDto> findPatientEntries(int pageNumber,
                                                      int pageSize) {
        List<Patient> patientList = patientRepository.findAll(
                new PageRequest(pageNumber, pageSize)).getContent();

        List<PatientProfileDto> patientDtoList = new ArrayList<PatientProfileDto>();

        for (Patient patient : patientList) {
            PatientProfileDto patientProfileDto = modelMapper.map(patient,
                    PatientProfileDto.class);
            patientDtoList.add(patientProfileDto);
        }

        return patientDtoList;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.patient.PatientService#savePatient(gov
     * .samhsa.consent2share.service.dto.PatientProfileDto)
     */
    @Override
    @Transactional
    public PatientProfileDto savePatient(PatientProfileDto patientDto) {
        Patient patient = modelMapper.map(patientDto, Patient.class);
        patientRepository.save(patient);
        PatientProfileDto patientProfileDto = modelMapper.map(patient,
                PatientProfileDto.class);

        return patientProfileDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.patient.PatientService#updatePatient
     * (gov.samhsa.consent2share.service.dto.PatientProfileDto)
     */
    @Override
    @Transactional
    public void updatePatient(PatientProfileDto patientDto) {
        logger.info("{} being run...", "updatePatient");
        Patient patient = modelMapper.map(patientDto, Patient.class);

        // Save
        patientRepository.save(patient);
        try {
            emailSender.sendMessage(patientDto.getFirstName() + " "
                            + patientDto.getLastName(), patientDto.getEmail(),
                    EmailType.USER_PROFILE_CHANGE, null, null);
        } catch (MessagingException e) {
            logger.warn("Error when sending the email message.");
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findAddConsentIndividualProviderDtoByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddConsentIndividualProviderDto> findAddConsentIndividualProviderDtoByUsername(
            String username) {
        Patient patient = patientRepository.findByUsername(username);
        List<AddConsentIndividualProviderDto> individualProvidersDto = new ArrayList<AddConsentIndividualProviderDto>();
        Set<IndividualProvider> individualProviders = patient
                .getIndividualProviders();
        Iterator<IndividualProvider> individualProvidersIterator = individualProviders
                .iterator();
        while (individualProvidersIterator.hasNext()) {
            individualProvidersDto.add(modelMapper.map(
                    individualProvidersIterator.next(),
                    AddConsentIndividualProviderDto.class));
        }
        return individualProvidersDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findAddConsentOrganizationalProviderDtoByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddConsentOrganizationalProviderDto> findAddConsentOrganizationalProviderDtoByPatientId(
            long pateintId) {
        Patient patient = patientRepository.findOne(pateintId);
        List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = new ArrayList<AddConsentOrganizationalProviderDto>();
        Set<OrganizationalProvider> organizationalProviders = patient
                .getOrganizationalProviders();
        Iterator<OrganizationalProvider> organizationalProvidersIterator = organizationalProviders
                .iterator();
        while (organizationalProvidersIterator.hasNext()) {
            organizationalProvidersDto.add(modelMapper.map(
                    organizationalProvidersIterator.next(),
                    AddConsentOrganizationalProviderDto.class));
        }
        return organizationalProvidersDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findAddConsentIndividualProviderDtoByPatientId(long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddConsentIndividualProviderDto> findAddConsentIndividualProviderDtoByPatientId(
            long pateintId) {
        Patient patient = patientRepository.findOne(pateintId);
        List<AddConsentIndividualProviderDto> individualProvidersDto = new ArrayList<AddConsentIndividualProviderDto>();
        Set<IndividualProvider> individualProviders = patient
                .getIndividualProviders();
        Iterator<IndividualProvider> individualProvidersIterator = individualProviders
                .iterator();
        while (individualProvidersIterator.hasNext()) {
            individualProvidersDto.add(modelMapper.map(
                    individualProvidersIterator.next(),
                    AddConsentIndividualProviderDto.class));
        }
        return individualProvidersDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findAddConsentOrganizationalProviderDtoByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AddConsentOrganizationalProviderDto> findAddConsentOrganizationalProviderDtoByUsername(
            String username) {
        Patient patient = patientRepository.findByUsername(username);
        List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = new ArrayList<AddConsentOrganizationalProviderDto>();
        Set<OrganizationalProvider> organizationalProviders = patient
                .getOrganizationalProviders();
        Iterator<OrganizationalProvider> organizationalProvidersIterator = organizationalProviders
                .iterator();
        while (organizationalProvidersIterator.hasNext()) {
            organizationalProvidersDto.add(modelMapper.map(
                    organizationalProvidersIterator.next(),
                    AddConsentOrganizationalProviderDto.class));
        }
        return organizationalProvidersDto;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * isLegalRepForCurrentUser(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isLegalRepForCurrentUser(String username, Long legalRepId) {

        Patient patient = patientRepository.findByUsername(username);
        List<PatientLegalRepresentativeAssociation> associations = patientLegalRepresentativeAssociationRepository
                .findByPatientLegalRepresentativeAssociationPkLegalRepresentativeId(legalRepId);
        for (PatientLegalRepresentativeAssociation association : associations) {
            if (association.getPatientLegalRepresentativeAssociationPk()
                    .getPatient().getId().longValue() == patient.getId()
                    .longValue()) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.patient.PatientService#
     * findRecentPatientDtosById(java.util.List)
     */
    @Override
    @Transactional(readOnly = true)
    public List<RecentPatientDto> findRecentPatientDtosById(List<String> ids) {
        List<RecentPatientDto> patients = new ArrayList<RecentPatientDto>();
        for (String id : ids) {
            patients.add(modelMapper.map(
                    patientRepository.findOne(Long.parseLong(id)),
                    RecentPatientDto.class));
        }

        return patients;
    }

    /**
     * Map patient list to patient admin dto list.
     *
     * @param patients the patients
     * @return the list
     */
    private List<PatientAdminDto> mapPatientListToPatientAdminDtoList(
            List<Patient> patients) {
        List<PatientAdminDto> patientAdminDtoList = new ArrayList<PatientAdminDto>();
        for (Patient patient : patients) {
            PatientAdminDto patientAdminDto = modelMapper.map(patient,
                    PatientAdminDto.class);
            String fullSSN = patientAdminDto.getSocialSecurityNumber();
            if (fullSSN != null)
                patientAdminDto
                        .setSocialSecurityNumber(fullSSN.length() > LAST_N_DIGITS_OF_SSN ? fullSSN
                                .substring(fullSSN.length()
                                        - LAST_N_DIGITS_OF_SSN) : fullSSN);
            patientAdminDtoList.add(patientAdminDto);
        }
        return patientAdminDtoList;
    }

}
