 package gov.va.ds4p.policy.reference;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlAttribute;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="")
 @XmlRootElement(name="ClinicalFact")
 public class ClinicalFact
 {
 
   @XmlAttribute(name="code", required=true)
   protected String code;
 
   @XmlAttribute(name="codeSystem", required=true)
   protected String codeSystem;
 
   @XmlAttribute(name="codeSystemName", required=true)
   protected String codeSystemName;
 
   @XmlAttribute(name="codeSystemVersion", required=true)
   protected String codeSystemVersion;
 
   @XmlAttribute(name="displayName", required=true)
   protected String displayName;
 
   public String getCode()
   {
     return this.code;
   }
 
   public void setCode(String value)
   {
     this.code = value;
   }
 
   public String getCodeSystem()
   {
     return this.codeSystem;
   }
 
   public void setCodeSystem(String value)
   {
     this.codeSystem = value;
   }
 
   public String getCodeSystemName()
   {
     return this.codeSystemName;
   }
 
   public void setCodeSystemName(String value)
   {
     this.codeSystemName = value;
   }
 
   public String getCodeSystemVersion()
   {
     return this.codeSystemVersion;
   }
 
   public void setCodeSystemVersion(String value)
   {
     this.codeSystemVersion = value;
   }
 
   public String getDisplayName()
   {
     return this.displayName;
   }
 
   public void setDisplayName(String value)
   {
     this.displayName = value;
   }
 }
