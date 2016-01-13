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
package gov.samhsa.bhits.pcm.domain.consent;

import gov.samhsa.bhits.pcm.domain.DomainEventManager;
import gov.samhsa.bhits.pcm.domain.consent.event.ConsentRevokeSubmittedEvent;
import gov.samhsa.bhits.pcm.domain.patient.Patient;
import gov.samhsa.bhits.pcm.domain.reference.ClinicalConceptCode;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class Consent.
 */
@Entity
@Audited(withModifiedFlag = true)
@Table(name = "consent", indexes = { @Index(name = "consent_consent_reference_id_idx", columnList = "consent_reference_id", unique = true) })
public class Consent {

	/** The name. */
	@NotNull
	@Size(max = 30)
	private String name;

	/** The description. */
	@NotNull
	@Size(max = 250)
	private String description;

	/** The signed pdf consent. */
	@OneToOne(cascade = CascadeType.ALL)
	@Basic(fetch = FetchType.LAZY)
	private SignedPDFConsent signedPdfConsent;

	/** The reference id. */
	@NotNull
	@Column(name = "consent_reference_id")
	private String consentReferenceId;

	/** The unsigned pdf consent revoke. */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotAudited
	private byte[] unsignedPdfConsentRevoke;

	/** The signed pdf consent revoke. */
	@OneToOne(cascade = CascadeType.ALL)
	@Basic(fetch = FetchType.LAZY)
	private SignedPDFConsentRevocation signedPdfConsentRevoke;

	/** The unsigned pdf consent. */
	@NotNull
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotAudited
	private byte[] unsignedPdfConsent;

	/** The exported CDA R2 consent. */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotAudited
	private byte[] exportedCDAR2Consent;

	/** The exported XACML consent. */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotAudited
	private byte[] exportedXACMLConsent;

	/**
	 * The XACML policy to give access to consentTo(Recipient) provider for ccd.
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotAudited
	private byte[] xacmlCcd;

	/**
	 * The XACML policy to give access to consentFrom(Intermediary) provider for
	 * consent pdf.
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotAudited
	private byte[] xacmlPdfConsentFrom;

	/**
	 * The XACML policy to give access to consentTo(Recipient) provider for
	 * consent pdf.
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@NotAudited
	private byte[] xacmlPdfConsentTo;

	/** The providers permitted to disclose. */
	@ElementCollection
	@CollectionTable(name = "ConsentIndividualProviderPermittedToDisclose", joinColumns = @JoinColumn(name = "CONSENT_ID"))
	@Basic(fetch = FetchType.EAGER)
	@NotAudited
	private Set<ConsentIndividualProviderPermittedToDisclose> providersPermittedToDisclose = new HashSet<ConsentIndividualProviderPermittedToDisclose>();

	/** The providers disclosure is made to. */
	@ElementCollection
	@CollectionTable(name = "ConsentIndividualProviderDisclosureIsMadeTo", joinColumns = @JoinColumn(name = "CONSENT_ID"))
	@Basic(fetch = FetchType.EAGER)
	@NotAudited
	private Set<ConsentIndividualProviderDisclosureIsMadeTo> providersDisclosureIsMadeTo = new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>();

	/** The organizational providers permitted to disclose. */
	@ElementCollection
	@CollectionTable(name = "ConsentOrganizationalProviderPermittedToDisclose", joinColumns = @JoinColumn(name = "CONSENT_ID"))
	@Basic(fetch = FetchType.EAGER)
	@NotAudited
	private Set<ConsentOrganizationalProviderPermittedToDisclose> organizationalProvidersPermittedToDisclose = new HashSet<ConsentOrganizationalProviderPermittedToDisclose>();

	/** The organizational providers disclosure is made to. */
	@ElementCollection
	@CollectionTable(name = "ConsentOrganizationalProviderDisclosureIsMadeTo", joinColumns = @JoinColumn(name = "CONSENT_ID"))
	@Basic(fetch = FetchType.EAGER)
	@NotAudited
	private Set<ConsentOrganizationalProviderDisclosureIsMadeTo> organizationalProvidersDisclosureIsMadeTo = new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>();

