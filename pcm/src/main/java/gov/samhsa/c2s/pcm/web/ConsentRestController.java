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
package gov.samhsa.c2s.pcm.web;

import gov.samhsa.c2s.common.consentgen.ConsentGenException;
import gov.samhsa.c2s.pcm.domain.consent.ConsentTermsVersions;
import gov.samhsa.c2s.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.c2s.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.c2s.pcm.service.consent.ConsentHelper;
import gov.samhsa.c2s.pcm.service.consent.ConsentService;
import gov.samhsa.c2s.pcm.service.consent.ConsentStatus;
import gov.samhsa.c2s.pcm.service.dto.AbstractPdfDto;
import gov.samhsa.c2s.pcm.service.dto.AttestationDto;
import gov.samhsa.c2s.pcm.service.dto.AttestedDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentAttestationDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentPdfDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentRevocationAttestationDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentValidationDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentsListDto;
import gov.samhsa.c2s.pcm.service.dto.PatientProfileDto;
import gov.samhsa.c2s.pcm.service.dto.RevocationDto;
import gov.samhsa.c2s.pcm.service.dto.SpecificMedicalInfoDto;
import gov.samhsa.c2s.pcm.service.exception.CannotDeleteConsentException;
import gov.samhsa.c2s.pcm.service.exception.ConflictingConsentException;
import gov.samhsa.c2s.pcm.service.exception.ConsentNotBelongingToPatientException;
import gov.samhsa.c2s.pcm.service.exception.InternalServerErrorException;
import gov.samhsa.c2s.pcm.service.exception.InvalidConsentDatesException;
import gov.samhsa.c2s.pcm.service.exception.MissingPurposeOfUseException;
import gov.samhsa.c2s.pcm.service.exception.TooManyProvidersSelectedException;
import gov.samhsa.c2s.pcm.service.exception.UnprocessableConsentAddRequestException;
import gov.samhsa.c2s.pcm.service.fhir.FhirConsentService;
import gov.samhsa.c2s.pcm.service.notification.NotificationService;
import gov.samhsa.c2s.pcm.service.patient.PatientService;
import gov.samhsa.c2s.pcm.service.reference.PurposeOfUseCodeService;
import gov.samhsa.c2s.vss.service.ValueSetCategoryService;
import gov.samhsa.c2s.vss.service.dto.AddConsentFieldsDto;
import gov.samhsa.c2s.vss.service.dto.ValueSetCategoryFieldsDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    private FhirConsentService fhirConsentService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "consents/pageNumber/{pageNumber}")
    public ConsentsListDto listConsents(@PathVariable("pageNumber") String pageNumber) {
        // FIXME (#7): remove this line when patient creation concept in PCM is finalized
        final Long patientId = patientService.createNewPatientWithOAuth2AuthenticationIfNotExists();
        ConsentsListDto consentsListDto = new ConsentsListDto(consentService
                .findAllConsentsDtoByPatientAndPage(patientId, pageNumber));
        return consentsListDto;
    }

    @RequestMapping(value = "purposeOfUse")
    public List<AddConsentFieldsDto> purposeOfUseLookup() {

        List<AddConsentFieldsDto> purposeOfUseDto = purposeOfUseCodeService
                .findAllPurposeOfUseCodesAddConsentFieldsDto();
        Locale locale = LocaleContextHolder.getLocale();
        if (!locale.getLanguage().equalsIgnoreCase("en")) {
            for (AddConsentFieldsDto addConsentFieldsDto : purposeOfUseDto) {

                //the properties file: key-value should be like : code.name=value, code.description=value
                List<String> purposeNameAndDescription = getMultiLangName (locale, addConsentFieldsDto.getCode());
                addConsentFieldsDto.setDisplayName(purposeNameAndDescription.get(0));//displayName
                addConsentFieldsDto.setDescription(purposeNameAndDescription.get(1));//description
            }
        }
        return purposeOfUseDto;
    }

    @RequestMapping(value = "sensitivityPolicy")
    public List<ValueSetCategoryFieldsDto> sensitivityPolicyLookup() {

        //String language = request.getHeader("Accept-Language");
        //get Locale - solution 1: do not rely on request  -- added by Wentao
        Locale locale = LocaleContextHolder.getLocale();
        //solution 2: Locale locale = RequestContextUtils.getLocale(request);

        List<ValueSetCategoryFieldsDto> sensitivityPolicyDtos = valueSetCategoryService
                .findAllValueSetCategoriesAddConsentFieldsDto();

        // re-set name and description for multi-language - added by Wentao
        if (!locale.getLanguage().equalsIgnoreCase("en")) {
            for (ValueSetCategoryFieldsDto vssCategory : sensitivityPolicyDtos) {
                //the properties file: key-value should be like : code.name=value, code.description=value
                List<String> vssNameAndDescription = getMultiLangName (locale, vssCategory.getCode());
                vssCategory.setDisplayName(vssNameAndDescription.get(0));//displayName
                vssCategory.setDescription(vssNameAndDescription.get(1));//Description
            }
        }


        return sensitivityPolicyDtos;
    }

    /**
     * get display name and description for vss catergories according to locale
     * author:  Wentao
     * */
    public List<String> getMultiLangName (Locale locale, String vssCode) {

        List<String> vssNameAndDescription = new ArrayList<String>();
        vssNameAndDescription.add(messageSource.getMessage(vssCode + ".NAME", null, locale));
        vssNameAndDescription.add(messageSource.getMessage(vssCode + ".DESCRIPTION", null, locale));

        return vssNameAndDescription;
    }

    @RequestMapping(value = "consents/{consentId}", method = RequestMethod.DELETE)
    public void deleteConsent(Principal principal, @PathVariable("consentId") Long consentId) {
        final Long patientId = patientService.findIdByUsername(principal.getName());

        if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
            if (consentService.deleteConsent(consentId) == false)
                throw new CannotDeleteConsentException("Error: Unable to delete this consent.");
        } else {
            throw new ConsentNotBelongingToPatientException("Error: Unable to delete this consent because it is not belong to patient.");
        }

    }

    @RequestMapping(value = "consents/{consentId}", method = RequestMethod.GET)
    public ConsentDto getConsent(Principal principal, @PathVariable("consentId") String consentId) {
        ConsentDto consentDto = consentService.findConsentById(principal.getName(), Long.valueOf(consentId));
        consentDto.setId(consentId);
        return consentDto;
    }

    @RequestMapping(value = "consents", method = RequestMethod.POST)
    public void consentAddPost(Principal principal, @RequestBody ConsentDto consentDto,
                               @RequestParam(value = "ICD9", required = false) HashSet<String> icd9)
            throws ConsentGenException, IOException {
        consentDto.setUsername(principal.getName());

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

    @RequestMapping(value = "consents/{consentId}/revocation", method = RequestMethod.POST)
    public void completeConsentRevocation(Principal principal, @RequestBody RevocationDto revocationDto, @RequestHeader(value = "X-Forwarded-For") String xForwardedFor) throws ConsentGenException {
        AttestationDto attestationDto = new AttestationDto();

        final Long patientId = patientService.findIdByUsername(principal.getName());
        Long consentId = revocationDto.getConsentId();
        boolean acceptTerms = revocationDto.isAcceptTerms();

        attestationDto.setConsentId(consentId);
        attestationDto.setAttesterIpAddress(xForwardedFor);

        //TODO (#8): Move check for consent belonging to this user and consent signed stage to service
        if (consentId != null && acceptTerms && consentService.isConsentBelongToThisUser(consentId, patientId) && consentService.getConsentStatus(consentId).equals(ConsentStatus.CONSENT_SIGNED) ){
            consentService.attestConsentRevocation(attestationDto);
        } else
            throw new InternalServerErrorException("Resource Not Found");
    }

    @RequestMapping(value = "consents/{consentId}/attested", method = RequestMethod.POST)
    public void completeConsentAttestation(Principal principal, @RequestBody AttestedDto attestedDto, @RequestHeader("X-Forwarded-For") String xForwardedFor) throws ConsentGenException {
        AttestationDto attestationDto = new AttestationDto();

        final Long patientId = patientService.findIdByUsername(principal.getName());
        Long consentId = attestedDto.getConsentId();
        boolean acceptTerms = attestedDto.isAcceptTerms();

        attestationDto.setConsentId(consentId);
        attestationDto.setAttesterIpAddress(xForwardedFor);

        //TODO (#9): Move check for consent belonging to this user and consent signed stage to service
        if (consentId != null && acceptTerms && consentService.isConsentBelongToThisUser(consentId, patientId)
                && consentService.getConsentStatus(consentId).equals(ConsentStatus.CONSENT_SAVED)) {
            consentService.attestConsent(attestationDto);

        } else
            throw new InternalServerErrorException("Resource Not Found");
    }

    @RequestMapping(value = "consents/{consentId}/attestation", method = RequestMethod.GET)
    public ConsentAttestationDto getConsentAttestationDto(Principal principal, @PathVariable("consentId") Long consentId) throws ConsentGenException {
        final Long patientId = patientService.findIdByUsername(principal.getName());
        Locale locale = LocaleContextHolder.getLocale();
        //TODO (#10): Move check for consent belonging to this user and consent signed stage to service
        if (consentService.isConsentBelongToThisUser(consentId, patientId)
                && consentService.getConsentStatus(consentId).equals(ConsentStatus.CONSENT_SAVED)) {
            ConsentAttestationDto consentAttestationDto = consentService.getConsentAttestationDto(principal.getName(),consentId);
            //return consentService.getConsentAttestationDto(principal.getName(),consentId);
            if (!locale.getLanguage().equalsIgnoreCase("en")) {
                for (PurposeOfUseCode purposeOfUseCode : consentAttestationDto.getPurposeOfUseCodes()) {
                    String useCode = purposeOfUseCode.getCode();
                    purposeOfUseCode.setDisplayName(messageSource.getMessage(useCode + ".NAME", null, locale));
                    purposeOfUseCode.setOriginalText(messageSource.getMessage(useCode + ".DESCRIPTION", null, locale));
                }
                ConsentTermsVersions consentTerm = consentAttestationDto.getConsentTermsVersions();
                consentTerm.setConsentTermsText(messageSource.getMessage("CONSENT.TERMS.TEXT", null, locale));
            }

            return consentAttestationDto;
        } else
            throw new InternalServerErrorException("Consent Attestation Dto Not Found");
    }

    @RequestMapping(value = "consents/{consentId}/unattested", method = RequestMethod.GET)
    public byte[] getUnAttestedConsentPDF(Principal principal, @PathVariable("consentId") Long consentId) throws ConsentGenException {
        final Long patientId = patientService.findIdByUsername(principal.getName());

        if (consentService.isConsentBelongToThisUser(consentId, patientId) && consentService.getConsentStatus(consentId).equals(ConsentStatus.CONSENT_SAVED)) {
            ConsentPdfDto consentPdfDto = consentService.findConsentPdfDto(consentId);
            return consentPdfDto.getContent();
        } else
            throw new InternalServerErrorException("Consent Attestation PDF Not Found");
    }

    @RequestMapping(value = "consents/{consentId}/attested/download", method = RequestMethod.GET)
    public byte[] getAttestedConsent(Principal principal, @PathVariable("consentId") Long consentId) throws ConsentGenException {
        final Long patientId = patientService.findIdByUsername(principal.getName());
        if (consentService.isConsentBelongToThisUser(consentId, patientId)
                &&( consentService.getConsentStatus(consentId).equals(ConsentStatus.CONSENT_SIGNED) || consentService.getConsentStatus(consentId).equals(ConsentStatus.REVOCATION_REVOKED))) {
            return consentService.getAttestedConsentPdf(consentId);
        } else
            throw new InternalServerErrorException("Resource Not Found");
    }

    @RequestMapping(value = "consents/{consentId}/revoked/download", method = RequestMethod.GET)
    public byte[] getAttestedConsentRevoked(Principal principal, @PathVariable("consentId") Long consentId) throws ConsentGenException {
        final Long patientId = patientService.findIdByUsername(principal.getName());
        if (consentService.isConsentBelongToThisUser(consentId, patientId)
                && consentService.getConsentStatus(consentId).equals(ConsentStatus.REVOCATION_REVOKED)) {
            return consentService.getAttestedConsentRevokedPdf(consentId);
        } else
            throw new InternalServerErrorException("Resource Not Found");
    }

    @RequestMapping(value = "consents/{consentId}/revokeConsent", method = RequestMethod.GET)
    public ConsentRevocationAttestationDto getConsentRevocationAttestationDto(Principal principal, @PathVariable("consentId") Long consentId) throws ConsentGenException {
        final Long patientId = patientService.findIdByUsername(principal.getName());
        //TODO (#11): Move check for consent belonging to this user and consent signed stage to service
        if ((consentService.isConsentBelongToThisUser(consentId, patientId))) {
            return consentService.getConsentRevocationAttestationDto(principal.getName(),consentId);
        } else
            throw new InternalServerErrorException("Consent Revocation Attestation Dto Not Found");
    }

    @RequestMapping(value = "consents/exportConsentDirective/{consentId}", method = RequestMethod.GET)
    public Map<String, String>  exportConsentDirective(HttpServletRequest request, Principal principal, @PathVariable("consentId") Long consentId) {
        final Long patientId = patientService.findIdByUsername(principal.getName());
        if (consentService.isConsentBelongToThisUser(consentId, patientId)) {
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
