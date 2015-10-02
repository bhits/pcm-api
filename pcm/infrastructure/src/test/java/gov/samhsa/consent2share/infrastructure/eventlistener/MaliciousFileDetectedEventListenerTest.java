package gov.samhsa.consent2share.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.consent2share.domain.SecurityEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.MaliciousFileDetectedEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityAuditVerb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.audit.AuditException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MaliciousFileDetectedEventListenerTest {
	
	final static String IP_ADDRESS = "192.168.0.1";
	final static String USER_NAME = "user1";
	final static String FILE_UPLOADED = "file1";
	
	@Mock
	AuditService auditService;

	@InjectMocks
	MaliciousFileDetectedEventListener listener;

	@Test
	public void test() throws AuditException {
		@SuppressWarnings("unchecked")
		Map<PredicateKey, String> predicateMap = (Map<PredicateKey, String>) mock(Map.class);
		doReturn(predicateMap).when(auditService).createPredicateMap();

		SecurityEvent event = new MaliciousFileDetectedEvent(IP_ADDRESS,
				USER_NAME, FILE_UPLOADED);
		listener.audit(event);
		verify(auditService).audit("MaliciousFileDetectedEventListener",
				USER_NAME, SecurityAuditVerb.UPLOADS_MALICIOUS_FILE,
				FILE_UPLOADED, predicateMap);
	}

}
