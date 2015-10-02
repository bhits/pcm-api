package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Other")
@XmlEnum
public enum Other
{
  OTH, 
  NINF, 
  PINF;

  public String value() {
    return name();
  }

  public static Other fromValue(String v) {
    return valueOf(v);
  }
}

