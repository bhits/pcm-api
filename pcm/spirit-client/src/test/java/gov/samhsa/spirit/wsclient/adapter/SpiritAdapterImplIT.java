package gov.samhsa.spirit.wsclient.adapter;

import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.spirit.wsclient.exception.SpiritAdapterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spirit.ehr.ws.client.generated.AuthorMetadataClientDto;
import com.spirit.ehr.ws.client.generated.DocumentClientDto;
import com.spirit.ehr.ws.client.generated.EhrPatientClientDto;
import com.spirit.ehr.ws.client.generated.EhrPolicyDiscardRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitOrUpdateRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicySubmitRsp;
import com.spirit.ehr.ws.client.generated.EhrPolicyUpdateRsp;
import com.spirit.ehr.ws.client.generated.EhrXdsQRsp;
import com.spirit.ehr.ws.client.generated.IheClassificationClientDto;
import com.spirit.ehr.ws.client.generated.SourceSubmissionClientDto;
import com.spirit.ehr.ws.client.generated.SubmissionSetClientDto;
import com.spirit.ehr.ws.client.generated.XdsSrcSubmitReq;

public class SpiritAdapterImplIT {
	public static final String SMART_NPI = "1427467752";
	String endpointAddress;
	String org;
	String pwd;
	String rol;
	String user;

	SpiritAdapter adapter;

	String stateId;

	byte[] imageFileToUpload;
	byte[] smartC32Bytes;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() throws URISyntaxException, IOException,
			SpiritAdapterException {
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();

		URL resource = classloader.getResource("spiritconfig.properties");
		File file = new File(resource.toURI());
		FileInputStream fis = new FileInputStream(file);
		Properties props = new Properties();
		props.load(fis);

		// reading proeprty
		String endpoint = props.getProperty("spirit.client.endpointAddress");
		org = props.getProperty("spirit.client.org");
		pwd = props.getProperty("spirit.client.pwd");
		rol = props.getProperty("spirit.client.rol");
		user = props.getProperty("spirit.client.user");
		final String domain = props.getProperty("spirit.client.domainId");
		final String c2sDomain = props
				.getProperty("spirit.client.c2s.domainId");
		final String c2sDomainType = props
				.getProperty("spirit.client.c2s.domainType");
		final String c2sEnvType = props.getProperty("spirit.client.pid.prefix");

		initSpiritAdapter(endpoint, org, pwd, rol, user, domain, c2sDomain,
				c2sDomainType, c2sEnvType);
	}

