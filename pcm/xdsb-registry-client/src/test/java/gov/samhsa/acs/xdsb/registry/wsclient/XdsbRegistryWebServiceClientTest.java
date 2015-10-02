package gov.samhsa.acs.xdsb.registry.wsclient;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.xdsb.registry.wsclient.exception.XdsbRegistryClientException;
import gov.samhsa.ds4p.xdsbregistry.DocumentRegistryService;
import ihe.iti.xds_b._2007.XDSRegistry;

import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.hl7.v3.Id;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;
import org.hl7.v3.PRPAIN201304UV02;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class XdsbRegistryWebServiceClientTest {

	protected Endpoint ep;
	protected String address;
	private final AdhocQueryResponse returnedValueOfRegistryStoredQuery = new AdhocQueryResponse();
	private FileReaderImpl fileReader;

	@Before
	public void setUp() {
		fileReader = new FileReaderImpl();
		try {
			final Resource resource = new ClassPathResource(
					"/jettyServerPortForTesing.properties");
			final Properties props = PropertiesLoaderUtils
					.loadProperties(resource);
			final String portNumber = props
					.getProperty("jettyServerPortForTesing.number");

			address = String.format(
					"http://localhost:%s/services/xdsregistryb", portNumber);

			ep = Endpoint.publish(address, new XdsbRegistryServiceImpl());

			XdsbRegistryServiceImpl.returnedValueOfRegistryStoredQuery = returnedValueOfRegistryStoredQuery;

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		try {
			ep.stop();
		} catch (final Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	@Test
	public void testAddPatientRegistryRecord() throws Throwable {
		// Arrange
		final String expectedResponse = fileReader
				.readFile("unitTestMCCI_IN000002UV01.xml");
		final PRPAIN201301UV02 requestMock = mock(PRPAIN201301UV02.class);
		final MCCIIN000002UV01 responseMock = setMCCIIN000002UV01();
		XdsbRegistryServiceImpl.returnedValueOfPatientRegistryRecordAdded = responseMock;
		final XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address, new SimpleMarshallerImpl());

		// Act
		final String actualResponse = wsc.addPatientRegistryRecord(requestMock);

		// Assert
		assertXMLEqual("", expectedResponse, actualResponse);
	}

	@Test(expected = XdsbRegistryClientException.class)
	public void testAddPatientRegistryRecord_Throws_Exception()
			throws Throwable {
		// Arrange
		initExceptionEndpoint();
		final String expectedResponse = fileReader
				.readFile("unitTestMCCI_IN000002UV01.xml");
		final PRPAIN201301UV02 requestMock = mock(PRPAIN201301UV02.class);
		final MCCIIN000002UV01 responseMock = setMCCIIN000002UV01();
		XdsbRegistryServiceImpl.returnedValueOfPatientRegistryRecordAdded = responseMock;
		final XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address, new SimpleMarshallerImpl());

		// Act
		final String actualResponse = wsc.addPatientRegistryRecord(requestMock);

		// Assert
		assertXMLEqual("", expectedResponse, actualResponse);
	}

	@Test
	public void testResolvePatientRegistryDuplicates() throws Throwable {
		// Arrange
		final String expectedResponse = fileReader
				.readFile("unitTestMCCI_IN000002UV01.xml");
		final PRPAIN201304UV02 requestMock = mock(PRPAIN201304UV02.class);
		final MCCIIN000002UV01 responseMock = setMCCIIN000002UV01();
		XdsbRegistryServiceImpl.returnedValueOfPatientRegistryDuplicatesResolved = responseMock;
		final XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address, new SimpleMarshallerImpl());

		// Act
		final String actualResponse = wsc
				.resolvePatientRegistryDuplicates(requestMock);

		// Assert
		assertXMLEqual("", expectedResponse, actualResponse);
	}

	@Test(expected = XdsbRegistryClientException.class)
	public void testResolvePatientRegistryDuplicates_Throws_Exception()
			throws Throwable {
		// Arrange
		initExceptionEndpoint();
		final String expectedResponse = fileReader
				.readFile("unitTestMCCI_IN000002UV01.xml");
		final PRPAIN201304UV02 requestMock = mock(PRPAIN201304UV02.class);
		final MCCIIN000002UV01 responseMock = setMCCIIN000002UV01();
		XdsbRegistryServiceImpl.returnedValueOfPatientRegistryDuplicatesResolved = responseMock;
		final XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address, new SimpleMarshallerImpl());

		// Act
		final String actualResponse = wsc
				.resolvePatientRegistryDuplicates(requestMock);

		// Assert
		assertXMLEqual("", expectedResponse, actualResponse);
	}

	@Test
	public void testRevisePatientRegistryRecord() throws Throwable {
		// Arrange
		final String expectedResponse = fileReader
				.readFile("unitTestMCCI_IN000002UV01.xml");
		final PRPAIN201302UV02 requestMock = mock(PRPAIN201302UV02.class);
		final MCCIIN000002UV01 responseMock = setMCCIIN000002UV01();
		XdsbRegistryServiceImpl.returnedValueOfPatientRegistryRecordRevised = responseMock;
		final XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address, new SimpleMarshallerImpl());

		// Act
		final String actualResponse = wsc
				.revisePatientRegistryRecord(requestMock);

		// Assert
		assertXMLEqual("", expectedResponse, actualResponse);
	}

	@Test(expected = XdsbRegistryClientException.class)
	public void testRevisePatientRegistryRecord_Throws_Exception()
			throws Throwable {
		// Arrange
		initExceptionEndpoint();
		final String expectedResponse = fileReader
				.readFile("unitTestMCCI_IN000002UV01.xml");
		final PRPAIN201302UV02 requestMock = mock(PRPAIN201302UV02.class);
		final MCCIIN000002UV01 responseMock = setMCCIIN000002UV01();
		XdsbRegistryServiceImpl.returnedValueOfPatientRegistryRecordRevised = responseMock;
		final XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address, new SimpleMarshallerImpl());

		// Act
		final String actualResponse = wsc
				.revisePatientRegistryRecord(requestMock);

		// Assert
		assertXMLEqual("", expectedResponse, actualResponse);
	}

	// Test if the stub web service activate properly
	@Test
	public void testStubWebServiceWorks() {
		final AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();

		final Object response = createPort().registryStoredQuery(
				adhocQueryRequest);
		validateResponseOfRetrieveDocumentSetRequest(response);
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks_retrieveDocumentSetRequest() {
		final AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();

		final XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address, new SimpleMarshallerImpl());
		final Object resp = wsc.registryStoredQuery(adhocQueryRequest);
		validateResponseOfRetrieveDocumentSetRequest(resp);
	}

	@Test(expected = XdsbRegistryClientException.class)
	public void testWSClientSOAPCallWorks_retrieveDocumentSetRequest_Throws_Exception() {
		initExceptionEndpoint();
		final AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();

		final XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address, new SimpleMarshallerImpl());
		final Object resp = wsc.registryStoredQuery(adhocQueryRequest);
		validateResponseOfRetrieveDocumentSetRequest(resp);
	}

	private XDSRegistry createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("XDS.b_registry.net.wsdl");
		final QName SERVICE = new QName("http://samhsa.gov/ds4p/XDSbRegistry/",
				"DocumentRegistryService");

		final XDSRegistry port = new DocumentRegistryService(WSDL_LOCATION,
				SERVICE).getXDSRegistryHTTPEndpoint();

		final BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);

		return port;
	}

	private void initExceptionEndpoint() {
		ep.stop();
		ep = Endpoint.publish(address,
				new XdsbRegistryServiceImplThrowingException());
	}

	private MCCIIN000002UV01 setMCCIIN000002UV01() {
		final MCCIIN000002UV01 responseMock = new MCCIIN000002UV01();
		final Id idMock = new Id();
		idMock.setExtension("extensionMock");
		idMock.setRoot("rootMock");
		responseMock.setId(idMock);
		return responseMock;
	}

	private void validateResponseOfRetrieveDocumentSetRequest(Object resp) {
		assertNotNull(resp);
	}
}