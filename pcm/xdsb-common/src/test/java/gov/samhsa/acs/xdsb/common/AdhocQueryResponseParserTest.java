package gov.samhsa.acs.xdsb.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.acs.common.tool.exception.DocumentXmlConverterException;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RunWith(MockitoJUnitRunner.class)
public class AdhocQueryResponseParserTest {

	@Mock
	private SimpleMarshaller marshallerMock;

	@Mock
	private DocumentXmlConverter documentXmlConverterMock;

	@Mock
	private DocumentAccessor documentAccessorMock;

	// Helpers
	private SimpleMarshaller marshaller;
	private DocumentXmlConverter documentXmlConverter;
	private FileReader fileReader;

	@InjectMocks
	private AdhocQueryResponseParser sut;

	@Before
	public void setUp() throws Exception {
		this.marshaller = new SimpleMarshallerImpl();
		this.fileReader = new FileReaderImpl();
		this.documentXmlConverter = new DocumentXmlConverterImpl();
	}

	@Test
	public void testMarshal() throws SimpleMarshallerException {
		// Arrange
		final AdhocQueryResponse adhocQueryResponse = mock(AdhocQueryResponse.class);
		final String adhocQueryResponseXml = "adhocQueryResponseXml";
		when(marshallerMock.marshal(adhocQueryResponse)).thenReturn(
				adhocQueryResponseXml);

		// Act
		final String response = sut.marshal(adhocQueryResponse);

		// Assert
		assertEquals(adhocQueryResponseXml, response);
	}

	@Test
	public void testParseDeprecatedDocumentUniqueId()
			throws SimpleMarshallerException, DocumentAccessorException {
		// Arrange
		final AdhocQueryResponse responseMock = mock(AdhocQueryResponse.class);
		final String responseXmlMock = "responseXmlMock";
		final Document documentMock = mock(Document.class);
		final Node nodeMock = mock(Node.class);
		final Optional<Node> nodeMockOptional = Optional.of(nodeMock);
		final String nodeValueMock = "nodeValueMock";
		when(marshallerMock.marshal(responseMock)).thenReturn(responseXmlMock);
		when(documentXmlConverterMock.loadDocument(responseXmlMock))
				.thenReturn(documentMock);
		when(documentAccessorMock.getNode(eq(documentMock), anyString()))
				.thenReturn(nodeMockOptional);
		when(nodeMock.getNodeValue()).thenReturn(nodeValueMock);

		// Act
		final String deprecatedDocumentUniqueId = sut
				.parseDeprecatedDocumentUniqueId(responseMock).get();

		// Assert
		assertEquals(nodeValueMock, deprecatedDocumentUniqueId);
	}

	@Test
	public void testParseSubmissionSetUniqueIds()
			throws DocumentAccessorException, SimpleMarshallerException {
		// Arrange
		final AdhocQueryResponse responseMock = mock(AdhocQueryResponse.class);
		final String responseXmlMock = "responseXmlMock";
		final Document documentMock = mock(Document.class);
		final NodeList nodeListMock = mock(NodeList.class);
		final Node nodeMock = mock(Node.class);
		final String nodeValueMock = "nodeValueMock";
		when(marshallerMock.marshal(responseMock)).thenReturn(responseXmlMock);
		when(documentXmlConverterMock.loadDocument(responseXmlMock))
		.thenReturn(documentMock);
		when(documentAccessorMock.getNodeList(eq(documentMock), anyString()))
		.thenReturn(nodeListMock);
		when(nodeListMock.getLength()).thenReturn(1);
		when(nodeListMock.item(0)).thenReturn(nodeMock);
		when(nodeMock.getNodeValue()).thenReturn(nodeValueMock);

		// Act
		final List<String> submissionSetUniqueIds = sut
				.parseSubmissionSetUniqueIds(responseMock);

		// Assert
		assertTrue(submissionSetUniqueIds.contains(nodeValueMock));
	}

