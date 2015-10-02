package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActMoodDefEvn")
@XmlEnum
public enum XActMoodDefEvn
{
  DEF, 
  EVN;

  public String value() {
    return name();
  }

  public static XActMoodDefEvn fromValue(String v) {
    return valueOf(v);
  }
}

