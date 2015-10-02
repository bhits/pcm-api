package gov.va.ds4p.policy.reference;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "clinicalTaggingRule" })
@XmlRootElement(name = "ImmunizationsSensitivityRules")
public class ImmunizationsSensitivityRules {

	@XmlElement(name = "ClinicalTaggingRule")
	protected List<ClinicalTaggingRule> clinicalTaggingRule;

	public List<ClinicalTaggingRule> getClinicalTaggingRule() {
		if (this.clinicalTaggingRule == null) {
			this.clinicalTaggingRule = new ArrayList();
		}
		return this.clinicalTaggingRule;
	}
 }
