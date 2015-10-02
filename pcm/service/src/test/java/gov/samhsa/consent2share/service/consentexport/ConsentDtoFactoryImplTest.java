package gov.samhsa.consent2share.service.consentexport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.consent.ConsentDto;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsentDtoFactoryImplTest {

	@Mock
	ConsentRepository consentRepository;

	@Mock
	ConsentExportMapper consentExportMapper;

	@InjectMocks
	ConsentDtoFactoryImpl sut;

	@Test
	public void testCreateConsentDto_Repository_Is_Called() {
		// Arrange
		long consentId = 1;
		Consent consent = mock(Consent.class);
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		ConsentDto consentDto = mock(ConsentDto.class);
		when(consentExportMapper.map(consent)).thenReturn(consentDto);

		// Act
		sut.createConsentDto(consentId);

		// Assert
		verify(consentRepository).findOne(consentId);
	}

	@Test
	public void testCreateConsentDto_Returns_Successfully() {
		// Arrange
		long consentId = 1;
		Consent consent = mock(Consent.class);
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		ConsentDto consentDtoMock = mock(ConsentDto.class);
		when(consentExportMapper.map(consent)).thenReturn(consentDtoMock);

		// Act
		ConsentDto consentDto = sut.createConsentDto(consentId);

		// Assert
		assertEquals(consentDtoMock, consentDto);
	}

	@Test
	public void testCreateConsentDto_ObjectReturns_Successfully() {
		// Arrange
		Consent consent = mock(Consent.class);

		ConsentDto consentDtoMock = mock(ConsentDto.class);
		when(consentExportMapper.map(consent)).thenReturn(consentDtoMock);

		// Act
		ConsentDto consentDto = sut.createConsentDto(consent);

		// Assert
		assertEquals(consentDtoMock, consentDto);
	}

	@Test
	public void testCreateConsentDto_ConsentReturns_Successfully() {
		// Arrange
		ConsentDto consentDtoMock = mock(ConsentDto.class);

		Consent consent = new Consent();
		when(consentExportMapper.map(consent)).thenReturn(consentDtoMock);

		// Act
		ConsentDto consentDto = sut.createConsentDto(consent);

		// Assert
		assertEquals(consentDtoMock, consentDto);
	}

	@Test
	public void testCreateConsentDto_Loong_Returns_Successfully() {
		// Arrange
		Long consentId = new Long(1);
		Consent consent = mock(Consent.class);
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		ConsentDto consentDtoMock = mock(ConsentDto.class);
		when(consentExportMapper.map(consent)).thenReturn(consentDtoMock);

		// Act
		ConsentDto consentDto = sut.createConsentDto(consentId);

		// Assert
		assertEquals(consentDtoMock, consentDto);
	}

	@Test
	public void testCreateConsentDto_Null_Returns_Successfully() {
		// Arrange
		Long consentId = null;

		// Act
		ConsentDto consentDto = sut.createConsentDto(consentId);

		// Assert
		assertNull(consentDto);
	}
}
