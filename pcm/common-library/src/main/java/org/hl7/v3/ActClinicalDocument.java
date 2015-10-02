package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActClinicalDocument")
@XmlEnum
public enum ActClinicalDocument
{
  DOCCLIN, 
  CDALVLONE;

  public String value() {
    return name();
  }

  public static ActClinicalDocument fromValue(String v) {
    return valueOf(v);
  }
}

