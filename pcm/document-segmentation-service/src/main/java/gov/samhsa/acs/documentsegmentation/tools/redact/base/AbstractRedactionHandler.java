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

import gov.samhsa.acs.brms.domain.ClinicalFact;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.tool.DocumentAccessor;

import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class AbstractRedactionHandler.
 */
public abstract class AbstractRedactionHandler {

	/** The document accessor. */
	protected DocumentAccessor documentAccessor;

	/**
	 * Instantiates a new abstract callback.
	 *
	 * @param documentAccessor
	 *            the document accessor
	 */
	public AbstractRedactionHandler(DocumentAccessor documentAccessor) {
		super();
		this.documentAccessor = documentAccessor;
	}

	/**
	 * Adds the nodes to list.
	 *
	 * @param xmlDocument
	 *            the xml document
	 * @param listOfNodes
	 *            the list of nodes
	 * @param redactSectionCodesAndGeneratedEntryIds
	 *            the redact section codes and generated entry ids
	 * @param xPathExpr
	 *            the x path expr
	 * @param values
	 *            the values
	 * @return the int
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	protected final int addNodesToList(Document xmlDocument,
			List<Node> listOfNodes,
			Set<String> redactSectionCodesAndGeneratedEntryIds,
			String xPathExpr, String... values) throws XPathExpressionException {
		int added = 0;
		NodeList nodeList = documentAccessor
				.getNodeList(xmlDocument, xPathExpr, values);
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				// add section or generated entry code to redactList, so they
				// can be ignored during tagging
				if (values != null && values.length > 0) {
					redactSectionCodesAndGeneratedEntryIds.add(values[0]);
				}
				Node node = nodeList.item(i);
				listOfNodes.add(node);
				if(Node.ELEMENT_NODE == node.getNodeType()){
					Element element = (Element) node;
					element.setAttribute("redact", "redact");
				}
				added++;
			}
		}
		return added;
	}

	/**
	 * Contains any.
	 *
	 * @param obligations
	 *            the obligations
	 * @param categories
	 *            the categories
	 * @return the string
	 */
	protected final String containsAny(List<String> obligations,
			Set<String> categories) {
		if (obligations != null && categories != null) {
			for (String category : categories) {
				if (obligations.contains(category)) {
					return category;
				}
			}
		}
		return null;
	}

	/**
	 * Find matching category.
	 *
	 * @param xacmlResult
	 *            the xacml result
	 * @param fact
	 *            the fact
	 * @return the string
	 */
	protected final String findMatchingCategory(XacmlResult xacmlResult,
			ClinicalFact fact) {
		return containsAny(xacmlResult.getPdpObligations(),
				fact.getValueSetCategories());
	}
}
