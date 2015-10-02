package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="CompoundFilterType", propOrder={"leftFilter", "rightFilter"})
public class CompoundFilterType extends FilterType
{

  @XmlElement(name="LeftFilter", required=true)
  protected FilterType leftFilter;

  @XmlElement(name="RightFilter", required=true)
  protected FilterType rightFilter;

  @XmlAttribute(name="logicalOperator", required=true)
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  protected String logicalOperator;

  public FilterType getLeftFilter()
  {
    return this.leftFilter;
  }

  public void setLeftFilter(FilterType value)
  {
    this.leftFilter = value;
  }

  public FilterType getRightFilter()
  {
    return this.rightFilter;
  }

  public void setRightFilter(FilterType value)
  {
    this.rightFilter = value;
  }

  public String getLogicalOperator()
  {
    return this.logicalOperator;
  }

  public void setLogicalOperator(String value)
  {
    this.logicalOperator = value;
  }
}

