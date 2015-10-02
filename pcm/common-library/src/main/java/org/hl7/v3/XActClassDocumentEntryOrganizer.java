package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActClassDocumentEntryOrganizer")
@XmlEnum
public enum XActClassDocumentEntryOrganizer
{
  BATTERY, 
  CLUSTER;

  public String value() {
    return name();
  }

  public static XActClassDocumentEntryOrganizer fromValue(String v) {
    return valueOf(v);
  }
}

