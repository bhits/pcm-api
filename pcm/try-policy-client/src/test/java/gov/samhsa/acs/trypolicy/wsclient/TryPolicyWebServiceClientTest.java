package gov.samhsa.acs.trypolicy.wsclient;

import gov.samhsa.acs.pep.ws.contract.TryPolicyPortType;
import gov.samhsa.acs.pep.ws.contract.TryPolicyService;

import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class TryPolicyWebServiceClientTest {

	protected Endpoint ep;
	protected String address;

	private final String returnedValueOfTryPolicy = "Policy is tried";

	@Before
	public void setUp() throws Exception {
		final Resource resource = new ClassPathResource(
				"/jettyServerPortForTesing.properties");
		final Properties props = PropertiesLoaderUtils.loadProperties(resource);
		final String portNumber = props
				.getProperty("jettyServerPortForTesing.number");

		address = String.format(
				"http://localhost:%s/services/TryPolicyService", portNumber);

		ep = Endpoint.publish(address, new TryPolicyPortTypeImpl());
		TryPolicyPortTypeImpl.returnedValueOfTryPolicy = returnedValueOfTryPolicy;
	}

	@After
	public void tearDown() {
		try {
			ep.stop();
		} catch (final Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	// Test if the stub web service activate properly
	@Test
	public void testStubWebServiceWorks() {
		final String c32Xml = "";
		final String xacmlPolicy = "";
		final String purposeOfUse = "";

		final String resp = createPort().tryPolicy(c32Xml, xacmlPolicy,
				purposeOfUse);
		validateResponse(resp);
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks() {
		// Arrange
		final String c32Xml = "";
		final String xacmlPolicy = "";
		final String purposeOfUse = "";

		final TryPolicyWebServiceClient wsc = new TryPolicyWebServiceClient(
				address);

		// Act
		final String resp = wsc.tryPolicy(c32Xml, xacmlPolicy, purposeOfUse);

		// Assert
		validateResponse(resp);
	}

	@Test(expected = TryPolicyWebServiceClientException.class)
	public void testWSClientSOAPCallWorks_Throws_Exception() {
		// Arrange
		initExceptionEndpoint();
		final String c32Xml = "";
		final String xacmlPolicy = "";
		final String purposeOfUse = "";

		final TryPolicyWebServiceClient wsc = new TryPolicyWebServiceClient(
				address);

		// Act
		final String resp = wsc.tryPolicy(c32Xml, xacmlPolicy, purposeOfUse);

		// Assert
		validateResponse(resp);
	}

	private TryPolicyPortType createPort() {
		final URL WSDL_LOCATION = ClassLoader
				.getSystemResource("TryPolicy.wsdl");
		final QName SERVICE = new QName(
				"http://acs.samhsa.gov/pep/ws/contract", "TryPolicyService");

		final TryPolicyPortType port = new TryPolicyService(WSDL_LOCATION,
				SERVICE).getTryPolicyServicePort();
		final BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}

	private void initExceptionEndpoint() {
		ep.stop();
		ep = Endpoint.publish(address,
				new TryPolicyPortTypeImplThrowingException());

	}

	private void validateResponse(String resp) {
		Assert.assertEquals("Try policy reutruend wrong",
				returnedValueOfTryPolicy, resp);
	}
}
