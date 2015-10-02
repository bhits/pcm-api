package oasis.names.tc.ebxml_regrep.xsd.rim._3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="AssociationType1")
public class AssociationType1 extends RegistryObjectType
{

  @XmlAttribute(name="associationType", required=true)
  protected String associationType;

  @XmlAttribute(name="sourceObject", required=true)
  protected String sourceObject;

  @XmlAttribute(name="targetObject", required=true)
  protected String targetObject;

  public String getAssociationType()
  {
    return this.associationType;
  }

  public void setAssociationType(String value)
  {
    this.associationType = value;
  }

  public String getSourceObject()
  {
    return this.sourceObject;
  }

  public void setSourceObject(String value)
  {
    this.sourceObject = value;
  }

  public String getTargetObject()
  {
    return this.targetObject;
  }

  public void setTargetObject(String value)
  {
    this.targetObject = value;
  }
}

