/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p>
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
 * <p>
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

import gov.samhsa.mhc.common.validation.XmlValidation;
import gov.samhsa.mhc.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.mhc.pcm.infrastructure.security.ClamAVClientNotAvailableException;
import gov.samhsa.mhc.pcm.infrastructure.security.ClamAVService;
import gov.samhsa.mhc.pcm.infrastructure.security.InfectedFileException;
import gov.samhsa.mhc.pcm.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.mhc.pcm.infrastructure.securityevent.FileUploadedEvent;
import gov.samhsa.mhc.pcm.infrastructure.securityevent.MaliciousFileDetectedEvent;
import gov.samhsa.mhc.pcm.service.clinicaldata.ClinicalDocumentService;
import gov.samhsa.mhc.pcm.service.dto.CCDDto;
import gov.samhsa.mhc.pcm.service.dto.ClinicalDocumentDto;
import gov.samhsa.mhc.pcm.service.dto.LookupDto;
import gov.samhsa.mhc.pcm.service.exception.*;
import gov.samhsa.mhc.pcm.service.patient.PatientService;
import gov.samhsa.mhc.pcm.service.reference.ClinicalDocumentTypeCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class ClinicalDocumentController.
 */
@RestController
@RequestMapping("/patients")
public class ClinicalDocumentRestController {

    /**
     * The Constant C32_CDA_XSD_PATH.
     */
    public static final String C32_CDA_XSD_PATH = "schema/cdar2c32/infrastructure/cda/";
    /**
     * The Constant C32_CDA_XSD_NAME.
     */
    public static final String C32_CDA_XSD_NAME = "C32_CDA.xsd";
    /**
     * The logger.
     */
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * The clinical document service.
     */
    @Autowired
    private ClinicalDocumentService clinicalDocumentService;
    /**
     * The patient service.
     */
    @Autowired
    private PatientService patientService;
    /**
     * The clinical document type code service.
     */
    @Autowired
    private ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;
    @Autowired
    private ClamAVService clamAVUtil;
    @Autowired
    private EventService eventService;
    /**
     * The xml validator.
     */
    private XmlValidation xmlValidator;

    /**
     * List clinical documents.
     */
    @RequestMapping(value = "clinicaldocuments", method = RequestMethod.GET)
    public List<ClinicalDocumentDto> listClinicalDocuments() {
        // FIXME: remove this line when patient creation concept in PCM is finalized
        final Long patientId = patientService.createNewPatientWithOAuth2AuthenticationIfNotExists();
        List<ClinicalDocumentDto> clinicaldocumentDtos = clinicalDocumentService
                .findClinicalDocumentDtoByPatientId(patientId);
        return clinicaldocumentDtos;
    }

