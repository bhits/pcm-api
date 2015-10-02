package gov.samhsa.acs.documentsegmentation.tools;

import static org.junit.Assert.assertTrue;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataGeneratorImplTest {
	private static FileReaderImpl fileReader;

	private static SimpleMarshaller marshaller;
	private static XmlTransformer xmlTransformer;
	private static String c32;

	private static String executionResponseContainer;
	private static String homeCommunityId;
	private static String senderEmailAddress;
	private static String recipientEmailAddress;
	private static MetadataGeneratorImpl metadataGenerator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testGenerateMetadataXml() throws Exception {
		// Act
		final String metadata = metadataGenerator.generateMetadataXml(c32,
				executionResponseContainer, homeCommunityId,
				senderEmailAddress, recipientEmailAddress);
		logger.debug(metadata);

		// Assert
		assertTrue(metadata
				.contains("<Value>PatientID^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value>"));
		assertTrue(metadata
				.contains("<Value>^^Internet^leo.smith@direct.obhita-stage.org</Value>"));
		assertTrue(metadata
				.contains("<Value>^^Internet^Duane_Decouteau@direct.healthvault-stage.com</Value>"));
		assertTrue(metadata.contains("<Value>TREAT</Value>"));
		assertTrue(metadata
				.contains("<Value observationId=\"d11275e7-67ae-11db-bd13-0800200c9a66\">ENCRYPT</Value>"));
		assertTrue(metadata
				.contains("<Value observationId=\"d11275e7-67ae-11db-bd13-0800200c9a66\">NORDSLCD</Value>"));
		assertTrue(metadata
				.contains("<Value observationId=\"d11275e7-67ae-11db-bd13-0800200c9a66\">HIV</Value>"));
		assertTrue(metadata
				.contains("<Value observationId=\"d11275e7-67ae-11db-bd13-0800200c9a66\">42CFRPart2</Value>"));
		assertTrue(metadata
				.contains("<Value>100010020002^^^&amp;2.16.840.1.113883.3.467&amp;ISO</Value>"));
	}

	@Test(expected = DS4PException.class)
	public void testGenerateMetadataXml_Throws_DS4PException() {
		// Empty xml file
		@SuppressWarnings("unused")
		final String metadata = metadataGenerator.generateMetadataXml("",
				executionResponseContainer, homeCommunityId,
				senderEmailAddress, recipientEmailAddress);
	}

	@BeforeClass
	public static void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();
		c32 = fileReader.readFile("sampleC32/c32.xml");
		executionResponseContainer = fileReader
				.readFile("ruleExecutionResponseContainer.xml");
		homeCommunityId = "2.16.840.1.113883.3.467";
		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		recipientEmailAddress = "Duane_Decouteau@direct.healthvault-stage.com";
		marshaller = new SimpleMarshallerImpl();
		xmlTransformer = new XmlTransformerImpl(marshaller);
		metadataGenerator = new MetadataGeneratorImpl(xmlTransformer);
	}
}
