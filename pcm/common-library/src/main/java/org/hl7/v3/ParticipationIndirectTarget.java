package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParticipationIndirectTarget")
@XmlEnum
public enum ParticipationIndirectTarget
{
  IND, 
  BEN, 
  COV, 
  HLD, 
  RCT, 
  RCV;

  public String value() {
    return name();
  }

  public static ParticipationIndirectTarget fromValue(String v) {
    return valueOf(v);
  }
}

