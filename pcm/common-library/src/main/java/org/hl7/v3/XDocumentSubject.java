package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_DocumentSubject")
@XmlEnum
public enum XDocumentSubject
{
  PAT, 
  PRS;

  public String value() {
    return name();
  }

  public static XDocumentSubject fromValue(String v) {
    return valueOf(v);
  }
}

