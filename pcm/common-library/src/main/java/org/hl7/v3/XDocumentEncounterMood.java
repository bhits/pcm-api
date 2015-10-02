package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_DocumentEncounterMood")
@XmlEnum
public enum XDocumentEncounterMood
{
  INT, 
  APT, 
  ARQ, 
  EVN, 
  PRMS, 
  PRP, 
  RQO;

  public String value() {
    return name();
  }

  public static XDocumentEncounterMood fromValue(String v) {
    return valueOf(v);
  }
}

