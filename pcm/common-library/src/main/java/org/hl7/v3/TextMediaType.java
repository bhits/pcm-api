package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TextMediaType")
@XmlEnum
public enum TextMediaType
{
  TEXT_HTML("text/html"), 

  TEXT_PLAIN("text/plain"), 

  TEXT_RTF("text/rtf"), 

  TEXT_SGML("text/sgml"), 

  TEXT_X_HL_7_FT("text/x-hl7-ft"), 

  TEXT_XML("text/xml");

  private final String value;

  private TextMediaType(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static TextMediaType fromValue(String v) {
    for (TextMediaType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

