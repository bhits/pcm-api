package gov.samhsa.acs.c32.wsclient;

import gov.samhsa.schemas.c32service.C32Service;
import gov.samhsa.schemas.c32service.IC32Service;

import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class C32WebServiceClientTest {

	protected static Endpoint ep;
	protected static String address;

	private static final String returnedValueOfGetC32 = "C32";

	@BeforeClass
	public static void setUp() throws Exception {
		Resource resource = new ClassPathResource(
				"/jettyServerPortForTesing.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		String portNumber = props
				.getProperty("jettyServerPortForTesing.number");

		address = String.format("http://localhost:%s/services/C32Service",
				portNumber);

		ep = Endpoint.publish(address, new IC32ServiceImpl());
		IC32ServiceImpl.returnedValueOfGetC32 = returnedValueOfGetC32;
	}

	@AfterClass
	public static void tearDown() {
		try {
			ep.stop();
		} catch (Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	// Test if the stub web service activate properly
	@Test
	public void testStubWebServiceWorks() {
		final String patientId = "";

		String resp = createPort().getC32(patientId);
		validateResponse(resp);
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks() {
		final String patientId = "";

		C32WebServiceClient wsc = new C32WebServiceClient(address);
		String resp = wsc.getC32(patientId);
		validateResponse(resp);
	}

	private void validateResponse(String resp) {
		Assert.assertEquals("Returned C32 wrong", returnedValueOfGetC32, resp);
	}

	private IC32Service createPort() {
		final URL WSDL_LOCATION = ClassLoader
				.getSystemResource("C32Service.wsdl");
		final QName SERVICE = new QName("http://schemas.samhsa.gov/c32service",
				"C32Service");

		IC32Service port = new C32Service(WSDL_LOCATION, SERVICE)
				.getBasicHttpBindingIC32Service();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}

}
