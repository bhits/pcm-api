package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SlotType1", propOrder={"valueList"})
public class SlotType1
{

  @XmlElement(name="ValueList", required=true)
  protected ValueListType valueList;

  @XmlAttribute(name="name", required=true)
  protected String name;

  @XmlAttribute(name="slotType")
  protected String slotType;

  public ValueListType getValueList()
  {
    return this.valueList;
  }

  public void setValueList(ValueListType value)
  {
    this.valueList = value;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String value)
  {
    this.name = value;
  }

  public String getSlotType()
  {
    return this.slotType;
  }

  public void setSlotType(String value)
  {
    this.slotType = value;
  }
}

