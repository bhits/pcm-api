package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ProbabilityDistributionType")
@XmlEnum
public enum ProbabilityDistributionType
{
  B("B"), 
  E("E"), 
  F("F"), 
  G("G"), 
  LN("LN"), 
  N("N"), 
  T("T"), 
  U("U"), 
  X_2("X2");

  private final String value;

  private ProbabilityDistributionType(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static ProbabilityDistributionType fromValue(String v) {
    for (ProbabilityDistributionType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

