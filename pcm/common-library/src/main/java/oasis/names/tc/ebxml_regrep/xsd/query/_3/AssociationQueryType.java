package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AssociationQueryType", propOrder={"associationTypeQuery", "sourceObjectQuery", "targetObjectQuery"})
public class AssociationQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="AssociationTypeQuery")
  protected ClassificationNodeQueryType associationTypeQuery;

  @XmlElement(name="SourceObjectQuery")
  protected RegistryObjectQueryType sourceObjectQuery;

  @XmlElement(name="TargetObjectQuery")
  protected RegistryObjectQueryType targetObjectQuery;

  public ClassificationNodeQueryType getAssociationTypeQuery()
  {
    return this.associationTypeQuery;
  }

  public void setAssociationTypeQuery(ClassificationNodeQueryType value)
  {
    this.associationTypeQuery = value;
  }

  public RegistryObjectQueryType getSourceObjectQuery()
  {
    return this.sourceObjectQuery;
  }

  public void setSourceObjectQuery(RegistryObjectQueryType value)
  {
    this.sourceObjectQuery = value;
  }

  public RegistryObjectQueryType getTargetObjectQuery()
  {
    return this.targetObjectQuery;
  }

  public void setTargetObjectQuery(RegistryObjectQueryType value)
  {
    this.targetObjectQuery = value;
  }
}

