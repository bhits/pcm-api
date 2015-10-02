package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="NamePseudonymUse")
@XmlEnum
public enum NamePseudonymUse
{
  P, 
  A;

  public String value() {
    return name();
  }

  public static NamePseudonymUse fromValue(String v) {
    return valueOf(v);
  }
}

