package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="LicensedEntityRole")
@XmlEnum
public enum LicensedEntityRole
{
  LIC, 
  NOT, 
  PROV;

  public String value() {
    return name();
  }

  public static LicensedEntityRole fromValue(String v) {
    return valueOf(v);
  }
}

