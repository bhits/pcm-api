/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.mhc.pcm.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.samhsa.mhc.pcm.domain.consent.ConsentTermsVersions;
import gov.samhsa.mhc.pcm.domain.provider.IndividualProvider;
import gov.samhsa.mhc.pcm.domain.provider.OrganizationalProvider;
import gov.samhsa.mhc.pcm.domain.reference.PurposeOfUseCode;
import gov.samhsa.mhc.pcm.service.util.CustomJsonDateDeserializer;
import gov.samhsa.mhc.pcm.service.util.CustomJsonDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;


/**
 * The Consent Attestation Dto.
 */
public class ConsentAttestationDto{

    private String consentId;
    /**
     * The consent reference number
     */
    private String consentReferenceNumber;
    /**
     * The consent end.
     */
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    private Date patientDateOfBirth;

    private String firstName;

    private String lastName;

    private String username;

    private boolean confirmConsentSign;

    private Date consentStart;

    private Date consentEnd;

    private ConsentTermsVersions consentTermsVersions;

    private Set<OrganizationalProvider> orgProvidersDisclosureIsMadeTo;

    private Set<IndividualProvider> indProvidersDisclosureIsMadeTo;

    private Set<OrganizationalProvider> orgProvidersPermittedToDisclosure;

    private Set<IndividualProvider> indProvidersPermittedToDisclosure;

    private Set<PurposeOfUseCode> PurposeOfUseCodes;

    private Set<String> doNotShareSensitivityPolicyDisplayNames;

    public String getConsentReferenceNumber() {
        return consentReferenceNumber;
    }

    public void setConsentReferenceNumber(String consentReferenceNumber) {
        this.consentReferenceNumber = consentReferenceNumber;
    }

    public Date getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    public void setPatientDateOfBirth(Date patientDateOfBirth) {
        this.patientDateOfBirth = patientDateOfBirth;
    }

    public boolean isConfirmConsentSign() {
        return confirmConsentSign;
    }

    public void setConfirmConsentSign(boolean confirmConsentSign) {
        this.confirmConsentSign = confirmConsentSign;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<OrganizationalProvider> getOrgProvidersDisclosureIsMadeTo() {
        return orgProvidersDisclosureIsMadeTo;
    }

    public void setOrgProvidersDisclosureIsMadeTo(Set<OrganizationalProvider> orgProvidersDisclosureIsMadeTo) {
        this.orgProvidersDisclosureIsMadeTo = orgProvidersDisclosureIsMadeTo;
    }

    public Set<OrganizationalProvider> getOrgProvidersPermittedToDisclosure() {
        return orgProvidersPermittedToDisclosure;
    }

    public void setOrgProvidersPermittedToDisclosure(Set<OrganizationalProvider> orgProvidersPermittedToDisclosure) {
        this.orgProvidersPermittedToDisclosure = orgProvidersPermittedToDisclosure;
    }

    public Set<PurposeOfUseCode> getPurposeOfUseCodes() {
        return PurposeOfUseCodes;
    }

    public void setPurposeOfUseCodes(Set<PurposeOfUseCode> purposeOfUseCodes) {
        PurposeOfUseCodes = purposeOfUseCodes;
    }

    public Set<IndividualProvider> getIndProvidersDisclosureIsMadeTo() {
        return indProvidersDisclosureIsMadeTo;
    }

    public void setIndProvidersDisclosureIsMadeTo(Set<IndividualProvider> indProvidersDisclosureIsMadeTo) {
        this.indProvidersDisclosureIsMadeTo = indProvidersDisclosureIsMadeTo;
    }

    public Set<IndividualProvider> getIndProvidersPermittedToDisclosure() {
        return indProvidersPermittedToDisclosure;
    }

    public void setIndProvidersPermittedToDisclosure(Set<IndividualProvider> indProvidersPermittedToDisclosure) {
        this.indProvidersPermittedToDisclosure = indProvidersPermittedToDisclosure;
    }

    public Set<String> getDoNotShareSensitivityPolicyDisplayNames() {
        return doNotShareSensitivityPolicyDisplayNames;
    }

    public void setDoNotShareSensitivityPolicyDisplayNames(Set<String> doNotShareSensitivityPolicyDisplayNames) {
        this.doNotShareSensitivityPolicyDisplayNames = doNotShareSensitivityPolicyDisplayNames;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getConsentStart() {
        return consentStart;
    }

    public void setConsentStart(Date consentStart) {
        this.consentStart = consentStart;
    }

    public Date getConsentEnd() {
        return consentEnd;
    }

    public void setConsentEnd(Date consentEnd) {
        this.consentEnd = consentEnd;
    }

    public ConsentTermsVersions getConsentTermsVersions() {
        return consentTermsVersions;
    }

    public void setConsentTermsVersions(ConsentTermsVersions consentTermsVersions) {
        this.consentTermsVersions = consentTermsVersions;
    }
}
