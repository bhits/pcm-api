package gov.samhsa.acs.common.tool;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.bind.JAXBException;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SimpleMarshallerImplTest {
	private static FileReaderImpl fileReader;
	private static RuleExecutionContainer ruleExecutionContainer;

	private static String ruleExecutionContainerString;
	private static final String EXPECTED_RESPONSE_1 = "11450-4:Problems:66214007:SNOMED CT:Substance Abuse Disorder:ENCRYPT:NORDSCLCD:REDACT:e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7:ETH:42CFRPart2:";

	private static final String EXPECTED_RESPONSE_2 = "11450-4:Problems:111880001:SNOMED CT:Acute HIV:ENCRYPT:NORDSCLCD:MASK:d11275e7-67ae-11db-bd13-0800200c9a66:HIV:42CFRPart2:";
	private static final String EXPECTED_MARSHALL_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ruleExecutionContainer><executionResponseList/></ruleExecutionContainer>";
	private static SimpleMarshallerImpl marshaller;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testMarshalAsByteArrayOutputStream()
			throws SimpleMarshallerException, SAXException, IOException {
		// Arrange
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		final String expectedOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ruleExecutionContainer><executionResponseList/></ruleExecutionContainer>";
		final RuleExecutionContainer r = createRuleExecutionContainer();

		// Act
		final ByteArrayOutputStream output = marshaller
				.marshalAsByteArrayOutputStream(r);

		assertNotNull(output);
		assertXMLEqual(expectedOutput, output.toString());
	}

	@Test
	public void testMarshall() throws JAXBException {
		// Arrange
		final RuleExecutionContainer r = createRuleExecutionContainer();

		// Act
		final String response = marshaller.marshal(r);

		// Assert
		assertEquals(EXPECTED_MARSHALL_RESPONSE, response);
	}

	@Test
	public void testUnmarshallFromXml() throws JAXBException {
		// Act
		ruleExecutionContainer = marshaller.unmarshalFromXml(
				RuleExecutionContainer.class, ruleExecutionContainerString);
		final String[] results = new String[2];
		int i = 0;
		for (final RuleExecutionResponse r : ruleExecutionContainer
				.getExecutionResponseList()) {
			final String s = ruleExecutionResponseToString(r);
			results[i] = s;
			logger.debug(s);
			i++;
		}

		// Assert
		assertNotNull(ruleExecutionContainer);
		assertEquals(2, ruleExecutionContainer.getExecutionResponseList()
				.size());
		assertEquals(EXPECTED_RESPONSE_1, results[0]);
		assertEquals(EXPECTED_RESPONSE_2, results[1]);
	}

	private RuleExecutionContainer createRuleExecutionContainer() {
		final RuleExecutionContainer r = new RuleExecutionContainer();
		r.setExecutionResponseList(new LinkedList<RuleExecutionResponse>());
		return r;
	}

	private String ruleExecutionResponseToString(RuleExecutionResponse r) {
		final StringBuilder builder = new StringBuilder();
		ruleExecutionResponseToStringAppender(builder,
				r.getC32SectionLoincCode());
		ruleExecutionResponseToStringAppender(builder, r.getC32SectionTitle());
		ruleExecutionResponseToStringAppender(builder, r.getCode());
		ruleExecutionResponseToStringAppender(builder, r.getCodeSystemName());
		ruleExecutionResponseToStringAppender(builder, r.getDisplayName());
		ruleExecutionResponseToStringAppender(builder, r
				.getDocumentObligationPolicy().toString());
		ruleExecutionResponseToStringAppender(builder, r
				.getDocumentRefrainPolicy().toString());
		ruleExecutionResponseToStringAppender(builder, r.getItemAction());
		ruleExecutionResponseToStringAppender(builder, r.getObservationId());
		ruleExecutionResponseToStringAppender(builder, r.getSensitivity()
				.toString());
		ruleExecutionResponseToStringAppender(builder, r.getUSPrivacyLaw()
				.getPrivacy());
		return builder.toString();
	}

	private void ruleExecutionResponseToStringAppender(StringBuilder builder,
			String s) {
		builder.append(s);
		builder.append(":");
	}

	@BeforeClass
	public static void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();
		ruleExecutionContainerString = fileReader
				.readFile("ruleExecutionResponseContainer.xml");
		ruleExecutionContainer = null;

		marshaller = new SimpleMarshallerImpl();
	}
}
