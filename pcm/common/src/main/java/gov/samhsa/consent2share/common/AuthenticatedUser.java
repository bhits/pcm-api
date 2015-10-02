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
package gov.samhsa.consent2share.common;

import java.util.Date;

/**
 * The Class AuthenticatedUser.
 */
public class AuthenticatedUser {

	/** The username. */
	private String username;

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The birth date. */
	private Date birthDate;

	/** The gender display name. */
	private String genderDisplayName;
	
	//TODO: Replace this boolean flag with a more comprehensive and flexible way to store the user's role
	/** The providerAdmin role flag. */
	private boolean isProviderAdmin;
	
	
	
	/**
	 * Default constructor
	 */
	public AuthenticatedUser(){
		this.username = null;
		this.firstName = null;
		this.lastName = null;
		this.birthDate = null;
		this.genderDisplayName = null;
		this.isProviderAdmin = false;
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
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the birth date.
	 *
	 * @return the birth date
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Sets the birth date.
	 *
	 * @param birthDate the new birth date
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Gets the gender display name.
	 *
	 * @return the gender display name
	 */
	public String getGenderDisplayName() {
		return genderDisplayName;
	}

	/**
	 * Sets the gender display name.
	 *
	 * @param genderDisplayName the new gender display name
	 */
	public void setGenderDisplayName(String genderDisplayName) {
		this.genderDisplayName = genderDisplayName;
	}

	
	
	
	/**
	 * Gets the isProviderAdmin flag.
	 * 
	 * @return the isProviderAdmin
	 */
	public boolean getIsProviderAdmin() {
		return isProviderAdmin;
	}

	/**
	 * Sets the isProviderAdmin flag.
	 * 
	 * @param isProviderAdmin the isProviderAdmin to set (TRUE or FALSE)
	 */
	public void setIsProviderAdmin(boolean isProviderAdmin) {
		this.isProviderAdmin = isProviderAdmin;
	}

}
