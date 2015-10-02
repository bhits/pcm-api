package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Location", propOrder={"realmCode", "typeId", "templateId", "healthCareFacility"})
public class POCDMT000040Location
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;

  @XmlElement(required=true)
  protected POCDMT000040HealthCareFacility healthCareFacility;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode")
  protected ParticipationTargetLocation typeCode;

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

  public POCDMT000040HealthCareFacility getHealthCareFacility()
  {
    return this.healthCareFacility;
  }

  public void setHealthCareFacility(POCDMT000040HealthCareFacility value)
  {
    this.healthCareFacility = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public ParticipationTargetLocation getTypeCode()
  {
    if (this.typeCode == null) {
      return ParticipationTargetLocation.LOC;
    }
    return this.typeCode;
  }

  public void setTypeCode(ParticipationTargetLocation value)
  {
    this.typeCode = value;
  }
}

