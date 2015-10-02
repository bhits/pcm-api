package gov.samhsa.consent2share.service.account;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class AccountUserDetailsServiceTest {
	@Mock
	UsersRepository usersRepository;

	@InjectMocks
	AccountUserDetailsService accountUserDetailsService = new AccountUserDetailsService(
			(short) 3, 300000, usersRepository);

	private String username = "username";

	@Test
	public void testLoadUserByUsername_when_succeeds() {
		Users user = mock(Users.class);
		when(usersRepository.loadUserByUsername(username)).thenReturn(user);
		when(user.getFailedLoginAttempts()).thenReturn(0);
		when(user.getLockoutTime()).thenReturn(null);
		assertEquals(accountUserDetailsService.loadUserByUsername(username),
				user);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsername_when_username_is_wrong_verify_that_UsernameNotFoundException_is_thrown() {
		when(usersRepository.loadUserByUsername(username)).thenReturn(null);
		accountUserDetailsService.loadUserByUsername(username);
	}

	@Test()
	public void testLoadUserByUsername_when_account_is_lockout_verify_that_lockout_flag_is_set() {
		Users user = mock(Users.class);
		when(usersRepository.loadUserByUsername(username)).thenReturn(user);
		when(user.getFailedLoginAttempts()).thenReturn(5);
		when(user.getLockoutTime()).thenReturn(Calendar.getInstance());
		assertEquals(accountUserDetailsService.loadUserByUsername(username),
				user);
		verify(user).setAccountNonLocked(false);
	}

	@Test()
	public void testLoadUserByUsername_when_account_is_lockout_verify_that_account_is_unlocked_after_5_minutes() {
		Users user = mock(Users.class);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - 300000);
		when(usersRepository.loadUserByUsername(username)).thenReturn(user);
		when(user.getFailedLoginAttempts()).thenReturn(5);
		when(user.getLockoutTime()).thenReturn(cal);
		assertEquals(accountUserDetailsService.loadUserByUsername(username),
				user);
		verify(user).setLockoutTime(null);
		verify(user).setFailedLoginAttemptsToZero();
		verify(user).setAccountNonLocked(true);
		verify(usersRepository).updateUser(any(Users.class));
	}

}
