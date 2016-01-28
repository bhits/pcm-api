package gov.samhsa.mhc.pcm.infrastructure.securityevent;

import gov.samhsa.mhc.pcm.domain.SecurityEvent;

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
