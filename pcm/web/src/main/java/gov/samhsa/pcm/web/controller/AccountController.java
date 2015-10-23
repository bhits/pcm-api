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
package gov.samhsa.pcm.web.controller;

import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.account.TroubleType;
import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.pcm.infrastructure.security.TokenExpiredException;
import gov.samhsa.pcm.infrastructure.security.TokenNotExistException;
import gov.samhsa.pcm.infrastructure.security.UsernameNotExistException;
import gov.samhsa.pcm.service.account.AccountLinkToPatientService;
import gov.samhsa.pcm.service.account.AccountService;
import gov.samhsa.pcm.service.account.PasswordResetService;
import gov.samhsa.pcm.service.dto.LoginTroubleDto;
import gov.samhsa.pcm.service.dto.PasswordChangeDto;
import gov.samhsa.pcm.service.dto.PasswordResetDto;
import gov.samhsa.pcm.service.dto.SignupLinkToPatientDto;
import gov.samhsa.pcm.service.validator.FieldValidatorChangePassword;
import gov.samhsa.pcm.service.validator.FieldValidatorCreateNewAccountOnPatient;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroubleCreateNewPassword;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroublePassword;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroubleSelection;
import gov.samhsa.pcm.service.validator.FieldValidatorLoginTroubleUsername;

import java.text.ParseException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The Class AccountController.
 */
@Controller
public class AccountController extends AbstractController {

	/** The field validator password. */
	private FieldValidatorLoginTroublePassword fieldValidatorPassword;

	/** The field validator username. */
	private FieldValidatorLoginTroubleUsername fieldValidatorUsername;

	/** The field validator trouble selection. */
	private FieldValidatorLoginTroubleSelection fieldValidatorTroubleSelection;

	/** The field validator login trouble create new password. */
	private FieldValidatorLoginTroubleCreateNewPassword fieldValidatorLoginTroubleCreateNewPassword;

	/** The field validator change password. */
	private FieldValidatorChangePassword fieldValidatorChangePassword;

	/** The field validator create new account on patient. */
	@Autowired
	private FieldValidatorCreateNewAccountOnPatient fieldValidatorCreateNewAccountOnPatient;

	/** The password reset service. */
	private PasswordResetService passwordResetService;

	/** The account link to patient service. */
	@Autowired
	private AccountLinkToPatientService accountLinkToPatientService;

	/** The account service. */
	@Autowired
	private AccountService accountService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/**
	 * Instantiates a new account controller.
	 * 
	 * @param fieldValidatorPassword
	 *            the field validator password
	 * @param fieldValidatorLoginTroubleCreateNewPassword
	 *            the field validator login trouble create new password
	 * @param fieldValidatorTroubleSelection
	 *            the field validator trouble selection
	 * @param fieldValidatorChangePassword
	 *            the field validator change password
	 * @param passwordResetService
	 *            the password reset service
	 */
	@Autowired
	public AccountController(
			FieldValidatorLoginTroublePassword fieldValidatorPassword,
			FieldValidatorLoginTroubleCreateNewPassword fieldValidatorLoginTroubleCreateNewPassword,
			FieldValidatorLoginTroubleSelection fieldValidatorTroubleSelection,
			FieldValidatorChangePassword fieldValidatorChangePassword,
			FieldValidatorLoginTroubleUsername fieldValidatorUsername,
			PasswordResetService passwordResetService,
			AccountService accountService) {
		this.fieldValidatorPassword = fieldValidatorPassword;
		this.fieldValidatorTroubleSelection = fieldValidatorTroubleSelection;
		this.passwordResetService = passwordResetService;
		this.accountService = accountService;
		this.fieldValidatorLoginTroubleCreateNewPassword = fieldValidatorLoginTroubleCreateNewPassword;
		this.fieldValidatorUsername = fieldValidatorUsername;
		this.fieldValidatorChangePassword = fieldValidatorChangePassword;
	}

