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

import flexjson.JSONDeserializer;
import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.provider.IndividualProvider;
import gov.samhsa.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.pcm.domain.reference.EntityType;
import gov.samhsa.pcm.infrastructure.CodedConceptLookupService;
import gov.samhsa.pcm.infrastructure.PixService;
import gov.samhsa.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.pcm.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.pcm.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.pcm.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.pcm.service.account.pg.PatientExistingException;
import gov.samhsa.pcm.service.admin.AdminService;
import gov.samhsa.pcm.service.audit.AdminAuditService;
import gov.samhsa.pcm.service.consent.ConsentHelper;
import gov.samhsa.pcm.service.consent.ConsentNotFoundException;
import gov.samhsa.pcm.service.consent.ConsentService;
import gov.samhsa.pcm.service.dto.AbstractPdfDto;
import gov.samhsa.pcm.service.dto.AddConsentFieldsDto;
import gov.samhsa.pcm.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.pcm.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.pcm.service.dto.AdminCreatePatientResponseDto;
import gov.samhsa.pcm.service.dto.AdminProfileDto;
import gov.samhsa.pcm.service.dto.BasicPatientAccountDto;
import gov.samhsa.pcm.service.dto.ConsentDto;
import gov.samhsa.pcm.service.dto.ConsentListDto;
import gov.samhsa.pcm.service.dto.ConsentPdfDto;
import gov.samhsa.pcm.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.pcm.service.dto.ConsentValidationDto;
import gov.samhsa.pcm.service.dto.IndividualProviderDto;
import gov.samhsa.pcm.service.dto.OrganizationalProviderDto;
import gov.samhsa.pcm.service.dto.PatientAdminDto;
import gov.samhsa.pcm.service.dto.PatientConnectionDto;
import gov.samhsa.pcm.service.dto.PatientProfileDto;
import gov.samhsa.pcm.service.dto.RecentAcctivityDto;
import gov.samhsa.pcm.service.dto.SpecificMedicalInfoDto;
import gov.samhsa.pcm.service.dto.SystemNotificationDto;
import gov.samhsa.pcm.service.patient.PatientNotFoundException;
import gov.samhsa.pcm.service.patient.PatientService;
import gov.samhsa.pcm.service.provider.HashMapResultToProviderDtoConverter;
import gov.samhsa.pcm.service.provider.IndividualProviderService;
import gov.samhsa.pcm.service.provider.OrganizationalProviderService;
import gov.samhsa.pcm.service.provider.ProviderSearchLookupService;
import gov.samhsa.pcm.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.pcm.service.reference.ClinicalDocumentTypeCodeService;
import gov.samhsa.pcm.service.reference.LanguageCodeService;
import gov.samhsa.pcm.service.reference.MaritalStatusCodeService;
import gov.samhsa.pcm.service.reference.PurposeOfUseCodeService;
import gov.samhsa.pcm.service.reference.RaceCodeService;
import gov.samhsa.pcm.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.pcm.service.reference.pg.StateCodeServicePg;
import gov.samhsa.pcm.service.spirit.SpiritClientNotAvailableException;
import gov.samhsa.pcm.service.systemnotification.SystemNotificationService;
import gov.samhsa.pcm.service.validator.FieldValidator;
import gov.samhsa.pcm.service.valueset.MedicalSectionService;
import gov.samhsa.pcm.service.valueset.ValueSetCategoryService;
import gov.samhsa.pcm.web.AjaxException;
import gov.samhsa.spirit.wsclient.adapter.SpiritConstants;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The Class AdminController.
 */
@Controller
@RequestMapping("/Administrator")
public class AdminController extends AbstractController {

	/** The patient service. */
	@Autowired
	PatientService patientService;

	/** The admin service. */
	@Autowired
	AdminService adminService;

	/** The consent service. */
	@Autowired
	ConsentService consentService;

	/** The user context. */
	@Autowired
	UserContext adminUserContext;

	/** The patient audit service. */
	@Autowired
	AdminAuditService adminAuditService;

	/** The system notification service. */
	@Autowired
	SystemNotificationService systemNotificationService;

	/** The individual provider service. */
	@Autowired
	IndividualProviderService individualProviderService;

	/** The organizational provider service. */
	@Autowired
	OrganizationalProviderService organizationalProviderService;

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

	/** The clinical document section type code service. */
	@Autowired
	private MedicalSectionService medicalSectionServiceImpl;

	/** The clinical document type code service. */
	@Autowired
	private ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;

	/** The hippa space coded concept lookup service. */
	@Autowired
	private CodedConceptLookupService hippaSpaceCodedConceptLookupService;

	/** The purpose of use code service. */
	@Autowired
	private PurposeOfUseCodeService purposeOfUseCodeService;

	/** The value set category service. */
	@Autowired
	private ValueSetCategoryService valueSetCategoryService;

