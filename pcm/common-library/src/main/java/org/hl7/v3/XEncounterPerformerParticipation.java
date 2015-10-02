package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_EncounterPerformerParticipation")
@XmlEnum
public enum XEncounterPerformerParticipation
{
  PRF, 
  CON, 
  SPRF;

  public String value() {
    return name();
  }

  public static XEncounterPerformerParticipation fromValue(String v) {
    return valueOf(v);
  }
}

