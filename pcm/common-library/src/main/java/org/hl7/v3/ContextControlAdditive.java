package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ContextControlAdditive")
@XmlEnum
public enum ContextControlAdditive
{
  AN, 
  AP;

  public String value() {
    return name();
  }

  public static ContextControlAdditive fromValue(String v) {
    return valueOf(v);
  }
}

