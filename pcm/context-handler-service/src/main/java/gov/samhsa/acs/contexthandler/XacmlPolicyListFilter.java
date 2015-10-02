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

import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * The Class XacmlPolicyListFilter.
 */
public class XacmlPolicyListFilter {

	/** The Constant URN_XACML_1_INTERMEDIARY_SUBJECT. */
	public static final String URN_XACML_1_INTERMEDIARY_SUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject";

	/** The Constant URN_XACML_1_RECIPIENT_SUBJECT. */
	public static final String URN_XACML_1_RECIPIENT_SUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject";

	/** The Constant XPATH_CONDITION_ATTRIBUTE_VALUE. */
	public static final String XPATH_CONDITION_ATTRIBUTE_VALUE = "//xacml2:Condition//xacml2:SubjectAttributeDesignator[@AttributeId='$']/parent::xacml2:Apply/parent::xacml2:Apply/child::xacml2:AttributeValue/text()";

	/** The document xml converter. */
	private DocumentXmlConverter documentXmlConverter;

	/** The document accessor. */
	private DocumentAccessor documentAccessor;

	/**
	 * Instantiates a new xacml policy list filter.
	 * 
	 * @param documentXmlConverter
	 *            the document xml converter
	 * @param documentAccessor
	 *            the document accessor
	 */
	public XacmlPolicyListFilter(DocumentXmlConverter documentXmlConverter,
			DocumentAccessor documentAccessor) {
		super();
		this.documentXmlConverter = documentXmlConverter;
		this.documentAccessor = documentAccessor;
	}

	/**
	 * Filter by npi.
	 * 
	 * @param policies
	 *            the policies
	 * @param recipientSubjectNPI
	 *            the recipient subject npi
	 * @param intermediarySubjectNPI
	 *            the intermediary subject npi
	 * @throws Exception
	 *             the exception
	 */
	public void filterByNPI(List<String> policies, String recipientSubjectNPI,
			String intermediarySubjectNPI) throws Exception {
		LinkedList<String> removeList = new LinkedList<String>();
		for (String policy : policies) {
			Document policyDoc = documentXmlConverter.loadDocument(policy);

			String xPathExprIntermediary = XPATH_CONDITION_ATTRIBUTE_VALUE
					.replace("$", URN_XACML_1_INTERMEDIARY_SUBJECT);
			NodeList nodeListIntermediaryNPI = this.documentAccessor
					.getNodeList(policyDoc, xPathExprIntermediary);
			boolean containsIntermediaryNPI = containsInValue(
					nodeListIntermediaryNPI, intermediarySubjectNPI);
			if (containsIntermediaryNPI == false) {
				removeList.add(policy);
			} else {
				String xPathExprRecipient = XPATH_CONDITION_ATTRIBUTE_VALUE
						.replace("$", URN_XACML_1_RECIPIENT_SUBJECT);
				NodeList nodeListRecipientNPI = this.documentAccessor
						.getNodeList(policyDoc, xPathExprRecipient);
				boolean containsRecipientNPI = containsInValue(
						nodeListRecipientNPI, recipientSubjectNPI);
				if (containsRecipientNPI == false) {
					removeList.add(policy);
				}
			}
		}
		policies.removeAll(removeList);
	}

	/**
	 * Contains in value.
	 * 
	 * @param nodeList
	 *            the node list
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public boolean containsInValue(NodeList nodeList, String value) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			String nodeValue = nodeList.item(i).getNodeValue();
			if (value.equals(nodeValue)) {
				return true;
			}
		}
		return false;
	}
}
