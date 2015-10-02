 package gov.va.ds4p.cas.providers;
 
 import gov.va.ds4p.policy.reference.Addr;
 import gov.va.ds4p.policy.reference.AssignedAuthor;
 import gov.va.ds4p.policy.reference.AssignedAuthoringDevice;
 import gov.va.ds4p.policy.reference.AssignedPerson;
 import gov.va.ds4p.policy.reference.DefaultCustodianInfo;
 import gov.va.ds4p.policy.reference.DefaultPatientDemographics;
 import gov.va.ds4p.policy.reference.ManufacturingModelName;
 import gov.va.ds4p.policy.reference.Name;
 import gov.va.ds4p.policy.reference.OrganizationConsentPolicyInfo;
 import gov.va.ds4p.policy.reference.OrganizationPolicy;
 import gov.va.ds4p.policy.reference.SoftwareName;
 import java.io.PrintStream;
 import java.io.StringReader;
 import java.io.StringWriter;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.List;
 import java.util.StringTokenizer;
 import javax.xml.bind.JAXBContext;
 import javax.xml.bind.JAXBElement;
 import javax.xml.bind.JAXBException;
 import javax.xml.bind.Marshaller;
 import javax.xml.bind.Unmarshaller;
 import javax.xml.namespace.QName;
 import org.apache.commons.codec.binary.Base64;
 import org.hl7.v3.AD;
 import org.hl7.v3.ActClinicalDocument;
 import org.hl7.v3.ActRelationshipHasComponent;
 import org.hl7.v3.AdxpCity;
 import org.hl7.v3.AdxpCountry;
 import org.hl7.v3.AdxpCounty;
 import org.hl7.v3.AdxpPostalCode;
 import org.hl7.v3.AdxpState;
 import org.hl7.v3.AdxpStreetAddressLine;
 import org.hl7.v3.BinaryDataEncoding;
 import org.hl7.v3.CD;
 import org.hl7.v3.CE;
 import org.hl7.v3.CS;
 import org.hl7.v3.ED;
 import org.hl7.v3.EnFamily;
 import org.hl7.v3.EnGiven;
 import org.hl7.v3.II;
 import org.hl7.v3.IVLTS;
 import org.hl7.v3.IVXBTS;
 import org.hl7.v3.ON;
 import org.hl7.v3.ObjectFactory;
 import org.hl7.v3.PN;
 import org.hl7.v3.POCDMT000040Act;
 import org.hl7.v3.POCDMT000040AssignedAuthor;
 import org.hl7.v3.POCDMT000040AssignedCustodian;
 import org.hl7.v3.POCDMT000040AssignedEntity;
 import org.hl7.v3.POCDMT000040Author;
 import org.hl7.v3.POCDMT000040AuthoringDevice;
 import org.hl7.v3.POCDMT000040ClinicalDocument;
 import org.hl7.v3.POCDMT000040Component2;
 import org.hl7.v3.POCDMT000040Component3;
 import org.hl7.v3.POCDMT000040Criterion;
 import org.hl7.v3.POCDMT000040Custodian;
 import org.hl7.v3.POCDMT000040CustodianOrganization;
 import org.hl7.v3.POCDMT000040DocumentationOf;
 import org.hl7.v3.POCDMT000040Entry;
 import org.hl7.v3.POCDMT000040EntryRelationship;
 import org.hl7.v3.POCDMT000040InformationRecipient;
 import org.hl7.v3.POCDMT000040InfrastructureRootTypeId;
 import org.hl7.v3.POCDMT000040IntendedRecipient;
 import org.hl7.v3.POCDMT000040LegalAuthenticator;
 import org.hl7.v3.POCDMT000040ObservationMedia;
 import org.hl7.v3.POCDMT000040Organization;
 import org.hl7.v3.POCDMT000040Participant2;
 import org.hl7.v3.POCDMT000040Patient;
 import org.hl7.v3.POCDMT000040PatientRole;
 import org.hl7.v3.POCDMT000040Person;
 import org.hl7.v3.POCDMT000040Precondition;
 import org.hl7.v3.POCDMT000040RecordTarget;
 import org.hl7.v3.POCDMT000040Section;
 import org.hl7.v3.POCDMT000040ServiceEvent;
 import org.hl7.v3.POCDMT000040StructuredBody;
 import org.hl7.v3.SC;
 import org.hl7.v3.ST;
 import org.hl7.v3.TEL;
 import org.hl7.v3.TS;
 import org.hl7.v3.XActClassDocumentEntryAct;
 import org.hl7.v3.XActRelationshipEntry;
 import org.hl7.v3.XActRelationshipEntryRelationship;
 import org.hl7.v3.XDocumentActMood;
 import org.hl7.v3.XInformationRecipient;
 import org.hl7.v3.XInformationRecipientRole;
 
 public class ClinicalDocumentProvider
 {
   private POCDMT000040ClinicalDocument clinicalDocument;
 
   public POCDMT000040ClinicalDocument createClinicalDocumentFromXMLString(String xml)
   {
     POCDMT000040ClinicalDocument obj = null;
     try {
       JAXBContext context = JAXBContext.newInstance(new Class[] { POCDMT000040ClinicalDocument.class });
       Unmarshaller unmarshaller = context.createUnmarshaller();
       StringReader sr = new StringReader(xml);
 
       Object o = unmarshaller.unmarshal(sr);
       JAXBElement element = (JAXBElement)o;
       obj = (POCDMT000040ClinicalDocument)element.getValue();
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return obj;
   }
 
   private String createClinicalDocumentStringFromObject(POCDMT000040ClinicalDocument doc) throws JAXBException
   {
     String res = "";
     ObjectFactory factory = new ObjectFactory();
     JAXBElement element = factory.createClinicalDocument(doc);
     try {
       JAXBContext context = JAXBContext.newInstance(new Class[] { POCDMT000040ClinicalDocument.class });
       Marshaller marshaller = context.createMarshaller();
       StringWriter sw = new StringWriter();
       marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
       marshaller.setProperty("jaxb.schemaLocation", "http://www.w3.org/2001/XMLSchema-instance");
 
       marshaller.marshal(element, sw);
       res = sw.toString();
     }
     catch (Exception ex) {
       ex.printStackTrace();
     }
 
     return res;
   }
 
   public String createConsentDirective(OrganizationPolicy orgPolicy, String patientName, String patientId, String patientIdType, String patientBirthDate, String patientGender, String authorization, String intendedPOU, List<String> allowedPOU, String primaryRecipient, List<String> allowedRecipients, List<String> maskingActions, List<String> redactActions, String pdfstring, String xacml)
   {
     String res = "";
     try {
       this.clinicalDocument = new POCDMT000040ClinicalDocument();
 
       this.clinicalDocument.setClassCode(ActClinicalDocument.DOCCLIN);
       this.clinicalDocument.getMoodCode().add("EVN");
 
       CS realmCode = new CS();
       realmCode.setCode("US");
       this.clinicalDocument.getRealmCode().add(realmCode);
 
       POCDMT000040InfrastructureRootTypeId cId = new POCDMT000040InfrastructureRootTypeId();
 
       cId.setExtension("09230");
       cId.setRoot("2.16.840.1.113883.1.3");
       this.clinicalDocument.setTypeId(cId);
 
       List ids = getDocumentTemplates();
       Iterator iter = ids.iterator();
       while (iter.hasNext()) {
         II i = (II)iter.next();
         this.clinicalDocument.getTemplateId().add(i);
       }
 
       II idV = new II();
       idV.setExtension("221");
       idV.setRoot("1.3.6.4.1.4.1.2835.888888");
       this.clinicalDocument.setId(idV);
 
       this.clinicalDocument.setCode(getDocumentCode());
 
       this.clinicalDocument.setTitle(getDocumentTitle());
 
       this.clinicalDocument.setEffectiveTime(getDocumentEffectiveDate());
 
       this.clinicalDocument.setConfidentialityCode(getDocumentConfidentialityCode(orgPolicy, maskingActions, redactActions));
 
       List<POCDMT000040RecordTarget> recordTarget = getDocumentRecordTargets(patientId, patientName, patientGender, patientBirthDate, orgPolicy);
       this.clinicalDocument.getRecordTarget().add(recordTarget.get(0));
 
       POCDMT000040Author author = getDocumentAuthor(orgPolicy);
       this.clinicalDocument.getAuthor().add(author);
 
       POCDMT000040Custodian custodian = getDocumentCustodian(orgPolicy);
       this.clinicalDocument.setCustodian(custodian);
 
       POCDMT000040InformationRecipient recipient = getDocumentInformationRecipient(primaryRecipient);
       this.clinicalDocument.getInformationRecipient().add(recipient);
 
       POCDMT000040LegalAuthenticator authenticator = getDocumentLegalAuthenticator(orgPolicy, patientName);
       this.clinicalDocument.setLegalAuthenticator(authenticator);
 
       POCDMT000040DocumentationOf docOf = getDocumentDocumentOf();
       this.clinicalDocument.getDocumentationOf().add(docOf);
 
       POCDMT000040Component2 comp = getDocumentComponent2(getDocumentConfidentialityCode(orgPolicy, maskingActions, redactActions), intendedPOU, authorization, orgPolicy, maskingActions, redactActions, pdfstring, xacml);
 
       this.clinicalDocument.setComponent(comp);
       try {
         res = createClinicalDocumentStringFromObject(this.clinicalDocument);
       }
       catch (Exception tEx) {
         res = tEx.getMessage();
       }
 
     }
     catch (Exception ex)
     {
       System.out.println(ex.getMessage());
       ex.printStackTrace();
     }
 
     return res;
   }
 
   private String getCurrentTime() {
     String res = "";
     Date dt = new Date();
     SimpleDateFormat sdt = new SimpleDateFormat("yyyyMMddhhmmss");
     try {
       res = sdt.format(dt);
     }
     catch (Exception ex2) {
       ex2.printStackTrace();
     }
 
     return res;
   }
 
   private String getCurrentDate() {
     String res = "";
     Date dt = new Date();
     SimpleDateFormat sdt = new SimpleDateFormat("yyyyMMdd");
     try {
       res = sdt.format(dt);
     }
     catch (Exception ex2) {
       ex2.printStackTrace();
     }
 
     return res;
   }
 
   private String getDefaultEndDate() {
     String res = "";
     Calendar cal = Calendar.getInstance();
     cal.add(1, 5);
     Date dt = cal.getTime();
     SimpleDateFormat sdt = new SimpleDateFormat("yyyyMMdd");
     try {
       res = sdt.format(dt);
     }
     catch (Exception ex2) {
       ex2.printStackTrace();
     }
 
     return res;
   }
 
   private POCDMT000040InfrastructureRootTypeId getDocumentInfrastructureTypeId() {
     POCDMT000040InfrastructureRootTypeId cId = new POCDMT000040InfrastructureRootTypeId();
     cId.setExtension("09230");
     cId.setRoot("2.16.840.1.113883.1.3");
     return cId;
   }
 
   private List<II> getDocumentTemplates() {
     List mList = new ArrayList();
     II t1 = new II();
     II t2 = new II();
     t1.setRoot("2.16.840.1.113883.10.20.3");
     t2.setRoot("2.16.840.1.113883.3.445.1");
     mList.add(t1);
     mList.add(t2);
     return mList;
   }
 
   private II getDocumentId() {
     II i = new II();
     i.setRoot("1.3.6.4.1.4.1.2835.888888");
     i.setExtension("221");
     return i;
   }
 
   private CE getDocumentCode() {
     CE docCode = new CE();
     docCode.setDisplayName("Privacy Policy Acknowledgement Document");
     docCode.setCodeSystemName("LOINC");
     docCode.setCodeSystem("2.16.840.1.113883.6.1");
     docCode.setCode("57016-8");
 
     return docCode;
   }
 
   private ST getDocumentTitle() {
     ST st = new ST();
     st.setMediaType("text/plain");
     st.setRepresentation(BinaryDataEncoding.TXT);
 
     st.getContent().add(new String("Privacy Consent Authorization"));
 
     return st;
   }
 
   private TS getDocumentEffectiveDate() {
     TS ts = new TS();
     ts.setValue(getCurrentTime());
     return ts;
   }
 
   private CE getDocumentConfidentialityCode(OrganizationPolicy orgPolicy, List<String> maskingActions, List<String> redactActions) {
     CE conf = new CE();
     conf.setCodeSystem("2.16.840.1.113883.5.25");
     conf.setCodeSystemName("Confidentiality");
     conf.setCodeSystemVersion("3.0");
 
     String law = orgPolicy.getUsPrivacyLaw();
 
     if ((law.equals("42CFRPart2")) || (law.equals("Title38Section7332")) || (!maskingActions.isEmpty()) || (!redactActions.isEmpty())) {
       conf.setCode("R");
       conf.setDisplayName("R (restricted)");
     }
     else {
       conf.setCode("N");
       conf.setDisplayName("N (normal)");
     }
 
     return conf;
   }
 
   private List<POCDMT000040RecordTarget> getDocumentRecordTargets(String patientId, String patientName, String patientGender, String patientBirthDate, OrganizationPolicy orgPolicy) {
     List targets = new ArrayList();
     POCDMT000040RecordTarget target = new POCDMT000040RecordTarget();
 
     II uniqueId = new II();
     uniqueId.setExtension(patientId);
     uniqueId.setRoot(orgPolicy.getHomeCommunityId());
 
     POCDMT000040Patient patient = new POCDMT000040Patient();
 
     CE gender = new CE();
     gender.setCode(patientGender);
     patient.setAdministrativeGenderCode(gender);
 
     TS birthtime = new TS();
     birthtime.setValue(patientBirthDate);
     patient.setBirthTime(birthtime);
     PN patientname = new PN();
 
     StringTokenizer st = new StringTokenizer(patientName);
     String first = st.nextToken();
     String last = st.nextToken();
 
     patientname.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "given"), EnGiven.class, first));
     patientname.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "family"), EnFamily.class, last));
 
     patient.getName().add(patientname);
 
     AD patientaddr = new AD();
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "streetAddressLine"), AdxpStreetAddressLine.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getStreetAddressLine()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "city"), AdxpCity.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getCity()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "state"), AdxpState.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getState()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "postalCode"), AdxpPostalCode.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getPostalCode()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "county"), AdxpCounty.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getCounty()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "country"), AdxpCountry.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getCountry()));
 
     POCDMT000040PatientRole patientRole = new POCDMT000040PatientRole();
     patientRole.getId().add(uniqueId);
     patientRole.getAddr().add(patientaddr);
     patientRole.setPatient(patient);
     target.setPatientRole(patientRole);
     targets.add(target);
 
     return targets;
   }
 
   private POCDMT000040Author getDocumentAuthor(OrganizationPolicy orgPolicy) {
     POCDMT000040Author author = new POCDMT000040Author();
     POCDMT000040InfrastructureRootTypeId authortemplate = new POCDMT000040InfrastructureRootTypeId();
     authortemplate.setRoot("2.16.840.1.113883.3.445.2");
     author.setTypeId(authortemplate);
 
     TS authortime = new TS();
     authortime.setValue(getCurrentTime());
     author.setTime(authortime);
 
     POCDMT000040AssignedAuthor assigned = new POCDMT000040AssignedAuthor();
     POCDMT000040InfrastructureRootTypeId assignedTemp = new POCDMT000040InfrastructureRootTypeId();
     assignedTemp.setRoot("1.3.5.35.1.4436.7");
     assignedTemp.setExtension("999999999^^^&" + orgPolicy.getHomeCommunityId() + "&ISO");
     assignedTemp.setAssigningAuthorityName(orgPolicy.getOrgName());
     assigned.setTypeId(assignedTemp);
 
     POCDMT000040Person person = new POCDMT000040Person();
     PN authorName = new PN();
     authorName.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "given"), EnGiven.class, orgPolicy.getOrganizationConsentPolicyInfo().getAssignedAuthor().getAssignedPerson().getName().getGiven()));
     authorName.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "family"), EnFamily.class, orgPolicy.getOrganizationConsentPolicyInfo().getAssignedAuthor().getAssignedPerson().getName().getFamily()));
 
     person.getName().add(authorName);
     assigned.setClassCode("PSN");
     assigned.setAssignedPerson(person);
     POCDMT000040AuthoringDevice device = new POCDMT000040AuthoringDevice();
     SC model = new SC();
     SC sft = new SC();
     model.setDisplayName(orgPolicy.getOrganizationConsentPolicyInfo().getAssignedAuthoringDevice().getManufacturingModelName().getDisplayName());
     model.setCode(orgPolicy.getOrganizationConsentPolicyInfo().getAssignedAuthoringDevice().getManufacturingModelName().getCode());
     sft.setDisplayName(orgPolicy.getOrganizationConsentPolicyInfo().getAssignedAuthoringDevice().getSoftwareName().getDisplayName());
     sft.setCode(orgPolicy.getOrganizationConsentPolicyInfo().getAssignedAuthoringDevice().getSoftwareName().getCode());
 
     device.setManufacturerModelName(model);
     device.setSoftwareName(sft);
 
     assigned.setAssignedAuthoringDevice(device);
 
     author.setAssignedAuthor(assigned);
     return author;
   }
 
   private POCDMT000040Custodian getDocumentCustodian(OrganizationPolicy orgPolicy) {
     POCDMT000040Custodian custodian = new POCDMT000040Custodian();
     POCDMT000040AssignedCustodian assignedC = new POCDMT000040AssignedCustodian();
     POCDMT000040CustodianOrganization custOrg = new POCDMT000040CustodianOrganization();
 
     ON orgName = new ON();
     orgName.getUse().add(orgPolicy.getOrgName());
     custOrg.setName(orgName);
     TEL oTEL = new TEL();
     oTEL.setValue(orgPolicy.getOrganizationConsentPolicyInfo().getDefaultCustodianInfo().getTelcom());
 
     custOrg.setTelecom(oTEL);
 
     assignedC.setRepresentedCustodianOrganization(custOrg);
     custodian.setAssignedCustodian(assignedC);
 
     return custodian;
   }
 
   private POCDMT000040Component2 getDocumentComponent2(CE conf, String intendedPurposeOfUse, String authorization, OrganizationPolicy orgPolicy, List<String> maskingActions, List<String> redactActions, String pdf, String xacml) {
     POCDMT000040Component2 comp = new POCDMT000040Component2();
 
     POCDMT000040StructuredBody body = new POCDMT000040StructuredBody();
 
     POCDMT000040Component3 comp2 = new POCDMT000040Component3();
     comp2.setTypeCode(ActRelationshipHasComponent.COMP);
 
     POCDMT000040Section section = new POCDMT000040Section();
     section.getClassCode().add("DOCSECT");
     section.getMoodCode().add("EVN");
 
     II sectTemp = new II();
     sectTemp.setRoot("2.16.840.1.113883.3.445.17");
     section.getTemplateId().add(sectTemp);
 
     ST sectTitle = new ST();
 
     sectTitle.getContent().add(new String("Consent Directive Detail"));
     section.setTitle(sectTitle);
 
     section.getEntry().add(getEntryPurposeOfUse(intendedPurposeOfUse, authorization, orgPolicy, maskingActions, redactActions, pdf, xacml));
 
     section.setConfidentialityCode(conf);
     POCDMT000040InfrastructureRootTypeId sectionId = new POCDMT000040InfrastructureRootTypeId();
     sectionId.setRoot("2.16.840.1.113883.3.445.5");
 
     POCDMT000040Entry entry = new POCDMT000040Entry();
     entry.setTypeCode(XActRelationshipEntry.COMP);
     POCDMT000040InfrastructureRootTypeId entryType = new POCDMT000040InfrastructureRootTypeId();
     entryType.setRoot("2.16.840.1.113883.3.445.4");
     entry.setTypeId(entryType);
     POCDMT000040Act act = new POCDMT000040Act();
     act.setClassCode(XActClassDocumentEntryAct.ACT);
     act.setMoodCode(XDocumentActMood.DEF);
     entry.setAct(act);
 
     comp2.setSection(section);
     body.getComponent().add(comp2);
 
     comp.setStructuredBody(body);
 
     return comp;
   }
 
   private POCDMT000040InformationRecipient getDocumentInformationRecipient(String primaryRecipient) {
     POCDMT000040InformationRecipient recipient = new POCDMT000040InformationRecipient();
     XInformationRecipient x = XInformationRecipient.PRCP;
     recipient.setTypeCode(x);
 
     POCDMT000040IntendedRecipient iRecipient = new POCDMT000040IntendedRecipient();
     XInformationRecipientRole role = XInformationRecipientRole.ASSIGNED;
 
     POCDMT000040InfrastructureRootTypeId cId = new POCDMT000040InfrastructureRootTypeId();
     cId.setExtension("7878");
     cId.setRoot(primaryRecipient);
 
     iRecipient.setTypeId(cId);
 
     POCDMT000040Organization vaOrg = new POCDMT000040Organization();
     vaOrg.setClassCode("ORG");
     vaOrg.setDeterminerCode("INSTANCE");
 
     POCDMT000040InfrastructureRootTypeId oId = new POCDMT000040InfrastructureRootTypeId();
     oId.setRoot("2.16.840.1.113883.4.349");
 
     vaOrg.setTypeId(oId);
 
     iRecipient.setReceivedOrganization(vaOrg);
 
     recipient.setIntendedRecipient(iRecipient);
 
     return recipient;
   }
 
   private POCDMT000040LegalAuthenticator getDocumentLegalAuthenticator(OrganizationPolicy orgPolicy, String patientName)
   {
     POCDMT000040LegalAuthenticator authenticator = new POCDMT000040LegalAuthenticator();
     TS ts = new TS();
     ts.setValue(getCurrentTime());
     authenticator.setTime(ts);
     CS cs = new CS();
     cs.setCode("S");
     authenticator.setSignatureCode(cs);
 
     POCDMT000040AssignedEntity entity = new POCDMT000040AssignedEntity();
     AD patientaddr = new AD();
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "streetAddressLine"), AdxpStreetAddressLine.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getStreetAddressLine()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "city"), AdxpCity.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getCity()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "state"), AdxpState.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getState()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "postalCode"), AdxpPostalCode.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getPostalCode()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "county"), AdxpCounty.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getCounty()));
     patientaddr.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "country"), AdxpCountry.class, orgPolicy.getOrganizationConsentPolicyInfo().getDefaultPatientDemographics().getAddr().getCountry()));
 
     entity.getAddr().add(patientaddr);
 
     PN patientname = new PN();
 
     StringTokenizer st = new StringTokenizer(patientName);
     String first = st.nextToken();
     String last = st.nextToken();
 
     patientname.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "given"), EnGiven.class, first));
     patientname.getContent().add(new JAXBElement(new QName("urn:hl7-org:v3", "family"), EnFamily.class, last));
 
     POCDMT000040Person person = new POCDMT000040Person();
     person.getName().add(patientname);
 
     entity.setAssignedPerson(person);
     authenticator.setAssignedEntity(entity);
 
     return authenticator;
   }
 
   private POCDMT000040DocumentationOf getDocumentDocumentOf()
   {
     POCDMT000040DocumentationOf doc = new POCDMT000040DocumentationOf();
     doc.getTypeCode().add("DOC");
     POCDMT000040ServiceEvent event = new POCDMT000040ServiceEvent();
     event.getMoodCode().add("EVN");
 
     II ii = new II();
     ii.setRoot("2.16.840.1.113883.3.445.3");
     event.getTemplateId().add(ii);
 
     ii = new II();
     ii.setRoot("2.16.840.1.113883.3.72.4.2.5");
     event.getId().add(ii);
 
     CE code = new CE();
     code.setCode("57016-8");
     code.setCodeSystem("2.16.840.1.113883.6.1");
     code.setCodeSystemName("LOINC");
     code.setDisplayName("Privacy Policy Acknowledgement Document");
 
     event.setCode(code);
 
     IVLTS effTime = new IVLTS();
     effTime.getRest().add(new JAXBElement(new QName("urn:hl7-org:v3", "low"), IVXBTS.class, getCurrentDate()));
     effTime.getRest().add(new JAXBElement(new QName("urn:hl7-org:v3", "high"), IVXBTS.class, getDefaultEndDate()));
 
     event.setEffectiveTime(effTime);
 
     doc.setServiceEvent(event);
 
     return doc;
   }
 
   private POCDMT000040Precondition getEntryRelationPrecondition(String actInfomationSensitivity) {
     POCDMT000040Precondition cond = new POCDMT000040Precondition();
 
     POCDMT000040InfrastructureRootTypeId oId = new POCDMT000040InfrastructureRootTypeId();
     oId.setRoot("2.16.840.1.113883.3.445.12");
     cond.setTypeId(oId);
 
     POCDMT000040Criterion criterion = new POCDMT000040Criterion();
     criterion.getMoodCode().add("EVN.CRT");
     criterion.getClassCode().add("COND");
 
     CD cd = new CD();
     cd.setCode(actInfomationSensitivity);
     cd.setDisplayName(actInfomationSensitivity);
     cd.setCodeSystem("2.16.840.1.113883.1.11.20429");
     cd.setCodeSystemName("ActInformationSensitivity");
 
     criterion.setCode(cd);
 
     cond.setCriterion(criterion);
     return cond;
   }
 
   private POCDMT000040Entry getEntryPurposeOfUse(String actGeneralReason, String authorization, OrganizationPolicy orgPolicy, List<String> maskingActions, List<String> redactActions, String pdfString, String xacmlPolicy) {
     POCDMT000040Entry entry = new POCDMT000040Entry();
     XActRelationshipEntry actx = XActRelationshipEntry.COMP;
     entry.setTypeCode(actx);
 
     POCDMT000040InfrastructureRootTypeId oId = new POCDMT000040InfrastructureRootTypeId();
     oId.setRoot("2.16.840.1.113883.3.445.4");
     entry.setTypeId(oId);
 
     POCDMT000040Act act = new POCDMT000040Act();
     XActClassDocumentEntryAct actClass = XActClassDocumentEntryAct.ACT;
     XDocumentActMood actMood = XDocumentActMood.DEF;
     entry.setAct(act);
 
     POCDMT000040InfrastructureRootTypeId aId = new POCDMT000040InfrastructureRootTypeId();
     aId.setRoot("2.16.840.1.113883.3.445.5");
     act.setTypeId(oId);
 
     CD code = new CD();
     code.setCode(actGeneralReason);
     code.setCodeSystem("2.16.840.1.113883.5.8");
     code.setCodeSystemName("ActReason");
     code.setDisplayName(actGeneralReason);
 
     act.setCode(code);
 
     CS status = new CS();
     status.setCode("active");
 
     act.setStatusCode(status);
 
     POCDMT000040Participant2 participant = new POCDMT000040Participant2();
     participant.getTypeCode().add("IRCP");
     POCDMT000040InfrastructureRootTypeId pId = new POCDMT000040InfrastructureRootTypeId();
     pId.setRoot("2.16.840.1.113883.3.445.6");
     participant.setTypeId(aId);
     act.getParticipant().add(participant);
 
     act.getEntryRelationship().add(getDisclosure(authorization));
     act.getEntryRelationship().add(getPrivacyObligations(orgPolicy));
 
     Iterator mIter = maskingActions.iterator();
     while (mIter.hasNext()) {
       String mask = (String)mIter.next();
       act.getPrecondition().add(getEntryRelationPrecondition(mask));
     }
 
     Iterator rIter = redactActions.iterator();
     while (rIter.hasNext()) {
       String redact = (String)rIter.next();
       act.getPrecondition().add(getEntryRelationPrecondition(redact));
     }
 
     act.getEntryRelationship().add(getPDFValue(pdfString));
 
     act.getEntryRelationship().add(getXACMLValue(xacmlPolicy));
 
     entry.setAct(act);
 
     return entry;
   }
 
   private POCDMT000040EntryRelationship getDisclosure(String authorization) {
     POCDMT000040EntryRelationship eR = new POCDMT000040EntryRelationship();
     XActRelationshipEntryRelationship xC = XActRelationshipEntryRelationship.COMP;
     eR.setTypeCode(xC);
 
     eR.setContextConductionInd(Boolean.TRUE);
 
     POCDMT000040Act act = new POCDMT000040Act();
     XActClassDocumentEntryAct aClass = XActClassDocumentEntryAct.ACT;
     act.setClassCode(aClass);
 
     XDocumentActMood mood = XDocumentActMood.DEF;
     act.setMoodCode(mood);
 
     if (authorization.equals("Permit")) {
       act.setNegationInd(Boolean.FALSE);
     }
     else {
       act.setNegationInd(Boolean.TRUE);
     }
 
     CD code = new CD();
 
     code.setCode("DISCLOSE");
     code.setCodeSystem("2.16.840.1.113883.5.4");
     code.setCodeSystemName("ActConsentType");
     code.setDisplayName("Disclose");
 
     act.setCode(code);
 
     eR.setAct(act);
 
     return eR;
   }
 
   private POCDMT000040EntryRelationship getPrivacyObligations(OrganizationPolicy orgPolicy) {
     POCDMT000040EntryRelationship obl = new POCDMT000040EntryRelationship();
     String law = orgPolicy.getUsPrivacyLaw();
 
     if ((law.equals("42CFRPart2")) || (law.equals("Title38Section7332"))) {
       obl.setContextConductionInd(Boolean.TRUE);
 
       XActRelationshipEntryRelationship xR = XActRelationshipEntryRelationship.COMP;
       obl.setTypeCode(xR);
       II pId = new II();
       pId.setRoot("2.16.840.1.113883.3.445.13");
       obl.getTemplateId().add(pId);
 
       POCDMT000040Act act = new POCDMT000040Act();
       XDocumentActMood mood = XDocumentActMood.DEF;
       act.setMoodCode(mood);
 
       XActClassDocumentEntryAct dClass = XActClassDocumentEntryAct.CONS;
       act.setClassCode(dClass);
 
       CD cd = new CD();
       cd.setCode(law);
       cd.setCodeSystem("2.16.840.1.113883.5.1138");
       cd.setCodeSystemName("ActUSPrivacyLaw");
       cd.setDisplayName(law);
 
       act.setCode(cd);
 
       POCDMT000040Precondition cond = new POCDMT000040Precondition();
       II oId = new II();
       oId.setRoot("2.16.840.1.113883.3.445.14");
       cond.getTemplateId().add(oId);
 
       POCDMT000040Criterion criterion = new POCDMT000040Criterion();
       criterion.getMoodCode().add("EVN.CRT");
       criterion.getClassCode().add("OBS");
 
       CD c2 = new CD();
       c2.setCode("NORDSCLCD");
       c2.setDisplayName("NORDSCLCD");
       c2.setCodeSystem("2.16.840.1.113883.1.11.20446");
       c2.setCodeSystemName("RefrainPolicy");
 
       criterion.setCode(c2);
       cond.setCriterion(criterion);
       act.getPrecondition().add(cond);
       obl.setAct(act);
     }
     else
     {
       obl = null;
     }
 
     return obl;
   }
 
   private POCDMT000040EntryRelationship getPDFValue(String pdfString) {
     POCDMT000040EntryRelationship pdf = new POCDMT000040EntryRelationship();
     II id = new II();
     id.setRoot("2.16.840.1.113883.3.445.15");
     POCDMT000040ObservationMedia media = new POCDMT000040ObservationMedia();
     media.getMoodCode().add("EVN");
     media.getClassCode().add("OBS");
 
     ED ed = new ED();
     ed.setMediaType("application/pdf");
     ed.setRepresentation(BinaryDataEncoding.B_64);
     String pdfArray = Base64.encodeBase64String(pdfString.getBytes());
     ed.getContent().add(pdfArray);
 
     media.setValue(ed);
     pdf.setObservationMedia(media);
     pdf.getTemplateId().add(id);
     return pdf;
   }
 
   private POCDMT000040EntryRelationship getXACMLValue(String policy) {
     POCDMT000040EntryRelationship xacml = new POCDMT000040EntryRelationship();
     II id = new II();
     id.setRoot("2.16.840.1.113883.3.445.16");
     POCDMT000040ObservationMedia media = new POCDMT000040ObservationMedia();
     media.getMoodCode().add("EVN");
     media.getClassCode().add("OBS");
 
     ED ed = new ED();
     ed.setMediaType("application/xacml+xml");
     ed.setRepresentation(BinaryDataEncoding.B_64);
     String xacmlarray = Base64.encodeBase64String(policy.getBytes());
     ed.getContent().add(xacmlarray);
     media.setValue(ed);
     xacml.setObservationMedia(media);
     xacml.getTemplateId().add(id);
     return xacml;
   }
 }
