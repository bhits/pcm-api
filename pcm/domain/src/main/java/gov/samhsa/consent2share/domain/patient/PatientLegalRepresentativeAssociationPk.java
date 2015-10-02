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
package gov.samhsa.consent2share.domain.patient;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.Audited;

/**
 * The Class PatientLegalRepresentativeAssociationPk.
 */
@Embeddable
@Audited
public class PatientLegalRepresentativeAssociationPk {

    /** The patient. */
    @ManyToOne(fetch = FetchType.EAGER)
    private Patient patient;

    /** The legal representative. */
    @ManyToOne(fetch = FetchType.EAGER)
    private Patient legalRepresentative;

	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	/**
	 * From json to patient legal representative association pk.
	 *
	 * @param json the json
	 * @return the patient legal representative association pk
	 */
	public static PatientLegalRepresentativeAssociationPk fromJsonToPatientLegalRepresentativeAssociationPk(String json) {
        return new JSONDeserializer<PatientLegalRepresentativeAssociationPk>().use(null, PatientLegalRepresentativeAssociationPk.class).deserialize(json);
    }

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<PatientLegalRepresentativeAssociationPk> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	/**
	 * From json array to patient legal representative association pks.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<PatientLegalRepresentativeAssociationPk> fromJsonArrayToPatientLegalRepresentativeAssociationPks(String json) {
        return new JSONDeserializer<List<PatientLegalRepresentativeAssociationPk>>().use(null, ArrayList.class).use("values", PatientLegalRepresentativeAssociationPk.class).deserialize(json);
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
	 * Gets the legal representative.
	 *
	 * @return the legal representative
	 */
	public Patient getLegalRepresentative() {
        return this.legalRepresentative;
    }

	/**
	 * Sets the legal representative.
	 *
	 * @param legalRepresentative the new legal representative
	 */
	public void setLegalRepresentative(Patient legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
