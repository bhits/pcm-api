package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassIsSpeciesEntity")
@XmlEnum
public enum RoleClassIsSpeciesEntity
{
  GEN, 
  GRIC;

  public String value() {
    return name();
  }

  public static RoleClassIsSpeciesEntity fromValue(String v) {
    return valueOf(v);
  }
}

