package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Reference", propOrder={"realmCode", "typeId", "templateId", "seperatableInd", "externalAct", "externalObservation", "externalProcedure", "externalDocument"})
public class POCDMT000040Reference
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected BL seperatableInd;
  protected POCDMT000040ExternalAct externalAct;
  protected POCDMT000040ExternalObservation externalObservation;
  protected POCDMT000040ExternalProcedure externalProcedure;
  protected POCDMT000040ExternalDocument externalDocument;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode", required=true)
  protected XActRelationshipExternalReference typeCode;

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

  public BL getSeperatableInd()
  {
    return this.seperatableInd;
  }

  public void setSeperatableInd(BL value)
  {
    this.seperatableInd = value;
  }

  public POCDMT000040ExternalAct getExternalAct()
  {
    return this.externalAct;
  }

  public void setExternalAct(POCDMT000040ExternalAct value)
  {
    this.externalAct = value;
  }

  public POCDMT000040ExternalObservation getExternalObservation()
  {
    return this.externalObservation;
  }

  public void setExternalObservation(POCDMT000040ExternalObservation value)
  {
    this.externalObservation = value;
  }

  public POCDMT000040ExternalProcedure getExternalProcedure()
  {
    return this.externalProcedure;
  }

  public void setExternalProcedure(POCDMT000040ExternalProcedure value)
  {
    this.externalProcedure = value;
  }

  public POCDMT000040ExternalDocument getExternalDocument()
  {
    return this.externalDocument;
  }

  public void setExternalDocument(POCDMT000040ExternalDocument value)
  {
    this.externalDocument = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public XActRelationshipExternalReference getTypeCode()
  {
    return this.typeCode;
  }

  public void setTypeCode(XActRelationshipExternalReference value)
  {
    this.typeCode = value;
  }
}

