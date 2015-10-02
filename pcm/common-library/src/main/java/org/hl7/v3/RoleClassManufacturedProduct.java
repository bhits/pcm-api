package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassManufacturedProduct")
@XmlEnum
public enum RoleClassManufacturedProduct
{
  MANU, 
  THER;

  public String value() {
    return name();
  }

  public static RoleClassManufacturedProduct fromValue(String v) {
    return valueOf(v);
  }
}

