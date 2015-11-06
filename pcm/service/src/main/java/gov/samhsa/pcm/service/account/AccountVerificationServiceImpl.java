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

import gov.samhsa.pcm.domain.account.EmailToken;
import gov.samhsa.pcm.domain.account.EmailTokenRepository;
import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.pcm.domain.commondomainservices.EmailType;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.infrastructure.security.TokenExpiredException;
import gov.samhsa.pcm.infrastructure.security.TokenNotExistException;
import gov.samhsa.pcm.infrastructure.security.UsernameNotExistException;
import gov.samhsa.pcm.service.dto.AccountVerificationDto;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

/**
 * The Class AccountVerificationServiceImpl.
 */
public class AccountVerificationServiceImpl implements
		AccountVerificationService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The users repository. */
	private UsersRepository usersRepository;

	/** The patient repository. */
	private PatientRepository patientRepository;

	/** The email token repository. */
	private EmailTokenRepository emailTokenRepository;

	/** The email sender. */
	private EmailSender emailSender;

	/**
	 * Instantiates a new account verification service impl.
	 *
	 * @param usersRepository
	 *            the users repository
	 * @param patientRepository
	 *            the patient repository
	 * @param emailTokenRepository
	 *            the email token repository
	 * @param emailSender
	 *            the email sender
	 */
	public AccountVerificationServiceImpl(UsersRepository usersRepository,
			PatientRepository patientRepository,
			EmailTokenRepository emailTokenRepository, EmailSender emailSender) {
		this.usersRepository = usersRepository;
		this.patientRepository = patientRepository;
		this.emailTokenRepository = emailTokenRepository;
		this.emailSender = emailSender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.account.AccountVerificationService#
	 * isAccountVerificationTokenExpired(java.lang.String)
	 */
	@Override
	public Boolean isAccountVerificationTokenExpired(String token)
			throws TokenNotExistException {
		if (!StringUtils.hasText(token)) {
			throw new IllegalArgumentException(
					"Account verification token is required.");
		}

		EmailToken accountVerificatioinToken = findToken(token);
		Boolean isExpired = accountVerificatioinToken.isTokenExpired();

		return isExpired;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.account.AccountVerificationService#
	 * enableAccount
	 * (gov.samhsa.consent2share.service.dto.AccountVerificationDto,
	 * java.lang.String)
	 */
	@Override
	public void enableAccount(AccountVerificationDto accountVerificationDto,
			String linkUrl) throws TokenNotExistException,
			TokenExpiredException, UsernameNotExistException,
			MessagingException {
		if (accountVerificationDto == null) {
			throw new IllegalArgumentException(
					"Account verification dto is required.");
		}

		String token = accountVerificationDto.getToken();

		EmailToken emailToken = findToken(token);

		Boolean isExpired = emailToken.isTokenExpired();
		if (isExpired) {
			throw new TokenExpiredException("Email token is expired.");
		}

		emailToken.setIsTokenUsed(true);
		emailTokenRepository.save(emailToken);

		String username = emailToken.getUsername();

		Users user = null;
		try {
			user = usersRepository.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			logger.warn(String.format(e.getMessage()), e);
			throw new UsernameNotExistException(e.getMessage());
		}

		Users updatedUser = new Users(user.getFailedLoginAttempts(), username,
				user.getPassword(), true, user.isAccountNonExpired(),
				user.isCredentialsNonExpired(), user.getAuthorities());

		usersRepository.updateUser(updatedUser);

		Patient patient = patientRepository.findByUsername(username);

		emailSender.sendMessage(
				patient.getFirstName() + " " + patient.getLastName(),
				patient.getEmail(), EmailType.SIGNUP_CONFIRMATION, linkUrl,
				null);
	}

	/**
	 * Find token.
	 *
	 * @param token
	 *            the token
	 * @return the email token
	 * @throws TokenNotExistException
	 *             the token not exist exception
	 */
	private EmailToken findToken(String token) throws TokenNotExistException {
		EmailToken emailToken = emailTokenRepository.findByToken(token);

		if (emailToken == null) {
			throw new TokenNotExistException("Email token doesn't exist.");
		}

		return emailToken;
	}
}
