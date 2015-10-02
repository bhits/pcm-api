package org.hl7.v3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SLIST_TS", propOrder={"origin", "scale", "digits"})
public class SLISTTS extends ANY
{

  @XmlElement(required=true)
  protected TS origin;

  @XmlElement(required=true)
  protected PQ scale;

  @XmlList
  @XmlElement(required=true)
  protected List<BigInteger> digits;

  public TS getOrigin()
  {
    return this.origin;
  }

  public void setOrigin(TS value)
  {
    this.origin = value;
  }

  public PQ getScale()
  {
    return this.scale;
  }

  public void setScale(PQ value)
  {
    this.scale = value;
  }

  public List<BigInteger> getDigits()
  {
    if (this.digits == null) {
      this.digits = new ArrayList();
    }
    return this.digits;
  }
}

