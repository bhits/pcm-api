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

import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.OrganizationalProvider;

import java.util.Date;
import java.util.Set;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * The Class LegalRepresentativeDto.
 */
public class LegalRepresentativeDto {

	/** The relationship start date. */
	@DateTimeFormat(pattern = "MM/dd/yyyy")	
    private Date relationshipStartDate;

    /** The relationship end date. */
    @DateTimeFormat(pattern = "MM/dd/yyyy")	
    private Date relationshipEndDate;

    /** The legal representative type code. */
    @ManyToOne
    private String legalRepresentativeTypeCode;
	
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
	@Pattern (regexp = "(^\\d{5}$|^\\d{5}-\\d{4})*")
	private String addressPostalCode;
	
	/** The address country code. */
	private String addressCountryCode;
	
	/** The telephone telephone. */
	@Pattern (regexp = "(^\\(?([0-9]{3})\\)?[-. ]([0-9]{3})[-. ]([0-9]{4}))*")
	private String telephoneTelephone;
	
	/** The telephone telecom use code. */
	private String telephoneTelecomUseCode;	
	
	/** The email. */
	private String email;	
	
	/** The id. */
	private Long id;
	
	/** The birth date. */
	@Past
	@DateTimeFormat(pattern = "MM/dd/yyyy")	
	private Date birthDate;
	
	/** The username. */
	private String username;	
	
	/** The individual providers. */
	private Set<IndividualProvider> individualProviders;
	
	/** The organizational providers. */
	private Set<OrganizationalProvider> organizationalProviders;
	
	/** The social security number. */
	@Pattern(regexp = "(\\d{3}-?\\d{2}-?\\d{4})*")
	private String socialSecurityNumber;



	/** The administrative gender code. */
	@NotEmpty
	private String administrativeGenderCode;
		
	/** The marital status code. */
	private LookupDto maritalStatusCode;
	
	/** The religious affiliation code. */
	private LookupDto religiousAffiliationCode;
	
	/** The race code. */
	private LookupDto raceCode;
	
	/** The ethnic group code. */
	private LookupDto ethnicGroupCode;
	
	/** The language code. */
	private LookupDto languageCode;
	
	/** The medical record number. */
	@Size(max = 30)
    private String medicalRecordNumber;
	
	/** The patient id number. */
	@Size(max = 30)
    private String patientIdNumber;

	/**
	 * Gets the relationship start date.
	 *
	 * @return the relationship start date
	 */
	public Date getRelationshipStartDate() {
		return relationshipStartDate;
	}

	/**
	 * Sets the relationship start date.
	 *
	 * @param relationshipStartDate the new relationship start date
	 */
	public void setRelationshipStartDate(Date relationshipStartDate) {
		this.relationshipStartDate = relationshipStartDate;
	}

	/**
	 * Gets the relationship end date.
	 *
	 * @return the relationship end date
	 */
	public Date getRelationshipEndDate() {
		return relationshipEndDate;
	}

	/**
	 * Sets the relationship end date.
	 *
	 * @param relationshipEndDate the new relationship end date
	 */
	public void setRelationshipEndDate(Date relationshipEndDate) {
		this.relationshipEndDate = relationshipEndDate;
	}

	/**
	 * Gets the legal representative type code.
	 *
	 * @return the legal representative type code
	 */
	public String getLegalRepresentativeTypeCode() {
		return legalRepresentativeTypeCode;
	}

