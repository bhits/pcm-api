package gov.samhsa.mhc.pcm.domain.consent;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "consent_revocation_terms_versions")
public class ConsentRevocationTermsVersions {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20000) //Maximum of 20,000 characters (using utf-8 encoding)
    @Column(columnDefinition = "TEXT")
    private String consentRevokeTermsText;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date addedDateTime;

    //When set to true, this version will not be shown to users regardless of whether it is the most recent or not
    private Boolean versionDisabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsentRevokeTermsText() {
        return consentRevokeTermsText;
    }

    public void setConsentRevokeTermsText(String consentRevokeTermsText) {
        this.consentRevokeTermsText = consentRevokeTermsText;
    }

    public Date getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(Date addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public Boolean getVersionDisabled() {
        return versionDisabled;
    }

    public void setVersionDisabled(Boolean versionDisabled) {
        this.versionDisabled = versionDisabled;
    }

}
