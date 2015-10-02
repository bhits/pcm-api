package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="IVXB_PQ")
public class IVXBPQ extends PQ
{

  @XmlAttribute(name="inclusive")
  protected Boolean inclusive;

  public boolean isInclusive()
  {
    if (this.inclusive == null) {
      return true;
    }
    return this.inclusive.booleanValue();
  }

  public void setInclusive(Boolean value)
  {
    this.inclusive = value;
  }
}

