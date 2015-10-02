package org.hl7.v3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StrucDoc.Td", propOrder={"content"})
public class StrucDocTd
{

  @XmlElementRefs({@javax.xml.bind.annotation.XmlElementRef(name="paragraph", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="br", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="content", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="sup", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="list", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="footnoteRef", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="sub", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="linkHtml", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="renderMultiMedia", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class), @javax.xml.bind.annotation.XmlElementRef(name="footnote", namespace="urn:hl7-org:v3", type=javax.xml.bind.JAXBElement.class)})
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

  @XmlAttribute(name="abbr")
  protected String abbr;

  @XmlAttribute(name="axis")
  protected String axis;

  @XmlAttribute(name="headers")
  @XmlIDREF
  @XmlSchemaType(name="IDREFS")
  protected List<Object> headers;

  @XmlAttribute(name="scope")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String scope;

  @XmlAttribute(name="rowspan")
  protected String rowspan;

  @XmlAttribute(name="colspan")
  protected String colspan;

  @XmlAttribute(name="align")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String align;

  @XmlAttribute(name="char")
  protected String _char;

  @XmlAttribute(name="charoff")
  protected String charoff;

  @XmlAttribute(name="valign")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String valign;

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

  public String getAbbr()
  {
    return this.abbr;
  }

  public void setAbbr(String value)
  {
    this.abbr = value;
  }

  public String getAxis()
  {
    return this.axis;
  }

  public void setAxis(String value)
  {
    this.axis = value;
  }

  public List<Object> getHeaders()
  {
    if (this.headers == null) {
      this.headers = new ArrayList();
    }
    return this.headers;
  }

  public String getScope()
  {
    return this.scope;
  }

  public void setScope(String value)
  {
    this.scope = value;
  }

  public String getRowspan()
  {
    if (this.rowspan == null) {
      return "1";
    }
    return this.rowspan;
  }

  public void setRowspan(String value)
  {
    this.rowspan = value;
  }

  public String getColspan()
  {
    if (this.colspan == null) {
      return "1";
    }
    return this.colspan;
  }

  public void setColspan(String value)
  {
    this.colspan = value;
  }

  public String getAlign()
  {
    return this.align;
  }

  public void setAlign(String value)
  {
    this.align = value;
  }

  public String getChar()
  {
    return this._char;
  }

  public void setChar(String value)
  {
    this._char = value;
  }

  public String getCharoff()
  {
    return this.charoff;
  }

  public void setCharoff(String value)
  {
    this.charoff = value;
  }

  public String getValign()
  {
    return this.valign;
  }

  public void setValign(String value)
  {
    this.valign = value;
  }
}

