package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SXCM_PQ")
@XmlSeeAlso({IVLPQ.class})
public class SXCMPQ extends PQ
{

  @XmlAttribute(name="operator")
  protected SetOperator operator;

  public SetOperator getOperator()
  {
    if (this.operator == null) {
      return SetOperator.I;
    }
    return this.operator;
  }

  public void setOperator(SetOperator value)
  {
    this.operator = value;
  }
}

