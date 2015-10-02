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
package gov.samhsa.consent;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class OrganizationalProviderExportDto.
 */
@XmlRootElement
public class OrganizationalProviderDto {

	/** The org name. */
	@NotNull
	@Size(max = 255)
	private String orgName;

	/** The practice location address telephone number. */
	@NotNull
	@Size(max = 30)
	private String practiceLocationAddressTelephoneNumber;

	/** The first line practice location address. */
	@NotNull
	@Size(max = 30)
	private String firstLinePracticeLocationAddress;

	/** The second line practice location address. */
	@NotNull
	@Size(max = 30)
	private String secondLinePracticeLocationAddress;

	/** The practice location address city name. */
	@NotNull
	@Size(max = 30)
	private String practiceLocationAddressCityName;

	/** The practice location address state name. */
	@NotNull
	@Size(max = 30)
	private String practiceLocationAddressStateName;

	/** The practice location address postal code. */
	@NotNull
	@Size(max = 30)
	private String practiceLocationAddressPostalCode;

	/** The practice location address country code. */
	@NotNull
	@Size(max = 30)
	private String practiceLocationAddressCountryCode;

	/** The npi. */
	@NotNull
	@Size(max = 30)
	private String npi;

	/** The enumeration date. */
	@NotNull
	@Size(max = 30)
	private String enumerationDate;

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
	 * Gets the practice location address telephone number.
	 *
	 * @return the practice location address telephone number
	 */
	public String getPracticeLocationAddressTelephoneNumber() {
		return practiceLocationAddressTelephoneNumber;
	}

	/**
	 * Sets the practice location address telephone number.
	 *
	 * @param practiceLocationAddressTelephoneNumber the new practice location address telephone number
	 */
	public void setPracticeLocationAddressTelephoneNumber(
			String practiceLocationAddressTelephoneNumber) {
		this.practiceLocationAddressTelephoneNumber = practiceLocationAddressTelephoneNumber;
	}

	/**
	 * Gets the first line practice location address.
	 *
	 * @return the first line practice location address
	 */
	public String getFirstLinePracticeLocationAddress() {
		return firstLinePracticeLocationAddress;
	}

	/**
	 * Sets the first line practice location address.
	 *
	 * @param firstLinePracticeLocationAddress the new first line practice location address
	 */
	public void setFirstLinePracticeLocationAddress(
			String firstLinePracticeLocationAddress) {
		this.firstLinePracticeLocationAddress = firstLinePracticeLocationAddress;
	}

	/**
	 * Gets the second line practice location address.
	 *
	 * @return the second line practice location address
	 */
	public String getSecondLinePracticeLocationAddress() {
		return secondLinePracticeLocationAddress;
	}

	/**
	 * Sets the second line practice location address.
	 *
	 * @param secondLinePracticeLocationAddress the new second line practice location address
	 */
	public void setSecondLinePracticeLocationAddress(
			String secondLinePracticeLocationAddress) {
		this.secondLinePracticeLocationAddress = secondLinePracticeLocationAddress;
	}

	/**
	 * Gets the practice location address city name.
	 *
	 * @return the practice location address city name
	 */
	public String getPracticeLocationAddressCityName() {
		return practiceLocationAddressCityName;
	}

	/**
	 * Sets the practice location address city name.
	 *
	 * @param practiceLocationAddressCityName the new practice location address city name
	 */
	public void setPracticeLocationAddressCityName(
			String practiceLocationAddressCityName) {
		this.practiceLocationAddressCityName = practiceLocationAddressCityName;
	}

	/**
	 * Gets the practice location address state name.
	 *
	 * @return the practice location address state name
	 */
	public String getPracticeLocationAddressStateName() {
		return practiceLocationAddressStateName;
	}

	/**
	 * Sets the practice location address state name.
	 *
	 * @param practiceLocationAddressStateName the new practice location address state name
	 */
	public void setPracticeLocationAddressStateName(
			String practiceLocationAddressStateName) {
		this.practiceLocationAddressStateName = practiceLocationAddressStateName;
	}

	/**
	 * Gets the practice location address postal code.
	 *
	 * @return the practice location address postal code
	 */
	public String getPracticeLocationAddressPostalCode() {
		return practiceLocationAddressPostalCode;
	}

	/**
	 * Sets the practice location address postal code.
	 *
	 * @param practiceLocationAddressPostalCode the new practice location address postal code
	 */
	public void setPracticeLocationAddressPostalCode(
			String practiceLocationAddressPostalCode) {
		this.practiceLocationAddressPostalCode = practiceLocationAddressPostalCode;
	}

	/**
	 * Gets the practice location address country code.
	 *
	 * @return the practice location address country code
	 */
	public String getPracticeLocationAddressCountryCode() {
		return practiceLocationAddressCountryCode;
	}

	/**
	 * Sets the practice location address country code.
	 *
	 * @param practiceLocationAddressCountryCode the new practice location address country code
	 */
	public void setPracticeLocationAddressCountryCode(
			String practiceLocationAddressCountryCode) {
		this.practiceLocationAddressCountryCode = practiceLocationAddressCountryCode;
	}

	/**
	 * Gets the npi.
	 *
	 * @return the npi
	 */
	public String getNpi() {
		return npi;
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
	 * Gets the enumeration date.
	 *
	 * @return the enumeration date
	 */
	public String getEnumerationDate() {
		return enumerationDate;
	}

	/**
	 * Sets the enumeration date.
	 *
	 * @param enumerationDate the new enumeration date
	 */
	public void setEnumerationDate(String enumerationDate) {
		this.enumerationDate = enumerationDate;
	}

}
