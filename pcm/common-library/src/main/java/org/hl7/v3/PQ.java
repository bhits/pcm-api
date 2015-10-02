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
@XmlType(name="PQ", propOrder={"translation"})
@XmlSeeAlso({HXITPQ.class, IVXBPQ.class, SXCMPQ.class, PPDPQ.class})
public class PQ extends QTY
{
  protected List<PQR> translation;

  @XmlAttribute(name="value")
  protected String value;

  @XmlAttribute(name="unit")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String unit;

  public List<PQR> getTranslation()
  {
    if (this.translation == null) {
      this.translation = new ArrayList();
    }
    return this.translation;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public String getUnit()
  {
    if (this.unit == null) {
      return "1";
    }
    return this.unit;
  }

  public void setUnit(String value)
  {
    this.unit = value;
  }
}

