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
package gov.samhsa.consent2share.domain.clinicaldata;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.reference.AdverseEventTypeCode;
import gov.samhsa.consent2share.domain.reference.AllergyReactionCode;
import gov.samhsa.consent2share.domain.reference.AllergySeverityCode;
import gov.samhsa.consent2share.domain.reference.AllergyStatusCode;
import gov.samhsa.consent2share.domain.valueobject.CodedConcept;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class Allergy.
 */
@Entity
public class Allergy {

    /** The adverse event type code. */
    @ManyToOne
    private AdverseEventTypeCode adverseEventTypeCode;

    /** The allergen. */
    @Embedded
    private CodedConcept allergen;

    /** The allergy reaction. */
    @ManyToOne
    private AllergyReactionCode allergyReaction;

    /** The allergy status code. */
    @ManyToOne
    private AllergyStatusCode allergyStatusCode;

    /** The allergy severity code. */
    @ManyToOne
    private AllergySeverityCode allergySeverityCode;

    /** The allergy start date. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date allergyStartDate;

    /** The allergy end date. */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date allergyEndDate;

    /** The patient. */
    @ManyToOne
    private Patient patient;

	/**
	 * Gets the adverse event type code.
	 *
	 * @return the adverse event type code
	 */
	public AdverseEventTypeCode getAdverseEventTypeCode() {
        return this.adverseEventTypeCode;
    }

	/**
	 * Sets the adverse event type code.
	 *
	 * @param adverseEventTypeCode the new adverse event type code
	 */
	public void setAdverseEventTypeCode(AdverseEventTypeCode adverseEventTypeCode) {
        this.adverseEventTypeCode = adverseEventTypeCode;
    }

	/**
	 * Gets the allergen.
	 *
	 * @return the allergen
	 */
	public CodedConcept getAllergen() {
        return this.allergen;
    }

	/**
	 * Sets the allergen.
	 *
	 * @param allergen the new allergen
	 */
	public void setAllergen(CodedConcept allergen) {
        this.allergen = allergen;
    }

	/**
	 * Gets the allergy reaction.
	 *
	 * @return the allergy reaction
	 */
	public AllergyReactionCode getAllergyReaction() {
        return this.allergyReaction;
    }

	/**
	 * Sets the allergy reaction.
	 *
	 * @param allergyReaction the new allergy reaction
	 */
	public void setAllergyReaction(AllergyReactionCode allergyReaction) {
        this.allergyReaction = allergyReaction;
    }

	/**
	 * Gets the allergy status code.
	 *
	 * @return the allergy status code
	 */
	public AllergyStatusCode getAllergyStatusCode() {
        return this.allergyStatusCode;
    }

	/**
	 * Sets the allergy status code.
	 *
	 * @param allergyStatusCode the new allergy status code
	 */
	public void setAllergyStatusCode(AllergyStatusCode allergyStatusCode) {
        this.allergyStatusCode = allergyStatusCode;
    }

	/**
	 * Gets the allergy severity code.
	 *
	 * @return the allergy severity code
	 */
	public AllergySeverityCode getAllergySeverityCode() {
        return this.allergySeverityCode;
    }

	/**
	 * Sets the allergy severity code.
	 *
	 * @param allergySeverityCode the new allergy severity code
	 */
	public void setAllergySeverityCode(AllergySeverityCode allergySeverityCode) {
        this.allergySeverityCode = allergySeverityCode;
    }

	/**
	 * Gets the allergy start date.
	 *
	 * @return the allergy start date
	 */
	public Date getAllergyStartDate() {
        return this.allergyStartDate;
    }

	/**
	 * Sets the allergy start date.
	 *
	 * @param allergyStartDate the new allergy start date
	 */
	public void setAllergyStartDate(Date allergyStartDate) {
        this.allergyStartDate = allergyStartDate;
    }

	/**
	 * Gets the allergy end date.
	 *
	 * @return the allergy end date
	 */
	public Date getAllergyEndDate() {
        return this.allergyEndDate;
    }

	/**
	 * Sets the allergy end date.
	 *
	 * @param allergyEndDate the new allergy end date
	 */
	public void setAllergyEndDate(Date allergyEndDate) {
        this.allergyEndDate = allergyEndDate;
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
	 * @param patient the new patient
	 */
	public void setPatient(Patient patient) {
        this.patient = patient;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
	 * From json to allergy.
	 *
	 * @param json the json
	 * @return the allergy
	 */
	public static Allergy fromJsonToAllergy(String json) {
        return new JSONDeserializer<Allergy>().use(null, Allergy.class).deserialize(json);
    }

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<Allergy> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	/**
	 * From json array to allergys.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<Allergy> fromJsonArrayToAllergys(String json) {
        return new JSONDeserializer<List<Allergy>>().use(null, ArrayList.class).use("values", Allergy.class).deserialize(json);
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
