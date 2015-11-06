package gov.samhsa.pcm.service.account.pg;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.account.EmailTokenRepository;
import gov.samhsa.pcm.domain.account.TokenGenerator;
import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.domain.account.UsersRepository;
import gov.samhsa.pcm.domain.commondomainservices.EmailSender;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.pcm.hl7.Hl7v3TransformerException;
import gov.samhsa.pcm.infrastructure.PixService;
import gov.samhsa.pcm.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.pcm.infrastructure.security.UsernameNotExistException;
import gov.samhsa.pcm.service.account.MrnService;
import gov.samhsa.pcm.service.dto.SignupDto;
import gov.samhsa.pcm.service.spirit.SpiritClientNotAvailableException;
import gov.samhsa.pcm.service.spirit.SpiritQueryService;

import java.text.ParseException;
import java.util.Date;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountServiceImplTest {

	private AccountServiceImpl sut;

	private PatientRepository patientRepository;
	private TokenGenerator tokenGenerator;
	private Integer accountVerificationTokenExpireInHours;
	private EmailTokenRepository emailTokenRepository;
	private EmailSender emailSender;
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;
	private PasswordEncoder passwordEncoder;
	private UserContext userContext;
	private UsersRepository usersRepository;
	private Patient patient;
	private MrnService mrnService;
	private PixService pixService;

	@Mock
	private SpiritQueryService spiritQueryService;

	@Before
	public void setUp() {
		// Mock dependencies and create sut
		// Just to save a few lines of code for each individual test
		// But independency, clarity of the unit tests are much more important
		// than code reuse
		patientRepository = mock(PatientRepository.class);
		administrativeGenderCodeRepository = mock(AdministrativeGenderCodeRepository.class);
		tokenGenerator = mock(TokenGenerator.class);
		accountVerificationTokenExpireInHours = 8;
		emailTokenRepository = mock(EmailTokenRepository.class);
		emailSender = mock(EmailSender.class);
		userContext = mock(UserContext.class);
		passwordEncoder = mock(PasswordEncoder.class);
		usersRepository = mock(UsersRepository.class);
		patient = mock(Patient.class);
		mrnService = mock(MrnService.class);
		pixService = mock(PixService.class);

		sut = new AccountServiceImpl(accountVerificationTokenExpireInHours,
				patientRepository, administrativeGenderCodeRepository,
				passwordEncoder, userContext, emailSender, tokenGenerator,
				emailTokenRepository, usersRepository, spiritQueryService,
				null, mrnService, pixService);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSignup_Throws_Exception_Given_Link_Url_Has_Whitespaces_Only()
			throws MessagingException, UsernameNotExistException,
			EmailAddressNotExistException, Hl7v3TransformerException,
			SpiritClientNotAvailableException, PatientExistingException {
		SignupDto signupDto = mock(SignupDto.class);
		sut.signup(signupDto, " ");
	}

	// @Test(expected = IllegalArgumentException.class)
	// public void
	// testSignup_Throws_Exception_Given_Valid_Link_Url_And_Given_No_Email_Address()
	// throws MessagingException, UsernameNotExistException,
	// EmailAddressNotExistException, Hl7v3TransformerException {
	// // Arrange
	// final String linkUrl = "linkUrl";
	// SignupDto signupDto = mock(SignupDto.class);
	// when(signupDto.getUsername()).thenReturn("username");
	//
	// when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
	// when(spiritQueryService.getHIEPatientIdbyPDQ(signupDto)).thenReturn("123");
	//
	// // Act
	// sut.signup(signupDto, linkUrl);
	// }

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmailToken_Throws_Exception_When_Username_Has_Whitespaces_Only()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createEmailToken("  ", "emailAddress");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmailToken_Throws_Exception_When_Username_Is_Null()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {

		sut.createEmailToken(null, "emailAddress");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmailToken_Throws_Exception_When_Email_Has_Whitespaces_Only()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createEmailToken("username", "  ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmailToken_Throws_Exception_When_Email_Is_Null()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createEmailToken("username", null);
	}

	@Test
	public void testCreateEmailToken() throws UsernameNotExistException,
			EmailAddressNotExistException, MessagingException {
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getEmail()).thenReturn("emailAddress");
		sut.createEmailToken("username", "emailAddress");
	}

	@Test
	public void testFindByUserName() {
		Users user = mock(Users.class);
		when(usersRepository.loadUserByUsername(anyString())).thenReturn(user);
		sut.findUserByUsername("username");
	}

	@Test
	public void testRecoverUsername() throws ParseException {
		when(
				patientRepository
						.findByFirstNameAndLastNameAndBirthDayAndEmail(
								anyString(), anyString(), any(Date.class),
								anyString())).thenReturn(patient);
		when(patient.getUsername()).thenReturn("username");
		assertNotNull((sut.recoverUsername("Albert", "Smith", "02/19/1983",
				"email@email.com")));
	}

	@Test
	public void testRecoverUsernameWhenNotFound() throws ParseException {
		when(
				patientRepository
						.findByFirstNameAndLastNameAndBirthDayAndEmail(
								anyString(), anyString(), any(Date.class),
								anyString())).thenReturn(patient);
		when(patient.getUsername()).thenReturn(null);
		assertNull((sut.recoverUsername("Albert", "Smith", "02/19/1983",
				"email@email.com")));
	}
}
