package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Component1", propOrder={"realmCode", "typeId", "templateId", "encompassingEncounter"})
public class POCDMT000040Component1
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;

  @XmlElement(required=true)
  protected POCDMT000040EncompassingEncounter encompassingEncounter;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode")
  protected ActRelationshipHasComponent typeCode;

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

  public POCDMT000040EncompassingEncounter getEncompassingEncounter()
  {
    return this.encompassingEncounter;
  }

  public void setEncompassingEncounter(POCDMT000040EncompassingEncounter value)
  {
    this.encompassingEncounter = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public ActRelationshipHasComponent getTypeCode()
  {
    if (this.typeCode == null) {
      return ActRelationshipHasComponent.COMP;
    }
    return this.typeCode;
  }

  public void setTypeCode(ActRelationshipHasComponent value)
  {
    this.typeCode = value;
  }
}

