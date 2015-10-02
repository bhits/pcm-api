package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CalendarCycleOneLetter")
@XmlEnum
public enum CalendarCycleOneLetter
{
  D, 
  H, 
  J, 
  M, 
  N, 
  S, 
  W, 
  Y;

  public String value() {
    return name();
  }

  public static CalendarCycleOneLetter fromValue(String v) {
    return valueOf(v);
  }
}

