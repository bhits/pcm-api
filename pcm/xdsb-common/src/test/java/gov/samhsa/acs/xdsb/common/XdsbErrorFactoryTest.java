package gov.samhsa.acs.xdsb.common;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.io.IOException;
import java.util.HashMap;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XdsbErrorFactoryTest {

	private SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
	private FileReaderImpl fileReader = new FileReaderImpl();

	private XdsbErrorFactory sut;

	@Before
	public void setUp() throws Exception {
		sut = new XdsbErrorFactory(new RegistryErrorListSetterImpl());
	}
	
	@Test
	public void testErrorAdhocQueryResponseConstructByErrorMessage() throws SAXException, IOException, Throwable{
		// Arrange
		String expectedResponse = fileReader.readFile("errorAdhocQueryResponseConstructByErrorMessage.xml");
		String errorMessageMock = "errorMessageMock";
		
		// Act
		AdhocQueryResponse actualResponse =sut.errorAdhocQueryResponseConstructByErrorMessage(errorMessageMock);
		
		// Assert
		assertResponse(expectedResponse, actualResponse);
	}	
	
	@Test
	public void testErrorRetrieveDocumentSetResponseNoConsentsFound() throws SAXException, Throwable{
		// Arrange
		String expectedResponse = fileReader.readFile("errorRetrieveDocumentSetResponseNoConsentsFound.xml");
		String patientUniqueIdMock = "patientUniqueIdMock";
		
		// Act
		RetrieveDocumentSetResponse actualResponse =sut.errorRetrieveDocumentSetResponseNoConsentsFound(patientUniqueIdMock);
		
		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorRetrieveDocumentSetResponseSchemaValidation_Partial_Success() {
		// Arrange
		RetrieveDocumentSetResponse response = new RetrieveDocumentSetResponse();
		DocumentResponse document = new DocumentResponse();
		document.setDocumentUniqueId("1");
		response.getDocumentResponse().add(document);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("2", "error message");

		// Act
		RetrieveDocumentSetResponse actualResponse = sut
				.errorRetrieveDocumentSetResponseSchemaValidation(response, map);

		// Assert
		assertEquals(1, actualResponse.getDocumentResponse().size());
		assertEquals(document, actualResponse.getDocumentResponse().get(0));
		assertEquals(
				"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:PartialSuccess",
				actualResponse.getRegistryResponse().getStatus());
		assertEquals(
				"Document validation error(s) occurred in Policy Enforcement Point for document(s): [2]. Please contact to system administrator if this error persists.",
				actualResponse.getRegistryResponse().getRegistryErrorList()
						.getRegistryError().get(0).getCodeContext());
	}

	@Test
	public void testErrorRetrieveDocumentSetResponseSchemaValidation_Failure() {
		// Arrange
		RetrieveDocumentSetResponse response = new RetrieveDocumentSetResponse();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("2", "error message");

		// Act
		RetrieveDocumentSetResponse actualResponse = sut
				.errorRetrieveDocumentSetResponseSchemaValidation(response, map);

		// Assert
		assertEquals(0, actualResponse.getDocumentResponse().size());
		assertEquals(
				"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure",
				actualResponse.getRegistryResponse().getStatus());
		assertEquals(
				"Document validation error(s) occurred in Policy Enforcement Point for document(s): [2]. Please contact to system administrator if this error persists.",
				actualResponse.getRegistryResponse().getRegistryErrorList()
						.getRegistryError().get(0).getCodeContext());
	}

	@Test
	public void testSetRetrieveDocumentSetResponseRegistryErrorListFilteredByPatientAndAuthor_Failure()
			throws SAXException, IOException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorSetRetrieveDocumentSetResponseRegistryErrorListFilteredByPatientAndAuthor_Failure.xml");
		RetrieveDocumentSetResponse response = new RetrieveDocumentSetResponse();
		int numRemoved = 2;
		String patientIdMock = "patientIdMock";
		String authorNPIMock = "authorNPIMock";

		// Act
		RetrieveDocumentSetResponse actualResponse = sut
				.setRetrieveDocumentSetResponseRegistryErrorListFilteredByPatientAndAuthor(
						response, numRemoved, patientIdMock, authorNPIMock);

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testSetRetrieveDocumentSetResponseRegistryErrorListFilteredByPatientAndAuthor_PartialSuccess()
			throws SAXException, IOException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorSetRetrieveDocumentSetResponseRegistryErrorListFilteredByPatientAndAuthor_PartialSuccess.xml");
		RetrieveDocumentSetResponse response = new RetrieveDocumentSetResponse();
		response.getDocumentResponse().add(new DocumentResponse());
		response.getDocumentResponse().add(new DocumentResponse());
		int numRemoved = 2;
		String patientIdMock = "patientIdMock";
		String authorNPIMock = "authorNPIMock";

		// Act
		RetrieveDocumentSetResponse actualResponse = sut
				.setRetrieveDocumentSetResponseRegistryErrorListFilteredByPatientAndAuthor(
						response, numRemoved, patientIdMock, authorNPIMock);

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorRetrieveDocumentSetResponseMultipleRepositoryId()
			throws SAXException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorRetrieveDocumentSetResponseMultipleRepositoryId.xml");

		// Act
		RetrieveDocumentSetResponse actualResponse = sut
				.errorRetrieveDocumentSetResponseMultipleRepositoryId();

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorRetrieveDocumentSetResponseNotExistsOrAccessible()
			throws SAXException, IOException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorRetrieveDocumentSetResponseNotExistsOrAccessible.xml");
		RetrieveDocumentSetRequest input = new RetrieveDocumentSetRequest();
		DocumentRequest doc1 = new DocumentRequest();
		doc1.setDocumentUniqueId("DocumentUniqueId1");
		doc1.setRepositoryUniqueId("RepositoryUniqueId1");
		DocumentRequest doc2 = new DocumentRequest();
		doc2.setDocumentUniqueId("DocumentUniqueId2");
		doc2.setRepositoryUniqueId("RepositoryUniqueId2");
		input.getDocumentRequest().add(doc1);
		input.getDocumentRequest().add(doc2);

		// Act
		RetrieveDocumentSetResponse actualResponse = sut
				.errorRetrieveDocumentSetResponseNotExistsOrAccessible(input);

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorRetrieveDocumentSetResponseRepositoryNotAvailable()
			throws SAXException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorRetrieveDocumentSetResponseRepositoryNotAvailable.xml");

		// Act
		RetrieveDocumentSetResponse actualResponse = sut
				.errorRetrieveDocumentSetResponseRepositoryNotAvailable();

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorRetrieveDocumentSetResponseInternalPEPError()
			throws SAXException, IOException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorRetrieveDocumentSetResponseInternalPEPError.xml");

		// Act
		RetrieveDocumentSetResponse actualResponse = sut
				.errorRetrieveDocumentSetResponseInternalPEPError();

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorRetrieveDocumentSetResponseAccessDeniedByPDP()
			throws SAXException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorRetrieveDocumentSetResponseAccessDeniedByPDP.xml");

		// Act
		RetrieveDocumentSetResponse actualResponse = sut
				.errorRetrieveDocumentSetResponseAccessDeniedByPDP();

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorAdhocQueryResponseRegistryNotAvailable()
			throws SAXException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorAdhocQueryResponseRegistryNotAvailable.xml");

		// Act
		AdhocQueryResponse actualResponse = sut
				.errorAdhocQueryResponseRegistryNotAvailable();

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorAdhocQueryResponseAccessDeniedByPDP()
			throws SAXException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorAdhocQueryResponseAccessDeniedByPDP.xml");

		// Act
		AdhocQueryResponse actualResponse = sut
				.errorAdhocQueryResponseAccessDeniedByPDP();

		// assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorAdhocQueryResponseNoDocumentsFound()
			throws SAXException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorAdhocQueryResponseNoDocumentsFound.xml");
		String patientUniqueIdMock = "patientUniqueIdMock";
		String intermediarySubjectNPIMock = "intermediarySubjectNPIMock";

		// Act
		AdhocQueryResponse actualResponse = sut
				.errorAdhocQueryResponseNoDocumentsFound(patientUniqueIdMock,
						intermediarySubjectNPIMock);

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	@Test
	public void testErrorAdhocQueryResponseMissingParameters()
			throws SAXException, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("errorAdhocQueryResponseMissingParameters.xml");

		// Act
		AdhocQueryResponse actualResponse = sut
				.errorAdhocQueryResponseMissingParameters();

		// Assert
		assertResponse(expectedResponse, actualResponse);
	}

	private void assertResponse(String expectedResponse, Object actualResponse)
			throws SAXException, IOException, Throwable {
		assertNotNull(actualResponse);
		assertXMLEqual("", expectedResponse,
				marshaller.marshal(actualResponse));
	}
}
