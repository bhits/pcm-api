package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SimpleFilterType")
@XmlSeeAlso({DateTimeFilterType.class, BooleanFilterType.class, IntegerFilterType.class, StringFilterType.class, FloatFilterType.class})
public abstract class SimpleFilterType extends FilterType
{

  @XmlAttribute(name="domainAttribute", required=true)
  protected String domainAttribute;

  @XmlAttribute(name="comparator", required=true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String comparator;

  public String getDomainAttribute()
  {
    return this.domainAttribute;
  }

  public void setDomainAttribute(String value)
  {
    this.domainAttribute = value;
  }

  public String getComparator()
  {
    return this.comparator;
  }

  public void setComparator(String value)
  {
    this.comparator = value;
  }
}

