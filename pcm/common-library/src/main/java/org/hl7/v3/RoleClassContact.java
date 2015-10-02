package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassContact")
@XmlEnum
public enum RoleClassContact
{
  CON, 
  ECON, 
  NOK;

  public String value() {
    return name();
  }

  public static RoleClassContact fromValue(String v) {
    return valueOf(v);
  }
}

