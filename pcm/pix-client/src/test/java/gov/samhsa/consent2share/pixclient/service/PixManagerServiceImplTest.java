package gov.samhsa.consent2share.pixclient.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.hl7.v3.types.PRPAIN201301UV02;
import org.hl7.v3.types.PRPAIN201302UV02;
import org.hl7.v3.types.PRPAIN201304UV02;
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
import org.openhie.openpixpdq.services.PIXManagerService.PIXManagerPortTypeProxy;

@RunWith(MockitoJUnitRunner.class)
public class PixManagerServiceImplTest {

	private URL wsdlURL;
	private String address;
	private QName serviceName;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@InjectMocks
	@Spy
	PixManagerServiceImpl sut;

	@Mock
	PIXManagerPortTypeProxy pIXManagerPortTypeMock;

	@Before
	public void setup() throws MalformedURLException {
		serviceName = new QName("urn:org:openhie:openpixpdq:services",
				"PIXManager_Service");

		address = "http://localhost:12345/services/PIXManager_Service";
		wsdlURL = ClassLoader.getSystemResource("PIXPDQManager.wsdl");
		when(sut.createPort()).thenReturn(pIXManagerPortTypeMock);
	}

	@Test
	public void testPixManagerPRPAIN201301UV02() {
		// Arrange
		final PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		final MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		when(
				pIXManagerPortTypeMock
				.pixManagerPRPAIN201301UV02(pRPAIN201301UV02Mock))
				.thenReturn(mCCIIN000002UV01Mock);

		// Act
		final MCCIIN000002UV01 actualObj = sut
				.pixManagerPRPAIN201301UV02(pRPAIN201301UV02Mock);

		// Assert
		assertEquals(mCCIIN000002UV01Mock, actualObj);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPixManagerPRPAIN201301UV02_Throws_PixManagerServiceException() {
		// Arrange
		thrown.expect(PixManagerServiceException.class);
		final PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		final MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		when(
				pIXManagerPortTypeMock
				.pixManagerPRPAIN201301UV02(pRPAIN201301UV02Mock))
				.thenThrow(Exception.class);

		// Act
		final MCCIIN000002UV01 actualObj = sut
				.pixManagerPRPAIN201301UV02(pRPAIN201301UV02Mock);

		// Assert
		assertEquals(mCCIIN000002UV01Mock, actualObj);
	}

	@Test
	public void testPixManagerPRPAIN201302UV02() {
		// Arrange
		final PRPAIN201302UV02 pRPAIN201302UV02Mock = mock(PRPAIN201302UV02.class);
		final MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		when(
				pIXManagerPortTypeMock
				.pixManagerPRPAIN201302UV02(pRPAIN201302UV02Mock))
				.thenReturn(mCCIIN000002UV01Mock);

		// Act
		final MCCIIN000002UV01 actualObj = sut
				.pixManagerPRPAIN201302UV02(pRPAIN201302UV02Mock);

		// Assert
		assertEquals(mCCIIN000002UV01Mock, actualObj);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPixManagerPRPAIN201302UV02_Throws_PixManagerServiceException() {
		// Arrange
		thrown.expect(PixManagerServiceException.class);
		final PRPAIN201302UV02 pRPAIN201302UV02Mock = mock(PRPAIN201302UV02.class);
		final MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		when(
				pIXManagerPortTypeMock
				.pixManagerPRPAIN201302UV02(pRPAIN201302UV02Mock))
				.thenThrow(Exception.class);

		// Act
		final MCCIIN000002UV01 actualObj = sut
				.pixManagerPRPAIN201302UV02(pRPAIN201302UV02Mock);

		// Assert
		assertEquals(mCCIIN000002UV01Mock, actualObj);
	}

	@Test
	public void testPixManagerPRPAIN201304UV02() {
		// Arrange
		final PRPAIN201304UV02 pRPAIN201304UV02Mock = mock(PRPAIN201304UV02.class);
		final MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		when(
				pIXManagerPortTypeMock
				.pixManagerPRPAIN201304UV02(pRPAIN201304UV02Mock))
				.thenReturn(mCCIIN000002UV01Mock);

		// Act
		final MCCIIN000002UV01 actualObj = sut
				.pixManagerPRPAIN201304UV02(pRPAIN201304UV02Mock);

		// Assert
		assertEquals(mCCIIN000002UV01Mock, actualObj);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPixManagerPRPAIN201304UV02_Throws_PixManagerServiceException() {
		// Arrange
		thrown.expect(PixManagerServiceException.class);
		final PRPAIN201304UV02 pRPAIN201304UV02Mock = mock(PRPAIN201304UV02.class);
		final MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		when(
				pIXManagerPortTypeMock
				.pixManagerPRPAIN201304UV02(pRPAIN201304UV02Mock))
				.thenThrow(Exception.class);

		// Act
		final MCCIIN000002UV01 actualObj = sut
				.pixManagerPRPAIN201304UV02(pRPAIN201304UV02Mock);

		// Assert
		assertEquals(mCCIIN000002UV01Mock, actualObj);
	}

	@Test
	public void testPixManagerPRPAIN201309UV02() {
		// Arrange
		final PRPAIN201309UV02 pRPAIN201309UV02Mock = mock(PRPAIN201309UV02.class);
		final PRPAIN201310UV02 pRPAIN201310UV02Mock = mock(PRPAIN201310UV02.class);
		when(
				pIXManagerPortTypeMock
				.pixManagerPRPAIN201309UV02(pRPAIN201309UV02Mock))
				.thenReturn(pRPAIN201310UV02Mock);

		// Act
		final PRPAIN201310UV02 actualObj = sut
				.pixManagerPRPAIN201309UV02(pRPAIN201309UV02Mock);

		// Assert
		assertEquals(pRPAIN201310UV02Mock, actualObj);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPixManagerPRPAIN201309UV02_Throws_PixManagerServiceException() {
		// Arrange
		thrown.expect(PixManagerServiceException.class);
		final PRPAIN201309UV02 pRPAIN201309UV02Mock = mock(PRPAIN201309UV02.class);
		final PRPAIN201310UV02 pRPAIN201310UV02Mock = mock(PRPAIN201310UV02.class);
		when(
				pIXManagerPortTypeMock
				.pixManagerPRPAIN201309UV02(pRPAIN201309UV02Mock))
				.thenThrow(Exception.class);

		// Act
		final PRPAIN201310UV02 actualObj = sut
				.pixManagerPRPAIN201309UV02(pRPAIN201309UV02Mock);

		// Assert
		assertEquals(pRPAIN201310UV02Mock, actualObj);
	}

	@Test
	public void testPixManagerServiceImplString() {
		assertEquals(pIXManagerPortTypeMock, sut.createPort());
	}

}
