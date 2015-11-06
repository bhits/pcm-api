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

import gov.samhsa.acs.common.validation.XmlValidation;
import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.pcm.infrastructure.security.AccessReferenceMapper;
import gov.samhsa.pcm.infrastructure.security.ClamAVClientNotAvailableException;
import gov.samhsa.pcm.infrastructure.security.ClamAVService;
import gov.samhsa.pcm.infrastructure.security.RecaptchaService;
import gov.samhsa.pcm.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.pcm.infrastructure.securityevent.FileUploadedEvent;
import gov.samhsa.pcm.service.clinicaldata.ClinicalDocumentService;
import gov.samhsa.pcm.service.dto.ClinicalDocumentDto;
import gov.samhsa.pcm.service.dto.LookupDto;
import gov.samhsa.pcm.service.dto.PatientProfileDto;
import gov.samhsa.pcm.service.patient.PatientService;
import gov.samhsa.pcm.service.reference.ClinicalDocumentTypeCodeService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class ClinicalDocumentController.
 */
@Controller
@RequestMapping("/patients")
public class ClinicalDocumentController implements InitializingBean {

	/** The clinical document service. */
	@Autowired
	ClinicalDocumentService clinicalDocumentService;

	/** The patient service. */
	@Autowired
	PatientService patientService;

	/** The clinical document type code service. */
	@Autowired
	ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/** The access reference mapper. */
	@Autowired
	AccessReferenceMapper accessReferenceMapper;

	@Autowired
	ClamAVService clamAVUtil;

	@Autowired
	RecaptchaService recaptchaUtil;

	@Autowired
	EventService eventService;

	/** The xml validator. */
	private XmlValidation xmlValidator;

	/** The Constant C32_CDA_XSD_PATH. */
	public static final String C32_CDA_XSD_PATH = "schema/cdar2c32/infrastructure/cda/";

	/** The Constant C32_CDA_XSD_NAME. */
	public static final String C32_CDA_XSD_NAME = "C32_CDA.xsd";

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Document home.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/medicalinfo.html")
	public String documentHome(Model model) {
		return "views/clinicaldocuments/mymedicalinfo";
	}

