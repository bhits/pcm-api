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
package gov.samhsa.consent2share.domain.commondomainservices;

import echosign.api.clientv20.dto16.EmbeddedWidgetCreationResult;


/**
 * The Interface SignatureService.
 */
public interface SignatureService {

	/**
	 * Send document to sign.
	 *
	 * @param documentBytes the document bytes
	 * @param documentFileName the document file name
	 * @param documentName the document name
	 * @param recipientEmail the recipient email
	 * @param messageToRecipient the message to recipient
	 * @return the document key
	 */
	String sendDocumentToSign(byte[] documentBytes, String documentFileName,
			String documentName, String recipientEmail,
			String messageToRecipient);

	/**
	 * Send document to sign.
	 *
	 * @param documentBytes the document bytes
	 * @param documentFileName the document file name
	 * @param documentName the document name
	 * @param recipientEmail the recipient email
	 * @param messageToRecipient the message to recipient
	 * @param signedDocumentUrl the signed document url
	 * @return the document key
	 */
	String sendDocumentToSign(byte[] documentBytes, String documentFileName,
			String documentName, String recipientEmail,
			String messageToRecipient, String signedDocumentUrl);

	EmbeddedWidgetCreationResult createEmbeddedWidget(byte[] documentBytes,
			String documentFileName, String documentName,
			String signedDocumentUrl, String email);
}
