package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="QueryExpressionType", propOrder={"content"})
public class QueryExpressionType
{

  @XmlMixed
  @XmlAnyElement(lax=true)
  protected List<Object> content;

  @XmlAttribute(name="queryLanguage", required=true)
  protected String queryLanguage;

  public List<Object> getContent()
  {
    if (this.content == null) {
      this.content = new ArrayList();
    }
    return this.content;
  }

  public String getQueryLanguage()
  {
    return this.queryLanguage;
  }

  public void setQueryLanguage(String value)
  {
    this.queryLanguage = value;
  }
}

