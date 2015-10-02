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
import gov.samhsa.acs.common.param.Params;
import gov.samhsa.acs.common.param.ParamsBuilder;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.util.StringURIResolver;

import java.util.Optional;

import javax.xml.transform.URIResolver;

/**
 * The Class MetadataGeneratorImpl.
 */
public class MetadataGeneratorImpl implements MetadataGenerator {

	/** The Constant PARAM_NAME_INTENDED_RECIPIENT. */
	private static final String PARAM_NAME_INTENDED_RECIPIENT = "intendedRecipient";

	/** The Constant PARAM_NAME_AUTHOR_TELECOMMUNICATION. */
	private static final String PARAM_NAME_AUTHOR_TELECOMMUNICATION = "authorTelecommunication";

	/** The Constant PARAM_NAME_HOME_COMMUNITY_ID. */
	private static final String PARAM_NAME_HOME_COMMUNITY_ID = "homeCommunityId";

	/** The Constant URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER. */
	private static final String URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER = "ruleExecutionResponseContainer";

	/** The Constant METADATA_XSL. */
	private static final String METADATA_XSL = "metadata.xsl";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The xml transformer. */
	private final XmlTransformer xmlTransformer;

	/**
	 * Instantiates a new metadata generator impl.
	 *
	 * @param xmlTransformer
	 *            the xml transformer
	 */
	public MetadataGeneratorImpl(XmlTransformer xmlTransformer) {
		super();
		this.xmlTransformer = xmlTransformer;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.acs.documentsegmentation.util
	 * .MetadataGenerator#generateMetadataXml(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String generateMetadataXml(String document,
			String executionResponseContainer, String homeCommunityId,
			String senderEmailAddress, String recipientEmailAddress) {

		final Params params = ParamsBuilder
				.withParam(PARAM_NAME_HOME_COMMUNITY_ID, homeCommunityId)
				.and(PARAM_NAME_AUTHOR_TELECOMMUNICATION, senderEmailAddress)
				.and(PARAM_NAME_INTENDED_RECIPIENT, recipientEmailAddress);
		final String xslUrl = Thread.currentThread().getContextClassLoader()
				.getResource(METADATA_XSL).toString();
		// add namespace execution response container for transformation
		executionResponseContainer = executionResponseContainer.replace(
				"<ruleExecutionContainer>",
				"<ruleExecutionContainer xmlns=\"urn:hl7-org:v3\">");
		final Optional<URIResolver> uriResolver = Optional
				.of(new StringURIResolver().put(
						URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER,
						executionResponseContainer));
		final String metadataXml = xmlTransformer.transform(document, xslUrl,
				Optional.of(params), uriResolver);
		logger.debug("Metadata:");
		logger.debug(metadataXml);
		return metadataXml;
	}
}
