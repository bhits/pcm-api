package gov.samhsa.spirit.wsclient.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

public class PatientDto {
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;

	/** The gender code. */
	@NotEmpty
	private String genderCode;

	/** The birth date. */
//	@Past
//	@DateTimeFormat(pattern = "yyyyMMdd")
	private String birthDate;

	private String emailHome;
   
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String ssnNumber;
    private String maritalStatus;
    private String race;
    private String religion;
    private String language;
    private String homePhone;
    private boolean isNewInExchange;

	/** The global identifier. */
	private String patientId;
	
	/** The Local identifier. */
	private String localPatientId;
	
	public boolean isNewInExchange() {
		return isNewInExchange;
	}

	public void setNewInExchange(boolean isNewInExchange) {
		this.isNewInExchange = isNewInExchange;
	}

	private String country="USA";

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getEmailHome() {
		return emailHome;
	}

	public void setEmailHome(String emailHome) {
		this.emailHome = emailHome;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

	public String getLocalPatientId() {
		return localPatientId;
	}

	public void setLocalPatientId(String localPatientId) {
		this.localPatientId = localPatientId;
	}	
	
	
	

}
