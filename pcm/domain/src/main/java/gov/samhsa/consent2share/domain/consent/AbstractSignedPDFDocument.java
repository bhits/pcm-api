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
package gov.samhsa.consent2share.domain.consent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@MappedSuperclass
public abstract class AbstractSignedPDFDocument implements BinaryContentAccessible{
	/** The document created version key. */
    private String documentCreatedVersionKey;
    
    /** The document name by sender. */
    @NotNull
    private String documentNameBySender;

    /** The document message by sender. */
    @NotNull
    private String documentMessageBySender;

    /** The document locale. */
    private String documentLocale;

    /** The document esigned version key. */
    private String documentEsignedVersionKey;

    /** The documentlast version key. */
    private String documentlastVersionKey;

    /** The signer email. */
    @NotNull
    private String signerEmail;

    /** The document id. */
    @NotNull
    private String documentId;
    
	/** The version. */
	@Version
    @Column(name = "version")
    private Integer version;

    /** The document signed status. */
    @NotNull
    private String documentSignedStatus;

    /** The document creation date time. */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date documentCreationDateTime;

    /** The document created by. */
    private String documentCreatedBy;

    /** The document sent out for signature date time. */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date documentSentOutForSignatureDateTime;

    /** The document viewed date time. */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date documentViewedDateTime;

    /** The document viewed by. */
    private String documentViewedBy;

    /** The document esigned date time. */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date documentEsignedDateTime;

    /** The document esigned by. */
    private String documentEsignedBy;

	/**
	 * Gets the document created version key.
	 *
	 * @return the document created version key
	 */
	public String getDocumentCreatedVersionKey() {
        return this.documentCreatedVersionKey;
    }

	/**
	 * Sets the document created version key.
	 *
	 * @param documentCreatedVersionKey the new document created version key
	 */
	public void setDocumentCreatedVersionKey(String documentCreatedVersionKey) {
        this.documentCreatedVersionKey = documentCreatedVersionKey;
    }

	/**
	 * Gets the document name by sender.
	 *
	 * @return the document name by sender
	 */
	public String getDocumentNameBySender() {
        return this.documentNameBySender;
    }

	/**
	 * Sets the document name by sender.
	 *
	 * @param documentNameBySender the new document name by sender
	 */
	public void setDocumentNameBySender(String documentNameBySender) {
        this.documentNameBySender = documentNameBySender;
    }

	/**
	 * Gets the document message by sender.
	 *
	 * @return the document message by sender
	 */
	public String getDocumentMessageBySender() {
        return this.documentMessageBySender;
    }

	/**
	 * Sets the document message by sender.
	 *
	 * @param documentMessageBySender the new document message by sender
	 */
	public void setDocumentMessageBySender(String documentMessageBySender) {
        this.documentMessageBySender = documentMessageBySender;
    }

	/**
	 * Gets the document locale.
	 *
	 * @return the document locale
	 */
	public String getDocumentLocale() {
        return this.documentLocale;
    }

	/**
	 * Sets the document locale.
	 *
	 * @param documentLocale the new document locale
	 */
	public void setDocumentLocale(String documentLocale) {
        this.documentLocale = documentLocale;
    }

	/**
	 * Gets the document esigned version key.
	 *
	 * @return the document esigned version key
	 */
	public String getDocumentEsignedVersionKey() {
        return this.documentEsignedVersionKey;
    }

	/**
	 * Sets the document esigned version key.
	 *
	 * @param documentEsignedVersionKey the new document esigned version key
	 */
	public void setDocumentEsignedVersionKey(String documentEsignedVersionKey) {
        this.documentEsignedVersionKey = documentEsignedVersionKey;
    }

	/**
	 * Gets the documentlast version key.
	 *
	 * @return the documentlast version key
	 */
	public String getDocumentlastVersionKey() {
        return this.documentlastVersionKey;
    }

	/**
	 * Sets the documentlast version key.
	 *
	 * @param documentlastVersionKey the new documentlast version key
	 */
	public void setDocumentlastVersionKey(String documentlastVersionKey) {
        this.documentlastVersionKey = documentlastVersionKey;
    }

	/**
	 * Gets the signer email.
	 *
	 * @return the signer email
	 */
	public String getSignerEmail() {
        return this.signerEmail;
    }

	/**
	 * Sets the signer email.
	 *
	 * @param signerEmail the new signer email
	 */
	public void setSignerEmail(String signerEmail) {
        this.signerEmail = signerEmail;
    }

	/**
	 * Gets the document id.
	 *
	 * @return the document id
	 */
	public String getDocumentId() {
        return this.documentId;
    }

