package gov.va.ds4p.hcs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "confidentiality",
		"clinicalDocumentPolicyControl" })
@XmlRootElement(name = "InnerPolicyControl")
public class InnerPolicyControl {

	@XmlElement(name = "Confidentiality", required = true)
	protected String confidentiality;

	@XmlElement(name = "ClinicalDocumentPolicyControl", required = true)
	protected ClinicalDocumentPolicyControl clinicalDocumentPolicyControl;

	public String getConfidentiality() {
		return this.confidentiality;
	}

	public void setConfidentiality(String value) {
		this.confidentiality = value;
	}

	public ClinicalDocumentPolicyControl getClinicalDocumentPolicyControl() {
		return this.clinicalDocumentPolicyControl;
	}

	public void setClinicalDocumentPolicyControl(
			ClinicalDocumentPolicyControl value) {
		this.clinicalDocumentPolicyControl = value;
	}
}
