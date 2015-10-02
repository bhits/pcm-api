package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="EntityClassDevice")
@XmlEnum
public enum EntityClassDevice
{
  DEV, 
  CER, 
  MODDV;

  public String value() {
    return name();
  }

  public static EntityClassDevice fromValue(String v) {
    return valueOf(v);
  }
}