	@Test
	public void testParseXdsbDocumentReferenceList_Clinical_Document()
			throws Exception, Throwable {
		// Arrange
		final XdsbDocumentReference xdsbDocumentReference1 = new XdsbDocumentReference(
				"41421263015.98411.41414.91230.401390172014139",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		final XdsbDocumentReference xdsbDocumentReference2 = new XdsbDocumentReference(
				"1513150391310.11184.4632.11139.05080551281557",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		final String adhocQueryResponseString = fileReader
				.readFile("adhocQueryResponseClinicalDocument.xml");
		final AdhocQueryResponse adhocQueryResponse = marshaller
				.unmarshalFromXml(AdhocQueryResponse.class,
						adhocQueryResponseString);
		when(marshallerMock.marshal(adhocQueryResponse)).thenReturn(
				adhocQueryResponseString);
		final Document doc = documentXmlConverter
				.loadDocument(adhocQueryResponseString);
		when(documentXmlConverterMock.loadDocument(adhocQueryResponseString))
				.thenReturn(doc);

		// Act
		final List<XdsbDocumentReference> list = sut
				.parseXdsbDocumentReferenceList(adhocQueryResponse);

		// Assert
		assertEquals(2, list.size());
		assertTrue(list.contains(xdsbDocumentReference1));
		assertTrue(list.contains(xdsbDocumentReference2));
	}

	@Test
	public void testParseXdsbDocumentReferenceList_Privacy_Consent()
			throws Exception, Throwable {
		// Arrange
		final XdsbDocumentReference xdsbDocumentReference1 = new XdsbDocumentReference(
				"2931513224.111050.43108.1114145.628015389614413",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		final XdsbDocumentReference xdsbDocumentReference2 = new XdsbDocumentReference(
				"12132124715.97915.410413.87115.61142312711102135",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		final String adhocQueryResponseString = fileReader
				.readFile("adhocQueryResponsePrivacyConsent.xml");
		final AdhocQueryResponse adhocQueryResponse = marshaller
				.unmarshalFromXml(AdhocQueryResponse.class,
						adhocQueryResponseString);
		when(marshallerMock.marshal(adhocQueryResponse)).thenReturn(
				adhocQueryResponseString);
		final Document doc = documentXmlConverter
				.loadDocument(adhocQueryResponseString);
		when(documentXmlConverterMock.loadDocument(adhocQueryResponseString))
				.thenReturn(doc);

		// Act
		final List<XdsbDocumentReference> list = sut
				.parseXdsbDocumentReferenceList(adhocQueryResponse);

		// Assert
		assertEquals(2, list.size());
		assertTrue(list.contains(xdsbDocumentReference1));
		assertTrue(list.contains(xdsbDocumentReference2));
	}

	@Test
	public void testParseXdsbDocumentReferenceListAsRetrieveDocumentSetRequest()
			throws SimpleMarshallerException, DocumentXmlConverterException,
			IOException {
		// Arrange
		final AdhocQueryResponse responseMock = mock(AdhocQueryResponse.class);
		final String marshalledXmlStringMock = "marshalledXmlStringMock";
		final XdsbDocumentReference ref1 = new XdsbDocumentReference(
				"41421263015.98411.41414.91230.401390172014139",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		final XdsbDocumentReference ref2 = new XdsbDocumentReference(
				"1513150391310.11184.4632.11139.05080551281557",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		when(marshallerMock.marshal(responseMock)).thenReturn(
				marshalledXmlStringMock);
		when(documentXmlConverterMock.loadDocument(marshalledXmlStringMock))
				.thenReturn(
						documentXmlConverter.loadDocument(fileReader
								.readFile("adhocQueryResponseClinicalDocument.xml")));

		// Act
		final RetrieveDocumentSetRequest actualResponse = sut
				.parseXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(responseMock);

		// Assert
		assertTrue(actualResponse.getDocumentRequest().contains(ref1));
		assertTrue(actualResponse.getDocumentRequest().contains(ref2));
	}

}
