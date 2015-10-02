package org.hl7.v3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Currency")
@XmlEnum
public enum Currency
{
  ARS, 
  AUD, 
  BRL, 
  CAD, 
  CHF, 
  CLF, 
  CNY, 
  DEM, 
  ESP, 
  EUR, 
  FIM, 
  FRF, 
  GBP, 
  ILS, 
  INR, 
  JPY, 
  KRW, 
  MXN, 
  NLG, 
  NZD, 
  PHP, 
  RUR, 
  THB, 
  TRL, 
  TWD, 
  USD, 
  ZAR;

  public String value() {
    return name();
  }

  public static Currency fromValue(String v) {
    return valueOf(v);
  }
}

