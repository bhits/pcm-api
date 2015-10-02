package oasis.names.tc.ebxml_regrep.xsd.query._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ClassificationSchemeQueryType", propOrder={"childrenQuery", "nodeTypeQuery"})
public class ClassificationSchemeQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="ChildrenQuery")
  protected List<ClassificationNodeQueryType> childrenQuery;

  @XmlElement(name="NodeTypeQuery")
  protected ClassificationNodeQueryType nodeTypeQuery;

  public List<ClassificationNodeQueryType> getChildrenQuery()
  {
    if (this.childrenQuery == null) {
      this.childrenQuery = new ArrayList();
    }
    return this.childrenQuery;
  }

  public ClassificationNodeQueryType getNodeTypeQuery()
  {
    return this.nodeTypeQuery;
  }

  public void setNodeTypeQuery(ClassificationNodeQueryType value)
  {
    this.nodeTypeQuery = value;
  }
}

