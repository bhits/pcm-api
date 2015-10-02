package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AdditionalLocator")
@XmlEnum
public enum AdditionalLocator
{
  ADL, 
  UNID, 
  UNIT;

  public String value() {
    return name();
  }

  public static AdditionalLocator fromValue(String v) {
    return valueOf(v);
  }
}

