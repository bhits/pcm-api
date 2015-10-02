 package gov.va.ds4p.policy.reference;
 
 import java.util.ArrayList;
 import java.util.List;
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlAttribute;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="", propOrder={"organizationTaggingRules", "organizationConsentPolicyInfo"})
 @XmlRootElement(name="OrganizationPolicy")
 public class OrganizationPolicy
 {
 
   @XmlElement(name="OrganizationTaggingRules")
   protected List<OrganizationTaggingRules> organizationTaggingRules;
   protected OrganizationConsentPolicyInfo organizationConsentPolicyInfo;
 
   @XmlAttribute(name="orgName")
   protected String orgName;
 
   @XmlAttribute(name="homeCommunityId")
   protected String homeCommunityId;
 
   @XmlAttribute(name="usPrivacyLaw")
   protected String usPrivacyLaw;
 
   public List<OrganizationTaggingRules> getOrganizationTaggingRules()
   {
     if (this.organizationTaggingRules == null) {
       this.organizationTaggingRules = new ArrayList();
     }
     return this.organizationTaggingRules;
   }
 
   public OrganizationConsentPolicyInfo getOrganizationConsentPolicyInfo()
   {
     return this.organizationConsentPolicyInfo;
   }
 
   public void setOrganizationConsentPolicyInfo(OrganizationConsentPolicyInfo value)
   {
     this.organizationConsentPolicyInfo = value;
   }
 
   public String getOrgName()
   {
     return this.orgName;
   }
 
   public void setOrgName(String value)
   {
     this.orgName = value;
   }
 
   public String getHomeCommunityId()
   {
     return this.homeCommunityId;
   }
 
   public void setHomeCommunityId(String value)
   {
     this.homeCommunityId = value;
   }
 
   public String getUsPrivacyLaw()
   {
     return this.usPrivacyLaw;
   }
 
   public void setUsPrivacyLaw(String value)
   {
     this.usPrivacyLaw = value;
   }
 }
