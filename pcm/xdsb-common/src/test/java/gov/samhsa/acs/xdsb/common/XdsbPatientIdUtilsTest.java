package gov.samhsa.acs.xdsb.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XdsbPatientIdUtilsTest {

	@Test
	public void testToFullyQualifiedPatientIdentifier() {
		// Arrange
		final String patientIdMock = "patientIdMock";
		final String domainIdMock = "domainIdMock";
		final String expectedResponse = "patientIdMock^^^&domainIdMock&ISO";

		// Act
		final String actual = XdsbPatientIdUtils
				.toFullyQualifiedPatientIdentifier(patientIdMock, domainIdMock);

		// Assert
		assertEquals(expectedResponse, actual);
	}

	@Test
	public void testToFullyQualifiedPatientIdentifierWrappedWithSingleQuotes() {
		// Arrange
		final String patientIdMock = "patientIdMock";
		final String domainIdMock = "domainIdMock";
		final String expectedResponse = "'patientIdMock^^^&domainIdMock&ISO'";

		// Act
		final String actual = XdsbPatientIdUtils
				.toFullyQualifiedPatientIdentifierWrappedWithSingleQuotes(
						patientIdMock, domainIdMock);

		// Assert
		assertEquals(expectedResponse, actual);
	}

	@Test
	public void testToFullyQualifiedPatientIdentifierWrappedWithSingleQuotesXmlEncoded() {
		// Arrange
		final String patientIdMock = "patientIdMock";
		final String domainIdMock = "domainIdMock";
		final String expectedResponse = "'patientIdMock^^^&amp;domainIdMock&amp;ISO'";

		// Act
		final String actual = XdsbPatientIdUtils
				.toFullyQualifiedPatientIdentifierWrappedWithSingleQuotesXmlEncoded(
						patientIdMock, domainIdMock);

		// Assert
		assertEquals(expectedResponse, actual);
	}

	@Test
	public void testToFullyQualifiedPatientIdentifierXmlEncoded() {
		// Arrange
		final String patientIdMock = "patientIdMock";
		final String domainIdMock = "domainIdMock";
		final String expectedResponse = "patientIdMock^^^&amp;domainIdMock&amp;ISO";

		// Act
		final String actual = XdsbPatientIdUtils
				.toFullyQualifiedPatientIdentifierXmlEncoded(patientIdMock,
						domainIdMock);

		// Assert
		assertEquals(expectedResponse, actual);
	}

}
