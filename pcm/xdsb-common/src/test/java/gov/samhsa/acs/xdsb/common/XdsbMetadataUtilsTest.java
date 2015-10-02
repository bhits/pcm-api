package gov.samhsa.acs.xdsb.common;

import static org.junit.Assert.assertEquals;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.xdsb.common.XdsbMetadataUtils;

import java.io.IOException;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;

import org.junit.Before;
import org.junit.Test;

public class XdsbMetadataUtilsTest {

	private FileReader fileReader;
	private SimpleMarshaller marshaller;

	@Before
	public void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		marshaller = new SimpleMarshallerImpl();
	}

	@Test
	public void testExtractDocumentEntryStatus()
			throws SimpleMarshallerException, IOException {
		// Arrange
		final String expectedResponse = "('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')";
		final AdhocQueryRequest requestMock = marshaller.unmarshalFromXml(
				AdhocQueryRequest.class,
				fileReader.readFile("unitTestAdhocQueryRequest.xml"));

		// Act
		final String actualResponse = XdsbMetadataUtils
				.extractDocumentEntryStatus(requestMock).get();

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testExtractFormatCode() throws SimpleMarshallerException,
			IOException {
		// Arrange
		final String expectedResponse = "'2.16.840.1.113883.10.20.1^^HITSP'";
		final AdhocQueryRequest requestMock = marshaller.unmarshalFromXml(
				AdhocQueryRequest.class,
				fileReader.readFile("unitTestAdhocQueryRequest.xml"));

		// Act
		final String actualResponse = XdsbMetadataUtils.extractFormatCode(
				requestMock).get();

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testExtractPatientId() throws SimpleMarshallerException,
	IOException {
		// Arrange
		final String expectedResponse = "'1c5c59f0-5788-11e3-84b3-00155d3a2124^^^&2.16.840.1.113883.4.357&ISO'";
		final AdhocQueryRequest requestMock = marshaller.unmarshalFromXml(
				AdhocQueryRequest.class,
				fileReader.readFile("unitTestAdhocQueryRequest.xml"));

		// Act
		final String actualResponse = XdsbMetadataUtils.extractPatientId(
				requestMock).get();

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testExtractResponseOptionReturnType()
			throws SimpleMarshallerException, IOException {
		// Arrange
		final String expectedResponse = "LeafClass";
		final AdhocQueryRequest requestMock = marshaller.unmarshalFromXml(
				AdhocQueryRequest.class,
				fileReader.readFile("unitTestAdhocQueryRequest.xml"));

		// Act
		final String actualResponse = XdsbMetadataUtils
				.extractResponseOptionReturnType(requestMock).get();

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testExtractSlotValue() throws SimpleMarshallerException,
	IOException {
		// Arrange
		final String expectedResponse = "'1c5c59f0-5788-11e3-84b3-00155d3a2124^^^&2.16.840.1.113883.4.357&ISO'";
		final AdhocQueryRequest requestMock = marshaller.unmarshalFromXml(
				AdhocQueryRequest.class,
				fileReader.readFile("unitTestAdhocQueryRequest.xml"));

		// Act
		final String actualResponse = XdsbMetadataUtils.extractSlotValue(
				requestMock, "$XDSDocumentEntryPatientId").get();

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}
}