	/**
	 * Sets the legal representative type code.
	 *
	 * @param legalRepresentativeTypeCode the new legal representative type code
	 */
	public void setLegalRepresentativeTypeCode(String legalRepresentativeTypeCode) {
		this.legalRepresentativeTypeCode = legalRepresentativeTypeCode;
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
	 * @param prefix the new prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

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
	 * @param addressStreetAddressLine the new address street address line
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
	 * @param addressCity the new address city
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
	 * @param addressStateCode the new address state code
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
	 * @param addressPostalCode the new address postal code
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
	 * @param addressCountryCode the new address country code
	 */
	public void setAddressCountryCode(String addressCountryCode) {
		this.addressCountryCode = addressCountryCode;
	}

	/**
	 * Gets the telephone telephone.
	 *
	 * @return the telephone telephone
	 */
	public String getTelephoneTelephone() {
		return telephoneTelephone;
	}

	/**
	 * Sets the telephone telephone.
	 *
	 * @param telephoneTelephone the new telephone telephone
	 */
	public void setTelephoneTelephone(String telephoneTelephone) {
		this.telephoneTelephone = telephoneTelephone;
	}

	/**
	 * Gets the telephone telecom use code.
	 *
	 * @return the telephone telecom use code
	 */
	public String getTelephoneTelecomUseCode() {
		return telephoneTelecomUseCode;
	}

	/**
	 * Sets the telephone telecom use code.
	 *
	 * @param telephoneTelecomUseCode the new telephone telecom use code
	 */
	public void setTelephoneTelecomUseCode(String telephoneTelecomUseCode) {
		this.telephoneTelecomUseCode = telephoneTelecomUseCode;
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
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Gets the individual providers.
	 *
	 * @return the individual providers
	 */
	public Set<IndividualProvider> getIndividualProviders() {
		return individualProviders;
	}

	/**
	 * Sets the individual providers.
	 *
	 * @param individualProviders the new individual providers
	 */
	public void setIndividualProviders(Set<IndividualProvider> individualProviders) {
		this.individualProviders = individualProviders;
	}

	/**
	 * Gets the organizational providers.
	 *
	 * @return the organizational providers
	 */
	public Set<OrganizationalProvider> getOrganizationalProviders() {
		return organizationalProviders;
	}

	/**
	 * Sets the organizational providers.
	 *
	 * @param organizationalProviders the new organizational providers
	 */
	public void setOrganizationalProviders(
			Set<OrganizationalProvider> organizationalProviders) {
		this.organizationalProviders = organizationalProviders;
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
	 * @param socialSecurityNumber the new social security number
	 */
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
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
	 * @param administrativeGenderCode the new administrative gender code
	 */
	public void setAdministrativeGenderCode(String administrativeGenderCode) {
		this.administrativeGenderCode = administrativeGenderCode;
	}

	/**
	 * Gets the marital status code.
	 *
	 * @return the marital status code
	 */
	public LookupDto getMaritalStatusCode() {
		return maritalStatusCode;
	}

	/**
	 * Sets the marital status code.
	 *
	 * @param maritalStatusCode the new marital status code
	 */
	public void setMaritalStatusCode(LookupDto maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	/**
	 * Gets the religious affiliation code.
	 *
	 * @return the religious affiliation code
	 */
	public LookupDto getReligiousAffiliationCode() {
		return religiousAffiliationCode;
	}

	/**
	 * Sets the religious affiliation code.
	 *
	 * @param religiousAffiliationCode the new religious affiliation code
	 */
	public void setReligiousAffiliationCode(LookupDto religiousAffiliationCode) {
		this.religiousAffiliationCode = religiousAffiliationCode;
	}

	/**
	 * Gets the race code.
	 *
	 * @return the race code
	 */
	public LookupDto getRaceCode() {
		return raceCode;
	}

	/**
	 * Sets the race code.
	 *
	 * @param raceCode the new race code
	 */
	public void setRaceCode(LookupDto raceCode) {
		this.raceCode = raceCode;
	}

	/**
	 * Gets the ethnic group code.
	 *
	 * @return the ethnic group code
	 */
	public LookupDto getEthnicGroupCode() {
		return ethnicGroupCode;
	}

	/**
	 * Sets the ethnic group code.
	 *
	 * @param ethnicGroupCode the new ethnic group code
	 */
	public void setEthnicGroupCode(LookupDto ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}

	/**
	 * Gets the language code.
	 *
	 * @return the language code
	 */
	public LookupDto getLanguageCode() {
		return languageCode;
	}

	/**
	 * Sets the language code.
	 *
	 * @param languageCode the new language code
	 */
	public void setLanguageCode(LookupDto languageCode) {
		this.languageCode = languageCode;
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
	 * @param medicalRecordNumber the new medical record number
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
	 * @param patientIdNumber the new patient id number
	 */
	public void setPatientIdNumber(String patientIdNumber) {
		this.patientIdNumber = patientIdNumber;
	}


}
