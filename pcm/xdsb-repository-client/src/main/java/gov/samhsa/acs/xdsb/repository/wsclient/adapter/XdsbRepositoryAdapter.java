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
package gov.samhsa.acs.xdsb.repository.wsclient.adapter;

import static gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorParams.PatientUniqueId_Parameter_Name;
import static gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorParams.XdsDocumentEntry_EntryUUID_Parameter_Name;
import gov.samhsa.acs.common.param.Params;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.xdsb.common.UniqueOidProviderImpl;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGenerator;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorImpl;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorParams;
import gov.samhsa.acs.xdsb.repository.wsclient.XDSRepositorybWebServiceClient;
import gov.samhsa.acs.xdsb.repository.wsclient.exception.XdsbRepositoryAdapterException;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest.Document;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.springframework.util.Assert;

/**
 * The Class XdsbRepositoryAdapter.
 */
public class XdsbRepositoryAdapter {

	/** The Constant EMPTY_XML_DOCUMENT. */
	public static final String EMPTY_XML_DOCUMENT = "<empty/>";

	// Services
	/** The xdsb repository. */
	private XDSRepositorybWebServiceClient xdsbRepository;

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/** The xml transformer. */
	private XmlTransformer xmlTransformer;

	/** The response filter. */
	private RetrieveDocumentSetResponseFilter responseFilter;

	/**
	 * Instantiates a new xdsb repository adapter.
	 */
	public XdsbRepositoryAdapter() {
	}

	/**
	 * Instantiates a new xdsb repository adapter.
	 *
	 * @param xdsbRepository
	 *            the xdsb repository
	 * @param marshaller
	 *            the marshaller
	 * @param responseFilter
	 *            the response filter
	 * @param xmlTransformer
	 *            the xml transformer
	 */
	public XdsbRepositoryAdapter(XDSRepositorybWebServiceClient xdsbRepository,
			SimpleMarshaller marshaller,
			RetrieveDocumentSetResponseFilter responseFilter,
			XmlTransformer xmlTransformer) {

		this.xdsbRepository = xdsbRepository;
		this.marshaller = marshaller;
		this.responseFilter = responseFilter;
		this.xmlTransformer = xmlTransformer;
	}

	/**
	 * Provide and register document set request (direct call to the XDS.b
	 * repository service).
	 *
	 * @param provideAndRegisterDocumentSetRequest
	 *            the provide and register document set request
	 * @return the registry response
	 */
	public RegistryResponse provideAndRegisterDocumentSet(
			ProvideAndRegisterDocumentSetRequest provideAndRegisterDocumentSetRequest) {
		return xdsbRepository
				.provideAndRegisterDocumentSet(provideAndRegisterDocumentSetRequest);
	}

	/**
	 * Provide and register document set request (indirect call to the XDS.b
	 * repository service with a simplified interface).
	 *
	 * @param documentXmlString
	 *            the document xml string (Pass
	 *            XdsbRepositoryAdapter.EMPTY_XML_DOCUMENT if deprecating a
	 *            document. Otherwise, pass the actual document to be provided.)
	 * @param homeCommunityId
	 *            the home community id (May pass null if deprecating a
	 *            document.)
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @param patientUniqueId
	 *            the patient unique id (Pass this only if deprecating a
	 *            document. Otherwise, pass null.)
	 * @param entryUUID
	 *            the entry uuid (Pass this only if deprecating a document.
	 *            Otherwise, pass null.)
	 * @return the registry response
	 * @throws XdsbRepositoryAdapterException
	 *             the xdsb repository adapter exception
	 */
	@Deprecated
	public RegistryResponse provideAndRegisterDocumentSet(
			String documentXmlString, String homeCommunityId,
			XdsbDocumentType documentTypeForXdsbMetadata,
			String patientUniqueId, String entryUUID)
			throws XdsbRepositoryAdapterException {
		switch (documentTypeForXdsbMetadata) {
		case DEPRECATE_PRIVACY_CONSENT:
			final String messageDeprecate = "patientUniqueId and entryUUID must be injected to deprecate a document.";
			Assert.notNull(patientUniqueId, messageDeprecate);
			Assert.notNull(entryUUID, messageDeprecate);
			break;
		default:
			final String messageNotDeprecate = "patientUniqueId and entryUUID can only be injected to deprecate a document.";
			Assert.isNull(patientUniqueId, messageNotDeprecate);
			Assert.isNull(entryUUID, messageNotDeprecate);
			break;
		}

		final String submitObjectRequestXml = generateMetadata(
				documentXmlString, homeCommunityId,
				documentTypeForXdsbMetadata, patientUniqueId, entryUUID);
		SubmitObjectsRequest submitObjectRequest;
		try {
			submitObjectRequest = marshaller.unmarshalFromXml(
					SubmitObjectsRequest.class, submitObjectRequestXml);
		} catch (final SimpleMarshallerException e) {
			throw new XdsbRepositoryAdapterException(e);
		}

		Document document = null;
		if (!documentXmlString.equals(EMPTY_XML_DOCUMENT)) {
			document = createDocument(documentXmlString);
		}

		final ProvideAndRegisterDocumentSetRequest request = createProvideAndRegisterDocumentSetRequest(
				submitObjectRequest, document);

		final RegistryResponse response = provideAndRegisterDocumentSet(request);
		return response;
	}

