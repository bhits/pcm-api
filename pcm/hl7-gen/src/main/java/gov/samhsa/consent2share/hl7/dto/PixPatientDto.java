package gov.samhsa.consent2share.hl7.dto;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PixPatientDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class PixPatientDto {
	private String messageId;
	private String idExtension;
	private String idRoot;
	private String idAssigningAuthorityName;
	private String ssn;
	private String patientFirstName;
	private String patientLastName;
	private String patientEmailHome;
	private String telecomUse;
	private String telecomValue;
	private String administrativeGenderCode;
	private String birthTimeValue;
	private String addrStreetAddressLine;
	private String addrCity;
	private String addrState;
	private String addrPostalCode;
	private String maritalStatusCode;

	public PixPatientDto() {
		super();
		this.messageId = UUID.randomUUID().toString();
		this.telecomUse = "H";
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getIdExtension() {
		return idExtension;
	}

	public void setIdExtension(String idExtension) {
		this.idExtension = idExtension;
	}

	public String getIdRoot() {
		return idRoot;
	}

	public void setIdRoot(String idRoot) {
		this.idRoot = idRoot;
	}

	public String getIdAssigningAuthorityName() {
		return idAssigningAuthorityName;
	}

	public void setIdAssigningAuthorityName(String idAssigningAuthorityName) {
		this.idAssigningAuthorityName = idAssigningAuthorityName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getPatientEmailHome() {
		return patientEmailHome;
	}

	public void setPatientEmailHome(String patientEmailHome) {
		this.patientEmailHome = patientEmailHome;
	}

	public String getTelecomUse() {
		return telecomUse;
	}

	public void setTelecomUse(String telecomUse) {
		this.telecomUse = telecomUse;
	}

	public String getTelecomValue() {
		return telecomValue;
	}

	public void setTelecomValue(String telecomValue) {
		this.telecomValue = telecomValue;
	}

	public String getAdministrativeGenderCode() {
		return administrativeGenderCode;
	}

	public void setAdministrativeGenderCode(String administrativeGenderCode) {
		this.administrativeGenderCode = administrativeGenderCode;
	}

	public String getBirthTimeValue() {
		return birthTimeValue;
	}

	public void setBirthTimeValue(String birthTimeValue) {
		this.birthTimeValue = birthTimeValue;
	}

	public String getAddrStreetAddressLine() {
		return addrStreetAddressLine;
	}

	public void setAddrStreetAddressLine(String addrStreetAddressLine) {
		this.addrStreetAddressLine = addrStreetAddressLine;
	}

	public String getAddrCity() {
		return addrCity;
	}

	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	public String getAddrState() {
		return addrState;
	}

	public void setAddrState(String addrState) {
		this.addrState = addrState;
	}

	public String getAddrPostalCode() {
		return addrPostalCode;
	}

	public void setAddrPostalCode(String addrPostalCode) {
		this.addrPostalCode = addrPostalCode;
	}

	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}
}
