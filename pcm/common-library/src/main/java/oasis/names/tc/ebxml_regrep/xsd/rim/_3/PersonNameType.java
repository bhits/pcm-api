package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PersonNameType")
public class PersonNameType
{

  @XmlAttribute(name="firstName")
  protected String firstName;

  @XmlAttribute(name="middleName")
  protected String middleName;

  @XmlAttribute(name="lastName")
  protected String lastName;

  public String getFirstName()
  {
    return this.firstName;
  }

  public void setFirstName(String value)
  {
    this.firstName = value;
  }

  public String getMiddleName()
  {
    return this.middleName;
  }

  public void setMiddleName(String value)
  {
    this.middleName = value;
  }

  public String getLastName()
  {
    return this.lastName;
  }

  public void setLastName(String value)
  {
    this.lastName = value;
  }
}

