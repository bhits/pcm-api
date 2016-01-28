package gov.samhsa.mhc.pcm.domain.account;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class Users implements Serializable,  
org.springframework.security.core.userdetails.UserDetails{
	
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	private int failedLoginAttempts;
    private String password;
    private Calendar lockoutTime;
    final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private final String username;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

	public Users(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, Set<GrantedAuthority> authorities) {
    	this(0,username, password, enabled, enabled, true, credentialsNonExpired, authorities,null);
    }


	public Users(String username, String password, Set<GrantedAuthority> authorities) {
        this(0,username, password, true, true, true, true, authorities,null);
    }
	
	public Users(int failedLoginAttempts,String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, Set<GrantedAuthority> authorities) {
		this(failedLoginAttempts,username, password, enabled, accountNonExpired, true, credentialsNonExpired, authorities,null);
    }
	
	public Users(int failedLoginAttempts,String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, Set<GrantedAuthority> authorities,Calendar cal) {
		this(failedLoginAttempts,username, password, enabled, accountNonExpired, true, credentialsNonExpired, authorities, cal);
	}
	
	public Users(int failedLoginAttempts,String username, String password, boolean enabled, boolean accountNonExpired, boolean accountNonLocked,
            boolean credentialsNonExpired, Set<GrantedAuthority> authorities,Calendar cal) {
		if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
		this.failedLoginAttempts=failedLoginAttempts;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = authorities;
        this.lockoutTime=cal;
    }


	public void increaseFailedLoginAttempts(){
		failedLoginAttempts++;
	}
	
	

	public void setFailedLoginAttemptsToZero(){
		failedLoginAttempts=0;
	}
	
	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}


	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}


	@Override
	public Set<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}


	@Override
	public String getPassword() {
		return this.password;
	}


	@Override
	public String getUsername() {
		return this.username;
	}


	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}


	@Override
	public boolean isEnabled() {
		return this.enabled;
	}


	public Calendar getLockoutTime() {
		return lockoutTime;
	}


	public void setLockoutTime(Calendar lockoutTime) {
		this.lockoutTime = lockoutTime;
	}
	
	public void setAccountNonLocked(boolean isAccountNonLocked){
		this.accountNonLocked = isAccountNonLocked;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	
	@Override
	public int hashCode() {
		return username.hashCode();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Users) {
            return username.equals(((Users) obj).username);
        }
        return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
		return sb.toString();
	}
	
	
	
}
