package gov.samhsa.acs.polrep.client;

import gov.samhsa.acs.polrep.client.dto.PolicyContainerDto;
import gov.samhsa.acs.polrep.client.dto.PolicyContentContainerDto;
import gov.samhsa.acs.polrep.client.dto.PolicyContentDto;
import gov.samhsa.acs.polrep.client.dto.PolicyDto;
import gov.samhsa.acs.polrep.client.dto.PolicyMetadataContainerDto;
import gov.samhsa.acs.polrep.client.dto.PolicyMetadataDto;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;

public class PolRepRestClientIT {

	private static final String POLICY1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" PolicyId=\"C2S.001:&amp;2.16.840.1.113883.3.72.5.9.4&amp;ISO:1902131865:1568797520:FSMMDV\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Description>This is a reference policy for	consent2share@outlook.com</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">consent2share@outlook.com</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1568797520</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1902131865</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.pcm.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">EMERGENCY</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.pcm.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">PAYMENT</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.pcm.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">RESEARCH</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.pcm.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREATMENT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2015-03-03T00:00:00-0500</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2016-03-02T23:59:59-0500</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">PSY</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">COM</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">HIV</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ALC</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">GDIS</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">SEX</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ADD</AttributeAssignment></Obligation></Obligations></Policy>";
	private static final String POLICY2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" PolicyId=\"C2S.001:&amp;2.16.840.1.113883.3.704.100.200.1.1.3.1&amp;ISO:1174858088:1740515725:JKCE7C\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Description>This is a reference policy for	consent2share@outlook.com</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">34133-9</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:typeCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</AttributeValue><ResourceAttributeDesignator AttributeId=\"xacml:status\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsquery</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsretrieve</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1740515725</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1174858088</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREATMENT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2015-03-03T00:00:00-0500</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2016-03-02T23:59:59-0500</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ADD</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ALC</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">PSY</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">COM</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">GDIS</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">SEX</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">HIV</AttributeAssignment></Obligation></Obligations></Policy>";

	private static final String POLICY1_ID = "C2S.001:&2.16.840.1.113883.3.72.5.9.4&ISO:1902131865:1568797520:FSMMDV";
	private static final String POLICY2_ID = "C2S.001:&2.16.840.1.113883.3.704.100.200.1.1.3.1&ISO:1174858088:1740515725:JKCE7C";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private PolRepRestClient sut;

	@Before
	public void setUp() throws Exception {
		final String scheme = "http";
		final String host = "localhost";
		final int port = 8080;
		final String context = "polrep-web";
		final String version = "latest";
		sut = new PolRepRestClient(scheme, host, port, context, version);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws UnsupportedEncodingException {
		System.out.println("SUCCESSFULLY STARTED");
		boolean success = true;
		try {
			// post test: SUCCESS (if not already published)
			testPost(false);

			// post test: FAIL (because already published)
			try {
				testPost(false);
			} catch (final Exception e) {
			}

			// post test: SUCCESS (force update)
			testPost(true);

			// get single test: SUCCESS
			testGetSingle("C2S.001:&2.16.840.1.113883.3.72.5.9.4&ISO:1902131865:1568797520:FSMMDV");

			// get single test: FAIL (because policy id doesn't exist)
			try {
				testGetSingle("THIS_IS_NOT_A_VALID_POLICY_ID");
			} catch (final Exception e) {
			}

			// get test no wildcard: SUCCESS
			testGet("C2S.001:&2.16.840.1.113883.3.704.100.200.1.1.3.1&ISO:1174858088:1740515725:JKCE7C",
					null);

			// get test wildcard: SUCCESS
			testGet("C2S*", "*");

			// get test no wildcard: FAIL (because policy id doesn't exist)
			try {
				testGet("THIS_IS_NOT_A_VALID_POLICY_ID", null);
			} catch (final Exception e) {
			}

			// put test: SUCCESS
			testPut(POLICY1_ID);

			// put test: FAIL (because policy id doesn't exist)
			try {
				testPut("THIS_IS_NOT_A_VALID_POLICY_ID");
			} catch (final Exception e) {
			}

			// getPolicyCombiningAlgIds test: success
			testGetPolicyCombiningAlgIds();

			// get as policy set test: SUCCESS
			testGetAsPolicySet("C2S*", "*", "SAMPLE_POLICY_SET_ID");

			// get as policy set test: FAIL (because policy id doesn't exist)
			try {
				testGetAsPolicySet("THIS_IS_NOT_A_VALID_POLICY_ID", "*",
						"SAMPLE_POLICY_SET_ID");
			} catch (final Exception e) {
			}

			// delete test: SUCCESS
			testDelete(POLICY1_ID);

			// delete test: SUCCESS
			testDelete(POLICY2_ID);

			// delete test: FAIL (because policy id doesn't exist)
			try {
				testPut("THIS_IS_NOT_A_VALID_POLICY_ID");
			} catch (final Exception e) {
			}
		} catch (final Exception e) {
			success = false;
			System.out.println("FAILED");
		}

		if (success) {
			System.out.println("SUCCESSFULLY ENDED");
		}
		Assert.isTrue(success);
	}

	// delete test
	private void testDelete(String policyId) {
		sut.deletePolicy(policyId);
	}

	// get test
	private void testGet(String policyId, String wildcard) {
		final PolicyContainerDto resp = sut.getPolicies(policyId, wildcard);
		resp.getPolicies().stream().map(PolicyDto::getPolicy).map(String::new)
				.forEach(System.out::println);
	}

	// get as policy set test
	private void testGetAsPolicySet(String policyId, String wildcard,
			String policySetId) {
		System.out.println(new String(sut.getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId,
				PolicyCombiningAlgIds.FIRST_APPLICABLE).getPolicy()));
	}

	// getPolicyCombiningAlgIds test
	private void testGetPolicyCombiningAlgIds() {
		System.out.println(sut.getPolicyCombiningAlgIds());
	}

	// get test single
	private void testGetSingle(String policyId) {
		System.out.println(new String(sut.getPolicy(policyId).getPolicy()));
	}

	// post test: SUCCESS (if not already published)
	private void testPost(boolean force) throws UnsupportedEncodingException {
		final PolicyContentContainerDto request = new PolicyContentContainerDto(
				new PolicyContentDto(POLICY1.getBytes("UTF-8")),
				new PolicyContentDto(POLICY2.getBytes("UTF-8")));
		PolicyMetadataContainerDto response = null;
		try {
			response = sut.addPolicies(request, force);
		} catch (final HttpStatusCodeException e) {
			logger.error(e.getMessage(), e);
			logger.error(e.getResponseBodyAsString());
		}
		response.getPolicies()
				.stream()
				.forEach(
						dto -> System.out.println("id:" + dto.getId()
								+ "; valid:" + dto.isValid()));
	}

	// update test
	private void testPut(String policyId) {

		final PolicyMetadataDto response = sut.updatePolicy(
				new PolicyContentDto(POLICY1.getBytes()), policyId);
		System.out.println(response.getId());
		System.out.println(response.isValid());
	}

}
