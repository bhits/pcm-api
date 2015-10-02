package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="BuildingNumber")
@XmlEnum
public enum BuildingNumber
{
  BNR, 
  BNN, 
  BNS;

  public String value() {
    return name();
  }

  public static BuildingNumber fromValue(String v) {
    return valueOf(v);
  }
}

