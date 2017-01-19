package gov.samhsa.c2s.pcm.service.clinicaldata;

import gov.samhsa.c2s.pcm.config.PcmProperties;
import gov.samhsa.c2s.pcm.domain.clinicaldata.ClinicalDocument;
import gov.samhsa.c2s.pcm.domain.clinicaldata.ClinicalDocumentRepository;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.domain.patient.PatientRepository;
import gov.samhsa.c2s.pcm.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.c2s.pcm.service.dto.ClinicalDocumentDto;
import gov.samhsa.c2s.pcm.service.dto.PatientProfileDto;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * The Class ClinicalDocumentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClinicalDocumentServiceImplTest {
    /**
     * The clinical document repository.
     */
    @Mock
    ClinicalDocumentRepository clinicalDocumentRepository;

    /**
     * The clinical document type code repository.
     */
    @Mock
    ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;

    /**
     * The model mapper.
     */
    @Mock
    ModelMapper modelMapper;

    /**
     * The patient repository.
     */
    @Mock
    PatientRepository patientRepository;

    /**
     * The validator.
     */
    @Mock
    Validator validator;

    /**
     * The sut.
     */
    ClinicalDocumentServiceImpl sut;

    /**
     * The max file size.
     */
    private long maxFileSize = (long) 10000000;

    /**
     * The permitted extensions.
     */
    private String permittedExtensions = "txt,xml";

    /**
     * Sets the up.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        PcmProperties pcmProperties = new PcmProperties();
        PcmProperties.ClinicalData clinicalData = new PcmProperties.ClinicalData();
        clinicalData.setMaximumUploadFileSize(maxFileSize);
        clinicalData.setExtensionsPermittedToUpload(permittedExtensions);
        pcmProperties.setClinicaldata(clinicalData);
        ClinicalDocumentServiceImpl clinicalDocumentServiceImpl = new ClinicalDocumentServiceImpl(
                pcmProperties,
                clinicalDocumentRepository, clinicalDocumentTypeCodeRepository,
                modelMapper, patientRepository, validator);
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

    @Ignore
    public void testIsDocumentBelongedToThisUserWhenAuthenticationSucceds() {
        ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
        ClinicalDocumentDto patientsClinicalDocumentDto = mock(ClinicalDocumentDto.class);
        Patient patient = mock(Patient.class);
        List<ClinicalDocumentDto> clinicaldocumentDtos = Arrays
                .asList(patientsClinicalDocumentDto);
        doReturn("1").when(clinicalDocumentDto).getId();
        doReturn("1").when(patientsClinicalDocumentDto).getId();
        doReturn(patient).when(patientRepository).findByUsername("Owner");
        doReturn(clinicaldocumentDtos).when(sut).findDtoByPatient(patient);
        assertTrue(sut.isDocumentBelongsToThisUser("Owner", clinicalDocumentDto));
    }

    @Ignore
    public void testIsDocumentBelongedToThisUserWhenAuthenticationFailed() {
        ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
        ClinicalDocumentDto patientsClinicalDocumentDto = mock(ClinicalDocumentDto.class);
        Patient patient = mock(Patient.class);
        List<ClinicalDocumentDto> clinicaldocumentDtos = Arrays
                .asList(patientsClinicalDocumentDto);
        doReturn("1").when(clinicalDocumentDto).getId();
        doReturn("2").when(patientsClinicalDocumentDto).getId();
        doReturn(patient).when(patientRepository).findByUsername("Owner");
        doReturn(clinicaldocumentDtos).when(sut).findDtoByPatient(patient);
        assertFalse(sut.isDocumentBelongsToThisUser("Owner", clinicalDocumentDto));
    }

    @Test
    public void testFindClinicalDocumentDtoByPatientId() {
        // Arrange
        PatientProfileDto patientDto = mock(PatientProfileDto.class);
        Patient patient = mock(Patient.class);
        ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
        List<ClinicalDocumentDto> dtos = Arrays.asList(clinicalDocumentDto);
        ClinicalDocument clinicalDocument = mock(ClinicalDocument.class);
        List<ClinicalDocument> clinicalDocuments = Arrays.asList(clinicalDocument);
        when(patientDto.getUsername()).thenReturn("user1");
        Long patientId = 10L;
        when(patientDto.getId()).thenReturn(patientId);
        when(patientRepository.findByUsername("user1")).thenReturn(patient);
        when(patientRepository.findOne(patientId)).thenReturn(patient);
        when(clinicalDocumentRepository.findByPatientId(patientId)).thenReturn(clinicalDocuments);
        doReturn(dtos).when(sut).findDtoByPatient(patient);

        // Act
        final List<ClinicalDocumentDto> response = sut.findClinicalDocumentDtoByPatientId(patientId);

        // Assert
        assertEquals(dtos, response);
    }

}
