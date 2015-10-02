package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Supply", propOrder={"realmCode", "typeId", "templateId", "id", "code", "text", "statusCode", "effectiveTime", "priorityCode", "repeatNumber", "independentInd", "quantity", "expectedUseTime", "subject", "specimen", "product", "performer", "author", "informant", "participant", "entryRelationship", "reference", "precondition"})
public class POCDMT000040Supply
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected List<II> id;
  protected CD code;
  protected ED text;
  protected CS statusCode;
  protected List<SXCMTS> effectiveTime;
  protected List<CE> priorityCode;
  protected IVLINT repeatNumber;
  protected BL independentInd;
  protected PQ quantity;
  protected IVLTS expectedUseTime;
  protected POCDMT000040Subject subject;
  protected List<POCDMT000040Specimen> specimen;
  protected POCDMT000040Product product;
  protected List<POCDMT000040Performer2> performer;
  protected List<POCDMT000040Author> author;
  protected List<POCDMT000040Informant12> informant;
  protected List<POCDMT000040Participant2> participant;
  protected List<POCDMT000040EntryRelationship> entryRelationship;
  protected List<POCDMT000040Reference> reference;
  protected List<POCDMT000040Precondition> precondition;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode", required=true)
  protected ActClassSupply classCode;

  @XmlAttribute(name="moodCode", required=true)
  protected XDocumentSubstanceMood moodCode;

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

  public List<SXCMTS> getEffectiveTime()
  {
    if (this.effectiveTime == null) {
      this.effectiveTime = new ArrayList();
    }
    return this.effectiveTime;
  }

  public List<CE> getPriorityCode()
  {
    if (this.priorityCode == null) {
      this.priorityCode = new ArrayList();
    }
    return this.priorityCode;
  }

  public IVLINT getRepeatNumber()
  {
    return this.repeatNumber;
  }

  public void setRepeatNumber(IVLINT value)
  {
    this.repeatNumber = value;
  }

  public BL getIndependentInd()
  {
    return this.independentInd;
  }

  public void setIndependentInd(BL value)
  {
    this.independentInd = value;
  }

  public PQ getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(PQ value)
  {
    this.quantity = value;
  }

  public IVLTS getExpectedUseTime()
  {
    return this.expectedUseTime;
  }

  public void setExpectedUseTime(IVLTS value)
  {
    this.expectedUseTime = value;
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

  public POCDMT000040Product getProduct()
  {
    return this.product;
  }

  public void setProduct(POCDMT000040Product value)
  {
    this.product = value;
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

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public ActClassSupply getClassCode()
  {
    if (this.classCode == null) {
      return ActClassSupply.SPLY;
    }
    return this.classCode;
  }

  public void setClassCode(ActClassSupply value)
  {
    this.classCode = value;
  }

  public XDocumentSubstanceMood getMoodCode()
  {
    return this.moodCode;
  }

  public void setMoodCode(XDocumentSubstanceMood value)
  {
    this.moodCode = value;
  }
}

