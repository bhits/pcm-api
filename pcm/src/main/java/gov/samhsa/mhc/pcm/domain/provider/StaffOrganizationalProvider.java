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

import gov.samhsa.mhc.pcm.domain.reference.EntityType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * The Class OrganizationalProvider.
 */
@Entity
@SequenceGenerator(name="idgen", sequenceName="ORGPROV_SEQ", initialValue = 1)
public class StaffOrganizationalProvider{

	@OneToOne
    @JoinColumn(name = "individual_Provider")
    private OrganizationalProvider organizationalProvider;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idgen")
    @Column(name = "id")
    private Long id;
    
    public StaffOrganizationalProvider(){
    	super();
    	this.id = (long) -1;
    }

	public void setId(long id) {
		this.id = id;
	}
	
	public long getId(){
		return this.id;
	}

	public OrganizationalProvider getOrganizationalProvider() {
		return organizationalProvider;
	}

	public void setOrganizationalProvider(
			OrganizationalProvider organizationalProvider) {
		this.organizationalProvider = organizationalProvider;
	}
	
	/**
     * Gets the npi.
     *
     * @return the npi
     */
    public String getNpi() {
        return this.organizationalProvider.getNpi();
    }

	/**
	 * Sets the npi.
	 *
	 * @param npi the new npi
	 */
	public void setNpi(String npi) {
        this.organizationalProvider.setNpi(npi);
    }

	/**
	 * Gets the entity type.
	 *
	 * @return the entity type
	 */
	public EntityType getEntityType() {
        return this.organizationalProvider.getEntityType();
    }

	/**
	 * Sets the entity type.
	 *
	 * @param entityType the new entity type
	 */
	public void setEntityType(EntityType entityType) {
        this.organizationalProvider.setEntityType(entityType);
    }

	/**
	 * Gets the first line mailing address.
	 *
	 * @return the first line mailing address
	 */
	public String getFirstLineMailingAddress() {
        return this.organizationalProvider.getFirstLineMailingAddress();
    }

	/**
	 * Sets the first line mailing address.
	 *
	 * @param firstLineMailingAddress the new first line mailing address
	 */
	public void setFirstLineMailingAddress(String firstLineMailingAddress) {
        this.organizationalProvider.setFirstLineMailingAddress(firstLineMailingAddress);
    }

	/**
	 * Gets the second line mailing address.
	 *
	 * @return the second line mailing address
	 */
	public String getSecondLineMailingAddress() {
        return this.organizationalProvider.getSecondLineMailingAddress();
    }

	/**
	 * Sets the second line mailing address.
	 *
	 * @param secondLineMailingAddress the new second line mailing address
	 */
	public void setSecondLineMailingAddress(String secondLineMailingAddress) {
        this.organizationalProvider.setSecondLineMailingAddress(secondLineMailingAddress);
    }

	/**
	 * Gets the mailing address city name.
	 *
	 * @return the mailing address city name
	 */
	public String getMailingAddressCityName() {
        return this.organizationalProvider.getMailingAddressCityName();
    }

	/**
	 * Sets the mailing address city name.
	 *
	 * @param mailingAddressCityName the new mailing address city name
	 */
	public void setMailingAddressCityName(String mailingAddressCityName) {
        this.organizationalProvider.setMailingAddressCityName(mailingAddressCityName);
    }

	/**
	 * Gets the mailing address state name.
	 *
	 * @return the mailing address state name
	 */
	public String getMailingAddressStateName() {
        return this.organizationalProvider.getMailingAddressStateName();
    }

	/**
	 * Sets the mailing address state name.
	 *
	 * @param mailingAddressStateName the new mailing address state name
	 */
	public void setMailingAddressStateName(String mailingAddressStateName) {
        this.organizationalProvider.setMailingAddressStateName(mailingAddressStateName);
    }

	/**
	 * Gets the mailing address postal code.
	 *
	 * @return the mailing address postal code
	 */
	public String getMailingAddressPostalCode() {
        return this.organizationalProvider.getMailingAddressPostalCode();
    }

