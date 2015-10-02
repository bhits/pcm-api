package gov.samhsa.acs.common.validation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@RunWith(MockitoJUnitRunner.class)
public class ErrorHandlerImplTest {
	
	@Mock
	private XmlValidationResult resultMock;
	
	@InjectMocks
	private ErrorHandlerImpl sut;

	@Test
	public void testWarning() throws SAXException {
		// Arrange
		SAXParseException eMock = mock(SAXParseException.class);
		
		// Act
		sut.warning(eMock);
		
		// Assert
		verify(resultMock, times(1)).addError(eMock);
	}

	@Test
	public void testError() throws SAXException {
		// Arrange
		SAXParseException eMock = mock(SAXParseException.class);
		
		// Act
		sut.error(eMock);
		
		// Assert
		verify(resultMock, times(1)).addError(eMock);
	}

	@Test
	public void testFatalError() throws SAXException {
		// Arrange
		SAXParseException eMock = mock(SAXParseException.class);
		
		// Act
		sut.fatalError(eMock);
		
		// Assert
		verify(resultMock, times(1)).addError(eMock);
	}

	@Test
	public void testGetExceptions() {
		// Act
		sut.getExceptions();
		
		// Assert
		verify(resultMock, times(1)).getExceptions();
	}
}