	@Test
	public void testDocumentSubmitSmartC32_SmartWay() throws Exception {
		Scanner scan = new Scanner(System.in);
		initSpiritAdapterByCommandLine(scan);
		String[] patientIds = initPatientIdsByCommandLine(scan);
		scan.close();

		this.smartC32Bytes = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><?xml-stylesheet href=\"http://obhita.org/CDA.xsl\" type=\"text/xsl\"?><ClinicalDocument xmlns=\"urn:hl7-org:v3\" xmlns:hl7=\"urn:hl7-org:v3\" xmlns:sdtc=\"urn:hl7-org:sdtc\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:hl7-org:v3 schema/cdar2c32/infrastructure/cda/C32_CDA.xsd\"><realmCode code=\"US\"/><typeId root=\"2.16.840.1.113883.1.3\" extension=\"POCD_HD000040\"/><templateId root=\"2.16.840.1.113883.3.27.1776\" assigningAuthorityName=\"CDA/R2\"/><templateId root=\"2.16.840.1.113883.10.20.3\" assigningAuthorityName=\"HL7/CDT Header\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.1.1\" assigningAuthorityName=\"IHE/PCC\"/><templateId root=\"2.16.840.1.113883.3.88.11.32.1\" assigningAuthorityName=\"HITSP/C32\"/><id root=\"2.16.840.1.113883.3.72\" assigningAuthorityName=\"FEI Systems Inc.\"/><code code=\"34133-9\" displayName=\"Summarization of episode note\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/><title>Summarization of episode note</title><effectiveTime value=\"201408151401\"/><confidentialityCode code=\"N\"/><languageCode code=\"en-US\"/><recordTarget><patientRole><id root=\"00000000-0000-0000-0000-000000000000\" extension=\"REG.1DVRUZMRCA\"/><addr use=\"HP\"><streetAddressLine>3333 Wickhart Road</streetAddressLine><city>College Park</city><state>MD</state><postalCode>20742</postalCode></addr><telecom use=\"HP\" value=\"H:(333) 333-3333\"/><patient><name><family>Spock</family><given>James</given></name><administrativeGenderCode code=\"M\" codeSystem=\"2.16.840.1.113883.5.1\" codeSystemName=\"AdministrativeGenderCode\" displayName=\"Male\"/><birthTime value=\"19840103\"/><maritalStatusCode nullFlavor=\"UNK\"/><religiousAffiliationCode nullFlavor=\"UNK\"/><raceCode code=\"R5\" codeSystem=\"2.16.840.1.113883.6.238\" codeSystemName=\"CDC Race and Ethnicity\" displayName=\"White\"/><ethnicGroupCode code=\"E2\" codeSystem=\"2.16.840.1.113883.6.238\" codeSystemName=\"CDC Race and Ethnicity\" displayName=\"Not of Hispanic Origin\"/><birthplace nullFlavor=\"UNK\"><place><addr><streetAddressLine/><city/><state/><postalCode/></addr></place></birthplace><languageCommunication><templateId root=\"2.16.840.1.113883.3.88.11.83.2\" assigningAuthorityName=\"HITSP/C83\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.2.1\" assigningAuthorityName=\"IHE/PCC\"/><languageCode code=\"en-US\"/></languageCommunication></patient></patientRole></recordTarget><author><time value=\"201408151401\"/><assignedAuthor><id nullFlavor=\"UNK\"/><addr/><telecom/><assignedPerson><name><family>Zwiesler</family><given>Theodore</given><prefix/></name></assignedPerson></assignedAuthor></author><custodian><assignedCustodian><representedCustodianOrganization><id root=\"2.16.840.1.113883.4.6\" extension=\"1427467752\"/><name>PG Co Health Dept (MD-300030)</name><telecom/><addr/></representedCustodianOrganization></assignedCustodian></custodian><component><structuredBody><component><section><templateId root=\"2.16.840.1.113883.3.88.11.83.103\" assigningAuthorityName=\"HITSP/C83\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.3.6\" assigningAuthorityName=\"IHE PCC\"/><templateId root=\"2.16.840.1.113883.10.20.1.11\" assigningAuthorityName=\"HL7 CCD\"/><code code=\"11450-4\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"Problem list\"/><title>Problems</title><text><table width=\"100%\" border=\"1\"><thead><tr><th>Problem Name</th><th>Problem Code</th><th>Effective Dates</th><th>Problem Status</th></tr></thead><tbody><tr ID=\"d15e256\"><td>Cholera</td><td>001.9</td><td>From: July 3, 2014 <br/> To: </td><td>Active</td></tr></tbody></table></text><entry typeCode=\"DRIV\"><act classCode=\"ACT\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.3.88.11.83.7\" assigningAuthorityName=\"HITSP C83\"/><templateId root=\"2.16.840.1.113883.10.20.1.27\" assigningAuthorityName=\"CCD\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.5.1\" assigningAuthorityName=\"IHE PCC\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.5.2\" assigningAuthorityName=\"IHE PCC\"/><id root=\"d15e256\"/><code nullFlavor=\"NA\"/><statusCode code=\"completed\"/><effectiveTime xsi:type=\"IVL_TS\"><low value=\"20140703\"/><high nullFlavor=\"UNK\"/></effectiveTime><entryRelationship inversionInd=\"false\" typeCode=\"SUBJ\"><sequenceNumber value=\"0\"/><observation classCode=\"OBS\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.10.20.1.28\" assigningAuthorityName=\"CCD\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.5\" assigningAuthorityName=\"IHE PCC\"/><id root=\"d15e256\"/><code code=\"404684003\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\" displayName=\"Finding\"/><text><reference value=\"d15e256\"/></text><statusCode code=\"completed\"/><effectiveTime xsi:type=\"IVL_TS\"><low value=\"20140703\"/><high nullFlavor=\"UNK\"/></effectiveTime><value nullFlavor=\"UNK\" xsi:type=\"CD\"><translation code=\"001.9\" codeSystem=\"2.16.840.1.113883.6.103\" codeSystemName=\"ICD-9-CM\" displayName=\"Alcohol or drug dependence\"/></value><entryRelationship typeCode=\"REFR\"><observation classCode=\"OBS\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.10.20.1.50\"/><code code=\"33999-4\" codeSystem=\"2.16.840.1.113883.6.1\" displayName=\"Status\"/><value xsi:type=\"CD\" code=\"55561003\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\" displayName=\"Active\"/></observation></entryRelationship></observation></entryRelationship></act></entry></section></component><component><section><templateId root=\"2.16.840.1.113883.3.88.11.83.102\" assigningAuthorityName=\"HITSP/C83\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.3.13\" assigningAuthorityName=\"IHE PCC\"/><templateId root=\"2.16.840.1.113883.10.20.1.2\" assigningAuthorityName=\"HL7 CCD\"/><code code=\"48765-2\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"Allergies\"/><title>Allergies and Adverse Reactions</title><text><table width=\"100%\" border=\"1\"><thead><tr><th>Type</th><th>Type SNOMED CT Code</th><th>Substance Name</th><th>Substance Code</th><th>Reaction</th><th>Adverse Event Date</th><th>Status</th></tr></thead><tbody><tr ID=\"d15e223\"><td>Drug</td><td>416098002</td><td>Codeine</td><td>RXA001</td><td>Photosensitivity<br/></td><td> From: July 1, 2014 <br/> To: </td><td>Active</td></tr></tbody></table></text><entry typeCode=\"DRIV\"><act classCode=\"ACT\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.3.88.11.83.6\" assigningAuthorityName=\"HITSP C83\"/><templateId root=\"2.16.840.1.113883.10.20.1.27\" assigningAuthorityName=\"CCD\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.5.1\" assigningAuthorityName=\"IHE PCC\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.5.3\" assigningAuthorityName=\"IHE PCC\"/><id root=\"d15e223\"/><code nullFlavor=\"NA\"/><statusCode code=\"completed\"/><effectiveTime xsi:type=\"IVL_TS\"><low value=\"20140701\"/><high nullFlavor=\"UNK\"/></effectiveTime><entryRelationship inversionInd=\"false\" typeCode=\"SUBJ\"><observation classCode=\"OBS\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.10.20.1.18\" assigningAuthorityName=\"CCD\"/><templateId root=\"2.16.840.1.113883.10.20.1.28\" assigningAuthorityName=\"CCD\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.5\" assigningAuthorityName=\"IHE PCC\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.6\" assigningAuthorityName=\"IHE PCC\"/><templateId root=\"2.16.840.1.113883.10.20.1.18\"/><id root=\"d15e223\"/><code code=\"416098002\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\" displayName=\"Drug\"/><text><reference value=\"d15e223\"/></text><statusCode code=\"completed\"/><effectiveTime xsi:type=\"IVL_TS\"><low value=\"20140701\"/><high nullFlavor=\"UNK\"/></effectiveTime><value xsi:type=\"CD\" code=\"RXA001\" codeSystem=\"2.16.840.1.113883.6.88\" codeSystemName=\"RxNorm\" displayName=\"Codeine\"><originalText>Drug</originalText></value><participant typeCode=\"CSM\"><participantRole classCode=\"MANU\"><addr/><telecom/><playingEntity classCode=\"MMAT\"><code code=\"RXA001\" codeSystem=\"2.16.840.1.113883.6.88\" codeSystemName=\"RxNorm\" displayName=\"Codeine\"><originalText><reference/></originalText></code><name>Codeine</name></playingEntity></participantRole></participant><entryRelationship typeCode=\"MFST\" inversionInd=\"true\"><observation classCode=\"OBS\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.10.20.1.54\"/><code code=\"ASSERTION\" codeSystem=\"2.16.840.1.113883.5.4\"/><statusCode code=\"completed\"/><value xsi:type=\"CE\" nullFlavor=\"UNK\" displayName=\"Photosensitivity\"/></observation></entryRelationship><entryRelationship typeCode=\"REFR\"><observation classCode=\"OBS\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.10.20.1.39\"/><code code=\"33999-4\" codeSystem=\"2.16.840.1.113883.6.1\" displayName=\"Status\"/><statusCode code=\"completed\"/><value xsi:type=\"CE\" code=\"55561003\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\" displayName=\"Active\"/></observation></entryRelationship></observation></entryRelationship></act></entry></section></component><component><section><templateId root=\"2.16.840.1.113883.3.88.11.83.112\" assigningAuthorityName=\"HITSP/C83\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.3.19\" assigningAuthorityName=\"IHE PCC\"/><templateId root=\"2.16.840.1.113883.10.20.1.8\" assigningAuthorityName=\"HL7 CCD\"/><code code=\"10160-0\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"History of medication use\"/><title>Medications</title><text><table width=\"100%\" border=\"1\"><thead><tr><th>RxNorm Code</th><th>Product</th><th>Generic Name</th><th>Brand Name</th><th>Dose</th><th>Form</th><th>Route</th><th>Frequency</th><th>Patient Instructions</th><th>Status</th><th>Date Started</th></tr></thead><tbody><tr ID=\"d15e168\"><td>151358</td><td>Medication</td><td>Antabuse (disulfiram)</td><td/><td>10 mg</td><td/><td/><td>1/day</td><td>test medication notes</td><td>Active</td><td>July 4, 2014 </td></tr></tbody></table></text><entry typeCode=\"DRIV\"><substanceAdministration classCode=\"SBADM\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.3.88.11.83.8\" assigningAuthorityName=\"HITSP C83\"/><templateId root=\"2.16.840.1.113883.10.20.1.24\" assigningAuthorityName=\"CCD\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.7\" assigningAuthorityName=\"IHE PCC\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.7.1\" assigningAuthorityName=\"IHE PCC\"/><id root=\"d15e168\"/><text><reference value=\"d15e168\"/></text><statusCode code=\"completed\"/><effectiveTime xsi:type=\"IVL_TS\"><low value=\"20140704\"/><high nullFlavor=\"UNK\"/></effectiveTime><routeCode nullFlavor=\"UNK\"/><doseQuantity value=\"1\" unit=\"{10_mg}\"/><administrationUnitCode nullFlavor=\"UNK\"/><consumable><manufacturedProduct><templateId root=\"2.16.840.1.113883.3.88.11.83.8.2\" assigningAuthorityName=\"HITSP C83\"/><templateId root=\"2.16.840.1.113883.10.20.1.53\" assigningAuthorityName=\"CCD\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.7.2\" assigningAuthorityName=\"IHE PCC\"/><manufacturedMaterial><code code=\"151358\" codeSystem=\"2.16.840.1.113883.6.88\" codeSystemName=\"RxNorm\" displayName=\"Antabuse (disulfiram)\"><originalText>Antabuse (disulfiram)<reference/></originalText></code><name/></manufacturedMaterial></manufacturedProduct></consumable><entryRelationship typeCode=\"SUBJ\"><observation classCode=\"OBS\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.3.88.11.83.8.1\" assigningAuthorityName=\"HITSP C83\"/><code code=\"329505003\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\"/></observation></entryRelationship><entryRelationship typeCode=\"SUBJ\"><act classCode=\"ACT\" moodCode=\"INT\"><templateId root=\"2.16.840.1.113883.10.20.1.49\" assigningAuthorityName=\"CCD\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.3\" assigningAuthorityName=\"IHE PCC\"/><code code=\"PINSTRUCT\" codeSystem=\"1.3.6.1.4.1.19376.1.5.3.2\" codeSystemName=\"IHEActCode\"/><text>This is the text of the Patient Instruction. Note that this instruction is printed in the narrative text of the parent Section and is refereced by the following pointer to it.<reference value=\"PntrtoSectionText\"/></text><statusCode code=\"completed\"/></act></entryRelationship><entryRelationship typeCode=\"SUBJ\"><act classCode=\"ACT\" moodCode=\"INT\"><templateId root=\"2.16.840.1.113883.10.20.1.43\" assigningAuthorityName=\"CCD\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.3.1\" assigningAuthorityName=\"IHE PCC\"/><code code=\"FINSTRUCT\" codeSystem=\"1.3.6.1.4.1.19376.1.5.3.2\" codeSystemName=\"IHEActCode\"/><text>test medication notes<reference/></text><statusCode code=\"completed\"/></act></entryRelationship><entryRelationship typeCode=\"REFR\"><observation classCode=\"OBS\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.10.20.1.47\"/><code code=\"33999-4\" displayName=\"Status\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\"/><value xsi:type=\"CE\" code=\"55561003\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\" displayName=\"Active\"/></observation></entryRelationship></substanceAdministration></entry></section></component><component><section><templateId root=\"2.16.840.1.113883.3.88.11.83.127\" assigningAuthorityName=\"HITSP/C83\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.1.5.3.3\" assigningAuthorityName=\"IHE PCC\"/><templateId root=\"2.16.840.1.113883.10.20.1.3\" assigningAuthorityName=\"HL7 CCD\"/><code code=\"46240-8\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"History of encounters\"/><title>Encounters</title><text><table border=\"1\" width=\"100%\"><thead><tr><th>Encounter</th><th>Performer</th><th>Location</th><th>Date</th></tr></thead><tbody><tr ID=\"d15e132\"><td>4 - AA/NA/Self-Help Group</td><td> Theodore Zwiesler</td><td>PG Co Health Dept (MD-300030)</td><td>August 8, 2014 </td></tr></tbody></table></text><entry typeCode=\"DRIV\"><encounter classCode=\"ENC\" moodCode=\"EVN\"><templateId root=\"2.16.840.1.113883.10.20.1.21\"/><templateId root=\"1.3.6.1.4.1.19376.1.5.3.1.4.14\"/><templateId root=\"2.16.840.1.113883.3.88.11.83.16\"/><id root=\"d15e132\"/><code code=\"4\" codeSystem=\"2.16.840.1.113883.6.12\" codeSystemName=\"CPT\" displayName=\"4 - AA/NA/Self-Help Group\"><originalText>4 - AA/NA/Self-Help Group<reference value=\"PointerToTextInSection\"/></originalText></code><text>IHE Requires reference to go here instead of originalText of code.<reference/></text><effectiveTime xsi:type=\"IVL_TS\" value=\"20140808\"/><performer><assignedEntity><id/><addr/><telecom/><assignedPerson><name> Theodore Zwiesler</name></assignedPerson></assignedEntity></performer><participant typeCode=\"LOC\"><templateId root=\"2.16.840.1.113883.10.20.1.45\"/><participantRole classCode=\"SDLOC\"><id root=\"d15e132\"/><addr/><telecom/><playingEntity classCode=\"PLC\"><name>PG Co Health Dept (MD-300030)</name></playingEntity></participantRole></participant></encounter></entry></section></component></structuredBody></component></ClinicalDocument>"
				.getBytes("UTF-8");

		Arrays.asList(patientIds).forEach(this::submitSmartC32_SmartWay);
	}

