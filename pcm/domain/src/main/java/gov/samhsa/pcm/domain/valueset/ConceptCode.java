/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.pcm.domain.valueset;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "code",
		"fk_code_system_version_id" }))
@Audited
public class ConceptCode extends AbstractNode {
	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "concept_code_id", unique = true, nullable = false)
	private Long id;

	// optional
	private String description;

	// Mapping between ConceptCode and CodeSystemVersion
	@ManyToOne
	@JoinColumn(name = "fk_code_system_version_id")
	private CodeSystemVersion codeSystemVersion;

	// Mapping between ConceptCode and Valueset
	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.conceptCode", cascade = CascadeType.ALL)
	private List<ConceptCodeValueSet> valueSets = new ArrayList<ConceptCodeValueSet>();

	// it is required by Hibernate
	public ConceptCode() {
	}

	/**
	 * A Builder class used to create new CodeSystem objects.
	 */
	public static class Builder {
		ConceptCode built;

		/**
		 * Creates a new Builder instance.
		 * 
		 * @param code
		 *            The code of the created ConceptCode object.
		 * @param name
		 *            The name of the created ConceptCode object.
		 * @param userName
		 *            The User who created ConceptCode object.
		 */
		Builder(String code, String name, String userName) {
			built = new ConceptCode();
			built.code = code;
			built.name = name;
			built.userName = userName;
		}

		/**
		 * Builds the new CodeSystem object.
		 * 
		 * @return The created CodeSystem object.
		 */
		public ConceptCode build() {
			return built;
		}

		// optional
		public Builder description(String description) {
			built.setDescription(description);
			return this;
		}
	}

	/**
	 * Creates a new Builder instance.
	 * 
	 * @param code
	 *            The code of the created ConceptCode object.
	 * @param name
	 *            The name of the created ConceptCode object.
	 * @param userName
	 *            The User who created ConceptCode object.
	 */
	public static Builder getBuilder(String code, String name, String userName) {
		return new Builder(code, name, userName);
	}

	/**
	 * Updates a ConceptCode instance.
	 * 
	 * @param code
	 *            The code of the created ConceptCode object.
	 * @param name
	 *            The name of the created ConceptCode object.
	 * @param userName
	 *            The User who created ConceptCode object.
	 *
	 */
	public void update(String code, String name, String userName) {
		this.code = code;
		this.name = name;
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CodeSystemVersion getCodeSystemVersion() {
		return codeSystemVersion;
	}

	public void setCodeSystemVersion(CodeSystemVersion codeSystemVersion) {
		this.codeSystemVersion = codeSystemVersion;
	}

	public List<ConceptCodeValueSet> getValueSets() {
		return valueSets;
	}

	public void setValueSets(List<ConceptCodeValueSet> valueSets) {
		this.valueSets = valueSets;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
