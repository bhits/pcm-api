package gov.samhsa.pcm.web.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import gov.samhsa.pcm.domain.account.TroubleType;
import gov.samhsa.pcm.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.pcm.infrastructure.security.TokenExpiredException;
import gov.samhsa.pcm.infrastructure.security.TokenNotExistException;
import gov.samhsa.pcm.infrastructure.security.UsernameNotExistException;
import gov.samhsa.pcm.service.account.AccountService;
import gov.samhsa.pcm.service.account.PasswordResetService;
import gov.samhsa.pcm.service.dto.LoginTroubleDto;
import gov.samhsa.pcm.service.dto.PasswordResetDto;
import gov.samhsa.pcm.service.validator.FieldValidatorChangePassword;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroubleCreateNewPassword;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroublePassword;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroubleSelection;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroubleUsername;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.StringUtils;

public class AccountControllerTest {

	private AccountController sut;

	private FieldValidatorLoginTroublePassword fieldValidatorPassword;
	private FieldValidatorLoginTroubleUsername fieldValidatorUsername;
	private FieldValidatorLoginTroubleSelection fieldValidatorTroubleSelection;
	private FieldValidatorLoginTroubleCreateNewPassword fieldValidatorLoginTroubleCreateNewPassword;
	private PasswordResetService passwordResetService;
	private AccountService accountService;
	private FieldValidatorChangePassword fieldValidatorChangePassword;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		// Mock dependencies and create sut
		// Just to save a few lines of code for each individual test
		// But independency, clarity of the unit tests are much more important
		// than code reuse
		passwordResetService = mock(PasswordResetService.class);
		accountService = mock(AccountService.class);

		fieldValidatorPassword = new FieldValidatorLoginTroublePassword();
		fieldValidatorUsername = new FieldValidatorLoginTroubleUsername();
		
		fieldValidatorTroubleSelection = new FieldValidatorLoginTroubleSelection();
		fieldValidatorLoginTroubleCreateNewPassword = new FieldValidatorLoginTroubleCreateNewPassword();

		sut = new AccountController(fieldValidatorPassword,
				fieldValidatorLoginTroubleCreateNewPassword,
				fieldValidatorTroubleSelection, fieldValidatorChangePassword, fieldValidatorUsername, passwordResetService, accountService);

