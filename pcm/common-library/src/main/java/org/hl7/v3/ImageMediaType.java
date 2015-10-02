package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ImageMediaType")
@XmlEnum
public enum ImageMediaType
{
  IMAGE_G_3_FAX("image/g3fax"), 

  IMAGE_GIF("image/gif"), 

  IMAGE_JPEG("image/jpeg"), 

  IMAGE_PNG("image/png"), 

  IMAGE_TIFF("image/tiff");

  private final String value;

  private ImageMediaType(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static ImageMediaType fromValue(String v) {
    for (ImageMediaType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

