package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActRelationshipExcerpt")
@XmlEnum
public enum ActRelationshipExcerpt
{
  XCRPT, 
  VRXCRPT;

  public String value() {
    return name();
  }

  public static ActRelationshipExcerpt fromValue(String v) {
    return valueOf(v);
  }
}

