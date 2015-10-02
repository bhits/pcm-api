package gov.va.ds4p.policy.reference;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "xspaPatientObligation" })
@XmlRootElement(name = "XspaPatientObligations")
public class XspaPatientObligations {

	@XmlElement(name = "XspaPatientObligation", required = true)
	protected List<XspaPatientObligation> xspaPatientObligation;

	public List<XspaPatientObligation> getXspaPatientObligation() {
		if (this.xspaPatientObligation == null) {
			this.xspaPatientObligation = new ArrayList();
		}
		return this.xspaPatientObligation;
	}
 }
