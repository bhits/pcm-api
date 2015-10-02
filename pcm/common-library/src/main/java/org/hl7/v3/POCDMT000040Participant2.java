package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Participant2", propOrder={"realmCode", "typeId", "templateId", "time", "awarenessCode", "participantRole"})
public class POCDMT000040Participant2
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected IVLTS time;
  protected CE awarenessCode;

  @XmlElement(required=true)
  protected POCDMT000040ParticipantRole participantRole;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode", required=true)
  protected List<String> typeCode;

  @XmlAttribute(name="contextControlCode")
  protected String contextControlCode;

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

  public CE getAwarenessCode()
  {
    return this.awarenessCode;
  }

  public void setAwarenessCode(CE value)
  {
    this.awarenessCode = value;
  }

  public POCDMT000040ParticipantRole getParticipantRole()
  {
    return this.participantRole;
  }

  public void setParticipantRole(POCDMT000040ParticipantRole value)
  {
    this.participantRole = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public List<String> getTypeCode()
  {
    if (this.typeCode == null) {
      this.typeCode = new ArrayList();
    }
    return this.typeCode;
  }

  public String getContextControlCode()
  {
    if (this.contextControlCode == null) {
      return "OP";
    }
    return this.contextControlCode;
  }

  public void setContextControlCode(String value)
  {
    this.contextControlCode = value;
  }
}

