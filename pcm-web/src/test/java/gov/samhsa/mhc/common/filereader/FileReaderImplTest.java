package gov.samhsa.mhc.common.filereader;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class FileReaderImplTest {
    private static FileReaderImpl fileReader;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
