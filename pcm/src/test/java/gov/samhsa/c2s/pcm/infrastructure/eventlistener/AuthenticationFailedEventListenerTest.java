package gov.samhsa.c2s.pcm.infrastructure.eventlistener;

import ch.qos.logback.audit.AuditException;
import gov.samhsa.c2s.pcm.infrastructure.eventlistener.AuthenticationFailedEventListener;
import gov.samhsa.c2s.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.c2s.common.audit.AuditClient;
import gov.samhsa.c2s.common.audit.PredicateKey;
import gov.samhsa.c2s.pcm.domain.SecurityEvent;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.AuthenticationFailedEvent;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.SecurityAuditVerb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFailedEventListenerTest {

    final static String IP_ADDRESS = "192.168.0.1";
    final static String USER_NAME = "user1";

    @Mock
    AuditClient auditClient;
    @Mock
    EventService eventService;

    @InjectMocks
    AuthenticationFailedEventListener listener;


    @Test
    public void testAudit() throws AuditException {
        @SuppressWarnings("unchecked")
        Map<PredicateKey, String> predicateMap = (Map<PredicateKey, String>) mock(Map.class);
        doReturn(predicateMap).when(auditClient).createPredicateMap();

        SecurityEvent event = new AuthenticationFailedEvent(IP_ADDRESS, USER_NAME);
        listener.audit(event);
        verify(auditClient).audit("AuthenticationFailedEventListener", IP_ADDRESS, SecurityAuditVerb.FAILED_ATTEMPTS_TO_LOGIN_AS, USER_NAME, predicateMap);

    }

}
