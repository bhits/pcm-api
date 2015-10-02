package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActRelationshipPosting")
@XmlEnum
public enum ActRelationshipPosting
{
  CREDIT, 
  DEBIT;

  public String value() {
    return name();
  }

  public static ActRelationshipPosting fromValue(String v) {
    return valueOf(v);
  }
}

