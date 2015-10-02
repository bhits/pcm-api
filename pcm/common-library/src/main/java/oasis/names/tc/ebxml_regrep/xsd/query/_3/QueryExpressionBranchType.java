package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="QueryExpressionBranchType", propOrder={"queryLanguageQuery"})
public class QueryExpressionBranchType extends BranchType
{

  @XmlElement(name="QueryLanguageQuery")
  protected ClassificationNodeQueryType queryLanguageQuery;

  public ClassificationNodeQueryType getQueryLanguageQuery()
  {
    return this.queryLanguageQuery;
  }

  public void setQueryLanguageQuery(ClassificationNodeQueryType value)
  {
    this.queryLanguageQuery = value;
  }
}

