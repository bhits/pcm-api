package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActMoodEvnOrdPrmsPrp")
@XmlEnum
public enum XActMoodEvnOrdPrmsPrp
{
  EVN, 
  PRMS, 
  PRP, 
  RQO;

  public String value() {
    return name();
  }

  public static XActMoodEvnOrdPrmsPrp fromValue(String v) {
    return valueOf(v);
  }
}

