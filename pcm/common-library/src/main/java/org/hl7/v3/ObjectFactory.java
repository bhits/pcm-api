package org.hl7.v3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory
{
  private static final QName _ClinicalDocument_QNAME = new QName("urn:hl7-org:v3", "ClinicalDocument");
  private static final QName _ENSuffix_QNAME = new QName("urn:hl7-org:v3", "suffix");
  private static final QName _ENDelimiter_QNAME = new QName("urn:hl7-org:v3", "delimiter");
  private static final QName _ENValidTime_QNAME = new QName("urn:hl7-org:v3", "validTime");
  private static final QName _ENPrefix_QNAME = new QName("urn:hl7-org:v3", "prefix");
  private static final QName _ENFamily_QNAME = new QName("urn:hl7-org:v3", "family");
  private static final QName _ENGiven_QNAME = new QName("urn:hl7-org:v3", "given");
  private static final QName _StrucDocItemTable_QNAME = new QName("urn:hl7-org:v3", "table");
  private static final QName _StrucDocItemList_QNAME = new QName("urn:hl7-org:v3", "list");
  private static final QName _StrucDocItemCaption_QNAME = new QName("urn:hl7-org:v3", "caption");
  private static final QName _StrucDocItemRenderMultiMedia_QNAME = new QName("urn:hl7-org:v3", "renderMultiMedia");
  private static final QName _StrucDocItemBr_QNAME = new QName("urn:hl7-org:v3", "br");
  private static final QName _StrucDocItemLinkHtml_QNAME = new QName("urn:hl7-org:v3", "linkHtml");
  private static final QName _StrucDocItemSup_QNAME = new QName("urn:hl7-org:v3", "sup");
  private static final QName _StrucDocItemSub_QNAME = new QName("urn:hl7-org:v3", "sub");
  private static final QName _StrucDocItemContent_QNAME = new QName("urn:hl7-org:v3", "content");
  private static final QName _StrucDocItemFootnoteRef_QNAME = new QName("urn:hl7-org:v3", "footnoteRef");
  private static final QName _StrucDocItemFootnote_QNAME = new QName("urn:hl7-org:v3", "footnote");
  private static final QName _StrucDocItemParagraph_QNAME = new QName("urn:hl7-org:v3", "paragraph");
  private static final QName _IVLINTHigh_QNAME = new QName("urn:hl7-org:v3", "high");
  private static final QName _IVLINTLow_QNAME = new QName("urn:hl7-org:v3", "low");
  private static final QName _IVLINTCenter_QNAME = new QName("urn:hl7-org:v3", "center");
  private static final QName _IVLINTWidth_QNAME = new QName("urn:hl7-org:v3", "width");
  private static final QName _ADDeliveryModeIdentifier_QNAME = new QName("urn:hl7-org:v3", "deliveryModeIdentifier");
  private static final QName _ADHouseNumber_QNAME = new QName("urn:hl7-org:v3", "houseNumber");
  private static final QName _ADState_QNAME = new QName("urn:hl7-org:v3", "state");
  private static final QName _ADCity_QNAME = new QName("urn:hl7-org:v3", "city");
  private static final QName _ADAdditionalLocator_QNAME = new QName("urn:hl7-org:v3", "additionalLocator");
  private static final QName _ADStreetAddressLine_QNAME = new QName("urn:hl7-org:v3", "streetAddressLine");
  private static final QName _ADDeliveryInstallationArea_QNAME = new QName("urn:hl7-org:v3", "deliveryInstallationArea");
  private static final QName _ADStreetNameType_QNAME = new QName("urn:hl7-org:v3", "streetNameType");
  private static final QName _ADDeliveryInstallationQualifier_QNAME = new QName("urn:hl7-org:v3", "deliveryInstallationQualifier");
  private static final QName _ADDirection_QNAME = new QName("urn:hl7-org:v3", "direction");
  private static final QName _ADCensusTract_QNAME = new QName("urn:hl7-org:v3", "censusTract");
  private static final QName _ADUnitID_QNAME = new QName("urn:hl7-org:v3", "unitID");
  private static final QName _ADPostalCode_QNAME = new QName("urn:hl7-org:v3", "postalCode");
  private static final QName _ADStreetName_QNAME = new QName("urn:hl7-org:v3", "streetName");
  private static final QName _ADDeliveryInstallationType_QNAME = new QName("urn:hl7-org:v3", "deliveryInstallationType");
  private static final QName _ADStreetNameBase_QNAME = new QName("urn:hl7-org:v3", "streetNameBase");
  private static final QName _ADDeliveryMode_QNAME = new QName("urn:hl7-org:v3", "deliveryMode");
  private static final QName _ADPostBox_QNAME = new QName("urn:hl7-org:v3", "postBox");
  private static final QName _ADCountry_QNAME = new QName("urn:hl7-org:v3", "country");
  private static final QName _ADDeliveryAddressLine_QNAME = new QName("urn:hl7-org:v3", "deliveryAddressLine");
  private static final QName _ADUseablePeriod_QNAME = new QName("urn:hl7-org:v3", "useablePeriod");
  private static final QName _ADCareOf_QNAME = new QName("urn:hl7-org:v3", "careOf");
  private static final QName _ADUnitType_QNAME = new QName("urn:hl7-org:v3", "unitType");
  private static final QName _ADPrecinct_QNAME = new QName("urn:hl7-org:v3", "precinct");
  private static final QName _ADHouseNumberNumeric_QNAME = new QName("urn:hl7-org:v3", "houseNumberNumeric");
  private static final QName _ADBuildingNumberSuffix_QNAME = new QName("urn:hl7-org:v3", "buildingNumberSuffix");
  private static final QName _ADCounty_QNAME = new QName("urn:hl7-org:v3", "county");

  public POCDMT000040ClinicalDocument createPOCDMT000040ClinicalDocument()
  {
    return new POCDMT000040ClinicalDocument();
  }

  public POCDMT000040Specimen createPOCDMT000040Specimen()
  {
    return new POCDMT000040Specimen();
  }

  public POCDMT000040RegionOfInterestValue createPOCDMT000040RegionOfInterestValue()
  {
    return new POCDMT000040RegionOfInterestValue();
  }

  public BXITIVLPQ createBXITIVLPQ()
  {
    return new BXITIVLPQ();
  }

  public ED createED()
  {
    return new ED();
  }

  public StrucDocBr createStrucDocBr()
  {
    return new StrucDocBr();
  }

  public POCDMT000040ReferenceRange createPOCDMT000040ReferenceRange()
  {
    return new POCDMT000040ReferenceRange();
  }

  public POCDMT000040CustodianOrganization createPOCDMT000040CustodianOrganization()
  {
    return new POCDMT000040CustodianOrganization();
  }

  public POCDMT000040ExternalDocument createPOCDMT000040ExternalDocument()
  {
    return new POCDMT000040ExternalDocument();
  }

  public POCDMT000040Product createPOCDMT000040Product()
  {
    return new POCDMT000040Product();
  }

  public POCDMT000040ExternalProcedure createPOCDMT000040ExternalProcedure()
  {
    return new POCDMT000040ExternalProcedure();
  }

  public AdxpDeliveryMode createAdxpDeliveryMode()
  {
    return new AdxpDeliveryMode();
  }

  public EN createEN()
  {
    return new EN();
  }

  public ANYNonNull createANYNonNull()
  {
    return new ANYNonNull();
  }

  public StrucDocTitleFootnote createStrucDocTitleFootnote()
  {
    return new StrucDocTitleFootnote();
  }

  public SXCMPQ createSXCMPQ()
  {
    return new SXCMPQ();
  }

  public StrucDocColgroup createStrucDocColgroup()
  {
    return new StrucDocColgroup();
  }

  public POCDMT000040AssociatedEntity createPOCDMT000040AssociatedEntity()
  {
    return new POCDMT000040AssociatedEntity();
  }

  public POCDMT000040MaintainedEntity createPOCDMT000040MaintainedEntity()
  {
    return new POCDMT000040MaintainedEntity();
  }

  public AdxpDeliveryInstallationType createAdxpDeliveryInstallationType()
  {
    return new AdxpDeliveryInstallationType();
  }

  public AdxpDelimiter createAdxpDelimiter()
  {
    return new AdxpDelimiter();
  }

  public INT createINT()
  {
    return new INT();
  }

  public EIVLTS createEIVLTS()
  {
    return new EIVLTS();
  }

  public POCDMT000040RecordTarget createPOCDMT000040RecordTarget()
  {
    return new POCDMT000040RecordTarget();
  }

  public POCDMT000040ExternalObservation createPOCDMT000040ExternalObservation()
  {
    return new POCDMT000040ExternalObservation();
  }

  public POCDMT000040Device createPOCDMT000040Device()
  {
    return new POCDMT000040Device();
  }

  public UVPTS createUVPTS()
  {
    return new UVPTS();
  }

  public POCDMT000040SubstanceAdministration createPOCDMT000040SubstanceAdministration()
  {
    return new POCDMT000040SubstanceAdministration();
  }

  public POCDMT000040Custodian createPOCDMT000040Custodian()
  {
    return new POCDMT000040Custodian();
  }

  public POCDMT000040Component1 createPOCDMT000040Component1()
  {
    return new POCDMT000040Component1();
  }

  public POCDMT000040Component2 createPOCDMT000040Component2()
  {
    return new POCDMT000040Component2();
  }

  public POCDMT000040Component3 createPOCDMT000040Component3()
  {
    return new POCDMT000040Component3();
  }

  public POCDMT000040Component4 createPOCDMT000040Component4()
  {
    return new POCDMT000040Component4();
  }

  public POCDMT000040Component5 createPOCDMT000040Component5()
  {
    return new POCDMT000040Component5();
  }

  public AdxpPrecinct createAdxpPrecinct()
  {
    return new AdxpPrecinct();
  }

  public POCDMT000040Consent createPOCDMT000040Consent()
  {
    return new POCDMT000040Consent();
  }

  public POCDMT000040ParticipantRole createPOCDMT000040ParticipantRole()
  {
    return new POCDMT000040ParticipantRole();
  }

  public POCDMT000040Reference createPOCDMT000040Reference()
  {
    return new POCDMT000040Reference();
  }

  public POCDMT000040Participant1 createPOCDMT000040Participant1()
  {
    return new POCDMT000040Participant1();
  }

  public POCDMT000040Participant2 createPOCDMT000040Participant2()
  {
    return new POCDMT000040Participant2();
  }

  public POCDMT000040Author createPOCDMT000040Author()
  {
    return new POCDMT000040Author();
  }

  public POCDMT000040Subject createPOCDMT000040Subject()
  {
    return new POCDMT000040Subject();
  }

  public AD createAD()
  {
    return new AD();
  }

  public AdxpStreetAddressLine createAdxpStreetAddressLine()
  {
    return new AdxpStreetAddressLine();
  }

  public StrucDocFootnoteRef createStrucDocFootnoteRef()
  {
    return new StrucDocFootnoteRef();
  }

  public POCDMT000040RelatedDocument createPOCDMT000040RelatedDocument()
  {
    return new POCDMT000040RelatedDocument();
  }

  public SXCMMO createSXCMMO()
  {
    return new SXCMMO();
  }

  public BN createBN()
  {
    return new BN();
  }

  public BL createBL()
  {
    return new BL();
  }

  public CV createCV()
  {
    return new CV();
  }

  public EnDelimiter createEnDelimiter()
  {
    return new EnDelimiter();
  }

  public CS createCS()
  {
    return new CS();
  }

  public SLISTPQ createSLISTPQ()
  {
    return new SLISTPQ();
  }

  public StrucDocParagraph createStrucDocParagraph()
  {
    return new StrucDocParagraph();
  }

  public CE createCE()
  {
    return new CE();
  }

  public CD createCD()
  {
    return new CD();
  }

  public AdxpUnitType createAdxpUnitType()
  {
    return new AdxpUnitType();
  }

  public CR createCR()
  {
    return new CR();
  }

  public POCDMT000040AuthoringDevice createPOCDMT000040AuthoringDevice()
  {
    return new POCDMT000040AuthoringDevice();
  }

  public CO createCO()
  {
    return new CO();
  }

  public IVLPPDTS createIVLPPDTS()
  {
    return new IVLPPDTS();
  }

  public AdxpCountry createAdxpCountry()
  {
    return new AdxpCountry();
  }

  public AdxpHouseNumberNumeric createAdxpHouseNumberNumeric()
  {
    return new AdxpHouseNumberNumeric();
  }

  public POCDMT000040Authorization createPOCDMT000040Authorization()
  {
    return new POCDMT000040Authorization();
  }

  public StrucDocTable createStrucDocTable()
  {
    return new StrucDocTable();
  }

  public IVXBREAL createIVXBREAL()
  {
    return new IVXBREAL();
  }

  public POCDMT000040InFulfillmentOf createPOCDMT000040InFulfillmentOf()
  {
    return new POCDMT000040InFulfillmentOf();
  }

  public POCDMT000040ManufacturedProduct createPOCDMT000040ManufacturedProduct()
  {
    return new POCDMT000040ManufacturedProduct();
  }

  public AdxpUnitID createAdxpUnitID()
  {
    return new AdxpUnitID();
  }

  public AdxpCareOf createAdxpCareOf()
  {
    return new AdxpCareOf();
  }

  public POCDMT000040Observation createPOCDMT000040Observation()
  {
    return new POCDMT000040Observation();
  }

  public POCDMT000040SpecimenRole createPOCDMT000040SpecimenRole()
  {
    return new POCDMT000040SpecimenRole();
  }

  public StrucDocFootnote createStrucDocFootnote()
  {
    return new StrucDocFootnote();
  }

  public EnPrefix createEnPrefix()
  {
    return new EnPrefix();
  }

  public POCDMT000040Consumable createPOCDMT000040Consumable()
  {
    return new POCDMT000040Consumable();
  }

  public POCDMT000040Organization createPOCDMT000040Organization()
  {
    return new POCDMT000040Organization();
  }

  public MO createMO()
  {
    return new MO();
  }

  public POCDMT000040Birthplace createPOCDMT000040Birthplace()
  {
    return new POCDMT000040Birthplace();
  }

  public IVXBTS createIVXBTS()
  {
    return new IVXBTS();
  }

  public ON createON()
  {
    return new ON();
  }

  public PPDPQ createPPDPQ()
  {
    return new PPDPQ();
  }

  public TEL createTEL()
  {
    return new TEL();
  }

  public POCDMT000040ObservationMedia createPOCDMT000040ObservationMedia()
  {
    return new POCDMT000040ObservationMedia();
  }

  public RTOPQPQ createRTOPQPQ()
  {
    return new RTOPQPQ();
  }

  public PN createPN()
  {
    return new PN();
  }

  public EnSuffix createEnSuffix()
  {
    return new EnSuffix();
  }

  public AdxpDeliveryInstallationQualifier createAdxpDeliveryInstallationQualifier()
  {
    return new AdxpDeliveryInstallationQualifier();
  }

  public AdxpCounty createAdxpCounty()
  {
    return new AdxpCounty();
  }

  public POCDMT000040Material createPOCDMT000040Material()
  {
    return new POCDMT000040Material();
  }

  public POCDMT000040Place createPOCDMT000040Place()
  {
    return new POCDMT000040Place();
  }

  public POCDMT000040NonXMLBody createPOCDMT000040NonXMLBody()
  {
    return new POCDMT000040NonXMLBody();
  }

  public StrucDocList createStrucDocList()
  {
    return new StrucDocList();
  }

  public IVLPPDPQ createIVLPPDPQ()
  {
    return new IVLPPDPQ();
  }

  public HXITPQ createHXITPQ()
  {
    return new HXITPQ();
  }

  public StrucDocThead createStrucDocThead()
  {
    return new StrucDocThead();
  }

  public AdxpHouseNumber createAdxpHouseNumber()
  {
    return new AdxpHouseNumber();
  }

  public StrucDocTfoot createStrucDocTfoot()
  {
    return new StrucDocTfoot();
  }

  public EIVLPPDTS createEIVLPPDTS()
  {
    return new EIVLPPDTS();
  }

  public II createII()
  {
    return new II();
  }

  public StrucDocCol createStrucDocCol()
  {
    return new StrucDocCol();
  }

  public IVLINT createIVLINT()
  {
    return new IVLINT();
  }

  public IVXBPQ createIVXBPQ()
  {
    return new IVXBPQ();
  }

  public SXCMTS createSXCMTS()
  {
    return new SXCMTS();
  }

  public POCDMT000040RegionOfInterest createPOCDMT000040RegionOfInterest()
  {
    return new POCDMT000040RegionOfInterest();
  }

  public EnGiven createEnGiven()
  {
    return new EnGiven();
  }

  public POCDMT000040AssignedCustodian createPOCDMT000040AssignedCustodian()
  {
    return new POCDMT000040AssignedCustodian();
  }

  public POCDMT000040Entry createPOCDMT000040Entry()
  {
    return new POCDMT000040Entry();
  }

  public StrucDocTitle createStrucDocTitle()
  {
    return new StrucDocTitle();
  }

  public IVXBMO createIVXBMO()
  {
    return new IVXBMO();
  }

  public POCDMT000040ParentDocument createPOCDMT000040ParentDocument()
  {
    return new POCDMT000040ParentDocument();
  }

  public POCDMT000040RelatedSubject createPOCDMT000040RelatedSubject()
  {
    return new POCDMT000040RelatedSubject();
  }

  public POCDMT000040StructuredBody createPOCDMT000040StructuredBody()
  {
    return new POCDMT000040StructuredBody();
  }

  public POCDMT000040Performer1 createPOCDMT000040Performer1()
  {
    return new POCDMT000040Performer1();
  }

  public POCDMT000040InfrastructureRootTypeId createPOCDMT000040InfrastructureRootTypeId()
  {
    return new POCDMT000040InfrastructureRootTypeId();
  }

  public StrucDocContent createStrucDocContent()
  {
    return new StrucDocContent();
  }

  public POCDMT000040Performer2 createPOCDMT000040Performer2()
  {
    return new POCDMT000040Performer2();
  }

  public POCDMT000040AssignedAuthor createPOCDMT000040AssignedAuthor()
  {
    return new POCDMT000040AssignedAuthor();
  }

  public POCDMT000040EncompassingEncounter createPOCDMT000040EncompassingEncounter()
  {
    return new POCDMT000040EncompassingEncounter();
  }

  public StrucDocTr createStrucDocTr()
  {
    return new StrucDocTr();
  }

  public POCDMT000040RelatedEntity createPOCDMT000040RelatedEntity()
  {
    return new POCDMT000040RelatedEntity();
  }

  public POCDMT000040Order createPOCDMT000040Order()
  {
    return new POCDMT000040Order();
  }

  public StrucDocTd createStrucDocTd()
  {
    return new StrucDocTd();
  }

  public AdxpBuildingNumberSuffix createAdxpBuildingNumberSuffix()
  {
    return new AdxpBuildingNumberSuffix();
  }

  public StrucDocTh createStrucDocTh()
  {
    return new StrucDocTh();
  }

  public IVXBPPDTS createIVXBPPDTS()
  {
    return new IVXBPPDTS();
  }

  public IVLTS createIVLTS()
  {
    return new IVLTS();
  }

  public SXPRTS createSXPRTS()
  {
    return new SXPRTS();
  }

  public PIVLTS createPIVLTS()
  {
    return new PIVLTS();
  }

  public POCDMT000040Entity createPOCDMT000040Entity()
  {
    return new POCDMT000040Entity();
  }

  public POCDMT000040Procedure createPOCDMT000040Procedure()
  {
    return new POCDMT000040Procedure();
  }

  public POCDMT000040PlayingEntity createPOCDMT000040PlayingEntity()
  {
    return new POCDMT000040PlayingEntity();
  }

  public POCDMT000040ExternalAct createPOCDMT000040ExternalAct()
  {
    return new POCDMT000040ExternalAct();
  }

  public IVLPQ createIVLPQ()
  {
    return new IVLPQ();
  }

  public StrucDocTbody createStrucDocTbody()
  {
    return new StrucDocTbody();
  }

  public AdxpCensusTract createAdxpCensusTract()
  {
    return new AdxpCensusTract();
  }

  public POCDMT000040ServiceEvent createPOCDMT000040ServiceEvent()
  {
    return new POCDMT000040ServiceEvent();
  }

  public REAL createREAL()
  {
    return new REAL();
  }

  public POCDMT000040Organizer createPOCDMT000040Organizer()
  {
    return new POCDMT000040Organizer();
  }

  public PIVLPPDTS createPIVLPPDTS()
  {
    return new PIVLPPDTS();
  }

  public POCDMT000040SubjectPerson createPOCDMT000040SubjectPerson()
  {
    return new POCDMT000040SubjectPerson();
  }

  public ENXP createENXP()
  {
    return new ENXP();
  }

  public IVLREAL createIVLREAL()
  {
    return new IVLREAL();
  }

  public SXCMPPDTS createSXCMPPDTS()
  {
    return new SXCMPPDTS();
  }

  public POCDMT000040LanguageCommunication createPOCDMT000040LanguageCommunication()
  {
    return new POCDMT000040LanguageCommunication();
  }

  public POCDMT000040HealthCareFacility createPOCDMT000040HealthCareFacility()
  {
    return new POCDMT000040HealthCareFacility();
  }

  public IVXBPPDPQ createIVXBPPDPQ()
  {
    return new IVXBPPDPQ();
  }

  public AdxpDeliveryAddressLine createAdxpDeliveryAddressLine()
  {
    return new AdxpDeliveryAddressLine();
  }

  public StrucDocCaption createStrucDocCaption()
  {
    return new StrucDocCaption();
  }

  public PQ createPQ()
  {
    return new PQ();
  }

  public AdxpDeliveryModeIdentifier createAdxpDeliveryModeIdentifier()
  {
    return new AdxpDeliveryModeIdentifier();
  }

  public StrucDocItem createStrucDocItem()
  {
    return new StrucDocItem();
  }

  public POCDMT000040InformationRecipient createPOCDMT000040InformationRecipient()
  {
    return new POCDMT000040InformationRecipient();
  }

  public TN createTN()
  {
    return new TN();
  }

  public TS createTS()
  {
    return new TS();
  }

  public POCDMT000040LabeledDrug createPOCDMT000040LabeledDrug()
  {
    return new POCDMT000040LabeledDrug();
  }

  public POCDMT000040Section createPOCDMT000040Section()
  {
    return new POCDMT000040Section();
  }

  public ST createST()
  {
    return new ST();
  }

  public AdxpStreetName createAdxpStreetName()
  {
    return new AdxpStreetName();
  }

  public AdxpStreetNameType createAdxpStreetNameType()
  {
    return new AdxpStreetNameType();
  }

  public AdxpDirection createAdxpDirection()
  {
    return new AdxpDirection();
  }

  public SC createSC()
  {
    return new SC();
  }

  public PPDTS createPPDTS()
  {
    return new PPDTS();
  }

  public POCDMT000040EncounterParticipant createPOCDMT000040EncounterParticipant()
  {
    return new POCDMT000040EncounterParticipant();
  }

  public IVLMO createIVLMO()
  {
    return new IVLMO();
  }

  public POCDMT000040Criterion createPOCDMT000040Criterion()
  {
    return new POCDMT000040Criterion();
  }

  public POCDMT000040IntendedRecipient createPOCDMT000040IntendedRecipient()
  {
    return new POCDMT000040IntendedRecipient();
  }

  public StrucDocRenderMultiMedia createStrucDocRenderMultiMedia()
  {
    return new StrucDocRenderMultiMedia();
  }

  public AdxpState createAdxpState()
  {
    return new AdxpState();
  }

  public AdxpCity createAdxpCity()
  {
    return new AdxpCity();
  }

  public RTOMOPQ createRTOMOPQ()
  {
    return new RTOMOPQ();
  }

  public POCDMT000040PatientRole createPOCDMT000040PatientRole()
  {
    return new POCDMT000040PatientRole();
  }

  public SXCMPPDPQ createSXCMPPDPQ()
  {
    return new SXCMPPDPQ();
  }

  public RTOQTYQTY createRTOQTYQTY()
  {
    return new RTOQTYQTY();
  }

  public POCDMT000040Authenticator createPOCDMT000040Authenticator()
  {
    return new POCDMT000040Authenticator();
  }

  public POCDMT000040Encounter createPOCDMT000040Encounter()
  {
    return new POCDMT000040Encounter();
  }

  public IVXBINT createIVXBINT()
  {
    return new IVXBINT();
  }

  public POCDMT000040LegalAuthenticator createPOCDMT000040LegalAuthenticator()
  {
    return new POCDMT000040LegalAuthenticator();
  }

  public POCDMT000040ObservationRange createPOCDMT000040ObservationRange()
  {
    return new POCDMT000040ObservationRange();
  }

  public StrucDocSub createStrucDocSub()
  {
    return new StrucDocSub();
  }

  public AdxpPostalCode createAdxpPostalCode()
  {
    return new AdxpPostalCode();
  }

  public SXCMREAL createSXCMREAL()
  {
    return new SXCMREAL();
  }

  public POCDMT000040Patient createPOCDMT000040Patient()
  {
    return new POCDMT000040Patient();
  }

  public StrucDocSup createStrucDocSup()
  {
    return new StrucDocSup();
  }

  public SLISTTS createSLISTTS()
  {
    return new SLISTTS();
  }

  public BXITCD createBXITCD()
  {
    return new BXITCD();
  }

  public GLISTTS createGLISTTS()
  {
    return new GLISTTS();
  }

  public POCDMT000040ResponsibleParty createPOCDMT000040ResponsibleParty()
  {
    return new POCDMT000040ResponsibleParty();
  }

  public PQR createPQR()
  {
    return new PQR();
  }

  public POCDMT000040Guardian createPOCDMT000040Guardian()
  {
    return new POCDMT000040Guardian();
  }

  public EIVLEvent createEIVLEvent()
  {
    return new EIVLEvent();
  }

  public StrucDocLinkHtml createStrucDocLinkHtml()
  {
    return new StrucDocLinkHtml();
  }

  public POCDMT000040Informant12 createPOCDMT000040Informant12()
  {
    return new POCDMT000040Informant12();
  }

  public SXCMCD createSXCMCD()
  {
    return new SXCMCD();
  }

  public POCDMT000040AssignedEntity createPOCDMT000040AssignedEntity()
  {
    return new POCDMT000040AssignedEntity();
  }

  public AdxpStreetNameBase createAdxpStreetNameBase()
  {
    return new AdxpStreetNameBase();
  }

  public Thumbnail createThumbnail()
  {
    return new Thumbnail();
  }

  public POCDMT000040Act createPOCDMT000040Act()
  {
    return new POCDMT000040Act();
  }

  public StrucDocTitleContent createStrucDocTitleContent()
  {
    return new StrucDocTitleContent();
  }

  public POCDMT000040DocumentationOf createPOCDMT000040DocumentationOf()
  {
    return new POCDMT000040DocumentationOf();
  }

  public SXCMINT createSXCMINT()
  {
    return new SXCMINT();
  }

  public AdxpAdditionalLocator createAdxpAdditionalLocator()
  {
    return new AdxpAdditionalLocator();
  }

  public RTO createRTO()
  {
    return new RTO();
  }

  public HXITCE createHXITCE()
  {
    return new HXITCE();
  }

  public GLISTPQ createGLISTPQ()
  {
    return new GLISTPQ();
  }

  public POCDMT000040DataEnterer createPOCDMT000040DataEnterer()
  {
    return new POCDMT000040DataEnterer();
  }

  public POCDMT000040OrganizationPartOf createPOCDMT000040OrganizationPartOf()
  {
    return new POCDMT000040OrganizationPartOf();
  }

  public POCDMT000040Precondition createPOCDMT000040Precondition()
  {
    return new POCDMT000040Precondition();
  }

  public StrucDocText createStrucDocText()
  {
    return new StrucDocText();
  }

  public AdxpPostBox createAdxpPostBox()
  {
    return new AdxpPostBox();
  }

  public ADXP createADXP()
  {
    return new ADXP();
  }

  public POCDMT000040Supply createPOCDMT000040Supply()
  {
    return new POCDMT000040Supply();
  }

  public POCDMT000040EntryRelationship createPOCDMT000040EntryRelationship()
  {
    return new POCDMT000040EntryRelationship();
  }

  public AdxpDeliveryInstallationArea createAdxpDeliveryInstallationArea()
  {
    return new AdxpDeliveryInstallationArea();
  }

  public POCDMT000040Location createPOCDMT000040Location()
  {
    return new POCDMT000040Location();
  }

  public POCDMT000040Person createPOCDMT000040Person()
  {
    return new POCDMT000040Person();
  }

  public EnFamily createEnFamily()
  {
    return new EnFamily();
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="ClinicalDocument")
  public JAXBElement<POCDMT000040ClinicalDocument> createClinicalDocument(POCDMT000040ClinicalDocument value)
  {
    return new JAXBElement(_ClinicalDocument_QNAME, POCDMT000040ClinicalDocument.class, null, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="suffix", scope=EN.class)
  public JAXBElement<EnSuffix> createENSuffix(EnSuffix value)
  {
    return new JAXBElement(_ENSuffix_QNAME, EnSuffix.class, EN.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="delimiter", scope=EN.class)
  public JAXBElement<EnDelimiter> createENDelimiter(EnDelimiter value)
  {
    return new JAXBElement(_ENDelimiter_QNAME, EnDelimiter.class, EN.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="validTime", scope=EN.class)
  public JAXBElement<IVLTS> createENValidTime(IVLTS value)
  {
    return new JAXBElement(_ENValidTime_QNAME, IVLTS.class, EN.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="prefix", scope=EN.class)
  public JAXBElement<EnPrefix> createENPrefix(EnPrefix value)
  {
    return new JAXBElement(_ENPrefix_QNAME, EnPrefix.class, EN.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="family", scope=EN.class)
  public JAXBElement<EnFamily> createENFamily(EnFamily value)
  {
    return new JAXBElement(_ENFamily_QNAME, EnFamily.class, EN.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="given", scope=EN.class)
  public JAXBElement<EnGiven> createENGiven(EnGiven value)
  {
    return new JAXBElement(_ENGiven_QNAME, EnGiven.class, EN.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="table", scope=StrucDocItem.class)
  public JAXBElement<StrucDocTable> createStrucDocItemTable(StrucDocTable value)
  {
    return new JAXBElement(_StrucDocItemTable_QNAME, StrucDocTable.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="list", scope=StrucDocItem.class)
  public JAXBElement<StrucDocList> createStrucDocItemList(StrucDocList value)
  {
    return new JAXBElement(_StrucDocItemList_QNAME, StrucDocList.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="caption", scope=StrucDocItem.class)
  public JAXBElement<StrucDocCaption> createStrucDocItemCaption(StrucDocCaption value)
  {
    return new JAXBElement(_StrucDocItemCaption_QNAME, StrucDocCaption.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="renderMultiMedia", scope=StrucDocItem.class)
  public JAXBElement<StrucDocRenderMultiMedia> createStrucDocItemRenderMultiMedia(StrucDocRenderMultiMedia value)
  {
    return new JAXBElement(_StrucDocItemRenderMultiMedia_QNAME, StrucDocRenderMultiMedia.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocItem.class)
  public JAXBElement<StrucDocBr> createStrucDocItemBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="linkHtml", scope=StrucDocItem.class)
  public JAXBElement<StrucDocLinkHtml> createStrucDocItemLinkHtml(StrucDocLinkHtml value)
  {
    return new JAXBElement(_StrucDocItemLinkHtml_QNAME, StrucDocLinkHtml.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocItem.class)
  public JAXBElement<StrucDocSup> createStrucDocItemSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocItem.class)
  public JAXBElement<StrucDocSub> createStrucDocItemSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocItem.class)
  public JAXBElement<StrucDocContent> createStrucDocItemContent(StrucDocContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocContent.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocItem.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocItemFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocItem.class)
  public JAXBElement<StrucDocFootnote> createStrucDocItemFootnote(StrucDocFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocFootnote.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="paragraph", scope=StrucDocItem.class)
  public JAXBElement<StrucDocParagraph> createStrucDocItemParagraph(StrucDocParagraph value)
  {
    return new JAXBElement(_StrucDocItemParagraph_QNAME, StrucDocParagraph.class, StrucDocItem.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="renderMultiMedia", scope=StrucDocContent.class)
  public JAXBElement<StrucDocRenderMultiMedia> createStrucDocContentRenderMultiMedia(StrucDocRenderMultiMedia value)
  {
    return new JAXBElement(_StrucDocItemRenderMultiMedia_QNAME, StrucDocRenderMultiMedia.class, StrucDocContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocContent.class)
  public JAXBElement<StrucDocBr> createStrucDocContentBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="linkHtml", scope=StrucDocContent.class)
  public JAXBElement<StrucDocLinkHtml> createStrucDocContentLinkHtml(StrucDocLinkHtml value)
  {
    return new JAXBElement(_StrucDocItemLinkHtml_QNAME, StrucDocLinkHtml.class, StrucDocContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocContent.class)
  public JAXBElement<StrucDocSup> createStrucDocContentSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocContent.class)
  public JAXBElement<StrucDocSub> createStrucDocContentSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocContent.class)
  public JAXBElement<StrucDocContent> createStrucDocContentContent(StrucDocContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocContent.class, StrucDocContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocContent.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocContentFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocContent.class)
  public JAXBElement<StrucDocFootnote> createStrucDocContentFootnote(StrucDocFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocFootnote.class, StrucDocContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="high", scope=IVLINT.class)
  public JAXBElement<IVXBINT> createIVLINTHigh(IVXBINT value)
  {
    return new JAXBElement(_IVLINTHigh_QNAME, IVXBINT.class, IVLINT.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="low", scope=IVLINT.class)
  public JAXBElement<IVXBINT> createIVLINTLow(IVXBINT value)
  {
    return new JAXBElement(_IVLINTLow_QNAME, IVXBINT.class, IVLINT.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="center", scope=IVLINT.class)
  public JAXBElement<INT> createIVLINTCenter(INT value)
  {
    return new JAXBElement(_IVLINTCenter_QNAME, INT.class, IVLINT.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="width", scope=IVLINT.class)
  public JAXBElement<INT> createIVLINTWidth(INT value)
  {
    return new JAXBElement(_IVLINTWidth_QNAME, INT.class, IVLINT.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="high", scope=IVLPPDPQ.class)
  public JAXBElement<IVXBPPDPQ> createIVLPPDPQHigh(IVXBPPDPQ value)
  {
    return new JAXBElement(_IVLINTHigh_QNAME, IVXBPPDPQ.class, IVLPPDPQ.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="low", scope=IVLPPDPQ.class)
  public JAXBElement<IVXBPPDPQ> createIVLPPDPQLow(IVXBPPDPQ value)
  {
    return new JAXBElement(_IVLINTLow_QNAME, IVXBPPDPQ.class, IVLPPDPQ.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="center", scope=IVLPPDPQ.class)
  public JAXBElement<PPDPQ> createIVLPPDPQCenter(PPDPQ value)
  {
    return new JAXBElement(_IVLINTCenter_QNAME, PPDPQ.class, IVLPPDPQ.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="width", scope=IVLPPDPQ.class)
  public JAXBElement<PPDPQ> createIVLPPDPQWidth(PPDPQ value)
  {
    return new JAXBElement(_IVLINTWidth_QNAME, PPDPQ.class, IVLPPDPQ.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="list", scope=StrucDocTd.class)
  public JAXBElement<StrucDocList> createStrucDocTdList(StrucDocList value)
  {
    return new JAXBElement(_StrucDocItemList_QNAME, StrucDocList.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="renderMultiMedia", scope=StrucDocTd.class)
  public JAXBElement<StrucDocRenderMultiMedia> createStrucDocTdRenderMultiMedia(StrucDocRenderMultiMedia value)
  {
    return new JAXBElement(_StrucDocItemRenderMultiMedia_QNAME, StrucDocRenderMultiMedia.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocTd.class)
  public JAXBElement<StrucDocBr> createStrucDocTdBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="linkHtml", scope=StrucDocTd.class)
  public JAXBElement<StrucDocLinkHtml> createStrucDocTdLinkHtml(StrucDocLinkHtml value)
  {
    return new JAXBElement(_StrucDocItemLinkHtml_QNAME, StrucDocLinkHtml.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocTd.class)
  public JAXBElement<StrucDocSup> createStrucDocTdSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocTd.class)
  public JAXBElement<StrucDocSub> createStrucDocTdSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocTd.class)
  public JAXBElement<StrucDocContent> createStrucDocTdContent(StrucDocContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocContent.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocTd.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocTdFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocTd.class)
  public JAXBElement<StrucDocFootnote> createStrucDocTdFootnote(StrucDocFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocFootnote.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="paragraph", scope=StrucDocTd.class)
  public JAXBElement<StrucDocParagraph> createStrucDocTdParagraph(StrucDocParagraph value)
  {
    return new JAXBElement(_StrucDocItemParagraph_QNAME, StrucDocParagraph.class, StrucDocTd.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocTitle.class)
  public JAXBElement<StrucDocBr> createStrucDocTitleBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocTitle.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocTitle.class)
  public JAXBElement<StrucDocSup> createStrucDocTitleSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocTitle.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocTitle.class)
  public JAXBElement<StrucDocSub> createStrucDocTitleSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocTitle.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocTitle.class)
  public JAXBElement<StrucDocTitleContent> createStrucDocTitleContent(StrucDocTitleContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocTitleContent.class, StrucDocTitle.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocTitle.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocTitleFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocTitle.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocTitle.class)
  public JAXBElement<StrucDocTitleFootnote> createStrucDocTitleFootnote(StrucDocTitleFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocTitleFootnote.class, StrucDocTitle.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="table", scope=StrucDocText.class)
  public JAXBElement<StrucDocTable> createStrucDocTextTable(StrucDocTable value)
  {
    return new JAXBElement(_StrucDocItemTable_QNAME, StrucDocTable.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="list", scope=StrucDocText.class)
  public JAXBElement<StrucDocList> createStrucDocTextList(StrucDocList value)
  {
    return new JAXBElement(_StrucDocItemList_QNAME, StrucDocList.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="renderMultiMedia", scope=StrucDocText.class)
  public JAXBElement<StrucDocRenderMultiMedia> createStrucDocTextRenderMultiMedia(StrucDocRenderMultiMedia value)
  {
    return new JAXBElement(_StrucDocItemRenderMultiMedia_QNAME, StrucDocRenderMultiMedia.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocText.class)
  public JAXBElement<StrucDocBr> createStrucDocTextBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="linkHtml", scope=StrucDocText.class)
  public JAXBElement<StrucDocLinkHtml> createStrucDocTextLinkHtml(StrucDocLinkHtml value)
  {
    return new JAXBElement(_StrucDocItemLinkHtml_QNAME, StrucDocLinkHtml.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocText.class)
  public JAXBElement<StrucDocSup> createStrucDocTextSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocText.class)
  public JAXBElement<StrucDocSub> createStrucDocTextSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocText.class)
  public JAXBElement<StrucDocContent> createStrucDocTextContent(StrucDocContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocContent.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocText.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocTextFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocText.class)
  public JAXBElement<StrucDocFootnote> createStrucDocTextFootnote(StrucDocFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocFootnote.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="paragraph", scope=StrucDocText.class)
  public JAXBElement<StrucDocParagraph> createStrucDocTextParagraph(StrucDocParagraph value)
  {
    return new JAXBElement(_StrucDocItemParagraph_QNAME, StrucDocParagraph.class, StrucDocText.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="renderMultiMedia", scope=StrucDocTh.class)
  public JAXBElement<StrucDocRenderMultiMedia> createStrucDocThRenderMultiMedia(StrucDocRenderMultiMedia value)
  {
    return new JAXBElement(_StrucDocItemRenderMultiMedia_QNAME, StrucDocRenderMultiMedia.class, StrucDocTh.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocTh.class)
  public JAXBElement<StrucDocBr> createStrucDocThBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocTh.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="linkHtml", scope=StrucDocTh.class)
  public JAXBElement<StrucDocLinkHtml> createStrucDocThLinkHtml(StrucDocLinkHtml value)
  {
    return new JAXBElement(_StrucDocItemLinkHtml_QNAME, StrucDocLinkHtml.class, StrucDocTh.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocTh.class)
  public JAXBElement<StrucDocSup> createStrucDocThSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocTh.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocTh.class)
  public JAXBElement<StrucDocSub> createStrucDocThSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocTh.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocTh.class)
  public JAXBElement<StrucDocContent> createStrucDocThContent(StrucDocContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocContent.class, StrucDocTh.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocTh.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocThFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocTh.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocTh.class)
  public JAXBElement<StrucDocFootnote> createStrucDocThFootnote(StrucDocFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocFootnote.class, StrucDocTh.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="caption", scope=StrucDocParagraph.class)
  public JAXBElement<StrucDocCaption> createStrucDocParagraphCaption(StrucDocCaption value)
  {
    return new JAXBElement(_StrucDocItemCaption_QNAME, StrucDocCaption.class, StrucDocParagraph.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="renderMultiMedia", scope=StrucDocParagraph.class)
  public JAXBElement<StrucDocRenderMultiMedia> createStrucDocParagraphRenderMultiMedia(StrucDocRenderMultiMedia value)
  {
    return new JAXBElement(_StrucDocItemRenderMultiMedia_QNAME, StrucDocRenderMultiMedia.class, StrucDocParagraph.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocParagraph.class)
  public JAXBElement<StrucDocBr> createStrucDocParagraphBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocParagraph.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="linkHtml", scope=StrucDocParagraph.class)
  public JAXBElement<StrucDocLinkHtml> createStrucDocParagraphLinkHtml(StrucDocLinkHtml value)
  {
    return new JAXBElement(_StrucDocItemLinkHtml_QNAME, StrucDocLinkHtml.class, StrucDocParagraph.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocParagraph.class)
  public JAXBElement<StrucDocSup> createStrucDocParagraphSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocParagraph.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocParagraph.class)
  public JAXBElement<StrucDocSub> createStrucDocParagraphSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocParagraph.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocParagraph.class)
  public JAXBElement<StrucDocContent> createStrucDocParagraphContent(StrucDocContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocContent.class, StrucDocParagraph.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocParagraph.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocParagraphFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocParagraph.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocParagraph.class)
  public JAXBElement<StrucDocFootnote> createStrucDocParagraphFootnote(StrucDocFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocFootnote.class, StrucDocParagraph.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocLinkHtml.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocLinkHtmlFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocLinkHtml.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocLinkHtml.class)
  public JAXBElement<StrucDocFootnote> createStrucDocLinkHtmlFootnote(StrucDocFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocFootnote.class, StrucDocLinkHtml.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocTitleContent.class)
  public JAXBElement<StrucDocBr> createStrucDocTitleContentBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocTitleContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocTitleContent.class)
  public JAXBElement<StrucDocSup> createStrucDocTitleContentSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocTitleContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocTitleContent.class)
  public JAXBElement<StrucDocSub> createStrucDocTitleContentSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocTitleContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocTitleContent.class)
  public JAXBElement<StrucDocTitleContent> createStrucDocTitleContentContent(StrucDocTitleContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocTitleContent.class, StrucDocTitleContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocTitleContent.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocTitleContentFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocTitleContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocTitleContent.class)
  public JAXBElement<StrucDocTitleFootnote> createStrucDocTitleContentFootnote(StrucDocTitleFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocTitleFootnote.class, StrucDocTitleContent.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="linkHtml", scope=StrucDocCaption.class)
  public JAXBElement<StrucDocLinkHtml> createStrucDocCaptionLinkHtml(StrucDocLinkHtml value)
  {
    return new JAXBElement(_StrucDocItemLinkHtml_QNAME, StrucDocLinkHtml.class, StrucDocCaption.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocCaption.class)
  public JAXBElement<StrucDocSup> createStrucDocCaptionSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocCaption.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocCaption.class)
  public JAXBElement<StrucDocSub> createStrucDocCaptionSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocCaption.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnoteRef", scope=StrucDocCaption.class)
  public JAXBElement<StrucDocFootnoteRef> createStrucDocCaptionFootnoteRef(StrucDocFootnoteRef value)
  {
    return new JAXBElement(_StrucDocItemFootnoteRef_QNAME, StrucDocFootnoteRef.class, StrucDocCaption.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="footnote", scope=StrucDocCaption.class)
  public JAXBElement<StrucDocFootnote> createStrucDocCaptionFootnote(StrucDocFootnote value)
  {
    return new JAXBElement(_StrucDocItemFootnote_QNAME, StrucDocFootnote.class, StrucDocCaption.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="high", scope=IVLTS.class)
  public JAXBElement<IVXBTS> createIVLTSHigh(IVXBTS value)
  {
    return new JAXBElement(_IVLINTHigh_QNAME, IVXBTS.class, IVLTS.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="low", scope=IVLTS.class)
  public JAXBElement<IVXBTS> createIVLTSLow(IVXBTS value)
  {
    return new JAXBElement(_IVLINTLow_QNAME, IVXBTS.class, IVLTS.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="center", scope=IVLTS.class)
  public JAXBElement<TS> createIVLTSCenter(TS value)
  {
    return new JAXBElement(_IVLINTCenter_QNAME, TS.class, IVLTS.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="width", scope=IVLTS.class)
  public JAXBElement<PQ> createIVLTSWidth(PQ value)
  {
    return new JAXBElement(_IVLINTWidth_QNAME, PQ.class, IVLTS.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocTitleFootnote.class)
  public JAXBElement<StrucDocBr> createStrucDocTitleFootnoteBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocTitleFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocTitleFootnote.class)
  public JAXBElement<StrucDocSup> createStrucDocTitleFootnoteSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocTitleFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocTitleFootnote.class)
  public JAXBElement<StrucDocSub> createStrucDocTitleFootnoteSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocTitleFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocTitleFootnote.class)
  public JAXBElement<StrucDocTitleContent> createStrucDocTitleFootnoteContent(StrucDocTitleContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocTitleContent.class, StrucDocTitleFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="deliveryModeIdentifier", scope=AD.class)
  public JAXBElement<AdxpDeliveryModeIdentifier> createADDeliveryModeIdentifier(AdxpDeliveryModeIdentifier value)
  {
    return new JAXBElement(_ADDeliveryModeIdentifier_QNAME, AdxpDeliveryModeIdentifier.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="houseNumber", scope=AD.class)
  public JAXBElement<AdxpHouseNumber> createADHouseNumber(AdxpHouseNumber value)
  {
    return new JAXBElement(_ADHouseNumber_QNAME, AdxpHouseNumber.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="state", scope=AD.class)
  public JAXBElement<AdxpState> createADState(AdxpState value)
  {
    return new JAXBElement(_ADState_QNAME, AdxpState.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="city", scope=AD.class)
  public JAXBElement<AdxpCity> createADCity(AdxpCity value)
  {
    return new JAXBElement(_ADCity_QNAME, AdxpCity.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="additionalLocator", scope=AD.class)
  public JAXBElement<AdxpAdditionalLocator> createADAdditionalLocator(AdxpAdditionalLocator value)
  {
    return new JAXBElement(_ADAdditionalLocator_QNAME, AdxpAdditionalLocator.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="streetAddressLine", scope=AD.class)
  public JAXBElement<AdxpStreetAddressLine> createADStreetAddressLine(AdxpStreetAddressLine value)
  {
    return new JAXBElement(_ADStreetAddressLine_QNAME, AdxpStreetAddressLine.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="deliveryInstallationArea", scope=AD.class)
  public JAXBElement<AdxpDeliveryInstallationArea> createADDeliveryInstallationArea(AdxpDeliveryInstallationArea value)
  {
    return new JAXBElement(_ADDeliveryInstallationArea_QNAME, AdxpDeliveryInstallationArea.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="streetNameType", scope=AD.class)
  public JAXBElement<AdxpStreetNameType> createADStreetNameType(AdxpStreetNameType value)
  {
    return new JAXBElement(_ADStreetNameType_QNAME, AdxpStreetNameType.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="deliveryInstallationQualifier", scope=AD.class)
  public JAXBElement<AdxpDeliveryInstallationQualifier> createADDeliveryInstallationQualifier(AdxpDeliveryInstallationQualifier value)
  {
    return new JAXBElement(_ADDeliveryInstallationQualifier_QNAME, AdxpDeliveryInstallationQualifier.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="direction", scope=AD.class)
  public JAXBElement<AdxpDirection> createADDirection(AdxpDirection value)
  {
    return new JAXBElement(_ADDirection_QNAME, AdxpDirection.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="censusTract", scope=AD.class)
  public JAXBElement<AdxpCensusTract> createADCensusTract(AdxpCensusTract value)
  {
    return new JAXBElement(_ADCensusTract_QNAME, AdxpCensusTract.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="unitID", scope=AD.class)
  public JAXBElement<AdxpUnitID> createADUnitID(AdxpUnitID value)
  {
    return new JAXBElement(_ADUnitID_QNAME, AdxpUnitID.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="postalCode", scope=AD.class)
  public JAXBElement<AdxpPostalCode> createADPostalCode(AdxpPostalCode value)
  {
    return new JAXBElement(_ADPostalCode_QNAME, AdxpPostalCode.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="streetName", scope=AD.class)
  public JAXBElement<AdxpStreetName> createADStreetName(AdxpStreetName value)
  {
    return new JAXBElement(_ADStreetName_QNAME, AdxpStreetName.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="deliveryInstallationType", scope=AD.class)
  public JAXBElement<AdxpDeliveryInstallationType> createADDeliveryInstallationType(AdxpDeliveryInstallationType value)
  {
    return new JAXBElement(_ADDeliveryInstallationType_QNAME, AdxpDeliveryInstallationType.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="streetNameBase", scope=AD.class)
  public JAXBElement<AdxpStreetNameBase> createADStreetNameBase(AdxpStreetNameBase value)
  {
    return new JAXBElement(_ADStreetNameBase_QNAME, AdxpStreetNameBase.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="deliveryMode", scope=AD.class)
  public JAXBElement<AdxpDeliveryMode> createADDeliveryMode(AdxpDeliveryMode value)
  {
    return new JAXBElement(_ADDeliveryMode_QNAME, AdxpDeliveryMode.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="postBox", scope=AD.class)
  public JAXBElement<AdxpPostBox> createADPostBox(AdxpPostBox value)
  {
    return new JAXBElement(_ADPostBox_QNAME, AdxpPostBox.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="country", scope=AD.class)
  public JAXBElement<AdxpCountry> createADCountry(AdxpCountry value)
  {
    return new JAXBElement(_ADCountry_QNAME, AdxpCountry.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="deliveryAddressLine", scope=AD.class)
  public JAXBElement<AdxpDeliveryAddressLine> createADDeliveryAddressLine(AdxpDeliveryAddressLine value)
  {
    return new JAXBElement(_ADDeliveryAddressLine_QNAME, AdxpDeliveryAddressLine.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="useablePeriod", scope=AD.class)
  public JAXBElement<SXCMTS> createADUseablePeriod(SXCMTS value)
  {
    return new JAXBElement(_ADUseablePeriod_QNAME, SXCMTS.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="careOf", scope=AD.class)
  public JAXBElement<AdxpCareOf> createADCareOf(AdxpCareOf value)
  {
    return new JAXBElement(_ADCareOf_QNAME, AdxpCareOf.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="unitType", scope=AD.class)
  public JAXBElement<AdxpUnitType> createADUnitType(AdxpUnitType value)
  {
    return new JAXBElement(_ADUnitType_QNAME, AdxpUnitType.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="precinct", scope=AD.class)
  public JAXBElement<AdxpPrecinct> createADPrecinct(AdxpPrecinct value)
  {
    return new JAXBElement(_ADPrecinct_QNAME, AdxpPrecinct.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="delimiter", scope=AD.class)
  public JAXBElement<AdxpDelimiter> createADDelimiter(AdxpDelimiter value)
  {
    return new JAXBElement(_ENDelimiter_QNAME, AdxpDelimiter.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="houseNumberNumeric", scope=AD.class)
  public JAXBElement<AdxpHouseNumberNumeric> createADHouseNumberNumeric(AdxpHouseNumberNumeric value)
  {
    return new JAXBElement(_ADHouseNumberNumeric_QNAME, AdxpHouseNumberNumeric.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="buildingNumberSuffix", scope=AD.class)
  public JAXBElement<AdxpBuildingNumberSuffix> createADBuildingNumberSuffix(AdxpBuildingNumberSuffix value)
  {
    return new JAXBElement(_ADBuildingNumberSuffix_QNAME, AdxpBuildingNumberSuffix.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="county", scope=AD.class)
  public JAXBElement<AdxpCounty> createADCounty(AdxpCounty value)
  {
    return new JAXBElement(_ADCounty_QNAME, AdxpCounty.class, AD.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="high", scope=IVLREAL.class)
  public JAXBElement<IVXBREAL> createIVLREALHigh(IVXBREAL value)
  {
    return new JAXBElement(_IVLINTHigh_QNAME, IVXBREAL.class, IVLREAL.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="low", scope=IVLREAL.class)
  public JAXBElement<IVXBREAL> createIVLREALLow(IVXBREAL value)
  {
    return new JAXBElement(_IVLINTLow_QNAME, IVXBREAL.class, IVLREAL.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="center", scope=IVLREAL.class)
  public JAXBElement<REAL> createIVLREALCenter(REAL value)
  {
    return new JAXBElement(_IVLINTCenter_QNAME, REAL.class, IVLREAL.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="width", scope=IVLREAL.class)
  public JAXBElement<REAL> createIVLREALWidth(REAL value)
  {
    return new JAXBElement(_IVLINTWidth_QNAME, REAL.class, IVLREAL.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="high", scope=IVLPQ.class)
  public JAXBElement<IVXBPQ> createIVLPQHigh(IVXBPQ value)
  {
    return new JAXBElement(_IVLINTHigh_QNAME, IVXBPQ.class, IVLPQ.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="low", scope=IVLPQ.class)
  public JAXBElement<IVXBPQ> createIVLPQLow(IVXBPQ value)
  {
    return new JAXBElement(_IVLINTLow_QNAME, IVXBPQ.class, IVLPQ.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="center", scope=IVLPQ.class)
  public JAXBElement<PQ> createIVLPQCenter(PQ value)
  {
    return new JAXBElement(_IVLINTCenter_QNAME, PQ.class, IVLPQ.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="width", scope=IVLPQ.class)
  public JAXBElement<PQ> createIVLPQWidth(PQ value)
  {
    return new JAXBElement(_IVLINTWidth_QNAME, PQ.class, IVLPQ.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="table", scope=StrucDocFootnote.class)
  public JAXBElement<StrucDocTable> createStrucDocFootnoteTable(StrucDocTable value)
  {
    return new JAXBElement(_StrucDocItemTable_QNAME, StrucDocTable.class, StrucDocFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="list", scope=StrucDocFootnote.class)
  public JAXBElement<StrucDocList> createStrucDocFootnoteList(StrucDocList value)
  {
    return new JAXBElement(_StrucDocItemList_QNAME, StrucDocList.class, StrucDocFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="renderMultiMedia", scope=StrucDocFootnote.class)
  public JAXBElement<StrucDocRenderMultiMedia> createStrucDocFootnoteRenderMultiMedia(StrucDocRenderMultiMedia value)
  {
    return new JAXBElement(_StrucDocItemRenderMultiMedia_QNAME, StrucDocRenderMultiMedia.class, StrucDocFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="br", scope=StrucDocFootnote.class)
  public JAXBElement<StrucDocBr> createStrucDocFootnoteBr(StrucDocBr value)
  {
    return new JAXBElement(_StrucDocItemBr_QNAME, StrucDocBr.class, StrucDocFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="linkHtml", scope=StrucDocFootnote.class)
  public JAXBElement<StrucDocLinkHtml> createStrucDocFootnoteLinkHtml(StrucDocLinkHtml value)
  {
    return new JAXBElement(_StrucDocItemLinkHtml_QNAME, StrucDocLinkHtml.class, StrucDocFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sup", scope=StrucDocFootnote.class)
  public JAXBElement<StrucDocSup> createStrucDocFootnoteSup(StrucDocSup value)
  {
    return new JAXBElement(_StrucDocItemSup_QNAME, StrucDocSup.class, StrucDocFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="sub", scope=StrucDocFootnote.class)
  public JAXBElement<StrucDocSub> createStrucDocFootnoteSub(StrucDocSub value)
  {
    return new JAXBElement(_StrucDocItemSub_QNAME, StrucDocSub.class, StrucDocFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="content", scope=StrucDocFootnote.class)
  public JAXBElement<StrucDocContent> createStrucDocFootnoteContent(StrucDocContent value)
  {
    return new JAXBElement(_StrucDocItemContent_QNAME, StrucDocContent.class, StrucDocFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="paragraph", scope=StrucDocFootnote.class)
  public JAXBElement<StrucDocParagraph> createStrucDocFootnoteParagraph(StrucDocParagraph value)
  {
    return new JAXBElement(_StrucDocItemParagraph_QNAME, StrucDocParagraph.class, StrucDocFootnote.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="high", scope=IVLPPDTS.class)
  public JAXBElement<IVXBPPDTS> createIVLPPDTSHigh(IVXBPPDTS value)
  {
    return new JAXBElement(_IVLINTHigh_QNAME, IVXBPPDTS.class, IVLPPDTS.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="low", scope=IVLPPDTS.class)
  public JAXBElement<IVXBPPDTS> createIVLPPDTSLow(IVXBPPDTS value)
  {
    return new JAXBElement(_IVLINTLow_QNAME, IVXBPPDTS.class, IVLPPDTS.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="center", scope=IVLPPDTS.class)
  public JAXBElement<PPDTS> createIVLPPDTSCenter(PPDTS value)
  {
    return new JAXBElement(_IVLINTCenter_QNAME, PPDTS.class, IVLPPDTS.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="width", scope=IVLPPDTS.class)
  public JAXBElement<PPDPQ> createIVLPPDTSWidth(PPDPQ value)
  {
    return new JAXBElement(_IVLINTWidth_QNAME, PPDPQ.class, IVLPPDTS.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="high", scope=IVLMO.class)
  public JAXBElement<IVXBMO> createIVLMOHigh(IVXBMO value)
  {
    return new JAXBElement(_IVLINTHigh_QNAME, IVXBMO.class, IVLMO.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="low", scope=IVLMO.class)
  public JAXBElement<IVXBMO> createIVLMOLow(IVXBMO value)
  {
    return new JAXBElement(_IVLINTLow_QNAME, IVXBMO.class, IVLMO.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="center", scope=IVLMO.class)
  public JAXBElement<MO> createIVLMOCenter(MO value)
  {
    return new JAXBElement(_IVLINTCenter_QNAME, MO.class, IVLMO.class, value);
  }

  @XmlElementDecl(namespace="urn:hl7-org:v3", name="width", scope=IVLMO.class)
  public JAXBElement<MO> createIVLMOWidth(MO value)
  {
    return new JAXBElement(_IVLINTWidth_QNAME, MO.class, IVLMO.class, value);
  }
}

