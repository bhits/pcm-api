package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParticipationInformationRecipient")
@XmlEnum
public enum ParticipationInformationRecipient
{
  IRCP, 
  NOT, 
  PRCP, 
  REFB, 
  REFT, 
  TRC;

  public String value() {
    return name();
  }

  public static ParticipationInformationRecipient fromValue(String v) {
    return valueOf(v);
  }
}

