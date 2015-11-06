package gov.samhsa.acs.brms.ws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import gov.samhsa.consent2share.contract.ruleexecutionservice.RuleExecutionService;
import gov.samhsa.consent2share.contract.ruleexecutionservice.RuleExecutionServicePortType;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsRequest;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class RuleExecutionServiceImplEndPointTest {

	private URL wsdlURL;
	private String address;
	private QName serviceName;
	private String ruleExecutionContainer;
	private String clinicalFactsXML;
	private Endpoint ep;
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutionServiceImplEndPointTest.class);
	
	private AssertAndExecuteClinicalFactsResponse assertAndExecuteResponse = new AssertAndExecuteClinicalFactsResponse(); 
	private gov.samhsa.acs.brms.RuleExecutionService ruleExecutionServiceMock = mock(gov.samhsa.acs.brms.RuleExecutionService.class);
	
	@Before
	public void setUp() throws Exception {	
		Resource resource = new ClassPathResource("/jettyServerPortForTesing.properties");
    	Properties props = PropertiesLoaderUtils.loadProperties(resource);
    	String portNumber = props.getProperty("jettyServerPortForTesing.number");

        address = String.format("http://localhost:%s/services/RuleExecutionService", portNumber);
        
		serviceName = new QName("http://www.samhsa.gov/consent2share/contract/RuleExecutionService", "RuleExecutionService");
		new QName("http://www.samhsa.gov/consent2share/contract/RuleExecutionService", "RuleExecutionServicePort");

		wsdlURL = new URL(address + "?wsdl");
		clinicalFactsXML = "<FactModel><xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>71fe0397-3684-4acb-9811-6416c5c77b55</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId></xacmlResult><ClinicalFacts><ClinicalFact><code>111880001</code><displayName>Acute HIV</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>d11275e7-67ae-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>66214007</code><displayName>Substance Abuse Disorder</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7</observationId></ClinicalFact><ClinicalFact><code>234391009</code><displayName>Sickle Cell Anemia</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>ab1791b0-5c71-11db-b0de-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>233604007</code><displayName>Pneumonia</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>9d3d416d-45ab-4da1-912f-4583e0632000</observationId></ClinicalFact><ClinicalFact><code>22298006</code><displayName>Myocardial infarction</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId/></ClinicalFact><ClinicalFact><code>77386006</code><displayName>Patient currently pregnant</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId/></ClinicalFact><ClinicalFact><code>70618</code><displayName>Penicillin</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Allergies and Adverse Reactions</c32SectionTitle><c32SectionLoincCode>48765-2</c32SectionLoincCode><observationId>4adc1020-7b14-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>1191</code><displayName>Aspirin</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Allergies and Adverse Reactions</c32SectionTitle><c32SectionLoincCode>48765-2</c32SectionLoincCode><observationId>eb936011-7b17-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>2670</code><displayName>Codeine</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Allergies and Adverse Reactions</c32SectionTitle><c32SectionLoincCode>48765-2</c32SectionLoincCode><observationId>c3df3b60-7b18-11db-9fe1-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>6736007</code><displayName>moderate</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED</codeSystemName><c32SectionTitle>Allergies and Adverse Reactions</c32SectionTitle><c32SectionLoincCode>48765-2</c32SectionLoincCode><observationId/></ClinicalFact><ClinicalFact><code>307782</code><displayName>Albuterol 0.09 MG/ACTUAT inhalant solution</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><substanceAdministrationId>cdbd33f0-6cde-11db-9fe1-0800200c9a66</substanceAdministrationId></ClinicalFact><ClinicalFact><code>309362</code><displayName>Clopidogrel 75 MG oral tablet</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><substanceAdministrationId>cdbd5b05-6cde-11db-9fe1-0800200c9a66</substanceAdministrationId></ClinicalFact><ClinicalFact><code>430618</code><displayName>Metoprolol 25 MG oral tablet</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><substanceAdministrationId>cdbd5b01-6cde-11db-9fe1-0800200c9a66</substanceAdministrationId></ClinicalFact><ClinicalFact><code>312615</code><displayName>Prednisone 20 MG oral tablet</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><substanceAdministrationId>cdbd5b03-6cde-11db-9fe1-0800200c9a66</substanceAdministrationId></ClinicalFact><ClinicalFact><code>197454</code><displayName>Cephalexin 500 MG oral tablet</displayName><codeSystem>2.16.840.1.113883.6.88</codeSystem><codeSystemName>RxNorm</codeSystemName><c32SectionTitle>Medications</c32SectionTitle><c32SectionLoincCode>10160-0</c32SectionLoincCode><substanceAdministrationId>cdbd5b07-6cde-11db-9fe1-0800200c9a66</substanceAdministrationId></ClinicalFact><ClinicalFact><code>30313-1</code><displayName>HGB</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>107c2dc0-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>33765-9</code><displayName>WBC</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>8b3fa370-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>26515-7</code><displayName>PLT</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>80a6c740-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>2951-2</code><displayName>NA</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>a40027e1-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>2823-3</code><displayName>K</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>a40027e2-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>2075-0</code><displayName>CL</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>a40027e3-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>1963-8</code><displayName>HCO3</displayName><codeSystem>2.16.840.1.113883.6.1</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId>a40027e4-67a5-11db-bd13-0800200c9a66</observationId></ClinicalFact><ClinicalFact><code>43789009</code><displayName>CBC WO DIFFERENTIAL</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId/></ClinicalFact><ClinicalFact><code>20109005</code><displayName>LYTES</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName>SNOMED CT</codeSystemName><c32SectionTitle>Diagnostic Results</c32SectionTitle><c32SectionLoincCode>30954-2</c32SectionLoincCode><observationId/></ClinicalFact></ClinicalFacts></FactModel>";	
		ruleExecutionContainer = "<ruleExecutionContainer><executionResponseList><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><c32SectionTitle>Problems</c32SectionTitle><code>66214007</code><codeSystemName>SNOMED CT</codeSystemName><displayName>Substance Abuse Disorder</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NORDSLCD</documentRefrainPolicy><impliedConfSection>R</impliedConfSection><itemAction>REDACT</itemAction><observationId>e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7</observationId><sensitivity>ETH</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><c32SectionTitle>Problems</c32SectionTitle><code>111880001</code><codeSystemName>SNOMED CT</codeSystemName><displayName>Acute HIV</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NORDSLCD</documentRefrainPolicy><impliedConfSection>R</impliedConfSection><itemAction>MASK</itemAction><observationId>d11275e7-67ae-11db-bd13-0800200c9a66</observationId><sensitivity>HIV</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse></executionResponseList></ruleExecutionContainer>";		
		
		assertAndExecuteResponse.setRuleExecutionResponseContainer(ruleExecutionContainer);
		
		ep = Endpoint.publish(address, new RuleExecutionServiceWsImpl(ruleExecutionServiceMock));
	}
	
	/*
	 * This test uses wsimport/wsdl2java generated artifacts, both service and
	 * SEI
	 */
	@Test
	public void assertAndExecuteClinicalFactsWithGeneratedServiceAndSei() {
		// Arrange
	    RuleExecutionService service = new RuleExecutionService(wsdlURL, serviceName);
		RuleExecutionServicePortType port = service.getRuleExecutionServicePort();
		
		AssertAndExecuteClinicalFactsRequest request = new AssertAndExecuteClinicalFactsRequest();
				
		when(ruleExecutionServiceMock.assertAndExecuteClinicalFacts(anyString())).thenReturn(assertAndExecuteResponse);
		
		// Act
		AssertAndExecuteClinicalFactsResponse response = port.assertAndExecuteClinicalFacts(request);
		
		// Assert		
		validateResponse(response);
	}
	
	private void validateResponse(AssertAndExecuteClinicalFactsResponse response) {
		assertEquals("RuleExecutionService not returning expected ExecutionResponseContainer",
				assertAndExecuteResponse.getRuleExecutionResponseContainer(), response.getRuleExecutionResponseContainer());
		
	}
	
	@After
	public void tearDown() {
		try {
			ep.stop();
		} catch (Throwable t) {
			LOGGER.error(t.getMessage(),t);
		}
	}

}
