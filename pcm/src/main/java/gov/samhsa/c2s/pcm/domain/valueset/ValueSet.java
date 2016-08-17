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
package gov.samhsa.c2s.pcm.domain.valueset;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Audited
@AttributeOverride(name = "code", column = @Column(name = "code", unique = true, nullable = false))
public class ValueSet extends AbstractNode {

	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "valueset_id")
	private Long id;

	// optional
	private String description;

	@ManyToOne
	@JoinColumn(name = "fk_valueset_cat_id")
	private ValueSetCategory valueSetCategory;

	// Mapping between ConceptCode and ValueSet
	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.valueSet", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ConceptCodeValueSet> conceptCodes = new ArrayList<ConceptCodeValueSet>();

	// it is required by Hibernate
	public ValueSet() {
	}

	/**
	 * A Builder class used to create new CodeSystem objects.
	 */
	public static class Builder {
		ValueSet built;

		/**
		 * Creates a new Builder instance.
		 * 
		 * @param code
		 *            The code of the created ValueSet object.
		 * @param name
		 *            The name of the created ValueSet object.
		 * @param userName
		 *            The User who created ValueSet object.
		 */
		Builder(String code, String name, String userName) {
			built = new ValueSet();
			built.code = code;
			built.name = name;
			built.userName = userName;
		}

		/**
		 * Builds the new CodeSystem object.
		 * 
		 * @return The created CodeSystem object.
		 */
		public ValueSet build() {
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
	 *            The code of the created ValueSet object.
	 * @param name
	 *            The name of the created ValueSet object.
	 * @param userName
	 *            The User who created ValueSet object.
	 */
	public static Builder getBuilder(String code, String name, String userName) {
		return new Builder(code, name, userName);
	}

	/**
	 * Updates a ValueSet instance.
	 * 
	 * @param code
	 *            The code of the created ValueSet object.
	 * @param name
	 *            The name of the created ValueSet object.
	 * @param userName
	 *            The User who created ValueSet object.
	 *
	 */
	public void update(String code, String name, String description,
			String userName) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ValueSetCategory getValueSetCategory() {
		return valueSetCategory;
	}

	public void setValueSetCategory(ValueSetCategory valueSetCategory) {
		this.valueSetCategory = valueSetCategory;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public List<ConceptCodeValueSet> getConceptCodes() {
		return conceptCodes;
	}

	public void setConceptCodes(List<ConceptCodeValueSet> conceptCodes) {
		this.conceptCodes = conceptCodes;
	}
}
