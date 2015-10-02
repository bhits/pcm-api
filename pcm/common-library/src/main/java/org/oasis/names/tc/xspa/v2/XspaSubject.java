package org.oasis.names.tc.xspa.v2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"subjectId", "userName", "userId", "subjectPurposeOfUse", "subjectNPI", "subjectLocality", "organization", "organizationId", "subjectStructuredRole", "subjectFunctionalRole", "subjectPermissions", "subjectEmailAddress", "messageId"})
@XmlRootElement(name="xspaSubject")
public class XspaSubject
{

  @XmlElement(required=true)
  protected String subjectId;

  @XmlElement(required=true)
  protected String userName;

  @XmlElement(required=true)
  protected String userId;

  @XmlElement(required=true)
  protected String subjectPurposeOfUse;
  protected String subjectNPI;

  @XmlElement(required=true)
  protected String subjectLocality;

  @XmlElement(required=true)
  protected String organization;
  protected String organizationId;

  @XmlElement(required=true)
  protected List<String> subjectStructuredRole;
  protected List<String> subjectFunctionalRole;
  protected List<String> subjectPermissions;
  protected String subjectEmailAddress;
  protected String messageId;

  public String getSubjectId()
  {
    return this.subjectId;
  }

  public void setSubjectId(String value)
  {
    this.subjectId = value;
  }

  public String getUserName()
  {
    return this.userName;
  }

  public void setUserName(String value)
  {
    this.userName = value;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String value)
  {
    this.userId = value;
  }

  public String getSubjectPurposeOfUse()
  {
    return this.subjectPurposeOfUse;
  }

  public void setSubjectPurposeOfUse(String value)
  {
    this.subjectPurposeOfUse = value;
  }

  public String getSubjectNPI()
  {
    return this.subjectNPI;
  }

  public void setSubjectNPI(String value)
  {
    this.subjectNPI = value;
  }

  public String getSubjectLocality()
  {
    return this.subjectLocality;
  }

  public void setSubjectLocality(String value)
  {
    this.subjectLocality = value;
  }

  public String getOrganization()
  {
    return this.organization;
  }

  public void setOrganization(String value)
  {
    this.organization = value;
  }

  public String getOrganizationId()
  {
    return this.organizationId;
  }

  public void setOrganizationId(String value)
  {
    this.organizationId = value;
  }

  public List<String> getSubjectStructuredRole()
  {
    if (this.subjectStructuredRole == null) {
      this.subjectStructuredRole = new ArrayList();
    }
    return this.subjectStructuredRole;
  }

  public List<String> getSubjectFunctionalRole()
  {
    if (this.subjectFunctionalRole == null) {
      this.subjectFunctionalRole = new ArrayList();
    }
    return this.subjectFunctionalRole;
  }

  public List<String> getSubjectPermissions()
  {
    if (this.subjectPermissions == null) {
      this.subjectPermissions = new ArrayList();
    }
    return this.subjectPermissions;
  }

  public String getSubjectEmailAddress()
  {
    return this.subjectEmailAddress;
  }

  public void setSubjectEmailAddress(String value)
  {
    this.subjectEmailAddress = value;
  }

  public String getMessageId()
  {
    return this.messageId;
  }

  public void setMessageId(String value)
  {
    this.messageId = value;
  }
}

