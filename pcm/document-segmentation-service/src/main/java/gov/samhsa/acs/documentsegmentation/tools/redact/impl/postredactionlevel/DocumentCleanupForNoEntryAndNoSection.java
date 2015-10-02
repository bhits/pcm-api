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
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.acs.documentsegmentation.tools.redact.base.AbstractPostRedactionLevelRedactionHandler;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class DocumentCleanupForNoEntryAndNoSection.
 */
public class DocumentCleanupForNoEntryAndNoSection extends
		AbstractPostRedactionLevelRedactionHandler {

	/** The Constant URN_HL7_V3. */
	public static final String URN_HL7_V3 = "urn:hl7-org:v3";

	/** The Constant TAG_COMPONENT. */
	public static final String TAG_COMPONENT = "component";

	/** The Constant TAG_SECTION. */
	public static final String TAG_SECTION = "section";

	/** The Constant XPATH_NO_COMPONENT_IN_STRUCTURED_BODY. */
	public static final String XPATH_NO_COMPONENT_IN_STRUCTURED_BODY = "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody[not(hl7:component)]";

	/** The Constant XPATH_SECTION_COMPONENT_WITH_NO_ENTRY. */
	public static final String XPATH_SECTION_COMPONENT_WITH_NO_ENTRY = "/hl7:ClinicalDocument/hl7:component/hl7:structuredBody/hl7:component[hl7:section[not(hl7:entry)]]";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/**
	 * Instantiates a new document cleanup for no section.
	 *
	 * @param documentAccessor
	 *            the document accessor
	 */
	public DocumentCleanupForNoEntryAndNoSection(
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

		// Clean up section components with no entries
		cleanUpSectionComponentsWithNoEntries(xmlDocument);

		// Add empty component/section under structuredBody if none exists
		// (required to pass validation)
		addEmptySectionComponentIfNoneExists(xmlDocument);
	}

	/**
	 * Adds the empty section component if none exists.
	 *
	 * @param xmlDocument
	 *            the xml document
	 * @throws DocumentAccessorException
	 *             the document accessor exception
	 */
	private void addEmptySectionComponentIfNoneExists(Document xmlDocument)
			throws DocumentAccessorException {
		final Optional<Node> structuredBody = documentAccessor.getNode(
				xmlDocument, XPATH_NO_COMPONENT_IN_STRUCTURED_BODY);
		structuredBody.ifPresent(sb -> {
			final Element emptyComponent = xmlDocument.createElementNS(
					URN_HL7_V3, TAG_COMPONENT);
			final Element emptySection = xmlDocument.createElementNS(
					URN_HL7_V3, TAG_SECTION);
			emptyComponent.appendChild(emptySection);
			sb.appendChild(emptyComponent);
		});
	}

	/**
	 * Clean up section components with no entries.
	 *
	 * @param xmlDocument
	 *            the xml document
	 * @throws DocumentAccessorException
	 *             the document accessor exception
	 */
	private void cleanUpSectionComponentsWithNoEntries(Document xmlDocument)
			throws DocumentAccessorException {
		final NodeList emptySectionComponents = documentAccessor.getNodeList(
				xmlDocument, XPATH_SECTION_COMPONENT_WITH_NO_ENTRY);
		if (emptySectionComponents != null) {
			for (int i = 0; i < emptySectionComponents.getLength(); i++) {
				final Node sectionComponent = emptySectionComponents.item(i);
				try {
					sectionComponent.getParentNode().removeChild(
							sectionComponent);
				} catch (final NullPointerException e) {
					logger.info(
							() -> new StringBuilder().append("Node Name: '")
									.append(sectionComponent.getNodeName())
									.append("'").append("; Node Value: '")
									.append(sectionComponent.getNodeValue())
									.append("'")
									.append("; has already been removed.")
									.toString(), e);
				}
			}
		}
	}
}
