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
package gov.samhsa.acs.brms.ws;

import gov.samhsa.consent2share.contract.ruleexecutionservice.RuleExecutionServicePortType;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsRequest;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import javax.jws.WebService;

/**
 * The Class RuleExecutionServiceWsImpl.
 */
@WebService(targetNamespace = "http://www.samhsa.gov/consent2share/contract/RuleExecutionService", portName = "RuleExecutionServicePort", serviceName = "RuleExecutionService", endpointInterface = "gov.samhsa.consent2share.contract.ruleexecutionservice.RuleExecutionServicePortType")
public class RuleExecutionServiceWsImpl implements RuleExecutionServicePortType {

	/** The rule execution service. */
	private gov.samhsa.acs.brms.RuleExecutionService ruleExecutionService;

	/**
	 * Instantiates a new rule execution service ws impl.
	 */
	public RuleExecutionServiceWsImpl() {
	}

	/**
	 * Instantiates a new rule execution service ws impl.
	 * 
	 * @param ruleExecutionService
	 *            the rule execution service
	 */
	public RuleExecutionServiceWsImpl(
			gov.samhsa.acs.brms.RuleExecutionService ruleExecutionService) {
		this.ruleExecutionService = ruleExecutionService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.contract.ruleexecutionservice.
	 * RuleExecutionServicePortType
	 * #assertAndExecuteClinicalFacts(gov.samhsa.consent2share
	 * .schema.ruleexecutionservice.AssertAndExecuteClinicalFactsRequest)
	 */
	@Override
	public AssertAndExecuteClinicalFactsResponse assertAndExecuteClinicalFacts(
			AssertAndExecuteClinicalFactsRequest parameters) {
		AssertAndExecuteClinicalFactsResponse response = new AssertAndExecuteClinicalFactsResponse();

		response = ruleExecutionService
				.assertAndExecuteClinicalFacts(parameters
						.getClinicalFactXmlString());
		return response;
	}
}
