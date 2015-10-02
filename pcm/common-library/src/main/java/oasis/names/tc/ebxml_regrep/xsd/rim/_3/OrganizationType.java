package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="OrganizationType", propOrder={"address", "telephoneNumber", "emailAddress"})
public class OrganizationType extends RegistryObjectType
{

  @XmlElement(name="Address")
  protected List<PostalAddressType> address;

  @XmlElement(name="TelephoneNumber")
  protected List<TelephoneNumberType> telephoneNumber;

  @XmlElement(name="EmailAddress")
  protected List<EmailAddressType> emailAddress;

  @XmlAttribute(name="parent")
  protected String parent;

  @XmlAttribute(name="primaryContact")
  protected String primaryContact;

  public List<PostalAddressType> getAddress()
  {
    if (this.address == null) {
      this.address = new ArrayList();
    }
    return this.address;
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

  public String getParent()
  {
    return this.parent;
  }

  public void setParent(String value)
  {
    this.parent = value;
  }

  public String getPrimaryContact()
  {
    return this.primaryContact;
  }

  public void setPrimaryContact(String value)
  {
    this.primaryContact = value;
  }
}

