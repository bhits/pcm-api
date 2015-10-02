package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceQueryType", propOrder={"serviceBindingQuery"})
public class ServiceQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="ServiceBindingQuery")
  protected List<ServiceBindingQueryType> serviceBindingQuery;

  public List<ServiceBindingQueryType> getServiceBindingQuery()
  {
    if (this.serviceBindingQuery == null) {
      this.serviceBindingQuery = new ArrayList();
    }
    return this.serviceBindingQuery;
  }
}

