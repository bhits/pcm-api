package gov.samhsa.bhits.pcm.infrastructure.eventlistener;

import static org.junit.Assert.*;

import java.util.Calendar;

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
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginFailureEventListenerTest {

    @Mock
    UsersRepository usersRepository;

    @Mock
    EventService eventService;

    @InjectMocks
    LoginFailureEventListener loginFailureEventListener=new LoginFailureEventListener((short)3, eventService, usersRepository);

    private String username="username";

    @Test
    public void testHandle_when_account_is_not_locked() {
        AuthenticationFailureBadCredentialsEvent loginFailureEvent=mock(AuthenticationFailureBadCredentialsEvent.class);
        Authentication authentication=mock(Authentication.class);
        WebAuthenticationDetails webAuthenticationDetails=mock(WebAuthenticationDetails.class);
        Object name=new String(username);
        when(loginFailureEvent.getAuthentication()).thenReturn(authentication);
        when(authentication.getDetails()).thenReturn(webAuthenticationDetails);
        when(webAuthenticationDetails.getRemoteAddress()).thenReturn("127.0.0.1");
        when(authentication.getPrincipal()).thenReturn(name);
        Users user=mock(Users.class);
        when(user.getFailedLoginAttempts()).thenReturn(0);
        when(usersRepository.loadUserByUsername(username)).thenReturn(user);
        loginFailureEventListener.handle(loginFailureEvent);
        verify(usersRepository).updateUser(user);
    }

    @Test
    public void testHandle_when_account_is_locked() {
        AuthenticationFailureBadCredentialsEvent loginFailureEvent=mock(AuthenticationFailureBadCredentialsEvent.class);
        Authentication authentication=mock(Authentication.class);
        WebAuthenticationDetails webAuthenticationDetails=mock(WebAuthenticationDetails.class);
        Object name=new String(username);
        when(loginFailureEvent.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(name);
        when(authentication.getDetails()).thenReturn(webAuthenticationDetails);
        when(webAuthenticationDetails.getRemoteAddress()).thenReturn("127.0.0.1");
        Users user=mock(Users.class);
        when(user.getFailedLoginAttempts()).thenReturn(3);
        when(usersRepository.loadUserByUsername(username)).thenReturn(user);
        loginFailureEventListener.handle(loginFailureEvent);
        verify(user).setLockoutTime(any(Calendar.class));
        verify(usersRepository).updateUser(user);
    }

    @Test
    public void testCanHandle_when_event_matches() {
        AuthenticationFailureBadCredentialsEvent event=mock(AuthenticationFailureBadCredentialsEvent.class);
        assertEquals(loginFailureEventListener.canHandle(event),true);
    }

    @Test
    public void testCanHandle_when_event_unmatch() {
        AuthenticationSuccessEvent event=mock(AuthenticationSuccessEvent.class);
        assertEquals(loginFailureEventListener.canHandle(event),false);
    }

}
