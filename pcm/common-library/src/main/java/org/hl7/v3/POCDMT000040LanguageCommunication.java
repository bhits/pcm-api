package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.LanguageCommunication", propOrder={"realmCode", "typeId", "templateId", "languageCode", "modeCode", "proficiencyLevelCode", "preferenceInd"})
public class POCDMT000040LanguageCommunication
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected CS languageCode;
  protected CE modeCode;
  protected CE proficiencyLevelCode;
  protected BL preferenceInd;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

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

  public CS getLanguageCode()
  {
    return this.languageCode;
  }

  public void setLanguageCode(CS value)
  {
    this.languageCode = value;
  }

  public CE getModeCode()
  {
    return this.modeCode;
  }

  public void setModeCode(CE value)
  {
    this.modeCode = value;
  }

  public CE getProficiencyLevelCode()
  {
    return this.proficiencyLevelCode;
  }

  public void setProficiencyLevelCode(CE value)
  {
    this.proficiencyLevelCode = value;
  }

  public BL getPreferenceInd()
  {
    return this.preferenceInd;
  }

  public void setPreferenceInd(BL value)
  {
    this.preferenceInd = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }
}

