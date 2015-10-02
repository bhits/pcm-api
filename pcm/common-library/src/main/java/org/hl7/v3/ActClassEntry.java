package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActClassEntry")
@XmlEnum
public enum ActClassEntry
{
  ENTRY, 
  BATTERY, 
  CLUSTER;

  public String value() {
    return name();
  }

  public static ActClassEntry fromValue(String v) {
    return valueOf(v);
  }
}

