package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_DeterminerInstanceKind")
@XmlEnum
public enum XDeterminerInstanceKind
{
  KIND, 
  INSTANCE;

  public String value() {
    return name();
  }

  public static XDeterminerInstanceKind fromValue(String v) {
    return valueOf(v);
  }
}

