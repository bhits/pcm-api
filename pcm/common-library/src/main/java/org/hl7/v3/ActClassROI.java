package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActClassROI")
@XmlEnum
public enum ActClassROI
{
  ROIBND, 
  ROIOVL;

  public String value() {
    return name();
  }

  public static ActClassROI fromValue(String v) {
    return valueOf(v);
  }
}

