package gov.samhsa.acs.documentsegmentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.audit.AcsAuditVerb;
import gov.samhsa.acs.audit.AuditServiceImpl;
import gov.samhsa.acs.audit.AuditVerb;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.brms.RuleExecutionService;
import gov.samhsa.acs.brms.domain.Confidentiality;
import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.ObligationPolicyDocument;
import gov.samhsa.acs.brms.domain.RefrainPolicy;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.RuleExecutionResponse;
import gov.samhsa.acs.brms.domain.Sensitivity;
import gov.samhsa.acs.brms.domain.SubjectPurposeOfUse;
import gov.samhsa.acs.brms.domain.UsPrivacyLaw;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;
import gov.samhsa.acs.common.util.FileHelper;
import gov.samhsa.acs.common.validation.XmlValidation;
import gov.samhsa.acs.common.validation.XmlValidationResult;
import gov.samhsa.acs.common.validation.exception.InvalidXmlDocumentException;
import gov.samhsa.acs.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.acs.documentsegmentation.dto.SegmentDocumentResponse;
import gov.samhsa.acs.documentsegmentation.exception.InvalidOriginalClinicalDocumentException;
import gov.samhsa.acs.documentsegmentation.exception.InvalidSegmentedClinicalDocumentException;
import gov.samhsa.acs.documentsegmentation.tools.AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentEditorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentFactModelExtractorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentRedactor;
import gov.samhsa.acs.documentsegmentation.tools.DocumentRedactorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentTaggerImpl;
import gov.samhsa.acs.documentsegmentation.tools.EmbeddedClinicalDocumentExtractorImpl;
import gov.samhsa.acs.documentsegmentation.tools.MetadataGeneratorImpl;
import gov.samhsa.acs.documentsegmentation.tools.dto.RedactedDocument;
import gov.samhsa.acs.documentsegmentation.valueset.ValueSetServiceImplMock;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.xpath.XPathExpressionException;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.utils.EncryptionConstants;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.qos.logback.audit.AuditException;

public class DocumentSegmentationImplTest {
	private static String xacmlResult;

	private static String factModel;
	private static XacmlResult xacmlResultObj;
	private static FactModel factModelObj;
	private static RuleExecutionContainer ruleExecutionContainerObj;
	private static String senderEmailAddress;

	private static String recipientEmailAddress;
	private static FileReader fileReader;

	private static DocumentXmlConverterImpl documentXmlConverter;
	private static SimpleMarshallerImpl marshaller;
	private static RuleExecutionService ruleExecutionServiceClientMock;

	private static AuditServiceImpl auditServiceMock;
	private static DocumentEditorImpl documentEditorMock;
	private static DocumentFactModelExtractorImpl documentFactModelExtractorMock;
	private static SimpleMarshallerImpl marshallerMock;
	private static DocumentRedactor documentRedactorMock;
	private static DocumentTaggerImpl documentTaggerMock;
	private static EmbeddedClinicalDocumentExtractorImpl embeddedClinicalDocumentExtractorMock;
	private static AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock;
	private static XmlValidation xmlValidatorMock;
	private static RedactedDocument redactedDocumentMock;
	private static String testOriginal_C32_xml;

	private static String testFactModel_xml;
	private static String testExecutionResponseContainer_xml;
	private static String testRedacted_C32_xml;
	private static String testTagged_C32_xml;
	private static String testMasked_C32_xml;
	private static String testEncrypted_C32_xml;
	private static String testAdditionalMetadata_xml;
	private static final String PURPOSE_OF_USE = "TREATMENT";

