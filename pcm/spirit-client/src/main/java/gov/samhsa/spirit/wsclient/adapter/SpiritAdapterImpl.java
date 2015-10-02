package gov.samhsa.spirit.wsclient.adapter;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGenerator;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorImpl;
import gov.samhsa.spirit.wsclient.dto.EhrPatientClientListDto;
import gov.samhsa.spirit.wsclient.dto.PatientDto;
import gov.samhsa.spirit.wsclient.exception.SpiritAdapterException;
import gov.samhsa.spirit.wsclient.util.SpiritClientHelper;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.spirit.ehr.ws.client.generated.DocumentClientDto;
import com.spirit.ehr.ws.client.generated.EhrDomainClientDto;
import com.spirit.ehr.ws.client.generated.EhrException_Exception;
import com.spirit.ehr.ws.client.generated.EhrPIDClientDto;
import com.spirit.ehr.ws.client.generated.EhrPapCfgRq;
import com.spirit.ehr.ws.client.generated.EhrPapCfgRsp;
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
import com.spirit.ehr.ws.client.generated.EhrXdsQDocumentByUidReq;
import com.spirit.ehr.ws.client.generated.EhrXdsQGetAllRq;
import com.spirit.ehr.ws.client.generated.EhrXdsQRsp;
import com.spirit.ehr.ws.client.generated.EhrXdsRetrReq;
import com.spirit.ehr.ws.client.generated.EhrXdsRetrRsp;
import com.spirit.ehr.ws.client.generated.FolderClientDto;
import com.spirit.ehr.ws.client.generated.IheClassificationClientDto;
import com.spirit.ehr.ws.client.generated.PatientContentClientDto;
import com.spirit.ehr.ws.client.generated.SourceSubmissionClientDto;
import com.spirit.ehr.ws.client.generated.SpiritSimpleLoginRequest;
import com.spirit.ehr.ws.client.generated.SpiritUserResponse;
import com.spirit.ehr.ws.client.generated.SubmissionSetClientDto;
import com.spirit.ehr.ws.client.generated.XdsQArgsDocumentByUid;
import com.spirit.ehr.ws.client.generated.XdsQArgsGetAll;
import com.spirit.ehr.ws.client.generated.XdsSrcApRpReq;
import com.spirit.ehr.ws.client.generated.XdsSrcDeleteReq;
import com.spirit.ehr.ws.client.generated.XdsSrcDeprecateReq;
import com.spirit.ehr.ws.client.generated.XdsSrcSubmitReq;
import com.spirit.ehr.ws.client.generated.XdsSrcSubmitRsp;
import com.spirit.ehr.ws.client.generated.XdsSrcUpdateReq;
import com.spirit.ehr.ws.interfaces.InterfaceFactory;
import com.spirit.ehr.ws.interfaces.SpiritEhrWsClientRqRspInterface;

public class SpiritAdapterImpl implements SpiritAdapter {

	private String org;
	private String pwd;
	private String rol;
	private String user;
	private String endpointUrl;

	private XdsbMetadataGenerator policyMetadataGenerator;
	private XdsbMetadataGenerator c32MetadataGenerator;

	// Declare the SpiritEhrWsClientRqRspInterface
	private volatile SpiritEhrWsClientRqRspInterface webService;

	private String domainId;

	private String c2sDomainId;

	private String c2sDomainType;

	private String c2sEnvType;

	private SpiritClientHelper sHelper;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public SpiritAdapterImpl(String endpointUrl, String org, String pwd,
			String rol, String user, String domainId, String c2sDomainId,
			String c2sDomainType, String c2sEnvType) {
		this.endpointUrl = endpointUrl;
		this.org = org;
		this.pwd = pwd;
		this.rol = rol;
		this.user = user;
		this.domainId = domainId;
		this.c2sDomainId = c2sDomainId;
		this.c2sDomainType = c2sDomainType;
		this.c2sEnvType = c2sEnvType;

		this.sHelper = new SpiritClientHelper();
		this.policyMetadataGenerator = new XdsbMetadataGeneratorImpl(
				XdsbDocumentType.SPIRIT_PRIVACY_CONSENT);
		this.c32MetadataGenerator = new XdsbMetadataGeneratorImpl(
				XdsbDocumentType.SPIRIT_PRIVACY_C32);
	}

	public SpiritAdapterImpl(String endpointUrl, String org, String pwd,
			String rol, String user, String domainId, String c2sDomainId,
			String c2sDomainType, String c2sEnvType,
			SpiritEhrWsClientRqRspInterface webService) {
		// Initialize via InterfaceFactory
		this.endpointUrl = endpointUrl;
		this.org = org;
		this.pwd = pwd;
		this.rol = rol;
		this.user = user;
		this.domainId = domainId;
		this.c2sDomainId = c2sDomainId;
		this.c2sDomainType = c2sDomainType;
		this.c2sEnvType = c2sEnvType;
		this.sHelper = new SpiritClientHelper();
		this.webService = webService;
		this.policyMetadataGenerator = new XdsbMetadataGeneratorImpl(
				XdsbDocumentType.SPIRIT_PRIVACY_CONSENT);
		this.c32MetadataGenerator = new XdsbMetadataGeneratorImpl(
				XdsbDocumentType.SPIRIT_PRIVACY_C32);
	}

	@Override
	public SpiritUserResponse usrOrgRoleLogin() throws SpiritAdapterException {
		SpiritUserResponse spiritUserResponse = new SpiritUserResponse();
		SpiritSimpleLoginRequest request = new SpiritSimpleLoginRequest();
		request.setOrg(this.org);
		request.setPwd(this.pwd);
		request.setRol(this.rol);
		request.setUser(this.user);
		try {
			spiritUserResponse = getWebService().usrLogin(request);
		} catch (EhrException_Exception e) {
			logger.error(e.getMessage());
			throw new SpiritAdapterException(e.getMessage(), e);
		}
		return spiritUserResponse;

	}

	@Override
	public String login() throws SpiritAdapterException {
		return usrOrgRoleLogin().getStateID();
	}

	@Override
	public void logout(String stateId) throws SpiritAdapterException {
		EhrWsEmptyReq logoutRequest = new EhrWsEmptyReq();
		logoutRequest.setStateID(stateId);
		try {
			getWebService().usrLogout(logoutRequest);
		} catch (EhrException_Exception e) {
			logger.error(e.getMessage());
			throw new SpiritAdapterException(e.getMessage(), e);
		}
	}

