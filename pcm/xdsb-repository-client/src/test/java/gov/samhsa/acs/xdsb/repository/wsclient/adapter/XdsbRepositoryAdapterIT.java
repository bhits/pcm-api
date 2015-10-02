package gov.samhsa.acs.xdsb.repository.wsclient.adapter;

import static org.junit.Assert.assertEquals;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbErrorFactory;
import gov.samhsa.acs.xdsb.repository.wsclient.XDSRepositorybWebServiceClient;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.ElementNameAndAttributeQualifier;
import org.custommonkey.xmlunit.ElementNameAndTextQualifier;
import org.custommonkey.xmlunit.ElementNameQualifier;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

@SuppressWarnings("unused")
public class XdsbRepositoryAdapterIT {
	// Constants
	private static final String XDSB_SUCCESS_MESSAGE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:RegistryResponse status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\" xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"/>";

	private static final String HOME_COMMUNITY_ID = "2.16.840.1.113883.3.467";
	private static final String OPENEMPI_DOMAIN_ID = "2.16.840.1.113883.4.357";
	private static final String REPOSITORY_ID = "1.3.6.1.4.1.21367.2010.1.2.1040";
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT = XdsbDocumentType.CLINICAL_DOCUMENT;
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT = XdsbDocumentType.PRIVACY_CONSENT;
	// Endpoints by environment
	private static final String DEV_XDSB_REPO_ENDPOINT = "http://obhidevacs001:9080//axis2/services/xdsrepositoryb";

	private static final String QA_XDSB_REPO_ENDPOINT = "http://obhitaqaacs01:9080/axis2/services/xdsrepositoryb";
	private static final String DEMO_XDSB_REPO_ENDPOINT = "http://obhitademoacs01:9080/axis2/services/xdsrepositoryb";
	// Document references by environments
	private static final String DEV_CLINICAL_DOCUMENT_ID = "41421263015.98411.41414.91230.401390172014139";

	private static final String DEV_XACML_DOCUMENT_ID = "115131313411.1521214.42153.10531.01415253967874";
	private static final String QA_CLINICAL_DOCUMENT_ID = "21411614111313.111559.412156.88122.51310810121010135131";

	private static final String QA_XACML_DOCUMENT_ID = "10121163121112.145102.4999.1181413.11101339479154213";
	private static final String DEMO_CLINICAL_DOCUMENT_ID = "1159100196.8727.4619.9589.55410001012119150";

	private static final String DEMO_XACML_DOCUMENT_ID = "12156561203.101298.41246.11843.2145892121061503";
	// Variables
	private static String c32;

	private static String uploadC32;
	private static String xacml;
	private static String clinicalDocumentId;
	private static String xacmlDocumentId;
	private static String endpointAddress;
	// Services
	private static FileReader fileReader;

	private static SimpleMarshaller marshaller;
	// System under test
	private static XdsbRepositoryAdapter xdsbRepositoryAdapter;

	// Logger
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testProvideAndRegisterDocumentSetRequest_C32() throws Throwable {
		// Act
		final RegistryResponse registryResponse = xdsbRepositoryAdapter
				.provideAndRegisterDocumentSet(uploadC32, OPENEMPI_DOMAIN_ID,
						XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT, null, null);
		final String result = marshaller.marshal(registryResponse);
		logger.debug("testProvideAndRegisterDocumentSet_C32 Result:");
		logger.debug(result);
		System.out.println(result);

		// Assert
		assertEquals(XDSB_SUCCESS_MESSAGE, result);
	}

	@Test
	public void testProvideAndRegisterDocumentSetRequest_Xacml_Consent()
			throws Throwable {

		// Act
		final RegistryResponse registryResponse = xdsbRepositoryAdapter
				.provideAndRegisterDocumentSet(xacml, OPENEMPI_DOMAIN_ID,
						XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT, null, null);
		final String result = marshaller.marshal(registryResponse);
		logger.debug("testProvideAndRegisterDocumentSet_Xacml_Consent Result:");
		logger.debug(result);

		// Assert
		assertEquals(XDSB_SUCCESS_MESSAGE, result);
	}

