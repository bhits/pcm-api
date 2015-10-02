package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ExternalIdentifierQueryType", propOrder={"registryObjectQuery", "identificationSchemeQuery"})
public class ExternalIdentifierQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="RegistryObjectQuery")
  protected RegistryObjectQueryType registryObjectQuery;

  @XmlElement(name="IdentificationSchemeQuery")
  protected ClassificationSchemeQueryType identificationSchemeQuery;

  public RegistryObjectQueryType getRegistryObjectQuery()
  {
    return this.registryObjectQuery;
  }

  public void setRegistryObjectQuery(RegistryObjectQueryType value)
  {
    this.registryObjectQuery = value;
  }

  public ClassificationSchemeQueryType getIdentificationSchemeQuery()
  {
    return this.identificationSchemeQuery;
  }

  public void setIdentificationSchemeQuery(ClassificationSchemeQueryType value)
  {
    this.identificationSchemeQuery = value;
  }
}

