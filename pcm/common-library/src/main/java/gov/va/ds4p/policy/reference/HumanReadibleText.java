package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "displayText", "imageURL" })
@XmlRootElement(name = "humanReadibleText")
public class HumanReadibleText {

	@XmlElement(required = true)
	protected String displayText;

	@XmlElement(required = true)
	protected String imageURL;

	public String getDisplayText() {
		return this.displayText;
	}

	public void setDisplayText(String value) {
		this.displayText = value;
	}

	public String getImageURL() {
		return this.imageURL;
	}

	public void setImageURL(String value) {
		this.imageURL = value;
	}
 }
