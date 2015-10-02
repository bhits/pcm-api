package gov.va.ds4p.hcs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "hl7ConceptCode" })
@XmlRootElement(name = "ActRefrainPolicy")
public class ActRefrainPolicy {

	@XmlElement(name = "HL7ConceptCode", required = true)
	protected List<String> hl7ConceptCode;

	public List<String> getHL7ConceptCode() {
		if (this.hl7ConceptCode == null) {
			this.hl7ConceptCode = new ArrayList();
		}
		return this.hl7ConceptCode;
	}
}
