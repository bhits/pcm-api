package gov.samhsa.acs.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.samhsa.consent2share.commonunit.xml.XmlComparator;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.w3c.dom.Document;

public class XmlHelperTest {

	@Test
	public void test() throws IOException, TransformerException, Exception {
		Document doc = XmlHelper
				.loadDocument("<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>");
		assertEquals(doc.getClass().getName(),
				"org.apache.xerces.dom.DeferredDocumentImpl");
		assertTrue(XmlComparator
				.compareXMLs(
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?><note>    <to>Tove</to>    <from>Jani</from>    <heading>Reminder</heading>    <body>Don't forget me this weekend!</body></note>",
						XmlHelper.converXmlDocToString(doc),
						new LinkedList<String>()).similar());
	}

}
