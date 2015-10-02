package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FilterType")
@XmlSeeAlso({CompoundFilterType.class, SimpleFilterType.class})
public class FilterType
{

  @XmlAttribute(name="negate")
  protected Boolean negate;

  public boolean isNegate()
  {
    if (this.negate == null) {
      return false;
    }
    return this.negate.booleanValue();
  }

  public void setNegate(Boolean value)
  {
    this.negate = value;
  }
}

