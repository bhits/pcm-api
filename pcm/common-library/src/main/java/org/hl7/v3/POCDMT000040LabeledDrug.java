package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.LabeledDrug", propOrder={"realmCode", "typeId", "templateId", "code", "name"})
public class POCDMT000040LabeledDrug
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected CE code;
  protected EN name;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected String classCode;

  @XmlAttribute(name="determinerCode")
  protected EntityDeterminerDetermined determinerCode;

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

  public CE getCode()
  {
    return this.code;
  }

  public void setCode(CE value)
  {
    this.code = value;
  }

  public EN getName()
  {
    return this.name;
  }

  public void setName(EN value)
  {
    this.name = value;
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
      return "MMAT";
    }
    return this.classCode;
  }

  public void setClassCode(String value)
  {
    this.classCode = value;
  }

  public EntityDeterminerDetermined getDeterminerCode()
  {
    if (this.determinerCode == null) {
      return EntityDeterminerDetermined.KIND;
    }
    return this.determinerCode;
  }

  public void setDeterminerCode(EntityDeterminerDetermined value)
  {
    this.determinerCode = value;
  }
}

