package gov.samhsa.acs.documentsegmentation.brms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.brms.RuleExecutionServiceImpl;
import gov.samhsa.acs.brms.guvnor.GuvnorService;
import gov.samhsa.acs.brms.guvnor.GuvnorServiceImpl;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsRequest;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleExecutionServiceImplTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String FACT_MODEL = "<FactModel><xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>71fe0397-3684-4acb-9811-6416c5c77b55</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId></xacmlResult><ClinicalFacts><ClinicalFact><code>66214007</code><displayName>Substance abuse (disorder)</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>d17e216</observationId></ClinicalFact><ClinicalFact><code>DOCUMENT</code></ClinicalFact></ClinicalFacts></FactModel>";
	private static final String RULE_EXECUTION_CONTAINER = "<ruleExecutionContainer><executionResponseList><executionResponse><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NODSCLCD</documentRefrainPolicy><USPrivacyLaw>_42CFRPart2</USPrivacyLaw></executionResponse><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><code>66214007</code><codeSystem>2.16.840.1.113883.6.96</codeSystem><displayName>Substance abuse (disorder)</displayName><impliedConfSection>R</impliedConfSection><observationId>d17e216</observationId><sensitivity>ETH</sensitivity></executionResponse></executionResponseList></ruleExecutionContainer>";

	private static AssertAndExecuteClinicalFactsRequest request;
	private static AssertAndExecuteClinicalFactsResponse response;

	private static RuleExecutionServiceImpl ruleExecutionServiceImpl;
	private static RuleExecutionServiceImpl ruleExecutionServiceImplSpy;

	@BeforeClass
	public static void setUp() throws Exception {
		response = new AssertAndExecuteClinicalFactsResponse();
		response.setRuleExecutionResponseContainer(RULE_EXECUTION_CONTAINER);
		request = new AssertAndExecuteClinicalFactsRequest();
		request.setClinicalFactXmlString(FACT_MODEL);

		ruleExecutionServiceImpl = mock(RuleExecutionServiceImpl.class);
		when(
				ruleExecutionServiceImpl
						.assertAndExecuteClinicalFacts(FACT_MODEL)).thenReturn(
				response);
	}

	@Test
	public void testAssertAndExecuteClinicalFacts() {
		AssertAndExecuteClinicalFactsResponse result = ruleExecutionServiceImpl
				.assertAndExecuteClinicalFacts(FACT_MODEL);
		//logger.debug(result);
		assertEquals(RULE_EXECUTION_CONTAINER, result.getRuleExecutionResponseContainer());
	}
}
