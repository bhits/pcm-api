package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="StreetName")
@XmlEnum
public enum StreetName
{
  STR, 
  STB, 
  STTYP;

  public String value() {
    return name();
  }

  public static StreetName fromValue(String v) {
    return valueOf(v);
  }
}

