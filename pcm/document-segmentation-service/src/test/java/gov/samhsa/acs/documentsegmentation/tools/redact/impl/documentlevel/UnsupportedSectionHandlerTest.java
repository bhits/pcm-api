package gov.samhsa.acs.documentsegmentation.tools.redact.impl.documentlevel;

import static org.junit.Assert.*;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class UnsupportedSectionHandlerTest {
	
	public static final String TEST_PATH = "sampleC32/";
	public static final Set<String> sectionWhiteList = 
			new HashSet<String>(Arrays.asList("11450-4", "48765-2","10160-0","30954-2"));
	
	private Set<String> redactSectionCodesAndGeneratedEntryIds = new HashSet<String>();
	private FileReader fileReader;
	private DocumentAccessor documentAccessor;
	private DocumentXmlConverter documentXmlConverter;
	private UnsupportedSectionHandler sut;
	
	@Before
	public void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		documentAccessor = new DocumentAccessorImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		sut = new UnsupportedSectionHandler(documentAccessor,sectionWhiteList);
	}


	@Test
	public void testExecute()
			throws IOException, SimpleMarshallerException,
			XPathExpressionException {
		// Arrange
		List<Node> redactNodeList = new LinkedList<Node>();
		String c32FileName = "JohnHalamkaCCDDocument_C32.xml";
		String c32 = fileReader.readFile(TEST_PATH + c32FileName);
		Document c32Document = documentXmlConverter.loadDocument(c32);
		sut.execute(c32Document, redactSectionCodesAndGeneratedEntryIds,redactNodeList);
		assertEquals(redactNodeList.size(),12);
		
	}

}
