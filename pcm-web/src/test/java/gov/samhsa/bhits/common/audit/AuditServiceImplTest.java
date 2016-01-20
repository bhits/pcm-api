package gov.samhsa.bhits.common.audit;

import ch.qos.logback.audit.AuditException;
import ch.qos.logback.audit.client.AuditorFacade;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

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
        AuditVerb auditVerbMock = () -> "DEPLOY_POLICY";
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