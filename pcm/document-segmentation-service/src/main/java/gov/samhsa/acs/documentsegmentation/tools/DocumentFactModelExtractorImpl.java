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

/**
 * The Class DocumentFactModelExtractorImpl.
 */
public class DocumentFactModelExtractorImpl implements
		DocumentFactModelExtractor {

	/** The Constant EXTRACT_CLINICAL_FACTS_XSL. */
	private static final String EXTRACT_CLINICAL_FACTS_XSL = "extractClinicalFacts.xsl";

	/** The Constant PARAM_XACML_RESULT. */
	private static final String PARAM_XACML_RESULT = "xacmlResult";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The xml transformer. */
	private final XmlTransformer xmlTransformer;

	/**
	 * Instantiates a new document fact model extractor impl.
	 *
	 * @param xmlTransformer
	 *            the xml transformer
	 */
	public DocumentFactModelExtractorImpl(XmlTransformer xmlTransformer) {
		super();
		this.xmlTransformer = xmlTransformer;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.acs.documentsegmentation.tools
	 * .DocumentFactModelExtractor#extractFactModel(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String extractFactModel(String document, String enforcementPolicies) {

		final String xslUrl = Thread.currentThread().getContextClassLoader()
				.getResource(EXTRACT_CLINICAL_FACTS_XSL).toString();
		final String xacmlResult = enforcementPolicies.replace("<xacmlReslt>",
				"<xacmlReslt xmlns:\"urn:hl7-org:v3\">");
		final Optional<URIResolver> uriResolver = Optional
				.of(new StringURIResolver()
						.put(PARAM_XACML_RESULT, xacmlResult));
		String factModel = xmlTransformer.transform(document, xslUrl,
				Optional.empty(), uriResolver);

		factModel = factModel
				.replace(
						"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
						"");
		logger.debug("FactModel:");
		logger.debug(factModel);
		return factModel;
	}
}
