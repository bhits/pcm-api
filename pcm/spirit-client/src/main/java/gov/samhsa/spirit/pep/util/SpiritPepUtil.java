package gov.samhsa.spirit.pep.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class SpiritPepUtil {

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(SpiritPepUtil.class);

	/**
	 * @param xml
	 *            - The xml-file to be parsed
	 * @return an element-array
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Element[] parse(File xml)
			throws ParserConfigurationException, FileNotFoundException,
			SAXException, IOException {

		if (xml == null) {
			logger.warn("parse: no addAttrib-Xml passed -> makes this test a bit useless");
			return null;
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(new FileInputStream(xml));

		logger.debug("parse: \n" + _documentToString(doc));

		Element[] els = new Element[] { doc.getDocumentElement() };

		return els;
	}

	private static String _documentToString(Document doc) throws IOException {
		DOMImplementationLS domImplementation = (DOMImplementationLS) doc
				.getImplementation();
		LSSerializer lsSerializer = domImplementation.createLSSerializer();
		return lsSerializer.writeToString(doc);
	}

	public Document elementArrayToDocument(Element[] ea) throws DOMException,
			ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().newDocument();
		Element e = doc.createElement("SpiritElementArray");
		for (int i = 0; i < ea.length; i++) {
			e.appendChild(doc.importNode(ea[i], true));
		}
		// Bugfix {} Premature end of file
		doc.appendChild(e);
		return doc;
	}
}
