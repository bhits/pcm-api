package gov.samhsa.c2s.pcm.infrastructure.securityevent;

import gov.samhsa.c2s.pcm.domain.SecurityEvent;

public class AuthenticationFailedEvent extends SecurityEvent {

    String userName;

    public AuthenticationFailedEvent(String ipAddress, String userName) {
        super(ipAddress);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
