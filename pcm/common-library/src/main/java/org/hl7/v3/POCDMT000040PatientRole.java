package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.PatientRole", propOrder={"realmCode", "typeId", "templateId", "id", "addr", "telecom", "patient", "providerOrganization"})
public class POCDMT000040PatientRole
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;

  @XmlElement(required=true)
  protected List<II> id;
  protected List<AD> addr;
  protected List<TEL> telecom;
  protected POCDMT000040Patient patient;
  protected POCDMT000040Organization providerOrganization;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected List<String> classCode;

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

  public POCDMT000040Patient getPatient()
  {
    return this.patient;
  }

  public void setPatient(POCDMT000040Patient value)
  {
    this.patient = value;
  }

  public POCDMT000040Organization getProviderOrganization()
  {
    return this.providerOrganization;
  }

  public void setProviderOrganization(POCDMT000040Organization value)
  {
    this.providerOrganization = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public List<String> getClassCode()
  {
    if (this.classCode == null) {
      this.classCode = new ArrayList();
    }
    return this.classCode;
  }
}

