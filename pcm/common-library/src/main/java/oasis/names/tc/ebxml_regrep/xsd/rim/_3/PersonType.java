package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PersonType", propOrder={"address", "personName", "telephoneNumber", "emailAddress"})
@XmlSeeAlso({UserType.class})
public class PersonType extends RegistryObjectType
{

  @XmlElement(name="Address")
  protected List<PostalAddressType> address;

  @XmlElement(name="PersonName")
  protected PersonNameType personName;

  @XmlElement(name="TelephoneNumber")
  protected List<TelephoneNumberType> telephoneNumber;

  @XmlElement(name="EmailAddress")
  protected List<EmailAddressType> emailAddress;

  public List<PostalAddressType> getAddress()
  {
    if (this.address == null) {
      this.address = new ArrayList();
    }
    return this.address;
  }

  public PersonNameType getPersonName()
  {
    return this.personName;
  }

  public void setPersonName(PersonNameType value)
  {
    this.personName = value;
  }

  public List<TelephoneNumberType> getTelephoneNumber()
  {
    if (this.telephoneNumber == null) {
      this.telephoneNumber = new ArrayList();
    }
    return this.telephoneNumber;
  }

  public List<EmailAddressType> getEmailAddress()
  {
    if (this.emailAddress == null) {
      this.emailAddress = new ArrayList();
    }
    return this.emailAddress;
  }
}

