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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import gov.samhsa.consent2share.domain.patient.PatientLegalRepresentativeAssociation;

/**
 * The Class PatientLegalRepresentativeAssociationDto.
 */
public class PatientLegalRepresentativeAssociationDto {

	/** The relationship start date. */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date relationshipStartDate;

	/** The relationship end date. */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date relationshipEndDate;

	/** The legal representative type code. */
	@ManyToOne
	private LookupDto legalRepresentativeTypeCode;

	/** The patient id. */
	@ManyToOne
	private Long patientId;

	/** The legal representative id. */
	@ManyToOne
	private Long legalRepresentativeId;

	/**
	 * Gets the relationship start date.
	 *
	 * @return the relationship start date
	 */
	public Date getRelationshipStartDate() {
		return this.relationshipStartDate;
	}

	/**
	 * Sets the relationship start date.
	 *
	 * @param relationshipStartDate the new relationship start date
	 */
	public void setRelationshipStartDate(Date relationshipStartDate) {
		this.relationshipStartDate = relationshipStartDate;
	}

	/**
	 * Gets the relationship end date.
	 *
	 * @return the relationship end date
	 */
	public Date getRelationshipEndDate() {
		return this.relationshipEndDate;
	}

	/**
	 * Sets the relationship end date.
	 *
	 * @param relationshipEndDate the new relationship end date
	 */
	public void setRelationshipEndDate(Date relationshipEndDate) {
		this.relationshipEndDate = relationshipEndDate;
	}

	/**
	 * Gets the legal representative type code.
	 *
	 * @return the legal representative type code
	 */
	public LookupDto getLegalRepresentativeTypeCode() {
		return legalRepresentativeTypeCode;
	}

	/**
	 * Sets the legal representative type code.
	 *
	 * @param legalRepresentativeTypeCode the new legal representative type code
	 */
	public void setLegalRepresentativeTypeCode(
			LookupDto legalRepresentativeTypeCode) {
		this.legalRepresentativeTypeCode = legalRepresentativeTypeCode;
	}

	/**
	 * Gets the patient id.
	 *
	 * @return the patient id
	 */
	public Long getPatientId() {
		return patientId;
	}

	/**
	 * Sets the patient id.
	 *
	 * @param patientId the new patient id
	 */
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	/**
	 * Gets the legal representative id.
	 *
	 * @return the legal representative id
	 */
	public Long getLegalRepresentativeId() {
		return legalRepresentativeId;
	}

	/**
	 * Sets the legal representative id.
	 *
	 * @param legalRepresentativeId the new legal representative id
	 */
	public void setLegalRepresentativeId(Long legalRepresentativeId) {
		this.legalRepresentativeId = legalRepresentativeId;
	}

	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
		return new JSONSerializer().exclude("*.class").deepSerialize(this);
	}

	/**
	 * From json to patient legal representative association.
	 *
	 * @param json the json
	 * @return the patient legal representative association
	 */
	public static PatientLegalRepresentativeAssociation fromJsonToPatientLegalRepresentativeAssociation(
			String json) {
		return new JSONDeserializer<PatientLegalRepresentativeAssociation>()
				.use(null, PatientLegalRepresentativeAssociation.class)
				.deserialize(json);
	}

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(
			Collection<PatientLegalRepresentativeAssociation> collection) {
		return new JSONSerializer().exclude("*.class")
				.deepSerialize(collection);
	}

	/**
	 * From json array to patient legal representative associations.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<PatientLegalRepresentativeAssociation> fromJsonArrayToPatientLegalRepresentativeAssociations(
			String json) {
		return new JSONDeserializer<List<PatientLegalRepresentativeAssociation>>()
				.use(null, ArrayList.class)
				.use("values", PatientLegalRepresentativeAssociation.class)
				.deserialize(json);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	 * @param id the new id
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
	 * @param version the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
}
