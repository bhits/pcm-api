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

import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent2share.common.AuthenticatedUser;
import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.domain.clinicaldata.ClinicalDocument;
import gov.samhsa.consent2share.infrastructure.CodedConceptLookupService;
import gov.samhsa.consent2share.infrastructure.eventlistener.EventService;
import gov.samhsa.consent2share.infrastructure.security.AccessReferenceMapper;
import gov.samhsa.consent2share.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService;
import gov.samhsa.consent2share.service.consent.ConsentCheckService;
import gov.samhsa.consent2share.service.consent.ConsentHelper;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.consent2share.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.ClinicalDocumentDto;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentValidationDto;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;
import gov.samhsa.consent2share.service.dto.OrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.PatientConnectionDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.SpecificMedicalInfoDto;
import gov.samhsa.consent2share.service.dto.TryMyPolicyDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientNotFoundException;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentTypeCodeService;
import gov.samhsa.consent2share.service.reference.PurposeOfUseCodeService;
import gov.samhsa.consent2share.service.reference.pg.StateCodeServicePg;
import gov.samhsa.consent2share.service.valueset.MedicalSectionService;
import gov.samhsa.consent2share.service.valueset.ValueSetCategoryService;
import gov.samhsa.consent2share.web.AjaxException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.google.common.io.ByteStreams;

/**
 * The Class ConsentController.
 */
@Controller
@RequestMapping("/consents")
public class ConsentController extends AbstractController {

	public final static Long SAMPLE_C32_ID = new Long(-1);

	/** The consent service. */
	@Autowired
	private ConsentService consentService;

	/** The patient service. */
	@Autowired
	private PatientService patientService;

	/** The clinical document section type code service. */
	@Autowired
	private MedicalSectionService medicalSectionServiceImpl;

	/** The clinical document type code service. */
	@Autowired
	private ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;

	/** The clinical document type code service. */
	@Autowired
	private ClinicalDocumentService clinicalDocumentService;

	/** The hippa space coded concept lookup service. */
	@Autowired
	private CodedConceptLookupService hippaSpaceCodedConceptLookupService;

	/** The purpose of use code service. */
	@Autowired
	private PurposeOfUseCodeService purposeOfUseCodeService;

	@Autowired
	NotificationService notificationService;

	/** The value set category service. */
	@Autowired
	private ValueSetCategoryService valueSetCategoryService;

	/** The administrative gender code service. */
	@Autowired
	private AdministrativeGenderCodeService administrativeGenderCodeService;

	/** The state code service. */
	@Autowired
	private StateCodeServicePg stateCodeService;

	/** The user context. */
	@Autowired
	private UserContext userContext;

	/** The consent export service. */
	@Autowired
	private ConsentExportService consentExportService;

	@Autowired
	private AccessReferenceMapper accessReferenceMapper;

	@Autowired
	private ConsentHelper consentHelper;

	@Autowired
	private ConsentCheckService consentCheckService;

	@Autowired
	private EventService eventService;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The C32_ do c_ code. */
	public final String C32_DOC_CODE = "34133-9";

