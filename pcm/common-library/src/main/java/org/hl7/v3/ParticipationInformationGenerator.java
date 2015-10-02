package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParticipationInformationGenerator")
@XmlEnum
public enum ParticipationInformationGenerator
{
  AUT, 
  ENT, 
  INF, 
  WIT;

  public String value() {
    return name();
  }

  public static ParticipationInformationGenerator fromValue(String v) {
    return valueOf(v);
  }
}

