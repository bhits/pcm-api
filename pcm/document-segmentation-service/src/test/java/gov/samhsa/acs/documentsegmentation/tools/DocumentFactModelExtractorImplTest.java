package gov.samhsa.acs.documentsegmentation.tools;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertTrue;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;
import gov.samhsa.consent2share.commonunit.xml.XmlComparator;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DocumentFactModelExtractorImplTest {

	private static FileReaderImpl fileReader;

	private static String xacmlResult;

	private static final String SAMPLE_C32_LOCATION = "sampleC32/";
	private static final String SAMPLE_OUTPUT_LOCATION = "sampleC32/output/";

	private static DocumentFactModelExtractorImpl documentFactModelExtractor;
	private static XmlTransformer xmlTransformer;

	@Test
	public void testExtractFactModel() throws IOException, SAXException {
		// Arrange
		final String c32FileName = "c32.xml";
		final List<String> ignoreList = new LinkedList<String>();
		ignoreList.add("EmbeddedClinicalDocument");

		// Act & Assert
		testFactModelExtraction(c32FileName, ignoreList);
	}

	@Test
	public void testExtractFactModel_CCD_HITSP_C32_ALL_TemplateIdsAtRoot_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_HITSP_C32_ALL_TemplateIdsAtRoot.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_CCD_HITSP_C32_HistoryAndMedications_WithAllR2Elements_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_HITSP_C32_HistoryAndMedications_WithAllR2Elements.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_CCD_HITSP_C32_Medications_Template_Robust_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_HITSP_C32_Medications_Template_Robust.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_CCD_HITSP_C32_Medications_Template_Small_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_HITSP_C32_Medications_Template_Small.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_CCD_HITSP_C32_Minimal_NoSections_Valid_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_HITSP_C32_Minimal_NoSections_Valid.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_CCD_HITSP_C32_Minimal_WithEntries_Valid_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_HITSP_C32_Minimal_WithEntries_Valid.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_CCD_HITSP_C32_Minimal_WithSections_Valid_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_HITSP_C32_Minimal_WithSections_Valid.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_CCD_HITSP_C32_v2_1_Examples_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_HITSP_C32_v2.1_Examples.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_CCD_HITSP_C32_Valid_WithViolations_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_HITSP_C32_Valid_WithViolations.xml";
		final List<String> ignoreList = new LinkedList<String>();
		ignoreList.add("EmbeddedClinicalDocument");

		// Act & Assert
		testFactModelExtraction(c32FileName, ignoreList);
	}

	@Test
	public void testExtractFactModel_CCD_Minimal_No_C32_templateIds_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "CCD_Minimal_No_C32_templateIds.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_HITSP_C32v2_5_Rev0_CDAR2_Requirements_Only_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "HITSP_C32v2.5_Rev0_CDAR2_Requirements_Only.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_HITSP_C32v2_5_Rev1_BaseC32_RequiredTemplateIds_NoErrors_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "HITSP_C32v2.5_Rev1_BaseC32_RequiredTemplateIds_NoErrors.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_HITSP_C32v2_5_Rev2_BaseC32_RequiredContent_NoWarnings_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "HITSP_C32v2.5_Rev2_BaseC32_RequiredContent_NoWarnings.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_HITSP_C32v2_5_Rev3_11Sections_NoEntries_EntryErrorsOnly_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "HITSP_C32v2.5_Rev3_11Sections_NoEntries_EntryErrorsOnly.xml";

		// Act & Assert
		testFactModelExtraction(c32FileName);
	}

	@Test
	public void testExtractFactModel_HITSP_C32v2_5_Rev4_11Sections_Entries_MinimalErrors_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "HITSP_C32v2.5_Rev4_11Sections_Entries_MinimalErrors.xml";
		final List<String> ignoreList = new LinkedList<String>();
		ignoreList.add("EmbeddedClinicalDocument");

		// Act & Assert
		testFactModelExtraction(c32FileName, ignoreList);
	}

	@Test
	public void testExtractFactModel_HITSP_C32v2_5_Rev5_11Sections_Entries_MinimalWarnings_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "HITSP_C32v2.5_Rev5_11Sections_Entries_MinimalWarnings.xml";
		final List<String> ignoreList = new LinkedList<String>();
		ignoreList.add("EmbeddedClinicalDocument");

		// Act & Assert
		testFactModelExtraction(c32FileName, ignoreList);
	}

	@Test
	public void testExtractFactModel_HITSP_C32v2_5_Rev6_16Sections_Entries_MinimalErrors_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "HITSP_C32v2.5_Rev6_16Sections_Entries_MinimalErrors.xml";
		final List<String> ignoreList = new LinkedList<String>();
		ignoreList.add("EmbeddedClinicalDocument");

		// Act & Assert
		testFactModelExtraction(c32FileName, ignoreList);
	}

	@Test
	public void testExtractFactModel_JohnHalamkaCCDDocument_C32_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "JohnHalamkaCCDDocument_C32.xml";
		final List<String> ignoreList = new LinkedList<String>();
		ignoreList.add("EmbeddedClinicalDocument");

		// Act & Assert
		testFactModelExtraction(c32FileName, ignoreList);
	}

	@Test
	public void testExtractFactModel_JohnHalamkaCCDDocument_CCDonly_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "JohnHalamkaCCDDocument_CCDonly.xml";
		final List<String> ignoreList = new LinkedList<String>();
		ignoreList.add("EmbeddedClinicalDocument");

		// Act & Assert
		testFactModelExtraction(c32FileName, ignoreList);
	}

	@Test
	public void testExtractFactModel_SegmentedDocumentContentProfileSample_Xml()
			throws IOException, SAXException {
		// Arrange
		final String c32FileName = "SegmentedDocumentContentProfileSample.xml";
		final List<String> ignoreList = new LinkedList<String>();
		ignoreList.add("EmbeddedClinicalDocument");

		// Act & Assert
		testFactModelExtraction(c32FileName, ignoreList);
	}

	@Test(expected = DS4PException.class)
	public void testExtractFactModel_Throws_DS4PException() {
		// Empty xml file
		@SuppressWarnings("unused")
		final String factModel = documentFactModelExtractor.extractFactModel(
				"", xacmlResult);
	}

	private void testFactModelExtraction(String c32FileName)
			throws IOException, SAXException {
		final String cd = fileReader
				.readFile(SAMPLE_C32_LOCATION + c32FileName);
		final String expectedFactModel = fileReader
				.readFile(SAMPLE_OUTPUT_LOCATION + c32FileName);

		// Act
		final String factModel = documentFactModelExtractor.extractFactModel(
				cd, xacmlResult);

		// Assert
		assertXMLEqual("", expectedFactModel, factModel);
	}

	private void testFactModelExtraction(String c32FileName,
			List<String> ignoreList) throws IOException, SAXException {
		final String cd = fileReader
				.readFile(SAMPLE_C32_LOCATION + c32FileName);
		final String expectedFactModel = fileReader
				.readFile(SAMPLE_OUTPUT_LOCATION + c32FileName);

		// Act
		final String factModel = documentFactModelExtractor.extractFactModel(
				cd, xacmlResult);

		// Assert
		assertTrue(XmlComparator.compareXMLs(expectedFactModel, factModel,
				ignoreList).similar());
	}

	@BeforeClass
	public static void setUp() throws Exception {
		// Arrange
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreComments(true);
		fileReader = new FileReaderImpl();
		xacmlResult = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREATMENT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>";
		xmlTransformer = new XmlTransformerImpl(new SimpleMarshallerImpl());
		documentFactModelExtractor = new DocumentFactModelExtractorImpl(
				xmlTransformer);
	}
}
