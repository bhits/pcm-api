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

import gov.samhsa.acs.brms.domain.XacmlResult;

import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.apache.xml.security.encryption.XMLEncryptionException;

/**
 * The Interface DocumentEditor.
 */
public interface DocumentEditor {

	/**
	 * Sets the document creation date.
	 *
	 * @param document
	 *            the document
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 * @throws XMLEncryptionException
	 *             the xML encryption exception
	 */
	public abstract String setDocumentCreationDate(String document)
			throws Exception, XPathExpressionException, XMLEncryptionException;

	/**
	 * Sets the document payload raw data.
	 *
	 * @param document
	 *            the document
	 * @param packageAsXdm
	 *            the package as xdm
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @param xacmlResult
	 *            the xacml result
	 * @param executionResponseContainer
	 *            the execution response container
	 * @param maskingKeyBytes
	 *            the masking key bytes
	 * @param encryptionKeyBytes
	 *            the encryption key bytes
	 * @return the byte array data source
	 * @throws Exception
	 *             the exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public abstract ByteArrayDataSource setDocumentPayloadRawData(
			String document, boolean packageAsXdm, String senderEmailAddress,
			String recipientEmailAddress, XacmlResult xacmlResult,
			String executionResponseContainer, byte[] maskingKeyBytes,
			byte[] encryptionKeyBytes) throws Exception, IOException;

}
