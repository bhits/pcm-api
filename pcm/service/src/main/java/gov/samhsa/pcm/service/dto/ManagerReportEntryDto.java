package gov.samhsa.pcm.service.dto;

import java.util.Date;

public class ManagerReportEntryDto {

	private Date accountCreatedDate;
	private String createdByStaffAdministrator;
	private String staffAdministratorLocation;
	private String patientUsername;
	private String patientMrn;
	private String patientLastName;
	private String patientFirstName;
	private String patientGender;
	private int numOfUnsignedConsents;
	private int numOfSignedConsents;
	private int numOfRevokedConsents;
	private int numOfEffectiveConsents;
	private int numOfExpiredConsents;
	private Date patientBirthDay;
	private Date activeAccountDateTime;
	private Date consentInitialDateTime;

	public Date getAccountCreatedDate() {
		return accountCreatedDate;
	}

	public String getCreatedByStaffAdministrator() {
		return createdByStaffAdministrator;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public Date getPatientBirthDay() {
		return patientBirthDay;
	}

	public Date getActiveAccountDateTime() {
		return activeAccountDateTime;
	}

	public Date getConsentInitialDateTime() {
		return consentInitialDateTime;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public String getPatientMrn() {
		return patientMrn;
	}

	public String getPatientUsername() {
		return patientUsername;
	}

	public int getNumOfSignedConsents() {
		return numOfSignedConsents;
	}

	public int getNumOfEffectiveConsents() {
		return numOfEffectiveConsents;
	}

	public int getNumOfRevokedConsents() {
		return numOfRevokedConsents;
	}

	public int getNumOfExpiredConsents() {
		return numOfExpiredConsents;
	}

	public int getNumOfUnsignedConsents() {
		return numOfUnsignedConsents;
	}

	public String getStaffAdministratorLocation() {
		return staffAdministratorLocation;
	}

	public void setAccountCreatedDate(Date accountCreatedDate) {
		this.accountCreatedDate = accountCreatedDate;
	}

	public void setCreatedByStaffAdministrator(
			String createdByStaffAdministrator) {
		this.createdByStaffAdministrator = createdByStaffAdministrator;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public void setPatientBirthDay(Date patientBirthDay) {
		this.patientBirthDay = patientBirthDay;
	}

	public void setActiveAccountDateTime(Date activeAccountDateTime) {
		this.activeAccountDateTime = activeAccountDateTime;
	}

	public void setConsentInitialDateTime(Date consentInitialDateTime) {
		this.consentInitialDateTime = consentInitialDateTime;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public void setPatientMrn(String patientMrn) {
		this.patientMrn = patientMrn;
	}

	public void setPatientUsername(String patientUsername) {
		this.patientUsername = patientUsername;
	}

	public void setNumOfSignedConsents(int numOfSignedConsents) {
		this.numOfSignedConsents = numOfSignedConsents;
	}

	public void setNumOfEffectiveConsents(int numOfEffectiveConsents) {
		this.numOfEffectiveConsents = numOfEffectiveConsents;
	}

	public void setNumOfRevokedConsents(int numOfRevokedConsents) {
		this.numOfRevokedConsents = numOfRevokedConsents;
	}

	public void setNumOfExpiredConsents(int numOfExpiredConsents) {
		this.numOfExpiredConsents = numOfExpiredConsents;
	}

	public void setNumOfUnsignedConsents(int numOfUnsignedConsents) {
		this.numOfUnsignedConsents = numOfUnsignedConsents;
	}

	public void setStaffAdministratorLocation(String staffAdministratorLocation) {
		this.staffAdministratorLocation = staffAdministratorLocation;
	}
}
