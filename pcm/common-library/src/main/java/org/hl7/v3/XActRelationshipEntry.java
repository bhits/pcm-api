package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActRelationshipEntry")
@XmlEnum
public enum XActRelationshipEntry
{
  COMP, 
  DRIV;

  public String value() {
    return name();
  }

  public static XActRelationshipEntry fromValue(String v) {
    return valueOf(v);
  }
}

