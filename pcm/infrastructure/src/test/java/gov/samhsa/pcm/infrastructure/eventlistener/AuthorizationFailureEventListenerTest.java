package gov.samhsa.pcm.infrastructure.eventlistener;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import gov.samhsa.pcm.common.AuthenticatedUser;
import gov.samhsa.pcm.common.UserContext;
import gov.samhsa.pcm.infrastructure.eventlistener.AuthorizationFailureEventListener;
import gov.samhsa.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.pcm.infrastructure.securityevent.UnauthorizedAccessAttemptedEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationFailureEventListenerTest {
	
	final static String IP_ADDRESS="192.168.0.1";
	final static String USER_NAME="user1";
	
	@Mock
	UserContext userContext;
	
	@Mock
	EventService eventService;
	
	@InjectMocks
	AuthorizationFailureEventListener authorizationFailureEventListener;
	
	@Test
	public void testHandle() {
		AuthorizationFailureEvent event=mock(AuthorizationFailureEvent.class);
		WebAuthenticationDetails details=mock(WebAuthenticationDetails.class);
		Authentication authentication=mock(Authentication.class);
		AuthenticatedUser user=mock(AuthenticatedUser.class);
		doReturn(authentication).when(event).getAuthentication();
		doReturn(details).when(authentication).getDetails();
		doReturn(IP_ADDRESS).when(details).getRemoteAddress();
		doReturn(user).when(userContext).getCurrentUser();
		doReturn(USER_NAME).when(user).getUsername();
		authorizationFailureEventListener.handle(event);
		verify(eventService).raiseSecurityEvent(any(UnauthorizedAccessAttemptedEvent.class));
		verify(userContext).getCurrentUser();		
	}
}
