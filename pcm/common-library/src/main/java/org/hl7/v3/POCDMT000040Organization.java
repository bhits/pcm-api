package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Organization", propOrder={"realmCode", "typeId", "templateId", "id", "name", "telecom", "addr", "standardIndustryClassCode", "asOrganizationPartOf"})
public class POCDMT000040Organization
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected List<II> id;
  protected List<ON> name;
  protected List<TEL> telecom;
  protected List<AD> addr;
  protected CE standardIndustryClassCode;
  protected POCDMT000040OrganizationPartOf asOrganizationPartOf;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected String classCode;

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

  public List<II> getId()
  {
    if (this.id == null) {
      this.id = new ArrayList();
    }
    return this.id;
  }

  public List<ON> getName()
  {
    if (this.name == null) {
      this.name = new ArrayList();
    }
    return this.name;
  }

  public List<TEL> getTelecom()
  {
    if (this.telecom == null) {
      this.telecom = new ArrayList();
    }
    return this.telecom;
  }

  public List<AD> getAddr()
  {
    if (this.addr == null) {
      this.addr = new ArrayList();
    }
    return this.addr;
  }

  public CE getStandardIndustryClassCode()
  {
    return this.standardIndustryClassCode;
  }

  public void setStandardIndustryClassCode(CE value)
  {
    this.standardIndustryClassCode = value;
  }

  public POCDMT000040OrganizationPartOf getAsOrganizationPartOf()
  {
    return this.asOrganizationPartOf;
  }

  public void setAsOrganizationPartOf(POCDMT000040OrganizationPartOf value)
  {
    this.asOrganizationPartOf = value;
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
      return "ORG";
    }
    return this.classCode;
  }

  public void setClassCode(String value)
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