	/** The do not share clinical document type codes. */
	@ElementCollection
	@CollectionTable(name = "ConsentDoNotShareClinicalDocumentTypeCode", joinColumns = @JoinColumn(name = "CONSENT_ID"))
	@Basic(fetch = FetchType.EAGER)
	@NotAudited
	// @Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
	private Set<ConsentDoNotShareClinicalDocumentTypeCode> doNotShareClinicalDocumentTypeCodes = new HashSet<ConsentDoNotShareClinicalDocumentTypeCode>();

	/** The do not share clinical document section type codes. */
	@ElementCollection
	@CollectionTable(name = "ConsentDoNotShareClinicalDocumentSectionTypeCode", joinColumns = @JoinColumn(name = "CONSENT_ID"))
	@Basic(fetch = FetchType.EAGER)
	@NotAudited
	private Set<ConsentDoNotShareClinicalDocumentSectionTypeCode> doNotShareClinicalDocumentSectionTypeCodes = new HashSet<ConsentDoNotShareClinicalDocumentSectionTypeCode>();

	/** The do not share clinical concept codes. */
	@ElementCollection
	@CollectionTable(name = "ConsentDoNotShareClinicalConceptCodes", joinColumns = @JoinColumn(name = "CONSENT_ID"))
	@Basic(fetch = FetchType.EAGER)
	@OneToMany(cascade = CascadeType.ALL)
	@NotAudited
	private Set<ClinicalConceptCode> doNotShareClinicalConceptCodes = new HashSet<ClinicalConceptCode>();

	/** The do not share sensitivity policy codes. */
	@ElementCollection
	@CollectionTable(name = "ConsentDoNotShareSensitivityPolicyCode", joinColumns = @JoinColumn(name = "CONSENT_ID"))
	@Basic(fetch = FetchType.EAGER)
	@NotAudited
	private Set<ConsentDoNotShareSensitivityPolicyCode> doNotShareSensitivityPolicyCodes = new HashSet<ConsentDoNotShareSensitivityPolicyCode>();

	/** The do not share for purpose of use codes. */
	@ElementCollection
	@CollectionTable(name = "ConsentShareForPurposeOfUseCode", joinColumns = @JoinColumn(name = "CONSENT_ID"))
	@Basic(fetch = FetchType.EAGER)
	@NotAudited
	private Set<ConsentShareForPurposeOfUseCode> shareForPurposeOfUseCodes = new HashSet<ConsentShareForPurposeOfUseCode>();

	/** The consent revoked. */
	private Boolean consentRevoked;

	/** The consent revokation type. */
	private String consentRevokationType;

	/** The start date. */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date startDate;

	/** The end date. */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date endDate;

	/** The signed date. */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date signedDate;

	/** The revocation date. */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date revocationDate;

	/** The patient. */
	@ManyToOne
	private Patient patient;

	/** The legal representative. */
	@ManyToOne
	private Patient legalRepresentative;

	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	/** The version. */
	@Version
	@Column(name = "version")
	private Integer version;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;

	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
		return this.version;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the signed pdf consent.
	 *
	 * @return the signed pdf consent
	 */

	public SignedPDFConsent getSignedPdfConsent() {
		return this.signedPdfConsent;
	}

	/**
	 * Sets the signed pdf consent.
	 *
	 * @param signedPdfConsent
	 *            the new signed pdf consent
	 */
	public void setSignedPdfConsent(SignedPDFConsent signedPdfConsent) {
		this.signedPdfConsent = signedPdfConsent;

	}

	/**
	 * Gets the unsigned pdf consent.
	 *
	 * @return the unsigned pdf consent
	 */
	public byte[] getUnsignedPdfConsent() {
		return this.unsignedPdfConsent;
	}

	/**
	 * Sets the unsigned pdf consent.
	 *
	 * @param unsignedPdfConsent
	 *            the new unsigned pdf consent
	 */
	public void setUnsignedPdfConsent(byte[] unsignedPdfConsent) {
		this.unsignedPdfConsent = unsignedPdfConsent;
	}

	/**
	 * Gets the providers permitted to disclose.
	 *
	 * @return the providers permitted to disclose
	 */
	public Set<ConsentIndividualProviderPermittedToDisclose> getProvidersPermittedToDisclose() {
		return this.providersPermittedToDisclose;
	}

