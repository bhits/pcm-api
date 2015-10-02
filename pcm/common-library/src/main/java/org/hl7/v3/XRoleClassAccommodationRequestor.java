package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_RoleClassAccommodationRequestor")
@XmlEnum
public enum XRoleClassAccommodationRequestor
{
  AGNT, 
  PAT, 
  PROV, 
  PRS;

  public String value() {
    return name();
  }

  public static XRoleClassAccommodationRequestor fromValue(String v) {
    return valueOf(v);
  }
}

