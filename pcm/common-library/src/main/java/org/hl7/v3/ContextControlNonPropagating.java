package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ContextControlNonPropagating")
@XmlEnum
public enum ContextControlNonPropagating
{
  AN, 
  ON;

  public String value() {
    return name();
  }

  public static ContextControlNonPropagating fromValue(String v) {
    return valueOf(v);
  }
}

