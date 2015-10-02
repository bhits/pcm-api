package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="PersonQueryType", propOrder={"addressFilter", "personNameFilter", "telephoneNumberFilter", "emailAddressFilter"})
@XmlSeeAlso({UserQueryType.class})
public class PersonQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="AddressFilter")
  protected List<FilterType> addressFilter;

  @XmlElement(name="PersonNameFilter")
  protected FilterType personNameFilter;

  @XmlElement(name="TelephoneNumberFilter")
  protected List<FilterType> telephoneNumberFilter;

  @XmlElement(name="EmailAddressFilter")
  protected List<FilterType> emailAddressFilter;

  public List<FilterType> getAddressFilter()
  {
    if (this.addressFilter == null) {
      this.addressFilter = new ArrayList();
    }
    return this.addressFilter;
  }

  public FilterType getPersonNameFilter()
  {
    return this.personNameFilter;
  }

  public void setPersonNameFilter(FilterType value)
  {
    this.personNameFilter = value;
  }

  public List<FilterType> getTelephoneNumberFilter()
  {
    if (this.telephoneNumberFilter == null) {
      this.telephoneNumberFilter = new ArrayList();
    }
    return this.telephoneNumberFilter;
  }

  public List<FilterType> getEmailAddressFilter()
  {
    if (this.emailAddressFilter == null) {
      this.emailAddressFilter = new ArrayList();
    }
    return this.emailAddressFilter;
  }
}

