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
package gov.samhsa.consent2share.service.account;

import gov.samhsa.consent2share.domain.account.EmailToken;
import gov.samhsa.consent2share.domain.account.EmailTokenRepository;
import gov.samhsa.consent2share.domain.account.TokenGenerator;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.domain.commondomainservices.EmailSender;
import gov.samhsa.consent2share.domain.commondomainservices.EmailType;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.infrastructure.security.TokenExpiredException;
import gov.samhsa.consent2share.infrastructure.security.TokenNotExistException;
import gov.samhsa.consent2share.infrastructure.security.UsersAuthorityUtils;
import gov.samhsa.consent2share.service.dto.SignupLinkToPatientDto;

import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * The Class AccountLinkToPatientServiceImpl.
 */
public class AccountLinkToPatientServiceImpl implements
		AccountLinkToPatientService {

	/** The password reset token expire in hours. */
	private Integer passwordResetTokenExpireInHours;

	/** The users repository. */
	private UsersRepository usersRepository;

	/** The patient repository. */
	private PatientRepository patientRepository;

	/** The token generator. */
	private TokenGenerator tokenGenerator;

	/** The password reset token repository. */
	private EmailTokenRepository emailTokenRepository;

	/** The email sender. */
	private EmailSender emailSender;

	/** The password encoder. */
	private PasswordEncoder passwordEncoder;

	/**
	 * Instantiates a new account link to patient service impl.
	 *
	 * @param passwordResetTokenExpireInHours
	 *            the password reset token expire in hours
	 * @param usersRepository
	 *            the users repository
	 * @param patientRepository
	 *            the patient repository
	 * @param tokenGenerator
	 *            the token generator
	 * @param emailTokenRepository
	 *            the email token repository
	 * @param emailSender
	 *            the email sender
	 * @param passwordEncoder
	 *            the password encoder
	 */
	public AccountLinkToPatientServiceImpl(
			Integer passwordResetTokenExpireInHours,
			UsersRepository usersRepository,
			PatientRepository patientRepository, TokenGenerator tokenGenerator,
			EmailTokenRepository emailTokenRepository, EmailSender emailSender,
			PasswordEncoder passwordEncoder) {
		super();
		this.passwordResetTokenExpireInHours = passwordResetTokenExpireInHours;
		this.usersRepository = usersRepository;
		this.patientRepository = patientRepository;
		this.tokenGenerator = tokenGenerator;
		this.emailTokenRepository = emailTokenRepository;
		this.emailSender = emailSender;
		this.passwordEncoder = passwordEncoder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.account.PasswordResetService#
	 * isPasswordResetTokenExpired(java.lang.String)
	 */
	@Override
	public Boolean isTokenExpired(String token) throws TokenNotExistException {
		if (!StringUtils.hasText(token)) {
			throw new IllegalArgumentException("Token is required.");
		}

		EmailToken setupNewAccountToken = findNewAccountToken(token);
		Boolean isExpired = setupNewAccountToken.isTokenExpired();

		return isExpired;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.account.AccountLinkToPatientService#
	 * isTokenUsed(java.lang.String)
	 */
	@Override
	public Boolean isTokenUsed(String token) throws TokenNotExistException {
		if (!StringUtils.hasText(token)) {
			throw new IllegalArgumentException("Token is required.");
		}

		EmailToken setupNewAccountToken = findNewAccountToken(token);
		Boolean isUsed = setupNewAccountToken.getIsTokenUsed();

		return isUsed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.account.AccountLinkToPatientService#
	 * setupAccount(gov.samhsa.consent2share.service.dto.SignupLinkToPatientDto,
	 * java.lang.String)
	 */
	@Override
	public void setupAccount(SignupLinkToPatientDto signupLinkToPatientDto,
			String linkUrl) throws MessagingException, TokenExpiredException {
		if (signupLinkToPatientDto == null) {
			throw new IllegalArgumentException("Sign up Dto is required.");
		}

		String token = signupLinkToPatientDto.getToken();

		EmailToken signupLinkToPatientToken = emailTokenRepository
				.findByToken(token);

		Boolean isExpired = signupLinkToPatientToken.isTokenExpired();
		if (isExpired) {
			throw new TokenExpiredException("sign up token is expired.");
		}
		signupLinkToPatientToken.setIsTokenUsed(true);
		signupLinkToPatientToken.setUsername(signupLinkToPatientDto
				.getUsername());
		long patientId = signupLinkToPatientToken.getPatientId();

		String encodedPassword = passwordEncoder.encode(signupLinkToPatientDto
				.getPassword());

		String username = signupLinkToPatientDto.getUsername();

		// update patient profile
		Patient patient = patientRepository.findOne(patientId);

		if (patient.getUsername() == null
				&& signupLinkToPatientDto.getBirthDate().equals(
						patient.getBirthDay())
				&& signupLinkToPatientDto.getVerificationCode().equals(
						patient.getVerificationCode())) {
			patient.setUsername(signupLinkToPatientDto.getUsername());

			// create user account
			Set<GrantedAuthority> authorities = UsersAuthorityUtils
					.createAuthoritySet("ROLE_USER");

			Users user = new Users(username, encodedPassword, true, true, true,
					authorities);
			emailTokenRepository.save(signupLinkToPatientToken);
			usersRepository.createUser(user);
			patientRepository.save(patient);
			// send out confirmation email
			emailSender.sendMessage(
					patient.getFirstName() + " " + patient.getLastName(),
					patient.getEmail(), EmailType.SIGNUP_CONFIRMATION, linkUrl,
					null);
		} else {
			throw new MessagingException("Invalid verification code");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.account.AccountLinkToPatientService#
	 * checkUpAccount
	 * (gov.samhsa.consent2share.service.dto.SignupLinkToPatientDto)
	 */
	@Override
	public Boolean checkUpAccount(SignupLinkToPatientDto signupLinkToPatientDto) {
		String token = signupLinkToPatientDto.getToken();

		EmailToken signupLinkToPatientToken = emailTokenRepository
				.findByToken(token);
		long patientId = signupLinkToPatientToken.getPatientId();
		Patient patient = patientRepository.findOne(patientId);
		if (patient.getUsername() == null
				&& signupLinkToPatientDto.getBirthDate().equals(
						patient.getBirthDay())
				&& signupLinkToPatientDto.getVerificationCode().equals(
						patient.getVerificationCode())) {
			return true;
		} else
			return false;
	}

	/**
	 * Find new account token.
	 *
	 * @param token
	 *            the token
	 * @return the email token
	 * @throws TokenNotExistException
	 *             the token not exist exception
	 */
	private EmailToken findNewAccountToken(String token)
			throws TokenNotExistException {
		EmailToken newAccountSetupToken = emailTokenRepository
				.findByToken(token);

		if (newAccountSetupToken == null) {
			throw new TokenNotExistException(
					"New Account Setup token doesn't exist.");
		}

		return newAccountSetupToken;
	}
}
