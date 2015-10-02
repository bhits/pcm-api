package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassServiceDeliveryLocation")
@XmlEnum
public enum RoleClassServiceDeliveryLocation
{
  SDLOC, 
  DSDLOC, 
  ISDLOC;

  public String value() {
    return name();
  }

  public static RoleClassServiceDeliveryLocation fromValue(String v) {
    return valueOf(v);
  }
}

