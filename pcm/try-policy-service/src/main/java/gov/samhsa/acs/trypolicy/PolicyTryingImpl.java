package gov.samhsa.acs.trypolicy;

import gov.samhsa.acs.brms.domain.SubjectPurposeOfUse;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.dto.PdpRequestResponse;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.contexthandler.ContextHandler;
import gov.samhsa.acs.dss.ws.schema.DSSRequest;
import gov.samhsa.acs.dss.ws.schema.DSSResponse;
import gov.samhsa.acs.dss.wsclient.DSSWebServiceClient;
import gov.samhsa.acs.trypolicy.exception.TryPolicyException;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyTryingImpl implements PolicyTrying {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final boolean AUDITED = false;
	public static final boolean AUDIT_FAILURE_BY_PASS = true;

	private final ContextHandler contextHandler;
	private final SimpleMarshaller marshaller;
	private final DSSWebServiceClient documentSegmentation;

	public PolicyTryingImpl(ContextHandler contextHandler,
			SimpleMarshaller marshaller,
			DSSWebServiceClient documentSegmentation) {
		super();
		this.contextHandler = contextHandler;
		this.marshaller = marshaller;
		this.documentSegmentation = documentSegmentation;
	}

	@Override
	public String tryPolicy(String c32Xml, String xacmlPolicy,
			String purposeOfUse) throws TryPolicyException {

		final String messageId = UUID.randomUUID().toString();
		DSSResponse segmentDocumentResponse;
		try {
			final PdpRequestResponse pdpRequestResponse = contextHandler
					.makeDecisionForTryingPolicy(xacmlPolicy, purposeOfUse);
			final XacmlResponse xacmlResponse = pdpRequestResponse
					.getXacmlResponse();

			final XacmlRequest xacmlRequest = pdpRequestResponse
					.getXacmlRequest();

			final XacmlResult xacmlResult = createXacmlResult(xacmlRequest,
					xacmlResponse);

			final String enforcementPolicies = marshaller.marshal(xacmlResult);

			final DSSRequest request = new DSSRequest();
			request.setDocumentXml(c32Xml);
			request.setEnforcementPoliciesXml(enforcementPolicies);
			request.setAudited(AUDITED);
			request.setAuditFailureByPass(AUDIT_FAILURE_BY_PASS);
			request.setEnableTryPolicyResponse(Boolean.TRUE);

			segmentDocumentResponse = documentSegmentation
					.segmentDocument(request);
		} catch (final Throwable t) {
			logger.error(messageId, "Error in tryPolicy", t);
			throw new TryPolicyException(t);
		}

		return segmentDocumentResponse.getTryPolicyDocumentXml();
	}

	private XacmlResult createXacmlResult(XacmlRequest xacmlRequest,
			XacmlResponse xacmlResponse) {
		final XacmlResult xacmlResult = new XacmlResult();
		xacmlResult.setHomeCommunityId(xacmlRequest.getHomeCommunityId());
		xacmlResult.setMessageId(xacmlRequest.getMessageId());
		xacmlResult.setPdpDecision(xacmlResponse.getPdpDecision());
		xacmlResult.setPdpObligations(xacmlResponse.getPdpObligation());
		xacmlResult.setSubjectPurposeOfUse(SubjectPurposeOfUse
				.fromAbbreviation(xacmlRequest.getPurposeOfUse()));
		xacmlResult.setPatientId(xacmlRequest.getPatientId());
		return xacmlResult;
	}
}
