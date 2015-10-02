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

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class PatientExportDto.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PatientDto {

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The prefix. */
	@Size(max = 30)
	private String prefix;

	/** The address street address line. */
	private String addressStreetAddressLine;

	/** The address city. */
	private String addressCity;

	/** The address state code. */
	private String addressStateCode;

	/** The address postal code. */
	@Pattern(regexp = "(^\\d{5}$|^\\d{5}-\\d{4})*")
	private String addressPostalCode;

	/** The address country code. */
	private String addressCountryCode;

	/** The birth date. */
	@Past
	@DateTimeFormat(pattern = "yyyyMMdd")
	@XmlJavaTypeAdapter(XMLIntegerDateAdapter.class)
	private Date birthDate;

	/**
	 * Gets the address street address line.
	 * 
	 * @return the address street address line
	 */
	public String getAddressStreetAddressLine() {
		return addressStreetAddressLine;
	}

	/**
	 * Sets the address street address line.
	 * 
	 * @param addressStreetAddressLine
	 *            the new address street address line
	 */
	public void setAddressStreetAddressLine(String addressStreetAddressLine) {
		this.addressStreetAddressLine = addressStreetAddressLine;
	}

	/**
	 * Gets the address city.
	 * 
	 * @return the address city
	 */
	public String getAddressCity() {
		return addressCity;
	}

	/**
	 * Sets the address city.
	 * 
	 * @param addressCity
	 *            the new address city
	 */
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	/**
	 * Gets the address state code.
	 * 
	 * @return the address state code
	 */
	public String getAddressStateCode() {
		return addressStateCode;
	}

	/**
	 * Sets the address state code.
	 * 
	 * @param addressStateCode
	 *            the new address state code
	 */
	public void setAddressStateCode(String addressStateCode) {
		this.addressStateCode = addressStateCode;
	}

	/**
	 * Gets the address postal code.
	 * 
	 * @return the address postal code
	 */
	public String getAddressPostalCode() {
		return addressPostalCode;
	}

	/**
	 * Sets the address postal code.
	 * 
	 * @param addressPostalCode
	 *            the new address postal code
	 */
	public void setAddressPostalCode(String addressPostalCode) {
		this.addressPostalCode = addressPostalCode;
	}

	/**
	 * Gets the address country code.
	 * 
	 * @return the address country code
	 */
	public String getAddressCountryCode() {
		return addressCountryCode;
	}

	/**
	 * Sets the address country code.
	 * 
	 * @param addressCountryCode
	 *            the new address country code
	 */
	public void setAddressCountryCode(String addressCountryCode) {
		this.addressCountryCode = addressCountryCode;
	}

	/** The administrative gender code. */
	private String administrativeGenderCode;

	/** The telephone type telephone. */
	@Pattern(regexp = "(^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4}))*")
	private String telephoneTypeTelephone;

	/** The email. */
	private String email;

	/** The social security number. */
	@Pattern(regexp = "(\\d{3}-?\\d{2}-?\\d{4})*")
	private String socialSecurityNumber;

	/** The medical record number. */
	@Size(max = 30)
	private String medicalRecordNumber;

	/** The enterprise identifier. */
	@Size(max = 255)
	private String enterpriseIdentifier;

	/** The patient id number. */
	@Size(max = 30)
	private String patientIdNumber;

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
	 * @param firstName
	 *            the new first name
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
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the prefix.
	 * 
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the prefix.
	 * 
	 * @param prefix
	 *            the new prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
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
	 * @param birthDate
	 *            the new birth date
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Gets the administrative gender code.
	 * 
	 * @return the administrative gender code
	 */
	public String getAdministrativeGenderCode() {
		return administrativeGenderCode;
	}

	/**
	 * Sets the administrative gender code.
	 * 
	 * @param administrativeGenderCode
	 *            the new administrative gender code
	 */
	public void setAdministrativeGenderCode(String administrativeGenderCode) {
		this.administrativeGenderCode = administrativeGenderCode;
	}

	/**
	 * Gets the telephone type telephone.
	 * 
	 * @return the telephone type telephone
	 */
	public String getTelephoneTypeTelephone() {
		return telephoneTypeTelephone;
	}

	/**
	 * Sets the telephone type telephone.
	 * 
	 * @param telephoneTypeTelephone
	 *            the new telephone type telephone
	 */
	public void setTelephoneTypeTelephone(String telephoneTypeTelephone) {
		this.telephoneTypeTelephone = telephoneTypeTelephone;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the social security number.
	 * 
	 * @return the social security number
	 */
	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	/**
	 * Sets the social security number.
	 * 
	 * @param socialSecurityNumber
	 *            the new social security number
	 */
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	/**
	 * Gets the medical record number.
	 * 
	 * @return the medical record number
	 */
	public String getMedicalRecordNumber() {
		return medicalRecordNumber;
	}

	/**
	 * Sets the medical record number.
	 * 
	 * @param medicalRecordNumber
	 *            the new medical record number
	 */
	public void setMedicalRecordNumber(String medicalRecordNumber) {
		this.medicalRecordNumber = medicalRecordNumber;
	}

	/**
	 * Gets the patient id number.
	 * 
	 * @return the patient id number
	 */
	public String getPatientIdNumber() {
		return patientIdNumber;
	}

	/**
	 * Sets the patient id number.
	 * 
	 * @param patientIdNumber
	 *            the new patient id number
	 */
	public void setPatientIdNumber(String patientIdNumber) {
		this.patientIdNumber = patientIdNumber;
	}

	/**
	 * Gets the enterprise identifier.
	 * 
	 * @return the enterprise identifier
	 */
	public String getEnterpriseIdentifier() {
		return enterpriseIdentifier;
	}

	/**
	 * Sets the enterprise identifier.
	 * 
	 * @param enterpriseIdentifier
	 *            the new enterprise identifier
	 */
	public void setEnterpriseIdentifier(String enterpriseIdentifier) {
		this.enterpriseIdentifier = enterpriseIdentifier;
	}
}
