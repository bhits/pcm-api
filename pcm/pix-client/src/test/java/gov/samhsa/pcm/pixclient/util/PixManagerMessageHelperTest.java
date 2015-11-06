package gov.samhsa.pcm.pixclient.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.hl7.v3.types.ObjectFactory;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PixManagerMessageHelperTest {
	private PixManagerMessageHelper sut;

	@Before
	public void setUp() throws Exception {
		sut = new PixManagerMessageHelper();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetGeneralExpMessage_add() {
		// Arrange
		JAXBException JAXBExceptionMock = mock(JAXBException.class);
		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getGeneralExpMessage(JAXBExceptionMock, pixManagerBean,
				PixManagerConstants.PIX_ADD);

		// Asserts
		assertTrue("Actual Add message " + pixManagerBean.getAddMessage(),
				pixManagerBean.getAddMessage().startsWith("Add Failure! "));
	}

	@Test
	public void testGetGeneralExpMessage_Update() {
		// Arrange
		JAXBException JAXBExceptionMock = mock(JAXBException.class);
		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getGeneralExpMessage(JAXBExceptionMock, pixManagerBean,
				PixManagerConstants.PIX_UPDATE);

		// Assert
		assertTrue(
				"Actual Update message " + pixManagerBean.getUpdateMessage(),
				pixManagerBean.getUpdateMessage()
						.startsWith("Update Failure! "));
	}

	@Test
	public void testGetGeneralExpMessage_Query() {
		// Arrange
		JAXBException JAXBExceptionMock = mock(JAXBException.class);
		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getGeneralExpMessage(JAXBExceptionMock, pixManagerBean,
				PixManagerConstants.PIX_QUERY);

		// Assert
		assertTrue("Actual Query message " + pixManagerBean.getQueryMessage(),
				pixManagerBean.getQueryMessage().startsWith("Query Failure! "));
	}

	@Test
	public void testGetAddUpdateMessage() throws JAXBException {
		// Arrange

		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject(
				"xml/AddSample_MCCIIN000002UV01.xml",
				PixManagerConstants.ENCODE_STRING);

		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getAddUpdateMessage(mCCIIN000002UV01, pixManagerBean,
				PixManagerConstants.PIX_ADD);

		// Assert
		assertTrue("Actual Add message " + pixManagerBean.getAddMessage(),
				pixManagerBean.getAddMessage().startsWith("Add Success! "));
	}

	@Test
	public void testGetAddUpdateMessage_error() throws JAXBException {

		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject(
				"xml/AddSampleError_MCCIIN000002UV01.xml",
				PixManagerConstants.ENCODE_STRING);

		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getAddUpdateMessage(mCCIIN000002UV01, pixManagerBean,
				PixManagerConstants.PIX_ADD);

		// Assert
		assertTrue("Actual Add message " + pixManagerBean.getAddMessage(),
				pixManagerBean.getAddMessage().startsWith("Add Failure! "));
	}

	@Test
	public void testGetAddUpdateMessage_errorcode() throws JAXBException {
		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject(
				"xml/AddSampleErrorCode_MCCIIN000002UV01.xml",
				PixManagerConstants.ENCODE_STRING);

		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getAddUpdateMessage(mCCIIN000002UV01, pixManagerBean,
				PixManagerConstants.PIX_ADD);

		// Assert
		assertTrue("Actual Add message " + pixManagerBean.getAddMessage(),
				pixManagerBean.getAddMessage().startsWith("Add Failure! "));
	}

	@Test
	public void testGetAddUpdateMessage_Update() throws JAXBException {
		// Arrange

		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject(
				"xml/AddSample_MCCIIN000002UV01.xml",
				PixManagerConstants.ENCODE_STRING);

		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getAddUpdateMessage(mCCIIN000002UV01, pixManagerBean,
				PixManagerConstants.PIX_UPDATE);

		// Assert
		assertTrue(
				"Actual Update message " + pixManagerBean.getUpdateMessage(),
				pixManagerBean.getUpdateMessage()
						.startsWith("Update Success! "));
	}

	@Test
	public void testGetAddUpdateMessage_Update_error() throws JAXBException {

		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject(
				"xml/AddSampleError_MCCIIN000002UV01.xml",
				PixManagerConstants.ENCODE_STRING);

		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getAddUpdateMessage(mCCIIN000002UV01, pixManagerBean,
				PixManagerConstants.PIX_UPDATE);

		// Assert
		assertTrue(
				"Actual Update message " + pixManagerBean.getUpdateMessage(),
				pixManagerBean.getUpdateMessage()
						.startsWith("Update Failure! "));
	}

	@Test
	public void testGetAddUpdateMessage_Update_errorcode() throws JAXBException {
		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject(
				"xml/AddSampleErrorCode_MCCIIN000002UV01.xml",
				PixManagerConstants.ENCODE_STRING);

		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getAddUpdateMessage(mCCIIN000002UV01, pixManagerBean,
				PixManagerConstants.PIX_UPDATE);

		// Assert
		assertTrue(
				"Actual Update message " + pixManagerBean.getUpdateMessage(),
				pixManagerBean.getUpdateMessage()
						.startsWith("Update Failure! "));
	}

	@Test
	public void testGetQueryMessage() throws JAXBException {
		// Arrange

		PRPAIN201310UV02 pRPAIN201310UV02 = (PRPAIN201310UV02) getPIXResObject(
				"xml/QuerySample_PRPA_IN201310UV02.xml",
				PixManagerConstants.ENCODE_STRING);

		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getQueryMessage(pRPAIN201310UV02, pixManagerBean);

		// Assert
		assertTrue("Actual query message " + pixManagerBean.getQueryMessage(),
				pixManagerBean.getQueryMessage().startsWith("Query Success! "));
	}

	@Test
	public void testGetQueryMessage_error() throws JAXBException {
		// Arrange

		PRPAIN201310UV02 pRPAIN201310UV02 = (PRPAIN201310UV02) getPIXResObject(
				"xml/QuerySampleError_PRPA_IN201310UV02.xml",
				PixManagerConstants.ENCODE_STRING);

		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getQueryMessage(pRPAIN201310UV02, pixManagerBean);

		// Assert
		assertTrue("Actual query message " + pixManagerBean.getQueryMessage(),
				pixManagerBean.getQueryMessage().startsWith("Query Failure! "));
	}

	@Test
	public void testGetQueryMessage_errorcode() throws JAXBException {
		// Arrange

		PRPAIN201310UV02 pRPAIN201310UV02 = (PRPAIN201310UV02) getPIXResObject(
				"xml/QuerySampleErrorCode_PRPA_IN201310UV02.xml",
				PixManagerConstants.ENCODE_STRING);

		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getQueryMessage(pRPAIN201310UV02, pixManagerBean);

		// Assert
		assertTrue("Actual query message " + pixManagerBean.getQueryMessage(),
				pixManagerBean.getQueryMessage().startsWith("Query Failure! "));
	}

	private Object getPIXResObject(String reqXMLFilePath, String encodeString)
			throws JAXBException {

		// 1. We need to create JAXContext instance
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

		// 2. Use JAXBContext instance to create the Unmarshaller.
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		if (reqXMLFilePath == null) {
			throw new JAXBException("input is null");
		}

		// 3. Use the Unmarshaller to unmarshal the XML document to get an
		// instance of JAXBElement.
		// 4. Get the instance of the required JAXB Root Class from the
		// JAXBElement.
		Object resObj = unmarshaller.unmarshal(getClass().getClassLoader()
				.getResourceAsStream(reqXMLFilePath));

		return resObj;
	}

}
