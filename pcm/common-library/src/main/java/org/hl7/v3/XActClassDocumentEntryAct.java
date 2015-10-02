package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_ActClassDocumentEntryAct")
@XmlEnum
public enum XActClassDocumentEntryAct
{
  ACT, 
  ACCM, 
  CONS, 
  CTTEVENT, 
  INC, 
  INFRM, 
  PCPR, 
  REG, 
  SPCTRT;

  public String value() {
    return name();
  }

  public static XActClassDocumentEntryAct fromValue(String v) {
    return valueOf(v);
  }
}

