/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
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
package gov.samhsa.mhc.pcm.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Class ProviderDto.
 */
public class ProviderDto
{

    /** The entity type. */
    private String entityType;

    /** The npi. */
    @NotNull
    @Size(min = 3, max = 30)
    private String npi;

    /** The org name. */
    @Size(max = 255)
    private String orgName;

    /** The last name. */
    @Size(max = 30)
    private String lastName;

    /** The first name. */
    @Size(max = 30)
    private String firstName;

    /** The first line practice location address. */
    @Size(max = 30)
    private String firstLinePracticeLocationAddress;

    /** The second line practice location address. */
    @Size(max = 30)
    private String secondLinePracticeLocationAddress;

    /** The practice location address city name. */
    @Size(max = 30)
    private String practiceLocationAddressCityName;

    /** The practice location address state name. */
    @Size(max = 30)
    private String practiceLocationAddressStateName;

    /** The practice location address postal code. */
    @Size(max = 30)
    private String practiceLocationAddressPostalCode;

    /** The practice location address country code. */
    @Size(max = 30)
    private String practiceLocationAddressCountryCode;

    /** The practice location address telephone number. */
    @Size(max = 30)
    private String practiceLocationAddressTelephoneNumber;

    /** The provider taxonomy description. */
    @Size(max = 255)
    private String providerTaxonomyDescription;

    /** The deletable. */
    private boolean deletable;

    public boolean isDeletable()
    {
        return deletable;
    }

    public void setDeletable(boolean deletable)
    {
        this.deletable = deletable;
    }


    public String getEntityType()
    {
        return entityType;
    }

    public void setEntityType(String entityType)
    {
        this.entityType = entityType;
    }

    public String getNpi()
    {
        return npi;
    }

    public void setNpi(String npi)
    {
        this.npi = npi;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getFirstLinePracticeLocationAddress()
    {
        return firstLinePracticeLocationAddress;
    }

    public void setFirstLinePracticeLocationAddress(String firstLinePracticeLocationAddress)
    {
        this.firstLinePracticeLocationAddress = firstLinePracticeLocationAddress;
    }

    public String getSecondLinePracticeLocationAddress()
    {
        return secondLinePracticeLocationAddress;
    }

    public void setSecondLinePracticeLocationAddress(String secondLinePracticeLocationAddress)
    {
        this.secondLinePracticeLocationAddress = secondLinePracticeLocationAddress;
    }

    public String getPracticeLocationAddressCityName()
    {
        return practiceLocationAddressCityName;
    }

    public void setPracticeLocationAddressCityName(String practiceLocationAddressCityName)
    {
        this.practiceLocationAddressCityName = practiceLocationAddressCityName;
    }

    public String getPracticeLocationAddressStateName()
    {
        return practiceLocationAddressStateName;
    }

    public void setPracticeLocationAddressStateName(String practiceLocationAddressStateName)
    {
        this.practiceLocationAddressStateName = practiceLocationAddressStateName;
    }

    public String getPracticeLocationAddressPostalCode()
    {
        return practiceLocationAddressPostalCode;
    }

    public void setPracticeLocationAddressPostalCode(String practiceLocationAddressPostalCode)
    {
        this.practiceLocationAddressPostalCode = practiceLocationAddressPostalCode;
    }

    public String getPracticeLocationAddressCountryCode()
    {
        return practiceLocationAddressCountryCode;
    }

    public void setPracticeLocationAddressCountryCode(String practiceLocationAddressCountryCode)
    {
        this.practiceLocationAddressCountryCode = practiceLocationAddressCountryCode;
    }

    public String getPracticeLocationAddressTelephoneNumber()
    {
        return practiceLocationAddressTelephoneNumber;
    }

    public void setPracticeLocationAddressTelephoneNumber(String practiceLocationAddressTelephoneNumber)
    {
        this.practiceLocationAddressTelephoneNumber = practiceLocationAddressTelephoneNumber;
    }

    public String getProviderTaxonomyDescription()
    {
        return providerTaxonomyDescription;
    }

    public void setProviderTaxonomyDescription(String providerTaxonomyDescription)
    {
        this.providerTaxonomyDescription = providerTaxonomyDescription;
    }

}
