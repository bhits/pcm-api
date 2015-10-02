package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActClassExtract")
@XmlEnum
public enum ActClassExtract
{
  EXTRACT, 
  EHR;

  public String value() {
    return name();
  }

  public static ActClassExtract fromValue(String v) {
    return valueOf(v);
  }
}

