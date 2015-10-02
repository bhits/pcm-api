package org.oasis.names.tc.xspa.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"xacmlResultTypeDecision", "xacmlResultTypeResourceId", "xacmlStatusType", "xacmlObligationsType"})
@XmlRootElement(name="xacmlResultType")
public class XacmlResultType
{

  @XmlElement(required=true)
  protected String xacmlResultTypeDecision;

  @XmlElement(required=true)
  protected String xacmlResultTypeResourceId;

  @XmlElement(required=true)
  protected XacmlStatusType xacmlStatusType;

  @XmlElement(required=true)
  protected XacmlObligationsType xacmlObligationsType;

  public String getXacmlResultTypeDecision()
  {
    return this.xacmlResultTypeDecision;
  }

  public void setXacmlResultTypeDecision(String value)
  {
    this.xacmlResultTypeDecision = value;
  }

  public String getXacmlResultTypeResourceId()
  {
    return this.xacmlResultTypeResourceId;
  }

  public void setXacmlResultTypeResourceId(String value)
  {
    this.xacmlResultTypeResourceId = value;
  }

  public XacmlStatusType getXacmlStatusType()
  {
    return this.xacmlStatusType;
  }

  public void setXacmlStatusType(XacmlStatusType value)
  {
    this.xacmlStatusType = value;
  }

  public XacmlObligationsType getXacmlObligationsType()
  {
    return this.xacmlObligationsType;
  }

  public void setXacmlObligationsType(XacmlObligationsType value)
  {
    this.xacmlObligationsType = value;
  }
}

