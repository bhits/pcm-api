package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_RoleClassCoverageInvoice")
@XmlEnum
public enum XRoleClassCoverageInvoice
{
  PAYEE, 
  PAYOR;

  public String value() {
    return name();
  }

  public static XRoleClassCoverageInvoice fromValue(String v) {
    return valueOf(v);
  }
}

