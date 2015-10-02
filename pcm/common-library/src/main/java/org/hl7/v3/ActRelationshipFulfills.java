package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActRelationshipFulfills")
@XmlEnum
public enum ActRelationshipFulfills
{
  FLFS, 
  OCCR, 
  OREF, 
  SCH;

  public String value() {
    return name();
  }

  public static ActRelationshipFulfills fromValue(String v) {
    return valueOf(v);
  }
}