	/**
	 * Sets the mailing address postal code.
	 *
	 * @param mailingAddressPostalCode the new mailing address postal code
	 */
	public void setMailingAddressPostalCode(String mailingAddressPostalCode) {
        this.organizationalProvider.setMailingAddressPostalCode(mailingAddressPostalCode);
    }

	/**
	 * Gets the mailing address country code.
	 *
	 * @return the mailing address country code
	 */
	public String getMailingAddressCountryCode() {
        return this.organizationalProvider.getMailingAddressCountryCode();
    }

	/**
	 * Sets the mailing address country code.
	 *
	 * @param mailingAddressCountryCode the new mailing address country code
	 */
	public void setMailingAddressCountryCode(String mailingAddressCountryCode) {
        this.organizationalProvider.setMailingAddressCountryCode(mailingAddressCountryCode);
    }

	/**
	 * Gets the mailing address telephone number.
	 *
	 * @return the mailing address telephone number
	 */
	public String getMailingAddressTelephoneNumber() {
        return this.organizationalProvider.getMailingAddressTelephoneNumber();
    }

	/**
	 * Sets the mailing address telephone number.
	 *
	 * @param mailingAddressTelephoneNumber the new mailing address telephone number
	 */
	public void setMailingAddressTelephoneNumber(String mailingAddressTelephoneNumber) {
        this.organizationalProvider.setMailingAddressTelephoneNumber(mailingAddressTelephoneNumber);
    }

	/**
	 * Gets the mailing address fax number.
	 *
	 * @return the mailing address fax number
	 */
	public String getMailingAddressFaxNumber() {
        return this.organizationalProvider.getMailingAddressFaxNumber();
    }

	/**
	 * Sets the mailing address fax number.
	 *
	 * @param mailingAddressFaxNumber the new mailing address fax number
	 */
	public void setMailingAddressFaxNumber(String mailingAddressFaxNumber) {
        this.organizationalProvider.setMailingAddressFaxNumber(mailingAddressFaxNumber);
    }

	/**
	 * Gets the first line practice location address.
	 *
	 * @return the first line practice location address
	 */
	public String getFirstLinePracticeLocationAddress() {
        return this.organizationalProvider.getFirstLinePracticeLocationAddress();
    }

	/**
	 * Sets the first line practice location address.
	 *
	 * @param firstLinePracticeLocationAddress the new first line practice location address
	 */
	public void setFirstLinePracticeLocationAddress(String firstLinePracticeLocationAddress) {
        this.organizationalProvider.setFirstLinePracticeLocationAddress(firstLinePracticeLocationAddress);
    }

	/**
	 * Gets the second line practice location address.
	 *
	 * @return the second line practice location address
	 */
	public String getSecondLinePracticeLocationAddress() {
        return this.organizationalProvider.getSecondLinePracticeLocationAddress();
    }

	/**
	 * Sets the second line practice location address.
	 *
	 * @param secondLinePracticeLocationAddress the new second line practice location address
	 */
	public void setSecondLinePracticeLocationAddress(String secondLinePracticeLocationAddress) {
        this.organizationalProvider.setSecondLinePracticeLocationAddress(secondLinePracticeLocationAddress);
    }

	/**
	 * Gets the practice location address city name.
	 *
	 * @return the practice location address city name
	 */
	public String getPracticeLocationAddressCityName() {
        return this.organizationalProvider.getPracticeLocationAddressCityName();
    }

	/**
	 * Sets the practice location address city name.
	 *
	 * @param practiceLocationAddressCityName the new practice location address city name
	 */
	public void setPracticeLocationAddressCityName(String practiceLocationAddressCityName) {
        this.organizationalProvider.setPracticeLocationAddressCityName(practiceLocationAddressCityName);
    }

	/**
	 * Gets the practice location address state name.
	 *
	 * @return the practice location address state name
	 */
	public String getPracticeLocationAddressStateName() {
        return this.organizationalProvider.getPracticeLocationAddressStateName();
    }

	/**
	 * Sets the practice location address state name.
	 *
	 * @param practiceLocationAddressStateName the new practice location address state name
	 */
	public void setPracticeLocationAddressStateName(String practiceLocationAddressStateName) {
        this.organizationalProvider.setPracticeLocationAddressStateName(practiceLocationAddressStateName);
    }

