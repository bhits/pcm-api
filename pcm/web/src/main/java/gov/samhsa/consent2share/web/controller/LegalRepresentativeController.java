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
import gov.samhsa.consent2share.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.consent2share.service.dto.LegalRepresentativeDto;
import gov.samhsa.consent2share.service.dto.PatientLegalRepresentativeAssociationDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.patient.PatientLegalRepresentativeAssociationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.provider.IndividualProviderService;
import gov.samhsa.consent2share.service.provider.OrganizationalProviderService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.pg.StateCodeServicePg;
import gov.samhsa.consent2share.service.spirit.SpiritClientNotAvailableException;
import gov.samhsa.consent2share.service.validator.pg.FieldValidator;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * The Class LegalRepresentativeController.
 */
@Controller
@RequestMapping("/patients")
public class LegalRepresentativeController {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The patient service. */
	@Autowired
	PatientService patientService;

	/** The individual provider service. */
	@Autowired
	IndividualProviderService individualProviderService;

	/** The organizational provider service. */
	@Autowired
	OrganizationalProviderService organizationalProviderService;

	/** The administrative gender code service. */
	@Autowired
	AdministrativeGenderCodeService administrativeGenderCodeService;

	/** The race code service. */
	@Autowired
	RaceCodeService raceCodeService;

	/** The religious affiliation code service. */
	@Autowired
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	/** The state code service. */
	@Autowired
	StateCodeServicePg stateCodeService;

	/** The patient legal representative association service. */
	@Autowired
	PatientLegalRepresentativeAssociationService patientLegalRepresentativeAssociationService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/** The field validator. */
	@Autowired
	private FieldValidator fieldValidator;

	/** The message properties. */
	@Autowired
	private MessageSource messageProperties;

	/**
	 * Adds the legal representative.
	 *
	 * @param newLegalRepresentativeDto
	 *            the new legal representative dto
	 * @param result
	 *            the result
	 * @param response
	 *            the response
	 * @param model
	 *            the model
	 * @return the model and view
	 * @throws ParseException
	 *             the parse exception
	 */
	@RequestMapping(value = "connectionMain.html", method = RequestMethod.POST)
	public ModelAndView addLegalRepresentative(
			@Valid LegalRepresentativeDto newLegalRepresentativeDto,
			BindingResult result, HttpServletResponse response, Model model)
			throws ParseException {

		fieldValidator.validate(newLegalRepresentativeDto, result);
		if (result.hasErrors()) {
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			Map<String, String> map = new HashMap<String, String>();

			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				for (String errorCode : error.getCodes()) {
					try {
						String errorMessage = messageProperties.getMessage(
								errorCode, null, null);
						String[] a = errorCode.split("\\.");

						// the third token is the field name
						map.put(a[a.length - 1], errorMessage);
					} catch (NoSuchMessageException e) {
						continue;
					}
				}
			}

			view.setAttributesMap(map);
			ModelAndView mav = new ModelAndView();
			mav.setView(view);

			// return json if validation not pass
			return mav;
		}

		AuthenticatedUser currentUser = userContext.getCurrentUser();

		PatientProfileDto legalRepDto = patientLegalRepresentativeAssociationService
				.getPatientDtoFromLegalRepresentativeDto(newLegalRepresentativeDto);
		PatientLegalRepresentativeAssociationDto associationDto = patientLegalRepresentativeAssociationService
				.getAssociationDtoFromLegalRepresentativeDto(newLegalRepresentativeDto);

		// save to get id
		PatientProfileDto updatedLegalRepresentativeDto = patientService
				.savePatient(legalRepDto);
		associationDto.setPatientId(patientService
				.findPatientProfileByUsername(currentUser.getUsername())
				.getId());
		associationDto.setLegalRepresentativeId(updatedLegalRepresentativeDto
				.getId());
		patientLegalRepresentativeAssociationService
				.savePatientLegalRepresentativeAssociationDto(associationDto);

		// return html if validation passed
		return new ModelAndView("redirect:/patients/connectionMain.html");
	}

	/**
	 * Edits the legal representative.
	 *
	 * @param newLegalRepresentativeDto
	 *            the new legal representative dto
	 * @param result
	 *            the result
	 * @param legalRepId
	 *            the legal rep id
	 * @param response
	 *            the response
	 * @param model
	 *            the model
	 * @return the model and view
	 * @throws ParseException
	 *             the parse exception
	 * @throws SpiritClientNotAvailableException
	 */
	@RequestMapping(value = "connectionMain/editLegalRepresenttive/{legalRepId}", method = RequestMethod.POST)
	public ModelAndView editLegalRepresentative(
			@Valid LegalRepresentativeDto newLegalRepresentativeDto,
			BindingResult result, @PathVariable("legalRepId") Long legalRepId,
			HttpServletResponse response, Model model) throws ParseException,
			SpiritClientNotAvailableException {

		fieldValidator.validate(newLegalRepresentativeDto, result);
		if (result.hasErrors()) {
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			Map<String, String> map = new HashMap<String, String>();

			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				for (String errorCode : error.getCodes()) {
					try {
						String errorMessage = messageProperties.getMessage(
								errorCode, null, null);
						String[] a = errorCode.split("\\.");

						// the third token is the field name
						map.put(a[a.length - 1], errorMessage);
					} catch (NoSuchMessageException e) {
						continue;
					}
				}
			}

			view.setAttributesMap(map);
			ModelAndView mav = new ModelAndView();
			mav.setView(view);

			// return json if validation not pass
			return mav;
		}

		AuthenticatedUser currentUser = userContext.getCurrentUser();

		PatientProfileDto legalRepDto = patientLegalRepresentativeAssociationService
				.getPatientDtoFromLegalRepresentativeDto(newLegalRepresentativeDto);
		PatientLegalRepresentativeAssociationDto associationDto = patientLegalRepresentativeAssociationService
				.getAssociationDtoFromLegalRepresentativeDto(newLegalRepresentativeDto);

		// TODO: The following should be in one transaction in service layer
		// TODO: The patientService.updatePatient method is updated with
		// authentication. The following method needs to be re-implemented if
		// legal-representative is still needed in the future.
		legalRepDto.setId(legalRepId);
		try {
			patientService.updatePatient(legalRepDto);
		} catch (AuthenticationFailedException e) {
			logger.warn("Exception when updating patient profile");
		}
		associationDto.setPatientId(patientService
				.findPatientProfileByUsername(currentUser.getUsername())
				.getId());
		associationDto.setLegalRepresentativeId(legalRepId);
		patientLegalRepresentativeAssociationService
				.updatePatientLegalRepresentativeAssociationDto(associationDto);

		// return html if validation passed
		return new ModelAndView("redirect:/patients/connectionMain.html");
	}

	/**
	 * Delete legal rep.
	 *
	 * @param legalRepId
	 *            the legal rep id
	 * @return the string
	 */
	@RequestMapping("connectionMain/deleteLegalRep/{legalRepId}")
	public String deleteLegalRep(@PathVariable("legalRepId") Long legalRepId) {

		if (patientService.isLegalRepForCurrentUser(legalRepId)) {
			patientLegalRepresentativeAssociationService
					.deletePatientLegalRepresentativeById(legalRepId);
			return "redirect:/patients/connectionMain.html";
		}

		return "views/resourceNotFound";

	}
}
