package gov.samhsa.c2s.pcm.infrastructure.eventlistener;

import ch.qos.logback.audit.AuditException;
import gov.samhsa.c2s.pcm.domain.SecurityEvent;
import gov.samhsa.c2s.common.audit.AuditClient;
import gov.samhsa.c2s.common.audit.PredicateKey;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.SecurityPredicateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * The listener interface for receiving fileDownloadedEvent events. The class
 * that is interested in processing a fileDownloadedEvent event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addFileDownloadedEventListener<code> method. When
 * the fileDownloadedEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see FileDownloadedEvent
 */
public class FileDownloadedEventListener extends SecurityEventListener {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Instantiates a new file downloaded event listener.
     *
     * @param eventService the event service
     * @param auditClient  the audit service
     */
    public FileDownloadedEventListener(EventService eventService,
                                       AuditClient  auditClient ) {
        super(eventService, auditClient );
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.infrastructure.eventlistener.EventListener#canHandle
     * (java.lang.Object)
     */
    @Override
    public boolean canHandle(Object event) {
        return event instanceof FileDownloadedEvent;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.infrastructure.eventlistener.SecurityEventListener
     * #handle(java.lang.Object)
     */
    @Override
    public void handle(Object event) {
        super.handle(event);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.infrastructure.eventlistener.SecurityEventListener
     * #audit(gov.samhsa.consent2share.domain.SecurityEvent)
     */
    @Override
    public void audit(SecurityEvent event) {
        FileDownloadedEvent fileDownloadedEvent = (FileDownloadedEvent) event;
        Map<PredicateKey, String> predicateMap = auditClient
                .createPredicateMap();
        predicateMap.put(SecurityPredicateKey.IP_ADDRESS, event.getIpAddress());
        try {
            auditClient.audit("FileDownloadedEventListener",
                    fileDownloadedEvent.getUserName(),
                    SecurityAuditVerb.DOWNLOADS_FILE,
                    fileDownloadedEvent.getFileDownloaded(), predicateMap);
        } catch (AuditException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