		mockMvc = standaloneSetup(this.sut).build();
	}

	@Test
	public void testLoginTrouble_When_HttpGet() throws Exception {
		Matcher<LoginTroubleDto> loginTroubleDtoMatcher = notNullValue(LoginTroubleDto.class);

		mockMvc.perform(get("/loginTrouble.html"))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeExists(
								StringUtils.uncapitalize(LoginTroubleDto.class
										.getSimpleName())))
				.andExpect(
						model().attribute(
								StringUtils.uncapitalize(LoginTroubleDto.class
										.getSimpleName()),
								loginTroubleDtoMatcher))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("views/loginTrouble"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testLoginTrouble_When_HttpPost_With_No_Value_For_Param_TroubleTypeId()
			throws Exception {
		mockMvc.perform(post("/loginTrouble.html").param("troubleTypeId", ""))
				.andExpect(status().isOk()).andExpect(model().hasErrors())
				.andExpect(view().name("views/loginTrouble"));
	}

	@Test
	public void testLoginTrouble_When_HttpPost_With_0_Value_For_Param_TroubleTypeId()
			throws Exception {
		mockMvc.perform(post("/loginTrouble.html").param("troubleTypeId", "0"))
				.andExpect(status().isOk()).andExpect(model().hasErrors())
				.andExpect(view().name("views/loginTrouble"));
	}

	@Test
	public void testLoginTrouble_When_HttpPost_With_4_Value_For_Param_TroubleTypeId()
			throws Exception {
		mockMvc.perform(post("/loginTrouble.html").param("troubleTypeId", "4"))
				.andExpect(status().isOk()).andExpect(model().hasErrors())
				.andExpect(view().name("views/loginTrouble"));
	}

	@Test
	public void testLoginTrouble_When_HttpPost_With_Value_1_For_Param_TroubleTypeId()
			throws Exception {
		mockMvc.perform(post("/loginTrouble.html").param("troubleTypeId", "1"))
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/loginTroublePassword.html"));
	}

	@Test
	public void testLoginTrouble_When_HttpPost_With_Value_2_For_Param_TroubleTypeId()
			throws Exception {
		mockMvc.perform(post("/loginTrouble.html").param("troubleTypeId", "2"))
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/loginTroubleUsername.html"));
	}

	@Test
	public void testLoginTrouble_When_HttpPost_With_Value_3_For_Param_TroubleTypeId()
			throws Exception {
		mockMvc.perform(post("/loginTrouble.html").param("troubleTypeId", "3"))
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/loginTroubleOther.html"));
	}

	@Test
	public void testLoginTroublePassword_When_HttpGet() throws Exception {

		Matcher<LoginTroubleDto> loginTroubleDtoMatcher = new TypeSafeMatcher<LoginTroubleDto>() {

			@Override
			public void describeTo(Description arg0) {
				arg0.appendText(String.format(
						"TroubleType of %s is UNKNOWN_PASSWORD",
						LoginTroubleDto.class.getSimpleName()));
			}

			@Override
			protected boolean matchesSafely(LoginTroubleDto item) {
				if (item != null
						&& item.getTroubleTypeId() == TroubleType.UNKNOWN_PASSWORD
								.getValue()) {
					return true;
				}
				return false;
			}
		};

		final String loginTroubleDtoDtoObjectName = StringUtils
				.uncapitalize(LoginTroubleDto.class.getSimpleName());

		mockMvc.perform(get("/loginTroublePassword.html"))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeExists(loginTroubleDtoDtoObjectName))
				.andExpect(
						model().attribute(loginTroubleDtoDtoObjectName,
								loginTroubleDtoMatcher))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("views/loginTroublePassword"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testLoginTroublePassword_When_HttpPost_With_Value_WhiteSpace_For_Param_Username()
			throws Exception {

		mockMvc.perform(
				post("/loginTroublePassword.html").param("username", " "))
				.andExpect(status().isOk()).andExpect(model().hasErrors())
				.andExpect(view().name("views/loginTroublePassword"));
	}

	@Test
	public void testLoginTroublePassword_When_HttpPost_With_Invalid_Lenght_For_Param_Username()
			throws Exception {

		mockMvc.perform(
				post("/loginTroublePassword.html").param("username", "1"))
				.andExpect(status().isOk()).andExpect(model().hasErrors())
				.andExpect(view().name("views/loginTroublePassword"));

		mockMvc.perform(
				post("/loginTroublePassword.html").param("username",
						"1234567890123456789012345678901"))
				.andExpect(status().isOk()).andExpect(model().hasErrors())
				.andExpect(view().name("views/loginTroublePassword"));
	}

	@Test
	public void testLoginTroublePassword_When_HttpPost_With_Value_WhiteSpace_For_Param_Email()
			throws Exception {

		mockMvc.perform(post("/loginTroublePassword.html").param("email", " "))
				.andExpect(status().isOk()).andExpect(model().hasErrors())
				.andExpect(view().name("views/loginTroublePassword"));
	}

	@Test
	public void testLoginTroublePassword_When_HttpPost_With_Invalid_Value_For_Param_Email()
			throws Exception {

		mockMvc.perform(
				post("/loginTroublePassword.html").param("email", "blabal"))
				.andExpect(status().isOk()).andExpect(model().hasErrors())
				.andExpect(view().name("views/loginTroublePassword"));
	}

	@Test
	public void testLoginTroublePassword_When_HttpPost_With_Valid_Values_For_Param_Username_And_Email()
			throws Exception {

		doNothing().when(passwordResetService).createPasswordResetToken(
				anyString(), anyString(), anyString());

		mockMvc.perform(
				post("/loginTroublePassword.html").param("username", "blabal")
						.param("email", "test@test.com"))
				.andExpect(status().is(302))
				.andExpect(model().hasNoErrors())
				.andExpect(
						request().sessionAttribute("tokenMessage",
								"tokenSuccess"))
				.andExpect(view().name("redirect:/newPasswordRequested.html"));

		verify(passwordResetService, times(1)).createPasswordResetToken(
				anyString(), anyString(), anyString());
	}

	@Test
	public void testLoginTroublePassword_When_HttpPost_With_Valid_Values_For_Param_Username_And_Email_And_When_With_UsernameNotExistException()
			throws Exception {

		doThrow(new UsernameNotExistException(""))
				.when(passwordResetService)
				.createPasswordResetToken(anyString(), anyString(), anyString());

		final String loginTroubleDtoObjectName = StringUtils
				.uncapitalize(LoginTroubleDto.class.getSimpleName());

		mockMvc.perform(
				post("/loginTroublePassword.html").param("username", "blabal")
						.param("email", "test@test.com"))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeHasFieldErrors(
								loginTroubleDtoObjectName, "username"))
				.andExpect(view().name("views/loginTroublePassword"));
	}

	@Test
	public void testLoginTroublePassword_When_HttpPost_With_Valid_Values_For_Param_Username_And_Email_And_When_With_EmailAddressNotExistException()
			throws Exception {

		doThrow(new EmailAddressNotExistException("")).when(
				passwordResetService).createPasswordResetToken(anyString(),
				anyString(), anyString());

		final String loginTroubleDtoObjectName = StringUtils
				.uncapitalize(LoginTroubleDto.class.getSimpleName());

		mockMvc.perform(
				post("/loginTroublePassword.html").param("username", "blabal")
						.param("email", "test@test.com"))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeHasFieldErrors(
								loginTroubleDtoObjectName, "email"))
				.andExpect(view().name("views/loginTroublePassword"));
	}

	@Test
	public void testNewPasswordRequested_When_HttpGet() throws Exception {

		final String tokenMessageValue = "bla";

		mockMvc.perform(
				get("/newPasswordRequested.html").sessionAttr("tokenMessage",
						tokenMessageValue))
				.andExpect(status().isOk())
				.andExpect(model().attribute("tokenMessage", tokenMessageValue))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("views/newPasswordRequested"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testVerifyLink_Returns_Bad_Request_When_HttpGet_Without_Para_Token()
			throws Exception {

		mockMvc.perform(get("/resetPasswordLink.html")).andExpect(
				status().is(400));
	}

	@Test
	public void testVerifyLink_When_HttpGet_With_Para_Token_And_When_Token_Expired()
			throws Exception {

		final String tokenVaule = "bla";

		when(passwordResetService.isPasswordResetTokenExpired(tokenVaule))
				.thenReturn(true);

		mockMvc.perform(
				get("/resetPasswordLink.html").param("token", tokenVaule))
				.andExpect(status().is(302))
				.andExpect(
						request().sessionAttribute("tokenMessage",
								"tokenExpired"))
				.andExpect(view().name("redirect:/newPasswordRequested.html"));
	}

	@Test
	public void testVerifyLink_When_HttpGet_With_Para_Token_And_When_Token_Not_Expired()
			throws Exception {

		final String tokenVaule = "bla";

		when(passwordResetService.isPasswordResetTokenExpired(tokenVaule))
				.thenReturn(false);

		mockMvc.perform(
				get("/resetPasswordLink.html").param("token", tokenVaule))
				.andExpect(status().is(302))
				.andExpect(request().sessionAttribute("token", tokenVaule))
				.andExpect(view().name("redirect:/createPassword.html"));
	}

	@Test
	public void testVerifyLink_When_HttpGet_With_Para_Token_And_When_Token_Not_Exist()
			throws Exception {

		final String tokenVaule = "bla";

		when(passwordResetService.isPasswordResetTokenExpired(tokenVaule))
				.thenThrow(new TokenNotExistException(""));

		mockMvc.perform(
				get("/resetPasswordLink.html").param("token", tokenVaule))
				.andExpect(status().is(302))
				.andExpect(
						request().sessionAttribute("tokenMessage",
								"tokenNotExist"))
				.andExpect(view().name("redirect:/newPasswordRequested.html"));
	}

	@Test
	public void testCreatePassword_When_HttpGet() throws Exception {

		final String tokenValue = "Bla";

		Matcher<PasswordResetDto> passwordResetDtoDtoMatcher = new TypeSafeMatcher<PasswordResetDto>() {

			@Override
			public void describeTo(Description arg0) {
				arg0.appendText(String.format("Token of %s is %s",
						PasswordResetDto.class.getSimpleName(), tokenValue));
			}

			@Override
			protected boolean matchesSafely(PasswordResetDto item) {
				if (item != null && item.getToken().equals(tokenValue)) {
					return true;
				}
				return false;
			}
		};

		final String passwordResetDtoObjectName = StringUtils
				.uncapitalize(PasswordResetDto.class.getSimpleName());

		mockMvc.perform(
				get("/createPassword.html").sessionAttr("token", tokenValue))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists(passwordResetDtoObjectName))
				.andExpect(
						model().attribute(passwordResetDtoObjectName,
								passwordResetDtoDtoMatcher))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("views/createPassword"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void tesCcreatePasswordk_When_HttpPost_With_Value_WhiteSpace_For_Param_Password()
			throws Exception {

		final String passwordResetDtoObjectName = StringUtils
				.uncapitalize(PasswordResetDto.class.getSimpleName());

		mockMvc.perform(post("/createPassword.html").param("password", " "))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeHasFieldErrors(
								passwordResetDtoObjectName, "password"))
				.andExpect(view().name("views/createPassword"));
	}

	@Test
	public void tesCcreatePasswordk_When_HttpPost_With_Invalid_Length_For_Param_Password()
			throws Exception {

		final String passwordResetDtoObjectName = StringUtils
				.uncapitalize(PasswordResetDto.class.getSimpleName());

		mockMvc.perform(
				post("/createPassword.html").param("password", "1234567"))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeHasFieldErrors(
								passwordResetDtoObjectName, "password"))
				.andExpect(view().name("views/createPassword"));

		mockMvc.perform(
				post("/createPassword.html").param("password",
						"1234567890123456789012345678901"))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeHasFieldErrors(
								passwordResetDtoObjectName, "password"))
				.andExpect(view().name("views/createPassword"));
	}

	@Test
	public void tesCcreatePasswordk_When_HttpPost_With_Value_WhiteSpace_For_Param_RepeatPassword()
			throws Exception {

		final String passwordResetDtoObjectName = StringUtils
				.uncapitalize(PasswordResetDto.class.getSimpleName());

		mockMvc.perform(
				post("/createPassword.html").param("password", "12345678")
						.param("repeatPassword", " "))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeHasFieldErrors(
								passwordResetDtoObjectName, "repeatPassword"))
				.andExpect(view().name("views/createPassword"));
	}

	@Test
	public void tesCcreatePasswordk_When_HttpPost_With_Invalid_Length_For_Param_RepeatPassword()
			throws Exception {

		final String passwordResetDtoObjectName = StringUtils
				.uncapitalize(PasswordResetDto.class.getSimpleName());

		mockMvc.perform(
				post("/createPassword.html").param("password", "12345678")
						.param("repeatPassword", "1234567"))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeHasFieldErrors(
								passwordResetDtoObjectName, "repeatPassword"))
				.andExpect(view().name("views/createPassword"));

		mockMvc.perform(
				post("/createPassword.html").param("password", "12345678")
						.param("repeatPassword",
								"1234567890123456789012345678901"))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeHasFieldErrors(
								passwordResetDtoObjectName, "repeatPassword"))
				.andExpect(view().name("views/createPassword"));
	}

	@Test
	public void tesCcreatePasswordk_When_HttpPost_With_Different_Param_Password_And_RepeatPassword()
			throws Exception {

		final String passwordResetDtoObjectName = StringUtils
				.uncapitalize(PasswordResetDto.class.getSimpleName());

		mockMvc.perform(
				post("/createPassword.html").param("password", "12345678")
						.param("repeatPassword", "123456789"))
				.andExpect(status().isOk())
				.andExpect(
						model().attributeHasFieldErrors(
								passwordResetDtoObjectName, "password"))
				.andExpect(view().name("views/createPassword"));
	}

	@Test
	public void tesCcreatePasswordk_When_HttpPost_With_Valid_Param_Password_And_RepeatPassword()
			throws Exception {

		final String password = "Aa2345678$";

		mockMvc.perform(
				post("/createPassword.html").param("password", password).param(
						"repeatPassword", password))
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/accountUpdated.html"));
	}

	@Test
	public void tesCcreatePasswordk_When_HttpPost_With_Valid_Param_Password_And_RepeatPassword_And_Token_Expired()
			throws Exception {

		final String password = "Aa2345678$";

		doThrow(new TokenExpiredException(""))
				.when(passwordResetService)
				.resetPassword(Mockito.any(PasswordResetDto.class), anyString());

		mockMvc.perform(
				post("/createPassword.html").param("password", password).param(
						"repeatPassword", password))
				.andExpect(status().is(302))
				.andExpect(
						request().sessionAttribute("tokenMessage",
								"tokenExpired"))
				.andExpect(view().name("redirect:/newPasswordRequested.html"));
	}

	@Test
	public void testAccountUpdated_When_HttpGet() throws Exception {

		mockMvc.perform(get("/accountUpdated.html")).andExpect(status().isOk())
				.andExpect(view().name("views/accountUpdated"));
	}
	
	@Test
	public void testAccount_Username_Found() throws Exception {
		when(accountService.recoverUsername(anyString(), anyString(), anyString(), anyString())).thenReturn("test");
		mockMvc.perform(post("/loginTroubleUsername.html")
				.param("firstname", "Albert")
				.param("lastname", "Smith")
				.param("birthdate", "1983-02-29")
				.param("email", "email@email.com"))
				.andExpect(status().isOk())
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("views/loginTroubleUsernameFound"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testAccount_Username_NotFound() throws Exception {
		when(accountService.recoverUsername(anyString(), anyString(), anyString(), anyString())).thenReturn(null);
		mockMvc.perform(post("/loginTroubleUsername.html")
				.param("firstname", "Fake")
				.param("lastname", "Smith")
				.param("birthdate", "1983-02-29")
				.param("email", "email@email.com"))
				.andExpect(status().isOk())
				.andExpect(view().name("views/loginTroubleUsername"))
				.andExpect(model().hasErrors())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testAccount_Username_FirstNameError() throws Exception {
		when(accountService.recoverUsername(anyString(), anyString(), anyString(), anyString())).thenReturn("test");
		mockMvc.perform(post("/loginTroubleUsername.html")
				.param("firstname", "a")
				.param("lastname", "Smith")
				.param("birthdate", "1983-02-29")
				.param("email", "email@email.com"))
				.andExpect(status().isOk())
				.andExpect(view().name("views/loginTroubleUsername"))
				.andExpect(model().hasErrors())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testAccount_Username_LastNameError() throws Exception {
		when(accountService.recoverUsername(anyString(), anyString(), anyString(), anyString())).thenReturn("test");
		mockMvc.perform(post("/loginTroubleUsername.html")
				.param("firstname", "Albert")
				.param("lastname", "a")
				.param("birthdate", "1983-02-29")
				.param("email", "email@email.com"))
				.andExpect(status().isOk())
				.andExpect(view().name("views/loginTroubleUsername"))
				.andExpect(model().hasErrors())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testAccount_Username_BirthDateError() throws Exception {
		when(accountService.recoverUsername(anyString(), anyString(), anyString(), anyString())).thenReturn("test");
		mockMvc.perform(post("/loginTroubleUsername.html")
				.param("firstname", "Albert")
				.param("lastname", "Smith")
				.param("birthdate", "")
				.param("email", "email@email.com"))
				.andExpect(status().isOk())
				.andExpect(view().name("views/loginTroubleUsername"))
				.andExpect(model().hasErrors())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testAccount_Username_EmailError() throws Exception {
		when(accountService.recoverUsername(anyString(), anyString(), anyString(), anyString())).thenReturn("test");
		mockMvc.perform(post("/loginTroubleUsername.html")
				.param("firstname", "Albert")
				.param("lastname", "Smith")
				.param("birthdate", "1983-02-29")
				.param("email", "wrongEmail"))
				.andExpect(status().isOk())
				.andExpect(view().name("views/loginTroubleUsername"))
				.andExpect(model().hasErrors())
				.andDo(MockMvcResultHandlers.print());
	}
}
