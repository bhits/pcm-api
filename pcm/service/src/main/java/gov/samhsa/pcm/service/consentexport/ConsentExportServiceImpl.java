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

import gov.samhsa.consent.ConsentBuilder;
import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.pcm.domain.consent.Consent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class ConsentExportServiceImpl.
 */
@Transactional(readOnly = true)
public class ConsentExportServiceImpl implements ConsentExportService {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The consent builder. */
	ConsentBuilder consentBuilder;

	/**
	 * Instantiates a new consent export service impl.
	 *
	 * @param consentBuilder
	 *            the consent builder
	 */
	public ConsentExportServiceImpl(ConsentBuilder consentBuilder) {
		super();
		this.consentBuilder = consentBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#
	 * exportCDAR2Consent(java.lang.Long)
	 */
	@Override
	public String exportConsent2CDAR2(Long consentId)
			throws ConsentGenException {
		return consentBuilder.buildConsent2Cdar2(consentId);
	}

	@Override
	public String exportConsent2CDAR2ConsentDirective(Consent consent)
			throws ConsentGenException {
		return consentBuilder.buildConsent2Cdar2ConsentDirective(consent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#
	 * exportConsent2XACML(java.lang.Long)
	 */
	@Override
	public String exportConsent2XACML(Long consentId)
			throws ConsentGenException {
		return consentBuilder.buildConsent2Xacml(consentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#
	 * exportConsent2XACML(gov.samhsa.consent2share.domain.consent.Consent)
	 */
	@Override
	public String exportConsent2XACML(Consent consent)
			throws ConsentGenException {
		return consentBuilder.buildConsent2Xacml(consent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#
	 * exportConsent2XacmlPdfConsentFrom
	 * (gov.samhsa.consent2share.domain.consent.Consent)
	 */
	@Override
	public String exportConsent2XacmlPdfConsentFrom(Consent consent)
			throws ConsentGenException {
		return consentBuilder.buildConsent2XacmlPdfConsentFrom(consent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#
	 * exportConsent2XacmlPdfConsentTo
	 * (gov.samhsa.consent2share.domain.consent.Consent)
	 */
	@Override
	public String exportConsent2XacmlPdfConsentTo(Consent consent)
			throws ConsentGenException {
		return consentBuilder.buildConsent2XacmlPdfConsentTo(consent);
	}
}