	private static final String XDS_ENTRY_ID = "123";
	private static final String MESSAGE_ID = "cf8cace6-6331-4a45-8e79-5bf503925be4";
	private static DocumentSegmentation documentSegmentation;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() throws XPathExpressionException,
			XMLEncryptionException, Exception {
		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		recipientEmailAddress = "Duane_Decouteau@direct.healthvault-stage.com";

		// File reader
		fileReader = new FileReaderImpl();
		// Marshaller
		marshaller = new SimpleMarshallerImpl();
		// Document-Xml converter
		documentXmlConverter = new DocumentXmlConverterImpl();

		testOriginal_C32_xml = fileReader.readFile("testOriginal_C32.xml");
		testFactModel_xml = fileReader.readFile("testFactModel.xml");
		testExecutionResponseContainer_xml = fileReader
				.readFile("testExecutionResponseContainer.xml");
		testRedacted_C32_xml = fileReader.readFile("testRedacted_C32.xml");
		testTagged_C32_xml = fileReader.readFile("testTagged_C32.xml");
		testMasked_C32_xml = fileReader.readFile("testMasked_C32.xml");
		testEncrypted_C32_xml = fileReader.readFile("testEncrypted_C32.xml");
		testAdditionalMetadata_xml = fileReader
				.readFile("testAdditionalMetadata.xml");

		redactedDocumentMock = mock(RedactedDocument.class);
		when(redactedDocumentMock.getRedactedDocument()).thenReturn(
				testRedacted_C32_xml);

		// Xacml result
		xacmlResult = "<xacmlResult><pdpDecision>PERMIT</pdpDecision><purposeOfUse>TREATMENT</purposeOfUse><messageId>cf8cace6-6331-4a45-8e79-5bf503925be4</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>51848-0</pdpObligation><pdpObligation>121181</pdpObligation><pdpObligation>47420-5</pdpObligation><pdpObligation>46240-8</pdpObligation><pdpObligation>ETH</pdpObligation><pdpObligation>GDIS</pdpObligation><pdpObligation>PSY</pdpObligation><pdpObligation>SEX</pdpObligation><pdpObligation>18748-4</pdpObligation><pdpObligation>11504-8</pdpObligation><pdpObligation>34117-2</pdpObligation></xacmlResult>";
		xacmlResultObj = setXacmlResult();

		// FactModel
		factModel = "<FactModel><xacmlResult><pdpDecision>PERMIT</pdpDecision><purposeOfUse>TREATMENT</purposeOfUse><messageId>cf8cace6-6331-4a45-8e79-5bf503925be4</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>51848-0</pdpObligation><pdpObligation>121181</pdpObligation><pdpObligation>47420-5</pdpObligation><pdpObligation>46240-8</pdpObligation><pdpObligation>ETH</pdpObligation><pdpObligation>GDIS</pdpObligation><pdpObligation>PSY</pdpObligation><pdpObligation>SEX</pdpObligation><pdpObligation>18748-4</pdpObligation><pdpObligation>11504-8</pdpObligation><pdpObligation>34117-2</pdpObligation></xacmlResult><ClinicalFacts><ClinicalFact><code>111880001</code><displayName>Acute HIV</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>d11275e7-67ae-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>66214007</code><displayName>Substance Abuse Disorder</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7</observationId></ClinicalFact><ClinicalFact><code>234391009</code><displayName>Sickle Cell Anemia</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>ab1791b0-5c71-11db-b0de-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>233604007</code><displayName>Pneumonia</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>9d3d416d-45ab-4da1-912f-4583e0632000</observationId></ClinicalFact><ClinicalFact><code>22298006</code><displayName>Myocardial infarction</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId/></ClinicalFact><ClinicalFact><code>77386006</code><displayName>Patient currently pregnant</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId/></ClinicalFact><ClinicalFact><code>70618</code><displayName>Penicillin</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Allergies and Adverse Reactions</c32SectionTitle><c32SectionLoincCode>48765-2</c32SectionLoincCode><observationId>4adc1020-7b14-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>1191</code><displayName>Aspirin</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Allergies and Adverse Reactions</c32SectionTitle><c32SectionLoincCode>48765-2</c32SectionLoincCode><observationId>eb936011-7b17-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>2670</code><displayName>Codeine</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Allergies and Adverse Reactions</c32SectionTitle><c32SectionLoincCode>48765-2</c32SectionLoincCode><observationId>c3df3b60-7b18-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>6736007</code><displayName>moderate</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Allergies and Adverse Reactions</c32SectionTitle><c32SectionLoincCode>48765-2</c32SectionLoincCode><observationId/></ClinicalFact><ClinicalFact><code>993536</code><displayName>Bupropion Hydrochloride 200 MG Extended Release Tablet</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><observationId>cdbd5b05-6cde-11db-9fe1-08002tg964rfh8823ejba-00c9a66</observationId></ClinicalFact><ClinicalFact><code>309362</code><displayName>Clopidogrel 75 MG oral tablet</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><observationId>cdbd5b05-6cde-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>430618</code><displayName>Metoprolol 25 MG oral tablet</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><observationId>cdbd5b01-6cde-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>312615</code><displayName>Prednisone 20 MG oral tablet</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><observationId>cdbd5b03-6cde-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>197454</code><displayName>Cephalexin 500 MG oral tablet</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><observationId>cdbd5b07-6cde-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>30313-1</code><displayName>HGB</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>107c2dc0-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>33765-9</code><displayName>WBC</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>8b3fa370-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>26515-7</code><displayName>PLT</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>80a6c740-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>2951-2</code><displayName>NA</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>a40027e1-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>2823-3</code><displayName>K</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>a40027e2-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>2075-0</code><displayName>CL</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>a40027e3-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>1963-8</code><displayName>HCO3</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>a40027e4-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>43789009</code><displayName>CBC WO DIFFERENTIAL</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId/></ClinicalFact><ClinicalFact><code>20109005</code><displayName>LYTES</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED CT</codeSystemName><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId/></ClinicalFact></ClinicalFacts></FactModel>";
		factModelObj = marshaller.unmarshalFromXml(FactModel.class, factModel);
		// Document editor mock
		documentEditorMock = mock(DocumentEditorImpl.class);
		when(documentEditorMock.setDocumentCreationDate(testOriginal_C32_xml))
				.thenReturn(testOriginal_C32_xml);
		when(
				documentEditorMock.setDocumentPayloadRawData(anyString(),
						anyBoolean(), anyString(), anyString(),
						any(XacmlResult.class), anyString(), any(byte[].class),
						any(byte[].class))).thenReturn(null);

		// Fact model extractor mock
		documentFactModelExtractorMock = mock(DocumentFactModelExtractorImpl.class);
		when(
				documentFactModelExtractorMock.extractFactModel(
						testOriginal_C32_xml, xacmlResult)).thenReturn(
				testFactModel_xml);

		// BRMS client mock
		ruleExecutionServiceClientMock = mock(RuleExecutionService.class);
		when(
				ruleExecutionServiceClientMock
						.assertAndExecuteClinicalFacts(testFactModel_xml))
				.thenReturn(mock(AssertAndExecuteClinicalFactsResponse.class));

		when(
				ruleExecutionServiceClientMock.assertAndExecuteClinicalFacts(
						testFactModel_xml).getRuleExecutionResponseContainer())
				.thenReturn(testExecutionResponseContainer_xml);
		final AssertAndExecuteClinicalFactsResponse cfr = mock(AssertAndExecuteClinicalFactsResponse.class);
		when(cfr.getRuleExecutionResponseContainer()).thenReturn(
				testExecutionResponseContainer_xml);
		doReturn(cfr).when(ruleExecutionServiceClientMock)
				.assertAndExecuteClinicalFacts(factModelObj);

		// Marshaller mock
		marshallerMock = mock(SimpleMarshallerImpl.class);
		ruleExecutionContainerObj = setRuleExecutionContainer();
		when(
				marshallerMock.unmarshalFromXml(
						eq(RuleExecutionContainer.class), anyString()))
				.thenReturn(ruleExecutionContainerObj);
		when(
				marshallerMock.unmarshalFromXml(eq(XacmlResult.class),
						anyString())).thenReturn(xacmlResultObj);
		when(marshallerMock.unmarshalFromXml(eq(FactModel.class), anyString()))
				.thenReturn(factModelObj);
		when(marshallerMock.marshal(ruleExecutionContainerObj)).thenReturn(
				testExecutionResponseContainer_xml);

		// Documnent redactor
		documentRedactorMock = mock(DocumentRedactorImpl.class);
		when(
				documentRedactorMock.redactDocument(eq(testOriginal_C32_xml),
						eq(ruleExecutionContainerObj), isA(FactModel.class)))
				.thenReturn(redactedDocumentMock);

		// Document tagger
		documentTaggerMock = mock(DocumentTaggerImpl.class);
		when(
				documentTaggerMock.tagDocument(testRedacted_C32_xml,
						testExecutionResponseContainer_xml)).thenReturn(
				testTagged_C32_xml);
		when(documentRedactorMock.cleanUpGeneratedEntryIds(testTagged_C32_xml))
				.thenReturn(testTagged_C32_xml);
		when(
				documentRedactorMock
						.cleanUpGeneratedServiceEventIds(testTagged_C32_xml))
				.thenReturn(testTagged_C32_xml);

		// Audit service mock
		auditServiceMock = mock(AuditServiceImpl.class);
		doNothing().when(auditServiceMock).audit(anyObject(), anyString(),
				isA(AuditVerb.class), anyString(),
				anyMapOf(PredicateKey.class, String.class));

		// Document masker
		/*
		 * documentMaskerMock = mock(DocumentMaskerImpl.class); when(
		 * documentMaskerMock.maskDocument(eq(testTagged_C32_xml),
		 * any(Key.class), eq(ruleExecutionContainerObj),
		 * eq(xacmlResultObj))).thenReturn(testMasked_C32_xml);
		 */

		// Document encrypter
		/*
		 * documentEncrypterMock = mock(DocumentEncrypterImpl.class); when(
		 * documentEncrypterMock.encryptDocument(any(Key.class),
		 * eq(testMasked_C32_xml), eq(ruleExecutionContainerObj)))
		 * .thenReturn(testEncrypted_C32_xml);
		 */

		// Additional Metadata Generator For Segmented Clinical Document
		additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock = mock(AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl.class);
		when(
				additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock
						.generateMetadataXml(MESSAGE_ID, testTagged_C32_xml,
								testExecutionResponseContainer_xml,
								senderEmailAddress, recipientEmailAddress,
								PURPOSE_OF_USE, XDS_ENTRY_ID)).thenReturn(
				testAdditionalMetadata_xml);
		embeddedClinicalDocumentExtractorMock = mock(EmbeddedClinicalDocumentExtractorImpl.class);
		when(
				embeddedClinicalDocumentExtractorMock
						.extractClinicalDocumentFromFactModel(testFactModel_xml))
				.thenReturn(testOriginal_C32_xml);

		xmlValidatorMock = mock(XmlValidation.class);
		when(xmlValidatorMock.validate(testOriginal_C32_xml)).thenReturn(true);
		when(xmlValidatorMock.validate(testTagged_C32_xml)).thenReturn(true);
		when(xmlValidatorMock.validate(testMasked_C32_xml)).thenReturn(true);
		when(xmlValidatorMock.validate(testEncrypted_C32_xml)).thenReturn(true);
		final XmlValidationResult xmlValidationResultMock = mock(XmlValidationResult.class);
		when(xmlValidationResultMock.isValid()).thenReturn(true);
		when(xmlValidatorMock.validateWithAllErrors(testOriginal_C32_xml))
				.thenReturn(xmlValidationResultMock);
		when(xmlValidatorMock.validateWithAllErrors(testTagged_C32_xml))
				.thenReturn(xmlValidationResultMock);
		when(xmlValidatorMock.validateWithAllErrors(testMasked_C32_xml))
				.thenReturn(xmlValidationResultMock);
		when(xmlValidatorMock.validateWithAllErrors(testEncrypted_C32_xml))
				.thenReturn(xmlValidationResultMock);

		documentSegmentation = new DocumentSegmentationImpl(
				ruleExecutionServiceClientMock, auditServiceMock,
				documentEditorMock, marshallerMock, documentRedactorMock,
				documentTaggerMock, documentFactModelExtractorMock,
				embeddedClinicalDocumentExtractorMock,
				new ValueSetServiceImplMock(fileReader),
				additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock);
	}

