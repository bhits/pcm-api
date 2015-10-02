package gov.samhsa.consent2share.infrastructure.securityevent;

import gov.samhsa.consent2share.domain.SecurityEvent;

public class AuthenticationFailedEvent extends SecurityEvent {
	
	String userName;

	public AuthenticationFailedEvent(String ipAddress, String userName) {
		super(ipAddress);
		this.userName=userName;
	}
	
	public String getUserName() {
		return userName;
	}
}
