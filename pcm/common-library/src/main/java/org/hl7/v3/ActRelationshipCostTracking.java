package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActRelationshipCostTracking")
@XmlEnum
public enum ActRelationshipCostTracking
{
  CHRG, 
  COST;

  public String value() {
    return name();
  }

  public static ActRelationshipCostTracking fromValue(String v) {
    return valueOf(v);
  }
}