	/**
	 * Show clinical documents.
	 *
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping("/clinicaldocuments.html")
	public String showSecureClinicalDocuments(
			Model model,
			@RequestParam(value = "notify", required = false) String notification) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = currentUser.getUsername();
		PatientProfileDto patientDto = patientService
				.findPatientProfileByUsername(username);
		String captchaString = recaptchaUtil.createSecureRecaptchaHtml();
		List<ClinicalDocumentDto> clinicaldocumentDtos = clinicalDocumentService
				.findDtoByPatientDto(patientDto);
		accessReferenceMapper.setupAccessReferenceMap(clinicaldocumentDtos);
		List<LookupDto> allDocumentTypeCodes = clinicalDocumentTypeCodeService
				.findAllClinicalDocumentTypeCodes();
		model.addAttribute("clinicaldocumentDtos", clinicaldocumentDtos);
		model.addAttribute("allDocumentTypeCodes", allDocumentTypeCodes);
		model.addAttribute("notification", notification);
		model.addAttribute("captcha", captchaString);

		return "views/clinicaldocuments/secureClinicalDocuments";
	}

	/**
	 * Upload clinical documents.
	 *
	 * @param request
	 *            the request
	 * @param clinicalDocumentDto
	 *            the clinical document dto
	 * @param file
	 *            the file
	 * @param documentName
	 *            the document name
	 * @param description
	 *            the description
	 * @param documentTypeCode
	 *            the document type code
	 * @return the string
	 */
	@RequestMapping(value = "/clinicaldocuments.html", method = RequestMethod.POST)
	public String uploadClinicalDocumentsSecurely(
			HttpServletRequest request,
			@ModelAttribute("document") ClinicalDocumentDto clinicalDocumentDto,
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String documentName,
			@RequestParam("description") String description,
			@RequestParam("documentType") String documentTypeCode) {

		/*Commented out temporally for prod server
		if(scanMultipartFile(file)=="error") {
			return "redirect:/patients/clinicaldocuments.html?notify=error";
		}else if (scanMultipartFile(file)=="false") {
			eventService.raiseSecurityEvent(new MaliciousFileDetectedEvent(request.getRemoteAddr(),
					userContext.getCurrentUser().getUsername(),documentName));
			return "redirect:/patients/clinicaldocuments.html?notify=virus_detected";
		}*/
		if (clinicalDocumentService.isDocumentOversized(file))
			return "redirect:/patients/clinicaldocuments.html?notify=size_over_limits";
		if (clinicalDocumentService.isDocumentExtensionPermitted(file) == false)
			return "redirect:/patients/clinicaldocuments.html?notify=extension_not_permitted";
		if (request.getParameter("recaptcha_challenge_field") == null)
			return "redirect:/patients/clinicaldocuments.html?notify=wrong_captcha";
		if (recaptchaUtil.checkAnswer(request.getRemoteAddr(),
				request.getParameter("recaptcha_challenge_field"),
				request.getParameter("recaptcha_response_field")) == false)
			return "redirect:/patients/clinicaldocuments.html?notify=wrong_captcha";
		try {
			xmlValidator.validate(file.getInputStream());
		} catch (Exception e) {
			return "redirect:/patients/clinicaldocuments.html?notify=invalid_c32";
		}

		try {
			AuthenticatedUser currentUser = userContext.getCurrentUser();
			String username = currentUser.getUsername();
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

		return "redirect:/patients/clinicaldocuments.html";
	}

	/**
	 * Download.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param documentId
	 *            the document id
	 * @return the string
	 */
	@RequestMapping(value = "/downloaddoc.html", method = RequestMethod.POST)
	public String download(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("download_id") String documentId) {

		Long directDocumentId = accessReferenceMapper
				.getDirectReference(documentId);
		ClinicalDocumentDto clinicalDocumentDto = clinicalDocumentService
				.findClinicalDocumentDto(directDocumentId);
		if (clinicalDocumentService
				.isDocumentBelongsToThisUser(clinicalDocumentDto)) {
			try {
				response.setHeader(
						"Content-Disposition",
						"attachment;filename=\""
								+ clinicalDocumentDto.getFilename() + "\"");
				response.setHeader("Content-Type", "application/"
						+ clinicalDocumentDto.getContentType());
				OutputStream out = response.getOutputStream();
				response.setContentType(clinicalDocumentDto.getContentType());
				IOUtils.copy(
						new ByteArrayInputStream(clinicalDocumentDto
								.getContent()), out);
				out.flush();
				out.close();
				eventService
						.raiseSecurityEvent(new FileDownloadedEvent(request
								.getRemoteAddr(), userContext.getCurrentUser()
								.getUsername(), "Clinical_Document_"
								+ directDocumentId));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Removes the.
	 *
	 * @param documentId
	 *            the document id
	 * @return the string
	 */
	@RequestMapping(value = "/deletedoc.html", method = RequestMethod.POST)
	public String remove(@RequestParam("delete_id") String documentId) {
		Long directDocumentId = accessReferenceMapper
				.getDirectReference(documentId);
		ClinicalDocumentDto clinicalDocumentDto = clinicalDocumentService
				.findClinicalDocumentDto(directDocumentId);

		if (clinicalDocumentService
				.isDocumentBelongsToThisUser(clinicalDocumentDto)) {
			clinicalDocumentService.deleteClinicalDocument(clinicalDocumentDto);
		}
		return "redirect:/patients/clinicaldocuments.html";
	}

	String scanMultipartFile(MultipartFile file){
		Boolean isItClean=null;
		try (InputStream inputStream = file.getInputStream()){
			isItClean = clamAVUtil.fileScanner(inputStream);
		} catch (ClamAVClientNotAvailableException e){
			logger.error(e.getMessage());
			return "error";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "error";
		}
		return isItClean.toString();
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		this.xmlValidator = new XmlValidation(this.getClass().getClassLoader()
				.getResourceAsStream(C32_CDA_XSD_PATH + C32_CDA_XSD_NAME),
				C32_CDA_XSD_PATH);
	}

	public ClinicalDocumentController(
			ClinicalDocumentService clinicalDocumentService,
			PatientService patientService,
			ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService,
			UserContext userContext,
			AccessReferenceMapper accessReferenceMapper,
			ClamAVService clamAVUtil, RecaptchaService recaptchaUtil,
			EventService eventService, XmlValidation xmlValidator) {
		super();
		this.clinicalDocumentService = clinicalDocumentService;
		this.patientService = patientService;
		this.clinicalDocumentTypeCodeService = clinicalDocumentTypeCodeService;
		this.userContext = userContext;
		this.accessReferenceMapper = accessReferenceMapper;
		this.clamAVUtil = clamAVUtil;
		this.recaptchaUtil = recaptchaUtil;
		this.eventService = eventService;
		this.xmlValidator = xmlValidator;
	}

	@SuppressWarnings("unused")
	private ClinicalDocumentController() {
	}

}
