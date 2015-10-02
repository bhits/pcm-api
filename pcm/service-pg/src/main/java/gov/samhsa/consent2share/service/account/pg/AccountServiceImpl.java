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
package gov.samhsa.consent2share.service.account.pg;

import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.domain.account.EmailTokenRepository;
import gov.samhsa.consent2share.domain.account.TokenGenerator;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.domain.commondomainservices.EmailSender;
import gov.samhsa.consent2share.domain.commondomainservices.EmailType;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.consent2share.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.consent2share.infrastructure.PixService;
import gov.samhsa.consent2share.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.consent2share.infrastructure.security.UsernameNotExistException;
import gov.samhsa.consent2share.infrastructure.security.UsersAuthorityUtils;
import gov.samhsa.consent2share.service.account.MrnService;
import gov.samhsa.consent2share.service.dto.SignupDto;
import gov.samhsa.consent2share.service.spirit.SpiritClientNotAvailableException;
import gov.samhsa.consent2share.service.spirit.SpiritQueryService;
import gov.samhsa.consent2share.service.util.TypeConverter;
import gov.samhsa.spirit.wsclient.dto.PatientDto;

import java.util.Set;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * The Class AccountServiceImpl.
 */
public class AccountServiceImpl extends
		gov.samhsa.consent2share.service.account.AccountServiceImpl {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The spirit query service. */
	private SpiritQueryService spiritQueryService;

	/** The patient dto to patient mapper. */
	private DtoToDomainEntityMapper<PatientDto, Patient> patientDtoToPatientMapper;

	/**
	 * Instantiates a new account service impl.
	 *
	 * @param accountVerificationTokenExpireInHours
	 *            the account verification token expire in hours
	 * @param patientRepository
	 *            the patient repository
	 * @param administrativeGenderCodeRepository
	 *            the administrative gender code repository
	 * @param passwordEncoder
	 *            the password encoder
	 * @param userContext
	 *            the user context
	 * @param emailSender
	 *            the email sender
	 * @param tokenGenerator
	 *            the token generator
	 * @param emailTokenRepository
	 *            the email token repository
	 * @param usersRepository
	 *            the users repository
	 * @param spiritQueryService
	 *            the spirit query service
	 * @param patientDtoToPatientMapper
	 *            the patient dto to patient mapper
	 * @param mrnService
	 *            the mrn service
	 * @param pixService
	 *            the pix service
	 */
	public AccountServiceImpl(
			Integer accountVerificationTokenExpireInHours,
			PatientRepository patientRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			PasswordEncoder passwordEncoder,
			UserContext userContext,
			EmailSender emailSender,
			TokenGenerator tokenGenerator,
			EmailTokenRepository emailTokenRepository,
			UsersRepository usersRepository,
			SpiritQueryService spiritQueryService,
			DtoToDomainEntityMapper<PatientDto, Patient> patientDtoToPatientMapper,
			MrnService mrnService, PixService pixService) {
		super(accountVerificationTokenExpireInHours, patientRepository,
				administrativeGenderCodeRepository, passwordEncoder,
				userContext, emailSender, tokenGenerator, emailTokenRepository,
				usersRepository, mrnService, pixService);
		this.spiritQueryService = spiritQueryService;
		this.patientDtoToPatientMapper = patientDtoToPatientMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.account.AccountService#signup(gov.samhsa
	 * .consent2share.service.dto.SignupDto, java.lang.String)
	 */
	@Override
	@Transactional
	public void signup(SignupDto signupDto, String linkUrl)
			throws MessagingException, UsernameNotExistException,
			EmailAddressNotExistException {
		
		final String mrn = generateMrn();
		
		if (!StringUtils.hasText(linkUrl)) {
			throw new IllegalArgumentException("Email link is required.");
		}

		String encodedPassword = passwordEncoder
				.encode(signupDto.getPassword());
		
		Patient patient = new Patient();
		patient.setMedicalRecordNumber(mrn);
		setPatientFieldsWithSignupDto(signupDto, patient);

		patientRepository.save(patient);
		
		// patient.setEnterpriseIdentifier(spiritQueryService.getHIEPatientIdbyPDQ(signupDto));
		PatientDto patientDto = spiritQueryService.addPatient(TypeConverter
				.signupDtoToPixPatientDto(signupDto, null));
		if (patientDto == null)
			throw new SpiritClientNotAvailableException(
					"Error when creating patient by PDQ. Spirit service not available.");

		// Check if the same patient is already in c2s system
		if (isDuplicatPatientByEid(patientDto.getPatientId()))
			throw new PatientExistingException("Patient already exists.");

		// if(patientDto.isNewInExchange())
		// {
		// Patient patient=patientDtoToPatientMapper.map(patientDto);
		// patientRepository.save(createPatientWithSignupDto(signupDto));
		// }
		// else{
		// Patient patient=patientDtoToPatientMapper.map(patientDto);
		// setPatientFieldsWithSignupDto(signupDto,patient);
		// patient = patientRepository.save(patient);
		// }

		// Create User
		Set<GrantedAuthority> authorities = UsersAuthorityUtils
				.createAuthoritySet("ROLE_USER");
		String username = signupDto.getUsername();
		Users user = new Users(username, encodedPassword, false, true, true,
				authorities);
		usersRepository.createUser(user);
		userContext.setCurrentUser(signupDto.getUsername());

		// Send out email to notify user
		String token = createEmailToken(signupDto.getUsername(),
				signupDto.getEmail());
		emailSender.sendMessage(
				signupDto.getFirstName() + " " + signupDto.getLastName(),
				signupDto.getEmail(), EmailType.SIGNUP_VERIFICATION, linkUrl,
				token);
	}

	/**
	 * Creates the patient with signup dto.
	 *
	 * @param signupDto
	 *            the signup dto
	 * @return the patient
	 */
	private Patient createPatientWithSignupDto(SignupDto signupDto) {
		Patient patient = new Patient();
		return setPatientFieldsWithSignupDto(signupDto, patient);
	}

	/**
	 * Sets the patient fields with signup dto.
	 *
	 * @param signupDto
	 *            the signup dto
	 * @param patient
	 *            the patient
	 * @return the patient
	 */
	private Patient setPatientFieldsWithSignupDto(SignupDto signupDto,
			Patient patient) {
		patient.setFirstName(signupDto.getFirstName());
		patient.setLastName(signupDto.getLastName());
		patient.setUsername(signupDto.getUsername());
		patient.setEmail(signupDto.getEmail());
		patient.setSocialSecurityNumber(signupDto.getSocialSecurityNumber());
		patient.setBirthDay(signupDto.getBirthDate());
		if (StringUtils.hasText(signupDto.getGenderCode())) {
			patient.setAdministrativeGenderCode(administrativeGenderCodeRepository
					.findByCode(signupDto.getGenderCode()));
		}
		return patient;
	}

	/**
	 * Checks if is duplicat patient.
	 *
	 * @param patient
	 *            the patient
	 * @return true, if is duplicat patient
	 */
	private boolean isDuplicatPatient(Patient patient) {
		Patient dupPatient = patientRepository
				.findByFirstNameAndLastNameAndBirthDayAndSocialSecurityNumberAndAdministrativeGenderCode(
						patient.getFirstName(), patient.getLastName(),
						patient.getBirthDay(),
						patient.getSocialSecurityNumber(),
						patient.getAdministrativeGenderCode());
		if (dupPatient == null)
			return false;
		else
			return true;

	}

	/**
	 * Checks if is duplicat patient by eid.
	 *
	 * @param enterpriseIdentifier
	 *            the enterprise identifier
	 * @return true, if is duplicat patient by eid
	 */
	private boolean isDuplicatPatientByEid(String enterpriseIdentifier) {
		Patient dupPatient = patientRepository
				.findByEnterpriseIdentifier(enterpriseIdentifier);
		if (dupPatient == null)
			return false;
		else
			return true;

	}
}
