package gov.samhsa.consent2share.domain;

public abstract class SecurityEvent{
	String ipAddress;
	
	public String getIpAddress() {
		return ipAddress;
	}

	public SecurityEvent(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
