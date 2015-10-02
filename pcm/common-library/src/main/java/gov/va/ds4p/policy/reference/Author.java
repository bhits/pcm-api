package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "author")
public class Author {

	@XmlAttribute(name = "templateId")
	protected String templateId;

	@XmlAttribute(name = "time")
	@XmlSchemaType(name = "time")
	protected XMLGregorianCalendar time;

	public String getTemplateId() {
		if (this.templateId == null) {
			return "1.3.5.35.1.4436.7";
		}
		return this.templateId;
	}

	public void setTemplateId(String value) {
		this.templateId = value;
	}

	public XMLGregorianCalendar getTime() {
		return this.time;
	}

	public void setTime(XMLGregorianCalendar value) {
		this.time = value;
	}
}
