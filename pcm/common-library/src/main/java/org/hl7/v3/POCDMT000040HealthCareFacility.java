package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.HealthCareFacility", propOrder={"realmCode", "typeId", "templateId", "id", "code", "location", "serviceProviderOrganization"})
public class POCDMT000040HealthCareFacility
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected List<II> id;
  protected CE code;
  protected POCDMT000040Place location;
  protected POCDMT000040Organization serviceProviderOrganization;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected RoleClassServiceDeliveryLocation classCode;

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

  public CE getCode()
  {
    return this.code;
  }

  public void setCode(CE value)
  {
    this.code = value;
  }

  public POCDMT000040Place getLocation()
  {
    return this.location;
  }

  public void setLocation(POCDMT000040Place value)
  {
    this.location = value;
  }

  public POCDMT000040Organization getServiceProviderOrganization()
  {
    return this.serviceProviderOrganization;
  }

  public void setServiceProviderOrganization(POCDMT000040Organization value)
  {
    this.serviceProviderOrganization = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public RoleClassServiceDeliveryLocation getClassCode()
  {
    if (this.classCode == null) {
      return RoleClassServiceDeliveryLocation.SDLOC;
    }
    return this.classCode;
  }

  public void setClassCode(RoleClassServiceDeliveryLocation value)
  {
    this.classCode = value;
  }
}

