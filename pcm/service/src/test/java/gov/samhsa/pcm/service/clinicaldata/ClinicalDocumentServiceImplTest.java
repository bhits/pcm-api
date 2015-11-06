package gov.samhsa.pcm.service.clinicaldata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.domain.clinicaldata.ClinicalDocumentRepository;
import gov.samhsa.pcm.domain.patient.Patient;
import gov.samhsa.pcm.domain.patient.PatientRepository;
import gov.samhsa.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.pcm.service.clinicaldata.ClinicalDocumentServiceImpl;
import gov.samhsa.pcm.service.dto.ClinicalDocumentDto;
import gov.samhsa.pcm.service.dto.PatientProfileDto;

import java.util.Arrays;
import java.util.List;

import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class ClinicalDocumentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClinicalDocumentServiceImplTest {
	/** The clinical document repository. */
	@Mock
	ClinicalDocumentRepository clinicalDocumentRepository;

	/** The clinical document type code repository. */
	@Mock
	ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

	/** The model mapper. */
	@Mock
	ModelMapper modelMapper;

	/** The patient repository. */
	@Mock
	PatientRepository patientRepository;

	/** The user context. */
	@Mock
	UserContext userContext;

	/** The validator. */
	@Mock
	Validator validator;

	/** The sut. */
	ClinicalDocumentServiceImpl sut;

	/** The max file size. */
	private Long maxFileSize = (long) 10000000;

	/** The permitted extensions. */
	private String permittedExtensions = "txt,xml";

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		ClinicalDocumentServiceImpl clinicalDocumentServiceImpl = new ClinicalDocumentServiceImpl(
				Long.toString(maxFileSize), permittedExtensions,
				clinicalDocumentRepository, clinicalDocumentTypeCodeRepository,
				modelMapper, patientRepository, userContext, validator);
		clinicalDocumentServiceImpl.afterPropertiesSet();
		sut = spy(clinicalDocumentServiceImpl);
	}

	/**
	 * Test is document extension permitted when extension is permitted.
	 */
	@Test
	public void testIsDocumentExtensionPermittedWhenExtensionIsPermitted() {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getOriginalFilename()).thenReturn("hello.xml");
		assertTrue(sut.isDocumentExtensionPermitted(file));
	}

	/**
	 * Test is document extension permitted when extension is not permitted.
	 */
	@Test
	public void testIsDocumentExtensionPermittedWhenExtensionIsNotPermitted() {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getOriginalFilename()).thenReturn("hello.pdf");
		assertFalse(sut.isDocumentExtensionPermitted(file));
	}

	/**
	 * Test is document extension permitted when file name has no extension.
	 */
	@Test
	public void testIsDocumentExtensionPermittedWhenFileNameHasNoExtension() {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getOriginalFilename()).thenReturn("hello");
		assertFalse(sut.isDocumentExtensionPermitted(file));
	}

	/**
	 * Test is document oversized when file fits.
	 */
	@Test
	public void testIsDocumentOversizedWhenFileFits() {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getSize()).thenReturn((long) 10000);
		assertFalse(sut.isDocumentOversized(file));
	}

	/**
	 * Test is document oversized when file is oversized.
	 */
	@Test
	public void testIsDocumentOversizedWhenFileIsOversized() {
		MultipartFile file = mock(MultipartFile.class);
		when(file.getSize()).thenReturn((long) 1000000000);
		assertTrue(sut.isDocumentOversized(file));
	}

	@Test
	public void testIsDocumentBelongedToThisUserWhenAuthenticationSucceds() {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		ClinicalDocumentDto patientsClinicalDocumentDto = mock(ClinicalDocumentDto.class);
		AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		Patient patient = mock(Patient.class);
		List<ClinicalDocumentDto> clinicaldocumentDtos = Arrays
				.asList(patientsClinicalDocumentDto);
		doReturn("1").when(clinicalDocumentDto).getId();
		doReturn("1").when(patientsClinicalDocumentDto).getId();
		doReturn("Owner").when(currentUser).getUsername();
		doReturn(patient).when(patientRepository).findByUsername("Owner");
		doReturn(clinicaldocumentDtos).when(sut).findDtoByPatient(patient);
		doReturn(currentUser).when(userContext).getCurrentUser();
		assertTrue(sut.isDocumentBelongsToThisUser(clinicalDocumentDto));
	}

	@Test
	public void testIsDocumentBelongedToThisUserWhenAuthenticationFailed() {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		ClinicalDocumentDto patientsClinicalDocumentDto = mock(ClinicalDocumentDto.class);
		AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		Patient patient = mock(Patient.class);
		List<ClinicalDocumentDto> clinicaldocumentDtos = Arrays
				.asList(patientsClinicalDocumentDto);
		doReturn("1").when(clinicalDocumentDto).getId();
		doReturn("2").when(patientsClinicalDocumentDto).getId();
		doReturn("Owner").when(currentUser).getUsername();
		doReturn(patient).when(patientRepository).findByUsername("Owner");
		doReturn(clinicaldocumentDtos).when(sut).findDtoByPatient(patient);
		doReturn(currentUser).when(userContext).getCurrentUser();
		assertFalse(sut.isDocumentBelongsToThisUser(clinicalDocumentDto));
	}

	@Test
	public void testFindDtoByPatientDto() {
		PatientProfileDto patientDto = mock(PatientProfileDto.class);
		Patient patient = mock(Patient.class);
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		List<ClinicalDocumentDto> dtos = Arrays.asList(clinicalDocumentDto);
		when(patientDto.getUsername()).thenReturn("user1");
		when(patientRepository.findByUsername("user1")).thenReturn(patient);
		doReturn(dtos).when(sut).findDtoByPatient(patient);
		assertEquals(dtos, sut.findDtoByPatientDto(patientDto));
	}

}