	@Test
	public void testDeprecateC32s() throws Exception {
		Scanner scan = new Scanner(System.in);
		initSpiritAdapterByCommandLine(scan);
		String[] patientIds = initPatientIdsByCommandLine(scan);
		scan.close();

		Arrays.asList(patientIds).forEach(this::deprecateC32sOfASmartPatient);
	}

	/*
	 * @Test public void testDocumentSubmit() throws Exception { PatientDto
	 * patientDto = new PatientDto(); patientDto.setFirstName("Albert");
	 * patientDto.setLastName("Smith"); patientDto.setCountry("USA");
	 * patientDto.setBirthDate("19830219"); patientDto.setGenderCode("M");
	 * List<EhrPatientClientDto> patientList = adapter.queryPatientsPIDByPDQ(
	 * patientDto, stateId);
	 * 
	 * // Create a SubmissionSet (contains information about the submitter)
	 * SubmissionSetClientDto submissionSet = new SubmissionSetClientDto();
	 * 
	 * AuthorMetadataClientDto author=new AuthorMetadataClientDto();
	 * author.setPerson("subm.authorPerson"); // Set some information about the
	 * submission submissionSet.getAuthor().add(author);
	 * 
	 * submissionSet.setDescription("subm.description");
	 * 
	 * // Create a document object DocumentClientDto submittingDocument = new
	 * DocumentClientDto();
	 * 
	 * // Read the given file and put it into the document object
	 * submittingDocument.setBytes(fileToUpload);
	 * 
	 * // Set some document information
	 * submittingDocument.getAuthor().add(author);
	 * submittingDocument.getLegalAuthenticator().add(
	 * "document.legalAuthenticator");
	 * submittingDocument.setLanguageCode("DE-TI");
	 * submittingDocument.setDescription("document.description");
	 * submittingDocument.setName("document.name");
	 * 
	 * // Set the MIMEType of the submitFile //
	 * submittingDocument.setMimeType("application/pdf");
	 * submittingDocument.setMimeType("image/jpeg"); //
	 * submittingDocument.setMimeType("text/xml");
	 * 
	 * // Some required IHE definitions IheClassificationClientDto formatCode =
	 * new IheClassificationClientDto();
	 * formatCode.setNodeRepresentation("CDAR2/IHE 1.0");
	 * formatCode.setValue("CDAR2/IHE 1.0");
	 * formatCode.getSchema().add("Connect-a-thon formatCodes");
	 * submittingDocument.setFormatCode(formatCode);
	 * 
	 * IheClassificationClientDto contentTypeCode = new
	 * IheClassificationClientDto();
	 * contentTypeCode.setNodeRepresentation("60591-5");
	 * contentTypeCode.setValue("Patient Summary");
	 * contentTypeCode.getSchema().add("2.16.840.1.113883.6.1");
	 * submissionSet.setContentTypeCode(contentTypeCode);
	 * 
	 * IheClassificationClientDto classCode = new IheClassificationClientDto();
	 * classCode.setNodeRepresentation("60591-5");
	 * classCode.setValue("Patient Summary");
	 * classCode.getSchema().add("2.16.840.1.113883.6.1");
	 * submittingDocument.setClassCode(classCode);
	 * 
	 * IheClassificationClientDto typeCode = new IheClassificationClientDto();
	 * typeCode.setNodeRepresentation("60591-5");
	 * typeCode.setValue("Patient Summary");
	 * typeCode.getSchema().add("2.16.840.1.113883.6.1");
	 * submittingDocument.setTypeCode(typeCode);
	 * 
	 * IheClassificationClientDto confidentialityCode = new
	 * IheClassificationClientDto();
	 * confidentialityCode.setNodeRepresentation("N");
	 * confidentialityCode.setValue("Normal");
	 * confidentialityCode.getSchema().add(
	 * "Connect-a-thon confidentialityCodes");
	 * submittingDocument.setConfidentialityCode(confidentialityCode);
	 * 
	 * IheClassificationClientDto healthcareFacilityCode = new
	 * IheClassificationClientDto();
	 * healthcareFacilityCode.setNodeRepresentation("Home");
	 * healthcareFacilityCode.setValue("Home");
	 * healthcareFacilityCode.getSchema().add(
	 * "Connect-a-thon healthcareFacilityTypeCodes");
	 * submittingDocument.setHealthcareFacilityCode(healthcareFacilityCode);
	 * 
	 * IheClassificationClientDto practiceSettingCode = new
	 * IheClassificationClientDto();
	 * practiceSettingCode.setNodeRepresentation("Anesthesia");
	 * practiceSettingCode.setValue("Anesthesia");
	 * practiceSettingCode.getSchema().add(
	 * "Connect-a-thon practiceSettingCodes");
	 * submittingDocument.setPracticeSettingCode(practiceSettingCode);
	 * 
	 * // Create a SourceSubmission (contains the document itself)
	 * SourceSubmissionClientDto sourceSubmission = new
	 * SourceSubmissionClientDto();
	 * 
	 * // Put the new document object into the SourceSubmission
	 * sourceSubmission.getDocuments().add(submittingDocument);
	 * 
	 * // Put the SubmissionSet into the SourceSubmission
	 * sourceSubmission.setSubmissionSet(submissionSet);
	 * 
	 * // sourceSubmission.getSubmissionSet().setUniqueId(
	 * "1.2.40.0.13.1.1.4244045049.20140527095039249.12345"); //
	 * sourceSubmission
	 * .getSubmissionSet().setUUID("abd1e7df-647a-442c-9c70-52fdc124f0e1"); //
	 * sourceSubmission
	 * .getDocuments().get(0).setUUID("abd1e7df-647a-442c-9c70-52fdc124f0e1");
	 * // sourceSubmission.getDocuments().get(0).setUniqueId(
	 * "3b838619-d521-4afb-b4a8-ee9261f5d35");
	 * 
	 * XdsSrcSubmitRsp respRsp = adapter.submitDocument(patientList.get(0),
	 * sourceSubmission, false); System.out.println(respRsp); }
	 */

