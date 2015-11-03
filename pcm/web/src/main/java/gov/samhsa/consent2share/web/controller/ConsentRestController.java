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
import gov.samhsa.consent2share.infrastructure.security.AccessReferenceMapper;
import gov.samhsa.consent2share.service.consent.ConsentHelper;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentValidationDto;
import gov.samhsa.consent2share.service.dto.ConsentsListDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.SpecificMedicalInfoDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.PurposeOfUseCodeService;
import gov.samhsa.consent2share.service.valueset.MedicalSectionService;
import gov.samhsa.consent2share.service.valueset.ValueSetCategoryService;
import gov.samhsa.consent2share.web.AjaxException;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class ConsentRestController.
 */
@RestController
@RequestMapping("/patients")
public class ConsentRestController extends AbstractController {

	public final static Long SAMPLE_C32_ID = new Long(-1);
	
	@Autowired
	private AccessReferenceMapper accessReferenceMapper;
	
	@Autowired
	private ConsentHelper consentHelper;

	/** The consent service. */
	@Autowired
	private ConsentService consentService;

	/** The patient service. */
	@Autowired
	private PatientService patientService;

	/** The clinical document section type code service. */
	@Autowired
	private MedicalSectionService medicalSectionServiceImpl;

	/** The purpose of use code service. */
	@Autowired
	private PurposeOfUseCodeService purposeOfUseCodeService;

	@Autowired
	NotificationService notificationService;

	/** The value set category service. */
	@Autowired
	private ValueSetCategoryService valueSetCategoryService;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The C32_ do c_ code. */
	public final String C32_DOC_CODE = "34133-9";
	
	
	private String  username="albert.smith";
	
	
	
	@RequestMapping(value = "consents/pageNumber/{pageNumber}")
	public ConsentsListDto listConsents(@PathVariable("pageNumber") String pageNumber) {
		
		ConsentsListDto consentsListDto = new ConsentsListDto(consentService
				.findAllConsentsDtoByPatientAndPage(patientService
						.findIdByUsername(username), pageNumber));

		accessReferenceMapper.setupAccessReferenceMap(consentsListDto.getConsentList());
		return consentsListDto;
	}
	
	@RequestMapping(value = "purposeOfUse")
	public List<AddConsentFieldsDto> purposeOfUseLookup() {

		List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
				.findAllPurposeOfUseCodesAddConsentFieldsDto();
		 return purposeOfUseDto;
	}
	
	
	@RequestMapping(value = "medicalSection")
	public List<AddConsentFieldsDto> medicalSectionLookup() {

		List<AddConsentFieldsDto> medicalSectionDtos = medicalSectionServiceImpl
				.findAllMedicalSectionsAddConsentFieldsDto();
		 return medicalSectionDtos;
	}
	
	
	@RequestMapping(value = "sensitivityPolicy")
	public List<AddConsentFieldsDto> sensitivityPolicyLookup() {

		List<AddConsentFieldsDto> sensitivityPolicyDtos =valueSetCategoryService
				.findAllValueSetCategoriesAddConsentFieldsDto();
		 return sensitivityPolicyDtos;
	}
	

	@RequestMapping(value = "/consents/{consentId}", method = RequestMethod.DELETE)
	public void deleteConsent(@PathVariable("consentId") String consentId) {
		final Long patientId = patientService.findIdByUsername(username);

		final Long directConsentId = accessReferenceMapper.getDirectReference(consentId);
		if (consentService.isConsentBelongToThisUser(directConsentId, patientId)) {
			if(consentService.deleteConsent(directConsentId)==false)
				throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error: Unable to delete this consent.");

		} else {
			throw new AjaxException(HttpStatus.BAD_REQUEST,
					"Error: Unable to delete this consent because it is not belong to patient.");
		}

	}
	
	@RequestMapping(value = "/consents/{consentId}", method = RequestMethod.GET)
	public ConsentDto getConsent(@PathVariable("consentId") String consentId) {
		
		Long directConsentId = accessReferenceMapper.getDirectReference(consentId);
		ConsentDto consentDto = consentService.findConsentById(directConsentId);
		consentDto.setId(consentId);
		return consentDto;
	}
	
	@RequestMapping(value = "/consents", method = RequestMethod.POST)
	public void consentAddPost(@Valid ConsentDto consentDto,
			@RequestParam(value = "ICD9", required = false) HashSet<String> icd9)
			throws ConsentGenException, IOException, JSONException {
		
		consentDto.setUsername(username);
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

			}
		} else {
			throw new AjaxException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Resource Not Found");
		}

	}
	
	

	
}
