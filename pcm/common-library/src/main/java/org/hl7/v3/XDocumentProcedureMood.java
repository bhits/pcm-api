package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_DocumentProcedureMood")
@XmlEnum
public enum XDocumentProcedureMood
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

  public static XDocumentProcedureMood fromValue(String v) {
    return valueOf(v);
  }
}

