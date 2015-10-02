package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "clinicalTaggingRule" })
@XmlRootElement(name = "LabResultsSensitivityRules")
public class LabResultsSensitivityRules {

	@XmlElement(name = "ClinicalTaggingRule", required = true)
	protected ClinicalTaggingRule clinicalTaggingRule;

	public ClinicalTaggingRule getClinicalTaggingRule() {
		return this.clinicalTaggingRule;
	}

	public void setClinicalTaggingRule(ClinicalTaggingRule value) {
		this.clinicalTaggingRule = value;
	}
}
