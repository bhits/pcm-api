package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActMoodIntent")
@XmlEnum
public enum ActMoodIntent
{
  INT, 
  APT, 
  ARQ, 
  PRMS, 
  PRP, 
  RQO, 
  SLOT;

  public String value() {
    return name();
  }

  public static ActMoodIntent fromValue(String v) {
    return valueOf(v);
  }
}

