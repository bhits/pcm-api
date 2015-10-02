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
package gov.samhsa.consent2share.domain.audit;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;

/**
 * The Class RevisionInfoEntity.
 */
@Entity
@Table(name = "REVINFO")
@RevisionEntity(RevisionListenerImpl.class)
public class RevisionInfoEntity {

	/** The id. */
	@Id
	@GeneratedValue
	@RevisionNumber
	@Column(name = "REV")
	private long id;

	/** The timestamp. */
	@RevisionTimestamp
	@Column(name = "REVTSTMP")
	private long timestamp;

	/** The username. */
	private String username;

	/** The modified entity types. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "revision")
	private Set<ModifiedEntityTypeEntity> modifiedEntityTypes = new HashSet<ModifiedEntityTypeEntity>();

	/**
	 * Adds the modified entity type.
	 *
	 * @param entityClassName the entity class name
	 * @param rt the rt
	 * @param revisionEntity the revision entity
	 */
	public void addModifiedEntityType(String entityClassName, Byte rt,
			RevisionInfoEntity revisionEntity) {
		ModifiedEntityTypeEntity mete = new ModifiedEntityTypeEntity();
		mete.setEntityClassName(entityClassName);
		mete.setRevisionType(rt);
		mete.setRevision(revisionEntity);
		modifiedEntityTypes.add(mete);
	}

	/**
	 * Gets the modified entity types.
	 *
	 * @return the modified entity types
	 */
	public Set<ModifiedEntityTypeEntity> getModifiedEntityTypes() {
		return modifiedEntityTypes;
	}

	/**
	 * Sets the modified entity types.
	 *
	 * @param modifiedEntityTypes the new modified entity types
	 */
	public void setModifiedEntityTypes(
			Set<ModifiedEntityTypeEntity> modifiedEntityTypes) {
		this.modifiedEntityTypes = modifiedEntityTypes;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
