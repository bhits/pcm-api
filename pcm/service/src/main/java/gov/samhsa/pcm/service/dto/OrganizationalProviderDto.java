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
package gov.samhsa.pcm.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class OrganizationalProviderDto.
 */
public class OrganizationalProviderDto extends AbstractProviderDto {

	/** The org name. */
	@NotNull
	@Size(max = 255)
	private String orgName;

	/** The authorized official last name. */
	@NotNull
	@Size(max = 30)
	private String authorizedOfficialLastName;

	/** The authorized official first name. */
	@NotNull
	@Size(max = 30)
	private String authorizedOfficialFirstName;

	/** The authorized official title. */
	@NotNull
	@Size(max = 30)
	private String authorizedOfficialTitle;

	/** The authorized official name prefix. */
	@NotNull
	@Size(max = 30)
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
		return orgName;
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
	 * Gets the authorized official last name.
	 *
	 * @return the authorized official last name
	 */
	public String getAuthorizedOfficialLastName() {
		return authorizedOfficialLastName;
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
		return authorizedOfficialFirstName;
	}

	/**
	 * Sets the authorized official first name.
	 *
	 * @param authorizedOfficialFirstName the new authorized official first name
	 */
	public void setAuthorizedOfficialFirstName(
			String authorizedOfficialFirstName) {
		this.authorizedOfficialFirstName = authorizedOfficialFirstName;
	}

	/**
	 * Gets the authorized official title.
	 *
	 * @return the authorized official title
	 */
	public String getAuthorizedOfficialTitle() {
		return authorizedOfficialTitle;
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
		return authorizedOfficialNamePrefix;
	}

	/**
	 * Sets the authorized official name prefix.
	 *
	 * @param authorizedOfficialNamePrefix the new authorized official name prefix
	 */
	public void setAuthorizedOfficialNamePrefix(
			String authorizedOfficialNamePrefix) {
		this.authorizedOfficialNamePrefix = authorizedOfficialNamePrefix;
	}

	/**
	 * Gets the authorized official telephone number.
	 *
	 * @return the authorized official telephone number
	 */
	public String getAuthorizedOfficialTelephoneNumber() {
		return authorizedOfficialTelephoneNumber;
	}

	/**
	 * Sets the authorized official telephone number.
	 *
	 * @param authorizedOfficialTelephoneNumber the new authorized official telephone number
	 */
	public void setAuthorizedOfficialTelephoneNumber(
			String authorizedOfficialTelephoneNumber) {
		this.authorizedOfficialTelephoneNumber = authorizedOfficialTelephoneNumber;
	}

}
