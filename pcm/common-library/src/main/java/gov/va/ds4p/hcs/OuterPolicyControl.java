package gov.va.ds4p.hcs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "actSecurityPolicyType", "innerPolicyControl" })
@XmlRootElement(name = "OuterPolicyControl")
public class OuterPolicyControl {

	@XmlElement(name = "ActSecurityPolicyType", required = true)
	protected ActSecurityPolicyType actSecurityPolicyType;

	@XmlElement(name = "InnerPolicyControl", required = true)
	protected InnerPolicyControl innerPolicyControl;

	public ActSecurityPolicyType getActSecurityPolicyType() {
		return this.actSecurityPolicyType;
	}

	public void setActSecurityPolicyType(ActSecurityPolicyType value) {
		this.actSecurityPolicyType = value;
	}

	public InnerPolicyControl getInnerPolicyControl() {
		return this.innerPolicyControl;
	}

	public void setInnerPolicyControl(InnerPolicyControl value) {
		this.innerPolicyControl = value;
	}
}
