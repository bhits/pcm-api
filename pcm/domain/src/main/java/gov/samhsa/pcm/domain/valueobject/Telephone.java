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
package gov.samhsa.pcm.domain.valueobject;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import gov.samhsa.pcm.domain.reference.TelecomUseCode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * The Class Telephone.
 */
@Embeddable
@Audited
public class Telephone {

	/** The telephone. */
	@NotNull
	@Size(max = 30)
	private String telephone;

	/** The telecom use code. */
	@ManyToOne(cascade = CascadeType.ALL)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private TelecomUseCode telecomUseCode;

	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
		return new JSONSerializer().exclude("*.class").deepSerialize(this);
	}

	/**
	 * From json to telephone.
	 *
	 * @param json the json
	 * @return the telephone
	 */
	public static Telephone fromJsonToTelephone(String json) {
		return new JSONDeserializer<Telephone>().use(null, Telephone.class)
				.deserialize(json);
	}

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<Telephone> collection) {
		return new JSONSerializer().exclude("*.class")
				.deepSerialize(collection);
	}

	/**
	 * From json array to telephones.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<Telephone> fromJsonArrayToTelephones(String json) {
		return new JSONDeserializer<List<Telephone>>()
				.use(null, ArrayList.class).use("values", Telephone.class)
				.deserialize(json);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * Gets the telephone.
	 *
	 * @return the telephone
	 */
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * Sets the telephone.
	 *
	 * @param telephone the new telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * Gets the telecom use code.
	 *
	 * @return the telecom use code
	 */
	public TelecomUseCode getTelecomUseCode() {
		return this.telecomUseCode;
	}

	/**
	 * Sets the telecom use code.
	 *
	 * @param telecomUseCode the new telecom use code
	 */
	public void setTelecomUseCode(TelecomUseCode telecomUseCode) {
		this.telecomUseCode = telecomUseCode;
	}
}
