package gov.samhsa.bhits.common.tool;

import gov.samhsa.bhits.common.document.accessor.DocumentAccessorImpl;
import gov.samhsa.bhits.common.filereader.FileReaderImpl;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DocumentAccessorImplTest {

    private FileReaderImpl fileReader;
    private DocumentXmlConverterImpl documentXmlConverter;
    private Document c32Doc;

    private DocumentAccessorImpl sut;

    @Before
    public void setUp() throws Exception {
        fileReader = new FileReaderImpl();
        documentXmlConverter = new DocumentXmlConverterImpl();
        final String c32 = fileReader.readFile("c32.xml");
        c32Doc = documentXmlConverter.loadDocument(c32);
        sut = new DocumentAccessorImpl();
    }

    @Test
    public void testGetElement() throws XPathExpressionException {
        // Arrange
        final String xPath = "/hl7:ClinicalDocument//hl7:patientRole//hl7:city";

        // Act
        final Element element = sut.getElement(c32Doc, xPath).get();

        // Assert
        assertNotNull(element);
        assertEquals("CityName", element.getTextContent());
    }

    @Test
    public void testGetNode() throws XPathExpressionException {
        // Arrange
        final String xPath = "/hl7:ClinicalDocument//hl7:patientRole//hl7:city";

        // Act
        final Node node = sut.getNode(c32Doc, xPath).get();

        // Assert
        assertNotNull(node);
        assertEquals("CityName", node.getTextContent());
    }

    @Test
    public void testGetNodeList() throws XPathExpressionException {
        // Arrange
        final String xPath = "//hl7:templateId";

        // Act
        final NodeList nodeList = sut.getNodeList(c32Doc, xPath);

        // Assert
        assertEquals(176, nodeList.getLength());
    }
}