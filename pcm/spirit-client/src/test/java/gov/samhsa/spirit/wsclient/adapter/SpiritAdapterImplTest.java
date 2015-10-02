package gov.samhsa.spirit.wsclient.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGenerator;
import gov.samhsa.spirit.wsclient.dto.EhrPatientClientListDto;
import gov.samhsa.spirit.wsclient.dto.PatientDto;
import gov.samhsa.spirit.wsclient.exception.SpiritAdapterException;
import gov.samhsa.spirit.wsclient.util.SpiritClientHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.spirit.ehr.ws.client.generated.AuthorMetadataClientDto;
import com.spirit.ehr.ws.client.generated.DocumentClientDto;
import com.spirit.ehr.ws.client.generated.EhrException_Exception;
import com.spirit.ehr.ws.client.generated.EhrPatientClientDto;
import com.spirit.ehr.ws.client.generated.EhrPatientRq;
import com.spirit.ehr.ws.client.generated.EhrPatientRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicyDiscardRq;
import com.spirit.ehr.ws.client.generated.EhrPolicyDiscardRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicyRetrieveRq;
import com.spirit.ehr.ws.client.generated.EhrPolicyRetrieveRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitOrUpdateRq;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitOrUpdateRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitRq;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicyUpdateRq;
import com.spirit.ehr.ws.client.generated.EhrPolicyUpdateRsp;
import com.spirit.ehr.ws.client.generated.EhrWsEmptyReq;
import com.spirit.ehr.ws.client.generated.EhrXdsQGetAllRq;
import com.spirit.ehr.ws.client.generated.EhrXdsQRsp;
import com.spirit.ehr.ws.client.generated.EhrXdsRetrReq;
import com.spirit.ehr.ws.client.generated.FolderClientDto;
import com.spirit.ehr.ws.client.generated.IheClassificationClientDto;
import com.spirit.ehr.ws.client.generated.PatientContentClientDto;
import com.spirit.ehr.ws.client.generated.SourceSubmissionClientDto;
import com.spirit.ehr.ws.client.generated.SpiritSimpleLoginRequest;
import com.spirit.ehr.ws.client.generated.SpiritUserResponse;
import com.spirit.ehr.ws.client.generated.SubmissionSetClientDto;
import com.spirit.ehr.ws.client.generated.XdsSrcApRpReq;
import com.spirit.ehr.ws.client.generated.XdsSrcDeleteReq;
import com.spirit.ehr.ws.client.generated.XdsSrcDeprecateReq;
import com.spirit.ehr.ws.client.generated.XdsSrcSubmitReq;
import com.spirit.ehr.ws.client.generated.XdsSrcSubmitRsp;
import com.spirit.ehr.ws.client.generated.XdsSrcUpdateReq;
import com.spirit.ehr.ws.interfaces.SpiritEhrWsClientRqRspInterface;

@RunWith(MockitoJUnitRunner.class)
public class SpiritAdapterImplTest {

	private String org = "org";
	private String pwd = "password";
	private String rol = "role";
	private String user = "user";
	private String domainId = "domainId";
	private String c2sDomainId = "c2sDomainId";
	private String c2sDomainType = "c2sDomainType";
	private String c2sEnvType="c2sEnvType";
	private String stateId = "stateId";
	private String endPointUrl = "http://216.59.44.169:8181";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private static SpiritEhrWsClientRqRspInterface webService;

	@Mock
	private SpiritClientHelper sHelper;

	@Mock
	JXPathContext context;

	@Mock
	XdsbMetadataGenerator policyMetadataGenerator;

	@InjectMocks
	SpiritAdapterImpl sat;

	@Before
	public void setUp() throws EhrException_Exception {

		// when(sat.createEhrWsRqRspInterface(anyString())).thenReturn(webService);
		webService = mock(SpiritEhrWsClientRqRspInterface.class);
		sat = new SpiritAdapterImpl(endPointUrl, org, pwd, rol, user, domainId,c2sDomainId,c2sDomainType,c2sEnvType,
				webService);
		// Stub log in
		SpiritUserResponse loginResponseMock = mock(SpiritUserResponse.class);
		when(loginResponseMock.getStateID()).thenReturn(stateId);
		when(webService.usrLogin(isA(SpiritSimpleLoginRequest.class)))
				.thenReturn(loginResponseMock);
	}

	@Test
	public void testInsertPatient() throws EhrException_Exception,
			SpiritAdapterException {
		EhrPatientRq ehrWsEmptyReq = mock(EhrPatientRq.class);

		EhrPatientRsp ehrPatIdRsp = mock(EhrPatientRsp.class);

		when(webService.insertPatient(ehrWsEmptyReq)).thenReturn(ehrPatIdRsp);

		assertEquals(ehrPatIdRsp, sat.insertPatient(ehrWsEmptyReq));
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SpiritAdapterException.class)
	public void testInsertPatient_throw_exceptions()
			throws EhrException_Exception, SpiritAdapterException {
		EhrPatientRq ehrWsEmptyReq = mock(EhrPatientRq.class);

		when(webService.insertPatient(ehrWsEmptyReq)).thenThrow(
				EhrException_Exception.class);

		sat.insertPatient(ehrWsEmptyReq);
	}

	@Test
	public void testUsrOrgRoleLogin() throws EhrException_Exception,
			SpiritAdapterException {
		SpiritUserResponse spiritUserResponse = mock(SpiritUserResponse.class);

		when(webService.usrLogin(any(SpiritSimpleLoginRequest.class)))
				.thenReturn(spiritUserResponse);

		assertEquals(spiritUserResponse, sat.usrOrgRoleLogin());
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SpiritAdapterException.class)
	public void testUsrOrgRoleLogin_throw_exceptions()
			throws EhrException_Exception, SpiritAdapterException {

		when(webService.usrLogin(any(SpiritSimpleLoginRequest.class)))
				.thenThrow(EhrException_Exception.class);

		sat.usrOrgRoleLogin();
	}

	@Test
	public void testLogout() throws SpiritAdapterException,
			EhrException_Exception {

		SpiritAdapterImpl spysat = spy(sat);
		spysat.logout("stateID");
		verify(webService, times(1)).usrLogout(any(EhrWsEmptyReq.class));

	}

	@Test
	public void testLogin() throws SpiritAdapterException,
			EhrException_Exception {
		SpiritUserResponse spiritUserResponse = mock(SpiritUserResponse.class);
		when(webService.usrLogin(any(SpiritSimpleLoginRequest.class)))
				.thenReturn(spiritUserResponse);
		when(spiritUserResponse.getStateID()).thenReturn("stateID");
		assertEquals("stateID", sat.login());

	}


