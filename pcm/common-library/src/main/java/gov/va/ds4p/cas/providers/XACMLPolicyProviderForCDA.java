 package gov.va.ds4p.cas.providers;
 
 import gov.va.ds4p.policy.reference.ActUSPrivacyLaw;
 import gov.va.ds4p.policy.reference.ObligationPolicy;
 import gov.va.ds4p.policy.reference.OrgObligationPolicyDocument;
 import gov.va.ds4p.policy.reference.OrganizationPolicy;
 import gov.va.ds4p.policy.reference.OrganizationTaggingRules;
 import gov.va.ds4p.policy.reference.RefrainPolicy;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 
 public class XACMLPolicyProviderForCDA
 {
   StringBuffer buffer = new StringBuffer();
   private static final String ORG_PRIVACY_LAW_TYPE = "USPrivacyLaw";
   private static final String ORG_REFRAIN_POLICY_TYPE = "RefrainPolicy";
   private static final String ORG_DOCUMENT_HANDLING_TYPE = "DocumentHandling";
   private List<String> orgLaw = new ArrayList();
   private List<String> refrainPolicy = new ArrayList();
   private List<String> documentHandling = new ArrayList();
 
   public String createPatientConsentXACMLPolicy(OrganizationPolicy orgPolicy, String patientId, String authorization, List<String> allowedPOU, List<String> allowedRecipients, List<String> redactActions, List<String> maskingActions)
   {
     String res = "";
     createPolicySetHeader(patientId, orgPolicy.getHomeCommunityId());
     createPolicyHeader();
     createPolicyTarget();
     createRuleAuthorization();
     createRuleAllowedPOU(allowedPOU);
     createRuleAllowedRecipients(allowedRecipients);
     createRuleRequiredSensitivityPermissions(maskingActions);
     createEndPolicy();
 
     if (!redactActions.isEmpty()) {
       createObligationPolicyRedact(redactActions);
     }
     if (!maskingActions.isEmpty()) {
       createObligationPolicyMask(maskingActions);
     }
 
     this.orgLaw.clear();
     this.refrainPolicy.clear();
     this.documentHandling.clear();
     getOrganizationalPolicyTags(orgPolicy, "USPrivacyLaw");
     getOrganizationalPolicyTags(orgPolicy, "RefrainPolicy");
     getOrganizationalPolicyTags(orgPolicy, "DocumentHandling");
     if (!this.orgLaw.isEmpty()) {
       createObligationPolicyUSPrivacyLaw();
     }
     if (!this.refrainPolicy.isEmpty()) {
       createObligationPolicyRefrain();
     }
     if (!this.documentHandling.isEmpty()) {
       createObligationPolicyDocumentHandling();
     }
 
     createRuleEmergency();
 
     createEndPolicySet();
     res = this.buffer.toString();
     return res;
   }
 
   private void createPolicySetHeader(String patientId, String homeCommunityId) {
     this.buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
     this.buffer.append("<PolicySet xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" ");
     this.buffer.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
     this.buffer.append("xsi:schemaLocation=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os http://docs.oasis-open.org/xacml/access_control-xacml-2.0-policy-schema-os.xsd\" ");
     this.buffer.append("PolicySetId=\"" + patientId + "-" + homeCommunityId + "\" ");
     this.buffer.append("PolicyCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides\">");
     this.buffer.append("<Target/>");
   }
 
   private void createPolicyHeader() {
     this.buffer.append("<!-- DISCLOSURE AUTHORIZATION POLICY -->");
     this.buffer.append("<Policy PolicyId=\"urn:oasis:names:tc:xspa:1.0:nwhin:exchange:query\" ");
     this.buffer.append("RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\">");
     this.buffer.append("<Description>Denies the request if authorization does not exist.</Description>");
   }
 
   private void createPolicyTarget() {
     this.buffer.append("<Target>");
     this.buffer.append("<Resources>");
 
     this.buffer.append("<Resource>");
     this.buffer.append("<ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">");
     this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentQuery</AttributeValue>");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</ResourceMatch>");
     this.buffer.append("</Resource>");
 
     this.buffer.append("<Resource>");
     this.buffer.append("<ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">");
     this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">DocumentRetrieve</AttributeValue>");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</ResourceMatch>");
     this.buffer.append("</Resource>");
 
     this.buffer.append("</Resources>");
     this.buffer.append("</Target>");
   }
 
   private void createRuleAuthorization() {
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:notauthorized\" Effect=\"Permit\">");
     this.buffer.append("<Description>If request is to disclose then permit.</Description>");
     this.buffer.append("<Target/>");
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">");
     this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">Disclose</AttributeValue>");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:patient:authorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
   }
 
   private void createRuleAllowedPOU(List<String> allowedPOUs) {
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedpous\" Effect=\"Deny\">");
     this.buffer.append("<Description>If request in not in list then deny.</Description>");
     this.buffer.append("<Target/>");
     this.buffer.append("<Condition>");
 
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\">");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
 
     Iterator iter = allowedPOUs.iterator();
     while (iter.hasNext()) {
       String pou = (String)iter.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + pou + "</AttributeValue>");
     }
 
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
   }
 
   private void createRuleAllowedRecipients(List<String> allowedRecipients) {
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:allowedrecipients\" Effect=\"Deny\">");
     this.buffer.append("<Description>If request in not in list then deny.</Description>");
     this.buffer.append("<Target/>");
     this.buffer.append("<Condition>");
 
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\">");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:2.0:subject:subject-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
 
     Iterator iter = allowedRecipients.iterator();
     while (iter.hasNext()) {
       String recipient = (String)iter.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + recipient + "</AttributeValue>");
     }
 
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
   }
 
   private void createRuleRequiredSensitivityPermissions(List<String> maskingActions) {
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:fha:nhinc:docquery:requiredpermissions\" Effect=\"Deny\">");
     this.buffer.append("<Description>If request in not in list then deny.</Description>");
     this.buffer.append("<Target/>");
     this.buffer.append("<Condition>");
 
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\">");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-subset\">");
 
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
 
     Iterator iter = maskingActions.iterator();
     while (iter.hasNext()) {
       String mask = (String)iter.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + mask + "</AttributeValue>");
     }
 
     this.buffer.append("</Apply>");
     this.buffer.append("<SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:subject:sensitivity:privileges\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
 
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
   }
 
   private void createRuleEmergency() {
     this.buffer.append("<!-- EMERGENCY TREATMENT OVERRIDE POLICY -->");
     this.buffer.append("<Policy PolicyId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyPolicy\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\">");
     this.buffer.append("<Target/>");
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:fha:nhinc:anyServiceType:EmergencyRule\" Effect=\"Permit\">");
     this.buffer.append("<Description>Purpose of Use is Emergency so Permit All</Description>");
     this.buffer.append("<Target/>");
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">");
     this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETREAT</AttributeValue>");
     this.buffer.append("<SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
     this.buffer.append("</Policy>");
   }
 
   private void createObligationPolicyRedact(List<String> redactCodes) {
     String resourceName = "DS4PRedactAuthorization";
     this.buffer.append("<!-- DATA REDACTION POLICY -->");
     this.buffer.append("<Policy PolicyId=\"urn:gov:hhs:onc:ds4p:redact\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\">");
     this.buffer.append("<Target>");
     this.buffer.append("<Resources>");
     this.buffer.append("<Resource>");
     this.buffer.append("<ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">");
     this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + resourceName + "</AttributeValue>");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</ResourceMatch>");
     this.buffer.append("</Resource>");
     this.buffer.append("</Resources>");
     this.buffer.append("</Target>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RedactAllowRule\" Effect=\"Permit\">");
     this.buffer.append("<Description>Determine is Redaction is allowed for specific data sensitivity</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:redactauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter = redactCodes.iterator();
     while (iter.hasNext()) {
       String redact = (String)iter.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + redact + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RedactDenyRule\" Effect=\"Deny\">");
     this.buffer.append("<Description>Determine is Redaction is not allowed for specific data sensitivity</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\">");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:redactauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter2 = redactCodes.iterator();
     while (iter2.hasNext()) {
       String redact = (String)iter2.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + redact + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("</Policy>");
   }
 
   private void createObligationPolicyMask(List<String> maskCodes) {
     String resourceName = "DS4PMaskAuthorization";
     this.buffer.append("<!-- DATA MASKING POLICY -->");
     this.buffer.append("<Policy PolicyId=\"urn:gov:hhs:onc:ds4p:redact\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\">");
     this.buffer.append("<Target>");
     this.buffer.append("<Resources>");
     this.buffer.append("<Resource>");
     this.buffer.append("<ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">");
     this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + resourceName + "</AttributeValue>");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</ResourceMatch>");
     this.buffer.append("</Resource>");
     this.buffer.append("</Resources>");
     this.buffer.append("</Target>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:MaskAllowRule\" Effect=\"Permit\">");
     this.buffer.append("<Description>Determine is Masking is allowed for specific data sensitivity</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:maskauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter = maskCodes.iterator();
     while (iter.hasNext()) {
       String mask = (String)iter.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + mask + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:MaskDenyRule\" Effect=\"Deny\">");
     this.buffer.append("<Description>Determine is Masking is not allowed for specific data sensitivity</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\">");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:maskauthorization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter2 = maskCodes.iterator();
     while (iter2.hasNext()) {
       String mask = (String)iter2.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + mask + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("</Policy>");
   }
 
   private void createObligationPolicyUSPrivacyLaw() {
     String resourceName = "DS4PUSPrivacyLaw";
     this.buffer.append("<!-- ORGANIZATION US PRIVACY LAW POLICY -->");
     this.buffer.append("<Policy PolicyId=\"urn:gov:hhs:onc:ds4p:usprivacylaw\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\">");
     this.buffer.append("<Target>");
     this.buffer.append("<Resources>");
     this.buffer.append("<Resource>");
     this.buffer.append("<ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">");
     this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + resourceName + "</AttributeValue>");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</ResourceMatch>");
     this.buffer.append("</Resource>");
     this.buffer.append("</Resources>");
     this.buffer.append("</Target>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:USPrivacyLawAllowRule\" Effect=\"Permit\">");
     this.buffer.append("<Description>Determine if US Privacy is required</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter = this.orgLaw.iterator();
     while (iter.hasNext()) {
       String law = (String)iter.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + law + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:USPrivacyLawDenyRule\" Effect=\"Deny\">");
     this.buffer.append("<Description>Determine if US Privacy Law is not required</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\">");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter2 = this.orgLaw.iterator();
     while (iter2.hasNext()) {
       String law2 = (String)iter2.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + law2 + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("</Policy>");
   }
 
   private void createObligationPolicyRefrain() {
     String resourceName = "DS4PRefrainPolicy";
     this.buffer.append("<!-- ORGANIZATION REFRAIN POLICY -->");
     this.buffer.append("<Policy PolicyId=\"urn:gov:hhs:onc:ds4p:refrainpolicy\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\">");
     this.buffer.append("<Target>");
     this.buffer.append("<Resources>");
     this.buffer.append("<Resource>");
     this.buffer.append("<ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">");
     this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + resourceName + "</AttributeValue>");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</ResourceMatch>");
     this.buffer.append("</Resource>");
     this.buffer.append("</Resources>");
     this.buffer.append("</Target>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RefrainPolicyAllowRule\" Effect=\"Permit\">");
     this.buffer.append("<Description>Determine refrain policy allowed</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter = this.refrainPolicy.iterator();
     while (iter.hasNext()) {
       String refrain = (String)iter.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + refrain + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RefrainPolicyDenyRule\" Effect=\"Deny\">");
     this.buffer.append("<Description>Determine refrain policy is denied</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\">");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter2 = this.refrainPolicy.iterator();
     while (iter2.hasNext()) {
       String refrain2 = (String)iter2.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + refrain2 + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("</Policy>");
   }
 
   private void createObligationPolicyDocumentHandling() {
     String resourceName = "DS4PDocumentHandling";
     this.buffer.append("<!-- ORGANIZATION DOCUMENT HANDLING POLICY -->");
     this.buffer.append("<Policy PolicyId=\"urn:gov:hhs:onc:ds4p:refrainpolicy\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides\">");
     this.buffer.append("<Target>");
     this.buffer.append("<Resources>");
     this.buffer.append("<Resource>");
     this.buffer.append("<ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">");
     this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + resourceName + "</AttributeValue>");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:gov:hhs:fha:nhinc:service-type\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("</ResourceMatch>");
     this.buffer.append("</Resource>");
     this.buffer.append("</Resources>");
     this.buffer.append("</Target>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RefrainPolicyAllowRule\" Effect=\"Permit\">");
     this.buffer.append("<Description>Determine document handling policy is allowed</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:document-handling\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter = this.documentHandling.iterator();
     while (iter.hasNext()) {
       String dh = (String)iter.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + dh + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("<Rule RuleId=\"urn:gov:hhs:onc:ds4p:anyServiceType:RefrainPolicyDenyRule\" Effect=\"Deny\">");
     this.buffer.append("<Description>Determine document handling policy is denied</Description>");
     this.buffer.append("<Target/>");
 
     this.buffer.append("<Condition>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:not\">");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of\">");
     this.buffer.append("<ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:2.0:resource:org:document-handling\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/>");
     this.buffer.append("<Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-bag\">");
     Iterator iter2 = this.documentHandling.iterator();
     while (iter2.hasNext()) {
       String dh2 = (String)iter2.next();
       this.buffer.append("<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">" + dh2 + "</AttributeValue>");
     }
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Apply>");
     this.buffer.append("</Condition>");
     this.buffer.append("</Rule>");
 
     this.buffer.append("</Policy>");
   }
 
   private void createEndPolicy() {
     this.buffer.append("</Policy>");
   }
 
   private void createEndPolicySet() {
     this.buffer.append("</PolicySet>");
   }
 
   private void getOrganizationalPolicyTags(OrganizationPolicy orgPolicy, String tagType) {
     try {
       Iterator iter = orgPolicy.getOrganizationTaggingRules().iterator();
       while (iter.hasNext()) {
         OrganizationTaggingRules r = (OrganizationTaggingRules)iter.next();
         if ("USPrivacyLaw".equals(tagType)) {
           String law = r.getActUSPrivacyLaw().getCode();
           if (!this.orgLaw.contains(law)) {
             this.orgLaw.add(law);
           }
         }
         else if ("RefrainPolicy".equals(tagType)) {
           String ref = r.getRefrainPolicy().getCode();
           if (!this.refrainPolicy.contains(ref)) {
             this.refrainPolicy.add(ref);
           }
         }
         else if ("DocumentHandling".equals(tagType)) {
           String dh = r.getOrgObligationPolicyDocument().getObligationPolicy().getCode();
           if (!this.documentHandling.contains(dh)) {
             this.documentHandling.add(dh);
           }
         }
 
       }
 
     }
     catch (Exception ex)
     {
       ex.printStackTrace();
     }
   }
 }
