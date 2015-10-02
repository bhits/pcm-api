package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActRelationshipPatientTransport")
@XmlEnum
public enum XActRelationshipPatientTransport
{
  ARR, 
  DEP;

  public String value() {
    return name();
  }

  public static XActRelationshipPatientTransport fromValue(String v) {
    return valueOf(v);
  }
}

