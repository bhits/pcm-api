package gov.samhsa.spirit.pep.wsclient;

import gov.samhsa.spirit.pep.util.SpiritPepUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import com.spirit.ehr.ws.client.generated.DocumentClientDto;
import com.spirit.ehr.ws.client.generated.EhrDomainClientDto;
import com.spirit.ehr.ws.client.generated.EhrException;
import com.spirit.ehr.ws.client.generated.EhrException_Exception;
import com.spirit.ehr.ws.client.generated.EhrPIDClientDto;
import com.spirit.ehr.ws.client.generated.EhrPatIdReq;
import com.spirit.ehr.ws.client.generated.EhrPatIdRsp;
import com.spirit.ehr.ws.client.generated.EhrPatientClientDto;
import com.spirit.ehr.ws.client.generated.EhrPatientRq;
import com.spirit.ehr.ws.client.generated.EhrPatientRsp;
import com.spirit.ehr.ws.client.generated.EhrWsEmptyReq;
import com.spirit.ehr.ws.client.generated.EhrXdsQDocumentRq;
import com.spirit.ehr.ws.client.generated.EhrXdsQRsp;
import com.spirit.ehr.ws.client.generated.EhrXdsRetrReq;
import com.spirit.ehr.ws.client.generated.EhrXdsRetrRsp;
import com.spirit.ehr.ws.client.generated.IheClassificationClientDto;
import com.spirit.ehr.ws.client.generated.NiUserRequest;
import com.spirit.ehr.ws.client.generated.SpiritUserClientDto;
import com.spirit.ehr.ws.client.generated.XdsQArgsDocument;
import com.spirit.ehr.ws.interfaces.InterfaceFactory;
import com.spirit.ehr.ws.interfaces.SpiritEhrWsClientRqRspInterface;

public class SpiritDocConsumerClient {

	// Declare the SpiritEhrWsClientRqRspInterface
	private static SpiritEhrWsClientRqRspInterface webService;

	private SpiritPepUtil spiritPepUtil = new SpiritPepUtil();

	private static String endpointAddress;
	private static String org;
	private static String rol;
	private static String user;
	private static String domain;

	/** The logger. */
	private static Logger logger = LoggerFactory
			.getLogger(SpiritDocConsumerClient.class);

	// Constructor which gets the endpoint address of the EhrWsRemote
	public SpiritDocConsumerClient(String endpointUrl) {
		// Initialize via InterfaceFactory
		webService = InterfaceFactory.createEhrWsRqRspInterface(endpointUrl);
	}

	/**
	 * @param usrName
	 *            - the usrName used for login
	 * @param role
	 *            - the role to login for
	 * @param org
	 *            - the organisation to log into
	 * @param addAttibXml
	 *            - the SAML2-xml-file holding some additional attributes
	 * @return
	 * @throws Exception
	 */
	public SpiritUserClientDto initUser(String usrName, String role,
			String org, File addAttibXml) throws Exception {
		try {

			// Parse an xml holding saml2-attributes
			Element[] additionalAttributes = SpiritPepUtil.parse(addAttibXml);

			NiUserRequest rq = new NiUserRequest();

			rq.setUsername(usrName);
			rq.setRole(role);
			rq.setOrganisation(org);

			if (additionalAttributes != null) {
				Document document = spiritPepUtil
						.elementArrayToDocument(additionalAttributes);
				DOMImplementationLS domImplementation = (DOMImplementationLS) document
						.getImplementation();
				LSOutput lsOutput = domImplementation.createLSOutput();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				lsOutput.setByteStream(byteArrayOutputStream);

				try {
					LSSerializer lsSerializer = domImplementation
							.createLSSerializer();
					lsSerializer.write(document, lsOutput);
				} catch (Exception e) {
					e.printStackTrace();
					throw new EhrException_Exception(e.getMessage(),
							new EhrException(), e);
				}
				rq.setAdditionalAttributes(byteArrayOutputStream.toByteArray());
			}

			// Init User
			SpiritUserClientDto usr = webService.initUser(rq).getUser();

			logger.info("initUser: SUCCESS");

			return usr;
		} catch (Exception e) {
			logger.error("initUser: FAILED", e);
			throw e;
		}
	}

