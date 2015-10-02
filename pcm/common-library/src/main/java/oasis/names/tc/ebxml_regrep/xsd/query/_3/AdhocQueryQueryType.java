package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AdhocQueryQueryType", propOrder={"queryExpressionBranch"})
public class AdhocQueryQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="QueryExpressionBranch")
  protected QueryExpressionBranchType queryExpressionBranch;

  public QueryExpressionBranchType getQueryExpressionBranch()
  {
    return this.queryExpressionBranch;
  }

  public void setQueryExpressionBranch(QueryExpressionBranchType value)
  {
    this.queryExpressionBranch = value;
  }
}

