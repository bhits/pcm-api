package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DeliveryAddressLine")
@XmlEnum
public enum DeliveryAddressLine
{
  DAL, 
  DINST, 
  DINSTA, 
  DINSTQ, 
  DMOD, 
  DMODID;

  public String value() {
    return name();
  }

  public static DeliveryAddressLine fromValue(String v) {
    return valueOf(v);
  }
}

