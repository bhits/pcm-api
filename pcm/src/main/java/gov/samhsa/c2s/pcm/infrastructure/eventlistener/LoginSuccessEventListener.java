package gov.samhsa.c2s.pcm.infrastructure.eventlistener;

import gov.samhsa.c2s.pcm.domain.account.Users;
import gov.samhsa.c2s.pcm.domain.account.UsersRepository;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

/**
 * The listener interface for receiving loginSuccessEvent events. The class that
 * is interested in processing a loginSuccessEvent event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addLoginSuccessEventListener<code> method. When
 * the loginSuccessEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AuthenticationSuccessEvent
 */
public class LoginSuccessEventListener extends EventListener {

    /**
     * The users repository.
     */
    private UsersRepository usersRepository;

    /**
     * Instantiates a new login success event listener.
     *
     * @param eventService    the event service
     * @param usersRepository the users repository
     */
    public LoginSuccessEventListener(EventService eventService,
                                     UsersRepository usersRepository) {
        super(eventService);
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
        return event instanceof AuthenticationSuccessEvent;
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
        AuthenticationSuccessEvent loginSuccessEvent = (AuthenticationSuccessEvent) event;
        Object name = loginSuccessEvent.getAuthentication().getPrincipal();
        if (name != null) {
            Users user = (Users) name;
            user.setFailedLoginAttemptsToZero();
            usersRepository.updateUser(user);
        }
    }
}
