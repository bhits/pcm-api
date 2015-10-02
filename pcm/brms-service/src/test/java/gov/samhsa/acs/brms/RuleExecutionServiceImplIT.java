package gov.samhsa.acs.brms;

import gov.samhsa.acs.brms.RuleExecutionServiceImpl;
import gov.samhsa.acs.brms.guvnor.GuvnorServiceImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Integration test for RuleExecution and Guvnor services.
 */
public class RuleExecutionServiceImplIT {

	/** The sut. */
	private RuleExecutionServiceImpl sut;

	/** The clinical facts. */
	private String clinicalFacts;

	private String endpointAddressGuvnorService;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutionServiceImplIT.class);

	/**
	 * Set up.
	 */
	@Before
	public void setUp() {
		endpointAddressGuvnorService = "http://localhost:8080/guvnor-5.5.0.Final-tomcat-6.0/rest/packages/AnnotationRules/source";

		sut = new RuleExecutionServiceImpl(new GuvnorServiceImpl(
				endpointAddressGuvnorService,"admin", "admin"), new SimpleMarshallerImpl());
	}

	/**
	 * Test assert and execute clinical facts_ returns_ execution response.
	 */
	// Integration test
	@Test
	public void testAssertAndExecuteClinicalFacts_Returns_ExecutionResponse() {
		clinicalFacts = "<FactModel><xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>71fe0397-3684-4acb-9811-6416c5c77b55</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId></xacmlResult>"
				+ "<ClinicalFacts><ClinicalFact><code>66214007</code><displayName>Substance abuse (disorder)</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>d17e216</observationId></ClinicalFact><ClinicalFact><code>DOCUMENT</code></ClinicalFact></ClinicalFacts></FactModel>";

		AssertAndExecuteClinicalFactsResponse response = sut
				.assertAndExecuteClinicalFacts(clinicalFacts);
		String ruleExecutionContainerXML = response
				.getRuleExecutionResponseContainer();
		LOGGER.debug("\n\n" + ruleExecutionContainerXML);

		Assert.assertNotNull(ruleExecutionContainerXML);
	}

	/**
	 * Test Clinical Rule Mental health problem (finding) REDACT rule
	 */
	// Integration test
	@Test
	public void testRule_clinicalRuleMentalHealthProblemREDACT() {
		clinicalFacts = "<FactModel><xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>71fe0397-3684-4acb-9811-6416c5c77b55</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId></xacmlResult><ClinicalFacts><ClinicalFact><code>413307004</code><displayName>Mental health problem (finding)</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>d17e216</observationId></ClinicalFact></ClinicalFacts></FactModel>";

		AssertAndExecuteClinicalFactsResponse response = sut
				.assertAndExecuteClinicalFacts(clinicalFacts);
		String ruleExecutionContainerXML = response
				.getRuleExecutionResponseContainer();
		LOGGER.debug("\n\n" + ruleExecutionContainerXML);
		Assert.assertNotNull(ruleExecutionContainerXML);
		// Assertions below need a particular rule in Gunvor
		Assert.assertTrue("Sensitivity isn't PSY", ruleExecutionContainerXML
				.contains("<sensitivity>PSY</sensitivity>"));
		Assert.assertTrue("Confidentiality isn't R", ruleExecutionContainerXML
				.contains("<impliedConfSection>R</impliedConfSection>"));
		Assert.assertTrue("US Privacy Law isn't 42CFRPart2",
				ruleExecutionContainerXML
						.contains("<USPrivacyLaw>42CFRPart2</USPrivacyLaw>"));
		Assert.assertTrue(
				"Document Refrain Policy isn't NODSCLCD",
				ruleExecutionContainerXML
						.contains("<documentRefrainPolicy>NODSCLCD</documentRefrainPolicy>"));
		Assert.assertTrue(
				"Document Obligation Policy isn't ENCRYPT",
				ruleExecutionContainerXML
						.contains("<documentObligationPolicy>ENCRYPT</documentObligationPolicy>"));
		Assert.assertTrue("Clinical fact returned with a different code",
				ruleExecutionContainerXML.contains("<code>413307004</code>"));

	}
}
