package oasis.names.tc.ebxml_regrep.xsd.lcm._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectListType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryRequestType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"registryObjectList"})
@XmlRootElement(name="SubmitObjectsRequest")
public class SubmitObjectsRequest extends RegistryRequestType
{

  @XmlElement(name="RegistryObjectList", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", required=true)
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

