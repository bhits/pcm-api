 package gov.va.ds4p.policy.reference;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="", propOrder={"organizationName", "telcom", "addr"})
 @XmlRootElement(name="defaultCustodianInfo")
 public class DefaultCustodianInfo
 {
 
   @XmlElement(required=true)
   protected String organizationName;
 
   @XmlElement(required=true)
   protected String telcom;
 
   @XmlElement(required=true)
   protected Addr addr;
 
   public String getOrganizationName()
   {
     return this.organizationName;
   }
 
   public void setOrganizationName(String value)
   {
     this.organizationName = value;
   }
 
   public String getTelcom()
   {
     return this.telcom;
   }
 
   public void setTelcom(String value)
   {
     this.telcom = value;
   }
 
   public Addr getAddr()
   {
     return this.addr;
   }
 
   public void setAddr(Addr value)
   {
     this.addr = value;
   }
 }