	@Test
	public void testSegmentDocument_Decrypt_Document_From_Zip() {

		Document processedDoc;
		DESedeKeySpec desedeEncryptKeySpec;
		DESedeKeySpec desedeMaskKeySpec;
		try {

			org.apache.xml.security.Init.init();

			final ZipInputStream zis = new ZipInputStream(Thread
					.currentThread().getContextClassLoader()
					.getResourceAsStream("Patientone_Asample_XDM.zip"));

			final byte[] processDocBytes = entryBytesFromZipBytes(zis,
					"SUBSET01/DOCUMENT.xml");
			final String processDocString = new String(processDocBytes);
			FileHelper.writeStringToFile(processDocString,
					"processDocString.xml");

			processedDoc = documentXmlConverter.loadDocument(processDocString);

			final byte[] kekEncryptionKeyBytes = entryBytesFromZipBytes(zis,
					"kekEncryptionKey");

			desedeEncryptKeySpec = new DESedeKeySpec(kekEncryptionKeyBytes);
			final SecretKeyFactory skfEncrypt = SecretKeyFactory
					.getInstance("DESede");
			final SecretKey desedeEncryptKey = skfEncrypt
					.generateSecret(desedeEncryptKeySpec);

			final byte[] kekMaskingKeyBytes = entryBytesFromZipBytes(zis,
					"kekMaskingKey");

			desedeMaskKeySpec = new DESedeKeySpec(kekMaskingKeyBytes);
			final SecretKeyFactory skfMask = SecretKeyFactory
					.getInstance("DESede");
			final SecretKey desedeMaskKey = skfMask
					.generateSecret(desedeMaskKeySpec);

			zis.close();

			/*************************************************
			 * DECRYPT DOCUMENT
			 *************************************************/
			final Element encryptedDataElement = (Element) processedDoc
					.getElementsByTagNameNS(
							EncryptionConstants.EncryptionSpecNS,
							EncryptionConstants._TAG_ENCRYPTEDDATA).item(0);

			/*
			 * The key to be used for decrypting xml data would be obtained from
			 * the keyinfo of the EncrypteData using the kek.
			 */
			final XMLCipher xmlCipher = XMLCipher.getInstance();
			xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
			xmlCipher.setKEK(desedeEncryptKey);

			/*
			 * The following doFinal call replaces the encrypted data with
			 * decrypted contents in the document.
			 */
			if (encryptedDataElement != null) {
				xmlCipher.doFinal(processedDoc, encryptedDataElement);
			}

			/*************************************************
			 * DECRYPT ELEMENTS
			 *************************************************/
			NodeList encryptedDataElements = processedDoc
					.getElementsByTagNameNS(
							EncryptionConstants.EncryptionSpecNS,
							EncryptionConstants._TAG_ENCRYPTEDDATA);

			while (encryptedDataElements.getLength() > 0) {
				/*
				 * The key to be used for decrypting xml data would be obtained
				 * from the keyinfo of the EncrypteData using the kek.
				 */
				final XMLCipher xmlMaskCipher = XMLCipher.getInstance();
				xmlMaskCipher.init(XMLCipher.DECRYPT_MODE, null);
				xmlMaskCipher.setKEK(desedeMaskKey);

				xmlMaskCipher.doFinal(processedDoc,
						(Element) encryptedDataElements.item(0));

				encryptedDataElements = processedDoc.getElementsByTagNameNS(
						EncryptionConstants.EncryptionSpecNS,
						EncryptionConstants._TAG_ENCRYPTEDDATA);
			}

			FileHelper.writeDocToFile(processedDoc,
					"unitTest_DecryptedUnMasked_C32_from_zip.xml");

		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test(expected = DS4PException.class)
	public void testSegmentDocument_Given_Real_DocumentEditor_Throws_DS4PException()
			throws IOException, InvalidOriginalClinicalDocumentException,
			InvalidSegmentedClinicalDocumentException, AuditException {
		// Arrange
		final boolean xdm = true;
		final boolean ecrypt = true;
		final XmlValidationResult xmlValidationResultTrue = mock(XmlValidationResult.class);
		when(xmlValidationResultTrue.isValid()).thenReturn(true);
		when(xmlValidatorMock.validateWithAllErrors("")).thenReturn(
				xmlValidationResultTrue);
		final DocumentEditorImpl realDocumentEditorImpl = new DocumentEditorImpl(
				new MetadataGeneratorImpl(new XmlTransformerImpl(
						new SimpleMarshallerImpl())), new FileReaderImpl(),
				new DocumentXmlConverterImpl(), new DocumentAccessorImpl());

		final DocumentSegmentationImpl documentSegmentationWithRealDocumentEditor = new DocumentSegmentationImpl(
				ruleExecutionServiceClientMock, auditServiceMock,
				realDocumentEditorImpl, marshallerMock, documentRedactorMock,
				documentTaggerMock, documentFactModelExtractorMock,
				embeddedClinicalDocumentExtractorMock,
				new ValueSetServiceImplMock(fileReader),
				additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock);
		ReflectionTestUtils.setField(
				documentSegmentationWithRealDocumentEditor, "xmlValidator",
				xmlValidatorMock);

		// Act
		@SuppressWarnings("unused")
		final SegmentDocumentResponse resp = documentSegmentationWithRealDocumentEditor
				.segmentDocument("", "", false, true, true);

		// Assert
		// expect DS4PException
	}

	@Test(expected = DS4PException.class)
	public void testSegmentDocument_Given_Real_Marshaller_Throws_DS4PException()
			throws IOException, InvalidOriginalClinicalDocumentException,
			InvalidSegmentedClinicalDocumentException, AuditException {
		// Arrange
		final boolean xdm = true;
		final boolean ecrypt = true;
		final XmlValidationResult xmlValidationResultMock = mock(XmlValidationResult.class);
		when(xmlValidationResultMock.isValid()).thenReturn(true);
		when(xmlValidatorMock.validateWithAllErrors("")).thenReturn(
				xmlValidationResultMock);
		final DocumentSegmentationImpl documentSegmentationWithRealMarshaller = new DocumentSegmentationImpl(
				ruleExecutionServiceClientMock, auditServiceMock,
				documentEditorMock, new SimpleMarshallerImpl(),
				documentRedactorMock, documentTaggerMock,
				documentFactModelExtractorMock,
				embeddedClinicalDocumentExtractorMock,
				new ValueSetServiceImplMock(fileReader),
				additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock);
		ReflectionTestUtils.setField(documentSegmentationWithRealMarshaller,
				"xmlValidator", xmlValidatorMock);
		// Act
		@SuppressWarnings("unused")
		final SegmentDocumentResponse resp = documentSegmentationWithRealMarshaller
				.segmentDocument("", "", false, true, true);

		// Assert
		// expect DS4PException
	}

	@Test(expected = XmlDocumentReadFailureException.class)
	public void testSegmentDocument_Given_Real_Marshaller_Throws_XmlDocumentReadFailureException()
			throws IOException, InvalidXmlDocumentException, AuditException {
		// Arrange
		final boolean xdm = true;
		final boolean ecrypt = true;
		final XmlValidation validationMock = mock(XmlValidation.class);
		doThrow(XmlDocumentReadFailureException.class).when(validationMock)
				.validateWithAllErrors("");
		final DocumentSegmentationImpl documentSegmentationWithRealMarshaller = new DocumentSegmentationImpl(
				ruleExecutionServiceClientMock, auditServiceMock,
				documentEditorMock, new SimpleMarshallerImpl(),
				documentRedactorMock, documentTaggerMock,
				documentFactModelExtractorMock,
				embeddedClinicalDocumentExtractorMock,
				new ValueSetServiceImplMock(fileReader),
				additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock);
		ReflectionTestUtils.setField(documentSegmentationWithRealMarshaller,
				"xmlValidator", validationMock);

		// Act
		@SuppressWarnings("unused")
		final SegmentDocumentResponse resp = documentSegmentationWithRealMarshaller
				.segmentDocument("", "", false, true, true);

		// Assert
		// expect DS4PException
	}

	@Test
	public void testSegmentDocument_Given_XdmFalse_EncryptFalse()
			throws Exception {
		// Arrange
		final boolean xdm = false;
		final boolean ecrypt = false;
		ReflectionTestUtils.setField(documentSegmentation, "xmlValidator",
				xmlValidatorMock);

		// Act
		final SegmentDocumentResponse resp = documentSegmentation
				.segmentDocument(testOriginal_C32_xml, xacmlResult, false,
						true, true);
		documentSegmentation.setAdditionalMetadataForSegmentedClinicalDocument(
				resp, senderEmailAddress, recipientEmailAddress, XDS_ENTRY_ID,
				xacmlResultObj);
		documentSegmentation.setDocumentPayloadRawData(resp, xdm,
				senderEmailAddress, recipientEmailAddress, xacmlResultObj);

		// Assert
		validateResponse(resp, ecrypt);
	}

	@Test
	public void testSegmentDocument_Given_XdmFalse_EncryptFalse_AuditTrue()
			throws Exception {
		// Arrange
		final boolean xdm = false;
		final boolean ecrypt = false;
		ReflectionTestUtils.setField(documentSegmentation, "xmlValidator",
				xmlValidatorMock);

		// Act
		final SegmentDocumentResponse resp = documentSegmentation
				.segmentDocument(testOriginal_C32_xml, xacmlResult, true, true,
						true);
		documentSegmentation.setAdditionalMetadataForSegmentedClinicalDocument(
				resp, senderEmailAddress, recipientEmailAddress, XDS_ENTRY_ID,
				xacmlResultObj);
		documentSegmentation.setDocumentPayloadRawData(resp, xdm,
				senderEmailAddress, recipientEmailAddress, xacmlResultObj);

		// Assert
		validateResponse(resp, ecrypt);
		final String s = null;
		verify(auditServiceMock, times(1)).audit(
				eq((Object) documentSegmentation),
				eq("cf8cace6-6331-4a45-8e79-5bf503925be4"),
				eq((AuditVerb) AcsAuditVerb.SEGMENT_DOCUMENT), eq(s),
				anyMapOf(PredicateKey.class, String.class));
	}

	@SuppressWarnings("unchecked")
	@Test(expected = InvalidSegmentedClinicalDocumentException.class)
	public void testSegmentDocument_Given_XdmFalse_EncryptFalse_Throws_InvalidSegmentedClinicalDocumentException()
			throws Exception {
		// Arrange
		final boolean xdm = false;
		final boolean ecrypt = false;
		final XmlValidation validationMock = mock(XmlValidation.class);
		final XmlValidationResult xmlValidationResultMockFalse = mock(XmlValidationResult.class);
		final XmlValidationResult xmlValidationResultMockTrue = mock(XmlValidationResult.class);
		when(xmlValidationResultMockFalse.isValid()).thenReturn(false);
		when(xmlValidationResultMockTrue.isValid()).thenReturn(true);
		when(validationMock.validateWithAllErrors(testOriginal_C32_xml))
				.thenReturn(xmlValidationResultMockTrue);
		when(validationMock.validateWithAllErrors(testTagged_C32_xml))
				.thenReturn(xmlValidationResultMockFalse);
		ReflectionTestUtils.setField(documentSegmentation, "xmlValidator",
				validationMock);

		// Act
		final SegmentDocumentResponse resp = documentSegmentation
				.segmentDocument(testOriginal_C32_xml, xacmlResult, false,
						true, true);
		documentSegmentation.setAdditionalMetadataForSegmentedClinicalDocument(
				resp, senderEmailAddress, recipientEmailAddress, XDS_ENTRY_ID,
				xacmlResultObj);
		documentSegmentation.setDocumentPayloadRawData(resp, xdm,
				senderEmailAddress, recipientEmailAddress, xacmlResultObj);

		// Assert
		validateResponse(resp, ecrypt);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = XmlDocumentReadFailureException.class)
	public void testSegmentDocument_Given_XdmFalse_EncryptFalse_Throws_XmlDocumentReadFailureException()
			throws Exception {
		// Arrange
		final boolean xdm = false;
		final boolean ecrypt = false;
		final XmlValidation validationMock = mock(XmlValidation.class);
		final XmlValidationResult xmlValidationResultTrue = mock(XmlValidationResult.class);
		when(xmlValidationResultTrue.isValid()).thenReturn(true);
		when(validationMock.validateWithAllErrors(testOriginal_C32_xml))
				.thenReturn(xmlValidationResultTrue);
		when(validationMock.validateWithAllErrors(testTagged_C32_xml))
				.thenThrow(XmlDocumentReadFailureException.class);
		ReflectionTestUtils.setField(documentSegmentation, "xmlValidator",
				validationMock);

		// Act
		final SegmentDocumentResponse resp = documentSegmentation
				.segmentDocument(testOriginal_C32_xml, xacmlResult, false,
						true, true);
		documentSegmentation.setAdditionalMetadataForSegmentedClinicalDocument(
				resp, senderEmailAddress, recipientEmailAddress, XDS_ENTRY_ID,
				xacmlResultObj);
		documentSegmentation.setDocumentPayloadRawData(resp, xdm,
				senderEmailAddress, recipientEmailAddress, xacmlResultObj);

		// Assert
		validateResponse(resp, ecrypt);
	}

	@Test
	public void testSegmentDocument_Given_XdmTrue_EncryptFalse()
			throws Exception {
		// Arrange
		final boolean xdm = true;
		final boolean ecrypt = false;
		ReflectionTestUtils.setField(documentSegmentation, "xmlValidator",
				xmlValidatorMock);

		// Act
		final SegmentDocumentResponse resp = documentSegmentation
				.segmentDocument(testOriginal_C32_xml, xacmlResult, false,
						true, true);
		documentSegmentation.setDocumentPayloadRawData(resp, xdm,
				senderEmailAddress, recipientEmailAddress, xacmlResultObj);
		documentSegmentation.setAdditionalMetadataForSegmentedClinicalDocument(
				resp, senderEmailAddress, recipientEmailAddress, "123",
				xacmlResultObj);

		// Assert
		validateResponse(resp, ecrypt);
	}

	private byte[] entryBytesFromZipBytes(ZipInputStream zip_inputstream,
			String entryName) throws IOException {

		ZipEntry current_zip_entry = null;
		byte[] buf = new byte[4096];
		boolean found = false;
		current_zip_entry = zip_inputstream.getNextEntry();
		while (current_zip_entry != null && !found) {
			if (current_zip_entry.getName().equals(entryName)) {
				found = true;
				final ByteArrayOutputStream output = streamToOutputByteStream(zip_inputstream);
				buf = output.toByteArray();
				output.flush();
				output.close();
			} else {
				current_zip_entry = zip_inputstream.getNextEntry();
			}
		}
		return buf;
	}

	private ByteArrayOutputStream streamToOutputByteStream(
			ZipInputStream zip_inputstream) throws IOException {
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		int data = 0;
		while ((data = zip_inputstream.read()) != -1) {
			output.write(data);
		}
		return output;
	}

	private void validateResponse(SegmentDocumentResponse resp, boolean encrypt)
			throws SAXException, IOException {
		// Assert masked document
		logger.debug(resp.getSegmentedDocumentXml());
		assertEquals(testTagged_C32_xml, resp.getSegmentedDocumentXml());

		// Assert processed document
		logger.debug(resp.getDocumentPayloadRawData().toString());
		assertTrue(resp.getDocumentPayloadRawData().toString()
				.startsWith("javax.activation.DataHandler@"));

		// Assert processing metadata
		final String metadata = resp.getPostSegmentationMetadataXml();
		logger.debug(metadata);
		assertNotNull(metadata);
		assertTrue(metadata
				.contains("<rim:Value>2.16.840.1.113883.1.11.16926</rim:Value>"));
		assertTrue(metadata
				.contains("<rim:Value>^^Internet^leo.smith@direct.obhita-stage.org</rim:Value>"));
		assertTrue(metadata
				.contains("<rim:Value>^^Internet^Duane_Decouteau@direct.healthvault-stage.com</rim:Value>"));
		assertTrue(metadata.contains("nodeRepresentation=\"TREATMENT\">"));
		assertTrue(metadata
				.contains("<rim:LocalizedString value=\"Treatment\"/>"));
		assertTrue(metadata.contains("nodeRepresentation=\"ENCRYPT\">"));
		assertTrue(metadata
				.contains("<rim:LocalizedString value=\"ENCRYPT\"/>"));
		assertTrue(metadata.contains("nodeRepresentation=\"NODSCLCD\">"));
		assertTrue(metadata
				.contains("<rim:LocalizedString value=\"NODSCLCD\"/>"));
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

	private static XacmlResult setXacmlResult() {
		final XacmlResult xacmlResultObject = new XacmlResult();
		xacmlResultObject.setPdpDecision("PERMIT");
		xacmlResultObject
				.setSubjectPurposeOfUse(SubjectPurposeOfUse.HEALTHCARE_TREATMENT);
		xacmlResultObject.setMessageId("cf8cace6-6331-4a45-8e79-5bf503925be4");
		xacmlResultObject.setHomeCommunityId("2.16.840.1.113883.3.467");
		final String[] o = { "51848-0", "121181", "47420-5", "46240-8", "ETH",
				"GDIS", "PSY", "SEX", "18748-4", "11504-8", "34117-2" };
		final List<String> obligations = Arrays.asList(o);
		xacmlResultObject.setPdpObligations(obligations);
		return xacmlResultObject;
	}
}
