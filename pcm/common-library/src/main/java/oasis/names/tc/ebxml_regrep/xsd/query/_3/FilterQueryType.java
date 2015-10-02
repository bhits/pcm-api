package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="FilterQueryType", propOrder={"primaryFilter"})
@XmlSeeAlso({RegistryObjectQueryType.class, BranchType.class})
public abstract class FilterQueryType
{

  @XmlElement(name="PrimaryFilter")
  protected FilterType primaryFilter;

  public FilterType getPrimaryFilter()
  {
    return this.primaryFilter;
  }

  public void setPrimaryFilter(FilterType value)
  {
    this.primaryFilter = value;
  }
}

