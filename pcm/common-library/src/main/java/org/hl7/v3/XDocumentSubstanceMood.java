package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_DocumentSubstanceMood")
@XmlEnum
public enum XDocumentSubstanceMood
{
  INT, 
  EVN, 
  PRMS, 
  PRP, 
  RQO;

  public String value() {
    return name();
  }

  public static XDocumentSubstanceMood fromValue(String v) {
    return valueOf(v);
  }
}

