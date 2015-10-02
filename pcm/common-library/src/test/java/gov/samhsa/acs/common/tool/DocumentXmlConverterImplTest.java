package gov.samhsa.acs.common.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.samhsa.consent2share.commonunit.xml.XmlComparator;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class DocumentXmlConverterImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static FileReaderImpl fileReader;
	private static String c32;

	private static DocumentXmlConverterImpl documentXmlConverter;

	@BeforeClass
	public static void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();
		c32 = fileReader.readFile("c32.xml");

		documentXmlConverter = new DocumentXmlConverterImpl();
	}

	@Test
	public void testLoadDocument() throws Exception {
		// Act
		Document document = documentXmlConverter.loadDocument(c32);

		// Assert
		assertNotNull(document);

		logger.debug(document.getNodeName());
		assertEquals("#document", document.getNodeName());

		logger.debug(document.getXmlEncoding());
		assertEquals("UTF-8", document.getXmlEncoding());

		logger.debug(document.getXmlVersion());
		assertEquals("1.0", document.getXmlVersion());

		logger.debug(document.toString());
		assertEquals("[#document: null]", document.toString());

		logger.debug(document.getDocumentElement().toString());
		assertEquals("[ClinicalDocument: null]", document.getDocumentElement()
				.toString());

		logger.debug(document.getFirstChild().toString());
		assertEquals("[ClinicalDocument: null]", document.getFirstChild()
				.toString());

		logger.debug(document.getImplementation().toString());
		assertTrue(document
				.getImplementation()
				.toString()
				.startsWith(
						"org.apache.xerces.dom.DeferredDOMImplementationImpl@"));

		logger.debug(document.getLastChild().toString());
		assertEquals("[ClinicalDocument: null]", document.getLastChild()
				.toString());

		logger.debug(Boolean.toString(document.hasAttributes()));
		assertFalse(document.hasAttributes());

		logger.debug(Boolean.toString(document.hasChildNodes()));
		assertTrue(document.hasChildNodes());
	}

	@Test
	public void testConverXmlDocToString_1() throws Exception {
		// Arrange
		Document document = readDocument("src/test/resources/xmlDocument.txt");

		// Act
		String docString = documentXmlConverter.convertXmlDocToString(document);
		logger.debug(docString);

		// Assert
		assertNotNull(docString);
		String s1 = fileReader.readFile("xmlString1.txt");
		assertNotNull(s1, "s1 is null");
		assertNotNull(docString, "docString is null");
		assertTrue(XmlComparator.compareXMLs(s1, docString, Arrays.asList(""))
				.similar());
	}

	@Test
	public void testConverXmlDocToString_2() throws Exception {
		// Arrange
		Document document = readDocument("src/test/resources/xmlDocument.txt");

		// Act
		String docString = documentXmlConverter.convertXmlDocToString(document);
		logger.debug(docString);

		// Assert
		assertNotNull(docString);
		String s2 = fileReader.readFile("xmlString2.txt");
		assertTrue(XmlComparator.compareXMLs(s2, docString, Arrays.asList(""))
				.similar());
	}

	@Test
	public void testConverXmlDocToString_3() throws Exception {
		// Arrange
		Document document = readDocument("src/test/resources/xmlDocument.txt");

		// Act
		String docString = documentXmlConverter.convertXmlDocToString(document);
		logger.debug(docString);

		// Assert
		assertNotNull(docString);
		String s3 = fileReader.readFile("xmlString3.txt");
		assertTrue(XmlComparator.compareXMLs(s3, docString, Arrays.asList(""))
				.similar());
	}

	private static Document readDocument(String filePath) throws IOException,
			ClassNotFoundException {
		return (Document) readObject(filePath);
	}

	private static Object readObject(String filePath) throws IOException,
			ClassNotFoundException {
		File file = new File(filePath);
		byte[] b = null;

		b = FileUtils.readFileToByteArray(file);
		ByteArrayInputStream in = new ByteArrayInputStream(b);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}
}
