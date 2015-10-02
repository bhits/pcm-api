package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RegistryPackageType", propOrder={"registryObjectList"})
public class RegistryPackageType extends RegistryObjectType
{

  @XmlElement(name="RegistryObjectList")
  protected RegistryObjectListType registryObjectList;

  public RegistryObjectListType getRegistryObjectList()
  {
    return this.registryObjectList;
  }

  public void setRegistryObjectList(RegistryObjectListType value)
  {
    this.registryObjectList = value;
  }
}

