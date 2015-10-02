package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TemporallyPertains")
@XmlEnum
public enum TemporallyPertains
{
  SAS;

  public String value() {
    return name();
  }

  public static TemporallyPertains fromValue(String v) {
    return valueOf(v);
  }
}

