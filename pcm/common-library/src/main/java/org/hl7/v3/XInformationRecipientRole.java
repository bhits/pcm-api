package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_InformationRecipientRole")
@XmlEnum
public enum XInformationRecipientRole
{
  ASSIGNED, 
  HLTHCHRT;

  public String value() {
    return name();
  }

  public static XInformationRecipientRole fromValue(String v) {
    return valueOf(v);
  }
}

