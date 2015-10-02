package gov.samhsa.acs.documentsegmentation.tools.redact.impl.postredactionlevel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.documentsegmentation.tools.EmbeddedClinicalDocumentExtractor;
import gov.samhsa.acs.documentsegmentation.tools.EmbeddedClinicalDocumentExtractorImpl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import javax.xml.xpath.XPathExpressionException;

import net.sf.saxon.dom.DOMNodeList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RunWith(MockitoJUnitRunner.class)
public class DocumentCleanupForNoEntryAndNoSectionTest {

	public static final String TEST_PATH = "sampleC32-redactionHandlers/";
	public static final String FACTMODEL_PATH = "factmodel/";
	public static final String RULEEXECUTIONCONTAINER_PATH = "ruleexecutioncontainer/";

	private FileReader fileReader;
	private SimpleMarshaller marshaller;
	private DocumentAccessor documentAccessor;
	private DocumentXmlConverter documentXmlConverter;
	private EmbeddedClinicalDocumentExtractor embeddedClinicalDocumentExtractor;

	private DocumentCleanupForNoEntryAndNoSection sut;

	@Before
	public void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		marshaller = new SimpleMarshallerImpl();
		documentAccessor = new DocumentAccessorImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		embeddedClinicalDocumentExtractor = new EmbeddedClinicalDocumentExtractorImpl(
				documentXmlConverter, documentAccessor);
		sut = new DocumentCleanupForNoEntryAndNoSection(documentAccessor);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute_AddEmptySectionComponentIfNoneExists()
			throws IOException, SimpleMarshallerException,
			XPathExpressionException {
		// Arrange
		final String c32FileName = "MIE_SampleC32-sectionWithNoEntriesAndNoOtherSections.xml";
		final String factmodelXml = fileReader.readFile(TEST_PATH
				+ FACTMODEL_PATH + c32FileName);
		final String c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factmodelXml);
		final String ruleExecutionContainerXml = fileReader.readFile(TEST_PATH
				+ RULEEXECUTIONCONTAINER_PATH + c32FileName);
		final RuleExecutionContainer ruleExecutionContainer = marshaller
				.unmarshalFromXml(RuleExecutionContainer.class,
						ruleExecutionContainerXml);
		final Document c32Document = documentXmlConverter.loadDocument(c32);
		final Document factModelDocument = documentXmlConverter
				.loadDocument(factmodelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factmodelXml);
		final List<Node> listOfNodes = new LinkedList<Node>();
		final Set<String> redactSectionCodesAndGeneratedEntryIds = new HashSet<String>();

		// Act
		sut.execute(c32Document, factModel.getXacmlResult(), factModel,
				factModelDocument, ruleExecutionContainer, listOfNodes,
				redactSectionCodesAndGeneratedEntryIds);

