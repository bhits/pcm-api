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
@XmlType(name="StrucDoc.Paragraph", propOrder={"content"})
public class StrucDocParagraph
{

  @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="linkHtml", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="renderMultiMedia", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="caption", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="footnoteRef", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="br", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="sup", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="sub", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="content", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="footnote", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class)})
  @XmlMixed
  protected List<Serializable> content;

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

