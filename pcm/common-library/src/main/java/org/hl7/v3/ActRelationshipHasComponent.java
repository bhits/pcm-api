package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActRelationshipHasComponent")
@XmlEnum
public enum ActRelationshipHasComponent
{
  COMP, 
  ARR, 
  CTRLV, 
  DEP;

  public String value() {
    return name();
  }

  public static ActRelationshipHasComponent fromValue(String v) {
    return valueOf(v);
  }
}

