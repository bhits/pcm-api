package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParticipationTargetLocation")
@XmlEnum
public enum ParticipationTargetLocation
{
  LOC, 
  DST, 
  ELOC, 
  ORG, 
  RML, 
  VIA;

  public String value() {
    return name();
  }

  public static ParticipationTargetLocation fromValue(String v) {
    return valueOf(v);
  }
}

