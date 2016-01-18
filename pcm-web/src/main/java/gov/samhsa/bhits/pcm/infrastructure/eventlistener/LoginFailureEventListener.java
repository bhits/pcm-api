package gov.samhsa.bhits.pcm.infrastructure.eventlistener;

import gov.samhsa.bhits.pcm.domain.account.Users;
import gov.samhsa.bhits.pcm.domain.account.UsersRepository;
import gov.samhsa.bhits.pcm.infrastructure.securityevent.AuthenticationFailedEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Calendar;

/**
 * The listener interface for receiving loginFailureEvent events. The class that
 * is interested in processing a loginFailureEvent event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addLoginFailureEventListener<code> method. When
 * the loginFailureEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AuthenticationFailureBadCredentialsEvent
 */
public class LoginFailureEventListener extends EventListener {

    /**
     * The users repository.
     */
    UsersRepository usersRepository;

    /**
     * The max_failed_attempts.
     */
    private short max_failed_attempts;

    /**
     * Instantiates a new login failure event listener.
     *
     * @param max_failed_attempts the max_failed_attempts
     * @param eventService        the event service
     * @param usersRepository     the users repository
     */
    public LoginFailureEventListener(short max_failed_attempts,
                                     EventService eventService, UsersRepository usersRepository) {
        super(eventService);
        this.max_failed_attempts = max_failed_attempts;
        this.usersRepository = usersRepository;
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
        return event instanceof AuthenticationFailureBadCredentialsEvent;
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
        AuthenticationFailureBadCredentialsEvent loginFailureEvent = (AuthenticationFailureBadCredentialsEvent) event;
        Object name = loginFailureEvent.getAuthentication().getPrincipal();
        Users user = usersRepository.loadUserByUsername((String) name);
        eventService.raiseSecurityEvent(new AuthenticationFailedEvent(
                ((WebAuthenticationDetails) loginFailureEvent
                        .getAuthentication().getDetails()).getRemoteAddress(),
                (String) name));
        if (user != null) {
            // update the failed login count
            user.increaseFailedLoginAttempts();
            if (user.getFailedLoginAttempts() >= max_failed_attempts) {
                Calendar cal = Calendar.getInstance();
                user.setLockoutTime(cal);
            }
            // update user
            usersRepository.updateUser(user);
        }
    }
}
