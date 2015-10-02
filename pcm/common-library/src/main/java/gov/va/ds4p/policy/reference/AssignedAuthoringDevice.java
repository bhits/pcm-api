 package gov.va.ds4p.policy.reference;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlRootElement;
 import javax.xml.bind.annotation.XmlType;
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="", propOrder={"manufacturingModelName", "softwareName", "patientAuthor"})
 @XmlRootElement(name="assignedAuthoringDevice")
 public class AssignedAuthoringDevice
 {
 
   @XmlElement(required=true)
   protected ManufacturingModelName manufacturingModelName;
 
   @XmlElement(required=true)
   protected SoftwareName softwareName;
   protected boolean patientAuthor;
 
   public ManufacturingModelName getManufacturingModelName()
   {
     return this.manufacturingModelName;
   }
 
   public void setManufacturingModelName(ManufacturingModelName value)
   {
     this.manufacturingModelName = value;
   }
 
   public SoftwareName getSoftwareName()
   {
     return this.softwareName;
   }
 
   public void setSoftwareName(SoftwareName value)
   {
     this.softwareName = value;
   }
 
   public boolean isPatientAuthor()
   {
     return this.patientAuthor;
   }
 
   public void setPatientAuthor(boolean value)
   {
     this.patientAuthor = value;
   }
 }

