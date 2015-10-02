package gov.va.ds4p.policy.reference;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "actInformationSensitivityPolicy" })
@XmlRootElement(name = "ApplicableSensitivityCodes")
public class ApplicableSensitivityCodes {

	@XmlElement(name = "ActInformationSensitivityPolicy", required = true)
	protected List<ActInformationSensitivityPolicy> actInformationSensitivityPolicy;

	public List<ActInformationSensitivityPolicy> getActInformationSensitivityPolicy() {
		if (this.actInformationSensitivityPolicy == null) {
			this.actInformationSensitivityPolicy = new ArrayList();
		}
		return this.actInformationSensitivityPolicy;
	}
}