	/**
	 * Consent add.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "addConsent.html")
	public String addConsent(Model model) {
		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		PatientConnectionDto patientConnectionDto = patientService
				.findPatientConnectionByUsername(currentUser.getUsername());
		PatientProfileDto currentPatient = null;

		if (currentUser.getIsProviderAdmin() == false) {
			currentPatient = patientService
					.findPatientProfileByUsername(currentUser.getUsername());

			if (currentPatient == null) {
				throw new PatientNotFoundException(
						"Patient not found by username");
			}

		} else {
			throw new IllegalStateException(
					"ProviderAdmin users cannot access the ConsentController");
		}

		final List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
				.findAddConsentIndividualProviderDtoByUsername(currentPatient
						.getUsername());
		final List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
				.findAddConsentOrganizationalProviderDtoByUsername(currentPatient
						.getUsername());
		final ConsentDto consentDto = consentService.makeConsentDto();

		consentDto.setUsername(currentPatient.getUsername());
		Set<String> npiList = new HashSet<String>();
		for (IndividualProviderDto individualProviderDto : patientConnectionDto
				.getIndividualProviders()) {
			npiList.add(individualProviderDto.getNpi());
		}
		for (OrganizationalProviderDto organizationalProviderDto : patientConnectionDto
				.getOrganizationalProviders()) {
			npiList.add(organizationalProviderDto.getNpi());
		}

		final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		final Calendar today = Calendar.getInstance();
		final Calendar oneYearFromNow = Calendar.getInstance();
		oneYearFromNow.add(Calendar.YEAR, 1);

		populateLookupCodes(model);

		final List<AddConsentFieldsDto> sensitivityPolicyDto = valueSetCategoryService
				.findAllValueSetCategoriesAddConsentFieldsDto();
		final List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
				.findAllPurposeOfUseCodesAddConsentFieldsDto();
		final List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = medicalSectionServiceImpl
				.findAllMedicalSectionsAddConsentFieldsDto();
		final List<AddConsentFieldsDto> clinicalDocumentTypeDto = clinicalDocumentTypeCodeService
				.findAllClinicalDocumentTypeCodesAddConsentFieldsDto();
		
		model.addAttribute("npiList", npiList);
		model.addAttribute("defaultStartDate",
				dateFormat.format(today.getTime()));
		model.addAttribute("defaultEndDate",
				dateFormat.format(oneYearFromNow.getTime()));
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
		model.addAttribute("isProviderAdminUser", false);
		return "views/consents/addConsent";
	}

	@RequestMapping(value = "addConsentCheck.html")
	public String addConsentCheck(Model model) {

		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		PatientProfileDto currentPatient = null;

		if (currentUser.getIsProviderAdmin() == false) {
			currentPatient = patientService
					.findPatientProfileByUsername(currentUser.getUsername());

			if (currentPatient == null) {
				throw new PatientNotFoundException(
						"Patient not found by username");
			}

		} else {
			throw new IllegalStateException(
					"ProviderAdmin users cannot access the ConsentController");
		}

		final List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
				.findAddConsentIndividualProviderDtoByUsername(currentPatient
						.getUsername());
		final List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
				.findAddConsentOrganizationalProviderDtoByUsername(currentPatient
						.getUsername());
		final ConsentDto consentDto = consentService.makeConsentDto();

		consentDto.setUsername(currentPatient.getUsername());

		populateLookupCodes(model);

		final List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
				.findAllPurposeOfUseCodesAddConsentFieldsDto();

		model.addAttribute("patient_lname", currentPatient.getLastName());
		model.addAttribute("patient_fname", currentPatient.getFirstName());
		model.addAttribute("consentDto", consentDto);
		model.addAttribute("individualProvidersDto", individualProvidersDto);

		model.addAttribute("purposeOfUse", purposeOfUseDto);
		model.addAttribute("organizationalProvidersDto",
				organizationalProvidersDto);
		model.addAttribute("addConsent", true);
		model.addAttribute("isProviderAdminUser", false);
		return "views/consents/addConsentCheck";

	}

	@RequestMapping(value = "listConsents.html/checkConsentStatus", method = RequestMethod.GET)
	public void ajaxCheckConsentStatus(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		boolean isSigned = false;
		final String[] consentPreSignStringList = request
				.getParameterValues("consentPreSignList");

		final String[] consentPreRevokeStringList = request
				.getParameterValues("consentPreRevokeList");

		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final List<ConsentListDto> consentListDtos = consentService
				.findAllConsentsDtoByPatient(patientService
						.findIdByUsername(currentUser.getUsername()));
		if (consentPreSignStringList != null) {
			for (final String consentIdString : consentPreSignStringList) {
				final long directconsentId = accessReferenceMapper
						.getDirectReference(consentIdString);
				for (final ConsentListDto consentListDto : consentListDtos) {
					if (consentListDto.getId().equals(
							String.valueOf(directconsentId))
							&& consentListDto.getConsentStage().equals(
									"CONSENT_SIGNED")) {
						isSigned = true;
					}
				}
			}
		}

		if (consentPreRevokeStringList != null) {
			for (final String consentIdString : consentPreRevokeStringList) {
				for (final ConsentListDto consentListDto : consentListDtos) {
					final long directconsentId = accessReferenceMapper
							.getDirectReference(consentIdString);
					if (consentListDto.getId().equals(
							String.valueOf(directconsentId))
							&& consentListDto.getRevokeStage().equals(
									"REVOCATION_REVOKED")) {
						isSigned = true;
					}
				}
			}
		}
		response.setContentType("text/plain;charset=utf-8");
		response.getWriter().println(isSigned);

	}

	/**
	 * Call hippa space.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param domain
	 *            the domain
	 * @param query
	 *            the query
	 * @param dataformat
	 *            the dataformat
	 * @return the string
	 */
	@RequestMapping(value = "/callHippaSpace.html")
	public String callHippaSpace(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("domain") String domain,
			@RequestParam("q") String query,
			@RequestParam("rt") String dataformat) {
		try {
			response.setHeader("Content-Type", "application/json");
			final OutputStream out = response.getOutputStream();
			IOUtils.copy(
					new ByteArrayInputStream(
							hippaSpaceCodedConceptLookupService.searchCodes(
									domain, dataformat, query).getBytes()), out);
			out.flush();
			out.close();

		} catch (final IOException e) {
			logger.warn("Error when calling HipaaSpace. The exception is:", e);
		}

		return null;
	}