	/**
	 * @throws Exception
	 */
	public void usrLogout() throws Exception {
		try {
			webService.usrLogout(new EhrWsEmptyReq());
			logger.info("usrLogout: SUCCESS");
		} catch (Exception e) {
			logger.error("usrLogout: FAILED", e);
			throw e;
		}
	}

	/**
	 * @param usrName
	 *            - the usrName used for login
	 * @param pwd
	 *            - the password of this user
	 * @return SpiritUserClientDto
	 * @throws Exception
	 */
	public List<EhrPatientClientDto> queryPatients(String familyName,
			String givenName, String country) throws Exception {
		try {
			// Create a patient object
			EhrPatientClientDto searchPatient = new EhrPatientClientDto();

			// Set the patient information to look for
			// There are many more search arguments you can use not only these
			// three
			searchPatient.setFamilyName(familyName);
			searchPatient.setGivenName(givenName);
			searchPatient.setCountry(country);

			// Create a PatientRequest
			EhrPatientRq patientRequest = new EhrPatientRq();

			// Pass the patient, to look for, to the PatientRequest
			patientRequest.setRequestData(searchPatient);

			// Print status
			logger.info("queryPatients: Looking for: " + familyName + " / "
					+ givenName + " / " + country);

			// Call the webservice
			EhrPatientRsp patientResponse = webService
					.queryPatients(patientRequest);

			// Print status
			logger.info("queryPatients: Success!");

			List<EhrPatientClientDto> pts = patientResponse.getResponseData();
			// Return the list of found patients
			return patientResponse.getResponseData();
		} catch (Exception e) {
			logger.error("queryPatients: FAILED", e);
			throw e;
		}
	}

	/**
	 * @param wantedDocument
	 *            - the document you want to retrieve
	 * @return EhrXdsRetrRsp
	 * @throws Exception
	 */
	public EhrXdsRetrRsp retrieveDocument(DocumentClientDto wantedDocument)
			throws Exception {
		try {
			logger.info("retrieveDocument: START");

			// Create a the retrieveDocumentRequest
			EhrXdsRetrReq request = new EhrXdsRetrReq();

			// Set the required information of the request
			// Set the selected document
			request.setRequestData(wantedDocument);

			// Call the webservice-method retrieveDocument with the created
			// request
			EhrXdsRetrRsp response = webService.retrieveDocument(request);

			logger.info("retrieveDocument: SUCCESS");

			return response;
		} catch (Exception e) {
			logger.error("retrieveDocument: FAILED", e);
			throw e;
		}
	}

	private static void readWSconfig(ClassLoader classloader)
			throws URISyntaxException, IOException {

		URL resource = classloader.getResource("spirit.properties");
		File file = new File(resource.toURI());
		FileInputStream fis = new FileInputStream(file);
		Properties props = new Properties();
		props.load(fis);

		// reading proeprty
		endpointAddress = props.getProperty("spirit.client.endpointAddress");
		org = props.getProperty("spirit.client.org");
		rol = props.getProperty("spirit.client.rol");
		user = props.getProperty("spirit.client.user");
		domain = props.getProperty("spirit.client.domainId");

	}

	private File getSAMLInputs(ClassLoader classLoader)
			throws URISyntaxException {
		URL resource2 = classLoader.getResource("SamlAttributesSample.xml");
		File addAttrFile = new File(resource2.toURI());
		return addAttrFile;

	}

