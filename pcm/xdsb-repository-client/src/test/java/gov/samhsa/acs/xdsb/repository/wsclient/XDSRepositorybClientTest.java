package gov.samhsa.acs.xdsb.repository.wsclient;

import gov.samhsa.acs.xdsb.repository.wsclient.exception.XdsbRepositoryClientException;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest.Document;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.XDSRepository;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class XDSRepositorybClientTest {

	protected Endpoint ep;
	protected String address;
	private ihe.iti.xds_b._2007.RetrieveDocumentSetResponse returnedValueOfRetrieveDocumentSet;
	private RegistryResponse provideAndRegisterDocumentSet;

	@Before
	public void setUp() {
		try {
			final Resource resource = new ClassPathResource(
					"/jettyServerPortForTesing.properties");
			final Properties props = PropertiesLoaderUtils
					.loadProperties(resource);
			final String portNumber = props
					.getProperty("jettyServerPortForTesing.number");

			address = String.format(
					"http://localhost:%s/services/xdsrepositoryb", portNumber);

			ep = Endpoint.publish(address, new XdsRepositorybImpl());

			XdsRepositorybImpl.returnedValueOfProvideAndRegisterDocumentSet = provideAndRegisterDocumentSet;
			XdsRepositorybImpl.returnedValueOfRetrieveDocumentSet = returnedValueOfRetrieveDocumentSet;

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		try {
			ep.stop();
		} catch (final Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	// Test if the stub web service activate properly
	@Test
	public void testStubWebServiceWorks_provideAndRegisterDocumentSetRequest()
			throws JAXBException {
		// Arrange
		final String submitObjectRequestXml = "<tns:SubmitObjectsRequest xmlns:tns=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\"> <RegistryObjectList xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"> <ObjectRef id=\"urn:uuid:7edca82f-054d-47f2-a032-9b2a5b518fff\" /> <ObjectRef id=\"urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd\" /> <ObjectRef id=\"urn:uuid:f64ffdf0-4b97-4e06-b79f-a52b38ec2f8a\" /> <ObjectRef id=\"urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8\" /> <ObjectRef id=\"urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832\" /> <ObjectRef id=\"urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446\" /> <ObjectRef id=\"urn:uuid:d9d542f3-6cc4-48b6-8870-ea235fbc94c2\" /> <ObjectRef id=\"urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1\" /> <ObjectRef id=\"urn:uuid:aa543740-bdda-424e-8c96-df4873be8500\" /> <ObjectRef id=\"urn:uuid:75df8f67-9973-4fbe-a900-df66cefecc5a\" /> <ObjectRef id=\"urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f\" /> <ObjectRef id=\"urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d\" /> <ObjectRef id=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\" /> <ObjectRef id=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\" /> <ObjectRef id=\"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a\" /> <ObjectRef id=\"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab\" /> <ObjectRef id=\"urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427\" /> <ObjectRef id=\"urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d\" /> <ObjectRef id=\"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d\" /> <ExtrinsicObject mimeType=\"text/xml\" objectType=\"urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1\" id=\"Document01\"> <Slot name=\"creationTime\"> <ValueList> <Value>20110203</Value> </ValueList> </Slot> <Slot name=\"languageCode\"> <ValueList> <Value>en-US</Value> </ValueList> </Slot> <Slot name=\"sourcePatientId\"> <ValueList> <Value>100015060001^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> </ValueList> </Slot> <Slot name=\"sourcePatientInfo\"> <ValueList> <Value>PID-3|100015060001^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> <Value>PID-5|Patientone^Asample^^^</Value> <Value>PID-7|19710510</Value> <Value>PID-8|M</Value> <Value>PID-11|14235 South St^^Baltimore^Maryland^21075^</Value> </ValueList> </Slot> <Slot name=\"intendedRecipient\"> <ValueList> <Value>^^Internet^Duane_Decouteau@direct.healthvault-stage.com </Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:authorTelecommunication\"> <ValueList> <Value>^^Internet^leo.smith@direct.obhita-stage.org</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:purposeofuse\"> <ValueList> <Value>TREAT</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:obligationpolicy\"> <ValueList> <Value>ENCRYPT</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:refrainpolicy\"> <ValueList> <Value>NORDSLCD</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:sensitivitypolicy\"> <ValueList> <Value>HIV</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:usprivacylaw\"> <ValueList> <Value>42CFRPart2</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Clinic Personal Health Record Extract\" /> </Name> <Description /> <Classification classificationScheme=\"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d\" classifiedObject=\"Document01\" id=\"urn:uuid:4e1d56a3-cf98-9deb-cfa2-5686b221778e\" nodeRepresentation=\"\"> <Slot name=\"authorPerson\"> <ValueList> <Value>100010020002^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> </ValueList> </Slot> <Slot name=\"authorInstitution\"> <ValueList> <Value>XYZ Clinic</Value> </ValueList> </Slot> <Slot name=\"authorRole\"> <ValueList> <Value>Provider</Value> </ValueList> </Slot> <Slot name=\"authorSpecialty\"> <ValueList> <Value>General Medicine</Value> </ValueList> </Slot> </Classification> <Classification id=\"urn:uuid:81dfbd93-a50b-c603-c867-50640424eb1d\" classificationScheme=\"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a\" classifiedObject=\"Document01\" nodeRepresentation=\"Consult\"> <Slot name=\"codingScheme\"> <ValueList> <Value>Connect-a-thon classCodes</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Consult Note\" /> </Name> </Classification> <Classification id=\"cl03\" classificationScheme=\"urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f\" classifiedObject=\"Document01\" nodeRepresentation=\"R\"> <Slot name=\"codingScheme\"> <ValueList> <Value>2.16.840.1.113883.5.25</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"R\" /> </Name> </Classification> <Classification id=\"urn:uuid:fb57bcf4-7f7a-28ab-c29c-fe067854abbd\" classificationScheme=\"urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d\" classifiedObject=\"Document01\" nodeRepresentation=\"2.16.840.1.113883.10.20.1\"> <Slot name=\"codingScheme\"> <ValueList> <Value>HITSP</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"HL7 CCD Document\" /> </Name> </Classification> <Classification id=\"urn:uuid:a6e536d1-b496-4006-e243-5c94b6db7c07\" classificationScheme=\"urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1\" classifiedObject=\"Document01\" nodeRepresentation=\"OF\"> <Slot name=\"codingScheme\"> <ValueList> <Value>2.16.840.1.113883.5.11</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Outpatient facility\" /> </Name> </Classification> <Classification id=\"urn:uuid:241be963-c4f3-d799-a6a5-d5a4636a8f0f\" classificationScheme=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\" classifiedObject=\"Document01\" nodeRepresentation=\"Psychiatry\"> <Slot name=\"codingScheme\"> <ValueList> <Value>Connect-a-thon practiceSettingCodes</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Psychiatry\" /> </Name> </Classification> <Classification id=\"urn:uuid:7aec7490-a293-12ae-d70b-8976b4f1f703\" classificationScheme=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\" classifiedObject=\"Document01\" nodeRepresentation=\"34133-9\"> <Slot name=\"codingScheme\"> <ValueList> <Value>LOINC</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Summarization of episode note\" /> </Name> </Classification> <ExternalIdentifier id=\"urn:uuid:9afb1c3f-942c-6676-77e2-38fdc3f32a47\" registryObject=\"Document01\" identificationScheme=\"urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427\" value=\"$PatientId\"> <Name> <LocalizedString value=\"XDSDocumentEntry.patientId\" /> </Name> </ExternalIdentifier> <ExternalIdentifier id=\"urn:uuid:267c9cd2-29e2-81e5-0a78-9bb078c59ec3\" registryObject=\"Document01\" identificationScheme=\"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab\" value=\"2009.9.1.2500\"> <Name> <LocalizedString value=\"XDSDocumentEntry.uniqueId\" /> </Name> </ExternalIdentifier> </ExtrinsicObject> <RegistryPackage id=\"SubmissionSet\"> <Slot name=\"submissionTime\"> <ValueList> <Value>20110203</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Submission Set\" /> </Name> <Description> <LocalizedString value=\"This Submission Set contains a Clinical Exchange Document\" /> </Description> <Classification id=\"urn:uuid:0ca4ed97-5d52-9f4e-e17e-37b1e8964909\" classificationScheme=\"urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d\" classifiedObject=\"SubmissionSet\" nodeRepresentation=\"\"> <Slot name=\"authorPerson\"> <ValueList> <Value>100010020002^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> </ValueList> </Slot> <Slot name=\"authorInstitution\"> <ValueList> <Value>XYZ Clinic</Value> </ValueList> </Slot> <Slot name=\"authorRole\"> <ValueList> <Value>Provider</Value> </ValueList> </Slot> <Slot name=\"authorSpecialty\"> <ValueList> <Value>General Medicine</Value> </ValueList> </Slot> </Classification> <Classification id=\"urn:uuid:2d7456e0-d449-6b74-27f0-1f870aa21be2\" classificationScheme=\"urn:uuid:aa543740-bdda-424e-8c96-df4873be8500\" classifiedObject=\"SubmissionSet\" nodeRepresentation=\"Summarization of episode\"> <Slot name=\"codingScheme\"> <ValueList> <Value>Connect-a-thon contentTypeCodes</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Summarization of episode\" /> </Name> </Classification> <ExternalIdentifier id=\"urn:uuid:90673650-b2d7-25b8-ac08-f5966d228fc8\" registryObject=\"SubmissionSet\" identificationScheme=\"urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8\" value=\"129.6.58.42.33727\"> <Name> <LocalizedString value=\"XDSSubmissionSet.uniqueId\" /> </Name> </ExternalIdentifier> <ExternalIdentifier id=\"urn:uuid:73ea59e0-4d41-4bda-03e4-4a5cc2f1ce3b\" registryObject=\"SubmissionSet\" identificationScheme=\"urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832\" value=\"1.3.6.1.4.1.21367.2005.3.999.901\"> <Name> <LocalizedString value=\"XDSSubmissionSet.sourceId\" /> </Name> </ExternalIdentifier> <ExternalIdentifier id=\"urn:uuid:6e11c871-91c3-0206-9df2-0cb245d2e888\" registryObject=\"SubmissionSet\" identificationScheme=\"urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446\" value=\"$PatientId\"> <Name> <LocalizedString value=\"XDSSubmissionSet.patientId\" /> </Name> </ExternalIdentifier> </RegistryPackage> <Classification id=\"urn:uuid:316d4506-6afc-ff81-486e-2d137e36e01c\" classifiedObject=\"SubmissionSet\" classificationNode=\"urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd\" /> <Association id=\"urn:uuid:a02892f9-fb97-bc72-7e3b-85a73f4f170e\" associationType=\"HasMember\" sourceObject=\"SubmissionSet\" targetObject=\"Document01\"> <Slot name=\"SubmissionSetStatus\"> <ValueList> <Value>Original</Value> </ValueList> </Slot> </Association> </RegistryObjectList> </tns:SubmitObjectsRequest>";
		final SubmitObjectsRequest submitObjectRequest = unmarshallFromXml(
				SubmitObjectsRequest.class, submitObjectRequestXml);

		final Document document = new Document();
		document.setId("Document01");
		document.setValue("xyz".getBytes());

		final ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		request.setSubmitObjectsRequest(submitObjectRequest);
		request.getDocument().add(document);

		// Act
		final Object response = createPort().provideAndRegisterDocumentSet(
				request);

		// Assert
		validateResponseOfProvideAndRegisterDocumentSet(response);
	}

	// Test if the stub web service activate properly
	@Test
	public void testStubWebServiceWorks_retrieveDocumentSetRequest() {
		// Arrange
		final RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
		final DocumentRequest documentRequest = new DocumentRequest();
		documentRequest.setHomeCommunityId("HC");
		documentRequest
				.setRepositoryUniqueId("1.3.6.1.4.1.21367.2010.1.2.1040");
		documentRequest.setDocumentUniqueId("$uniqueId06");
		request.getDocumentRequest().add(documentRequest);

		// Act
		final Object response = createPort().retrieveDocumentSet(request);

		// Assert
		validateResponseOfRetrieveDocumentSetRequest(response);
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks_provideAndRegisterDocumentSetRequest()
			throws JAXBException {
		// Arrange
		final String submitObjectRequestXml = "<tns:SubmitObjectsRequest xmlns:tns=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\"> <RegistryObjectList xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"> <ObjectRef id=\"urn:uuid:7edca82f-054d-47f2-a032-9b2a5b518fff\" /> <ObjectRef id=\"urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd\" /> <ObjectRef id=\"urn:uuid:f64ffdf0-4b97-4e06-b79f-a52b38ec2f8a\" /> <ObjectRef id=\"urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8\" /> <ObjectRef id=\"urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832\" /> <ObjectRef id=\"urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446\" /> <ObjectRef id=\"urn:uuid:d9d542f3-6cc4-48b6-8870-ea235fbc94c2\" /> <ObjectRef id=\"urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1\" /> <ObjectRef id=\"urn:uuid:aa543740-bdda-424e-8c96-df4873be8500\" /> <ObjectRef id=\"urn:uuid:75df8f67-9973-4fbe-a900-df66cefecc5a\" /> <ObjectRef id=\"urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f\" /> <ObjectRef id=\"urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d\" /> <ObjectRef id=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\" /> <ObjectRef id=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\" /> <ObjectRef id=\"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a\" /> <ObjectRef id=\"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab\" /> <ObjectRef id=\"urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427\" /> <ObjectRef id=\"urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d\" /> <ObjectRef id=\"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d\" /> <ExtrinsicObject mimeType=\"text/xml\" objectType=\"urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1\" id=\"Document01\"> <Slot name=\"creationTime\"> <ValueList> <Value>20110203</Value> </ValueList> </Slot> <Slot name=\"languageCode\"> <ValueList> <Value>en-US</Value> </ValueList> </Slot> <Slot name=\"sourcePatientId\"> <ValueList> <Value>100015060001^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> </ValueList> </Slot> <Slot name=\"sourcePatientInfo\"> <ValueList> <Value>PID-3|100015060001^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> <Value>PID-5|Patientone^Asample^^^</Value> <Value>PID-7|19710510</Value> <Value>PID-8|M</Value> <Value>PID-11|14235 South St^^Baltimore^Maryland^21075^</Value> </ValueList> </Slot> <Slot name=\"intendedRecipient\"> <ValueList> <Value>^^Internet^Duane_Decouteau@direct.healthvault-stage.com </Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:authorTelecommunication\"> <ValueList> <Value>^^Internet^leo.smith@direct.obhita-stage.org</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:purposeofuse\"> <ValueList> <Value>TREAT</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:obligationpolicy\"> <ValueList> <Value>ENCRYPT</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:refrainpolicy\"> <ValueList> <Value>NORDSLCD</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:sensitivitypolicy\"> <ValueList> <Value>HIV</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:usprivacylaw\"> <ValueList> <Value>42CFRPart2</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Clinic Personal Health Record Extract\" /> </Name> <Description /> <Classification classificationScheme=\"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d\" classifiedObject=\"Document01\" id=\"urn:uuid:4e1d56a3-cf98-9deb-cfa2-5686b221778e\" nodeRepresentation=\"\"> <Slot name=\"authorPerson\"> <ValueList> <Value>100010020002^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> </ValueList> </Slot> <Slot name=\"authorInstitution\"> <ValueList> <Value>XYZ Clinic</Value> </ValueList> </Slot> <Slot name=\"authorRole\"> <ValueList> <Value>Provider</Value> </ValueList> </Slot> <Slot name=\"authorSpecialty\"> <ValueList> <Value>General Medicine</Value> </ValueList> </Slot> </Classification> <Classification id=\"urn:uuid:81dfbd93-a50b-c603-c867-50640424eb1d\" classificationScheme=\"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a\" classifiedObject=\"Document01\" nodeRepresentation=\"Consult\"> <Slot name=\"codingScheme\"> <ValueList> <Value>Connect-a-thon classCodes</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Consult Note\" /> </Name> </Classification> <Classification id=\"cl03\" classificationScheme=\"urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f\" classifiedObject=\"Document01\" nodeRepresentation=\"R\"> <Slot name=\"codingScheme\"> <ValueList> <Value>2.16.840.1.113883.5.25</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"R\" /> </Name> </Classification> <Classification id=\"urn:uuid:fb57bcf4-7f7a-28ab-c29c-fe067854abbd\" classificationScheme=\"urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d\" classifiedObject=\"Document01\" nodeRepresentation=\"2.16.840.1.113883.10.20.1\"> <Slot name=\"codingScheme\"> <ValueList> <Value>HITSP</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"HL7 CCD Document\" /> </Name> </Classification> <Classification id=\"urn:uuid:a6e536d1-b496-4006-e243-5c94b6db7c07\" classificationScheme=\"urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1\" classifiedObject=\"Document01\" nodeRepresentation=\"OF\"> <Slot name=\"codingScheme\"> <ValueList> <Value>2.16.840.1.113883.5.11</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Outpatient facility\" /> </Name> </Classification> <Classification id=\"urn:uuid:241be963-c4f3-d799-a6a5-d5a4636a8f0f\" classificationScheme=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\" classifiedObject=\"Document01\" nodeRepresentation=\"Psychiatry\"> <Slot name=\"codingScheme\"> <ValueList> <Value>Connect-a-thon practiceSettingCodes</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Psychiatry\" /> </Name> </Classification> <Classification id=\"urn:uuid:7aec7490-a293-12ae-d70b-8976b4f1f703\" classificationScheme=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\" classifiedObject=\"Document01\" nodeRepresentation=\"34133-9\"> <Slot name=\"codingScheme\"> <ValueList> <Value>LOINC</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Summarization of episode note\" /> </Name> </Classification> <ExternalIdentifier id=\"urn:uuid:9afb1c3f-942c-6676-77e2-38fdc3f32a47\" registryObject=\"Document01\" identificationScheme=\"urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427\" value=\"$PatientId\"> <Name> <LocalizedString value=\"XDSDocumentEntry.patientId\" /> </Name> </ExternalIdentifier> <ExternalIdentifier id=\"urn:uuid:267c9cd2-29e2-81e5-0a78-9bb078c59ec3\" registryObject=\"Document01\" identificationScheme=\"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab\" value=\"2009.9.1.2500\"> <Name> <LocalizedString value=\"XDSDocumentEntry.uniqueId\" /> </Name> </ExternalIdentifier> </ExtrinsicObject> <RegistryPackage id=\"SubmissionSet\"> <Slot name=\"submissionTime\"> <ValueList> <Value>20110203</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Submission Set\" /> </Name> <Description> <LocalizedString value=\"This Submission Set contains a Clinical Exchange Document\" /> </Description> <Classification id=\"urn:uuid:0ca4ed97-5d52-9f4e-e17e-37b1e8964909\" classificationScheme=\"urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d\" classifiedObject=\"SubmissionSet\" nodeRepresentation=\"\"> <Slot name=\"authorPerson\"> <ValueList> <Value>100010020002^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> </ValueList> </Slot> <Slot name=\"authorInstitution\"> <ValueList> <Value>XYZ Clinic</Value> </ValueList> </Slot> <Slot name=\"authorRole\"> <ValueList> <Value>Provider</Value> </ValueList> </Slot> <Slot name=\"authorSpecialty\"> <ValueList> <Value>General Medicine</Value> </ValueList> </Slot> </Classification> <Classification id=\"urn:uuid:2d7456e0-d449-6b74-27f0-1f870aa21be2\" classificationScheme=\"urn:uuid:aa543740-bdda-424e-8c96-df4873be8500\" classifiedObject=\"SubmissionSet\" nodeRepresentation=\"Summarization of episode\"> <Slot name=\"codingScheme\"> <ValueList> <Value>Connect-a-thon contentTypeCodes</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Summarization of episode\" /> </Name> </Classification> <ExternalIdentifier id=\"urn:uuid:90673650-b2d7-25b8-ac08-f5966d228fc8\" registryObject=\"SubmissionSet\" identificationScheme=\"urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8\" value=\"129.6.58.42.33727\"> <Name> <LocalizedString value=\"XDSSubmissionSet.uniqueId\" /> </Name> </ExternalIdentifier> <ExternalIdentifier id=\"urn:uuid:73ea59e0-4d41-4bda-03e4-4a5cc2f1ce3b\" registryObject=\"SubmissionSet\" identificationScheme=\"urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832\" value=\"1.3.6.1.4.1.21367.2005.3.999.901\"> <Name> <LocalizedString value=\"XDSSubmissionSet.sourceId\" /> </Name> </ExternalIdentifier> <ExternalIdentifier id=\"urn:uuid:6e11c871-91c3-0206-9df2-0cb245d2e888\" registryObject=\"SubmissionSet\" identificationScheme=\"urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446\" value=\"$PatientId\"> <Name> <LocalizedString value=\"XDSSubmissionSet.patientId\" /> </Name> </ExternalIdentifier> </RegistryPackage> <Classification id=\"urn:uuid:316d4506-6afc-ff81-486e-2d137e36e01c\" classifiedObject=\"SubmissionSet\" classificationNode=\"urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd\" /> <Association id=\"urn:uuid:a02892f9-fb97-bc72-7e3b-85a73f4f170e\" associationType=\"HasMember\" sourceObject=\"SubmissionSet\" targetObject=\"Document01\"> <Slot name=\"SubmissionSetStatus\"> <ValueList> <Value>Original</Value> </ValueList> </Slot> </Association> </RegistryObjectList> </tns:SubmitObjectsRequest>";
		SubmitObjectsRequest submitObjectRequest = new SubmitObjectsRequest();
		submitObjectRequest = unmarshallFromXml(SubmitObjectsRequest.class,
				submitObjectRequestXml);

		final Document document = new Document();
		document.setId("Document01");
		document.setValue("xyz".getBytes());

		final ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		request.setSubmitObjectsRequest(submitObjectRequest);
		request.getDocument().add(document);

		final XDSRepositorybWebServiceClient wsc = new XDSRepositorybWebServiceClient(
				address);

		// Act
		final Object response = wsc.provideAndRegisterDocumentSet(request);

		// Assert
		validateResponseOfProvideAndRegisterDocumentSet(response);
	}

	@Test(expected = XdsbRepositoryClientException.class)
	public void testWSClientSOAPCallWorks_provideAndRegisterDocumentSetRequest_Throws_Exception()
			throws JAXBException {
		// Arrange
		initExceptionEndpoint();
		final String submitObjectRequestXml = "<tns:SubmitObjectsRequest xmlns:tns=\"urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0\"> <RegistryObjectList xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"> <ObjectRef id=\"urn:uuid:7edca82f-054d-47f2-a032-9b2a5b518fff\" /> <ObjectRef id=\"urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd\" /> <ObjectRef id=\"urn:uuid:f64ffdf0-4b97-4e06-b79f-a52b38ec2f8a\" /> <ObjectRef id=\"urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8\" /> <ObjectRef id=\"urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832\" /> <ObjectRef id=\"urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446\" /> <ObjectRef id=\"urn:uuid:d9d542f3-6cc4-48b6-8870-ea235fbc94c2\" /> <ObjectRef id=\"urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1\" /> <ObjectRef id=\"urn:uuid:aa543740-bdda-424e-8c96-df4873be8500\" /> <ObjectRef id=\"urn:uuid:75df8f67-9973-4fbe-a900-df66cefecc5a\" /> <ObjectRef id=\"urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f\" /> <ObjectRef id=\"urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d\" /> <ObjectRef id=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\" /> <ObjectRef id=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\" /> <ObjectRef id=\"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a\" /> <ObjectRef id=\"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab\" /> <ObjectRef id=\"urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427\" /> <ObjectRef id=\"urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d\" /> <ObjectRef id=\"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d\" /> <ExtrinsicObject mimeType=\"text/xml\" objectType=\"urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1\" id=\"Document01\"> <Slot name=\"creationTime\"> <ValueList> <Value>20110203</Value> </ValueList> </Slot> <Slot name=\"languageCode\"> <ValueList> <Value>en-US</Value> </ValueList> </Slot> <Slot name=\"sourcePatientId\"> <ValueList> <Value>100015060001^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> </ValueList> </Slot> <Slot name=\"sourcePatientInfo\"> <ValueList> <Value>PID-3|100015060001^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> <Value>PID-5|Patientone^Asample^^^</Value> <Value>PID-7|19710510</Value> <Value>PID-8|M</Value> <Value>PID-11|14235 South St^^Baltimore^Maryland^21075^</Value> </ValueList> </Slot> <Slot name=\"intendedRecipient\"> <ValueList> <Value>^^Internet^Duane_Decouteau@direct.healthvault-stage.com </Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:authorTelecommunication\"> <ValueList> <Value>^^Internet^leo.smith@direct.obhita-stage.org</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:purposeofuse\"> <ValueList> <Value>TREAT</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:obligationpolicy\"> <ValueList> <Value>ENCRYPT</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:refrainpolicy\"> <ValueList> <Value>NORDSLCD</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:sensitivitypolicy\"> <ValueList> <Value>HIV</Value> </ValueList> </Slot> <Slot name=\"urn:siframework.org:ds4p:usprivacylaw\"> <ValueList> <Value>42CFRPart2</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Clinic Personal Health Record Extract\" /> </Name> <Description /> <Classification classificationScheme=\"urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d\" classifiedObject=\"Document01\" id=\"urn:uuid:4e1d56a3-cf98-9deb-cfa2-5686b221778e\" nodeRepresentation=\"\"> <Slot name=\"authorPerson\"> <ValueList> <Value>100010020002^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> </ValueList> </Slot> <Slot name=\"authorInstitution\"> <ValueList> <Value>XYZ Clinic</Value> </ValueList> </Slot> <Slot name=\"authorRole\"> <ValueList> <Value>Provider</Value> </ValueList> </Slot> <Slot name=\"authorSpecialty\"> <ValueList> <Value>General Medicine</Value> </ValueList> </Slot> </Classification> <Classification id=\"urn:uuid:81dfbd93-a50b-c603-c867-50640424eb1d\" classificationScheme=\"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a\" classifiedObject=\"Document01\" nodeRepresentation=\"Consult\"> <Slot name=\"codingScheme\"> <ValueList> <Value>Connect-a-thon classCodes</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Consult Note\" /> </Name> </Classification> <Classification id=\"cl03\" classificationScheme=\"urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f\" classifiedObject=\"Document01\" nodeRepresentation=\"R\"> <Slot name=\"codingScheme\"> <ValueList> <Value>2.16.840.1.113883.5.25</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"R\" /> </Name> </Classification> <Classification id=\"urn:uuid:fb57bcf4-7f7a-28ab-c29c-fe067854abbd\" classificationScheme=\"urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d\" classifiedObject=\"Document01\" nodeRepresentation=\"2.16.840.1.113883.10.20.1\"> <Slot name=\"codingScheme\"> <ValueList> <Value>HITSP</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"HL7 CCD Document\" /> </Name> </Classification> <Classification id=\"urn:uuid:a6e536d1-b496-4006-e243-5c94b6db7c07\" classificationScheme=\"urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1\" classifiedObject=\"Document01\" nodeRepresentation=\"OF\"> <Slot name=\"codingScheme\"> <ValueList> <Value>2.16.840.1.113883.5.11</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Outpatient facility\" /> </Name> </Classification> <Classification id=\"urn:uuid:241be963-c4f3-d799-a6a5-d5a4636a8f0f\" classificationScheme=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\" classifiedObject=\"Document01\" nodeRepresentation=\"Psychiatry\"> <Slot name=\"codingScheme\"> <ValueList> <Value>Connect-a-thon practiceSettingCodes</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Psychiatry\" /> </Name> </Classification> <Classification id=\"urn:uuid:7aec7490-a293-12ae-d70b-8976b4f1f703\" classificationScheme=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\" classifiedObject=\"Document01\" nodeRepresentation=\"34133-9\"> <Slot name=\"codingScheme\"> <ValueList> <Value>LOINC</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Summarization of episode note\" /> </Name> </Classification> <ExternalIdentifier id=\"urn:uuid:9afb1c3f-942c-6676-77e2-38fdc3f32a47\" registryObject=\"Document01\" identificationScheme=\"urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427\" value=\"$PatientId\"> <Name> <LocalizedString value=\"XDSDocumentEntry.patientId\" /> </Name> </ExternalIdentifier> <ExternalIdentifier id=\"urn:uuid:267c9cd2-29e2-81e5-0a78-9bb078c59ec3\" registryObject=\"Document01\" identificationScheme=\"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab\" value=\"2009.9.1.2500\"> <Name> <LocalizedString value=\"XDSDocumentEntry.uniqueId\" /> </Name> </ExternalIdentifier> </ExtrinsicObject> <RegistryPackage id=\"SubmissionSet\"> <Slot name=\"submissionTime\"> <ValueList> <Value>20110203</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Submission Set\" /> </Name> <Description> <LocalizedString value=\"This Submission Set contains a Clinical Exchange Document\" /> </Description> <Classification id=\"urn:uuid:0ca4ed97-5d52-9f4e-e17e-37b1e8964909\" classificationScheme=\"urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d\" classifiedObject=\"SubmissionSet\" nodeRepresentation=\"\"> <Slot name=\"authorPerson\"> <ValueList> <Value>100010020002^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value> </ValueList> </Slot> <Slot name=\"authorInstitution\"> <ValueList> <Value>XYZ Clinic</Value> </ValueList> </Slot> <Slot name=\"authorRole\"> <ValueList> <Value>Provider</Value> </ValueList> </Slot> <Slot name=\"authorSpecialty\"> <ValueList> <Value>General Medicine</Value> </ValueList> </Slot> </Classification> <Classification id=\"urn:uuid:2d7456e0-d449-6b74-27f0-1f870aa21be2\" classificationScheme=\"urn:uuid:aa543740-bdda-424e-8c96-df4873be8500\" classifiedObject=\"SubmissionSet\" nodeRepresentation=\"Summarization of episode\"> <Slot name=\"codingScheme\"> <ValueList> <Value>Connect-a-thon contentTypeCodes</Value> </ValueList> </Slot> <Name> <LocalizedString value=\"Summarization of episode\" /> </Name> </Classification> <ExternalIdentifier id=\"urn:uuid:90673650-b2d7-25b8-ac08-f5966d228fc8\" registryObject=\"SubmissionSet\" identificationScheme=\"urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8\" value=\"129.6.58.42.33727\"> <Name> <LocalizedString value=\"XDSSubmissionSet.uniqueId\" /> </Name> </ExternalIdentifier> <ExternalIdentifier id=\"urn:uuid:73ea59e0-4d41-4bda-03e4-4a5cc2f1ce3b\" registryObject=\"SubmissionSet\" identificationScheme=\"urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832\" value=\"1.3.6.1.4.1.21367.2005.3.999.901\"> <Name> <LocalizedString value=\"XDSSubmissionSet.sourceId\" /> </Name> </ExternalIdentifier> <ExternalIdentifier id=\"urn:uuid:6e11c871-91c3-0206-9df2-0cb245d2e888\" registryObject=\"SubmissionSet\" identificationScheme=\"urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446\" value=\"$PatientId\"> <Name> <LocalizedString value=\"XDSSubmissionSet.patientId\" /> </Name> </ExternalIdentifier> </RegistryPackage> <Classification id=\"urn:uuid:316d4506-6afc-ff81-486e-2d137e36e01c\" classifiedObject=\"SubmissionSet\" classificationNode=\"urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd\" /> <Association id=\"urn:uuid:a02892f9-fb97-bc72-7e3b-85a73f4f170e\" associationType=\"HasMember\" sourceObject=\"SubmissionSet\" targetObject=\"Document01\"> <Slot name=\"SubmissionSetStatus\"> <ValueList> <Value>Original</Value> </ValueList> </Slot> </Association> </RegistryObjectList> </tns:SubmitObjectsRequest>";
		SubmitObjectsRequest submitObjectRequest = new SubmitObjectsRequest();
		submitObjectRequest = unmarshallFromXml(SubmitObjectsRequest.class,
				submitObjectRequestXml);

		final Document document = new Document();
		document.setId("Document01");
		document.setValue("xyz".getBytes());

		final ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		request.setSubmitObjectsRequest(submitObjectRequest);
		request.getDocument().add(document);

		final XDSRepositorybWebServiceClient wsc = new XDSRepositorybWebServiceClient(
				address);

		// Act
		final Object response = wsc.provideAndRegisterDocumentSet(request);

		// Assert
		validateResponseOfProvideAndRegisterDocumentSet(response);
	}

	/*
	 * @Test public void testProvideAndRegisterDocumentSetRequest() throws
	 * JAXBException { final String demoEndpoint =
	 * "http://feijboss01:8080/axis2/services/xdsrepositoryb"; final String
	 * javaVmEndpoint =
	 * "http://192.168.223.134:8080/axis2/services/xdsrepositoryb"; final String
	 * dotnetVmEndpoint =
	 * "http://192.168.223.128:8080/xdsservice/xdsrepository";
	 *
	 * InputStream is = getClass().getClassLoader().getResourceAsStream(
	 * "xdsbMetadata.xml");
	 *
	 * BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 *
	 * StringBuilder metadataStringBuilder = new StringBuilder(); String line;
	 *
	 * try { while ((line = br.readLine()) != null) {
	 * metadataStringBuilder.append(line); }
	 *
	 * br.close(); is.close(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 *
	 * String submitObjectRequestXml = metadataStringBuilder.toString();
	 * SubmitObjectsRequest submitObjectRequest = new SubmitObjectsRequest();
	 * submitObjectRequest = unmarshallFromXml(SubmitObjectsRequest.class,
	 * submitObjectRequestXml);
	 *
	 * Document document = new Document(); document.setId("Document_01");
	 * document.setValue("xyz".getBytes());
	 *
	 * ProvideAndRegisterDocumentSetRequest request = new
	 * ProvideAndRegisterDocumentSetRequest();
	 * request.setSubmitObjectsRequest(submitObjectRequest);
	 * request.getDocument().add(document);
	 *
	 * XDSRepositorybWebServiceClient wsc = new XDSRepositorybWebServiceClient(
	 * dotnetVmEndpoint); RegistryResponseType response = wsc
	 * .provideAndRegisterDocumentSetReponse(request); try {
	 * System.out.println(marshall(response)); } catch (Throwable e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks_retrieveDocumentSetRequest() {
		// Arrange
		final RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
		final DocumentRequest documentRequest = new DocumentRequest();
		documentRequest.setHomeCommunityId("HC");
		documentRequest
				.setRepositoryUniqueId("1.3.6.1.4.1.21367.2010.1.2.1040");
		documentRequest.setDocumentUniqueId("$uniqueId06");
		request.getDocumentRequest().add(documentRequest);

		final XDSRepositorybWebServiceClient wsc = new XDSRepositorybWebServiceClient(
				address);

		// Act
		final Object response = wsc.retrieveDocumentSet(request);

		// Assert
		validateResponseOfRetrieveDocumentSetRequest(response);
	}

	@Test(expected = XdsbRepositoryClientException.class)
	public void testWSClientSOAPCallWorks_retrieveDocumentSetRequest_Throws_Exception() {
		// Arrange
		initExceptionEndpoint();
		final RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
		final DocumentRequest documentRequest = new DocumentRequest();
		documentRequest.setHomeCommunityId("HC");
		documentRequest
				.setRepositoryUniqueId("1.3.6.1.4.1.21367.2010.1.2.1040");
		documentRequest.setDocumentUniqueId("$uniqueId06");
		request.getDocumentRequest().add(documentRequest);

		final XDSRepositorybWebServiceClient wsc = new XDSRepositorybWebServiceClient(
				address);

		// Act
		final Object response = wsc.retrieveDocumentSet(request);

		// Assert
		validateResponseOfRetrieveDocumentSetRequest(response);
	}

	private XDSRepository createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("XDS.b_repository.net.wsdl");
		final QName SERVICE = new QName("http://tempuri.org/",
				"DocumentRepositoryService");

		final XDSRepository port = new org.tempuri.DocumentRepositoryService(
				WSDL_LOCATION, SERVICE).getXDSRepositoryHTTPEndpoint();
		final BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}

	private void initExceptionEndpoint() {
		ep.stop();
		ep = Endpoint.publish(address,
				new XdsRepositorybImplThrowingException());
	}

	private <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException {
		final JAXBContext context = JAXBContext.newInstance(clazz);
		final Unmarshaller um = context.createUnmarshaller();
		final ByteArrayInputStream input = new ByteArrayInputStream(
				xml.getBytes());
		return (T) um.unmarshal(input);
	}

	private void validateResponseOfProvideAndRegisterDocumentSet(Object response) {
		Assert.assertNotNull(response);
	}

	private void validateResponseOfRetrieveDocumentSetRequest(Object response) {
		Assert.assertNotNull(response);
	}

	private static String marshall(Object obj) throws Throwable {
		final JAXBContext context = JAXBContext.newInstance(obj.getClass());

		// Create the marshaller, this is the nifty little thing that will
		// actually transform the object into XML
		final Marshaller marshaller = context.createMarshaller();

		// Create a stringWriter to hold the XML
		final StringWriter stringWriter = new StringWriter();

		// Marshal the javaObject and write the XML to the stringWriter
		marshaller.marshal(obj, stringWriter);

		return stringWriter.toString();
	}
}
