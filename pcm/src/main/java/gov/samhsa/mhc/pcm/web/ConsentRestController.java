/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p/>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p/>
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
package gov.samhsa.mhc.pcm.web;

import gov.samhsa.mhc.common.consentgen.ConsentGenException;
import gov.samhsa.mhc.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.mhc.pcm.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.mhc.pcm.service.consent.ConsentHelper;
import gov.samhsa.mhc.pcm.service.consent.ConsentService;
import gov.samhsa.mhc.pcm.service.dto.*;
import gov.samhsa.mhc.pcm.service.exception.*;
import gov.samhsa.mhc.pcm.service.notification.NotificationService;
import gov.samhsa.mhc.pcm.service.patient.PatientService;
import gov.samhsa.mhc.pcm.service.reference.PurposeOfUseCodeService;
import gov.samhsa.mhc.vss.service.ValueSetCategoryService;
import gov.samhsa.mhc.vss.service.dto.AddConsentFieldsDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * The Class ConsentRestController.
 */
@RestController
@RequestMapping("/patients")
public class ConsentRestController {

    public final static Long SAMPLE_C32_ID = new Long(-1);
    /**
     * The C32_ do c_ code.
     */
    public final String C32_DOC_CODE = "34133-9";
    /**
     * The logger.
     */
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    NotificationService notificationService;
    @Autowired
    private ConsentHelper consentHelper;
    /**
     * The consent service.
     */
    @Autowired
    private ConsentService consentService;
    /**
     * The patient service.
     */
    @Autowired
    private PatientService patientService;

    /**
     * The purpose of use code service.
     */
    @Autowired
    private PurposeOfUseCodeService purposeOfUseCodeService;
    /**
     * The value set category service.
     */
    @Autowired
    private ValueSetCategoryService valueSetCategoryService;

    @Autowired
    private EventService eventService;

    private String revokationType = "EMERGENCY ONLY";

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @RequestMapping(value = "consents/pageNumber/{pageNumber}")
    public ConsentsListDto listConsents(@PathVariable("pageNumber") String pageNumber) {
        // FIXME: remove this line when patient creation concept in PCM is finalized
        final Long patientId = patientService.createNewPatientWithOAuth2AuthenticationIfNotExists();
        ConsentsListDto consentsListDto = new ConsentsListDto(consentService
                .findAllConsentsDtoByPatientAndPage(patientId, pageNumber));
        // using directId
        // accessReferenceMapper.setupAccessReferenceMap(consentsListDto.getConsentList());
        return consentsListDto;
    }

    @RequestMapping(value = "purposeOfUse")
    public List<AddConsentFieldsDto> purposeOfUseLookup() {

        List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
                .findAllPurposeOfUseCodesAddConsentFieldsDto();
        return purposeOfUseDto;
    }

    @RequestMapping(value = "sensitivityPolicy")
    public List<AddConsentFieldsDto> sensitivityPolicyLookup() {

        List<AddConsentFieldsDto> sensitivityPolicyDtos = valueSetCategoryService
                .findAllValueSetCategoriesAddConsentFieldsDto();
        return sensitivityPolicyDtos;
    }

