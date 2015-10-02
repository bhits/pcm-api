/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.documentsegmentation.tools;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.param.Params;
import gov.samhsa.acs.common.param.ParamsBuilder;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.util.StringURIResolver;

import java.util.Optional;

import javax.xml.transform.URIResolver;

import org.springframework.util.Assert;

/**
 * The Class AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl.
 */
public class AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl
		implements AdditionalMetadataGeneratorForSegmentedClinicalDocument {

	/** The Constant URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER. */
	private static final String URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER = "ruleExecutionResponseContainer";

	/** The Constant PARAM_NAME_XDS_DOCUMENT_ENTRY_UNIQUE_ID. */
	private static final String PARAM_NAME_XDS_DOCUMENT_ENTRY_UNIQUE_ID = "xdsDocumentEntryUniqueId";

	/** The Constant PARAM_NAME_PRIVACY_POLICIES_EXTERNAL_DOC_URL. */
	private static final String PARAM_NAME_PRIVACY_POLICIES_EXTERNAL_DOC_URL = "privacyPoliciesExternalDocUrl";

	/** The Constant PARAM_NAME_PURPOSE_OF_USE. */
	private static final String PARAM_NAME_PURPOSE_OF_USE = "purposeOfUse";

	/** The Constant PARAM_NAME_INTENDED_RECIPIENT. */
	private static final String PARAM_NAME_INTENDED_RECIPIENT = "intendedRecipient";

	/** The Constant PARAM_NAME_AUTHOR_TELECOMMUNICATION. */
	private static final String PARAM_NAME_AUTHOR_TELECOMMUNICATION = "authorTelecommunication";

	/** The Additonal metadata stylesheet for processed c32_ xsl_ file_ name. */
	private static String AdditonalMetadataStylesheetForProcessedC32_Xsl_File_Name = "AdditonalMetadataStylesheetForProcessedC32.xsl";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The xml trasformer. */
	private final XmlTransformer xmlTrasformer;

	/**
	 * Instantiates a new additional metadata generator for segmented clinical
	 * document impl.
	 *
	 * @param xmlTrasformer
	 *            the xml trasformer
	 */
	public AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl(
			XmlTransformer xmlTrasformer) {
		super();
		this.xmlTrasformer = xmlTrasformer;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.ds4ppilot.pep.XdsbMetadataGenerator#generateMetadataXml
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public String generateMetadataXml(String messageId, String document,
			String ruleExecutionResponseContainer, String senderEmailAddress,
			String recipientEmailAddress, String purposeOfUse,
			String xdsDocumentEntryUniqueId) {

		try {
			final Params params = ParamsBuilder
					.withParam(PARAM_NAME_AUTHOR_TELECOMMUNICATION,
							senderEmailAddress)
					.and(PARAM_NAME_INTENDED_RECIPIENT, recipientEmailAddress)
					.and(PARAM_NAME_PURPOSE_OF_USE, purposeOfUse)
					.and(PARAM_NAME_PRIVACY_POLICIES_EXTERNAL_DOC_URL,
							messageId)
					.and(PARAM_NAME_XDS_DOCUMENT_ENTRY_UNIQUE_ID,
							xdsDocumentEntryUniqueId);
			final String xslUrl = Thread
					.currentThread()
					.getContextClassLoader()
					.getResource(
							AdditonalMetadataStylesheetForProcessedC32_Xsl_File_Name)
					.toString();
			final Optional<URIResolver> uriResolver = Optional
					.of(new StringURIResolver()
							.put(URI_RESOLVER_HREF_RULE_EXECUTION_RESPONSE_CONTAINER,
									ruleExecutionResponseContainer));
			final String output = xmlTrasformer.transform(document, xslUrl,
					Optional.of(params), uriResolver);
			Assert.hasText(output, "Cannot generate additional metadata!");
			logger.debug("AdditionalMetadata:");
			logger.debug(output);
			return output;
		} catch (final Exception e) {
			throw new DS4PException(e.getMessage(), e);
		}
	}
}