	/**
	 * @param patientId
	 *            -
	 * @param domainId
	 *            -
	 * @param domainIdType
	 *            -
	 * @return SpiritUserClientDto
	 * @throws Exception
	 */
	public List<EhrPIDClientDto> queryPatientIds(String patientId,
			String domainId, String domainIdType) throws Exception {
		try {
			// Print status
			logger.info("queryPatientIds: START");

			// Create a EhrPIDClientDto (holds a PID)
			EhrPIDClientDto patientPid = new EhrPIDClientDto();

			// Set the given patientId
			patientPid.setPatientID(patientId);

			// Create a EhrDomainClientDto (holds the domainId and domainType)
			EhrDomainClientDto patientDomain = new EhrDomainClientDto();

			// Set the given domainId and domainType
			patientDomain.setAuthUniversalID(domainId);
			patientDomain.setAuthUniversalIDType(domainIdType);

			// Put the domain into the EhrPIDClientDto
			patientPid.setDomain(patientDomain);

			// Create a PatientRequest
			EhrPatIdReq patientIdRequest = new EhrPatIdReq();

			// Add the PID to the request
			patientIdRequest.getRequestData().add(patientPid);

			// Print status
			logger.info("queryPatientIds: Looking for patientIds...");

			// Call the webservice
			EhrPatIdRsp patientIdResponse = webService
					.queryPatientIds(patientIdRequest);

			// Print status
			logger.info("queryPatientIds: SUCCESS");

			List<EhrPIDClientDto> queryPatientIds = patientIdResponse
					.getResponseData();
			// EhrpqueryPatientIds.get(0).getPatientIDType();
			logger.info("pid " + queryPatientIds.get(0).getPatientIDType());
			// Return the list of all found Pids of the patient
			return patientIdResponse.getResponseData();
		} catch (Exception e) {
			logger.error("queryPatientIds: FAILED", e);
			throw e;
		}
	}

