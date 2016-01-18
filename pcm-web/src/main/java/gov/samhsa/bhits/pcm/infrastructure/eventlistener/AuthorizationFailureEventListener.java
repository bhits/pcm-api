package gov.samhsa.bhits.pcm.infrastructure.eventlistener;


import gov.samhsa.bhits.pcm.infrastructure.securityevent.UnauthorizedAccessAttemptedEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Optional;

/**
 * The listener interface for receiving authorizationFailureEvent events. The
 * class that is interested in processing a authorizationFailureEvent event
 * implements this interface, and the object created with that class is
 * registered with a component using the component's
 * <code>addAuthorizationFailureEventListener<code> method. When
 * the authorizationFailureEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AuthorizationFailureEvent
 */
public class AuthorizationFailureEventListener extends EventListener {

    /**
     * Instantiates a new authorization failure event listener.
     *
     * @param eventService the event service
     */
    public AuthorizationFailureEventListener(EventService eventService) {
        super(eventService);
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
        return event instanceof AuthorizationFailureEvent;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.infrastructure.eventlistener.EventListener#handle
     * (java.lang.Object)
     */
    @Override
    public void handle(Object event) {
        WebAuthenticationDetails details = (WebAuthenticationDetails) ((AuthorizationFailureEvent) event)
                .getAuthentication().getDetails();
        eventService.raiseSecurityEvent(new UnauthorizedAccessAttemptedEvent(
                details.getRemoteAddress(), Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName).orElse("AuthenticationNameNotFound")));
    }
}
