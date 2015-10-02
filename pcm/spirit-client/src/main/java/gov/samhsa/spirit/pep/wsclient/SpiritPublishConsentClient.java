package gov.samhsa.spirit.pep.wsclient;

import gov.samhsa.spirit.pep.util.SpiritPepUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import com.spirit.ehr.ws.client.generated.EhrException;
import com.spirit.ehr.ws.client.generated.EhrException_Exception;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitOrUpdateRq;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitOrUpdateRsp;
import com.spirit.ehr.ws.client.generated.EhrWsEmptyReq;
import com.spirit.ehr.ws.client.generated.NiUserRequest;
import com.spirit.ehr.ws.client.generated.SpiritSimpleLoginRequest;
import com.spirit.ehr.ws.client.generated.SpiritUserClientDto;
import com.spirit.ehr.ws.client.generated.SpiritUserResponse;
import com.spirit.ehr.ws.interfaces.InterfaceFactory;
import com.spirit.ehr.ws.interfaces.SpiritEhrWsClientRqRspInterface;

public class SpiritPublishConsentClient {

	// Declare the SpiritEhrWsClientRqRspInterface
	private static SpiritEhrWsClientRqRspInterface webService;

	private SpiritPepUtil spiritPepUtil = new SpiritPepUtil();

	/** The logger. */
	private static Logger logger = LoggerFactory
			.getLogger(SpiritPublishConsentClient.class);

