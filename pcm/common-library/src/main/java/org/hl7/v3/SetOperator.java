package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SetOperator")
@XmlEnum
public enum SetOperator
{
  A, 
  E, 
  H, 
  I, 
  P;

  public String value() {
    return name();
  }

  public static SetOperator fromValue(String v) {
    return valueOf(v);
  }
}

