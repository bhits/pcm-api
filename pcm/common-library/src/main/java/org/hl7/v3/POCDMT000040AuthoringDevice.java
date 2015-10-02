package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.AuthoringDevice", propOrder={"realmCode", "typeId", "templateId", "code", "manufacturerModelName", "softwareName", "asMaintainedEntity"})
public class POCDMT000040AuthoringDevice
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected CE code;
  protected SC manufacturerModelName;
  protected SC softwareName;
  protected List<POCDMT000040MaintainedEntity> asMaintainedEntity;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected EntityClassDevice classCode;

  @XmlAttribute(name="determinerCode")
  protected String determinerCode;

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

  public CE getCode()
  {
    return this.code;
  }

  public void setCode(CE value)
  {
    this.code = value;
  }

  public SC getManufacturerModelName()
  {
    return this.manufacturerModelName;
  }

  public void setManufacturerModelName(SC value)
  {
    this.manufacturerModelName = value;
  }

  public SC getSoftwareName()
  {
    return this.softwareName;
  }

  public void setSoftwareName(SC value)
  {
    this.softwareName = value;
  }

  public List<POCDMT000040MaintainedEntity> getAsMaintainedEntity()
  {
    if (this.asMaintainedEntity == null) {
      this.asMaintainedEntity = new ArrayList();
    }
    return this.asMaintainedEntity;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public EntityClassDevice getClassCode()
  {
    if (this.classCode == null) {
      return EntityClassDevice.DEV;
    }
    return this.classCode;
  }

  public void setClassCode(EntityClassDevice value)
  {
    this.classCode = value;
  }

  public String getDeterminerCode()
  {
    if (this.determinerCode == null) {
      return "INSTANCE";
    }
    return this.determinerCode;
  }

  public void setDeterminerCode(String value)
  {
    this.determinerCode = value;
  }
}

