package org.oasis.names.tc.xspa.v2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _PurposeOfUse_QNAME = new QName("", "purposeOfUse");
  private static final QName _GroupName_QNAME = new QName("", "groupName");
  private static final QName _HomeCommunityId_QNAME = new QName("", "homeCommunityId");
  private static final QName _RequestTime_QNAME = new QName("", "requestTime");
  private static final QName _SubjectFunctionalRole_QNAME = new QName("", "subjectFunctionalRole");
  private static final QName _SubjectNPI_QNAME = new QName("", "subjectNPI");
  private static final QName _SubjectEmailAddress_QNAME = new QName("", "subjectEmailAddress");
  private static final QName _OrganizationId_QNAME = new QName("", "organizationId");
  private static final QName _XacmlStatusMessage_QNAME = new QName("", "xacmlStatusMessage");
  private static final QName _XacmlStatusDetail_QNAME = new QName("", "xacmlStatusDetail");
  private static final QName _PdpRequest_QNAME = new QName("", "pdpRequest");
  private static final QName _ResourceId_QNAME = new QName("", "resourceId");
  private static final QName _Organization_QNAME = new QName("", "organization");
  private static final QName _SupportedObligations_QNAME = new QName("", "supportedObligations");
  private static final QName _SubjectPermissions_QNAME = new QName("", "subjectPermissions");
  private static final QName _UserId_QNAME = new QName("", "userId");
  private static final QName _XacmlResultTypeDecision_QNAME = new QName("", "xacmlResultTypeDecision");
  private static final QName _SubjectPurposeOfUse_QNAME = new QName("", "subjectPurposeOfUse");
  private static final QName _ResourceAction_QNAME = new QName("", "resourceAction");
  private static final QName _UserName_QNAME = new QName("", "userName");
  private static final QName _XacmlObligations_QNAME = new QName("", "xacmlObligations");
  private static final QName _ResourceName_QNAME = new QName("", "resourceName");
  private static final QName _ResponseTime_QNAME = new QName("", "responseTime");
  private static final QName _ResourceDestination_QNAME = new QName("", "resourceDestination");
  private static final QName _XacmlResultTypeResourceId_QNAME = new QName("", "xacmlResultTypeResourceId");
  private static final QName _PdpStatus_QNAME = new QName("", "pdpStatus");
  private static final QName _PdpResponse_QNAME = new QName("", "pdpResponse");
  private static final QName _ResourceType_QNAME = new QName("", "resourceType");
  private static final QName _GroupId_QNAME = new QName("", "groupId");
  private static final QName _PdpRefrain_QNAME = new QName("", "pdpRefrain");
  private static final QName _SubjectStructuredRole_QNAME = new QName("", "subjectStructuredRole");
  private static final QName _PdpDecision_QNAME = new QName("", "pdpDecision");
  private static final QName _SubjectLocality_QNAME = new QName("", "subjectLocality");
  private static final QName _SubjectId_QNAME = new QName("", "subjectId");
  private static final QName _XacmlStatusCodeType_QNAME = new QName("", "xacmlStatusCodeType");
  private static final QName _PdpObligation_QNAME = new QName("", "pdpObligation");
  private static final QName _MessageId_QNAME = new QName("", "messageId");
  private static final QName _SupportedRefrainPolicies_QNAME = new QName("", "supportedRefrainPolicies");

  public XacmlStatusDetailType createXacmlStatusDetailType()
  {
    return new XacmlStatusDetailType();
  }

  public XacmlObligationsType createXacmlObligationsType()
  {
    return new XacmlObligationsType();
  }

  public XacmlStatusType createXacmlStatusType()
  {
    return new XacmlStatusType();
  }

  public PolicyEnforcementObject createPolicyEnforcementObject()
  {
    return new PolicyEnforcementObject();
  }

  public XacmlResultType createXacmlResultType()
  {
    return new XacmlResultType();
  }

  public XspaResource createXspaResource()
  {
    return new XspaResource();
  }

  public XspaTestGroup createXspaTestGroup()
  {
    return new XspaTestGroup();
  }

  public XspaTestObject createXspaTestObject()
  {
    return new XspaTestObject();
  }

  public XspaSubject createXspaSubject()
  {
    return new XspaSubject();
  }

  @XmlElementDecl(namespace="", name="purposeOfUse")
  public JAXBElement<String> createPurposeOfUse(String value)
  {
    return new JAXBElement(_PurposeOfUse_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="groupName")
  public JAXBElement<String> createGroupName(String value)
  {
    return new JAXBElement(_GroupName_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="homeCommunityId")
  public JAXBElement<String> createHomeCommunityId(String value)
  {
    return new JAXBElement(_HomeCommunityId_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="requestTime")
  public JAXBElement<XMLGregorianCalendar> createRequestTime(XMLGregorianCalendar value)
  {
    return new JAXBElement(_RequestTime_QNAME, XMLGregorianCalendar.class, null, value);
  }

  @XmlElementDecl(namespace="", name="subjectFunctionalRole")
  public JAXBElement<String> createSubjectFunctionalRole(String value)
  {
    return new JAXBElement(_SubjectFunctionalRole_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="subjectNPI")
  public JAXBElement<String> createSubjectNPI(String value)
  {
    return new JAXBElement(_SubjectNPI_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="subjectEmailAddress")
  public JAXBElement<String> createSubjectEmailAddress(String value)
  {
    return new JAXBElement(_SubjectEmailAddress_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="organizationId")
  public JAXBElement<String> createOrganizationId(String value)
  {
    return new JAXBElement(_OrganizationId_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="xacmlStatusMessage")
  public JAXBElement<String> createXacmlStatusMessage(String value)
  {
    return new JAXBElement(_XacmlStatusMessage_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="xacmlStatusDetail")
  public JAXBElement<String> createXacmlStatusDetail(String value)
  {
    return new JAXBElement(_XacmlStatusDetail_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="pdpRequest")
  public JAXBElement<String> createPdpRequest(String value)
  {
    return new JAXBElement(_PdpRequest_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="resourceId")
  public JAXBElement<String> createResourceId(String value)
  {
    return new JAXBElement(_ResourceId_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="organization")
  public JAXBElement<String> createOrganization(String value)
  {
    return new JAXBElement(_Organization_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="supportedObligations")
  public JAXBElement<String> createSupportedObligations(String value)
  {
    return new JAXBElement(_SupportedObligations_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="subjectPermissions")
  public JAXBElement<String> createSubjectPermissions(String value)
  {
    return new JAXBElement(_SubjectPermissions_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="userId")
  public JAXBElement<String> createUserId(String value)
  {
    return new JAXBElement(_UserId_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="xacmlResultTypeDecision")
  public JAXBElement<String> createXacmlResultTypeDecision(String value)
  {
    return new JAXBElement(_XacmlResultTypeDecision_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="subjectPurposeOfUse")
  public JAXBElement<String> createSubjectPurposeOfUse(String value)
  {
    return new JAXBElement(_SubjectPurposeOfUse_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="resourceAction")
  public JAXBElement<String> createResourceAction(String value)
  {
    return new JAXBElement(_ResourceAction_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="userName")
  public JAXBElement<String> createUserName(String value)
  {
    return new JAXBElement(_UserName_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="xacmlObligations")
  public JAXBElement<String> createXacmlObligations(String value)
  {
    return new JAXBElement(_XacmlObligations_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="resourceName")
  public JAXBElement<String> createResourceName(String value)
  {
    return new JAXBElement(_ResourceName_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="responseTime")
  public JAXBElement<XMLGregorianCalendar> createResponseTime(XMLGregorianCalendar value)
  {
    return new JAXBElement(_ResponseTime_QNAME, XMLGregorianCalendar.class, null, value);
  }

  @XmlElementDecl(namespace="", name="resourceDestination")
  public JAXBElement<String> createResourceDestination(String value)
  {
    return new JAXBElement(_ResourceDestination_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="xacmlResultTypeResourceId")
  public JAXBElement<String> createXacmlResultTypeResourceId(String value)
  {
    return new JAXBElement(_XacmlResultTypeResourceId_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="pdpStatus")
  public JAXBElement<String> createPdpStatus(String value)
  {
    return new JAXBElement(_PdpStatus_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="pdpResponse")
  public JAXBElement<String> createPdpResponse(String value)
  {
    return new JAXBElement(_PdpResponse_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="resourceType")
  public JAXBElement<String> createResourceType(String value)
  {
    return new JAXBElement(_ResourceType_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="groupId")
  public JAXBElement<String> createGroupId(String value)
  {
    return new JAXBElement(_GroupId_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="pdpRefrain")
  public JAXBElement<String> createPdpRefrain(String value)
  {
    return new JAXBElement(_PdpRefrain_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="subjectStructuredRole")
  public JAXBElement<String> createSubjectStructuredRole(String value)
  {
    return new JAXBElement(_SubjectStructuredRole_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="pdpDecision")
  public JAXBElement<String> createPdpDecision(String value)
  {
    return new JAXBElement(_PdpDecision_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="subjectLocality")
  public JAXBElement<String> createSubjectLocality(String value)
  {
    return new JAXBElement(_SubjectLocality_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="subjectId")
  public JAXBElement<String> createSubjectId(String value)
  {
    return new JAXBElement(_SubjectId_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="xacmlStatusCodeType")
  public JAXBElement<String> createXacmlStatusCodeType(String value)
  {
    return new JAXBElement(_XacmlStatusCodeType_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="pdpObligation")
  public JAXBElement<String> createPdpObligation(String value)
  {
    return new JAXBElement(_PdpObligation_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="messageId")
  public JAXBElement<String> createMessageId(String value)
  {
    return new JAXBElement(_MessageId_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="", name="supportedRefrainPolicies")
  public JAXBElement<String> createSupportedRefrainPolicies(String value)
  {
    return new JAXBElement(_SupportedRefrainPolicies_QNAME, String.class, null, value);
  }
}

