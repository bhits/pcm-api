package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="TelephoneNumberType")
public class TelephoneNumberType
{

  @XmlAttribute(name="areaCode")
  protected String areaCode;

  @XmlAttribute(name="countryCode")
  protected String countryCode;

  @XmlAttribute(name="extension")
  protected String extension;

  @XmlAttribute(name="number")
  protected String number;

  @XmlAttribute(name="phoneType")
  protected String phoneType;

  public String getAreaCode()
  {
    return this.areaCode;
  }

  public void setAreaCode(String value)
  {
    this.areaCode = value;
  }

  public String getCountryCode()
  {
    return this.countryCode;
  }

  public void setCountryCode(String value)
  {
    this.countryCode = value;
  }

  public String getExtension()
  {
    return this.extension;
  }

  public void setExtension(String value)
  {
    this.extension = value;
  }

  public String getNumber()
  {
    return this.number;
  }

  public void setNumber(String value)
  {
    this.number = value;
  }

  public String getPhoneType()
  {
    return this.phoneType;
  }

  public void setPhoneType(String value)
  {
    this.phoneType = value;
  }
}

