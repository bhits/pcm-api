 package gov.va.ds4p.policy.reference;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlAttribute;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlSchemaType;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="")
 @XmlRootElement(name="manufacturingModelName")
 public class ManufacturingModelName
 {
 
   @XmlAttribute(name="displayName")
   @XmlSchemaType(name="anySimpleType")
   protected String displayName;
 
   @XmlAttribute(name="code")
   @XmlSchemaType(name="anySimpleType")
   protected String code;
 
   public String getDisplayName()
   {
     return this.displayName;
   }
 
   public void setDisplayName(String value)
   {
     this.displayName = value;
   }
 
   public String getCode()
   {
     return this.code;
   }
 
   public void setCode(String value)
   {
     this.code = value;
   }
 }

