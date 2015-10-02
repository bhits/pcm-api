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
package gov.samhsa.acs.xdsb.common;

import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryErrorList;

/**
 * A factory for creating XdsbError objects.
 */
public class XdsbErrorFactory {

	/** The registry error list setter. */
	private RegistryErrorListSetter registryErrorListSetter;

	/**
	 * Instantiates a new xdsb error factory.
	 */
	public XdsbErrorFactory() {
	}

	/**
	 * Instantiates a new xdsb error factory.
	 * 
	 * @param registryErrorListSetter
	 *            the registry error list setter
	 */
	public XdsbErrorFactory(RegistryErrorListSetter registryErrorListSetter) {
		super();
		this.registryErrorListSetter = registryErrorListSetter;
	}

	/**
	 * Sets the retrieve document set response registry error list.
	 * 
	 * @param response
	 *            the retrieve document set response that will have the new
	 *            registry error list with error message
	 * @param numRemoved
	 *            the number of removed documents after filtering by author
	 * @param patientId
	 *            the patient id
	 * @param authorNPI
	 *            the author npi
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse setRetrieveDocumentSetResponseRegistryErrorListFilteredByPatientAndAuthor(
			RetrieveDocumentSetResponse response, int numRemoved,
			String patientId, String authorNPI) {
		StringBuilder builder = new StringBuilder();
		builder.append(numRemoved);
		builder.append(" document(s) is/are not authored by ");
		builder.append(authorNPI);
		builder.append(" or does/do not belong to the patient ");
		builder.append(patientId);
		builder.append(" and removed from the response.");
		String codeContext = builder.toString();
		String errorCode = "XDSRepositoryError";
		boolean isPartial = response.getDocumentResponse().size() > 0;
		setErrorRegistryResponse(codeContext, errorCode, isPartial, response);
		return response;
	}

	/**
	 * Error retrieve document set response multiple repository id.
	 * 
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseMultipleRepositoryId() {
		String codeContext = "All repository ids in RetrieveDocumentSetRequest need to be same.";
		String errorCode = "XDSRepositoryError";
		boolean isPartial = false;

		return createRetrieveDocumentSetResponseError(codeContext, errorCode,
				isPartial);
	}

	/**
	 * Error retrieve document set response construct by error message.
	 * 
	 * @param errorMessage
	 *            the error message
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseConstructByErrorMessage(
			String errorMessage) {
		String codeContext = errorMessage;
		String errorCode = "XDSRepositoryError";
		boolean isPartial = false;

		return createRetrieveDocumentSetResponseError(codeContext, errorCode,
				isPartial);
	}

	/**
	 * Error retrieve document set internal server error.
	 * 
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetInternalServerError() {
		String codeContext = "Internal server error in Policy Enforcement Point.";
		String errorCode = "XDSRepositoryError";
		boolean isPartial = false;

		return createRetrieveDocumentSetResponseError(codeContext, errorCode,
				isPartial);
	}

	/**
	 * No clinical document found.
	 * 
	 * @param patientId
	 *            the patient id
	 * @param authorNPI
	 *            the author npi
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse noClinicalDocumentFound(
			String patientId, String authorNPI) {
		StringBuilder builder = new StringBuilder();
		builder.append("Requested Document is not a valid C32(Clinic Personal Health Record Extract) For the Patient : ");
		builder.append(patientId);
		builder.append(" authored by : ");
		builder.append(authorNPI);
		String codeContext = builder.toString();
		String errorCode = "XDSRepositoryError";
		boolean isPartial = true;
		return createRetrieveDocumentSetResponseError(codeContext, errorCode,
				isPartial);
	}

	/**
	 * Error retrieve document set response schema validation.
	 * 
	 * @param response
	 *            the response
	 * @param errorMap
	 *            the error map
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseSchemaValidation(
			RetrieveDocumentSetResponse response, Map<String, String> errorMap) {
		StringBuilder builder = new StringBuilder();
		builder.append("Document validation error(s) occurred in Policy Enforcement Point for document(s): ");
		builder.append(errorMap.keySet());
		builder.append(". Please contact to system administrator if this error persists.");
		String codeContext = builder.toString();
		String errorCode = "XDSRepositoryError";
		boolean isPartial = response.getDocumentResponse().size() > 0;

		setErrorRegistryResponse(codeContext, errorCode, isPartial, response);

		return response;
	}

	/**
	 * Error retrieve document set response not exists or accessible.
	 * 
	 * @param input
	 *            the input
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseNotExistsOrAccessible(
			RetrieveDocumentSetRequest input) {
		List<String> list = new LinkedList<String>();
		for (DocumentRequest req : input.getDocumentRequest()) {
			StringBuilder builder = new StringBuilder();
			builder.append(req.getDocumentUniqueId());
			builder.append(":");
			builder.append(req.getRepositoryUniqueId());
			list.add(builder.toString());
		}
		StringBuilder builder = new StringBuilder();
		builder.append("The document(s) ");
		builder.append(list.toString());
		builder.append(" does/do not exist or access denied by Policy Enforcement Point.");
		String codeContext = builder.toString();
		String errorCode = "XDSRepositoryError";
		boolean isPartial = false;

		return createRetrieveDocumentSetResponseError(codeContext, errorCode,
				isPartial);
	}

	/**
	 * Error retrieve document set response repository not available.
	 * 
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseRepositoryNotAvailable() {
		String codeContext = "Policy Enforcement Point is unable to access the XDS.b Repository.";
		String errorCode = "XDSRepositoryError";
		boolean isPartial = false;

		return createRetrieveDocumentSetResponseError(codeContext, errorCode,
				isPartial);
	}

	/**
	 * Error retrieve document set response internal pep error.
	 * 
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseInternalPEPError() {
		String codeContext = "An internal error occured in Policy Enforcement Point.";
		String errorCode = "XDSRepositoryError";
		boolean isPartial = false;

		return createRetrieveDocumentSetResponseError(codeContext, errorCode,
				isPartial);
	}

	/**
	 * Error retrieve document set response access denied by pdp.
	 * 
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseAccessDeniedByPDP() {
		String codeContext = "The access to patient documents is denied by Policy Decision Point.";
		String errorCode = "XDSRepositoryError";
		boolean isPartial = false;

		return createRetrieveDocumentSetResponseError(codeContext, errorCode,
				isPartial);
	}

	/**
	 * Error retrieve document set response no consents found.
	 * 
	 * @param patientUniqueId
	 *            the patient unique id
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseNoConsentsFound(
			String patientUniqueId) {
		StringBuilder builder = new StringBuilder();
		builder.append("No consents found for patient ");
		builder.append(patientUniqueId);
		builder.append(".");
		String codeContext = builder.toString();
		String errorCode = "XDSRepositoryError";
		boolean isPartial = false;

		return createRetrieveDocumentSetResponseError(codeContext, errorCode,
				isPartial);
	}

	/**
	 * Error adhoc query response construct by error message.
	 * 
	 * @param errorMessage
	 *            the error message
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseConstructByErrorMessage(
			String errorMessage) {
		String codeContext = errorMessage;
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response registry not available.
	 * 
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseRegistryNotAvailable() {
		String codeContext = "Policy Enforcement Point is unable to access the XDS.b Registry.";
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response access denied by pdp.
	 * 
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseAccessDeniedByPDP() {
		String codeContext = "The access to patient documents is denied by Policy Decision Point.";
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response internal server error.
	 * 
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseInternalServerError() {
		String codeContext = "Internal server error in Policy Enforcement Point.";
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response no documents found.
	 * 
	 * @param patientUniqueId
	 *            the patient unique id
	 * @param intermediarySubjectNPI
	 *            the intermediary subject npi
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseNoDocumentsFound(
			String patientUniqueId, String intermediarySubjectNPI) {
		StringBuilder builder = new StringBuilder();
		builder.append("No documents found for patient ");
		builder.append(patientUniqueId);
		builder.append(" authored by ");
		builder.append(intermediarySubjectNPI);
		builder.append(".");
		String codeContext = builder.toString();
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response missing parameters.
	 * 
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseMissingParameters() {
		String codeContext = "$XDSDocumentEntryPatientId, $XDSDocumentEntryStatus and $XDSDocumentEntryFormatCode are required by Policy Enforcement Point.";
		String errorCode = "XDSStoredQueryMissingParam";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Creates a new XdsbError object.
	 * 
	 * @param codeContext
	 *            the code context
	 * @param errorCode
	 *            the error code
	 * @return the adhoc query response
	 */
	private AdhocQueryResponse createAdhocQueryResponseError(
			String codeContext, String errorCode) {
		AdhocQueryResponse errorResponse = new AdhocQueryResponse();
		errorResponse
				.setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure");

		RegistryErrorList registryErrorList = createRegistryErrorList(
				codeContext, errorCode);

		errorResponse.setRegistryErrorList(registryErrorList);
		return errorResponse;
	}

