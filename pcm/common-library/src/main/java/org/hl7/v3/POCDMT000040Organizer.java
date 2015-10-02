package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Organizer", propOrder={"realmCode", "typeId", "templateId", "id", "code", "statusCode", "effectiveTime", "subject", "specimen", "performer", "author", "informant", "participant", "reference", "precondition", "component"})
public class POCDMT000040Organizer
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected List<II> id;
  protected CD code;

  @XmlElement(required=true)
  protected CS statusCode;
  protected IVLTS effectiveTime;
  protected POCDMT000040Subject subject;
  protected List<POCDMT000040Specimen> specimen;
  protected List<POCDMT000040Performer2> performer;
  protected List<POCDMT000040Author> author;
  protected List<POCDMT000040Informant12> informant;
  protected List<POCDMT000040Participant2> participant;
  protected List<POCDMT000040Reference> reference;
  protected List<POCDMT000040Precondition> precondition;
  protected List<POCDMT000040Component4> component;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode", required=true)
  protected XActClassDocumentEntryOrganizer classCode;

  @XmlAttribute(name="moodCode", required=true)
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

  public List<II> getId()
  {
    if (this.id == null) {
      this.id = new ArrayList();
    }
    return this.id;
  }

  public CD getCode()
  {
    return this.code;
  }

  public void setCode(CD value)
  {
    this.code = value;
  }

  public CS getStatusCode()
  {
    return this.statusCode;
  }

  public void setStatusCode(CS value)
  {
    this.statusCode = value;
  }

  public IVLTS getEffectiveTime()
  {
    return this.effectiveTime;
  }

  public void setEffectiveTime(IVLTS value)
  {
    this.effectiveTime = value;
  }

  public POCDMT000040Subject getSubject()
  {
    return this.subject;
  }

  public void setSubject(POCDMT000040Subject value)
  {
    this.subject = value;
  }

  public List<POCDMT000040Specimen> getSpecimen()
  {
    if (this.specimen == null) {
      this.specimen = new ArrayList();
    }
    return this.specimen;
  }

  public List<POCDMT000040Performer2> getPerformer()
  {
    if (this.performer == null) {
      this.performer = new ArrayList();
    }
    return this.performer;
  }

  public List<POCDMT000040Author> getAuthor()
  {
    if (this.author == null) {
      this.author = new ArrayList();
    }
    return this.author;
  }

  public List<POCDMT000040Informant12> getInformant()
  {
    if (this.informant == null) {
      this.informant = new ArrayList();
    }
    return this.informant;
  }

  public List<POCDMT000040Participant2> getParticipant()
  {
    if (this.participant == null) {
      this.participant = new ArrayList();
    }
    return this.participant;
  }

  public List<POCDMT000040Reference> getReference()
  {
    if (this.reference == null) {
      this.reference = new ArrayList();
    }
    return this.reference;
  }

  public List<POCDMT000040Precondition> getPrecondition()
  {
    if (this.precondition == null) {
      this.precondition = new ArrayList();
    }
    return this.precondition;
  }

  public List<POCDMT000040Component4> getComponent()
  {
    if (this.component == null) {
      this.component = new ArrayList();
    }
    return this.component;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public XActClassDocumentEntryOrganizer getClassCode()
  {
    return this.classCode;
  }

  public void setClassCode(XActClassDocumentEntryOrganizer value)
  {
    this.classCode = value;
  }

  public List<String> getMoodCode()
  {
    if (this.moodCode == null) {
      this.moodCode = new ArrayList();
    }
    return this.moodCode;
  }
}

