package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ContextControlPropagating")
@XmlEnum
public enum ContextControlPropagating
{
  AP, 
  OP;

  public String value() {
    return name();
  }

  public static ContextControlPropagating fromValue(String v) {
    return valueOf(v);
  }
}

