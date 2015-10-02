package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ModelMediaType")
@XmlEnum
public enum ModelMediaType
{
  MODEL_VRML("model/vrml");

  private final String value;

  private ModelMediaType(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static ModelMediaType fromValue(String v) {
    for (ModelMediaType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

