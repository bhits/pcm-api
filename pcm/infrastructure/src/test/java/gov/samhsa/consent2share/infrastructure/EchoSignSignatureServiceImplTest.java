package gov.samhsa.consent2share.infrastructure;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import echosign.api.clientv20.dto.SenderInfo;
import echosign.api.clientv20.dto16.EmbeddedWidgetCreationResult;
import echosign.api.clientv20.dto8.GetFormDataResult;
import echosign.api.clientv20.dto8.WidgetCreationInfo;
import echosign.api.clientv20.dto8.WidgetPersonalizationInfo;
import echosign.api.clientv20.service.EchoSignDocumentService20PortType;



/**
 * The Class EchoSignSignatureServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class EchoSignSignatureServiceImplTest {
	
	/** The document bytes. */
	byte[] DOCUMENT_BYTES="text".getBytes();
	
	/** The document file name. */
	String DOCUMENT_FILE_NAME="documentFileName";
	
	/** The document name. */
	String DOCUMENT_NAME="documentName";
	
	/** The signed document url. */
	String SIGNED_DOCUMENT_URL="signedDocumentUrl";
	
	/** The email. */
	String EMAIL="consent2shar@gmail.com";
	
	/** The echosign api key. */
	String ECHOSIGN_API_KEY="echoSignApiKey";
	
	/** The echosign service url. */
	String ECHOSIGN_SERVICE_URL="echoSignServiceUrl";
	
	/** The sut. */
	@InjectMocks
	EchoSignSignatureServiceImpl sut=spy(new EchoSignSignatureServiceImpl("echoSignServiceUrl","echoSignApiKey"));
	
	private EchoSignDocumentService20PortType service;
	
	private String mockDocumentKey;
	
	private String mockEchoSignApiKey;
	
	/**
	 * Test create embedded widget.
	 */
	
	@Before
	public void setUp() {
		service = mock(EchoSignDocumentService20PortType.class);
		EmbeddedWidgetCreationResult widgetResult=mock(EmbeddedWidgetCreationResult.class);
		doReturn(widgetResult).when(service).createPersonalEmbeddedWidget(eq("echoSignApiKey"), isNull(SenderInfo.class), any(WidgetCreationInfo.class), any(WidgetPersonalizationInfo.class));
		doReturn(service).when(sut).getCachedService();
		
		mockDocumentKey = "mockDocumentKey"; 
		mockEchoSignApiKey = "echoSignApiKey";
	}
	
	@After
	public void tearDown() {
		service = null;
		mockDocumentKey = null; 
		mockEchoSignApiKey = null;
	}
	
	@Test
	public void testCreateEmbeddedWidget() {
		
		sut.createEmbeddedWidget(DOCUMENT_BYTES, DOCUMENT_FILE_NAME, DOCUMENT_NAME, SIGNED_DOCUMENT_URL, EMAIL);
		verify(service).createPersonalEmbeddedWidget(eq("echoSignApiKey"), isNull(SenderInfo.class), any(WidgetCreationInfo.class), any(WidgetPersonalizationInfo.class));
	}
	
	/**
	 * Test create embedded widget_when_file_name_is_null.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateEmbeddedWidget_when_file_name_is_null() {
		sut.createEmbeddedWidget(DOCUMENT_BYTES, null, DOCUMENT_NAME, SIGNED_DOCUMENT_URL, EMAIL);
	}
	
	/**
	 * Test create embedded widget_when_file_document_byte_is_null.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateEmbeddedWidget_when_file_document_byte_is_null() {
		sut.createEmbeddedWidget(null, DOCUMENT_FILE_NAME, DOCUMENT_NAME, SIGNED_DOCUMENT_URL, EMAIL);
	}

	
	@Test
	public void testGetChildDocumentKey_ScnearioOne() {
		String mockFormDataCsv = "\"completed\",\"email\",\"role\",\"first\",\"last\",\"title\",\"company\",\"Transaction Number\"";

		GetFormDataResult mockGetFormDataResult=mock(GetFormDataResult.class);
		doReturn(mockFormDataCsv).when(mockGetFormDataResult).getFormDataCsv();

		doReturn(mockGetFormDataResult).when(service).getFormData(mockEchoSignApiKey, mockDocumentKey);
		Assert.assertNull(sut.getChildDocumentKey(mockDocumentKey));
	}
	
	@Test
	public void testGetChildDocumentKey_ScnearioTwo() {
		String mockFormDataCsv = "\"completed\",\"email\",\"role\",\"first\",\"last\",\"title\",\"company\",\"Document Key\",\"Transaction Number\"\n" +
"\"2015-04-08 11:31:20\",\"sadhana.chandravanka@feisystems.com\",\"SIGNER\",\"testConsent2\",\"Share\",\"\",\"\",\"XLS4XIRNXN6S3V6\",\"XLS4XIR84U26J58\"";
		
		String expected = "XLS4XIRNXN6S3V6";
		GetFormDataResult mockGetFormDataResult=mock(GetFormDataResult.class);
		doReturn(mockFormDataCsv).when(mockGetFormDataResult).getFormDataCsv();
		
		doReturn(mockGetFormDataResult).when(service).getFormData(mockEchoSignApiKey, mockDocumentKey);
		String actual = sut.getChildDocumentKey(mockDocumentKey);
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetChildDocumentKey_ScnearioThree() {
		String mockFormDataCsv = "\"completed\",\"email\",\"role\",\"first\",\"last\",\"title\",\"company\",\"Transaction Number\"" +
"\"2015-04-16 12:16:04\",\"cindy.li@feisystems.com\",\"SIGNER\",\"cindy\",\"li\",\"software developer\",\"fei systems\",\"XM6WKHU3JXV5X7D\"";
		
		GetFormDataResult mockGetFormDataResult=mock(GetFormDataResult.class);
		doReturn(mockFormDataCsv).when(mockGetFormDataResult).getFormDataCsv();
		doReturn(mockGetFormDataResult).when(service).getFormData(mockEchoSignApiKey, mockDocumentKey);
		
		Assert.assertNull(sut.getChildDocumentKey(mockDocumentKey));
	}
	
	@Test
	public void testGetChildDocumentKey_ScnearioFour() {
		String mockFormDataCsv = "\"completed\",\"email\",\"role\",\"first\",\"last\",\"title\",\"company\",\"Document1 Key\",\"Transaction Number\"\n" +
"\"2015-04-08 11:31:20\",\"sadhana.chandravanka@feisystems.com\",\"SIGNER\",\"testConsent2\",\"Share\",\"\",\"\",\"XLS4XIRNXN6S3V6\",\"XLS4XIR84U26J58\"";
		
		GetFormDataResult mockGetFormDataResult=mock(GetFormDataResult.class);
		doReturn(mockFormDataCsv).when(mockGetFormDataResult).getFormDataCsv();
		
		doReturn(mockGetFormDataResult).when(service).getFormData(mockEchoSignApiKey, mockDocumentKey);
		Assert.assertNull(sut.getChildDocumentKey(mockDocumentKey));
	}	

}
