package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.ClinicalDocument", propOrder={"realmCode", "typeId", "templateId", "id", "code", "title", "effectiveTime", "confidentialityCode", "languageCode", "setId", "versionNumber", "copyTime", "recordTarget", "author", "dataEnterer", "informant", "custodian", "informationRecipient", "legalAuthenticator", "authenticator", "participant", "inFulfillmentOf", "documentationOf", "relatedDocument", "authorization", "componentOf", "component"})
public class POCDMT000040ClinicalDocument
{
  protected List<CS> realmCode;

  @XmlElement(required=true)
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;

  @XmlElement(required=true)
  protected II id;

  @XmlElement(required=true)
  protected CE code;
  protected ST title;

  @XmlElement(required=true)
  protected TS effectiveTime;

  @XmlElement(required=true)
  protected CE confidentialityCode;
  protected CS languageCode;
  protected II setId;
  protected INT versionNumber;
  protected TS copyTime;

  @XmlElement(required=true)
  protected List<POCDMT000040RecordTarget> recordTarget;

  @XmlElement(required=true)
  protected List<POCDMT000040Author> author;
  protected POCDMT000040DataEnterer dataEnterer;
  protected List<POCDMT000040Informant12> informant;

  @XmlElement(required=true)
  protected POCDMT000040Custodian custodian;
  protected List<POCDMT000040InformationRecipient> informationRecipient;
  protected POCDMT000040LegalAuthenticator legalAuthenticator;
  protected List<POCDMT000040Authenticator> authenticator;
  protected List<POCDMT000040Participant1> participant;
  protected List<POCDMT000040InFulfillmentOf> inFulfillmentOf;
  protected List<POCDMT000040DocumentationOf> documentationOf;
  protected List<POCDMT000040RelatedDocument> relatedDocument;
  protected List<POCDMT000040Authorization> authorization;
  protected POCDMT000040Component1 componentOf;

  @XmlElement(required=true)
  protected POCDMT000040Component2 component;

  @XmlAttribute(name="nullFlavor")
  protected List<String> nullFlavor;

  @XmlAttribute(name="classCode")
  protected ActClinicalDocument classCode;

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

  public II getId()
  {
    return this.id;
  }

  public void setId(II value)
  {
    this.id = value;
  }

  public CE getCode()
  {
    return this.code;
  }

  public void setCode(CE value)
  {
    this.code = value;
  }

  public ST getTitle()
  {
    return this.title;
  }

  public void setTitle(ST value)
  {
    this.title = value;
  }

  public TS getEffectiveTime()
  {
    return this.effectiveTime;
  }

  public void setEffectiveTime(TS value)
  {
    this.effectiveTime = value;
  }

  public CE getConfidentialityCode()
  {
    return this.confidentialityCode;
  }

  public void setConfidentialityCode(CE value)
  {
    this.confidentialityCode = value;
  }

  public CS getLanguageCode()
  {
    return this.languageCode;
  }

  public void setLanguageCode(CS value)
  {
    this.languageCode = value;
  }

  public II getSetId()
  {
    return this.setId;
  }

  public void setSetId(II value)
  {
    this.setId = value;
  }

  public INT getVersionNumber()
  {
    return this.versionNumber;
  }

  public void setVersionNumber(INT value)
  {
    this.versionNumber = value;
  }

  public TS getCopyTime()
  {
    return this.copyTime;
  }

  public void setCopyTime(TS value)
  {
    this.copyTime = value;
  }

  public List<POCDMT000040RecordTarget> getRecordTarget()
  {
    if (this.recordTarget == null) {
      this.recordTarget = new ArrayList();
    }
    return this.recordTarget;
  }

  public List<POCDMT000040Author> getAuthor()
  {
    if (this.author == null) {
      this.author = new ArrayList();
    }
    return this.author;
  }

  public POCDMT000040DataEnterer getDataEnterer()
  {
    return this.dataEnterer;
  }

  public void setDataEnterer(POCDMT000040DataEnterer value)
  {
    this.dataEnterer = value;
  }

  public List<POCDMT000040Informant12> getInformant()
  {
    if (this.informant == null) {
      this.informant = new ArrayList();
    }
    return this.informant;
  }

  public POCDMT000040Custodian getCustodian()
  {
    return this.custodian;
  }

  public void setCustodian(POCDMT000040Custodian value)
  {
    this.custodian = value;
  }

  public List<POCDMT000040InformationRecipient> getInformationRecipient()
  {
    if (this.informationRecipient == null) {
      this.informationRecipient = new ArrayList();
    }
    return this.informationRecipient;
  }

  public POCDMT000040LegalAuthenticator getLegalAuthenticator()
  {
    return this.legalAuthenticator;
  }

  public void setLegalAuthenticator(POCDMT000040LegalAuthenticator value)
  {
    this.legalAuthenticator = value;
  }

  public List<POCDMT000040Authenticator> getAuthenticator()
  {
    if (this.authenticator == null) {
      this.authenticator = new ArrayList();
    }
    return this.authenticator;
  }

  public List<POCDMT000040Participant1> getParticipant()
  {
    if (this.participant == null) {
      this.participant = new ArrayList();
    }
    return this.participant;
  }

  public List<POCDMT000040InFulfillmentOf> getInFulfillmentOf()
  {
    if (this.inFulfillmentOf == null) {
      this.inFulfillmentOf = new ArrayList();
    }
    return this.inFulfillmentOf;
  }

  public List<POCDMT000040DocumentationOf> getDocumentationOf()
  {
    if (this.documentationOf == null) {
      this.documentationOf = new ArrayList();
    }
    return this.documentationOf;
  }

  public List<POCDMT000040RelatedDocument> getRelatedDocument()
  {
    if (this.relatedDocument == null) {
      this.relatedDocument = new ArrayList();
    }
    return this.relatedDocument;
  }

  public List<POCDMT000040Authorization> getAuthorization()
  {
    if (this.authorization == null) {
      this.authorization = new ArrayList();
    }
    return this.authorization;
  }

  public POCDMT000040Component1 getComponentOf()
  {
    return this.componentOf;
  }

  public void setComponentOf(POCDMT000040Component1 value)
  {
    this.componentOf = value;
  }

  public POCDMT000040Component2 getComponent()
  {
    return this.component;
  }

  public void setComponent(POCDMT000040Component2 value)
  {
    this.component = value;
  }

  public List<String> getNullFlavor()
  {
    if (this.nullFlavor == null) {
      this.nullFlavor = new ArrayList();
    }
    return this.nullFlavor;
  }

  public ActClinicalDocument getClassCode()
  {
    if (this.classCode == null) {
      return ActClinicalDocument.DOCCLIN;
    }
    return this.classCode;
  }

  public void setClassCode(ActClinicalDocument value)
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

