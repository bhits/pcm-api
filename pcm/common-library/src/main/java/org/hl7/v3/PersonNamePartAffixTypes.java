package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PersonNamePartAffixTypes")
@XmlEnum
public enum PersonNamePartAffixTypes
{
  AC, 
  NB, 
  PR, 
  VV;

  public String value() {
    return name();
  }

  public static PersonNamePartAffixTypes fromValue(String v) {
    return valueOf(v);
  }
}

