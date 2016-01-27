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
package gov.samhsa.pcm.service.consentexport;

import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.pcm.domain.consent.Consent;

import org.springframework.security.access.annotation.Secured;

/**
 * The Interface ConsentExportService.
 */
public abstract interface ConsentExportService {

	/**
	 * Export consent to CDAR2 format.
	 *
	 * @param consentId
	 *            the consent id
	 */
	String exportConsent2CDAR2(Long consentId) throws ConsentGenException;

	/**
	 * Export consent to xacml format.
	 *
	 * @param consentId
	 *            the consent id
	 */
	String exportConsent2XACML(Long consentId) throws ConsentGenException;

	/**
	 * Export consent to xacml format.
	 *
	 * @param consent
	 *            the consent
	 * @return the string
	 */
	String exportConsent2XACML(Consent consent) throws ConsentGenException;

	/**
	 * Export consent2 xacml for consentFrom provider. to give access to consent
	 * pdf from C2S Health
	 * 
	 * @param consent
	 *            the consent
	 * @return the string
	 * @throws ConsentGenException
	 *             the consent gen exception
	 */
	public String exportConsent2XacmlPdfConsentFrom(Consent consent)
			throws ConsentGenException;

	/**
	 * Export the consent2 xacml for consentTo provider. to give access to
	 * consent pdf from C2S Health
	 * 
	 * @param consent
	 *            the consent
	 * @return the string
	 * @throws ConsentGenException
	 *             the consent gen exception
	 */
	public String exportConsent2XacmlPdfConsentTo(Consent consent)
			throws ConsentGenException;
}
