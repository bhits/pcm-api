package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceBindingQueryType", propOrder={"serviceQuery", "specificationLinkQuery", "targetBindingQuery"})
public class ServiceBindingQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="ServiceQuery")
  protected ServiceQueryType serviceQuery;

  @XmlElement(name="SpecificationLinkQuery")
  protected List<SpecificationLinkQueryType> specificationLinkQuery;

  @XmlElement(name="TargetBindingQuery")
  protected ServiceBindingQueryType targetBindingQuery;

  public ServiceQueryType getServiceQuery()
  {
    return this.serviceQuery;
  }

  public void setServiceQuery(ServiceQueryType value)
  {
    this.serviceQuery = value;
  }

  public List<SpecificationLinkQueryType> getSpecificationLinkQuery()
  {
    if (this.specificationLinkQuery == null) {
      this.specificationLinkQuery = new ArrayList();
    }
    return this.specificationLinkQuery;
  }

  public ServiceBindingQueryType getTargetBindingQuery()
  {
    return this.targetBindingQuery;
  }

  public void setTargetBindingQuery(ServiceBindingQueryType value)
  {
    this.targetBindingQuery = value;
  }
}

