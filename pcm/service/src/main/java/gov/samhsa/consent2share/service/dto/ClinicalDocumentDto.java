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
package gov.samhsa.consent2share.service.dto;

import gov.samhsa.consent2share.infrastructure.security.HasId;

import javax.persistence.Lob;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Class ClinicalDocumentDto.
 */
public class ClinicalDocumentDto implements HasId {

	/** The name. */
	@NotEmpty
	@Size(max = 100)
	private String name;

	/** The clinical document type code. */
	private LookupDto clinicalDocumentTypeCode;

	/** The description. */
	@Size(max = 500)
	private String description;

	/** The filename. */
	@NotEmpty
	private String filename;

	/** The content. */
	@NotEmpty
	@Lob
	private byte[] content;

	/** The content type. */
	@NotEmpty
	private String contentType;

	/** The document size. */
	private Long documentSize;

	/** The document url. */
	@Size(max = 100)
	private String documentUrl;

	/** The patient id. */
	private long patientId;

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
	 * Gets the clinical document type code.
	 *
	 * @return the clinical document type code
	 */
	public LookupDto getClinicalDocumentTypeCode() {
		return clinicalDocumentTypeCode;
	}

	/**
	 * Sets the clinical document type code.
	 *
	 * @param clinicalDocumentTypeCode
	 *            the new clinical document type code
	 */
	public void setClinicalDocumentTypeCode(LookupDto clinicalDocumentTypeCode) {
		this.clinicalDocumentTypeCode = clinicalDocumentTypeCode;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the filename.
	 *
	 * @param filename
	 *            the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content
	 *            the new content
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType
	 *            the new content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Gets the document size.
	 *
	 * @return the document size
	 */
	public Long getDocumentSize() {
		return documentSize;
	}

	/**
	 * Sets the document size.
	 *
	 * @param documentSize
	 *            the new document size
	 */
	public void setDocumentSize(Long documentSize) {
		this.documentSize = documentSize;
	}

	/**
	 * Gets the document url.
	 *
	 * @return the document url
	 */
	public String getDocumentUrl() {
		return documentUrl;
	}

	/**
	 * Sets the document url.
	 *
	 * @param documentUrl
	 *            the new document url
	 */
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	/**
	 * Gets the patient id.
	 *
	 * @return the patient id
	 */
	public long getPatientId() {
		return patientId;
	}

	/**
	 * Sets the patient id.
	 *
	 * @param patientId
	 *            the new patient id
	 */
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	/** The id. */
	private String id;

	/** The version. */
	private Integer version;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
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
}
