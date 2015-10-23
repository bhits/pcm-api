package gov.samhsa.pcm.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.pcm.domain.SecurityEvent;
import gov.samhsa.pcm.infrastructure.eventlistener.FileUploadedEventListener;
import gov.samhsa.pcm.infrastructure.securityevent.FileUploadedEvent;
import gov.samhsa.pcm.infrastructure.securityevent.SecurityAuditVerb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ch.qos.logback.audit.AuditException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileUploadedEventListenerTest {
	
	final static String IP_ADDRESS = "192.168.0.1";
	final static String USER_NAME = "user1";
	final static String FILE_UPLOADED = "file1";
	
	@Mock
	AuditService auditService;

	@InjectMocks
	FileUploadedEventListener listener;

	@Test
	public void test() throws AuditException {
		@SuppressWarnings("unchecked")
		Map<PredicateKey, String> predicateMap = (Map<PredicateKey, String>) mock(Map.class);
		doReturn(predicateMap).when(auditService).createPredicateMap();

		SecurityEvent event = new FileUploadedEvent(IP_ADDRESS,
				USER_NAME, FILE_UPLOADED);
		listener.audit(event);
		verify(auditService).audit("FileUploadedEventListener",
				USER_NAME, SecurityAuditVerb.UPLOADS_FILE,
				FILE_UPLOADED, predicateMap);
	}

}
