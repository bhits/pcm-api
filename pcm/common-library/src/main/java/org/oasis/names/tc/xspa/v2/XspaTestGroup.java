package org.oasis.names.tc.xspa.v2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"xspaTestObject"})
@XmlRootElement(name="xspaTestGroup")
public class XspaTestGroup
{

  @XmlElement(required=true)
  protected List<XspaTestObject> xspaTestObject;

  @XmlAttribute(name="testGroupName")
  protected String testGroupName;

  @XmlAttribute(name="testGroupDescription")
  protected String testGroupDescription;

  public List<XspaTestObject> getXspaTestObject()
  {
    if (this.xspaTestObject == null) {
      this.xspaTestObject = new ArrayList();
    }
    return this.xspaTestObject;
  }

  public String getTestGroupName()
  {
    return this.testGroupName;
  }

  public void setTestGroupName(String value)
  {
    this.testGroupName = value;
  }

  public String getTestGroupDescription()
  {
    return this.testGroupDescription;
  }

  public void setTestGroupDescription(String value)
  {
    this.testGroupDescription = value;
  }
}

