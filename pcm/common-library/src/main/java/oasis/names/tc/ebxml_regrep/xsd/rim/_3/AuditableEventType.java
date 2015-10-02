package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AuditableEventType", propOrder={"affectedObjects"})
public class AuditableEventType extends RegistryObjectType
{

  @XmlElement(required=true)
  protected ObjectRefListType affectedObjects;

  @XmlAttribute(name="eventType", required=true)
  protected String eventType;

  @XmlAttribute(name="timestamp", required=true)
  @XmlSchemaType(name="dateTime")
  protected XMLGregorianCalendar timestamp;

  @XmlAttribute(name="user", required=true)
  protected String user;

  @XmlAttribute(name="requestId", required=true)
  protected String requestId;

  public ObjectRefListType getAffectedObjects()
  {
    return this.affectedObjects;
  }

  public void setAffectedObjects(ObjectRefListType value)
  {
    this.affectedObjects = value;
  }

  public String getEventType()
  {
    return this.eventType;
  }

  public void setEventType(String value)
  {
    this.eventType = value;
  }

  public XMLGregorianCalendar getTimestamp()
  {
    return this.timestamp;
  }

  public void setTimestamp(XMLGregorianCalendar value)
  {
    this.timestamp = value;
  }

  public String getUser()
  {
    return this.user;
  }

  public void setUser(String value)
  {
    this.user = value;
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

