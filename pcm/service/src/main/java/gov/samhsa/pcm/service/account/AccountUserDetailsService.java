package gov.samhsa.pcm.service.account;

import gov.samhsa.pcm.domain.account.Users;
import gov.samhsa.pcm.domain.account.UsersRepository;

import java.util.Calendar;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The Class AccountUserDetailsService.
 */
public class AccountUserDetailsService implements UserDetailsService {

	/** The users repository. */
	UsersRepository usersRepository;

	/** The max failed attempts. */
	private short maxFailedAttempts;

	/** The auto unlock interval. */
	private long autoUnlockInterval;

	/**
	 * Instantiates a new account user details service.
	 *
	 * @param maxFailedAttempts
	 *            the max failed attempts
	 * @param autoUnlockInterval
	 *            the auto unlock interval
	 * @param usersRepository
	 *            the users repository
	 */
	public AccountUserDetailsService(short maxFailedAttempts,
			long autoUnlockInterval, UsersRepository usersRepository) {
		this.maxFailedAttempts = maxFailedAttempts;
		this.autoUnlockInterval = autoUnlockInterval;
		this.usersRepository = usersRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Users user = usersRepository.loadUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username/password");
		}
		if (user.getFailedLoginAttempts() >= maxFailedAttempts) {
			Calendar cal = Calendar.getInstance();
			if (user.getLockoutTime() != null)
				if (cal.getTimeInMillis()
						- user.getLockoutTime().getTimeInMillis() >= autoUnlockInterval) {
					user.setAccountNonLocked(true);
					user.setLockoutTime(null);
					user.setFailedLoginAttemptsToZero();
					// Fix issue#515 Now update/reset Login Attempt Counter to
					// database after lockouttime
					usersRepository.updateUser(user);
					return user;
				}
			user.setAccountNonLocked(false);
		}
		return user;
	}
}