    @RequestMapping(value = "consents/{consentId}", method = RequestMethod.DELETE)
    public void deleteConsent(Principal principal, @PathVariable("consentId") Long consentId) {
        final Long patientId = patientService.findIdByUsername(principal.getName());

        // final Long directConsentId =
        // accessReferenceMapper.getDirectReference(consentId);
        if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
            if (consentService.deleteConsent(consentId) == false)
                throw new CannotDeleteConsentException("Error: Unable to delete this consent.");

        } else {
            throw new ConsentNotBelongingToPatientException("Error: Unable to delete this consent because it is not belong to patient.");
        }

    }

    @RequestMapping(value = "consents/{consentId}", method = RequestMethod.GET)
    public ConsentDto getConsent(Principal principal, @PathVariable("consentId") String consentId) {
        // Long directConsentId =
        // accessReferenceMapper.getDirectReference(consentId);
        ConsentDto consentDto = consentService.findConsentById(principal.getName(), Long.valueOf(consentId));
        consentDto.setId(consentId);
        return consentDto;
    }

    @RequestMapping(value = "consents", method = RequestMethod.POST)
    public void consentAddPost(Principal principal, @RequestBody ConsentDto consentDto,
                               @RequestParam(value = "ICD9", required = false) HashSet<String> icd9)
            throws ConsentGenException, IOException {
        consentDto.setUsername(principal.getName());
        /*
         * if (consentDto.getId() != null) { final String directConsentId =
		 * String.valueOf(accessReferenceMapper
		 * .getDirectReference(consentDto.getId()));
		 * consentDto.setId(directConsentId); }
		 */

        final Set<String> isMadeTo = new HashSet<String>();
        final Set<String> isMadeFrom = new HashSet<String>();

        // validate consent start date and end date
        if (consentService.validateConsentDate(consentDto.getConsentStart(), consentDto.getConsentEnd()) == false) {
            throw new InvalidConsentDatesException("Invalid value(s) passed in for one or more date fields.");
        }

        try {
            consentDto.setConsentEnd(consentHelper.setDateAsEndOfDay(consentDto.getConsentEnd()));
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            logger.warn(
                    "Exception thrown & caught in consentAddPost() method of ConsentController when calling consentHelper.setDateAsEndOfDay()");
            logger.warn("    Stack Trace: " + e);

            throw new InvalidConsentDatesException("Invalid value(s) passed in for one or more date fields.");
        }

        if (consentDto.getOrganizationalProvidersDisclosureIsMadeToNpi() != null) {
            isMadeTo.addAll(consentDto.getOrganizationalProvidersDisclosureIsMadeToNpi());
        }

        if (consentDto.getProvidersDisclosureIsMadeToNpi() != null) {
            isMadeTo.addAll(consentDto.getProvidersDisclosureIsMadeToNpi());
        }

        if (consentDto.getOrganizationalProvidersPermittedToDiscloseNpi() != null) {
            isMadeFrom.addAll(consentDto.getOrganizationalProvidersPermittedToDiscloseNpi());
        }

        if (consentDto.getProvidersPermittedToDiscloseNpi() != null) {
            isMadeFrom.addAll(consentDto.getProvidersPermittedToDiscloseNpi());
        }

        consentService.areThereDuplicatesInTwoSets(isMadeTo, isMadeFrom);

        if (!isMadeTo.isEmpty() && !isMadeFrom.isEmpty()
                && consentService.areThereDuplicatesInTwoSets(isMadeTo, isMadeFrom) == false) {
            if ((consentDto.getShareForPurposeOfUseCodes() == null)
                    || (consentDto.getShareForPurposeOfUseCodes().isEmpty())) {
                throw new MissingPurposeOfUseException("At least one purpose of use needs to be selected.");
            }

            // one to one policy validation
            if (isMadeTo.size() != 1 || isMadeFrom.size() != 1) {
                throw new TooManyProvidersSelectedException("Only one provider can be selected from the list");
            }

            // Make sure username from consentDto matches a valid patient
            // username

            PatientProfileDto checkMatch = null;

            try {
                checkMatch = patientService.findPatientProfileByUsername(consentDto.getUsername());
            } catch (final IllegalArgumentException e) {
                logger.warn("Username from consentDto does not match any valid patient usernames");
                logger.warn("Stack trace: ", e);
                throw new UnprocessableConsentAddRequestException("Username from consentDto does not match any valid patient usernames");
            }

            if (checkMatch == null) {
                logger.warn("Username from consentDto does not match any valid patient usernames");
                throw new UnprocessableConsentAddRequestException("Username from consentDto does not match any valid patient usernames");
            } else {
                Set<SpecificMedicalInfoDto> doNotShareClinicalConceptCodes = new HashSet<SpecificMedicalInfoDto>();
                if (icd9 != null) {
                    doNotShareClinicalConceptCodes = consentHelper.getDoNotShareClinicalConceptCodes(icd9);
                }

                consentDto.setDoNotShareClinicalConceptCodes(doNotShareClinicalConceptCodes);

                Object obj = null;
                try {
                    obj = consentService.saveConsent(consentDto, 0);
                } catch (final Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new InternalServerErrorException("Failed to save the consent, please try again later.");
                }

                if (null != obj && obj instanceof ConsentValidationDto) {

                    final ConsentValidationDto conDto = (ConsentValidationDto) obj;
                    /*
                     * final String indirRef = accessReferenceMapper
					 * .getIndirectReference(conDto.getExistingConsentId());
					 * 
					 * conDto.setExistingConsentId(indirRef);
					 */
                    // duplicate policy found
                    final ObjectMapper mapper = new ObjectMapper();
                    String errorMessage = null;

                    errorMessage = mapper.writeValueAsString(conDto);

                    throw new ConflictingConsentException(errorMessage);
                }

            }
        } else {
            throw new InternalServerErrorException("Resource Not Found");
        }

    }

    @RequestMapping(value = "consents/{consentId}", method = RequestMethod.PUT)
    public void updateConsents(Principal principal, @RequestBody ConsentDto consentDto, @PathVariable("consentId") Long consentId,
                               @RequestParam(value = "ICD9", required = false) HashSet<String> icd9)
            throws ConsentGenException, IOException {

        consentDto.setUsername(principal.getName());
        /*
         * if (consentDto.getId() != null) { final String directConsentId =
		 * String.valueOf(accessReferenceMapper
		 * .getDirectReference(consentDto.getId()));
		 * consentDto.setId(directConsentId); }
		 */

        final Set<String> isMadeTo = new HashSet<String>();
        final Set<String> isMadeFrom = new HashSet<String>();

        // validate consent start date and end date
        if (consentService.validateConsentDate(consentDto.getConsentStart(), consentDto.getConsentEnd()) == false) {
            throw new InvalidConsentDatesException("Invalid value(s) passed in for one or more date fields.");
        }

        try {
            consentDto.setConsentEnd(consentHelper.setDateAsEndOfDay(consentDto.getConsentEnd()));
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            logger.warn(
                    "Exception thrown & caught in consentAddPost() method of ConsentController when calling consentHelper.setDateAsEndOfDay()");
            logger.warn("    Stack Trace: " + e);

            throw new InvalidConsentDatesException("Invalid value(s) passed in for one or more date fields.");
        }

        if (consentDto.getOrganizationalProvidersDisclosureIsMadeToNpi() != null) {
            isMadeTo.addAll(consentDto.getOrganizationalProvidersDisclosureIsMadeToNpi());
        }

        if (consentDto.getProvidersDisclosureIsMadeToNpi() != null) {
            isMadeTo.addAll(consentDto.getProvidersDisclosureIsMadeToNpi());
        }

        if (consentDto.getOrganizationalProvidersPermittedToDiscloseNpi() != null) {
            isMadeFrom.addAll(consentDto.getOrganizationalProvidersPermittedToDiscloseNpi());
        }

        if (consentDto.getProvidersPermittedToDiscloseNpi() != null) {
            isMadeFrom.addAll(consentDto.getProvidersPermittedToDiscloseNpi());
        }

        consentService.areThereDuplicatesInTwoSets(isMadeTo, isMadeFrom);

        if (!isMadeTo.isEmpty() && !isMadeFrom.isEmpty()
                && consentService.areThereDuplicatesInTwoSets(isMadeTo, isMadeFrom) == false) {
            if ((consentDto.getShareForPurposeOfUseCodes() == null)
                    || (consentDto.getShareForPurposeOfUseCodes().isEmpty())) {
                throw new MissingPurposeOfUseException("At least one purpose of use needs to be selected.");
            }
            // one to one policy validation
            if (isMadeTo.size() != 1 || isMadeFrom.size() != 1) {
                throw new TooManyProvidersSelectedException("Only one provider can be selected from the list");
            }

            // Make sure username from consentDto matches a valid patient
            // username

            PatientProfileDto checkMatch = null;

            try {
                checkMatch = patientService.findPatientProfileByUsername(consentDto.getUsername());
            } catch (final IllegalArgumentException e) {
                logger.warn("Username from consentDto does not match any valid patient usernames");
                logger.warn("Stack trace: ", e);
                throw new UnprocessableConsentAddRequestException("Username from consentDto does not match any valid patient usernames");
            }

            if (checkMatch == null) {
                logger.warn("Username from consentDto does not match any valid patient usernames");
                throw new UnprocessableConsentAddRequestException("Username from consentDto does not match any valid patient usernames");
            } else {
                Set<SpecificMedicalInfoDto> doNotShareClinicalConceptCodes = new HashSet<SpecificMedicalInfoDto>();
                if (icd9 != null) {
                    doNotShareClinicalConceptCodes = consentHelper.getDoNotShareClinicalConceptCodes(icd9);
                }

                consentDto.setDoNotShareClinicalConceptCodes(doNotShareClinicalConceptCodes);

                Object obj = null;
                try {
                    obj = consentService.saveConsent(consentDto, 0);
                } catch (final Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new InternalServerErrorException("Failed to save the consent, please try again later.");
                }

                if (null != obj && obj instanceof ConsentValidationDto) {

                    final ConsentValidationDto conDto = (ConsentValidationDto) obj;
                    /*
                     * final String indirRef = accessReferenceMapper
					 * .getIndirectReference(conDto.getExistingConsentId());
					 * 
					 * conDto.setExistingConsentId(indirRef);
					 */
                    // duplicate policy found
                    final ObjectMapper mapper = new ObjectMapper();
                    String errorMessage = null;

                    errorMessage = mapper.writeValueAsString(conDto);

                    throw new ConflictingConsentException(errorMessage);
                }
            }
        } else {
            throw new InternalServerErrorException("Resource Not Found");
        }

    }

    @RequestMapping(value = "consents/signConsent/{consentId}", method = RequestMethod.GET)
    public Map<String, String> signConsent(Principal principal, @PathVariable("consentId") Long consentId) throws ConsentGenException {
        final Long patientId = patientService.findIdByUsername(principal.getName());
        if (consentService.isConsentBelongToThisUser(consentId, patientId)
                && consentService.getConsentSignedStage(consentId).equals("CONSENT_SAVED")) {
            final ConsentPdfDto consentPdfDto = consentService.findConsentPdfDto(consentId);
            final String javascriptWidgetCode = consentService.createConsentEmbeddedWidget(consentPdfDto);

            String[] rt = javascriptWidgetCode.split("'");

            RestTemplate restTemplate = new RestTemplate();
            String javaScript = restTemplate.getForObject(rt[5], String.class);

            Map<String, String> map = new HashMap<String, String>();
            map.put("javascriptCode", javaScript.substring(javaScript.indexOf("(") + 2, javaScript.indexOf(")") - 1));
            return map;
        } else
            throw new InternalServerErrorException("Resource Not Found");
    }

    @RequestMapping(value = "consents/revokeConsent/{consentId}", method = RequestMethod.GET)
    public Map<String, String> signConsentRevokation(Principal principal, @PathVariable("consentId") Long consentId) {
        final Long patientId = patientService.findIdByUsername(principal.getName());
        consentService.addUnsignedConsentRevokationPdf(consentId, revokationType);
        if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
            final ConsentRevokationPdfDto consentRevokationPdfDto = consentService
                    .findConsentRevokationPdfDto(consentId);
            consentRevokationPdfDto.setRevokationType(revokationType);
            final String javascriptWidgetCode = consentService.createRevocationEmbeddedWidget(consentRevokationPdfDto);

            String[] rt = javascriptWidgetCode.split("'");

            RestTemplate restTemplate = new RestTemplate();
            String javaScript = restTemplate.getForObject(rt[5], String.class);

            Map<String, String> map = new HashMap<String, String>();
            map.put("javascriptCode", javaScript.substring(javaScript.indexOf("(") + 2, javaScript.indexOf(")") - 1));
            return map;
        } else
            throw new InternalServerErrorException("Resource Not Found");
    }


    @RequestMapping(value = "consents/exportConsentDirective/{consentId}", method = RequestMethod.GET)
    public Map exportConsentDirective(HttpServletRequest request, Principal principal, @PathVariable("consentId") Long consentId) {
        final Long patientId = patientService.findIdByUsername(principal.getName());
        if (consentService
                .isConsentBelongToThisUser(consentId, patientId)) {
            final byte[] consentDirective = consentService.getConsentDirective(consentId);

            eventService.raiseSecurityEvent(new FileDownloadedEvent(
                    request.getRemoteAddr(), "User_"
                    + principal.getName(), "Consent_"
                    + consentId));
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);

            Map<String, String> map = new HashMap<String, String>();
            map.put("data", new String(consentDirective));
            return map;
            //return new ResponseEntity<byte[]>(consentDirective,headers, HttpStatus.OK);
        } else
            throw new InternalServerErrorException("Resource Not Found");

    }

    @RequestMapping(value = "consents/download/{docType}/{consentId}", method = RequestMethod.GET, produces = "application/pdf")
    public byte[] downloadConsentPdfFile(HttpServletRequest request, Principal principal, @PathVariable("consentId") Long consentId, @PathVariable("docType") String docType) throws IOException {
        final Long patientId = patientService.findIdByUsername(principal.getName());
        if (consentService
                .isConsentBelongToThisUser(consentId, patientId)) {
            final AbstractPdfDto pdfDto = getPdfDto(docType, consentId);
            eventService.raiseSecurityEvent(new FileDownloadedEvent(request
                    .getRemoteAddr(), "User_" + principal.getName(),
                    "Consent_" + consentId));
            return pdfDto.getContent();
        } else
            throw new InternalServerErrorException("Resource Not Found");
    }

    private AbstractPdfDto getPdfDto(String docType, long consentId) {
        if (docType.equals("revokation")) {
            return consentService.findConsentRevokationPdfDto(consentId);
        }
        return consentService.findConsentPdfDto(consentId);
    }

}
