package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FloatFilterType")
public class FloatFilterType extends SimpleFilterType
{

  @XmlAttribute(name="value", required=true)
  protected float value;

  public float getValue()
  {
    return this.value;
  }

  public void setValue(float value)
  {
    this.value = value;
  }
}

