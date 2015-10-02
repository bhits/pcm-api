package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceType", propOrder={"serviceBinding"})
public class ServiceType extends RegistryObjectType
{

  @XmlElement(name="ServiceBinding")
  protected List<ServiceBindingType> serviceBinding;

  public List<ServiceBindingType> getServiceBinding()
  {
    if (this.serviceBinding == null) {
      this.serviceBinding = new ArrayList();
    }
    return this.serviceBinding;
  }
}

