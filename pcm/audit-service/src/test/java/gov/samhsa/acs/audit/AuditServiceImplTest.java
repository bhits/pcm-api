package gov.samhsa.acs.audit;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.audit.AuditException;
import ch.qos.logback.audit.client.AuditorFacade;

@RunWith(MockitoJUnitRunner.class)
public class AuditServiceImplTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@InjectMocks
	private AuditServiceImpl sut;

	@Test
	public void testAudit() throws AuditException {
		// Arrange
		AuditServiceImpl spy = spy(sut);
		Object auditingObjectMock = this;
		String subjectMock = "subjectMock";
		AuditVerb auditVerbMock = AcsAuditVerb.DEPLOY_POLICY;
		String objectMock = "objectMock";
		Map<PredicateKey, String> predicateMapMock = new HashMap<PredicateKey, String>();
		AuditorFacade afMock = mock(AuditorFacade.class);
		doReturn(afMock).when(spy).createAuditorFacade(subjectMock,
				auditVerbMock, objectMock);

		// Act
		spy.audit(auditingObjectMock, subjectMock, auditVerbMock, objectMock,
				predicateMapMock);

		// Assert
		verify(afMock, times(1)).audit();
	}
}
