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
package gov.samhsa.consent2share.service.dto;


import java.util.Set;

/**
 * The Class PatientConnectionDto.
 */
public class PatientConnectionDto{

	/** The username. */
	private String username;

	/** The individual providers. */
	private Set<IndividualProviderDto> individualProviders;
	
	/** The organizational providers. */
	private Set<OrganizationalProviderDto> organizationalProviders;

	/**
	 * Gets the individual providers.
	 *
	 * @return the individual providers
	 */
	public Set<IndividualProviderDto> getIndividualProviders() {
		return individualProviders;
	}

	/**
	 * Sets the individual providers.
	 *
	 * @param invividualProviders the new individual providers
	 */
	public void setIndividualProviders(
			Set<IndividualProviderDto> invividualProviders) {
		this.individualProviders = invividualProviders;
	}

	/**
	 * Gets the organizational providers.
	 *
	 * @return the organizational providers
	 */
	public Set<OrganizationalProviderDto> getOrganizationalProviders() {
		return organizationalProviders;
	}

	/**
	 * Sets the organizational providers.
	 *
	 * @param organizationalProviders the new organizational providers
	 */
	public void setOrganizationalProviders(
			Set<OrganizationalProviderDto> organizationalProviders) {
		this.organizationalProviders = organizationalProviders;
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

}
