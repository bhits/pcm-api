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
package gov.samhsa.c2s.pcm.domain.patient;

import gov.samhsa.c2s.pcm.domain.reference.LegalRepresentativeTypeCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * The Class PatientLegalRepresentativeAssociation.
 */
@Entity
@Audited
public class PatientLegalRepresentativeAssociation {

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
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private LegalRepresentativeTypeCode legalRepresentativeTypeCode;


    /** The patient legal representative association pk. */
    @Embedded
    @AuditOverrides(value={@AuditOverride(name="legalRepresentative",isAudited=true),@AuditOverride(name="patient",isAudited=true)})
    private PatientLegalRepresentativeAssociationPk patientLegalRepresentativeAssociationPk;

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
	public LegalRepresentativeTypeCode getLegalRepresentativeTypeCode() {
        return this.legalRepresentativeTypeCode;
    }

	/**
	 * Sets the legal representative type code.
	 *
	 * @param legalRepresentativeTypeCode the new legal representative type code
	 */
	public void setLegalRepresentativeTypeCode(LegalRepresentativeTypeCode legalRepresentativeTypeCode) {
        this.legalRepresentativeTypeCode = legalRepresentativeTypeCode;
    }

	/**
	 * Gets the patient legal representative association pk.
	 *
	 * @return the patient legal representative association pk
	 */
	public PatientLegalRepresentativeAssociationPk getPatientLegalRepresentativeAssociationPk() {
        return this.patientLegalRepresentativeAssociationPk;
    }

	/**
	 * Sets the patient legal representative association pk.
	 *
	 * @param patientLegalRepresentativeAssociationPk the new patient legal representative association pk
	 */
	public void setPatientLegalRepresentativeAssociationPk(PatientLegalRepresentativeAssociationPk patientLegalRepresentativeAssociationPk) {
        this.patientLegalRepresentativeAssociationPk = patientLegalRepresentativeAssociationPk;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
