package gov.samhsa.pcm.domain;

public abstract class SecurityEvent{
	String ipAddress;
	
	public String getIpAddress() {
		return ipAddress;
	}

	public SecurityEvent(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
