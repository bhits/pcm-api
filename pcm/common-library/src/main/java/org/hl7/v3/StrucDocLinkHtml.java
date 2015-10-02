package org.hl7.v3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StrucDoc.LinkHtml", propOrder={"content"})
public class StrucDocLinkHtml
{

  @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="footnoteRef", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="footnote", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class)})
  @XmlMixed
  protected List<Serializable> content;

  @XmlAttribute(name="name")
  protected String name;

  @XmlAttribute(name="href")
  protected String href;

  @XmlAttribute(name="rel")
  protected String rel;

  @XmlAttribute(name="rev")
  protected String rev;

  @XmlAttribute(name="title")
  protected String title;

  @XmlAttribute(name="ID")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlID
  @XmlSchemaType(name="ID")
  protected String id;

  @XmlAttribute(name="language")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  @XmlSchemaType(name="NMTOKEN")
  protected String language;

  @XmlAttribute(name="styleCode")
  @XmlSchemaType(name="NMTOKENS")
  protected List<String> styleCode;

  public List<Serializable> getContent()
  {
    if (this.content == null) {
      this.content = new ArrayList();
    }
    return this.content;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String value)
  {
    this.name = value;
  }

  public String getHref()
  {
    return this.href;
  }

  public void setHref(String value)
  {
    this.href = value;
  }

  public String getRel()
  {
    return this.rel;
  }

  public void setRel(String value)
  {
    this.rel = value;
  }

  public String getRev()
  {
    return this.rev;
  }

  public void setRev(String value)
  {
    this.rev = value;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setTitle(String value)
  {
    this.title = value;
  }

  public String getID()
  {
    return this.id;
  }

  public void setID(String value)
  {
    this.id = value;
  }

  public String getLanguage()
  {
    return this.language;
  }

  public void setLanguage(String value)
  {
    this.language = value;
  }

  public List<String> getStyleCode()
  {
    if (this.styleCode == null) {
      this.styleCode = new ArrayList();
    }
    return this.styleCode;
  }
}

