package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RelatedLinkType")
@XmlEnum
public enum RelatedLinkType
{
  REL, 
  BACKUP, 
  DIRAUTH, 
  INDAUTH, 
  PART, 
  REPL;

  public String value() {
    return name();
  }

  public static RelatedLinkType fromValue(String v) {
    return valueOf(v);
  }
}

