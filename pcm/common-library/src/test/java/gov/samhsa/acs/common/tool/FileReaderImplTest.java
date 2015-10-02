package gov.samhsa.acs.common.tool;

import static org.junit.Assert.assertTrue;

import gov.samhsa.acs.common.tool.FileReaderImpl;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReaderImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static FileReaderImpl fileReader;

	@BeforeClass
	public static void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();
	}

	@Test
	public void testReadFile() throws IOException {
		// Act
		String ruleExecutionResponseContainer = fileReader
				.readFile("ruleExecutionResponseContainer.xml");

		logger.debug(ruleExecutionResponseContainer);

		// Assert
		assertTrue(ruleExecutionResponseContainer
				.contains("<ruleExecutionContainer>"));
		assertTrue(ruleExecutionResponseContainer
				.contains("<executionResponseList>"));
		assertTrue(ruleExecutionResponseContainer
				.contains("<executionResponse>"));
		assertTrue(ruleExecutionResponseContainer
				.contains("<c32SectionLoincCode>11450-4</c32SectionLoincCode>"));
		assertTrue(ruleExecutionResponseContainer
				.contains("<sensitivity>ETH</sensitivity>"));
		assertTrue(ruleExecutionResponseContainer
				.contains("<sensitivity>HIV</sensitivity>"));
	}
}
