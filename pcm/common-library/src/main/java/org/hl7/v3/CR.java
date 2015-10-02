package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="CR", propOrder={"name", "value"})
public class CR extends ANY
{
  protected CV name;
  protected CD value;

  @XmlAttribute(name="inverted")
  protected Boolean inverted;

  public CV getName()
  {
    return this.name;
  }

  public void setName(CV value)
  {
    this.name = value;
  }

  public CD getValue()
  {
    return this.value;
  }

  public void setValue(CD value)
  {
    this.value = value;
  }

  public boolean isInverted()
  {
    if (this.inverted == null) {
      return false;
    }
    return this.inverted.booleanValue();
  }

  public void setInverted(Boolean value)
  {
    this.inverted = value;
  }
}

