/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.xdsb.registry.wsclient.adapter;

import static gov.samhsa.acs.xdsb.common.XdsbPatientIdUtils.toFullyQualifiedPatientIdentifierWrappedWithSingleQuotes;
import static gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO;
import static gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindDocuments.XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM;
import static gov.samhsa.acs.xdsb.common.XdsbTextValueUtils.collectAsString;
import static gov.samhsa.acs.xdsb.common.XdsbTextValueUtils.wrapWithSingleQuotes;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.xdsb.common.AdhocQueryRequestBuilder;
import gov.samhsa.acs.xdsb.common.AdhocQueryRequestBuilder.ReturnType;
import gov.samhsa.acs.xdsb.common.AdhocQueryRequestBuilder.XdsbRegistryRequestData;
import gov.samhsa.acs.xdsb.common.AdhocQueryResponseParser;
import gov.samhsa.acs.xdsb.common.SourcePatientIdAndIntermediaryNpi;
import gov.samhsa.acs.xdsb.common.XdsbDocumentStatus;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataFormatCode;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindDocuments;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.FindSubmissionSets;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.GetDocuments;
import gov.samhsa.acs.xdsb.common.XdsbQueryParameterValue.GetSubmissionSetAndContents;
import gov.samhsa.acs.xdsb.common.XdsbTextValueUtils;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;
import gov.samhsa.acs.xdsb.registry.wsclient.exception.XdsbRegistryAdapterException;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * The Class XdsbRegistryAdapter.
 */
public class XdsbRegistryAdapter {

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	// Services
	/** The xdsb registry. */
	private final XdsbRegistryWebServiceClient xdsbRegistry;

	/** The xdsb registry request builder. */
	private final AdhocQueryRequestBuilder adhocQueryRequestBuilder;

	/** The response filter. */
	private final AdhocQueryResponseFilter adhocQueryResponseFilter;

	/** The adhoc query response parser. */
	private final AdhocQueryResponseParser adhocQueryResponseParser;

	/**
	 * Instantiates a new xdsb registry adapter.
	 *
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param adhocQueryRequestBuilder
	 *            the adhoc query request builder
	 * @param adhocQueryResponseFilter
	 *            the adhoc query response filter
	 * @param adhocQueryResponseParser
	 *            the adhoc query response parser
	 */
	public XdsbRegistryAdapter(XdsbRegistryWebServiceClient xdsbRegistry,
			AdhocQueryRequestBuilder adhocQueryRequestBuilder,
			AdhocQueryResponseFilter adhocQueryResponseFilter,
			AdhocQueryResponseParser adhocQueryResponseParser) {
		this.xdsbRegistry = xdsbRegistry;
		this.adhocQueryRequestBuilder = adhocQueryRequestBuilder;
		this.adhocQueryResponseFilter = adhocQueryResponseFilter;
		this.adhocQueryResponseParser = adhocQueryResponseParser;
	}

	/**
	 * Find deprecated document unique ids using registry stored query find
	 * submission sets (urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9) and get
	 * submission set and contents
	 * (urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83).
	 *
	 * @param submissionSetPatientId
	 *            the submission set patient id
	 * @param submissionSetAuthorPerson
	 *            the submission set author person
	 * @return the list
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public List<String> findDeprecatedDocumentUniqueIds(
			String submissionSetPatientId, String submissionSetAuthorPerson)
					throws XdsbRegistryAdapterException {
		submissionSetPatientId = submissionSetPatientId.replaceAll("&(?!amp;)",
				"&amp;");
		submissionSetAuthorPerson = submissionSetAuthorPerson.replaceAll(
				"&(?!amp;)", "&amp;");

		final List<String> deprecatedDocumentUniqueIds = new LinkedList<String>();
		final AdhocQueryResponse findSubmissionSetsResponse = findSubmissionSets(
				submissionSetPatientId, submissionSetAuthorPerson);
		final List<String> submissionSetUniqueIds = adhocQueryResponseParser
				.parseSubmissionSetUniqueIds(findSubmissionSetsResponse);
		for (final String submissionSetUniqueId : submissionSetUniqueIds) {
			final AdhocQueryResponse getSubmissionSetAndContentsResponse = getSubmissionSetAndContents(submissionSetUniqueId);
			final Optional<String> deprecatedDocumentUniqueId = adhocQueryResponseParser
					.parseDeprecatedDocumentUniqueId(getSubmissionSetAndContentsResponse);
			deprecatedDocumentUniqueId
					.ifPresent(deprecatedDocumentUniqueIds::add);
		}
		return deprecatedDocumentUniqueIds;
	}

	/**
	 * Registry stored query find documents -
	 * urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d (indirect call to the XDS.b
	 * registry service with a simplified interface).
	 *
	 * @param patientId
	 *            the patient id
	 * @param domainId
	 *            the domain id
	 * @param authorId
	 *            the author id
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @param serviceTimeAware
	 *            the service time aware
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse findDocuments(String patientId, String domainId,
			String authorId, XdsbDocumentType xdsbDocumentType,
			boolean serviceTimeAware) throws XdsbRegistryAdapterException {
		final String patientUniqueId = toFullyQualifiedPatientIdentifierWrappedWithSingleQuotes(
				patientId, domainId);

		return findDocuments(patientUniqueId, authorId, xdsbDocumentType,
				serviceTimeAware);
	}

	/**
	 * Registry stored query find documents -
	 * urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d.
	 *
	 * @param patientUniqueId
	 *            the patient unique id
	 * @param authorNPI
	 *            the author npi
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @param serviceTimeAware
	 *            the service time aware
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse findDocuments(String patientUniqueId,
			String authorNPI, XdsbDocumentType xdsbDocumentType,
			boolean serviceTimeAware) throws XdsbRegistryAdapterException {
		// Create a query request to search by patient unique id
		final AdhocQueryRequest registryStoredQuery = createRegistryStoredQueryByPatientId(
				patientUniqueId, xdsbDocumentType, serviceTimeAware);

		return registryStoredQueryFilterByAuthorNPI(registryStoredQuery,
				authorNPI);
	}

	/**
	 * Find metadata by retrieve document set request.
	 *
	 * @param retrieveDocumentSetRequest
	 *            the retrieve document set request
	 * @return the list
	 */
	public List<SourcePatientIdAndIntermediaryNpi> findMetadataByRetrieveDocumentSetRequest(
			RetrieveDocumentSetRequest retrieveDocumentSetRequest) {
		final AdhocQueryResponse metadata = getDocuments(retrieveDocumentSetRequest);
		return this.adhocQueryResponseParser
				.parseSourcePatientIdAndIntermediaryNpi(
						retrieveDocumentSetRequest, metadata);
	}

