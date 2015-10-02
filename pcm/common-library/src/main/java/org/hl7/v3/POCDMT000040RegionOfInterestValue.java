package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="POCD_MT000040.RegionOfInterest.value")
public class POCDMT000040RegionOfInterestValue extends INT
{

  @XmlAttribute(name="unsorted")
  protected Boolean unsorted;

  public boolean isUnsorted()
  {
    if (this.unsorted == null) {
      return false;
    }
    return this.unsorted.booleanValue();
  }

  public void setUnsorted(Boolean value)
  {
    this.unsorted = value;
  }
}

