package org.oasis.names.tc.xspa.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"xacmlStatusCodeType", "xacmlStatusDetailType", "xacmlStatusMessage"})
@XmlRootElement(name="xacmlStatusType")
public class XacmlStatusType
{

  @XmlElement(required=true)
  protected String xacmlStatusCodeType;

  @XmlElement(required=true)
  protected XacmlStatusDetailType xacmlStatusDetailType;

  @XmlElement(required=true)
  protected String xacmlStatusMessage;

  public String getXacmlStatusCodeType()
  {
    return this.xacmlStatusCodeType;
  }

  public void setXacmlStatusCodeType(String value)
  {
    this.xacmlStatusCodeType = value;
  }

  public XacmlStatusDetailType getXacmlStatusDetailType()
  {
    return this.xacmlStatusDetailType;
  }

  public void setXacmlStatusDetailType(XacmlStatusDetailType value)
  {
    this.xacmlStatusDetailType = value;
  }

  public String getXacmlStatusMessage()
  {
    return this.xacmlStatusMessage;
  }

  public void setXacmlStatusMessage(String value)
  {
    this.xacmlStatusMessage = value;
  }
}