		// Assert
		assertEquals(1,
				documentAccessor.getNodeList(c32Document, "//hl7:section")
						.getLength());
		assertEquals(0,
				documentAccessor.getNodeList(c32Document, "//hl7:section")
						.item(0).getChildNodes().getLength());
		assertEquals(
				0,
				documentAccessor.getNodeList(c32Document,
						"//hl7:section[hl7:code[@code='11450-4']]").getLength());
		assertEquals(
				0,
				documentAccessor.getNodeList(c32Document,
						"//hl7:section[hl7:code[@code='30954-2']]").getLength());
	}

	@Test
	public void testExecute_CleanUpSectionComponentsWithNoEntries()
			throws IOException, SimpleMarshallerException,
			XPathExpressionException {
		// Arrange
		final String c32FileName = "MIE_SampleC32-sectionWithNoEntries.xml";
		final String factmodelXml = fileReader.readFile(TEST_PATH
				+ FACTMODEL_PATH + c32FileName);
		final String c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factmodelXml);
		final String ruleExecutionContainerXml = fileReader.readFile(TEST_PATH
				+ RULEEXECUTIONCONTAINER_PATH + c32FileName);
		final RuleExecutionContainer ruleExecutionContainer = marshaller
				.unmarshalFromXml(RuleExecutionContainer.class,
						ruleExecutionContainerXml);
		final Document c32Document = documentXmlConverter.loadDocument(c32);
		final Document factModelDocument = documentXmlConverter
				.loadDocument(factmodelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factmodelXml);
		final List<Node> listOfNodes = new LinkedList<Node>();
		final Set<String> redactSectionCodesAndGeneratedEntryIds = new HashSet<String>();

		// Act
		sut.execute(c32Document, factModel.getXacmlResult(), factModel,
				factModelDocument, ruleExecutionContainer, listOfNodes,
				redactSectionCodesAndGeneratedEntryIds);

		// Assert
		assertEquals(1,
				documentAccessor.getNodeList(c32Document, "//hl7:section")
						.getLength());
		assertEquals(
				0,
				documentAccessor.getNodeList(c32Document,
						"//hl7:section[hl7:code[@code='11450-4']]").getLength());
		assertEquals(
				1,
				documentAccessor.getNodeList(c32Document,
						"//hl7:section[hl7:code[@code='30954-2']]").getLength());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExecute_CleanUpSectionComponentsWithNoEntries_EmptySectionComponents_ParentNull()
			throws IOException, SimpleMarshallerException,
			XPathExpressionException {
		// Arrange
		final AcsLogger loggerMock = mock(AcsLogger.class);
		final DocumentAccessor documentAccessorMock = mock(DocumentAccessor.class);
		sut = new DocumentCleanupForNoEntryAndNoSection(documentAccessorMock);
		ReflectionTestUtils.setField(sut, "logger", loggerMock);
		final String c32FileName = "MIE_SampleC32-sectionWithNoEntries.xml";
		final String factmodelXml = fileReader.readFile(TEST_PATH
				+ FACTMODEL_PATH + c32FileName);
		final String c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factmodelXml);
		final String ruleExecutionContainerXml = fileReader.readFile(TEST_PATH
				+ RULEEXECUTIONCONTAINER_PATH + c32FileName);
		final RuleExecutionContainer ruleExecutionContainer = marshaller
				.unmarshalFromXml(RuleExecutionContainer.class,
						ruleExecutionContainerXml);
		final Document c32Document = documentXmlConverter.loadDocument(c32);
		final Document factModelDocument = documentXmlConverter
				.loadDocument(factmodelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factmodelXml);
		final List<Node> listOfNodes = new LinkedList<Node>();
		final Set<String> redactSectionCodesAndGeneratedEntryIds = new HashSet<String>();
		final NodeList nodeListMock = mock(NodeList.class);
		when(nodeListMock.getLength()).thenReturn(1);
		final Node nodeMock = mock(Node.class);
		when(nodeListMock.item(0)).thenReturn(nodeMock);
		when(nodeMock.getParentNode()).thenReturn(null);
		when(documentAccessorMock.getNodeList(eq(c32Document), anyString()))
				.thenReturn(nodeListMock);
		when(documentAccessorMock.getNode(eq(c32Document), anyString()))
				.thenReturn(Optional.empty());

		// Act
		sut.execute(c32Document, factModel.getXacmlResult(), factModel,
				factModelDocument, ruleExecutionContainer, listOfNodes,
				redactSectionCodesAndGeneratedEntryIds);

		// Assert
		assertEquals(2,
				documentAccessor.getNodeList(c32Document, "//hl7:section")
						.getLength());
		assertEquals(
				1,
				documentAccessor.getNodeList(c32Document,
						"//hl7:section[hl7:code[@code='11450-4']]").getLength());
		assertEquals(
				1,
				documentAccessor.getNodeList(c32Document,
						"//hl7:section[hl7:code[@code='30954-2']]").getLength());
		verify(loggerMock, times(1)).info(any(Supplier.class),
				any(Throwable.class));
	}

	@Test
	public void testExecute_CleanUpSectionComponentsWithNoEntries_EmptySectionComponentsNull()
			throws IOException, SimpleMarshallerException,
			XPathExpressionException {
		// Arrange
		final DocumentAccessor documentAccessorMock = mock(DocumentAccessor.class);
		sut = new DocumentCleanupForNoEntryAndNoSection(documentAccessorMock);
		final String c32FileName = "MIE_SampleC32-sectionWithNoEntries.xml";
		final String factmodelXml = fileReader.readFile(TEST_PATH
				+ FACTMODEL_PATH + c32FileName);
		final String c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factmodelXml);
		final String ruleExecutionContainerXml = fileReader.readFile(TEST_PATH
				+ RULEEXECUTIONCONTAINER_PATH + c32FileName);
		final RuleExecutionContainer ruleExecutionContainer = marshaller
				.unmarshalFromXml(RuleExecutionContainer.class,
						ruleExecutionContainerXml);
		final Document c32Document = documentXmlConverter.loadDocument(c32);
		final Document factModelDocument = documentXmlConverter
				.loadDocument(factmodelXml);
		final FactModel factModel = marshaller.unmarshalFromXml(
				FactModel.class, factmodelXml);
		final List<Node> listOfNodes = new LinkedList<Node>();
		final Set<String> redactSectionCodesAndGeneratedEntryIds = new HashSet<String>();
		when(documentAccessorMock.getNodeList(eq(c32Document), anyString()))
				.thenReturn(new DOMNodeList(Collections.emptyList()));
		when(documentAccessorMock.getNode(eq(c32Document), anyString()))
		.thenReturn(Optional.empty());

		// Act
		sut.execute(c32Document, factModel.getXacmlResult(), factModel,
				factModelDocument, ruleExecutionContainer, listOfNodes,
				redactSectionCodesAndGeneratedEntryIds);

		// Assert
		assertEquals(2,
				documentAccessor.getNodeList(c32Document, "//hl7:section")
						.getLength());
		assertEquals(
				1,
				documentAccessor.getNodeList(c32Document,
						"//hl7:section[hl7:code[@code='11450-4']]").getLength());
		assertEquals(
				1,
				documentAccessor.getNodeList(c32Document,
						"//hl7:section[hl7:code[@code='30954-2']]").getLength());
	}
}
