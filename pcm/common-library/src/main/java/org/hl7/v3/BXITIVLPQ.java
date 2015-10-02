package org.hl7.v3;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="BXIT_IVL_PQ")
public class BXITIVLPQ extends IVLPQ
{

  @XmlAttribute(name="qty")
  protected BigInteger qty;

  public BigInteger getQty()
  {
    if (this.qty == null) {
      return new BigInteger("1");
    }
    return this.qty;
  }

  public void setQty(BigInteger value)
  {
    this.qty = value;
  }
}

