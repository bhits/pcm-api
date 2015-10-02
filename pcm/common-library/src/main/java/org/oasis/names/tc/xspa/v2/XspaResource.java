package org.oasis.names.tc.xspa.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"resourceId", "resourceName", "resourceType", "resourceAction", "resourceDestination"})
@XmlRootElement(name="xspaResource")
public class XspaResource
{

  @XmlElement(required=true)
  protected String resourceId;

  @XmlElement(required=true)
  protected String resourceName;

  @XmlElement(required=true)
  protected String resourceType;

  @XmlElement(required=true)
  protected String resourceAction;
  protected String resourceDestination;

  public String getResourceId()
  {
    return this.resourceId;
  }

  public void setResourceId(String value)
  {
    this.resourceId = value;
  }

  public String getResourceName()
  {
    return this.resourceName;
  }

  public void setResourceName(String value)
  {
    this.resourceName = value;
  }

  public String getResourceType()
  {
    return this.resourceType;
  }

  public void setResourceType(String value)
  {
    this.resourceType = value;
  }

  public String getResourceAction()
  {
    return this.resourceAction;
  }

  public void setResourceAction(String value)
  {
    this.resourceAction = value;
  }

  public String getResourceDestination()
  {
    return this.resourceDestination;
  }

  public void setResourceDestination(String value)
  {
    this.resourceDestination = value;
  }
}

