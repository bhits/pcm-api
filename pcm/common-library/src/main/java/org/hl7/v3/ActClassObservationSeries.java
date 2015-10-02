package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActClassObservationSeries")
@XmlEnum
public enum ActClassObservationSeries
{
  OBSSER, 
  OBSCOR;

  public String value() {
    return name();
  }

  public static ActClassObservationSeries fromValue(String v) {
    return valueOf(v);
  }
}

