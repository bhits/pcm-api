package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActMoodOrdPrmsEvn")
@XmlEnum
public enum XActMoodOrdPrmsEvn
{
  EVN, 
  PRMS, 
  RQO;

  public String value() {
    return name();
  }

  public static XActMoodOrdPrmsEvn fromValue(String v) {
    return valueOf(v);
  }
}

