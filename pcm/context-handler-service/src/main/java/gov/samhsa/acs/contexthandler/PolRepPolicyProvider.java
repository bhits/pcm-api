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
package gov.samhsa.acs.contexthandler;

import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;
import gov.samhsa.acs.polrep.client.PolRepRestClient;
import gov.samhsa.acs.polrep.client.PolicyCombiningAlgIds;
import gov.samhsa.acs.polrep.client.dto.PolicyDto;
import gov.samhsa.consent2share.hl7.Hl7v3Transformer;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.pixclient.service.PixManagerService;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.pixclient.util.PixManagerConstants;
import gov.samhsa.consent2share.pixclient.util.PixManagerMessageHelper;
import gov.samhsa.consent2share.pixclient.util.PixManagerRequestXMLToJava;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import org.herasaf.xacml.core.SyntaxException;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * The Class PolRepPolicyProvider.
 */
public class PolRepPolicyProvider implements PolicyProvider {

	/** The Constant WILDCARD. */
	private static final String WILDCARD = "*";

	/** The Constant DELIMITER_AMPERSAND. */
	private static final String DELIMITER_AMPERSAND = "&";

	/** The Constant DELIMITER_COLON. */
	private static final String DELIMITER_COLON = ":";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	/** The pid domain. */
	private final String pidDomain;

	/** The pid domain type. */
	private final String pidDomainType;

	/** The hl7v3 transformer. */
	private final Hl7v3Transformer hl7v3Transformer;

	/** The request xml to java. */
	private final PixManagerRequestXMLToJava requestXMLToJava;

	/** The pix manager message helper. */
	private final PixManagerMessageHelper pixManagerMessageHelper;

	/** The pix manager service. */
	private final PixManagerService pixManagerService;

	/** The pol rep rest client. */
	private final PolRepRestClient polRepRestClient;

	/**
	 * Instantiates a new pol rep policy provider.
	 *
	 * @param pidDomain
	 *            the pid domain
	 * @param pidDomainType
	 *            the pid domain type
	 * @param hl7v3Transformer
	 *            the hl7v3 transformer
	 * @param requestXMLToJava
	 *            the request xml to java
	 * @param pixManagerMessageHelper
	 *            the pix manager message helper
	 * @param pixManagerService
	 *            the pix manager service
	 * @param polRepRestClient
	 *            the pol rep rest client
	 */
	public PolRepPolicyProvider(String pidDomain, String pidDomainType,
			Hl7v3Transformer hl7v3Transformer,
			PixManagerRequestXMLToJava requestXMLToJava,
			PixManagerMessageHelper pixManagerMessageHelper,
			PixManagerService pixManagerService,
			PolRepRestClient polRepRestClient) {
		super();
		this.pidDomain = pidDomain;
		this.pidDomainType = pidDomainType;
		this.hl7v3Transformer = hl7v3Transformer;
		this.requestXMLToJava = requestXMLToJava;
		this.pixManagerMessageHelper = pixManagerMessageHelper;
		this.pixManagerService = pixManagerService;
		this.polRepRestClient = polRepRestClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyProvider#getPolicies(gov.samhsa.acs
	 * .common.dto.XacmlRequest)
	 */
	@Override
	public List<Evaluatable> getPolicies(XacmlRequest xacmlRequest)
			throws NoPolicyFoundException, PolicyProviderException {
		try {
			final String eid = xacmlRequest.getPatientId();
			final String eidDomain = xacmlRequest.getHomeCommunityId();
			final String mrn = getMrn(eid, eidDomain);
			final String policyId = toPolicyId(mrn, pidDomain,
					xacmlRequest.getRecipientSubjectNPI(),
					xacmlRequest.getIntermediarySubjectNPI());
			final PolicyDto policyDto = polRepRestClient
					.getPoliciesCombinedAsPolicySet(policyId, WILDCARD, UUID
							.randomUUID().toString(),
							PolicyCombiningAlgIds.DENY_OVERRIDES);
			final Evaluatable policySet = PolicyMarshaller
					.unmarshal(new ByteArrayInputStream(policyDto.getPolicy()));
			return Arrays.asList(policySet);
		} catch (final SyntaxException e) {
			logger.error(e.getMessage(), e);
			throw new PolicyProviderException(e.getMessage(), e);
		} catch (final HttpStatusCodeException e) {
			logger.error(e.getMessage(), e);
			if (e.getStatusCode().is4xxClientError()) {
				logger.info(e.getMessage());
				throw new NoPolicyFoundException(e.getMessage(), e);
			} else {
				throw new PolicyProviderException(e.getMessage(), e);
			}
		}
	}

	/**
	 * Gets the mrn.
	 *
	 * @param eid
	 *            the eid
	 * @param eidDomain
	 *            the eid domain
	 * @return the mrn
	 */
	private String getMrn(String eid, String eidDomain) {
		PixManagerBean pixMgrBean = new PixManagerBean();
		try {
			final String hl7Query = hl7v3Transformer.getPixQueryXml(eid,
					eidDomain, Hl7v3Transformer.XML_PIX_QUERY);
			final PRPAIN201309UV02 request = requestXMLToJava
					.getPIXQueryReqObject(hl7Query,
							PixManagerConstants.ENCODE_STRING);
			final PRPAIN201310UV02 response = pixManagerService
					.pixManagerPRPAIN201309UV02(request);
			pixMgrBean = pixManagerMessageHelper.getQueryMessage(response,
					pixMgrBean);
			return pixMgrBean.getQueryIdMap().get(pidDomain);
		} catch (Hl7v3TransformerException | JAXBException | IOException e) {
			pixManagerMessageHelper.getGeneralExpMessage(e, pixMgrBean,
					PixManagerConstants.PIX_QUERY);
			throw new DS4PException(e.getMessage(), e);
		}
	}

	/**
	 * To policy id.
	 *
	 * @param pid
	 *            the pid
	 * @param pidDomain
	 *            the pid domain
	 * @param recipientSubjectNPI
	 *            the recipient subject npi
	 * @param intermediarySubjectNPI
	 *            the intermediary subject npi
	 * @return the string
	 */
	private String toPolicyId(String pid, String pidDomain,
			String recipientSubjectNPI, String intermediarySubjectNPI) {
		final StringBuilder policyIdBuilder = new StringBuilder();
		policyIdBuilder.append(pid);
		policyIdBuilder.append(DELIMITER_COLON);
		policyIdBuilder.append(DELIMITER_AMPERSAND);
		policyIdBuilder.append(pidDomain);
		policyIdBuilder.append(DELIMITER_AMPERSAND);
		policyIdBuilder.append(pidDomainType);
		policyIdBuilder.append(DELIMITER_COLON);
		policyIdBuilder.append(recipientSubjectNPI);
		policyIdBuilder.append(DELIMITER_COLON);
		policyIdBuilder.append(intermediarySubjectNPI);
		policyIdBuilder.append(DELIMITER_COLON);
		policyIdBuilder.append(WILDCARD);
		return policyIdBuilder.toString();
	}
}
