package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParticipationVerifier")
@XmlEnum
public enum ParticipationVerifier
{
  VRF, 
  AUTHEN, 
  LA;

  public String value() {
    return name();
  }

  public static ParticipationVerifier fromValue(String v) {
    return valueOf(v);
  }
}

