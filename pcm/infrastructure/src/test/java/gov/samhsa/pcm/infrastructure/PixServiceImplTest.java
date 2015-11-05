package gov.samhsa.pcm.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.pcm.hl7.Hl7v3Transformer;
import gov.samhsa.pcm.hl7.Hl7v3TransformerException;
import gov.samhsa.pcm.pixclient.service.PixManagerService;
import gov.samhsa.pcm.pixclient.util.PixManagerBean;
import gov.samhsa.pcm.pixclient.util.PixManagerConstants;
import gov.samhsa.pcm.pixclient.util.PixManagerMessageHelper;
import gov.samhsa.pcm.pixclient.util.PixManagerRequestXMLToJava;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class PixServiceImplTest {

	@InjectMocks
	private PixServiceImpl sut;;

	@Mock
	private PixManagerRequestXMLToJava requestXMLToJava;
	@Mock
	private PixManagerService pixManagerService;
	@Mock
	private Hl7v3Transformer hl7v3Transformer;
	@Spy
	private PixManagerMessageHelper pixManagerMessageHelper;
	@Mock
	private SimpleMarshallerImpl marshallerMock;

	private SimpleMarshallerImpl marshaller;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private final String MRN_DOMAIN = "MRN_DOMAIN";
	private final String MRN = "MRN";
	private final String EXPECTED_EID = "1c5c59f0-5788-11e3-84b3-00155d3a2124";
	private final String EXPECTED_DOMAIN = "2.16.840.1.113883.4.357";

	@Before
	public void setUp() throws Exception {
		marshaller = new SimpleMarshallerImpl();
	}

	@Test
	public void testGetEid() throws Hl7v3TransformerException, JAXBException,
			IOException {
		// Arrange
		ReflectionTestUtils.setField(sut, "mrnDomain", MRN_DOMAIN);
		PixManagerConstants.GLOBAL_DOMAIN_ID = EXPECTED_DOMAIN;

		arrangeCommonValid();

		// Act
		String eid = sut.getEid(MRN);

		// Assert
		assertEquals(EXPECTED_EID, eid);
		verify(marshallerMock, times(2)).marshal(anyObject());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetEid_Query_Failure_Hl7v3TransformerException()
			throws Hl7v3TransformerException {
		// Arrange
		thrown.expect(IllegalArgumentException.class);
		ReflectionTestUtils.setField(sut, "mrnDomain", MRN_DOMAIN);
		PixManagerConstants.GLOBAL_DOMAIN_ID = EXPECTED_DOMAIN;
		when(
				hl7v3Transformer.getPixQueryXml(MRN, MRN_DOMAIN,
						Hl7v3Transformer.XML_PIX_QUERY)).thenThrow(
				Hl7v3TransformerException.class);

		// Act
		String eid = sut.getEid(MRN);

		// Assert
		assertNull(eid);
	}

	@Test
	public void testQueryPerson() throws Hl7v3TransformerException,
			JAXBException, IOException {
		// Arrange
		String expectedQueryMessage = "Query Success!  Given PID: PUI100010060001 Given UID: 2.16.840.1.113883.3.72.5.9.1	";

		arrangeCommonValid();

		// Act
		PixManagerBean bean = sut.queryPatient(MRN, MRN_DOMAIN);

		// Assert
		assertNotNull(bean);
		assertEquals(expectedQueryMessage, bean.getQueryMessage());
		assertEquals(EXPECTED_EID, bean.getQueryIdMap().get(EXPECTED_DOMAIN));
		assertEquals("", bean.getAddMessage());
		assertEquals("", bean.getUpdateMessage());
		verify(marshallerMock, times(2)).marshal(anyObject());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testQueryPerson_Throws_Hl7v3TransformerException()
			throws Hl7v3TransformerException, JAXBException, IOException {
		// Arrange
		when(
				hl7v3Transformer.getPixQueryXml(MRN, MRN_DOMAIN,
						Hl7v3Transformer.XML_PIX_QUERY)).thenThrow(
				Hl7v3TransformerException.class);
		thrown.expect(RuntimeException.class);

		// Act
		@SuppressWarnings("unused")
		PixManagerBean bean = sut.queryPatient(MRN, MRN_DOMAIN);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testQueryPerson_Query_Failure_JAXBException()
			throws Hl7v3TransformerException, JAXBException, IOException {
		// Arrange
		String expectedErrorMessage = "Query Failure! Server error! A unexpected error has occured";
		String hl7v3 = "hl7v3";
		when(
				hl7v3Transformer.getPixQueryXml(MRN, MRN_DOMAIN,
						Hl7v3Transformer.XML_PIX_QUERY)).thenReturn(hl7v3);
		when(
				requestXMLToJava.getPIXQueryReqObject(hl7v3,
						PixManagerConstants.ENCODE_STRING)).thenThrow(
				JAXBException.class);

		// Act
		PixManagerBean bean = sut.queryPatient(MRN, MRN_DOMAIN);

		// Assert
		assertEquals(expectedErrorMessage, bean.getQueryMessage());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testQueryPerson_Query_Failure_IOException()
			throws Hl7v3TransformerException, JAXBException, IOException {
		// Arrange
		String expectedErrorMessage = "Query Failure! Server error! A unexpected error has occured";
		String hl7v3 = "hl7v3";
		when(
				hl7v3Transformer.getPixQueryXml(MRN, MRN_DOMAIN,
						Hl7v3Transformer.XML_PIX_QUERY)).thenReturn(hl7v3);
		when(
				requestXMLToJava.getPIXQueryReqObject(hl7v3,
						PixManagerConstants.ENCODE_STRING)).thenThrow(
				IOException.class);

		// Act
		PixManagerBean bean = sut.queryPatient(MRN, MRN_DOMAIN);

		// Assert
		assertEquals(expectedErrorMessage, bean.getQueryMessage());
	}

	// Arrange common valid scenario
	private void arrangeCommonValid() throws JAXBException,
			Hl7v3TransformerException, IOException {
		String hl7v3 = "hl7v3";
		String responseMockString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PRPA_IN201310UV02 ITSVersion=\"XML_1.0\" xmlns=\"urn:hl7-org:v3\"><id root=\"1.1\" extension=\"107e7d8d-5d80-4744-a4b3-3462edbacca6\"/><creationTime value=\"20131216193923\"/><interactionId root=\"2.16.840.1.113883.1.6\" extension=\"PRPA_IN201310UV02\"/><processingCode code=\"P\"/><processingModeCode code=\"T\"/><acceptAckCode code=\"NE\"/><receiver typeCode=\"RCV\"><device classCode=\"DEV\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99998.8734\"/></device></receiver><sender typeCode=\"SND\"><device classCode=\"DEV\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99999.4567\"/></device></sender><acknowledgement><typeCode code=\"AA\"/><targetMessage><id root=\"2220c1c4-87ef-11dc-b865-3603d6866807\"/></targetMessage></acknowledgement><controlActProcess classCode=\"CACT\" moodCode=\"EVN\"><code code=\"PRPA_TE201310UV02\"/><subject typeCode=\"SUBJ\"><registrationEvent classCode=\"REG\" moodCode=\"EVN\"><id nullFlavor=\"NA\"/><statusCode code=\"active\"/><subject1 typeCode=\"SBJ\"><patient classCode=\"PAT\"><id root=\"2.16.840.1.113883.4.357\" extension=\"1c5c59f0-5788-11e3-84b3-00155d3a2124\"/><statusCode code=\"active\"/><patientPerson classCode=\"PSN\" determinerCode=\"INSTANCE\"><name nullFlavor=\"NA\"/></patientPerson></patient></subject1><custodian typeCode=\"CST\"><assignedEntity classCode=\"ASSIGNED\"><id root=\"1.3.6.1.4.1.21367.13.50.106\"/></assignedEntity></custodian></registrationEvent></subject><queryAck><queryId root=\"1.2.840.114350.1.13.99999.4567.34\" extension=\"018499884245\"/><queryResponseCode code=\"OK\"/></queryAck><queryByParameter><queryId root=\"1.2.840.114350.1.13.99999.4567.34\" extension=\"018499884245\"/><statusCode code=\"new\"/><responsePriorityCode code=\"I\"/><parameterList><patientIdentifier><value root=\"2.16.840.1.113883.3.72.5.9.1\" extension=\"PUI100010060001\"/><semanticsText/></patientIdentifier></parameterList></queryByParameter></controlActProcess></PRPA_IN201310UV02>";
		PRPAIN201309UV02 requestMock = mock(PRPAIN201309UV02.class);
		PRPAIN201310UV02 responseMock = marshaller.unmarshalFromXml(
				PRPAIN201310UV02.class, responseMockString);
		when(
				hl7v3Transformer.getPixQueryXml(MRN, MRN_DOMAIN,
						Hl7v3Transformer.XML_PIX_QUERY)).thenReturn(hl7v3);
		when(
				requestXMLToJava.getPIXQueryReqObject(hl7v3,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixManagerService.pixManagerPRPAIN201309UV02(requestMock))
				.thenReturn(responseMock);
	}
}
