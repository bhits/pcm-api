package org.hl7.v3;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="INT")
@XmlSeeAlso({POCDMT000040RegionOfInterestValue.class, IVXBINT.class, SXCMINT.class})
public class INT extends QTY
{

  @XmlAttribute(name="value")
  protected BigInteger value;

  public BigInteger getValue()
  {
    return this.value;
  }

  public void setValue(BigInteger value)
  {
    this.value = value;
  }
}

