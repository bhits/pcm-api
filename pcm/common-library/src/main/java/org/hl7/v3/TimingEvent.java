package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TimingEvent")
@XmlEnum
public enum TimingEvent
{
  AC, 
  ACD, 
  ACM, 
  ACV, 
  HS, 
  IC, 
  ICD, 
  ICM, 
  ICV, 
  PC, 
  PCD, 
  PCM, 
  PCV;

  public String value() {
    return name();
  }

  public static TimingEvent fromValue(String v) {
    return valueOf(v);
  }
}

