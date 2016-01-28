package gov.samhsa.mhc.pcm.domain;

public abstract class SecurityEvent{
	String ipAddress;
	
	public String getIpAddress() {
		return ipAddress;
	}

	public SecurityEvent(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
