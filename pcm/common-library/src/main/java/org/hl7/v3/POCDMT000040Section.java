package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.Section", propOrder={"realmCode", "typeId", "templateId", "id", "code", "title", "text", "confidentialityCode", "languageCode", "subject", "author", "informant", "entry", "component"})
public class POCDMT000040Section
{
  protected List<CS> realmCode;
  protected POCDMT000040InfrastructureRootTypeId typeId;
  protected List<II> templateId;
  protected II id;
  protected CE code;
  protected ST title;
  protected StrucDocText text;
  protected CE confidentialityCode;
  protected CS languageCode;
  protected POCDMT000040Subject subject;
  protected List<POCDMT000040Author> author;
  protected List<POCDMT000040Informant12> informant;
  protected List<POCDMT000040Entry> entry;
  protected List<POCDMT000040Component5> component;

  @XmlAttribute(name="ID1")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name="ID")
  protected String id1;

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

  public StrucDocText getText()
  {
    return this.text;
  }

  public void setText(StrucDocText value)
  {
    this.text = value;
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

  public POCDMT000040Subject getSubject()
  {
    return this.subject;
  }

  public void setSubject(POCDMT000040Subject value)
  {
    this.subject = value;
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

  public List<POCDMT000040Entry> getEntry()
  {
    if (this.entry == null) {
      this.entry = new ArrayList();
    }
    return this.entry;
  }

  public List<POCDMT000040Component5> getComponent()
  {
    if (this.component == null) {
      this.component = new ArrayList();
    }
    return this.component;
  }

  public String getID1()
  {
    return this.id1;
  }

  public void setID1(String value)
  {
    this.id1 = value;
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

