package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="EntityClassNonPersonLivingSubject")
@XmlEnum
public enum EntityClassNonPersonLivingSubject
{
  NLIV, 
  ANM, 
  MIC, 
  PLNT;

  public String value() {
    return name();
  }

  public static EntityClassNonPersonLivingSubject fromValue(String v) {
    return valueOf(v);
  }
}

