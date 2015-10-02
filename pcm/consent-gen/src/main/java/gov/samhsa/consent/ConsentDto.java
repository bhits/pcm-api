/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
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
package gov.samhsa.consent;

import java.util.Date;
import java.util.Set;

import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class ConsentExportDto.
 */
@XmlRootElement(name = "ConsentExport")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsentDto {

	/** The consent referenceid. */
	@XmlElement(name = "id")
	private String consentReferenceid;

	/** The patient export dto. */
	@XmlElement(name = "Patient")
	private PatientDto patientDto;

	/** The providers permitted to disclose. */
	@XmlElementWrapper(name = "IndividualProvidersPermittedToDiscloseList")
	@XmlElement(name = "IndividualProvidersPermittedToDisclose")
	private Set<IndividualProviderDto> providersPermittedToDisclose;

	/** The providers disclosure is made to. */
	@XmlElementWrapper(name = "IndividualProvidersDisclosureIsMadeToList")
	@XmlElement(name = "IndividualProvidersDisclosureIsMadeTo")
	private Set<IndividualProviderDto> providersDisclosureIsMadeTo;

	/** The organizational providers permitted to disclose. */
	@XmlElementWrapper(name = "OrganizationalProvidersPermittedToDiscloseList")
	@XmlElement(name = "OrganizationalProvidersPermittedToDisclose")
	private Set<OrganizationalProviderDto> organizationalProvidersPermittedToDisclose;

	/** The organizational providers disclosure is made to. */
	@XmlElementWrapper(name = "OrganizationalProvidersDisclosureIsMadeToList")
	@XmlElement(name = "OrganizationalProvidersDisclosureIsMadeTo")
	private Set<OrganizationalProviderDto> organizationalProvidersDisclosureIsMadeTo;

	/** The consent start. */
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	@XmlJavaTypeAdapter(XMLDateAdapter.class)
	private Date consentStart;

	/** The consent end. */
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	@XmlJavaTypeAdapter(XMLDateAdapter.class)
	private Date consentEnd;

	/** The signed date. */
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	@XmlJavaTypeAdapter(XMLDateAdapter.class)
	private Date signedDate;

	/** The revocation date. */
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	@XmlJavaTypeAdapter(XMLDateAdapter.class)
	private Date revocationDate;

	/** The version. */
	private Integer version;

	/** The legal representative. */
	@ManyToOne
	private PatientDto legalRepresentative;

	/** The do not share clinical document type codes. */
	@XmlElementWrapper(name = "doNotShareClinicalDocumentTypeCodesList")
	private Set<TypeCodesDto> doNotShareClinicalDocumentTypeCodes;

	/** The do not share clinical document section type codes. */
	@XmlElementWrapper(name = "doNotShareClinicalDocumentSectionTypeCodesList")
	private Set<TypeCodesDto> doNotShareClinicalDocumentSectionTypeCodes;

	/** The do not share sensitivity policy codes. */
	@XmlElementWrapper(name = "doNotShareSensitivityPolicyCodesList")
	private Set<TypeCodesDto> doNotShareSensitivityPolicyCodes;

	/** The do not share for purpose of use codes. */
	@XmlElementWrapper(name = "shareForPurposeOfUseCodesList")
	private Set<TypeCodesDto> shareForPurposeOfUseCodes;

	/** The do not share clinical concept codes. */
	@XmlElementWrapper(name = "doNotShareClinicalConceptCodesList")
	private Set<TypeCodesDto> doNotShareClinicalConceptCodes;

	public PatientDto getPatientDto() {
		return patientDto;
	}

	public void setPatientDto(PatientDto patientDto) {
		this.patientDto = patientDto;
	}

	/**
	 * Gets the providers permitted to disclose.
	 * 
	 * @return the providers permitted to disclose
	 */
	public Set<IndividualProviderDto> getProvidersPermittedToDisclose() {
		return providersPermittedToDisclose;
	}

	/**
	 * Sets the providers permitted to disclose.
	 * 
	 * @param providersPermittedToDisclose
	 *            the new providers permitted to disclose
	 */
	public void setProvidersPermittedToDisclose(
			Set<IndividualProviderDto> providersPermittedToDisclose) {
		this.providersPermittedToDisclose = providersPermittedToDisclose;
	}

	/**
	 * Gets the providers disclosure is made to.
	 * 
	 * @return the providers disclosure is made to
	 */
	public Set<IndividualProviderDto> getProvidersDisclosureIsMadeTo() {
		return providersDisclosureIsMadeTo;
	}

	/**
	 * Sets the providers disclosure is made to.
	 * 
	 * @param providersDisclosureIsMadeTo
	 *            the new providers disclosure is made to
	 */
	public void setProvidersDisclosureIsMadeTo(
			Set<IndividualProviderDto> providersDisclosureIsMadeTo) {
		this.providersDisclosureIsMadeTo = providersDisclosureIsMadeTo;
	}

	/**
	 * Gets the organizational providers permitted to disclose.
	 * 
	 * @return the organizational providers permitted to disclose
	 */
	public Set<OrganizationalProviderDto> getOrganizationalProvidersPermittedToDisclose() {
		return organizationalProvidersPermittedToDisclose;
	}

	/**
	 * Sets the organizational providers permitted to disclose.
	 * 
	 * @param organizationalProvidersPermittedToDisclose
	 *            the new organizational providers permitted to disclose
	 */
	public void setOrganizationalProvidersPermittedToDisclose(
			Set<OrganizationalProviderDto> organizationalProvidersPermittedToDisclose) {
		this.organizationalProvidersPermittedToDisclose = organizationalProvidersPermittedToDisclose;
	}

	/**
	 * Gets the organizational providers disclosure is made to.
	 * 
	 * @return the organizational providers disclosure is made to
	 */
	public Set<OrganizationalProviderDto> getOrganizationalProvidersDisclosureIsMadeTo() {
		return organizationalProvidersDisclosureIsMadeTo;
	}

	/**
	 * Sets the organizational providers disclosure is made to.
	 * 
	 * @param organizationalProvidersDisclosureIsMadeTo
	 *            the new organizational providers disclosure is made to
	 */
	public void setOrganizationalProvidersDisclosureIsMadeTo(
			Set<OrganizationalProviderDto> organizationalProvidersDisclosureIsMadeTo) {
		this.organizationalProvidersDisclosureIsMadeTo = organizationalProvidersDisclosureIsMadeTo;
	}

	/**
	 * Gets the consent start.
	 * 
	 * @return the consent start
	 */
	public Date getConsentStart() {
		return consentStart;
	}

	/**
	 * Sets the consent start.
	 * 
	 * @param consentStart
	 *            the new consent start
	 */
	public void setConsentStart(Date consentStart) {
		this.consentStart = consentStart;
	}

	/**
	 * Gets the consent end.
	 * 
	 * @return the consent end
	 */
	public Date getConsentEnd() {
		return consentEnd;
	}

	/**
	 * Sets the consent end.
	 * 
	 * @param consentEnd
	 *            the new consent end
	 */
	public void setConsentEnd(Date consentEnd) {
		this.consentEnd = consentEnd;
	}

	/**
	 * Gets the do not share clinical document type codes.
	 * 
	 * @return the do not share clinical document type codes
	 */
	public Set<TypeCodesDto> getDoNotShareClinicalDocumentTypeCodes() {
		return doNotShareClinicalDocumentTypeCodes;
	}

	/**
	 * Sets the do not share clinical document type codes.
	 * 
	 * @param doNotShareClinicalDocumentTypeCodes
	 *            the new do not share clinical document type codes
	 */
	public void setDoNotShareClinicalDocumentTypeCodes(
			Set<TypeCodesDto> doNotShareClinicalDocumentTypeCodes) {
		this.doNotShareClinicalDocumentTypeCodes = doNotShareClinicalDocumentTypeCodes;
	}

	/**
	 * Gets the do not share clinical document section type codes.
	 * 
	 * @return the do not share clinical document section type codes
	 */
	public Set<TypeCodesDto> getDoNotShareClinicalDocumentSectionTypeCodes() {
		return doNotShareClinicalDocumentSectionTypeCodes;
	}

	/**
	 * Sets the do not share clinical document section type codes.
	 * 
	 * @param doNotShareClinicalDocumentSectionTypeCodes
	 *            the new do not share clinical document section type codes
	 */
	public void setDoNotShareClinicalDocumentSectionTypeCodes(
			Set<TypeCodesDto> doNotShareClinicalDocumentSectionTypeCodes) {
		this.doNotShareClinicalDocumentSectionTypeCodes = doNotShareClinicalDocumentSectionTypeCodes;
	}

	/**
	 * Gets the do not share sensitivity policy codes.
	 * 
	 * @return the do not share sensitivity policy codes
	 */
	public Set<TypeCodesDto> getDoNotShareSensitivityPolicyCodes() {
		return doNotShareSensitivityPolicyCodes;
	}

	/**
	 * Sets the do not share sensitivity policy codes.
	 * 
	 * @param doNotShareSensitivityPolicyCodes
	 *            the new do not share sensitivity policy codes
	 */
	public void setDoNotShareSensitivityPolicyCodes(
			Set<TypeCodesDto> doNotShareSensitivityPolicyCodes) {
		this.doNotShareSensitivityPolicyCodes = doNotShareSensitivityPolicyCodes;
	}

	/**
	 * Gets the do not share for purpose of use codes.
	 * 
	 * @return the do not share for purpose of use codes
	 */
	public Set<TypeCodesDto> getShareForPurposeOfUseCodes() {
		return shareForPurposeOfUseCodes;
	}

	/**
	 * Sets the do not share for purpose of use codes.
	 * 
	 * @param doNotShareForPurposeOfUseCodes
	 *            the new do not share for purpose of use codes
	 */
	public void setShareForPurposeOfUseCodes(
			Set<TypeCodesDto> shareForPurposeOfUseCodes) {
		this.shareForPurposeOfUseCodes = shareForPurposeOfUseCodes;
	}

	/**
	 * Gets the signed date.
	 * 
	 * @return the signed date
	 */
	public Date getSignedDate() {
		return signedDate;
	}

	/**
	 * Sets the signed date.
	 * 
	 * @param signedDate
	 *            the new signed date
	 */
	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}

	/**
	 * Gets the revocation date.
	 * 
	 * @return the revocation date
	 */
	public Date getRevocationDate() {
		return revocationDate;
	}

	/**
	 * Sets the revocation date.
	 * 
	 * @param revocationDate
	 *            the new revocation date
	 */
	public void setRevocationDate(Date revocationDate) {
		this.revocationDate = revocationDate;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Gets the legal representative.
	 * 
	 * @return the legal representative
	 */
	public PatientDto getLegalRepresentative() {
		return legalRepresentative;
	}

	/**
	 * Sets the legal representative.
	 * 
	 * @param legalRepresentative
	 *            the new legal representative
	 */
	public void setLegalRepresentative(PatientDto legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	/**
	 * Gets the do not share clinical concept codes.
	 * 
	 * @return the do not share clinical concept codes
	 */
	public Set<TypeCodesDto> getDoNotShareClinicalConceptCodes() {
		return doNotShareClinicalConceptCodes;
	}

	/**
	 * Sets the do not share clinical concept codes.
	 * 
	 * @param doNotShareClinicalConceptCodes
	 *            the new do not share clinical concept codes
	 */
	public void setDoNotShareClinicalConceptCodes(
			Set<TypeCodesDto> doNotShareClinicalConceptCodes) {
		this.doNotShareClinicalConceptCodes = doNotShareClinicalConceptCodes;
	}

	/**
	 * Gets the consent referenceid.
	 * 
	 * @return the consent referenceid
	 */
	public String getConsentReferenceid() {
		return consentReferenceid;
	}

	/**
	 * Sets the consent referenceid.
	 * 
	 * @param consentReferenceid
	 *            the new consent referenceid
	 */
	public void setConsentReferenceid(String consentReferenceid) {
		this.consentReferenceid = consentReferenceid;
	}
}
