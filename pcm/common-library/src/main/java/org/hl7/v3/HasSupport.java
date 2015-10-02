package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="hasSupport")
@XmlEnum
public enum HasSupport
{
  SPRT, 
  SPRTBND;

  public String value() {
    return name();
  }

  public static HasSupport fromValue(String v) {
    return valueOf(v);
  }
}

