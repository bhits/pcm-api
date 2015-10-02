package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_DocumentActMood")
@XmlEnum
public enum XDocumentActMood
{
  INT, 
  APT, 
  ARQ, 
  DEF, 
  EVN, 
  PRMS, 
  PRP, 
  RQO;

  public String value() {
    return name();
  }

  public static XDocumentActMood fromValue(String v) {
    return valueOf(v);
  }
}