	// Constructor which gets the endpoint address of the EhrWsRemote
	public SpiritPublishConsentClient(String endpointUrl) {
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
	 * @param userName
	 *            - The user name used for login
	 * @param userPassword
	 *            - The password of this user
	 * @param organisation
	 *            - The organisation of the user
	 * @param role
	 *            - The role of the user
	 * @return SpiritUserClientDto
	 * @throws Exception
	 */
	public String usrLogin(String userName, String userPassword,
			String organisation, String role) throws Exception {
		try {
			// Print status
			logger.info("One-step-usrLogin: START");

			// Print used login data
			logger.debug("One-step-usrLogin: Used login data: " + userName
					+ " / " + "**********");

			// Create a SimpleLoginRequest (needed to send the login)
			SpiritSimpleLoginRequest loginRequest = new SpiritSimpleLoginRequest();

			// Set login information: username, password, role and organization
			loginRequest.setUser(userName);
			loginRequest.setPwd(userPassword);
			loginRequest.setRol(role);
			loginRequest.setOrg(organisation);

			// Print status
			logger.debug("One-step-usrLogin: Logging in...");

			// Send the SimpleLoginRequest to the webservice
			SpiritUserResponse userResponse = webService.usrLogin(loginRequest);

			// Print status
			logger.debug("One-step-usrLogin: Login successful!");
			logger.debug("One-step-usrLogin: userDN = "
					+ userResponse.getUser().getUserDN());
			logger.debug("One-step-usrLogin: stateId= "
					+ userResponse.getStateID());

			// Print status
			logger.info("One-step-usrLogin: SUCCESS");

			// Return the logged-in user
			return userResponse.getStateID();
		} catch (Exception e) {
			logger.error("One-step-usrLogin: FAILED", e);
			throw e;
		}
	}

	/**
	 * @param spiritSimpleLoginRequest
	 *            - Filled SpiritSimpleLoginRequest
	 * @return SpiritUserClientDto
	 * @throws Exception
	 */
	public String usrLogin(SpiritSimpleLoginRequest spiritSimpleLoginRequest)
			throws Exception {
		try {
			// Print status
			logger.info("One-step-usrLogin: START");

			// Print used login data
			logger.debug("One-step-usrLogin: Used login data: "
					+ spiritSimpleLoginRequest.getUser() + " / "
					+ spiritSimpleLoginRequest.getPwd());

			// Print status
			logger.debug("One-step-usrLogin: Logging in...");

			// Send the SimpleLoginRequest to the webservice
			SpiritUserResponse userResponse = webService
					.usrLogin(spiritSimpleLoginRequest);

			// Print status
			logger.debug("One-step-usrLogin: Login successful!");
			logger.debug("One-step-usrLogin: userDN = "
					+ userResponse.getUser().getUserDN());
			logger.debug("One-step-usrLogin: stateId= "
					+ userResponse.getStateID());

			// Print status
			logger.info("One-step-usrLogin: SUCCESS");

			// Return the logged-in user
			return userResponse.getStateID();
		} catch (Exception e) {
			logger.error("One-step-usrLogin: FAILED", e);
			throw e;
		}
	}

	/**
	 * @param policyList
	 *            - A list of policies to update or submit
	 * @return EhrPolicyUpdateRsp
	 * @throws Exception
	 */
	public EhrPolicySubmitOrUpdateRsp submitOrUpdatePolicies(
			List<byte[]> policyList, String stateId) throws Exception {
		try {
			// Print status
			logger.info("submitOrUpdatePolicies: START");

			// Create a request
			EhrPolicySubmitOrUpdateRq ehrPolicySubmitOrUpdateRq = new EhrPolicySubmitOrUpdateRq();

			// Add the required information of the request
			// Add policies to submit or update
			ehrPolicySubmitOrUpdateRq.getPolicyList().addAll(policyList);
			ehrPolicySubmitOrUpdateRq.setStateID(stateId);

			// Call the webservice-method submitOrUpdatePolicies with the
			// created request
			EhrPolicySubmitOrUpdateRsp ehrPolicySubmitOrUpdateRsp = webService
					.submitOrUpdatePolicies(ehrPolicySubmitOrUpdateRq);

			// Print status
			logger.info("submitOrUpdatePolicies: SUCCESS");

			// Return the response
			return ehrPolicySubmitOrUpdateRsp;
		} catch (Exception e) {
			logger.info("submitOrUpdatePolicies: FAILED", e);
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
	 *
	 * Main-Method
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		try {
			// Instance the client class and pass the endpoint address
			String endpointUrlAndPort = "http://216.59.44.169:8181";
			SpiritPublishConsentClient myEhrClient = new SpiritPublishConsentClient(
					endpointUrlAndPort);

			// Print status
			logger.info("usrLoginExampleWorkflow: START");

			// /////////////////////////////////////////////////////////////////////////////////////
			// (1) Login (usrLogin_1_Step)
			// /////////////////////////////////////////////////////////////////////////////////////

			// Print status
			logger.info("usrLoginExampleWorkflow: Calling usrLogin_1_Step");

			// UsrLogin_1_Step
			String stateId = myEhrClient.usrLogin("wsAdmin", "spirit4c2s",
					"C2S Health", "Admin");

			/*
			 * // UsrLogin_1_Step ClassLoader classloader1 =
			 * Thread.currentThread() .getContextClassLoader(); URL resource1 =
			 * classloader1.getResource("SamlAttributesSample.xml"); File
			 * addAttrFile = new File(resource1.toURI());
			 * 
			 * SpiritUserClientDto loggedInUser =
			 * myEhrClient.initUser("wsAdmin", "Admin", "C2S Health",
			 * addAttrFile);
			 * 
			 * // Print status logger.info("initUserExampleWorkflow: Success" +
			 * loggedInUser.getBase64IdentityAssertion());
			 * 
			 * byte[] decodeds1 =
			 * DatatypeConverter.parseBase64Binary(loggedInUser
			 * .getBase64IdentityAssertion());
			 * 
			 * // Print status logger.info("decoded string : " + new
			 * String(decodeds1));
			 */

			// /////////////////////////////////////////////////////////////////////////////////////
			// (2) Load the PAP-Configuration (loadPapConfiguration)
			// /////////////////////////////////////////////////////////////////////////////////////

			// Print status
			logger.info("submitOrUpdatePoliciesExampleWorkflow: Calling loadPapConfiguration");

			// Load the PAP-Configuration
			ClassLoader classloader = Thread.currentThread()
					.getContextClassLoader();
			URL resource2 = classloader.getResource("XACMLPolicy.xml");
			// File xacmlFile = new File(resource2.toURI());
			Path path = Paths.get(resource2.toURI());
			byte[] policiesToSubmitOrUpdate = Files.readAllBytes(path);
			// List<byte[]> policiesToSubmitOrUpdate = xacmlFile.rea
			// /////////////////////////////////////////////////////////////////////////////////////
			// (3) Submit Or Update Policies (submitOrUpdatePolicies)
			// /////////////////////////////////////////////////////////////////////////////////////

			// Print status
			logger.info("submitOrUpdatePoliciesExampleWorkflow: Calling submitOrUpdatePolicies");

			// SubmitOrUpdatePolicies
			EhrPolicySubmitOrUpdateRsp ehrPolicySubmitOrUpdateRsp = myEhrClient
					.submitOrUpdatePolicies(
							Arrays.asList(policiesToSubmitOrUpdate), stateId);

			// Print status
			logger.info("ehrPolicySubmitOrUpdateRsp:"
					+ ehrPolicySubmitOrUpdateRsp.getStateID());

			// /////////////////////////////////////////////////////////////////////////////////////
			// (4) Logout (usrLogout)
			// /////////////////////////////////////////////////////////////////////////////////////

			// Print status
			logger.info("submitOrUpdatePoliciesExampleWorkflow: Calling usrLogout");

			// UsrLogout
			myEhrClient.usrLogout();

			// Print status
			logger.info("submitOrUpdatePoliciesExampleWorkflow: SUCCESS");

		} catch (Exception e) {
			logger.error("initUserExampleWorkflow: FAILED", e);
			throw e;
		}

	}

}
