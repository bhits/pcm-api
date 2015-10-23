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
package gov.samhsa.pcm.service.patient;

import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.pcm.domain.commondomainservices.EmailType;
import gov.samhsa.pcm.domain.consent.Consent;
import gov.samhsa.pcm.domain.consent.ConsentIndividualProviderDisclosureIsMadeTo;
import gov.samhsa.pcm.domain.consent.ConsentIndividualProviderPermittedToDisclose;
import gov.samhsa.pcm.domain.consent.ConsentOrganizationalProviderDisclosureIsMadeTo;
import gov.samhsa.pcm.domain.consent.ConsentOrganizationalProviderPermittedToDisclose;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientLegalRepresentativeAssociation;
import gov.samhsa.pcm.domain.patient.PatientLegalRepresentativeAssociationRepository;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.provider.IndividualProvider;
import gov.samhsa.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.pcm.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.pcm.infrastructure.PixService;
import gov.samhsa.pcm.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.pcm.pixclient.util.PixManagerBean;
import gov.samhsa.pcm.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.pcm.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.pcm.service.dto.IndividualProviderDto;
import gov.samhsa.pcm.service.dto.OrganizationalProviderDto;
import gov.samhsa.pcm.service.dto.PatientAdminDto;
import gov.samhsa.pcm.service.dto.PatientConnectionDto;
import gov.samhsa.pcm.service.dto.PatientProfileDto;
import gov.samhsa.pcm.service.dto.ProviderDto;
import gov.samhsa.pcm.service.dto.RecentPatientDto;
import gov.samhsa.pcm.service.util.TypeConverter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * The Class PatientServiceImpl.
 */
@Transactional
public class PatientServiceImpl implements PatientService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Constant LAST_N_DIGITS_OF_SSN. */
	public static final Integer LAST_N_DIGITS_OF_SSN = 4;

	/** The patient repository. */
	protected PatientRepository patientRepository;

	/** The patient legal representative association repository. */
	protected PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

	/** The model mapper. */
	protected ModelMapper modelMapper;

	/** The user context. */
	protected UserContext userContext;

	/** The patient profile dto to patient mapper. */
	protected DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper;

	/** The users repository. */
	protected UsersRepository usersRepository;

	/** The password encoder. */
	protected PasswordEncoder passwordEncoder;

	/** The email sender. */
	protected EmailSender emailSender;

	/** The pix service. */
	protected PixService pixService;

	/**
	 * Instantiates a new patient service impl.
	 *
	 * @param patientRepository
	 *            the patient repository
	 * @param patientLegalRepresentativeAssociationRepository
	 *            the patient legal representative association repository
	 * @param modelMapper
	 *            the model mapper
	 * @param userContext
	 *            the user context
	 * @param patientProfileDtoToPatientMapper
	 *            the patient profile dto to patient mapper
	 * @param usersRepository
	 *            the users repository
	 * @param passwordEncoder
	 *            the password encoder
	 * @param emailSender
	 *            the email sender
	 * @param pixService
	 *            the pix service
	 */
	public PatientServiceImpl(
			PatientRepository patientRepository,
			PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository,
			ModelMapper modelMapper,
			UserContext userContext,
			DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper,
			UsersRepository usersRepository, PasswordEncoder passwordEncoder,
			EmailSender emailSender, PixService pixService) {
		super();
		this.patientRepository = patientRepository;
		this.patientLegalRepresentativeAssociationRepository = patientLegalRepresentativeAssociationRepository;
		this.modelMapper = modelMapper;
		this.userContext = userContext;
		this.patientProfileDtoToPatientMapper = patientProfileDtoToPatientMapper;
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailSender = emailSender;
		this.pixService = pixService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.patient.PatientService#countAllPatients
	 * ()
	 */
	@Override
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
			   ProviderDto providerDto =  modelMapper.map(individualProviderDto, ProviderDto.class);
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
				 ProviderDto providerDto =  modelMapper.map(organizationalProviderDto, ProviderDto.class);
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
		Patient patient = patientProfileDtoToPatientMapper.map(patientDto);
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
	public void updatePatient(PatientProfileDto patientDto)
			throws AuthenticationFailedException {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = patientDto.getUsername();

		if (!currentUser.getUsername().equals(username))
			throw new AuthenticationFailedException(
					"Username does not match current active user.");
		Users user = usersRepository.loadUserByUsername(username);
		if (user != null)
			if (!passwordEncoder.matches(patientDto.getPassword(),
					user.getPassword()))
				throw new AuthenticationFailedException(
						"Password is incorrect.");
		logger.info("{} being run...", "updatePatient");
		Patient patient = patientProfileDtoToPatientMapper.map(patientDto);

		// Find MRN
		final String mrn = patient.getMedicalRecordNumber();
		Assert.hasText(mrn, "MRN cannot be retrieved from database!");

		// Update patient profile on MPI
		PixManagerBean pixManagerBean = pixService.updatePatient(TypeConverter
				.patientProfileDtoToPixPatientDto(patientDto, mrn));
		Assert.isTrue(
				pixManagerBean.isSuccess(),
				"Patient cannot be updated on MPI! Error: "
						+ pixManagerBean.getUpdateMessage());

		// Get EID
		final String eid = pixService.getEid(mrn);
		Assert.hasText(eid, "EID cannot be retrieved from MPI!");
		patient.setEnterpriseIdentifier(eid);

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
	 * @see
	 * gov.samhsa.consent2share.service.patient.PatientService#updateEid(java
	 * .lang.String)
	 */
	@Override
	@Transactional
	public void updateEid(String username) {
		Patient patient = patientRepository.findByUsername(username);
		updateEid(patient);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.patient.PatientService#updateEid(long)
	 */
	@Override
	@Transactional
	public void updateEid(long id) {
		Patient patient = patientRepository.findOne(id);
		updateEid(patient);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.PatientService#
	 * findAddConsentIndividualProviderDtoByUsername(java.lang.String)
	 */
	@Override
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
	public boolean isLegalRepForCurrentUser(Long legalRepId) {

		String username = userContext.getCurrentUser().getUsername();
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
	 * @param patients
	 *            the patients
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

	/**
	 * Update eid.
	 *
	 * @param patient
	 *            the patient
	 */
	private void updateEid(Patient patient) {
		String mrn = patient.getMedicalRecordNumber();
		Assert.hasText(mrn, "MRN cannot be null!");
		String eid = null;
		try {
			eid = pixService.getEid(mrn);
		} catch (Exception e) {
			logger.error(
					"Error retrieving EID from MPI. Cause: " + e.getMessage(),
					e);
		}
		if (StringUtils.hasText(eid)) {
			patient.setEnterpriseIdentifier(eid);
		}
		patientRepository.save(patient);
	}
}
