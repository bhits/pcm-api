package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "actInformationSensitivityPolicy" })
@XmlRootElement(name = "PatientSensitivityConstraint")
public class PatientSensitivityConstraint {

	@XmlElement(name = "ActInformationSensitivityPolicy", required = true)
	protected ActInformationSensitivityPolicy actInformationSensitivityPolicy;

	public ActInformationSensitivityPolicy getActInformationSensitivityPolicy() {
		return this.actInformationSensitivityPolicy;
	}

	public void setActInformationSensitivityPolicy(
			ActInformationSensitivityPolicy value) {
		this.actInformationSensitivityPolicy = value;
	}
 }
