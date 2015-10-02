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
package gov.samhsa.acs.documentsegmentation.tools.redact.base;

import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.tool.DocumentAccessor;

import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class AbstractObligationLevelRedactionHandler.
 */
public abstract class AbstractObligationLevelRedactionHandler extends AbstractRedactionHandler {

	/**
	 * Instantiates a new abstract obligation level callback.
	 *
	 * @param documentAccessor
	 *            the document accessor
	 */
	public AbstractObligationLevelRedactionHandler(DocumentAccessor documentAccessor) {
		super(documentAccessor);
	}

	/**
	 * Execute.
	 *
	 * @param xmlDocument
	 *            the xml document
	 * @param xacmlResult
	 *            the xacml result
	 * @param factModel
	 *            the fact model
	 * @param factModelDocument
	 *            the fact model document
	 * @param ruleExecutionContainer
	 *            the rule execution container
	 * @param listOfNodes
	 *            the list of nodes
	 * @param redactSectionCodesAndGeneratedEntryIds
	 *            the redact section codes and generated entry ids
	 * @param redactSectionCodes
	 *            the redact section codes
	 * @param sectionLoincCode
	 *            the section loinc code
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	public abstract void execute(Document xmlDocument, XacmlResult xacmlResult,
			FactModel factModel, Document factModelDocument,
			RuleExecutionContainer ruleExecutionContainer,
			List<Node> listOfNodes,
			Set<String> redactSectionCodesAndGeneratedEntryIds,
			Set<String> redactSectionCodes, String sectionLoincCode)
			throws XPathExpressionException;
}
