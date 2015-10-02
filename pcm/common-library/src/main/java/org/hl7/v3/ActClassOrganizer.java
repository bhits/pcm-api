package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ActClassOrganizer")
@XmlEnum
public enum ActClassOrganizer
{
  ORGANIZER, 
  CATEGORY, 
  DOCBODY, 
  DOCSECT, 
  TOPIC;

  public String value() {
    return name();
  }

  public static ActClassOrganizer fromValue(String v) {
    return valueOf(v);
  }
}

