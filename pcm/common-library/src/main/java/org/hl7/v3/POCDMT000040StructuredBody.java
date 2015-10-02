package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.StructuredBody", propOrder={"realmCode", "typeId", "templateId", "confidentialityCode", "languageCode", "component"})
public class POCDMT000040StructuredBody
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected CE confidentialityCode;
  protected CS languageCode;

  @XmlElement(required=true)
  protected List<POCDMT000040Component3> component;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected List<String> classCode;

  @XmlAttribute(name="moodCode")
  protected List<String> moodCode;

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

  public CE getConfidentialityCode()
  {
    return this.confidentialityCode;
  }

  public void setConfidentialityCode(CE value)
  {
    this.confidentialityCode = value;
  }

  public CS getLanguageCode()
  {
    return this.languageCode;
  }

  public void setLanguageCode(CS value)
  {
    this.languageCode = value;
  }

  public List<POCDMT000040Component3> getComponent()
  {
    if (this.component == null) {
      this.component = new ArrayList();
    }
    return this.component;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public List<String> getClassCode()
  {
    if (this.classCode == null) {
      this.classCode = new ArrayList();
    }
    return this.classCode;
  }

  public List<String> getMoodCode()
  {
    if (this.moodCode == null) {
      this.moodCode = new ArrayList();
    }
    return this.moodCode;
  }
}

