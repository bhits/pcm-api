package gov.samhsa.c2s.pcm.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.c2s.pcm.infrastructure.eventlistener.FileDownloadedEventListener;
import gov.samhsa.c2s.common.audit.AuditClient;
import gov.samhsa.c2s.common.audit.PredicateKey;
import gov.samhsa.c2s.pcm.domain.SecurityEvent;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.SecurityAuditVerb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ch.qos.logback.audit.AuditException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileDownloadedEventListenerTest {

    final static String IP_ADDRESS = "192.168.0.1";
    final static String USER_NAME = "user1";
    final static String FILE_DOWNLOADED = "file1";

    @Mock
    AuditClient auditClient;

    @InjectMocks
    FileDownloadedEventListener listener;

    @Test
    public void test() throws AuditException {
        @SuppressWarnings("unchecked")
        Map<PredicateKey, String> predicateMap = (Map<PredicateKey, String>) mock(Map.class);
        doReturn(predicateMap).when(auditClient).createPredicateMap();

        SecurityEvent event = new FileDownloadedEvent(IP_ADDRESS,
                USER_NAME, FILE_DOWNLOADED);
        listener.audit(event);
        verify(auditClient).audit("FileDownloadedEventListener",
                USER_NAME, SecurityAuditVerb.DOWNLOADS_FILE,
                FILE_DOWNLOADED, predicateMap);
    }

}
