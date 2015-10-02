package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "given", "family" })
@XmlRootElement(name = "name")
public class Name {

	@XmlElement(required = true)
	protected String given;

	@XmlElement(required = true)
	protected String family;

	public String getGiven() {
		return this.given;
	}

	public void setGiven(String value) {
		this.given = value;
	}

	public String getFamily() {
		return this.family;
	}

	public void setFamily(String value) {
		this.family = value;
	}
}