	/**
	 * Consent add post.
	 *
	 * @param consentDto
	 *            the consent dto
	 * @param bindingResult
	 *            the binding result
	 * @param model
	 *            the model
	 * @param icd9
	 *            the icd9
	 * @return the string
	 * @throws ConsentGenException
	 * @throws IOException
	 * @throws JSONException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "addConsent.html", method = RequestMethod.POST)
	public @ResponseBody String consentAddPost(@Valid ConsentDto consentDto,
			BindingResult bindingResult, Model model,
			@RequestParam(value = "ICD9", required = false) HashSet<String> icd9)
			throws ConsentGenException, IOException, JSONException {

		if (consentDto.getId() != null) {
			final String directConsentId = String.valueOf(accessReferenceMapper
					.getDirectReference(consentDto.getId()));
			consentDto.setId(directConsentId);
		}

		final Set<String> isMadeTo = new HashSet<String>();
		final Set<String> isMadeFrom = new HashSet<String>();

		// validate consent start date and end date
		if (consentService.validateConsentDate(consentDto.getConsentStart(),
				consentDto.getConsentEnd()) == false) {
			throw new AjaxException(HttpStatus.BAD_REQUEST,
					"Invalid value(s) passed in for one or more date fields.");
		}

		try {
			consentDto.setConsentEnd(consentHelper.setDateAsEndOfDay(consentDto
					.getConsentEnd()));
		} catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
			logger.warn("Exception thrown & caught in consentAddPost() method of ConsentController when calling consentHelper.setDateAsEndOfDay()");
			logger.warn("    Stack Trace: " + e);

			throw new AjaxException(HttpStatus.BAD_REQUEST,
					"Invalid value(s) passed in for one or more date fields.");
		}

		if (consentDto.getOrganizationalProvidersDisclosureIsMadeTo() != null) {
			isMadeTo.addAll(consentDto
					.getOrganizationalProvidersDisclosureIsMadeTo());
		}

		if (consentDto.getProvidersDisclosureIsMadeTo() != null) {
			isMadeTo.addAll(consentDto.getProvidersDisclosureIsMadeTo());
		}

		if (consentDto.getOrganizationalProvidersPermittedToDisclose() != null) {
			isMadeFrom.addAll(consentDto
					.getOrganizationalProvidersPermittedToDisclose());
		}

		if (consentDto.getProvidersPermittedToDisclose() != null) {
			isMadeFrom.addAll(consentDto.getProvidersPermittedToDisclose());
		}

		consentService.areThereDuplicatesInTwoSets(isMadeTo, isMadeFrom);

		if (!isMadeTo.isEmpty()
				&& !isMadeFrom.isEmpty()
				&& consentService.areThereDuplicatesInTwoSets(isMadeTo,
						isMadeFrom) == false) {
			if (consentDto.getSharedPurposeNames() == null) {
				throw new AjaxException(HttpStatus.UNPROCESSABLE_ENTITY,
						"At least one purpose of use needs to be selected.");
			}
			// one to one policy validation
			if (isMadeTo.size() != 1 || isMadeFrom.size() != 1) {
				throw new AjaxException(HttpStatus.UNPROCESSABLE_ENTITY,
						"Only one provider can be selected from the list");
			}

			// Make sure username from consentDto matches a valid patient
			// username

			PatientProfileDto checkMatch = null;

			try {
				checkMatch = patientService
						.findPatientProfileByUsername(consentDto.getUsername());
			} catch (final IllegalArgumentException e) {
				logger.warn("Username from consentDto does not match any valid patient usernames");
				logger.warn("Stack trace: ", e);
				throw new AjaxException(HttpStatus.UNPROCESSABLE_ENTITY,
						"Username from consentDto does not match any valid patient usernames");
			}

			if (checkMatch == null) {
				logger.warn("Username from consentDto does not match any valid patient usernames");
				throw new AjaxException(HttpStatus.UNPROCESSABLE_ENTITY,
						"Username from consentDto does not match any valid patient usernames");
			} else {
				Set<SpecificMedicalInfoDto> doNotShareClinicalConceptCodes = new HashSet<SpecificMedicalInfoDto>();
				if (icd9 != null) {
					doNotShareClinicalConceptCodes = consentHelper
							.getDoNotShareClinicalConceptCodes(icd9);
				}

				consentDto
						.setDoNotShareClinicalConceptCodes(doNotShareClinicalConceptCodes);

				Object obj = null;
				try {
					obj = consentService.saveConsent(consentDto, 0);
				} catch (final Exception e) {
					logger.error(e.getMessage(), e);
					throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
							"Failed to save the consent, please try again later.");
				}

				if (null != obj && obj instanceof ConsentValidationDto) {

					final ConsentValidationDto conDto = (ConsentValidationDto) obj;
					final String indirRef = accessReferenceMapper
							.getIndirectReference(conDto.getExistingConsentId());

					conDto.setExistingConsentId(indirRef);
					// duplicate policy found
					final ObjectMapper mapper = new ObjectMapper();
					String errorMessage = null;

					errorMessage = mapper.writeValueAsString(conDto);

					throw new AjaxException(HttpStatus.CONFLICT, errorMessage);
				}

				final JSONObject succObj = new JSONObject();

				succObj.put("isSuccess", true);
				succObj.put("isAdmin", false);

				final String str_succObj = succObj.toString();

				if (str_succObj == null) {
					logger.warn("An unknown error has occured consentAddPost() method of ConsentController. Call to succObj.toString() returned null value.");
					throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
							"An unknown error has occured.");
				}

				return str_succObj;
			}
		} else {
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Resource Not Found");
		}

	}

	/**
	 * Consent main page.
	 *
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping(value = "listConsents.html")
	public String consentMainPage(
			Model model,
			HttpServletRequest request,
			@RequestParam(value = "duplicateconsent", defaultValue = "-1") String duplicateConsentId) {
		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final String username = currentUser.getUsername();

		final String notify = request.getParameter("notify");
		final String notification = notificationService.notificationStage(
				username, notify);
		String emailSent = request.getParameter("emailsent");

		if (emailSent == null) {
			emailSent = "false";
		}

		final List<ConsentListDto> consentListDtos = consentService
				.findAllConsentsDtoByPatient(patientService
						.findIdByUsername(currentUser.getUsername()));

		accessReferenceMapper.setupAccessReferenceMap(consentListDtos);

		if (duplicateConsentId.equals("-1") == false) {
			model.addAttribute("duplicateConsentId", duplicateConsentId);
		}

		model.addAttribute("listConsentDtos", consentListDtos);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("emailSent", emailSent);
		model.addAttribute("notification", notification);
		model.addAttribute("notify", notify);

		return "views/consents/listConsents";
	}

	@RequestMapping(value = "editConsent.html")
	public String consetEdit(
			@RequestParam(value = "patientId", defaultValue = "-1") long in_patientId,
			Model model, @RequestParam("consentId") String consentId) {

		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		PatientProfileDto currentPatient = null;
		final Long directConsentId = accessReferenceMapper
				.getDirectReference(consentId);
		if (currentUser.getIsProviderAdmin() == true) {
			try {
				currentPatient = patientService.findPatient(in_patientId);
			} catch (final IllegalArgumentException e) {
				logger.warn("in_patientId was null when consentAdd called by a providerAdmin user.");
				logger.warn("Exception Stack Trace: " + e);
			}

			if (currentPatient == null) {
				throw new PatientNotFoundException("Patient not found by id");
			}
		} else {
			currentPatient = patientService
					.findPatientProfileByUsername(currentUser.getUsername());

			if (currentPatient == null) {
				throw new PatientNotFoundException(
						"Patient not found by username");
			}
		}

		final ConsentDto consentDto = consentService
				.findConsentById(directConsentId);
		consentDto.setId(consentId);

		// Make sure current patient and patient listed in consentDto match
		if (!consentDto.getUsername().equals(currentPatient.getUsername())) {
			throw new IllegalStateException(
					"Current patient username and username from consentDto do not match.");
		} else {

			final List<AddConsentIndividualProviderDto> individualProvidersDto = patientService
					.findAddConsentIndividualProviderDtoByUsername(currentUser
							.getUsername());
			final List<AddConsentOrganizationalProviderDto> organizationalProvidersDto = patientService
					.findAddConsentOrganizationalProviderDtoByUsername(currentUser
							.getUsername());

			final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			final Calendar today = Calendar.getInstance();
			final Calendar oneYearFromNow = Calendar.getInstance();
			oneYearFromNow.add(Calendar.YEAR, 1);

			populateLookupCodes(model);

			final List<AddConsentFieldsDto> sensitivityPolicyDto = valueSetCategoryService
					.findAllValueSetCategoriesAddConsentFieldsDto();
			final List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
					.findAllPurposeOfUseCodesAddConsentFieldsDto();
			final List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto = medicalSectionServiceImpl
					.findAllMedicalSectionsAddConsentFieldsDto();
			final List<AddConsentFieldsDto> clinicalDocumentTypeDto = clinicalDocumentTypeCodeService
					.findAllClinicalDocumentTypeCodesAddConsentFieldsDto();

			final Set<SpecificMedicalInfoDto> clinicalConceptCodes = consentDto
					.getDoNotShareClinicalConceptCodes();

			model.addAttribute("DoNotShareClinicalConceptCodes",
					clinicalConceptCodes);

			model.addAttribute("defaultStartDate",
					dateFormat.format(today.getTime()));
			model.addAttribute("defaultEndDate",
					dateFormat.format(oneYearFromNow.getTime()));
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
			model.addAttribute("isProviderAdminUser", false);
			model.addAttribute("addConsent", false);

			return "views/consents/addConsent";
		}
	}

	/**
	 * Delete consent.
	 *
	 * @param consentId
	 *            the consent id
	 * @return the string
	 */
	@RequestMapping(value = "/deleteConsents", method = RequestMethod.POST)
	public String deleteConsent(@RequestParam("consentId") String consentId) {
		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());

