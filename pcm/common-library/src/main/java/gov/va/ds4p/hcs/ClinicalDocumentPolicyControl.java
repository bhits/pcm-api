package gov.va.ds4p.hcs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"actHealthInformationPurposeOfUseReason", "actInformationSensitivityPrivacyPolicyType", "actPolicyType", "aesEncryptedDocumentPayload"})
@XmlRootElement(name="ClinicalDocumentPolicyControl")
public class ClinicalDocumentPolicyControl
{

  @XmlElement(name="ActHealthInformationPurposeOfUseReason", required=true)
  protected ActHealthInformationPurposeOfUseReason actHealthInformationPurposeOfUseReason;

  @XmlElement(name="ActInformationSensitivityPrivacyPolicyType", required=true)
  protected ActInformationSensitivityPrivacyPolicyType actInformationSensitivityPrivacyPolicyType;

  @XmlElement(name="ActPolicyType", required=true)
  protected ActPolicyType actPolicyType;

  @XmlElement(name="AESEncryptedDocumentPayload", required=true)
  protected String aesEncryptedDocumentPayload;

  public ActHealthInformationPurposeOfUseReason getActHealthInformationPurposeOfUseReason()
  {
    return this.actHealthInformationPurposeOfUseReason;
  }

  public void setActHealthInformationPurposeOfUseReason(ActHealthInformationPurposeOfUseReason value)
  {
    this.actHealthInformationPurposeOfUseReason = value;
  }

  public ActInformationSensitivityPrivacyPolicyType getActInformationSensitivityPrivacyPolicyType()
  {
    return this.actInformationSensitivityPrivacyPolicyType;
  }

  public void setActInformationSensitivityPrivacyPolicyType(ActInformationSensitivityPrivacyPolicyType value)
  {
    this.actInformationSensitivityPrivacyPolicyType = value;
  }

  public ActPolicyType getActPolicyType()
  {
    return this.actPolicyType;
  }

  public void setActPolicyType(ActPolicyType value)
  {
    this.actPolicyType = value;
  }

  public String getAESEncryptedDocumentPayload()
  {
    return this.aesEncryptedDocumentPayload;
  }

  public void setAESEncryptedDocumentPayload(String value)
  {
    this.aesEncryptedDocumentPayload = value;
  }
}
