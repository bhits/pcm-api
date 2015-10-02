package gov.va.ds4p.hcs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "hl7ConceptCode" })
@XmlRootElement(name = "ActInformationSensitivityPrivacyPolicyType")
public class ActInformationSensitivityPrivacyPolicyType {

	@XmlElement(name = "HL7ConceptCode", required = true)
	protected String hl7ConceptCode;

	public String getHL7ConceptCode() {
		return this.hl7ConceptCode;
	}

	public void setHL7ConceptCode(String value) {
		this.hl7ConceptCode = value;
	}
}
