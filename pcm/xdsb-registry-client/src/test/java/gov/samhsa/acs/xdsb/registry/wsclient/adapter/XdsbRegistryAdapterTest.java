package gov.samhsa.acs.xdsb.registry.wsclient.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.xdsb.common.AdhocQueryRequestBuilder;
import gov.samhsa.acs.xdsb.common.AdhocQueryRequestBuilder.ReturnType;
import gov.samhsa.acs.xdsb.common.AdhocQueryRequestBuilder.XdsbRegistryRequestData;
import gov.samhsa.acs.xdsb.common.AdhocQueryResponseParser;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindDocuments;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindSubmissionSets;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.GetSubmissionSetAndContents;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;
import gov.samhsa.acs.xdsb.registry.wsclient.exception.XdsbRegistryAdapterException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class XdsbRegistryAdapterTest {
	// Constants
	private static final String PATIENT_ID = "PATIENT_ID";
	private static final String DOMAIN_ID = "DOMAIN_ID";
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT = XdsbDocumentType.CLINICAL_DOCUMENT;
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT = XdsbDocumentType.PRIVACY_CONSENT;
	private String patientUniqueId;

	// Mocks
	@Mock
	private XdsbRegistryWebServiceClient xdsbRegistryMock;

	@Mock
	private AdhocQueryRequestBuilder adhocQueryRequestBuilderMock;

	@Mock
	private AdhocQueryResponseFilter adhocQueryResponseFilterMock;

	@Mock
	private AdhocQueryResponseParser adhocQueryResponseParserMock;

	// System under test
	@InjectMocks
	private XdsbRegistryAdapter sut;

	@Before
	public void setUp() throws Exception {
		final StringBuilder builder = new StringBuilder();
		builder.append("'");
		builder.append(PATIENT_ID);
		builder.append("^^^&");
		builder.append(DOMAIN_ID);
		builder.append("&ISO'");
		this.patientUniqueId = builder.toString();
	}

	@Test
	public void testFindDeprecatedDocumentUniqueIds() throws Throwable {
		// Arrange
		final String submissionSetPatientIdMock = "submissionSetPatientIdMock";
		final String submissionSetAuthorPersonMock = "submissionSetAuthorPersonMock";
		final AdhocQueryResponse findSubmissionSetsResponseMock = mock(AdhocQueryResponse.class);
		final AdhocQueryRequest getSubmissionSetAndContentsRequestMock = new AdhocQueryRequest();
		final AdhocQueryResponse getSubmissionSetAndContentsResponseMock = mock(AdhocQueryResponse.class);
		final List<String> extractSubmissionSetUniqueIdsMock = new LinkedList<String>();
		final String extractSubmissionSetUniqueIdMock = "extractSubmissionSetUniqueIdMock";
		extractSubmissionSetUniqueIdsMock.add(extractSubmissionSetUniqueIdMock);
		final Optional<String> deprecatedDocumentUniqueIdMock = Optional
				.of("deprecatedDocumentUniqueIdMock");
		final AdhocQueryRequest findSubmissionSetsRequestMock = new AdhocQueryRequest();
		final AdhocQueryType adhocQueryType = new AdhocQueryType();
		final SlotType1 slotXDS_SUBMISSION_SET_UNIQUE_ID = new SlotType1();
		final SlotType1 slotXDS_SUBMISSION_SET_PATIENT_ID = new SlotType1();
		final SlotType1 slotXDS_SUBMISSION_SET_AUTHOR_PERSON = new SlotType1();
		slotXDS_SUBMISSION_SET_PATIENT_ID
				.setName(XdsbQueryParameterValue.FindSubmissionSets.XDS_SUBMISSION_SET_PATIENT_ID
						.getParameterName());
		slotXDS_SUBMISSION_SET_AUTHOR_PERSON
				.setName(XdsbQueryParameterValue.FindSubmissionSets.XDS_SUBMISSION_SET_AUTHOR_PERSON
						.getParameterName());
		slotXDS_SUBMISSION_SET_UNIQUE_ID
				.setName(XdsbQueryParameterValue.GetSubmissionSetAndContents.XDS_SUBMISSION_SET_UNIQUE_ID
						.getParameterName());
		adhocQueryType.getSlot().add(slotXDS_SUBMISSION_SET_PATIENT_ID);
		adhocQueryType.getSlot().add(slotXDS_SUBMISSION_SET_AUTHOR_PERSON);
		adhocQueryType.getSlot().add(slotXDS_SUBMISSION_SET_UNIQUE_ID);
		slotXDS_SUBMISSION_SET_PATIENT_ID.setValueList(new ValueListType());
		slotXDS_SUBMISSION_SET_AUTHOR_PERSON.setValueList(new ValueListType());
		slotXDS_SUBMISSION_SET_UNIQUE_ID.setValueList(new ValueListType());
		findSubmissionSetsRequestMock.setAdhocQuery(adhocQueryType);
		getSubmissionSetAndContentsRequestMock.setAdhocQuery(adhocQueryType);
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<FindSubmissionSets> adhocQueryRequestData = mock(XdsbRegistryRequestData.class);
		when(
				adhocQueryRequestData.addQueryParameter(
						any(FindSubmissionSets.class), anyString()))
				.thenReturn(adhocQueryRequestData);
		when(adhocQueryRequestData.build()).thenReturn(
				findSubmissionSetsRequestMock);
		when(adhocQueryRequestBuilderMock.findSubmissionSets()).thenReturn(
				adhocQueryRequestData);
		when(
				adhocQueryResponseParserMock
						.parseSubmissionSetUniqueIds(findSubmissionSetsResponseMock))
				.thenReturn(extractSubmissionSetUniqueIdsMock);
		when(
				adhocQueryResponseParserMock
				.parseDeprecatedDocumentUniqueId(getSubmissionSetAndContentsResponseMock))
				.thenReturn(deprecatedDocumentUniqueIdMock);
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<GetSubmissionSetAndContents> getSubmissionSetAndContentsRequestData = mock(XdsbRegistryRequestData.class);
		when(
				getSubmissionSetAndContentsRequestData.addQueryParameter(
						any(GetSubmissionSetAndContents.class), anyString()))
				.thenReturn(getSubmissionSetAndContentsRequestData);
		when(adhocQueryRequestBuilderMock.getSubmissionSetAndContents())
				.thenReturn(getSubmissionSetAndContentsRequestData);
		when(getSubmissionSetAndContentsRequestData.build()).thenReturn(
				getSubmissionSetAndContentsRequestMock);
		when(
				xdsbRegistryMock
						.registryStoredQuery(findSubmissionSetsRequestMock))
				.thenReturn(findSubmissionSetsResponseMock);

		when(
				xdsbRegistryMock
						.registryStoredQuery(getSubmissionSetAndContentsRequestMock))
				.thenReturn(getSubmissionSetAndContentsResponseMock);

		// Act
		final List<String> result = sut.findDeprecatedDocumentUniqueIds(
				submissionSetPatientIdMock, submissionSetAuthorPersonMock);

		// Assert
		assertTrue(result.contains(deprecatedDocumentUniqueIdMock.get()));
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				FindSubmissionSets.XDS_SUBMISSION_SET_PATIENT_ID,
				submissionSetPatientIdMock);
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				FindSubmissionSets.XDS_SUBMISSION_SET_AUTHOR_PERSON,
				submissionSetAuthorPersonMock);
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindSubmissionSets.XDS_SUBMISSION_SET_STATUS), anyString());
		verify(getSubmissionSetAndContentsRequestData, times(1))
				.addQueryParameter(
						eq(GetSubmissionSetAndContents.XDS_SUBMISSION_SET_UNIQUE_ID),
						anyString());
	}

	@Test
	public void testFindSubmissionSets() throws JAXBException,
			XdsbRegistryAdapterException {
		// Arrange
		final String submissionSetPatientIdMock = "submissionSetPatientIdMock";
		final String submissionSetAuthorPersonMock = "submissionSetAuthorPersonMock";
		final AdhocQueryRequest findSubmissionSetsRequestMock = new AdhocQueryRequest();
		final AdhocQueryType adhocQueryType = new AdhocQueryType();
		final SlotType1 slotXDS_SUBMISSION_SET_PATIENT_ID = new SlotType1();
		final SlotType1 slotXDS_SUBMISSION_SET_AUTHOR_PERSON = new SlotType1();
		slotXDS_SUBMISSION_SET_PATIENT_ID
				.setName(XdsbQueryParameterValue.FindSubmissionSets.XDS_SUBMISSION_SET_PATIENT_ID
						.getParameterName());
		slotXDS_SUBMISSION_SET_AUTHOR_PERSON
				.setName(XdsbQueryParameterValue.FindSubmissionSets.XDS_SUBMISSION_SET_AUTHOR_PERSON
						.getParameterName());
		adhocQueryType.getSlot().add(slotXDS_SUBMISSION_SET_PATIENT_ID);
		adhocQueryType.getSlot().add(slotXDS_SUBMISSION_SET_AUTHOR_PERSON);
		slotXDS_SUBMISSION_SET_PATIENT_ID.setValueList(new ValueListType());
		slotXDS_SUBMISSION_SET_AUTHOR_PERSON.setValueList(new ValueListType());
		findSubmissionSetsRequestMock.setAdhocQuery(adhocQueryType);
		final AdhocQueryResponse findSubmissionSetsResponseMock = mock(AdhocQueryResponse.class);
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<FindSubmissionSets> adhocQueryRequestData = mock(XdsbRegistryRequestData.class);
		when(
				adhocQueryRequestData.addQueryParameter(
						any(FindSubmissionSets.class), anyString()))
				.thenReturn(adhocQueryRequestData);
		when(adhocQueryRequestData.build()).thenReturn(
				findSubmissionSetsRequestMock);
		when(adhocQueryRequestBuilderMock.findSubmissionSets()).thenReturn(
				adhocQueryRequestData);
		when(
				xdsbRegistryMock
				.registryStoredQuery(findSubmissionSetsRequestMock))
				.thenReturn(findSubmissionSetsResponseMock);

		// Act
		final AdhocQueryResponse actualResponse = sut.findSubmissionSets(
				submissionSetPatientIdMock, submissionSetAuthorPersonMock);

		// Assert
		assertEquals(findSubmissionSetsResponseMock, actualResponse);
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				FindSubmissionSets.XDS_SUBMISSION_SET_PATIENT_ID,
				submissionSetPatientIdMock);
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				FindSubmissionSets.XDS_SUBMISSION_SET_AUTHOR_PERSON,
				submissionSetAuthorPersonMock);
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindSubmissionSets.XDS_SUBMISSION_SET_STATUS), anyString());
	}

	@Test
	public void testGetSubmissionSetAndContents() throws JAXBException,
			XdsbRegistryAdapterException {
		// Arrange
		final String submissionSetPatientIdMock = "submissionSetPatientIdMock";
		final AdhocQueryRequest getSubmissionSetAndContentsRequestMock = new AdhocQueryRequest();
		final AdhocQueryResponse getSubmissionSetAndContentsResponseMock = mock(AdhocQueryResponse.class);
		final AdhocQueryType adhocQueryType = new AdhocQueryType();
		final SlotType1 slotXDS_SUBMISSION_SET_UNIQUE_ID = new SlotType1();
		slotXDS_SUBMISSION_SET_UNIQUE_ID
		.setName(XdsbQueryParameterValue.GetSubmissionSetAndContents.XDS_SUBMISSION_SET_UNIQUE_ID
						.getParameterName());
		slotXDS_SUBMISSION_SET_UNIQUE_ID.setValueList(new ValueListType());
		adhocQueryType.getSlot().add(slotXDS_SUBMISSION_SET_UNIQUE_ID);
		getSubmissionSetAndContentsRequestMock.setAdhocQuery(adhocQueryType);
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<GetSubmissionSetAndContents> getSubmissionSetAndContentsData = mock(XdsbRegistryRequestData.class);
		when(
				getSubmissionSetAndContentsData.addQueryParameter(
						any(GetSubmissionSetAndContents.class), anyString()))
						.thenReturn(getSubmissionSetAndContentsData);
		when(getSubmissionSetAndContentsData.build()).thenReturn(
				getSubmissionSetAndContentsRequestMock);
		when(adhocQueryRequestBuilderMock.getSubmissionSetAndContents())
		.thenReturn(getSubmissionSetAndContentsData);
		when(
				xdsbRegistryMock
				.registryStoredQuery(getSubmissionSetAndContentsRequestMock))
				.thenReturn(getSubmissionSetAndContentsResponseMock);

		// Act
		final AdhocQueryResponse actualResponse = sut
				.getSubmissionSetAndContents(submissionSetPatientIdMock);

		// Assert
		assertEquals(getSubmissionSetAndContentsResponseMock, actualResponse);
		verify(getSubmissionSetAndContentsData, times(1)).addQueryParameter(
				eq(GetSubmissionSetAndContents.XDS_SUBMISSION_SET_UNIQUE_ID),
				anyString());
	}

	@Test
	public void testRegistryStoredQuery_Given_AdhocQueryRequest() {
		// Arrange
		final AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();
		final AdhocQueryResponse adhocQueryResponse = new AdhocQueryResponse();
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
		.thenReturn(adhocQueryResponse);

		// Act
		final AdhocQueryResponse actualResponse = sut
				.registryStoredQuery(adhocQueryRequest);

		// Assert
		assertEquals(adhocQueryResponse, actualResponse);
	}

	@Test
	public void testRegistryStoredQueryFindDocuments_Given_PatientId_HomeCommunityId_XdsbDocumentType_ClinicalDocument_Service_Time_False()
			throws Exception, Throwable {
		// Arrange
		final boolean serviceTimeAware = false;
		final AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();
		final AdhocQueryResponse adhocQueryResponse = new AdhocQueryResponse();
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<FindDocuments> adhocQueryRequestData = mock(XdsbRegistryRequestData.class);
		when(adhocQueryRequestData.setReturnComposedObjects(true)).thenReturn(
				adhocQueryRequestData);
		when(adhocQueryRequestData.setReturnType(any(ReturnType.class)))
				.thenReturn(adhocQueryRequestData);
		when(
				adhocQueryRequestData.addQueryParameter(
						any(FindDocuments.class), anyString())).thenReturn(
				adhocQueryRequestData);
		when(adhocQueryRequestData.build()).thenReturn(adhocQueryRequest);
		when(adhocQueryRequestBuilderMock.findDocuments()).thenReturn(
				adhocQueryRequestData);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
		.thenReturn(adhocQueryResponse);

		// Act
		final AdhocQueryResponse actualResponse = sut.findDocuments(
				patientUniqueId, null, XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT,
				serviceTimeAware);

		// Assert
		assertEquals(adhocQueryResponse, actualResponse);
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_PATIENT_ID), anyString());
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_STATUS), anyString());
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_FORMAT_CODE), anyString());
		verify(adhocQueryRequestData, times(0)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO),
				anyString());
		verify(adhocQueryRequestData, times(0)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM),
				anyString());
	}

	@Test
	public void testRegistryStoredQueryFindDocuments_Given_PatientId_HomeCommunityId_XdsbDocumentType_ClinicalDocument_Service_Time_True()
			throws Exception, Throwable {
		// Arrange
		final boolean serviceTimeAware = true;
		final AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();
		final AdhocQueryResponse adhocQueryResponse = new AdhocQueryResponse();
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<FindDocuments> adhocQueryRequestData = mock(XdsbRegistryRequestData.class);
		when(adhocQueryRequestData.setReturnComposedObjects(true)).thenReturn(
				adhocQueryRequestData);
		when(adhocQueryRequestData.setReturnType(any(ReturnType.class)))
				.thenReturn(adhocQueryRequestData);
		when(
				adhocQueryRequestData.addQueryParameter(
						any(FindDocuments.class), anyString())).thenReturn(
				adhocQueryRequestData);
		when(adhocQueryRequestData.build()).thenReturn(adhocQueryRequest);
		when(adhocQueryRequestBuilderMock.findDocuments()).thenReturn(
				adhocQueryRequestData);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
		.thenReturn(adhocQueryResponse);

		// Act
		final AdhocQueryResponse actualResponse = sut.findDocuments(
				patientUniqueId, null, XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT,
				serviceTimeAware);

		// Assert
		assertEquals(adhocQueryResponse, actualResponse);
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_PATIENT_ID), anyString());
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_STATUS), anyString());
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_FORMAT_CODE), anyString());
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO),
				anyString());
		verify(adhocQueryRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM),
				anyString());
	}

	@Test
	public void testRegistryStoredQueryFindDocuments_Given_PatientId_HomeCommunityId_XdsbDocumentType_PrivacyConsent_Service_Time_Aware_False()
			throws Exception, Throwable {
		// Arrange
		final boolean serviceTimeAware = false;
		final AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();
		final AdhocQueryResponse adhocQueryResponse = new AdhocQueryResponse();
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<FindDocuments> findDocumentRequestData = mock(XdsbRegistryRequestData.class);
		when(findDocumentRequestData.setReturnComposedObjects(true))
		.thenReturn(findDocumentRequestData);
		when(findDocumentRequestData.setReturnType(ReturnType.LeafClass))
				.thenReturn(findDocumentRequestData);
		when(
				findDocumentRequestData.addQueryParameter(
						any(FindDocuments.class), anyString())).thenReturn(
				findDocumentRequestData);
		when(findDocumentRequestData.build()).thenReturn(adhocQueryRequest);
		when(adhocQueryRequestBuilderMock.findDocuments()).thenReturn(
				findDocumentRequestData);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
		.thenReturn(adhocQueryResponse);

		// Act
		final AdhocQueryResponse actualResponse = sut.findDocuments(
				patientUniqueId, null, XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT,
				serviceTimeAware);

		// Assert
		assertEquals(adhocQueryResponse, actualResponse);
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_PATIENT_ID), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_STATUS), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				FindDocuments.XDS_DOCUMENT_ENTRY_FORMAT_CODE,
				XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT.getXdsbMetadataFormatCode()
				.get().getFormatCode());
		verify(findDocumentRequestData, times(0)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO),
				anyString());
		verify(findDocumentRequestData, times(0)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM),
				anyString());
	}

	@Test
	public void testRegistryStoredQueryFindDocuments_Given_PatientId_HomeCommunityId_XdsbDocumentType_PrivacyConsent_Service_Time_Aware_True()
			throws Exception, Throwable {
		// Arrange
		final boolean serviceTimeAware = true;
		final AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();
		final AdhocQueryResponse adhocQueryResponse = new AdhocQueryResponse();
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<FindDocuments> findDocumentRequestData = mock(XdsbRegistryRequestData.class);
		when(findDocumentRequestData.setReturnComposedObjects(true))
		.thenReturn(findDocumentRequestData);
		when(findDocumentRequestData.setReturnType(ReturnType.LeafClass))
				.thenReturn(findDocumentRequestData);
		when(
				findDocumentRequestData.addQueryParameter(
						any(FindDocuments.class), anyString())).thenReturn(
				findDocumentRequestData);
		when(findDocumentRequestData.build()).thenReturn(adhocQueryRequest);
		when(adhocQueryRequestBuilderMock.findDocuments()).thenReturn(
				findDocumentRequestData);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
		.thenReturn(adhocQueryResponse);

		// Act
		final AdhocQueryResponse actualResponse = sut.findDocuments(
				patientUniqueId, null, XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT,
				serviceTimeAware);

		// Assert
		assertEquals(adhocQueryResponse, actualResponse);
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_PATIENT_ID), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_STATUS), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_FORMAT_CODE), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO),
				anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM),
				anyString());
	}

	@Test
	public void testRegistryStoredQueryFindDocuments_Service_Time_Aware_False()
			throws Exception, Throwable {
		// Arrange
		final String patientIdMock = "patientIdMock";
		final String domainIdMock = "domainIdMock";
		final String authorIdMock = "authorIdMock";
		final XdsbDocumentType xdsbDocumentTypeMock = XdsbDocumentType.CLINICAL_DOCUMENT;
		final boolean serviceTimeAwareMock = false;
		final AdhocQueryResponse responseMock = new AdhocQueryResponse();
		final AdhocQueryResponse filteredResponseMock = new AdhocQueryResponse();
		when(
				adhocQueryResponseFilterMock.filterByAuthor(responseMock,
						authorIdMock)).thenReturn(filteredResponseMock);
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<FindDocuments> findDocumentRequestData = mock(XdsbRegistryRequestData.class);
		when(findDocumentRequestData.setReturnComposedObjects(true))
		.thenReturn(findDocumentRequestData);
		when(findDocumentRequestData.setReturnType(ReturnType.LeafClass))
				.thenReturn(findDocumentRequestData);
		when(
				findDocumentRequestData.addQueryParameter(
						any(FindDocuments.class), anyString())).thenReturn(
				findDocumentRequestData);
		final AdhocQueryRequest adhocQueryRequest = mock(AdhocQueryRequest.class);
		when(findDocumentRequestData.build()).thenReturn(adhocQueryRequest);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
				.thenReturn(responseMock);
		when(adhocQueryRequestBuilderMock.findDocuments()).thenReturn(
				findDocumentRequestData);

		// Act
		final AdhocQueryResponse actualResponse = sut.findDocuments(
				patientIdMock, domainIdMock, authorIdMock,
				xdsbDocumentTypeMock, serviceTimeAwareMock);

		// Assert
		assertEquals(filteredResponseMock, actualResponse);
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_PATIENT_ID), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_STATUS), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_FORMAT_CODE), anyString());
		verify(findDocumentRequestData, times(0)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO),
				anyString());
		verify(findDocumentRequestData, times(0)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM),
				anyString());
	}

	@Test
	public void testRegistryStoredQueryFindDocuments_Service_Time_Aware_True_Xdsb_Document_Type_CLINICAL_DOCUMENT()
			throws Exception, Throwable {
		// Arrange
		final String patientIdMock = "patientIdMock";
		final String domainIdMock = "domainIdMock";
		final String authorIdMock = "authorIdMock";
		final XdsbDocumentType xdsbDocumentTypeMock = XdsbDocumentType.CLINICAL_DOCUMENT;
		final boolean serviceTimeAwareMock = true;
		final AdhocQueryResponse responseMock = new AdhocQueryResponse();
		final AdhocQueryResponse filteredResponseMock = new AdhocQueryResponse();
		when(
				adhocQueryResponseFilterMock.filterByAuthor(responseMock,
						authorIdMock)).thenReturn(filteredResponseMock);
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<FindDocuments> findDocumentRequestData = mock(XdsbRegistryRequestData.class);
		when(findDocumentRequestData.setReturnComposedObjects(true))
		.thenReturn(findDocumentRequestData);
		when(findDocumentRequestData.setReturnType(ReturnType.LeafClass))
		.thenReturn(findDocumentRequestData);
		when(
				findDocumentRequestData.addQueryParameter(
						any(FindDocuments.class), anyString())).thenReturn(
								findDocumentRequestData);
		final AdhocQueryRequest adhocQueryRequest = mock(AdhocQueryRequest.class);
		when(findDocumentRequestData.build()).thenReturn(adhocQueryRequest);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
		.thenReturn(responseMock);
		when(adhocQueryRequestBuilderMock.findDocuments()).thenReturn(
				findDocumentRequestData);

		// Act
		final AdhocQueryResponse actualResponse = sut.findDocuments(
				patientIdMock, domainIdMock, authorIdMock,
				xdsbDocumentTypeMock, serviceTimeAwareMock);

		// Assert
		assertEquals(filteredResponseMock, actualResponse);
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_PATIENT_ID), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_STATUS), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				FindDocuments.XDS_DOCUMENT_ENTRY_FORMAT_CODE,
				xdsbDocumentTypeMock.getXdsbMetadataFormatCode().get()
				.getFormatCode());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO),
				anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM),
				anyString());
	}

	@Test
	public void testRegistryStoredQueryFindDocuments_Service_Time_Aware_True_Xdsb_Document_Type_PRIVACY_CONSENT()
			throws Exception, Throwable {
		// Arrange
		final String patientIdMock = "patientIdMock";
		final String domainIdMock = "domainIdMock";
		final String authorIdMock = "authorIdMock";
		final XdsbDocumentType xdsbDocumentTypeMock = XdsbDocumentType.PRIVACY_CONSENT;
		final boolean serviceTimeAwareMock = true;
		final AdhocQueryResponse responseMock = new AdhocQueryResponse();
		final AdhocQueryResponse filteredResponseMock = new AdhocQueryResponse();
		when(
				adhocQueryResponseFilterMock.filterByAuthor(responseMock,
						authorIdMock)).thenReturn(filteredResponseMock);
		@SuppressWarnings("unchecked")
		final XdsbRegistryRequestData<FindDocuments> findDocumentRequestData = mock(XdsbRegistryRequestData.class);
		when(findDocumentRequestData.setReturnComposedObjects(true))
		.thenReturn(findDocumentRequestData);
		when(findDocumentRequestData.setReturnType(ReturnType.LeafClass))
		.thenReturn(findDocumentRequestData);
		when(
				findDocumentRequestData.addQueryParameter(
						any(FindDocuments.class), anyString())).thenReturn(
								findDocumentRequestData);
		final AdhocQueryRequest adhocQueryRequest = mock(AdhocQueryRequest.class);
		when(findDocumentRequestData.build()).thenReturn(adhocQueryRequest);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
		.thenReturn(responseMock);
		when(adhocQueryRequestBuilderMock.findDocuments()).thenReturn(
				findDocumentRequestData);

		// Act
		final AdhocQueryResponse actualResponse = sut.findDocuments(
				patientIdMock, domainIdMock, authorIdMock,
				xdsbDocumentTypeMock, serviceTimeAwareMock);

		// Assert
		assertEquals(filteredResponseMock, actualResponse);
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_PATIENT_ID), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_STATUS), anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				FindDocuments.XDS_DOCUMENT_ENTRY_FORMAT_CODE,
				xdsbDocumentTypeMock.getXdsbMetadataFormatCode().get()
				.getFormatCode());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO),
				anyString());
		verify(findDocumentRequestData, times(1)).addQueryParameter(
				eq(FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM),
				anyString());
	}
}
