package gov.samhsa.pcm.web.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.acs.common.validation.XmlValidation;
import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.pcm.infrastructure.security.AccessReferenceMapper;
import gov.samhsa.pcm.infrastructure.security.ClamAVService;
import gov.samhsa.pcm.infrastructure.security.RecaptchaService;
import gov.samhsa.pcm.service.clinicaldata.ClinicalDocumentService;
import gov.samhsa.pcm.service.dto.ClinicalDocumentDto;
import gov.samhsa.pcm.service.dto.LookupDto;
import gov.samhsa.pcm.service.dto.PatientProfileDto;
import gov.samhsa.pcm.service.patient.PatientService;
import gov.samhsa.pcm.service.reference.ClinicalDocumentTypeCodeService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.owasp.esapi.errors.AccessControlException;
import org.slf4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ClinicalDocumentControllerTest {

	@Mock
	ClinicalDocumentService clinicalDocumentService;

	@Mock
	PatientService patientService;

	@Mock
	ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;

	@Mock
	UserContext userContext;

	@Mock
	AuthenticatedUser authenticatedUser;
	
	@Mock 
	AccessReferenceMapper accessReferenceMapper;
	
	@Mock
	ClamAVService clamAVUtil;
	
	@Mock
	RecaptchaService recaptchaUtil;
	
	@Mock
	XmlValidation xmlValidator;
	
	@Mock
	EventService eventService;
	
	@Mock
	private Logger logger;
	
	ClinicalDocumentController sut;
	
	ClinicalDocumentController clinicalDocumentController;

	MockMvc mockMvc;

	@Before
	public void before() throws AccessControlException {
		clinicalDocumentController=new ClinicalDocumentController(clinicalDocumentService, patientService, clinicalDocumentTypeCodeService, userContext, accessReferenceMapper, clamAVUtil, recaptchaUtil, eventService, xmlValidator);
		ReflectionTestUtils.setField(clinicalDocumentController, "logger", logger);
		sut=spy(clinicalDocumentController);
		mockMvc = MockMvcBuilders.standaloneSetup(
				this.sut).build();
		doReturn((long)1).when(accessReferenceMapper).getDirectReference("ScrambledText");
		when(userContext.getCurrentUser()).thenReturn(authenticatedUser);
		when(authenticatedUser.getUsername()).thenReturn("albert.smith");
	}

	@Test
	public void testDocumentHome() throws Exception {
		mockMvc.perform(get("/patients/medicalinfo.html")).andExpect(
				view().name("views/clinicaldocuments/mymedicalinfo"));

	}

	@Test
	public void testShowClinicalDocuments() throws Exception {
		PatientProfileDto patientProfileDto = mock(PatientProfileDto.class);
		List<ClinicalDocumentDto> clinicaldocumentDtos = new ArrayList<ClinicalDocumentDto>();
		ClinicalDocumentDto notNullClinicalDocumentDto=mock(ClinicalDocumentDto.class);
		LookupDto lookupDto=mock(LookupDto.class);
		when(notNullClinicalDocumentDto.getClinicalDocumentTypeCode()).thenReturn(lookupDto);
		clinicaldocumentDtos.add(notNullClinicalDocumentDto);
		for (int i = 0; i < 3; i++) {
			ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
			clinicaldocumentDtos.add(clinicalDocumentDto);
		}
		List<LookupDto> allDocumentTypeCodes = new ArrayList<LookupDto>();
		for (int i = 0; i < 3; i++) {
			LookupDto clinicalDocumentTypeCode = mock(LookupDto.class);
			allDocumentTypeCodes.add(clinicalDocumentTypeCode);
		}
		when(patientService.findPatientProfileByUsername(anyString()))
				.thenReturn(patientProfileDto);
		when(clinicalDocumentService.findDtoByPatientDto(patientProfileDto))
				.thenReturn(clinicaldocumentDtos);
		when(clinicalDocumentTypeCodeService.findAllClinicalDocumentTypeCodes())
				.thenReturn(allDocumentTypeCodes);

		mockMvc.perform(get("/patients/clinicaldocuments.html")).andExpect(
				view().name("views/clinicaldocuments/secureClinicalDocuments"));

	}
	

	@Test
	public void testDownload_when_authentication_succeeds() throws Exception {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		String s = "mock string";
		byte[] byteArray = s.getBytes();
		when(clinicalDocumentService.findClinicalDocumentDto(anyLong()))
				.thenReturn(clinicalDocumentDto);
		when(
				clinicalDocumentService
						.isDocumentBelongsToThisUser(clinicalDocumentDto))
				.thenReturn(true);
		when(clinicalDocumentDto.getContent()).thenReturn(byteArray);
		mockMvc.perform(
				post("/patients/downloaddoc.html").param("download_id", "ScrambledText"))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/null"));

	}
	
	@Test
	public void testDownload_when_authentication_fails() throws Exception {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		String s = "mock string";
		byte[] byteArray = s.getBytes();
		when(clinicalDocumentService.findClinicalDocumentDto(anyLong()))
				.thenReturn(clinicalDocumentDto);
		when(
				clinicalDocumentService
						.isDocumentBelongsToThisUser(clinicalDocumentDto))
				.thenReturn(false);
		when(clinicalDocumentDto.getContent()).thenReturn(byteArray);
		mockMvc.perform(
				post("/patients/downloaddoc.html").param("download_id", "ScrambledText"))
				.andExpect(status().isOk());
		verify(clinicalDocumentDto,never()).getContent();

	}

	@Test
	public void testRemove_when_authentication_fails() throws Exception {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		when(clinicalDocumentService.findClinicalDocumentDto(anyLong()))
				.thenReturn(clinicalDocumentDto);
		when(
				clinicalDocumentService
						.isDocumentBelongsToThisUser(clinicalDocumentDto))
				.thenReturn(false);
		mockMvc.perform(
				post("/patients/deletedoc.html").param("delete_id", "ScrambledText"))
				.andExpect(redirectedUrl("/patients/clinicaldocuments.html"));
		verify (clinicalDocumentService,never()).deleteClinicalDocument(any(ClinicalDocumentDto.class));
	}
	
	@Test
	public void testRemove_when_authentication_succeeds() throws Exception {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		when(clinicalDocumentService.findClinicalDocumentDto(anyLong()))
				.thenReturn(clinicalDocumentDto);
		when(
				clinicalDocumentService
						.isDocumentBelongsToThisUser(clinicalDocumentDto))
				.thenReturn(true);
		mockMvc.perform(
				post("/patients/deletedoc.html").param("delete_id", "ScrambledText"))
				.andExpect(redirectedUrl("/patients/clinicaldocuments.html"));
		verify (clinicalDocumentService).deleteClinicalDocument(any(ClinicalDocumentDto.class));
	}
	
	@Test
	public void testSecureClinicalDocumentsUploadWhenAllChecksSucced() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		doReturn(true).when(xmlValidator).validate(any(InputStream.class));
		doReturn("true").when(sut).scanMultipartFile(file);
		doReturn(false).when(clinicalDocumentService).isDocumentOversized(file);
		doReturn(true).when(clinicalDocumentService).isDocumentExtensionPermitted(file);
		doReturn(true).when(recaptchaUtil).checkAnswer(anyString(), anyString(), anyString());
		mockMvc.perform(fileUpload("/patients/clinicaldocuments.html").file(file)
				.param("name", "mocked_name")
				.param("description", "mocked_description")
				.param("documentType", "mocked_type")
				.param("recaptcha_challenge_field", "recaptcha_challenge_field")
				.param("recaptcha_response_field", "recaptcha_response_field"))
			.andExpect(view().name("redirect:/patients/clinicaldocuments.html"));
	}
	
