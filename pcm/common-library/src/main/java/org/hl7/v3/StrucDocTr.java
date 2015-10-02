package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StrucDoc.Tr", propOrder={"thOrTd"})
public class StrucDocTr
{

  @XmlElements({@javax.xml.bind.annotation.XmlElement(name="th", type=StrucDocTh.class), @javax.xml.bind.annotation.XmlElement(name="td", type=StrucDocTd.class)})
  protected List<Object> thOrTd;

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

  public List<Object> getThOrTd()
  {
    if (this.thOrTd == null) {
      this.thOrTd = new ArrayList();
    }
    return this.thOrTd;
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