	/**
	 * Registry stored query find submission sets -
	 * urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9.
	 *
	 * @param submissionSetPatientId
	 *            the submission set patient id
	 * @param submissionSetAuthorPerson
	 *            the submission set author person
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse findSubmissionSets(String submissionSetPatientId,
			String submissionSetAuthorPerson)
					throws XdsbRegistryAdapterException {
		Assert.notNull(submissionSetPatientId);
		Assert.notNull(submissionSetAuthorPerson);

		final AdhocQueryRequest findSubmissionSetsRequest = this.adhocQueryRequestBuilder
				.findSubmissionSets()
				.addQueryParameter(
						FindSubmissionSets.XDS_SUBMISSION_SET_PATIENT_ID,
						submissionSetPatientId)
						.addQueryParameter(
								FindSubmissionSets.XDS_SUBMISSION_SET_AUTHOR_PERSON,
								submissionSetAuthorPerson)
								.addQueryParameter(
										FindSubmissionSets.XDS_SUBMISSION_SET_STATUS,
										collectAsString(XdsbDocumentStatus.APPROVED.getUrn()))
										.build();
		return registryStoredQuery(findSubmissionSetsRequest);
	}

	/**
	 * Gets the adhoc query request builder.
	 *
	 * @return the adhoc query request builder
	 */
	public AdhocQueryRequestBuilder getAdhocQueryRequestBuilder() {
		return adhocQueryRequestBuilder;
	}

	/**
	 * Gets the adhoc query response filter.
	 *
	 * @return the adhoc query response filter
	 */
	public AdhocQueryResponseFilter getAdhocQueryResponseFilter() {
		return adhocQueryResponseFilter;
	}

	/**
	 * Gets the adhoc query response parser.
	 *
	 * @return the adhoc query response parser
	 */
	public AdhocQueryResponseParser getAdhocQueryResponseParser() {
		return adhocQueryResponseParser;
	}

	/**
	 * Gets the documents.
	 *
	 * @param retrieveDocumentSetRequest
	 *            the retrieve document set request
	 * @return the documents
	 */
	public AdhocQueryResponse getDocuments(
			RetrieveDocumentSetRequest retrieveDocumentSetRequest) {
		final AdhocQueryRequest getDocumentsRequest = this.adhocQueryRequestBuilder
				.getDocuments()
				.setReturnType(ReturnType.LeafClass)
				.addQueryParameter(
						GetDocuments.XDS_DOCUMENT_ENTRY_UNIQUE_ID,
						XdsbTextValueUtils
						.collectAsString(retrieveDocumentSetRequest
								.getDocumentRequest()
								.stream()
								.map(DocumentRequest::getDocumentUniqueId)))
				.build();
		return registryStoredQuery(getDocumentsRequest);
	}

