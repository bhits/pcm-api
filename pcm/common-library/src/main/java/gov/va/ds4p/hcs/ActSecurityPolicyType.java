package gov.va.ds4p.hcs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "actObligationPolicy", "actRefrainPolicy" })
@XmlRootElement(name = "ActSecurityPolicyType")
public class ActSecurityPolicyType {

	@XmlElement(name = "ActObligationPolicy", required = true)
	protected ActObligationPolicy actObligationPolicy;

	@XmlElement(name = "ActRefrainPolicy", required = true)
	protected ActRefrainPolicy actRefrainPolicy;

	public ActObligationPolicy getActObligationPolicy() {
		return this.actObligationPolicy;
	}

	public void setActObligationPolicy(ActObligationPolicy value) {
		this.actObligationPolicy = value;
	}

	public ActRefrainPolicy getActRefrainPolicy() {
		return this.actRefrainPolicy;
	}

	public void setActRefrainPolicy(ActRefrainPolicy value) {
		this.actRefrainPolicy = value;
	}
}
