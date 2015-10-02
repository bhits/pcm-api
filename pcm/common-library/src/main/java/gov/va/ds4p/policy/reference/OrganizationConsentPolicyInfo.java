 package gov.va.ds4p.policy.reference;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="", propOrder={"addr", "assignedAuthoringDevice", "defaultCustodianInfo", "humanReadibleText", "assignedAuthor", "defaultPatientDemographics"})
 @XmlRootElement(name="organizationConsentPolicyInfo")
 public class OrganizationConsentPolicyInfo
 {
 
   @XmlElement(required=true)
   protected Addr addr;
 
   @XmlElement(required=true)
   protected AssignedAuthoringDevice assignedAuthoringDevice;
 
   @XmlElement(required=true)
   protected DefaultCustodianInfo defaultCustodianInfo;
 
   @XmlElement(required=true)
   protected HumanReadibleText humanReadibleText;
 
   @XmlElement(required=true)
   protected AssignedAuthor assignedAuthor;
 
   @XmlElement(required=true)
   protected DefaultPatientDemographics defaultPatientDemographics;
 
   public Addr getAddr()
   {
     return this.addr;
   }
 
   public void setAddr(Addr value)
   {
     this.addr = value;
   }
 
   public AssignedAuthoringDevice getAssignedAuthoringDevice()
   {
     return this.assignedAuthoringDevice;
   }
 
   public void setAssignedAuthoringDevice(AssignedAuthoringDevice value)
   {
     this.assignedAuthoringDevice = value;
   }
 
   public DefaultCustodianInfo getDefaultCustodianInfo()
   {
     return this.defaultCustodianInfo;
   }
 
   public void setDefaultCustodianInfo(DefaultCustodianInfo value)
   {
     this.defaultCustodianInfo = value;
   }
 
   public HumanReadibleText getHumanReadibleText()
   {
     return this.humanReadibleText;
   }
 
   public void setHumanReadibleText(HumanReadibleText value)
   {
     this.humanReadibleText = value;
   }
 
   public AssignedAuthor getAssignedAuthor()
   {
     return this.assignedAuthor;
   }
 
   public void setAssignedAuthor(AssignedAuthor value)
   {
     this.assignedAuthor = value;
   }
 
   public DefaultPatientDemographics getDefaultPatientDemographics()
   {
     return this.defaultPatientDemographics;
   }
 
   public void setDefaultPatientDemographics(DefaultPatientDemographics value)
   {
     this.defaultPatientDemographics = value;
   }
 }
