package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActRelationshipPertinentInfo")
@XmlEnum
public enum XActRelationshipPertinentInfo
{
  SPRT, 
  CAUS, 
  MFST, 
  REFR, 
  SUBJ;

  public String value() {
    return name();
  }

  public static XActRelationshipPertinentInfo fromValue(String v) {
    return valueOf(v);
  }
}

