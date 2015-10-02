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
@XmlType(name="SLIST_PQ", propOrder={"origin", "scale", "digits"})
public class SLISTPQ extends ANY
{

  @XmlElement(required=true)
  protected PQ origin;

  @XmlElement(required=true)
  protected PQ scale;

  @XmlList
  @XmlElement(required=true)
  protected List<BigInteger> digits;

  public PQ getOrigin()
  {
    return this.origin;
  }

  public void setOrigin(PQ value)
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

