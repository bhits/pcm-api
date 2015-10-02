package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="NameRepresentationUse")
@XmlEnum
public enum NameRepresentationUse
{
  ABC, 
  IDE, 
  SYL;

  public String value() {
    return name();
  }

  public static NameRepresentationUse fromValue(String v) {
    return valueOf(v);
  }
}

