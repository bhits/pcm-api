package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="State")
@XmlEnum
public enum State
{
  STATE, 
  NAT;

  public String value() {
    return name();
  }

  public static State fromValue(String v) {
    return valueOf(v);
  }
}