	/**
	 * Gets the practice location address postal code.
	 *
	 * @return the practice location address postal code
	 */
	public String getPracticeLocationAddressPostalCode() {
        return this.organizationalProvider.getPracticeLocationAddressPostalCode();
    }

	/**
	 * Sets the practice location address postal code.
	 *
	 * @param practiceLocationAddressPostalCode the new practice location address postal code
	 */
	public void setPracticeLocationAddressPostalCode(String practiceLocationAddressPostalCode) {
        this.organizationalProvider.setPracticeLocationAddressPostalCode(practiceLocationAddressPostalCode);
    }

	/**
	 * Gets the practice location address country code.
	 *
	 * @return the practice location address country code
	 */
	public String getPracticeLocationAddressCountryCode() {
        return this.organizationalProvider.getPracticeLocationAddressCountryCode();
    }

	/**
	 * Sets the practice location address country code.
	 *
	 * @param practiceLocationAddressCountryCode the new practice location address country code
	 */
	public void setPracticeLocationAddressCountryCode(String practiceLocationAddressCountryCode) {
        this.organizationalProvider.setPracticeLocationAddressCountryCode(practiceLocationAddressCountryCode);
    }

	/**
	 * Gets the practice location address telephone number.
	 *
	 * @return the practice location address telephone number
	 */
	public String getPracticeLocationAddressTelephoneNumber() {
        return this.organizationalProvider.getPracticeLocationAddressTelephoneNumber();
    }

	/**
	 * Sets the practice location address telephone number.
	 *
	 * @param practiceLocationAddressTelephoneNumber the new practice location address telephone number
	 */
	public void setPracticeLocationAddressTelephoneNumber(String practiceLocationAddressTelephoneNumber) {
        this.organizationalProvider.setPracticeLocationAddressTelephoneNumber(practiceLocationAddressTelephoneNumber);
    }

	/**
	 * Gets the practice location address fax number.
	 *
	 * @return the practice location address fax number
	 */
	public String getPracticeLocationAddressFaxNumber() {
        return this.organizationalProvider.getPracticeLocationAddressFaxNumber();
    }

	/**
	 * Sets the practice location address fax number.
	 *
	 * @param practiceLocationAddressFaxNumber the new practice location address fax number
	 */
	public void setPracticeLocationAddressFaxNumber(String practiceLocationAddressFaxNumber) {
        this.organizationalProvider.setPracticeLocationAddressFaxNumber(practiceLocationAddressFaxNumber);
    }

	/**
	 * Gets the enumeration date.
	 *
	 * @return the enumeration date
	 */
	public String getEnumerationDate() {
        return this.organizationalProvider.getEnumerationDate();
    }

	/**
	 * Sets the enumeration date.
	 *
	 * @param enumerationDate the new enumeration date
	 */
	public void setEnumerationDate(String enumerationDate) {
        this.organizationalProvider.setEnumerationDate(enumerationDate);
    }

	/**
	 * Gets the last update date.
	 *
	 * @return the last update date
	 */
	public String getLastUpdateDate() {
        return this.organizationalProvider.getLastUpdateDate();
    }

	/**
	 * Sets the last update date.
	 *
	 * @param lastUpdateDate the new last update date
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
        this.organizationalProvider.setLastUpdateDate(lastUpdateDate);
    }

	/**
	 * Gets the provider taxonomy code.
	 *
	 * @return the provider taxonomy code
	 */
	public String getProviderTaxonomyCode() {
        return this.organizationalProvider.getProviderTaxonomyCode();
    }

	/**
	 * Sets the provider taxonomy code.
	 *
	 * @param providerTaxonomyCode the new provider taxonomy code
	 */
	public void setProviderTaxonomyCode(String providerTaxonomyCode) {
        this.organizationalProvider.setProviderTaxonomyCode(providerTaxonomyCode);
    }

	/**
	 * Gets the provider taxonomy description.
	 *
	 * @return the provider taxonomy description
	 */
	public String getProviderTaxonomyDescription() {
        return this.organizationalProvider.getProviderTaxonomyDescription();
    }

