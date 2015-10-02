package gov.samhsa.acs.xdsb.common;

import static gov.samhsa.acs.xdsb.common.XdsbDocumentStatus.DEPRECATED;
import static gov.samhsa.acs.xdsb.common.XdsbUUIDValue.XDSDocumentEntry.UUID_XDS_DOCUMENTENTRY;
import static gov.samhsa.acs.xdsb.common.XdsbUUIDValue.XDSDocumentEntry.UUID_XDS_DOCUMENTENTRY_UNIQUEID;
import static gov.samhsa.acs.xdsb.common.XdsbUUIDValue.XDSSubmissionSet.UUID_XDS_SUBMISSION_SET_UNIQUEID;
import static java.util.stream.Collectors.toList;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.acs.common.tool.exception.DocumentXmlConverterException;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class AdhocQueryResponseParser.
 */
public class AdhocQueryResponseParser {

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The marshaller. */
	private final SimpleMarshaller marshaller;

	/** The document xml converter. */
	private final DocumentXmlConverter documentXmlConverter;

	/** The document accessor. */
	private final DocumentAccessor documentAccessor;

	/**
	 * Instantiates a new adhoc query response parser.
	 *
	 * @param marshaller
	 *            the marshaller
	 * @param documentXmlConverter
	 *            the document xml converter
	 * @param documentAccessor
	 *            the document accessor
	 */
	public AdhocQueryResponseParser(SimpleMarshaller marshaller,
			DocumentXmlConverter documentXmlConverter,
			DocumentAccessor documentAccessor) {
		super();
		this.marshaller = marshaller;
		this.documentXmlConverter = documentXmlConverter;
		this.documentAccessor = documentAccessor;
	}

	/**
	 * Marshal.
	 *
	 * @param adhocQueryResponse
	 *            the adhoc query response
	 * @return the string
	 */
	public String marshal(AdhocQueryResponse adhocQueryResponse) {
		try {
			return marshaller.marshal(adhocQueryResponse);
		} catch (final SimpleMarshallerException e) {
			throw new AdhocQueryResponseParserException(e);
		}
	}

	/**
	 * Extract deprecated document unique id.
	 *
	 * @param response
	 *            the response
	 * @return the optional
	 * @throws AdhocQueryResponseParserException
	 *             the adhoc query response parser exception
	 */
	public Optional<String> parseDeprecatedDocumentUniqueId(
			AdhocQueryResponse response)
					throws AdhocQueryResponseParserException {
		try {
			final String responseXml = marshal(response);
			final Document responseDoc = this.documentXmlConverter
					.loadDocument(responseXml);
			// Extract documentUniqueId if there is an association in the
			// submission
			// set with a NewStatus slot having SUBMISSION_SET_STATUS_DEPRECATED
			// value. Return null, if there is not any.
			final StringBuilder builder = new StringBuilder();
			builder.append("//rim:Association[descendant::rim:Slot[@name='NewStatus']][descendant::rim:Value[.='");
			builder.append(DEPRECATED.getUrn());
			builder.append("']]/preceding-sibling::rim:ExtrinsicObject[@objectType='");
			builder.append(UUID_XDS_DOCUMENTENTRY.getUUID());
			builder.append("']/descendant::rim:ExternalIdentifier[@identificationScheme='");
			builder.append(UUID_XDS_DOCUMENTENTRY_UNIQUEID.getUUID());
			builder.append("']/@value");
			final String xPathExpr = builder.toString();
			final Optional<Node> node = this.documentAccessor.getNode(
					responseDoc, xPathExpr);
			return node.map(Node::getNodeValue);
		} catch (final DocumentAccessorException e) {
			logger.error(e.getMessage());
			throw new AdhocQueryResponseParserException(e.getMessage(), e);
		}
	}

