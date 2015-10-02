package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_EncounterParticipant")
@XmlEnum
public enum XEncounterParticipant
{
  ADM, 
  ATND, 
  CON, 
  DIS, 
  REF;

  public String value() {
    return name();
  }

  public static XEncounterParticipant fromValue(String v) {
    return valueOf(v);
  }
}

