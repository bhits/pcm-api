package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RegistryQueryType", propOrder={"operatorQuery"})
public class RegistryQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="OperatorQuery")
  protected OrganizationQueryType operatorQuery;

  public OrganizationQueryType getOperatorQuery()
  {
    return this.operatorQuery;
  }

  public void setOperatorQuery(OrganizationQueryType value)
  {
    this.operatorQuery = value;
  }
}

