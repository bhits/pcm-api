package gov.samhsa.bhits.pcm.infrastructure.eventlistener;

import gov.samhsa.bhits.pcm.domain.account.Users;
import gov.samhsa.bhits.pcm.domain.account.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginSuccessEventListenerTest {

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    LoginSuccessEventListener loginSuccessEventListener;

    @Test
    public void testHandle() {
        AuthenticationSuccessEvent loginSuccessEvent=mock(AuthenticationSuccessEvent.class);
        Authentication authentication=mock(Authentication.class);
        Users user=mock(Users.class);
        when(loginSuccessEvent.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        loginSuccessEventListener.handle(loginSuccessEvent);
        verify(user).setFailedLoginAttemptsToZero();
        verify(usersRepository).updateUser(user);
    }

    @Test
    public void testCanHandle_when_event_unmatch() {
        AuthenticationFailureBadCredentialsEvent event=mock(AuthenticationFailureBadCredentialsEvent.class);
        assertEquals(loginSuccessEventListener.canHandle(event),false);
    }

    @Test
    public void testCanHandle_when_event_matches() {
        AuthenticationSuccessEvent event=mock(AuthenticationSuccessEvent.class);
        assertEquals(loginSuccessEventListener.canHandle(event),true);
    }

}
