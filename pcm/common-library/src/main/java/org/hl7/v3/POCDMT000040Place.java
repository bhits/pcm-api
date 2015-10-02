package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Place", propOrder={"realmCode", "typeId", "templateId", "name", "addr"})
public class POCDMT000040Place
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected EN name;
  protected AD addr;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected EntityClassPlace classCode;

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

  public EN getName()
  {
    return this.name;
  }

  public void setName(EN value)
  {
    this.name = value;
  }

  public AD getAddr()
  {
    return this.addr;
  }

  public void setAddr(AD value)
  {
    this.addr = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public EntityClassPlace getClassCode()
  {
    if (this.classCode == null) {
      return EntityClassPlace.PLC;
    }
    return this.classCode;
  }

  public void setClassCode(EntityClassPlace value)
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