	/*
	 * @Test public void testDocumentRetrieve() throws Exception { PatientDto
	 * patientDto = new PatientDto(); patientDto.setFirstName("Patient");
	 * patientDto.setLastName("Test"); patientDto.setCountry("USA");
	 * patientDto.setBirthDate("19880726"); patientDto.setGenderCode("M");
	 * List<EhrPatientClientDto> patientList =
	 * adapter.queryPatientsWithPids("Patientid", stateId); // Select patient
	 * EhrPatientClientDto selectedPatient = patientList.get(0);
	 * 
	 * //
	 * ///////////////////////////////////////////////////////////////////////
	 * ////////////// // (3) Query patient content (queryPatientContent) //
	 * /////
	 * /////////////////////////////////////////////////////////////////////
	 * ///////////
	 * 
	 * // Print status
	 * logger.info("retrieveDocumentExampleWorkflow: Calling queryPatientContent"
	 * );
	 * 
	 * // Query patient content PatientContentClientDto patientContent = adapter
	 * .queryPatientContent(selectedPatient).getResponseData();
	 * 
	 * System.out.println(patientContent.getDocuments());
	 * 
	 * // Select a document DocumentClientDto selectedDocument =
	 * patientContent.getDocuments().get( 0);
	 * 
	 * // Call the webservice-method retrieveDocument with the created request
	 * EhrXdsRetrRsp response = adapter.retrieveDocument(selectedDocument);
	 * 
	 * // The response contains amongst other things: // The document as byte[]
	 * response.getDocument();
	 * 
	 * // The document's uniqueId as String response.getDocumentUniqueId();
	 * 
	 * // The documents's mimeType as String response.getMimeType(); }
	 * 
	 * @Test public void testQueryDocumentById() throws Exception {
	 * DocumentClientDto response=adapter.queryDocumentsByUid("REG.1DK7BVY8QU",
	 * "3b838619-d521-4afb-b4a8-ee9261f5d35b", stateId);
	 * System.out.println(response.getUUID()); }
	 * 
	 * @Test public void testUpdateDocument() throws Exception { try { // Print
	 * status logger.info("updateDocumentExampleWorkflow: START");
	 * 
	 * // Print status
	 * logger.info("updateDocumentExampleWorkflow: Calling usrLogin_1_Step");
	 * 
	 * // QueryPatientsWithDemographics PatientDto patientDto = new
	 * PatientDto(); patientDto.setFirstName("Patient");
	 * patientDto.setLastName("Test"); patientDto.setCountry("USA");
	 * patientDto.setBirthDate("19880726"); patientDto.setGenderCode("M");
	 * List<EhrPatientClientDto> patientList = adapter
	 * .queryPatientsPIDByPDQ(patientDto, stateId);
	 * 
	 * // Select patient EhrPatientClientDto selectedPatient =
	 * patientList.get(0);
	 * 
	 * //
	 * ///////////////////////////////////////////////////////////////////////
	 * ////////////// // (3) Query patient content (queryPatientContent) //
	 * /////
	 * /////////////////////////////////////////////////////////////////////
	 * ///////////
	 * 
	 * // Print status
	 * logger.info("updateDocumentExampleWorkflow: Calling queryPatientContent"
	 * );
	 * 
	 * // Query patient content PatientContentClientDto patientContent = adapter
	 * .queryPatientContent(selectedPatient).getResponseData();
	 * 
	 * // Select a document DocumentClientDto selectedDocument =
	 * patientContent.getDocuments() .get(0);
	 * 
	 * //
	 * ///////////////////////////////////////////////////////////////////////
	 * ////////////// // (4) Update a document or/and a folder (updateDocument)
	 * //
	 * ///////////////////////////////////////////////////////////////////////
	 * //////////////
	 * 
	 * // Print status
	 * logger.info("updateDocumentExampleWorkflow: Calling updateDocument");
	 * 
	 * // Change some information of the queried document // Change metadata of
	 * the document selectedDocument.setDescription(
	 * "An Ultra New Updated Document Description 2:02");
	 * selectedDocument.setName("A New Updated Document Description Name");
	 * selectedDocument.setLanguageCode("de-AT"); selectedDocument.setDocStatus(
	 * "urn:oasis:names:tc:ebxml-regrep:StatusType:Deprecated"); // Change the
	 * typeCode // IheClassificationClientDto typeCode = new //
	 * IheClassificationClientDto(); //
	 * typeCode.setNodeRepresentation("34104-0"); //
	 * typeCode.setValue("Hospital Consultation Note"); //
	 * typeCode.getSchema().add("LOINC"); //
	 * selectedDocument.setTypeCode(typeCode); // // Change the
	 * confidentialityCode // IheClassificationClientDto confidentialityCode =
	 * new // IheClassificationClientDto(); //
	 * confidentialityCode.setNodeRepresentation("C"); //
	 * confidentialityCode.setValue("Celebrity"); //
	 * confidentialityCode.getSchema
	 * ().add("Connect-a-thon confidentialityCodes"); //
	 * selectedDocument.setConfidentialityCode(confidentialityCode); // Change
	 * the healthcareFacilityCode // IheClassificationClientDto
	 * healthcareFacilityCode = new // IheClassificationClientDto(); //
	 * healthcareFacilityCode.setNodeRepresentation("Nursing Home"); //
	 * healthcareFacilityCode.setValue("Nursing Home"); //
	 * healthcareFacilityCode
	 * .getSchema().add("Connect-a-thon healthcareFacilityTypeCodes"); //
	 * selectedDocument.setHealthcareFacilityCode(healthcareFacilityCode); // //
	 * Change the practiceSettingCode // IheClassificationClientDto
	 * practiceSettingCode = new // IheClassificationClientDto(); //
	 * practiceSettingCode.setNodeRepresentation("Nursery"); //
	 * practiceSettingCode.setValue("Nursery"); //
	 * practiceSettingCode.getSchema(
	 * ).add("Connect-a-thon practiceSettingCodes"); //
	 * selectedDocument.setPracticeSettingCode(practiceSettingCode);
	 * 
	 * // Create a SubmissionSet (because every XDS-action needs a new own //
	 * one, e.g. for our updateDocument) SubmissionSetClientDto submissionSet =
	 * selectedDocument .getSubmissionSet(); //
	 * submissionSet.getAuthorPerson().add("UpdateAuthor"); //
	 * submissionSet.setName("UpdateName"); //
	 * submissionSet.setDescription("UpdateDescription");
	 * 
	 * // Get the XDS-patient-Id from the patient or the old submission set //
	 * of the document //
	 * submissionSet.setPatientId(selectedDocument.getSubmissionSet
	 * ().getPatientId());
	 * 
	 * // Set a IheClassification contenTypeCode for the submission set //
	 * IheClassificationClientDto contentTypeCode = new //
	 * IheClassificationClientDto(); //
	 * contentTypeCode.setNodeRepresentation("Patient Summary"); //
	 * contentTypeCode.setValue("Patient Summary"); //
	 * contentTypeCode.getSchema().add("Connect-a-thon contentTypeCodes"); //
	 * submissionSet.setContentTypeCode(contentTypeCode);
	 * 
	 * // Create the request XdsSrcUpdateReq request = new XdsSrcUpdateReq(); //
	 * Set the document request.setDocument(selectedDocument); ; // Set the
	 * patient request.setPatient(selectedPatient); // Set the submissionSet
	 * request.setSubmission(submissionSet);
	 * 
	 * request.setFolder(null);
	 * 
	 * // Call the webservice-method updateDocument with the created // request
	 * EhrPatientClientDto response = adapter.updateDocument(request);
	 * 
	 * logger.info(response.toString()); // Print status
	 * logger.info("updateDocumentExampleWorkflow: SUCCESS"); } catch (Exception
	 * e) { logger.error("updateDocumentExampleWorkflow: FAILED", e); throw e; }
	 * }
	 */
	@Test
	public void testDeprecateDocument() throws Exception {
		adapter.deprecatePolicy("3b838619-d521-4afb-b4a8-ee9261f5d35b",
				"REG.1DNIZMYO77", new byte[1]);
	}

