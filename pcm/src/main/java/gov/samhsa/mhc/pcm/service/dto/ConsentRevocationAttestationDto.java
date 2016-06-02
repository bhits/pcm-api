package gov.samhsa.mhc.pcm.service.dto;

public class ConsentRevocationAttestationDto {
    private String consentReferenceId;

    private String attesterEmail;

    private String attesterLastName;

    private String attesterFirstName;

    private String attesterMiddleName;

    private String attesterByUser;

    private boolean consentRevokeTermsAccepted;

    private String consentRevokeTermsText;

    public String getConsentReferenceId() {
        return consentReferenceId;
    }

    public void setConsentReferenceId(String consentReferenceId) {
        this.consentReferenceId = consentReferenceId;
    }

    public String getAttesterEmail() {
        return attesterEmail;
    }

    public void setAttesterEmail(String attesterEmail) {
        this.attesterEmail = attesterEmail;
    }

    public String getAttesterLastName() {
        return attesterLastName;
    }

    public void setAttesterLastName(String attesterLastName) {
        this.attesterLastName = attesterLastName;
    }

    public String getAttesterFirstName() {
        return attesterFirstName;
    }

    public void setAttesterFirstName(String attesterFirstName) {
        this.attesterFirstName = attesterFirstName;
    }

    public String getAttesterMiddleName() {
        return attesterMiddleName;
    }

    public void setAttesterMiddleName(String attesterMiddleName) {
        this.attesterMiddleName = attesterMiddleName;
    }

    public String getAttesterByUser() {
        return attesterByUser;
    }

    public void setAttesterByUser(String attesterByUser) {
        this.attesterByUser = attesterByUser;
    }

    public boolean isConsentRevokeTermsAccepted() {
        return consentRevokeTermsAccepted;
    }

    public void setConsentRevokeTermsAccepted(boolean consentRevokeTermsAccepted) {
        this.consentRevokeTermsAccepted = consentRevokeTermsAccepted;
    }

    public String getConsentRevokeTermsText() {
        return consentRevokeTermsText;
    }

    public void setConsentRevokeTermsText(String consentRevokeTermsText) {
        this.consentRevokeTermsText = consentRevokeTermsText;
    }

}
