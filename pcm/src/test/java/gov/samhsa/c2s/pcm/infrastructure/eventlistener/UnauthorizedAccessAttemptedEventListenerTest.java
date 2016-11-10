package gov.samhsa.c2s.pcm.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.c2s.pcm.infrastructure.eventlistener.UnauthorizedAccessAttemptedEventListener;
import gov.samhsa.c2s.common.audit.AuditClient;
import gov.samhsa.c2s.common.audit.PredicateKey;
import gov.samhsa.c2s.pcm.domain.SecurityEvent;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.UnauthorizedAccessAttemptedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.audit.AuditException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UnauthorizedAccessAttemptedEventListenerTest {

    final static String IP_ADDRESS = "192.168.0.1";
    final static String USER_NAME = "user1";

    @Mock
    AuditClient auditClient;

    @InjectMocks
    UnauthorizedAccessAttemptedEventListener listener;

    @Test
    public void testAudit() throws AuditException {
        @SuppressWarnings("unchecked")
        Map<PredicateKey, String> predicateMap = (Map<PredicateKey, String>) mock(Map.class);
        doReturn(predicateMap).when(auditClient).createPredicateMap();

        SecurityEvent event = new UnauthorizedAccessAttemptedEvent(IP_ADDRESS,
                USER_NAME);
        listener.audit(event);
        verify(auditClient).audit("UnauthorizedAccessAttemptedEventListener",
                USER_NAME, SecurityAuditVerb.ATTEMPTS_TO_ACCESS_UNAUTHORIZED_RESOURCE,
                "Unauthorized Page", predicateMap);
    }
}
