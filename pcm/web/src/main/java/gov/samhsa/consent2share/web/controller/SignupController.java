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
package gov.samhsa.consent2share.web.controller;

import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.infrastructure.eventlistener.EventService;
import gov.samhsa.consent2share.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.consent2share.infrastructure.security.RecaptchaService;
import gov.samhsa.consent2share.infrastructure.security.TokenExpiredException;
import gov.samhsa.consent2share.infrastructure.security.TokenNotExistException;
import gov.samhsa.consent2share.infrastructure.security.UsernameNotExistException;
import gov.samhsa.consent2share.infrastructure.securityevent.UserCreatedEvent;
import gov.samhsa.consent2share.service.account.AccountService;
import gov.samhsa.consent2share.service.account.AccountVerificationService;
import gov.samhsa.consent2share.service.account.pg.PatientExistingException;
import gov.samhsa.consent2share.service.dto.AccountVerificationDto;
import gov.samhsa.consent2share.service.dto.LookupDto;
import gov.samhsa.consent2share.service.dto.SignupDto;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.spirit.SpiritClientNotAvailableException;
import gov.samhsa.consent2share.service.validator.pg.FieldValidator;

import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The Class SignupController.
 */
@Controller
public class SignupController extends AbstractController {

	/** The account service. */
	private AccountService accountService;

	/** The administrative gender code service. */
	private AdministrativeGenderCodeService administrativeGenderCodeService;

	/** The account verification service. */
	private AccountVerificationService accountVerificationService;

	/** The field validator. */
	private FieldValidator fieldValidator;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/** The recaptcha util. */
	@Autowired
	RecaptchaService recaptchaUtil;

	@Autowired
	EventService eventService;

	/**
	 * Instantiates a new signup controller.
	 *
	 * @param accountService
	 *            the account service
	 * @param administrativeGenderCodeService
	 *            the administrative gender code service
	 * @param fieldValidator
	 *            the field validator
	 * @param accountVerificationService
	 *            the account verification service
	 */
	@Autowired
	public SignupController(AccountService accountService,
			AdministrativeGenderCodeService administrativeGenderCodeService,
			FieldValidator fieldValidator,
			AccountVerificationService accountVerificationService,
			RecaptchaService recaptchaUtil, EventService eventService) {
		if (accountService == null) {
			throw new IllegalArgumentException("accountService cannot be null");
		}
		if (administrativeGenderCodeService == null) {
			throw new IllegalArgumentException(
					"administrativeGenderCodeService cannot be null");
		}
		this.accountService = accountService;
		this.administrativeGenderCodeService = administrativeGenderCodeService;
		this.fieldValidator = fieldValidator;
		this.accountVerificationService = accountVerificationService;
		this.recaptchaUtil = recaptchaUtil;
		this.eventService = eventService;
	}

	/**
	 * Signup.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	// @RequestMapping(value = "registration.html")
	public String signup(
			Model model,
			@RequestParam(value = "notify", required = false) String notification) {
		SignupDto signupDto = new SignupDto();
		model.addAttribute("signupDto", signupDto);
		List<LookupDto> genderCodes = administrativeGenderCodeService
				.findAllAdministrativeGenderCodes();
		String captchaString = recaptchaUtil.createSecureRecaptchaHtml();
		model.addAttribute("genderCodes", genderCodes);
		model.addAttribute("captcha", captchaString);
		model.addAttribute("notification", notification);
		return "views/registration";
	}

	/**
	 * Signup.
	 *
	 * @param signupDto
	 *            the signup dto
	 * @param result
	 *            the result
	 * @param request
	 *            the request
	 * @param redirectAttributes
	 *            the redirect attributes
	 * @param model
	 *            the model
	 * @return the string
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws ParseException
	 *             the parse exception
	 * @throws UsernameNotExistException
	 *             the username not exist exception
	 * @throws EmailAddressNotExistException
	 *             the email address not exist exception
	 * @throws SpiritClientNotAvailableException
	 */
	// @RequestMapping(value = "registration.html", method = RequestMethod.POST)
	public String signup(@Valid SignupDto signupDto, BindingResult result,
			HttpServletRequest request, RedirectAttributes redirectAttributes,
			Model model) throws MessagingException, ParseException,
			UsernameNotExistException, EmailAddressNotExistException,
			SpiritClientNotAvailableException {
		boolean captchaIsWrong = false;
		if (request.getParameter("recaptcha_response_field") == null)
			captchaIsWrong = true;
		if (recaptchaUtil.checkAnswer(request.getRemoteAddr(),
				request.getParameter("recaptcha_challenge_field"),
				request.getParameter("recaptcha_response_field")) == false) {
			captchaIsWrong = true;
		}

		if (captchaIsWrong == true) {
			model.addAttribute("signupDto", signupDto);
			List<LookupDto> genderCodes = administrativeGenderCodeService
					.findAllAdministrativeGenderCodes();
			String captchaString = recaptchaUtil.createSecureRecaptchaHtml();
			model.addAttribute("captcha", captchaString);
			model.addAttribute("genderCodes", genderCodes);
			model.addAttribute("notification", "wrong_captcha");
			return "views/registration";
		}

		fieldValidator.validate(signupDto, result);

		if (result.hasErrors()) {
			String captchaString = recaptchaUtil.createSecureRecaptchaHtml();
			model.addAttribute("captcha", captchaString);
			List<LookupDto> genderCodes = administrativeGenderCodeService
					.findAllAdministrativeGenderCodes();
			model.addAttribute("genderCodes", genderCodes);

			return "views/registration";
		}

		String username = signupDto.getUsername();

		Users user = accountService.findUserByUsername(username);

		if (user != null) {
			String captchaString = recaptchaUtil.createSecureRecaptchaHtml();
			model.addAttribute("captcha", captchaString);
			List<LookupDto> genderCodes = administrativeGenderCodeService
					.findAllAdministrativeGenderCodes();
			model.addAttribute("genderCodes", genderCodes);
			FieldError error = new FieldError("signupDto", "username",
					"Username is already in use.");
			result.addError(error);

			return "views/registration";
		}

		String linkUrl = getServletUrl(request);

		try {
			accountService.signup(signupDto, linkUrl.toString());
		} catch (PatientExistingException ex) {
			model.addAttribute("notification", "PatientExisting");
			return "views/registration";
		}

		eventService.raiseSecurityEvent(new UserCreatedEvent(request
				.getRemoteAddr(), signupDto.getUsername()));
		SecurityContextHolder.clearContext();

		model.addAttribute("tokenSuccess", true);
		return "views/signupVerification";
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
	@RequestMapping(value = "verifyLink.html")
	public String verifyLink(
			@RequestParam(value = "token", required = true) String token,
			Model model, HttpServletRequest request)
			throws TokenNotExistException, TokenExpiredException,
			UsernameNotExistException, MessagingException {

		try {
			if (accountVerificationService
					.isAccountVerificationTokenExpired(token)) {
				model.addAttribute("tokenExpired", true);
				return "views/signupVerification";
			}
		} catch (TokenNotExistException ex) {
			model.addAttribute("tokenNotExist", true);
			return "views/signupVerification";
		}

		String linkUrl = getServletUrl(request);
		AccountVerificationDto accountVerificationDto = new AccountVerificationDto(
				token);

		try {
			accountVerificationService.enableAccount(accountVerificationDto,
					linkUrl);
		} catch (TokenExpiredException ex) {
			model.addAttribute("tokenExpired", true);
			return "views/signupVerification";
		}

		return "redirect:/index.html";
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
}
