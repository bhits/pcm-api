package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AudioMediaType")
@XmlEnum
public enum AudioMediaType
{
  AUDIO_BASIC("audio/basic"), 

  AUDIO_K_32_ADPCM("audio/k32adpcm"), 

  AUDIO_MPEG("audio/mpeg");

  private final String value;

  private AudioMediaType(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static AudioMediaType fromValue(String v) {
    for (AudioMediaType c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