	/**
	 * Sets the document id.
	 *
	 * @param documentId the new document id
	 */
	public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

	/**
	 * Gets the document signed status.
	 *
	 * @return the document signed status
	 */
	public String getDocumentSignedStatus() {
        return this.documentSignedStatus;
    }

	/**
	 * Sets the document signed status.
	 *
	 * @param documentSignedStatus the new document signed status
	 */
	public void setDocumentSignedStatus(String documentSignedStatus) {
        this.documentSignedStatus = documentSignedStatus;
    }

	/**
	 * Gets the document creation date time.
	 *
	 * @return the document creation date time
	 */
	public Date getDocumentCreationDateTime() {
        return this.documentCreationDateTime;
    }

	/**
	 * Sets the document creation date time.
	 *
	 * @param documentCreationDateTime the new document creation date time
	 */
	public void setDocumentCreationDateTime(Date documentCreationDateTime) {
        this.documentCreationDateTime = documentCreationDateTime;
    }

	/**
	 * Gets the document created by.
	 *
	 * @return the document created by
	 */
	public String getDocumentCreatedBy() {
        return this.documentCreatedBy;
    }

	/**
	 * Sets the document created by.
	 *
	 * @param documentCreatedBy the new document created by
	 */
	public void setDocumentCreatedBy(String documentCreatedBy) {
        this.documentCreatedBy = documentCreatedBy;
    }

	/**
	 * Gets the document sent out for signature date time.
	 *
	 * @return the document sent out for signature date time
	 */
	public Date getDocumentSentOutForSignatureDateTime() {
        return this.documentSentOutForSignatureDateTime;
    }

	/**
	 * Sets the document sent out for signature date time.
	 *
	 * @param documentSentOutForSignatureDateTime the new document sent out for signature date time
	 */
	public void setDocumentSentOutForSignatureDateTime(Date documentSentOutForSignatureDateTime) {
        this.documentSentOutForSignatureDateTime = documentSentOutForSignatureDateTime;
    }

	/**
	 * Gets the document viewed date time.
	 *
	 * @return the document viewed date time
	 */
	public Date getDocumentViewedDateTime() {
        return this.documentViewedDateTime;
    }

	/**
	 * Sets the document viewed date time.
	 *
	 * @param documentViewedDateTime the new document viewed date time
	 */
	public void setDocumentViewedDateTime(Date documentViewedDateTime) {
        this.documentViewedDateTime = documentViewedDateTime;
    }

	/**
	 * Gets the document viewed by.
	 *
	 * @return the document viewed by
	 */
	public String getDocumentViewedBy() {
        return this.documentViewedBy;
    }

	/**
	 * Sets the document viewed by.
	 *
	 * @param documentViewedBy the new document viewed by
	 */
	public void setDocumentViewedBy(String documentViewedBy) {
        this.documentViewedBy = documentViewedBy;
    }

	/**
	 * Gets the document esigned date time.
	 *
	 * @return the document esigned date time
	 */
	public Date getDocumentEsignedDateTime() {
        return this.documentEsignedDateTime;
    }

	/**
	 * Sets the document esigned date time.
	 *
	 * @param documentEsignedDateTime the new document esigned date time
	 */
	public void setDocumentEsignedDateTime(Date documentEsignedDateTime) {
        this.documentEsignedDateTime = documentEsignedDateTime;
    }

	/**
	 * Gets the document esigned by.
	 *
	 * @return the document esigned by
	 */
	public String getDocumentEsignedBy() {
        return this.documentEsignedBy;
    }

	/**
	 * Sets the document esigned by.
	 *
	 * @param documentEsignedBy the new document esigned by
	 */
	public void setDocumentEsignedBy(String documentEsignedBy) {
        this.documentEsignedBy = documentEsignedBy;
    }

	/**
	 * From json to signed pdf consent.
	 *
	 * @param json the json
	 * @return the signed pdf consent
	 */
	public static SignedPDFConsent fromJsonToSignedPDFConsent(String json) {
        return new JSONDeserializer<SignedPDFConsent>().use(null, SignedPDFConsent.class).deserialize(json);
    }

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<SignedPDFConsent> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	/**
	 * From json array to signed pdf consents.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<SignedPDFConsent> fromJsonArrayToSignedPDFConsents(String json) {
        return new JSONDeserializer<List<SignedPDFConsent>>().use(null, ArrayList.class).use("values", SignedPDFConsent.class).deserialize(json);
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
	 * @param version the new version
	 */
	public void setVersion(Integer version) {
        this.version = version;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
