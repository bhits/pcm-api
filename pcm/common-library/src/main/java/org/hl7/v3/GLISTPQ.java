package org.hl7.v3;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="GLIST_PQ", propOrder={"head", "increment"})
public class GLISTPQ extends ANY
{

  @XmlElement(required=true)
  protected PQ head;

  @XmlElement(required=true)
  protected PQ increment;

  @XmlAttribute(name="period")
  protected BigInteger period;

  @XmlAttribute(name="denominator")
  protected BigInteger denominator;

  public PQ getHead()
  {
    return this.head;
  }

  public void setHead(PQ value)
  {
    this.head = value;
  }

  public PQ getIncrement()
  {
    return this.increment;
  }

  public void setIncrement(PQ value)
  {
    this.increment = value;
  }

  public BigInteger getPeriod()
  {
    return this.period;
  }

  public void setPeriod(BigInteger value)
  {
    this.period = value;
  }

  public BigInteger getDenominator()
  {
    return this.denominator;
  }

  public void setDenominator(BigInteger value)
  {
    this.denominator = value;
  }
}

