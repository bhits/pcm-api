package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassEmployee")
@XmlEnum
public enum RoleClassEmployee
{
  EMP, 
  MIL;

  public String value() {
    return name();
  }

  public static RoleClassEmployee fromValue(String v) {
    return valueOf(v);
  }
}

