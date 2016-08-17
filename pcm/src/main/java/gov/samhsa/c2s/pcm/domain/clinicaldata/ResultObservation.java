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
package gov.samhsa.c2s.pcm.domain.clinicaldata;

import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.domain.reference.ResultInterpretationCode;
import gov.samhsa.c2s.pcm.domain.reference.ResultStatusCode;
import gov.samhsa.c2s.pcm.domain.valueobject.CodedConcept;
import gov.samhsa.c2s.pcm.domain.valueobject.Quantity;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private gov.samhsa.c2s.pcm.domain.reference.ResultStatusCode ResultStatusCode;

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
