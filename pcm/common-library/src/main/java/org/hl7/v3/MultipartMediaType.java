package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="MultipartMediaType")
@XmlEnum
public enum MultipartMediaType
{
  MULTIPART_X_HL_7_CDA_LEVEL_1("multipart/x-hl7-cda-level1");

  private final String value;

  private MultipartMediaType(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static MultipartMediaType fromValue(String v) {
    for (MultipartMediaType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

