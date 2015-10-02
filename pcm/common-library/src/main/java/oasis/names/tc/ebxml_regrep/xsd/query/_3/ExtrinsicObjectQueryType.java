package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ExtrinsicObjectQueryType", propOrder={"contentVersionInfoFilter"})
public class ExtrinsicObjectQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="ContentVersionInfoFilter")
  protected FilterType contentVersionInfoFilter;

  public FilterType getContentVersionInfoFilter()
  {
    return this.contentVersionInfoFilter;
  }

  public void setContentVersionInfoFilter(FilterType value)
  {
    this.contentVersionInfoFilter = value;
  }
}

