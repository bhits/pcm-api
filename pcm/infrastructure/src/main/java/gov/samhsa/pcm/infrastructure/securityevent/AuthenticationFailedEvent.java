package gov.samhsa.pcm.infrastructure.securityevent;

import gov.samhsa.pcm.domain.SecurityEvent;

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
