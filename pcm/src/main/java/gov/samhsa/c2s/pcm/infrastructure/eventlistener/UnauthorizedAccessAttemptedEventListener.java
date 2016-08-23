package gov.samhsa.c2s.pcm.infrastructure.eventlistener;

import ch.qos.logback.audit.AuditException;
import gov.samhsa.c2s.pcm.domain.SecurityEvent;
import gov.samhsa.c2s.common.audit.AuditService;
import gov.samhsa.c2s.common.audit.PredicateKey;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.SecurityPredicateKey;
import gov.samhsa.c2s.pcm.infrastructure.securityevent.UnauthorizedAccessAttemptedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * The listener interface for receiving unauthorizedAccessAttemptedEvent events.
 * The class that is interested in processing a unauthorizedAccessAttemptedEvent
 * event implements this interface, and the object created with that class is
 * registered with a component using the component's
 * <code>addUnauthorizedAccessAttemptedEventListener<code> method. When
 * the unauthorizedAccessAttemptedEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see UnauthorizedAccessAttemptedEvent
 */
public class UnauthorizedAccessAttemptedEventListener extends
        SecurityEventListener {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Instantiates a new unauthorized access attempted event listener.
     *
     * @param eventService the event service
     * @param auditService the audit service
     */
    public UnauthorizedAccessAttemptedEventListener(EventService eventService,
                                                    AuditService auditService) {
        super(eventService, auditService);
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
        return event instanceof UnauthorizedAccessAttemptedEvent;
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
        UnauthorizedAccessAttemptedEvent unauthorizedAccessAttemptedEvent = (UnauthorizedAccessAttemptedEvent) event;
        Map<PredicateKey, String> predicateMap = auditService
                .createPredicateMap();
        predicateMap.put(SecurityPredicateKey.IP_ADDRESS, event.getIpAddress());
        try {
            auditService.audit("UnauthorizedAccessAttemptedEventListener",
                    unauthorizedAccessAttemptedEvent.getUserName(),
                    SecurityAuditVerb.ATTEMPTS_TO_ACCESS_UNAUTHORIZED_RESOURCE,
                    "Unauthorized Page", predicateMap);
        } catch (AuditException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