	@Override
	public PatientDto createPatientByPDQ(PatientDto patientDto)
			throws SpiritAdapterException {

		// 1. EHR Portal Login
		String stateId = login();

		// 2. Query the patient with DMG(demographics)

		// Create a PatientRequest
		EhrPatientRq patientRequest = createEhrPatientRq(patientDto, stateId,
				true, null);

		// Print status
		logger.info("queryPatients: Looking for: " + patientDto);

		// Call the webservice
		EhrPatientRsp patientResponse = null;
		try {
			patientResponse = getWebService().queryPatients(patientRequest);
		} catch (EhrException_Exception e) {
			logger.error("Error while invoking querypatients request "
					+ e.getMessage());
			throw new SpiritAdapterException(e.getMessage());
		} catch (Exception e) {
			logger.error("Error while invoking querypatients request "
					+ e.getMessage());	
			throw new SpiritAdapterException(e.getClass().toString() +" "+ e.getMessage());
		}	

		String xdsId = getXDSPatientId(patientResponse);
		String localC2SId = getC2SPatientId(patientResponse);
		/*
		 * If xdsId == null and localC2SId == null then its a brand new patient
		 * in HIE If xdsId == null and localC2SId != null then something wrong
		 * with system If xdsId != null and localC2SId == null then Patient
		 * exists in HIE by some other source system(S) other than C2S If xdsId
		 * != null and localC2SId != null then Patient exists in HIE by C2S
		 * system and/or by some other source system(S)
		 */

		if ((null != xdsId && null != localC2SId)) {

			logger.debug("Patient Already exists in HIE by C2S Source Identifier"
					+ localC2SId);
			throw new SpiritAdapterException(
					"Patient Already exists in HIE withs C2S Source Identifier"
							+ localC2SId);

		} else if ((null == xdsId && null != localC2SId)) {

			logger.debug("Rare Case: Something wrong with HIE system");
			throw new SpiritAdapterException(
					"Something wrong with HIE system AS C2S Local Identifier is available but no XDS Id");

		} else if ((null == xdsId && null == localC2SId)
				|| (null != xdsId && null == localC2SId)) {

			if (null == xdsId && null == localC2SId)
				logger.debug("Brand New Patient in HIE");
			else if (null != xdsId && null == localC2SId)
				logger.debug("Patient exists in HIE by some other source system other than C2S");

			// Adding a Patient to HIE
			patientDto.setPatientId(xdsId);
			addPatientToHIE(stateId, patientDto);

		}

		// 3.EHR Portal Logout
		logout(stateId);
		return patientDto;

	}

	@Override
	public PatientDto updatePatientByLocId(PatientDto patientDto)
			throws SpiritAdapterException {
		// Print status
		logger.info("updatePatientByLocId: START");

		String patientId = null;
		String xdsId = null;

		List<EhrPatientClientDto> pClientDtos = null;
		// 1. EHR Portal Login
		String stateId = login();

		// 2. Query the patient with local identifier
		EhrPatientClientListDto ehrPatientClientListDto = queryPatientsWithPids(
				patientDto.getLocalPatientId(), stateId);
		pClientDtos = ehrPatientClientListDto.getEhrPatientClientListDto();
		// check c2s id pclientdto
		EhrPatientClientDto ehrPatientClientDto = getC2SPClientDto(pClientDtos);

		if (null == pClientDtos) {
			// patient does not exists in exchange with c2s identifier -- Error
			// Scenario
			logger.debug("Updating a C2S Patient that is not available in HIE with c2s local identifier.");
			throw new SpiritAdapterException(
					"Updating a C2S Patient that is not available in HIE for C2S domain.");
		} else {
			// update PatientRequest
			EhrPatientRq patientRequest = createEhrPatientRq(patientDto,
					stateId, false, pClientDtos.get(0));

			// Print status
			logger.info("updatePatientByLocId: Looking for: " + patientDto);

			// Call the web service
			EhrPatientRsp patientResponse = null;
			try {
				patientResponse = webService.updatePatient(patientRequest);
				// Print status
				logger.debug("updatePatient: Updated patient: "
						+ patientResponse.getResponseData().get(0)
								.getFamilyName()
						+ " / "
						+ patientResponse.getResponseData().get(0)
								.getGivenName());

				patientId = getC2SPatientId(patientResponse);
				xdsId = getXDSPatientId(patientResponse);
			} catch (EhrException_Exception e) {
				logger.error("updatePatient: FAILED", e.getMessage());
				throw new SpiritAdapterException(e.getMessage());
			} catch (Exception e) {
				logger.error("updatePatient: FAILED", e.getMessage());	
				throw new SpiritAdapterException(e.getClass().toString() +" "+ e.getMessage());
			}

			// Print status
			logger.info("updatePatientByLocId: SUCCESS");

		}

		// setting back the c2s local id
		patientDto.setLocalPatientId(patientId);

		// setting the latest xds id
		patientDto.setPatientId(xdsId);

		// 3.EHR Portal Logout
		logout(stateId);
		return patientDto;
	}

	private void addPatientToHIE(String stateId, PatientDto patientDto)
			throws SpiritAdapterException {
		// 2.1. Create Local Identifier
		// Create a helper object
		if (null == sHelper)
			sHelper = new SpiritClientHelper();

		final String newLocalId = patientDto.getLocalPatientId();
		List<EhrPIDClientDto> localPids = sHelper.createPIDList(newLocalId,
				c2sDomainId, c2sDomainType);

		// updating with local id
		patientDto.setPatientId(newLocalId);
		patientDto.setLocalPatientId(newLocalId);

		patientDto.setNewInExchange(true);
		// 2.2. Insert patient
		patientDto.setPatientId(insertPatientByPDQ(patientDto, localPids,
				stateId));
		logger.info("patientid from insertpatient "
				+ patientDto.getLocalPatientId());

	}

	@Override
	public XdsSrcSubmitRsp submitSignedConsent(byte[] xacmlPolicy,
			byte[] signedConsentPdf, byte[] pdfConsentFromXacml,
			byte[] pdfConsentToXacml, String xacmlPolicyId, String patientId,
			String country) throws SpiritAdapterException {
		// Assert arguments
		Assert.notNull(xacmlPolicy, "'xacmlPolicy' cannot be null.");
		Assert.notNull(pdfConsentFromXacml,
				"'pdfConsentFromXacml' cannot be null.");
		Assert.notNull(pdfConsentToXacml, "'pdfConsentToXacml' cannot be null.");
		Assert.notNull(signedConsentPdf, "'signedConsentPdf' cannot be null.");

		Assert.isTrue(xacmlPolicy.length > 0, "'xacmlPolicy' cannot be empty.");
		Assert.isTrue(pdfConsentFromXacml.length > 0,
				"'pdfConsentFromXacml' cannot be empty.");
		Assert.isTrue(pdfConsentToXacml.length > 0,
				"'pdfConsentToXacml' cannot be empty.");
		Assert.isTrue(signedConsentPdf.length > 0,
				"'signedConsentPdf' cannot be empty.");

		Assert.hasText(xacmlPolicyId,
				"'xacmlPolicyId' cannot be null and it must have a text.");
		Assert.hasText(patientId,
				"'patientId' cannot be null and it must have a text.");
		Assert.hasText(country,
				"'country' cannot be null and it must have a text.");

		// Generate the metadata
		SourceSubmissionClientDto sourceSubmissionClientDto = generatePolicyMetadata(new String(
				xacmlPolicy));

		// Assert metadata
		Assert.notNull(
				sourceSubmissionClientDto,
				"There is a problem with the metadata generation: Metadata generation or unmarshalling of the metadata failed!");
		Assert.notEmpty(sourceSubmissionClientDto.getDocuments(),
				"There is a problem with the metadata generation: Metadata cannot be empty!");
		Assert.isTrue(
				sourceSubmissionClientDto.getDocuments().size() == 1,
				"There is a problem with the metadata generation: Metadata must exactly contain 1 entry (only for Signed Consent PDF)!");

		// Inject document content and document unique id to metadata
		DocumentClientDto document = sourceSubmissionClientDto.getDocuments()
				.get(0);
		Assert.isTrue(SpiritConstants.MIME_TYPE_PDF.equalsIgnoreCase(document
				.getMimeType()),
				"The document mime type must be PDF for the signed consent PDF document.");
		document.setBytes(signedConsentPdf);
		document.setUniqueId(SpiritConstants.URN_PREFIX_PDF_SIGNED
				+ toXdsUniqueId(xacmlPolicyId));
		document.setName(document.getName() + xacmlPolicyId);

		// Login
		String stateId = login();

		// Query patient
		EhrPatientClientListDto ehrPatientClientListDto = queryPatientsWithPids(
				patientId, stateId);
		List<EhrPatientClientDto> patientClientDtoList = ehrPatientClientListDto
				.getEhrPatientClientListDto();
		stateId = ehrPatientClientListDto.getStateId();
		EhrPatientClientDto patientClientDto = patientClientDtoList.get(0);

		// Submit C32 XACML to policy repository
		EhrPolicySubmitOrUpdateRsp policyRepoResp = submitOrUpdatePolicy(
				xacmlPolicy, stateId);
		stateId = policyRepoResp.getStateID();

		// Submit pdf ConsentFrom XACML to policy repository
		policyRepoResp = submitOrUpdatePolicy(pdfConsentFromXacml, stateId);
		stateId = policyRepoResp.getStateID();

		// Submit pdf consentTo XAMCL to policy repository
		policyRepoResp = submitOrUpdatePolicy(pdfConsentToXacml, stateId);
		stateId = policyRepoResp.getStateID();
		// Submit Signed PDF document to XDS.b repository
		XdsSrcSubmitRsp response = submitDocument(patientClientDto,
				sourceSubmissionClientDto, false, stateId);
		stateId = response.getStateID();

		// Logout and return response
		logout(stateId);
		return response;
	}

