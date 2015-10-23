package gov.samhsa.pcm.service.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.domain.account.EmailToken;
import gov.samhsa.pcm.domain.account.EmailTokenRepository;
import gov.samhsa.pcm.domain.account.TokenGenerator;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.pcm.domain.commondomainservices.EmailType;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.staff.StaffRepository;
import gov.samhsa.pcm.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.pcm.infrastructure.security.TokenNotExistException;
import gov.samhsa.pcm.infrastructure.security.UsernameNotExistException;

import java.util.Collection;

import javax.mail.MessagingException;

import gov.samhsa.pcm.service.account.PasswordResetServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.mockito.ArgumentMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordResetServiceImplTest {

	private PasswordResetServiceImpl sut;

	private PatientRepository patientRepository;
	private TokenGenerator tokenGenerator;
	private Integer passwordResetTokenExpireInHours;
	private EmailTokenRepository passwordResetTokenRepository;
	private EmailSender emailSender;
	private PasswordEncoder passwordEncoder;
	private UsersRepository usersRepository;
	private StaffRepository staffRepository;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
		@Override
		protected void starting(Description description) {
			logger.info("{} being run...", description.getMethodName());
		}
	};

	@Before
	public void setUp() {
		// Mock dependencies and create sut
		// Just to save a few lines of code for each individual test
		// But independency, clarity of the unit tests are much more important
		// than code reuse
		usersRepository = mock(UsersRepository.class);
		patientRepository = mock(PatientRepository.class);
		tokenGenerator = mock(TokenGenerator.class);
		passwordResetTokenExpireInHours = 8;
		passwordResetTokenRepository = mock(EmailTokenRepository.class);
		emailSender = mock(EmailSender.class);
		passwordEncoder = mock(PasswordEncoder.class);
		staffRepository = mock(StaffRepository.class);

		sut = new PasswordResetServiceImpl(passwordResetTokenExpireInHours,
				usersRepository, patientRepository, staffRepository,
				tokenGenerator, passwordResetTokenRepository, emailSender,
				passwordEncoder);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPasswordResetToken_Throws_Exception_When_Username_Has_Whitespaces_Only()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createPasswordResetToken("  ", "emailAddress",
				"resetPasswordLinkPlaceHolder");
	}

	@Test(expected = IllegalArgumentException.class)
	public void createPasswordResetToken_Throws_Exception_When_Username_Is_Null()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {

		sut.createPasswordResetToken(null, "emailAddress",
				"resetPasswordLinkPlaceHolder");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreatePasswordResetToken_Throws_Exception_When_Email_Has_Whitespaces_Only()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createPasswordResetToken("username", "  ",
				"resetPasswordLinkPlaceHolder");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreatePasswordResetToken_Throws_Exception_When_Email_Is_Null()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createPasswordResetToken("username", null,
				"resetPasswordLinkPlaceHolder");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreatePasswordResetToken_Throws_Exception_When_ResetPasswordLinkPlaceHolder_Has_Whitespaces_Only()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createPasswordResetToken("username", "emailAddress", " ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreatePasswordResetToken_Throws_Exception_When_ResetPasswordLinkPlaceHolder_Is_Null()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {

		sut.createPasswordResetToken("username", "emailAddress", null);
	}

	@Test
	public void testCreatePasswordResetToken_Succeeds_When_ResetPasswordLinkPlaceHolder_End_Correctly()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		// Arrange
		final String emailAddress = "emailAddress";
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getEmail()).thenReturn(emailAddress);

		final String resetPasswordLinkPlaceHolder = "http://comsent2share.com/resetPassword.html?token=%s";

		// Act
		sut.createPasswordResetToken("username", emailAddress,
				resetPasswordLinkPlaceHolder);
	}

	@Test(expected = UsernameNotExistException.class)
	public void testCreatePasswordResetToken_Throws_Exception_When_Username_Not_Exist()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		// Arrange
		when(usersRepository.loadUserByUsername(anyString())).thenThrow(
				new UsernameNotFoundException("The Message"));
		final String resetPasswordLinkPlaceHolder = "http://comsent2share.com/resetPassword.html?token=%s";
		final String emailAddress = "emailAddress";

		// Act
		sut.createPasswordResetToken("username", emailAddress,
				resetPasswordLinkPlaceHolder);
	}

	@Test(expected = EmailAddressNotExistException.class)
	public void testCreatePasswordResetToken_Throws_Exception_When_EmailAddress_Not_Exist()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		// Arrange
		final String resetPasswordLinkPlaceHolder = "http://comsent2share.com/resetPassword.html?token=%s";
		final String emailAddress = "emailAddress";
		final String anotherEmailAddress = "anotherEmailAddress";
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getEmail()).thenReturn(anotherEmailAddress);

		// Act
		sut.createPasswordResetToken("username", emailAddress,
				resetPasswordLinkPlaceHolder);
	}

	@Test
	public void testCreatePasswordResetToken_PasswordResetToken_With_Correct_ExpireInHours_Is_Saved_By_PasswordResetTokenRepository()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		// Arrange
		final String resetPasswordLinkPlaceHolder = "http://comsent2share.com/resetPassword.html?token=%s";
		final String emailAddress = "emailAddress";
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getEmail()).thenReturn(emailAddress);

		passwordResetTokenExpireInHours = 5;

		sut = new PasswordResetServiceImpl(passwordResetTokenExpireInHours,
				usersRepository, patientRepository, staffRepository,
				tokenGenerator, passwordResetTokenRepository, emailSender,
				passwordEncoder);

		// Act
		sut.createPasswordResetToken("username", emailAddress,
				resetPasswordLinkPlaceHolder);

		// Assert
		verify(passwordResetTokenRepository, times(1)).save(
				argThat(new IsPasswordResetTokenWithCorrectExpireInHours(
						passwordResetTokenExpireInHours)));
	}

	private class IsPasswordResetTokenWithCorrectExpireInHours extends
			ArgumentMatcher<EmailToken> {
		private Integer expireInHours;

		public IsPasswordResetTokenWithCorrectExpireInHours(
				Integer passwordResetTokenExpireInHours) {
			this.expireInHours = passwordResetTokenExpireInHours;
		}

		@Override
		public boolean matches(Object argument) {
			EmailToken passwordResetToken = (EmailToken) argument;
			if (passwordResetToken.getExpireInHours() == expireInHours) {
				return true;
			}

			return false;
		}
	}

	@Test
	public void testCreatePasswordResetToken_PasswordResetToken_With_Correct_Token_Is_Saved_By_PasswordResetTokenRepository()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		// Arrange
		final String resetPasswordLinkPlaceHolder = "http://comsent2share.com/resetPassword.html?token=%s";
		final String emailAddress = "emailAddress";
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getEmail()).thenReturn(emailAddress);

		final String token = "TheToken";
		when(tokenGenerator.generateToken()).thenReturn(token);

		sut = new PasswordResetServiceImpl(passwordResetTokenExpireInHours,
				usersRepository, patientRepository, staffRepository,
				tokenGenerator, passwordResetTokenRepository, emailSender,
				passwordEncoder);

		// Act
		sut.createPasswordResetToken("username", emailAddress,
				resetPasswordLinkPlaceHolder);

		// Assert
		verify(passwordResetTokenRepository, times(1)).save(
				argThat(new IsPasswordResetTokenWithCorrectToken(token)));
	}

	private class IsPasswordResetTokenWithCorrectToken extends
			ArgumentMatcher<EmailToken> {
		private String token;

		public IsPasswordResetTokenWithCorrectToken(String token) {
			this.token = token;
		}

		@Override
		public boolean matches(Object argument) {
			EmailToken passwordResetToken = (EmailToken) argument;
			if (passwordResetToken.getToken().equals(token)) {
				return true;
			}
			return false;
		}
	}

	@Test
	public void testCreatePasswordResetToken_PasswordResetToken_With_Correct_RequestDateTime_Is_Saved_By_PasswordResetTokenRepository()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		// Arrange
		final String resetPasswordLinkPlaceHolder = "http://comsent2share.com/resetPassword.html?token=%s";
		final String emailAddress = "emailAddress";
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getEmail()).thenReturn(emailAddress);

		sut = new PasswordResetServiceImpl(passwordResetTokenExpireInHours,
				usersRepository, patientRepository, staffRepository,
				tokenGenerator, passwordResetTokenRepository, emailSender,
				passwordEncoder);

		// Act
		sut.createPasswordResetToken("username", emailAddress,
				resetPasswordLinkPlaceHolder);

		// Assert
		verify(passwordResetTokenRepository, times(1)).save(
				argThat(new IsPasswordResetTokenWithCorrectRequestDateTime()));
	}

	private class IsPasswordResetTokenWithCorrectRequestDateTime extends
			ArgumentMatcher<EmailToken> {

		@Override
		public boolean matches(Object argument) {
			EmailToken passwordResetToken = (EmailToken) argument;
			if (passwordResetToken.getRequestDateTime() != null) {
				return true;
			}
			return false;
		}
	}

	@Test
	public void testCreatePasswordResetToken_Email_Is_Sent_Successfully()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		// Arrange
		final String resetPasswordLinkPlaceHolder = "http://comsent2share.com/resetPassword.html?token=%s";
		final String emailAddress = "emailAddress";
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getEmail()).thenReturn(emailAddress);

		sut = new PasswordResetServiceImpl(passwordResetTokenExpireInHours,
				usersRepository, patientRepository, staffRepository,
				tokenGenerator, passwordResetTokenRepository, emailSender,
				passwordEncoder);

		// Act
		sut.createPasswordResetToken("username", emailAddress,
				resetPasswordLinkPlaceHolder);

		// Assert
		verify(emailSender, times(1)).sendMessage(anyString(), anyString(),
				eq(EmailType.PASSWORD_RESET_REQUEST), anyString(), anyString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsPasswordResetTokenExpired_Throws_Exception_When_Token_Has_Whitespaces_Only()
			throws TokenNotExistException {

		sut.isPasswordResetTokenExpired("  ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsPasswordResetTokenExpired_Throws_Exception_When_Token_Is_Null()
			throws TokenNotExistException {
		sut.isPasswordResetTokenExpired(null);
	}

	@Test(expected = TokenNotExistException.class)
	public void testIsPasswordResetTokenExpired_Throws_Exception_When_PasswordToken_Not_Exist()
			throws TokenNotExistException {
		// Arrange
		final String token = "TheToken";
		when(passwordResetTokenRepository.findByToken(token)).thenReturn(null);

		// Act
		sut.isPasswordResetTokenExpired(token);
	}

	@Test
	public void testIsPasswordResetTokenExpired_Returns_True_Successfully()
			throws TokenNotExistException {
		// Arrange
		final String token = "TheToken";
		EmailToken passwordResetToken = mock(EmailToken.class);
		when(passwordResetToken.isTokenExpired()).thenReturn(true);
		when(passwordResetTokenRepository.findByToken(token)).thenReturn(
				passwordResetToken);

		// Act
		Boolean result = sut.isPasswordResetTokenExpired(token);

		// Assert
		assertTrue(result);
	}

	@Test
	public void testIsPasswordResetTokenExpired_Returns_False_Successfully()
			throws TokenNotExistException {
		// Arrange
		final String token = "TheToken";
		EmailToken passwordResetToken = mock(EmailToken.class);
		when(passwordResetToken.isTokenExpired()).thenReturn(false);
		when(passwordResetTokenRepository.findByToken(token)).thenReturn(
				passwordResetToken);

		// Act
		Boolean result = sut.isPasswordResetTokenExpired(token);

		// Assert
		assertFalse(result);
	}

	// @Ignore
	// @Test
	// public void
	// testResetPassword_UserDetails_With_Correct_Authorities_Is_Updated_By_UsersRepository()
	// throws TokenNotExistException, TokenExpiredException,
	// UsernameNotExistException, MessagingException {
	// // Arrange
	// final String token = "TheToken";
	// final String username = "Username";
	// final String password = "Password";
	// final String encodedPassword = "encodedPassword";
	// PasswordResetDto passwordResetDto = mock(PasswordResetDto.class);
	// when(passwordResetDto.getToken()).thenReturn(token);
	// when(passwordResetDto.getPassword()).thenReturn(password);
	// EmailToken passwordResetToken = mock(EmailToken.class);
	// when(passwordResetToken.isTokenExpired()).thenReturn(false);
	// when(passwordResetToken.getUsername()).thenReturn(username);
	// when(passwordResetTokenRepository.findByToken(token)).thenReturn(
	// passwordResetToken);
	// Users user = mock(Users.class);
	// Collection<? extends GrantedAuthority> authorities = (Collection<?
	// extends GrantedAuthority>) mock(Collection.class);
	// Iterator<? extends GrantedAuthority> authoritiesIterator =
	// mock(Iterator.class);
	// when(authoritiesIterator.hasNext()).thenReturn(false);
	// when(authorities.iterator()).thenReturn((Iterator) authoritiesIterator);
	//
	// when(user.getAuthorities()).thenReturn((Set<GrantedAuthority>)
	// authorities);
	// when(usersRepository.loadUserByUsername(username)).thenReturn(
	// user);
	//
	// when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
	//
	// // Act
	// sut.resetPassword(passwordResetDto, anyString());
	//
	// // Assert
	// verify(usersRepository, times(1)).updateUser(
	// argThat(new IsUserDetailsnWithCorrectAuthorities(authorities)));
	// }

	private class IsUserDetailsnWithCorrectAuthorities extends
			ArgumentMatcher<UserDetails> {
		private Collection<? extends GrantedAuthority> authorities;

		public IsUserDetailsnWithCorrectAuthorities(
				Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
		}

		@Override
		public boolean matches(Object argument) {
			UserDetails userDetails = (UserDetails) argument;
			if (userDetails.getAuthorities() != null) {
				return true;
			}
			return false;
		}
	}
}