	@Test
	public void testSubmitOrUpdatePolicy() throws SpiritAdapterException,
			SimpleMarshallerException {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();

		String xacml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" PolicyId=\"FAM.140268652248613:1770818783:1477888469:4ffeea24-5f6f-44a5-abe8-4415219bf5db\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Description>This is a reference policy for	...</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">FAM.140268652248613</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1477888469</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1770818783</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREAT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2014-06-23T00:00:00-0400</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2015-06-22T23:59:59-0400</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">30954-2</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">HIV</AttributeAssignment></Obligation></Obligations></Policy>";

		EhrPolicySubmitOrUpdateRsp response = null;
		try {
			String stateId = adapter.login();
			response = adapter.submitOrUpdatePolicy(xacml.getBytes(), stateId);
		} catch (SpiritAdapterException e) {
			logger.error("testSubmitOrUpdatePolicy: FAILED", e);
			throw e;
		}
		logger.info("EhrPolicySubmitOrUpdateRsp:");
		logger.info(marshaller.marshalWithoutRootElement(response));
	}

	@Test
	public void testSubmitPolicy() throws SpiritAdapterException,
			SimpleMarshallerException {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();

		String xacml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" PolicyId=\"FAM.140268652248613:1770818783:1477888469:4ffeea24-5f6f-44a5-abe8-4415219bf5db\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Description>This is a reference policy for	...</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">FAM.140268652248613</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1477888469</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1770818783</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREAT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2014-06-23T00:00:00-0400</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2015-06-22T23:59:59-0400</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">30954-2</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">HIV</AttributeAssignment></Obligation></Obligations></Policy>";
		EhrPolicySubmitRsp response = null;
		try {
			response = adapter.submitPolicy(xacml.getBytes());
		} catch (SpiritAdapterException e) {
			logger.error("testSubmitPolicy: FAILED", e);
			throw e;
		}
		logger.info("EhrPolicySubmitRsp:");
		logger.info(marshaller.marshalWithoutRootElement(response));
	}

