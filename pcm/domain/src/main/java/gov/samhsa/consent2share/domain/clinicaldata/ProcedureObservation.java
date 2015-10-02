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
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.reference.ProcedureStatusCode;
import gov.samhsa.consent2share.domain.reference.TargetSiteCode;
import gov.samhsa.consent2share.domain.valueobject.CodedConcept;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class ProcedureObservation.
 */
@Entity
public class ProcedureObservation {

	/** The procedure status code. */
	@ManyToOne
	private ProcedureStatusCode procedureStatusCode;

	/** The procedure type. */
	@Embedded
	private CodedConcept procedureType;

	/** The target site code. */
	@ManyToOne
	private TargetSiteCode targetSiteCode;

	/** The procedure start date. */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date procedureStartDate;

	/** The procedure end date. */
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date procedureEndDate;

	/** The procedure performer. */
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<IndividualProvider> procedurePerformer = new HashSet<IndividualProvider>();

	/** The patient. */
	@ManyToOne
	private Patient patient;

	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
		return new JSONSerializer().exclude("*.class").deepSerialize(this);
	}

	/**
	 * From json to procedure observation.
	 *
	 * @param json the json
	 * @return the procedure observation
	 */
	public static ProcedureObservation fromJsonToProcedureObservation(
			String json) {
		return new JSONDeserializer<ProcedureObservation>().use(null,
				ProcedureObservation.class).deserialize(json);
	}

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<ProcedureObservation> collection) {
		return new JSONSerializer().exclude("*.class")
				.deepSerialize(collection);
	}

	/**
	 * From json array to procedure observations.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<ProcedureObservation> fromJsonArrayToProcedureObservations(
			String json) {
		return new JSONDeserializer<List<ProcedureObservation>>()
				.use(null, ArrayList.class)
				.use("values", ProcedureObservation.class).deserialize(json);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * Gets the procedure status code.
	 *
	 * @return the procedure status code
	 */
	public ProcedureStatusCode getProcedureStatusCode() {
		return this.procedureStatusCode;
	}

	/**
	 * Sets the procedure status code.
	 *
	 * @param procedureStatusCode the new procedure status code
	 */
	public void setProcedureStatusCode(ProcedureStatusCode procedureStatusCode) {
		this.procedureStatusCode = procedureStatusCode;
	}

	/**
	 * Gets the procedure type.
	 *
	 * @return the procedure type
	 */
	public CodedConcept getProcedureType() {
		return this.procedureType;
	}

	/**
	 * Sets the procedure type.
	 *
	 * @param procedureType the new procedure type
	 */
	public void setProcedureType(CodedConcept procedureType) {
		this.procedureType = procedureType;
	}

	/**
	 * Gets the target site code.
	 *
	 * @return the target site code
	 */
	public TargetSiteCode getTargetSiteCode() {
		return this.targetSiteCode;
	}

	/**
	 * Sets the target site code.
	 *
	 * @param targetSiteCode the new target site code
	 */
	public void setTargetSiteCode(TargetSiteCode targetSiteCode) {
		this.targetSiteCode = targetSiteCode;
	}

	/**
	 * Gets the procedure start date.
	 *
	 * @return the procedure start date
	 */
	public Date getProcedureStartDate() {
		return this.procedureStartDate;
	}

	/**
	 * Sets the procedure start date.
	 *
	 * @param procedureStartDate the new procedure start date
	 */
	public void setProcedureStartDate(Date procedureStartDate) {
		this.procedureStartDate = procedureStartDate;
	}

	/**
	 * Gets the procedure end date.
	 *
	 * @return the procedure end date
	 */
	public Date getProcedureEndDate() {
		return this.procedureEndDate;
	}

	/**
	 * Sets the procedure end date.
	 *
	 * @param procedureEndDate the new procedure end date
	 */
	public void setProcedureEndDate(Date procedureEndDate) {
		this.procedureEndDate = procedureEndDate;
	}

	/**
	 * Gets the procedure performer.
	 *
	 * @return the procedure performer
	 */
	public Set<IndividualProvider> getProcedurePerformer() {
		return this.procedurePerformer;
	}

	/**
	 * Sets the procedure performer.
	 *
	 * @param procedurePerformer the new procedure performer
	 */
	public void setProcedurePerformer(Set<IndividualProvider> procedurePerformer) {
		this.procedurePerformer = procedurePerformer;
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
