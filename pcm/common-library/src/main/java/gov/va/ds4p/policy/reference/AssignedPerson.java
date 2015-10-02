 package gov.va.ds4p.policy.reference;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlAttribute;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="", propOrder={"name"})
 @XmlRootElement(name="assignedPerson")
 public class AssignedPerson
 {
 
   @XmlElement(required=true)
   protected Name name;
 
   @XmlAttribute(name="typeId")
   protected String typeId;
 
   @XmlAttribute(name="classCode")
   protected String classCode;
 
   public Name getName()
   {
     return this.name;
   }
 
   public void setName(Name value)
   {
     this.name = value;
   }
 
   public String getTypeId()
   {
     return this.typeId;
   }
 
   public void setTypeId(String value)
   {
     this.typeId = value;
   }
 
   public String getClassCode()
   {
     if (this.classCode == null) {
       return "PSN";
     }
     return this.classCode;
   }
 
   public void setClassCode(String value)
   {
     this.classCode = value;
   }
 }