	/**
	 * Parses the source patient id and intermediary npi.
	 *
	 * @param retrieveDocumentSetRequest
	 *            the retrieve document set request
	 * @param adhocQueryResponse
	 *            the adhoc query response
	 * @return the list
	 * @throws AdhocQueryResponseParserException
	 *             the adhoc query response parser exception
	 */
	public List<SourcePatientIdAndIntermediaryNpi> parseSourcePatientIdAndIntermediaryNpi(
			RetrieveDocumentSetRequest retrieveDocumentSetRequest,
			AdhocQueryResponse adhocQueryResponse)
			throws AdhocQueryResponseParserException {
		try {
			final String xpathSourcePatientId = "/query:AdhocQueryResponse/rim:RegistryObjectList/rim:ExtrinsicObject[rim:Slot[@name='repositoryUniqueId'][rim:ValueList[rim:Value[1][text()='%1']]]][rim:ExternalIdentifier[@identificationScheme='urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab' and @value='%2']]/rim:Slot[@name='sourcePatientId']/rim:ValueList/rim:Value[1]/text()";
			final String xpathAuthorPerson = "/query:AdhocQueryResponse/rim:RegistryObjectList/rim:ExtrinsicObject[rim:Slot[@name='repositoryUniqueId'][rim:ValueList[rim:Value[1][text()='%1']]]][rim:ExternalIdentifier[@identificationScheme='urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab' and @value='%2']]/rim:Classification[@classificationScheme='urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d'][1]/rim:Slot[@name='authorPerson'][1]/rim:ValueList/rim:Value[1]/text()";
			final String responseXml = marshal(adhocQueryResponse);
			final Document responseDoc = this.documentXmlConverter
					.loadDocument(responseXml);
			final List<SourcePatientIdAndIntermediaryNpi> sourcePatientIdAndIntermediaryNpis = retrieveDocumentSetRequest
					.getDocumentRequest()
					.stream()
					.map(XdsbDocumentReference::from)
					.map(ref -> new SourcePatientIdAndIntermediaryNpi(ref,
							getNode(responseDoc, xpathSourcePatientId,
									ref.getRepositoryUniqueId(),
									ref.getDocumentUniqueId()).get()
									.getNodeValue(), getNode(responseDoc,
									xpathAuthorPerson,
									ref.getRepositoryUniqueId(),
									ref.getDocumentUniqueId()).get()
									.getNodeValue())).collect(toList());
			return sourcePatientIdAndIntermediaryNpis;
		} catch (final Exception e) {
			logger.error(e.getMessage());
			throw new AdhocQueryResponseParserException(e.getMessage(), e);
		}
	}

	/**
	 * Extract submission set unique ids.
	 *
	 * @param response
	 *            the response
	 * @return the list
	 * @throws AdhocQueryResponseParserException
	 *             the adhoc query response parser exception
	 */
	public List<String> parseSubmissionSetUniqueIds(AdhocQueryResponse response)
			throws AdhocQueryResponseParserException {
		try {
			final List<String> submissionSetUniqueIdList = new LinkedList<String>();
			final String responseXml = this.marshaller.marshal(response);
			final Document responseDoc = this.documentXmlConverter
					.loadDocument(responseXml);
			final String xPathExpr = "//rim:ExternalIdentifier[@identificationScheme='$']/@value";
			final NodeList nodeList = this.documentAccessor.getNodeList(
					responseDoc,
					xPathExpr.replace("$",
							UUID_XDS_SUBMISSION_SET_UNIQUEID.getUUID()));
			for (int i = 0; i < nodeList.getLength(); i++) {
				submissionSetUniqueIdList.add(nodeList.item(i).getNodeValue());
			}
			return submissionSetUniqueIdList;
		} catch (SimpleMarshallerException | DocumentAccessorException e) {
			throw new AdhocQueryResponseParserException(e);
		}
	}

	/**
	 * Extract XDS.b document reference list.
	 *
	 * @param adhocQueryResponse
	 *            the adhoc query response
	 * @return the list of XdsbDocumentReference objects having documentUniqueId
	 *         and repositoryId pairs to retrieve the documents
	 * @throws Exception
	 *             the exception
	 * @throws Throwable
	 *             the throwable
	 */
	public List<XdsbDocumentReference> parseXdsbDocumentReferenceList(
			AdhocQueryResponse adhocQueryResponse) throws Exception, Throwable {
		final String adhocQueryResponseXmlString = marshal(adhocQueryResponse);
		final Document doc = documentXmlConverter
				.loadDocument(adhocQueryResponseXmlString);

		// Scan the document ExtrinsicObject elements
		final NodeList extrinsicObjects = doc
				.getElementsByTagName("ExtrinsicObject");

		// Temporary storage for documentUniqueIds and repositryIds
		final Map<Integer, String> documentUniqueIdMap = new HashMap<Integer, String>();
		final Map<Integer, String> repositoryUniqueIdMap = new HashMap<Integer, String>();

		// For each ExtrinsicObject
		for (int i = 0; i < extrinsicObjects.getLength(); i++) {
			final Node extrinsicObject = extrinsicObjects.item(i);
			final NodeList extrinsicObjectItems = extrinsicObject
					.getChildNodes();

			// For each element of ExtrinsicObject
			for (int j = 0; j < extrinsicObjectItems.getLength(); j++) {
				final Node extrinsicObjectItem = extrinsicObjectItems.item(j);

				// If the element is ExternalIdentifier with
				// a XDSDocumentEntry UniqueId
				if ("ExternalIdentifier".equals(extrinsicObjectItem
						.getNodeName())
						&& UUID_XDS_DOCUMENTENTRY_UNIQUEID.getUUID().equals(
								extrinsicObjectItem.getAttributes()
										.getNamedItem("identificationScheme")
										.getNodeValue())) {
					// Get the value of UniqueId and store in temporary map
					documentUniqueIdMap.put(i, extrinsicObjectItem
							.getAttributes().getNamedItem("value")
							.getNodeValue());
				}

				// If the element is a Slot with repositoryUniqueId
				if ("Slot".equals(extrinsicObjectItem.getNodeName())
						&& "repositoryUniqueId".equals(extrinsicObjectItem
								.getAttributes().getNamedItem("name")
								.getNodeValue())) {
					// Get the value of repositoryUniqueId and store in
					// temporary map
					repositoryUniqueIdMap.put(i, extrinsicObjectItem
							.getChildNodes().item(0).getChildNodes().item(0)
							.getTextContent());
				}
			}
		}

		// Combine the maps into a list of XdsbDocumentReference
		final Set<Integer> keys = documentUniqueIdMap.keySet();
		final List<XdsbDocumentReference> repositoryAndDocumentUniqueIdList = new LinkedList<XdsbDocumentReference>();
		for (final Integer key : keys) {
			final XdsbDocumentReference xdsbDocumentReference = new XdsbDocumentReference(
					documentUniqueIdMap.get(key),
					repositoryUniqueIdMap.get(key));
			repositoryAndDocumentUniqueIdList.add(xdsbDocumentReference);
		}

		return repositoryAndDocumentUniqueIdList;
	}