	@Test
	public void testUpdatePatientIdByLocalId() throws SpiritAdapterException,
			EhrException_Exception {
		SpiritUserResponse spiritUserResponse = mock(SpiritUserResponse.class);
		when(webService.usrLogin(any(SpiritSimpleLoginRequest.class)))
				.thenReturn(spiritUserResponse);
		when(spiritUserResponse.getStateID()).thenReturn("stateID");

		PatientDto patientDto = mock(PatientDto.class);
		when(patientDto.getBirthDate()).thenReturn("19070707");

		List<EhrPatientClientDto> ehrPatientClientDtos = new LinkedList<EhrPatientClientDto>();

		EhrPatientClientDto ehrPatientClientDto = mock(EhrPatientClientDto.class);
		ehrPatientClientDtos.add(ehrPatientClientDto);

		EhrPatientRsp patientResponse = mock(EhrPatientRsp.class);
		when(webService.queryPatients(any(EhrPatientRq.class))).thenReturn(
				patientResponse);
		when(patientResponse.getResponseData())
				.thenReturn(ehrPatientClientDtos);

		EhrPatientRsp patientResponse2 = mock(EhrPatientRsp.class);
		when(webService.updatePatient(any(EhrPatientRq.class))).thenReturn(
				patientResponse2);
		when(patientResponse2.getResponseData()).thenReturn(
				ehrPatientClientDtos);

		when(patientResponse2.getResponseData().get(0).getFamilyName())
				.thenReturn("albert");
		when(patientResponse2.getResponseData().get(0).getGivenName())
				.thenReturn("smith");

		EhrPatientClientDto ehrDto = mock(EhrPatientClientDto.class);
		when(
				sHelper.convertFromPatientDto(any(PatientDto.class),
						anyBoolean(), any(EhrPatientClientDto.class)))
				.thenReturn(ehrDto);

		sat.updatePatientByLocId(patientDto);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SpiritAdapterException.class)
	public void testUpdatePatientIdByPDQ_throw_exceptions()
			throws SpiritAdapterException, EhrException_Exception {
		SpiritUserResponse spiritUserResponse = mock(SpiritUserResponse.class);
		when(webService.usrLogin(any(SpiritSimpleLoginRequest.class)))
				.thenReturn(spiritUserResponse);
		when(spiritUserResponse.getStateID()).thenReturn("stateID");

		PatientDto patientDto = mock(PatientDto.class);
		when(patientDto.getBirthDate()).thenReturn("19070707");

		List<EhrPatientClientDto> ehrPatientClientDtos = new LinkedList<EhrPatientClientDto>();

		EhrPatientClientDto ehrPatientClientDto = mock(EhrPatientClientDto.class);
		ehrPatientClientDtos.add(ehrPatientClientDto);

		EhrPatientRsp patientResponse = mock(EhrPatientRsp.class);
		when(webService.queryPatients(any(EhrPatientRq.class))).thenReturn(
				patientResponse);
		when(patientResponse.getResponseData())
				.thenReturn(ehrPatientClientDtos);

		EhrPatientRsp patientResponse2 = mock(EhrPatientRsp.class);
		when(webService.updatePatient(any(EhrPatientRq.class))).thenThrow(
				EhrException_Exception.class);
		when(patientResponse2.getResponseData()).thenReturn(
				ehrPatientClientDtos);

		when(patientResponse2.getResponseData().get(0).getFamilyName())
				.thenReturn("albert");
		when(patientResponse2.getResponseData().get(0).getGivenName())
				.thenReturn("smith");

		EhrPatientClientDto ehrDto = mock(EhrPatientClientDto.class);
		when(
				sHelper.convertFromPatientDto(any(PatientDto.class),
						anyBoolean(), any(EhrPatientClientDto.class)))
				.thenReturn(ehrDto);

		sat.updatePatientByLocId(patientDto);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SpiritAdapterException.class)
	public void testUpdatePatientIdByPDQ_2() throws SpiritAdapterException,
			EhrException_Exception {
		SpiritUserResponse spiritUserResponse = mock(SpiritUserResponse.class);
		when(webService.usrLogin(any(SpiritSimpleLoginRequest.class)))
				.thenReturn(spiritUserResponse);
		when(spiritUserResponse.getStateID()).thenReturn("stateID");

		PatientDto patientDto = mock(PatientDto.class);
		when(patientDto.getBirthDate()).thenReturn("19070707");

		List<EhrPatientClientDto> ehrPatientClientDtos = new LinkedList<EhrPatientClientDto>();

		EhrPatientClientDto ehrPatientClientDto = mock(EhrPatientClientDto.class);
		ehrPatientClientDtos.add(ehrPatientClientDto);

		EhrPatientRsp patientResponse = mock(EhrPatientRsp.class);
		when(webService.queryPatients(any(EhrPatientRq.class))).thenReturn(
				patientResponse);
		when(patientResponse.getResponseData()).thenReturn(null);

		EhrPatientRsp patientResponse2 = mock(EhrPatientRsp.class);
		when(webService.updatePatient(any(EhrPatientRq.class))).thenThrow(
				EhrException_Exception.class);
		when(patientResponse2.getResponseData()).thenReturn(
				ehrPatientClientDtos);

		when(patientResponse2.getResponseData().get(0).getFamilyName())
				.thenReturn("albert");
		when(patientResponse2.getResponseData().get(0).getGivenName())
				.thenReturn("smith");

		EhrPatientClientDto ehrDto = mock(EhrPatientClientDto.class);
		when(
				sHelper.convertFromPatientDto(any(PatientDto.class),
						anyBoolean(), any(EhrPatientClientDto.class)))
				.thenReturn(ehrDto);

		sat.updatePatientByLocId(patientDto);
	}

	@Test
	public void testAppendDocument() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		EhrPatientClientDto patientMock = mock(EhrPatientClientDto.class);
		DocumentClientDto oldDocumentMock = mock(DocumentClientDto.class);
		DocumentClientDto documentToAppendMock = mock(DocumentClientDto.class);
		String stateIdMock = "stateIdMock";
		final XdsSrcSubmitRsp responseMock = mock(XdsSrcSubmitRsp.class);
		when(webService.appendDocument(any(XdsSrcApRpReq.class))).thenReturn(
				responseMock);
		// ReflectionTestUtils.setField(sat, "client", webService);

		// Act
		XdsSrcSubmitRsp actualResponse = sat.appendDocument(patientMock,
				oldDocumentMock, documentToAppendMock, stateIdMock);

