package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActClassFinancialContract")
@XmlEnum
public enum ActClassFinancialContract
{
  FCNTRCT, 
  COV;

  public String value() {
    return name();
  }

  public static ActClassFinancialContract fromValue(String v) {
    return valueOf(v);
  }
}

