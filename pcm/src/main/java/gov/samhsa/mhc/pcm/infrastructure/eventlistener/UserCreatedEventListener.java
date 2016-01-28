package gov.samhsa.mhc.pcm.infrastructure.eventlistener;

import ch.qos.logback.audit.AuditException;
import gov.samhsa.mhc.common.audit.AuditService;
import gov.samhsa.mhc.common.audit.PredicateKey;
import gov.samhsa.mhc.pcm.domain.SecurityEvent;
import gov.samhsa.mhc.pcm.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.mhc.pcm.infrastructure.securityevent.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * The listener interface for receiving userCreatedEvent events. The class that
 * is interested in processing a userCreatedEvent event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addUserCreatedEventListener<code> method. When
 * the userCreatedEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see UserCreatedEvent
 */
public class UserCreatedEventListener extends SecurityEventListener {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Instantiates a new user created event listener.
     *
     * @param eventService the event service
     * @param auditService the audit service
     */
    public UserCreatedEventListener(EventService eventService,
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
        return event instanceof UserCreatedEvent;
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
        UserCreatedEvent userCreatedEvent = (UserCreatedEvent) event;
        Map<PredicateKey, String> predicateMap = auditService
                .createPredicateMap();
        try {
            auditService.audit("UserCreatedEventListener",
                    userCreatedEvent.getIpAddress(),
                    SecurityAuditVerb.CREATES_USER,
                    userCreatedEvent.getUserName(), predicateMap);
        } catch (AuditException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
