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
package gov.samhsa.pcm.service.account;

import gov.samhsa.pcm.common.UniqueValueGeneratorException;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.account.EmailToken;
import gov.samhsa.pcm.domain.account.EmailTokenRepository;
import gov.samhsa.pcm.domain.account.TokenGenerator;
import gov.samhsa.pcm.domain.account.TokenType;
import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.pcm.domain.commondomainservices.EmailType;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.pcm.infrastructure.PixService;
import gov.samhsa.pcm.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.pcm.infrastructure.security.UsernameNotExistException;
import gov.samhsa.pcm.infrastructure.security.UsersAuthorityUtils;
import gov.samhsa.pcm.pixclient.util.PixManagerBean;
import gov.samhsa.pcm.service.dto.SignupDto;
import gov.samhsa.pcm.service.util.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * The Class AccountServiceImpl.
 */
public class AccountServiceImpl implements AccountService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The patient repository. */
	protected PatientRepository patientRepository;

	/** The administrative gender code repository. */
	protected AdministrativeGenderCodeRepository administrativeGenderCodeRepository;

	/** The password encoder. */
	protected PasswordEncoder passwordEncoder;

	/** The user context. */
	protected UserContext userContext;

	/** The email sender. */
	protected EmailSender emailSender;

	/** The token generator. */
	protected TokenGenerator tokenGenerator;

	/** The account verification token expire in hours. */
	protected Integer accountVerificationTokenExpireInHours;

	/** The email token repository. */
	protected EmailTokenRepository emailTokenRepository;

	/** The users repository. */
	protected UsersRepository usersRepository;

	/** The mrn service. */
	private MrnService mrnService;

	/** The pix service. */
	private PixService pixService;

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
	 * @param mrnService
	 *            the mrn service
	 * @param pixService
	 *            the pix service
	 */
	public AccountServiceImpl(
			Integer accountVerificationTokenExpireInHours,
			PatientRepository patientRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			PasswordEncoder passwordEncoder, UserContext userContext,
			EmailSender emailSender, TokenGenerator tokenGenerator,
			EmailTokenRepository emailTokenRepository,
			UsersRepository usersRepository, MrnService mrnService,
			PixService pixService) {
		this.patientRepository = patientRepository;
		this.administrativeGenderCodeRepository = administrativeGenderCodeRepository;
		this.passwordEncoder = passwordEncoder;
		this.userContext = userContext;
		this.emailSender = emailSender;
		this.emailTokenRepository = emailTokenRepository;
		this.usersRepository = usersRepository;
		this.tokenGenerator = tokenGenerator;
		this.accountVerificationTokenExpireInHours = accountVerificationTokenExpireInHours;
		this.mrnService = mrnService;
		this.pixService = pixService;
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
		String eid = null;
		final String mrn = generateMrn();

		try {
			PixManagerBean pixManagerBean = pixService.addPatient(TypeConverter
					.signupDtoToPixPatientDto(signupDto, mrn));
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

		if (!StringUtils.hasText(linkUrl)) {
			throw new IllegalArgumentException("Email link is required.");
		}

		String encodedPassword = passwordEncoder
				.encode(signupDto.getPassword());

		Patient patient = new Patient();
		patient.setMedicalRecordNumber(mrn);
		patient.setEnterpriseIdentifier(eid);
		patient.setFirstName(signupDto.getFirstName());
		patient.setLastName(signupDto.getLastName());
		patient.setUsername(signupDto.getUsername());
		patient.setEmail(signupDto.getEmail());
		patient.setBirthDay(signupDto.getBirthDate());
		if (StringUtils.hasText(signupDto.getGenderCode())) {
			patient.setAdministrativeGenderCode(administrativeGenderCodeRepository
					.findByCode(signupDto.getGenderCode()));
		}

		patientRepository.save(patient);

		Set<GrantedAuthority> authorities = UsersAuthorityUtils
				.createAuthoritySet("ROLE_USER");

		String username = signupDto.getUsername();

		Users user = new Users(username, encodedPassword, false, true, true,
				authorities);
		usersRepository.createUser(user);

		userContext.setCurrentUser(signupDto.getUsername());

		String token = createEmailToken(signupDto.getUsername(),
				signupDto.getEmail());

		emailSender.sendMessage(
				signupDto.getFirstName() + " " + signupDto.getLastName(),
				signupDto.getEmail(), EmailType.SIGNUP_VERIFICATION, linkUrl,
				token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.account.AccountService#createEmailToken
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public String createEmailToken(String username, String emailAddress)
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		String emailLinkPlaceHolder = "?token=%s";

		if (!StringUtils.hasText(username)) {
			throw new IllegalArgumentException("Username is required.");
		}

		if (!StringUtils.hasText(emailAddress)) {
			throw new IllegalArgumentException("Email Address is required.");
		}

		Patient patient = patientRepository.findByUsername(username);
		String patientEmailAddress = patient.getEmail();
		if (!patientEmailAddress.equalsIgnoreCase(emailAddress)) {
			String message = String.format(
					"Email address %s doesn't exist for username %s.",
					emailAddress, username);

			logger.info("message");
			throw new EmailAddressNotExistException(message);
		}

		EmailToken accountVerificationToken = new EmailToken();
		accountVerificationToken
				.setExpireInHours(accountVerificationTokenExpireInHours);
		accountVerificationToken.setRequestDateTime(new Date());
		String token = tokenGenerator.generateToken();
		accountVerificationToken.setToken(token);
		accountVerificationToken.setUsername(username);
		accountVerificationToken.setIsTokenUsed(false);
		accountVerificationToken.setTokenType(TokenType.ACCOUNT_VERIFICATION);
		emailTokenRepository.save(accountVerificationToken);

		String link = String.format(emailLinkPlaceHolder, token);

		return link;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.account.AccountService#findUserByUsername
	 * (java.lang.String)
	 */
	@Override
	public Users findUserByUsername(String username) {
		Users user = null;
		try {
			user = usersRepository.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			String message = String.format("Username %s is not found.",
					username);
			logger.info(message);
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.account.AccountService#recoverUsername
	 * (java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String recoverUsername(String firstName, String lastName,
			String birthDay, String email) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = dateFormat.parse(birthDay);

		Patient user = patientRepository
				.findByFirstNameAndLastNameAndBirthDayAndEmail(firstName,
						lastName, date, email);

		if (user == null) {
			return null;
		}

		return user.getUsername();
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
}
