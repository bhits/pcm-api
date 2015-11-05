package gov.samhsa.acs.trypolicy;

import static gov.samhsa.pcm.commonunit.matcher.ArgumentMatchers.matching;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.brms.domain.SubjectPurposeOfUse;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.dto.PdpRequestResponse;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.acs.contexthandler.ContextHandlerImpl;
import gov.samhsa.acs.documentsegmentation.dto.SegmentDocumentResponse;
import gov.samhsa.acs.documentsegmentation.exception.InvalidSegmentedClinicalDocumentException;
import gov.samhsa.acs.dss.ws.schema.DSSRequest;
import gov.samhsa.acs.dss.ws.schema.DSSResponse;
import gov.samhsa.acs.dss.wsclient.DSSWebServiceClient;
import gov.samhsa.acs.trypolicy.exception.TryPolicyException;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.audit.AuditException;

@RunWith(MockitoJUnitRunner.class)
public class PolicyTryingImplTest {

	@Mock
	private ContextHandlerImpl contextHandler;

	@Mock
	private SimpleMarshallerImpl marshaller;

	@Mock
	private DSSWebServiceClient documentSegmentation;

	private PolicyTryingImpl sut;

	@Before
	public void setup() {
		sut = new PolicyTryingImpl(contextHandler, marshaller,
				documentSegmentation);
	}

	@Test
	public void testTryPolicy() throws SimpleMarshallerException,
	InvalidSegmentedClinicalDocumentException,
	XmlDocumentReadFailureException, AuditException, TryPolicyException {
		// Arrange
		final String c32Xml = "c32Xml";
		final String xacmlPolicy = "xacmlPolicy";
		final String purposeOfUse = "TREATMENT";
		final PdpRequestResponse pdpRequestResponse = mock(PdpRequestResponse.class);
		final XacmlRequest xacmlRequest = mock(XacmlRequest.class);
		final XacmlResponse xacmlResponse = mock(XacmlResponse.class);
		when(xacmlRequest.getPurposeOfUse()).thenReturn(
				SubjectPurposeOfUse.HEALTHCARE_TREATMENT.getPurpose());
		when(xacmlResponse.getPdpObligation()).thenReturn(
				new ArrayList<String>());
		when(
				contextHandler.makeDecisionForTryingPolicy(xacmlPolicy,
						purposeOfUse)).thenReturn(pdpRequestResponse);
		when(pdpRequestResponse.getXacmlRequest()).thenReturn(xacmlRequest);
		when(pdpRequestResponse.getXacmlResponse()).thenReturn(xacmlResponse);
		final String enforcementPolicies = "String enforcementPolicies";
		// when(marshaller.marshall(isA(XacmlResult.class))).thenReturn(enforcementPolicies);
		when(
				marshaller
				.marshal(argThat(new IsXacmlResultWithCorrectProperties(
						xacmlRequest, xacmlResponse)))).thenReturn(
								enforcementPolicies);
		final SegmentDocumentResponse segmentDocumentResponse = mock(SegmentDocumentResponse.class);
		final String segmentedC32 = "segmentedC32";
		when(segmentDocumentResponse.getTryPolicyDocumentXml()).thenReturn(
				segmentedC32);
		final DSSResponse response = mock(DSSResponse.class);
		when(response.getTryPolicyDocumentXml()).thenReturn(segmentedC32);
		when(documentSegmentation.segmentDocument(isA(DSSRequest.class)))
		.thenReturn(response);

		// Act
		final String result = sut.tryPolicy(c32Xml, xacmlPolicy, purposeOfUse);

		// Assert
		assertEquals("Not return expected result", segmentedC32, result);
		verify(documentSegmentation, times(1)).segmentDocument(
				argThat(matching((DSSRequest r) -> r.getDocumentXml().equals(
						c32Xml)
						&& r.getEnforcementPoliciesXml().equals(
								enforcementPolicies)
								&& r.isAudited() == false
								&& r.isAuditFailureByPass() == true)));
	}

	private class IsXacmlResultWithCorrectProperties extends
	ArgumentMatcher<XacmlResult> {

		/** The xacml request. */
		private final XacmlRequest xacmlRequest;

		/** The xacml response. */
		private final XacmlResponse xacmlResponse;

		/**
		 * Instantiates a new checks if is xacml result with correct properties.
		 *
		 * @param xacmlRequest
		 *            the xacml request
		 * @param xacmlResponse
		 *            the xacml response
		 */
		public IsXacmlResultWithCorrectProperties(XacmlRequest xacmlRequest,
				XacmlResponse xacmlResponse) {
			this.xacmlRequest = xacmlRequest;
			this.xacmlResponse = xacmlResponse;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.mockito.ArgumentMatcher#matches(java.lang.Object)
		 */
		@Override
		public boolean matches(Object argument) {
			final XacmlResult xacmlResult = (XacmlResult) argument;
			if (xacmlResult.getHomeCommunityId() == xacmlRequest
					.getHomeCommunityId()
					&& xacmlResult.getSubjectPurposeOfUse().getPurpose() == xacmlRequest
					.getPurposeOfUse()
					&& xacmlResult.getPdpDecision() == xacmlResponse
					.getPdpDecision()
					&& xacmlResult.getPdpObligations() == xacmlResponse
					.getPdpObligation()) {
				return true;
			}
			return false;
		}
	}

}
