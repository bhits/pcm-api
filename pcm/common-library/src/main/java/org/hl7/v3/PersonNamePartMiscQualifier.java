package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PersonNamePartMiscQualifier")
@XmlEnum
public enum PersonNamePartMiscQualifier
{
  CL;

  public String value() {
    return name();
  }

  public static PersonNamePartMiscQualifier fromValue(String v) {
    return valueOf(v);
  }
}

