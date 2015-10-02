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
package gov.samhsa.acs.documentsegmentation.tools.redact.impl.clinicalfactlevel;

import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gov.samhsa.acs.brms.domain.ClinicalFact;
import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.documentsegmentation.tools.redact.base.AbstractClinicalFactLevelRedactionHandler;

/**
 * The Class HumanReadableTableRowById.
 */
public class HumanReadableTableRowById extends
		AbstractClinicalFactLevelRedactionHandler {

	/** The Constant XPATH_HUMAN_READABLE_TABLE_ROW_BY_REFERENCE. */
	public static final String XPATH_HUMAN_READABLE_TABLE_ROW_BY_REFERENCE = "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component/hl7:section[child::hl7:entry[child::hl7:generatedEntryId/text()='%1']]/hl7:text/hl7:table/hl7:tbody/hl7:tr[descendant-or-self::node()[@ID='%2']]";

	/**
	 * Instantiates a new document node collector for human readable table row
	 * by id.
	 *
	 * @param documentAccessor
	 *            the document accessor
	 */
	public HumanReadableTableRowById(
			DocumentAccessor documentAccessor) {
		super(documentAccessor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.tools.redact.base.
	 * AbstractClinicalFactLevelCallback#execute(org.w3c.dom.Document,
	 * gov.samhsa.acs.brms.domain.XacmlResult,
	 * gov.samhsa.acs.brms.domain.FactModel, org.w3c.dom.Document,
	 * gov.samhsa.acs.brms.domain.ClinicalFact,
	 * gov.samhsa.acs.brms.domain.RuleExecutionContainer, java.util.List,
	 * java.util.Set, java.util.Set)
	 */
	@Override
	public void execute(Document xmlDocument, XacmlResult xacmlResult,
			FactModel factModel, Document factModelDocument, ClinicalFact fact,
			RuleExecutionContainer ruleExecutionContainer,
			List<Node> listOfNodes,
			Set<String> redactSectionCodesAndGeneratedEntryIds,
			Set<String> redactSensitiveCategoryCodes)
			throws XPathExpressionException {
		String foundCategory = findMatchingCategory(xacmlResult, fact);
		if (foundCategory != null) {
			NodeList references = getEntryReferenceIdNodeList(
					factModelDocument, fact);
			for (int i = 0; i < references.getLength(); i++) {
				// Collect the table rows
				addNodesToList(xmlDocument, listOfNodes,
						redactSectionCodesAndGeneratedEntryIds,
						XPATH_HUMAN_READABLE_TABLE_ROW_BY_REFERENCE,
						fact.getEntry(), references.item(i).getNodeValue());
			}
			redactSensitiveCategoryCodes.add(foundCategory);
		}

	}

}
