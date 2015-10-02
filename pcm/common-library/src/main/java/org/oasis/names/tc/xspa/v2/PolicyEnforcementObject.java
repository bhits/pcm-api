package org.oasis.names.tc.xspa.v2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"messageId", "resourceId", "resourceName", "homeCommunityId", "purposeOfUse", "pdpDecision", "pdpStatus", "pdpObligation", "pdpRequest", "pdpResponse", "requestTime", "responseTime", "xacmlResultType"})
@XmlRootElement(name="policyEnforcementObject")
public class PolicyEnforcementObject
{

  @XmlElement(required=true)
  protected String messageId;

  @XmlElement(required=true)
  protected String resourceId;

  @XmlElement(required=true)
  protected String resourceName;

  @XmlElement(required=true)
  protected String homeCommunityId;

  @XmlElement(required=true)
  protected String purposeOfUse;

  @XmlElement(required=true)
  protected String pdpDecision;

  @XmlElement(required=true)
  protected String pdpStatus;
  protected List<String> pdpObligation;

  @XmlElement(required=true)
  protected String pdpRequest;

  @XmlElement(required=true)
  protected String pdpResponse;

  @XmlElement(required=true)
  @XmlSchemaType(name="dateTime")
  protected XMLGregorianCalendar requestTime;

  @XmlElement(required=true)
  @XmlSchemaType(name="dateTime")
  protected XMLGregorianCalendar responseTime;

  @XmlElement(required=true)
  protected XacmlResultType xacmlResultType;

  public String getMessageId()
  {
    return this.messageId;
  }

  public void setMessageId(String value)
  {
    this.messageId = value;
  }

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

  public String getHomeCommunityId()
  {
    return this.homeCommunityId;
  }

  public void setHomeCommunityId(String value)
  {
    this.homeCommunityId = value;
  }

  public String getPurposeOfUse()
  {
    return this.purposeOfUse;
  }

  public void setPurposeOfUse(String value)
  {
    this.purposeOfUse = value;
  }

  public String getPdpDecision()
  {
    return this.pdpDecision;
  }

  public void setPdpDecision(String value)
  {
    this.pdpDecision = value;
  }

  public String getPdpStatus()
  {
    return this.pdpStatus;
  }

  public void setPdpStatus(String value)
  {
    this.pdpStatus = value;
  }

  public List<String> getPdpObligation()
  {
    if (this.pdpObligation == null) {
      this.pdpObligation = new ArrayList();
    }
    return this.pdpObligation;
  }

  public String getPdpRequest()
  {
    return this.pdpRequest;
  }

  public void setPdpRequest(String value)
  {
    this.pdpRequest = value;
  }

  public String getPdpResponse()
  {
    return this.pdpResponse;
  }

  public void setPdpResponse(String value)
  {
    this.pdpResponse = value;
  }

  public XMLGregorianCalendar getRequestTime()
  {
    return this.requestTime;
  }

  public void setRequestTime(XMLGregorianCalendar value)
  {
    this.requestTime = value;
  }

  public XMLGregorianCalendar getResponseTime()
  {
    return this.responseTime;
  }

  public void setResponseTime(XMLGregorianCalendar value)
  {
    this.responseTime = value;
  }

  public XacmlResultType getXacmlResultType()
  {
    return this.xacmlResultType;
  }

  public void setXacmlResultType(XacmlResultType value)
  {
    this.xacmlResultType = value;
  }
}

