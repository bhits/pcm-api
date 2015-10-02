package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActMoodOrdPrms")
@XmlEnum
public enum XActMoodOrdPrms
{
  PRMS, 
  RQO;

  public String value() {
    return name();
  }

  public static XActMoodOrdPrms fromValue(String v) {
    return valueOf(v);
  }
}