	/**
	 * Sets the provider taxonomy description.
	 *
	 * @param providerTaxonomyDescription the new provider taxonomy description
	 */
	public void setProviderTaxonomyDescription(String providerTaxonomyDescription) {
        this.organizationalProvider.setProviderTaxonomyDescription(providerTaxonomyDescription);
    }

	/**
	 * Gets the org name.
	 *
	 * @return the org name
	 */
	public String getOrgName() {
        return this.organizationalProvider.getOrgName();
    }

	/**
	 * Sets the org name.
	 *
	 * @param orgName the new org name
	 */
	public void setOrgName(String orgName) {
        this.organizationalProvider.setOrgName(orgName);
    }

	/**
	 * Gets the other org name.
	 *
	 * @return the other org name
	 */
	public String getOtherOrgName() {
        return this.organizationalProvider.getOtherOrgName();
    }

	/**
	 * Sets the other org name.
	 *
	 * @param otherOrgName the new other org name
	 */
	public void setOtherOrgName(String otherOrgName) {
        this.organizationalProvider.setOtherOrgName(otherOrgName);
    }

	/**
	 * Gets the authorized official last name.
	 *
	 * @return the authorized official last name
	 */
	public String getAuthorizedOfficialLastName() {
        return this.organizationalProvider.getAuthorizedOfficialLastName();
    }

	/**
	 * Sets the authorized official last name.
	 *
	 * @param authorizedOfficialLastName the new authorized official last name
	 */
	public void setAuthorizedOfficialLastName(String authorizedOfficialLastName) {
        this.organizationalProvider.setAuthorizedOfficialLastName(authorizedOfficialLastName);
    }

	/**
	 * Gets the authorized official first name.
	 *
	 * @return the authorized official first name
	 */
	public String getAuthorizedOfficialFirstName() {
        return this.organizationalProvider.getAuthorizedOfficialFirstName();
    }

	/**
	 * Sets the authorized official first name.
	 *
	 * @param authorizedOfficialFirstName the new authorized official first name
	 */
	public void setAuthorizedOfficialFirstName(String authorizedOfficialFirstName) {
        this.organizationalProvider.setAuthorizedOfficialFirstName(authorizedOfficialFirstName);
    }

	/**
	 * Gets the authorized official title.
	 *
	 * @return the authorized official title
	 */
	public String getAuthorizedOfficialTitle() {
        return this.organizationalProvider.getAuthorizedOfficialTitle();
    }

	/**
	 * Sets the authorized official title.
	 *
	 * @param authorizedOfficialTitle the new authorized official title
	 */
	public void setAuthorizedOfficialTitle(String authorizedOfficialTitle) {
        this.organizationalProvider.setAuthorizedOfficialTitle(authorizedOfficialTitle);
    }

	/**
	 * Gets the authorized official name prefix.
	 *
	 * @return the authorized official name prefix
	 */
	public String getAuthorizedOfficialNamePrefix() {
        return this.organizationalProvider.getAuthorizedOfficialNamePrefix();
    }

	/**
	 * Sets the authorized official name prefix.
	 *
	 * @param authorizedOfficialNamePrefix the new authorized official name prefix
	 */
	public void setAuthorizedOfficialNamePrefix(String authorizedOfficialNamePrefix) {
        this.organizationalProvider.setAuthorizedOfficialNamePrefix(authorizedOfficialNamePrefix);
    }

	/**
	 * Gets the authorized official telephone number.
	 *
	 * @return the authorized official telephone number
	 */
	public String getAuthorizedOfficialTelephoneNumber() {
        return this.organizationalProvider.getAuthorizedOfficialTelephoneNumber();
    }

	/**
	 * Sets the authorized official telephone number.
	 *
	 * @param authorizedOfficialTelephoneNumber the new authorized official telephone number
	 */
	public void setAuthorizedOfficialTelephoneNumber(String authorizedOfficialTelephoneNumber) {
        this.organizationalProvider.setAuthorizedOfficialTelephoneNumber(authorizedOfficialTelephoneNumber);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this.organizationalProvider, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
