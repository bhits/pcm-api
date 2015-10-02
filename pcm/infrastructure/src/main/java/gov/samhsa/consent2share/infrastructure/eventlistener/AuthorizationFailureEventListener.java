package gov.samhsa.consent2share.infrastructure.eventlistener;

import gov.samhsa.consent2share.common.UserContext;
import gov.samhsa.consent2share.infrastructure.securityevent.UnauthorizedAccessAttemptedEvent;

import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * The listener interface for receiving authorizationFailureEvent events. The
 * class that is interested in processing a authorizationFailureEvent event
 * implements this interface, and the object created with that class is
 * registered with a component using the component's
 * <code>addAuthorizationFailureEventListener<code> method. When
 * the authorizationFailureEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AuthorizationFailureEventEvent
 */
public class AuthorizationFailureEventListener extends EventListener {

	/** The user context. */
	UserContext userContext;

	/**
	 * Instantiates a new authorization failure event listener.
	 *
	 * @param eventService
	 *            the event service
	 * @param userContext
	 *            the user context
	 */
	public AuthorizationFailureEventListener(EventService eventService,
			UserContext userContext) {
		super(eventService);
		this.userContext = userContext;
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
				details.getRemoteAddress(), userContext.getCurrentUser()
						.getUsername()));
	}
}
