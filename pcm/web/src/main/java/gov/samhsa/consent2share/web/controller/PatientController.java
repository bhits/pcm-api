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

import gov.samhsa.consent2share.common.AuthenticatedUser;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.infrastructure.PixService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.SystemNotificationDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.LanguageCodeService;
import gov.samhsa.consent2share.service.reference.MaritalStatusCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.pg.StateCodeServicePg;
import gov.samhsa.consent2share.service.spirit.SpiritClientNotAvailableException;
import gov.samhsa.consent2share.service.systemnotification.SystemNotificationService;
import gov.samhsa.consent2share.service.validator.pg.FieldValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The Class PatientController.
 */
@Controller
@RequestMapping("/patients")
public class PatientController extends AbstractController {

	/** The patient service. */
	@Autowired
	PatientService patientService;

	/** The administrative gender code service. */
	@Autowired
	AdministrativeGenderCodeService administrativeGenderCodeService;

	/** The language code service. */
	@Autowired
	LanguageCodeService languageCodeService;

	/** The marital status code service. */
	@Autowired
	MaritalStatusCodeService maritalStatusCodeService;

	/** The race code service. */
	@Autowired
	RaceCodeService raceCodeService;

	/** The religious affiliation code service. */
	@Autowired
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	/** The state code service. */
	@Autowired
	StateCodeServicePg stateCodeService;

	/** The notification service. */
	@Autowired
	NotificationService notificationService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/** The field validator. */
	@Autowired
	private FieldValidator fieldValidator;

	@Autowired
	private PixService pixService;

	/** The system notification service. */
	@Autowired
	SystemNotificationService systemNotificationService;

	/**
	 * Home.
	 *
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "home.html")
	public String home(Model model, HttpServletRequest request) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = currentUser.getUsername();
		PatientProfileDto patientProfileDto = patientService
				.findPatientProfileByUsername(username);

		List<SystemNotificationDto> systemNotificationDtos = systemNotificationService
				.findAllSystemNotificationDtosByPatient(patientProfileDto
						.getId());

		String notify = request.getParameter("notify");

		model.addAttribute("notifyevent", notify);

		String notification = notificationService.notificationStage(username,
				null);
		model.addAttribute("systemNotificationDtos", systemNotificationDtos);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("notification", notification);
		return "views/patients/home";
	}

	/**
	 * Profile.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "profile.html")
	public String profile(Model model) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		PatientProfileDto patientProfileDto = patientService
				.findPatientProfileByUsername(currentUser.getUsername());

		model.addAttribute("patientProfileDto", patientProfileDto);
		model.addAttribute("currentUser", currentUser);
		populateLookupCodes(model);

		return "views/patients/profile";
	}

	/**
	 * Profile.
	 *
	 * @param patientProfileDto
	 *            the patient profile dto
	 * @param bindingResult
	 *            the binding result
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "profile.html", method = RequestMethod.POST)
	public String profile(@Valid PatientProfileDto patientProfileDto,
			BindingResult bindingResult, Model model) {

		fieldValidator.validate(patientProfileDto, bindingResult);

		if (bindingResult.hasErrors()) {

			populateLookupCodes(model);
			return "views/patients/profile";
		} else {
			AuthenticatedUser currentUser = userContext.getCurrentUser();

			model.addAttribute("currentUser", currentUser);
			patientProfileDto.setAddressCountryCode("US");

			try {
				patientService.updatePatient(patientProfileDto);
				model.addAttribute("updatedMessage",
						"Updated your profile successfully!");
			} catch (AuthenticationFailedException e) {
				model.addAttribute("updatedMessage",
						"Failed. Please check your username and password and try again.");
				PatientProfileDto originalPatientProfileDto = patientService
						.findPatientProfileByUsername(currentUser.getUsername());
				model.addAttribute("patientProfileDto",
						originalPatientProfileDto);
			} catch (SpiritClientNotAvailableException e) {
				model.addAttribute("updatedMessage",
						"Failed. Please update your profile later.");
				PatientProfileDto originalPatientProfileDto = patientService
						.findPatientProfileByUsername(currentUser.getUsername());
				model.addAttribute("patientProfileDto",
						originalPatientProfileDto);
			}

			populateLookupCodes(model);

			return "views/patients/profile";
		}
	}

	/**
	 * Populate lookup codes.
	 *
	 * @param model
	 *            the model
	 */
	private void populateLookupCodes(Model model) {

		model.addAttribute("administrativeGenderCodes",
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes());
		model.addAttribute("maritalStatusCodes",
				maritalStatusCodeService.findAllMaritalStatusCodes());
		model.addAttribute("religiousAffiliationCodes",
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes());
		model.addAttribute("raceCodes", raceCodeService.findAllRaceCodes());
		model.addAttribute("languageCodes",
				languageCodeService.findAllLanguageCodes());
		model.addAttribute("stateCodes", stateCodeService.findAllStateCodes());
	}
}
