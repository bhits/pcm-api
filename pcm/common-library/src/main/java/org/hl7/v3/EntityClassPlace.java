package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="EntityClassPlace")
@XmlEnum
public enum EntityClassPlace
{
  PLC, 
  CITY, 
  COUNTRY, 
  COUNTY, 
  PROVINCE;

  public String value() {
    return name();
  }

  public static EntityClassPlace fromValue(String v) {
    return valueOf(v);
  }
}

