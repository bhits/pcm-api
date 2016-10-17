package gov.samhsa.c2s.pcm.service.dto;

public class AttestationDto {
    private Long consentId;
    private String attesterIpAddress;

    public Long getConsentId() {
        return consentId;
    }

    public void setConsentId(Long consentId) {
        this.consentId = consentId;
    }

    public String getAttesterIpAddress() {
        return attesterIpAddress;
    }

    public void setAttesterIpAddress(String attesterIpAddress) {
        this.attesterIpAddress = attesterIpAddress;
    }
}
