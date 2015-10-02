package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassSpecimen")
@XmlEnum
public enum RoleClassSpecimen
{
  SPEC, 
  ALQT, 
  ISLT;

  public String value() {
    return name();
  }

  public static RoleClassSpecimen fromValue(String v) {
    return valueOf(v);
  }
}

