package gov.samhsa.consent2share.service.consentexport;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent.ConsentBuilder;
import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent2share.domain.consent.Consent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsentExportServiceImplTest {

	@InjectMocks
	ConsentExportServiceImpl sut;

	@Mock
	ConsentBuilder consentBuilderMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testExportConsent2CDAR2() throws ConsentGenException {
		// Arrange
		long consentId = 1;
		String cdar2Mock = "cdar2";
		when(consentBuilderMock.buildConsent2Cdar2(anyLong())).thenReturn(
				cdar2Mock);

		// Act
		String cdar2 = sut.exportConsent2CDAR2(consentId);

		// Assert
		assertEquals(cdar2Mock, cdar2);
	}

	@Test
	public void testExportConsent2CDAR2_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		thrown.expect(ConsentGenException.class);
		long consentId = 1;
		when(consentBuilderMock.buildConsent2Cdar2(anyLong())).thenThrow(
				new ConsentGenException("Error in export consent to cdar2"));

		// Act
		sut.exportConsent2CDAR2(consentId);

		// Assert
	}

	@Test
	public void testExportConsent2XACML() throws ConsentGenException {
		// Arrange
		long consentId = 1;
		String xacmlMock = "xacml";
		when(consentBuilderMock.buildConsent2Xacml(anyLong())).thenReturn(
				xacmlMock);

		// Act
		String xacml = sut.exportConsent2XACML(consentId);

		// Assert
		assertEquals(xacmlMock, xacml);
	}

	@Test
	public void testExportConsent2XACML_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		thrown.expect(ConsentGenException.class);
		long consentId = 1;
		when(consentBuilderMock.buildConsent2Xacml(anyLong())).thenThrow(
				new ConsentGenException("Error in export consent to cdar2"));

		// Act
		sut.exportConsent2XACML(consentId);

		// Assert
	}

	@Test
	public void testExportConsent2XACML_Object() throws ConsentGenException {
		// Arrange
		Consent consent = mock(Consent.class);
		String xacmlMock = "xacml";
		when(consentBuilderMock.buildConsent2Xacml(consent)).thenReturn(
				xacmlMock);

		// Act
		String xacml = sut.exportConsent2XACML(consent);

		// Assert
		assertEquals(xacmlMock, xacml);
	}

	@Test
	public void testExportConsent2XACML_Object_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		thrown.expect(ConsentGenException.class);
		Consent consent = mock(Consent.class);
		when(consentBuilderMock.buildConsent2Xacml(consent)).thenThrow(
				new ConsentGenException("Error in export consent to cdar2"));

		// Act
		sut.exportConsent2XACML(consent);

		// Assert
	}

	@Test
	public void testExportConsent2XACMLPdfConsentFrom_Object()
			throws ConsentGenException {
		// Arrange
		Consent consent = mock(Consent.class);
		String xacmlMock = "xacml";
		when(consentBuilderMock.buildConsent2XacmlPdfConsentFrom(consent))
				.thenReturn(xacmlMock);

		// Act
		String xacml = sut.exportConsent2XacmlPdfConsentFrom(consent);

		// Assert
		assertEquals(xacmlMock, xacml);
	}

	@Test
	public void testExportConsent2XACMLPdfConsentFrom_Object_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		thrown.expect(ConsentGenException.class);
		Consent consent = mock(Consent.class);
		when(consentBuilderMock.buildConsent2XacmlPdfConsentFrom(consent))
				.thenThrow(
						new ConsentGenException(
								"Error in export consent to cdar2"));

		// Act
		sut.exportConsent2XacmlPdfConsentFrom(consent);

		// Assert
	}

	@Test
	public void testExportConsent2XACMLPdfConsentTo_Object()
			throws ConsentGenException {
		// Arrange
		Consent consent = mock(Consent.class);
		String xacmlMock = "xacml";
		when(consentBuilderMock.buildConsent2XacmlPdfConsentTo(consent))
				.thenReturn(xacmlMock);

		// Act
		String xacml = sut.exportConsent2XacmlPdfConsentTo(consent);

		// Assert
		assertEquals(xacmlMock, xacml);
	}

	@Test
	public void testExportConsent2XACMLPdfConsentTo_Object_ConsentGenException()
			throws ConsentGenException {
		// Arrange
		thrown.expect(ConsentGenException.class);
		Consent consent = mock(Consent.class);
		when(consentBuilderMock.buildConsent2XacmlPdfConsentTo(consent))
				.thenThrow(
						new ConsentGenException(
								"Error in export consent to cdar2"));

		// Act
		sut.exportConsent2XacmlPdfConsentTo(consent);

		// Assert
	}
}
