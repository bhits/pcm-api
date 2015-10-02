package gov.samhsa.acs.xdsb.repository.wsclient.adapter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.acs.common.tool.exception.DocumentXmlConverterException;
import gov.samhsa.acs.xdsb.common.XdsbErrorFactory;
import gov.samhsa.acs.xdsb.repository.wsclient.exception.XdsbRepositoryAdapterException;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.util.LinkedList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class ConsentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class RetrieveDocumentSetResponseFilterTest {

	@Mock
	private DocumentXmlConverter converter;

	@Mock
	private DocumentAccessor documentAccessor;

	@Mock
	private XdsbErrorFactory xdsbErrorFactory;

	@InjectMocks
	private RetrieveDocumentSetResponseFilter sut;

	@SuppressWarnings("unchecked")
	@Test(expected = XdsbRepositoryAdapterException.class)
	public void testFilterByPatientAndAuthor()
			throws XdsbRepositoryAdapterException, DocumentAccessorException {

		final RetrieveDocumentSetResponse response = mock(RetrieveDocumentSetResponse.class);
		final String patientId = "1";
		final String authorNPI = "2";

		final DocumentResponse docResponse1 = mock(DocumentResponse.class);
		final DocumentResponse docResponse2 = mock(DocumentResponse.class);
		final Node nodeMock = mock(Node.class);
		final Optional<Node> nodeMockOptional = Optional.of(nodeMock);
		final Document documentMock = mock(Document.class);

		final byte[] document1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\r\n    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\r\n    exclude-result-prefixes=\"xs\"\r\n    xmlns:hl7=\"urn:hl7-org:v3\"\r\n    version=\"2.0\">\r\n    <xsl:output method=\"xml\" version=\"1.0\" indent=\"yes\" encoding=\"UTF-8\"/>   \r\n <xsl:template match=\"/\">\r\n    \r\n     <urn:PRPA_IN201301UV02 xmlns:urn=\"urn:hl7-org:v3\"  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n         xsi:schemaLocation=\"urn:hl7-org:v3 ../schema/HL7V3/NE2008/multicacheschemas/PRPA_IN201301UV02.xsd\"\r\n         xmlns=\"urn:hl7-org:v3\" ITSVersion=\"XML_1.0\">\r\n         <urn:id root=\"21acf7be-007c-41e6-b176-d0969794983b\"/>\r\n         <urn:creationTime value=\"20091112115139\"/>\r\n         <urn:interactionId extension=\"PRPA_IN201301UV02\" root=\"2.16.840.1.113883.1.6\"/>\r\n         <urn:processingCode code=\"P\"/>\r\n         <urn:processingModeCode code=\"T\"/>\r\n         <urn:acceptAckCode code=\"AL\"/>\r\n         <urn:receiver typeCode=\"RCV\">\r\n             <urn:device classCode=\"DEV\" determinerCode=\"INSTANCE\">\r\n                 <urn:id root=\"1.2.840.114350.1.13.99999.4567\"/>\r\n                 <urn:asAgent classCode=\"AGNT\">\r\n                     <urn:representedOrganization determinerCode=\"INSTANCE\" classCode=\"ORG\">\r\n                         <urn:id root=\"1.2.840.114350.1.13.99999.1234\"/>\r\n                     </urn:representedOrganization>\r\n                 </urn:asAgent>\r\n             </urn:device>\r\n         </urn:receiver>\r\n         <urn:sender typeCode=\"SND\">\r\n             <urn:device classCode=\"DEV\" determinerCode=\"INSTANCE\">\r\n                 <urn:id root=\"1.2.840.114350.1.13.99998.8734\"/>\r\n                 <urn:asAgent classCode=\"AGNT\">\r\n                     <urn:representedOrganization determinerCode=\"INSTANCE\" classCode=\"ORG\">\r\n                         <urn:id root=\"1.2.840.114350.1.13.99998\"/>\r\n                     </urn:representedOrganization>\r\n                 </urn:asAgent>\r\n             </urn:device>\r\n         </urn:sender>\r\n         <urn:controlActProcess classCode=\"CACT\" moodCode=\"EVN\">\r\n             <urn:code code=\"PRPA_TE201301UV02\" codeSystem=\"2.16.840.1.113883.1.6\"/>\r\n             <urn:subject typeCode=\"SUBJ\">\r\n                 <urn:registrationEvent classCode=\"REG\" moodCode=\"EVN\">\r\n                     <urn:id nullFlavor=\"NA\"/>\r\n                     <urn:statusCode code=\"active\"/>\r\n                     <urn:subject1 typeCode=\"SBJ\">\r\n                         <urn:patient classCode=\"PAT\">\r\n                             <urn:id extension=\"{/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:id/@extension}\" assigningAuthorityName=\"NIST2010\" root=\"2.16.840.1.113883.3.72.5.9.1\"/>\r\n                             <urn:statusCode code=\"active\"/>\r\n                             <urn:patientPerson>\r\n                                 <urn:name>\r\n                                     <urn:given><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:name/hl7:given\"/></urn:given>\r\n                                     <urn:family><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:name/hl7:family\"/></urn:family>\r\n                                 </urn:name>\r\n                                 <urn:telecom use=\"H\" value=\"tel:610-220-4354\"/>\r\n                                 <urn:administrativeGenderCode code=\"{/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:administrativeGenderCode/@code}\"/>\r\n                                 <urn:birthTime value=\"{/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:birthTime/@value}\"/>\r\n                                 <urn:addr>\r\n                                     <urn:streetAddressLine><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:streetAddressLine\"/></urn:streetAddressLine>\r\n                                     <urn:city><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:city\"/></urn:city>\r\n                                     <urn:state><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:state\"/></urn:state>\r\n                                     <urn:postalCode><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:postalCode\"/></urn:postalCode>\r\n                                 </urn:addr>\r\n                                 <urn:asOtherIDs classCode=\"CIT\">\r\n                                     <urn:id root=\"2.16.840.1.113883.4.1\" extension=\"197-18-9761\"/>\r\n                                     <urn:scopingOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">\r\n                                         <urn:id root=\"2.16.840.1.113883.4.1\"/>\r\n                                     </urn:scopingOrganization>\r\n                                 </urn:asOtherIDs>\r\n                                 <urn:personalRelationship classCode=\"PRS\">\r\n                                     <urn:code codeSystem=\"2.16.840.1.113883.5.111\" codeSystemName=\"PersonalRelationshipRoleType\" code=\"MTH\" displayName=\"Mother\"/>\r\n                                     <urn:relationshipHolder1 classCode=\"PSN\" determinerCode=\"INSTANCE\">\r\n                                     </urn:relationshipHolder1>\r\n                                 </urn:personalRelationship>\r\n                             </urn:patientPerson>\r\n                             <urn:providerOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">\r\n                                 <urn:id root=\"1.2.840.114350.1.13.99998.8734\"/>\r\n                                 <urn:contactParty classCode=\"CON\"/>\r\n                             </urn:providerOrganization>\r\n                         </urn:patient>\r\n                     </urn:subject1>\r\n                     <urn:custodian typeCode=\"CST\">\r\n                         <urn:assignedEntity classCode=\"ASSIGNED\">\r\n                             <urn:id root=\"1.2.840.114350.1.13.99998.8734\"/>\r\n                         </urn:assignedEntity>\r\n                     </urn:custodian>\r\n                 </urn:registrationEvent>\r\n             </urn:subject>\r\n         </urn:controlActProcess>\r\n     </urn:PRPA_IN201301UV02>\r\n     \r\n </xsl:template>   \r\n    \r\n    \r\n    \r\n</xsl:stylesheet>"
				.getBytes();
		final byte[] document2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\r\n    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\r\n    exclude-result-prefixes=\"xs\"\r\n    xmlns:hl7=\"urn:hl7-org:v3\"\r\n    version=\"2.0\">\r\n    <xsl:output method=\"xml\" version=\"1.0\" indent=\"yes\" encoding=\"UTF-8\"/>   \r\n <xsl:template match=\"/\">\r\n    \r\n     <urn:PRPA_IN201301UV02 xmlns:urn=\"urn:hl7-org:v3\"  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n         xsi:schemaLocation=\"urn:hl7-org:v3 ../schema/HL7V3/NE2008/multicacheschemas/PRPA_IN201301UV02.xsd\"\r\n         xmlns=\"urn:hl7-org:v3\" ITSVersion=\"XML_1.0\">\r\n         <urn:id root=\"21acf7be-007c-41e6-b176-d0969794983b\"/>\r\n         <urn:creationTime value=\"20091112115139\"/>\r\n         <urn:interactionId extension=\"PRPA_IN201301UV02\" root=\"2.16.840.1.113883.1.6\"/>\r\n         <urn:processingCode code=\"P\"/>\r\n         <urn:processingModeCode code=\"T\"/>\r\n         <urn:acceptAckCode code=\"AL\"/>\r\n         <urn:receiver typeCode=\"RCV\">\r\n             <urn:device classCode=\"DEV\" determinerCode=\"INSTANCE\">\r\n                 <urn:id root=\"1.2.840.114350.1.13.99999.4567\"/>\r\n                 <urn:asAgent classCode=\"AGNT\">\r\n                     <urn:representedOrganization determinerCode=\"INSTANCE\" classCode=\"ORG\">\r\n                         <urn:id root=\"1.2.840.114350.1.13.99999.1234\"/>\r\n                     </urn:representedOrganization>\r\n                 </urn:asAgent>\r\n             </urn:device>\r\n         </urn:receiver>\r\n         <urn:sender typeCode=\"SND\">\r\n             <urn:device classCode=\"DEV\" determinerCode=\"INSTANCE\">\r\n                 <urn:id root=\"1.2.840.114350.1.13.99998.8734\"/>\r\n                 <urn:asAgent classCode=\"AGNT\">\r\n                     <urn:representedOrganization determinerCode=\"INSTANCE\" classCode=\"ORG\">\r\n                         <urn:id root=\"1.2.840.114350.1.13.99998\"/>\r\n                     </urn:representedOrganization>\r\n                 </urn:asAgent>\r\n             </urn:device>\r\n         </urn:sender>\r\n         <urn:controlActProcess classCode=\"CACT\" moodCode=\"EVN\">\r\n             <urn:code code=\"PRPA_TE201301UV02\" codeSystem=\"2.16.840.1.113883.1.6\"/>\r\n             <urn:subject typeCode=\"SUBJ\">\r\n                 <urn:registrationEvent classCode=\"REG\" moodCode=\"EVN\">\r\n                     <urn:id nullFlavor=\"NA\"/>\r\n                     <urn:statusCode code=\"active\"/>\r\n                     <urn:subject1 typeCode=\"SBJ\">\r\n                         <urn:patient classCode=\"PAT\">\r\n                             <urn:id extension=\"{/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:id/@extension}\" assigningAuthorityName=\"NIST2010\" root=\"2.16.840.1.113883.3.72.5.9.1\"/>\r\n                             <urn:statusCode code=\"active\"/>\r\n                             <urn:patientPerson>\r\n                                 <urn:name>\r\n                                     <urn:given><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:name/hl7:given\"/></urn:given>\r\n                                     <urn:family><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:name/hl7:family\"/></urn:family>\r\n                                 </urn:name>\r\n                                 <urn:telecom use=\"H\" value=\"tel:610-220-4354\"/>\r\n                                 <urn:administrativeGenderCode code=\"{/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:administrativeGenderCode/@code}\"/>\r\n                                 <urn:birthTime value=\"{/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:birthTime/@value}\"/>\r\n                                 <urn:addr>\r\n                                     <urn:streetAddressLine><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:streetAddressLine\"/></urn:streetAddressLine>\r\n                                     <urn:city><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:city\"/></urn:city>\r\n                                     <urn:state><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:state\"/></urn:state>\r\n                                     <urn:postalCode><xsl:value-of select=\"/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:postalCode\"/></urn:postalCode>\r\n                                 </urn:addr>\r\n                                 <urn:asOtherIDs classCode=\"CIT\">\r\n                                     <urn:id root=\"2.16.840.1.113883.4.1\" extension=\"197-18-9761\"/>\r\n                                     <urn:scopingOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">\r\n                                         <urn:id root=\"2.16.840.1.113883.4.1\"/>\r\n                                     </urn:scopingOrganization>\r\n                                 </urn:asOtherIDs>\r\n                                 <urn:personalRelationship classCode=\"PRS\">\r\n                                     <urn:code codeSystem=\"2.16.840.1.113883.5.111\" codeSystemName=\"PersonalRelationshipRoleType\" code=\"MTH\" displayName=\"Mother\"/>\r\n                                     <urn:relationshipHolder1 classCode=\"PSN\" determinerCode=\"INSTANCE\">\r\n                                     </urn:relationshipHolder1>\r\n                                 </urn:personalRelationship>\r\n                             </urn:patientPerson>\r\n                             <urn:providerOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\">\r\n                                 <urn:id root=\"1.2.840.114350.1.13.99998.8734\"/>\r\n                                 <urn:contactParty classCode=\"CON\"/>\r\n                             </urn:providerOrganization>\r\n                         </urn:patient>\r\n                     </urn:subject1>\r\n                     <urn:custodian typeCode=\"CST\">\r\n                         <urn:assignedEntity classCode=\"ASSIGNED\">\r\n                             <urn:id root=\"1.2.840.114350.1.13.99998.8734\"/>\r\n                         </urn:assignedEntity>\r\n                     </urn:custodian>\r\n                 </urn:registrationEvent>\r\n             </urn:subject>\r\n         </urn:controlActProcess>\r\n     </urn:PRPA_IN201301UV02>\r\n     \r\n </xsl:template>   \r\n    \r\n    \r\n    \r\n</xsl:stylesheet>"
				.getBytes();

		when(docResponse1.getDocument()).thenReturn(document1);
		when(docResponse2.getDocument()).thenReturn(document2);
		when(converter.loadDocument(new String(document1))).thenThrow(
				DocumentXmlConverterException.class);
		doReturn(nodeMockOptional).when(documentAccessor).getNode(
				eq(documentMock), anyString(), any(String[].class));

		final LinkedList<DocumentResponse> docList = new LinkedList<DocumentResponse>();

		docList.add(docResponse1);
		docList.add(docResponse2);

		when(response.getDocumentResponse()).thenReturn(docList);

		sut.filterByPatientAndAuthor(response, patientId, authorNPI);
	}

	@Test
	public void testFilterByPatientAndAuthor2()
			throws XdsbRepositoryAdapterException {

		final RetrieveDocumentSetResponse response = mock(RetrieveDocumentSetResponse.class);
		final String patientId = "1";
		final String authorNPI = "2";

		final LinkedList<DocumentResponse> docList = new LinkedList<DocumentResponse>();

		when(response.getDocumentResponse()).thenReturn(docList);

		sut.filterByPatientAndAuthor(response, patientId, authorNPI);
	}

}
