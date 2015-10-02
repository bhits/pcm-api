package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.AssignedAuthor", propOrder={"realmCode", "typeId", "templateId", "id", "code", "addr", "telecom", "assignedPerson", "assignedAuthoringDevice", "representedOrganization"})
public class POCDMT000040AssignedAuthor
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;

  @XmlElement(required=true)
  protected List<II> id;
  protected CE code;
  protected List<AD> addr;
  protected List<TEL> telecom;
  protected POCDMT000040Person assignedPerson;
  protected POCDMT000040AuthoringDevice assignedAuthoringDevice;
  protected POCDMT000040Organization representedOrganization;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected String classCode;

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

  public List<AD> getAddr()
  {
    if (this.addr == null) {
      this.addr = new ArrayList();
    }
    return this.addr;
  }

  public List<TEL> getTelecom()
  {
    if (this.telecom == null) {
      this.telecom = new ArrayList();
    }
    return this.telecom;
  }

  public POCDMT000040Person getAssignedPerson()
  {
    return this.assignedPerson;
  }

  public void setAssignedPerson(POCDMT000040Person value)
  {
    this.assignedPerson = value;
  }

  public POCDMT000040AuthoringDevice getAssignedAuthoringDevice()
  {
    return this.assignedAuthoringDevice;
  }

  public void setAssignedAuthoringDevice(POCDMT000040AuthoringDevice value)
  {
    this.assignedAuthoringDevice = value;
  }

  public POCDMT000040Organization getRepresentedOrganization()
  {
    return this.representedOrganization;
  }

  public void setRepresentedOrganization(POCDMT000040Organization value)
  {
    this.representedOrganization = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public String getClassCode()
  {
    if (this.classCode == null) {
      return "ASSIGNED";
    }
    return this.classCode;
  }

  public void setClassCode(String value)
  {
    this.classCode = value;
  }
}

