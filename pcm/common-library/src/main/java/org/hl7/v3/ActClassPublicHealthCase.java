package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActClassPublicHealthCase")
@XmlEnum
public enum ActClassPublicHealthCase
{
  CASE, 
  OUTB;

  public String value() {
    return name();
  }

  public static ActClassPublicHealthCase fromValue(String v) {
    return valueOf(v);
  }
}

