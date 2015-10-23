package gov.samhsa.pcm.infrastructure;

import gov.samhsa.acs.trypolicy.wsclient.TryPolicyWebServiceClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * The Class TryPolicyServiceImpl.
 */
public class TryPolicyServiceImpl implements TryPolicyService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The try my policy service url. */
	private String tryPolicyServiceUrl;

	/**
	 * Instantiates a new echo sign signature service impl.
	 *
	 * @param tryPolicyServiceUrl
	 *            the echo sign service url
	 */
	public TryPolicyServiceImpl(String tryPolicyServiceUrl) {
		Assert.hasText(tryPolicyServiceUrl);

		this.tryPolicyServiceUrl = tryPolicyServiceUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.TryPolicyService#tryPolicy(java
	 * .lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String tryPolicy(String originalC32, String xacml,
			String purposeOfUse) {
		TryPolicyWebServiceClient client = new TryPolicyWebServiceClient(
				tryPolicyServiceUrl);
		String segmentedC32 = null;

		segmentedC32 = client.tryPolicy(originalC32, xacml, purposeOfUse);

		logger.info("Display origincal C32: " + originalC32);
		logger.info("Display segmented C32: " + segmentedC32);

		return segmentedC32;
	}
}