	private EhrPatientRq createEhrPatientRq(PatientDto patientDto,
			String stateId, boolean isBasic, EhrPatientClientDto pClientDto)
			throws SpiritAdapterException {

		// Create a patient object
		if (null == sHelper)
			sHelper = new SpiritClientHelper();

		EhrPatientClientDto searchPatient = sHelper.convertFromPatientDto(
				patientDto, isBasic, pClientDto);

		// Create a PatientRequest
		EhrPatientRq patientRequest = new EhrPatientRq();

		// patientRequest.setStateID(stateId);

		// Pass the patient, to look for, to the PatientRequest
		patientRequest.setRequestData(searchPatient);

		return patientRequest;

	}

	private String getC2SPatientId(EhrPatientRsp patientResponse) {
		String patientId = null;
		try {
			JXPathContext context = JXPathContext.newContext(patientResponse);
			patientId = (String) context
					.getValue("/responseData[1]/pid[domain[contains(authUniversalID, '"
							+ c2sDomainId
							+ "' ) and contains(authUniversalIDType, '"
							+ c2sDomainType + "' )]]/patientID");

		} catch (JXPathNotFoundException je) {
			logger.error("the patient does not exists in the exchange");
		}

		// Return the list of found patients
		return patientId;

	}

	private EhrPatientClientDto getC2SPClientDto(
			List<EhrPatientClientDto> pClientDtos) {
		JXPathContext context = JXPathContext.newContext(pClientDtos);

		return null;
	}

	private String getXDSPatientId(EhrPatientRsp patientResponse) {

		String patientId = null;
		try {
			JXPathContext context = JXPathContext.newContext(patientResponse);
			patientId = (String) context
					.getValue("/responseData/pid[patientIDType='RRI'][1]/patientID");

		} catch (JXPathNotFoundException je) {
			logger.error("the patient does not exists in the exchange");
		}

		// Return the list of found patients
		return patientId;

	}

