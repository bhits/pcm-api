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
import gov.samhsa.consent2share.domain.reference.ResultInterpretationCode;
import gov.samhsa.consent2share.domain.reference.ResultStatusCode;
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class ResultObservation.
 */
@Entity
public class ResultObservation {

    /** The result date time. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date resultDateTime;

    /** The result type. */
    @Embedded
    private CodedConcept resultType;

    /** The Result status code. */
    @ManyToOne
    private ResultStatusCode ResultStatusCode;

    /** The result value. */
    @Embedded
    private Quantity resultValue;

    /** The result interpretation code. */
    @ManyToOne
    private ResultInterpretationCode resultInterpretationCode;

    /** The result reference range. */
    private String resultReferenceRange;

    /** The patient. */
    @ManyToOne
    private Patient patient;

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
	 * From json to result observation.
	 *
	 * @param json the json
	 * @return the result observation
	 */
	public static ResultObservation fromJsonToResultObservation(String json) {
        return new JSONDeserializer<ResultObservation>().use(null, ResultObservation.class).deserialize(json);
    }

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<ResultObservation> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	/**
	 * From json array to result observations.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<ResultObservation> fromJsonArrayToResultObservations(String json) {
        return new JSONDeserializer<List<ResultObservation>>().use(null, ArrayList.class).use("values", ResultObservation.class).deserialize(json);
    }

	/**
	 * Gets the result date time.
	 *
	 * @return the result date time
	 */
	public Date getResultDateTime() {
        return this.resultDateTime;
    }

	/**
	 * Sets the result date time.
	 *
	 * @param resultDateTime the new result date time
	 */
	public void setResultDateTime(Date resultDateTime) {
        this.resultDateTime = resultDateTime;
    }

	/**
	 * Gets the result type.
	 *
	 * @return the result type
	 */
	public CodedConcept getResultType() {
        return this.resultType;
    }

	/**
	 * Sets the result type.
	 *
	 * @param resultType the new result type
	 */
	public void setResultType(CodedConcept resultType) {
        this.resultType = resultType;
    }

	/**
	 * Gets the result status code.
	 *
	 * @return the result status code
	 */
	public ResultStatusCode getResultStatusCode() {
        return this.ResultStatusCode;
    }

	/**
	 * Sets the result status code.
	 *
	 * @param ResultStatusCode the new result status code
	 */
	public void setResultStatusCode(ResultStatusCode ResultStatusCode) {
        this.ResultStatusCode = ResultStatusCode;
    }

	/**
	 * Gets the result value.
	 *
	 * @return the result value
	 */
	public Quantity getResultValue() {
        return this.resultValue;
    }

	/**
	 * Sets the result value.
	 *
	 * @param resultValue the new result value
	 */
	public void setResultValue(Quantity resultValue) {
        this.resultValue = resultValue;
    }

	/**
	 * Gets the result interpretation code.
	 *
	 * @return the result interpretation code
	 */
	public ResultInterpretationCode getResultInterpretationCode() {
        return this.resultInterpretationCode;
    }

	/**
	 * Sets the result interpretation code.
	 *
	 * @param resultInterpretationCode the new result interpretation code
	 */
	public void setResultInterpretationCode(ResultInterpretationCode resultInterpretationCode) {
        this.resultInterpretationCode = resultInterpretationCode;
    }

	/**
	 * Gets the result reference range.
	 *
	 * @return the result reference range
	 */
	public String getResultReferenceRange() {
        return this.resultReferenceRange;
    }

	/**
	 * Sets the result reference range.
	 *
	 * @param resultReferenceRange the new result reference range
	 */
	public void setResultReferenceRange(String resultReferenceRange) {
        this.resultReferenceRange = resultReferenceRange;
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
}
