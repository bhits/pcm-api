package gov.va.ds4p.policy.reference;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "confidentiality" })
@XmlRootElement(name = "ApplicableConfidentialities")
public class ApplicableConfidentialities {

	@XmlElement(name = "Confidentiality", required = true)
	protected List<Confidentiality> confidentiality;

	public List<Confidentiality> getConfidentiality() {
		if (this.confidentiality == null) {
			this.confidentiality = new ArrayList();
		}
		return this.confidentiality;
	}
 }
