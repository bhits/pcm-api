package gov.va.ds4p.policy.reference;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "refrainPolicy" })
@XmlRootElement(name = "ApplicableRefrainPolicies")
public class ApplicableRefrainPolicies {

	@XmlElement(name = "RefrainPolicy", required = true)
	protected List<RefrainPolicy> refrainPolicy;

	public List<RefrainPolicy> getRefrainPolicy() {
		if (this.refrainPolicy == null) {
			this.refrainPolicy = new ArrayList();
		}
		return this.refrainPolicy;
	}
 }


