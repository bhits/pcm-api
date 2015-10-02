package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.AssignedCustodian", propOrder={"realmCode", "typeId", "templateId", "representedCustodianOrganization"})
public class POCDMT000040AssignedCustodian
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;

  @XmlElement(required=true)
  protected POCDMT000040CustodianOrganization representedCustodianOrganization;

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

  public POCDMT000040CustodianOrganization getRepresentedCustodianOrganization()
  {
    return this.representedCustodianOrganization;
  }

  public void setRepresentedCustodianOrganization(POCDMT000040CustodianOrganization value)
  {
    this.representedCustodianOrganization = value;
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