		// TODO: Add code to display notice to user on delete failure
		final Long directConsentId = accessReferenceMapper
				.getDirectReference(consentId);
		if (consentService
				.isConsentBelongToThisUser(directConsentId, patientId)) {
			consentService.deleteConsent(directConsentId);
			return "redirect:/consents/listConsents.html";
		}
		return "redirect:/consents/listConsents.html";
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
	@RequestMapping(value = "/downloadPdf.html", method = RequestMethod.POST)
	public String downloadConsentPdfFile(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") String consentId,
			@RequestParam("doctype") String docType) {
		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		final Long directConsentId = accessReferenceMapper
				.getDirectReference(consentId);
		final AbstractPdfDto pdfDto = getPdfDto(docType, directConsentId);
		if (consentService
				.isConsentBelongToThisUser(directConsentId, patientId)) {
			try (final OutputStream out = response.getOutputStream()) {
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + pdfDto.getFilename() + "\"");
				IOUtils.copy(new ByteArrayInputStream(pdfDto.getContent()), out);
				out.flush();
				eventService.raiseSecurityEvent(new FileDownloadedEvent(request
						.getRemoteAddr(), "User_" + currentUser.getUsername(),
						"Consent_" + directConsentId));
			} catch (final IOException e) {
				logger.warn("Error while reading pdf file.");
				logger.warn("The exception is: ", e);
			}
		}

