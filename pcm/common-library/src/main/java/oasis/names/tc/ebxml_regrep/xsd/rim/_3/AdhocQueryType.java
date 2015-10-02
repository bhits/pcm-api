package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AdhocQueryType", propOrder={"queryExpression"})
public class AdhocQueryType extends RegistryObjectType
{

  @XmlElement(name="QueryExpression")
  protected QueryExpressionType queryExpression;

  public QueryExpressionType getQueryExpression()
  {
    return this.queryExpression;
  }

  public void setQueryExpression(QueryExpressionType value)
  {
    this.queryExpression = value;
  }
}

