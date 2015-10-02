package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DateTimeFilterType")
public class DateTimeFilterType extends SimpleFilterType
{

  @XmlAttribute(name="value", required=true)
  @XmlSchemaType(name="dateTime")
  protected XMLGregorianCalendar value;

  public XMLGregorianCalendar getValue()
  {
    return this.value;
  }

  public void setValue(XMLGregorianCalendar value)
  {
    this.value = value;
  }
}

