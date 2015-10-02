package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="EntityNameSearchUse")
@XmlEnum
public enum EntityNameSearchUse
{
  SRCH, 
  PHON, 
  SNDX;

  public String value() {
    return name();
  }

  public static EntityNameSearchUse fromValue(String v) {
    return valueOf(v);
  }
}

