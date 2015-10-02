package gov.samhsa.consent2share.infrastructure.eventlistener;

import java.util.Map;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.consent2share.domain.SecurityEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.AuthenticationFailedEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityAuditVerb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import ch.qos.logback.audit.AuditException;


@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFailedEventListenerTest {
	
	final static String IP_ADDRESS="192.168.0.1";
	final static String USER_NAME="user1";
	
	@Mock
	AuditService auditService;
	@Mock
	EventService eventService;
	
	@InjectMocks
	AuthenticationFailedEventListener listener;
	

	@Test
	public void testAudit() throws AuditException {
		@SuppressWarnings("unchecked")
		Map<PredicateKey, String> predicateMap=(Map<PredicateKey, String>)mock(Map.class);
		doReturn(predicateMap).when(auditService).createPredicateMap();
		
		SecurityEvent event=new AuthenticationFailedEvent(IP_ADDRESS, USER_NAME);
		listener.audit(event);
		verify(auditService).audit("AuthenticationFailedEventListener", IP_ADDRESS, SecurityAuditVerb.FAILED_ATTEMPTS_TO_LOGIN_AS, USER_NAME, predicateMap);
		
	}

}
