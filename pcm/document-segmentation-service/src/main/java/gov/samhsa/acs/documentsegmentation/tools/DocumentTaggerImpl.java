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

import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.util.StringURIResolver;

import java.util.Optional;

import javax.xml.transform.URIResolver;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * The Class DocumentTaggerImpl.
 */
public class DocumentTaggerImpl implements DocumentTagger {

	/** The Constant URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER. */
	private static final String URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER = "ruleExecutionResponseContainer";

	/** The Constant URI_RESOLVER_HREF_DISCLAMER. */
	private static final String URI_RESOLVER_HREF_DISCLAMER = "disclaimer";

	/** The Constant TAG_XSL. */
	private static final String TAG_XSL = "tag.xsl";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The disclaimer text. */
	private String disclaimerText;

	/** The xml transformer. */
	private final XmlTransformer xmlTransformer;

	/**
	 * Instantiates a new document tagger impl.
	 *
	 * @param disclaimerText
	 *            the disclaimer text
	 * @param xmlTransformer
	 *            the xml transformer
	 */
	public DocumentTaggerImpl(String disclaimerText,
			XmlTransformer xmlTransformer) {
		super();
		this.disclaimerText = StringEscapeUtils.unescapeXml(disclaimerText);
		this.disclaimerText = disclaimerText.replace("<disclaimerText>",
				"<disclaimerText xmlns=\"urn:hl7-org:v3\">");
		this.xmlTransformer = xmlTransformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.util
	 * .DocumentTagger#tagDocument(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String tagDocument(String document, String executionResponseContainer) {

		executionResponseContainer = executionResponseContainer.replace(
				"<ruleExecutionContainer>",
				"<ruleExecutionContainer xmlns=\"urn:hl7-org:v3\">");
		final String xslUrl = Thread.currentThread().getContextClassLoader()
				.getResource(TAG_XSL).toString();
		final StringURIResolver stringURIResolver = new StringURIResolver();
		stringURIResolver.put(
				URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER,
				executionResponseContainer);
		stringURIResolver.put(URI_RESOLVER_HREF_DISCLAMER, disclaimerText);
		final Optional<URIResolver> uriResolver = Optional
				.of(stringURIResolver);
		final String taggedDocument = xmlTransformer.transform(document,
				xslUrl, Optional.empty(), uriResolver);
		logger.debug("Tagged Document:");
		logger.debug(taggedDocument);
		return taggedDocument;
	}
}
