package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_RoleClassCoverage")
@XmlEnum
public enum XRoleClassCoverage
{
  COVPTY, 
  POLHOLD, 
  SPNSR, 
  UNDWRT;

  public String value() {
    return name();
  }

  public static XRoleClassCoverage fromValue(String v) {
    return valueOf(v);
  }
}