	// make sure you have "unitTestC32.xml" in your XDS.b endpoint with
	// documentId= clinicalDocumentId
	// repositoryId= REPOSITORY_ID
	// to pass this test
	@Test
	public void testRetrieveDocumentSetRequest_C32() {
		// Act
		final RetrieveDocumentSetResponse retrieveDocumentSetResponse = xdsbRepositoryAdapter
				.retrieveDocumentSet(clinicalDocumentId, REPOSITORY_ID);
		final String result = new String(retrieveDocumentSetResponse
				.getDocumentResponse().get(0).getDocument());
		logger.debug(result);

		/**
		 * list of regular expressions that custom difference listener used
		 * during xml comparison.
		 */
		final List<String> ignorableXPathsRegex = new ArrayList<String>();
		ignorableXPathsRegex
				.add("\\/ClinicalDocument\\[1\\]/effectiveTime\\[1\\]\\/@value");
		// these values will be changed from original rem c32 before uploading
		// to xdsb by showcase program
		// thats why we are ignoring these elemetns as they won't match with
		// original c32
		ignorableXPathsRegex
				.add("\\/ClinicalDocument\\[1\\]/recordTarget\\[1\\]/patientRole\\[1\\]/id\\[1\\]");
		ignorableXPathsRegex
				.add("\\/ClinicalDocument\\[1\\]\\/author\\[1\\]\\/assignedAuthor\\[1\\]\\/id\\[1\\]");

		final DetailedDiff diff = compareXMLs(c32, result, ignorableXPathsRegex);
		// Diff provides two methods for comparison identical and similar.
		// Identical expects content and order of elements to be same.
		// Similar is less stricter and allows change in order
		Assert.assertEquals(true, diff.similar());

	}

	// make sure you have "xacml_policy.xml" in your XDS.b endpoint with
	// documentId= xacmlDocumentId
	// repositoryId= REPOSITORY_ID
	// to pass this test
	@Test
	public void testRetrieveDocumentSetRequest_Xacml_Consent() {
		// Act
		final RetrieveDocumentSetResponse retrieveDocumentSetResponse = xdsbRepositoryAdapter
				.retrieveDocumentSet(xacmlDocumentId, REPOSITORY_ID);
		final String result = new String(retrieveDocumentSetResponse
				.getDocumentResponse().get(0).getDocument());
		logger.debug(result);

		// Assert
		assertEquals(xacml, result);
	}

	private DetailedDiff compareXMLs(String expectedResult,
			String actualResult, List<String> ignorableXPathsRegex) {

		DetailedDiff diff = null;
		try {
			setXMLUnitConfig();

			diff = new DetailedDiff(XMLUnit.compareXML(expectedResult,
					actualResult));
			diff.overrideElementQualifier(new ElementNameAndTextQualifier());
			diff.overrideElementQualifier(new ElementNameQualifier());
			diff.overrideElementQualifier(new ElementNameAndAttributeQualifier());
			diff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());

			if (ignorableXPathsRegex != null) {
				final RegexBasedDifferenceListener ignorableElementsListener = new RegexBasedDifferenceListener(
						ignorableXPathsRegex);
				/** setting our custom difference listener */
				diff.overrideDifferenceListener(ignorableElementsListener);
			}

			@SuppressWarnings("unchecked")
			final List<Difference> differences = diff.getAllDifferences();
			for (final Object object : differences) {
				final Difference difference = (Difference) object;
				System.out.println("***********************");
				System.out.println(difference);
				System.out.println("***********************");
			}

		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return diff;

	}

	private void setXMLUnitConfig() {

		XMLUnit.setIgnoreWhitespace(Boolean.TRUE);
		XMLUnit.setIgnoreComments(Boolean.TRUE);
		XMLUnit.setIgnoreDiffBetweenTextAndCDATA(Boolean.TRUE);
		XMLUnit.setIgnoreAttributeOrder(Boolean.TRUE);
	}

	@BeforeClass
	public static void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		marshaller = new SimpleMarshallerImpl();
		c32 = fileReader.readFile("unitTestC32.xml");
		uploadC32 = fileReader.readFile("uploadC32.xml");
		xacml = fileReader.readFile("xacml_policy.xml");

		// Set these to the values of the environment under test
		endpointAddress = QA_XDSB_REPO_ENDPOINT;
		clinicalDocumentId = QA_CLINICAL_DOCUMENT_ID;
		xacmlDocumentId = QA_XACML_DOCUMENT_ID;

		xdsbRepositoryAdapter = new XdsbRepositoryAdapter(
				new XDSRepositorybWebServiceClient(endpointAddress),
				new SimpleMarshallerImpl(),
				new RetrieveDocumentSetResponseFilter(
						new DocumentXmlConverterImpl(),
						new DocumentAccessorImpl(), new XdsbErrorFactory()),
				new XmlTransformerImpl(new SimpleMarshallerImpl()));
	}

}
