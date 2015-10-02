package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ClassificationNodeType", propOrder={"classificationNode"})
public class ClassificationNodeType extends RegistryObjectType
{

  @XmlElement(name="ClassificationNode")
  protected List<ClassificationNodeType> classificationNode;

  @XmlAttribute(name="parent")
  protected String parent;

  @XmlAttribute(name="code")
  protected String code;

  @XmlAttribute(name="path")
  protected String path;

  public List<ClassificationNodeType> getClassificationNode()
  {
    if (this.classificationNode == null) {
      this.classificationNode = new ArrayList();
    }
    return this.classificationNode;
  }

  public String getParent()
  {
    return this.parent;
  }

  public void setParent(String value)
  {
    this.parent = value;
  }

  public String getCode()
  {
    return this.code;
  }

  public void setCode(String value)
  {
    this.code = value;
  }

  public String getPath()
  {
    return this.path;
  }

  public void setPath(String value)
  {
    this.path = value;
  }
}

