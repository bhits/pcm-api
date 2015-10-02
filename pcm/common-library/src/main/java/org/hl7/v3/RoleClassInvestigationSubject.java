package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RoleClassInvestigationSubject")
@XmlEnum
public enum RoleClassInvestigationSubject
{
  INVSBJ, 
  CASESBJ, 
  RESBJ;

  public String value() {
    return name();
  }

  public static RoleClassInvestigationSubject fromValue(String v) {
    return valueOf(v);
  }
}