	@Test
	public void testSubmitOrUpdatePolicy_prompt()
			throws SpiritAdapterException, SimpleMarshallerException {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the xacml file name to be submitted:");
		String xacmlFileName = scan.nextLine().trim();
		scan.close();

		String xacml = convertXMLFileToString(xacmlFileName);

		EhrPolicySubmitOrUpdateRsp response = null;
		try {
			String stateId = adapter.login();
			response = adapter.submitOrUpdatePolicy(xacml.getBytes(), stateId);
		} catch (SpiritAdapterException e) {
			logger.error("testSubmitOrUpdatePolicy: FAILED", e);
			throw e;
		}
		logger.info("EhrPolicySubmitOrUpdateRsp:");
		logger.info(marshaller.marshalWithoutRootElement(response));
	}

	@Test
	public void testUpdatePolicy() throws SpiritAdapterException,
			SimpleMarshallerException {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();

		String xacml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" PolicyId=\"FAM.140268652248613:1770818783:1477888469:4ffeea24-5f6f-44a5-abe8-4415219bf5db\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Description>This is a reference policy for	...</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">FAM.140268652248613</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1477888469</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1770818783</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">HLEGAL</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2014-06-23T00:00:00-0400</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2015-06-22T23:59:59-0400</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">30954-2</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">HIV</AttributeAssignment></Obligation></Obligations></Policy>";
		EhrPolicyUpdateRsp response = null;
		try {
			response = adapter.updatePolicy(xacml.getBytes());
		} catch (SpiritAdapterException e) {
			logger.error("testUpdatePolicy: FAILED", e);
			throw e;
		}
		logger.info("EhrPolicyUpdateRsp:");
		logger.info(marshaller.marshalWithoutRootElement(response));
	}

	@Test
	public void testRetrievePolicy() throws SpiritAdapterException,
			SimpleMarshallerException {
		String policyId = "FAM.140268652248613:1770818783:1477888469:4ffeea24-5f6f-44a5-abe8-4415219bf5db";
		byte[] response = null;
		try {
			response = adapter.retrievePolicy(policyId);
		} catch (SpiritAdapterException e) {
			logger.error("testRetrievePolicy: FAILED", e);
			throw e;
		}
		logger.info("XACML:");
		logger.info(new String(response));
	}

