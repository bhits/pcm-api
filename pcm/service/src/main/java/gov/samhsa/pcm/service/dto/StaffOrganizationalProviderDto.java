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

/**
 * The Class StaffOrganizationalProviderDto
 * 
 * @author michael.hadjiosif
 *
 */
public class StaffOrganizationalProviderDto {
	
	private Long id;
	private String orgName;
	private String npi;
	
	private String providerTaxonomyDescription;
	private String practiceLocationAddressTelephoneNumber;
	private String firstLinePracticeLocationAddress;
	private String practiceLocationAddressCityName;
	private String practiceLocationAddressStateName;
	private String practiceLocationAddressPostalCode;

	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}
	

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
	 * @param orgName
	 *            the new org name
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
		
	
	/**
     * Gets the npi.
     *
     * @return the npi
     */
    public String getNpi() {
        return this.npi;
    }

	/**
	 * Sets the npi.
	 *
	 * @param npi the new npi
	 */
	public void setNpi(String npi) {
        this.npi = npi;
	}

	/**
	 * @return the providerTaxonomyDescription
	 */
	public String getProviderTaxonomyDescription() {
		return providerTaxonomyDescription;
	}

	/**
	 * @param providerTaxonomyDescription the providerTaxonomyDescription to set
	 */
	public void setProviderTaxonomyDescription(
			String providerTaxonomyDescription) {
		this.providerTaxonomyDescription = providerTaxonomyDescription;
	}

	/**
	 * @return the practiceLocationAddressTelephoneNumber
	 */
	public String getPracticeLocationAddressTelephoneNumber() {
		return practiceLocationAddressTelephoneNumber;
	}

	/**
	 * @param practiceLocationAddressTelephoneNumber the practiceLocationAddressTelephoneNumber to set
	 */
	public void setPracticeLocationAddressTelephoneNumber(
			String practiceLocationAddressTelephoneNumber) {
		this.practiceLocationAddressTelephoneNumber = practiceLocationAddressTelephoneNumber;
	}

	/**
	 * @return the firstLinePracticeLocationAddress
	 */
	public String getFirstLinePracticeLocationAddress() {
		return firstLinePracticeLocationAddress;
	}

	/**
	 * @param firstLinePracticeLocationAddress the firstLinePracticeLocationAddress to set
	 */
	public void setFirstLinePracticeLocationAddress(
			String firstLinePracticeLocationAddress) {
		this.firstLinePracticeLocationAddress = firstLinePracticeLocationAddress;
	}

	/**
	 * @return the practiceLocationAddressCityName
	 */
	public String getPracticeLocationAddressCityName() {
		return practiceLocationAddressCityName;
	}

	/**
	 * @param practiceLocationAddressCityName the practiceLocationAddressCityName to set
	 */
	public void setPracticeLocationAddressCityName(
			String practiceLocationAddressCityName) {
		this.practiceLocationAddressCityName = practiceLocationAddressCityName;
	}

	/**
	 * @return the practiceLocationAddressStateName
	 */
	public String getPracticeLocationAddressStateName() {
		return practiceLocationAddressStateName;
	}

	/**
	 * @param practiceLocationAddressStateName the practiceLocationAddressStateName to set
	 */
	public void setPracticeLocationAddressStateName(
			String practiceLocationAddressStateName) {
		this.practiceLocationAddressStateName = practiceLocationAddressStateName;
	}

	/**
	 * @return the practiceLocationAddressPostalCode
	 */
	public String getPracticeLocationAddressPostalCode() {
		return practiceLocationAddressPostalCode;
	}

	/**
	 * @param practiceLocationAddressPostalCode the practiceLocationAddressPostalCode to set
	 */
	public void setPracticeLocationAddressPostalCode(
			String practiceLocationAddressPostalCode) {
		this.practiceLocationAddressPostalCode = practiceLocationAddressPostalCode;
	}

}
