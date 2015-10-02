package gov.samhsa.acs.xdsb.common;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;

import java.io.IOException;
import java.util.LinkedList;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XdsbMetadataGeneratorImplTest {

	private final FileReaderImpl fileReader = new FileReaderImpl();
	private final SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
	private final XmlTransformer xmlTransformer = new XmlTransformerImpl(
			marshaller);

	private XdsbMetadataGeneratorImpl sut;

	@Test
	public void testGenerateMetadata_CdaR2() throws Throwable {
		initClinical();
		final String metadataFileName = "unitTestMetaCdaR2Consent.xml";
		testGenerateMetadata(metadataFileName);
	}

	@Test
	public void testGenerateMetadata_RemC32() throws Throwable {
		initClinical();
		final String metadataFileName = "unitTestMetaRemC32.xml";
		testGenerateMetadata(metadataFileName);
	}

	@Test
	public void testGenerateMetadata_Xacml() throws Throwable {
		initPrivacy();
		final String metadataFileName = "unitTestMetaXacml.xml";
		testGenerateMetadata(metadataFileName);
	}

	@Test
	public void testGenerateMetadataXml_CdaR2() throws Throwable {
		initClinical();
		final String c32FileName = "cdaR2Consent.xml";
		final String expectedMetadataFileName = "unitTestMetaCdaR2Consent.xml";
		testGenerateMetadataXml(c32FileName, expectedMetadataFileName,
				createIgnorableXPathRegexList());
	}

	@Test
	public void testGenerateMetadataXml_RemC32() throws Throwable {
		initClinical();
		final String c32FileName = "remC32.xml";
		final String expectedMetadataFileName = "unitTestMetaRemC32.xml";
		testGenerateMetadataXml(c32FileName, expectedMetadataFileName,
				createIgnorableXPathRegexList());
	}

	@Test
	public void testGenerateMetadataXml_Xacml() throws Throwable {
		initPrivacy();
		final String c32FileName = "xacml_policy.xml";
		final String expectedMetadataFileName = "unitTestMetaXacml.xml";
		testGenerateMetadataXml(c32FileName, expectedMetadataFileName,
				createIgnorableXPathRegexList());
	}

	private LinkedList<String> createIgnorableXPathRegexList() {
		final LinkedList<String> ignorableXPathsRegex = new LinkedList<String>();
		ignorableXPathsRegex
				.add("\\/SubmitObjectsRequest\\[1\\]/RegistryObjectList\\[1\\]/ExtrinsicObject\\[1\\]/Slot\\[1\\]/ValueList\\[1\\]/Value\\[1\\]");
		ignorableXPathsRegex
				.add("\\/SubmitObjectsRequest\\[1\\]/RegistryObjectList\\[1\\]/ExtrinsicObject\\[1\\]/ExternalIdentifier\\[2\\]/@value");
		ignorableXPathsRegex
				.add("\\/SubmitObjectsRequest\\[1\\]/RegistryObjectList\\[1\\]/RegistryPackage\\[1\\]/Slot\\[1\\]/ValueList\\[1\\]/Value\\[1\\]");
		ignorableXPathsRegex
				.add("\\/SubmitObjectsRequest\\[1\\]/RegistryObjectList\\[1\\]/RegistryPackage\\[1\\]/ExternalIdentifier\\[1\\]/@value");
		return ignorableXPathsRegex;
	}

	private void initClinical() {
		sut = new XdsbMetadataGeneratorImpl(new UniqueOidProviderImpl(),
				XdsbDocumentType.CLINICAL_DOCUMENT, marshaller, xmlTransformer);
	}

	private void initPrivacy() {
		sut = new XdsbMetadataGeneratorImpl(new UniqueOidProviderImpl(),
				XdsbDocumentType.PRIVACY_CONSENT, marshaller, xmlTransformer);
	}

	private void testGenerateMetadata(String metadataFileName)
			throws IOException, Throwable, SAXException {
		// Arrange
		final String metadata = fileReader.readFile(metadataFileName);
		final String documentMock = "documentMock";
		final String homeCommunityIdMock = "homeCommunityIdMock";
		sut = spy(sut);
		doReturn(metadata).when(sut).generateMetadataXml(documentMock,
				homeCommunityIdMock, null, null);
		XMLUnit.setIgnoreWhitespace(true);

		// Act
		final SubmitObjectsRequest submitObjectsRequest = sut.generateMetadata(
				documentMock, homeCommunityIdMock, null, null);
		final String xml = marshaller.marshal(submitObjectsRequest);

		// Assert
		assertNotNull(submitObjectsRequest);
		assertXMLEqual("", metadata, xml);
	}

	private void testGenerateMetadataXml(String fileName,
			String expectedMetadataFileName,
			LinkedList<String> ignorableXPathsRegex) throws IOException {
		// Arrange
		final String file = fileReader.readFile(fileName);
		final String expectedMetadata = fileReader
				.readFile(expectedMetadataFileName);

		// Act
		final String meta = sut.generateMetadataXml(file, "1.1.1.1.1", "100010060006",
				null);

		// Assert
		final DetailedDiff diff = XmlComparator.compareXMLs(expectedMetadata,
				meta, ignorableXPathsRegex);
		assertTrue(diff.similar());
	}
}
