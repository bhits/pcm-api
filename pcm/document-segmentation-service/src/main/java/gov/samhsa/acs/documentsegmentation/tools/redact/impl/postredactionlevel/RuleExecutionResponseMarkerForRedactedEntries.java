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
package gov.samhsa.acs.documentsegmentation.tools.redact.impl.postredactionlevel;

import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.RuleExecutionResponse;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.documentsegmentation.tools.redact.base.AbstractPostRedactionLevelRedactionHandler;

import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class RuleExecutionResponseMarkerForRedactedEntries.
 */
public class RuleExecutionResponseMarkerForRedactedEntries extends
		AbstractPostRedactionLevelRedactionHandler {

	/**
	 * Instantiates a new rule execution response marker for redacted entries.
	 *
	 * @param documentAccessor
	 *            the document accessor
	 */
	public RuleExecutionResponseMarkerForRedactedEntries(
			DocumentAccessor documentAccessor) {
		super(documentAccessor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.tools.redact.
	 * AbstractPostRedactionLevelCallback#execute(org.w3c.dom.Document,
	 * gov.samhsa.acs.brms.domain.XacmlResult,
	 * gov.samhsa.acs.brms.domain.FactModel, org.w3c.dom.Document,
	 * gov.samhsa.acs.brms.domain.RuleExecutionContainer, java.util.List,
	 * java.util.Set)
	 */
	@Override
	public void execute(Document xmlDocument, XacmlResult xacmlResult,
			FactModel factModel, Document factModelDocument,
			RuleExecutionContainer ruleExecutionContainer,
			List<Node> listOfNodes,
			Set<String> redactSectionCodesAndGeneratedEntryIds)
			throws XPathExpressionException {
		// Mark redacted sections and entries in ruleExecutionContainer,
		// so they can be ignored during tagging
		for (RuleExecutionResponse response : ruleExecutionContainer
				.getExecutionResponseList()) {
			if (redactSectionCodesAndGeneratedEntryIds.contains(response
					.getC32SectionLoincCode())
					|| redactSectionCodesAndGeneratedEntryIds.contains(response
							.getEntry())) {
				response.setItemAction(RuleExecutionResponse.ITEM_ACTION_REDACT);
			}
		}
	}
}
