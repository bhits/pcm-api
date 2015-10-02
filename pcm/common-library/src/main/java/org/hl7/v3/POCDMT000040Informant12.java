package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Informant12", propOrder={"realmCode", "typeId", "templateId", "assignedEntity", "relatedEntity"})
public class POCDMT000040Informant12
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected POCDMT000040AssignedEntity assignedEntity;
  protected POCDMT000040RelatedEntity relatedEntity;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode")
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

  public POCDMT000040AssignedEntity getAssignedEntity()
  {
    return this.assignedEntity;
  }

  public void setAssignedEntity(POCDMT000040AssignedEntity value)
  {
    this.assignedEntity = value;
  }

  public POCDMT000040RelatedEntity getRelatedEntity()
  {
    return this.relatedEntity;
  }

  public void setRelatedEntity(POCDMT000040RelatedEntity value)
  {
    this.relatedEntity = value;
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

