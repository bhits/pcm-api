package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.dto.PdpRequestResponse;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.audit.AuditException;

@RunWith(MockitoJUnitRunner.class)
public class ContextHandlerImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private PolicyDecisionPoint policyDesicionPoint;

	@InjectMocks
	private ContextHandlerImpl sut;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEnforcePolicy() throws AuditException,
			NoPolicyFoundException, PolicyProviderException {
		// Arrange
		final XacmlRequest xacmlRequest = mock(XacmlRequest.class);
		final XacmlResponse xacmlResponse = mock(XacmlResponse.class);
		when(policyDesicionPoint.evaluateRequest(xacmlRequest)).thenReturn(
				xacmlResponse);

		// Act
		final XacmlResponse response = sut.enforcePolicy(xacmlRequest);

		// Assert
		verify(policyDesicionPoint, times(1)).evaluateRequest(xacmlRequest);
		assertEquals(xacmlResponse, response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEnforcePolicy_Throws_AuditException()
			throws AuditException, NoPolicyFoundException,
			PolicyProviderException {
		// Arrange
		thrown.expect(AuditException.class);
		final XacmlRequest xacmlRequest = mock(XacmlRequest.class);
		final XacmlResponse xacmlResponse = mock(XacmlResponse.class);
		when(policyDesicionPoint.evaluateRequest(xacmlRequest)).thenThrow(
				AuditException.class);

		// Act
		final XacmlResponse response = sut.enforcePolicy(xacmlRequest);

		// Assert
		verify(policyDesicionPoint, times(1)).evaluateRequest(xacmlRequest);
		assertEquals(xacmlResponse, response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEnforcePolicy_Throws_NoPolicyFoundException()
			throws AuditException, NoPolicyFoundException,
			PolicyProviderException {
		// Arrange
		thrown.expect(NoPolicyFoundException.class);
		final XacmlRequest xacmlRequest = mock(XacmlRequest.class);
		final XacmlResponse xacmlResponse = mock(XacmlResponse.class);
		when(policyDesicionPoint.evaluateRequest(xacmlRequest)).thenThrow(
				NoPolicyFoundException.class);

		// Act
		final XacmlResponse response = sut.enforcePolicy(xacmlRequest);

		// Assert
		verify(policyDesicionPoint, times(1)).evaluateRequest(xacmlRequest);
		assertEquals(xacmlResponse, response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEnforcePolicy_Throws_PolicyProviderException()
			throws AuditException, NoPolicyFoundException,
			PolicyProviderException {
		// Arrange
		thrown.expect(PolicyProviderException.class);
		final XacmlRequest xacmlRequest = mock(XacmlRequest.class);
		final XacmlResponse xacmlResponse = mock(XacmlResponse.class);
		when(policyDesicionPoint.evaluateRequest(xacmlRequest)).thenThrow(
				PolicyProviderException.class);

		// Act
		final XacmlResponse response = sut.enforcePolicy(xacmlRequest);

		// Assert
		verify(policyDesicionPoint, times(1)).evaluateRequest(xacmlRequest);
		assertEquals(xacmlResponse, response);
	}

	@Test
	public void testMakeDecisionForTryingPolicy() {
		// Arrange
		final String purposeOfUse = "purposeOfUse";
		final String xacmlPolicy = "xacmlPolic";
		final PdpRequestResponse pdpResp = mock(PdpRequestResponse.class);
		when(
				policyDesicionPoint.evaluatePolicyForTrying(xacmlPolicy,
						purposeOfUse)).thenReturn(pdpResp);

		// Act
		final PdpRequestResponse resp = sut.makeDecisionForTryingPolicy(
				xacmlPolicy, purposeOfUse);

		// Assert
		verify(policyDesicionPoint, times(1)).evaluatePolicyForTrying(
				xacmlPolicy, purposeOfUse);
		assertEquals(pdpResp, resp);
	}

}