	/**
	 * Provide and register document set.
	 *
	 * @param documentXmlString
	 *            the document xml string
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @param params
	 *            the params
	 * @return the registry response
	 * @throws XdsbRepositoryAdapterException
	 *             the xdsb repository adapter exception
	 */
	public RegistryResponse provideAndRegisterDocumentSet(
			String documentXmlString,
			XdsbDocumentType documentTypeForXdsbMetadata, Params params)
			throws XdsbRepositoryAdapterException {
		switch (documentTypeForXdsbMetadata) {
		case DEPRECATE_PRIVACY_CONSENT:
			final String messageDeprecate = "patientUniqueId and entryUUID must be injected to deprecate a document.";
			Assert.notNull(
					getParamValue(params, PatientUniqueId_Parameter_Name),
					messageDeprecate);
			Assert.notNull(
					getParamValue(params,
							XdsDocumentEntry_EntryUUID_Parameter_Name),
					messageDeprecate);
			break;
		default:
			final String messageNotDeprecate = "patientUniqueId and entryUUID can only be injected to deprecate a document.";
			Assert.isNull(
					getParamValue(params, PatientUniqueId_Parameter_Name),
					messageNotDeprecate);
			Assert.isNull(
					getParamValue(params,
							XdsDocumentEntry_EntryUUID_Parameter_Name),
					messageNotDeprecate);
			break;
		}

		final String submitObjectRequestXml = generateMetadata(
				documentXmlString, documentTypeForXdsbMetadata, params);
		SubmitObjectsRequest submitObjectRequest;
		try {
			submitObjectRequest = marshaller.unmarshalFromXml(
					SubmitObjectsRequest.class, submitObjectRequestXml);
		} catch (final SimpleMarshallerException e) {
			throw new XdsbRepositoryAdapterException(e);
		}

		Document document = null;
		if (!documentXmlString.equals(EMPTY_XML_DOCUMENT)) {
			document = createDocument(documentXmlString);
		}

		final ProvideAndRegisterDocumentSetRequest request = createProvideAndRegisterDocumentSetRequest(
				submitObjectRequest, document);

		final RegistryResponse response = provideAndRegisterDocumentSet(request);
		return response;
	}

	/**
	 * Retrieve document set.
	 *
	 * @param docRequest
	 *            the doc request
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			DocumentRequest docRequest) {
		final RetrieveDocumentSetRequest request = createRetrieveDocumentSetRequest(docRequest);

		final RetrieveDocumentSetResponse retrieveDocumentSetResponse = retrieveDocumentSet(request);
		return retrieveDocumentSetResponse;
	}

	/**
	 * Retrieve document set.
	 *
	 * @param docRequest
	 *            the doc request
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			List<DocumentRequest> docRequest) {
		final RetrieveDocumentSetRequest request = createRetrieveDocumentSetRequest(docRequest);

		final RetrieveDocumentSetResponse retrieveDocumentSetResponse = retrieveDocumentSet(request);
		return retrieveDocumentSetResponse;
	}

	/**
	 * Retrieve document set request (direct call to XDS.b repository service).
	 *
	 * @param request
	 *            the request
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest request) {
		return xdsbRepository.retrieveDocumentSet(request);
	}

	/**
	 * Retrieve document set.
	 *
	 * @param request
	 *            the request
	 * @param patientId
	 *            the patient id
	 * @param authorNPI
	 *            the author npi
	 * @return the retrieve document set response
	 * @throws XdsbRepositoryAdapterException
	 *             the xdsb repository adapter exception
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest request, String patientId,
			String authorNPI) throws XdsbRepositoryAdapterException {
		try {
			RetrieveDocumentSetResponse response = xdsbRepository
					.retrieveDocumentSet(request);
			response = responseFilter.filterByPatientAndAuthor(response,
					patientId, authorNPI);
			return response;
		} catch (final Throwable t) {
			throw new XdsbRepositoryAdapterException(t);
		}
	}

	/**
	 * Retrieve document set request (indirect call to the XDS.b repository
	 * service with a simplified interface).
	 *
	 * @param documentUniqueId
	 *            the document unique id
	 * @param repositoryId
	 *            the repository id
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			String documentUniqueId, String repositoryId) {
		final RetrieveDocumentSetRequest request = createRetrieveDocumentSetRequest(
				documentUniqueId, repositoryId);

		final RetrieveDocumentSetResponse retrieveDocumentSetResponse = retrieveDocumentSet(request);
		return retrieveDocumentSetResponse;
	}

	/**
	 * Creates the document.
	 *
	 * @param documentXmlString
	 *            the document xml string
	 * @return the document
	 */
	Document createDocument(String documentXmlString) {
		final Document document = new Document();
		document.setId("Document01");
		document.setValue(documentXmlString.getBytes());
		return document;
	}

