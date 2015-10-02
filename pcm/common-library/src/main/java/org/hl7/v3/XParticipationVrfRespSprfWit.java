package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ParticipationVrfRespSprfWit")
@XmlEnum
public enum XParticipationVrfRespSprfWit
{
  VRF, 
  RESP, 
  SPRF, 
  WIT;

  public String value() {
    return name();
  }

  public static XParticipationVrfRespSprfWit fromValue(String v) {
    return valueOf(v);
  }
}

