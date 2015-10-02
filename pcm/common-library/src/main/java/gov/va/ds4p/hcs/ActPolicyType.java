package gov.va.ds4p.hcs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "actPrivacyPolicyType" })
@XmlRootElement(name = "ActPolicyType")
public class ActPolicyType {

	@XmlElement(name = "ActPrivacyPolicyType", required = true)
	protected List<ActPrivacyPolicyType> actPrivacyPolicyType;

	public List<ActPrivacyPolicyType> getActPrivacyPolicyType() {
		if (this.actPrivacyPolicyType == null) {
			this.actPrivacyPolicyType = new ArrayList();
		}
		return this.actPrivacyPolicyType;
	}
}
