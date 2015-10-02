 package gov.va.ds4p.hcs;
 
 import javax.xml.bind.JAXBElement;
 import javax.xml.bind.annotation.XmlElementDecl;
 import javax.xml.bind.annotation.XmlRegistry;
 import javax.xml.namespace.QName;
 
 @XmlRegistry
 public class ObjectFactory
 {
   private static final QName _AESEncryptedDocumentPayload_QNAME = new QName("", "AESEncryptedDocumentPayload");
   private static final QName _PolicyAttributeValueSet_QNAME = new QName("", "PolicyAttributeValueSet");
   private static final QName _DocumentId_QNAME = new QName("", "DocumentId");
   private static final QName _Confidentiality_QNAME = new QName("", "Confidentiality");
   private static final QName _HL7ConceptCode_QNAME = new QName("", "HL7ConceptCode");
   private static final QName _DocumentType_QNAME = new QName("", "DocumentType");
   private static final QName _PolicyLocatorService_QNAME = new QName("", "PolicyLocatorService");
 
   public CompositeSecuredDocumentSet createCompositeSecuredDocumentSet()
   {
     return new CompositeSecuredDocumentSet();
   }
 
   public SecuredMedicalDocument createSecuredMedicalDocument()
   {
     return new SecuredMedicalDocument();
   }
 
   public OuterPolicyControl createOuterPolicyControl()
   {
     return new OuterPolicyControl();
   }
 
   public ActSecurityPolicyType createActSecurityPolicyType()
   {
     return new ActSecurityPolicyType();
   }
 
   public ActObligationPolicy createActObligationPolicy()
   {
     return new ActObligationPolicy();
   }
 
   public ActRefrainPolicy createActRefrainPolicy()
   {
     return new ActRefrainPolicy();
   }
 
   public InnerPolicyControl createInnerPolicyControl()
   {
     return new InnerPolicyControl();
   }
 
   public ClinicalDocumentPolicyControl createClinicalDocumentPolicyControl()
   {
     return new ClinicalDocumentPolicyControl();
   }
 
   public ActHealthInformationPurposeOfUseReason createActHealthInformationPurposeOfUseReason()
   {
     return new ActHealthInformationPurposeOfUseReason();
   }
 
   public ActInformationSensitivityPrivacyPolicyType createActInformationSensitivityPrivacyPolicyType()
   {
     return new ActInformationSensitivityPrivacyPolicyType();
   }
 
   public ActPolicyType createActPolicyType()
   {
     return new ActPolicyType();
   }
 
   public ActPrivacyPolicyType createActPrivacyPolicyType()
   {
     return new ActPrivacyPolicyType();
   }
 
   @XmlElementDecl(namespace="", name="AESEncryptedDocumentPayload")
   public JAXBElement<String> createAESEncryptedDocumentPayload(String value)
   {
     return new JAXBElement(_AESEncryptedDocumentPayload_QNAME, String.class, null, value);
   }
 
   @XmlElementDecl(namespace="", name="PolicyAttributeValueSet")
   public JAXBElement<String> createPolicyAttributeValueSet(String value)
   {
     return new JAXBElement(_PolicyAttributeValueSet_QNAME, String.class, null, value);
   }
 
   @XmlElementDecl(namespace="", name="DocumentId")
   public JAXBElement<String> createDocumentId(String value)
   {
     return new JAXBElement(_DocumentId_QNAME, String.class, null, value);
   }
 
   @XmlElementDecl(namespace="", name="Confidentiality")
   public JAXBElement<String> createConfidentiality(String value)
   {
     return new JAXBElement(_Confidentiality_QNAME, String.class, null, value);
   }
 
   @XmlElementDecl(namespace="", name="HL7ConceptCode")
   public JAXBElement<String> createHL7ConceptCode(String value)
   {
     return new JAXBElement(_HL7ConceptCode_QNAME, String.class, null, value);
   }
 
   @XmlElementDecl(namespace="", name="DocumentType")
   public JAXBElement<String> createDocumentType(String value)
   {
     return new JAXBElement(_DocumentType_QNAME, String.class, null, value);
   }
 
   @XmlElementDecl(namespace="", name="PolicyLocatorService")
   public JAXBElement<String> createPolicyLocatorService(String value)
   {
     return new JAXBElement(_PolicyLocatorService_QNAME, String.class, null, value);
   }
 }

