package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActRelationshipEntryRelationship")
@XmlEnum
public enum XActRelationshipEntryRelationship
{
  XCRPT, 
  COMP, 
  RSON, 
  SPRT, 
  CAUS, 
  GEVL, 
  MFST, 
  REFR, 
  SAS, 
  SUBJ;

  public String value() {
    return name();
  }

  public static XActRelationshipEntryRelationship fromValue(String v) {
    return valueOf(v);
  }
}

