package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="BIN")
@XmlSeeAlso({ED.class})
public abstract class BIN extends ANY
{

  @XmlAttribute(name="representation")
  protected BinaryDataEncoding representation;

  public BinaryDataEncoding getRepresentation()
  {
    if (this.representation == null) {
      return BinaryDataEncoding.TXT;
    }
    return this.representation;
  }

  public void setRepresentation(BinaryDataEncoding value)
  {
    this.representation = value;
  }
}

