package org.oasis.names.tc.xspa.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"xspaSubject", "xspaResource"})
@XmlRootElement(name="xspaTestObject")
public class XspaTestObject
{

  @XmlElement(required=true)
  protected XspaSubject xspaSubject;

  @XmlElement(required=true)
  protected XspaResource xspaResource;

  @XmlAttribute(name="testName")
  protected String testName;

  @XmlAttribute(name="testDescription")
  protected String testDescription;

  public XspaSubject getXspaSubject()
  {
    return this.xspaSubject;
  }

  public void setXspaSubject(XspaSubject value)
  {
    this.xspaSubject = value;
  }

  public XspaResource getXspaResource()
  {
    return this.xspaResource;
  }

  public void setXspaResource(XspaResource value)
  {
    this.xspaResource = value;
  }

  public String getTestName()
  {
    return this.testName;
  }

  public void setTestName(String value)
  {
    this.testName = value;
  }

  public String getTestDescription()
  {
    return this.testDescription;
  }

  public void setTestDescription(String value)
  {
    this.testDescription = value;
  }
}

