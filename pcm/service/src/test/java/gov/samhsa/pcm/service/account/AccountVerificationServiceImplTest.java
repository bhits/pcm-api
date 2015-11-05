package gov.samhsa.pcm.service.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.domain.account.EmailToken;
import gov.samhsa.pcm.domain.account.EmailTokenRepository;
import gov.samhsa.pcm.domain.account.TokenGenerator;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.infrastructure.security.TokenExpiredException;
import gov.samhsa.pcm.infrastructure.security.TokenNotExistException;
import gov.samhsa.pcm.infrastructure.security.UsernameNotExistException;
import gov.samhsa.pcm.service.account.AccountVerificationServiceImpl;
import gov.samhsa.pcm.service.dto.AccountVerificationDto;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;

public class AccountVerificationServiceImplTest {

	private AccountVerificationServiceImpl sut;

	private PatientRepository patientRepository;
	private TokenGenerator tokenGenerator;
	private Integer accountVerificationTokenExpireInHours;
	private EmailTokenRepository emailTokenRepository;
	private EmailSender emailSender;
	private UsersRepository usersRepository;

	@Before
	public void setUp() {
		// Mock dependencies and create sut
		// Just to save a few lines of code for each individual test
		// But independency, clarity of the unit tests are much more important
		// than code reuse
		usersRepository = mock(UsersRepository.class);
		patientRepository = mock(PatientRepository.class);
		tokenGenerator = mock(TokenGenerator.class);
		accountVerificationTokenExpireInHours = 8;
		emailTokenRepository = mock(EmailTokenRepository.class);
		emailSender = mock(EmailSender.class);

		sut = new AccountVerificationServiceImpl(usersRepository,
				patientRepository, emailTokenRepository, emailSender);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsEmailTokenExpired_Throws_Exception_When_Token_Has_Whitespaces_Only()
			throws TokenNotExistException {

		sut.isAccountVerificationTokenExpired("  ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsEmailTokenExpired_Throws_Exception_When_Token_Is_Null()
			throws TokenNotExistException {
		sut.isAccountVerificationTokenExpired(null);
	}

	@Test(expected = TokenNotExistException.class)
	public void testIsEmailTokenExpired_Throws_Exception_When_PasswordToken_Not_Exist()
			throws TokenNotExistException {
		// Arrange
		final String token = "TheToken";
		when(emailTokenRepository.findByToken(token)).thenReturn(null);

		// Act
		sut.isAccountVerificationTokenExpired(token);
	}

	@Test
	public void testIsEmailTokenExpired_Returns_True_Successfully()
			throws TokenNotExistException {
		// Arrange
		final String token = "TheToken";
		EmailToken passwordResetToken = mock(EmailToken.class);
		when(passwordResetToken.isTokenExpired()).thenReturn(true);
		when(emailTokenRepository.findByToken(token)).thenReturn(
				passwordResetToken);

		// Act
		Boolean result = sut.isAccountVerificationTokenExpired(token);

		// Assert
		assertTrue(result);
	}

	@Test
	public void testIsEmailTokenExpired_Returns_False_Successfully()
			throws TokenNotExistException {
		// Arrange
		final String token = "TheToken";
		EmailToken passwordResetToken = mock(EmailToken.class);
		when(passwordResetToken.isTokenExpired()).thenReturn(false);
		when(emailTokenRepository.findByToken(token)).thenReturn(
				passwordResetToken);

		// Act
		Boolean result = sut.isAccountVerificationTokenExpired(token);

		// Assert
		assertFalse(result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEnableAccount_When_Account_Verification_Dto_Is_Null()
			throws TokenNotExistException, TokenExpiredException,
			UsernameNotExistException, MessagingException {
		sut.enableAccount(null, "linkUrl");
	}

	@Test(expected = TokenNotExistException.class)
	public void testEnableAccount_Expired_Token()
			throws TokenNotExistException, TokenExpiredException,
			UsernameNotExistException, MessagingException {
		AccountVerificationDto accountVerificationDto = mock(AccountVerificationDto.class);
		sut.enableAccount(accountVerificationDto, "linkUrl");
	}

}
