package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActMoodDocumentObservation")
@XmlEnum
public enum XActMoodDocumentObservation
{
  INT, 
  DEF, 
  EVN, 
  GOL, 
  PRMS, 
  PRP, 
  RQO;

  public String value() {
    return name();
  }

  public static XActMoodDocumentObservation fromValue(String v) {
    return valueOf(v);
  }
}

