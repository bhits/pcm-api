package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "addr", "assignedPerson" })
@XmlRootElement(name = "assignedAuthor")
public class AssignedAuthor {

	@XmlElement(required = true)
	protected Addr addr;

	@XmlElement(required = true)
	protected AssignedPerson assignedPerson;

	@XmlAttribute(name = "id")
	protected String id;

	@XmlAttribute(name = "root")
	protected String root;

	public Addr getAddr() {
		return this.addr;
	}

	public void setAddr(Addr value) {
		this.addr = value;
	}

	public AssignedPerson getAssignedPerson() {
		return this.assignedPerson;
	}

	public void setAssignedPerson(AssignedPerson value) {
		this.assignedPerson = value;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getRoot() {
		return this.root;
	}

	public void setRoot(String value) {
		this.root = value;
	}
 }
