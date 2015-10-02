package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="IntegerFilterType")
public class IntegerFilterType extends SimpleFilterType
{

  @XmlAttribute(name="value", required=true)
  protected BigInteger value;

  public BigInteger getValue()
  {
    return this.value;
  }

  public void setValue(BigInteger value)
  {
    this.value = value;
  }
}

