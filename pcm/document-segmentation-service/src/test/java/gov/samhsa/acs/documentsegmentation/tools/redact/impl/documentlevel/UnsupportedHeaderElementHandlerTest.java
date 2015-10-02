package gov.samhsa.acs.documentsegmentation.tools.redact.impl.documentlevel;

import static org.junit.Assert.assertEquals;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.documentsegmentation.tools.redact.impl.documentlevel.UnsupportedHeaderElementHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@RunWith(MockitoJUnitRunner.class)
public class UnsupportedHeaderElementHandlerTest {

	public static final String TEST_PATH = "sampleC32/";
	public static final Set<String> unsupportedHeaders = new HashSet<String>(
			Arrays.asList("realmCode", "typeId", "templateId", "id", "code",
					"title", "effectiveTime", "confidentialityCode",
					"languageCode", "setId", "versionNumber", "copyTime",
					"recordTarget", "author", "dataEnterer", "custodian",
					"legalAuthenticator", "inFulfillmentOf", "documentationOf",
					"relatedDocument", "authorization", "componentOf",
					"component"));

	private Set<String> redactSectionCodesAndGeneratedEntryIds = new HashSet<String>();
	private FileReader fileReader;
	private DocumentAccessor documentAccessor;
	private DocumentXmlConverter documentXmlConverter;
	private UnsupportedHeaderElementHandler sut;

	@Before
	public void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		documentAccessor = new DocumentAccessorImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		sut = new UnsupportedHeaderElementHandler(documentAccessor,
				unsupportedHeaders);
	}

	@Test
	public void testExecute() throws IOException, SimpleMarshallerException,
			XPathExpressionException {
		// Arrange
		List<Node> redactNodeList = new LinkedList<Node>();
		String c32FileName = "c32.xml";
		String c32 = fileReader.readFile(TEST_PATH + c32FileName);
		Document c32Document = documentXmlConverter.loadDocument(c32);
		sut.execute(c32Document, redactSectionCodesAndGeneratedEntryIds,
				redactNodeList);
		assertEquals(redactNodeList.size(), 1);

	}
}
