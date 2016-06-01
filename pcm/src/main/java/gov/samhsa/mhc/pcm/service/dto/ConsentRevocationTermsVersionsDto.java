package gov.samhsa.mhc.pcm.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.samhsa.mhc.pcm.service.util.CustomJsonDateDeserializer;
import gov.samhsa.mhc.pcm.service.util.CustomJsonDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ConsentRevocationTermsVersionsDto {

    private Long id;

    private String consentRevokeTermsText;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    @JsonSerialize(using = CustomJsonDateSerializer.class)
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
