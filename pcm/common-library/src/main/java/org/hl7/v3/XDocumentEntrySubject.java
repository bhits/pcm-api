package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_DocumentEntrySubject")
@XmlEnum
public enum XDocumentEntrySubject
{
  SPEC, 
  PAT, 
  PRS;

  public String value() {
    return name();
  }

  public static XDocumentEntrySubject fromValue(String v) {
    return valueOf(v);
  }
}

