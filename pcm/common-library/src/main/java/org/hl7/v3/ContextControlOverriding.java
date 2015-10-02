package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ContextControlOverriding")
@XmlEnum
public enum ContextControlOverriding
{
  ON, 
  OP;

  public String value() {
    return name();
  }

  public static ContextControlOverriding fromValue(String v) {
    return valueOf(v);
  }
}

