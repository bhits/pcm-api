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
package gov.samhsa.c2s.pcm.domain.valueobject;

import gov.samhsa.c2s.pcm.domain.reference.AddressUseCode;
import gov.samhsa.c2s.pcm.domain.reference.StateCode;
import gov.samhsa.c2s.pcm.domain.reference.CountryCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The Class Address.
 */
@Embeddable
@Audited
public class Address {

    /** The address use code. */
    @ManyToOne(cascade = CascadeType.ALL)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private AddressUseCode addressUseCode;

    /** The street address line. */
    @NotNull
    @Size(min = 1, max = 50)
    private String streetAddressLine;

    /** The city. */
    @NotNull
    @Size(max = 30)
    private String city;

    /** The state code. */
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private StateCode stateCode;

    /** The postal code. */
    @NotNull
    @Pattern(regexp = "\\d{5}(?:[-\\s]\\d{4})?")
    private String postalCode;

    /** The country code. */
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private CountryCode countryCode;

	/**
	 * Gets the address use code.
	 *
	 * @return the address use code
	 */
	public AddressUseCode getAddressUseCode() {
        return this.addressUseCode;
    }

	/**
	 * Sets the address use code.
	 *
	 * @param addressUseCode the new address use code
	 */
	public void setAddressUseCode(AddressUseCode addressUseCode) {
        this.addressUseCode = addressUseCode;
    }

	/**
	 * Gets the street address line.
	 *
	 * @return the street address line
	 */
	public String getStreetAddressLine() {
        return this.streetAddressLine;
    }

	/**
	 * Sets the street address line.
	 *
	 * @param streetAddressLine the new street address line
	 */
	public void setStreetAddressLine(String streetAddressLine) {
        this.streetAddressLine = streetAddressLine;
    }

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
        return this.city;
    }

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
        this.city = city;
    }

	/**
	 * Gets the state code.
	 *
	 * @return the state code
	 */
	public StateCode getStateCode() {
        return this.stateCode;
    }

	/**
	 * Sets the state code.
	 *
	 * @param stateCode the new state code
	 */
	public void setStateCode(StateCode stateCode) {
        this.stateCode = stateCode;
    }

	/**
	 * Gets the postal code.
	 *
	 * @return the postal code
	 */
	public String getPostalCode() {
        return this.postalCode;
    }

	/**
	 * Sets the postal code.
	 *
	 * @param postalCode the new postal code
	 */
	public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

	/**
	 * Gets the country code.
	 *
	 * @return the country code
	 */
	public CountryCode getCountryCode() {
        return this.countryCode;
    }

	/**
	 * Sets the country code.
	 *
	 * @param countryCode the new country code
	 */
	public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
