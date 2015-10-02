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

import gov.samhsa.acs.common.dto.PdpRequestResponse;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;

import java.util.List;

import org.herasaf.xacml.core.api.PDP;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;

import ch.qos.logback.audit.AuditException;

/**
 * The Interface PolicyDecisionPoint.
 */
public interface PolicyDecisionPoint {

	/**
	 * Evaluate policy for trying.
	 *
	 * @param xacmlPolicy
	 *            the xacml policy
	 * @param purposeOfUse
	 *            the purpose of use
	 * @return the pdp request response
	 */
	public PdpRequestResponse evaluatePolicyForTrying(String xacmlPolicy,
			String purposeOfUse);

	/**
	 * Evaluate the request using the simplePDP and retrieve the response from
	 * the PDP.
	 *
	 * @param pdp
	 *            the pdp
	 * @param request
	 *            the request
	 * @param policies
	 *            the policies
	 * @param xacmlRequest
	 *            the xacml request
	 * @return the xacml response
	 * @throws AuditException
	 *             the audit exception
	 */
	@Deprecated
	public XacmlResponse evaluateRequest(PDP pdp, RequestType request,
			List<Evaluatable> policies, XacmlRequest xacmlRequest)
			throws AuditException;

	/**
	 * Evaluate the request using the simplePDP and retrieve the response from
	 * the PDP.
	 *
	 * @param pdp
	 *            the pdp
	 * @param request
	 *            the request
	 * @param xacmlRequest
	 *            the xacml request
	 * @return the xacml response
	 * @throws AuditException
	 *             the audit exception
	 * @throws NoPolicyFoundException
	 *             the no policy found exception
	 * @throws PolicyProviderException
	 *             the policy provider exception
	 */
	@Deprecated
	public XacmlResponse evaluateRequest(PDP pdp, RequestType request,
			XacmlRequest xacmlRequest) throws AuditException,
			NoPolicyFoundException, PolicyProviderException;

	/**
	 * Evaluate the request using the simplePDP and retrieve the response from
	 * the PDP.
	 *
	 * @param request
	 *            the request
	 * @param policies
	 *            the policies
	 * @param xacmlRequest
	 *            the xacml request
	 * @return the xacml response
	 * @throws AuditException
	 *             the audit exception
	 */
	@Deprecated
	public XacmlResponse evaluateRequest(RequestType request,
			List<Evaluatable> policies, XacmlRequest xacmlRequest)
			throws AuditException;

	/**
	 * Evaluate the request using the simplePDP and retrieve the response from
	 * the PDP.
	 *
	 * @param request
	 *            the request
	 * @param xacmlRequest
	 *            the xacml request
	 * @return the xacml response
	 * @throws AuditException
	 *             the audit exception
	 * @throws NoPolicyFoundException
	 *             the no policy found exception
	 * @throws PolicyProviderException
	 *             the policy provider exception
	 */
	public XacmlResponse evaluateRequest(RequestType request,
			XacmlRequest xacmlRequest) throws AuditException,
			NoPolicyFoundException, PolicyProviderException;

	/**
	 * Evaluate the request using the simplePDP and retrieve the response from
	 * the PDP.
	 *
	 * @param xacmlRequest
	 *            the xacml request
	 * @return the xacml response
	 * @throws AuditException
	 *             the audit exception
	 * @throws NoPolicyFoundException
	 *             the no policy found exception
	 * @throws PolicyProviderException
	 *             the policy provider exception
	 */
	public XacmlResponse evaluateRequest(XacmlRequest xacmlRequest)
			throws AuditException, NoPolicyFoundException,
			PolicyProviderException;
}
