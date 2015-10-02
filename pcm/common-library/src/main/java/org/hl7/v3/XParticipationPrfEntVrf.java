package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ParticipationPrfEntVrf")
@XmlEnum
public enum XParticipationPrfEntVrf
{
  PRF, 
  VRF, 
  ENT;

  public String value() {
    return name();
  }

  public static XParticipationPrfEntVrf fromValue(String v) {
    return valueOf(v);
  }
}