	/**
	 * Creates a new XdsbError object.
	 * 
	 * @param codeContext
	 *            the code context
	 * @param errorCode
	 *            the error code
	 * @param isPartial
	 *            the is partial
	 * @return the retrieve document set response
	 */
	private RetrieveDocumentSetResponse createRetrieveDocumentSetResponseError(
			String codeContext, String errorCode, boolean isPartial) {
		RetrieveDocumentSetResponse errorRetrieveDocumentSetResponse = new RetrieveDocumentSetResponse();
		setErrorRegistryResponse(codeContext, errorCode, isPartial,
				errorRetrieveDocumentSetResponse);
		return errorRetrieveDocumentSetResponse;
	}

	/**
	 * Sets the error registry response.
	 * 
	 * @param codeContext
	 *            the code context
	 * @param errorCode
	 *            the error code
	 * @param isPartial
	 *            the is partial
	 * @param response
	 *            the response
	 */
	private void setErrorRegistryResponse(String codeContext, String errorCode,
			boolean isPartial, RetrieveDocumentSetResponse response) {
		RegistryErrorList registryErrorList = createRegistryErrorList(
				codeContext, errorCode);
		this.registryErrorListSetter.setRegistryErrorList(response,
				registryErrorList, isPartial);
	}

	/**
	 * Creates a new XdsbError object.
	 * 
	 * @param codeContext
	 *            the code context
	 * @param errorCode
	 *            the error code
	 * @return the registry error list
	 */
	private RegistryErrorList createRegistryErrorList(String codeContext,
			String errorCode) {
		RegistryErrorList registryErrorList = new RegistryErrorList();

		RegistryError error = new RegistryError();
		error.setCodeContext(codeContext);
		error.setErrorCode(errorCode);
		error.setLocation("");
		error.setSeverity("urn:oasis:names:tc:ebxml-regrep:ErrorSeverityType:Error");
		registryErrorList.getRegistryError().add(error);
		return registryErrorList;
	}

}
