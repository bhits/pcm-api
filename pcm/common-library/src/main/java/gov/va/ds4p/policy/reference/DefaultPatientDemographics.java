package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "patientGender", "patientTelcom",
		"defaultPatientBirthDate", "addr" })
@XmlRootElement(name = "defaultPatientDemographics")
public class DefaultPatientDemographics {

	@XmlElement(required = true)
	protected Name name;

	@XmlElement(required = true)
	protected String patientGender;

	@XmlElement(required = true)
	protected String patientTelcom;

	@XmlElement(required = true)
	@XmlSchemaType(name = "time")
	protected XMLGregorianCalendar defaultPatientBirthDate;

	@XmlElement(required = true)
	protected Addr addr;

	public Name getName() {
		return this.name;
	}

	public void setName(Name value) {
		this.name = value;
	}

	public String getPatientGender() {
		return this.patientGender;
	}

	public void setPatientGender(String value) {
		this.patientGender = value;
	}

	public String getPatientTelcom() {
		return this.patientTelcom;
	}

	public void setPatientTelcom(String value) {
		this.patientTelcom = value;
	}

	public XMLGregorianCalendar getDefaultPatientBirthDate() {
		return this.defaultPatientBirthDate;
	}

	public void setDefaultPatientBirthDate(XMLGregorianCalendar value) {
		this.defaultPatientBirthDate = value;
	}

	public Addr getAddr() {
		return this.addr;
	}

	public void setAddr(Addr value) {
		this.addr = value;
	}
 }
