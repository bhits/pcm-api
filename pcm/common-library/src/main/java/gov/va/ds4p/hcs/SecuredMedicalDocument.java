 package gov.va.ds4p.hcs;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlSchemaType;
 import javax.xml.bind.annotation.XmlType;
 import javax.xml.datatype.XMLGregorianCalendar;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="", propOrder={"documentId", "documentType", "dateDocumentGenerated", "outerPolicyControl"})
 @XmlRootElement(name="SecuredMedicalDocument")
 public class SecuredMedicalDocument
 {
 
   @XmlElement(name="DocumentId", required=true)
   protected String documentId;
 
   @XmlElement(name="DocumentType", required=true)
   protected String documentType;
 
   @XmlElement(name="DateDocumentGenerated", required=true)
   @XmlSchemaType(name="dateTime")
   protected XMLGregorianCalendar dateDocumentGenerated;
 
   @XmlElement(name="OuterPolicyControl", required=true)
   protected OuterPolicyControl outerPolicyControl;
 
   public String getDocumentId()
   {
     return this.documentId;
   }
 
   public void setDocumentId(String value)
   {
     this.documentId = value;
   }
 
   public String getDocumentType()
   {
     return this.documentType;
   }
 
   public void setDocumentType(String value)
   {
     this.documentType = value;
   }
 
   public XMLGregorianCalendar getDateDocumentGenerated()
   {
     return this.dateDocumentGenerated;
   }
 
   public void setDateDocumentGenerated(XMLGregorianCalendar value)
   {
     this.dateDocumentGenerated = value;
   }
 
   public OuterPolicyControl getOuterPolicyControl()
   {
     return this.outerPolicyControl;
   }
 
   public void setOuterPolicyControl(OuterPolicyControl value)
   {
     this.outerPolicyControl = value;
   }
 }
