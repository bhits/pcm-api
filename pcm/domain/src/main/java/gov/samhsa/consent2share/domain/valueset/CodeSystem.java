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
package gov.samhsa.consent2share.domain.valueset;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * An entity class which contains the information of a Code System.
 */
@Entity
@Audited
public class CodeSystem extends AbstractNode {

	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "codeSystem_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "codeSystem_oid", unique = true, nullable = false)
	private String codeSystemOId;

	// optional
	private String displayName;

	/*
	 * CodeSystemVersion has Composition Relation with CodeSystem If CodeSystem
	 * is destroyed or CodeSystem is removed from CodeSystemVersion's collection
	 * Then CodeSystemVersion is destroyed automatically
	 */
	@OneToMany(mappedBy = "codeSystem", orphanRemoval = true)
	@NotAudited
	private List<CodeSystemVersion> codeSystemVersions;

	// it is required by Hibernate
	public CodeSystem() {

	}

	/**
	 * A Builder class used to create new CodeSystem objects.
	 */
	public static class Builder {
		CodeSystem built;

		/**
		 * Creates a new Builder instance.
		 * 
		 * @param codeSystemOId
		 *            The object identifier of the created CodeSystem object.
		 * @param code
		 *            The code of the created CodeSystem object.
		 * @param name
		 *            The name of the created CodeSystem object.
		 * @param userName
		 *            The User who created CodeSystem object.
		 */
		Builder(String codeSystemOId, String code, String name, String userName) {
			built = new CodeSystem();
			built.codeSystemOId = codeSystemOId;
			built.code = code;
			built.name = name;
			built.userName = userName;
		}

		/**
		 * Builds the new CodeSystem object.
		 * 
		 * @return The created CodeSystem object.
		 */
		public CodeSystem build() {
			return built;
		}

		// optional
		public Builder displayName(String displayName) {
			built.setDisplayName(displayName);
			return this;
		}
	}

	/**
	 * Creates a new Builder instance.
	 * 
	 * @param codeSystemOId
	 *            The object identifier of the created CodeSystem object.
	 * @param code
	 *            The code of the created CodeSystem object.
	 * @param name
	 *            The name of the created CodeSystem object.
	 * @param userName
	 *            The User who created CodeSystem object.
	 */
	public static Builder getBuilder(String codeSystemOId, String code,
			String name, String userName) {
		return new Builder(codeSystemOId, code, name, userName);
	}

	/**
	 * Updates a Code System instance.
	 * 
	 * @param codeSystemOId
	 *            The object identifier of the created CodeSystem object.
	 * @param code
	 *            The code of the created CodeSystem object.
	 * @param name
	 *            The name of the created CodeSystem object.
	 * @param displayName
	 *            The display name of the created CodeSystem object.
	 * @param userName
	 *            The User who created CodeSystem object.
	 *
	 */
	public void update(String codeSystemOId, String code, String name,
			String displayName, String userName) {
		this.codeSystemOId = codeSystemOId;
		this.code = code;
		this.name = name;
		this.displayName = displayName;
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

	public String getCodeSystemOId() {
		return codeSystemOId;
	}

	public void setCodeSystemOId(String codeSystemOId) {
		this.codeSystemOId = codeSystemOId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<CodeSystemVersion> getCodeSystemVersions() {
		return codeSystemVersions;
	}

	public void setCodeSystemVersions(List<CodeSystemVersion> codeSystemVersions) {
		this.codeSystemVersions = codeSystemVersions;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
