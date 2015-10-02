package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "confidentiality" })
@XmlRootElement(name = "ImpliedConfidentiality")
public class ImpliedConfidentiality {

	@XmlElement(name = "Confidentiality", required = true)
	protected Confidentiality confidentiality;

	public Confidentiality getConfidentiality() {
		return this.confidentiality;
	}

	public void setConfidentiality(Confidentiality value) {
		this.confidentiality = value;
	}
}
