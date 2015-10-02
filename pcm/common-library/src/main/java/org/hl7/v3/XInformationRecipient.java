package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_InformationRecipient")
@XmlEnum
public enum XInformationRecipient
{
  PRCP, 
  TRC;

  public String value() {
    return name();
  }

  public static XInformationRecipient fromValue(String v) {
    return valueOf(v);
  }
}

