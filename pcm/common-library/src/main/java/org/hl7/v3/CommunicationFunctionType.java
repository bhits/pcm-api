package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CommunicationFunctionType")
@XmlEnum
public enum CommunicationFunctionType
{
  RCV, 
  RSP, 
  SND;

  public String value() {
    return name();
  }

  public static CommunicationFunctionType fromValue(String v) {
    return valueOf(v);
  }
}

