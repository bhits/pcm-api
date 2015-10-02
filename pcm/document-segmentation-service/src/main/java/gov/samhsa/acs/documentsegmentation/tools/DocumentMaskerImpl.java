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

import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;

import java.security.Key;

import org.w3c.dom.Document;

/**
 * The Class DocumentMaskerImpl.
 */
public class DocumentMaskerImpl implements DocumentMasker {

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The document xml converter. */
	private final DocumentXmlConverter documentXmlConverter;

	/**
	 * Instantiates a new document masker impl.
	 *
	 * @param documentXmlConverter
	 *            the document xml converter
	 */
	public DocumentMaskerImpl(DocumentXmlConverter documentXmlConverter) {
		super();
		this.documentXmlConverter = documentXmlConverter;
	}

	// commented out for redact-only application
	//
	// /** The pdp obligation prefix for mask. */
	// private final String PDP_OBLIGATION_PREFIX_MASK =
	// "urn:oasis:names:tc:xspa:2.0:resource:patient:mask:";

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.tools
	 * .DocumentMasker#maskDocument(java.lang.String, java.security.Key,
	 * gov.samhsa.acs.common.bean.RuleExecutionContainer,
	 * gov.samhsa.acs.common.bean.XacmlResult)
	 */
	@Override
	public String maskDocument(String document, Key deSedeMaskKey,
			RuleExecutionContainer ruleExecutionContainer,
			XacmlResult xacmlResult) {
		Document xmlDocument = null;
		String xmlString = null;

		try {
			xmlDocument = documentXmlConverter.loadDocument(document);

			/*
			 * Get a key to be used for encrypting the element. Here we are
			 * generating an AES key.
			 */
			// Key aesSymmetricKey = EncryptTool.generateDataEncryptionKey();

			// commented out for redact-only application
			// byte[] keyBytes = deSedeMaskKey.getEncoded();
			//
			// String algorithmURI = XMLCipher.TRIPLEDES_KeyWrap;
			//
			// XMLCipher keyCipher = XMLCipher.getInstance(algorithmURI);
			// keyCipher.init(XMLCipher.WRAP_MODE, deSedeMaskKey);
			// EncryptedKey encryptedKey = keyCipher.encryptKey(xmlDocument,
			// aesSymmetricKey);
			//
			// commented out for redact-only application
			// for (RuleExecutionResponse response : ruleExecutionContainer
			// .getExecutionResponseList()) {
			// if (containsMaskObligation(xacmlResult, response)) {
			// String observationId = response.getObservationId();
			// String displayName = response.getDisplayName();
			//
			// // mask display Name
			// String xPathExprDisplayName = "//hl7:td[.='%']/parent::hl7:tr";
			// xPathExprDisplayName = xPathExprDisplayName.replace("%",
			// displayName);
			//
			// Element elementToBeEncrypted =
			// DocumentHelper.getElement(xmlDocument,
			// xPathExprDisplayName);
			//
			// DocumentEncrypter.encryptElement(xmlDocument, aesSymmetricKey,
			// encryptedKey,
			// elementToBeEncrypted);
			//
			// // mask element contents
			// String xPathExprObservationId =
			// "//hl7:id[@root='%']/ancestor::hl7:entry";
			// xPathExprObservationId = xPathExprObservationId.replace(
			// "%", observationId);
			//
			// elementToBeEncrypted = DocumentHelper.getElement(xmlDocument,
			// xPathExprObservationId);
			//
			// DocumentEncrypter.encryptElement(xmlDocument, aesSymmetricKey,
			// encryptedKey,
			// elementToBeEncrypted);
			// }
			// }

			// FileHelper.writeDocToFile(xmlDocument, "Masked_C32.xml");
			xmlString = documentXmlConverter.convertXmlDocToString(xmlDocument);

		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		}
		return xmlString;
	}

	// commented out for redact-only application
	// /**
	// * Checks if the xacmlResult has any mask obligations for a single
	// executionResonse's sensitivity value.
	// *
	// * @param xacmlResult the xacml result
	// * @param response the response
	// * @return true, if successful
	// */
	// private boolean containsMaskObligation(XacmlResult xacmlResult,
	// RuleExecutionResponse response)
	// {
	// return
	// xacmlResult.getPdpObligations().contains(PDP_OBLIGATION_PREFIX_MASK+response.getSensitivity());
	//
	// }
}
