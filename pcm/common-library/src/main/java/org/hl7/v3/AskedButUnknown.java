package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AskedButUnknown")
@XmlEnum
public enum AskedButUnknown
{
  ASKU, 
  NAV;

  public String value() {
    return name();
  }

  public static AskedButUnknown fromValue(String v) {
    return valueOf(v);
  }
}

