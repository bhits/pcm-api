package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="NotificationQueryType", propOrder={"registryObjectQuery"})
public class NotificationQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="RegistryObjectQuery")
  protected RegistryObjectQueryType registryObjectQuery;

  public RegistryObjectQueryType getRegistryObjectQuery()
  {
    return this.registryObjectQuery;
  }

  public void setRegistryObjectQuery(RegistryObjectQueryType value)
  {
    this.registryObjectQuery = value;
  }
}

