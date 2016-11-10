package gov.samhsa.c2s.pcm.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.c2s.pcm.infrastructure.eventlistener.UserCreatedEventListener;
import gov.samhsa.c2s.common.audit.AuditClient;
import gov.samhsa.c2s.common.audit.PredicateKey;
import gov.samhsa.c2s.pcm.domain.SecurityEvent;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.UserCreatedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.audit.AuditException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserCreatedEventListenerTest {

    final static String IP_ADDRESS = "192.168.0.1";
    final static String USER_NAME = "user1";

    @Mock
    AuditClient auditClient;

    @InjectMocks
    UserCreatedEventListener listener;

    @Test
    public void testHandle() throws AuditException {
        @SuppressWarnings("unchecked")
        Map<PredicateKey, String> predicateMap = (Map<PredicateKey, String>) mock(Map.class);
        doReturn(predicateMap).when(auditClient).createPredicateMap();

        SecurityEvent event = new UserCreatedEvent(IP_ADDRESS,
                USER_NAME);
        listener.audit(event);
        verify(auditClient).audit("UserCreatedEventListener",
                IP_ADDRESS, SecurityAuditVerb.CREATES_USER,
                USER_NAME, predicateMap);
    }

}
