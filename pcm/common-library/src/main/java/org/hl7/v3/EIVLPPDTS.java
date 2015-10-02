package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="EIVL_PPD_TS", propOrder={"event", "offset"})
public class EIVLPPDTS extends SXCMPPDTS
{
  protected EIVLEvent event;
  protected IVLPPDPQ offset;

  public EIVLEvent getEvent()
  {
    return this.event;
  }

  public void setEvent(EIVLEvent value)
  {
    this.event = value;
  }

  public IVLPPDPQ getOffset()
  {
    return this.offset;
  }

  public void setOffset(IVLPPDPQ value)
  {
    this.offset = value;
  }
}

