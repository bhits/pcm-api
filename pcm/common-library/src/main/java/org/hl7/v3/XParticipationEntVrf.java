package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ParticipationEntVrf")
@XmlEnum
public enum XParticipationEntVrf
{
  VRF, 
  ENT;

  public String value() {
    return name();
  }

  public static XParticipationEntVrf fromValue(String v) {
    return valueOf(v);
  }
}