	/** The field validator. */
	@Autowired
	private FieldValidator fieldValidator;

	/** The provider search lookup service. */
	@Autowired
	private ProviderSearchLookupService providerSearchLookupService;

	/** The consent helper. */
	@Autowired
	private ConsentHelper consentHelper;

	/** The event service. */
	@Autowired
	private EventService eventService;

	/** The maximum number of recent patient. */
	int maximumNumberOfRecentPatient = 5;

	/** The pix service. */
	@Autowired
	private PixService pixService;

	/** The user context. */
	@Autowired
	private UserContext userContext;

	/** The hash map result to provider dto converter. */
	@Autowired
	HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Constant NPI_LENGTH. */
	public static final int NPI_LENGTH = 10;

	/**
	 * Admin Home Page.
	 *
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @param basicPatientAccountDto
	 *            the basic patient account dto
	 * @return the string
	 */
	@RequestMapping(value = "adminHome.html")
	public String adminHome(Model model, HttpServletRequest request,
			BasicPatientAccountDto basicPatientAccountDto) {
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		String notify = request.getParameter("notify");

		List<RecentAcctivityDto> recentActivityDtos = adminAuditService
				.findAdminHistoryByUsername(currentUser.getUsername());
		AdminProfileDto adminProfileDto = adminService
				.findAdminProfileByUsername(currentUser.getUsername());
		model.addAttribute("adminProfileDto", adminProfileDto);
		model.addAttribute("administrativeGenderCodes",
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes());

		model.addAttribute("notifyevent", notify);
		model.addAttribute("recentActivityDtos", recentActivityDtos);

		return "views/Administrator/adminHome";
	}

