package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="x_RoleClassPayeePolicyRelationship")
@XmlEnum
public enum XRoleClassPayeePolicyRelationship
{
  COVPTY, 
  GUAR, 
  POLHOLD, 
  PROV, 
  PRS;

  public String value() {
    return name();
  }

  public static XRoleClassPayeePolicyRelationship fromValue(String v) {
    return valueOf(v);
  }
}

