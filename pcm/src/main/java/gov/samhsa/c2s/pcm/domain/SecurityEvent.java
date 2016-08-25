package gov.samhsa.c2s.pcm.domain;

public abstract class SecurityEvent{
	String ipAddress;
	
	public String getIpAddress() {
		return ipAddress;
	}

	public SecurityEvent(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