	@Test
	public void testRetrievePolicy_Prompt() throws SpiritAdapterException,
			SimpleMarshallerException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the policy id to be retrieved:");
		String policyId = scan.nextLine().trim();
		scan.close();
		byte[] response = null;
		try {
			response = adapter.retrievePolicy(policyId);
		} catch (SpiritAdapterException e) {
			logger.error("testRetrievePolicy: FAILED", e);
			throw e;
		}
		logger.info("XACML:");
		logger.info(new String(response));
	}

	@Test
	public void testDiscardPolicy() throws SpiritAdapterException,
			SimpleMarshallerException {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		String policyId = "FAM.140268652248613:1770818783:1477888469:4ffeea24-5f6f-44a5-abe8-4415219bf5db";
		EhrPolicyDiscardRsp response = null;
		try {
			String stateId = adapter.login();
			response = adapter.discardPolicy(policyId, stateId);
		} catch (SpiritAdapterException e) {
			logger.error("testDiscardPolicy: FAILED", e);
			throw e;
		}
		logger.info("EhrPolicyDiscardRsp:");
		logger.info(marshaller.marshalWithoutRootElement(response));
	}

	@Test
	public void testDiscardPolicy_Prompt() throws SpiritAdapterException,
			SimpleMarshallerException {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the policy id to be discarded:");
		String policyId = scan.nextLine().trim();
		scan.close();
		EhrPolicyDiscardRsp response = null;
		try {
			String stateId = adapter.login();
			response = adapter.discardPolicy(policyId, stateId);
		} catch (SpiritAdapterException e) {
			logger.error("testDiscardPolicy: FAILED", e);
			throw e;
		}
		logger.info("EhrPolicyDiscardRsp:");
		logger.info(marshaller.marshalWithoutRootElement(response));
	}

	@Test
	public void generateXMLFile() throws FileNotFoundException,
			URISyntaxException, IOException {
		// Create a SubmissionSet (contains information about the submitter)
		SubmissionSetClientDto submissionSet = new SubmissionSetClientDto();

		AuthorMetadataClientDto author = new AuthorMetadataClientDto();
		author.setPerson("subm.authorPerson");
		// Set some information about the submission
		submissionSet.getAuthor().add(author);
		submissionSet.setDescription("subm.description");

		// Create a document object
		DocumentClientDto submittingDocument = new DocumentClientDto();

		// Read the given file and put it into the document object
		initImageFileToUpload();
		submittingDocument.setBytes(imageFileToUpload);

		// Set some document information
		submittingDocument.getAuthor().add(author);
		submittingDocument.getLegalAuthenticator().add(
				"document.legalAuthenticator");
		submittingDocument.setLanguageCode("DE-TI");
		submittingDocument.setDescription("document.description");
		submittingDocument.setName("document.name");

		// Set the MIMEType of the submitFile
		// submittingDocument.setMimeType("application/pdf");
		// submittingDocument.setMimeType("image/jpeg");
		submittingDocument.setMimeType("text/xml");

		// Some required IHE definitions
		IheClassificationClientDto formatCode = new IheClassificationClientDto();
		formatCode.setNodeRepresentation("CDAR2/IHE 1.0");
		formatCode.setValue("CDAR2/IHE 1.0");
		formatCode.getSchema().add("Connect-a-thon formatCodes");
		submittingDocument.setFormatCode(formatCode);

		IheClassificationClientDto contentTypeCode = new IheClassificationClientDto();
		contentTypeCode.setNodeRepresentation("60591-5");
		contentTypeCode.setValue("Patient Summary");
		contentTypeCode.getSchema().add("2.16.840.1.113883.6.1");
		submissionSet.setContentTypeCode(contentTypeCode);

		IheClassificationClientDto classCode = new IheClassificationClientDto();
		classCode.setNodeRepresentation("60591-5");
		classCode.setValue("Patient Summary");
		classCode.getSchema().add("2.16.840.1.113883.6.1");
		submittingDocument.setClassCode(classCode);

		IheClassificationClientDto typeCode = new IheClassificationClientDto();
		typeCode.setNodeRepresentation("60591-5");
		typeCode.setValue("Patient Summary");
		typeCode.getSchema().add("2.16.840.1.113883.6.1");
		submittingDocument.setTypeCode(typeCode);

		IheClassificationClientDto confidentialityCode = new IheClassificationClientDto();
		confidentialityCode.setNodeRepresentation("N");
		confidentialityCode.setValue("Normal");
		confidentialityCode.getSchema().add(
				"Connect-a-thon confidentialityCodes");
		submittingDocument.setConfidentialityCode(confidentialityCode);

		IheClassificationClientDto healthcareFacilityCode = new IheClassificationClientDto();
		healthcareFacilityCode.setNodeRepresentation("Home");
		healthcareFacilityCode.setValue("Home");
		healthcareFacilityCode.getSchema().add(
				"Connect-a-thon healthcareFacilityTypeCodes");
		submittingDocument.setHealthcareFacilityCode(healthcareFacilityCode);

		IheClassificationClientDto practiceSettingCode = new IheClassificationClientDto();
		practiceSettingCode.setNodeRepresentation("Anesthesia");
		practiceSettingCode.setValue("Anesthesia");
		practiceSettingCode.getSchema().add(
				"Connect-a-thon practiceSettingCodes");
		submittingDocument.setPracticeSettingCode(practiceSettingCode);

		// Create a SourceSubmission (contains the document itself)
		SourceSubmissionClientDto sourceSubmission = new SourceSubmissionClientDto();

		// Put the new document object into the SourceSubmission
		sourceSubmission.getDocuments().add(submittingDocument);

		// Put the SubmissionSet into the SourceSubmission
		sourceSubmission.setSubmissionSet(submissionSet);

		try {

			File file = new File("C:\\backup\\file.xml");
			JAXBContext jaxbContext = JAXBContext
					.newInstance(SourceSubmissionClientDto.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(sourceSubmission, file);
			jaxbMarshaller.marshal(sourceSubmission, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public String convertXMLFileToString(String fileName) {
		try {
			// DocumentBuilderFactory documentBuilderFactory =
			// DocumentBuilderFactory.newInstance();
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(fileName);
			// String StringFromInputStream = IOUtils.toString(inputStream,
			// "UTF-8");

			/*
			 * org.w3c.dom.Document doc =
			 * documentBuilderFactory.newDocumentBuilder().parse(inputStream);
			 * StringWriter stw = new StringWriter(); Transformer serializer =
			 * TransformerFactory.newInstance().newTransformer();
			 * serializer.transform(new DOMSource(doc), new StreamResult(stw));
			 * return stw.toString();
			 */

			StringWriter writer = new StringWriter();
			String encoding = "UTF-8";
			IOUtils.copy(inputStream, writer, encoding);
			System.out.println(writer.toString());
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isSmartC32Doc(DocumentClientDto d) {
		return "text/xml".equals(d.getMimeType())
				&& SMART_NPI.equals(d.getAuthor().get(0).getPerson())
				&& SMART_NPI.equals(d.getSubmissionSet().getAuthor().get(0)
						.getPerson())
				&& "2.16.840.1.113883.10.20.1".equals(d.getFormatCode()
						.getNodeRepresentation())
				&& "HL7 CCD Document".equals(d.getFormatCode().getValue())
				&& d.getFormatCode().getSchema().contains("HITSP")
				&& "34133-9".equals(d.getTypeCode().getNodeRepresentation())
				&& "SUMMARIZATION OF EPISODE NOTE".equals(d.getTypeCode()
						.getValue())
				&& d.getTypeCode().getSchema()
						.contains("2.16.840.1.113883.6.1")
				&& "34133-9".equals(d.getClassCode().getNodeRepresentation())
				&& "SUMMARIZATION OF EPISODE NOTE".equals(d.getClassCode()
						.getValue())
				&& d.getClassCode().getSchema()
						.contains("2.16.840.1.113883.6.1")
				&& "34133-9".equals(d.getSubmissionSet().getContentTypeCode()
						.getNodeRepresentation())
				&& "SUMMARIZATION OF EPISODE NOTE".equals(d.getSubmissionSet()
						.getContentTypeCode().getValue())
				&& d.getSubmissionSet().getContentTypeCode().getSchema()
						.contains("2.16.840.1.113883.6.1")
				&& "urn:oasis:names:tc:ebxml-regrep:StatusType:Approved"
						.equals(d.getDocStatus());
	}

	private void initSpiritAdapter(String endpoint, String org, String pwd,
			String rol, String user, String empiDomain, String pidDomain,
			String pidDomainType, String pidEnvType)
			throws SpiritAdapterException {
		this.endpointAddress = endpoint;
		this.org = org;
		this.pwd = pwd;
		this.rol = rol;
		this.user = user;
		final String domain = empiDomain;
		final String c2sDomain = pidDomain;
		final String c2sDomainType = pidDomainType;
		final String c2sEnvType = pidEnvType;
		adapter = new SpiritAdapterImpl(endpointAddress, org, pwd, rol, user,
				domain, c2sDomain, c2sDomainType, c2sEnvType);

		stateId = adapter.login();
	}

	private String[] initPatientIdsByCommandLine(Scanner scan) {
		System.out
				.println("Please provide the patient ID(s) in this format: PID1|PID2|PID3|");
		return scan.nextLine().split("\\|");
	}

	private void initSpiritAdapterByCommandLine(Scanner scan)
			throws SpiritAdapterException {
		System.out
				.println("Please provide the config in this format: endpoint|org|pwd|rol|user|empiDomain|pidDomain|pidDomainType|pidEnvType|");
		String configLine = scan.nextLine();
		String[] config = configLine.split("\\|");
		initSpiritAdapter(config[0], config[1], config[2], config[3],
				config[4], config[5], config[6], config[7], config[8]);
	}

	private void initImageFileToUpload() throws URISyntaxException,
			FileNotFoundException, IOException {
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		URL resource2 = classloader
				.getResource("FilesToUpload/sample-image-file.jpg");
		File file2 = new File(resource2.toURI());
		FileInputStream fis2 = new FileInputStream(file2);
		imageFileToUpload = new byte[(int) file2.length()];
		fis2.read(imageFileToUpload);
		fis2.close();
	}

	private void submitSmartC32_SmartWay(String smartPatientId) {
		try {
			List<EhrPatientClientDto> patientList = adapter
					.queryPatientsWithPids(smartPatientId, stateId)
					.getEhrPatientClientListDto();
			EhrPatientClientDto patientDto = patientList.get(0);
			XdsSrcSubmitReq submitRequest = new XdsSrcSubmitReq();
			submitRequest.setStateID(stateId);
			submitRequest.setPatient(patientDto);
			submitRequest.setUseHL7V3Style(false);
			submitRequest.setEncrypted(false);
			submitRequest.setSigned(false);
			submitRequest.setSrcSubmission(new SourceSubmissionClientDto());
			submitRequest.getSrcSubmission().getDocuments()
					.add(new DocumentClientDto());
			submitRequest.getSrcSubmission().getDocuments().get(0).getAuthor()
					.add(new AuthorMetadataClientDto());

			submitRequest.getSrcSubmission().getDocuments().get(0).getAuthor()
					.get(0).setPerson(SMART_NPI);

			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setBytes(this.smartC32Bytes);

			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setClassCode(new IheClassificationClientDto());
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getClassCode().setNodeRepresentation("34133-9");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getClassCode().getSchema().add("2.16.840.1.113883.6.1");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getClassCode().setValue("SUMMARIZATION OF EPISODE NOTE");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setConfidentialityCode(new IheClassificationClientDto());
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getConfidentialityCode().setNodeRepresentation("N");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getConfidentialityCode().getSchema()
					.add("Connect-a-thon confidentialityCodes");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getConfidentialityCode().setValue("Individual");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setDescription("SMART C32");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setFormatCode(new IheClassificationClientDto());
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getFormatCode()
					.setNodeRepresentation("2.16.840.1.113883.10.20.1");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getFormatCode().getSchema().add("HITSP");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getFormatCode().setValue("HL7 CCD Document");
			submitRequest
					.getSrcSubmission()
					.getDocuments()
					.get(0)
					.setHealthcareFacilityCode(new IheClassificationClientDto());
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getHealthcareFacilityCode().setNodeRepresentation("Home");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getHealthcareFacilityCode().getSchema()
					.add("Connect-a-thon healthcareFacilityTypeCodes");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getHealthcareFacilityCode().setValue("Home");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setLanguageCode("EN-US");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getLegalAuthenticator().add("document.legalAuthenticator");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setMimeType("text/xml");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setName("SMART C32");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setPracticeSettingCode(new IheClassificationClientDto());
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getPracticeSettingCode().setNodeRepresentation("Home");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getPracticeSettingCode().getSchema()
					.add("Connect-a-thon practiceSettingCodes");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getPracticeSettingCode().setValue("Home");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.setTypeCode(new IheClassificationClientDto());
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getTypeCode().setNodeRepresentation("34133-9");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getTypeCode().getSchema().add("2.16.840.1.113883.6.1");
			submitRequest.getSrcSubmission().getDocuments().get(0)
					.getTypeCode().setValue("SUMMARIZATION OF EPISODE NOTE");
			submitRequest.getSrcSubmission().setSubmissionSet(
					new SubmissionSetClientDto());
			submitRequest.getSrcSubmission().getSubmissionSet().getAuthor()
					.add(new AuthorMetadataClientDto());
			submitRequest.getSrcSubmission().getSubmissionSet().getAuthor()
					.get(0).setPerson(SMART_NPI);

			submitRequest.getSrcSubmission().getSubmissionSet()
					.setContentTypeCode(new IheClassificationClientDto());
			submitRequest.getSrcSubmission().getSubmissionSet()
					.getContentTypeCode().setNodeRepresentation("34133-9");
			submitRequest.getSrcSubmission().getSubmissionSet()
					.getContentTypeCode().getSchema()
					.add("2.16.840.1.113883.6.1");
			submitRequest.getSrcSubmission().getSubmissionSet()
					.getContentTypeCode()
					.setValue("SUMMARIZATION OF EPISODE NOTE");
			submitRequest.getSrcSubmission().getSubmissionSet()
					.setDescription("subm.description");

			System.out.println("Submitting SMART C32 for patient: "
					+ smartPatientId);
			adapter.submitDocument(submitRequest.getPatient(),
					submitRequest.getSrcSubmission(), false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void deprecateC32sOfASmartPatient(String smartPatientId) {
		try {
			System.out.println("Querying documents of patient: "
					+ smartPatientId);
			List<EhrPatientClientDto> patientList = adapter
					.queryPatientsWithPids(smartPatientId, stateId)
					.getEhrPatientClientListDto();
			EhrPatientClientDto patientDto = patientList.get(0);
			EhrXdsQRsp patientContent = adapter.queryPatientContent(patientDto,
					stateId);
			System.out.println("Total number of documents found: "
					+ patientContent.getResponseData().getDocuments().size());
			List<DocumentClientDto> smartC32sToDeprecate = patientContent
					.getResponseData().getDocuments().stream()
					.filter(SpiritAdapterImplIT::isSmartC32Doc)
					.collect(Collectors.toList());
			System.out.println("Number of documents after filtering: "
					+ smartC32sToDeprecate.size());
			List<String> smartC32IdsToDeprecate = smartC32sToDeprecate.stream()
					.map((d -> d.getUniqueId())).collect(Collectors.toList());
			String deprecateMsg = smartC32IdsToDeprecate.size() > 0 ? "Document IDs to deprecate: "
					+ smartC32IdsToDeprecate.toString()
					: "No documents found to deprecate!";
			System.out.println(deprecateMsg);
			adapter.deprecateDocuments(smartC32IdsToDeprecate, smartPatientId,
					stateId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
