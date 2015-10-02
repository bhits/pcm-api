package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ClassificationType")
public class ClassificationType extends RegistryObjectType
{

  @XmlAttribute(name="classificationScheme")
  protected String classificationScheme;

  @XmlAttribute(name="classifiedObject", required=true)
  protected String classifiedObject;

  @XmlAttribute(name="classificationNode")
  protected String classificationNode;

  @XmlAttribute(name="nodeRepresentation")
  protected String nodeRepresentation;

  public String getClassificationScheme()
  {
    return this.classificationScheme;
  }

  public void setClassificationScheme(String value)
  {
    this.classificationScheme = value;
  }

  public String getClassifiedObject()
  {
    return this.classifiedObject;
  }

  public void setClassifiedObject(String value)
  {
    this.classifiedObject = value;
  }

  public String getClassificationNode()
  {
    return this.classificationNode;
  }

  public void setClassificationNode(String value)
  {
    this.classificationNode = value;
  }

  public String getNodeRepresentation()
  {
    return this.nodeRepresentation;
  }

  public void setNodeRepresentation(String value)
  {
    this.nodeRepresentation = value;
  }
}

