package gov.samhsa.bhits.pcm.infrastructure.securityevent;

import gov.samhsa.bhits.pcm.domain.SecurityEvent;

public class FileUploadedEvent extends SecurityEvent {
    String userName;

    String fileUploaded;

    public FileUploadedEvent(String ipAddress, String userName,
                             String fileUploaded) {
        super(ipAddress);
        this.userName = userName;
        this.fileUploaded = fileUploaded;
    }

    public String getUserName() {
        return userName;
    }

    public String getFileUploaded() {
        return fileUploaded;
    }

}
