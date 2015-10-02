package gov.samhsa.acs.common.cxf;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.phase.Phase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContentTypeRebuildingOutboundSoapInterceptorTest {

	private static final String CONTENT_TYPE = "Content-Type";

	@InjectMocks
	private ContentTypeRebuildingOutboundSoapInterceptor sut;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testHandleMessage() {
		// Arrange
		final String contentType = "\"a=1; b=2\"; c=\"3\"";
		final String expectedContentType = "a=\"1\"; b=\"2\"; c=\"3\"";
		final SoapMessage message = mock(SoapMessage.class);
		when(message.get(CONTENT_TYPE)).thenReturn(contentType);

		// Act
		sut.handleMessage(message);

		// Assert
		verify(message, times(1)).put(CONTENT_TYPE, expectedContentType);
	}

	@Test
	public void testHandleMessage_NoChange() {
		// Arrange
		final String expectedContentType = "a=\"1\"; b=\"2\"; c=\"3\"";
		final SoapMessage message = mock(SoapMessage.class);
		when(message.get(CONTENT_TYPE)).thenReturn(expectedContentType);

		// Act
		sut.handleMessage(message);

		// Assert
		verify(message, times(1)).put(CONTENT_TYPE, expectedContentType);
	}

	@Test
	public void testPhase() {
		assertEquals(Phase.WRITE, sut.getPhase());
	}
}
