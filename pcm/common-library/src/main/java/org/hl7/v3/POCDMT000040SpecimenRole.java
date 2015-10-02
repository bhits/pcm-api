package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.SpecimenRole", propOrder={"realmCode", "typeId", "templateId", "id", "specimenPlayingEntity"})
public class POCDMT000040SpecimenRole
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected List<II> id;
  protected POCDMT000040PlayingEntity specimenPlayingEntity;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected RoleClassSpecimen classCode;

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

  public List<II> getId()
  {
    if (this.id == null) {
      this.id = new ArrayList();
    }
    return this.id;
  }

  public POCDMT000040PlayingEntity getSpecimenPlayingEntity()
  {
    return this.specimenPlayingEntity;
  }

  public void setSpecimenPlayingEntity(POCDMT000040PlayingEntity value)
  {
    this.specimenPlayingEntity = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public RoleClassSpecimen getClassCode()
  {
    if (this.classCode == null) {
      return RoleClassSpecimen.SPEC;
    }
    return this.classCode;
  }

  public void setClassCode(RoleClassSpecimen value)
  {
    this.classCode = value;
  }
}