		return null;
	}

	/**
	 * Export consents.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param consentId
	 *            the consent id
	 * @return the string
	 * @throws ConsentGenException
	 */
	@RequestMapping("exportCDAR2Consents/{consentId}")
	public String exportCDAR2Consents(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("consentId") String consentId)
			throws ConsentGenException {
		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		final Long directConsentId = accessReferenceMapper
				.getDirectReference(consentId);
		if (consentService
				.isConsentBelongToThisUser(directConsentId, patientId)) {
			final String cdar2 = consentExportService
					.exportConsent2CDAR2(directConsentId);
			response.setContentType("application/xml");
			response.setHeader("Content-Disposition",
					"attachment;filename=CDAR2consent" + consentId);
			if (cdar2 != null) {
				try {
					final OutputStream out = response.getOutputStream();
					IOUtils.copy(new ByteArrayInputStream(cdar2.getBytes()),
							out);

					out.flush();
					out.close();
					eventService.raiseSecurityEvent(new FileDownloadedEvent(
							request.getRemoteAddr(), "User_"
									+ currentUser.getUsername(), "CDAR2_"
									+ directConsentId));

				} catch (final IOException e) {

					logger.warn("Error in exporting CDAR2 consent");
					logger.warn("The exception is", e);
				}

			}
			return null;
		}
		return "views/resourceNotFound";
	}

	@RequestMapping("exportXACMLConsents/{consentId}")
	public String exportXACMLConsents(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("consentId") String consentId)
			throws ConsentGenException {
		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		final Long directConsentId = accessReferenceMapper
				.getDirectReference(consentId);
		if (consentService
				.isConsentBelongToThisUser(directConsentId, patientId)) {
			final byte[] xacml = consentService.getXacmlCcd(directConsentId);

			response.setContentType("application/xml");
			response.setHeader("Content-Disposition",
					"attachment;filename=XACMLconsent" + consentId
							+ ".xml");
			if (xacml != null && xacml.length > 0) {
				try {
					final OutputStream out = response.getOutputStream();
					IOUtils.copy(new ByteArrayInputStream(xacml), out);

					out.flush();
					out.close();
					eventService.raiseSecurityEvent(new FileDownloadedEvent(
							request.getRemoteAddr(), "User_"
									+ currentUser.getUsername(), "Consent_"
									+ directConsentId));

				} catch (final IOException e) {

					logger.warn("Error in exporting XACML consent");
					logger.warn("The exception is", e);
				}

			}
			return null;
		}
		return "views/resourceNotFound";
	}

	/**
	 * Redirect to email login page.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "toEmail.html")
	public String redirectToEmailLoginPage() {
		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final String email = patientService
				.findPatientEmailByUsername(currentUser.getUsername());
		final String urlString = email.substring(email.indexOf('@') + 1);

		return "redirect:http://" + urlString;
	}

	/**
	 * Redirect to email login page.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "validationModal.html")
	public String redirectToValidationModal() {
		return "views/consents/consent-validation-modal";
	}

	/**
	 * Sign consent.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param consentId
	 *            the consent id
	 * @return the string
	 */
	@RequestMapping(value = "signConsent.html", method = RequestMethod.POST)
	public String signConsent(Model model, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") String consentId) {
		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		final Long directConsentId = accessReferenceMapper
				.getDirectReference(consentId);
		if (consentService
				.isConsentBelongToThisUser(directConsentId, patientId)
				&& consentService.getConsentSignedStage(directConsentId)
						.equals("CONSENT_SAVED")) {
			final ConsentPdfDto consentPdfDto = consentService
					.findConsentPdfDto(directConsentId);
			final String javascriptCode = consentService
					.createConsentEmbeddedWidget(consentPdfDto);
			model.addAttribute("returned_javascript", javascriptCode);
			return "views/consents/signConsent";
		}
		return "redirect:listConsents.html";
	}

	@RequestMapping(value = "signConsentRevokation", method = RequestMethod.POST)
	public String signConsentRevokation(Model model,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("consentId") String consentId,
			@RequestParam("revokationType") String revokationType) {
		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		final Long directConsentId = accessReferenceMapper
				.getDirectReference(consentId.substring(0, 6));
		consentService.addUnsignedConsentRevokationPdf(directConsentId,
				revokationType);
		if (consentService
				.isConsentBelongToThisUser(directConsentId, patientId)) {
			final ConsentRevokationPdfDto consentRevokationPdfDto = consentService
					.findConsentRevokationPdfDto(directConsentId);
			consentRevokationPdfDto.setRevokationType(revokationType);
			final String javascriptCode = consentService
					.createRevocationEmbeddedWidget(consentRevokationPdfDto);
			model.addAttribute("returned_javascript", javascriptCode);
			return "views/consents/signConsent";
		}

		return "redirect:listConsents.html";
	}

	/**
	 * Return tagged C32 transformed wtih xslt
	 *
	 * @param model
	 * @param request
	 * @param response
	 * @param consentId
	 * @param c32Id
	 * @return
	 * @throws ConsentGenException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	@RequestMapping(value = "tryMyPolicyApply/consentId/{consentId}/c32Id/{c32Id}/purposeOfUse/{purposeOfUse}", method = RequestMethod.GET)
	public @ResponseBody String tryMyPolicyApply(Model model,
			HttpServletRequest request, HttpServletResponse response,
			@PathVariable("consentId") String consentId,
			@PathVariable("c32Id") Long c32Id,
			@PathVariable("purposeOfUse") String purposeOfUse)
			throws ConsentGenException, IOException,
			ParserConfigurationException, SAXException {

		// String c32Id = request.getParameter("c32Id");
		// Long consentId = Long.parseLong(request.getParameter("consentId"));

		final Long directConsentId = accessReferenceMapper
				.getDirectReference(consentId);
		ClinicalDocument document;

		logger.info("Consent id: " + consentId);
		logger.info("C32 id: " + c32Id);
		logger.info("Purpose of use: " + purposeOfUse);

		// move to service
		if (c32Id.equals(SAMPLE_C32_ID)) {
			document = loadSampleC32();
		} else {
			document = clinicalDocumentService.findClinicalDocument(c32Id);
		}

		final String originalC32 = new String(document.getContent());

		// get tagged c32
		final String taggedC32xml = consentService.getTaggedC32(originalC32,
				directConsentId, purposeOfUse);

		// response.setContentType("text/xml");
		response.setContentType("application/xml");

		logger.info("tagged c32: " + taggedC32xml);

		return taggedC32xml;
	}

	/**
	 * Return list of C32 documents patient uploaded
	 *
	 * @param model
	 * @param request
	 * @param response
	 * @param consentId
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "tryMyPolicyLookupC32Documents/{consentId}", method = RequestMethod.GET)
	public @ResponseBody TryMyPolicyDto tryMyPolicyLookupC32Documents(
			Model model, HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("consentId") String consentId) throws IOException {

		final AuthenticatedUser currentUser = userContext.getCurrentUser();
		final Long patientId = patientService.findIdByUsername(currentUser
				.getUsername());
		final TryMyPolicyDto tryMyPolicyDto = new TryMyPolicyDto();
		final Long directConsentId = accessReferenceMapper
				.getDirectReference(consentId);
		final List<ClinicalDocument> clinicalDocuments = clinicalDocumentService
				.findByPatientId(patientId);
		clinicalDocuments.add(loadSampleC32());

		final List<ClinicalDocumentDto> c32Documents = new ArrayList<ClinicalDocumentDto>();

		for (int i = 0; i < clinicalDocuments.size(); i++) {

			// return c32 documents only
			// TODO (AO): temporary solution to display c32 without having
			// document types
			// if (clinicalDocuments.get(i).getClinicalDocumentTypeCode()
			// .getCode().equals(C32_DOC_CODE)) {
			final ClinicalDocumentDto dto = new ClinicalDocumentDto();
			dto.setId(String.valueOf(clinicalDocuments.get(i).getId()));
			dto.setFilename(clinicalDocuments.get(i).getFilename());

			c32Documents.add(dto);
			// }
		}

		// TODO: move dto to service
		tryMyPolicyDto.setC32Documents(c32Documents);
		tryMyPolicyDto.setShareForPurposeOfUseCodesAndValues(consentService
				.findConsentById(directConsentId)
				.getPurposeOfUseCodesAndValues());

		return tryMyPolicyDto;
	}

	private AbstractPdfDto getPdfDto(String docType, long consentId) {
		if (docType.equals("revokation")) {
			return consentService.findConsentRevokationPdfDto(consentId);
		}
		return consentService.findConsentPdfDto(consentId);
	}

	/**
	 * Load sample c32.
	 *
	 * @return the clinical document
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private ClinicalDocument loadSampleC32() throws IOException {
		final byte[] data = ByteStreams.toByteArray(this.getClass()
				.getClassLoader()
				.getResourceAsStream("Sample Summary Medical Record.xml"));

		final ClinicalDocument doc = new ClinicalDocument();
		doc.setFilename("Sample Summary Medical Record");
		doc.setContent(data);
		doc.setContentType("text/xml");
		doc.setDescription("This file contains Sample Summary Medical Record");
		doc.setId(new Long(-1));
		doc.setName("Sample Summary Medical Record");

		return doc;
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
		model.addAttribute("stateCodes",
				stateCodeService.findByMDAndDCAndVAStates());
	}

}
