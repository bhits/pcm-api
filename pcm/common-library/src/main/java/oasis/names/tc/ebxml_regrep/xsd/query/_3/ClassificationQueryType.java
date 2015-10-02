package oasis.names.tc.ebxml_regrep.xsd.query._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ClassificationQueryType", propOrder={"classificationSchemeQuery", "classifiedObjectQuery", "classificationNodeQuery"})
public class ClassificationQueryType extends RegistryObjectQueryType
{

  @XmlElement(name="ClassificationSchemeQuery")
  protected ClassificationSchemeQueryType classificationSchemeQuery;

  @XmlElement(name="ClassifiedObjectQuery")
  protected RegistryObjectQueryType classifiedObjectQuery;

  @XmlElement(name="ClassificationNodeQuery")
  protected ClassificationNodeQueryType classificationNodeQuery;

  public ClassificationSchemeQueryType getClassificationSchemeQuery()
  {
    return this.classificationSchemeQuery;
  }

  public void setClassificationSchemeQuery(ClassificationSchemeQueryType value)
  {
    this.classificationSchemeQuery = value;
  }

  public RegistryObjectQueryType getClassifiedObjectQuery()
  {
    return this.classifiedObjectQuery;
  }

  public void setClassifiedObjectQuery(RegistryObjectQueryType value)
  {
    this.classifiedObjectQuery = value;
  }

  public ClassificationNodeQueryType getClassificationNodeQuery()
  {
    return this.classificationNodeQuery;
  }

  public void setClassificationNodeQuery(ClassificationNodeQueryType value)
  {
    this.classificationNodeQuery = value;
  }
}

