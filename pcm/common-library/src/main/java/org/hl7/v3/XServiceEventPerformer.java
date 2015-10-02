package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ServiceEventPerformer")
@XmlEnum
public enum XServiceEventPerformer
{
  PRF, 
  PPRF, 
  SPRF;

  public String value() {
    return name();
  }

  public static XServiceEventPerformer fromValue(String v) {
    return valueOf(v);
  }
}

