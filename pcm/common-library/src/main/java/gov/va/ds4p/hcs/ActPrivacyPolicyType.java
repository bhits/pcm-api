package gov.va.ds4p.hcs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "policyAttributeValueSet" })
@XmlRootElement(name = "ActPrivacyPolicyType")
public class ActPrivacyPolicyType {

	@XmlElement(name = "PolicyAttributeValueSet", required = true)
	protected String policyAttributeValueSet;

	@XmlAttribute(name = "PolicyType")
	protected String policyType;

	public String getPolicyAttributeValueSet() {
		return this.policyAttributeValueSet;
	}

	public void setPolicyAttributeValueSet(String value) {
		this.policyAttributeValueSet = value;
	}

	public String getPolicyType() {
		return this.policyType;
	}

	public void setPolicyType(String value) {
		this.policyType = value;
	}
}