	/**
	 * Extract xdsb document reference list as retrieve document set request.
	 *
	 * @param adhocQueryResponse
	 *            the adhoc query response
	 * @return the retrieve document set request
	 * @throws AdhocQueryResponseParserException
	 *             the adhoc query response parser exception
	 */
	public RetrieveDocumentSetRequest parseXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(
			AdhocQueryResponse adhocQueryResponse)
					throws AdhocQueryResponseParserException {
		String adhocQueryResponseXmlString;
		try {
			adhocQueryResponseXmlString = marshal(adhocQueryResponse);
			final Document doc = documentXmlConverter
					.loadDocument(adhocQueryResponseXmlString);

			// Scan the document ExtrinsicObject elements
			final NodeList extrinsicObjects = doc
					.getElementsByTagName("ExtrinsicObject");

			// Temporary storage for documentUniqueIds and repositryIds
			final Map<Integer, String> documentUniqueIdMap = new HashMap<Integer, String>();
			final Map<Integer, String> repositoryUniqueIdMap = new HashMap<Integer, String>();

			// For each ExtrinsicObject
			for (int i = 0; i < extrinsicObjects.getLength(); i++) {
				final Node extrinsicObject = extrinsicObjects.item(i);
				final NodeList extrinsicObjectItems = extrinsicObject
						.getChildNodes();

				// For each element of ExtrinsicObject
				for (int j = 0; j < extrinsicObjectItems.getLength(); j++) {
					final Node extrinsicObjectItem = extrinsicObjectItems
							.item(j);

					// If the element is ExternalIdentifier with
					// a XDSDocumentEntry UniqueId
					if ("ExternalIdentifier".equals(extrinsicObjectItem
							.getNodeName())
							&& UUID_XDS_DOCUMENTENTRY_UNIQUEID.getUUID()
							.equals(extrinsicObjectItem
									.getAttributes()
									.getNamedItem(
											"identificationScheme")
											.getNodeValue())) {
						// Get the value of UniqueId and store in temporary map
						documentUniqueIdMap.put(i, extrinsicObjectItem
								.getAttributes().getNamedItem("value")
								.getNodeValue());
					}

					// If the element is a Slot with repositoryUniqueId
					if ("Slot".equals(extrinsicObjectItem.getNodeName())
							&& "repositoryUniqueId".equals(extrinsicObjectItem
									.getAttributes().getNamedItem("name")
									.getNodeValue())) {
						// Get the value of repositoryUniqueId and store in
						// temporary map
						repositoryUniqueIdMap.put(i, extrinsicObjectItem
								.getChildNodes().item(0).getChildNodes()
								.item(0).getTextContent());
					}
				}
			}

			// Combine the maps into a list of XdsbDocumentReference
			final Set<Integer> keys = documentUniqueIdMap.keySet();
			final RetrieveDocumentSetRequest retrieveDocumentSetRequest = new RetrieveDocumentSetRequest();
			final List<DocumentRequest> documentRequestList = retrieveDocumentSetRequest
					.getDocumentRequest();
			for (final Integer key : keys) {
				final XdsbDocumentReference xdsbDocumentReference = new XdsbDocumentReference(
						documentUniqueIdMap.get(key),
						repositoryUniqueIdMap.get(key));
				documentRequestList.add(xdsbDocumentReference);
			}

			return retrieveDocumentSetRequest;

		} catch (final DocumentXmlConverterException e) {
			throw new AdhocQueryResponseParserException(e);
		}
	}

	/**
	 * Gets the node.
	 *
	 * @param document
	 *            the document
	 * @param xPathExpr
	 *            the x path expr
	 * @param arguments
	 *            the arguments
	 * @return the node
	 */
	private Optional<Node> getNode(Document document, String xPathExpr,
			String... arguments) {
		try {
			return this.documentAccessor
					.getNode(document, xPathExpr, arguments);
		} catch (final DocumentAccessorException e) {
			logger.error(e.getMessage());
			throw new AdhocQueryResponseParserException(e.getMessage(), e);
		}

	}
}
