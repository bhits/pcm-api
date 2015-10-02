package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassInactiveIngredient")
@XmlEnum
public enum RoleClassInactiveIngredient
{
  IACT, 
  COLR, 
  FLVR, 
  PRSV, 
  STBL;

  public String value() {
    return name();
  }

  public static RoleClassInactiveIngredient fromValue(String v) {
    return valueOf(v);
  }
}

