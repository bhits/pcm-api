package gov.samhsa.consent2share.infrastructure.securityevent;

import gov.samhsa.consent2share.domain.SecurityEvent;

public class UnauthorizedAccessAttemptedEvent extends SecurityEvent{
	
	String userName;
	
	public String getUserName() {
		return userName;
	}

	public UnauthorizedAccessAttemptedEvent(String ipAddress, String userName) {
		super(ipAddress);
		this.userName = userName;
	}
	
}
