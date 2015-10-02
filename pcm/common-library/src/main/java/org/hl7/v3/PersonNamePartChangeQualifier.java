package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PersonNamePartChangeQualifier")
@XmlEnum
public enum PersonNamePartChangeQualifier
{
  AD, 
  BR, 
  SP;

  public String value() {
    return name();
  }

  public static PersonNamePartChangeQualifier fromValue(String v) {
    return valueOf(v);
  }
}

