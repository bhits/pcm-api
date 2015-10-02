package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActRelationshipExternalReference")
@XmlEnum
public enum XActRelationshipExternalReference
{
  XCRPT, 
  RPLC, 
  SPRT, 
  ELNK, 
  REFR, 
  SUBJ;

  public String value() {
    return name();
  }

  public static XActRelationshipExternalReference fromValue(String v) {
    return valueOf(v);
  }
}

