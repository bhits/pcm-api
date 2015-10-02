package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="MO")
@XmlSeeAlso({IVXBMO.class, SXCMMO.class})
public class MO extends QTY
{

  @XmlAttribute(name="value")
  protected String value;

  @XmlAttribute(name="currency")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String currency;

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public String getCurrency()
  {
    return this.currency;
  }

  public void setCurrency(String value)
  {
    this.currency = value;
  }
}

