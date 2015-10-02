/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.contexthandler;

import gov.samhsa.acs.common.dto.PdpRequestResponse;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;

import org.springframework.util.Assert;

import ch.qos.logback.audit.AuditException;

/**
 * The Class ContextHandlerImpl.
 */
public class ContextHandlerImpl implements ContextHandler {

	/** The policy desicion point. */
	private final PolicyDecisionPoint policyDesicionPoint;

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/**
	 * Instantiates a new context handler impl.
	 *
	 * @param policyDesicionPoint
	 *            the policy desicion point
	 */
	public ContextHandlerImpl(PolicyDecisionPoint policyDesicionPoint) {
		super();
		this.policyDesicionPoint = policyDesicionPoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.contexthandler.ContextHandler#enforcePolicy(
	 * gov.samhsa.acs.common.dto.XacmlRequest)
	 */
	@Override
	public XacmlResponse enforcePolicy(XacmlRequest xacmlRequest)
			throws AuditException, NoPolicyFoundException,
			PolicyProviderException {
		logger.debug("policyDesicionPoint.evaluateRequest(xacmlRequest) is invoked");
		return policyDesicionPoint.evaluateRequest(xacmlRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.ContextHandler#makeDecisionForTryingPolicy
	 * (java.lang.String)
	 */
	@Override
	public PdpRequestResponse makeDecisionForTryingPolicy(String xacmlPolicy,
			String purposeOfUse) {

		logger.debug("makeDecisionForTryingPolicy(xacmlPolicy) is invoked");
		Assert.hasText(xacmlPolicy, "Xaml policy is not set");
		return policyDesicionPoint.evaluatePolicyForTrying(xacmlPolicy,
				purposeOfUse);
	}
}
