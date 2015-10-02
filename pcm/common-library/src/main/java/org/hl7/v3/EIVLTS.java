package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="EIVL_TS", propOrder={"event", "offset"})
public class EIVLTS extends SXCMTS
{
  protected EIVLEvent event;
  protected IVLPQ offset;

  public EIVLEvent getEvent()
  {
    return this.event;
  }

  public void setEvent(EIVLEvent value)
  {
    this.event = value;
  }

  public IVLPQ getOffset()
  {
    return this.offset;
  }

  public void setOffset(IVLPQ value)
  {
    this.offset = value;
  }
}

