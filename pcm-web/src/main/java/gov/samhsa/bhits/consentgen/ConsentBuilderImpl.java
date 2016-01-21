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
package gov.samhsa.bhits.consentgen;


import java.util.Optional;

import gov.samhsa.bhits.common.param.ParamsBuilder;
import gov.samhsa.bhits.common.document.transformer.XmlTransformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * The Class ConsentBuilderImpl.
 */
public class ConsentBuilderImpl implements ConsentBuilder {

	/** The Constant PARAM_EID. */
	public final static String PARAM_EID = "enterpriseIdentifier";

	/** The Constant PARAM_MRN. */
	public final static String PARAM_MRN = "medicalRecordNumber";

	/** The Constant PARAM_POLICY_ID. */
	public final static String PARAM_POLICY_ID = "policyId";

	/** The c2s account org. */
	private final String c2sAccountOrg;

	/** The xacml xsl url provider. */
	private final XacmlXslUrlProvider xacmlXslUrlProvider;

	/** The consent dto factory. */
	private final ConsentDtoFactory consentDtoFactory;

	/** The xml transformer. */
	private final XmlTransformer xmlTransformer;

	/**
	 * Instantiates a new consent builder impl.
	 *
	 * @param c2sAccountOrg
	 *            the c2s account org
	 * @param xacmlXslUrlProvider
	 *            the xacml xsl url provider
	 * @param consentDtoFactory
	 *            the consent dto factory
	 * @param xmlTransformer
	 *            the xml transformer
	 */
	public ConsentBuilderImpl(String c2sAccountOrg,
			XacmlXslUrlProvider xacmlXslUrlProvider,
			ConsentDtoFactory consentDtoFactory, XmlTransformer xmlTransformer) {
		super();
		this.c2sAccountOrg = c2sAccountOrg;
		this.xacmlXslUrlProvider = xacmlXslUrlProvider;
		this.consentDtoFactory = consentDtoFactory;
		this.xmlTransformer = xmlTransformer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent.ConsentBuilder#buildConsent2Cdar2(long)
	 */
	@Override
	public String buildConsent2Cdar2(long consentId) throws ConsentGenException {
		try {
			final ConsentDto consentDto = consentDtoFactory
					.createConsentDto(consentId);
			final String cdar2 = xmlTransformer.transform(consentDto,
					xacmlXslUrlProvider.getUrl(XslResource.CDAR2XSLNAME),
					Optional.empty(), Optional.empty());
			return cdar2;
		} catch (final Exception e) {
			throw new ConsentGenException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent.ConsentBuilder#buildConsent2Xacml(java.lang.Object)
	 */
	@Override
	public String buildConsent2Xacml(Object obj) throws ConsentGenException {
		try {
			final ConsentDto consentDto = consentDtoFactory
					.createConsentDto(obj);
			final String xacml = xmlTransformer.transform(consentDto,
					xacmlXslUrlProvider.getUrl(XslResource.XACMLXSLNAME),
					Optional.of(ParamsBuilder.withParam(PARAM_MRN, consentDto
							.getPatientDto().getMedicalRecordNumber())),
					Optional.empty());
			return xacml;
		} catch (final Exception e) {
			throw new ConsentGenException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent.ConsentBuilder#buildConsent2XacmlPdfConsentFrom(java
	 * .lang.Object)
	 */
	@Override
	public String buildConsent2XacmlPdfConsentFrom(Object obj)
			throws ConsentGenException {
		try {
			final ConsentDto consentDto = consentDtoFactory
					.createConsentDto(obj);
			final String consentId = consentDto.getConsentReferenceid();
			String policyId = null;
			if (consentId != null) {
				policyId = buildPdfPolicyId(consentId, true);
			}
			final String xacml = xmlTransformer.transform(consentDto,
					xacmlXslUrlProvider
							.getUrl(XslResource.XACMLPDFCONSENTFROMXSLNAME),
					Optional.of(ParamsBuilder
							.withParam(
									PARAM_MRN,
									consentDto.getPatientDto()
											.getMedicalRecordNumber()).and(
									PARAM_POLICY_ID, policyId)), Optional
							.empty());
			return xacml;
		} catch (final Exception e) {
			throw new ConsentGenException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent.ConsentBuilder#buildConsent2XacmlPdfConsentTo(java
	 * .lang.Object)
	 */
	@Override
	public String buildConsent2XacmlPdfConsentTo(Object obj)
			throws ConsentGenException {
		try {
			final ConsentDto consentDto = consentDtoFactory
					.createConsentDto(obj);
			final String consentId = consentDto.getConsentReferenceid();
			String policyId = null;
			if (consentId != null) {
				policyId = buildPdfPolicyId(consentId, false);
			}
			final String xacml = xmlTransformer.transform(consentDto,
					xacmlXslUrlProvider
							.getUrl(XslResource.XACMLPDFCONSENTTOXSLNAME),
					Optional.of(ParamsBuilder
							.withParam(
									PARAM_MRN,
									consentDto.getPatientDto()
											.getMedicalRecordNumber()).and(
									PARAM_POLICY_ID, policyId)), Optional
							.empty());
			return xacml;
		} catch (final Exception e) {
			throw new ConsentGenException(e.getMessage(), e);
		}
	}

	/**
	 * Builds the pdf policy id.
	 *
	 * @param consentId
	 *            the consent id
	 * @param isConsentFrom
	 *            the is consent from
	 * @return the string
	 */
	protected String buildPdfPolicyId(String consentId, boolean isConsentFrom) {
		// String id =
		// "C2S.PG-DEV.RmETWp:&amp;2.16.840.1.113883.3.704.100.200.1.1.3.1&amp;ISO:1578821153:1427467752:XM2UoY";
		final String[] tokens = consentId.split(":");
		final int tokenCount = tokens.length;
		// assert the no of elements as 5
		Assert.isTrue(tokenCount == 5,
				"consent reference id should split into 5 sub elements with : delimiter");
		final String consentTo = tokens[tokenCount - 3];
		final String consentFrom = tokens[tokenCount - 2];
		if (isConsentFrom) {
			tokens[tokenCount - 3] = consentFrom;
			tokens[tokenCount - 2] = c2sAccountOrg;

		} else {
			tokens[tokenCount - 3] = consentTo;
			tokens[tokenCount - 2] = c2sAccountOrg;

		}
		String policyId = StringUtils.join(tokens, ":");
		final String[] pTokens = policyId.split("&amp;");
		policyId = StringUtils.join(pTokens, "&");

		return policyId;
	}
}
