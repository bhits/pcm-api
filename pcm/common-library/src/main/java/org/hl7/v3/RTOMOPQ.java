package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RTO_MO_PQ", propOrder={"numerator", "denominator"})
public class RTOMOPQ extends QTY
{

  @XmlElement(required=true)
  protected MO numerator;

  @XmlElement(required=true)
  protected PQ denominator;

  public MO getNumerator()
  {
    return this.numerator;
  }

  public void setNumerator(MO value)
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

