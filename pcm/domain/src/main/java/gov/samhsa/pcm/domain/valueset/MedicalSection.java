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

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

@Entity
@Audited
@AttributeOverride(name = "code", column = @Column(name = "code", unique = true, nullable = false))
public class MedicalSection extends AbstractNode {

	@Id
	@GeneratedValue
	@Column(name = "medical_sec_id")
	private Long id;

	@Column(name = "description", length = 5000)
	private String description;

	// it is required by Hibernate
	public MedicalSection() {
	}

	/**
	 * A Builder class used to create new CodeSystem objects.
	 */
	public static class Builder {
		MedicalSection built;

		/**
		 * Creates a new Builder instance.
		 * 
		 * @param code
		 *            The code of the created MedicalSection object.
		 * @param name
		 *            The name of the created MedicalSection object.
		 * @param userName
		 *            The User who created MedicalSection object.
		 */
		Builder(String code, String name, String userName) {
			built = new MedicalSection();
			built.code = code;
			built.name = name;
			built.userName = userName;
		}

		/**
		 * Builds the new CodeSystem object.
		 * 
		 * @return The created CodeSystem object.
		 */
		public MedicalSection build() {
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
	 *            The code of the created MedicalSection object.
	 * @param name
	 *            The name of the created MedicalSection object.
	 * @param userName
	 *            The User who created MedicalSection object.
	 */
	public static Builder getBuilder(String code, String name, String userName) {
		return new Builder(code, name, userName);
	}

	/**
	 * Updates a MedicalSection instance.
	 * 
	 * @param code
	 *            The code of the created MedicalSection object.
	 * @param name
	 *            The name of the created MedicalSection object.
	 * @param userName
	 *            The User who created MedicalSection object.
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

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
