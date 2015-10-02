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
package gov.samhsa.acs.contexthandler;

import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;
import gov.samhsa.acs.xdsb.common.AdhocQueryResponseParser;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.herasaf.xacml.core.SyntaxException;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;

/**
 * The Class XdsbPolicyProvider.
 */
public class XdsbPolicyProvider implements PolicyProvider {

	/** The Constant URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES. */
	public static final String URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides";

	/** The urn policy combining algorithm. */
	private String urnPolicyCombiningAlgorithm;

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The xdsb registry. */
	private final XdsbRegistryAdapter xdsbRegistry;

	/** The xdsb repository. */
	private final XdsbRepositoryAdapter xdsbRepository;

	/** The policy list filter. */
	private final XacmlPolicyListFilter policyListFilter;

	/** The adhoc query response parser. */
	private final AdhocQueryResponseParser adhocQueryResponseParser;

	/**
	 * Instantiates a new policy decision point impl data xdsb.
	 *
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param xdsbRepository
	 *            the xdsb repository
	 * @param policyListFilter
	 *            the policy list filter
	 * @param adhocQueryResponseParser
	 *            the adhoc query response parser
	 */
	public XdsbPolicyProvider(XdsbRegistryAdapter xdsbRegistry,
			XdsbRepositoryAdapter xdsbRepository,
			XacmlPolicyListFilter policyListFilter,
			AdhocQueryResponseParser adhocQueryResponseParser) {
		this.xdsbRegistry = xdsbRegistry;
		this.xdsbRepository = xdsbRepository;
		this.policyListFilter = policyListFilter;
		this.adhocQueryResponseParser = adhocQueryResponseParser;
		this.urnPolicyCombiningAlgorithm = URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES;
	}

