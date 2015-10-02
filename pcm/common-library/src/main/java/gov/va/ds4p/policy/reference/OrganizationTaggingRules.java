package gov.va.ds4p.policy.reference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"actReason", "actInformationSensitivityPolicy", "patientSensitivityConstraint", "patientRequestedAction", "actUSPrivacyLaw", "orgObligationPolicyEntry", "orgObligationPolicyDocument", "refrainPolicy"})
@XmlRootElement(name="OrganizationTaggingRules")
public class OrganizationTaggingRules
{

  @XmlElement(name="ActReason", required=true)
  protected ActReason actReason;

  @XmlElement(name="ActInformationSensitivityPolicy", required=true)
  protected ActInformationSensitivityPolicy actInformationSensitivityPolicy;

  @XmlElement(name="PatientSensitivityConstraint", required=true)
  protected PatientSensitivityConstraint patientSensitivityConstraint;

  @XmlElement(name="PatientRequestedAction", required=true)
  protected PatientRequestedAction patientRequestedAction;

  @XmlElement(name="ActUSPrivacyLaw", required=true)
  protected ActUSPrivacyLaw actUSPrivacyLaw;

  @XmlElement(name="OrgObligationPolicyEntry", required=true)
  protected OrgObligationPolicyEntry orgObligationPolicyEntry;

  @XmlElement(name="OrgObligationPolicyDocument", required=true)
  protected OrgObligationPolicyDocument orgObligationPolicyDocument;

  @XmlElement(name="RefrainPolicy", required=true)
  protected RefrainPolicy refrainPolicy;

  public ActReason getActReason()
  {
    return this.actReason;
  }

  public void setActReason(ActReason value)
  {
    this.actReason = value;
  }

  public ActInformationSensitivityPolicy getActInformationSensitivityPolicy()
  {
    return this.actInformationSensitivityPolicy;
  }

  public void setActInformationSensitivityPolicy(ActInformationSensitivityPolicy value)
  {
    this.actInformationSensitivityPolicy = value;
  }

  public PatientSensitivityConstraint getPatientSensitivityConstraint()
  {
    return this.patientSensitivityConstraint;
  }

  public void setPatientSensitivityConstraint(PatientSensitivityConstraint value)
  {
    this.patientSensitivityConstraint = value;
  }

  public PatientRequestedAction getPatientRequestedAction()
  {
    return this.patientRequestedAction;
  }

  public void setPatientRequestedAction(PatientRequestedAction value)
  {
    this.patientRequestedAction = value;
  }

  public ActUSPrivacyLaw getActUSPrivacyLaw()
  {
    return this.actUSPrivacyLaw;
  }

  public void setActUSPrivacyLaw(ActUSPrivacyLaw value)
  {
    this.actUSPrivacyLaw = value;
  }

  public OrgObligationPolicyEntry getOrgObligationPolicyEntry()
  {
    return this.orgObligationPolicyEntry;
  }

  public void setOrgObligationPolicyEntry(OrgObligationPolicyEntry value)
  {
    this.orgObligationPolicyEntry = value;
  }

  public OrgObligationPolicyDocument getOrgObligationPolicyDocument()
  {
    return this.orgObligationPolicyDocument;
  }

  public void setOrgObligationPolicyDocument(OrgObligationPolicyDocument value)
  {
    this.orgObligationPolicyDocument = value;
  }

  public RefrainPolicy getRefrainPolicy()
  {
    return this.refrainPolicy;
  }

  public void setRefrainPolicy(RefrainPolicy value)
  {
    this.refrainPolicy = value;
  }
}
