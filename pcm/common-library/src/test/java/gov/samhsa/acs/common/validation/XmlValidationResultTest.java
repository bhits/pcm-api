package gov.samhsa.acs.common.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.SAXParseException;

@RunWith(MockitoJUnitRunner.class)
public class XmlValidationResultTest {

	@InjectMocks
	private XmlValidationResult sut;

	@Test
	public void testIsValid_True() {
		assertTrue(sut.isValid());
	}

	@Test
	public void testIsValid_False() {
		// Arrange
		SAXParseException eMock = mock(SAXParseException.class);

		// Act
		sut.addError(eMock);

		// Assert
		assertFalse(sut.isValid());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testAddError() {
		// Arrange
		List listMock = mock(List.class);
		SAXParseException eMock = mock(SAXParseException.class);
		ReflectionTestUtils.setField(sut, "exceptions", listMock);

		// Act
		sut.addError(eMock);

		// Assert
		verify(listMock, times(1)).add(eMock);
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Test
	public void testGetExceptions() {
		// Arrange
		List listMock = mock(List.class);
		SAXParseException eMock = mock(SAXParseException.class);
		ReflectionTestUtils.setField(sut, "exceptions", listMock);

		// Act
		List actualList = sut.getExceptions();

		// Assert
		assertEquals(listMock, actualList);
	}
}
