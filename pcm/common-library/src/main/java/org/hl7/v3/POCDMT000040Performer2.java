package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Performer2", propOrder={"realmCode", "typeId", "templateId", "time", "modeCode", "assignedEntity"})
public class POCDMT000040Performer2
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected IVLTS time;
  protected CE modeCode;

  @XmlElement(required=true)
  protected POCDMT000040AssignedEntity assignedEntity;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode")
  protected ParticipationPhysicalPerformer typeCode;

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

  public IVLTS getTime()
  {
    return this.time;
  }

  public void setTime(IVLTS value)
  {
    this.time = value;
  }

  public CE getModeCode()
  {
    return this.modeCode;
  }

  public void setModeCode(CE value)
  {
    this.modeCode = value;
  }

  public POCDMT000040AssignedEntity getAssignedEntity()
  {
    return this.assignedEntity;
  }

  public void setAssignedEntity(POCDMT000040AssignedEntity value)
  {
    this.assignedEntity = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public ParticipationPhysicalPerformer getTypeCode()
  {
    if (this.typeCode == null) {
      return ParticipationPhysicalPerformer.PRF;
    }
    return this.typeCode;
  }

  public void setTypeCode(ParticipationPhysicalPerformer value)
  {
    this.typeCode = value;
  }
}

