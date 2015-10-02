package gov.va.ds4p.policy.reference;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _State_QNAME = new QName("urn:hl7-org:v3", "state");
  private static final QName _City_QNAME = new QName("urn:hl7-org:v3", "city");
  private static final QName _Country_QNAME = new QName("urn:hl7-org:v3", "country");
  private static final QName _PatientTelcom_QNAME = new QName("urn:hl7-org:v3", "patientTelcom");
  private static final QName _StreetAddressLine_QNAME = new QName("urn:hl7-org:v3", "streetAddressLine");
  private static final QName _Description_QNAME = new QName("urn:hl7-org:v3", "Description");
  private static final QName _PatientGender_QNAME = new QName("urn:hl7-org:v3", "patientGender");
  private static final QName _OrganizationName_QNAME = new QName("urn:hl7-org:v3", "organizationName");
  private static final QName _PatientAuthor_QNAME = new QName("urn:hl7-org:v3", "patientAuthor");
  private static final QName _Telcom_QNAME = new QName("urn:hl7-org:v3", "telcom");
  private static final QName _ImageURL_QNAME = new QName("urn:hl7-org:v3", "imageURL");
  private static final QName _PatientConsentConstraint_QNAME = new QName("urn:hl7-org:v3", "PatientConsentConstraint");
  private static final QName _DefaultPatientBirthDate_QNAME = new QName("urn:hl7-org:v3", "defaultPatientBirthDate");
  private static final QName _PostalCode_QNAME = new QName("urn:hl7-org:v3", "postalCode");
  private static final QName _Family_QNAME = new QName("urn:hl7-org:v3", "family");
  private static final QName _County_QNAME = new QName("urn:hl7-org:v3", "county");
  private static final QName _DisplayText_QNAME = new QName("urn:hl7-org:v3", "displayText");
  private static final QName _Given_QNAME = new QName("urn:hl7-org:v3", "given");

  public ApplicableUSLaws createApplicableUSLaws()
  {
    return new ApplicableUSLaws();
  }

  public ActUSPrivacyLaw createActUSPrivacyLaw()
  {
    return new ActUSPrivacyLaw();
  }

  public DefaultCustodianInfo createDefaultCustodianInfo()
  {
    return new DefaultCustodianInfo();
  }

  public Addr createAddr()
  {
    return new Addr();
  }

  public DefaultPatientDemographics createDefaultPatientDemographics()
  {
    return new DefaultPatientDemographics();
  }

  public Name createName()
  {
    return new Name();
  }

  public ImpliedConfidentiality createImpliedConfidentiality()
  {
    return new ImpliedConfidentiality();
  }

  public Confidentiality createConfidentiality()
  {
    return new Confidentiality();
  }

  public ManufacturingModelName createManufacturingModelName()
  {
    return new ManufacturingModelName();
  }

  public OrganizationConsentPolicyInfo createOrganizationConsentPolicyInfo()
  {
    return new OrganizationConsentPolicyInfo();
  }

  public AssignedAuthoringDevice createAssignedAuthoringDevice()
  {
    return new AssignedAuthoringDevice();
  }

  public SoftwareName createSoftwareName()
  {
    return new SoftwareName();
  }

  public HumanReadibleText createHumanReadibleText()
  {
    return new HumanReadibleText();
  }

  public AssignedAuthor createAssignedAuthor()
  {
    return new AssignedAuthor();
  }

  public AssignedPerson createAssignedPerson()
  {
    return new AssignedPerson();
  }

  public XspaPatientObligations createXspaPatientObligations()
  {
    return new XspaPatientObligations();
  }

  public XspaPatientObligation createXspaPatientObligation()
  {
    return new XspaPatientObligation();
  }

  public OrgObligationPolicyEntry createOrgObligationPolicyEntry()
  {
    return new OrgObligationPolicyEntry();
  }

  public ObligationPolicy createObligationPolicy()
  {
    return new ObligationPolicy();
  }

  public AllergyListSensitivityRules createAllergyListSensitivityRules()
  {
    return new AllergyListSensitivityRules();
  }

  public ClinicalTaggingRule createClinicalTaggingRule()
  {
    return new ClinicalTaggingRule();
  }

  public ClinicalFact createClinicalFact()
  {
    return new ClinicalFact();
  }

  public ActReason createActReason()
  {
    return new ActReason();
  }

  public ActInformationSensitivityPolicy createActInformationSensitivityPolicy()
  {
    return new ActInformationSensitivityPolicy();
  }

  public OrgObligationPolicyDocument createOrgObligationPolicyDocument()
  {
    return new OrgObligationPolicyDocument();
  }

  public ClinicalDomain createClinicalDomain()
  {
    return new ClinicalDomain();
  }

  public ApplicableObligationPolicies createApplicableObligationPolicies()
  {
    return new ApplicableObligationPolicies();
  }

  public ApplicableConfidentialities createApplicableConfidentialities()
  {
    return new ApplicableConfidentialities();
  }

  public PatientRequestedAction createPatientRequestedAction()
  {
    return new PatientRequestedAction();
  }

  public PatientSensitivityConstraint createPatientSensitivityConstraint()
  {
    return new PatientSensitivityConstraint();
  }

  public OrganizationPolicy createOrganizationPolicy()
  {
    return new OrganizationPolicy();
  }

  public OrganizationTaggingRules createOrganizationTaggingRules()
  {
    return new OrganizationTaggingRules();
  }

  public RefrainPolicy createRefrainPolicy()
  {
    return new RefrainPolicy();
  }

  public PurposeOfUse createPurposeOfUse()
  {
    return new PurposeOfUse();
  }

  public Author createAuthor()
  {
    return new Author();
  }

  public LabResultsSensitivityRules createLabResultsSensitivityRules()
  {
    return new LabResultsSensitivityRules();
  }

  public ImmunizationsSensitivityRules createImmunizationsSensitivityRules()
  {
    return new ImmunizationsSensitivityRules();
  }

  public ProblemListSensitivityRules createProblemListSensitivityRules()
  {
    return new ProblemListSensitivityRules();
  }

  public ApplicableSensitivityCodes createApplicableSensitivityCodes()
  {
    return new ApplicableSensitivityCodes();
  }

  public DefaultAuthorInfo createDefaultAuthorInfo()
  {
    return new DefaultAuthorInfo();
  }

  public MedicationListSensitivityRules createMedicationListSensitivityRules()
  {
    return new MedicationListSensitivityRules();
  }

  public ApplicableRefrainPolicies createApplicableRefrainPolicies()
  {
    return new ApplicableRefrainPolicies();
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="state")
  public JAXBElement<String> createState(String value)
  {
    return new JAXBElement(_State_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="city")
  public JAXBElement<String> createCity(String value)
  {
    return new JAXBElement(_City_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="country")
  public JAXBElement<String> createCountry(String value)
  {
    return new JAXBElement(_Country_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="patientTelcom")
  public JAXBElement<String> createPatientTelcom(String value)
  {
    return new JAXBElement(_PatientTelcom_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="streetAddressLine")
  public JAXBElement<String> createStreetAddressLine(String value)
  {
    return new JAXBElement(_StreetAddressLine_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="Description")
  public JAXBElement<String> createDescription(String value)
  {
    return new JAXBElement(_Description_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="patientGender")
  public JAXBElement<String> createPatientGender(String value)
  {
    return new JAXBElement(_PatientGender_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="organizationName")
  public JAXBElement<String> createOrganizationName(String value)
  {
    return new JAXBElement(_OrganizationName_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="patientAuthor")
  public JAXBElement<Boolean> createPatientAuthor(Boolean value)
  {
    return new JAXBElement(_PatientAuthor_QNAME, Boolean.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="telcom")
  public JAXBElement<String> createTelcom(String value)
  {
    return new JAXBElement(_Telcom_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="imageURL")
  public JAXBElement<String> createImageURL(String value)
  {
    return new JAXBElement(_ImageURL_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="PatientConsentConstraint")
  public JAXBElement<Object> createPatientConsentConstraint(Object value)
  {
    return new JAXBElement(_PatientConsentConstraint_QNAME, Object.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="defaultPatientBirthDate")
  public JAXBElement<XMLGregorianCalendar> createDefaultPatientBirthDate(XMLGregorianCalendar value)
  {
    return new JAXBElement(_DefaultPatientBirthDate_QNAME, XMLGregorianCalendar.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="postalCode")
  public JAXBElement<String> createPostalCode(String value)
  {
    return new JAXBElement(_PostalCode_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="family")
  public JAXBElement<String> createFamily(String value)
  {
    return new JAXBElement(_Family_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="county")
  public JAXBElement<String> createCounty(String value)
  {
    return new JAXBElement(_County_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="displayText")
  public JAXBElement<String> createDisplayText(String value)
  {
    return new JAXBElement(_DisplayText_QNAME, String.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="given")
  public JAXBElement<String> createGiven(String value)
  {
    return new JAXBElement(_Given_QNAME, String.class, null, value);
  }
}

