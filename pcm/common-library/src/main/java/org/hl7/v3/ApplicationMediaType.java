package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ApplicationMediaType")
@XmlEnum
public enum ApplicationMediaType
{
  APPLICATION_DICOM("application/dicom"), 

  APPLICATION_MSWORD("application/msword"), 

  APPLICATION_PDF("application/pdf");

  private final String value;

  private ApplicationMediaType(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static ApplicationMediaType fromValue(String v) {
    for (ApplicationMediaType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

