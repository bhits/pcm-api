package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PostalAddressType")
public class PostalAddressType
{

  @XmlAttribute(name="city")
  protected String city;

  @XmlAttribute(name="country")
  protected String country;

  @XmlAttribute(name="postalCode")
  protected String postalCode;

  @XmlAttribute(name="stateOrProvince")
  protected String stateOrProvince;

  @XmlAttribute(name="street")
  protected String street;

  @XmlAttribute(name="streetNumber")
  protected String streetNumber;

  public String getCity()
  {
    return this.city;
  }

  public void setCity(String value)
  {
    this.city = value;
  }

  public String getCountry()
  {
    return this.country;
  }

  public void setCountry(String value)
  {
    this.country = value;
  }

  public String getPostalCode()
  {
    return this.postalCode;
  }

  public void setPostalCode(String value)
  {
    this.postalCode = value;
  }

  public String getStateOrProvince()
  {
    return this.stateOrProvince;
  }

  public void setStateOrProvince(String value)
  {
    this.stateOrProvince = value;
  }

  public String getStreet()
  {
    return this.street;
  }

  public void setStreet(String value)
  {
    this.street = value;
  }

  public String getStreetNumber()
  {
    return this.streetNumber;
  }

  public void setStreetNumber(String value)
  {
    this.streetNumber = value;
  }
}

