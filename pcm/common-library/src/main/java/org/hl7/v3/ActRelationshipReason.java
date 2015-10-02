package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActRelationshipReason")
@XmlEnum
public enum ActRelationshipReason
{
  RSON, 
  MITGT;

  public String value() {
    return name();
  }

  public static ActRelationshipReason fromValue(String v) {
    return valueOf(v);
  }
}

