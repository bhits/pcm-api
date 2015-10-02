package gov.samhsa.consent2share.infrastructure.securityevent;

import gov.samhsa.consent2share.domain.SecurityEvent;

public class MaliciousFileDetectedEvent extends SecurityEvent {
	String userName;
	
	String fileName;
	
	public MaliciousFileDetectedEvent(String ipAddress, String userName,
			String fileName) {
		super(ipAddress);
		this.userName = userName;
		this.fileName = fileName;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getFileName() {
		return fileName;
	}


}
