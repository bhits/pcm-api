package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActMoodPredicate")
@XmlEnum
public enum ActMoodPredicate
{
  EVN_CRT("EVN.CRT"), 

  GOL("GOL"), 
  OPT("OPT"), 
  PERM("PERM"), 
  PERMRQ("PERMRQ");

  private final String value;

  private ActMoodPredicate(String v) { this.value = v; }

  public String value()
  {
    return this.value;
  }

  public static ActMoodPredicate fromValue(String v) {
    for (ActMoodPredicate c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

