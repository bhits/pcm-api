package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="VideoMediaType")
@XmlEnum
public enum VideoMediaType
{
  VIDEO_MPEG("video/mpeg"), 

  VIDEO_X_AVI("video/x-avi");

  private final String value;

  private VideoMediaType(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static VideoMediaType fromValue(String v) {
    for (VideoMediaType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

