package gov.samhsa.bhits.pcm.infrastructure.securityevent;

import gov.samhsa.bhits.pcm.domain.SecurityEvent;

public class FileDownloadedEvent extends SecurityEvent {
    String userName;

    String fileDownloaded;

    public FileDownloadedEvent(String ipAddress, String userName,
                               String fileDownloaded) {
        super(ipAddress);
        this.userName = userName;
        this.fileDownloaded = fileDownloaded;
    }

    public String getUserName() {
        return userName;
    }

    public String getFileDownloaded() {
        return fileDownloaded;
    }


}
