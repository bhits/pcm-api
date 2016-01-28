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
package gov.samhsa.mhc.pcm.domain.provider;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class OrganizationalProvider.
 */
@Entity
@Audited
@AuditOverride(forClass=AbstractProvider.class)
@AuditTable("Organizational_Provider_audit")
@SequenceGenerator(name="idgen", sequenceName="ORGPROV_SEQ", initialValue = 1)
public class OrganizationalProvider extends AbstractProvider {

    /** The org name. */
    @NotNull
    @Size(max = 255)
    private String orgName;

    /** The other org name. */
    @Size(max = 70)
    private String otherOrgName;

    /** The authorized official last name. */
    @NotNull
    @Size(max = 35)
    private String authorizedOfficialLastName;

    /** The authorized official first name. */
    @NotNull
    @Size(max = 30)
    private String authorizedOfficialFirstName;

    /** The authorized official title. */
    @NotNull
    @Size(max = 35)
    private String authorizedOfficialTitle;

    /** The authorized official name prefix. */
    @NotNull
    @Size(max = 200)
    private String authorizedOfficialNamePrefix;

    /** The authorized official telephone number. */
    @NotNull
    @Size(max = 30)
    private String authorizedOfficialTelephoneNumber;

	/**
	 * Gets the org name.
	 *
	 * @return the org name
	 */
	public String getOrgName() {
        return this.orgName;
    }

	/**
	 * Sets the org name.
	 *
	 * @param orgName the new org name
	 */
	public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

	/**
	 * Gets the other org name.
	 *
	 * @return the other org name
	 */
	public String getOtherOrgName() {
        return this.otherOrgName;
    }

	/**
	 * Sets the other org name.
	 *
	 * @param otherOrgName the new other org name
	 */
	public void setOtherOrgName(String otherOrgName) {
        this.otherOrgName = otherOrgName;
    }

	/**
	 * Gets the authorized official last name.
	 *
	 * @return the authorized official last name
	 */
	public String getAuthorizedOfficialLastName() {
        return this.authorizedOfficialLastName;
    }

	/**
	 * Sets the authorized official last name.
	 *
	 * @param authorizedOfficialLastName the new authorized official last name
	 */
	public void setAuthorizedOfficialLastName(String authorizedOfficialLastName) {
        this.authorizedOfficialLastName = authorizedOfficialLastName;
    }

	/**
	 * Gets the authorized official first name.
	 *
	 * @return the authorized official first name
	 */
	public String getAuthorizedOfficialFirstName() {
        return this.authorizedOfficialFirstName;
    }

	/**
	 * Sets the authorized official first name.
	 *
	 * @param authorizedOfficialFirstName the new authorized official first name
	 */
	public void setAuthorizedOfficialFirstName(String authorizedOfficialFirstName) {
        this.authorizedOfficialFirstName = authorizedOfficialFirstName;
    }

	/**
	 * Gets the authorized official title.
	 *
	 * @return the authorized official title
	 */
	public String getAuthorizedOfficialTitle() {
        return this.authorizedOfficialTitle;
    }

	/**
	 * Sets the authorized official title.
	 *
	 * @param authorizedOfficialTitle the new authorized official title
	 */
	public void setAuthorizedOfficialTitle(String authorizedOfficialTitle) {
        this.authorizedOfficialTitle = authorizedOfficialTitle;
    }

	/**
	 * Gets the authorized official name prefix.
	 *
	 * @return the authorized official name prefix
	 */
	public String getAuthorizedOfficialNamePrefix() {
        return this.authorizedOfficialNamePrefix;
    }

	/**
	 * Sets the authorized official name prefix.
	 *
	 * @param authorizedOfficialNamePrefix the new authorized official name prefix
	 */
	public void setAuthorizedOfficialNamePrefix(String authorizedOfficialNamePrefix) {
        this.authorizedOfficialNamePrefix = authorizedOfficialNamePrefix;
    }

	/**
	 * Gets the authorized official telephone number.
	 *
	 * @return the authorized official telephone number
	 */
	public String getAuthorizedOfficialTelephoneNumber() {
        return this.authorizedOfficialTelephoneNumber;
    }

	/**
	 * Sets the authorized official telephone number.
	 *
	 * @param authorizedOfficialTelephoneNumber the new authorized official telephone number
	 */
	public void setAuthorizedOfficialTelephoneNumber(String authorizedOfficialTelephoneNumber) {
        this.authorizedOfficialTelephoneNumber = authorizedOfficialTelephoneNumber;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
