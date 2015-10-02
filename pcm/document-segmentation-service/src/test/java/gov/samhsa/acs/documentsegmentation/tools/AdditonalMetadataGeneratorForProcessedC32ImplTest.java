package gov.samhsa.acs.documentsegmentation.tools;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditonalMetadataGeneratorForProcessedC32ImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@After
	public void tearDown() throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		final XmlTransformer xmlTransformer = new XmlTransformerImpl(
				new SimpleMarshallerImpl());
		final AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForProcessedC32Impl = new AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl(
				xmlTransformer);
		final Field field = additionalMetadataGeneratorForProcessedC32Impl
				.getClass()
				.getDeclaredField(
						"AdditonalMetadataStylesheetForProcessedC32_Xsl_File_Name");
		field.setAccessible(true);
		field.set(additionalMetadataGeneratorForProcessedC32Impl,
				"AdditonalMetadataStylesheetForProcessedC32.xsl");
	}

	@Test
	public void testGenerateMetadataXml() throws Exception {
		// Arrange
		final XmlTransformer xmlTransformer = new XmlTransformerImpl(
				new SimpleMarshallerImpl());
		final String ruleExecutionResponseContainer = readStringFromFile("ruleExecutionResponseContainer.xml");
		final String taggedC32Doc = readStringFromFile("tagged_C32.xml");
		final AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForProcessedC32Impl = new AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl(
				xmlTransformer);
		final String senderEmailAddress = "sender@sender.com";
		final String recipientEmailAddress = "receiver@receiver.com";
		final String purposeOfUse = "TREAT";
		final String xdsDocumentEntryUniqueId = "123";

		// Act
		final String result = additionalMetadataGeneratorForProcessedC32Impl
				.generateMetadataXml(UUID.randomUUID().toString(),
						taggedC32Doc, ruleExecutionResponseContainer,
						senderEmailAddress, recipientEmailAddress,
						purposeOfUse, xdsDocumentEntryUniqueId);

		// Assert
		final String expectedResult = readStringFromFile("additionalMetadataGeneratedFromRuleExecutionResponseContainer.xml");
		Assert.assertNotNull(result);
		// assertTrue(result.trim().equals(expectedResult.trim()));
	}

	@Test(expected = DS4PException.class)
	public void testGenerateMetadataXml_Throws_DS4PException_Having_TransformerConfigurationException()
			throws Exception {
		// Arrange
		final XmlTransformer xmlTransformer = new XmlTransformerImpl(
				new SimpleMarshallerImpl());
		final String ruleExecutionResponseContainer = readStringFromFile("ruleExecutionResponseContainer.xml");
		final String taggedC32Doc = readStringFromFile("tagged_C32.xml");
		final AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForProcessedC32Impl = new AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl(
				xmlTransformer);
		final String senderEmailAddress = "sender@sender.com";
		final String recipientEmailAddress = "receiver@receiver.com";
		final String purposeOfUse = "TREAT";
		final String xdsDocumentEntryUniqueId = "123";

		// Try to use a wrong xsl file
		final Field field = additionalMetadataGeneratorForProcessedC32Impl
				.getClass()
				.getDeclaredField(
						"AdditonalMetadataStylesheetForProcessedC32_Xsl_File_Name");
		field.setAccessible(true);
		// Field modifiersField = Field.class.getDeclaredField( "modifiers" );
		// modifiersField.setAccessible( true );
		// modifiersField.setInt( field, field.getModifiers() & ~Modifier.FINAL
		// );
		field.set(additionalMetadataGeneratorForProcessedC32Impl,
				"WRONG_FILE_NAME.XSL");

		// Act
		@SuppressWarnings("unused")
		final String result = additionalMetadataGeneratorForProcessedC32Impl
				.generateMetadataXml(UUID.randomUUID().toString(),
						taggedC32Doc, ruleExecutionResponseContainer,
						senderEmailAddress, recipientEmailAddress,
						purposeOfUse, xdsDocumentEntryUniqueId);

		// Assert
		// expect exception
	}

	private String readStringFromFile(String fileName) {
		final InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(fileName);

		final BufferedReader br = new BufferedReader(new InputStreamReader(is));

		final StringBuilder resultStringBuilder = new StringBuilder();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line);
				resultStringBuilder.append("\n");
			}

			br.close();

			is.close();

		} catch (final IOException e) {
			logger.error(e.getMessage(), e);
		}

		final String result = resultStringBuilder.toString();
		return result;
	}
}
