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
package gov.samhsa.acs.documentsegmentation.tools;

import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.exception.DocumentXmlConverterException;
import gov.samhsa.acs.documentsegmentation.tools.exception.DocumentSegmentationException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class EmbeddedClinicalDocumentExtractorImpl.
 */
public class EmbeddedClinicalDocumentExtractorImpl implements
		EmbeddedClinicalDocumentExtractor {

	/** The Constant XPATH_CLINICALDOCUMENT. */
	public static final String XPATH_CLINICALDOCUMENT = "//hl7:ClinicalDocument";

	/** The document xml converter. */
	private final DocumentXmlConverter documentXmlConverter;

	/** The document accessor. */
	private final DocumentAccessor documentAccessor;

	/**
	 * Instantiates a new embedded clinical document extractor impl.
	 *
	 * @param documentXmlConverter
	 *            the document xml converter
	 * @param documentAccessor
	 *            the document accessor
	 */
	public EmbeddedClinicalDocumentExtractorImpl(
			DocumentXmlConverter documentXmlConverter,
			DocumentAccessor documentAccessor) {
		super();
		this.documentXmlConverter = documentXmlConverter;
		this.documentAccessor = documentAccessor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.tools.EmbeddedClinicalDocumentExtractor
	 * #extractClinicalDocumentFromFactModel(java.lang.String)
	 */
	@Override
	public String extractClinicalDocumentFromFactModel(String factModel)
			throws DocumentSegmentationException {
		Document newXmlDocument;
		try {
			final Document factModelDocument = documentXmlConverter
					.loadDocument(factModel);
			final Node clinicalDocumentNode = documentAccessor.getNode(
					factModelDocument, XPATH_CLINICALDOCUMENT).get();

			// Create new document
			newXmlDocument = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// Import ClinicalDocument node to the new document
			final Node copyNode = newXmlDocument.importNode(
					clinicalDocumentNode, true);
			// Add ClinicalDocument node as the root node to the document
			newXmlDocument.appendChild(copyNode);
		} catch (XPathExpressionException | ParserConfigurationException
				| DocumentXmlConverterException e) {
			throw new DocumentSegmentationException(e);
		}
		return documentXmlConverter.convertXmlDocToString(newXmlDocument);
	}
}
