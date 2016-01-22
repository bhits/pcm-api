package gov.samhsa.mhc.common.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;

import gov.samhsa.mhc.common.filereader.FileReaderImpl;
import gov.samhsa.mhc.common.validation.exception.InvalidXmlDocumentException;
import gov.samhsa.mhc.common.validation.exception.XmlDocumentReadFailureException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.xml.sax.SAXParseException;

public class XmlValidationTest {

    private static final String BASE_PATH = "schema/cdar2c32/infrastructure/cda/";
    private String invalidC32;
    private String validC32;

    private FileReaderImpl fileReader;
    private XmlValidation sut;

    @Before
    public void setUp() throws Exception {
        fileReader = new FileReaderImpl();
        invalidC32 = fileReader.readFile("invalidC32.xml");
        validC32 = fileReader.readFile("validC32.xml");
        sut = new XmlValidation(this.getClass().getClassLoader().getResourceAsStream(BASE_PATH+"C32_CDA.xsd"), BASE_PATH);
    }

    @Test(expected = InvalidXmlDocumentException.class)
    public void testValidateInputStream() throws InvalidXmlDocumentException, XmlDocumentReadFailureException {
        assertFalse(sut.validate(invalidC32));
    }

    @Test
    public void testXmlValidation_Initialization_Failure() {
        // Act
        XmlValidation xmlValidation = new XmlValidation(new ByteArrayInputStream(new byte[2]), "a");

        // Assert
        assertNull(ReflectionTestUtils.getField(xmlValidation, "validator"));
    }

    @Test
    public void testValidateString() throws InvalidXmlDocumentException, XmlDocumentReadFailureException {
        assertTrue(sut.validate(validC32));
    }

    @Test
    public void testValidateWithAllErrors() throws XmlDocumentReadFailureException{
        // Act
        XmlValidationResult result = sut.validateWithAllErrors(invalidC32);

        // Assert
        assertEquals(2, result.getExceptions().size());
        for(SAXParseException e: result.getExceptions()){
            assertTrue(e.getMessage().contains("noSuchElement") || e.getMessage().contains("noSuchElement2"));
        }
    }
}
