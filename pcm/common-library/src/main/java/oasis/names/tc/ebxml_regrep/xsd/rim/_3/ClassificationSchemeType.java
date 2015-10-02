package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ClassificationSchemeType", propOrder={"classificationNode"})
public class ClassificationSchemeType extends RegistryObjectType
{

  @XmlElement(name="ClassificationNode")
  protected List<ClassificationNodeType> classificationNode;

  @XmlAttribute(name="isInternal", required=true)
  protected boolean isInternal;

  @XmlAttribute(name="nodeType", required=true)
  protected String nodeType;

  public List<ClassificationNodeType> getClassificationNode()
  {
    if (this.classificationNode == null) {
      this.classificationNode = new ArrayList();
    }
    return this.classificationNode;
  }

  public boolean isIsInternal()
  {
    return this.isInternal;
  }

  public void setIsInternal(boolean value)
  {
    this.isInternal = value;
  }

  public String getNodeType()
  {
    return this.nodeType;
  }

  public void setNodeType(String value)
  {
    this.nodeType = value;
  }
}

