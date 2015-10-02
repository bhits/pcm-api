package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Entry", propOrder={"realmCode", "typeId", "templateId", "act", "encounter", "observation", "observationMedia", "organizer", "procedure", "regionOfInterest", "substanceAdministration", "supply"})
public class POCDMT000040Entry
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected POCDMT000040Act act;
  protected POCDMT000040Encounter encounter;
  protected POCDMT000040Observation observation;
  protected POCDMT000040ObservationMedia observationMedia;
  protected POCDMT000040Organizer organizer;
  protected POCDMT000040Procedure procedure;
  protected POCDMT000040RegionOfInterest regionOfInterest;
  protected POCDMT000040SubstanceAdministration substanceAdministration;
  protected POCDMT000040Supply supply;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="typeCode")
  protected XActRelationshipEntry typeCode;

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

  public POCDMT000040Act getAct()
  {
    return this.act;
  }

  public void setAct(POCDMT000040Act value)
  {
    this.act = value;
  }

  public POCDMT000040Encounter getEncounter()
  {
    return this.encounter;
  }

  public void setEncounter(POCDMT000040Encounter value)
  {
    this.encounter = value;
  }

  public POCDMT000040Observation getObservation()
  {
    return this.observation;
  }

  public void setObservation(POCDMT000040Observation value)
  {
    this.observation = value;
  }

  public POCDMT000040ObservationMedia getObservationMedia()
  {
    return this.observationMedia;
  }

  public void setObservationMedia(POCDMT000040ObservationMedia value)
  {
    this.observationMedia = value;
  }

  public POCDMT000040Organizer getOrganizer()
  {
    return this.organizer;
  }

  public void setOrganizer(POCDMT000040Organizer value)
  {
    this.organizer = value;
  }

  public POCDMT000040Procedure getProcedure()
  {
    return this.procedure;
  }

  public void setProcedure(POCDMT000040Procedure value)
  {
    this.procedure = value;
  }

  public POCDMT000040RegionOfInterest getRegionOfInterest()
  {
    return this.regionOfInterest;
  }

  public void setRegionOfInterest(POCDMT000040RegionOfInterest value)
  {
    this.regionOfInterest = value;
  }

  public POCDMT000040SubstanceAdministration getSubstanceAdministration()
  {
    return this.substanceAdministration;
  }

  public void setSubstanceAdministration(POCDMT000040SubstanceAdministration value)
  {
    this.substanceAdministration = value;
  }

  public POCDMT000040Supply getSupply()
  {
    return this.supply;
  }

  public void setSupply(POCDMT000040Supply value)
  {
    this.supply = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public XActRelationshipEntry getTypeCode()
  {
    if (this.typeCode == null) {
      return XActRelationshipEntry.COMP;
    }
    return this.typeCode;
  }

  public void setTypeCode(XActRelationshipEntry value)
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

