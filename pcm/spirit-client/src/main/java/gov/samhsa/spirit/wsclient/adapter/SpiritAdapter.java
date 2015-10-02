package gov.samhsa.spirit.wsclient.adapter;

import gov.samhsa.spirit.wsclient.dto.EhrPatientClientListDto;
import gov.samhsa.spirit.wsclient.dto.PatientDto;
import gov.samhsa.spirit.wsclient.exception.SpiritAdapterException;

import java.util.List;

import com.spirit.ehr.ws.client.generated.DocumentClientDto;
import com.spirit.ehr.ws.client.generated.EhrException_Exception;
import com.spirit.ehr.ws.client.generated.EhrPatientClientDto;
import com.spirit.ehr.ws.client.generated.EhrPolicyDiscardRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitOrUpdateRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicyUpdateRsp;
import com.spirit.ehr.ws.client.generated.EhrXdsQRsp;
import com.spirit.ehr.ws.client.generated.EhrXdsRetrRsp;
import com.spirit.ehr.ws.client.generated.FolderClientDto;
import com.spirit.ehr.ws.client.generated.PatientContentClientDto;
import com.spirit.ehr.ws.client.generated.SourceSubmissionClientDto;
import com.spirit.ehr.ws.client.generated.SpiritUserResponse;
import com.spirit.ehr.ws.client.generated.XdsSrcSubmitRsp;
import com.spirit.ehr.ws.client.generated.XdsSrcUpdateReq;

public interface SpiritAdapter {
	
	//customized(wrapped) methods
	public SpiritUserResponse usrOrgRoleLogin() throws SpiritAdapterException ;
	
	public String login() throws SpiritAdapterException;
	
	public void logout(String stateId) throws SpiritAdapterException;
	
	public PatientDto createPatientByPDQ(PatientDto patientDto) throws SpiritAdapterException;	
	EhrPatientClientListDto queryPatientsWithPids(String patientId, String stateId) throws SpiritAdapterException;

	public PatientDto updatePatientByLocId(PatientDto patientDto) throws SpiritAdapterException;
	
	public XdsSrcSubmitRsp submitDocument(EhrPatientClientDto submitForPatient, SourceSubmissionClientDto sourceSubmission, Boolean withFolder) throws SpiritAdapterException;

	EhrXdsRetrRsp retrieveDocument(DocumentClientDto wantedDocument)
			throws SpiritAdapterException;

	public EhrXdsQRsp queryPatientContent(EhrPatientClientDto patient, String stateId)
			throws SpiritAdapterException;

	EhrPatientClientDto updateDocument(XdsSrcUpdateReq xdsSrcUpdateRequest)
			throws SpiritAdapterException;

	public SourceSubmissionClientDto generatePolicyMetadata(String document);

	XdsSrcSubmitRsp submitDocument(EhrPatientClientDto submitForPatient,
			SourceSubmissionClientDto sourceSubmission, Boolean withFolder,
			String stateId) throws SpiritAdapterException;

	DocumentClientDto queryDocumentsByUid(String patientId,
			String documentUniqueId,String stateId) throws SpiritAdapterException;

	XdsSrcSubmitRsp deprecateDocument(String documentUniqueId, String patientId,
			String stateId) throws SpiritAdapterException;
	
	XdsSrcSubmitRsp deprecatePolicy(String documentUniqueId, String patientId, byte[] revokedPdfConsent)
			throws SpiritAdapterException;
	
	public XdsSrcSubmitRsp submitSignedConsent(byte[] xacmlPolicy, byte[] signedConsentPdf, byte[] pdfConsentFromXacml, byte[] pdfConsentToXacml, String xacmlPolicyId, String patientId, String country) throws SpiritAdapterException;

	XdsSrcSubmitRsp deleteDocument(FolderClientDto folder,
			DocumentClientDto document, EhrPatientClientDto patient,
			String stateId) throws EhrException_Exception,
			SpiritAdapterException;

	String deleteDocuments(FolderClientDto folder,
			List<DocumentClientDto> document, EhrPatientClientDto patient, String stateId) throws EhrException_Exception, SpiritAdapterException;

	XdsSrcSubmitRsp deprecateDocuments(List<String> documentUniqueIdList,
			String patientId, String stateId) throws SpiritAdapterException;

	XdsSrcSubmitRsp deprecateDocuments(List<String> documentUniqueIdList,
			EhrPatientClientDto patient,
			PatientContentClientDto patientContentClientDto, String stateId)
			throws SpiritAdapterException;
	
	public XdsSrcSubmitRsp submitC32(byte[] c32file) throws SpiritAdapterException;
	public SourceSubmissionClientDto generateC32Metadata(String document) ;
	
	// Policy repository operations
	public EhrPolicySubmitRsp submitPolicy(byte[] policy) throws SpiritAdapterException;
	public EhrPolicySubmitRsp submitPolicies(List<byte[]> policyList) throws SpiritAdapterException;
	public EhrPolicySubmitOrUpdateRsp submitOrUpdatePolicy(byte[] policy, String stateId) throws SpiritAdapterException;
	public EhrPolicySubmitOrUpdateRsp submitOrUpdatePolicies(List<byte[]> policyList, String stateId) throws SpiritAdapterException;
	public EhrPolicyUpdateRsp updatePolicy(byte[] policy) throws SpiritAdapterException;
	public EhrPolicyUpdateRsp updatePolicies(List<byte[]> policyList) throws SpiritAdapterException;
	public byte[] retrievePolicy(String policyId) throws SpiritAdapterException;
	public List<byte[]> retrievePolicies(List<String> listPolicySetIds, List<String> listPolicyIds) throws SpiritAdapterException;
	public EhrPolicyDiscardRsp discardPolicy(String policyId, String stateId) throws SpiritAdapterException;
	public EhrPolicyDiscardRsp discardPolicies(List<String> listPolicySetIds, List<String> listPolicyIds, String stateId) throws SpiritAdapterException;

}
