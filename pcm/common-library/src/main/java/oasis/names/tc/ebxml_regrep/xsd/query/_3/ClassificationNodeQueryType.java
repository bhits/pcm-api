package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ClassificationNodeQueryType", propOrder={"parentQuery", "childrenQuery"})
public class ClassificationNodeQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="ParentQuery")
  protected RegistryObjectQueryType parentQuery;

  @XmlElement(name="ChildrenQuery")
  protected List<ClassificationNodeQueryType> childrenQuery;

  public RegistryObjectQueryType getParentQuery()
  {
    return this.parentQuery;
  }

  public void setParentQuery(RegistryObjectQueryType value)
  {
    this.parentQuery = value;
  }

  public List<ClassificationNodeQueryType> getChildrenQuery()
  {
    if (this.childrenQuery == null) {
      this.childrenQuery = new ArrayList();
    }
    return this.childrenQuery;
  }
}