	/**
	 * Creates the provide and register document set request.
	 *
	 * @param submitObjectRequest
	 *            the submit object request
	 * @param document
	 *            the document
	 * @return the provide and register document set request
	 */
	ProvideAndRegisterDocumentSetRequest createProvideAndRegisterDocumentSetRequest(
			SubmitObjectsRequest submitObjectRequest, Document document) {
		final ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		request.setSubmitObjectsRequest(submitObjectRequest);
		if (document != null) {
			request.getDocument().add(document);
		}
		return request;
	}

	/**
	 * Creates the retrieve document set request.
	 *
	 * @param docRequest
	 *            the doc request
	 * @return the retrieve document set request
	 */
	RetrieveDocumentSetRequest createRetrieveDocumentSetRequest(
			DocumentRequest docRequest) {
		final RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
		request.getDocumentRequest().add(docRequest);
		return request;
	}

	/**
	 * Creates the retrieve document set request.
	 *
	 * @param docRequest
	 *            the doc request
	 * @return the retrieve document set request
	 */
	RetrieveDocumentSetRequest createRetrieveDocumentSetRequest(
			List<DocumentRequest> docRequest) {
		final RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
		request.getDocumentRequest().addAll(docRequest);
		return request;
	}

	/**
	 * Creates the retrieve document set request.
	 *
	 * @param documentUniqueId
	 *            the document unique id
	 * @param repositoryId
	 *            the repository id
	 * @return the retrieve document set request
	 */
	RetrieveDocumentSetRequest createRetrieveDocumentSetRequest(
			String documentUniqueId, String repositoryId) {
		final RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
		final DocumentRequest documentRequest = new DocumentRequest();

		documentRequest.setDocumentUniqueId(documentUniqueId);
		documentRequest.setRepositoryUniqueId(repositoryId);
		request.getDocumentRequest().add(documentRequest);
		return request;
	}

	/**
	 * Creates the xdsb metadata generator.
	 *
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @return the xdsb metadata generator impl
	 */
	XdsbMetadataGeneratorImpl createXdsbMetadataGenerator(
			XdsbDocumentType documentTypeForXdsbMetadata) {
		return new XdsbMetadataGeneratorImpl(new UniqueOidProviderImpl(),
				documentTypeForXdsbMetadata, this.marshaller, xmlTransformer);
	}

	/**
	 * Generate metadata.
	 *
	 * @param documentXmlString
	 *            the document xml string
	 * @param homeCommunityId
	 *            the home community id
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @param patientUniqueId
	 *            the patient unique id
	 * @param entryUUID
	 *            the entry uuid
	 * @return the string
	 */
	@Deprecated
	String generateMetadata(String documentXmlString, String homeCommunityId,
			XdsbDocumentType documentTypeForXdsbMetadata,
			String patientUniqueId, String entryUUID) {
		final XdsbMetadataGenerator xdsbMetadataGenerator = createXdsbMetadataGenerator(documentTypeForXdsbMetadata);
		final String metadata = xdsbMetadataGenerator.generateMetadataXml(
				documentXmlString, homeCommunityId, patientUniqueId, entryUUID);
		return metadata;
	}

	/**
	 * Generate metadata.
	 *
	 * @param documentXmlString
	 *            the document xml string
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @param params
	 *            the params
	 * @return the string
	 */
	String generateMetadata(String documentXmlString,
			XdsbDocumentType documentTypeForXdsbMetadata, Params params) {
		final XdsbMetadataGenerator xdsbMetadataGenerator = createXdsbMetadataGenerator(documentTypeForXdsbMetadata);
		final String metadata = xdsbMetadataGenerator.generateMetadataXml(
				documentXmlString, params);
		return metadata;
	}

	/**
	 * Gets the param value.
	 *
	 * @param params
	 *            the params
	 * @param paramKey
	 *            the param key
	 * @return the param value
	 */
	private String getParamValue(Params params,
			XdsbMetadataGeneratorParams paramKey) {
		return params.get(paramKey);
	}
}
