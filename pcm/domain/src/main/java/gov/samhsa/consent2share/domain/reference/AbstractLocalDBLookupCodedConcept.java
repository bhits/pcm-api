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
package gov.samhsa.consent2share.domain.reference;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class AbstractLocalDBLookupCodedConcept.
 */
@MappedSuperclass
public abstract class AbstractLocalDBLookupCodedConcept {

	/** The code. */
	@NotNull
	@Size(max = 250)
	private String code;

	/** The code system. */
	@Size(max = 250)
	private String codeSystem;

	/** The display name. */
	@NotNull
	@Size(max = 250)
	private String displayName;

	/** The code system name. */
	@NotNull
	@Size(max = 250)
	private String codeSystemName;

	/** The original text. */
	@Size(max = 250)
	private String originalText;

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the code system.
	 *
	 * @return the code system
	 */
	public String getCodeSystem() {
		return this.codeSystem;
	}

	/**
	 * Sets the code system.
	 *
	 * @param codeSystem the new code system
	 */
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * Sets the display name.
	 *
	 * @param displayName the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the code system name.
	 *
	 * @return the code system name
	 */
	public String getCodeSystemName() {
		return this.codeSystemName;
	}

	/**
	 * Sets the code system name.
	 *
	 * @param codeSystemName the new code system name
	 */
	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}

	/**
	 * Gets the original text.
	 *
	 * @return the original text
	 */
	public String getOriginalText() {
		return this.originalText;
	}

	/**
	 * Sets the original text.
	 *
	 * @param originalText the new original text
	 */
	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	/*
	 * public String toJson() { return new
	 * JSONSerializer().exclude("*.class").deepSerialize(this); }
	 * 
	 * public static AbstractLocalDBLookupCodedConcept
	 * fromJsonToAbstractLocalDBLookupCodedConcept(String json) { return new
	 * JSONDeserializer<AbstractLocalDBLookupCodedConcept>().use(null,
	 * AbstractLocalDBLookupCodedConcept.class).deserialize(json); }
	 * 
	 * public static String
	 * toJsonArray(Collection<AbstractLocalDBLookupCodedConcept> collection) {
	 * return new JSONSerializer().exclude("*.class").deepSerialize(collection);
	 * }
	 * 
	 * public static Collection<AbstractLocalDBLookupCodedConcept>
	 * fromJsonArrayToAbstractLocalDBLookupCodedConcepts(String json) { return
	 * new JSONDeserializer<List<AbstractLocalDBLookupCodedConcept>>().use(null,
	 * ArrayList.class).use("values",
	 * AbstractLocalDBLookupCodedConcept.class).deserialize(json); }
	 * 
	 * public String toString() { return
	 * ReflectionToStringBuilder.toString(this,
	 * ToStringStyle.SHORT_PREFIX_STYLE); }
	 */

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "idgener")
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
	@Override
	public String toString() {
		return "AbstractLocalDBLookupCodedConcept [code=" + code
				+ ", codeSystem=" + codeSystem + ", displayName=" + displayName
				+ ", codeSystemName=" + codeSystemName + ", originalText="
				+ originalText + ", id=" + id + ", version=" + version + "]";
	}

}
