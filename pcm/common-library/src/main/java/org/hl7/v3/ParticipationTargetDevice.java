package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParticipationTargetDevice")
@XmlEnum
public enum ParticipationTargetDevice
{
  DEV, 
  NRD, 
  RDV;

  public String value() {
    return name();
  }

  public static ParticipationTargetDevice fromValue(String v) {
    return valueOf(v);
  }
}

