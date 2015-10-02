package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="HomeAddressUse")
@XmlEnum
public enum HomeAddressUse
{
  H, 
  HP, 
  HV;

  public String value() {
    return name();
  }

  public static HomeAddressUse fromValue(String v) {
    return valueOf(v);
  }
}

