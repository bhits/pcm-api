package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Subject", propOrder={"realmCode", "typeId", "templateId", "awarenessCode", "relatedSubject"})
public class POCDMT000040Subject
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected CE awarenessCode;

  @XmlElement(required=true)
  protected POCDMT000040RelatedSubject relatedSubject;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode")
  protected ParticipationTargetSubject typeCode;

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

  public CE getAwarenessCode()
  {
    return this.awarenessCode;
  }

  public void setAwarenessCode(CE value)
  {
    this.awarenessCode = value;
  }

  public POCDMT000040RelatedSubject getRelatedSubject()
  {
    return this.relatedSubject;
  }

  public void setRelatedSubject(POCDMT000040RelatedSubject value)
  {
    this.relatedSubject = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public ParticipationTargetSubject getTypeCode()
  {
    if (this.typeCode == null) {
      return ParticipationTargetSubject.SBJ;
    }
    return this.typeCode;
  }

  public void setTypeCode(ParticipationTargetSubject value)
  {
    this.typeCode = value;
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

