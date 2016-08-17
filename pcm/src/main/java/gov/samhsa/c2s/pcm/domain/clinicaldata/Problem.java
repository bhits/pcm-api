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
import gov.samhsa.c2s.pcm.domain.reference.ProblemStatusCode;
import gov.samhsa.c2s.pcm.domain.valueobject.CodedConcept;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * The Class Problem.
 */
@Entity
public class Problem {

    /** The age at on set. */
    private Integer ageAtOnSet;

    /** The problem status code. */
    @ManyToOne
    private ProblemStatusCode problemStatusCode;

    /** The problem code. */
    @Embedded
    private CodedConcept problemCode;

    /** The problem start date. */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date problemStartDate;

    /** The problem end date. */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date problemEndDate;

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
	 * Gets the age at on set.
	 *
	 * @return the age at on set
	 */
	public Integer getAgeAtOnSet() {
        return this.ageAtOnSet;
    }

	/**
	 * Sets the age at on set.
	 *
	 * @param ageAtOnSet the new age at on set
	 */
	public void setAgeAtOnSet(Integer ageAtOnSet) {
        this.ageAtOnSet = ageAtOnSet;
    }

	/**
	 * Gets the problem status code.
	 *
	 * @return the problem status code
	 */
	public ProblemStatusCode getProblemStatusCode() {
        return this.problemStatusCode;
    }

	/**
	 * Sets the problem status code.
	 *
	 * @param problemStatusCode the new problem status code
	 */
	public void setProblemStatusCode(ProblemStatusCode problemStatusCode) {
        this.problemStatusCode = problemStatusCode;
    }

	/**
	 * Gets the problem code.
	 *
	 * @return the problem code
	 */
	public CodedConcept getProblemCode() {
        return this.problemCode;
    }

	/**
	 * Sets the problem code.
	 *
	 * @param problemCode the new problem code
	 */
	public void setProblemCode(CodedConcept problemCode) {
        this.problemCode = problemCode;
    }

	/**
	 * Gets the problem start date.
	 *
	 * @return the problem start date
	 */
	public Date getProblemStartDate() {
        return this.problemStartDate;
    }

	/**
	 * Sets the problem start date.
	 *
	 * @param problemStartDate the new problem start date
	 */
	public void setProblemStartDate(Date problemStartDate) {
        this.problemStartDate = problemStartDate;
    }

	/**
	 * Gets the problem end date.
	 *
	 * @return the problem end date
	 */
	public Date getProblemEndDate() {
        return this.problemEndDate;
    }

	/**
	 * Sets the problem end date.
	 *
	 * @param problemEndDate the new problem end date
	 */
	public void setProblemEndDate(Date problemEndDate) {
        this.problemEndDate = problemEndDate;
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