	/**
	 * Sets the providers permitted to disclose.
	 *
	 * @param providersPermittedToDisclose
	 *            the new providers permitted to disclose
	 */
	public void setProvidersPermittedToDisclose(
			Set<ConsentIndividualProviderPermittedToDisclose> providersPermittedToDisclose) {
		this.providersPermittedToDisclose = providersPermittedToDisclose;
	}

	/**
	 * Gets the providers disclosure is made to.
	 *
	 * @return the providers disclosure is made to
	 */
	public Set<ConsentIndividualProviderDisclosureIsMadeTo> getProvidersDisclosureIsMadeTo() {
		return this.providersDisclosureIsMadeTo;
	}

	/**
	 * Sets the providers disclosure is made to.
	 *
	 * @param providersDisclosureIsMadeTo
	 *            the new providers disclosure is made to
	 */
	public void setProvidersDisclosureIsMadeTo(
			Set<ConsentIndividualProviderDisclosureIsMadeTo> providersDisclosureIsMadeTo) {
		this.providersDisclosureIsMadeTo = providersDisclosureIsMadeTo;
	}

	/**
	 * Gets the organizational providers permitted to disclose.
	 *
	 * @return the organizational providers permitted to disclose
	 */
	public Set<ConsentOrganizationalProviderPermittedToDisclose> getOrganizationalProvidersPermittedToDisclose() {
		return organizationalProvidersPermittedToDisclose;
	}

	/**
	 * Sets the organizational providers permitted to disclose.
	 *
	 * @param organizationalProvidersPermittedToDisclose
	 *            the new organizational providers permitted to disclose
	 */
	public void setOrganizationalProvidersPermittedToDisclose(
			Set<ConsentOrganizationalProviderPermittedToDisclose> organizationalProvidersPermittedToDisclose) {
		this.organizationalProvidersPermittedToDisclose = organizationalProvidersPermittedToDisclose;
	}

	/**
	 * Gets the organizational providers disclosure is made to.
	 *
	 * @return the organizational providers disclosure is made to
	 */
	public Set<ConsentOrganizationalProviderDisclosureIsMadeTo> getOrganizationalProvidersDisclosureIsMadeTo() {
		return organizationalProvidersDisclosureIsMadeTo;
	}

	/**
	 * Sets the organizational providers disclosure is made to.
	 *
	 * @param organizationalProvidersDisclosureIsMadeTo
	 *            the new organizational providers disclosure is made to
	 */
	public void setOrganizationalProvidersDisclosureIsMadeTo(
			Set<ConsentOrganizationalProviderDisclosureIsMadeTo> organizationalProvidersDisclosureIsMadeTo) {
		this.organizationalProvidersDisclosureIsMadeTo = organizationalProvidersDisclosureIsMadeTo;
	}

	/**
	 * Gets the do not share clinical document type codes.
	 *
	 * @return the do not share clinical document type codes
	 */
	public Set<ConsentDoNotShareClinicalDocumentTypeCode> getDoNotShareClinicalDocumentTypeCodes() {
		return this.doNotShareClinicalDocumentTypeCodes;
	}

	/**
	 * Sets the do not share clinical document type codes.
	 *
	 * @param doNotShareClinicalDocumentTypeCodes
	 *            the new do not share clinical document type codes
	 */
	public void setDoNotShareClinicalDocumentTypeCodes(
			Set<ConsentDoNotShareClinicalDocumentTypeCode> doNotShareClinicalDocumentTypeCodes) {
		this.doNotShareClinicalDocumentTypeCodes = doNotShareClinicalDocumentTypeCodes;
	}

	/**
	 * Gets the do not share clinical document section type codes.
	 *
	 * @return the do not share clinical document section type codes
	 */
	public Set<ConsentDoNotShareClinicalDocumentSectionTypeCode> getDoNotShareClinicalDocumentSectionTypeCodes() {
		return this.doNotShareClinicalDocumentSectionTypeCodes;
	}

