package gov.va.ds4p.hcs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "securedMedicalDocument" })
@XmlRootElement(name = "CompositeSecuredDocumentSet")
public class CompositeSecuredDocumentSet {

	@XmlElement(name = "SecuredMedicalDocument", required = true)
	protected List<SecuredMedicalDocument> securedMedicalDocument;

	public List<SecuredMedicalDocument> getSecuredMedicalDocument() {
		if (this.securedMedicalDocument == null) {
			this.securedMedicalDocument = new ArrayList();
		}
		return this.securedMedicalDocument;
	}
 }
