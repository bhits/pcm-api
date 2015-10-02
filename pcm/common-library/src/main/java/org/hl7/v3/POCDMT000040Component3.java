package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Component3", propOrder={"realmCode", "typeId", "templateId", "section"})
public class POCDMT000040Component3
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;

  @XmlElement(required=true)
  protected POCDMT000040Section section;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode")
  protected ActRelationshipHasComponent typeCode;

  @XmlAttribute(name="contextConductionInd")
  protected Boolean contextConductionInd;

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

  public POCDMT000040Section getSection()
  {
    return this.section;
  }

  public void setSection(POCDMT000040Section value)
  {
    this.section = value;
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

  public boolean isContextConductionInd()
  {
    if (this.contextConductionInd == null) {
      return true;
    }
    return this.contextConductionInd.booleanValue();
  }

  public void setContextConductionInd(Boolean value)
  {
    this.contextConductionInd = value;
  }
}

