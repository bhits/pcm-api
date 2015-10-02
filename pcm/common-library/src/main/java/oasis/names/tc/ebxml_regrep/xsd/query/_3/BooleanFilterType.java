package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="BooleanFilterType")
public class BooleanFilterType extends SimpleFilterType
{

  @XmlAttribute(name="value", required=true)
  protected boolean value;

  public boolean isValue()
  {
    return this.value;
  }

  public void setValue(boolean value)
  {
    this.value = value;
  }
}

