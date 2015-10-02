package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.InformationRecipient", propOrder={"realmCode", "typeId", "templateId", "intendedRecipient"})
public class POCDMT000040InformationRecipient
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;

  @XmlElement(required=true)
  protected POCDMT000040IntendedRecipient intendedRecipient;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode")
  protected XInformationRecipient typeCode;

  public List<CS> getRealmCode()
  {
    if (this.realmCode == null) {
      this.realmCode = new ArrayList();
    }
    return this.realmCode;
  }

  public POCDMT000040InfrastructureRootTypeId getTypeId()
  {
    return this.typeId;
  }

  public void setTypeId(POCDMT000040InfrastructureRootTypeId value)
  {
    this.typeId = value;
  }

  public List<II> getTemplateId()
  {
    if (this.templateId == null) {
      this.templateId = new ArrayList();
    }
    return this.templateId;
  }

  public POCDMT000040IntendedRecipient getIntendedRecipient()
  {
    return this.intendedRecipient;
  }

  public void setIntendedRecipient(POCDMT000040IntendedRecipient value)
  {
    this.intendedRecipient = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public XInformationRecipient getTypeCode()
  {
    if (this.typeCode == null) {
      return XInformationRecipient.PRCP;
    }
    return this.typeCode;
  }

  public void setTypeCode(XInformationRecipient value)
  {
    this.typeCode = value;
  }
}