	/**
	 * Login trouble.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "loginTrouble.html")
	public String loginTrouble(Model model) {
		LoginTroubleDto loginTroubleDto = new LoginTroubleDto();
		model.addAttribute(loginTroubleDto);
		return "views/loginTrouble";
	}

	/**
	 * Login trouble.
	 * 
	 * @param loginTroubleDto
	 *            the login trouble dto
	 * @param result
	 *            the result
	 * @param request
	 *            the request
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "loginTrouble.html", method = RequestMethod.POST)
	public String loginTrouble(@Valid LoginTroubleDto loginTroubleDto,
			BindingResult result, HttpServletRequest request, Model model) {
		fieldValidatorTroubleSelection.validate(loginTroubleDto, result);

		if (result.hasErrors()) {
			return "views/loginTrouble";
		}

		String handleTroublePage = null;

		if (loginTroubleDto.getTroubleTypeId() == TroubleType.UNKNOWN_PASSWORD
				.getValue()) {
			handleTroublePage = "redirect:/loginTroublePassword.html";
		} else if (loginTroubleDto.getTroubleTypeId() == TroubleType.UNKNOWN_USERNAME
				.getValue()) {
			handleTroublePage = "redirect:/loginTroubleUsername.html";
		} else if (loginTroubleDto.getTroubleTypeId() == TroubleType.DIFFICULTY_SIGNING_IN
				.getValue()) {
			handleTroublePage = "redirect:/loginTroubleOther.html";
		} else {
			handleTroublePage = null;
		}

		if (handleTroublePage == null) {
			result.addError(new ObjectError(StringUtils
					.uncapitalize(LoginTroubleDto.class.getSimpleName()),
					"Please select from one of the options below."));
			handleTroublePage = "views/loginTrouble";
		}

		return handleTroublePage;
	}

	/**
	 * Login trouble password.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "loginTroublePassword.html")
	public String loginTroublePassword(Model model) {
		LoginTroubleDto loginTroubleDto = new LoginTroubleDto();
		loginTroubleDto.setTroubleTypeId(TroubleType.UNKNOWN_PASSWORD
				.getValue());
		model.addAttribute(loginTroubleDto);
		return "views/loginTroublePassword";
	}

	/**
	 * Login trouble password.
	 * 
	 * @param loginTroubleDto
	 *            the login trouble dto
	 * @param request
	 *            the request
	 * @param result
	 *            the result
	 * @param model
	 *            the model
	 * @return the string
	 * @throws UsernameNotExistException
	 *             the username not exist exception
	 * @throws EmailAddressNotExistException
	 *             the email address not exist exception
	 * @throws MessagingException
	 *             the messaging exception
	 */
	@RequestMapping(value = "loginTroublePassword.html", method = RequestMethod.POST)
	public String loginTroublePassword(@Valid LoginTroubleDto loginTroubleDto,
			HttpServletRequest request, BindingResult result, Model model)
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		fieldValidatorPassword.validate(loginTroubleDto, result);

		if (result.hasErrors()) {
			return "views/loginTroublePassword";
		}

		String linkUrl = getServletUrl(request);
		String loginTroubleDtoObjectName = StringUtils
				.uncapitalize(LoginTroubleDto.class.getSimpleName());

		try {
			passwordResetService.createPasswordResetToken(
					loginTroubleDto.getUsername(), loginTroubleDto.getEmail(),
					linkUrl);
		} catch (UsernameNotExistException ex) {
			FieldError error = new FieldError(loginTroubleDtoObjectName,
					"username", "Username does not exist");
			result.addError(error);
			return "views/loginTroublePassword";
		} catch (EmailAddressNotExistException ex) {
			FieldError error = new FieldError(loginTroubleDtoObjectName,
					"email", "Email address does not exist");
			result.addError(error);
			return "views/loginTroublePassword";
		}
		request.getSession().setAttribute("tokenMessage", "tokenSuccess");

