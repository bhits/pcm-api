package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_OrganizationNamePartType")
@XmlEnum
public enum XOrganizationNamePartType
{
  DEL, 
  PFX, 
  SFX;

  public String value() {
    return name();
  }

  public static XOrganizationNamePartType fromValue(String v) {
    return valueOf(v);
  }
}

