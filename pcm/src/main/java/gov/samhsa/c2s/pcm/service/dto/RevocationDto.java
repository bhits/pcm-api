package gov.samhsa.c2s.pcm.service.dto;

/**
 * Created by tomson.ngassa on 6/3/2016.
 */
public class RevocationDto {
    private Long consentId;
    private boolean acceptTerms;

    public Long getConsentId() {
        return consentId;
    }

    public void setConsentId(Long consentId) {
        this.consentId = consentId;
    }

    public boolean isAcceptTerms() {
        return acceptTerms;
    }

    public void setAcceptTerms(boolean acceptTerms) {
        this.acceptTerms = acceptTerms;
    }
}