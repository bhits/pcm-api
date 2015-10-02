package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="NotificationType", propOrder={"registryObjectList"})
public class NotificationType extends RegistryObjectType
{

  @XmlElement(name="RegistryObjectList", required=true)
  protected RegistryObjectListType registryObjectList;

  @XmlAttribute(name="subscription", required=true)
  protected String subscription;

  public RegistryObjectListType getRegistryObjectList()
  {
    return this.registryObjectList;
  }

  public void setRegistryObjectList(RegistryObjectListType value)
  {
    this.registryObjectList = value;
  }

  public String getSubscription()
  {
    return this.subscription;
  }

  public void setSubscription(String value)
  {
    this.subscription = value;
  }
}

