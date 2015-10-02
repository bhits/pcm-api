package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ExternalIdentifierType")
public class ExternalIdentifierType extends RegistryObjectType
{

  @XmlAttribute(name="registryObject", required=true)
  protected String registryObject;

  @XmlAttribute(name="identificationScheme", required=true)
  protected String identificationScheme;

  @XmlAttribute(name="value", required=true)
  protected String value;

  public String getRegistryObject()
  {
    return this.registryObject;
  }

  public void setRegistryObject(String value)
  {
    this.registryObject = value;
  }

  public String getIdentificationScheme()
  {
    return this.identificationScheme;
  }

  public void setIdentificationScheme(String value)
  {
    this.identificationScheme = value;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }
}

