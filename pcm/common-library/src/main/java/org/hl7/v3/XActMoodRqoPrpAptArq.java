package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActMoodRqoPrpAptArq")
@XmlEnum
public enum XActMoodRqoPrpAptArq
{
  APT, 
  ARQ, 
  PRP, 
  RQO;

  public String value() {
    return name();
  }

  public static XActMoodRqoPrpAptArq fromValue(String v) {
    return valueOf(v);
  }
}

