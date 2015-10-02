package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActClassSupply")
@XmlEnum
public enum ActClassSupply
{
  SPLY, 
  DIET;

  public String value() {
    return name();
  }

  public static ActClassSupply fromValue(String v) {
    return valueOf(v);
  }
}

