package gov.samhsa.acs.dss.wsclient;

import static org.junit.Assert.assertEquals;
import gov.samhsa.acs.dss.ws.schema.DSSRequest;
import gov.samhsa.acs.dss.ws.schema.DSSRequestForDirect;
import gov.samhsa.acs.dss.ws.schema.DSSResponse;
import gov.samhsa.acs.dss.ws.schema.DSSResponseForDirect;

import java.util.Properties;

import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class DSSWebServiceClientTest {

	private static final String DOCUMENT_XML_MOCK = "DOCUMENT_XML_MOCK";
	private static final String ENFORCEMENT_POLICIES_XML_MOCK = "ENFORCEMENT_POLICIES_XML_MOCK";
	private static final String SEGMENTED_DOCUMENT_XML_MOCK = "SEGMENTED_DOCUMENT_XML_MOCK";
	private static final String RECIPIENT_EMAIL_ADDRESS = "RECIPIENT_EMAIL_ADDRESS";
	private static final String SENDER_EMAIL_ADDRESS = "SENDER_EMAIL_ADDRESS";
	private static final String XDS_DOCUMENT_ENTRY_UNIQUEID = "XDS_DOCUMENT_ENTRY_UNIQUEID";

	protected static Endpoint ep;
	protected static String address;

	private static final DSSRequest expectedValueOfDSSRequest = new DSSRequest();
	private static final DSSResponse returnedValueOfDSSResponse = new DSSResponse();
	private static final DSSRequestForDirect expectedValueOfDSSRequestForDirect = new DSSRequestForDirect();
	private static final DSSResponseForDirect returnedValueOfDSSResponseForDirect = new DSSResponseForDirect();

	private DSSWebServiceClient sut;

	@Before
	public void setUp() throws Exception {
		final Resource resource = new ClassPathResource(
				"/jettyServerPortForTesing.properties");
		final Properties props = PropertiesLoaderUtils.loadProperties(resource);
		final String portNumber = props
				.getProperty("jettyServerPortForTesing.number");

		address = String.format("http://localhost:%s/services/DSS", portNumber);
		ep = Endpoint.publish(address, new DSSPortTypeImpl());
		sut = new DSSWebServiceClient(address);
	}

	@After
	public void tearDown() throws Exception {
		try {
			ep.stop();
		} catch (final Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	@Test
	public void testSegmentDocument() {
		// Arrange
		expectedValueOfDSSRequest.setDocumentXml(DOCUMENT_XML_MOCK);
		expectedValueOfDSSRequest
		.setEnforcementPoliciesXml(ENFORCEMENT_POLICIES_XML_MOCK);
		DSSPortTypeImpl.expectedValueOfDSSRequest = expectedValueOfDSSRequest;
		returnedValueOfDSSResponse
		.setSegmentedDocumentXml(SEGMENTED_DOCUMENT_XML_MOCK);
		DSSPortTypeImpl.returnedValueOfDSSResponse = returnedValueOfDSSResponse;

		// Act
		final String actualResponse = sut.segmentDocument(
				expectedValueOfDSSRequest).getSegmentedDocumentXml();

		// Assert
		assertEquals(SEGMENTED_DOCUMENT_XML_MOCK, actualResponse);
	}

	@Test(expected = DSSWebServiceClientException.class)
	public void testSegmentDocument_Throws_DSSWebServiceClientException() {
		// Arrange
		ep.stop();
		ep = Endpoint.publish(address, new DSSPortTypeImplThrowingException());

		// Act
		final String actualResponse = sut.segmentDocument(
				expectedValueOfDSSRequest).getSegmentedDocumentXml();

		// Assert
		assertEquals(SEGMENTED_DOCUMENT_XML_MOCK, actualResponse);
	}

	@Test
	public void testSegmentDocumentForDirect() {
		// Arrange
		expectedValueOfDSSRequestForDirect.setDocumentXml(DOCUMENT_XML_MOCK);
		expectedValueOfDSSRequestForDirect
		.setEnforcementPoliciesXml(ENFORCEMENT_POLICIES_XML_MOCK);
		expectedValueOfDSSRequestForDirect
		.setRecipientEmailAddress(RECIPIENT_EMAIL_ADDRESS);
		expectedValueOfDSSRequestForDirect
		.setSenderEmailAddress(SENDER_EMAIL_ADDRESS);
		expectedValueOfDSSRequestForDirect
		.setXdsDocumentEntryUniqueId(XDS_DOCUMENT_ENTRY_UNIQUEID);
		DSSPortTypeImpl.expectedValueOfDSSRequestForDirect = expectedValueOfDSSRequestForDirect;
		returnedValueOfDSSResponseForDirect
		.setSegmentedDocumentXml(SEGMENTED_DOCUMENT_XML_MOCK);
		DSSPortTypeImpl.returnedValueOfDSSResponseForDirect = returnedValueOfDSSResponseForDirect;

		// Act
		final String actualResponse = sut.segmentDocumentForDirect(
				expectedValueOfDSSRequestForDirect).getSegmentedDocumentXml();

		// Assert
		assertEquals(SEGMENTED_DOCUMENT_XML_MOCK, actualResponse);
	}

	@Test(expected = DSSWebServiceClientException.class)
	public void testSegmentDocumentForDirect_Throws_DSSWebServiceClientException() {
		// Arrange
		ep.stop();
		ep = Endpoint.publish(address, new DSSPortTypeImplThrowingException());

		// Act
		final String actualResponse = sut.segmentDocumentForDirect(
				expectedValueOfDSSRequestForDirect).getSegmentedDocumentXml();

		// Assert
		assertEquals(SEGMENTED_DOCUMENT_XML_MOCK, actualResponse);
	}
}
