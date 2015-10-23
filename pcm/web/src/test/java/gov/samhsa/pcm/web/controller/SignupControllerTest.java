package gov.samhsa.pcm.web.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.pcm.infrastructure.security.RecaptchaService;
import gov.samhsa.pcm.service.account.AccountService;
import gov.samhsa.pcm.service.account.AccountVerificationService;
import gov.samhsa.pcm.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.pcm.service.validator.pg.FieldValidator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class SignupControllerTest {

	@Mock
	AccountService accountService;

	@Mock
	AdministrativeGenderCodeService administrativeGenderCodeService;

	@Mock
	AccountVerificationService accountVerificationService;

	@Mock
	FieldValidator fieldValidator;

	@Mock
	RecaptchaService recaptchaUtil;

	@Mock
	EventService eventService;

	MockMvc mockMvc;

	@Before
	public void before() {
		doReturn("RecaptchaHtml").when(recaptchaUtil).createSecureRecaptchaHtml();
		SignupController signupController = new SignupController(accountService, administrativeGenderCodeService, fieldValidator, accountVerificationService,recaptchaUtil,eventService);
		mockMvc = MockMvcBuilders.standaloneSetup(signupController).build();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_when_accountService_is_null_verify_exception_is_thrown(){
		new SignupController(null,administrativeGenderCodeService,fieldValidator,accountVerificationService,recaptchaUtil,eventService);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_when_administrativeGenderCodeService_is_null_verify_exception_is_thrown(){
		new SignupController(accountService,null,fieldValidator,accountVerificationService,recaptchaUtil,eventService);
	}

	@Test
	@Ignore("Temporarily ignore this test because patient self sign up is disabled")
	public void testSignupModel() throws Exception {
		mockMvc.perform(get("/registration.html"))
			.andExpect(view().name("views/registration"));
	}

	@Test
	@Ignore("Temporarily ignore this test because patient self sign up is disabled")
	public void testSignupSignupDtoBindingResultHttpServletRequestRedirectAttributesModel() throws Exception {
		when(recaptchaUtil.checkAnswer(anyString(), anyString(), anyString())).thenReturn(true);
		mockMvc.perform(post("/registration.html")
				.param("recaptcha_challenge_field", "recaptcha_challenge_field")
				.param("recaptcha_response_field", "recaptcha_response_field"))
			.andExpect(view().name("views/registration"));
		mockMvc.perform(post("/registration.html")
				.param("recaptcha_challenge_field", "recaptcha_challenge_field")
				.param("recaptcha_response_field", "recaptcha_response_field")
				.param("genderCode", "M"))
				.andExpect(view().name("views/signupVerification"));
		Users user = mock(Users.class);
		when(accountService.findUserByUsername(anyString())).thenReturn(user);
		mockMvc.perform(post("/registration.html")
				.param("recaptcha_challenge_field", "recaptcha_challenge_field")
				.param("recaptcha_response_field", "recaptcha_response_field")
				.param("genderCode", "M"))
				.andExpect(view().name("views/registration"));
	}

	@Test
	public void testVerifyLink() throws Exception {
		when(accountVerificationService.isAccountVerificationTokenExpired(anyString())).thenReturn(true);
		mockMvc.perform(get("/verifyLink.html?token=914653028441015461098868301011413486798"))
			.andExpect(status().isOk())
			.andExpect(view().name("views/signupVerification"));
		when(accountVerificationService.isAccountVerificationTokenExpired(anyString())).thenReturn(false);
		mockMvc.perform(get("/verifyLink.html?token=914653028441015461098868301011413486798"))
		 	.andExpect(redirectedUrl("/index.html"));
	}

}
