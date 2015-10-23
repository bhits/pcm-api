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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class CodeSystemVersion extends AbstractVersion {

	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "codeSystem_version_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "version_name", nullable = false)
	protected String name;

	// Mapping between CodeSystemVersion and CodeSystem
	@ManyToOne
	@JoinColumn(name = "fk_codeSystem_id")
	private CodeSystem codeSystem;

	/*
	 * ConceptCode has Composition Relation with CodeSystemVersion If
	 * CodeSystemVersion is destroyed or ConceptCode is removed from
	 * ConceptCode's collection Then ConceptCode is destroyed automatically
	 */
	@OneToMany(mappedBy = "codeSystemVersion", orphanRemoval = true)
	private List<ConceptCode> conceptCodes;

	// optional
	private String description;

	// it is required by Hibernate
	public CodeSystemVersion() {
	}

	/**
	 * A Builder class used to create new CodeSystem objects.
	 */
	public static class Builder {
		CodeSystemVersion built;

		/**
		 * Creates a new Builder instance.
		 * 
		 * @param code
		 *            The code of the created CodeSystemVersion object.
		 * @param name
		 *            The name of the created CodeSystemVersion object.
		 * @param userName
		 *            The User who created CodeSystemVersion object.
		 */
		Builder(String code, String name, String userName) {
			built = new CodeSystemVersion();
			built.name = name;
			built.userName = userName;
		}

		/**
		 * Builds the new CodeSystem object.
		 * 
		 * @return The created CodeSystem object.
		 */
		public CodeSystemVersion build() {
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
	 *            The code of the created CodeSystemVersion object.
	 * @param name
	 *            The name of the created CodeSystemVersion object.
	 * @param userName
	 *            The User who created CodeSystemVersion object.
	 */
	public static Builder getBuilder(String code, String name, String userName) {
		return new Builder(code, name, userName);
	}

	/**
	 * Updates a CodeSystemVersion instance.
	 * 
	 * @param code
	 *            The code of the created CodeSystemVersion object.
	 * @param name
	 *            The name of the created CodeSystemVersion object.
	 * @param userName
	 *            The User who created CodeSystemVersion object.
	 *
	 */
	public void update(String code, String name, String description,
			String userName) {
		this.name = name;
		this.description = description;
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	/**
	 * This setter method should only be used by unit tests.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public CodeSystem getCodeSystem() {
		return codeSystem;
	}

	public void setCodeSystem(CodeSystem codeSystem) {
		this.codeSystem = codeSystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ConceptCode> getConceptCodes() {
		return conceptCodes;
	}

	public void setConceptCodes(List<ConceptCode> conceptCodes) {
		this.conceptCodes = conceptCodes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