	/**
	 * Registry stored query gets the submission set and contents -
	 * urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83.
	 *
	 * @param submissionSetUniqueId
	 *            the submission set unique id
	 * @return the gets the submission set and contents
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse getSubmissionSetAndContents(
			String submissionSetUniqueId) throws XdsbRegistryAdapterException {
		// Invoke this method for each submissionSetId at a time. This stored
		// query doesn't support retrieval of multiple submission sets, do not
		// try to implement it.
		Assert.notNull(submissionSetUniqueId);

		final AdhocQueryRequest getSubmissionSetAndContentsRequest = this.adhocQueryRequestBuilder
				.getSubmissionSetAndContents()
				.addQueryParameter(
						GetSubmissionSetAndContents.XDS_SUBMISSION_SET_UNIQUE_ID,
						wrapWithSingleQuotes(submissionSetUniqueId)).build();

		return registryStoredQuery(getSubmissionSetAndContentsRequest);
	}

	/**
	 * Gets the xdsb registry.
	 *
	 * @return the xdsb registry
	 */
	public XdsbRegistryWebServiceClient getXdsbRegistry() {
		return xdsbRegistry;
	}

	/**
	 * Registry stored query (direct call to the XDS.b registry service).
	 *
	 * @param adhocQueryRequest
	 *            the adhoc query request
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse registryStoredQuery(
			AdhocQueryRequest adhocQueryRequest) {
		return xdsbRegistry.registryStoredQuery(adhocQueryRequest);
	}

	/**
	 * Registry stored query with authorNPI (intermediaryNPI) filtration.
	 *
	 * @param req
	 *            the req
	 * @param authorNPI
	 *            the author npi
	 *
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse registryStoredQuery(AdhocQueryRequest req,
			String authorNPI) throws XdsbRegistryAdapterException {
		try {
			return registryStoredQueryFilterByAuthorNPI(req, authorNPI);
		} catch (final Throwable t) {
			throw new XdsbRegistryAdapterException(t);
		}
	}

	/**
	 * Creates the registry stored query by patient id.
	 *
	 * @param patientUniqueId
	 *            the patient unique id
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @param serviceTimeAware
	 *            the service time aware
	 * @return the adhoc query request
	 */
	private AdhocQueryRequest createRegistryStoredQueryByPatientId(
			String patientUniqueId, XdsbDocumentType xdsbDocumentType,
			boolean serviceTimeAware) {
		final XdsbRegistryRequestData<FindDocuments> findDocumentsQueryBuilder = this.adhocQueryRequestBuilder
				.findDocuments()
				.setReturnComposedObjects(true)
				.setReturnType(ReturnType.LeafClass)
				.addQueryParameter(FindDocuments.XDS_DOCUMENT_ENTRY_PATIENT_ID,
						wrapWithSingleQuotes(patientUniqueId))
				.addQueryParameter(FindDocuments.XDS_DOCUMENT_ENTRY_STATUS,
								collectAsString(XdsbDocumentStatus.APPROVED.getUrn()))
				.addQueryParameter(
						FindDocuments.XDS_DOCUMENT_ENTRY_FORMAT_CODE,
						resolveFormatCode(xdsbDocumentType));
		if (serviceTimeAware) {
			final String currentTime = getCurrentTime();
			findDocumentsQueryBuilder.addQueryParameter(
					XDS_DOCUMENT_ENTRY_SERVICE_START_TIME_TO, currentTime)
					.addQueryParameter(
							XDS_DOCUMENT_ENTRY_SERVICE_STOP_TIME_FROM,
							currentTime);
		}
		return findDocumentsQueryBuilder.build();
	}

	/**
	 * Gets the current time.
	 *
	 * @return the current time
	 */
	private String getCurrentTime() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	/**
	 * Registry stored query filter by author npi.
	 *
	 * @param req
	 *            the req
	 * @param authorNPI
	 *            the author npi
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	private AdhocQueryResponse registryStoredQueryFilterByAuthorNPI(
			AdhocQueryRequest req, String authorNPI)
			throws XdsbRegistryAdapterException {
		// Query Response
		final AdhocQueryResponse adhocQueryResponse = registryStoredQuery(req);
		final AdhocQueryResponse filteredAdhocQueryResponse = StringUtils
				.hasText(authorNPI) ? adhocQueryResponseFilter.filterByAuthor(
				adhocQueryResponse, authorNPI) : adhocQueryResponse;
						logger.debug(() -> adhocQueryResponseParser
				.marshal(filteredAdhocQueryResponse));
						return filteredAdhocQueryResponse;
	}

	/**
	 * Resolve format code.
	 *
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @return the string
	 */
	private String resolveFormatCode(XdsbDocumentType xdsbDocumentType) {
		final String formatCodeValue = xdsbDocumentType
				.getXdsbMetadataFormatCode()
				.map(XdsbMetadataFormatCode::getFormatCode)
				.orElseThrow(this::xdsbRegistryAdapterExceptionByLoggingError);
		return formatCodeValue;
	}

	/**
	 * Xdsb registry adapter exception by logging error.
	 *
	 * @return the xdsb registry adapter exception
	 */
	private XdsbRegistryAdapterException xdsbRegistryAdapterExceptionByLoggingError() {
		final String errorMessage = "Unsupported XDS.b document format code";
		logger.error(errorMessage);
		return new XdsbRegistryAdapterException(errorMessage);
	}
}
