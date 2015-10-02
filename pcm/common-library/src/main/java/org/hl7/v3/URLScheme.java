package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="URLScheme")
@XmlEnum
public enum URLScheme
{
  FAX("fax"), 

  FILE("file"), 

  FTP("ftp"), 

  HTTP("http"), 

  MAILTO("mailto"), 

  MLLP("mllp"), 

  MODEM("modem"), 

  NFS("nfs"), 

  TEL("tel"), 

  TELNET("telnet");

  private final String value;

  private URLScheme(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static URLScheme fromValue(String v) {
    for (URLScheme c : values()) {
      if (c.value.equals(v)) {
        return c;
      }
    }
    throw new IllegalArgumentException(v);
  }
}

