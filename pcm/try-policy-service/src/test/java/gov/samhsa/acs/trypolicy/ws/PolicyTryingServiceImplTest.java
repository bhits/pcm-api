package gov.samhsa.acs.trypolicy.ws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.pep.ws.contract.TryPolicyPortType;
import gov.samhsa.acs.pep.ws.contract.TryPolicyService;
import gov.samhsa.acs.trypolicy.PolicyTrying;
import gov.samhsa.acs.trypolicy.exception.TryPolicyException;

import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PolicyTryingServiceImplTest {

	private URL wsdlURL;
	private String address;
	private QName serviceName;
	private Endpoint ep;

	private PolicyTrying policyTrying;

	@Before
	public void setUp() throws Exception {
		final Resource resource = new ClassPathResource(
				"/jettyServerPortForTesing.properties");
		final Properties props = PropertiesLoaderUtils.loadProperties(resource);
		final String portNumber = props
				.getProperty("jettyServerPortForTesing.number");

		address = String.format(
				"http://localhost:%s/services/TryPolicyService", portNumber);

		serviceName = new QName("http://acs.samhsa.gov/pep/ws/contract",
				"TryPolicyService");

		wsdlURL = new URL(address + "?wsdl");

		policyTrying = mock(PolicyTrying.class);

		ep = Endpoint.publish(address,
				new PolicyTryingServiceImpl(policyTrying));
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
	public void testTryPolicy() throws TryPolicyException {
		// Arrange
		final String c32Xml = "";
		final String xacmlPolicy = "";
		final String segmentedC32 = "Segmented C32";
		final String purposeOfUse = "TREAT";

		final TryPolicyService service = new TryPolicyService(wsdlURL,
				serviceName);
		final TryPolicyPortType port = service.getTryPolicyServicePort();

		when(policyTrying.tryPolicy(c32Xml, xacmlPolicy, purposeOfUse))
				.thenReturn(segmentedC32);

		// Act
		final String result = port.tryPolicy(c32Xml, xacmlPolicy, purposeOfUse);

		// Assert
		assertEquals(segmentedC32, result);
	}
}
