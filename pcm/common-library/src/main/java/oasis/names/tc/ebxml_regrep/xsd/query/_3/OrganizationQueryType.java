package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="OrganizationQueryType", propOrder={"addressFilter", "telephoneNumberFilter", "emailAddressFilter", "parentQuery", "childOrganizationQuery", "primaryContactQuery"})
public class OrganizationQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="AddressFilter")
  protected List<FilterType> addressFilter;

  @XmlElement(name="TelephoneNumberFilter")
  protected List<FilterType> telephoneNumberFilter;

  @XmlElement(name="EmailAddressFilter")
  protected List<FilterType> emailAddressFilter;

  @XmlElement(name="ParentQuery")
  protected OrganizationQueryType parentQuery;

  @XmlElement(name="ChildOrganizationQuery")
  protected List<OrganizationQueryType> childOrganizationQuery;

  @XmlElement(name="PrimaryContactQuery")
  protected PersonQueryType primaryContactQuery;

  public List<FilterType> getAddressFilter()
  {
    if (this.addressFilter == null) {
      this.addressFilter = new ArrayList();
    }
    return this.addressFilter;
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

  public OrganizationQueryType getParentQuery()
  {
    return this.parentQuery;
  }

  public void setParentQuery(OrganizationQueryType value)
  {
    this.parentQuery = value;
  }

  public List<OrganizationQueryType> getChildOrganizationQuery()
  {
    if (this.childOrganizationQuery == null) {
      this.childOrganizationQuery = new ArrayList();
    }
    return this.childOrganizationQuery;
  }

  public PersonQueryType getPrimaryContactQuery()
  {
    return this.primaryContactQuery;
  }

  public void setPrimaryContactQuery(PersonQueryType value)
  {
    this.primaryContactQuery = value;
  }
}

