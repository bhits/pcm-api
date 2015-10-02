package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActMoodDefEvnRqoPrmsPrp")
@XmlEnum
public enum XActMoodDefEvnRqoPrmsPrp
{
  DEF, 
  EVN, 
  PRMS, 
  PRP, 
  RQO;

  public String value() {
    return name();
  }

  public static XActMoodDefEvnRqoPrmsPrp fromValue(String v) {
    return valueOf(v);
  }
}

