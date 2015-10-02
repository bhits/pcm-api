package gov.va.ds4p.policy.reference;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "actUSPrivacyLaw" })
@XmlRootElement(name = "ApplicableUSLaws")
public class ApplicableUSLaws {

	@XmlElement(name = "ActUSPrivacyLaw", required = true)
	protected List<ActUSPrivacyLaw> actUSPrivacyLaw;

	public List<ActUSPrivacyLaw> getActUSPrivacyLaw() {
		if (this.actUSPrivacyLaw == null) {
			this.actUSPrivacyLaw = new ArrayList();
		}
		return this.actUSPrivacyLaw;
	}
 }