	/**
	 * Sets the do not share clinical document section type codes.
	 *
	 * @param doNotShareClinicalDocumentSectionTypeCodes
	 *            the new do not share clinical document section type codes
	 */
	public void setDoNotShareClinicalDocumentSectionTypeCodes(
			Set<ConsentDoNotShareClinicalDocumentSectionTypeCode> doNotShareClinicalDocumentSectionTypeCodes) {
		this.doNotShareClinicalDocumentSectionTypeCodes = doNotShareClinicalDocumentSectionTypeCodes;
	}

	/**
	 * Gets the do not share sensitivity policy codes.
	 *
	 * @return the do not share sensitivity policy codes
	 */
	public Set<ConsentDoNotShareSensitivityPolicyCode> getDoNotShareSensitivityPolicyCodes() {
		return this.doNotShareSensitivityPolicyCodes;
	}

	/**
	 * Sets the do not share sensitivity policy codes.
	 *
	 * @param doNotShareSensitivityPolicyCodes
	 *            the new do not share sensitivity policy codes
	 */
	public void setDoNotShareSensitivityPolicyCodes(
			Set<ConsentDoNotShareSensitivityPolicyCode> doNotShareSensitivityPolicyCodes) {
		this.doNotShareSensitivityPolicyCodes = doNotShareSensitivityPolicyCodes;
	}

	/**
	 * Gets the do not share for purpose of use codes.
	 *
	 * @return the do not share for purpose of use codes
	 */
	public Set<ConsentShareForPurposeOfUseCode> getShareForPurposeOfUseCodes() {
		return this.shareForPurposeOfUseCodes;
	}

	/**
	 * Sets the do not share for purpose of use codes.
	 *
	 * @param shareForPurposeOfUseCodes
	 *            the new share for purpose of use codes
	 */
	public void setShareForPurposeOfUseCodes(
			Set<ConsentShareForPurposeOfUseCode> shareForPurposeOfUseCodes) {
		this.shareForPurposeOfUseCodes = shareForPurposeOfUseCodes;
	}

	/**
	 * Gets the consent revoked.
	 *
	 * @return the consent revoked
	 */
	public Boolean getConsentRevoked() {
		return this.consentRevoked;
	}

