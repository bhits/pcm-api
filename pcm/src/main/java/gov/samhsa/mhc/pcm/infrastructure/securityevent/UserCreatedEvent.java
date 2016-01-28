package gov.samhsa.mhc.pcm.infrastructure.securityevent;

import gov.samhsa.mhc.pcm.domain.SecurityEvent;

public class UserCreatedEvent extends SecurityEvent {
    String userName;

    public UserCreatedEvent(String ipAddress, String userName) {
        super(ipAddress);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}