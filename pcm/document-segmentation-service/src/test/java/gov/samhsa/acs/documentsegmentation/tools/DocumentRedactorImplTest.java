package gov.samhsa.acs.documentsegmentation.tools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.brms.domain.ClinicalFact;
import gov.samhsa.acs.brms.domain.Confidentiality;
import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.ObligationPolicyDocument;
import gov.samhsa.acs.brms.domain.RefrainPolicy;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.RuleExecutionResponse;
import gov.samhsa.acs.brms.domain.Sensitivity;
import gov.samhsa.acs.brms.domain.UsPrivacyLaw;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;
import gov.samhsa.acs.documentsegmentation.tools.redact.base.AbstractClinicalFactLevelRedactionHandler;
import gov.samhsa.acs.documentsegmentation.tools.redact.base.AbstractDocumentLevelRedactionHandler;
import gov.samhsa.acs.documentsegmentation.tools.redact.base.AbstractObligationLevelRedactionHandler;
import gov.samhsa.acs.documentsegmentation.tools.redact.base.AbstractPostRedactionLevelRedactionHandler;
import gov.samhsa.acs.documentsegmentation.tools.redact.impl.clinicalfactlevel.Entry;
import gov.samhsa.acs.documentsegmentation.tools.redact.impl.clinicalfactlevel.HumanReadableTextNodeByCode;
import gov.samhsa.acs.documentsegmentation.tools.redact.impl.clinicalfactlevel.HumanReadableTextNodeByDisplayName;
import gov.samhsa.acs.documentsegmentation.tools.redact.impl.documentlevel.UnsupportedHeaderElementHandler;
import gov.samhsa.acs.documentsegmentation.tools.redact.impl.obligationlevel.Section;
import gov.samhsa.acs.documentsegmentation.tools.redact.impl.postredactionlevel.DocumentCleanupForNoEntryAndNoSection;
import gov.samhsa.acs.documentsegmentation.tools.redact.impl.postredactionlevel.RuleExecutionResponseMarkerForRedactedEntries;
import gov.samhsa.acs.documentsegmentation.valueset.ValueSetService;
import gov.samhsa.acs.documentsegmentation.valueset.ValueSetServiceImplMock;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.FileUtils;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentRedactorImplTest {
	private static final String PROBLEMS = "@Problems";

	private static final String ALLERGIES = "@Allergies";
	private static final String MEDICATIONS = "@Medications";
	private static final String RESULTS = "@Results";
	private static final String HIV = "HIV";
	private static final String HIV_SELECTED = "HIV";
	private static final String PSY = "PSY";
	@SuppressWarnings("unused")
	private static final String PSY_SELECTED = "Psychiatric Information";
	private static final String ETH = "ETH";
	private static final String ETH_SELECTED = "Drug Abuse";
	private static final String GDIS = "GDIS";
	private static final String SDV = "SDV";
	private static final String SEX = "SEX";
	private static final String STD = "STD";
	private static final String PROBLEMS_SECTION = "11450-4";

	private static final String ALLERGIES_SECTION = "48765-2";
	private static final String MEDICATIONS_SECTION = "10160-0";
	private static final String RESULTS_SECTION = "30954-2";
	private static final String NOT_SELECTED = "";
	private static final String EMPTY_RULE_EXECUTION_RESPONSE_CONTAINER = "<ruleExecutionContainer><executionResponseList></executionResponseList></ruleExecutionContainer>";

	private static final String MOCK_XACML_RESULT = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>Drug Abuse</pdpObligation><pdpObligation>Psychiatric Information</pdpObligation><pdpObligation>HIV</pdpObligation></xacmlResult>";
	public static final Set<String> headersWhiteList = new HashSet<String>(
			Arrays.asList("realmCode", "typeId", "templateId", "id", "code",
					"title", "effectiveTime", "confidentialityCode",
					"languageCode", "setId", "versionNumber", "copyTime",
					"recordTarget", "author", "dataEnterer", "custodian",
					"legalAuthenticator", "inFulfillmentOf", "documentationOf",
					"relatedDocument", "authorization", "componentOf",
					"component"));
	private static SimpleMarshallerImpl marshaller;

	private static FileReaderImpl fileReader;
	private static DocumentEditorImpl documentEditor;
	private static DocumentXmlConverterImpl documentXmlConverter;
	private static DocumentAccessorImpl documentAccessor;
	private static DocumentAccessorImpl documentAccessorMock;
	private static DocumentFactModelExtractorImpl factModelExtractor;
	private static EmbeddedClinicalDocumentExtractorImpl embeddedClinicalDocumentExtractor;
	private static ValueSetService valueSetService;
	private static DocumentXmlConverterImpl documentXmlConverterSpy;
	private static XacmlResult xacmlResultMock;
	private static DocumentRedactor documentRedactor;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private XmlTransformer xmlTransformer;
	private String c32;
	private String robustC32;
	private String xacmlResult;
	private Document c32Document;
	private RuleExecutionContainer ruleExecutionContainer;

	private RuleExecutionContainer ruleExecutionContainerWithHivEth;

	@Before
	public void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();
		marshaller = new SimpleMarshallerImpl();
		xmlTransformer = new XmlTransformerImpl(marshaller);
		factModelExtractor = new DocumentFactModelExtractorImpl(xmlTransformer);
		documentXmlConverter = new DocumentXmlConverterImpl();
		documentAccessor = new DocumentAccessorImpl();
		documentEditor = new DocumentEditorImpl(new MetadataGeneratorImpl(
				xmlTransformer), fileReader, documentXmlConverter,
				documentAccessor);
		documentAccessorMock = mock(DocumentAccessorImpl.class);
		valueSetService = new ValueSetServiceImplMock(fileReader);
		embeddedClinicalDocumentExtractor = new EmbeddedClinicalDocumentExtractorImpl(
				documentXmlConverter, documentAccessor);

		ruleExecutionContainer = setRuleExecutionContainer();
		ruleExecutionContainerWithHivEth = marshaller.unmarshalFromXml(
				RuleExecutionContainer.class,
				fileReader.readFile("ruleExecutionResponseContainer.xml"));
		xacmlResultMock = setMockXacmlResult(MOCK_XACML_RESULT);
		c32 = fileReader.readFile("sampleC32/c32.xml");
		robustC32 = fileReader
				.readFile("testMU_Rev3_HITSP_C32C83_4Sections_RobustEntries_NoErrors.xml");
		xacmlResult = fileReader.readFile("testXacmlResult.xml");

		documentXmlConverterSpy = setSpyDocumentXmlConverter();

		final Set<AbstractObligationLevelRedactionHandler> obligationLevelChain = new HashSet<AbstractObligationLevelRedactionHandler>();
		final Set<AbstractClinicalFactLevelRedactionHandler> clinicalFactLevelChain = new HashSet<AbstractClinicalFactLevelRedactionHandler>();
		final Set<AbstractPostRedactionLevelRedactionHandler> postRedactionChain = new HashSet<AbstractPostRedactionLevelRedactionHandler>();
		final Set<AbstractDocumentLevelRedactionHandler> documentLevelRedactionHandlers = new HashSet<AbstractDocumentLevelRedactionHandler>();
		documentLevelRedactionHandlers.add(new UnsupportedHeaderElementHandler(
				documentAccessor, headersWhiteList));
		obligationLevelChain.add(new Section(documentAccessor));
		clinicalFactLevelChain.add(new Entry(documentAccessor));
		clinicalFactLevelChain.add(new HumanReadableTextNodeByCode(
				documentAccessor));
		clinicalFactLevelChain.add(new HumanReadableTextNodeByDisplayName(
				documentAccessor));
		postRedactionChain.add(new DocumentCleanupForNoEntryAndNoSection(
				documentAccessor));
		postRedactionChain
		.add(new RuleExecutionResponseMarkerForRedactedEntries(
				documentAccessor));

		documentRedactor = new DocumentRedactorImpl(marshaller,
				documentXmlConverterSpy, documentAccessorMock,
				documentLevelRedactionHandlers, obligationLevelChain,
				clinicalFactLevelChain, postRedactionChain);
	}

	@Test
	public void testCleanUpGeneratedEntryIds() throws Exception {
		// Arrange
		initDocumentRedactorWithActualServices();
		final String c32WithGeneratedEntryIds = fileReader
				.readFile("testC32WithGeneratedEntryIds.xml");

		// Act
		final String cleanC32 = documentRedactor
				.cleanUpGeneratedEntryIds(c32WithGeneratedEntryIds);

		// Assert
		final Document docWithGeneratedEntryIds = documentXmlConverter
				.loadDocument(c32WithGeneratedEntryIds);
		final Document docCleanedUp = documentXmlConverter
				.loadDocument(cleanC32);
		final String xPathExpr = "//hl7:generatedEntryId";
		NodeList foundGeneratedEntryIds = documentAccessor.getNodeList(
				docWithGeneratedEntryIds, xPathExpr);
		// original document must have entry ids
		assertTrue(foundGeneratedEntryIds.getLength() > 0);
		foundGeneratedEntryIds = documentAccessor.getNodeList(docCleanedUp,
				xPathExpr);
		// clean document shouldn't have any entry id elements
		assertTrue(foundGeneratedEntryIds.getLength() == 0);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = DS4PException.class)
	public void testCleanUpGeneratedEntryIds_Throws_Exception()
			throws Exception {
		// Arrange
		final String c32WithGeneratedEntryIds = "someDocument";
		when(documentXmlConverterSpy.loadDocument(c32WithGeneratedEntryIds))
		.thenThrow(Exception.class);

		// Act
		documentRedactor.cleanUpGeneratedEntryIds(c32WithGeneratedEntryIds);
	}

	@Test(expected = DS4PException.class)
	public void testCleanUpGeneratedEntryIds_Throws_Exception2()
			throws Exception {
		// Arrange
		final String c32WithGeneratedEntryIds = "someDocument";
		final Document documentMock = mock(Document.class);
		final NodeList nodeListMock = mock(NodeList.class);
		when(documentXmlConverterSpy.loadDocument(c32WithGeneratedEntryIds))
		.thenReturn(documentMock);
		when(
				documentAccessorMock.getNodeList(documentMock,
						"//hl7:generatedEntryId")).thenReturn(nodeListMock);
		when(nodeListMock.getLength()).thenReturn(0);
		doThrow(Exception.class).when(documentXmlConverterSpy)
		.convertXmlDocToString(documentMock);

		// Act
		documentRedactor.cleanUpGeneratedEntryIds(c32WithGeneratedEntryIds);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = DS4PException.class)
	public void testCleanUpGeneratedEntryIds_Throws_XPathExpressionException()
			throws Exception {
		// Arrange
		when(
				documentAccessorMock.getNodeList(isA(Document.class),
						eq("//hl7:generatedEntryId"))).thenThrow(
								XPathExpressionException.class);

		// Act
		documentRedactor.cleanUpGeneratedEntryIds("someString");
	}

	@Test
	public void testCleanUpGeneratedServiceEventIds() throws Exception {
		// Arrange
		initDocumentRedactorWithActualServices();
		final String c32WithGeneratedServiceEventIds = fileReader
				.readFile("testC32WithGeneratedEntryIds.xml");

		// Act
		final String cleanC32 = documentRedactor
				.cleanUpGeneratedServiceEventIds(c32WithGeneratedServiceEventIds);

		// Assert
		final Document docWithGeneratedServiceEventIds = documentXmlConverter
				.loadDocument(c32WithGeneratedServiceEventIds);
		final Document docCleanedUp = documentXmlConverter
				.loadDocument(cleanC32);
		final String xPathExpr = "//hl7:generatedServiceEventId";
		NodeList foundGeneratedServiceEventIds = documentAccessor.getNodeList(
				docWithGeneratedServiceEventIds, xPathExpr);
		// original document must have ServiceEvent ids
		assertTrue(foundGeneratedServiceEventIds.getLength() > 0);
		foundGeneratedServiceEventIds = documentAccessor.getNodeList(
				docCleanedUp, xPathExpr);
		// clean document shouldn't have any entry id elements
		assertTrue(foundGeneratedServiceEventIds.getLength() == 0);
	}

	@Test
	public void testRedactDocument() throws Throwable {
		// Act
		initDocumentRedactorWithActualServices();
		String factModelXml = factModelExtractor.extractFactModel(c32,
				MOCK_XACML_RESULT);
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultMock);
		final String result = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, factModel).getRedactedDocument();
		logger.debug("RESULT--> " + result);

		// Assert
		assertTrue(c32
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(c32.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
		assertTrue(!result
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(!result.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
	}

	@Test
	public void testRedactDocument_Allergies_Medications_Results_Sections_Hiv_Eth_Redacted()
			throws Throwable {
		// Arrange
		final String problems = NOT_SELECTED;
		final String allergies = ALLERGIES_SECTION;
		final String medications = MEDICATIONS_SECTION;
		final String results = RESULTS_SECTION;
		final String hiv = HIV_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = ETH_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = ruleExecutionContainerWithHivEth;
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(c32,
				marshaller.marshal(xacmlResultObj));
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		final String ethObservationId = "e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7";
		final String hivObservationId = "d11275e7-67ae-11db-bd13-0800200c9a66";
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(c32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(c32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(c32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(c32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, ALLERGIES_SECTION)
				.isPresent());
		// null
		assertFalse(getSectionElementAsOptional(redactedC32,
				MEDICATIONS_SECTION).isPresent());
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, RESULTS_SECTION)
				.isPresent());
		// RuleExecutionContainer has ETH and HIV sensitivities for these
		// observationIds, so the Problems section should exist, but these
		// entries should have been redacted.
		assertFalse(getEntryElementAsOptional(redactedC32, ethObservationId)
				.isPresent());
		assertFalse(getEntryElementAsOptional(redactedC32, hivObservationId)
				.isPresent());
	}

	@Test
	public void testRedactDocument_Allergies_Medications_Results_Sections_Redacted()
			throws Throwable {
		// Arrange
		final String problems = NOT_SELECTED;
		final String allergies = ALLERGIES_SECTION;
		final String medications = MEDICATIONS_SECTION;
		final String results = RESULTS_SECTION;
		final String hiv = NOT_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = NOT_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshal(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, ALLERGIES_SECTION)
				.isPresent());
		// null
		assertFalse(getSectionElementAsOptional(redactedC32,
				MEDICATIONS_SECTION).isPresent());
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, RESULTS_SECTION)
				.isPresent());
	}

	@Test
	public void testRedactDocument_Allergies_Medications_Results_Sections_Redacted_Hiv_Eth_NotRedacted()
			throws Throwable {
		// Arrange
		final String problems = NOT_SELECTED;
		final String allergies = ALLERGIES_SECTION;
		final String medications = MEDICATIONS_SECTION;
		final String results = RESULTS_SECTION;
		final String hiv = NOT_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = NOT_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		final String ethObservationId = "e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7";
		final String hivObservationId = "d11275e7-67ae-11db-bd13-0800200c9a66";
		String factModelXml = factModelExtractor.extractFactModel(c32,
				marshaller.marshal(xacmlResultObj));
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(c32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(c32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(c32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(c32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, ALLERGIES_SECTION)
				.isPresent());
		// null
		assertFalse(getSectionElementAsOptional(redactedC32,
				MEDICATIONS_SECTION).isPresent());
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, RESULTS_SECTION)
				.isPresent());
		// RuleExecutionContainer is empty, so these entries should still be in
		// Problems section
		assertNotNull(getEntryElement(redactedC32, ethObservationId));
		assertNotNull(getEntryElement(redactedC32, hivObservationId));
	}

	@Test
	public void testRedactDocument_Allergies_Section_Redacted()
			throws Throwable {
		// Arrange
		final String problems = NOT_SELECTED;
		final String allergies = ALLERGIES_SECTION;
		final String medications = NOT_SELECTED;
		final String results = NOT_SELECTED;
		final String hiv = NOT_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = NOT_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshal(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, ALLERGIES_SECTION)
				.isPresent());
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Medications_Section_Redacted()
			throws Throwable {
		// Arrange
		final String problems = NOT_SELECTED;
		final String allergies = NOT_SELECTED;
		final String medications = MEDICATIONS_SECTION;
		final String results = NOT_SELECTED;
		final String hiv = NOT_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = NOT_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshal(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32,
				MEDICATIONS_SECTION).isPresent());
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Problems_Allergies_Sections_Redacted()
			throws Throwable {
		// Arrange
		final String problems = PROBLEMS_SECTION;
		final String allergies = ALLERGIES_SECTION;
		final String medications = NOT_SELECTED;
		final String results = NOT_SELECTED;
		final String hiv = NOT_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = NOT_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshal(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, PROBLEMS_SECTION)
				.isPresent());
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, ALLERGIES_SECTION)
				.isPresent());
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Problems_Medications_Sections_Redacted()
			throws Throwable {
		// Arrange
		final String problems = PROBLEMS_SECTION;
		final String allergies = NOT_SELECTED;
		final String medications = MEDICATIONS_SECTION;
		final String results = NOT_SELECTED;
		final String hiv = NOT_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = NOT_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshal(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, PROBLEMS_SECTION)
				.isPresent());
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32,
				MEDICATIONS_SECTION).isPresent());
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Problems_Results_Sections_Redacted()
			throws Throwable {
		// Arrange
		final String problems = PROBLEMS_SECTION;
		final String allergies = NOT_SELECTED;
		final String medications = NOT_SELECTED;
		final String results = RESULTS_SECTION;
		final String hiv = NOT_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = NOT_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshal(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, PROBLEMS_SECTION)
				.isPresent());
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, RESULTS_SECTION)
				.isPresent());
	}

	@Test
	public void testRedactDocument_Problems_Section_Redacted() throws Throwable {
		// Arrange
		final String problems = PROBLEMS_SECTION;
		final String allergies = NOT_SELECTED;
		final String medications = NOT_SELECTED;
		final String results = NOT_SELECTED;
		final String hiv = NOT_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = NOT_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshal(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, PROBLEMS_SECTION)
				.isPresent());
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_RemC32() throws Throwable {
		// Arrange
		initDocumentRedactorWithActualServices();
		String remC32 = fileReader.readFile("testRemC32.xml");
		final String remRuleExecutionContainerActual = fileReader
				.readFile("testRemRuleExecutionContainerActual.xml");
		final RuleExecutionContainer remRuleExecutionContainerActualObj = marshaller
				.unmarshalFromXml(RuleExecutionContainer.class,
						remRuleExecutionContainerActual);
		final String remXacmlResult = fileReader
				.readFile("testRemXacmlResult.xml");
		final XacmlResult remXacmlResultObj = marshaller.unmarshalFromXml(
				XacmlResult.class, remXacmlResult);
		final String factModelXml = factModelExtractor.extractFactModel(remC32,
				marshaller.marshal(remXacmlResultObj));
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		remC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		final String ethObservationId = "d17e379";
		final String hivObservationId = "d17e356";
		factModel.setXacmlResult(xacmlResultMock);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(remC32,
				remRuleExecutionContainerActualObj, factModel)
				.getRedactedDocument();

		// Assert
		// sections
		assertNotNull(getSectionElement(remC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(remC32, MEDICATIONS_SECTION));
		assertFalse(getSectionElementAsOptional(remC32, ALLERGIES_SECTION)
				.isPresent());
		assertFalse(getSectionElementAsOptional(remC32, RESULTS_SECTION)
				.isPresent());
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertFalse(getSectionElementAsOptional(redactedC32, ALLERGIES_SECTION)
				.isPresent());
		assertFalse(getSectionElementAsOptional(redactedC32, RESULTS_SECTION)
				.isPresent());
		// entries
		assertNotNull(getEntryElement(remC32, ethObservationId));
		assertNotNull(getEntryElement(remC32, hivObservationId));
		assertFalse(getEntryElementAsOptional(redactedC32, ethObservationId)
				.isPresent());
		assertFalse(getEntryElementAsOptional(redactedC32, hivObservationId)
				.isPresent());
		// human readable
		assertTrue(getHumanReadableTextNodeList(remC32, "substance abuse")
				.getLength() > 0);
		assertTrue(getHumanReadableTextNodeList(remC32, "hiv").getLength() > 0);
		assertTrue(getHumanReadableTextNodeList(redactedC32, "substance abuse")
				.getLength() == 0);
		assertTrue(getHumanReadableTextNodeList(redactedC32, "hiv").getLength() == 0);
	}

	@Test
	public void testRedactDocument_Results_Section_Redacted() throws Throwable {
		// Arrange
		final String problems = NOT_SELECTED;
		final String allergies = NOT_SELECTED;
		final String medications = NOT_SELECTED;
		final String results = RESULTS_SECTION;
		final String hiv = NOT_SELECTED;
		final String psy = NOT_SELECTED;
		final String eth = NOT_SELECTED;
		final String gdis = NOT_SELECTED;
		final String sdv = NOT_SELECTED;
		final String sex = NOT_SELECTED;
		final String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		final RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		final XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshal(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		final String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		// null
		assertFalse(getSectionElementAsOptional(redactedC32, RESULTS_SECTION)
				.isPresent());
	}

	@Test(expected = DS4PException.class)
	public void testRedactDocument_Throws_DS4PException() {
		@SuppressWarnings("unused")
		final String result = documentRedactor.redactDocument("", null,
				new FactModel()).getRedactedDocument();
	}

	// Sensitivity in container doesn't mean anything anymore.
	@Test
	public void testRedactDocument_WrongSensitivityInContainer()
			throws Exception {
		// Arrange
		initDocumentRedactorWithActualServices();
		final String xacmlResultWithWrongObligations = MOCK_XACML_RESULT
				.replace("<pdpObligation>Drug Abuse</pdpObligation>", "")
				.replace("<pdpObligation>HIV</pdpObligation>", "");
		final XacmlResult xacmlResult = setMockXacmlResult(xacmlResultWithWrongObligations);
		String factModelXml = factModelExtractor.extractFactModel(c32,
				xacmlResultWithWrongObligations);
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResult);

		// Act
		final String result = documentRedactor.redactDocument(c32,
				setRuleExecutionContainer_WrongSensitivity(), factModel)
				.getRedactedDocument();
		logger.debug("RESULT--> " + result);

		// Assert
		assertTrue(c32
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(c32.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
		assertTrue(result
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(result.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
	}

	@Test
	public void testRedactDocument_WrongSensitivityInXacmlResult()
			throws Exception {
		// Arrange
		initDocumentRedactorWithActualServices();
		final String xacmlResultWithWrongObligations = MOCK_XACML_RESULT
				.replace("<pdpObligation>Drug Abuse</pdpObligation>", "")
				.replace("<pdpObligation>HIV</pdpObligation>", "");
		String factModelXml = factModelExtractor.extractFactModel(c32,
				xacmlResultWithWrongObligations);
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factModelXml);
		setValueSetCategories(factModel);
		factModel
		.setXacmlResult(setMockXacmlResult(xacmlResultWithWrongObligations));

		// Act
		final String result = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, factModel).getRedactedDocument();
		logger.debug("RESULT--> " + result);

		// Assert
		assertTrue(c32
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(c32.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
		assertTrue(result
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(result.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
	}

	private Element getEntryElement(String c32, String observationId)
			throws XPathExpressionException, XMLEncryptionException, Exception {
		return getEntryElementAsOptional(c32, observationId).get();
	}

	private Optional<Element> getEntryElementAsOptional(String c32,
			String observationId) throws XPathExpressionException,
			XMLEncryptionException, Exception {
		String xPathExprSection = "//hl7:id[@root='%']/ancestor::hl7:entry";
		xPathExprSection = xPathExprSection.replace("%", observationId);
		return documentAccessor.getElement(
				documentXmlConverter.loadDocument(c32), xPathExprSection);
	}

	private NodeList getHumanReadableTextNodeList(String c32, String textContent)
			throws XPathExpressionException, XMLEncryptionException, Exception {
		String xPathExprHumanReadableTextNode = "//hl7:section/hl7:text//*/text()[contains(lower-case(.), '%')]";
		xPathExprHumanReadableTextNode = xPathExprHumanReadableTextNode
				.replace("%", textContent.toLowerCase());
		final NodeList nodeList = documentAccessor.getNodeList(
				documentXmlConverter.loadDocument(c32),
				xPathExprHumanReadableTextNode);
		return nodeList;
	}

	private Element getSectionElement(String c32, String sectionLoincCode)
			throws XPathExpressionException, XMLEncryptionException, Exception {
		return getSectionElementAsOptional(c32, sectionLoincCode).get();
	}

	private Optional<Element> getSectionElementAsOptional(String c32,
			String sectionLoincCode) throws XPathExpressionException,
			XMLEncryptionException, Exception {
		String xPathExprSection = "//hl7:component[hl7:section[hl7:code[@code='%']]]";
		xPathExprSection = xPathExprSection.replace("%", sectionLoincCode);
		return documentAccessor.getElement(
				documentXmlConverter.loadDocument(c32), xPathExprSection);
	}

	private void initDocumentRedactorWithActualServices() {
		ReflectionTestUtils.setField(documentRedactor, "documentXmlConverter",
				documentXmlConverter);
		ReflectionTestUtils.setField(documentRedactor, "documentAccessor",
				documentAccessor);
	}

	private RuleExecutionContainer initRuleExecutionContainer()
			throws JAXBException {
		final RuleExecutionContainer ruleExecutionContainer = marshaller
				.unmarshalFromXml(RuleExecutionContainer.class,
						EMPTY_RULE_EXECUTION_RESPONSE_CONTAINER);
		return ruleExecutionContainer;
	}

	private XacmlResult initXacmlResult(String problems, String allergies,
			String medications, String results, String hiv, String psy,
			String eth, String gdis, String sdv, String sex, String std)
					throws JAXBException {
		final String xacmlResultForTest = xacmlResult
				.replace(PROBLEMS, problems).replace(ALLERGIES, allergies)
				.replace(MEDICATIONS, medications).replace(RESULTS, results)
				.replace(HIV, hiv).replace(PSY, psy).replace(ETH, eth)
				.replace(GDIS, gdis).replace(SDV, sdv).replace(SEX, sex)
				.replace(STD, std);
		final XacmlResult xacmlResultObj = marshaller.unmarshalFromXml(
				XacmlResult.class, xacmlResultForTest);
		xacmlResultObj.getPdpObligations().removeAll(Arrays.asList("", null));
		return xacmlResultObj;
	}

	private String removeEmbeddedClinicalDocument(String factModelXml)
			throws Exception, XPathExpressionException, IOException {
		final Document fmDoc = documentXmlConverter.loadDocument(factModelXml);
		final Node ecd = documentAccessor.getNode(fmDoc,
				"//hl7:EmbeddedClinicalDocument").get();
		ecd.getParentNode().removeChild(ecd);
		factModelXml = documentXmlConverter.convertXmlDocToString(fmDoc);
		return factModelXml;
	}

	@SuppressWarnings("unused")
	private DocumentAccessor setMockDocumentAccessor()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		final DocumentAccessor mock = mock(DocumentAccessor.class);

		final Optional<Element> e1 = documentAccessor.getElement(c32Document,
				"//hl7:td[.='Substance Abuse Disorder']/parent::hl7:tr");
		final Optional<Element> e2 = documentAccessor
				.getElement(
						c32Document,
						"//hl7:id[@root='e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7']/ancestor::hl7:entry");
		final Optional<Element> e3 = documentAccessor.getElement(c32Document,
				"//hl7:td[.='Acute HIV']/parent::hl7:tr");
		final Optional<Element> e4 = documentAccessor
				.getElement(c32Document,
						"//hl7:id[@root='d11275e7-67ae-11db-bd13-0800200c9a66']/ancestor::hl7:entry");
		when(mock.getElement(isA(Document.class), anyString())).thenReturn(e1)
		.thenReturn(e2).thenReturn(e3).thenReturn(e4).thenReturn(null)
		.thenReturn(null);

		return mock;
	}

	private DocumentXmlConverterImpl setSpyDocumentXmlConverter()
			throws Exception {
		final DocumentXmlConverterImpl converter = new DocumentXmlConverterImpl();
		final DocumentXmlConverterImpl spy = spy(converter);

		c32Document = readDocument("src/test/resources/xmlDocument.txt");

		doReturn(c32Document).when(spy).loadDocument(anyString());
		return spy;
	}

	private void setValueSetCategories(FactModel factModel) {
		// Get and set value set categories to clinical facts
		for (final ClinicalFact fact : factModel.getClinicalFactList()) {
			// Get value set categories
			final Set<String> valueSetCategories = valueSetService
					.lookupValueSetCategories(fact.getCode(),
							fact.getCodeSystem());
			// Set retrieved value set categories to the clinical fact
			fact.setValueSetCategories(valueSetCategories);
		}
	}

	private static Document readDocument(String filePath) throws IOException,
	ClassNotFoundException {
		return (Document) readObject(filePath);
	}

	private static Object readObject(String filePath) throws IOException,
	ClassNotFoundException {
		final File file = new File(filePath);
		byte[] b = null;

		b = FileUtils.readFileToByteArray(file);
		final ByteArrayInputStream in = new ByteArrayInputStream(b);
		final ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}

	private static XacmlResult setMockXacmlResult(String xacmlResultXml)
			throws JAXBException {
		/*
		 * XacmlResult mock = mock(XacmlResult.class); List<String> obligations
		 * = new LinkedList<String>(); obligations.add("ETH");
		 * obligations.add("HIV");
		 * when(mock.getPdpObligations()).thenReturn(obligations)
		 * .thenReturn(obligations).thenReturn(obligations); return mock;
		 */
		return marshaller.unmarshalFromXml(XacmlResult.class, xacmlResultXml);
	}

	@SuppressWarnings("unused")
	private static XacmlResult setMockXacmlResult_WrongSensitivity() {
		final XacmlResult mock = mock(XacmlResult.class);
		final List<String> obligations = new LinkedList<String>();
		obligations.add("STD");
		obligations.add("SEX");
		when(mock.getPdpObligations()).thenReturn(obligations)
		.thenReturn(obligations).thenReturn(obligations);
		return mock;
	}

	private static RuleExecutionContainer setRuleExecutionContainer() {
		final RuleExecutionContainer container = new RuleExecutionContainer();
		final RuleExecutionResponse r1 = new RuleExecutionResponse();
		r1.setC32SectionLoincCode("11450-4");
		r1.setC32SectionTitle("Problems");
		r1.setCode("66214007");
		r1.setCodeSystemName("SNOMED CT");
		r1.setDisplayName("Substance Abuse Disorder");
		r1.setDocumentObligationPolicy(ObligationPolicyDocument.ENCRYPT);
		r1.setDocumentRefrainPolicy(RefrainPolicy.NODSCLCD);
		r1.setImpliedConfSection(Confidentiality.R);
		r1.setItemAction("REDACT");
		r1.setObservationId("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7");
		r1.setSensitivity(Sensitivity.ETH);
		r1.setUSPrivacyLaw(UsPrivacyLaw._42CFRPart2);
		final RuleExecutionResponse r2 = new RuleExecutionResponse();
		r2.setC32SectionLoincCode("11450-4");
		r2.setC32SectionTitle("Problems");
		r2.setCode("111880001");
		r2.setCodeSystemName("SNOMED CT");
		r2.setDisplayName("Acute HIV");
		r2.setDocumentObligationPolicy(ObligationPolicyDocument.ENCRYPT);
		r2.setDocumentRefrainPolicy(RefrainPolicy.NODSCLCD);
		r2.setImpliedConfSection(Confidentiality.R);
		r2.setItemAction("MASK");
		r2.setObservationId("d11275e7-67ae-11db-bd13-0800200c9a66");
		r2.setSensitivity(Sensitivity.HIV);
		r2.setUSPrivacyLaw(UsPrivacyLaw._42CFRPart2);
		final List<RuleExecutionResponse> list = new LinkedList<RuleExecutionResponse>();
		list.add(r1);
		list.add(r2);
		container.setExecutionResponseList(list);
		return container;
	}

	private static RuleExecutionContainer setRuleExecutionContainer_WrongSensitivity() {
		final RuleExecutionContainer container = new RuleExecutionContainer();
		final RuleExecutionResponse r1 = new RuleExecutionResponse();
		r1.setC32SectionLoincCode("11450-4");
		r1.setC32SectionTitle("Problems");
		r1.setCode("66214007");
		r1.setCodeSystemName("SNOMED CT");
		r1.setDisplayName("Substance Abuse Disorder");
		r1.setDocumentObligationPolicy(ObligationPolicyDocument.ENCRYPT);
		r1.setDocumentRefrainPolicy(RefrainPolicy.NODSCLCD);
		r1.setImpliedConfSection(Confidentiality.R);
		r1.setItemAction("REDACT");
		r1.setObservationId("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7");
		r1.setSensitivity(Sensitivity.STD);
		r1.setUSPrivacyLaw(UsPrivacyLaw._42CFRPart2);
		final RuleExecutionResponse r2 = new RuleExecutionResponse();
		r2.setC32SectionLoincCode("11450-4");
		r2.setC32SectionTitle("Problems");
		r2.setCode("111880001");
		r2.setCodeSystemName("SNOMED CT");
		r2.setDisplayName("Acute HIV");
		r2.setDocumentObligationPolicy(ObligationPolicyDocument.ENCRYPT);
		r2.setDocumentRefrainPolicy(RefrainPolicy.NODSCLCD);
		r2.setImpliedConfSection(Confidentiality.R);
		r2.setItemAction("MASK");
		r2.setObservationId("d11275e7-67ae-11db-bd13-0800200c9a66");
		r2.setSensitivity(Sensitivity.SEX);
		r2.setUSPrivacyLaw(UsPrivacyLaw._42CFRPart2);
		final List<RuleExecutionResponse> list = new LinkedList<RuleExecutionResponse>();
		list.add(r1);
		list.add(r2);
		container.setExecutionResponseList(list);
		return container;
	}
}
