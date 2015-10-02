package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="EntityClassContainer")
@XmlEnum
public enum EntityClassContainer
{
  CONT, 
  HOLD;

  public String value() {
    return name();
  }

  public static EntityClassContainer fromValue(String v) {
    return valueOf(v);
  }
}

