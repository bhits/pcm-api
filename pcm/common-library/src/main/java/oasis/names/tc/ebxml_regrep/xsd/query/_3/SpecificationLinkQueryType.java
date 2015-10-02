package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SpecificationLinkQueryType", propOrder={"usageDescriptionBranch", "serviceBindingQuery", "specificationObjectQuery"})
public class SpecificationLinkQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="UsageDescriptionBranch")
  protected InternationalStringBranchType usageDescriptionBranch;

  @XmlElement(name="ServiceBindingQuery")
  protected ServiceBindingQueryType serviceBindingQuery;

  @XmlElement(name="SpecificationObjectQuery")
  protected RegistryObjectQueryType specificationObjectQuery;

  public InternationalStringBranchType getUsageDescriptionBranch()
  {
    return this.usageDescriptionBranch;
  }

  public void setUsageDescriptionBranch(InternationalStringBranchType value)
  {
    this.usageDescriptionBranch = value;
  }

  public ServiceBindingQueryType getServiceBindingQuery()
  {
    return this.serviceBindingQuery;
  }

  public void setServiceBindingQuery(ServiceBindingQueryType value)
  {
    this.serviceBindingQuery = value;
  }

  public RegistryObjectQueryType getSpecificationObjectQuery()
  {
    return this.specificationObjectQuery;
  }

  public void setSpecificationObjectQuery(RegistryObjectQueryType value)
  {
    this.specificationObjectQuery = value;
  }
}