	/**
	 * Admin Patient View Page.
	 *
	 * @param model
	 *            the model
	 * @param patientId
	 *            the patient id
	 * @param userName
	 *            the user name
	 * @param notify
	 *            the notify
	 * @param status
	 *            the status
	 * @param duplicateConsent
	 *            the duplicate consent
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientView.html")
	public String adminPatientViewByPatientId(
			Model model,
			@RequestParam(value = "id", required = false, defaultValue = "-1") long patientId,
			@RequestParam(value = "username", required = false) String userName,
			@RequestParam(value = "notify", required = false) String notify,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "duplicateconsent", required = false) String duplicateConsent) {
		PatientProfileDto patientProfileDto;
		List<ConsentListDto> consentListDto;
		List<SystemNotificationDto> systemNotificationDtos = null;
		PatientConnectionDto patientConnectionDto = null;
		if (patientId != -1) {
			patientProfileDto = patientService.findPatient(patientId);
			consentListDto = consentService
					.findAllConsentsDtoByPatient(patientId);
			patientConnectionDto = patientService
					.findPatientConnectionById(patientId);
			systemNotificationDtos = systemNotificationService
					.findAllSystemNotificationDtosByPatient(patientId);

			if (duplicateConsent != null) {
				model.addAttribute("duplicate_consent_id", duplicateConsent);
			}

		} else if (userName != null) {
			patientProfileDto = patientService.findByUsername(userName);
			consentListDto = consentService
					.findAllConsentsDtoByUserName(userName);
		} else {
			return "redirect:adminHome.html";
		}
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		AdminProfileDto adminProfileDto = adminService
				.findAdminProfileByUsername(currentUser.getUsername());
		Set<String> npiList = new HashSet<String>();
		for (IndividualProviderDto individualProviderDto : patientConnectionDto
				.getIndividualProviders()) {
			npiList.add(individualProviderDto.getNpi());
		}
		for (OrganizationalProviderDto organizationalProviderDto : patientConnectionDto
				.getOrganizationalProviders()) {
			npiList.add(organizationalProviderDto.getNpi());
		}
		model.addAttribute("npiList", npiList);
		model.addAttribute("adminProfileDto", adminProfileDto);
		model.addAttribute("individualProviders",
				patientConnectionDto.getIndividualProviders());
		model.addAttribute("organizationalProviders",
				patientConnectionDto.getOrganizationalProviders());
		model.addAttribute("patientProfileDto", patientProfileDto);
		model.addAttribute("consentListDto", consentListDto);
		model.addAttribute("systemNotificationDtos", systemNotificationDtos);
		model.addAttribute("notifyEvent", notify);
		model.addAttribute("statusEvent", status);
		populateLookupCodes(model);

		return "views/Administrator/adminPatientView";
	}

	/**
	 * Submit Consent.
	 *
	 * @param consentId
	 *            the consent id
	 * @param patientId
	 *            the patient id
	 * @return string
	 */
	@RequestMapping(value = "adminPatientViewSubmitConsent.html", method = RequestMethod.POST)
	public String adminPatientViewSubmitConsent(
			@RequestParam(value = "consentId") long consentId,
			@RequestParam(value = "patientId") long patientId) {

		ConsentPdfDto consentPdfDto = consentService
				.findConsentPdfDto(consentId);
		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			if (consentService.signConsent(consentPdfDto) == true)
				;
			return "redirect:adminPatientView.html?notify=submit&status=success&id="
					+ patientId;
		} else {
			logger.warn("Unable to submit consent...");
			logger.warn("...consentService.signConsent(consentPdfDto) did not return true.");
			return "redirect:adminPatientView.html?notify=submit&status=fail&id="
					+ patientId;
		}

	}

	/**
	 * Delete consent.
	 *
	 * @param consentId
	 *            the consent id
	 * @param patientId
	 *            the patient id
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewDeleteConsent", method = RequestMethod.POST)
	public String adminPatientViewDeleteConsent(
			@RequestParam(value = "consentId") long consentId,
			@RequestParam(value = "patientId") long patientId) {

		boolean isDeleteSuccess = false;

		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			isDeleteSuccess = consentService.deleteConsent(consentId);
			return "redirect:adminPatientView.html?notify=delete&status=success&id="
					+ patientId;
		} else {
			logger.warn("Unable to delete consent...");
			logger.warn("...consentService.deleteConsent(consentId) did not return true.");
			return "redirect:adminPatientView.html?notify=delete&status=fail&id="
					+ patientId;
		}

	}

	/**
	 * Admin edit patient profile.
	 *
	 * @param patientProfileDto
	 *            the patient profile dto
	 * @param bindingResult
	 *            the binding result
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "adminEditPatientProfile.html", method = RequestMethod.POST)
	public String adminEditPatientProfile(
			@Valid PatientProfileDto patientProfileDto,
			BindingResult bindingResult, Model model) {
		fieldValidator.validate(patientProfileDto, bindingResult);
		Long patientId = patientProfileDto.getId();

		if (bindingResult.hasErrors()) {

			return "redirect:/Administrator/adminPatientView.html?notify=editpatientprofile&status=fail&id="
					+ patientId;
		} else {

			patientProfileDto
					.setAddressCountryCode(SpiritConstants.C2S_PATIENT_COUNTRY);

			PatientProfileDto updatepatientProfileDto = patientService
					.findPatient(patientId);
			patientProfileDto
					.setUsername(updatepatientProfileDto.getUsername());
			patientProfileDto.setEnterpriseIdentifier(updatepatientProfileDto
					.getEnterpriseIdentifier());
			patientProfileDto.setMedicalRecordNumber(updatepatientProfileDto
					.getMedicalRecordNumber());

			try {
				adminService.updatePatient(patientProfileDto);
			} catch (SpiritClientNotAvailableException e) {
				logger.error("Spirit Client not available");

				return "redirect:/Administrator/adminPatientView.html?notify=editpatientprofile&status=fail&id="
						+ patientId;
			}

			return "redirect:/Administrator/adminPatientView.html?notify=editpatientprofile&status=success&id="
					+ patientId;
		}

	}

	/**
	 * Admin Patient View Create Consent Page.
	 *
	 * @param patientId
	 *            the patient id
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewCreateConsent.html")
	public String adminPatientViewCreateConsent(
			@RequestParam(value = "id", defaultValue = "-1") long patientId,
			Model model) {
		if (patientId <= -1) {
			throw new IllegalArgumentException(
					"Invalid id passed in query string to adminPatientViewCreateConsent.html");
		} else {
			PatientProfileDto currentPatient = patientService
					.findPatient(patientId);

			if (currentPatient == null) {
				throw new PatientNotFoundException("Patient not found by id");
			}

			List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
					.findAddConsentIndividualProviderDtoByPatientId(patientId);

			List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
					.findAddConsentOrganizationalProviderDtoByPatientId(patientId);

			ConsentDto consentDto = consentService.makeConsentDto();

			consentDto.setUsername(currentPatient.getUsername());

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar today = Calendar.getInstance();
			Calendar oneYearFromNow = Calendar.getInstance();
			oneYearFromNow.add(Calendar.YEAR, 1);

			List<AddConsentFieldsDto> sensitivityPolicyDto = valueSetCategoryService
					.findAllValueSetCategoriesAddConsentFieldsDto();
			List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
					.findAllPurposeOfUseCodesAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = medicalSectionServiceImpl
					.findAllMedicalSectionsAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentTypeDto = clinicalDocumentTypeCodeService
					.findAllClinicalDocumentTypeCodesAddConsentFieldsDto();

			populateLookupCodes(model);
			AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
			AdminProfileDto adminProfileDto = adminService
					.findAdminProfileByUsername(currentUser.getUsername());
			model.addAttribute("adminProfileDto", adminProfileDto);

			model.addAttribute("defaultStartDate",
					dateFormat.format(today.getTime()));
			model.addAttribute("defaultEndDate",
					dateFormat.format(oneYearFromNow.getTime()));
			model.addAttribute("patientId", currentPatient.getId());
			model.addAttribute("patient_lname", currentPatient.getLastName());
			model.addAttribute("patient_fname", currentPatient.getFirstName());
			model.addAttribute("consentDto", consentDto);
			model.addAttribute("individualProvidersDto", individualProvidersDto);
			model.addAttribute("clinicalDocumentSectionType",
					clinicalDocumentSectionTypeDto);
			model.addAttribute("clinicalDocumentType", clinicalDocumentTypeDto);
			model.addAttribute("sensitivityPolicy", sensitivityPolicyDto);
			model.addAttribute("purposeOfUse", purposeOfUseDto);
			model.addAttribute("organizationalProvidersDto",
					organizationalProvidersDto);
			model.addAttribute("addConsent", true);
			model.addAttribute("isProviderAdminUser", true);
			return "views/Administrator/adminPatientViewCreateConsent";
		}
	}

	/**
	 * Admin patient view create consent.
	 *
	 * @param consentDto
	 *            the consent dto
	 * @param patientId
	 *            the patient id
	 * @param bindingResult
	 *            the binding result
	 * @param model
	 *            the model
	 * @param icd9
	 *            the icd9
	 * @param isAddConsent
	 *            the is add consent
	 * @return the string
	 * @throws ConsentGenException
	 *             the consent gen exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws JSONException
	 *             the JSON exception
	 */
	@RequestMapping(value = "adminPatientViewCreateConsent.html", method = RequestMethod.POST)
	public @ResponseBody String adminPatientViewCreateConsent(
			@Valid ConsentDto consentDto,
			@RequestParam("patientId") long patientId,
			BindingResult bindingResult,
			Model model,
			@RequestParam(value = "ICD9", required = false) HashSet<String> icd9,
			@RequestParam(value = "isAddConsent") boolean isAddConsent)
			throws ConsentGenException, IOException, JSONException {

		Set<String> isMadeTo = new HashSet<String>();
		Set<String> isMadeFrom = new HashSet<String>();
		consentDto.setConsentEnd(consentHelper.setDateAsEndOfDay(consentDto
				.getConsentEnd()));

		if (consentDto.getOrganizationalProvidersDisclosureIsMadeTo() != null)
			isMadeTo.addAll(consentDto
					.getOrganizationalProvidersDisclosureIsMadeTo());
		if (consentDto.getProvidersDisclosureIsMadeTo() != null)
			isMadeTo.addAll(consentDto.getProvidersDisclosureIsMadeTo());
		if (consentDto.getOrganizationalProvidersPermittedToDisclose() != null)
			isMadeFrom.addAll(consentDto
					.getOrganizationalProvidersPermittedToDisclose());
		if (consentDto.getProvidersPermittedToDisclose() != null)
			isMadeFrom.addAll(consentDto.getProvidersPermittedToDisclose());
		if ((!isMadeTo.isEmpty())
				&& (!isMadeFrom.isEmpty())
				&& consentService.areThereDuplicatesInTwoSets(isMadeTo,
						isMadeFrom) == false) {

			// one to one policy validation
			if (isMadeTo.size() != 1 || isMadeFrom.size() != 1) {
				throw new AjaxException(HttpStatus.UNPROCESSABLE_ENTITY,
						"Only one provider can select from the list");
			}

			Set<SpecificMedicalInfoDto> doNotShareClinicalConceptCodes = new HashSet<SpecificMedicalInfoDto>();
			if (icd9 != null)
				doNotShareClinicalConceptCodes = consentHelper
						.getDoNotShareClinicalConceptCodes(icd9);
			consentDto
					.setDoNotShareClinicalConceptCodes(doNotShareClinicalConceptCodes);

			Object obj = null;
			try {
				obj = consentService.saveConsent(consentDto, patientId);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Failed to save the consent, please try again later.");
			}

			if (null != obj && obj instanceof ConsentValidationDto) {
				// duplicate policy found
				ObjectMapper mapper = new ObjectMapper();
				throw new AjaxException(HttpStatus.CONFLICT,
						mapper.writeValueAsString(obj));
			}
			JSONObject succObj = new JSONObject();
			succObj.put("isSuccess", true);
			succObj.put("isAdmin", true);
			succObj.put("patientId", patientId);
			if (isAddConsent == false) {
				succObj.put("isAdd", false);
			} else {
				succObj.put("isAdd", true);
			}
			return succObj.toString();
		} else {
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Resource Not Found");
		}

	}

	/**
	 * Sign consent revokation.
	 *
	 * @param consentId
	 *            the consent id
	 * @param revokationType
	 *            the revokation type
	 * @param patientId
	 *            the patient id
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewRevokeConsent.html", method = RequestMethod.POST)
	public String signConsentRevokation(
			@RequestParam("consentId") long consentId,
			@RequestParam("revokationType") String revokationType,
			@RequestParam("patientId") long patientId) {

		consentService.addUnsignedConsentRevokationPdf(consentId,
				revokationType);
		ConsentRevokationPdfDto consentRevokationPdfDto = consentService
				.findConsentRevokationPdfDto(consentId);
		consentRevokationPdfDto.setRevokationType(revokationType);

		if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
			consentService.signConsentRevokation(consentRevokationPdfDto);
			return "redirect:adminPatientView.html?notify=revokepatientconsent&status=success&id="
					+ patientId;
		}
		return "redirect:adminPatientView.html?notify=revokepatientconsent&status=fail&id="
				+ patientId;
	}

	/**
	 * Admin Patient View Edit Consent Page.
	 *
	 * @param consentId
	 *            the consent id
	 * @param patientId
	 *            the patient id
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewEditConsent.html")
	public String adminPatientViewEditConsent(
			@RequestParam(value = "consentId", defaultValue = "-1") long consentId,
			@RequestParam("patientId") long patientId, Model model) {
		if (consentId <= -1) {
			throw new IllegalArgumentException(
					"Invalid id passed in query string to adminPatientViewEditConsent.html");
		} else {
			ConsentDto consentDto = consentService.findConsentById(consentId);

			if (consentDto == null) {
				throw new ConsentNotFoundException("Consent not found by id");
			}

			PatientProfileDto currentPatient = patientService
					.findPatient(patientId);

			if (currentPatient == null) {
				throw new PatientNotFoundException(
						"Patient not found by username");
			}

			List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
					.findAddConsentIndividualProviderDtoByPatientId(patientId);

			List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
					.findAddConsentOrganizationalProviderDtoByPatientId(patientId);

			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Calendar today = Calendar.getInstance();
			Calendar oneYearFromNow = Calendar.getInstance();
			oneYearFromNow.add(Calendar.YEAR, 1);

			List<AddConsentFieldsDto> sensitivityPolicyDto = valueSetCategoryService
					.findAllValueSetCategoriesAddConsentFieldsDto();
			List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
					.findAllPurposeOfUseCodesAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = medicalSectionServiceImpl
					.findAllMedicalSectionsAddConsentFieldsDto();
			List<AddConsentFieldsDto> clinicalDocumentTypeDto = clinicalDocumentTypeCodeService
					.findAllClinicalDocumentTypeCodesAddConsentFieldsDto();

			populateLookupCodes(model);
			AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
			AdminProfileDto adminProfileDto = adminService
					.findAdminProfileByUsername(currentUser.getUsername());
			model.addAttribute("adminProfileDto", adminProfileDto);

			model.addAttribute("defaultStartDate",
					dateFormat.format(today.getTime()));
			model.addAttribute("defaultEndDate",
					dateFormat.format(oneYearFromNow.getTime()));
			model.addAttribute("patientId", currentPatient.getId());
			model.addAttribute("patient_lname", currentPatient.getLastName());
			model.addAttribute("patient_fname", currentPatient.getFirstName());
			model.addAttribute("consentDto", consentDto);
			model.addAttribute("individualProvidersDto", individualProvidersDto);
			model.addAttribute("clinicalDocumentSectionType",
					clinicalDocumentSectionTypeDto);
			model.addAttribute("clinicalDocumentType", clinicalDocumentTypeDto);
			model.addAttribute("sensitivityPolicy", sensitivityPolicyDto);
			model.addAttribute("purposeOfUse", purposeOfUseDto);
			model.addAttribute("organizationalProvidersDto",
					organizationalProvidersDto);
			model.addAttribute("addConsent", false);
			model.addAttribute("isProviderAdminUser", true);
			return "views/Administrator/adminPatientViewCreateConsent";
		}
	}

	/**
	 * Edits the admin profile.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "editAdminProfile.html")
	public String editAdminProfile(Model model) {
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		AdminProfileDto adminProfileDto = adminService
				.findAdminProfileByUsername(currentUser.getUsername());
		model.addAttribute("adminProfileDto", adminProfileDto);
		model.addAttribute("currentUser", currentUser);

		populateLookupCodes(model);

		return "views/Administrator/editAdminProfile";
	}

	@RequestMapping(value = "adminReports.html")
	public String adminReports(Model model) {
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		AdminProfileDto adminProfileDto = adminService
				.findAdminProfileByUsername(currentUser.getUsername());
		model.addAttribute("adminProfileDto", adminProfileDto);
		return "views/Administrator/adminReports";
	}

	/**
	 * Profile.
	 *
	 * @param adminProfileDto
	 *            the admin profile dto
	 * @param bindingResult
	 *            the binding result
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "editAdminProfile.html", method = RequestMethod.POST)
	public String profile(@Valid AdminProfileDto adminProfileDto,
			BindingResult bindingResult, Model model) {

		fieldValidator.validate(adminProfileDto, bindingResult);

		if (bindingResult.hasErrors()) {

			populateLookupCodes(model);
			return "views/Administrator/editAdminProfile";
		} else {
			AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
			model.addAttribute("currentUser", currentUser);
			try {
				adminService.updateAdministrator(adminProfileDto);
				model.addAttribute("updatedMessage",
						"Updated your profile successfully!");
			} catch (AuthenticationFailedException e) {
				model.addAttribute("updatedMessage",
						"Failed. Please check your username and password and try again.");
				AdminProfileDto originalAdminProfileDto = adminService
						.findAdminProfileByUsername(currentUser.getUsername());
				model.addAttribute("adminProfileDto", originalAdminProfileDto);
			}

			populateLookupCodes(model);

			return "views/Administrator/editAdminProfile";
		}
	}

	/**
	 * Download consent pdf file.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param consentId
	 *            the consent id
	 * @return the string
	 */
	@RequestMapping(value = "/downloadPdf.html", method = RequestMethod.GET)
	public String downloadConsentPdfFile(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") long consentId) {
		AbstractPdfDto pdfDto = consentService.findConsentContentDto(consentId);

		try {
			OutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			IOUtils.copy(new ByteArrayInputStream(pdfDto.getContent()), out);
			out.flush();
			out.close();
			eventService.raiseSecurityEvent(new FileDownloadedEvent(request
					.getRemoteAddr(), "Admin_User_"
					+ userContext.getCurrentUser().getUsername(), "Consent_"
					+ consentId));

		} catch (IOException e) {
			logger.warn("Error while reading pdf file.");
			logger.warn("The exception is: ", e);
		}

		return null;
	}

	/**
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO PROCESS THE ADMIN CREATE
	 * PATIENT ACCOUNT FORM WHEN IT IS SUBMITTED. THIS FUNCTION MUST BE MODIFIED
	 * BEFORE IT IS INTEGREATED WITH THE BACK-END CODE.
	 *
	 * @param basicPatientAccountDto
	 *            the basic patient account dto
	 * @param result
	 *            the result
	 * @param redirectAttributes
	 *            the redirect attributes
	 * @param model
	 *            the model
	 * @return the string
	 * @throws PatientExistingException
	 *             the patient existing exception
	 */
	@RequestMapping(value = "adminCreatePatientAccount.html", method = RequestMethod.POST)
	public String adminCreatePatientAccount(
			@Valid BasicPatientAccountDto basicPatientAccountDto,
			BindingResult result, RedirectAttributes redirectAttributes,
			Model model) throws PatientExistingException {

		fieldValidator.validate(basicPatientAccountDto, result);

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("basicPatientAccountDto",
					basicPatientAccountDto);
			redirectAttributes.addFlashAttribute("notify",
					"createPatientFailed");
			return "redirect:/Administrator/adminHome.html";
		}

		try {
			AdminCreatePatientResponseDto response = adminService
					.createPatientAccount(basicPatientAccountDto);
			return "redirect:/Administrator/adminPatientView.html?id="
					+ response.getPatientId() + "&status="
					+ response.getMessage();
		} catch (SpiritClientNotAvailableException e) {
			redirectAttributes.addFlashAttribute("basicPatientAccountDto",
					basicPatientAccountDto);
			redirectAttributes.addFlashAttribute("notify",
					"createPatientFailed");
			return "redirect:/Administrator/adminHome.html";
		}
	}

	/**
	 * Admin send login info email.
	 *
	 * @param patientId
	 *            the patient id
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "sendLoginInformation.html", method = RequestMethod.GET)
	public @ResponseBody String adminSendLoginInfoEmail(
			@RequestParam("patientId") long patientId,
			HttpServletRequest request) {
		try {
			String linkUrl = getServletUrl(request);
			adminService.sendLoginInformationEmail(patientId, linkUrl);
			return "Login information has been sent to email.";
		} catch (EmailAddressNotExistException e) {
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Email Address is not exist.");

		} catch (MessagingException e) {
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Message is not available, please click later");

		}
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
	 * Gets the patient by first and last name.
	 *
	 * @param token
	 *            the token
	 * @return the by first and last name
	 */
	@RequestMapping("/patientlookup/query")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<PatientAdminDto> getByFirstAndLastName(
			@RequestParam(value = "token", required = true) String token) {
		String[] tokens = token.split("\\s*(=>|,|\\s)\\s*");
		return patientService.findAllPatientByFirstNameAndLastName(tokens);
	}

	/**
	 * Search for a provider.
	 *
	 * @param npi
	 *            the npi
	 * @param patientUserName
	 *            the patient user name
	 * @param patientId
	 *            the patient id
	 * @return the string
	 */
	/*
	 * TODO: Remove patientusername from list of @RequestParam's, and from all
	 * calls to this function from other files
	 */
	@RequestMapping(value = "connectionProviderAdd.html", method = RequestMethod.POST)
	public @ResponseBody String addProvider(
			@RequestParam("npi") String npi,
			@RequestParam(value = "patientusername", defaultValue = "") String patientUserName,
			@RequestParam("patientId") String patientId) {

		OrganizationalProvider organizationalProviderReturned = null;
		IndividualProvider individualProviderReturned = null;
		boolean isOrgProvider = false;

		try {
			patientUserName = patientService.findUsernameById(Long.valueOf(
					patientId).longValue());
		} catch (IllegalArgumentException e) {
			/*
			 * this catches the exception thrown by Long.valueOf() in case the
			 * input patientId string value cannot be converted to a long type,
			 * and then throws an AjaxException to trigger the 400 HTTP Status
			 * Code error to be returned to the client-side Ajax listener
			 */
			throw new AjaxException(
					HttpStatus.BAD_REQUEST,
					"Unable to add this new provider because the request parameters contained invalid data.");
		}

		if (npi.length() == NPI_LENGTH && npi.matches("[0-9]+")) {
			String qureryResult = providerSearchLookupService
					.providerSearchByNpi(npi);

			HashMap<String, String> result = deserializeResult(qureryResult);

			if ((EntityType.valueOf(result.get("entityType")) == EntityType.Organization)) {
				isOrgProvider = true;
				OrganizationalProviderDto providerDto = new OrganizationalProviderDto();
				hashMapResultToProviderDtoConverter.setProviderDto(providerDto,
						result);
				providerDto.setOrgName(result.get("providerOrganizationName"));
				providerDto.setAuthorizedOfficialLastName(result
						.get("authorizedOfficialLastName"));
				providerDto.setAuthorizedOfficialFirstName(result
						.get("authorizedOfficialFirstName"));
				providerDto.setAuthorizedOfficialTitle(result
						.get("authorizedOfficialTitleorPosition"));
				providerDto.setAuthorizedOfficialNamePrefix(result
						.get("authorizedOfficialNamePrefixText"));
				providerDto.setAuthorizedOfficialTelephoneNumber(result
						.get("authorizedOfficialTelephoneNumber"));
				providerDto.setUsername(patientUserName);
				providerDto.setPatientId(patientId);

				organizationalProviderReturned = organizationalProviderService
						.addNewOrganizationalProvider(providerDto);
			} else {
				isOrgProvider = false;
				IndividualProviderDto providerDto = new IndividualProviderDto();
				hashMapResultToProviderDtoConverter.setProviderDto(providerDto,
						result);
				providerDto.setFirstName(result.get("providerFirstName"));
				providerDto.setMiddleName(result.get("providerMiddleName"));
				providerDto.setLastName(result.get("providerLastName"));
				providerDto.setNamePrefix(result.get("providerNamePrefixText"));
				providerDto.setNameSuffix(result.get("providerNameSuffixText"));
				providerDto.setCredential(result.get("providerCredentialText"));
				providerDto.setUsername(patientUserName);
				providerDto.setPatientId(patientId);

				individualProviderReturned = individualProviderService
						.addNewIndividualProvider(providerDto);
			}
		}

		if (isOrgProvider == true) {
			if (organizationalProviderReturned != null) {
				return organizationalProviderReturned.getId().toString();
			} else {
				throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Unable to add this new provider because this provider already exists.");
			}
		} else {
			if (individualProviderReturned != null) {
				return individualProviderReturned.getId().toString();
			} else {
				throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Unable to add this new provider because this provider already exists.");
			}
		}
	}

	/**
	 * Delete Individual Provider.
	 *
	 * @param individualProviderid
	 *            the individual providerid
	 * @param patientId
	 *            the patient id
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/deleteIndividualProvider", method = RequestMethod.POST)
	public @ResponseBody String deleteIndividualProvider(
			@RequestParam("individualProviderid") long individualProviderid,
			@RequestParam("patientId") String patientId, Model model) {

		PatientConnectionDto patientConnectionDto = null;
		IndividualProviderDto individualProviderDto = null;

		try {
			patientConnectionDto = patientService
					.findPatientConnectionById(Long.valueOf(patientId)
							.longValue());
		} catch (Exception e) {
			logger.error("Unable to delete individual provider: PatientConnectionDto could not be found for specified patientId...");
			logger.error("...STACK TRACE: ", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ERROR: Unable to delete this provider.");
		}

		Set<IndividualProviderDto> individualProviderDtos = patientConnectionDto
				.getIndividualProviders();

		for (IndividualProviderDto cur_individualProviderDto : individualProviderDtos) {
			if (Long.toString(individualProviderid).compareTo(
					cur_individualProviderDto.getId()) == 0) {
				individualProviderDto = cur_individualProviderDto;
				individualProviderDto.setPatientId(patientId);
				break;
			}
		}

		if (individualProviderDto == null) {
			logger.error("Unable to delete individual provider: IndividualProviderDto could not be found in PatientConnectionDto for specified individualProviderId.");
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ERROR: Unable to delete this provider.");
		}

		if (individualProviderDto.isDeletable() == false) {
			logger.info("Unable to delete specifed individual provider from patient account because it is currently used in a consent for that patient.");
			throw new AjaxException(
					HttpStatus.CONFLICT,
					"Unable to delete this provider because it is currently used in one or more of your consents.");
		}

		try {
			individualProviderService
					.deleteIndividualProviderDtoByPatientId(individualProviderDto);
		} catch (Exception e) {
			logger.error("Unable to delete individual provider...");
			logger.error("...STACK TRACE", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ERROR: Unable to delete this provider.");
		}

		return "Success";
	}

	/**
	 * Delete organizational provider.
	 *
	 * @param organizationalProviderid
	 *            the organizational providerid
	 * @param patientId
	 *            the patient id
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/deleteOrganizationalProvider", method = RequestMethod.POST)
	public @ResponseBody String deleteOrganizationalProvider(
			@RequestParam("organizationalProviderid") long organizationalProviderid,
			@RequestParam("patientId") String patientId, Model model) {

		PatientConnectionDto patientConnectionDto = null;
		OrganizationalProviderDto organizationalProviderDto = null;

		try {
			patientConnectionDto = patientService
					.findPatientConnectionById(Long.valueOf(patientId)
							.longValue());
		} catch (Exception e) {
			logger.error("Unable to delete organazational provider: PatientConnectionDto could not be found for specified patientId...");
			logger.error("...STACK TRACE: ", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ERROR: Unable to delete this provider.");
		}

		Set<OrganizationalProviderDto> organizationalProviderDtos = patientConnectionDto
				.getOrganizationalProviders();

		for (OrganizationalProviderDto cur_organizationalProviderDto : organizationalProviderDtos) {
			if (Long.toString(organizationalProviderid).compareTo(
					cur_organizationalProviderDto.getId()) == 0) {
				organizationalProviderDto = cur_organizationalProviderDto;
				organizationalProviderDto.setPatientId(patientId);
				break;
			}
		}

		if (organizationalProviderDto == null) {
			logger.error("Unable to delete organazational provider: OrganizationalProviderDto could not be found in PatientConnectionDto for specified organizationalProviderId.");
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ERROR: Unable to delete this provider.");
		}

		if (organizationalProviderDto.isDeletable() == false) {
			logger.info("Unable to delete specifed organizational provider from patient account because it is currently used in a consent for that patient.");
			throw new AjaxException(
					HttpStatus.CONFLICT,
					"Unable to delete this provider because it is currently used in one or more of your consents.");
		}

		try {
			organizationalProviderService
					.deleteOrganizationalProviderDtoByPatientId(organizationalProviderDto);
		} catch (Exception e) {
			logger.error("Unable to delete organazational provider...");
			logger.error("...STACK TRACE", e);
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ERROR: Unable to delete this provider.");
		}

		return "Success";

	}

	/**
	 * AJAX search for a provider.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "providerSearch.html", method = RequestMethod.GET)
	public String ajaxProviderSearch(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setHeader("Content-Type", "application/json");
			OutputStream out = response.getOutputStream();
			String usstate = request.getParameter("usstate");
			String city = request.getParameter("city");
			String zipcode = request.getParameter("zipcode");
			String gender = request.getParameter("gender");
			String specialty = request.getParameter("specialty");
			String phone = request.getParameter("phone");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String orgname = request.getParameter("facilityname");
			int pageNumber = Integer.parseInt(request
					.getParameter("pageNumber"));

			if (providerSearchLookupService.isValidatedSearch(usstate, city,
					zipcode, gender, specialty, phone, firstname, lastname,
					orgname) == true) {
				IOUtils.copy(
						new ByteArrayInputStream(providerSearchLookupService
								.providerSearch(usstate, city, zipcode, gender,
										specialty, phone, firstname, lastname,
										orgname, pageNumber).getBytes()), out);
				out.flush();
				out.close();
			}

		} catch (IOException e) {
			logger.error(
					"Error when calling provider search. The exception is:", e);
		}

		return null;

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

		// Fix issue #529 start
		// the search for state only display MD, DC and Virginia states
		model.addAttribute("stateCodes",
				stateCodeService.findByMDAndDCAndVAStates());
		// Fix issue #529 end

		model.addAttribute("stateCodes_Edit",
				stateCodeService.findAllStateCodes());
	}

	/**
	 * Deserialize result.
	 *
	 * @param providerDtoJSON
	 *            the provider dto json
	 * @return the hash map
	 */
	public HashMap<String, String> deserializeResult(String providerDtoJSON) {
		return new JSONDeserializer<HashMap<String, String>>()
				.deserialize(providerDtoJSON);
	}

	/**
	 * Gets the remote address.
	 *
	 * @param request
	 *            the request
	 * @return the remote address
	 */
	String getRemoteAddress(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

}