	/**
	 * Instantiates a new policy decision point impl data xdsb.
	 *
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param xdsbRepository
	 *            the xdsb repository
	 * @param policyListFilter
	 *            the policy list filter
	 * @param adhocQueryResponseParser
	 *            the adhoc query response parser
	 * @param urnPolicyCombiningAlgorithm
	 *            the urn policy combining algorithm
	 */
	public XdsbPolicyProvider(XdsbRegistryAdapter xdsbRegistry,
			XdsbRepositoryAdapter xdsbRepository,
			XacmlPolicyListFilter policyListFilter,
			AdhocQueryResponseParser adhocQueryResponseParser,
			String urnPolicyCombiningAlgorithm) {
		this.xdsbRegistry = xdsbRegistry;
		this.xdsbRepository = xdsbRepository;
		this.policyListFilter = policyListFilter;
		this.adhocQueryResponseParser = adhocQueryResponseParser;
		if (urnPolicyCombiningAlgorithm == null
				|| "".equals(urnPolicyCombiningAlgorithm)) {
			this.urnPolicyCombiningAlgorithm = URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES;
		} else {
			this.urnPolicyCombiningAlgorithm = urnPolicyCombiningAlgorithm;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPointImplData#getPolicies
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Evaluatable> getPolicies(XacmlRequest xacmlRequest)
			throws NoPolicyFoundException, PolicyProviderException {
		final String patientUniqueId = xacmlRequest.getPatientUniqueId();
		final String recipientSubjectNPI = xacmlRequest
				.getRecipientSubjectNPI();
		final String intermediarySubjectNPI = xacmlRequest
				.getIntermediarySubjectNPI();
		final List<Evaluatable> policies = new LinkedList<Evaluatable>();
		final List<String> policiesString = new LinkedList<String>();
		try {
			// Retrieve policy documents
			final AdhocQueryResponse response = xdsbRegistry.findDocuments(
					patientUniqueId, null, XdsbDocumentType.PRIVACY_CONSENT,
					true);

			// Extract doc.request from query response
			final RetrieveDocumentSetRequest retrieveDocumentSetRequest = adhocQueryResponseParser
					.parseXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(response);

			// If no policies at all, throw NoPolicyFoundException to be caught
			// at PolicyEnforcementPointImpl
			final StringBuilder noConsentsFoundErrorStringBuilder = new StringBuilder();
			noConsentsFoundErrorStringBuilder
			.append("No consents found for patient ");
			noConsentsFoundErrorStringBuilder.append(patientUniqueId);
			noConsentsFoundErrorStringBuilder.append(".");
			final String errMsg = noConsentsFoundErrorStringBuilder.toString();
			if (retrieveDocumentSetRequest.getDocumentRequest().size() <= 0) {
				logger.error(errMsg);
				throw new NoPolicyFoundException(errMsg);
			}

			// Retrieve all policies
			final RetrieveDocumentSetResponse retrieveDocumentSetResponse = xdsbRepository
					.retrieveDocumentSet(retrieveDocumentSetRequest);

			// Retrieve deprecated documentUniqueIds
			final List<String> deprecatedDocumentUniqueIds = this.xdsbRegistry
					.findDeprecatedDocumentUniqueIds(patientUniqueId,
							patientUniqueId);

			// Add policy documents to a string list (if they are not
			// deprecated)
			for (final DocumentResponse docResponse : retrieveDocumentSetResponse
					.getDocumentResponse()) {
				if (!deprecatedDocumentUniqueIds.contains(docResponse
						.getDocumentUniqueId())) {
					String docString = new String(docResponse.getDocument());
					docString = docString.replace(
							"<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
					policiesString.add(docString);
				}
			}

			// Filter the policiesString List by recipientSubjectNPI and
			// intermediarySubjectNPI to remove the unrelated policies (the
			// policies about other providers)
			policyListFilter.filterByNPI(policiesString, recipientSubjectNPI,
					intermediarySubjectNPI);

			// If no policies left in the list (no related policies), throw
			// NoPolicyFoundException to be caught at
			// PolicyEnforcementPointImpl
			if (policiesString.size() <= 0) {
				throw new NoPolicyFoundException(
						noConsentsFoundErrorStringBuilder.toString());
			}

			// Wrap policies in a policy set
			final String policySet = makePolicySet(policiesString);

			// Unmarshall policy set as an Evaluatable and add to policy list
			final Evaluatable policy = unmarshal(new ByteArrayInputStream(
					policySet.getBytes()));
			policies.add(policy);
		} catch (final NoPolicyFoundException e) {
			// Log the exception, but throw it again to be caught at
			// PolicyEnforcementPointImpl
			logger.info(e.getMessage());
			throw e;
		} catch (final Throwable t) {
			logger.error(t.getMessage(), t);
			throw new PolicyProviderException(
					"Consent files cannot be queried/retrieved from XDS.b");
		}
		return policies;
	}

	/**
	 * Unmarshal.
	 *
	 * @param inputStream
	 *            the input stream
	 * @return the evaluatable
	 * @throws SyntaxException
	 *             the syntax exception
	 */
	Evaluatable unmarshal(InputStream inputStream) throws SyntaxException {
		return PolicyMarshaller.unmarshal(inputStream);
	}

	/**
	 * Gets the policy set footer.
	 *
	 * @return the policy set footer
	 */
	private String getPolicySetFooter() {
		return "</PolicySet>";
	}

	/**
	 * Gets the policy set header.
	 *
	 * @return the policy set header
	 */
	private String getPolicySetHeader() {
		final StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><PolicySet xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os http://docs.oasis-open.org/xacml/access_control-xacml-2.0-policy-schema-os.xsd\" PolicySetId=\"urn:oasis:names:tc:xacml:2.0:example:policysetid:1\" PolicyCombiningAlgId=\"");
		builder.append(this.urnPolicyCombiningAlgorithm);
		builder.append("\"><Description/><Target/>");
		return builder.toString();
	}

	/**
	 * Make policy set.
	 *
	 * @param policies
	 *            the policies
	 * @return the string
	 */
	private String makePolicySet(List<String> policies) {
		final StringBuilder builder = new StringBuilder();
		builder.append(getPolicySetHeader());
		for (final String policy : policies) {
			builder.append(policy);
		}
		builder.append(getPolicySetFooter());
		return builder.toString();
	}
}
