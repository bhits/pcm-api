package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="NotifyActionType")
public class NotifyActionType extends ActionType
{

  @XmlAttribute(name="notificationOption")
  protected String notificationOption;

  @XmlAttribute(name="endPoint", required=true)
  @XmlSchemaType(name="anyURI")
  protected String endPoint;

  public String getNotificationOption()
  {
    if (this.notificationOption == null) {
      return "urn:oasis:names:tc:ebxml-regrep:NotificationOptionType:ObjectRefs";
    }
    return this.notificationOption;
  }

  public void setNotificationOption(String value)
  {
    this.notificationOption = value;
  }

  public String getEndPoint()
  {
    return this.endPoint;
  }

  public void setEndPoint(String value)
  {
    this.endPoint = value;
  }
}