		// Assert
		assertEquals(responseMock, actualResponse);
		verify(webService, times(1)).appendDocument(any(XdsSrcApRpReq.class));
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SpiritAdapterException.class)
	public void testAppendDocument_Throws_SpiritAdapterException()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		EhrPatientClientDto patientMock = mock(EhrPatientClientDto.class);
		DocumentClientDto oldDocumentMock = mock(DocumentClientDto.class);
		DocumentClientDto documentToAppendMock = mock(DocumentClientDto.class);
		String stateIdMock = "stateIdMock";
		when(webService.appendDocument(isA(XdsSrcApRpReq.class))).thenThrow(
				EhrException_Exception.class);

		// Act
		sat.appendDocument(patientMock, oldDocumentMock, documentToAppendMock,
				stateIdMock);
	}

	@Test
	public void testDeprecatePolicy() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		String documentUniqueIdMock = "documentUniqueIdMock";
		String patientIdMock = "patientIdMock";
		byte[] revokedPdfConsentMock = new byte[1];
		EhrPatientRsp ehrPatientRspMock = mock(EhrPatientRsp.class);
		List<EhrPatientClientDto> ehrPatientClientDtoList = new LinkedList<EhrPatientClientDto>();
		EhrPatientClientDto ehrPatientClientDtoMock = mock(EhrPatientClientDto.class);
		ehrPatientClientDtoList.add(ehrPatientClientDtoMock);
		when(ehrPatientRspMock.getResponseData()).thenReturn(
				ehrPatientClientDtoList);
		when(webService.queryPatients(isA(EhrPatientRq.class))).thenReturn(
				ehrPatientRspMock);
		EhrXdsQRsp ehrXdsQRspMock = mock(EhrXdsQRsp.class);
		when(webService.queryPatientContent(isA(EhrXdsQGetAllRq.class)))
				.thenReturn(ehrXdsQRspMock);
		PatientContentClientDto patientContentClientDtoMock = mock(PatientContentClientDto.class);
		when(ehrXdsQRspMock.getResponseData()).thenReturn(
				patientContentClientDtoMock);
		List<DocumentClientDto> documentClientDtoList = new LinkedList<DocumentClientDto>();
		DocumentClientDto documentClientDtoMockXacml = mock(DocumentClientDto.class);
		DocumentClientDto documentClientDtoMockSignedPdf = mock(DocumentClientDto.class);
		documentClientDtoList.add(documentClientDtoMockXacml);
		documentClientDtoList.add(documentClientDtoMockSignedPdf);
		when(patientContentClientDtoMock.getDocuments()).thenReturn(
				documentClientDtoList);
		when(documentClientDtoMockXacml.getUniqueId()).thenReturn(
				SpiritConstants.URN_PREFIX_XACML + documentUniqueIdMock);
		when(documentClientDtoMockSignedPdf.getUniqueId()).thenReturn(
				SpiritConstants.URN_PREFIX_PDF_SIGNED + documentUniqueIdMock);
		XdsSrcSubmitRsp xdsSrcSubmitRspMock = mock(XdsSrcSubmitRsp.class);
		when(webService.appendDocument(isA(XdsSrcApRpReq.class))).thenReturn(
				xdsSrcSubmitRspMock);
		XdsSrcSubmitRsp xdsSrcSubmitRspMockDeprecate = mock(XdsSrcSubmitRsp.class);
		when(webService.deprecateDocument(isA(XdsSrcDeprecateReq.class)))
				.thenReturn(xdsSrcSubmitRspMockDeprecate);
		EhrPolicyDiscardRsp ehrPolicyDiscardRspMock = mock(EhrPolicyDiscardRsp.class);
		when(webService.discardPolicies(isA(EhrPolicyDiscardRq.class)))
				.thenReturn(ehrPolicyDiscardRspMock);
		SubmissionSetClientDto submissionSetClientDto = mock(SubmissionSetClientDto.class);
		when(documentClientDtoMockSignedPdf.getSubmissionSet()).thenReturn(
				submissionSetClientDto);

		AuthorMetadataClientDto authorMetadataClientDto = new AuthorMetadataClientDto();
		authorMetadataClientDto.setPerson("authorPerson");
		IheClassificationClientDto classCode = new IheClassificationClientDto();
		classCode.setNodeRepresentation("classCode");
		classCode.setValue("classCode");
		IheClassificationClientDto formatCode = new IheClassificationClientDto();
		formatCode.setNodeRepresentation("formatCode");
		formatCode.setValue("formatCode");
		IheClassificationClientDto typeCode = new IheClassificationClientDto();
		typeCode.setNodeRepresentation("typeCode");
		typeCode.setValue("typeCode");
		IheClassificationClientDto confidentialityCode = new IheClassificationClientDto();
		confidentialityCode.setNodeRepresentation("confidentialityCode");
		confidentialityCode.setValue("confidentialityCode");
		IheClassificationClientDto healthcareFacilityCode = new IheClassificationClientDto();
		healthcareFacilityCode.setNodeRepresentation("healthcareFacilityCode");
		healthcareFacilityCode.setValue("healthcareFacilityCode");
		IheClassificationClientDto practiceSettingCode = new IheClassificationClientDto();
		practiceSettingCode.setNodeRepresentation("practiceSettingCode");
		practiceSettingCode.setValue("practiceSettingCode");
		IheClassificationClientDto contentTypeCode = new IheClassificationClientDto();
		contentTypeCode.setNodeRepresentation("contentTypeCode");
		contentTypeCode.setValue("contentTypeCode");

		when(submissionSetClientDto.getAuthor()).thenReturn(
				Arrays.asList(authorMetadataClientDto));

		when(documentClientDtoMockSignedPdf.getClassCode()).thenReturn(
				classCode);
		when(documentClientDtoMockSignedPdf.getFormatCode()).thenReturn(
				formatCode);
		when(documentClientDtoMockSignedPdf.getTypeCode()).thenReturn(typeCode);
		when(documentClientDtoMockSignedPdf.getConfidentialityCode())
				.thenReturn(confidentialityCode);
		when(documentClientDtoMockSignedPdf.getHealthcareFacilityCode())
				.thenReturn(healthcareFacilityCode);
		when(documentClientDtoMockSignedPdf.getPracticeSettingCode())
				.thenReturn(practiceSettingCode);
		when(submissionSetClientDto.getContentTypeCode()).thenReturn(
				contentTypeCode);

		// Act
		XdsSrcSubmitRsp actualResponse = sat.deprecatePolicy(
				documentUniqueIdMock, patientIdMock, revokedPdfConsentMock);

		// Assert
		verify(webService, times(1)).deprecateDocument(
				isA(XdsSrcDeprecateReq.class));
		verify(webService, times(1)).discardPolicies(
				isA(EhrPolicyDiscardRq.class));
		verify(webService, times(1)).appendDocument(
				argThat(new AppendDocumentArgumentMatcher(
						authorMetadataClientDto, classCode, formatCode,
						typeCode, confidentialityCode, healthcareFacilityCode,
						practiceSettingCode, contentTypeCode)));
		assertEquals(xdsSrcSubmitRspMockDeprecate, actualResponse);
	}

	private class AppendDocumentArgumentMatcher extends
			ArgumentMatcher<XdsSrcApRpReq> {
		@SuppressWarnings("unused")
		AuthorMetadataClientDto authorMetadataClientDto = new AuthorMetadataClientDto();
		IheClassificationClientDto classCode;
		IheClassificationClientDto formatCode;
		IheClassificationClientDto typeCode;
		IheClassificationClientDto confidentialityCode;
		IheClassificationClientDto healthcareFacilityCode;
		IheClassificationClientDto practiceSettingCode;
		IheClassificationClientDto contentTypeCode;

		public AppendDocumentArgumentMatcher(
				AuthorMetadataClientDto authorMetadataClientDto,
				IheClassificationClientDto classCode,
				IheClassificationClientDto formatCode,
				IheClassificationClientDto typeCode,
				IheClassificationClientDto confidentialityCode,
				IheClassificationClientDto healthcareFacilityCode,
				IheClassificationClientDto practiceSettingCode,
				IheClassificationClientDto contentTypeCode) {
			super();
			this.authorMetadataClientDto = authorMetadataClientDto;
			this.classCode = classCode;
			this.formatCode = formatCode;
			this.typeCode = typeCode;
			this.confidentialityCode = confidentialityCode;
			this.healthcareFacilityCode = healthcareFacilityCode;
			this.practiceSettingCode = practiceSettingCode;
			this.contentTypeCode = contentTypeCode;
		}

		@Override
		public boolean matches(Object argument) {
			XdsSrcApRpReq r = (XdsSrcApRpReq) argument;
			DocumentClientDto d = r.getOldDocument();
			return d.getClassCode().getNodeRepresentation()
					.equals(classCode.getNodeRepresentation())
					&& d.getClassCode().getValue().equals(classCode.getValue())
					&& d.getFormatCode().getNodeRepresentation()
							.equals(formatCode.getNodeRepresentation())
					&& d.getTypeCode().getNodeRepresentation()
							.equals(typeCode.getNodeRepresentation())
					&& d.getConfidentialityCode()
							.getNodeRepresentation()
							.equals(confidentialityCode.getNodeRepresentation())
					&& d.getHealthcareFacilityCode()
							.getNodeRepresentation()
							.equals(healthcareFacilityCode
									.getNodeRepresentation())
					&& d.getPracticeSettingCode()
							.getNodeRepresentation()
							.equals(practiceSettingCode.getNodeRepresentation())
					&& r.getSrcSubmission().getSubmissionSet()
							.getContentTypeCode().getNodeRepresentation()
							.equals(contentTypeCode.getNodeRepresentation());
		}

	}

	@Test
	public void testDeleteDocument() throws EhrException_Exception,
			SpiritAdapterException {
		FolderClientDto folderMock = mock(FolderClientDto.class);
		DocumentClientDto documentMock = mock(DocumentClientDto.class);
		EhrPatientClientDto patientMock = mock(EhrPatientClientDto.class);
		final XdsSrcSubmitRsp responseMock = mock(XdsSrcSubmitRsp.class);

		when(webService.deleteDocument(any(XdsSrcDeleteReq.class))).thenReturn(
				responseMock);

		// Act
		XdsSrcSubmitRsp actualResponse = sat.deleteDocument(folderMock,
				documentMock, patientMock, stateId);

		// Assert
		verify(webService, times(1)).deleteDocument(any(XdsSrcDeleteReq.class));
		assertEquals(responseMock, actualResponse);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = EhrException_Exception.class)
	public void testDeleteDocument_Throws_EhrException_Exception()
			throws EhrException_Exception, SpiritAdapterException {
		FolderClientDto folderMock = mock(FolderClientDto.class);
		DocumentClientDto documentMock = mock(DocumentClientDto.class);
		EhrPatientClientDto patientMock = mock(EhrPatientClientDto.class);
		when(webService.deleteDocument(isA(XdsSrcDeleteReq.class))).thenThrow(
				EhrException_Exception.class);

		// Act
		sat.deleteDocument(folderMock, documentMock, patientMock, stateId);
	}

	@Test
	public void testDeleteDocuments() throws EhrException_Exception,
			SpiritAdapterException {
		// Arrange
		FolderClientDto folderMock = mock(FolderClientDto.class);
		EhrPatientClientDto patientMock = mock(EhrPatientClientDto.class);
		SpiritAdapterImpl spy = spy(sat);
		List<DocumentClientDto> documentList = new LinkedList<DocumentClientDto>();
		DocumentClientDto docMock1 = mock(DocumentClientDto.class);
		DocumentClientDto docMock2 = mock(DocumentClientDto.class);
		documentList.add(docMock1);
		documentList.add(docMock2);
		XdsSrcSubmitRsp responseMock1 = mock(XdsSrcSubmitRsp.class);
		when(responseMock1.getStateID()).thenReturn(stateId);
		XdsSrcSubmitRsp responseMock2 = mock(XdsSrcSubmitRsp.class);
		when(responseMock2.getStateID()).thenReturn(stateId);
		doReturn(responseMock1).when(spy).deleteDocument(folderMock, docMock1,
				patientMock, stateId);
		doReturn(responseMock2).when(spy).deleteDocument(folderMock, docMock2,
				patientMock, stateId);

		// Act
		String actualResponse = spy.deleteDocuments(folderMock, documentList,
				patientMock, stateId);

		// Assert
		//verify(spy, times(1)).logout(stateId);
		assertEquals("Success", actualResponse);
	}

	@Test
	public void testSubmitDocumentWithStateId() throws Exception {
		@SuppressWarnings("unused")
		XdsSrcSubmitReq xdsSrcSubmitReq = mock(XdsSrcSubmitReq.class);
		// PowerMockito.
		// whenNew(XdsSrcSubmitReq.class).withNoArguments().thenReturn(xdsSrcSubmitReq);

		EhrPatientClientDto submitForPatient = mock(EhrPatientClientDto.class);
		SourceSubmissionClientDto sourceSubmission = mock(SourceSubmissionClientDto.class);
		Boolean withFolder = false;
		String stateId = "MockedStateId";
		sat.submitDocument(submitForPatient, sourceSubmission, withFolder,
				stateId);

		// verify(client).submit(xdsSrcSubmitReq);
		verify(webService).submitDocument(any(XdsSrcSubmitReq.class));
	}

	@Test
	public void testSubmitDocumentWithoutStateId() throws Exception {
		SpiritAdapterImpl satSpy = spy(sat);
		EhrPatientClientDto submitForPatient = mock(EhrPatientClientDto.class);
		SourceSubmissionClientDto sourceSubmission = mock(SourceSubmissionClientDto.class);
		Boolean withFolder = false;
		String stateId = "MockedStateId";
		doReturn(stateId).when(satSpy).login();
		satSpy.submitDocument(submitForPatient, sourceSubmission, withFolder);
		verify(satSpy).submitDocument(submitForPatient, sourceSubmission,
				withFolder, stateId);
		verify(satSpy).logout("MockedStateId");
	}

	@Test
	public void testQueryPatientContent() throws Exception {
		EhrPatientClientDto patient = mock(EhrPatientClientDto.class);
		SpiritAdapterImpl satSpy = spy(sat);
		String stateId = "MockedStateId";
		doReturn(stateId).when(satSpy).login();

		// EhrXdsQGetAllRq requestAllRq=mock(EhrXdsQGetAllRq.class);
		// whenNew(EhrXdsQGetAllRq.class).withNoArguments().thenReturn(requestAllRq);

		EhrXdsQRsp response = mock(EhrXdsQRsp.class);
		PatientContentClientDto patientClientDto = mock(PatientContentClientDto.class);
		doReturn(patientClientDto).when(response).getResponseData();
		doReturn(response).when(webService).queryPatientContent(
				any(EhrXdsQGetAllRq.class));
		satSpy.queryPatientContent(patient, stateId);
		verify(webService).queryPatientContent(any(EhrXdsQGetAllRq.class));
	}

	@Test
	public void testRetrieveDocument() throws Exception {
		DocumentClientDto wantedDocument = mock(DocumentClientDto.class);
		SpiritAdapterImpl satSpy = spy(sat);
		String stateId = "MockedStateId";
		doReturn(stateId).when(satSpy).login();

		satSpy.retrieveDocument(wantedDocument);
		verify(webService).retrieveDocument(any(EhrXdsRetrReq.class));
		verify(satSpy).logout("MockedStateId");
	}

	@Test
	public void testUpdateDocument() throws Exception {
		XdsSrcUpdateReq xdsSrcUpdateRequest = mock(XdsSrcUpdateReq.class);
		SpiritAdapterImpl satSpy = spy(sat);
		String stateId = "MockedStateId";
		doReturn(stateId).when(satSpy).login();

		XdsSrcSubmitRsp response = mock(XdsSrcSubmitRsp.class);
		EhrPatientClientDto ehrPatientClientDto = mock(EhrPatientClientDto.class);
		doReturn(ehrPatientClientDto).when(response).getResponseData();
		doReturn(response).when(webService).updateDocument(xdsSrcUpdateRequest);

		satSpy.updateDocument(xdsSrcUpdateRequest);
		verify(webService).updateDocument(xdsSrcUpdateRequest);
		verify(satSpy).logout("MockedStateId");
	}

	// @Test
	// public void testGeneratePolicyMetadata() {
	// String document="XML Document";
	// String homeCommunityId="domainId";
	// doReturn("metadata").when(policyMetadataGenerator).generateMetadataXml(document,
	// homeCommunityId, null, null);
	// sat.generatePolicyMetadata("c32 document");
	// }

	//@Test
	public void testDeprecateDocument() throws SpiritAdapterException,
			EhrException_Exception {
		String documentUniqueId = "documentUniqueId";
		String patientId = "patientId";
		String stateId = "stateId";

		SpiritAdapterImpl satSpy = spy(sat);
		EhrPatientClientListDto ehrPatientClientListDto = mock(EhrPatientClientListDto.class);
		doReturn(ehrPatientClientListDto).when(satSpy).queryPatientsWithPids(
				patientId,stateId);
		doReturn(stateId).when(ehrPatientClientListDto).getStateId();
		EhrPatientClientDto ehrPatientClientDto = mock(EhrPatientClientDto.class);
		List<EhrPatientClientDto> ehrPatientClientDtos = new ArrayList<EhrPatientClientDto>();
		ehrPatientClientDtos.add(ehrPatientClientDto);
		doReturn(ehrPatientClientDtos).when(ehrPatientClientListDto)
				.getEhrPatientClientListDto();

		List<DocumentClientDto> documents = new ArrayList<DocumentClientDto>();
		DocumentClientDto documentClientDto = mock(DocumentClientDto.class);
		doReturn(documentUniqueId).when(documentClientDto).getUniqueId();
		documents.add(documentClientDto);

		PatientContentClientDto patientContentClientDto = mock(PatientContentClientDto.class);
		EhrXdsQRsp ehrXdsQRsp = mock(EhrXdsQRsp.class);
		doReturn(patientContentClientDto).when(ehrXdsQRsp).getResponseData();
		doReturn(ehrXdsQRsp).when(satSpy).queryPatientContent(
				any(EhrPatientClientDto.class),stateId);

		DocumentClientDto documentToDeprecate = mock(DocumentClientDto.class);
		doReturn(documentToDeprecate).when(satSpy).makeDocumentClientDto();
		SubmissionSetClientDto submissionSetClientDto = mock(SubmissionSetClientDto.class);
		doReturn(submissionSetClientDto).when(documentToDeprecate)
				.getSubmissionSet();

		satSpy.deprecateDocument(documentUniqueId, patientId, stateId);
		verify(webService).deprecateDocument(any(XdsSrcDeprecateReq.class));

	}

	//@Test
	public void testDeprecateDocumentsWithPatientIds()
			throws SpiritAdapterException {
		String documentUniqueId1 = "documentUniqueId1";
		String documentUniqueId2 = "documentUniqueId2";
		String patientId = "patientId";
		String locPpatientId = "locPatientId";
		String stateId = "stateId";

		List<String> documentUniqueIdList = new ArrayList<String>();
		documentUniqueIdList.add(documentUniqueId1);
		documentUniqueIdList.add(documentUniqueId2);

		SpiritAdapterImpl satSpy = spy(sat);
		EhrPatientClientListDto ehrPatientClientListDto = mock(EhrPatientClientListDto.class);
		doReturn(ehrPatientClientListDto).when(satSpy).queryPatientsWithPids(
				patientId, stateId);

		EhrPatientClientDto patient = mock(EhrPatientClientDto.class);

		List<EhrPatientClientDto> ehrPatientClientDtos = new ArrayList<EhrPatientClientDto>();
		ehrPatientClientDtos.add(patient);
		//doReturn(stateId).when(ehrPatientClientListDto.getStateId());
		doReturn(ehrPatientClientDtos).when(ehrPatientClientListDto)
				.getEhrPatientClientListDto();

		EhrXdsQRsp ehrXdsQRsp = mock(EhrXdsQRsp.class);
		doReturn(ehrXdsQRsp).when(satSpy).queryPatientContent(patient,stateId);

		PatientContentClientDto patientContentClientDto = mock(PatientContentClientDto.class);
		doReturn(patientContentClientDto).when(ehrXdsQRsp).getResponseData();
		doReturn(stateId).when(ehrXdsQRsp).getStateID();

		satSpy.deprecateDocuments(documentUniqueIdList, patientId, stateId);

		verify(satSpy).deprecateDocuments(documentUniqueIdList, patientId,
				stateId);
	}

	@Test
	public void testDeprecateDocumentsWithEhrPatientClientDto()
			throws SpiritAdapterException, EhrException_Exception {
		List<String> documentUniqueIdList = new ArrayList<String>();
		documentUniqueIdList.add("UniqueId1");
		documentUniqueIdList.add("UniqueId2");
		EhrPatientClientDto patient = mock(EhrPatientClientDto.class);
		PatientContentClientDto patientContentClientDto = mock(PatientContentClientDto.class);
		String stateId = "stateId";

		List<DocumentClientDto> documents = new ArrayList<DocumentClientDto>();
		DocumentClientDto documentClientDto = mock(DocumentClientDto.class);
		doReturn("UniqueId1").when(documentClientDto).getUniqueId();
		documents.add(documentClientDto);
		doReturn(documents).when(patientContentClientDto).getDocuments();

		XdsSrcSubmitRsp response = mock(XdsSrcSubmitRsp.class);
		doReturn(stateId).when(response).getStateID();
		doReturn(response).when(webService).deprecateDocument(
				any(XdsSrcDeprecateReq.class));

		sat.deprecateDocuments(documentUniqueIdList, patient,
				patientContentClientDto, stateId);

		verify(webService).deprecateDocument(any(XdsSrcDeprecateReq.class));

	}

	@Test
	public void testSubmitPolicy() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		byte[] policy = { 0x01 };
		List<byte[]> policyListMock = Arrays.asList(policy);
		EhrPolicySubmitRsp expectedResponse = mock(EhrPolicySubmitRsp.class);
		when(webService.submitPolicies(any(EhrPolicySubmitRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicySubmitRsp actualResponse = sat.submitPolicy(policy);

		// Assert
		verify(webService, times(1)).submitPolicies(
				argThat(new SubmitPoliciesArgumentMatcher(policyListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testSubmitPolicy_Throws_IllegalArgumentException()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		byte[] policy = { 0x00 };
		EhrPolicySubmitRsp expectedResponse = mock(EhrPolicySubmitRsp.class);
		when(webService.submitPolicies(any(EhrPolicySubmitRq.class)))
				.thenReturn(expectedResponse);
		thrown.expect(IllegalArgumentException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicySubmitRsp actualResponse = sat.submitPolicy(policy);
	}

	@Test
	public void testSubmitPolicy_Throws_IllegalArgumentException2()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		byte[] policy = new byte[0];
		EhrPolicySubmitRsp expectedResponse = mock(EhrPolicySubmitRsp.class);
		when(webService.submitPolicies(any(EhrPolicySubmitRq.class)))
				.thenReturn(expectedResponse);
		thrown.expect(IllegalArgumentException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicySubmitRsp actualResponse = sat.submitPolicy(policy);
	}

	@Test
	public void testSubmitPolicies() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		List<byte[]> policyListMock = Arrays.asList(new byte[1]);
		EhrPolicySubmitRsp expectedResponse = mock(EhrPolicySubmitRsp.class);
		when(webService.submitPolicies(any(EhrPolicySubmitRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicySubmitRsp actualResponse = sat.submitPolicies(policyListMock);

		// Assert
		verify(webService, times(1)).submitPolicies(
				argThat(new SubmitPoliciesArgumentMatcher(policyListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSubmitPolicies_Throws_SpiritAdapterException()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		List<byte[]> policyListMock = Arrays.asList(new byte[1]);
		when(webService.submitPolicies(any(EhrPolicySubmitRq.class)))
				.thenThrow(EhrException_Exception.class);
		thrown.expect(SpiritAdapterException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicySubmitRsp actualResponse = sat.submitPolicies(policyListMock);
	}

	@Test
	public void testSubmitOrUpdatePolicy() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		byte[] policy = { 0x01 };
		List<byte[]> policyListMock = Arrays.asList(policy);
		EhrPolicySubmitOrUpdateRsp expectedResponse = mock(EhrPolicySubmitOrUpdateRsp.class);
		when(
				webService
						.submitOrUpdatePolicies(any(EhrPolicySubmitOrUpdateRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicySubmitOrUpdateRsp actualResponse = sat.submitOrUpdatePolicy(
				policy, stateId);

		// Assert
		verify(webService, times(1)).submitOrUpdatePolicies(
				argThat(new SubmitOrUpdatePoliciesArgumentMatcher(
						policyListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testSubmitOrUpdatePolicy_Throws_IllegalArgumentException()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		byte[] policy = { 0x00 };
		EhrPolicySubmitOrUpdateRsp expectedResponse = mock(EhrPolicySubmitOrUpdateRsp.class);
		when(
				webService
						.submitOrUpdatePolicies(any(EhrPolicySubmitOrUpdateRq.class)))
				.thenReturn(expectedResponse);
		thrown.expect(IllegalArgumentException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicySubmitOrUpdateRsp actualResponse = sat.submitOrUpdatePolicy(
				policy, stateId);
	}

	@Test
	public void testSubmitOrUpdatePolicy_Throws_IllegalArgumentException2()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		byte[] policy = new byte[0];
		EhrPolicySubmitOrUpdateRsp expectedResponse = mock(EhrPolicySubmitOrUpdateRsp.class);
		when(
				webService
						.submitOrUpdatePolicies(any(EhrPolicySubmitOrUpdateRq.class)))
				.thenReturn(expectedResponse);
		thrown.expect(IllegalArgumentException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicySubmitOrUpdateRsp actualResponse = sat.submitOrUpdatePolicy(
				policy, stateId);
	}

	@Test
	public void testSubmitOrUpdatePolicies() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		List<byte[]> policyListMock = Arrays.asList(new byte[1]);
		EhrPolicySubmitOrUpdateRsp expectedResponse = mock(EhrPolicySubmitOrUpdateRsp.class);
		when(
				webService
						.submitOrUpdatePolicies(any(EhrPolicySubmitOrUpdateRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicySubmitOrUpdateRsp actualResponse = sat.submitOrUpdatePolicies(
				policyListMock, stateId);

		// Assert
		verify(webService, times(1)).submitOrUpdatePolicies(
				argThat(new SubmitOrUpdatePoliciesArgumentMatcher(
						policyListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSubmitOrUpdatePolicies_Throws_SpiritAdapterException()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		List<byte[]> policyListMock = Arrays.asList(new byte[1]);
		when(
				webService
						.submitOrUpdatePolicies(any(EhrPolicySubmitOrUpdateRq.class)))
				.thenThrow(EhrException_Exception.class);
		thrown.expect(SpiritAdapterException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicySubmitOrUpdateRsp actualResponse = sat.submitOrUpdatePolicies(
				policyListMock, stateId);
	}

	@Test
	public void testUpdatePolicy() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		byte[] policy = { 0x01 };
		List<byte[]> policyListMock = Arrays.asList(policy);
		EhrPolicyUpdateRsp expectedResponse = mock(EhrPolicyUpdateRsp.class);
		when(webService.updatePolicies(any(EhrPolicyUpdateRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicyUpdateRsp actualResponse = sat.updatePolicy(policy);

		// Assert
		verify(webService, times(1)).updatePolicies(
				argThat(new UpdatePoliciesArgumentMatcher(policyListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testUpdatePolicy_Throws_IllegalArgumentException()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		byte[] policy = { 0x00 };
		EhrPolicyUpdateRsp expectedResponse = mock(EhrPolicyUpdateRsp.class);
		when(webService.updatePolicies(any(EhrPolicyUpdateRq.class)))
				.thenReturn(expectedResponse);
		thrown.expect(IllegalArgumentException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicyUpdateRsp actualResponse = sat.updatePolicy(policy);
	}

	@Test
	public void testUpdatePolicy_Throws_IllegalArgumentException2()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		byte[] policy = new byte[0];
		EhrPolicyUpdateRsp expectedResponse = mock(EhrPolicyUpdateRsp.class);
		when(webService.updatePolicies(any(EhrPolicyUpdateRq.class)))
				.thenReturn(expectedResponse);
		thrown.expect(IllegalArgumentException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicyUpdateRsp actualResponse = sat.updatePolicy(policy);
	}

	@Test
	public void testUpdatePolicies() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		List<byte[]> policyListMock = Arrays.asList(new byte[1]);
		EhrPolicyUpdateRsp expectedResponse = mock(EhrPolicyUpdateRsp.class);
		when(webService.updatePolicies(any(EhrPolicyUpdateRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicyUpdateRsp actualResponse = sat.updatePolicies(policyListMock);

		// Assert
		verify(webService, times(1)).updatePolicies(
				argThat(new UpdatePoliciesArgumentMatcher(policyListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdatePolicies_Throws_SpiritAdapterException()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		List<byte[]> policyListMock = Arrays.asList(new byte[1]);
		when(webService.updatePolicies(any(EhrPolicyUpdateRq.class)))
				.thenThrow(EhrException_Exception.class);
		thrown.expect(SpiritAdapterException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicyUpdateRsp actualResponse = sat.updatePolicies(policyListMock);
	}

	@Test
	public void testRetrievePolicy() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		final String policyIdMock = "policyIdMock";
		final byte[] policyMock1 = "policyMock1".getBytes();
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		List<byte[]> policyListMock = Arrays.asList(policyMock1);
		EhrPolicyRetrieveRsp expectedResponse = mock(EhrPolicyRetrieveRsp.class);
		when(expectedResponse.getPolicyList()).thenReturn(policyListMock);
		when(webService.retrievePolicies(any(EhrPolicyRetrieveRq.class)))
				.thenReturn(expectedResponse);

		// Act
		byte[] actualResponse = sat.retrievePolicy(policyIdMock);

		// Assert
		verify(webService, times(1)).retrievePolicies(
				argThat(new RetrievePoliciesArgumentMatcher(null,
						policyIdListMock)));
		assertTrue(policyListMock.contains(actualResponse));
	}

	@Test
	public void testRetrievePolicies() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		final String policySetIdMock = "policySetIdMock";
		final String policyIdMock = "policyIdMock";
		final byte[] policyMock1 = "policyMock1".getBytes();
		final byte[] policyMock2 = "policyMock2".getBytes();
		List<String> policySetIdListMock = Arrays.asList(policySetIdMock);
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		List<byte[]> policyListMock = Arrays.asList(policyMock1, policyMock2);
		EhrPolicyRetrieveRsp expectedResponse = mock(EhrPolicyRetrieveRsp.class);
		when(expectedResponse.getPolicyList()).thenReturn(policyListMock);
		when(webService.retrievePolicies(any(EhrPolicyRetrieveRq.class)))
				.thenReturn(expectedResponse);

		// Act
		List<byte[]> actualResponse = sat.retrievePolicies(policySetIdListMock,
				policyIdListMock);

		// Assert
		verify(webService, times(1)).retrievePolicies(
				argThat(new RetrievePoliciesArgumentMatcher(
						policySetIdListMock, policyIdListMock)));
		assertTrue(actualResponse.containsAll(policyListMock));
	}

	@Test
	public void testRetrievePolicies_When_PolicySetIdList_Is_Null()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final String policySetIdMock = "policySetIdMock";
		final String policyIdMock = "policyIdMock";
		final byte[] policyMock1 = "policyMock1".getBytes();
		final byte[] policyMock2 = "policyMock2".getBytes();
		List<String> policySetIdListMock = Arrays.asList(policySetIdMock);
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		List<byte[]> policyListMock = Arrays.asList(policyMock1, policyMock2);
		EhrPolicyRetrieveRsp expectedResponse = mock(EhrPolicyRetrieveRsp.class);
		when(expectedResponse.getPolicyList()).thenReturn(policyListMock);
		when(webService.retrievePolicies(any(EhrPolicyRetrieveRq.class)))
				.thenReturn(expectedResponse);

		// Act
		List<byte[]> actualResponse = sat.retrievePolicies(null,
				policyIdListMock);

		// Assert
		verify(webService, times(1)).retrievePolicies(
				argThat(new RetrievePoliciesArgumentMatcher(
						policySetIdListMock, policyIdListMock)));
		assertTrue(actualResponse.containsAll(policyListMock));
	}

	@Test
	public void testRetrievePolicies_When_PolicySetIdList_Is_Empty()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final String policyIdMock = "policyIdMock";
		final byte[] policyMock1 = "policyMock1".getBytes();
		final byte[] policyMock2 = "policyMock2".getBytes();
		List<String> policySetIdListMock = Arrays.asList();
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		List<byte[]> policyListMock = Arrays.asList(policyMock1, policyMock2);
		EhrPolicyRetrieveRsp expectedResponse = mock(EhrPolicyRetrieveRsp.class);
		when(expectedResponse.getPolicyList()).thenReturn(policyListMock);
		when(webService.retrievePolicies(any(EhrPolicyRetrieveRq.class)))
				.thenReturn(expectedResponse);

		// Act
		List<byte[]> actualResponse = sat.retrievePolicies(policySetIdListMock,
				policyIdListMock);

		// Assert
		verify(webService, times(1)).retrievePolicies(
				argThat(new RetrievePoliciesArgumentMatcher(
						policySetIdListMock, policyIdListMock)));
		assertTrue(actualResponse.containsAll(policyListMock));
	}

	@Test
	public void testRetrievePolicies_When_PolicyIdList_Is_Null()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final String policySetIdMock = "policySetIdMock";
		final String policyIdMock = "policyIdMock";
		final byte[] policyMock1 = "policyMock1".getBytes();
		final byte[] policyMock2 = "policyMock2".getBytes();
		List<String> policySetIdListMock = Arrays.asList(policySetIdMock);
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		List<byte[]> policyListMock = Arrays.asList(policyMock1, policyMock2);
		EhrPolicyRetrieveRsp expectedResponse = mock(EhrPolicyRetrieveRsp.class);
		when(expectedResponse.getPolicyList()).thenReturn(policyListMock);
		when(webService.retrievePolicies(any(EhrPolicyRetrieveRq.class)))
				.thenReturn(expectedResponse);

		// Act
		List<byte[]> actualResponse = sat.retrievePolicies(policySetIdListMock,
				null);

		// Assert
		verify(webService, times(1)).retrievePolicies(
				argThat(new RetrievePoliciesArgumentMatcher(
						policySetIdListMock, policyIdListMock)));
		assertTrue(actualResponse.containsAll(policyListMock));
	}

	@Test
	public void testRetrievePolicies_When_PolicyIdList_Is_Empty()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final String policySetIdMock = "policySetIdMock";
		final byte[] policyMock1 = "policyMock1".getBytes();
		final byte[] policyMock2 = "policyMock2".getBytes();
		List<String> policySetIdListMock = Arrays.asList(policySetIdMock);
		List<String> policyIdListMock = Arrays.asList();
		List<byte[]> policyListMock = Arrays.asList(policyMock1, policyMock2);
		EhrPolicyRetrieveRsp expectedResponse = mock(EhrPolicyRetrieveRsp.class);
		when(expectedResponse.getPolicyList()).thenReturn(policyListMock);
		when(webService.retrievePolicies(any(EhrPolicyRetrieveRq.class)))
				.thenReturn(expectedResponse);

		// Act
		List<byte[]> actualResponse = sat.retrievePolicies(policySetIdListMock,
				policyIdListMock);

		// Assert
		verify(webService, times(1)).retrievePolicies(
				argThat(new RetrievePoliciesArgumentMatcher(
						policySetIdListMock, policyIdListMock)));
		assertTrue(actualResponse.containsAll(policyListMock));
	}

	@Test
	public void testRetrievePolicies_When_PolicySetIdList_And_PolicyIdList_Is_Null()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final byte[] policyMock1 = "policyMock1".getBytes();
		final byte[] policyMock2 = "policyMock2".getBytes();
		List<byte[]> policyListMock = Arrays.asList(policyMock1, policyMock2);
		EhrPolicyRetrieveRsp expectedResponse = mock(EhrPolicyRetrieveRsp.class);
		when(expectedResponse.getPolicyList()).thenReturn(policyListMock);
		when(webService.retrievePolicies(any(EhrPolicyRetrieveRq.class)))
				.thenReturn(expectedResponse);
		thrown.expect(IllegalArgumentException.class);

		// Act
		@SuppressWarnings("unused")
		List<byte[]> actualResponse = sat.retrievePolicies(null, null);
	}

	@Test
	public void testRetrievePolicies_When_PolicySetIdList_And_PolicyIdList_Is_Empty()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final byte[] policyMock1 = "policyMock1".getBytes();
		final byte[] policyMock2 = "policyMock2".getBytes();
		List<String> policySetIdListMock = Arrays.asList();
		List<String> policyIdListMock = Arrays.asList();
		List<byte[]> policyListMock = Arrays.asList(policyMock1, policyMock2);
		EhrPolicyRetrieveRsp expectedResponse = mock(EhrPolicyRetrieveRsp.class);
		when(expectedResponse.getPolicyList()).thenReturn(policyListMock);
		when(webService.retrievePolicies(any(EhrPolicyRetrieveRq.class)))
				.thenReturn(expectedResponse);
		thrown.expect(IllegalArgumentException.class);

		// Act
		@SuppressWarnings("unused")
		List<byte[]> actualResponse = sat.retrievePolicies(policySetIdListMock,
				policyIdListMock);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRetrievePolicies_Throws_SpiritAdapterException()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final String policySetIdMock = "policySetIdMock";
		final String policyIdMock = "policyIdMock";
		final byte[] policyMock1 = "policyMock1".getBytes();
		final byte[] policyMock2 = "policyMock2".getBytes();
		List<String> policySetIdListMock = Arrays.asList(policySetIdMock);
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		List<byte[]> policyListMock = Arrays.asList(policyMock1, policyMock2);
		EhrPolicyRetrieveRsp expectedResponse = mock(EhrPolicyRetrieveRsp.class);
		when(expectedResponse.getPolicyList()).thenReturn(policyListMock);
		when(webService.retrievePolicies(any(EhrPolicyRetrieveRq.class)))
				.thenThrow(EhrException_Exception.class);
		thrown.expect(SpiritAdapterException.class);

		// Act
		@SuppressWarnings("unused")
		List<byte[]> actualResponse = sat.retrievePolicies(policySetIdListMock,
				policyIdListMock);
	}

	public void testDiscardPolicy() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		final String policyIdMock = "policyIdMock";
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		EhrPolicyDiscardRsp expectedResponse = mock(EhrPolicyDiscardRsp.class);
		when(webService.discardPolicies(any(EhrPolicyDiscardRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicyDiscardRsp actualResponse = sat.discardPolicy(policyIdMock,
				stateId);

		// Assert
		verify(webService, times(1)).discardPolicies(
				argThat(new DiscardPoliciesArgumentMatcher(null,
						policyIdListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testDiscardPolicies() throws SpiritAdapterException,
			EhrException_Exception {
		// Arrange
		final String policySetIdMock = "policySetIdMock";
		final String policyIdMock = "policyIdMock";
		List<String> policySetIdListMock = Arrays.asList(policySetIdMock);
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		EhrPolicyDiscardRsp expectedResponse = mock(EhrPolicyDiscardRsp.class);
		when(webService.discardPolicies(any(EhrPolicyDiscardRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicyDiscardRsp actualResponse = sat.discardPolicies(
				policySetIdListMock, policyIdListMock, stateId);

		// Assert
		verify(webService, times(1)).discardPolicies(
				argThat(new DiscardPoliciesArgumentMatcher(policySetIdListMock,
						policyIdListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testDiscardPolicies_When_PolicySetIdList_Is_Null()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final String policySetIdMock = "policySetIdMock";
		final String policyIdMock = "policyIdMock";
		List<String> policySetIdListMock = Arrays.asList(policySetIdMock);
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		EhrPolicyDiscardRsp expectedResponse = mock(EhrPolicyDiscardRsp.class);
		when(webService.discardPolicies(any(EhrPolicyDiscardRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicyDiscardRsp actualResponse = sat.discardPolicies(null,
				policyIdListMock, stateId);

		// Assert
		verify(webService, times(1)).discardPolicies(
				argThat(new DiscardPoliciesArgumentMatcher(policySetIdListMock,
						policyIdListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testDiscardPolicies_When_PolicyIdList_Is_Null()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final String policySetIdMock = "policySetIdMock";
		final String policyIdMock = "policyIdMock";
		List<String> policySetIdListMock = Arrays.asList(policySetIdMock);
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		EhrPolicyDiscardRsp expectedResponse = mock(EhrPolicyDiscardRsp.class);
		when(webService.discardPolicies(any(EhrPolicyDiscardRq.class)))
				.thenReturn(expectedResponse);

		// Act
		EhrPolicyDiscardRsp actualResponse = sat.discardPolicies(
				policySetIdListMock, null, stateId);

		// Assert
		verify(webService, times(1)).discardPolicies(
				argThat(new DiscardPoliciesArgumentMatcher(policySetIdListMock,
						policyIdListMock)));
		assertEquals(expectedResponse, actualResponse);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDiscardPolicies_Throws_SpiritAdapterException()
			throws SpiritAdapterException, EhrException_Exception {
		// Arrange
		final String policySetIdMock = "policySetIdMock";
		final String policyIdMock = "policyIdMock";
		List<String> policySetIdListMock = Arrays.asList(policySetIdMock);
		List<String> policyIdListMock = Arrays.asList(policyIdMock);
		when(webService.discardPolicies(any(EhrPolicyDiscardRq.class)))
				.thenThrow(EhrException_Exception.class);
		thrown.expect(SpiritAdapterException.class);

		// Act
		@SuppressWarnings("unused")
		EhrPolicyDiscardRsp actualResponse = sat.discardPolicies(
				policySetIdListMock, policyIdListMock, stateId);
	}

	private class DiscardPoliciesArgumentMatcher extends
			ArgumentMatcher<EhrPolicyDiscardRq> {
		private List<String> policySetList;
		private List<String> policyList;

		public DiscardPoliciesArgumentMatcher(List<String> policySetList,
				List<String> policyList) {
			this.policySetList = policySetList;
			this.policyList = policyList;
		}

		@Override
		public boolean matches(Object argument) {
			EhrPolicyDiscardRq p = (EhrPolicyDiscardRq) argument;
			if (policySetList != null)
				return policyList.containsAll(p.getPolicyIds())
						&& policySetList.containsAll(p.getPolicySetIds());
			else
				return policyList.containsAll(p.getPolicyIds());
		}
	}

	private class RetrievePoliciesArgumentMatcher extends
			ArgumentMatcher<EhrPolicyRetrieveRq> {
		private List<String> policySetList;
		private List<String> policyList;

		public RetrievePoliciesArgumentMatcher(List<String> policySetList,
				List<String> policyList) {
			this.policySetList = policySetList;
			this.policyList = policyList;
		}

		@Override
		public boolean matches(Object argument) {
			EhrPolicyRetrieveRq p = (EhrPolicyRetrieveRq) argument;
			if (policySetList != null)
				return policyList.containsAll(p.getPolicyIds())
						&& policySetList.containsAll(p.getPolicySetIds());
			else
				return policyList.containsAll(p.getPolicyIds());
		}
	}

	private class UpdatePoliciesArgumentMatcher extends
			ArgumentMatcher<EhrPolicyUpdateRq> {
		private List<byte[]> policyList;

		public UpdatePoliciesArgumentMatcher(List<byte[]> policyList) {
			this.policyList = policyList;
		}

		@Override
		public boolean matches(Object argument) {
			EhrPolicyUpdateRq p = (EhrPolicyUpdateRq) argument;
			return policyList.containsAll(p.getPolicyList());
		}
	}

	private class SubmitOrUpdatePoliciesArgumentMatcher extends
			ArgumentMatcher<EhrPolicySubmitOrUpdateRq> {
		private List<byte[]> policyList;

		public SubmitOrUpdatePoliciesArgumentMatcher(List<byte[]> policyList) {
			this.policyList = policyList;
		}

		@Override
		public boolean matches(Object argument) {
			EhrPolicySubmitOrUpdateRq p = (EhrPolicySubmitOrUpdateRq) argument;
			return policyList.containsAll(p.getPolicyList());
		}
	}

	private class SubmitPoliciesArgumentMatcher extends
			ArgumentMatcher<EhrPolicySubmitRq> {
		private List<byte[]> policyList;

		public SubmitPoliciesArgumentMatcher(List<byte[]> policyList) {
			this.policyList = policyList;
		}

		@Override
		public boolean matches(Object argument) {
			EhrPolicySubmitRq p = (EhrPolicySubmitRq) argument;
			return policyList.containsAll(p.getPolicyList());
		}
	}
}
