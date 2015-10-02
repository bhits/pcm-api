package gov.samhsa.consent2share.service.account;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.common.UniqueValueGeneratorException;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

@RunWith(MockitoJUnitRunner.class)
public class MrnServiceImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private MrnServiceImpl sut;

	@Test
	public void testGenerateMrn() {
		// Arrange
		final String prefixMock = "PREFIXMOCK";
		ReflectionTestUtils.setField(sut, "prefix", prefixMock);
		when(patientRepository.findAllByMedicalRecordNumber(anyString()))
				.thenReturn(new ArrayList<Patient>(1));

		// Act
		String mrn = sut.generateMrn();

		// Assert
		assertTrue(StringUtils.hasText(mrn));
		assertTrue(mrn.startsWith(prefixMock));
	}

	@Test
	public void testGenerateMrn_Throws_UniqueValueGeneratorException() {
		// Arrange
		final String prefixMock = "PREFIXMOCK";
		ReflectionTestUtils.setField(sut, "prefix", prefixMock);
		Patient patientMock = mock(Patient.class);
		when(patientRepository.findAllByMedicalRecordNumber(anyString()))
				.thenReturn(Arrays.asList(patientMock));
		thrown.expect(UniqueValueGeneratorException.class);

		// Act
		sut.generateMrn();
	}
}
