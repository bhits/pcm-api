package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParticipationAncillary")
@XmlEnum
public enum ParticipationAncillary
{
  ADM, 
  ATND, 
  CALLBCK, 
  CON, 
  DIS, 
  ESC, 
  REF;

  public String value() {
    return name();
  }

  public static ParticipationAncillary fromValue(String v) {
    return valueOf(v);
  }
}

