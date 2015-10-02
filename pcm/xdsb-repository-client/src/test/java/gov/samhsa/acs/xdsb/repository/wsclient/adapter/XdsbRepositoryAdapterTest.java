package gov.samhsa.acs.xdsb.repository.wsclient.adapter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorImpl;
import gov.samhsa.acs.xdsb.repository.wsclient.XDSRepositorybWebServiceClient;
import gov.samhsa.acs.xdsb.repository.wsclient.exception.XdsbRepositoryAdapterException;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest.Document;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.util.ArrayList;
import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class XdsbRepositoryAdapterTest {
	// Constants
	private static final String DOCUMENT_XML_STRING = "DOCUMENT_XML_STRING";
	private static final String HOME_COMMUNITY_ID = "HOME_COMMUNITY_ID";
	private static final String SUBMIT_OBJECTS_REQUEST_STRING = "SUBMIT_OBJECTS_REQUEST_STRING";

	// System under test
	private static XdsbRepositoryAdapter xdsbRepositoryAdapterSpy;
	// Mocks
	@Mock
	private XDSRepositorybWebServiceClient xdsbRepositoryMock;
	@Mock
	private XdsbMetadataGeneratorImpl xdsbMetadataGeneratorMock;
	@Mock
	private SimpleMarshaller marshallerMock;
	@Mock
	private RetrieveDocumentSetResponseFilter responseFilterMock;

	@Mock
	private XmlTransformer xmlTransformerMock;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		final XdsbRepositoryAdapter xdsbRepositoryAdapter = new XdsbRepositoryAdapter(
				xdsbRepositoryMock, marshallerMock, responseFilterMock,
				xmlTransformerMock);
		xdsbRepositoryAdapterSpy = spy(xdsbRepositoryAdapter);
	}

	@Test
	public void testcreateRetrieveDocumentSetRequest() {

		final DocumentRequest documentRequest = mock(DocumentRequest.class);
		final RetrieveDocumentSetRequest requestExpected = new RetrieveDocumentSetRequest();
		requestExpected.getDocumentRequest().add(documentRequest);

		final RetrieveDocumentSetRequest requestActual = xdsbRepositoryAdapterSpy
				.createRetrieveDocumentSetRequest(documentRequest);

		assertEquals(requestExpected.getDocumentRequest(),
				requestActual.getDocumentRequest());

	}

	@Test
	public void testCreateRetrieveDocumentSetRequest() {

		final List<DocumentRequest> docRequest = new ArrayList();
		final DocumentRequest documentRequest = mock(DocumentRequest.class);
		final DocumentRequest documentRequest2 = mock(DocumentRequest.class);

		docRequest.add(documentRequest);
		docRequest.add(documentRequest2);

		final RetrieveDocumentSetRequest requestExpected = new RetrieveDocumentSetRequest();
		requestExpected.getDocumentRequest().addAll(docRequest);

		final RetrieveDocumentSetRequest requestActual = xdsbRepositoryAdapterSpy
				.createRetrieveDocumentSetRequest(docRequest);

		assertEquals(requestExpected.getDocumentRequest(),
				requestActual.getDocumentRequest());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testProvideAndRegisterDocumentSet()
			throws XdsbRepositoryAdapterException, SimpleMarshallerException {

		xdsbRepositoryAdapterSpy.provideAndRegisterDocumentSet(
				DOCUMENT_XML_STRING, HOME_COMMUNITY_ID,
				XdsbDocumentType.DEPRECATE_PRIVACY_CONSENT, "1", null);

	}

	@Test
	public void testProvideAndRegisterDocumentSetRequest_Given_DocumentXml_HomeCommunityId()
			throws Throwable {
		// Arrange
		when(
				xdsbMetadataGeneratorMock.generateMetadataXml(
						DOCUMENT_XML_STRING, HOME_COMMUNITY_ID, null, null))
				.thenReturn(SUBMIT_OBJECTS_REQUEST_STRING);

		final SubmitObjectsRequest submitObjectRequest = new SubmitObjectsRequest();
		when(
				marshallerMock.unmarshalFromXml(SubmitObjectsRequest.class,
						SUBMIT_OBJECTS_REQUEST_STRING)).thenReturn(
				submitObjectRequest);

		final Document document = new Document();
		when(xdsbRepositoryAdapterSpy.createDocument(DOCUMENT_XML_STRING))
				.thenReturn(document);

		final ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		when(
				xdsbRepositoryAdapterSpy
						.createProvideAndRegisterDocumentSetRequest(
								submitObjectRequest, document)).thenReturn(
				request);

		final RegistryResponse response = new RegistryResponse();
		when(xdsbRepositoryMock.provideAndRegisterDocumentSet(request))
				.thenReturn(response);

		when(
				xdsbRepositoryAdapterSpy
						.createXdsbMetadataGenerator(isA(XdsbDocumentType.class)))
				.thenReturn(xdsbMetadataGeneratorMock);

		// Act
		final RegistryResponse actualResponse = xdsbRepositoryAdapterSpy
				.provideAndRegisterDocumentSet(DOCUMENT_XML_STRING,
						HOME_COMMUNITY_ID, XdsbDocumentType.CLINICAL_DOCUMENT,
						null, null);

		// Assert
		assertEquals(response, actualResponse);
	}

	@Test(expected = XdsbRepositoryAdapterException.class)
	public void testProvideAndRegisterDocumentSetRequest_Given_DocumentXml_HomeCommunityId_throw_exception()
			throws Throwable {
		// Arrange
		when(
				xdsbMetadataGeneratorMock.generateMetadataXml(
						DOCUMENT_XML_STRING, HOME_COMMUNITY_ID, null, null))
				.thenReturn(SUBMIT_OBJECTS_REQUEST_STRING);

		final SubmitObjectsRequest submitObjectRequest = new SubmitObjectsRequest();
		when(
				marshallerMock.unmarshalFromXml(SubmitObjectsRequest.class,
						SUBMIT_OBJECTS_REQUEST_STRING)).thenThrow(
				SimpleMarshallerException.class);

		final Document document = new Document();
		when(xdsbRepositoryAdapterSpy.createDocument(DOCUMENT_XML_STRING))
				.thenReturn(document);

		final ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		when(
				xdsbRepositoryAdapterSpy
						.createProvideAndRegisterDocumentSetRequest(
								submitObjectRequest, document)).thenReturn(
				request);

		final RegistryResponse response = new RegistryResponse();
		when(xdsbRepositoryMock.provideAndRegisterDocumentSet(request))
				.thenReturn(response);

		when(
				xdsbRepositoryAdapterSpy
						.createXdsbMetadataGenerator(isA(XdsbDocumentType.class)))
				.thenReturn(xdsbMetadataGeneratorMock);

		// Act
		final RegistryResponse actualResponse = xdsbRepositoryAdapterSpy
				.provideAndRegisterDocumentSet(DOCUMENT_XML_STRING,
						HOME_COMMUNITY_ID, XdsbDocumentType.CLINICAL_DOCUMENT,
						null, null);

		// Assert
		assertEquals(response, actualResponse);
	}

	@Test
	public void testProvideAndRegisterDocumentSetRequest_Given_DocumentXml_HomeCommunityId2()
			throws Throwable {
		// Arrange
		when(
				xdsbMetadataGeneratorMock.generateMetadataXml(
						DOCUMENT_XML_STRING, HOME_COMMUNITY_ID, null, null))
				.thenReturn(SUBMIT_OBJECTS_REQUEST_STRING);

		final SubmitObjectsRequest submitObjectRequest = new SubmitObjectsRequest();
		when(
				marshallerMock.unmarshalFromXml(SubmitObjectsRequest.class,
						SUBMIT_OBJECTS_REQUEST_STRING)).thenReturn(
				submitObjectRequest);

		final Document document = new Document();
		when(xdsbRepositoryAdapterSpy.createDocument(DOCUMENT_XML_STRING))
				.thenReturn(document);

		final ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		when(
				xdsbRepositoryAdapterSpy
						.createProvideAndRegisterDocumentSetRequest(
								submitObjectRequest, document)).thenReturn(
				request);

		final RegistryResponse response = new RegistryResponse();
		when(xdsbRepositoryMock.provideAndRegisterDocumentSet(request))
				.thenReturn(response);

		when(
				xdsbRepositoryAdapterSpy
						.createXdsbMetadataGenerator(isA(XdsbDocumentType.class)))
				.thenReturn(xdsbMetadataGeneratorMock);

		// Act
		final RegistryResponse actualResponse = xdsbRepositoryAdapterSpy
				.provideAndRegisterDocumentSet("<empty/>", HOME_COMMUNITY_ID,
						XdsbDocumentType.CLINICAL_DOCUMENT, null, null);

		// Assert
		assertEquals(null, actualResponse);
	}

	@Test
	public void testProvideAndRegisterDocumentSetRequest_Given_ProvideAndRegisterDocumentSetRequest() {
		// Arrange
		final ProvideAndRegisterDocumentSetRequest provideAndRegisterDocumentSetRequest = new ProvideAndRegisterDocumentSetRequest();
		final RegistryResponse registryResponse = new RegistryResponse();
		when(
				xdsbRepositoryMock
						.provideAndRegisterDocumentSet(provideAndRegisterDocumentSetRequest))
				.thenReturn(registryResponse);

		// Act
		final RegistryResponse actualRegistryResponse = xdsbRepositoryAdapterSpy
				.provideAndRegisterDocumentSet(provideAndRegisterDocumentSetRequest);

		// Assert
		assertEquals(registryResponse, actualRegistryResponse);
	}

	@Test
	public void testretrieveDocumentSet() throws XdsbRepositoryAdapterException {

		final RetrieveDocumentSetRequest request = mock(RetrieveDocumentSetRequest.class);
		final RetrieveDocumentSetResponse response = new RetrieveDocumentSetResponse();

		when(xdsbRepositoryMock.retrieveDocumentSet(request)).thenReturn(
				response);
		when(responseFilterMock.filterByPatientAndAuthor(response, "1", "123"))
				.thenReturn(response);

		final RetrieveDocumentSetResponse responseActual = xdsbRepositoryAdapterSpy
				.retrieveDocumentSet(request, "1", "123");

		assertEquals(response, responseActual);

	}

	@Test
	public void testRetrieveDocumentSet() {

		final DocumentRequest documentRequest = mock(DocumentRequest.class);
		final RetrieveDocumentSetRequest requestExpected = new RetrieveDocumentSetRequest();
		requestExpected.getDocumentRequest().add(documentRequest);
		final RetrieveDocumentSetResponse retrieveDocumentSetResponseExpected = xdsbRepositoryMock
				.retrieveDocumentSet(requestExpected);

		final RetrieveDocumentSetResponse retrieveDocumentSetResponseActual = xdsbRepositoryAdapterSpy
				.retrieveDocumentSet(documentRequest);

		assertEquals(retrieveDocumentSetResponseExpected,
				retrieveDocumentSetResponseActual);

	}

	@Test
	public void testRetrieveDocumentSet_set() {

		final List<DocumentRequest> docRequest = new ArrayList();
		final DocumentRequest documentRequest = mock(DocumentRequest.class);
		final DocumentRequest documentRequest2 = mock(DocumentRequest.class);

		docRequest.add(documentRequest);
		docRequest.add(documentRequest2);

		final RetrieveDocumentSetRequest requestExpected = new RetrieveDocumentSetRequest();
		requestExpected.getDocumentRequest().addAll(docRequest);

		final RetrieveDocumentSetResponse retrieveDocumentSetResponseExpected = xdsbRepositoryMock
				.retrieveDocumentSet(requestExpected);

		final RetrieveDocumentSetResponse retrieveDocumentSetResponseActual = xdsbRepositoryAdapterSpy
				.retrieveDocumentSet(docRequest);

		assertEquals(retrieveDocumentSetResponseExpected,
				retrieveDocumentSetResponseActual);

	}

	@Test
	public void testRetrieveDocumentSetRequest_Given_DocumentUniqueId_RepositoryId() {
		// Arrange
		final RetrieveDocumentSetRequest retrieveDocumentSetRequest = new RetrieveDocumentSetRequest();
		when(
				xdsbRepositoryAdapterSpy.createRetrieveDocumentSetRequest(
						DOCUMENT_XML_STRING, HOME_COMMUNITY_ID)).thenReturn(
				retrieveDocumentSetRequest);
		final RetrieveDocumentSetResponse retrieveDocumentSetResponse = new RetrieveDocumentSetResponse();
		when(xdsbRepositoryMock.retrieveDocumentSet(retrieveDocumentSetRequest))
				.thenReturn(retrieveDocumentSetResponse);

		// Act
		final RetrieveDocumentSetResponse actualResponse = xdsbRepositoryAdapterSpy
				.retrieveDocumentSet(DOCUMENT_XML_STRING, HOME_COMMUNITY_ID);

		// Assert
		assertEquals(retrieveDocumentSetResponse, actualResponse);
	}

	@Test
	public void testRetrieveDocumentSetRequest_Given_RetrieveDocumentSetRequest() {
		// Arrange
		final RetrieveDocumentSetRequest retrieveDocumentSetRequest = new RetrieveDocumentSetRequest();
		final RetrieveDocumentSetResponse retrieveDocumentSetResponse = new RetrieveDocumentSetResponse();
		when(xdsbRepositoryMock.retrieveDocumentSet(retrieveDocumentSetRequest))
				.thenReturn(retrieveDocumentSetResponse);

		// Act
		final RetrieveDocumentSetResponse actualResponse = xdsbRepositoryAdapterSpy
				.retrieveDocumentSet(retrieveDocumentSetRequest);

		// Assert
		assertEquals(retrieveDocumentSetResponse, actualResponse);
	}

}
