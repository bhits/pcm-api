package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Observation", propOrder={"realmCode", "typeId", "templateId", "id", "code", "derivationExpr", "text", "statusCode", "effectiveTime", "priorityCode", "repeatNumber", "languageCode", "value", "interpretationCode", "methodCode", "targetSiteCode", "subject", "specimen", "performer", "author", "informant", "participant", "entryRelationship", "reference", "precondition", "referenceRange"})
public class POCDMT000040Observation
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected List<II> id;

  @XmlElement(required=true)
  protected CD code;
  protected ST derivationExpr;
  protected ED text;
  protected CS statusCode;
  protected IVLTS effectiveTime;
  protected CE priorityCode;
  protected IVLINT repeatNumber;
  protected CS languageCode;
  protected List<ANY> value;
  protected List<CE> interpretationCode;
  protected List<CE> methodCode;
  protected List<CD> targetSiteCode;
  protected POCDMT000040Subject subject;
  protected List<POCDMT000040Specimen> specimen;
  protected List<POCDMT000040Performer2> performer;
  protected List<POCDMT000040Author> author;
  protected List<POCDMT000040Informant12> informant;
  protected List<POCDMT000040Participant2> participant;
  protected List<POCDMT000040EntryRelationship> entryRelationship;
  protected List<POCDMT000040Reference> reference;
  protected List<POCDMT000040Precondition> precondition;
  protected List<POCDMT000040ReferenceRange> referenceRange;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode", required=true)
  protected List<String> classCode;

  @XmlAttribute(name="moodCode", required=true)
  protected XActMoodDocumentObservation moodCode;

  @XmlAttribute(name="negationInd")
  protected Boolean negationInd;

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

  public ST getDerivationExpr()
  {
    return this.derivationExpr;
  }

  public void setDerivationExpr(ST value)
  {
    this.derivationExpr = value;
  }

  public ED getText()
  {
    return this.text;
  }

  public void setText(ED value)
  {
    this.text = value;
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

  public CE getPriorityCode()
  {
    return this.priorityCode;
  }

  public void setPriorityCode(CE value)
  {
    this.priorityCode = value;
  }

  public IVLINT getRepeatNumber()
  {
    return this.repeatNumber;
  }

  public void setRepeatNumber(IVLINT value)
  {
    this.repeatNumber = value;
  }

  public CS getLanguageCode()
  {
    return this.languageCode;
  }

  public void setLanguageCode(CS value)
  {
    this.languageCode = value;
  }

  public List<ANY> getValue()
  {
    if (this.value == null) {
      this.value = new ArrayList();
    }
    return this.value;
  }

  public List<CE> getInterpretationCode()
  {
    if (this.interpretationCode == null) {
      this.interpretationCode = new ArrayList();
    }
    return this.interpretationCode;
  }

  public List<CE> getMethodCode()
  {
    if (this.methodCode == null) {
      this.methodCode = new ArrayList();
    }
    return this.methodCode;
  }

  public List<CD> getTargetSiteCode()
  {
    if (this.targetSiteCode == null) {
      this.targetSiteCode = new ArrayList();
    }
    return this.targetSiteCode;
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

  public List<POCDMT000040EntryRelationship> getEntryRelationship()
  {
    if (this.entryRelationship == null) {
      this.entryRelationship = new ArrayList();
    }
    return this.entryRelationship;
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

  public List<POCDMT000040ReferenceRange> getReferenceRange()
  {
    if (this.referenceRange == null) {
      this.referenceRange = new ArrayList();
    }
    return this.referenceRange;
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

  public XActMoodDocumentObservation getMoodCode()
  {
    return this.moodCode;
  }

  public void setMoodCode(XActMoodDocumentObservation value)
  {
    this.moodCode = value;
  }

  public Boolean isNegationInd()
  {
    return this.negationInd;
  }

  public void setNegationInd(Boolean value)
  {
    this.negationInd = value;
  }
}

