package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="UVP_TS")
public class UVPTS extends TS
{

  @XmlAttribute(name="probability")
  protected Double probability;

  public Double getProbability()
  {
    return this.probability;
  }

  public void setProbability(Double value)
  {
    this.probability = value;
  }
}

