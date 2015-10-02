package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.ObservationRange", propOrder={"realmCode", "typeId", "templateId", "code", "text", "value", "interpretationCode"})
public class POCDMT000040ObservationRange
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected CD code;
  protected ED text;
  protected ANY value;
  protected CE interpretationCode;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected List<String> classCode;

  @XmlAttribute(name="moodCode")
  protected List<String> moodCode;

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

  public CD getCode()
  {
    return this.code;
  }

  public void setCode(CD value)
  {
    this.code = value;
  }

  public ED getText()
  {
    return this.text;
  }

  public void setText(ED value)
  {
    this.text = value;
  }

  public ANY getValue()
  {
    return this.value;
  }

  public void setValue(ANY value)
  {
    this.value = value;
  }

  public CE getInterpretationCode()
  {
    return this.interpretationCode;
  }

  public void setInterpretationCode(CE value)
  {
    this.interpretationCode = value;
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

  public List<String> getMoodCode()
  {
    if (this.moodCode == null) {
      this.moodCode = new ArrayList();
    }
    return this.moodCode;
  }
}

