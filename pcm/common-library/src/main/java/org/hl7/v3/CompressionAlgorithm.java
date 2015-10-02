package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CompressionAlgorithm")
@XmlEnum
public enum CompressionAlgorithm
{
  DF, 
  GZ, 
  Z, 
  ZL;

  public String value() {
    return name();
  }

  public static CompressionAlgorithm fromValue(String v) {
    return valueOf(v);
  }
}

