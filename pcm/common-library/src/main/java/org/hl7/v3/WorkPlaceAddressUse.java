package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="WorkPlaceAddressUse")
@XmlEnum
public enum WorkPlaceAddressUse
{
  WP, 
  DIR, 
  PUB;

  public String value() {
    return name();
  }

  public static WorkPlaceAddressUse fromValue(String v) {
    return valueOf(v);
  }
}

