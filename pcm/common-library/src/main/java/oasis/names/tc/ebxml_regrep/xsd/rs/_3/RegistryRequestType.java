package oasis.names.tc.ebxml_regrep.xsd.rs._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotListType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RegistryRequestType", propOrder={"requestSlotList"})
public class RegistryRequestType
{

  @XmlElement(name="RequestSlotList")
  protected SlotListType requestSlotList;

  @XmlAttribute(name="id")
  @XmlSchemaType(name="anyURI")
  protected String id;

  @XmlAttribute(name="comment")
  protected String comment;

  public SlotListType getRequestSlotList()
  {
    return this.requestSlotList;
  }

  public void setRequestSlotList(SlotListType value)
  {
    this.requestSlotList = value;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String value)
  {
    this.id = value;
  }

  public String getComment()
  {
    return this.comment;
  }

  public void setComment(String value)
  {
    this.comment = value;
  }
}

