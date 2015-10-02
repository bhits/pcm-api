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
import gov.samhsa.consent2share.domain.reference.BodySiteCode;
import gov.samhsa.consent2share.domain.reference.MedicationStatusCode;
import gov.samhsa.consent2share.domain.reference.ProductFormCode;
import gov.samhsa.consent2share.domain.reference.RouteCode;
import gov.samhsa.consent2share.domain.valueobject.Quantity;
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
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class Medication.
 */
@Entity
public class Medication {

    /** The free text sig. */
    @NotNull
    @Size(max = 250)
    private String freeTextSig;

    /** The medication status code. */
    @ManyToOne
    private MedicationStatusCode medicationStatusCode;

    /** The medication information code. */
    @Embedded
    private CodedConcept medicationInformationCode;

    /** The route code. */
    @ManyToOne
    private RouteCode routeCode;

    /** The body site code. */
    @ManyToOne
    private BodySiteCode bodySiteCode;

    /** The dose quantity. */
    @Embedded
    private Quantity doseQuantity;

    /** The product form code. */
    @ManyToOne
    private ProductFormCode productFormCode;

    /** The medication start date. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date medicationStartDate;

    /** The medication end date. */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date medicationEndDate;

    /** The patient. */
    @ManyToOne
    private Patient patient;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	/**
	 * Gets the free text sig.
	 *
	 * @return the free text sig
	 */
	public String getFreeTextSig() {
        return this.freeTextSig;
    }

	/**
	 * Sets the free text sig.
	 *
	 * @param freeTextSig the new free text sig
	 */
	public void setFreeTextSig(String freeTextSig) {
        this.freeTextSig = freeTextSig;
    }

	/**
	 * Gets the medication status code.
	 *
	 * @return the medication status code
	 */
	public MedicationStatusCode getMedicationStatusCode() {
        return this.medicationStatusCode;
    }

	/**
	 * Sets the medication status code.
	 *
	 * @param medicationStatusCode the new medication status code
	 */
	public void setMedicationStatusCode(MedicationStatusCode medicationStatusCode) {
        this.medicationStatusCode = medicationStatusCode;
    }

	/**
	 * Gets the medication information code.
	 *
	 * @return the medication information code
	 */
	public CodedConcept getMedicationInformationCode() {
        return this.medicationInformationCode;
    }

	/**
	 * Sets the medication information code.
	 *
	 * @param medicationInformationCode the new medication information code
	 */
	public void setMedicationInformationCode(CodedConcept medicationInformationCode) {
        this.medicationInformationCode = medicationInformationCode;
    }

	/**
	 * Gets the route code.
	 *
	 * @return the route code
	 */
	public RouteCode getRouteCode() {
        return this.routeCode;
    }

	/**
	 * Sets the route code.
	 *
	 * @param routeCode the new route code
	 */
	public void setRouteCode(RouteCode routeCode) {
        this.routeCode = routeCode;
    }

	/**
	 * Gets the body site code.
	 *
	 * @return the body site code
	 */
	public BodySiteCode getBodySiteCode() {
        return this.bodySiteCode;
    }

	/**
	 * Sets the body site code.
	 *
	 * @param bodySiteCode the new body site code
	 */
	public void setBodySiteCode(BodySiteCode bodySiteCode) {
        this.bodySiteCode = bodySiteCode;
    }

	/**
	 * Gets the dose quantity.
	 *
	 * @return the dose quantity
	 */
	public Quantity getDoseQuantity() {
        return this.doseQuantity;
    }

	/**
	 * Sets the dose quantity.
	 *
	 * @param doseQuantity the new dose quantity
	 */
	public void setDoseQuantity(Quantity doseQuantity) {
        this.doseQuantity = doseQuantity;
    }

	/**
	 * Gets the product form code.
	 *
	 * @return the product form code
	 */
	public ProductFormCode getProductFormCode() {
        return this.productFormCode;
    }

	/**
	 * Sets the product form code.
	 *
	 * @param productFormCode the new product form code
	 */
	public void setProductFormCode(ProductFormCode productFormCode) {
        this.productFormCode = productFormCode;
    }

	/**
	 * Gets the medication start date.
	 *
	 * @return the medication start date
	 */
	public Date getMedicationStartDate() {
        return this.medicationStartDate;
    }

	/**
	 * Sets the medication start date.
	 *
	 * @param medicationStartDate the new medication start date
	 */
	public void setMedicationStartDate(Date medicationStartDate) {
        this.medicationStartDate = medicationStartDate;
    }

	/**
	 * Gets the medication end date.
	 *
	 * @return the medication end date
	 */
	public Date getMedicationEndDate() {
        return this.medicationEndDate;
    }

	/**
	 * Sets the medication end date.
	 *
	 * @param medicationEndDate the new medication end date
	 */
	public void setMedicationEndDate(Date medicationEndDate) {
        this.medicationEndDate = medicationEndDate;
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

	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	/**
	 * From json to medication.
	 *
	 * @param json the json
	 * @return the medication
	 */
	public static Medication fromJsonToMedication(String json) {
        return new JSONDeserializer<Medication>().use(null, Medication.class).deserialize(json);
    }

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<Medication> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	/**
	 * From json array to medications.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<Medication> fromJsonArrayToMedications(String json) {
        return new JSONDeserializer<List<Medication>>().use(null, ArrayList.class).use("values", Medication.class).deserialize(json);
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
