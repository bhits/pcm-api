 package gov.va.ds4p.policy.reference;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlAttribute;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="")
 @XmlRootElement(name="XspaPatientObligation")
 public class XspaPatientObligation
 {
 
   @XmlAttribute(name="name")
   protected String name;
 
   @XmlAttribute(name="sensitivity")
   protected String sensitivity;
 
   @XmlAttribute(name="impliedAction")
   protected String impliedAction;
 
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String value)
   {
     this.name = value;
   }
 
   public String getSensitivity()
   {
     return this.sensitivity;
   }
 
   public void setSensitivity(String value)
   {
     this.sensitivity = value;
   }
 
   public String getImpliedAction()
   {
     return this.impliedAction;
   }
 
   public void setImpliedAction(String value)
   {
     this.impliedAction = value;
   }
 }
