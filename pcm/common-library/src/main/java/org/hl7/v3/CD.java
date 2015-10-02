package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="CD", propOrder={"originalText", "qualifier", "translation"})
@XmlSeeAlso({BXITCD.class, SXCMCD.class, CE.class})
public class CD extends ANY
{
  protected ED originalText;
  protected List<CR> qualifier;
  protected List<CD> translation;

  @XmlAttribute(name="code")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String code;

  @XmlAttribute(name="codeSystem")
  protected String codeSystem;

  @XmlAttribute(name="codeSystemName")
  protected String codeSystemName;

  @XmlAttribute(name="codeSystemVersion")
  protected String codeSystemVersion;

  @XmlAttribute(name="displayName")
  protected String displayName;

  public ED getOriginalText()
  {
    return this.originalText;
  }

  public void setOriginalText(ED value)
  {
    this.originalText = value;
  }

  public List<CR> getQualifier()
  {
    if (this.qualifier == null) {
      this.qualifier = new ArrayList();
    }
    return this.qualifier;
  }

  public List<CD> getTranslation()
  {
    if (this.translation == null) {
      this.translation = new ArrayList();
    }
    return this.translation;
  }

  public String getCode()
  {
    return this.code;
  }

  public void setCode(String value)
  {
    this.code = value;
  }

  public String getCodeSystem()
  {
    return this.codeSystem;
  }

  public void setCodeSystem(String value)
  {
    this.codeSystem = value;
  }

  public String getCodeSystemName()
  {
    return this.codeSystemName;
  }

  public void setCodeSystemName(String value)
  {
    this.codeSystemName = value;
  }

  public String getCodeSystemVersion()
  {
    return this.codeSystemVersion;
  }

  public void setCodeSystemVersion(String value)
  {
    this.codeSystemVersion = value;
  }

  public String getDisplayName()
  {
    return this.displayName;
  }

  public void setDisplayName(String value)
  {
    this.displayName = value;
  }
}

