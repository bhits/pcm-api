package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="SubscriptionType", propOrder={"action"})
public class SubscriptionType extends RegistryObjectType
{

  @XmlElementRef(name="Action", namespace="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0", type=JAXBElement.class)
  protected List<JAXBElement<? extends ActionType>> action;

  @XmlAttribute(name="selector", required=true)
  protected String selector;

  @XmlAttribute(name="startTime")
  @XmlSchemaType(name="dateTime")
  protected XMLGregorianCalendar startTime;

  @XmlAttribute(name="endTime")
  @XmlSchemaType(name="dateTime")
  protected XMLGregorianCalendar endTime;

  @XmlAttribute(name="notificationInterval")
  protected Duration notificationInterval;

  public List<JAXBElement<? extends ActionType>> getAction()
  {
    if (this.action == null) {
      this.action = new ArrayList();
    }
    return this.action;
  }

  public String getSelector()
  {
    return this.selector;
  }

  public void setSelector(String value)
  {
    this.selector = value;
  }

  public XMLGregorianCalendar getStartTime()
  {
    return this.startTime;
  }

  public void setStartTime(XMLGregorianCalendar value)
  {
    this.startTime = value;
  }

  public XMLGregorianCalendar getEndTime()
  {
    return this.endTime;
  }

  public void setEndTime(XMLGregorianCalendar value)
  {
    this.endTime = value;
  }

  public Duration getNotificationInterval()
  {
    return this.notificationInterval;
  }

  public void setNotificationInterval(Duration value)
  {
    this.notificationInterval = value;
  }
}