	@Override
	public EhrPatientClientListDto queryPatientsWithPids(String patientId,
			String stateId) throws SpiritAdapterException {
		try {
			// Print status
			logger.info("queryPatientsWithPids: START");

			EhrPatientClientDto searchPatient = getC2SPatientClientDto(patientId);

			// Create a PatientRequest
			EhrPatientRq patientRequest = new EhrPatientRq();

			// Pass the patient, to look for, to the PatientRequest
			patientRequest.setRequestData(searchPatient);

			// patientRequest.setStateID(stateId);

			// Print status
			logger.info("queryPatientsWithPids: Looking for patient...");

			// Call the web service, it returns a EhrPatientRsp containing a
			// list
			// of all found patients
			EhrPatientRsp patientResponse = getWebService().queryPatients(
					patientRequest);

			// Print status
			logger.info("queryPatientsWithPids: SUCCESS");

			// Return the list of found patients
			return new EhrPatientClientListDto(
					patientResponse.getResponseData(),
					patientResponse.getStateID());
		} catch (Exception e) {
			logger.error("queryPatientsWithPids: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	/**
	 * @param patientId
	 * @return
	 */
	private EhrPatientClientDto getC2SPatientClientDto(String patientId) {
		// Create a EhrPIDClientDto (holds the given pid)
		EhrPIDClientDto pid = new EhrPIDClientDto();

		// Set the given pid
		pid.setPatientID(patientId);

		// Create a EhrDomainClientDto (holds the given domainid)
		EhrDomainClientDto domain = new EhrDomainClientDto();

		// Set the given domain id
		domain.setAuthUniversalID(c2sDomainId);
		domain.setAuthUniversalIDType(c2sDomainType);

		// Set the c2s local domain
		pid.setDomain(domain);

		pid.setEhrPIDType(Integer.parseInt(SpiritConstants.C2S_EHRP_ID_TYPE));

		// Create a patient object
		EhrPatientClientDto searchPatient = new EhrPatientClientDto();

		// Set the patient information to look for
		searchPatient.getPid().add(pid);
		// searchPatient.setCountry(SpiritConstants.C2S_PATIENT_COUNTRY);
		return searchPatient;
	}

	private String insertPatientByPDQ(PatientDto patientDto,
			List<EhrPIDClientDto> localPids, String stateId)
			throws SpiritAdapterException {

		// Create a PatientRequest
		EhrPatientRq patientRequest = createEhrPatientRq(patientDto, stateId,
				true, null);

		// set pid information
		patientRequest.getRequestData().getPid().addAll(localPids);

		EhrPatientRsp patientRsp = insertPatient(patientRequest);

		// return getPatientId(patientRsp);
		return getXDSPatientId(patientRsp);
	}

	public EhrPatientRsp insertPatient(EhrPatientRq arg0)
			throws SpiritAdapterException {
		// Call the web service
		EhrPatientRsp insertResponse;
		try {
			insertResponse = getWebService().insertPatient(arg0);
		} catch (EhrException_Exception e) {
			logger.error(e.getMessage());
			throw new SpiritAdapterException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SpiritAdapterException(e.getClass().toString() +" "+ e.getMessage());
		}

		return insertResponse;
	}

	/**
	 * @param submitForPatient
	 *            - the patient
	 * @param submitFile
	 *            - file to submit
	 * @param withFolder
	 *            - true: a folder will be created and the submitted document
	 *            will be moved into the folder; false: folder wont be created
	 * @return XdsSrcSubmitRsp
	 * @throws Exception
	 */
	@Override
	public XdsSrcSubmitRsp submitDocument(EhrPatientClientDto submitForPatient,
			SourceSubmissionClientDto sourceSubmission, Boolean withFolder,
			String stateId) throws SpiritAdapterException {
		try {
			logger.info("submitDocument: START");

			// Create a XdsSourceSubmitRequest
			XdsSrcSubmitReq xdsSrcSubmitRequest = new XdsSrcSubmitReq();

			xdsSrcSubmitRequest.setStateID(stateId);

			// Put the patient into the Request
			xdsSrcSubmitRequest.setPatient(submitForPatient);

			// Put the SourceSubmission of the submitFile into the Request
			xdsSrcSubmitRequest.setSrcSubmission(sourceSubmission);

			// Create a XdsSrcSubmitResponse
			XdsSrcSubmitRsp xdsSrcSubmitResponse = new XdsSrcSubmitRsp();

			// Send the Request to the webservice
			xdsSrcSubmitResponse = getWebService().submitDocument(
					xdsSrcSubmitRequest);

			// Print status
			logger.info("submitDocument: SUCCESS");
			logger.info("submitDocument: Document submitted for patient: "
					+ submitForPatient.getFamilyName() + " / "
					+ submitForPatient.getGivenName() + " DONE");

			// Return the Response
			return xdsSrcSubmitResponse;
		} catch (Exception e) {
			logger.error("submitDocument: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	@Override
	public XdsSrcSubmitRsp submitDocument(EhrPatientClientDto submitForPatient,
			SourceSubmissionClientDto sourceSubmission, Boolean withFolder)
			throws SpiritAdapterException {
		String stateId = login();
		XdsSrcSubmitRsp response = submitDocument(submitForPatient,
				sourceSubmission, withFolder, stateId);
		logout(stateId);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.spirit.wswebService.adapter.SpiritAdapter#queryPatientContent(
	 * com.spirit.ehr.ws.remoting.EhrPatientClientDto)
	 */
	@Override
	public EhrXdsQRsp queryPatientContent(EhrPatientClientDto patient,
			String stateId) throws SpiritAdapterException {
		try {
			// String stateId = login();
			// Print status
			logger.info("queryPatientContent: START");

			// Create a xdsQArgsGetAll (holds the search-parameters)
			XdsQArgsGetAll qArgs = new XdsQArgsGetAll();

			// Create and set the search-parameter (there are some more
			// parameter, which can be set)
			// For example: FormatCode, Document status and Folder status
			// IheClassificationClientDto formatCode = new
			// IheClassificationClientDto();
			// formatCode.setNodeRepresentation("Generic Image");
			// formatCode.setValue("JPEG-Image");
			// formatCode.getSchema().add("Connect-a-thon formatCodes");
			// qArgs.getFormatCodes().add(formatCode);

			// Document status
			qArgs.getDocumentStatus().add("Approved");
			qArgs.getDocumentStatus().add("Deprecated");

			// Folder status
			// qArgs.getFolderStatus().add("Approved");

			// Create the patientContentRequest
			EhrXdsQGetAllRq xdsQGetAllRq = new EhrXdsQGetAllRq();

			// Set/Add the required information of the patientContentRequest
			// Add the Pids of the selected patient
			xdsQGetAllRq.getRequestData().addAll(patient.getPid());

			xdsQGetAllRq.setStateID(stateId);
			// Set the created XdsQArgsGetAll
			xdsQGetAllRq.setXdsQArgsGetAll(qArgs);

			// Call the webservice-method queryPatientContent with the created
			// patientContentRequest
			EhrXdsQRsp response = getWebService().queryPatientContent(
					xdsQGetAllRq);

			// Print status
			logger.info("queryPatientContent: SUCCESS");

			// Return the repsonse, containing the patientContent, which holds
			// the documents and folders of the patient
			return response;
		} catch (Exception e) {
			logger.error("queryPatientContent: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	/**
	 * @param wantedDocument
	 *            - the document you want to retrieve
	 * @return EhrXdsRetrRsp
	 * @throws Exception
	 */
	@Override
	public EhrXdsRetrRsp retrieveDocument(DocumentClientDto wantedDocument)
			throws SpiritAdapterException {
		try {
			String stateId = login();

			logger.info("retrieveDocument: START");

			// Create a the retrieveDocumentRequest
			EhrXdsRetrReq request = new EhrXdsRetrReq();

			request.setStateID(stateId);

			// Set the required information of the request
			// Set the selected document
			request.setRequestData(wantedDocument);

			// Call the webservice-method retrieveDocument with the created
			// request
			EhrXdsRetrRsp response = getWebService().retrieveDocument(request);

			logger.info("retrieveDocument: SUCCESS");

			logout(stateId);

			return response;
		} catch (Exception e) {
			logger.error("retrieveDocument: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	@Override
	public EhrPatientClientDto updateDocument(
			XdsSrcUpdateReq xdsSrcUpdateRequest) throws SpiritAdapterException {
		try {
			String stateId = login();
			logger.info("updateDocument: START");

			xdsSrcUpdateRequest.setStateID(stateId);

			// xdsSrcUpdateRequest.setSubmission(changedDocument.getSubmissionSet());

			// Send the XdsRetrieveRequest to the webservice, it returns a
			// XdsSrcSubmitRsp, which contains the affected patient
			XdsSrcSubmitRsp xdsSrcSubmitResponse = getWebService()
					.updateDocument(xdsSrcUpdateRequest);

			// Print status
			logger.info("updateDocument: SUCCESS");

			logout(stateId);

			// Return the affected patient
			return xdsSrcSubmitResponse.getResponseData();
		} catch (EhrException_Exception e) {
			logger.warn("UpdateDocument: FAILED\n", e);

			throw new SpiritAdapterException(e);
		}
	}

	@Override
	public SourceSubmissionClientDto generatePolicyMetadata(String document) {
		String metadataString = policyMetadataGenerator.generateMetadataXml(
				document, c2sDomainId, null, null);
		SourceSubmissionClientDto sourceSubmissionClientDto = null;
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(SourceSubmissionClientDto.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<SourceSubmissionClientDto> sourceSubmissionClientDtoElement = jaxbUnmarshaller
					.unmarshal(new StreamSource(
							new StringReader(metadataString)),
							SourceSubmissionClientDto.class);
			sourceSubmissionClientDto = sourceSubmissionClientDtoElement
					.getValue();
		} catch (JAXBException e) {
			throw new DS4PException(e.toString(), e);
		}

		return sourceSubmissionClientDto;
	}

	@Override
	public DocumentClientDto queryDocumentsByUid(String patientId,
			String documentUniqueId, String stateId)
			throws SpiritAdapterException {
		try {
			// Print status
			logger.info("queryDocumentsByUid: START");

			// Create a XdsQArgsDocumentByUid (holds the search-parameter)
			XdsQArgsDocumentByUid qArgs = new XdsQArgsDocumentByUid();
			qArgs.getUniqueIds().add(documentUniqueId);

			EhrXdsQDocumentByUidReq documentRequest = new EhrXdsQDocumentByUidReq();
			// REG.1DK7BVY8QU^^^&2.16.840.1.113883.3.704.100.990.1&ISO
			documentRequest.setXdsQArgsDocumentByUid(qArgs);
			// documentRequest.getRequestData()
			documentRequest.setStateID(stateId);
			EhrPIDClientDto pid = new EhrPIDClientDto();
			EhrDomainClientDto domainClientDto = new EhrDomainClientDto();
			domainClientDto.setAuthUniversalID(domainId);
			domainClientDto.setAuthUniversalIDType("ISO");
			pid.setDomain(domainClientDto);
			pid.setPatientID(patientId);
			pid.setPatientIDType("PI");
			pid.setEhrPIDType(6);
			documentRequest.getRequestData().add(pid);
			// Call the webservice-method queryDocumentsByUid with the created
			// documentRequest
			EhrXdsQRsp response = getWebService().queryDocumentsByUid(
					documentRequest);
			// Print status
			logger.info("queryDocumentsByUid: SUCCESS");

			// Return the response, containing the found documents/folders
			return response.getResponseData().getDocuments().get(0);
		} catch (Exception e) {
			logger.error("queryDocumentsByUid: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	DocumentClientDto makeDocumentClientDto() {
		return new DocumentClientDto();
	}

	@Override
	public XdsSrcSubmitRsp deprecateDocument(String documentUniqueId,
			String patientId, String stateId) throws SpiritAdapterException {
		XdsSrcDeprecateReq xdsSrcDeprecateRequest = new XdsSrcDeprecateReq();
		DocumentClientDto documentToDeprecate = makeDocumentClientDto();
		EhrPatientClientDto patient;
		try {
			EhrPatientClientListDto ehrPatientClientListDto = queryPatientsWithPids(
					patientId, stateId);
			stateId = ehrPatientClientListDto.getStateId();
			patient = ehrPatientClientListDto.getEhrPatientClientListDto().get(
					0);
			EhrXdsQRsp ehrXdsQRsp = queryPatientContent(patient, stateId);
			PatientContentClientDto patientContentClientDto = ehrXdsQRsp
					.getResponseData();
			stateId = ehrXdsQRsp.getStateID();
			List<DocumentClientDto> documents = patientContentClientDto
					.getDocuments();
			for (DocumentClientDto document : documents) {
				if (document.getUniqueId().equals(documentUniqueId)) {
					documentToDeprecate = document;
					break;
				}
			}
			xdsSrcDeprecateRequest.setDocument(documentToDeprecate);
			xdsSrcDeprecateRequest.setSubmission(documentToDeprecate
					.getSubmissionSet());
			xdsSrcDeprecateRequest.setPatient(patient);
			xdsSrcDeprecateRequest.setStateID(stateId);
			XdsSrcSubmitRsp response = getWebService().deprecateDocument(
					xdsSrcDeprecateRequest);
			return response;
		} catch (Exception e) {
			logger.error("deprecatePolicy: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	@Override
	public XdsSrcSubmitRsp deprecateDocuments(
			List<String> documentUniqueIdList, String patientId, String stateId)
			throws SpiritAdapterException {
		EhrPatientClientDto patient;
		try {
			EhrPatientClientListDto ehrPatientClientListDto = queryPatientsWithPids(
					patientId, stateId);
			stateId = ehrPatientClientListDto.getStateId();
			patient = ehrPatientClientListDto.getEhrPatientClientListDto().get(
					0);
			EhrXdsQRsp ehrXdsQRsp = queryPatientContent(patient, stateId);
			PatientContentClientDto patientContentClientDto = ehrXdsQRsp
					.getResponseData();
			stateId = ehrXdsQRsp.getStateID();

			return deprecateDocuments(documentUniqueIdList, patient,
					patientContentClientDto, stateId);
		} catch (Exception e) {
			logger.error("deprecatePolicy: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	@Override
	public XdsSrcSubmitRsp deprecateDocuments(
			List<String> documentUniqueIdList, EhrPatientClientDto patient,
			PatientContentClientDto patientContentClientDto, String stateId)
			throws SpiritAdapterException {
		XdsSrcDeprecateReq xdsSrcDeprecateRequest = new XdsSrcDeprecateReq();
		try {
			XdsSrcSubmitRsp response = null;
			List<DocumentClientDto> documents = patientContentClientDto
					.getDocuments();
			Map<String, DocumentClientDto> documentsMap = new HashMap<String, DocumentClientDto>();

			for (DocumentClientDto document : documents)
				documentsMap.put(document.getUniqueId(), document);
			for (String documentToDepricateId : documentUniqueIdList) {
				DocumentClientDto documentToDeprecate = documentsMap
						.get(documentToDepricateId);
				if (documentToDeprecate != null) {
					xdsSrcDeprecateRequest.setDocument(documentToDeprecate);
					xdsSrcDeprecateRequest.setSubmission(documentToDeprecate
							.getSubmissionSet());
					xdsSrcDeprecateRequest.setPatient(patient);
					xdsSrcDeprecateRequest.setStateID(stateId);
					response = getWebService().deprecateDocument(
							xdsSrcDeprecateRequest);
					stateId = response.getStateID();
				}
			}

			return response;
		} catch (Exception e) {
			logger.error("deprecatePolicy: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	@Override
	public String deleteDocuments(FolderClientDto folder,
			List<DocumentClientDto> documentList, EhrPatientClientDto patient,
			String stateId) throws EhrException_Exception,
			SpiritAdapterException {
		// String stateId = login();
		XdsSrcSubmitRsp deleteResponse = new XdsSrcSubmitRsp();
		deleteResponse.setStateID(stateId);
		for (DocumentClientDto document : documentList) {
			deleteResponse = deleteDocument(folder, document, patient,
					deleteResponse.getStateID());
		}

		// logout(deleteResponse.getStateID());
		return "Success";

	}

	@Override
	public XdsSrcSubmitRsp deleteDocument(FolderClientDto folder,
			DocumentClientDto document, EhrPatientClientDto patient,
			String stateId) throws EhrException_Exception,
			SpiritAdapterException {
		try {
			logger.info("deleteDocument: START");

			// Create a XdsSrcSubmitReq
			XdsSrcDeleteReq xdsSrcDeleteRequest = new XdsSrcDeleteReq();

			// Set the required attributes
			// Document to delete (can be null)
			xdsSrcDeleteRequest.setDocument(document);

			// Folder to delete (can be null)
			xdsSrcDeleteRequest.setFolder(folder);

			// Set the patient
			xdsSrcDeleteRequest.setPatient(patient);

			xdsSrcDeleteRequest.setStateID(stateId);

			// Call the webservice
			XdsSrcSubmitRsp xdsSrcSubmitResponse = getWebService()
					.deleteDocument(xdsSrcDeleteRequest);

			// Print status
			logger.info("deleteDocument: SUCCESS");

			// Return the affected patient
			return xdsSrcSubmitResponse;
		} catch (EhrException_Exception e) {
			logger.error("deleteDocument: FAILED\n", e);
			throw e;
		}
	}

	@Override
	public XdsSrcSubmitRsp deprecatePolicy(String documentUniqueId,
			String patientId, byte[] revokedPdfConsent)
			throws SpiritAdapterException {
		// Assert arguments
		Assert.hasText(documentUniqueId,
				"'documentUniqueId' cannot be null and it must have a text.");
		Assert.hasText(patientId,
				"'patientId' cannot be null and it must have a text.");
		Assert.notNull(revokedPdfConsent, "'revokedPdfConsent' cannot be null.");
		Assert.isTrue(revokedPdfConsent.length > 0,
				"revokedPdfConsent cannot be empty.");

		// Login
		String stateId = login();

		// Query patient
		EhrPatientClientListDto ehrPatientClientListDto = queryPatientsWithPids(
				patientId, stateId);
		stateId = ehrPatientClientListDto.getStateId();
		EhrPatientClientDto patient = ehrPatientClientListDto
				.getEhrPatientClientListDto().get(0);

		// Query patient content
		EhrXdsQRsp ehrXdsQRsp = queryPatientContent(patient, stateId);
		PatientContentClientDto patientContentClientDto = ehrXdsQRsp
				.getResponseData();
		stateId = ehrXdsQRsp.getStateID();

		// Get patient documents from patient content
		List<DocumentClientDto> documents = patientContentClientDto
				.getDocuments();
		DocumentClientDto oldDocument = null;
		// Locate the old document (Signed Consent PDF)
		for (DocumentClientDto document : documents) {
			if (document.getUniqueId().equals(
					SpiritConstants.URN_PREFIX_PDF_SIGNED
							+ toXdsUniqueId(documentUniqueId))) {
				oldDocument = document;
				break;
			}
		}
		Assert.notNull(oldDocument, "The signedConsentPdf cannot be found!");

		appendRevokedPdfConsent(patient, oldDocument, documentUniqueId,
				revokedPdfConsent);

		// Collect ids for documents to deprecate (Signed PDF)
		List<String> documentsToDeprecateId = new ArrayList<String>();
		documentsToDeprecateId.add(SpiritConstants.URN_PREFIX_PDF_SIGNED
				+ toXdsUniqueId(documentUniqueId));

		// Deprecate the Signed PDF document
		XdsSrcSubmitRsp response = deprecateDocuments(documentsToDeprecateId,
				patient, patientContentClientDto, stateId);
		stateId = response.getStateID();

		// Discard XACML policy from the policy repository
		EhrPolicyDiscardRsp discardPolicyResponse = discardPolicy(
				documentUniqueId, stateId);
		stateId = discardPolicyResponse.getStateID();

		// Logout and return response
		logout(stateId);
		return response;
	}

	// This method needs refactoring to make it work with new Tiani API
	public XdsSrcSubmitRsp appendDocument(EhrPatientClientDto patient,
			DocumentClientDto oldDocument, DocumentClientDto documentToAppend,
			String stateId) throws SpiritAdapterException {
		try {
			XdsSrcApRpReq request = new XdsSrcApRpReq();
			request.setPatient(patient);
			request.setOldDocument(oldDocument);
			SourceSubmissionClientDto sourceSubmission = new SourceSubmissionClientDto();
			sourceSubmission.getDocuments().add(documentToAppend);
			sourceSubmission.setSubmissionSet(oldDocument.getSubmissionSet());
			request.setSrcSubmission(sourceSubmission);
			request.setStateID(stateId);
			return getWebService().appendDocument(request);
		} catch (Exception e) {
			logger.error("appendDocument: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	@Override
	public EhrPolicySubmitRsp submitPolicy(byte[] policy)
			throws SpiritAdapterException {
		// Assert
		Assert.notNull(policy, "'policy' cannot be null.");
		Assert.isTrue(policy.length > 0 && policy[0] != 0,
				"'policy' cannot be empty");
		return submitPolicies(Arrays.asList(policy));
	}

	@Override
	public EhrPolicySubmitRsp submitPolicies(List<byte[]> policyList)
			throws SpiritAdapterException {
		// Assert
		Assert.notEmpty(policyList, "'policyList' cannot be empty.");

		// Print status
		logger.debug("submitPolicies: START");
		EhrPolicySubmitRsp ehrPolicySubmitRsp = null;

		try {
			// Login
			String stateId = login();

			// Create a request
			EhrPolicySubmitRq ehrPolicySubmitRq = new EhrPolicySubmitRq();
			ehrPolicySubmitRq.setStateID(stateId);

			// Add the required information of the request
			// Add policies
			ehrPolicySubmitRq.getPolicyList().addAll(policyList);

			// Call the webservice-method submitPolicies with the created
			// request
			ehrPolicySubmitRsp = getWebService().submitPolicies(
					ehrPolicySubmitRq);

			// Print status
			logger.debug("submitPolicies: SUCCESS");

			// Logout
			logout(stateId);
		} catch (Exception e) {
			logger.error("submitPolicies: FAILED", e);
			throw new SpiritAdapterException(e);
		}
		// Return the response
		return ehrPolicySubmitRsp;
	}

	@Override
	public EhrPolicySubmitOrUpdateRsp submitOrUpdatePolicy(byte[] policy,
			String stateId) throws SpiritAdapterException {
		// Assert
		Assert.notNull(policy, "'policy' cannot be null.");
		Assert.isTrue(policy.length > 0 && policy[0] != 0,
				"'policy' cannot be empty");
		return submitOrUpdatePolicies(Arrays.asList(policy), stateId);
	}

	@Override
	public EhrPolicySubmitOrUpdateRsp submitOrUpdatePolicies(
			List<byte[]> policyList, String stateId)
			throws SpiritAdapterException {
		// Assert
		Assert.notEmpty(policyList, "'policyList' cannot be empty.");

		// Print status
		logger.debug("submitOrUpdatePolicies: START");
		EhrPolicySubmitOrUpdateRsp ehrPolicySubmitOrUpdateRsp = null;
		try {
			// Create a request
			EhrPolicySubmitOrUpdateRq ehrPolicySubmitOrUpdateRq = new EhrPolicySubmitOrUpdateRq();
			ehrPolicySubmitOrUpdateRq.setStateID(stateId);

			// Add the required information of the request
			// Add policies to submit or update
			ehrPolicySubmitOrUpdateRq.getPolicyList().addAll(policyList);

			// Call the webservice-method submitOrUpdatePolicies with the
			// created request
			ehrPolicySubmitOrUpdateRsp = getWebService()
					.submitOrUpdatePolicies(ehrPolicySubmitOrUpdateRq);

			// Print status
			logger.debug("submitOrUpdatePolicies: SUCCESS");
		} catch (Exception e) {
			logger.error("submitOrUpdatePolicies: FAILED", e);
			throw new SpiritAdapterException(e);
		}
		// Return the response
		return ehrPolicySubmitOrUpdateRsp;
	}

	@Override
	public EhrPolicyUpdateRsp updatePolicy(byte[] policy)
			throws SpiritAdapterException {
		// Assert
		Assert.notNull(policy, "'policy' cannot be null.");
		Assert.isTrue(policy.length > 0 && policy[0] != 0,
				"'policy' cannot be empty");
		return updatePolicies(Arrays.asList(policy));
	}

	@Override
	public EhrPolicyUpdateRsp updatePolicies(List<byte[]> policyList)
			throws SpiritAdapterException {
		// Assert
		Assert.notEmpty(policyList, "'policyList' cannot be empty.");

		// Print status
		logger.debug("updatePolicies: START");
		EhrPolicyUpdateRsp ehrPolicyUpdateRsp = null;

		try {
			// Login
			String stateId = login();

			// Create a request
			EhrPolicyUpdateRq ehrPolicyUpdateRq = new EhrPolicyUpdateRq();
			ehrPolicyUpdateRq.setStateID(stateId);

			// Add the required information of the request
			// Add policies to update
			ehrPolicyUpdateRq.getPolicyList().addAll(policyList);

			// Call the webservice-method updatePolicies with the created
			// request
			ehrPolicyUpdateRsp = getWebService().updatePolicies(
					ehrPolicyUpdateRq);

			// Print status
			logger.debug("updatePolicies: SUCCESS");

			// Logout
			logout(stateId);
		} catch (Exception e) {
			logger.error("updatePolicies: FAILED", e);
			throw new SpiritAdapterException(e);
		}
		// Return the response
		return ehrPolicyUpdateRsp;
	}

	@Override
	public byte[] retrievePolicy(String policyId) throws SpiritAdapterException {
		// Assert
		Assert.hasText(policyId, "'policyId' must has a text.");
		byte[] policy = null;
		try {
			policy = retrievePolicies(null, Arrays.asList(policyId)).get(0);
		} catch (Exception e) {
			logger.error("retrievePolicy: FAILED", e);
			throw new SpiritAdapterException(e);
		}
		return policy;
	}

	@Override
	public List<byte[]> retrievePolicies(List<String> listPolicySetIds,
			List<String> listPolicyIds) throws SpiritAdapterException {
		// Assert
		Assert.isTrue((listPolicySetIds != null && listPolicySetIds.size() > 0)
				|| (listPolicyIds != null && listPolicyIds.size() > 0),
				"At least one of 'listPolicySetIds' or 'listPolicyIds' must be provided.");

		// Print status
		logger.debug("retrievePolicies: START");
		EhrPolicyRetrieveRsp ehrPolicyRetrieveRsp = null;

		try {
			// Login
			String stateId = login();

			// Create a request
			EhrPolicyRetrieveRq ehrPolicyRetrieveRq = new EhrPolicyRetrieveRq();
			ehrPolicyRetrieveRq.setStateID(stateId);

			// Add the required information of the request
			// Add PolicyIds
			if (listPolicyIds != null) {
				ehrPolicyRetrieveRq.getPolicyIds().addAll(listPolicyIds);
			}

			// Add PolicySetIds
			if (listPolicySetIds != null) {
				ehrPolicyRetrieveRq.getPolicySetIds().addAll(listPolicySetIds);
			}

			// Call the webservice-method retrievePolicies with the created
			// request
			ehrPolicyRetrieveRsp = getWebService().retrievePolicies(
					ehrPolicyRetrieveRq);

			// Print status
			logger.debug("retrievePolicies: SUCCESS");

			// Logout
			logout(stateId);
		} catch (Exception e) {
			logger.error("retrievePolicies: FAILED", e);
			throw new SpiritAdapterException(e);
		}
		return ehrPolicyRetrieveRsp.getPolicyList();
	}

	@Override
	public EhrPolicyDiscardRsp discardPolicy(String policyId, String stateId)
			throws SpiritAdapterException {
		// Assert
		Assert.hasText(policyId, "'policyId' must has a text.");
		return discardPolicies(null, Arrays.asList(policyId), stateId);
	}

	@Override
	public EhrPolicyDiscardRsp discardPolicies(List<String> listPolicySetIds,
			List<String> listPolicyIds, String stateId)
			throws SpiritAdapterException {
		// Print status
		logger.debug("discardPolicies: START");
		EhrPolicyDiscardRsp response = null;
		try {
			// Create a EhrPolicyDiscardRq
			EhrPolicyDiscardRq policyDiscardRequest = new EhrPolicyDiscardRq();
			policyDiscardRequest.setStateID(stateId);

			// Set the given policy lists in the request
			// Add PolicyIds
			if (listPolicyIds != null) {
				policyDiscardRequest.getPolicyIds().addAll(listPolicyIds);
			}

			// Add PolicySetIds
			if (listPolicySetIds != null) {
				policyDiscardRequest.getPolicySetIds().addAll(listPolicySetIds);
			}

			// Send the request to the webservice
			response = getWebService().discardPolicies(policyDiscardRequest);

			// Print status
			logger.debug("discardPolicies: SUCCESS");
		} catch (Exception e) {
			logger.error("discardPolicies: FAILED", e);
			throw new SpiritAdapterException(e);
		}
		return response;
	}

	public EhrPapCfgRsp loadPapConfiguration() throws SpiritAdapterException {
		try {
			// Print status
			logger.debug("loadPapConfiguration: START");
			String stateId = login();

			// Create an empty EhrPapCfgRq
			EhrPapCfgRq papCfgRequest = new EhrPapCfgRq();
			papCfgRequest.setStateID(stateId);

			// Send the request to the webservice
			EhrPapCfgRsp papCfgResponse = getWebService().loadPapConfiguration(
					papCfgRequest);

			// Print status
			logger.debug("loadPapConfiguration: SUCCESS");

			// Return the response
			return papCfgResponse;
		} catch (Exception e) {
			logger.error("loadPapConfiguration: FAILED", e);
			throw new SpiritAdapterException(e);
		}
	}

	@Override
	public XdsSrcSubmitRsp submitC32(byte[] c32file)
			throws SpiritAdapterException {
		// Assert arguments
		Assert.notNull(c32file, "'c32 file' cannot be null.");
		Assert.isTrue(c32file.length > 0, "'c32 file' cannot be empty.");

		// Generate the metadata
		SourceSubmissionClientDto sourceSubmissionClientDto = generateC32Metadata(new String(
				c32file));

		// Assert metadata
		Assert.notNull(
				sourceSubmissionClientDto,
				"There is a problem with the metadata generation: Metadata generation or unmarshalling of the metadata failed!");
		Assert.notEmpty(sourceSubmissionClientDto.getDocuments(),
				"There is a problem with the metadata generation: Metadata cannot be empty!");
		Assert.notNull(sourceSubmissionClientDto.getSubmissionSet()
				.getPatientId(),
				"There is a problem with the metadata generation: PatientId cannot be empty!");
		Assert.isTrue(
				sourceSubmissionClientDto.getDocuments().size() == 1,
				"There is a problem with the metadata generation: Metadata must exactly contain 1 entry (only for C32)!");

		// Inject document content and document unique id to metadata
		DocumentClientDto document = sourceSubmissionClientDto.getDocuments()
				.get(0);
		// Assert.isTrue(SpiritConstants.MIME_TYPE_PDF.equalsIgnoreCase(document.getMimeType()),
		// "The document mime type must be PDF for the signed consent PDF document.");
		document.setBytes(c32file);
		document.setUniqueId(SpiritConstants.URN_PREFIX_C32 + UUID.randomUUID());
		document.setName(document.getName());

		String longPatientId = sourceSubmissionClientDto.getSubmissionSet()
				.getPatientId();
		String patientId = longPatientId.substring(0,
				longPatientId.indexOf("^^^"));

		// Login
		String stateId = login();

		// Query patient
		EhrPatientClientListDto ehrPatientClientListDto = queryPatientsWithPids(
				patientId, stateId);

		List<EhrPatientClientDto> patientClientDtoList = ehrPatientClientListDto
				.getEhrPatientClientListDto();
		stateId = ehrPatientClientListDto.getStateId();
		// Assert.isTrue(patientClientDtoList!=null &&
		// patientClientDtoList.size() == 0,
		// "The pateintid "+ patientId + " does not exists in Exchange! ");

		EhrPatientClientDto patientClientDto = patientClientDtoList.get(0);

		// Submit XACML to policy repository
		// EhrPolicySubmitOrUpdateRsp policyRepoResp =
		// submitOrUpdatePolicy(xacmlPolicy, stateId);
		// stateId = policyRepoResp.getStateID();

		// Submit Signed PDF document to XDS.b repository
		XdsSrcSubmitRsp response = submitDocument(patientClientDto,
				sourceSubmissionClientDto, false, stateId);
		stateId = response.getStateID();

		// Logout and return response
		logout(stateId);
		return response;
	}

	@Override
	public SourceSubmissionClientDto generateC32Metadata(String document) {
		String metadataString = c32MetadataGenerator.generateMetadataXml(
				document, domainId, null, null);

		SourceSubmissionClientDto sourceSubmissionClientDto = null;
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(SourceSubmissionClientDto.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<SourceSubmissionClientDto> sourceSubmissionClientDtoElement = jaxbUnmarshaller
					.unmarshal(new StreamSource(
							new StringReader(metadataString)),
							SourceSubmissionClientDto.class);
			sourceSubmissionClientDto = sourceSubmissionClientDtoElement
					.getValue();
		} catch (JAXBException e) {
			throw new DS4PException(e.toString(), e);
		}

		return sourceSubmissionClientDto;
	}

	private void appendRevokedPdfConsent(EhrPatientClientDto patient,
			DocumentClientDto oldDocument, String documentUniqueId,
			byte[] revokedPdfConsent) throws SpiritAdapterException {
		SubmissionSetClientDto oldSubmissionSet = oldDocument
				.getSubmissionSet();
		// Create a SubmissionSet (contains information about the submitter)
		SubmissionSetClientDto submissionSet = new SubmissionSetClientDto();
		// Set some information of the submission
		submissionSet.getAuthor().addAll(oldSubmissionSet.getAuthor());
		submissionSet.setName(oldSubmissionSet.getName());
		submissionSet.setDescription(oldSubmissionSet.getDescription());
		// Set the contentTypeCode
		IheClassificationClientDto contentTypeCode = new IheClassificationClientDto();
		contentTypeCode.setNodeRepresentation(oldSubmissionSet
				.getContentTypeCode().getNodeRepresentation());
		contentTypeCode.setValue(oldSubmissionSet.getContentTypeCode()
				.getValue());
		contentTypeCode.getSchema().addAll(
				oldSubmissionSet.getContentTypeCode().getSchema());
		submissionSet.setContentTypeCode(contentTypeCode);
		// Create a document object
		DocumentClientDto documentToAppend = new DocumentClientDto();
		// Set general information
		// Add author person
		documentToAppend.getAuthor().addAll(oldDocument.getAuthor());
		// Set language code (e.g. DE-TI)
		documentToAppend.setLanguageCode(oldDocument.getLanguageCode());
		// Set description
		documentToAppend.setDescription(oldDocument.getDescription());
		// Set name
		documentToAppend.setName("Revoked Consent PDF: " + documentUniqueId);

		// Set required information
		// Set a file as byte[]
		documentToAppend.setBytes(revokedPdfConsent);
		// Set the MIMEType of the submitFile
		documentToAppend.setMimeType(oldDocument.getMimeType());

		// Some required IHE definitions
		// Set format code
		IheClassificationClientDto formatCode = new IheClassificationClientDto();
		formatCode.setNodeRepresentation(oldDocument.getFormatCode()
				.getNodeRepresentation());
		formatCode.setValue(oldDocument.getFormatCode().getValue());
		formatCode.getSchema().addAll(oldDocument.getFormatCode().getSchema());
		documentToAppend.setFormatCode(formatCode);
		// Set class code
		IheClassificationClientDto classCode = new IheClassificationClientDto();
		classCode.setNodeRepresentation(oldDocument.getClassCode()
				.getNodeRepresentation());
		classCode.setValue(oldDocument.getClassCode().getValue());
		classCode.getSchema().addAll(oldDocument.getClassCode().getSchema());
		documentToAppend.setClassCode(classCode);
		// Set type code
		IheClassificationClientDto typeCode = new IheClassificationClientDto();
		typeCode.setNodeRepresentation(oldDocument.getTypeCode()
				.getNodeRepresentation());
		typeCode.setValue(oldDocument.getTypeCode().getValue());
		typeCode.getSchema().addAll(oldDocument.getTypeCode().getSchema());
		documentToAppend.setTypeCode(typeCode);
		// Set confidentiality code
		IheClassificationClientDto confidentialityCode = new IheClassificationClientDto();
		confidentialityCode.setNodeRepresentation(oldDocument
				.getConfidentialityCode().getNodeRepresentation());
		confidentialityCode.setValue(oldDocument.getConfidentialityCode()
				.getValue());
		confidentialityCode.getSchema().addAll(
				oldDocument.getConfidentialityCode().getSchema());
		documentToAppend.setConfidentialityCode(confidentialityCode);
		// Set healthcare facility code
		IheClassificationClientDto healthcareFacilityCode = new IheClassificationClientDto();
		healthcareFacilityCode.setNodeRepresentation(oldDocument
				.getHealthcareFacilityCode().getNodeRepresentation());
		healthcareFacilityCode.setValue(oldDocument.getHealthcareFacilityCode()
				.getValue());
		healthcareFacilityCode.getSchema().addAll(
				oldDocument.getHealthcareFacilityCode().getSchema());
		documentToAppend.setHealthcareFacilityCode(healthcareFacilityCode);
		// Set practice setting code
		IheClassificationClientDto practiceSettingCode = new IheClassificationClientDto();
		practiceSettingCode.setNodeRepresentation(oldDocument
				.getPracticeSettingCode().getNodeRepresentation());
		practiceSettingCode.setValue(oldDocument.getPracticeSettingCode()
				.getValue());
		practiceSettingCode.getSchema().addAll(
				oldDocument.getPracticeSettingCode().getSchema());
		documentToAppend.setPracticeSettingCode(practiceSettingCode);
		// Create a SourceSubmission (contains the document itself)
		SourceSubmissionClientDto sourceSubmission = new SourceSubmissionClientDto();
		// Set the created document
		sourceSubmission.getDocuments().add(documentToAppend);
		// Set the created SubmissionSet
		sourceSubmission.setSubmissionSet(submissionSet);
		// Create a XdsSourceSubmitRequest
		XdsSrcApRpReq request = new XdsSrcApRpReq();
		// Set the patient
		request.setPatient(patient);
		// Set the document to which the new document should be appended to
		request.setOldDocument(oldDocument);
		// Set the created SourceSubmission
		request.setSrcSubmission(sourceSubmission);

		// Call the webservice-method appendDocument with the created request
		try {
			getWebService().appendDocument(request);
		} catch (EhrException_Exception e) {
			logger.error("webService.appendDocument failed");
			logger.error(e.getMessage(), e);
			throw new SpiritAdapterException(e);
		}
	}

	private String toXdsUniqueId(String xacmlPolicyId) {
		return xacmlPolicyId.replace("&", ":");
	}

	private SpiritEhrWsClientRqRspInterface getWebService() {
		SpiritEhrWsClientRqRspInterface tmp = webService;
		if (tmp == null) {
			synchronized (this) {
				tmp = webService;
				if (tmp == null) {
					tmp = InterfaceFactory
							.createEhrWsRqRspInterface(endpointUrl);
					webService = tmp;
				}
			}
		}
		return tmp;
	}

}
