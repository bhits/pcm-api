package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="BinaryDataEncoding")
@XmlEnum
public enum BinaryDataEncoding
{
  B_64("B64"), 

  TXT("TXT");

  private final String value;

  private BinaryDataEncoding(String v) { this.value = v; }

  public String value()
  {
    return this.value;
  }

  public static BinaryDataEncoding fromValue(String v) {
    for (BinaryDataEncoding c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

