package gov.samhsa.c2s.pcm.domain.consent;

import org.hibernate.envers.Audited;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "consent_terms_versions")
@Audited
public class ConsentTermsVersions {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20000) //Maximum of 20,000 characters (using utf-8 encoding)
    @Column(columnDefinition = "TEXT")
    private String consentTermsText;

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

    public String getConsentTermsText() {
        return consentTermsText;
    }

    public void setConsentTermsText(String consentTermsText) {
        this.consentTermsText = consentTermsText;
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