	/**
	 *
	 * Main-Method
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		try {

			ClassLoader classloader = Thread.currentThread()
					.getContextClassLoader();

			// read spirit ws client related configurations
			readWSconfig(classloader);

			// Instance the client class and pass the endpoint address
			SpiritDocConsumerClient myEhrClient = new SpiritDocConsumerClient(
					endpointAddress);

			// Print status

			logger.info("retrieveDocumentExampleWorkflow: START");

			logger.info("retrieveDocumentExampleWorkflow: Configurations: UserName:"
					+ user
					+ "\tRole:"
					+ rol
					+ "\torg:"
					+ org
					+ "\tendpointAddress:" + endpointAddress);

			// /////////////////////////////////////////////////////////////////////////////////////
			// (1) Login (initUser UserRequest)
			// /////////////////////////////////////////////////////////////////////////////////////

			// Print status
			logger.info("retrieveDocumentExampleWorkflow: Calling initUser_UserRequest");

			// read SAML Inputs
			File addAttrFile = myEhrClient.getSAMLInputs(classloader);

			// UsrLogin_1_Step
			SpiritUserClientDto loggedInUser = myEhrClient.initUser(user, rol,
					org, addAttrFile);

			// Print status
			logger.info("retrieveDocumentExampleWorkflow: Success"
					+ loggedInUser.getBase64IdentityAssertion());

			byte[] decodeds1 = DatatypeConverter.parseBase64Binary(loggedInUser
					.getBase64IdentityAssertion());

			// Print status
			logger.info("decoded string : " + new String(decodeds1));

			// /////////////////////////////////////////////////////////////////////////////////////
			// (2) Query patient (queryPatientIds)
			// /////////////////////////////////////////////////////////////////////////////////////

			// Print status
			logger.info("queryPatientIds: Looking for patientIds...");

			// QueryPatientsWithDemographics
			List<EhrPIDClientDto> listFoundPatients = myEhrClient
					.queryPatientIds("REG.1DSQPGACKF",
							"2.16.840.1.113883.3.704.100.990.1", "ISO");

			// Select patient
			EhrPIDClientDto selectedPatient = listFoundPatients.get(0);
			logger.info("selected patient : " + selectedPatient.getPatientID());

			// Print status
			logger.info("queryDocumentsWorkflowExample: Calling queryPatientsWithDemographics");

			// // QueryPatientsWithDemographics
			// List<EhrPatientClientDto> listFoundPatients =
			// myEhrClient.queryPatients("Smith", "Albert", "USA");
			//
			// // Select patient
			// EhrPatientClientDto selectedPatient = listFoundPatients.get(0);
			// logger.info("selected patient : " + selectedPatient.getPid());
			//
			// // // Select patient
			// EhrPIDClientDto selectedPatient = listFoundPatients.get(0);
			// logger.info("selected patient : " +
			// selectedPatient.getPatientID());

			// /////////////////////////////////////////////////////////////////////////////////////
			// (3) Query a document (queryDocuments)
			// /////////////////////////////////////////////////////////////////////////////////////

			// Print status
			logger.info("queryDocumentsWorkflowExample: Calling queryDocuments");

			// Create a XdsQArgsDocument (Holds the search-parameter)
			XdsQArgsDocument searchArguments = new XdsQArgsDocument();

			// Set the search-arguments (e.g. author, documentStatus,
			// formatCode)
			// Set author
			// searchArguments.getAuthorPersons().add("1568797520");

			// Set documentStatus
			searchArguments.getDocumentStatus().add("Approved");

			// Set formatCode
			IheClassificationClientDto formatCode = new IheClassificationClientDto();
			formatCode.setNodeRepresentation("2.16.840.1.113883.10.20.1");
			formatCode.setValue("HL7 CCD Document");
			formatCode.getSchema().add("HITSP");
			searchArguments.getFormatCodes().add(formatCode);

			// Create the documentRequest
			EhrXdsQDocumentRq documentRequest = new EhrXdsQDocumentRq();

			// Set the required information of the documentRequest
			// Set the created XdsQArgsDocument
			documentRequest.setXdsQArgsDocument(searchArguments);

			// Set the PIDs of the selected patient
			documentRequest.getRequestData().addAll(listFoundPatients);

			// Call the webservice-method queryDocuments with the created
			// documentRequest
			EhrXdsQRsp response = webService.queryDocuments(documentRequest);

			// The response contains:
			// The patient content
			logger.info("rsp " + response.getResponseData().toString());
			System.out.println("rsp "
					+ response.getResponseData().getDocuments());

			// Select a document
			DocumentClientDto selectedDocument = response.getResponseData()
					.getDocuments().get(0);

			// /////////////////////////////////////////////////////////////////////////////////////
			// (4) Retrieve document (retrieveDocument)
			// /////////////////////////////////////////////////////////////////////////////////////

			// Print status
			logger.info("retrieveDocumentExampleWorkflow: Calling retrieveDocument");

			// Create a the request
			EhrXdsRetrReq request = new EhrXdsRetrReq();

			// Set the required information of the request
			// Set the selected document
			request.setRequestData(selectedDocument);

			// Call the webservice-method retrieveDocument with the created
			// request
			EhrXdsRetrRsp retrieveResponse = webService
					.retrieveDocument(request);

			// The response contains amongst other things:
			// The document as byte[]
			byte[] segDoc = retrieveResponse.getDocument();

			// The document's uniqueId as String
			retrieveResponse.getDocumentUniqueId();

			// The documents's mimeType as String
			retrieveResponse.getMimeType();

			// Print status
			logger.info("Segmented C32 string : " + new String(segDoc));
			// /////////////////////////////////////////////////////////////////////////////////////
			// (5) Logout (usrLogout)
			// /////////////////////////////////////////////////////////////////////////////////////

			// Print status
			logger.info("retrieveDocumentExampleWorkflow: Calling usrLogout");

			// UsrLogout
			myEhrClient.usrLogout();

			// Print status
			logger.info("retrieveDocumentExampleWorkflow: SUCCESS");
		} catch (Exception e) {
			logger.error("retrieveDocumentExampleWorkflow: FAILED", e);
			throw e;
		}

	}

}
