 package gov.va.ds4p.policy.reference;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="", propOrder={"obligationPolicy"})
 @XmlRootElement(name="OrgObligationPolicyDocument")
 public class OrgObligationPolicyDocument
 {
 
   @XmlElement(name="ObligationPolicy", required=true)
   protected ObligationPolicy obligationPolicy;
 
   public ObligationPolicy getObligationPolicy()
   {
     return this.obligationPolicy;
   }
 
   public void setObligationPolicy(ObligationPolicy value)
   {
     this.obligationPolicy = value;
   }
 }
