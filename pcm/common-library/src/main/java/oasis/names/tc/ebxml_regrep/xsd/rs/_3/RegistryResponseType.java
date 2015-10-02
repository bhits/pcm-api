package oasis.names.tc.ebxml_regrep.xsd.rs._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotListType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="RegistryResponseType", propOrder={"responseSlotList", "registryErrorList"})
public class RegistryResponseType
{

  @XmlElement(name="ResponseSlotList")
  protected SlotListType responseSlotList;

  @XmlElement(name="RegistryErrorList")
  protected RegistryErrorList registryErrorList;

  @XmlAttribute(name="status", required=true)
  protected String status;

  @XmlAttribute(name="requestId")
  @XmlSchemaType(name="anyURI")
  protected String requestId;

  public SlotListType getResponseSlotList()
  {
    return this.responseSlotList;
  }

  public void setResponseSlotList(SlotListType value)
  {
    this.responseSlotList = value;
  }

  public RegistryErrorList getRegistryErrorList()
  {
    return this.registryErrorList;
  }

  public void setRegistryErrorList(RegistryErrorList value)
  {
    this.registryErrorList = value;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String value)
  {
    this.status = value;
  }

  public String getRequestId()
  {
    return this.requestId;
  }

  public void setRequestId(String value)
  {
    this.requestId = value;
  }
}

