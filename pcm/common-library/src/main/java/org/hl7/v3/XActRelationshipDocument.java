package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActRelationshipDocument")
@XmlEnum
public enum XActRelationshipDocument
{
  RPLC, 
  APND, 
  XFRM;

  public String value() {
    return name();
  }

  public static XActRelationshipDocument fromValue(String v) {
    return valueOf(v);
  }
}