    /**
     * Upload clinical documents.
     */
    @RequestMapping(value = "clinicaldocuments", method = RequestMethod.POST)
    public void uploadClinicalDocuments(
            Principal principal,
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String documentName,
            @RequestParam("description") String description,
            @RequestParam("documentType") String documentTypeCode) {
        final String username = principal.getName();
        if (scanMultipartFile(file) == "error") {
            throw new InternalServerErrorException("An unknown error has occured.");
        } else {
            if (scanMultipartFile(file) == "false") {
                eventService.raiseSecurityEvent(new MaliciousFileDetectedEvent(request.getRemoteAddr(),
                        username, documentName));
                throw new InfectedFileException("Virus detected");
            }
        }
        if (clinicalDocumentService.isDocumentOversized(file))
            throw new OversizedFileException("Size over limits");
        if (!clinicalDocumentService.isDocumentExtensionPermitted(file))
            throw new InvalidFileExtensionException("Extension not permitted");
        try {
            xmlValidator.validate(file.getInputStream());
        } catch (Exception e) {
            throw new InvalidClinicalDocumentException("Invalid C32");
        }

        ClinicalDocumentDto clinicalDocumentDto = new ClinicalDocumentDto();

        try {
            clinicalDocumentDto.setName(documentName);
            clinicalDocumentDto.setDescription(description);
            clinicalDocumentDto.setContent(file.getBytes());
            clinicalDocumentDto.setFilename(file.getOriginalFilename());
            clinicalDocumentDto.setContentType(file.getContentType());
            clinicalDocumentDto.setDocumentSize(file.getSize());
            clinicalDocumentDto.setPatientId(patientService
                    .findIdByUsername(username));

            LookupDto clinicalDocumentTypeCode = new LookupDto();
            clinicalDocumentTypeCode.setCode(documentTypeCode);
            clinicalDocumentDto
                    .setClinicalDocumentTypeCode(clinicalDocumentTypeCode);

            clinicalDocumentService.saveClinicalDocument(clinicalDocumentDto);
            eventService.raiseSecurityEvent(new FileUploadedEvent(request
                    .getRemoteAddr(), username, documentName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes clinical documents.
     */
    @RequestMapping(value = "clinicaldocuments/{documentId}", method = RequestMethod.DELETE)
    public void removeClinicalDocument(Principal principal, @PathVariable("documentId") Long documentId) {
        ClinicalDocumentDto clinicalDocumentDto = clinicalDocumentService
                .findClinicalDocumentDto(principal.getName(), documentId);

        if (clinicalDocumentService
                .isDocumentBelongsToThisUser(principal.getName(), clinicalDocumentDto)) {
            clinicalDocumentService.deleteClinicalDocument(clinicalDocumentDto);
        } else {
            throw new InvalidDeleteDocumentRequestException("Error: Unable to delete this document because it is not belong to user.");
        }
    }

    /**
     * Download.
     */
    @RequestMapping(value = "clinicaldocuments/{documentId}", method = RequestMethod.GET)
    public Map<String, String> downloadClinicalDocument(Principal principal,
                                                        @PathVariable("documentId") Long documentId,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        ClinicalDocumentDto clinicalDocumentDto = clinicalDocumentService
                .findClinicalDocumentDto(principal.getName(), documentId);
        Map<String, String> map = new HashMap<String, String>();
        if (clinicalDocumentService
                .isDocumentBelongsToThisUser(principal.getName(), clinicalDocumentDto)) {
            try {
                byte[] fileContent = clinicalDocumentDto.getContent();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_XML);
                map.put("data", new String(fileContent));

                eventService
                        .raiseSecurityEvent(new FileDownloadedEvent(request
                                .getRemoteAddr(), principal.getName(), "Clinical_Document_"
                                + documentId));
            } catch (Exception e) {
                throw new InternalServerErrorException("Resource Not Found");
            }
        }
        return map;
    }

    @RequestMapping(value = "clinicaldocuments/ccd/{documentId}", method = RequestMethod.GET)
    public CCDDto getClinicalDocument(Principal principal, @PathVariable("documentId") Long documentId) {
        return clinicalDocumentService.findCCDDto(principal.getName(), documentId);
    }

    //FIXME
    @RequestMapping(value = "clinicaldocuments/ccd/{patientUserName}/{documentId}", method = RequestMethod.GET)
    public CCDDto getClinicalDocumentByUserName(@PathVariable("patientUserName") String patientUserName, @PathVariable("documentId") Long documentId) {
        return clinicalDocumentService.findCCDDto(patientUserName, documentId);
    }

    String scanMultipartFile(MultipartFile file) {
        Boolean isItClean = null;
        try (InputStream inputStream = file.getInputStream()) {
            isItClean = clamAVUtil.fileScanner(inputStream);
        } catch (ClamAVClientNotAvailableException e) {
            logger.error(e.getMessage());
            return "error";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "error";
        }
        return isItClean.toString();
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        this.xmlValidator = new XmlValidation(this.getClass().getClassLoader()
                .getResourceAsStream(C32_CDA_XSD_PATH + C32_CDA_XSD_NAME),
                C32_CDA_XSD_PATH);
    }
}
