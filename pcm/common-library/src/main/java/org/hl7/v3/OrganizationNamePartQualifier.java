package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="OrganizationNamePartQualifier")
@XmlEnum
public enum OrganizationNamePartQualifier
{
  LS;

  public String value() {
    return name();
  }

  public static OrganizationNamePartQualifier fromValue(String v) {
    return valueOf(v);
  }
}

