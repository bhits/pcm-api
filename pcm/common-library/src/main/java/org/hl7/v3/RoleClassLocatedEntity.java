package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassLocatedEntity")
@XmlEnum
public enum RoleClassLocatedEntity
{
  LOCE, 
  STOR;

  public String value() {
    return name();
  }

  public static RoleClassLocatedEntity fromValue(String v) {
    return valueOf(v);
  }
}

