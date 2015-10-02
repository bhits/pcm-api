package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="EmailAddressType")
public class EmailAddressType
{

  @XmlAttribute(name="address", required=true)
  protected String address;

  @XmlAttribute(name="type")
  protected String type;

  public String getAddress()
  {
    return this.address;
  }

  public void setAddress(String value)
  {
    this.address = value;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String value)
  {
    this.type = value;
  }
}