//	@Test
//	public void testSecureClinicalDocumentsUploadWhenFileIsMalicious() throws Exception {
//		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
//		doReturn("false").when(sut).scanMultipartFile(file);
//		doReturn(false).when(clinicalDocumentService).isDocumentOversized(file);
//		doReturn(true).when(clinicalDocumentService).isDocumentExtensionPermitted(file);
//		doReturn(true).when(recaptchaUtil).checkAnswer(anyString(), anyString(), anyString());
//		mockMvc.perform(fileUpload("/patients/clinicaldocuments.html").file(file)
//				.param("name", "mocked_name")
//				.param("description", "mocked_description")
//				.param("documentType", "mocked_type"))
//			.andExpect(view().name("redirect:/patients/clinicaldocuments.html?notify=virus_detected"));
//	}
	
	/*@Test
	public void testSecureClinicalDocumentsUploadWhenClamAVNotWork() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		doReturn("error").when(sut).scanMultipartFile(file);
		doReturn(false).when(clinicalDocumentService).isDocumentOversized(file);
		doReturn(true).when(clinicalDocumentService).isDocumentExtensionPermitted(file);
		doReturn(true).when(recaptchaUtil).checkAnswer(anyString(), anyString(), anyString());
		mockMvc.perform(fileUpload("/patients/clinicaldocuments.html").file(file)
				.param("name", "mocked_name")
				.param("description", "mocked_description")
				.param("documentType", "mocked_type"))
			.andExpect(view().name("redirect:/patients/clinicaldocuments.html?notify=error"));
	}
	
	@Test
	public void testSecureClinicalDocumentsUploadWhenClamAVNotWork_Throws_ClamAVClientNotAvailableException() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		when(clamAVUtil.fileScanner(any(InputStream.class))).thenThrow(ClamAVClientNotAvailableException.class);
		doReturn(false).when(clinicalDocumentService).isDocumentOversized(file);
		doReturn(true).when(clinicalDocumentService).isDocumentExtensionPermitted(file);
		doReturn(true).when(recaptchaUtil).checkAnswer(anyString(), anyString(), anyString());
		mockMvc.perform(fileUpload("/patients/clinicaldocuments.html").file(file)
				.param("name", "mocked_name")
				.param("description", "mocked_description")
				.param("documentType", "mocked_type"))
			.andExpect(view().name("redirect:/patients/clinicaldocuments.html?notify=error"));
		verify(logger, times(1)).error(anyString());
	}
	
	@Test
	public void testSecureClinicalDocumentsUploadWhenClamAVNotWork_Throws_Exception() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		when(clamAVUtil.fileScanner(any(InputStream.class))).thenThrow(Exception.class);
		doReturn(false).when(clinicalDocumentService).isDocumentOversized(file);
		doReturn(true).when(clinicalDocumentService).isDocumentExtensionPermitted(file);
		doReturn(true).when(recaptchaUtil).checkAnswer(anyString(), anyString(), anyString());
		mockMvc.perform(fileUpload("/patients/clinicaldocuments.html").file(file)
				.param("name", "mocked_name")
				.param("description", "mocked_description")
				.param("documentType", "mocked_type"))
			.andExpect(view().name("redirect:/patients/clinicaldocuments.html?notify=error"));
		verify(logger, times(1)).error(anyString());
	}*/
	
	@Test
	public void testSecureClinicalDocumentsUploadWhenFileIsOversized() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		doReturn("true").when(sut).scanMultipartFile(file);
		doReturn(true).when(clinicalDocumentService).isDocumentOversized(file);
		doReturn(true).when(clinicalDocumentService).isDocumentExtensionPermitted(file);
		doReturn(true).when(recaptchaUtil).checkAnswer(anyString(), anyString(), anyString());
		mockMvc.perform(fileUpload("/patients/clinicaldocuments.html").file(file)
				.param("name", "mocked_name")
				.param("description", "mocked_description")
				.param("documentType", "mocked_type"))
			.andExpect(view().name("redirect:/patients/clinicaldocuments.html?notify=size_over_limits"));
	}
	
	@Test
	public void testSecureClinicalDocumentsUploadWhenFileExtensionIsNotPermitted() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		doReturn("true").when(sut).scanMultipartFile(file);
		doReturn(false).when(clinicalDocumentService).isDocumentOversized(file);
		doReturn(false).when(clinicalDocumentService).isDocumentExtensionPermitted(file);
		doReturn(true).when(recaptchaUtil).checkAnswer(anyString(), anyString(), anyString());
		mockMvc.perform(fileUpload("/patients/clinicaldocuments.html").file(file)
				.param("name", "mocked_name")
				.param("description", "mocked_description")
				.param("documentType", "mocked_type"))
			.andExpect(view().name("redirect:/patients/clinicaldocuments.html?notify=extension_not_permitted"));
	}
	
	@Test
	public void testSecureClinicalDocumentsUploadWhenWrongCaptchaIsEnterred() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		doReturn("true").when(sut).scanMultipartFile(file);
		doReturn(false).when(clinicalDocumentService).isDocumentOversized(file);
		doReturn(true).when(clinicalDocumentService).isDocumentExtensionPermitted(file);
		doReturn(false).when(recaptchaUtil).checkAnswer(anyString(), anyString(), anyString());
		mockMvc.perform(fileUpload("/patients/clinicaldocuments.html").file(file)
				.param("name", "mocked_name")
				.param("description", "mocked_description")
				.param("documentType", "mocked_type"))
			.andExpect(view().name("redirect:/patients/clinicaldocuments.html?notify=wrong_captcha"));
	}
	
	@Test
	public void testScanMultipartFileWhenFileIsClean() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		doReturn(true).when(clamAVUtil).fileScanner(any(InputStream.class));
		assertTrue(clinicalDocumentController.scanMultipartFile(file).toString().equals("true"));
	}
	
	@Test
	public void testScanMultipartFileWhenFileIsNotClean() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		doReturn(false).when(clamAVUtil).fileScanner(any(InputStream.class));
		assertFalse(clinicalDocumentController.scanMultipartFile(file).toString().equals("true"));
	}
	
	@Test
	public void testShowSecureClinicalDocuments() throws Exception {
		PatientProfileDto patientProfileDto = mock(PatientProfileDto.class);
		List<ClinicalDocumentDto> clinicaldocumentDtos = new ArrayList<ClinicalDocumentDto>();
		ClinicalDocumentDto notNullClinicalDocumentDto=mock(ClinicalDocumentDto.class);
		LookupDto lookupDto=mock(LookupDto.class);
		when(notNullClinicalDocumentDto.getClinicalDocumentTypeCode()).thenReturn(lookupDto);
		clinicaldocumentDtos.add(notNullClinicalDocumentDto);
		for (int i = 0; i < 3; i++) {
			ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
			clinicaldocumentDtos.add(clinicalDocumentDto);
		}
		List<LookupDto> allDocumentTypeCodes = new ArrayList<LookupDto>();
		for (int i = 0; i < 3; i++) {
			LookupDto clinicalDocumentTypeCode = mock(LookupDto.class);
			allDocumentTypeCodes.add(clinicalDocumentTypeCode);
		}
		when(patientService.findPatientProfileByUsername(anyString()))
				.thenReturn(patientProfileDto);
		when(clinicalDocumentService.findDtoByPatientDto(patientProfileDto))
				.thenReturn(clinicaldocumentDtos);
		when(clinicalDocumentTypeCodeService.findAllClinicalDocumentTypeCodes())
				.thenReturn(allDocumentTypeCodes);

		mockMvc.perform(get("/patients/clinicaldocuments.html")).andExpect(
				view().name("views/clinicaldocuments/secureClinicalDocuments"));

	}

}
