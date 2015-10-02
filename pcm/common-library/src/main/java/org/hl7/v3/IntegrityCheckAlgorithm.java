package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="IntegrityCheckAlgorithm")
@XmlEnum
public enum IntegrityCheckAlgorithm
{
  SHA_1("SHA-1"), 

  SHA_256("SHA-256");

  private final String value;

  private IntegrityCheckAlgorithm(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static IntegrityCheckAlgorithm fromValue(String v) {
    for (IntegrityCheckAlgorithm c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

