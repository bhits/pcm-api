package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_PersonNamePartType")
@XmlEnum
public enum XPersonNamePartType
{
  DEL, 
  FAM, 
  GIV, 
  PFX, 
  SFX;

  public String value() {
    return name();
  }

  public static XPersonNamePartType fromValue(String v) {
    return valueOf(v);
  }
}

