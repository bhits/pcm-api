package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActRelationshipReplacement")
@XmlEnum
public enum ActRelationshipReplacement
{
  RPLC, 
  SUCC;

  public String value() {
    return name();
  }

  public static ActRelationshipReplacement fromValue(String v) {
    return valueOf(v);
  }
}

