package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertNotNull;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;
import gov.samhsa.acs.polrep.client.PolRepRestClient;
import gov.samhsa.pcm.hl7.Hl7v3Transformer;
import gov.samhsa.pcm.hl7.Hl7v3TransformerImpl;
import gov.samhsa.pcm.pixclient.service.PixManagerService;
import gov.samhsa.pcm.pixclient.service.PixManagerServiceImpl;
import gov.samhsa.pcm.pixclient.util.PixManagerMessageHelper;
import gov.samhsa.pcm.pixclient.util.PixManagerRequestXMLToJava;

import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

import org.herasaf.xacml.core.WritingException;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PolRepPolicyProviderIT {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPolicies() throws WritingException,
			NoPolicyFoundException, PolicyProviderException, IOException {
		final String pidDomain = "2.16.840.1.113883.3.72.5.9.4";
		final String pidDomainType = "ISO";
		final String empiEndpointAddress = "";
		final String scheme = "http";
		final String host = "localhost";
		final int port = 8080;
		final String context = "polrep-web";
		final String version = "latest";
		final String empiDomainId = "2.16.840.1.113883.4.357";
		final String intermediarySubjectNPI = "1972838936";
		final String eid = "a5d1c640-ad43-11e4-96f1-00155d0a6a16";
		final String patientUniqueId = "'a5d1c640-ad43-11e4-96f1-00155d0a6a16^^^&2.16.840.1.113883.4.357&ISO'";
		final String purposeOfUse = "TREATMENT";
		final String recipientSubjectNPI = "1477888469";
		final String messageId = UUID.randomUUID().toString();

		SimplePDPFactory.getSimplePDP();
		final SimpleMarshaller marshaller = new SimpleMarshallerImpl();
		final XmlTransformer xmlTransformer = new XmlTransformerImpl(marshaller);
		final Hl7v3Transformer hl7v3Transformer = new Hl7v3TransformerImpl(
				xmlTransformer);
		final PixManagerRequestXMLToJava requestXMLToJava = new PixManagerRequestXMLToJava(
				marshaller);
		final PixManagerMessageHelper pixManagerMessageHelper = new PixManagerMessageHelper();

		final PixManagerService pixManagerService = new PixManagerServiceImpl(
				empiEndpointAddress);

		final PolRepRestClient polRepRestClient = new PolRepRestClient(scheme,
				host, port, context, version);

		final PolicyProvider sut = new PolRepPolicyProvider(pidDomain,
				pidDomainType, hl7v3Transformer, requestXMLToJava,
				pixManagerMessageHelper, pixManagerService, polRepRestClient);

		final XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setHomeCommunityId(empiDomainId);
		xacmlRequest.setIntermediarySubjectNPI(intermediarySubjectNPI);
		xacmlRequest.setMessageId(messageId);
		xacmlRequest.setPatientId(eid);
		xacmlRequest.setPatientUniqueId(patientUniqueId);
		xacmlRequest.setPurposeOfUse(purposeOfUse);
		xacmlRequest.setRecipientSubjectNPI(recipientSubjectNPI);

		String xacml = null;

		try (StringWriter writer = new StringWriter()) {
			Evaluatable evaluatable = sut.getPolicies(xacmlRequest).get(0);
			PolicyMarshaller.marshal(evaluatable,
					System.out);
			PolicyMarshaller.marshal(evaluatable,
					writer);
			xacml = writer.toString();
		}

		assertNotNull(xacml);
	}
}