		return "redirect:/newPasswordRequested.html";
	}

	/**
	 * Login trouble username.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "loginTroubleUsername.html")
	public String loginTroubleUsername(Model model) {
		LoginTroubleDto loginTroubleDto = new LoginTroubleDto();
		loginTroubleDto.setTroubleTypeId(TroubleType.UNKNOWN_USERNAME
				.getValue());
		model.addAttribute(loginTroubleDto);
		return "views/loginTroubleUsername";
	}

	/**
	 * Login trouble username.
	 *
	 * @param loginTroubleDto
	 *            the login trouble dto
	 * @param request
	 *            the request
	 * @param result
	 *            the result
	 * @param model
	 *            the model
	 * @return the string
	 * @throws UsernameNotExistException
	 *             the username not exist exception
	 * @throws EmailAddressNotExistException
	 *             the email address not exist exception
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws ParseException
	 *             the parse exception
	 */
	@RequestMapping(value = "loginTroubleUsername.html", method = RequestMethod.POST)
	public String loginTroubleUsername(@Valid LoginTroubleDto loginTroubleDto,
			HttpServletRequest request, BindingResult result, Model model)
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException, ParseException {
		fieldValidatorUsername.validate(loginTroubleDto, result);

		if (result.hasErrors()) {
			return "views/loginTroubleUsername";
		}

		String username = accountService.recoverUsername(
				loginTroubleDto.getFirstname(), loginTroubleDto.getLastname(),
				loginTroubleDto.getDateofbirth(), loginTroubleDto.getEmail());

		if (username == null) {
			model.addAttribute("usernameNotFound", true);

			String loginTroubleDtoObjectName = StringUtils
					.uncapitalize(LoginTroubleDto.class.getSimpleName());

			FieldError error = new FieldError(loginTroubleDtoObjectName,
					"username", "Username does not exist");
			result.addError(error);

			return "views/loginTroubleUsername";
		}

		model.addAttribute("username", username);

		return "views/loginTroubleUsernameFound";
	}

	/**
	 * Login trouble other.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "loginTroubleOther.html")
	public String loginTroubleOther(Model model) {
		LoginTroubleDto loginTroubleDto = new LoginTroubleDto();
		loginTroubleDto.setTroubleTypeId(TroubleType.DIFFICULTY_SIGNING_IN
				.getValue());
		model.addAttribute(loginTroubleDto);
		return "views/loginTroubleOther";
	}

	/**
	 * New password requested.
	 * 
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "newPasswordRequested.html")
	public String newPasswordRequested(Model model, HttpServletRequest request) {
		String tokenMessage = (String) request.getSession().getAttribute(
				"tokenMessage");
		model.addAttribute("tokenMessage", tokenMessage);
		return "views/newPasswordRequested";
	}

	/**
	 * Verify link.
	 * 
	 * @param token
	 *            the token
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 * @throws TokenNotExistException
	 *             the token not exist exception
	 * @throws TokenExpiredException
	 *             the token expired exception
	 * @throws UsernameNotExistException
	 *             the username not exist exception
	 * @throws MessagingException
	 *             the messaging exception
	 */
	@RequestMapping(value = "resetPasswordLink.html")
	public String verifyLink(
			@RequestParam(value = "token", required = true) String token,
			Model model, HttpServletRequest request)
			throws TokenNotExistException, TokenExpiredException,
			UsernameNotExistException, MessagingException {

		try {
			if (passwordResetService.isPasswordResetTokenExpired(token)) {
				// TODO: It is better not to use session
				request.getSession().setAttribute("tokenMessage",
						"tokenExpired");
				return "redirect:/newPasswordRequested.html";
			}
		} catch (TokenNotExistException ex) {
			// TODO: It is better not to use session
			request.getSession().setAttribute("tokenMessage", "tokenNotExist");
			return "redirect:/newPasswordRequested.html";
		}

		request.getSession().setAttribute("token", token);
		return "redirect:/createPassword.html";
	}

	/**
	 * Verify new account link.
	 * 
	 * @param token
	 *            the token
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "verifyNewAccountLink.html")
	public String verifyNewAccountLink(
			@RequestParam(value = "token", required = true) String token,
			Model model, HttpServletRequest request) {

		try {

			if (accountLinkToPatientService.isTokenUsed(token)) {
				request.getSession().setAttribute("tokenMessage",
						"tokenIsUsedForPatientAccount");
				return "redirect:/newPasswordRequested.html";
			}

			if (accountLinkToPatientService.isTokenExpired(token)) {
				// TODO: It is better not to use session
				request.getSession().setAttribute("tokenMessage",
						"tokenExpiredForPatientAccount");
				return "redirect:/newPasswordRequested.html";
			}
		} catch (TokenNotExistException ex) {
			request.getSession().setAttribute("tokenMessage",
					"tokenNotExistForPatientAccount");
			return "redirect:/newPasswordRequested.html";
		}

		request.getSession().setAttribute("token", token);
		return "redirect:/registrationLinkToPatient.html";
	}

	/**
	 * Registration link to patient.
	 * 
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "registrationLinkToPatient.html")
	public String registrationLinkToPatient(Model model,
			HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		SignupLinkToPatientDto signupLinkToPatientDto = new SignupLinkToPatientDto();
		signupLinkToPatientDto.setToken(token);
		model.addAttribute(signupLinkToPatientDto);
		return "views/registrationLinkToPatient";
	}

	/**
	 * Registration link to patient.
	 * 
	 * @param signupLinkToPatientDto
	 *            the signup link to patient dto
	 * @param request
	 *            the request
	 * @param result
	 *            the result
	 * @param model
	 *            the model
	 * @param redirectAttribute
	 *            the redirect attribute
	 * @return the string
	 * @throws TokenNotExistException
	 *             the token not exist exception
	 * @throws UsernameNotExistException
	 *             the username not exist exception
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws TokenExpiredException
	 *             the token expired exception
	 */
	@RequestMapping(value = "registrationLinkToPatient.html", method = RequestMethod.POST)
	public String registrationLinkToPatient(
			@Valid SignupLinkToPatientDto signupLinkToPatientDto,
			HttpServletRequest request, BindingResult result, Model model,
			RedirectAttributes redirectAttribute) {

		if (signupLinkToPatientDto.getBirthDate() == null) {
			FieldError error = new FieldError("signupLinkToPatientDto",
					"birthDate", "BirthDate can Not Null.");
			result.addError(error);
			return "redirect:/registrationLinkToPatient.html";
		}

		if (signupLinkToPatientDto.getVerificationCode() == null) {
			FieldError error = new FieldError("signupLinkToPatientDto",
					"verificationCode", "VerificationCode can Not Null.");
			result.addError(error);
			return "redirect:/registrationLinkToPatient.html";
		}

		if (accountLinkToPatientService.checkUpAccount(signupLinkToPatientDto) == false) {
			model.addAttribute("notification", "wrong_verification_info");
			model.addAttribute("signupLinkToPatientDto", signupLinkToPatientDto);
			return "views/registrationLinkToPatient";
		}

		redirectAttribute.addFlashAttribute("signupLinkToPatientDto",
				signupLinkToPatientDto);
		return "redirect:/registrationLinkToPatient_createUsername.html";
	}

	/**
	 * Registration link to patient create username.
	 * 
	 * @param signupLinkToPatientDto
	 *            the signup link to patient dto
	 * @param request
	 *            the request
	 * @param result
	 *            the result
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "registrationLinkToPatient_createUsername.html")
	public String registrationLinkToPatientCreateUsername(
			@Valid @ModelAttribute SignupLinkToPatientDto signupLinkToPatientDto,
			HttpServletRequest request, BindingResult result, Model model) {

		return "views/registrationLinkToPatient_createUsername";
	}

	/**
	 * Registration link to patient create usernam.
	 * 
	 * @param signupLinkToPatientDto
	 *            the signup link to patient dto
	 * @param request
	 *            the request
	 * @param result
	 *            the result
	 * @param model
	 *            the model
	 * @return the string
	 * @throws TokenNotExistException
	 *             the token not exist exception
	 * @throws UsernameNotExistException
	 *             the username not exist exception
	 * @throws TokenExpiredException
	 *             the token expired exception
	 */
	@RequestMapping(value = "registrationLinkToPatient_createUsername.html", method = RequestMethod.POST)
	public String registrationLinkToPatientCreateUsernam(
			@Valid SignupLinkToPatientDto signupLinkToPatientDto,
			HttpServletRequest request, BindingResult result, Model model) {

		fieldValidatorCreateNewAccountOnPatient.validate(
				signupLinkToPatientDto, result);

		if (result.hasErrors()) {
			return "views/registrationLinkToPatient_createUsername";
		}

		String username = signupLinkToPatientDto.getUsername();

		Users user = accountService.findUserByUsername(username);

		if (user != null) {
			FieldError error = new FieldError("signupLinkToPatientDto",
					"username", "Username is already in use.");
			result.addError(error);

			return "views/registrationLinkToPatient_createUsername";
		}

		String linkUrl = getServletUrl(request);
		try {
			accountLinkToPatientService.setupAccount(signupLinkToPatientDto,
					linkUrl.toString());
		} catch (MessagingException e) {
			model.addAttribute("notification", "wrong_verification_info");
			model.addAttribute("signupLinkToPatientDto", signupLinkToPatientDto);
			return "views/registrationLinkToPatient";
		} catch (TokenExpiredException e) {
			request.getSession().setAttribute("tokenMessage",
					"tokenExpiredForPatientAccount");
			return "redirect:/newPasswordRequested.html";
		}
		SecurityContextHolder.clearContext();

		return "views/registrationLinkToPatient_Successful";
	}

	/**
	 * Creates the password.
	 * 
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "createPassword.html")
	public String createPassword(Model model, HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		if (token == null) {
			request.getSession().setAttribute("tokenMessage", "tokenNotExist");
			return "redirect:/newPasswordRequested.html";
		}
		PasswordResetDto passwordResetDto = new PasswordResetDto();
		passwordResetDto.setToken(token);
		model.addAttribute(passwordResetDto);
		return "views/createPassword";
	}

	/**
	 * Creates the password.
	 * 
	 * @param passwordResetDto
	 *            the password reset dto
	 * @param request
	 *            the request
	 * @param result
	 *            the result
	 * @param model
	 *            the model
	 * @return the string
	 * @throws TokenNotExistException
	 *             the token not exist exception
	 * @throws UsernameNotExistException
	 *             the username not exist exception
	 * @throws MessagingException
	 *             the messaging exception
	 */
	@RequestMapping(value = "createPassword.html", method = RequestMethod.POST)
	public String createPassword(@Valid PasswordResetDto passwordResetDto,
			HttpServletRequest request, BindingResult result, Model model)
			throws TokenNotExistException, UsernameNotExistException,
			MessagingException {
		fieldValidatorLoginTroubleCreateNewPassword.validate(passwordResetDto,
				result);

		if (result.hasErrors()) {
			return "views/createPassword";
		}

		String linkUrl = getServletUrl(request);

		try {
			passwordResetService.resetPassword(passwordResetDto, linkUrl);
		} catch (TokenExpiredException ex) {
			// TODO: Better not to use Session
			request.getSession().setAttribute("tokenMessage", "tokenExpired");
			return "redirect:/newPasswordRequested.html";
		}

		return "redirect:/accountUpdated.html";
	}

	/**
	 * Account updated.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "accountUpdated.html")
	public String accountUpdated(Model model) {
		return "views/accountUpdated";
	}

	/**
	 * Gets the servlet url.
	 * 
	 * @param request
	 *            the request
	 * @return the servlet url
	 */
	private String getServletUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		StringBuffer hostName = new StringBuffer();
		hostName.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			hostName.append(":").append(serverPort);
		}

		hostName.append(contextPath);
		return hostName.toString();
	}

	/**
	 * Change Password.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	/*
	 * Issue #242 Fix Start The Change Password page doesn't render at all
	 * unless the users logged in.
	 */
	@RequestMapping(value = "changePassword.html")
	public String changePassword(Model model) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = currentUser.getUsername();

		PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
		passwordChangeDto.setUsername(username);

		model.addAttribute(passwordChangeDto);
		return "views/changePassword";
	}

	// Issue #242 Fix end

	/**
	 * Change Password.
	 * 
	 * @param passwordChangeDto
	 *            the password change dto
	 * @param result
	 *            the result
	 * @param model
	 *            the model
	 * @return the string
	 * @throws UsernameNotExistException
	 *             the username not exist exception
	 * @throws MessagingException
	 *             the messaging exception
	 */
	@RequestMapping(value = "changePassword.html", method = RequestMethod.POST)
	public String changePassword(PasswordChangeDto passwordChangeDto,
			BindingResult result, Model model)
			throws UsernameNotExistException, MessagingException {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = currentUser.getUsername();
		passwordChangeDto.setUsername(username);

		fieldValidatorChangePassword.validate(passwordChangeDto, result);

		if (result.hasErrors()) {
			return "views/changePassword";
		}

		String passwordChangeDtoObjectName = StringUtils
				.uncapitalize(PasswordChangeDto.class.getSimpleName());
		boolean isChangeSuccess = false;

		try {
			isChangeSuccess = passwordResetService
					.changePassword(passwordChangeDto);
		} catch (UsernameNotExistException ex) {
			FieldError error = new FieldError(passwordChangeDtoObjectName,
					"username", "Username does not exist");
			result.addError(error);
			model.addAttribute("generalErrorMessage",
					"An unknown error has occurred");
			return "views/changePassword";
		}

		if (isChangeSuccess == true) {
			return "redirect:defaultLoginPage.html?notify=passChangeSuccess";
		} else {
			FieldError error = new FieldError(passwordChangeDtoObjectName,
					"oldPassword", "Wrong password");
			result.addError(error);
			return "views/changePassword";
		}
	}

}