	/**
	 * Sets the consent revoked.
	 *
	 * @param consentRevoked
	 *            the new consent revoked
	 */
	public void setConsentRevoked(Boolean consentRevoked) {
		this.consentRevoked = consentRevoked;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate
	 *            the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate
	 *            the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the signed date.
	 *
	 * @return the signed date
	 */
	public Date getSignedDate() {
		return this.signedDate;
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
		return this.revocationDate;
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
	 * Gets the patient.
	 *
	 * @return the patient
	 */
	public Patient getPatient() {
		return this.patient;
	}

	/**
	 * Sets the patient.
	 *
	 * @param patient
	 *            the new patient
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Gets the legal representative.
	 *
	 * @return the legal representative
	 */
	public Patient getLegalRepresentative() {
		return this.legalRepresentative;
	}

	/**
	 * Sets the legal representative.
	 *
	 * @param legalRepresentative
	 *            the new legal representative
	 */
	public void setLegalRepresentative(Patient legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	/**
	 * Gets the do not share clinical concept codes.
	 *
	 * @return the do not share clinical concept codes
	 */
	public Set<ClinicalConceptCode> getDoNotShareClinicalConceptCodes() {
		return doNotShareClinicalConceptCodes;
	}

	/**
	 * Sets the do not share clinical concept codes.
	 *
	 * @param doNotShareClinicalConceptCodes
	 *            the new do not share clinical concept codes
	 */
	public void setDoNotShareClinicalConceptCodes(
			Set<ClinicalConceptCode> doNotShareClinicalConceptCodes) {
		this.doNotShareClinicalConceptCodes = doNotShareClinicalConceptCodes;
	}

	/**
	 * Gets the exported cda r2 consent.
	 *
	 * @return the exported cda r2 consent
	 */
	public byte[] getExportedCDAR2Consent() {
		return exportedCDAR2Consent;
	}

	/**
	 * Sets the exported cda r2 consent.
	 *
	 * @param exportedCDAR2Consent
	 *            the new exported cda r2 consent
	 */
	public void setExportedCDAR2Consent(byte[] exportedCDAR2Consent) {
		this.exportedCDAR2Consent = exportedCDAR2Consent;
	}

	/**
	 * Gets the exported xacml consent.
	 *
	 * @return the exported xacml consent
	 */
	public byte[] getExportedXACMLConsent() {
		return exportedXACMLConsent;
	}

	/**
	 * Sets the exported xacml consent.
	 *
	 * @param exportedXACMLConsent
	 *            the new exported xacml consent
	 */
	public void setExportedXACMLConsent(byte[] exportedXACMLConsent) {
		this.exportedXACMLConsent = exportedXACMLConsent;
	}

	/**
	 * Gets the unsigned pdf consent revoke.
	 *
	 * @return the unsigned pdf consent revoke
	 */
	public byte[] getUnsignedPdfConsentRevoke() {
		return unsignedPdfConsentRevoke;
	}

	/**
	 * Sets the unsigned pdf consent revoke.
	 *
	 * @param unsignedPdfConsentRevoke
	 *            the new unsigned pdf consent revoke
	 */
	public void setUnsignedPdfConsentRevoke(byte[] unsignedPdfConsentRevoke) {
		this.unsignedPdfConsentRevoke = unsignedPdfConsentRevoke;
	}

	/**
	 * Gets the signed pdf consent revoke.
	 *
	 * @return the signed pdf consent revoke
	 */
	public SignedPDFConsentRevocation getSignedPdfConsentRevoke() {
		return signedPdfConsentRevoke;
	}

	/**
	 * Sets the signed pdf consent revoke.
	 *
	 * @param signedPdfConsentRevoke
	 *            the new signed pdf consent revoke
	 */
	public void setSignedPdfConsentRevoke(
			SignedPDFConsentRevocation signedPdfConsentRevoke) {
		this.signedPdfConsentRevoke = signedPdfConsentRevoke;
		DomainEventManager.raise(new ConsentRevokeSubmittedEvent(id));
	}

	/**
	 * Gets the consent revokation type.
	 *
	 * @return the consent revokation type
	 */
	public String getConsentRevokationType() {
		return consentRevokationType;
	}

	/**
	 * Sets the consent revokation type.
	 *
	 * @param consentRevokationType
	 *            the new consent revokation type
	 */
	public void setConsentRevokationType(String consentRevokationType) {
		this.consentRevokationType = consentRevokationType;
	}

	/**
	 * Gets the consent reference id.
	 *
	 * @return the consent reference id
	 */
	public String getConsentReferenceId() {
		return consentReferenceId;
	}

	/**
	 * Sets the consent reference id.
	 *
	 * @param consentReferenceId
	 *            the new consent reference id
	 */
	public void setConsentReferenceId(String consentReferenceId) {
		this.consentReferenceId = consentReferenceId;
	}

	/**
	 * Gets the xacml ccd.
	 *
	 * @return the xacml ccd
	 */
	public byte[] getXacmlCcd() {
		return xacmlCcd;
	}

	/**
	 * Sets the xacml ccd.
	 *
	 * @param xacmlCcd
	 *            the new xacml ccd
	 */
	public void setXacmlCcd(byte[] xacmlCcd) {
		this.xacmlCcd = xacmlCcd;
	}

	/**
	 * Gets the xacml pdf consent from.
	 *
	 * @return the xacml pdf consent from
	 */
	public byte[] getXacmlPdfConsentFrom() {
		return xacmlPdfConsentFrom;
	}

	/**
	 * Sets the xacml pdf consent from.
	 *
	 * @param xacmlPdfConsentFrom
	 *            the new xacml pdf consent from
	 */
	public void setXacmlPdfConsentFrom(byte[] xacmlPdfConsentFrom) {
		this.xacmlPdfConsentFrom = xacmlPdfConsentFrom;
	}

	/**
	 * Gets the xacml pdf consent to.
	 *
	 * @return the xacml pdf consent to
	 */
	public byte[] getXacmlPdfConsentTo() {
		return xacmlPdfConsentTo;
	}

	/**
	 * Sets the xacml pdf consent to.
	 *
	 * @param xacmlPdfConsentTo
	 *            the new xacml pdf consent to
	 */
	public void setXacmlPdfConsentTo(byte[] xacmlPdfConsentTo) {
		this.xacmlPdfConsentTo = xacmlPdfConsentTo;
	}
}
