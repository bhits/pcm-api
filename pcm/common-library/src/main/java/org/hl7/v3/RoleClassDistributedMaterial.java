package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassDistributedMaterial")
@XmlEnum
public enum RoleClassDistributedMaterial
{
  DST, 
  RET;

  public String value() {
    return name();
  }

  public static RoleClassDistributedMaterial fromValue(String v) {
    return valueOf(v);
  }
}

