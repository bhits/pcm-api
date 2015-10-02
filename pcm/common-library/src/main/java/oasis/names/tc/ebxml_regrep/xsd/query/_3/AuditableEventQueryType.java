package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AuditableEventQueryType", propOrder={"affectedObjectQuery", "eventTypeQuery", "userQuery"})
public class AuditableEventQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="AffectedObjectQuery")
  protected List<RegistryObjectQueryType> affectedObjectQuery;

  @XmlElement(name="EventTypeQuery")
  protected ClassificationNodeQueryType eventTypeQuery;

  @XmlElement(name="UserQuery")
  protected UserQueryType userQuery;

  public List<RegistryObjectQueryType> getAffectedObjectQuery()
  {
    if (this.affectedObjectQuery == null) {
      this.affectedObjectQuery = new ArrayList();
    }
    return this.affectedObjectQuery;
  }

  public ClassificationNodeQueryType getEventTypeQuery()
  {
    return this.eventTypeQuery;
  }

  public void setEventTypeQuery(ClassificationNodeQueryType value)
  {
    this.eventTypeQuery = value;
  }

  public UserQueryType getUserQuery()
  {
    return this.userQuery;
  }

  public void setUserQuery(UserQueryType value)
  {
    this.userQuery = value;
  }
}

