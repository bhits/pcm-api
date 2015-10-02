package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SubscriptionQueryType", propOrder={"selectorQuery"})
public class SubscriptionQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="SelectorQuery")
  protected AdhocQueryQueryType selectorQuery;

  public AdhocQueryQueryType getSelectorQuery()
  {
    return this.selectorQuery;
  }

  public void setSelectorQuery(AdhocQueryQueryType value)
  {
    this.selectorQuery = value;
  }
}

