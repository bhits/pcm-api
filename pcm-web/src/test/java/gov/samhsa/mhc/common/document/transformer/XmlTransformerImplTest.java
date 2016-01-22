package gov.samhsa.mhc.common.document.transformer;

import static gov.samhsa.mhc.common.unit.xml.XmlComparator.compareXMLs;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import gov.samhsa.mhc.common.document.converter.DocumentXmlConverter;
import gov.samhsa.mhc.common.document.converter.DocumentXmlConverterImpl;
import gov.samhsa.mhc.common.filereader.FileReader;
import gov.samhsa.mhc.common.filereader.FileReaderImpl;
import gov.samhsa.mhc.common.marshaller.SimpleMarshaller;
import gov.samhsa.mhc.common.marshaller.SimpleMarshallerException;
import gov.samhsa.mhc.common.param.Params;
import gov.samhsa.mhc.common.param.ParamsBuilder;
import gov.samhsa.mhc.common.util.StringURIResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@RunWith(MockitoJUnitRunner.class)
public class XmlTransformerImplTest {

    private static final String URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER = "ruleExecutionResponseContainer";
    private static final String PARAM_NAME_XDS_DOCUMENT_ENTRY_UNIQUE_ID = "xdsDocumentEntryUniqueId";
    private static final String PARAM_NAME_PRIVACY_POLICIES_EXTERNAL_DOC_URL = "privacyPoliciesExternalDocUrl";
    private static final String PARAM_NAME_PURPOSE_OF_USE = "purposeOfUse";
    private static final String PARAM_NAME_INTENDED_RECIPIENT = "intendedRecipient";
    private static final String PARAM_NAME_AUTHOR_TELECOMMUNICATION = "authorTelecommunication";
    final String senderEmailAddress = "senderEmailAddress";
    final String recipientEmailAddress = "recipientEmailAddress";
    final String purposeOfUse = "purposeOfUse";
    final String messageId = "messageId";
    final String xdsDocumentEntryUniqueId = "xdsDocumentEntryUniqueId";
    final String ruleExecutionResponseContainer = "<ruleExecutionContainer><executionResponseList><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><code>66214007</code><codeSystem>2.16.840.1.113883.6.96</codeSystem><displayName>Substance Abuse Disorder</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NODSCLCD</documentRefrainPolicy><impliedConfSection>R</impliedConfSection><observationId>e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7</observationId><sensitivity>ETH</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><code>111880001</code><codeSystem>2.16.840.1.113883.6.96</codeSystem><displayName>Acute HIV</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NODSCLCD</documentRefrainPolicy><impliedConfSection>V</impliedConfSection><observationId>d11275e7-67ae-11db-bd13-0800200c9a66</observationId><sensitivity>HIV</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse></executionResponseList></ruleExecutionContainer>";
    final Optional<Params> params = Optional.of(ParamsBuilder
            .withParam(PARAM_NAME_AUTHOR_TELECOMMUNICATION, senderEmailAddress)
            .and(PARAM_NAME_INTENDED_RECIPIENT, recipientEmailAddress)
            .and(PARAM_NAME_PURPOSE_OF_USE, purposeOfUse)
            .and(PARAM_NAME_PRIVACY_POLICIES_EXTERNAL_DOC_URL, messageId)
            .and(PARAM_NAME_XDS_DOCUMENT_ENTRY_UNIQUE_ID,
                    xdsDocumentEntryUniqueId));

    final Optional<URIResolver> uriResolver = Optional
            .of(new StringURIResolver().put(
                    URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER,
                    ruleExecutionResponseContainer));

    private final List<String> ignoreList = Arrays
            .asList(addSlashes("/SubmitObjectsRequest[1]/RegistryObjectList[1]/ExtrinsicObject[1]/Slot[1]/ValueList[1]/Value[1]"),
                    addSlashes("/SubmitObjectsRequest[1]/RegistryObjectList[1]/RegistryPackage[1]/Slot[1]/ValueList[1]/Value[1]"));

    private final FileReader fileReader = new FileReaderImpl();
    private final DocumentXmlConverter documentXmlConverter = new DocumentXmlConverterImpl();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private SimpleMarshaller marshaller;

    @InjectMocks
    private XmlTransformerImpl sut;
    private final String xslName = "AdditonalMetadataStylesheetForProcessedC32.xsl";
    private final String docName = "c32.xml";
    private final String docNameTransformed = "c32Transformed.xml";
    private String doc;
    private String docTransformed;
    private final String xslUrl = Thread.currentThread()
            .getContextClassLoader().getResource(xslName).toString();

    @Before
    public void setUp() throws Exception {
        doc = fileReader.readFile(docName);
        docTransformed = fileReader.readFile(docNameTransformed);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTransformDocumentSourceOptionalOfParamsOptionalOfURIResolver()
            throws SAXException, IOException {
        // Arrange
        final Document xmlDocument = documentXmlConverter.loadDocument(doc);
        final StreamSource xslSource = new StreamSource(Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(xslName));

        // Act
        final String actualOutput = sut.transform(xmlDocument, xslSource,
                params, uriResolver);

        // Assert
        assertTrue(compareXMLs(docTransformed, actualOutput, ignoreList)
                .similar());
    }

    @Test
    public void testTransformDocumentStringOptionalOfParamsOptionalOfURIResolver() {
        // Arrange
        final Document xmlDocument = documentXmlConverter.loadDocument(doc);
        final String xslFileName = xslUrl;

        // Act
        final String actualOutput = sut.transform(xmlDocument, xslFileName,
                params, uriResolver);

        // Assert
        assertTrue(compareXMLs(docTransformed, actualOutput, ignoreList)
                .similar());
    }

    @Test
    public void testTransformObjectStringOptionalOfParamsOptionalOfURIResolver()
            throws SimpleMarshallerException, UnsupportedEncodingException {
        // Arrange
        final Object obj = mock(Object.class);
        final ByteArrayOutputStream byteArrayOutputStream = mock(ByteArrayOutputStream.class);
        when(marshaller.marshalAsByteArrayOutputStream(obj)).thenReturn(
                byteArrayOutputStream);
        when(byteArrayOutputStream.toByteArray()).thenReturn(
                doc.getBytes("UTF-8"));
        final String xslFileName = xslUrl;

        // Act
        final String actualOutput = sut.transform(obj, xslFileName, params,
                uriResolver);

        // Assert
        assertTrue(compareXMLs(docTransformed, actualOutput, ignoreList)
                .similar());
    }

    @Test
    public void testTransformSourceSourceOptionalOfParamsOptionalOfURIResolver()
            throws UnsupportedEncodingException {
        // Arrange
        final Source xmlSource = new StreamSource(new ByteArrayInputStream(
                doc.getBytes("UTF-8")));
        final Source xslSource = new StreamSource(Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(xslName));

        // Act
        final String actualOutput = sut.transform(xmlSource, xslSource, params,
                uriResolver);

        // Assert
        assertTrue(compareXMLs(docTransformed, actualOutput, ignoreList)
                .similar());
    }

    @Test
    public void testTransformStringStringOptionalOfParamsOptionalOfURIResolver() {
        // Act
        final String actualOutput = sut.transform(doc, xslUrl, params,
                uriResolver);

        // Assert
        assertTrue(compareXMLs(docTransformed, actualOutput, ignoreList)
                .similar());
    }

    private String addSlashes(String xpath) {
        return xpath.replace("[", "\\[").replace("]", "\\]");
    }
}