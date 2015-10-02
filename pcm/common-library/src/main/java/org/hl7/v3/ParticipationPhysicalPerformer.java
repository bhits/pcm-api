package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParticipationPhysicalPerformer")
@XmlEnum
public enum ParticipationPhysicalPerformer
{
  PRF, 
  DIST, 
  PPRF, 
  SPRF;

  public String value() {
    return name();
  }

  public static ParticipationPhysicalPerformer fromValue(String v) {
    return valueOf(v);
  }
}

