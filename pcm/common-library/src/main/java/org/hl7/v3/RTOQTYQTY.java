package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RTO_QTY_QTY", propOrder={"numerator", "denominator"})
@XmlSeeAlso({RTO.class})
public class RTOQTYQTY extends QTY
{

  @XmlElement(required=true)
  protected QTY numerator;

  @XmlElement(required=true)
  protected QTY denominator;

  public QTY getNumerator()
  {
    return this.numerator;
  }

  public void setNumerator(QTY value)
  {
    this.numerator = value;
  }

  public QTY getDenominator()
  {
    return this.denominator;
  }

  public void setDenominator(QTY value)
  {
    this.denominator = value;
  }
}

