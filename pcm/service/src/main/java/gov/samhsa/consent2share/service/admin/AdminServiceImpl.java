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
package gov.samhsa.consent2share.service.admin;

import gov.samhsa.consent2share.common.AuthenticatedUser;
import gov.samhsa.consent2share.common.UniqueValueGeneratorException;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.domain.account.EmailToken;
import gov.samhsa.consent2share.domain.account.EmailTokenRepository;
import gov.samhsa.consent2share.domain.account.TokenGenerator;
import gov.samhsa.consent2share.domain.account.TokenType;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.domain.commondomainservices.EmailSender;
import gov.samhsa.consent2share.domain.commondomainservices.EmailType;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCode;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.consent2share.domain.staff.Staff;
import gov.samhsa.consent2share.domain.staff.StaffRepository;
import gov.samhsa.consent2share.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.consent2share.infrastructure.PixService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.consent2share.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.service.account.MrnService;
import gov.samhsa.consent2share.service.dto.AdminCreatePatientResponseDto;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;
import gov.samhsa.consent2share.service.dto.BasicPatientAccountDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.util.TypeConverter;

import java.util.Date;
import java.util.UUID;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * The Class AdminServiceImpl.
 */
@Transactional
public class AdminServiceImpl implements AdminService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The account verification token expire in hours. */
	protected Integer accountVerificationTokenExpireInHours;

	/** The Administrator repository. */
	protected StaffRepository administratorRepository;

	/** The patient repository. */
	protected PatientRepository patientRepository;

	/** The patient profile dto to patient mapper. */
	protected DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper;

	/** The admin profile dto to administrator mapper. */
	protected AdminProfileDtoToAdministratorMapper adminProfileDtoToAdministratorMapper;

	/** The model mapper. */
	protected ModelMapper modelMapper;

	/** The user context. */
	protected UserContext userContext;

	/** The password encoder. */
	protected PasswordEncoder passwordEncoder;

	/** The email sender. */
	protected EmailSender emailSender;

	/** The token generator. */
	protected TokenGenerator tokenGenerator;

	/** The email token repository. */
	protected EmailTokenRepository emailTokenRepository;

	/** The users repository. */
	protected UsersRepository usersRepository;

	/** The administrative gender code repository. */
	protected AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	/** The mrn service. */
	private MrnService mrnService;

	/** The pix service. */
	private PixService pixService;

	/**
	 * Instantiates a new admin service impl.
	 *
	 * @param accountVerificationTokenExpireInHours
	 *            the account verification token expire in hours
	 * @param administratorRepository
	 *            the administrator repository
	 * @param patientRepository
	 *            the patient repository
	 * @param patientProfileDtoToPatientMapper
	 *            the patient profile dto to patient mapper
	 * @param adminProfileDtoToAdministratorMapper
	 *            the admin profile dto to administrator mapper
	 * @param modelMapper
	 *            the model mapper
	 * @param userContext
	 *            the user context
	 * @param passwordEncoder
	 *            the password encoder
	 * @param emailSender
	 *            the email sender
	 * @param tokenGenerator
	 *            the token generator
	 * @param emailTokenRepository
	 *            the email token repository
	 * @param usersRepository
	 *            the users repository
	 * @param administrativeGenderCodeRepository
	 *            the administrative gender code repository
	 * @param mrnService
	 *            the mrn service
	 * @param pixService
	 *            the pix query service
	 */
	public AdminServiceImpl(
			Integer accountVerificationTokenExpireInHours,
			StaffRepository administratorRepository,
			PatientRepository patientRepository,
			DtoToDomainEntityMapper<PatientProfileDto, Patient> patientProfileDtoToPatientMapper,
			AdminProfileDtoToAdministratorMapper adminProfileDtoToAdministratorMapper,
			ModelMapper modelMapper,
			UserContext userContext,
			PasswordEncoder passwordEncoder,
			EmailSender emailSender,
			TokenGenerator tokenGenerator,
			EmailTokenRepository emailTokenRepository,
			UsersRepository usersRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			MrnService mrnService, PixService pixService) {
		super();
		this.accountVerificationTokenExpireInHours = accountVerificationTokenExpireInHours;
		this.administratorRepository = administratorRepository;
		this.patientRepository = patientRepository;
		this.patientProfileDtoToPatientMapper = patientProfileDtoToPatientMapper;
		this.adminProfileDtoToAdministratorMapper = adminProfileDtoToAdministratorMapper;
		this.modelMapper = modelMapper;
		this.userContext = userContext;
		this.passwordEncoder = passwordEncoder;
		this.emailSender = emailSender;
		this.tokenGenerator = tokenGenerator;
		this.emailTokenRepository = emailTokenRepository;
		this.usersRepository = usersRepository;
		this.administrativeGenderCodeRepository = administrativeGenderCodeRepository;
		this.mrnService = mrnService;
		this.pixService = pixService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.patient.PatientService#
	 * findPatientProfileByUsername(java.lang.String)
	 */
	@Override
	public AdminProfileDto findAdminProfileByUsername(String username) {
		Staff admin = administratorRepository.findByUsername(username);
		AdminProfileDto adminProfileDto = modelMapper.map(admin,
				AdminProfileDto.class);

		return adminProfileDto;
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
	public void updateAdministrator(AdminProfileDto adminProfileDto)
			throws AuthenticationFailedException {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = adminProfileDto.getUsername();
		if (!currentUser.getUsername().equals(username))
			throw new AuthenticationFailedException(
					"Username does not match current active user.");
		Users user = usersRepository.loadUserByUsername(username);
		if (user != null)
			if (!passwordEncoder.matches(adminProfileDto.getPassword(),
					user.getPassword()))
				throw new AuthenticationFailedException(
						"Password is incorrect.");
		logger.info("{} being run...", "updatePatient");
		Staff admin = adminProfileDtoToAdministratorMapper.map(adminProfileDto);
		administratorRepository.save(admin);
		try {
			emailSender.sendMessage(adminProfileDto.getFirstName() + " "
					+ adminProfileDto.getLastName(),
					adminProfileDto.getEmail(), EmailType.USER_PROFILE_CHANGE,
					null, null);
		} catch (MessagingException e) {
			logger.warn("Error when sending the email message.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.admin.AdminService#updatePatient(gov
	 * .samhsa.consent2share.service.dto.PatientProfileDto)
	 */
	@Override
	@Transactional
	public void updatePatient(PatientProfileDto patientProfileDto) {
		patientProfileDto.setAddressCountryCode("US");

		// Find MRN
		Patient patient = patientRepository.findOne(patientProfileDto.getId());
		final String mrn = patient.getMedicalRecordNumber();

		patientProfileDto.setUsername(patient.getUsername());

		// Update patient profile on MPI
		PixManagerBean pixManagerBean = pixService.updatePatient(TypeConverter
				.patientProfileDtoToPixPatientDto(patientProfileDto, mrn));
		Assert.isTrue(
				pixManagerBean.isSuccess(),
				"Patient cannot be updated on MPI! Error: "
						+ pixManagerBean.getUpdateMessage());

		// Get EID
		final String eid = pixService.getEid(mrn);
		Assert.hasText(eid, "EID cannot be retrieved from MPI!");
		patient.setEnterpriseIdentifier(eid);

		// Save
		patient = patientProfileDtoToPatientMapper.map(patientProfileDto);
		patientRepository.save(patient);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.admin.AdminService#createPatientAccount
	 * (gov.samhsa.consent2share.service.dto.BasicPatientAccountDto)
	 */
	@Override
	public AdminCreatePatientResponseDto createPatientAccount(
			BasicPatientAccountDto basicPatientAccountDto) {
		String eid = null;
		final String mrn = generateMrn();

		try {
			PixManagerBean pixManagerBean = pixService.addPatient(TypeConverter
					.basicPatientAccountDtoToPixPatientDto(
							basicPatientAccountDto, mrn));
			Assert.isTrue(
					pixManagerBean.isSuccess(),
					"Patient cannot be added to MPI! Error: "
							+ pixManagerBean.getAddMessage());
			eid = pixService.getEid(mrn);
			Assert.hasText(eid, "EID cannot be retrieved from MPI!");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		Patient patient = mapBasicPatientAccountDtoToPatient(mrn,
				basicPatientAccountDto);
		patient.setEnterpriseIdentifier(eid);
		AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository
				.findByCode(basicPatientAccountDto
						.getAdministrativeGenderCode());
		patient.setAdministrativeGenderCode(administrativeGenderCode);
		patient.setVerificationCode(UUID.randomUUID().toString()
				.substring(0, 7));

		patientRepository.save(patient);

		return new AdminCreatePatientResponseDto(
				AdminCreatePatientResponseDto.PATIENT_STATUS_UNKNOWN,
				patient.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.admin.AdminService#sendLoginInformationEmail
	 * (long, java.lang.String)
	 */
	@Override
	public Boolean sendLoginInformationEmail(long patientId, String linkUrl)
			throws EmailAddressNotExistException, MessagingException {

		Patient patient = patientRepository.findOne(patientId);
		// create emailToken
		if (patient.getEmail() == null) {
			String message = String
					.format("Email address %s doesn't exist for username %s.");

			logger.info("message");
			throw new EmailAddressNotExistException(message);
		}

		EmailToken accountLoginInfoToken = new EmailToken();
		accountLoginInfoToken
				.setExpireInHours(accountVerificationTokenExpireInHours);
		accountLoginInfoToken.setRequestDateTime(new Date());
		String token = tokenGenerator.generateToken();
		accountLoginInfoToken.setToken(token);
		accountLoginInfoToken.setIsTokenUsed(false);
		accountLoginInfoToken.setPatientId(patientId);
		accountLoginInfoToken.setTokenType(TokenType.NEW_LOGIN_ACCOUNT);
		emailTokenRepository.save(accountLoginInfoToken);

		emailSender
				.sendMessage(
						patient.getFirstName() + " " + patient.getLastName(),
						patient.getEmail(), EmailType.NEW_LOGIN_ACCOUNT,
						linkUrl, token);
		return true;
	}

	/**
	 * Generate mrn.
	 *
	 * @return the string
	 */
	protected String generateMrn() {
		String mrn = null;
		StringBuilder errorBuilder = new StringBuilder();
		try {
			mrn = mrnService.generateMrn();
		} catch (UniqueValueGeneratorException e) {
			errorBuilder.append(". ");
			errorBuilder.append(e.getMessage());
			logger.error(e.getMessage(), e);
		} finally {
			Assert.hasText(mrn,
					errorBuilder.insert(0, "MRN cannot be generated by PCM!")
							.toString());
		}
		return mrn;
	}

	protected Patient mapBasicPatientAccountDtoToPatient(final String mrn,
			BasicPatientAccountDto basicPatientAccountDto) {
		Patient patient = new Patient();
		patient.setMedicalRecordNumber(mrn);
		patient.setBirthDay(basicPatientAccountDto.getBirthDate());
		patient.setFirstName(basicPatientAccountDto.getFirstName());
		patient.setLastName(basicPatientAccountDto.getLastName());
		patient.setEmail(basicPatientAccountDto.getEmail());
		return patient;
	}
}
