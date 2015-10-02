package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RTO_PQ_PQ", propOrder={"numerator", "denominator"})
public class RTOPQPQ extends QTY
{

  @XmlElement(required=true)
  protected PQ numerator;

  @XmlElement(required=true)
  protected PQ denominator;

  public PQ getNumerator()
  {
    return this.numerator;
  }

  public void setNumerator(PQ value)
  {
    this.numerator = value;
  }

  public PQ getDenominator()
  {
    return this.denominator;
  }

  public void setDenominator(PQ value)
  {
    this.denominator = value;
  }
}

