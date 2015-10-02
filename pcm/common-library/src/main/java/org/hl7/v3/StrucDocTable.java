package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StrucDoc.Table", propOrder={"caption", "col", "colgroup", "thead", "tfoot", "tbody"})
public class StrucDocTable
{
  protected StrucDocCaption caption;
  protected List<StrucDocCol> col;
  protected List<StrucDocColgroup> colgroup;
  protected StrucDocThead thead;
  protected StrucDocTfoot tfoot;

  @XmlElement(required=true)
  protected List<StrucDocTbody> tbody;

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

  @XmlAttribute(name="summary")
  protected String summary;

  @XmlAttribute(name="width")
  protected String width;

  @XmlAttribute(name="border")
  protected String border;

  @XmlAttribute(name="frame")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String frame;

  @XmlAttribute(name="rules")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String rules;

  @XmlAttribute(name="cellspacing")
  protected String cellspacing;

  @XmlAttribute(name="cellpadding")
  protected String cellpadding;

  public StrucDocCaption getCaption()
  {
    return this.caption;
  }

  public void setCaption(StrucDocCaption value)
  {
    this.caption = value;
  }

  public List<StrucDocCol> getCol()
  {
    if (this.col == null) {
      this.col = new ArrayList();
    }
    return this.col;
  }

  public List<StrucDocColgroup> getColgroup()
  {
    if (this.colgroup == null) {
      this.colgroup = new ArrayList();
    }
    return this.colgroup;
  }

  public StrucDocThead getThead()
  {
    return this.thead;
  }

  public void setThead(StrucDocThead value)
  {
    this.thead = value;
  }

  public StrucDocTfoot getTfoot()
  {
    return this.tfoot;
  }

  public void setTfoot(StrucDocTfoot value)
  {
    this.tfoot = value;
  }

  public List<StrucDocTbody> getTbody()
  {
    if (this.tbody == null) {
      this.tbody = new ArrayList();
    }
    return this.tbody;
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

  public String getSummary()
  {
    return this.summary;
  }

  public void setSummary(String value)
  {
    this.summary = value;
  }

  public String getWidth()
  {
    return this.width;
  }

  public void setWidth(String value)
  {
    this.width = value;
  }

  public String getBorder()
  {
    return this.border;
  }

  public void setBorder(String value)
  {
    this.border = value;
  }

  public String getFrame()
  {
    return this.frame;
  }

  public void setFrame(String value)
  {
    this.frame = value;
  }

  public String getRules()
  {
    return this.rules;
  }

  public void setRules(String value)
  {
    this.rules = value;
  }

  public String getCellspacing()
  {
    return this.cellspacing;
  }

  public void setCellspacing(String value)
  {
    this.cellspacing = value;
  }

  public String getCellpadding()
  {
    return this.cellpadding;
  }

  public void setCellpadding(String value)
  {
    this.cellpadding = value;
  }
}

